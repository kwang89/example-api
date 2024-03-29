package com.example.project.global.dto;

import com.example.project.constant.CharacterConstant;
import com.example.project.global.code.GlobalErrorCode;
import com.example.project.global.exception.InternalServerErrorException;
import com.example.project.global.masking.MaskingField;
import com.google.common.base.MoreObjects;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.springframework.util.ReflectionUtils;

public class BaseDto {

    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                String name = field.getName();
                if (!Modifier.isPublic(field.getModifiers())) {
                    ReflectionUtils.makeAccessible(field);
                }
                Object value =
                    field.isAnnotationPresent(MaskingField.class) ? fillMask(field.get(this))
                        : field.get(this);
                helper.add(name, value);

            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new InternalServerErrorException(GlobalErrorCode.ILLEGAL_ACCESS, e);
            }
        }
        return helper.toString();
    }

    private String fillMask(Object obj) {
        return CharacterConstant.ASTERISK.repeat(String.valueOf(obj).length());
    }
}
