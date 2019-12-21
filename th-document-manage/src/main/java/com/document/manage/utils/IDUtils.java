package com.document.manage.utils;

import java.util.Random;

/**
 * 各种id生成策略
 * @version 1.0
 */
public class IDUtils {

	/**
	 * 文档ID生成
	 */
	public static String genDocumentId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}

	/**
	 * 用户ID生成
	 */
	public static String genUserId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		String id = millis+"";
		return id;
	}

}
