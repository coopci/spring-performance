package gubo.annoconfig;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnoConfigApp {

	public static void main(String[] args) {
		
		Date before = new Date();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"AnnoConfigBeansDef.xml");
		Date after = new Date();
		
		long milliseconds = after.getTime() - before.getTime();
		
		gubo.pkg001.Book001001 book001001 = (gubo.pkg001.Book001001) context.getBean("book001001");
		
		gubo.pkg001.Book001002 book001002 = (gubo.pkg001.Book001002) context.getBean("book001002");

		System.out.println("milliseconds=" + milliseconds);
		System.out.println(book001001.member);
		System.out.println(book001002.member);

	}
}
