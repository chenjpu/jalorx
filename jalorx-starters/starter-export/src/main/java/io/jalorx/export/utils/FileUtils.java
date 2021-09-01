package io.jalorx.export.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class FileUtils {

	/**
	 * 删除一个文件
	 * 
	 * @param pathname
	 * @return
	 */
	public static boolean MsgFileDelete(String pathname) {
		File f = new File(pathname);
		if (f.exists()) {// 如果存在
			return f.delete();
		}
		return false;
	}
	
	/**
	 * 删除一个空文件夹
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(String dir) {
		boolean success = (new File(dir)).delete();
		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}
		return success;
	}
	
	/** 
     * 获得指定文件的byte数组 
     */  
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	 /** 
     * 根据byte数组，生成文件 
     */  
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}  
	
//	public static String getRootPath() {
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
//        ServletContext servletContext = webApplicationContext.getServletContext();
//		String rootApp = servletContext.getInitParameter("webAppRootKey");
//		String TMP_FILE_PATH = System.getProperty(rootApp);
//		String rootPath = (null !=TMP_FILE_PATH && TMP_FILE_PATH.indexOf(":") > 0)
//				? TMP_FILE_PATH.substring(0, TMP_FILE_PATH.indexOf(":") + 1) : "";
//		return rootPath;
//	}
	
}
