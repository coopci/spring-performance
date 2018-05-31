package gubo.custom.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomAutowireApp {
	
	public static String getInjectedBeanName(Qualifier qua, Method m) {
		if (qua != null) {
			return qua.value();	
		}
		String methodName = m.getName();
		if (!methodName.startsWith("set")) {
			return null;
		}
		return methodName.substring(3, methodName.length());		
	}
	

	public static String getInjectedBeanName(Qualifier qua, Field f) {
		if (qua != null) {
			return qua.value();	
		}
		String fieldName = f.getName();
		return fieldName;		
	}
	public static void customAutowire(ApplicationContext context, Object bean) throws BeansException, IllegalArgumentException, IllegalAccessException {

		Class<?> clazz = bean.getClass();
		for (Method m : clazz.getMethods()) {
			Autowired anno = m.getAnnotation(Autowired.class);
			if (anno == null) {
				continue;
			}
			Qualifier qua = m.getAnnotation(Qualifier.class);
			String beanname = getInjectedBeanName(qua, m);
			System.out.println("beanname injected on setter: " + beanname);
		}
		
		for (Field f : clazz.getDeclaredFields()) {
			Autowired anno = f.getAnnotation(Autowired.class);
			if (anno == null) {
				continue;
			}
			Qualifier qua = f.getAnnotation(Qualifier.class);
			String beanname = getInjectedBeanName(qua, f);
			f.setAccessible(true);
			f.set(bean, context.getBean(beanname));
			// f.is
			System.out.println("beanname injected on field: " + beanname);
		}
		
		
	}
	public static void customAutowire(ApplicationContext context) throws BeansException, IllegalArgumentException, IllegalAccessException {

		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			
			Object bean = context.getBean(name);
			customAutowire(context, bean);
			
		}
		
		
		
	}
	public static void main(String[] args) throws BeansException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		Date before = new Date();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"CustomAutowiredBeansDef.xml");
		
		customAutowire(context);
		Date after = new Date();
		
		long milliseconds = after.getTime() - before.getTime();
		Group g = (Group)context.getBean("group");
		PersonA personA = (PersonA)context.getBean("personA");
		System.out.println("g.getPersonA(): " + g.getPersonA());
		
		
		
		for (int i = 0; i< 10000; ++i) {
			Field f = g.getClass().getDeclaredField("personA");
			f.setAccessible(true);
			f.set(g, personA);
			System.out.println(" " + i);
		}
		
		
	}
}