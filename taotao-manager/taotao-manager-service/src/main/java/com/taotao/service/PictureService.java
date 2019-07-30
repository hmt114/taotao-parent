package com.taotao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author hmt
 * @date 2019/7/29 20:21
 */
@Service
public interface PictureService {
    Map<String, Object> uploadPicture(MultipartFile file);
}
