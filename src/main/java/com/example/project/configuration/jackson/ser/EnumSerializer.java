package com.example.project.configuration.jackson.ser;

import com.example.project.global.code.base.BaseErrorCode;
import com.example.project.global.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.springframework.context.support.MessageSourceAccessor;

public class EnumSerializer extends StdSerializer<BaseErrorCode> {

    private final MessageSourceAccessor messageSourceAccessor;

    public EnumSerializer(MessageSourceAccessor messageSourceAccessor) {
        super(BaseErrorCode.class);
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @Override
    public void serialize(BaseErrorCode value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        Object[] objArr = null;
        Object obj = gen.getCurrentValue();
        if (obj instanceof ErrorResponse) {
            objArr = ((ErrorResponse) obj).getMessageArgs();
        }
        String errorMessage = messageSourceAccessor.getMessage(value.getErrorCode(), objArr);
        gen.writeString(value.getErrorCode());
        gen.writeStringField("message", errorMessage);
    }
}
