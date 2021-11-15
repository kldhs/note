package com.utils.aop;

/**
 * @author xs
 * @date 2021/11/10 11:15
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class UserAction{
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);
    public void   login(){
        logger.info("  【UserAction】 用户登录 ... ");
    }

}
