package com.example.pojo;



import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

 

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

 

@RestController
@RequestMapping("/press")
public class UploadPicture {
	//获取当前日期时间的string类型用于文件名防重复
	public String dates(){       
		Date currentTime = new Date();    
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");   
		String dateString = formatter.format(currentTime);     
		return dateString;    }
	
    @RequestMapping(value = "/weChat")

    public ModelAndView uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("进入get方法！");

 

        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;

        MultipartFile multipartFile =  req.getFile("file");

 

        String realPath = "C:\\Users\\11578\\Documents\\workspace-sts-3.9.8.RELEASE\\demo5-1\\upload\\wximg";

        try {

            File dir = new File(realPath);

            if (!dir.exists()) {

                dir.mkdir();

            }

            File file  =  new File(realPath,"aaa.jpg");

            multipartFile.transferTo(file);

        } catch (IOException e) {

            e.printStackTrace();

        } catch (IllegalStateException e) {

            e.printStackTrace();

        }

        return null;

    }

 

}
