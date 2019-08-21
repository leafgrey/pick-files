package cn.z.exceltest;
import cn.z.utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.List;
public class ScanPathTest {
    public static void main(String[] args) {
        List<Object> list = Utils.scanFilesWithRecursion(".");
        for (Object o : list) {
            System.out.println(o);
        }
        URL url = ScanPathTest.class.getClassLoader().getResource("test.md");
        System.out.println(url);
        if (null == url) {
            return;
        }
        System.out.println(url.getPath());
        File f = new File(url.getPath());
    }
}
