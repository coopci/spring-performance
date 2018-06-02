package gubo.custom.autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Map.Entry;

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
		
		String ret = methodName.substring(3, methodName.length());
		
		
		ret = ret.substring(0, 1).toLowerCase() + ret.substring(1, ret.length()); 
		
		return ret;
	}

	public String getInjectedBeanName(Qualifier qua, Field f) {
		if (qua != null) {
			return qua.value();
		}
		String fieldName = f.getName();
		return fieldName;
	}

	
	public Object getBean(Class<?> clazz, ApplicationContext context, String beanname) {
		Object ret = null;
		try {
			return context.getBean(beanname);
		} catch (Exception ex) {
			
		}
		Map<String, ?> beans = context.getBeansOfType(clazz);
		if (beans.size() == 1) {
			Entry<String, ?> entry = beans.entrySet().iterator().next();
			return entry.getValue();
		} else if (beans.size() == 0) { 
			// throw 
		} else {
			
		}
		
		return ret;
		
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
				
				Parameter parameter = m.getParameters()[0];
				
				Object injectedBean = getBean(parameter.getType(), context, beanname);
				m.setAccessible(true);
				m.invoke(bean, injectedBean);
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
				
				Object injectedBean = getBean(f.getType(), context, beanname);
				f.setAccessible(true);
				f.set(bean, injectedBean);
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
