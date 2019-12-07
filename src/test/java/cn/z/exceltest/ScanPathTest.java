package cn.z.exceltest;
import cn.z.utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.List;
public class ScanPathTest {
	public static void main(String[] args) {
		// testScan();
        System.out.println(File.pathSeparator);
        System.out.println(File.separator);
	}
	public static void testScan() {
		List<Object> list = Utils.scanFilesWithRecursion(".");
		for (Object o : list) {
			System.out.println(o);
		}
		URL url = ScanPathTest.class.getClassLoader().getResource("杭州启颂教育人员分班级/test.md");
		System.out.println(url);
		if(null == url) {
			return;
		}
		System.out.println(url.getPath());
		File f = new File(url.getPath());
	}
}
