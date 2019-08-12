package com.taotao.portal.conf;

import com.taotao.portal.interceptor.OrderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hmt
 * @date 2019/8/6 19:18
 */
@Configuration
public class MyMVCConfiguration implements WebMvcConfigurer {

    @Autowired
    private OrderInterceptor orderInterceptor;
    //配置拦截器，未登录前不让执行订单
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderInterceptor).addPathPatterns("/order/**");
    }

    /**
     * 设置URI匹配规则
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //设置URI后缀模式匹配支持，如handler匹配请求/index,
        // 那么你访问/index.*(你配置的后缀)也可以到达handler
        configurer.setUseSuffixPatternMatch(true);

    }

    /**
     * 改变SpringMVC DispatcherServlet默认配置
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
        // 除了默认的/，增加对*.html后缀请求的处理
        servletRegistrationBean.addUrlMappings("*.html");
        servletRegistrationBean.addUrlMappings("/");
        return servletRegistrationBean;
    }


}


