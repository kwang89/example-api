package com.example.project.configuration.jackson.deser;

import com.example.project.global.code.base.BaseErrorCode;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class EnumDeserializer extends StdDeserializer<Enum<? extends BaseErrorCode>> {

    public EnumDeserializer() {
        this(null);
    }
    protected EnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Enum<? extends BaseErrorCode> deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        return null;
    }
}
