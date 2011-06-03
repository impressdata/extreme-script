package org.extreme.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;



/**
 * <p>
 * Title: ϵͳʱ�乫����
 * </p>
 * <li>�ṩȡ��ϵͳʱ������й��÷���</li>
 */
public class DateUtils {
	/**
	 * �����е����ں�����ʹ�õĸ�ʽ.
	 */
	// :�����Ƚϳ��õ�DateFormat,�ں����а��ַ���ת��������ʱ,Ĭ������ʹ��������Format
	public static final DateFormat DATEFORMAT1 = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat DATEFORMAT2 = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DATEFORMAT3 = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat DATEFORMAT4 = new SimpleDateFormat("MM/dd/yyyy"); // :�������
	public static final DateFormat DATETIMEFORMAT1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final DateFormat DATETIMEFORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATETIMEFORMAT3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat DATETIMEFORMAT4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	private DateUtils() {
	}

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

	/**
	 * ȡ��ĳ����ʱ����ض���ʾ��ʽ���ַ���
	 * 
	 * @param format
	 *            ʱ���ʽ
	 * @param date
	 *            ĳ���ڣ�Date��
	 * @return ĳ���ڵ��ַ���
	 */
	public static synchronized String getDate2Str(String format, Date date) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * ������ת��Ϊ���ַ�������������-��-�� ʱ:��:�룩
	 * 
	 * @param date
	 *            ����
	 * @return �������磺yyyy-MM-dd HH:mm:ss ���ַ���
	 */
	public static String getDate2LStr(Date date) {
		return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * ȡ�����磺yyyyMMddHmmssSSS���ַ���
	 * 
	 * @param date
	 * @return �������磺yyyyMMddHmmssSSS ���ַ���
	 */
	public static String getDate2AllIncludeSSS(Date date) {
		return getDate2Str("yyyyMMddHmmssSSS", date);
	}

	/*
	 * ������������������
	 */
	public static java.util.Date createDate(int year, int month, int day) {
		return createDate(year, month, day, 0, 0, 0);
	}

	/*
	 * ����������,ʱ������������
	 */
	public static java.util.Date createDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);

		return c.getTime();
	}

	/*
	 * ����ֵ������null,�����õ������,������NPE���ж�
	 */
	public static Date object2Date(Object obj, boolean returnNull) {
		java.util.Date ret = null;

		if (obj instanceof Date) {
			ret = (Date) obj;
		} else if (obj instanceof Calendar) {
			ret = ((Calendar) obj).getTime();
		} else if (obj instanceof Number) {
			long longValue = ((Number) obj).longValue();
			// :������ֵ����1000000(���ȡ��һ����),��ʾ��¼���Ǻ���
			if (longValue > 1000000) {
				ret = new Date(longValue);
			}
			// �����ʾ��¼��Ϊ����(��1900�����)
			else {
				ret = new Date((longValue - 25569) * 1000 * 24 * 60 * 60);
			}
		} else if (obj != null) {
			ret = DateUtils.string2Date(Utils.objectToString(obj), returnNull);
		}

		if (!returnNull && ret == null) {
			ret = new java.util.Date();
		}

		return ret;
	}
	
	public static Date string2Date(String str, boolean returnNull) {
		/*
		 * :����ǿհ׵�,����null,������new Date(); ��format("",
		 * "yyyyMM")�Ͳ�Ӧ�÷��ص�ǰ���ڵĽ��,��Ӧ����һ�����ַ���
		 */
		if (StringUtils.isBlank(str)) {
			return null;
		}

		// :����Date���͵�ʱ��,ֱ�ӱ���milliseconds
		else if (str.matches("^\\d+$")) {
			return object2Date(Long.valueOf(str), true);
		}

		if (str.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
			try {
				return DATEFORMAT4.parse(str);
			} catch (ParseException e) {
				LogUtil.getLogger().log(Level.WARNING, str + " can't be parsed to Date");
			}
		}

		try {
			return DATETIMEFORMAT1.parse(str);
		} catch (ParseException e1) {
			try {
				return DATETIMEFORMAT2.parse(str);
			} catch (ParseException e2) {
				try {
					return DATETIMEFORMAT3.parse(str);
				} catch (ParseException e3) {
					try {
						return DATETIMEFORMAT4.parse(str);
					} catch (ParseException e4) {
						try {
							return DATEFORMAT1.parse(str);
						} catch (ParseException e5) {
							try {
								return DATEFORMAT2.parse(str);
							} catch (ParseException e6) {
								try {
									return DATEFORMAT3.parse(str);
								} catch (ParseException e7) {
									if (returnNull) {
										return null;
									} else {
										LogUtil.getLogger().log(Level.WARNING, str + " can't be parsed to Date");
									}
								}
							}
						}
					}
				}
			}
		}

		// ���ص�ǰ����
		return new java.util.Date();
	}

	/*
	 * ����������ĺ���
	 */
	public static long subtractDate(Date subtractee, Date subtractor, String unit) {
		long time1 = subtractee.getTime();
		long time2 = subtractor.getTime();
		long timeDistance = time1 - time2;
		// ��
		if (SECOND.equals(unit)) {
			return timeDistance / 1000;
		}
		// ��
		else if (MINUTE.equals(unit)) {
			return timeDistance / (60 * 1000);
		}
		// Сʱ
		else if (HOUR.equals(unit)) {
			return timeDistance / (3600 * 1000);
		}
		// ��
		else if (DAY.equals(unit)) {
			return timeDistance / (24 * 3600 * 1000);
		}
		// ��
		else if (WEEK.equals(unit)) {
			return timeDistance / (7 * 24 * 3600 * 1000);
		}
		// Ĭ�Ϸ�������
		else {
			return timeDistance / (24 * 3600 * 1000);
		}
	}

	public static final String SECOND = "s";
	public static final String MINUTE = "m";
	public static final String HOUR = "h";
	public static final String DAY = "d";
	public static final String WEEK = "w";

	/*
	 * һ�����ڼ������� ���days�Ǹ����Ļ����Ǽ�ȥ����
	 */
	public static Date datePlusInteger(Date date, int days) {
		long time = date.getTime();
		time += (long) days * 24 * 3600 * 1000;
		return new Date(time);
	}
}
