package cn.z.utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @author zch
 */
public class DateUtil {
	public static void main(String[] args) {
		System.out.println(getFormattedNowTime("yyyy-MM-dd HH:mm:ss.SSS"));
		System.out.println(getFormattedNowTime("yyyy-MM-dd"));
		System.out.println(getFormattedNowTime("HH:mm:ss.SSS"));
		System.out.println(getFormattedNowTime("MM-dd HH:mm:ss.SSS"));
		System.out.println(getFormattedNowTime("dd HH:mm:ss.SSS"));
		/*-----------------------------*/
		System.out.println(getDayApartToday(1, "yyyy-MM-dd"));
	}
	/**
	 *
	 */
	public static String getFormattedNowTime(String pattern) {
		// 设置日期格式 yyyy-MM-dd HH:mm:ss.SSS
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}
	public static String getFormattedTime(String pattern,Date time) {
		// 设置日期格式 yyyy-MM-dd HH:mm:ss.SSS
		return new SimpleDateFormat(pattern).format(time);
	}
	public static String getDayApartToday(int count, String pattern) {
		Calendar cal = Calendar.getInstance();
		//5
		System.out.println(Calendar.DATE);
		//cal.add(Calendar.DATE, -1)
		cal.add(Calendar.DATE, count);
		Date time = cal.getTime();
		return getFormattedTime(pattern,time);
	}
}
