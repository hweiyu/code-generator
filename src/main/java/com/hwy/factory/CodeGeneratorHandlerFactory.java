package com.hwy.factory;

import com.hwy.bean.CodeGeneratorBean;
import com.hwy.handler.CodeGeneratorHandler;
import com.hwy.handler.CodeGeneratorHandlerImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.zip.ZipOutputStream;

/**
 * 异常处理器
 *
 * @author huangweiyu
 * @version V1.0
 * @date 10:33
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CodeGeneratorHandlerFactory {

    private CodeGeneratorBean param;

    private ZipOutputStream zip;

    public CodeGeneratorHandler createHandler() {
        return new CodeGeneratorHandlerImpl(getParam(), getZip());
    }
}
