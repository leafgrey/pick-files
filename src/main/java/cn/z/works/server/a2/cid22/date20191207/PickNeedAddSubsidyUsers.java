package cn.z.works.server.a2.cid22.date20191207;
import cn.z.constant.ConsNum;
import cn.z.constant.ConsStr;
import cn.z.utils.Utils;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.StringUtils;
import com.google.common.collect.Maps;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/***
 * @class PickExcel
 * @description 叶县二高补助人员信息过滤 因为校讯通收费不被家长接受 需要再发放补助回去 过滤出上个补助没有领取的累加到这一批
 * @author zch
 * @date 2019/8/24
 * @version V0.0.1.201908241111.01
 * @modfiyDate 201908241111
 * @createDate 201908241111
 * @package cn.z
 */
public class PickNeedAddSubsidyUsers {
	/**
	 * 已经领取.xls
	 * 本次导入20191207.xls
	 */
	private final static String CURRENT_FOLDER = File.separator;
	private final static String FILE1_NAME = "excel-上次补助.xlsx";
	private final static String FILE2_NAME = "excel-本次导入20191207.xlsx";
	private final static String FILE1_NAME_ABS = "E:\\叶县二高补助整理20191207\\test\\" + FILE1_NAME;
	private final static String FILE2_NAME_ABS = "E:\\叶县二高补助整理20191207\\test\\" + FILE2_NAME;
	public static void main(String[] args) {
		writeName();
	}
	/**
	 * 按照新商学院的学员信息格式分类excel并导入系统
	 */
	private static void writeName() {
		try {
			/* ===================================== */
			URL r = PickNeedAddSubsidyUsers.class.getResource("");
			String path = r.getPath().substring(1);
			ArrayList<Object> l = Utils.scanFilesWithNoRecursion(path);
			String file1 = "";
			String file2 = "";
			for (Object o : l) {
				if(o.toString().contains(FILE1_NAME)) {
					file1 = o.toString();
				}
				if(o.toString().contains(FILE2_NAME)) {
					file2 = o.toString();
				}
			}
			if(StringUtils.isEmpty(file1) || StringUtils.isEmpty(file2)) {
				System.out.println("找不到文件");
				return;
			}
			// 1 读取excel
			File f = new File(file1);
			InputStream inputStream = new BufferedInputStream(new FileInputStream(f.getPath()));
			List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(ConsNum.P1, ConsNum.P1));
			inputStream.close();
			File f2 = new File(file2);
			InputStream inputStream2 = new FileInputStream(f2);
			List<Object> data2 = EasyExcelFactory.read(inputStream2, new Sheet(ConsNum.P1, ConsNum.P1));
			inputStream2.close();
			List<List<Object>> contentList1 = new ArrayList<>();
			Map<Object, Object> m = Maps.newConcurrentMap();
			Map<Object, Object> m2 = Maps.newConcurrentMap();
			for (Object obj2 : data2) {
				// 返回每条数据的键值对 表示所在的列 和所在列的值
				ArrayList<String> l2 = (ArrayList<String>) obj2;
				String userNo2 = l2.get(0);
				String userName2 = l2.get(1);
				String subsidyFare2 = l2.get(2);
				BigDecimal newSubsidyFare = new BigDecimal(3);
				for (Object obj : data) {
					ArrayList<String> l1 = (ArrayList<String>) obj;
					String userNo = l1.get(8);
					String userName = l1.get(7);
					String subsidyFare = l1.get(4);
					String subsidyStatus = l1.get(6);
					if(userName.equals(userName2) && userNo.equals(userNo2) && subsidyStatus.equals(ConsStr.STRP1)) {
						newSubsidyFare = new BigDecimal(subsidyFare2).add(new BigDecimal(subsidyFare));
					}
				}
				List<Object> contentListObj1 = new ArrayList<>();
				contentListObj1.add(userNo2);
				contentListObj1.add(userName2);
				contentListObj1.add(newSubsidyFare.toString());
				contentList1.add(contentListObj1);
			}
			writeToExce(getHeadList(), contentList1, path, "本次补助整理20191207.xls");
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
			if(pathExit) {
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
		headListStr3.add("补助金额");
		headList.add(headListStr1);
		headList.add(headListStr2);
		headList.add(headListStr3);
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
		if(!dic.exists()) {
			// 目录不存在的情况下，创建目录。
			boolean mkResult = dic.mkdirs();
			if(mkResult) {
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
