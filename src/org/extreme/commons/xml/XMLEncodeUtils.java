package org.extreme.commons.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.extreme.commons.util.Ascii85InputStream;
import org.extreme.commons.util.Ascii85OutputStream;
import org.extreme.commons.util.StringUtils;
import org.extreme.commons.util.Utils;


/**
 * Encode data.
 */
public class XMLEncodeUtils {
	private XMLEncodeUtils() {
	}
	
	/**
	 * Compress data using zip algorithm.
	 */
	public static byte[] zipDeflate(byte[] data) {
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		try {
			DeflaterOutputStream out = new DeflaterOutputStream(
					byteArrayOutput, new Deflater(Deflater.BEST_COMPRESSION, true), 512);
			
			out.write(data, 0, data.length);
			out.close();
		} catch (Exception e) {
		}
		
		return byteArrayOutput.toByteArray();
	}
	
	/**
	 * Uncompress data using zip algorithm.
	 */
	public static byte[] zipInflate(byte[] data) {
		ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(data);
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		
		try {
			InflaterInputStream gZipInput = new InflaterInputStream(byteArrayInput, new Inflater(true), 512);
			
			byte[] ba = new byte[512];
			int cnt;
			
			while ((cnt = gZipInput.read(ba, 0, ba.length)) >= 0) {
				byteArrayOutput.write(ba, 0, cnt);
			}
		} catch (IOException ioe) {
		}
		
		return byteArrayOutput.toByteArray();
	}
	
	/**
	 * Encode Ascii85..
	 */
	public static byte[] encodeAscii85(byte[] data) {
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		
		try {
			Ascii85OutputStream out = new Ascii85OutputStream(byteArrayOutput);
			
			out.write(data, 0, data.length);
			out.close();
		} catch (Exception e) {
		}
		
		return byteArrayOutput.toByteArray();
	}
	
	/**
	 * Decode Ascii85.
	 */
	public static byte[] decodeAscii85(byte[] data) {
		ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(data);
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		
		try {
			Ascii85InputStream ascii85InputStream = new Ascii85InputStream(byteArrayInput);
			
			byte[] ba = new byte[512];
			int cnt;
			while ((cnt = ascii85InputStream.read(ba, 0, ba.length)) >= 0) {
				byteArrayOutput.write(ba, 0, cnt);
			}
		} catch (IOException ioe) {
		}
		
		return byteArrayOutput.toByteArray();
	}
	
	public static String encodeBytesAsString(byte[] bytes) {
		try {
			return new String(
					XMLEncodeUtils.encodeAscii85(XMLEncodeUtils.zipDeflate(bytes)), "ISO-8859-1"
			);
		} catch (UnsupportedEncodingException e) {
			return StringUtils.EMPTY;
		}
	}
	
	public static byte[] decodeStringAsBytes(String str) {
		try {
			return XMLEncodeUtils.zipInflate(XMLEncodeUtils.decodeAscii85(str.getBytes("ISO-8859-1")));
		}  catch (UnsupportedEncodingException e) {
			return new byte[0];
		}	catch (Exception e) {
			return new byte[0];
		}
	}
	
	/*
	 * дCDATA��ֵ,ÿ�ζ�ҪдCDATA,��,������д��,���Լ���ô��static����
	 */
	public static String wrapCDATA(String data) {
		return "<![CDATA[" + cdataEncode(data) + "]]>";
	}
	
	public static String cdataEncode(String data) {
		return Utils.replaceAllString(data, xmlImageDataArray[0], xmlImageDataArray[1]);
	}
	
	public static String cdataDecode(String data) {
		return Utils.replaceAllString(data, xmlImageDataArray[1], xmlImageDataArray[0]);
	}
	
	public static String deprecatedCDATADecode(String data) {
		return Utils.replaceAllString(data, deprecated_XmlImageDataArray[1], deprecated_XmlImageDataArray[0]);
	}
	
	// : ����CDATA�����е�]]>���޷������ģ�������Ҫ������룬�����滻
	// 6.2�汾֮ǰ�ǽ�]]�滻��]]A������]]>�����]]A>, ������Weblogic�����й������ʱ���������������]]����һ�е����ʧ�����ַ�������
	// �������: �ĳɽ�]�滻]A�����Խ��]]>�����⣬weblogic������Ҳ�ɽ��
	// ��������Ϊ�˼���ԭ���Ľ������
	private static String deprecated_XmlImageDataArray[] = {"]]", "]]A"};	// ԭ���Ľ������
	
	/*
	 * @see com.fr.util.Consts.CPT_XML_Version
	 * "FR_XML_CDATA_WEBLOGIC_BUG_20091127"
	 */
	private static String xmlImageDataArray[] = {"]", "]A"};
}
