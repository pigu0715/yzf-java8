package com.tydic.common.utils;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesTool {
	private static String key = "TyDic@2019";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请选择你的操作:(1:加密, 2:解密需加DES:,3解密)");
		int type = scanner.nextInt();
		if (type == 1) {
			System.out.println("请输入要加密的明文:");
			String encryptValue = enc(scanner.next());
			System.out.println("加密后的结果为:" + encryptValue);
		} else if (type == 2) {
			System.out.println("请输入要解密的密文:");
			String encryptValue = dec(scanner.next());
			System.out.println("解密后的结果为:" + encryptValue);
		}else if (type == 3) {
			System.out.println("请输入要解密的密文:");
			String encryptValue = decDecrypt(scanner.next());
			System.out.println("解密后的结果为:" + encryptValue);
		}else {
			System.out.println("请按照上面的提示进行操作，请选择1或者2");
		}
	}

	public static String enc(String str) {
		byte[] encryptResult = encrypt(str.getBytes());
		String encryptValue = byte2hex(encryptResult);
		return encryptValue;
	}
	
	public static String decDecrypt(String str) {
		byte[] decryptResult = decrypt(hex2byte(str.getBytes()));
		String decryptValue = new String(decryptResult);
		return decryptValue;
	}
	
	public static String dec(String passWord) {
		if(passWord!=null&&passWord.indexOf("DES:")==0){
			passWord=passWord.replace("DES:", "");
			byte[] decryptResult = decrypt(hex2byte(passWord.getBytes()));
			String decryptValue = new String(decryptResult);
			return decryptValue;
		}else{
			return passWord;
		}
	}

	public static byte[] encrypt(byte[] datasource) {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec desKey = new DESKeySpec(key.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(1, securekey, sr);

			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decrypt(byte[] src) {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec desKey = new DESKeySpec(key.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(2, securekey, sr);

			return cipher.doFinal(src);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";

		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);

			b2[(n / 2)] = ((byte) Integer.parseInt(item, 16));
		}
		return b2;
	}
}
