package org.extreme.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;



/**
 * <p>
 * Title: 系统时间公共类
 * </p>
 * <li>提供取得系统时间的所有共用方法</li>
 */
public class DateUtils {
	/**
	 * 在所有的日期函数中使用的格式.
	 */
	// :三个比较常用的DateFormat,在函数中把字符串转成日期型时,默认依次使用这三个Format
	public static final DateFormat DATEFORMAT1 = new SimpleDateFormat("yyyy/MM/dd");
	public static final DateFormat DATEFORMAT2 = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DATEFORMAT3 = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat DATEFORMAT4 = new SimpleDateFormat("MM/dd/yyyy"); // :吉大兼容
	public static final DateFormat DATETIMEFORMAT1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final DateFormat DATETIMEFORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATETIMEFORMAT3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat DATETIMEFORMAT4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	private DateUtils() {
	}

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

	/**
	 * 取得某日期时间的特定表示格式的字符串
	 * 
	 * @param format
	 *            时间格式
	 * @param date
	 *            某日期（Date）
	 * @return 某日期的字符串
	 */
	public static synchronized String getDate2Str(String format, Date date) {
		simpleDateFormat.applyPattern(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期转换为长字符串（包含：年-月-日 时:分:秒）
	 * 
	 * @param date
	 *            日期
	 * @return 返回型如：yyyy-MM-dd HH:mm:ss 的字符串
	 */
	public static String getDate2LStr(Date date) {
		return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * 取得型如：yyyyMMddHmmssSSS的字符串
	 * 
	 * @param date
	 * @return 返回型如：yyyyMMddHmmssSSS 的字符串
	 */
	public static String getDate2AllIncludeSSS(Date date) {
		return getDate2Str("yyyyMMddHmmssSSS", date);
	}

	/*
	 * 根据年月日生成日期
	 */
	public static java.util.Date createDate(int year, int month, int day) {
		return createDate(year, month, day, 0, 0, 0);
	}

	/*
	 * 根据年月日,时分秒生成日期
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
	 * 返回值可能是null,所以拿到结果后,必须做NPE的判断
	 */
	public static Date object2Date(Object obj, boolean returnNull) {
		java.util.Date ret = null;

		if (obj instanceof Date) {
			ret = (Date) obj;
		} else if (obj instanceof Calendar) {
			ret = ((Calendar) obj).getTime();
		} else if (obj instanceof Number) {
			long longValue = ((Number) obj).longValue();
			// :如果这个值大于1000000(随便取的一个数),表示记录的是毫秒
			if (longValue > 1000000) {
				ret = new Date(longValue);
			}
			// 否则表示记录的为天数(从1900年记起)
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
		 * :如果是空白的,返回null,不返回new Date(); 像format("",
		 * "yyyyMM")就不应该返回当前日期的结果,而应该是一个空字符串
		 */
		if (StringUtils.isBlank(str)) {
			return null;
		}

		// :保存Date类型的时候,直接保存milliseconds
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

		// 返回当前日期
		return new java.util.Date();
	}

	/*
	 * 日期型相减的函数
	 */
	public static long subtractDate(Date subtractee, Date subtractor, String unit) {
		long time1 = subtractee.getTime();
		long time2 = subtractor.getTime();
		long timeDistance = time1 - time2;
		// 秒
		if (SECOND.equals(unit)) {
			return timeDistance / 1000;
		}
		// 分
		else if (MINUTE.equals(unit)) {
			return timeDistance / (60 * 1000);
		}
		// 小时
		else if (HOUR.equals(unit)) {
			return timeDistance / (3600 * 1000);
		}
		// 天
		else if (DAY.equals(unit)) {
			return timeDistance / (24 * 3600 * 1000);
		}
		// 周
		else if (WEEK.equals(unit)) {
			return timeDistance / (7 * 24 * 3600 * 1000);
		}
		// 默认返回天数
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
	 * 一个日期加上天数 如果days是负数的话就是减去天数
	 */
	public static Date datePlusInteger(Date date, int days) {
		long time = date.getTime();
		time += (long) days * 24 * 3600 * 1000;
		return new Date(time);
	}
}
