package gubo.raw;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class RawMain {

	ConcurrentHashMap<String, Object> members = new ConcurrentHashMap<String, Object>();

	public static void main(String[] args) {
		// Raw.f();
		Date before = new Date();
		Giant g = new Giant();
		g.init();
		Date after = new Date();

		long milliseconds = after.getTime() - before.getTime();

		
		System.out.println("" + g.members.size());

		gubo.pkg001.Book001001 book001001 = (gubo.pkg001.Book001001) g.members
				.get("book001001");

		gubo.pkg001.Book001002 book001002 = (gubo.pkg001.Book001002) g.members
				.get("book001002");

		System.out.println("milliseconds=" + milliseconds);
		System.out.println(book001001.member);
		System.out.println(book001002.member);

	}
}
