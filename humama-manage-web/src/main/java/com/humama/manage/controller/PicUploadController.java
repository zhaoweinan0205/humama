package com.humama.manage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humama.common.bean.PicUploadResult;
import com.humama.manage.service.PropertieService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/13 .
 * @Version: 1.0 .
 */
@RequestMapping(value = "pic")
@Controller
public class PicUploadController {

    @Autowired
    private PropertieService propertieService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };

    @RequestMapping(value = "upload",
            method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile")MultipartFile uploadFile){
        //验证图片格式
        //是否合法
        boolean isLegal = false;
        for (String type : IMAGE_TYPE){
            if (StringUtils.endsWithIgnoreCase(
                    uploadFile.getOriginalFilename(), type)){
                isLegal = true;
                break;
            }
        }

        // 封装Result对象，并且将文件的byte数组放置到result对象中
        PicUploadResult fileUploadResult = new PicUploadResult();

        // 状态
        fileUploadResult.setError(isLegal ? 0 : 1);

        // 文件新路径
        String filePath = getFilePath(uploadFile.getOriginalFilename());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Pic file upload .[{}] to [{}] .", uploadFile.getOriginalFilename(), filePath);
        }

        // 生成图片的绝对引用地址
        String picUrl = StringUtils.replace(StringUtils.substringAfter(
                filePath,propertieService.REPOSITORY_PATH), "\\", "/");

        fileUploadResult.setUrl(propertieService.IMAGE_BASE_URL + picUrl);

        File newFile = new File(filePath);

        // 写文件到磁盘
        try {
            uploadFile.transferTo(newFile);

            // 校验图片是否合法
            isLegal = false;

            BufferedImage image = ImageIO.read(newFile);
            if (image != null){
                fileUploadResult.setWidth(image.getWidth() + "");
                fileUploadResult.setHeight(image.getHeight() + "");
                isLegal = true;
            }
        } catch (IOException e) {
            LOGGER.error("写文件异常文件名为{},异常为{}", uploadFile.getOriginalFilename(),e);
        }

        fileUploadResult.setError(isLegal ? 0 : 1);

        if (!isLegal){
            //不合法，将磁盘上的文件删除
            newFile.delete();
        }

        //将java对象转化成（序列化）json字符串
        try {
            return mapper.writeValueAsString(fileUploadResult);
        } catch (JsonProcessingException e) {
            LOGGER.error("fileUploadResult序列化为json字符串异常,异常为{}",e);
        }
        return null;
    }


    private String getFilePath(String sourceFilePath){
        String baseFolder = this.propertieService.REPOSITORY_PATH + File.separator +
                "images";
        Date date = new Date();
        // yyyy/MM/dd
        String fileFolder = baseFolder + File.separator + new DateTime(date).toString("yyyy")
                + File.separator + new DateTime(date).toString("MM")
                + File.separator + new DateTime(date).toString("dd");
        File file = new File(fileFolder);

        if (!file.isDirectory()){
            //如果目录不存在，则创建目录
            file.mkdirs();
        }

        //生成新的文件名 yyyyMMddhhmmssSSSS
        String fileName = new DateTime(date).toString("yyyyMMddhhmmssSSSS")
                + RandomUtils.nextInt(100,9999) + "."
                + StringUtils.substringAfterLast(sourceFilePath,".");
        return fileFolder + File.separator + fileName;
    }

}
