package gubo.custom.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class CustomAutowireAppResearch {
	
	public static void main1(String[] args) throws BeansException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException {
		
		Date before = new Date();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"CustomAutowiredBeansDef.xml");
		
		CustomAutowire ca = new CustomAutowire();
		ca.customAutowire(context);
		
		Date after = new Date();
		
		long milliseconds = after.getTime() - before.getTime();
		
		
//		Group g = (Group)context.getBean("group");
//		PersonA personA = (PersonA)context.getBean("personA");
//		System.out.println("g.getPersonA(): " + g.getPersonA());
//		System.out.println("g.getPersonA2(): " + g.getPersonA2());

//		for (int i = 0; i < 1; ++i) {
//			Field f = g.getClass().getDeclaredField("personA2");
//			f.setAccessible(true);
//			f.set(g, personA);
//			System.out.println(" " + i);
//		}
		

		Group2 g2 = (Group2)context.getBean("group2");
		System.out.println("g2.getPersonA(): " + g2.getPersonA()); // autowired by ctor
		System.out.println("g2.getPersonB(): " + g2.getPersonB()); // autowired by ctor
		System.out.println("g2.getPersonB2(): " + g2.getPersonB2());  // autowired by setter
		System.out.println("g2.getPersonB3(): " + g2.getPersonB3());  // autowired by private field
		
//		testRealAutowire(1000);
	}
	public static void main(String[] args) throws BeansException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException {
		testRealAutowire(1000);	
	}
	public static void testRealAutowire(int beansNum) {
		
		String xml = genearteXMLRealAutowire(beansNum);
		ApplicationContext context = load(xml);
		
		String[] beansName = context.getBeanDefinitionNames();
		Group2 g2 = (Group2)context.getBean("group2");
		System.out.println("g2.getPersonA(): " + g2.getPersonA()); // autowired by ctor
		System.out.println("g2.getPersonB(): " + g2.getPersonB()); // autowired by ctor
		System.out.println("g2.getPersonB2(): " + g2.getPersonB2());  // autowired by setter
		System.out.println("g2.getPersonB3(): " + g2.getPersonB3());  // autowired by private field
		
	}
	public static String genearteXMLRealAutowire(int beansNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\r\n" + 
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
				"xsi:schemaLocation=\"http://www.springframework.org/schema/beans\r\n" + 
				"http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"> \n");
	// 	sb.append("<bean class=\"org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor\" /> \n");
		
		
		sb.append("<bean id = \"group2\" class=\"gubo.custom.autowired.Group2\" /> \n");
		for (int i = 0; i < beansNum; ++i) {
			sb.append("<bean class=\"gubo.custom.autowired.Group2\" />");
		}
		
		sb.append("<bean id=\"personB\" class=\"gubo.custom.autowired.PersonB\" /> \n");
		sb.append("<bean id=\"personA\" class=\"gubo.custom.autowired.PersonA\" /> \n");
		sb.append("</beans>");
		return sb.toString();
	}
	
	
	public static ApplicationContext load(String contextXML) {
		Resource resource = new ByteArrayResource(contextXML.getBytes());
		GenericXmlApplicationContext springContext = new GenericXmlApplicationContext();
		AutowireCapableBeanFactory f = springContext.getAutowireCapableBeanFactory();
		
		AutowiredAnnotationBeanPostProcessor abpp = new AutowiredAnnotationBeanPostProcessor();
        abpp.setBeanFactory(springContext.getBeanFactory());
        springContext.getBeanFactory().addBeanPostProcessor(abpp);
        
		springContext.load(resource);
		springContext.refresh();
		return springContext;
	}
	
	
}