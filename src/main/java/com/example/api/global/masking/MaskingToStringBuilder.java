package com.example.api.global.masking;

import com.example.api.constant.CharacterConstant;
import com.example.api.global.code.GlobalErrorCode;
import com.example.api.global.exception.InternalServerErrorException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class MaskingToStringBuilder extends ReflectionToStringBuilder {
  public MaskingToStringBuilder(Object object) {
    super(object);
  }

  public MaskingToStringBuilder(Object object, ToStringStyle style) {
    super(object, style);
  }

  public MaskingToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer) {
    super(object, style, buffer);
  }

  public <T> MaskingToStringBuilder(T object, ToStringStyle style, StringBuffer buffer, Class<? super T> reflectUpToClass, boolean outputTransients, boolean outputStatics) {
    super(object, style, buffer, reflectUpToClass, outputTransients, outputStatics);
  }

  public <T> MaskingToStringBuilder(T object, ToStringStyle style, StringBuffer buffer, Class<? super T> reflectUpToClass, boolean outputTransients, boolean outputStatics, boolean excludeNullValues) {
    super(object, style, buffer, reflectUpToClass, outputTransients, outputStatics, excludeNullValues);
  }

  @Override
  protected void appendFieldsIn(Class<?> clazz) {
    if (clazz.isArray()) {
      this.reflectionAppendArray(this.getObject());
      return;
    }

    final Field[] fields = clazz.getDeclaredFields();
    AccessibleObject.setAccessible(fields, true);
    for (final Field field : fields) {
      final String fieldName = field.getName();
      if (this.accept(field)) {
        try {
          // Warning: Field.get(Object) creates wrappers objects
          // for primitive types.
          Object fieldValue = this.getValue(field);
          if (fieldValue != null) {
            if (field.getType() == String.class && field.isAnnotationPresent(MaskingField.class)) {
              fieldValue = fillMask(((String) fieldValue).length());
            }
            this.append(fieldName, fieldValue);
          }
        } catch (final IllegalAccessException ex) {
          throw new InternalServerErrorException(GlobalErrorCode.ILLEGAL_ACCESS, ex);
        }
      }
    }
  }

  /**
   * 입력된 길이만큼 "*"로 문자열 생성
   * <pre>
   *   fillMask(3) : "***"
   *   fillMask(6) : "******"
   * </pre>
   *
   * @param length 길이
   * @return String
   */
  private String fillMask(int length) {
    return CharacterConstant.ASTERISK.repeat(length);
  }

}
