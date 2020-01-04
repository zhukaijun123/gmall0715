package com.atguigu.gmall0715.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableTransactionManagement //开启事务   同时在impl 类上添加注解
@MapperScan(basePackages = "com.atguigu.gmall0715.manage.mapper")
public class GmallManageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallManageServiceApplication.class, args);
	}

}
