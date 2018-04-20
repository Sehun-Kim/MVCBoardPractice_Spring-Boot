package com.example.mvcBoard;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan(value= {"com.example.mvcBaord.dao"}) // Mapper 인터페이스 인식할 수 있도록 어노테이션 설정함
public class SpringMvcBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcBoardApplication.class, args);
	}
	
	// Mybatis 사용을 위한 bean 생성
	// 기존 Spring에서 servelet-context.xml에서 선언하여 사용했던 bean 객체를 여기서 생성해줌
		
	// sqlSessionFactory() : MyBatis의 SqlSessionFactory를 반환 스프링부트가 실행할 때 DataSource객체를 이 메서드 실행 시 주입해서 결과를 만들고, 그 결과를 스프링내 빈으로 사용하게 됨 
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();

	}
}
