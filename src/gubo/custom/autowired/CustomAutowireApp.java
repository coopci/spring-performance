package gubo.custom.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomAutowireApp {
	
	public static void main(String[] args) throws BeansException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException {
		
		Date before = new Date();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"CustomAutowiredBeansDef.xml");
		
		CustomAutowire ca = new CustomAutowire();
		ca.customAutowire(context);
		
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