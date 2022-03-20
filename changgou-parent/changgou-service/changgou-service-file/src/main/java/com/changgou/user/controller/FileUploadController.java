package com.changgou.user.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
*
*@ClassName: FileUploadController
*@Description
*@Author yang
*@Date 2020/9/22
*@Time 18:36
*/
@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
public class FileUploadController {
    @PostMapping
    public Result upload(@RequestParam(value = "file")MultipartFile file)throws  Exception{
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),//文件名
                file.getBytes(),//文件字节数组
                org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename())//获取文件扩展名
        );
        String[] uploads = FastDFSUtil.upload(fastDFSFile);
        //拼接访问地址
        //String url="http://192.168.211.132:8080"+"/"+uploads[0]+"/"+uploads[1];
        String url=FastDFSUtil.getTrackerInfo()+"/"+uploads[0]+"/"+uploads[1];
        return new Result(true,StatusCode.OK,"上传成功",url);
    }
}
