package com.example.project.configuration.jackson.module;

import com.example.project.configuration.jackson.ser.EnumSerializer;
import com.example.project.global.code.base.BaseErrorCode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;

public class EnumModule extends SimpleModule {

    @Override
    public String getModuleName() {
        return this.getClass().getName();
    }

    @Override
    public Version version() {
        return super.version();
    }

    public EnumModule(MessageSourceAccessor messageSourceAccessor) {
        this.addSerializer(BaseErrorCode.class, new EnumSerializer(messageSourceAccessor));
    }

}
