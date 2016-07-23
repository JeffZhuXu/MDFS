package com.yisinian.mdfs.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP����
 * 
 * @author <a href="mailto:zlex.dongliang@gmail.com">����</a>
 * @since 1.0
 */
public abstract class GZipUtils {

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";

	/**
	 * ���ѹ��
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// ѹ��
		compress(bais, baos);
		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return output;
	}

	/**
	 * �ļ�ѹ��
	 * 
	 * @param file
	 * @throws Exception
	 * @author Jeff
	 * @return new GZIP File Name
	 */
	public static String compress(File file) throws Exception {
		String newFileName=compress(file, true);
		return newFileName;
	}

	/**
	 * �ļ�ѹ��
	 * 
	 * @param file
	 * @param delete
	 *            �Ƿ�ɾ��ԭʼ�ļ�
	 * @throws Exception
	 * @author Jeff
	 * @return new GZIP File Name
	 */
	public static String compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);
		compress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();
		if (delete) {
			file.delete();
		}
		return 	file.getPath() + EXT;
	}

	/**
	 * ���ѹ��
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os)
			throws Exception {
		GZIPOutputStream gos = new GZIPOutputStream(os);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.flush();
		gos.close();
	}

	/**
	 * �ļ�ѹ��
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * �ļ�ѹ��
	 * 
	 * @param path
	 * @param delete
	 *            �Ƿ�ɾ��ԭʼ�ļ�
	 * @throws Exception
	 */
	public static String compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		String newFileName=compress(file, delete);
		return newFileName;
	}

	/**
	 * ��ݽ�ѹ��
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// ��ѹ��

		decompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return data;
	}

	/**
	 * �ļ���ѹ��
	 * 
	 * @param file
	 * @throws Exception
	 * @author Jeff
	 * @return original file name
	 */
	public static String decompress(File file) throws Exception {
		String originalFileNameString = decompress(file, true);
		return originalFileNameString;
	}

	/**
	 * �ļ���ѹ��
	 * 
	 * @param file
	 * @param delete
	 *            �Ƿ�ɾ��ԭʼ�ļ�
	 * @throws Exception
	 * @author Jeff
	 * @return��Original File name
	 */
	public static String decompress(File file, boolean delete) throws Exception {
		String originalFileNameString = file.getPath().replace(EXT,"");
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(originalFileNameString);
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
		return originalFileNameString;
	}

	/**
	 * ��ݽ�ѹ��
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

	/**
	 * �ļ���ѹ��
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static String decompress(String path) throws Exception {
		String originalName=decompress(path, true);
		return originalName;
	}

	/**
	 * �ļ���ѹ��
	 * 
	 * @param path
	 * @param delete
	 *            �Ƿ�ɾ��ԭʼ�ļ�
	 * @throws Exception
	 */
	public static String decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		String originalName=decompress(file, delete);
		return originalName;

	}

//	public static void main(String[] args) {
//	}

}
