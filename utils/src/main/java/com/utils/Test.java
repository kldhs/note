package com.utils;

import com.utils.aop.UserAction;
import com.utils.rsa.RSAConfig;
import com.utils.rsa.RSAEncrypt;
import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.executorsandspring.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * @author xs
 * @date 2021/11/4 18:18
 * 测试方法
 */
@Slf4j
@Component
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static Logger logger1 = LoggerFactory.getLogger("login");
    private static Logger logger2 = LoggerFactory.getLogger("register");
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Resource
    RSAEncrypt rsaEncrypt;
    @Resource
    RSAConfig rsaConfig;
    /**
     * 异步线程池，测试方法
     */
    public static void asyncTest() {
        for (int i = 0; i <= 100; i++) {
            AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
            System.err.println(i + "----start submit");
            //调用service层的任务
            asyncService.executeAsync();
            System.err.println(i + "----end submit");
        }
    }

    /**
     * lslf4j 日志输出，测试方法
     */
    public static void slf4jTest() {
        logger.trace("1111111111111111111111111");
        logger.debug("2222222222222222222222222");
        logger.info("3333333333333333333333333");
        logger.warn("44444444444444444444444444");
        logger.error("55555555555555555555555555");
        logger1.trace("--------------1111111111111111111111111");
        logger1.debug("--------------2222222222222222222222222");
        logger1.info("--------------3333333333333333333333333");
        logger1.warn("--------------44444444444444444444444444");
        logger1.error("--------------55555555555555555555555555");
        logger2.trace("++++++++++++++1111111111111111111111111");
        logger2.debug("++++++++++++++2222222222222222222222222");
        logger2.info("++++++++++++++3333333333333333333333333");
        logger2.warn("++++++++++++++44444444444444444444444444");
        logger2.error("++++++++++++++55555555555555555555555555");

    }

    /**
     * lslf4j 日志输出，测试方法
     */
    public static void aopTest() {
        UserAction bean = SpringBootUtil.getBean(UserAction.class);
        bean.login();
    }

    /**
     * rsa 测试方法
     */
    public  void rsaTest() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Object key = rsaEncrypt.getKey(true, false, rsaConfig.getPubKeyPath());
        System.out.println(key);
    }

}
