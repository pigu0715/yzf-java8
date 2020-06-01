
package com.tydic.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create on 2010-12-7 上午10:18:09
 * 
 * 工具类
 * 
 * @author edu184
 * @version
 */
@SuppressWarnings({ "rawtypes" })
public class ZipUtils {

	private static Logger log = LoggerFactory.getLogger(ZipUtils.class);

	private static final int BUFFER_SIZE = 2 * 1024;

	public static void main(String[] args) throws Exception {
		 /** 测试压缩方法1  */
        FileOutputStream fos1 = new FileOutputStream(new File("e:/Excel工单查询表.zip"));
        ZipUtils.toZip("E:\\Excel外呼录音报表明细.xlsx", fos1,true);
    }
	/**
	 * 压缩成ZIP 方法1
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException
	 *             压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @param name
	 *            压缩后的名称
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
			throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (KeepDirStructure) {
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}
				}
			}
		}
	}

	/**
	 * 多文件压缩
	 * 
	 * <pre>
	 *    Example : 
	 *    ZipOutputStream zosm = new ZipOutputStream(new FileOutputStream(&quot;c:/b.zip&quot;));
	 *    zipFiles(zosm, new File(&quot;c:/com&quot;), &quot;&quot;);
	 *    zosm.close();
	 * </pre>
	 * 
	 * @param zosm
	 * @param file
	 * @param basePath
	 * @throws IOException
	 */
	public static void compressionFiles(ZipOutputStream zosm, File file, String basePath) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			try {
				zosm.putNextEntry(new ZipEntry(basePath + "/"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
			for (File f : files) {
				compressionFiles(zosm, f, basePath);
			}
		} else {
			FileInputStream fism = null;
			BufferedInputStream bism = null;
			try {
				byte[] bytes = new byte[1024];
				fism = new FileInputStream(file);
				bism = new BufferedInputStream(fism, 1024);
				basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
				zosm.putNextEntry(new ZipEntry(basePath));
				int count;
				while ((count = bism.read(bytes, 0, 1024)) != -1) {
					zosm.write(bytes, 0, count);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bism != null) {
					try {
						bism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fism != null) {
					try {
						fism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void compressionFiles(ZipOutputStream zosm, File file, String basePath, String fileName) {
		log.info("MyUtils file==================================" + file);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			try {
				zosm.putNextEntry(new ZipEntry(basePath + "/"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			basePath = basePath + (basePath.length() == 0 ? "" : "/") + fileName;
			for (File f : files) {
				compressionFiles(zosm, f, basePath);
			}
		} else {
			FileInputStream fism = null;
			BufferedInputStream bism = null;
			try {
				byte[] bytes = new byte[1024];
				fism = new FileInputStream(file);
				bism = new BufferedInputStream(fism, 1024);
				basePath = basePath + (basePath.length() == 0 ? "" : "/") + fileName;
				zosm.putNextEntry(new ZipEntry(basePath));
				int count;
				while ((count = bism.read(bytes, 0, 1024)) != -1) {
					zosm.write(bytes, 0, count);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bism != null) {
					try {
						bism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fism != null) {
					try {
						fism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param zosm
	 * @param file
	 * @param basePath
	 */

	public static void compressionFiles(ZipOutputStream zosm, File[] files, String basePath, String[] fileNames) {
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			compressionFiles(zosm, f, "", fileNames[i]);
		}
	}

	/**
	 * 解压缩zip文件
	 * 
	 * @param zipFileName
	 *            压缩文件
	 * @param extPlace
	 *            解压的路径
	 */
	public static boolean decompressionZipFiles(String zipFileName, String extPlace) {
		boolean flag = false;
		try {
			unZip(zipFileName, extPlace);
			flag = true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return flag;
	}

	private static void getDir(String directory, String subDirectory) {
		String dir[];
		File fileDir = new File(directory);
		try {
			if (subDirectory == "" && fileDir.exists() != true)
				fileDir.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param zipFileNaame
	 *            being unzip file including file name and path ;
	 * @param outputDirectory
	 *            unzip files to this directory
	 * 
	 */
	public static void unZip(String zipFileName, String outputDirectory) {
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			java.util.Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			getDir(outputDirectory, "");
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				// System.out.println("unziping " + zipEntry.getName());
				if (zipEntry.isDirectory()) { // 如果得到的是个目录，
					String name = zipEntry.getName(); // 就创建在指定的文件夹下创建目录
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
					// System.out.println("创建目录：" + outputDirectory + File.separator
					// + name);
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					// System.out.println("测试文件1：" +fileName);
					if (fileName.indexOf("/") != -1) {
						getDir(outputDirectory, fileName.substring(0, fileName.lastIndexOf("/")));
						// System.out.println("文件的路径："+fileName);
						fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());

					}

					File f = new File(outputDirectory + File.separator + zipEntry.getName());

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * this mothed will unzip all the files which in your specifeid folder;
	 * 
	 * @param filesFolder
	 * @param outputDirectory
	 */
	public static void unzipFiles(String filesFolder, String outputDirectory) {
		File zipFolder = new File(filesFolder);
		String zipFiles[];
		String zipFileAbs;
		try {
			zipFiles = zipFolder.list();
			for (int i = 0; i < zipFiles.length; i++) {
				if (zipFiles[i].length() == (zipFiles[i].lastIndexOf(".zip") + 4)) {// 判断是不是zip包
					zipFileAbs = filesFolder + File.separator + zipFiles[i];
					unZip(zipFileAbs, outputDirectory);
				}
			}
		} catch (SecurityException ex) {
			ex.printStackTrace();
		}

	}

}
