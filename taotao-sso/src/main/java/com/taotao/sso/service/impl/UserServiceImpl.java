package com.taotao.sso.service.impl;

import com.taotao.common.*;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.print.PrintException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hmt
 * @date 2019/8/10 16:55
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${taotao.redis.login.token.prefix}")
    private String userTokenRedisKeyPrefix;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public TaotaoResult checkData(String param, int type) {
        TaotaoResult taotaoResult = null;
        boolean result = false;
        switch (type){
            case SystemConstants.TAOTAO_REGISTER_CHECK_TYPE_USERNAME:
                result = checkUserName(param);
                break;
            case SystemConstants.TAOTAO_REGISTER_CHECK_TYPE_PHONE:
                result = checkPhone(param);
                break;
            case SystemConstants.TAOTAO_REGISTER_CHECK_TYPE_EMAIL:
                result = checkEmail(param);
                break;
            default:
                taotaoResult = TaotaoResult.ok("不支持的数据检查类型"+type);
                break;
        }
        if (taotaoResult == null){
            if(result){
                taotaoResult = TaotaoResult.ok(result);
            }
            else {
                taotaoResult = TaotaoResult.build(500,"数据已存在");
            }
        }
        return taotaoResult;
    }

    @Override
    public TaotaoResult register(TbUser tbUser) {
        if(tbUser == null){
            return TaotaoResult.build(500,"error","数据不能为空");
        }
        try {
            //完善用户信息
            //加密密码
            String md5Pwd = DigestUtils.md5Hex(tbUser.getPassword().getBytes());
            tbUser.setPassword(md5Pwd);
            tbUser.setCreated(new Date());
            tbUser.setUpdated(new Date());
            //保存到数据库
            tbUserMapper.insert(tbUser);
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"保存异常");
        }
    }

    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return TaotaoResult.build(500,"用户名或密码不能为空!");
        }
        try {
            TbUserExample condition = new TbUserExample();
            condition.createCriteria().andUsernameEqualTo(username);

            List<TbUser> userList = tbUserMapper.selectByExample(condition);
            if(userList == null || userList.size() < 1) {
                return TaotaoResult.build(500,"用户名或密码错误!");
            }
            TbUser userInfo = userList.get(0);

            String md5Password = DigestUtils.md5Hex(password);
            if(!userInfo.getPassword().equals(md5Password)) {
                return TaotaoResult.build(500,"用户名或密码错误!");
            }
            // 生成用户Token(也可以使用JWT工具生成token，安全性更高)
            String token = IDUtils.genImageNameByUUID();
            // 生成Redis Key，将token前面加上统一的开头，方便统计
            String redisKey = getTokenRedisKey(token);
            // 存储到Redis之前，将用户密码清除。(可以降低安全风险)
            userInfo.setPassword(null);
            // 将用户信息存储到Redis
            redisTemplate.opsForValue().set(redisKey, JsonUtils.objectToJson(userInfo));
            // 设置Token过期时间
            redisTemplate.expire(redisKey, 30, TimeUnit.MINUTES);

            // 将Token写入Cookie
            CookieUtils.setCookie(request, response, "TT_TOKEN", token, (int)TimeUnit.MINUTES.toSeconds(30));

            return TaotaoResult.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"登录失败", ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 通过token获取用户登录信息
     * @param token
     * @return
     */
    @Override
    public TaotaoResult getUserByToken(String token) {
        if(StringUtils.isBlank(token)) {
            return TaotaoResult.build(500,"token不能为空！");
        }
        String redisKey = getTokenRedisKey(token);
        TbUser tbUser = null;
        try {
            String userJson = redisTemplate.opsForValue().get(redisKey);
            tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
        } catch (Exception e) {
            System.out.println(("获取用户信息失败, token: " + token));
        }
        if(tbUser == null) {
            return TaotaoResult.build(500,"获取用户信息失败");
        }
        return TaotaoResult.ok(tbUser);
    }

    @Override
    public TaotaoResult deleteUserByToken(String token, HttpServletRequest request, HttpServletResponse response) {
        String redisKey = getTokenRedisKey(token);
        boolean result = redisTemplate.delete(redisKey);
        CookieUtils.deleteCookie(request,response,"TT_TOKEN");
        if (result == true){
            return   TaotaoResult.ok("退出成功");
        }else {
            return TaotaoResult.build(500,"还未登录");
        }
    }


    private String getTokenRedisKey(String token) {
        return userTokenRedisKeyPrefix + "-" + token;
    }


    private boolean checkEmail(String param) {
        TbUserExample condition = new TbUserExample();
        condition.createCriteria().andEmailEqualTo(param);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(condition);
        //如果查出来数据，表明数据已被用过，不能再用了,要返回false
        return !(tbUsers != null && tbUsers.size() > 0);
    }

    private boolean checkPhone(String param) {
        TbUserExample condition = new TbUserExample();
        condition.createCriteria().andPhoneEqualTo(param);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(condition);
        //如果查出来数据，表明数据已被用过，不能再用了
        return !(tbUsers != null && tbUsers.size() > 0);
    }

    private boolean checkUserName(String param) {
        TbUserExample condition = new TbUserExample();
        condition.createCriteria().andUsernameEqualTo(param);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(condition);
        //如果查出来数据，表明数据已被用过，不能再用了
        return !(tbUsers != null && tbUsers.size() > 0);
    }
}
