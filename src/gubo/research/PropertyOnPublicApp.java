package gubo.research;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class PropertyOnPublicApp {
	public static void main(String[] args) throws BeansException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, InvocationTargetException {
		testAutowiredOnProperty();
	}

	public static void testAutowiredOnProperty() {

		String xml = genearteAutowiredOnProperty();
		ApplicationContext context = load(xml);

		String[] beansName = context.getBeanDefinitionNames();
		Group3 g3 = (Group3) context.getBean("group3");
		System.out.println("g3.getPersonA(): " + g3.personA); 

	}

	public static String genearteAutowiredOnProperty() {
		StringBuilder sb = new StringBuilder();
		sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\r\n"
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
				+ "xsi:schemaLocation=\"http://www.springframework.org/schema/beans\r\n"
				+ "http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"> \n");
		sb.append("<bean class=\"org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor\" /> \n");

		sb.append("<bean id = \"group3\" class=\"gubo.research.Group3\" > \n");
		sb.append("<property name=\"personA\" ref=\"personA\" /> \n");
		
		sb.append("</bean> \n");

		sb.append("<bean id=\"personA\" class=\"gubo.custom.autowired.PersonA\" /> \n");
		sb.append("</beans>");
		return sb.toString();
	}

	public static ApplicationContext load(String contextXML) {
		Resource resource = new ByteArrayResource(contextXML.getBytes());
		GenericXmlApplicationContext springContext = new GenericXmlApplicationContext();
		AutowireCapableBeanFactory f = springContext
				.getAutowireCapableBeanFactory();

		AutowiredAnnotationBeanPostProcessor abpp = new AutowiredAnnotationBeanPostProcessor();
		abpp.setBeanFactory(springContext.getBeanFactory());
		springContext.getBeanFactory().addBeanPostProcessor(abpp);

		springContext.load(resource);
		springContext.refresh();
		return springContext;
	}

}
