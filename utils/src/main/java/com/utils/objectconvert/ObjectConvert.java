package com.utils.objectconvert;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xs
 * @date 2021/12/14 11:18
 * 复制属性
 */
public class ObjectConvert {
    /**
     * 使用多线程安全的Map来缓存BeanCopier，由于读操作远大于写，所以性能影响可以忽略
     */
    public static ConcurrentHashMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();

    /**
     * 通过cglib BeanCopier实现（每万次10ms）
     *
     * @param source
     * @param target
     */
    public static Object copyProperties(Object source, Object target) {
        String beanKey = source.getClass().toString() + target.getClass().toString();
        BeanCopier copier = null;
        if (beanCopierMap.containsKey(beanKey)) {
            copier = beanCopierMap.get(beanKey);
        } else {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.putIfAbsent(beanKey, copier);// putIfAbsent已经实现了原子操作。
        }
        copier.copy(source, target, null);
        return target;
    }

    /**
     * org.springframework.beans BeanUtils实现（每万次146ms）
     * 其他jar包也存在类似方法 如：
     * org.apache.commons.beanutils.BeanUtils（每万次251ms）
     * org.apache.commons.beanutils.PropertyUtils（每万次284ms）
     *
     * @param source
     * @param target
     */
    public static Object beanUtilCopyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static void main(String[] args) {
        A a = new A();
        B b = new  B();
        a.setAge(11);
        a.setGender("23");
        a.setHeight(43);
        a.setWeight(23);
        a.setName("11111111");
        BeanUtils.copyProperties(a, b);
        //ObjectConvert.copyProperties(a,b);
        System.out.println("11111111111");
    }


}
