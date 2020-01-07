package com.atguigu.gmall0715.manage.controller;


import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class FileUploadController {

    @Value("${fileServer.url}")
    private String fileUrl; // fileUrl = http://192.168.200.129

    //对于服务器id来讲 都应该在应用程序中实现软编码
    //springmvc文件上传
    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file)  throws IOException, MyException { //应该获取的是动态的图片的地址
        String imgUrl = fileUrl;
        if(file != null){
            String configFile  = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile );
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String originalFilename = file.getOriginalFilename();//获取的是green.png
            //String orginalFilename="f://green.png";
            //设置文件的后缀名
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            String[] upload_file = storageClient.upload_file(file.getBytes(),extName, null);
            for (int i = 0; i < upload_file.length; i++) {
                //imgUrl = http://192.168.200.129
                String path = upload_file[i];
                //字符串拼接
              imgUrl+="/"+path;
            }
        }
        // imgUrl =  http://192.168.200.129/group1/M00/00/00/wKjIgV4T4oyAfLGoAAQO7rb14cE056.png
        return imgUrl;
    }
}
