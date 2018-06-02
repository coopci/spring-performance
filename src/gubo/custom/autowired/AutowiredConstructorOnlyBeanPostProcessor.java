package gubo.custom.autowired;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

public class AutowiredConstructorOnlyBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor {

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
			String beanName) throws BeansException {

		return null;
	}
}
