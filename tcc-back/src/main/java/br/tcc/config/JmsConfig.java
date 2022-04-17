package br.tcc.config;
//package br.coop.cooperforte.coopergestaodetaxas.interceptors;
//
//import javax.jms.ConnectionFactory;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.config.JmsListenerContainerFactory;
//import org.springframework.jms.core.JmsTemplate;
//
//@Configuration
//@EnableJms
//public class JmsConfig {
//
//	@Bean
//    public ActiveMQConnectionFactory connectionFactory() {
//		return new ActiveMQConnectionFactory("tcp://localhost:61616");
////		return new ActiveMQConnectionFactory("http://localhost:8080");
//    }
//	
//	@Bean
//    public JmsListenerContainerFactory<?> jmsFactoryTopic(final ConnectionFactory connectionFactory, final DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        factory.setPubSubDomain(true);
//        return factory;
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        return new JmsTemplate(this.connectionFactory());
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplateTopic() {
//        final JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory());
//        jmsTemplate.setPubSubDomain( true );
//        return jmsTemplate;
//    }
//	
//}
