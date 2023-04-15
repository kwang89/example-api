package com.example.api.global.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.example.api.constant.CharacterConstant;
import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.InternalServerErrorException;
import com.example.api.global.masking.MaskingField;
import com.google.common.base.MoreObjects;

public class BaseDto {
	@Override

	public String toString() {
		MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);

		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				String name = field.getName();
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				Object value =
					field.isAnnotationPresent(MaskingField.class) ? fillMask(field.get(this)) : field.get(this);
				helper.add(name, value);

			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new InternalServerErrorException(GlobalErrorCode.ILLEGAL_ACCESS, e);
			}
		}
		return helper.toString();
	}

	private final String fillMask(Object obj) {
		return CharacterConstant.ASTERISK.repeat(String.valueOf(obj).length());
	}
}
