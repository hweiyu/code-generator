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
 * @author huangweiyu
 * @version V1.0
 * @Title: 描述
 * @Description: 获取生成代码处理器工厂类
 * @date 2018/8/13 10:10
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
