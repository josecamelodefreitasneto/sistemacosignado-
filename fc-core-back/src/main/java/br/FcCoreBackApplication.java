package br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FcCoreBackApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FcCoreBackApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			final String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (final String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
	
	
}
