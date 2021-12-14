package com.utils.xxx;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lxy
 * @Date: 2020/8/20 14:55
 * @Desc: DO跟DTO转换的抽象类
 */
@Slf4j
public abstract class AbstractConvert<DO, DTO> {
    /**
     * 转换成DTO
     *
     * @param a do
     * @return
     */
    public <DTO> DTO convertToDTO(DO a) {
        DTO r = null;
        if (null == a) {
            return r;
        }
        Class<? extends AbstractConvert> aClass = getClass();
        Type genericSuperclass = getClass().getGenericSuperclass();
        String typeName = genericSuperclass.getTypeName();
        try {
            if (!StringUtils.contains(typeName, ".AbstractConvert")) {
                Class<?> aClass2 = Class.forName(typeName);
                genericSuperclass = aClass2.getGenericSuperclass();
            }
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Class<DTO> tClass = (Class<DTO>) (parameterizedType).getActualTypeArguments()[1];
            r = tClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(aClass.getName() + " covert to DTO error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(aClass.getName() + " covert to DTO error");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(aClass.getName() + " covert to DTO error");
        }
        BeanUtils.copyProperties(a, r);
        return r;
    }

    /**
     * 转换成DTO列表
     *
     * @param dos
     * @return
     */
    public List<DTO> convertToListDTO(List<DO> dos) {
        List<DTO> dtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(dos)) {
            return dtos;
        }
        for (DO aDo : dos) {
            DTO dto = convertToDTO(aDo);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 转换成DO
     *
     * @param b dto
     * @return
     */
    public <DO> DO convertToDO(DTO b) {
        DO r = null;
        if (null == b) {
            return r;
        }
        Class<? extends AbstractConvert> aClass = getClass();
        Type genericSuperclass = getClass().getGenericSuperclass();
        String typeName = genericSuperclass.getTypeName();
        try {
            if (!StringUtils.contains(typeName, ".AbstractConvert")) {
                Class<?> aClass2 = Class.forName(typeName);
                genericSuperclass = aClass2.getGenericSuperclass();
            }
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Class<DO> tClass = (Class<DO>) (parameterizedType).getActualTypeArguments()[0];
            r = tClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(aClass.getName() + " covert to DO error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(aClass.getName() + " covert to DO error");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(aClass.getName() + " covert to DO error");
        }

        BeanUtils.copyProperties(b, r);
        return r;
    }

    /**
     * 转成DO List
     *
     * @param dtos
     * @return
     */
    public List<DO> convertToListDO(List<DTO> dtos) {
        List<DO> dos = new ArrayList<>();
        if (CollectionUtils.isEmpty(dtos)) {
            return dos;
        }
        for (DTO dto : dtos) {
            DO ado = convertToDO(dto);
            dos.add(ado);
        }
        return dos;
    }

    public static void main(String[] args) {
        //RoleConvertDTO convert = new RoleConvertDTO();
        //RoleDO roleDO = new RoleDO();
        //RoleDTO roleDTO = convert.convertToDTO(roleDO);

    }
}