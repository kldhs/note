package com.utils;

import com.utils.spring.SpringBootUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(
        exclude= {DataSourceAutoConfiguration.class}
)
@EnableAsync
//开启定时任务
//@EnableScheduling
/**
 * 继承SpringBootServletInitializer可以使用外部tomcat，自己可以设置端口号，项目名
 */
public class UtilsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UtilsApplication.class);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {


        ConfigurableApplicationContext context = SpringApplication.run(UtilsApplication.class, args);
        System.err.println(context.getEnvironment());
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Test.aopTest();
        }
    }

    static List<List<Integer>> res = new ArrayList<List<Integer>>();

    private static void helper(ArrayList<Integer> candidates, Integer target, ArrayList<Integer> list, int index) {
        if( target == 0){
            if(!res.contains(list)){
                res.add(new ArrayList<>(list));
            }
        }
        for (int i = index; i < candidates.size(); i++) {
            if(candidates.get(i) <= target){
                list.add(candidates.get(i));
                helper(candidates, target-candidates.get(i), list, i+1);
                list.remove(list.size()-1);
            }
        }
    }





}
