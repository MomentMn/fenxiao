package com.hansan.fenxiao.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5
{
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String sign(String text, String key, String input_charset)
	{
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	public static boolean verify(String text, String sign, String key, String input_charset)
	{
		text = text + key;
		System.out.println("text:" + text);
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));

		return mysign.equals(sign);
	}

	private static byte[] getContentBytes(String content, String charset)
	{
		if ((charset == null) || ("".equals(charset)))
			return content.getBytes();
		try
		{
			return content.getBytes(charset);
		}
		catch (UnsupportedEncodingException e)
		{
		}
		throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
	}

	public static String MD5Encode(String origin, String charsetname)
	{
		String resultString = null;
		try
		{
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
			{
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			}
			else
			{
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		}
		catch (Exception exception)
		{
		}
		return resultString;
	}

	// MD5Util工具类中相关的方法

	private static String byteArrayToHexString(byte b[])
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b)
	{
		int n = b;
		if (n < 0)
		{
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}