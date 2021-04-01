package com.utils.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xs
 * @date 2021/04/01 22:08
 */
public class ExecutorController {
    @Autowired

    private AsyncService asyncService;



    @RequestMapping("/ececutor")

    public String submit(){

        System.err.println("start submit");


        //调用service层的任务

        asyncService.executeAsync();


        System.err.println("end submit");

        return "success";

    }
}
