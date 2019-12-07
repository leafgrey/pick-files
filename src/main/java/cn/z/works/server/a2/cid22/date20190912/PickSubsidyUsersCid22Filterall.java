package cn.z.works.server.a2.cid22.date20190912;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/***
 * @class PickExcel
 * @description 未使用 错误得方法 应该直接过滤userno 不再研究
 * @author zch
 * @date 2019/8/24
 * @version V0.0.1.201908241111.01
 * @modfiyDate 201908241111
 * @createDate 201908241111
 * @package cn.z
 */
public class PickSubsidyUsersCid22Filterall {
	private static String file1 = "E:\\tables2\\数据库查询整理.xlsx";
	private static String file2 = "E:\\tables2\\卡信息表201908130915.xlsx";
	private static String path = "E:\\tables2\\imp\\";
	public static void main(String[] args) {
		writeName();
	}
	/**
	 * 按照新商学院的学员信息格式分类excel并导入系统
	 */
	private static void writeName() {
		try {
			/* ===================================== */
			// 1 读取excel
			File f = new File(file1);
			InputStream inputStream = new FileInputStream(f);
			List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
			inputStream.close();
			File f2 = new File(file2);
			InputStream inputStream2 = new FileInputStream(f2);
			List<Object> data2 = EasyExcelFactory.read(inputStream2, new Sheet(1, 0));
			inputStream2.close();
			List<List<Object>> contentList1 = new ArrayList<>();
			List<List<Object>> contentList2 = new ArrayList<>();
			Map m = new HashMap();
			Map m2 = new HashMap();
			for (Object o2 : data2) {
				ArrayList<String> l2 = (ArrayList<String>) o2;
				String name2 = l2.get(0).replace(" ", "");
				String mon = l2.get(1).replace(" ", "");
				boolean isExit = false;
				for (Object obj : data) {
					// 返回每条数据的键值对 表示所在的列 和所在列的值
					ArrayList<String> l = (ArrayList<String>) obj;
					String name1 = l.get(1).replace(" ", "");
					String no = l.get(0).replace(" ", "");
					if (name1.equals(name2)) {
						List<Object> contentListObj1 = new ArrayList<>();
						contentListObj1.add(no);
						contentListObj1.add(name1);
						contentListObj1.add(name2);
						contentListObj1.add(mon);
						contentList1.add(contentListObj1);
						isExit = true;
					}
				}
				if (!isExit) {
					List<Object> contentListObj2 = new ArrayList<>();
					contentListObj2.add("不存在：");
					contentListObj2.add(name2);
					contentListObj2.add(mon);
					contentList2.add(contentListObj2);
				}
			}
			writeToExce(getHeadList(), contentList1, path, "impok.xls");
			writeToExce(getHeadList(), contentList2, path, "imperr.xls");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写excel
	 * @param headList excel表头
	 * @param dataList excel表数据
	 * @param path 写入文件路径
	 * @param excelName 写入文件名称
	 */
	private static void writeToExce(List<List<String>> headList, List<List<Object>> dataList, String path, String excelName) {
		try {
			boolean pathExit = checkPath(path);
			if (pathExit) {
				OutputStream out = new FileOutputStream(path + excelName);
				// ExcelWriter writer = EasyExcelFactory.getWriter(out)
				// 服务器需要03版本的 excel 格式
				ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLS, true);
				//写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
				Sheet sheet1 = new Sheet(1, 0);
				sheet1.setSheetName("sheet1");
				//设置列宽 设置每列的宽度
				sheet1.setHead(headList);
				//or 设置自适应宽度
				sheet1.setAutoWidth(Boolean.TRUE);
				writer.write1(dataList, sheet1);
				writer.finish();
				out.close();
			}
			else {
				System.err.println("路径不存在无法写入文件！");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取表头信息
	 * @return List<List < String>>
	 */
	private static List<List<String>> getHeadList() {
		List<List<String>> headList = new ArrayList<>();
		List<String> headListStr1 = new ArrayList<>();
		headListStr1.add("编号");
		List<String> headListStr2 = new ArrayList<>();
		headListStr2.add("姓名");
		List<String> headListStr3 = new ArrayList<>();
		headListStr3.add("别名");
		List<String> headListStr4 = new ArrayList<>();
		headListStr4.add("补助金额");
		headList.add(headListStr1);
		headList.add(headListStr2);
		headList.add(headListStr3);
		headList.add(headListStr4);
		return headList;
	}
	/**
	 * 检查要写入的路径是否存在
	 * @param path String
	 * @return boolean
	 */
	private static boolean checkPath(String path) {
		File dic = new File(path);
		// 如果路径不存在则创建路径并返回创建结果
		if (!dic.exists()) {
			// 目录不存在的情况下，创建目录。
			boolean mkResult = dic.mkdirs();
			if (mkResult) {
				// 创建成功则说明路径存在 返回 true
				System.out.println(path + "创建成功！");
				return true;
			}
			else {
				// 创建失败则说明 路径不存在返回 false
				System.out.println(path + "创建失败！");
				return false;
			}
		}
		// 如果路径已经存在则 直接返回 true
		else {
			return true;
		}
	}
}
