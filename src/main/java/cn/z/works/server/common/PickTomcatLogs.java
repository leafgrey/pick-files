package cn.z.works.server.common;
import cn.z.constant.ConsStr;
import cn.z.utils.DateUtil;
import cn.z.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author zch
 */
public class PickTomcatLogs {
	private static final String H = "-";
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("是否清理 catalina.out?强烈不建议清理，未经测试，输入1清理，其它值不清理：");
		// 是否清理 catalina.out 不建议
		int cleanCatalinaOut = s.nextInt();
		System.out.println("输入日志路径：");
		// 需要清理的日志放在什么地方
		String scanPath = s.next();
		System.out.println("输入截止日期（可选）,格式：yyyy-MM-dd：");
		// 日志清理截止日期
		String endDay = s.next();
		System.out.println("输入日志转移目标文件夹路径：");
		// 需要把历史日志放在什么地方
		String transferPath = s.next();
		analyseLogs(cleanCatalinaOut, scanPath, endDay, transferPath);
	}
	private static void analyseLogs(int cleanCatalinaOut, String scanPath, String endDay, String transferPath) {
		ArrayList<Object> allFiles = Utils.scanFilesWithRecursion(scanPath);
		String year = DateUtil.getFormattedNowTime(ConsStr.DATE_YEAR);
		String yesterday = DateUtil.getDayApartToday(-1, ConsStr.DATE_TO_DAY);
		String today = DateUtil.getFormattedNowTime(ConsStr.DATE_TO_DAY);
		String aimDate;
		String hisLogPath;
		for (Object o : allFiles) {
			File f = new File(o.toString());
			System.out.println("文件Name:" + f.getName());
			System.out.println("文件Path：" + f.getPath());
			if ("catalina.out".equals(f.getName()) && cleanCatalinaOut == 1) {
				Utils.rename(f, "catalina." + today + ".log");
			}
			for (int monthCount = 1; monthCount <= 12; monthCount++) {
				String month;
				if (String.valueOf(monthCount).length() <= 1) {
					month = "0" + monthCount;
				}
				else {
					month = String.valueOf(monthCount);
				}
				for (int i = 1; i <= 31; i++) {
					String day;
					if (String.valueOf(i).length() <= 1) {
						day = "0" + i;
					}
					else {
						day = String.valueOf(i);
					}
					aimDate = year + H + month + H + day;
					if (aimDate.equals(yesterday) || aimDate.equals(endDay)) {
						break;
					}
					else {
						if (f.getName().contains(aimDate)) {
							hisLogPath = transferPath + File.separator + today + File.separator + aimDate + File.separator;
							Utils.moveFileToFolders(o.toString(), hisLogPath);
						}
					}
				}
			}
		}
	}
}
