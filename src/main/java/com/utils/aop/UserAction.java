package com.utils.aop;

/**
 * @author xs
 * @date 2021/11/10 11:15
 */
import org.springframework.stereotype.Controller;

@Controller
public class UserAction{

    public void   login(){
        System.out.println("  【UserAction】 用户登录 ... ");
    }

}
