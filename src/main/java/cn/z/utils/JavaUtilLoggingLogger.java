package cn.z.utils;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author zch
 */
public class JavaUtilLoggingLogger {
	public static void main(String[] args) {
		Logger log = Logger.getLogger(JavaUtilLoggingLogger.class.getName());
		log.setLevel(Level.INFO);
		Logger log1 = Logger.getLogger(JavaUtilLoggingLogger.class.getName());
		//true
		System.out.println(log == log1);
		Logger log2 = Logger.getLogger(JavaUtilLoggingLogger.class.getName());
		//  log2.setLevel(Level.WARNING);
		// 控制台控制器
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		log.addHandler(consoleHandler);
		//文件控制器
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler(JavaUtilLoggingLogger.class.getName()+".log");
			fileHandler.setLevel(Level.INFO);
			log.addHandler(fileHandler);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		log.info("aaa");
		log2.info("bbb");
		log2.fine("fine");
	}
}
