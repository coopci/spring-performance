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
		
		System.out.println("milliseconds=" + milliseconds);
		System.out.println("" + g.members.size());
	}
}
