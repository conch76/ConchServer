package com.conch;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.conch.service.PlayerManagerService;

@SpringBootApplication
public class ConchServerApplication {
	
	private static ApplicationContext context;
	
    public static void main(String[] args) throws InterruptedException {
    	context = SpringApplication.run(ConchServerApplication.class, args);
    	String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        // create mock data
        context.getBean(MockDataService.class).createTestData();
    }
    

	/**
     * to be used by non spring beans...
     * @return ApplicationContext
     */
	public static ApplicationContext getAppContext() {
    	return  context;
    }
	
	public static PlayerManagerService getPlayerManagerService() {
		return context.getBean(PlayerManagerService.class);
	}
}
