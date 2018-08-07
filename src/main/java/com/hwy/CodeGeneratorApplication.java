package com.hwy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * boot启动类
 * @author huangweiyu
 * @version V1.0
 * @date 2018/8/7 14:06
 **/
@SpringBootApplication
@MapperScan("com.hwy.dao")
public class CodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGeneratorApplication.class, args);
	}
}
