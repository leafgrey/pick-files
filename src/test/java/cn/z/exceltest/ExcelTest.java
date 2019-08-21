package cn.z.exceltest;
import cn.z.utils.excel.util.DataUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;

import java.io.FileOutputStream;
import java.io.OutputStream;
/***
 * @class ExcelTest
 * @description TODO
 * @author zch
 * @date 2019/8/15
 * @version V0.0.1.201908151056.01
 * @modfiyDate 201908151056
 * @createDate 201908151056
 * @package cn.z.exceltest
 */
public class ExcelTest {
	public static void main(String[] args) {
		testWrite();
	}
	public static void testWrite() {
		try {
			OutputStream out = new FileOutputStream("E:\\aaaaaaaaaaaaaaaa\\2007_" + System.currentTimeMillis() + ".xlsx");
			ExcelWriter writer = EasyExcelFactory.getWriter(out);
			//写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
			Sheet sheet1 = new Sheet(1, 3);
			sheet1.setSheetName("第一个sheet");
			//设置列宽 设置每列的宽度
			//			Map<Integer,Integer> columnWidth = new HashMap<Integer, Integer>();
			//			columnWidth.put(0, 10000);
			//			columnWidth.put(1, 40000);
			//			columnWidth.put(2, 10000);
			//			columnWidth.put(3, 10000);
			//			sheet1.setColumnWidthMap(columnWidth);
			sheet1.setHead(DataUtil.createTestListStringHead());
			//or 设置自适应宽度
			sheet1.setAutoWidth(Boolean.TRUE);
			writer.write1(DataUtil.createTestListObject(), sheet1);
			writer.finish();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
