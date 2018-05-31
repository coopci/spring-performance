package gubo.custom.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

public class CustomAutowire {

	public boolean doSetter = false;
	public boolean doField = true;

	public String getInjectedBeanName(Qualifier qua, Method m) {
		if (qua != null) {
			return qua.value();
		}
		String methodName = m.getName();
		if (!methodName.startsWith("set")) {
			return null;
		}
		return methodName.substring(3, methodName.length());
	}

	public String getInjectedBeanName(Qualifier qua, Field f) {
		if (qua != null) {
			return qua.value();
		}
		String fieldName = f.getName();
		return fieldName;
	}

	public void customAutowire(ApplicationContext context, Object bean)
			throws BeansException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Class<?> clazz = bean.getClass();
		if (this.doSetter) {
			for (Method m : clazz.getMethods()) {
				Autowired anno = m.getAnnotation(Autowired.class);
				if (anno == null) {
					continue;
				}
				Qualifier qua = m.getAnnotation(Qualifier.class);
				String beanname = getInjectedBeanName(qua, m);
				if (beanname == null) {
					continue;
				}
				m.setAccessible(true);
				m.invoke(bean, context.getBean(beanname));
			}
		}

		if (this.doField) {
			for (Field f : clazz.getDeclaredFields()) {
				Autowired anno = f.getAnnotation(Autowired.class);
				if (anno == null) {
					continue;
				}
				Qualifier qua = f.getAnnotation(Qualifier.class);
				String beanname = getInjectedBeanName(qua, f);
				f.setAccessible(true);
				f.set(bean, context.getBean(beanname));
			}
		}
	}

	public void customAutowire(ApplicationContext context)
			throws BeansException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {

			Object bean = context.getBean(name);
			customAutowire(context, bean);
		}
	}
}
