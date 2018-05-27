package gubo.springxml;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HugeReverseAutowireXmlApp {
	
	public static void main(String[] args) {
		
		Date before = new Date();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"HugeReverseAutowireBeansDef.xml");
		Date after = new Date();
		
		long milliseconds = after.getTime() - before.getTime();
		
		gubo.pkg001.Book001001 book001001 = (gubo.pkg001.Book001001) context.getBean("book001001");

		gubo.pkg001.Book001002 book001002 = (gubo.pkg001.Book001002) context.getBean("book001002");

		System.out.println("milliseconds=" + milliseconds);
		System.out.println(book001001.member);
		System.out.println(book001002.member);
		
		gubo.pkg100.Book100049 book100049 = (gubo.pkg100.Book100049) context.getBean("book100049");
		System.out.println(book100049.member);

	}
}