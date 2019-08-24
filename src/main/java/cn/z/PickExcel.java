package cn.z;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/***
 * @class PickExcel
 * @description 按照新商学院的学员信息格式分类excel并导入系统
 * @author zch
 * @date 2019/8/24
 * @version V0.0.1.201908241111.01
 * @modfiyDate 201908241111
 * @createDate 201908241111
 * @package cn.z
 */
public class PickExcel {
	private static String file = "E:\\a.xlsx";
	private static String[] classA = new String[]{"ZB编导191", "ZB电气191", "ZB电气192", "ZB电气193", "ZB电商191", "ZB工商191", "ZB工商192", "ZB工商193", "ZB工商194", "ZB工商195", "ZB工商196", "ZB广电191", "ZB国贸191", "ZB环境191", "ZB环境192", "ZB会计191", "ZB会计192", "ZB机自191", "ZB机自192", "ZB视觉191", "ZB视觉192", "ZB土木191", "ZB土木192", "ZB物管191", "ZB信管191", "ZB信管192", "ZB信管193", "ZB信管194", "ZB信管195", "ZB信管196", "ZB英语191", "ZB英语192", "ZB营销191", "ZB营销192", "ZB造价191", "Z电商191", "Z电商192", "Z服设191", "Z服设192", "Z会计191", "Z会计192", "Z会计193", "Z会计194", "Z机电191", "Z机械191", "Z计191", "Z计192", "Z计193", "Z计194", "Z建工191", "Z空乘191", "Z空乘192", "Z空乘193", "Z空乘194", "Z空乘195", "Z空乘196", "Z影视191", "Z影视192", "Z造价191", "编导191", "编导192", "编导193", "编导194", "编导195", "编导196", "播音191", "播音192", "播音193", "播音194", "播音195", "播音196", "播音197", "产品191", "产品192", "产品193", "产品194", "电气191", "电气192", "电气193", "电气194", "电商191", "电商192", "电商193", "电商194", "服工191", "服工192", "服设191", "服设192", "服设193", "服设194", "工商191", "工商192", "工商193", "工商194", "工设191", "工设192", "广电191", "广电192", "轨道191", "轨道192", "国贸191", "国贸192", "环境191", "环境192", "环境193", "环境194", "环境195", "环境196", "环境197", "环境198", "会计191", "会计1910", "会计192", "会计193", "会计194", "会计195", "会计196", "会计197", "会计198", "会计199", "机电191", "机电192", "机自191", "机自192", "机自193", "机自194", "计科191", "计科192", "计科193", "计科194", "计科195", "计科196", "建电191", "建电192", "建环191", "建环192", "建筑191", "建筑192", "建筑193", "商英191", "商英192", "商英193", "视觉191", "视觉192", "视觉193", "视觉194", "数媒191", "数媒192", "数媒193", "数媒194", "数艺191", "数艺192", "数艺193", "数艺194", "土木191", "土木192", "网络191", "网络192", "网络193", "网络194", "物管191", "物管192", "信管191", "信管192", "信用191", "信用192", "英语191", "英语192", "英语193", "营销191", "营销192", "造价191", "造价192", "自动191", "自动192"};
	public static void main(String[] args) {
		writeName();
	}
	/**
	 * 按照新商学院的学员信息格式分类excel并导入系统
	 */
	private static void writeName() {
		try {
			for (String s : classA) {
				/* ===================================== */
				// 1 读取excel
				File f = new File(file);
				InputStream inputStream = new FileInputStream(f);
				List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
				inputStream.close();
				System.out.println(data);
				/* ===================================== */
				List<List<Object>> contentList = new ArrayList<>();
				String xibie = "";
				// 按照班级写入文件
				if (data.get(4).toString().equals(s)) {
					List<Object> contentListObj = new ArrayList<>();
					// contentListObj.add(l.get(4));班级
					//201902022117	张哲源	政法与传媒系	广播电视编导	ZB编导191	男	汉族
					// 编号	姓名	性别	身份证号	卡类别	绑定手机号1	绑定手机号2	赠送金额	预发金额	学(工)号
					contentListObj.add(data.get(0));
					contentListObj.add(data.get(1));
					contentListObj.add(data.get(5));
					contentListObj.add("123456789012345678");
					contentListObj.add("1");
					contentListObj.add("无");
					contentListObj.add("无");
					contentListObj.add("0");
					contentListObj.add("0");
					contentListObj.add(data.get(0));
					contentList.add(contentListObj);
					xibie = data.get(2).toString();
				}
				String path = "E:\\xingshang\\" + xibie + "\\";
				writeToExce(getHeadList(), contentList, path, s + ".xls");
			}
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
		headListStr3.add("性别");
		List<String> headListStr4 = new ArrayList<>();
		headListStr4.add("身份证号");
		List<String> headListStr5 = new ArrayList<>();
		headListStr5.add("卡类别");
		List<String> headListStr6 = new ArrayList<>();
		headListStr6.add("绑定手机号1");
		List<String> headListStr7 = new ArrayList<>();
		headListStr7.add("绑定手机号2");
		List<String> headListStr8 = new ArrayList<>();
		headListStr8.add("赠送金额");
		List<String> headListStr9 = new ArrayList<>();
		headListStr9.add("预发金额");
		List<String> headListStr10 = new ArrayList<>();
		headListStr10.add("学(工)号");
		headList.add(headListStr1);
		headList.add(headListStr2);
		headList.add(headListStr3);
		headList.add(headListStr4);
		headList.add(headListStr5);
		headList.add(headListStr6);
		headList.add(headListStr7);
		headList.add(headListStr8);
		headList.add(headListStr9);
		headList.add(headListStr10);
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
