package org.baichuan.borrow;

import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		//CollectionIdGenerator collectionIdGenerator=new CollectionIdGenerator();
		//collectionIdGenerator.initialize();
	}



}
