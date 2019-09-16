package cn.z;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/***
 * @class PickBsCompanyInfo
 * @description TODO
 * @author zch
 * @date 2019/9/16
 * @version V0.0.1.201909161759.01
 * @modfiyDate 201909161759
 * @createDate 201909161759
 * @package cn.z
 */
public class PickBsCompanyInfo {
	public static void main(String[] args) {
		writeMyData();
	}
	private static void writeMyData() {
		List<List<Object>> dataList = readCompanyInfo();
		List<List<Object>> newDataList = new ArrayList<>();
		if (dataList != null) {
			// System.out.println(dataList)
			for (List<Object> list : dataList) {
				List<Object> addData2 = new ArrayList<>();
				addData2.add("#" + list.get(0) + " " + list.get(1));
				newDataList.add(addData2);
				List<Object> addData = new ArrayList<>();
				addData.add("c" + list.get(0) + "=" + list.get(0));
				newDataList.add(addData);
			}
			for (List<Object> list : newDataList) {
				if (list.size() >= 2) {
					System.out.println((list.get(0).toString() + " " + list.get(1)));
				}
				else {
					System.out.println((list.get(0).toString()));
				}
			}
		}
		else {
			System.out.println(dataList + " is null !");
		}
	}
	private static List<List<Object>> readCompanyInfo() {
		try {
			String file = "C:\\Users\\zch\\Desktop\\jin.xlsx";
			File f = new File(file);
			InputStream inputStream = new FileInputStream(f);
			List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
			inputStream.close();
			List<List<Object>> contentList = new ArrayList<>();
			for (Object o : data) {
				List<Object> l2 = (ArrayList<Object>) o;
				contentList.add(l2);
			}
			// System.out.println(contentList);
			return contentList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
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
