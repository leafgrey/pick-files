package cn.z.utils;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author zch
 */
public class Utils {
	private static ArrayList<Object> scanFiles = new ArrayList<>();
	/**
	 * linkedList实现
	 **/
	private static LinkedList<File> queueFiles = new LinkedList<>();
	static Logger log = Logger.getLogger(Utils.class.getName());
	private Utils() {
		log.setLevel(Level.INFO);
	}
	/**
	 * 递归扫描指定文件夹下面的指定文件
	 * @return ArrayList<Object>
	 */
	public static ArrayList<Object> scanFilesWithRecursion(String folderPath) {
		// ArrayList<String> allDirctory = new ArrayList<>()
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			// throw new ScanFilesException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
			System.out.println('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
		}
		if (directory.isDirectory()) {
			File[] fileList = directory.listFiles();
			if (fileList != null) {
				for (File file : fileList) {
					/*如果当前是文件夹，进入递归扫描文件夹**/
					if (file.isDirectory()) {
						// allDirctory.add(file.getAbsolutePath())
						/*递归扫描下面的文件夹**/
						scanFilesWithRecursion(file.getAbsolutePath());
					}
					/*非文件夹*/
					else {
						scanFiles.add(file.getAbsolutePath());
					}
				}
			}
		}
		return scanFiles;
	}
	/**
	 * 非递归方式扫描指定文件夹下面的所有文件
	 * @param folderPath 需要进行文件扫描的文件夹路径
	 * @return ArrayList<Object>
	 */
	public static ArrayList<Object> scanFilesWithNoRecursion(String folderPath) {
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			// throw new Exception('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
			System.out.println('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
		}
		else {
			//首先将第一层目录扫描一遍
			File[] files = directory.listFiles();
			//遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						queueFiles.add(file);
					}
					else {
						//暂时将文件名放入scanFiles中
						scanFiles.add(file.getAbsolutePath());
					}
				}
			}
			//如果linkedList非空遍历linkedList
			while (!queueFiles.isEmpty()) {
				//移出linkedList中的第一个
				File headDirectory = queueFiles.removeFirst();
				File[] currentFiles = headDirectory.listFiles();
				if (currentFiles != null) {
					for (File currentFile : currentFiles) {
						if (currentFile.isDirectory()) {
							//如果仍然是文件夹，将其放入linkedList中
							queueFiles.add(currentFile);
						}
						else {
							scanFiles.add(currentFile.getAbsolutePath());
						}
					}
				}
			}
		}
		return scanFiles;
	}
	/**
	 * 转移文件
	 */
	public static void moveFileToFolders(String fileName, String ansPath) {
		try {
			File startFile = new File(fileName);
			//获取文件夹路径
			File tmpFile = new File(ansPath);
			//判断文件夹是否创建，没有创建则创建新文件夹
			if (!tmpFile.exists()) {
				boolean isMkdir = tmpFile.mkdirs();
				if (!isMkdir) {
					log.info(String.format("文件夹创建失败！文件名：《%s》 目标路径：%s", fileName, ansPath));
					return;
				}
			}
			if (startFile.renameTo(new File(ansPath + startFile.getName()))) {
				log.info(String.format("文件移动成功！文件名：《%s》 目标路径：%s", fileName, ansPath));
			}
			else {
				log.info(String.format("文件移动失败！文件名：《%s》 起始路径：%s", fileName, fileName));
			}
		}
		catch (Exception e) {
			log.info(String.format("文件移动异常！文件名：《%s》 起始路径：%s", fileName, fileName));
		}
	}
	/**
	 * Rename the file.
	 * 此方法慎用 为测试
	 */
	public static void rename(final File file, final String newName) {
		// file is null then return false
		if (file == null) {
			return;
		}
		// file doesn't exist then return false
		if (!file.exists()) {
			return;
		}
		// the new name is space then return false
		if (newName.contains(" ")) {
			return;
		}
		// the new name equals old name then return true
		if (newName.equals(file.getName())) {
			return;
		}
		File newFile = new File(file.getParent() + File.separator + newName);
		// the new name of file exists then return false
		if (!newFile.exists()) {
			boolean isRenamed = file.renameTo(newFile);
			if (!isRenamed) {
				log.warning("文件转移失败" + file);
			}
		}
	}
}
