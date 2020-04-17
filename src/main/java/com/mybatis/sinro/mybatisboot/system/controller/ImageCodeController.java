package com.mybatis.sinro.mybatisboot.system.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mybatis.sinro.mybatisboot.common.annotation.PathRestController;
import com.mybatis.sinro.mybatisboot.common.constant.NumberConstants;
import com.mybatis.sinro.mybatisboot.system.entity.Cache;
import com.mybatis.sinro.mybatisboot.system.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tan.hongwei
 */
@PathRestController("/check")
public class ImageCodeController {
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Autowired
    private ICacheService cacheService;

    private final static Logger logger = LoggerFactory.getLogger(ImageCodeController.class);
    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/createImageCode")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到内存中
            String key = request.getParameter("code");
            String createText = defaultKaptcha.createText();
            //redisService.set(key,createText);
            //GuavaUtils.storeImageCode(createText);
            Cache cache = new Cache();
            cache.setCodeKey(key);
            cache.setCodeValue(createText);
            cache.setType(NumberConstants.ONE);
            cacheService.add(cache);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            logger.info("createImageCode:{}",createText+key);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 验证码
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/checkImageCode/{inputImageCode}")
    @ResponseBody
    public Object checkImageCode(HttpServletRequest request,
                                 HttpServletResponse response, @PathVariable String inputImageCode) {
        Map<String, Object> result = new HashMap<>();
        //String imageCode = GuavaUtils.getImageCode();
        String key = request.getParameter("code");
        String imageCode = cacheService.checkCode(key);
        logger.info("imageCode:{},inputImageCode:{}",imageCode,inputImageCode);
        if (StringUtils.isEmpty(imageCode) || !imageCode.equals(inputImageCode)) {
            result.put("code", "201");
            result.put("msg", "error");
            result.put("result","验证码错误");
        } else {
            result.put("code", "200");
            result.put("msg", "success");
        }
        return result;
    }
}
