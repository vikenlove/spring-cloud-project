package com.emerging.framework.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期工具类
 * 
 * @author wt
 *
 */
public class DateUtil {

	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat formatChinest = new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * 获取当前系统日期
	 * 
	 * @param operation
	 *            操作符
	 * @return String 根据操作符返回反定格式日期数据(操作符为"-":返回yyyy-mm-dd.操作符为"":返回yyyymmdd)
	 */
	public static String getDateStr(String operation) {
		if (null == operation)
			operation = "";
		Calendar rightNow = Calendar.getInstance();
		String year = Integer.toString(rightNow.get(Calendar.YEAR));
		String month = Integer.toString(rightNow.get(Calendar.MONTH) + 1);
		String day = Integer.toString(rightNow.get(Calendar.DAY_OF_MONTH));
		year = (year.length() < 4) ? ("20" + year) : year;
		month = (month.length() < 2) ? ("0" + month) : month;
		day = (day.length() < 2) ? ("0" + day) : day;
		String res = year + operation + month + operation + day;
		return res;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @param operation
	 *            操作符
	 * @return String 根据操作符返回反定格式日期数据(操作符为"-":返回yyyy-mm-dd
	 *         hh:mi:ss.操作符为"":返回yyyymmdd hh:mi:ss)
	 */
	public static String getDateTimeStr(String operation) {
		if (null == operation)
			operation = "";
		Calendar rightNow = Calendar.getInstance();
		String year = Integer.toString(rightNow.get(Calendar.YEAR));
		String month = Integer.toString(rightNow.get(Calendar.MONTH) + 1);
		String day = Integer.toString(rightNow.get(Calendar.DAY_OF_MONTH));
		String hour = "" + rightNow.get(Calendar.HOUR_OF_DAY);
		String minute = "" + rightNow.get(Calendar.MINUTE);
		String second = "" + rightNow.get(Calendar.SECOND);
		year = (year.length() < 4) ? ("20" + year) : year;
		month = (month.length() < 2) ? ("0" + month) : month;
		day = (day.length() < 2) ? ("0" + day) : day;
		hour = (hour.length() < 2) ? ("0" + hour) : hour;
		minute = (minute.length() < 2) ? ("0" + minute) : minute;
		second = (second.length() < 2) ? ("0" + second) : second;
		String strHms = hour + ":" + minute + ":" + second;
		String res = year + operation + month + operation + day + " " + strHms;
		return res;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @param operation
	 *            操作符
	 * @param monthLen
	 *            月份
	 * @return String 根据操作符返回反定格式日期数据(操作符为"-":返回yyyy-mm-dd
	 *         hh:mi:ss.操作符为"":返回yyyymmdd hh:mi:ss)
	 */
	@SuppressWarnings("static-access")
	public static String getDateTimeStr(String operation, int monthLen) {
		if (null == operation)
			operation = "";
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(rightNow.MONTH, monthLen);
		String year = Integer.toString(rightNow.get(Calendar.YEAR));
		String month = Integer.toString(rightNow.get(Calendar.MONTH) + 1);
		String day = Integer.toString(rightNow.get(Calendar.DAY_OF_MONTH));
		int hour = rightNow.get(Calendar.HOUR_OF_DAY) * 10000;
		int minute = rightNow.get(Calendar.MINUTE) * 100;
		int second = rightNow.get(Calendar.SECOND);
		year = (year.length() < 4) ? ("20" + year) : year;
		month = (month.length() < 2) ? ("0" + month) : month;
		day = (day.length() < 2) ? ("0" + day) : day;
		String strHms = "" + (hour + minute + second);
		strHms = (strHms.length() < 6) ? ("0" + strHms) : strHms;
		String res = year + operation + month + operation + day + strHms;
		return res;
	}

	/**
	 * 对传入参数日期加减
	 * 
	 * @param dates
	 *            日期
	 * @param len
	 *            天数
	 * @return String 对传入的日期进行天数的增加或减少(返回格式：yyyy-mm-dd)
	 */
	public static String getDay(String dates, int len) {
		String res = dates;

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = ft.parse(dates);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, len);
			// 转换成YYYY-MM-DD
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			year = (year.length() < 4) ? ("20" + year) : year;
			month = (month.length() < 2) ? ("0" + month) : month;
			day = (day.length() < 2) ? ("0" + day) : day;
			res = year + "-" + month + "-" + day;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 对传入参数日期的年份加减
	 * 
	 * @param dates
	 *            日期
	 * @param len
	 *            天数
	 * @return String 对传入的日期进行月份的增加或减少(返回格式：yyyy-mm-dd)
	 */
	public static String getYear(String dates, int len) {
		String res = dates;

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = ft.parse(dates);
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, len);
			// 转换成YYYY-MM-DD
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			year = (year.length() < 4) ? ("20" + year) : year;
			month = (month.length() < 2) ? ("0" + month) : month;
			day = (day.length() < 2) ? ("0" + day) : day;
			res = year + "-" + month + "-" + day;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 对传入参数日期的月份加减
	 * 
	 * @param dates
	 *            日期
	 * @param len
	 *            天数
	 * @return String 对传入的日期进行月份的增加或减少(返回格式：yyyy-mm-dd)
	 */
	public static String getMonth(String dates, int len) {
		String res = dates;

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = ft.parse(dates);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, len);
			// 转换成YYYY-MM-DD
			String year = Integer.toString(calendar.get(Calendar.YEAR));
			String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
			String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			year = (year.length() < 4) ? ("20" + year) : year;
			month = (month.length() < 2) ? ("0" + month) : month;
			day = (day.length() < 2) ? ("0" + day) : day;
			res = year + "-" + month + "-" + day;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 获取上个月
	 * 
	 * @param date
	 * @return
	 */
	public static String getPreviousMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
		return ft.format(c.getTime());
	}

	/**
	 * 获取下个月
	 * 
	 * @param date
	 * @return
	 */
	public static String getNextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
		return ft.format(c.getTime());
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 */
	public static String getLastDay() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	/**
	 * 得到当前月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		String today = new SimpleDateFormat("yyyy-MM").format(new Date());// 本月
		return today;
	}

	/**
	 * 
	 * 获取当月第几天
	 * 
	 * @return
	 */
	public static int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 格式转换
	 * 
	 * @param date
	 *            字符串日期，对应格式为beginFormat
	 * @param beginFormat
	 *            转换前格式
	 * @param endformat
	 *            转换后格式
	 * @return
	 */
	public static String formatDate(String date, String beginFormat, String endformat) {
		SimpleDateFormat sf1 = new SimpleDateFormat(beginFormat);
		SimpleDateFormat sf2 = new SimpleDateFormat(endformat);
		String sfstr = "";
		try {
			sfstr = sf2.format(sf1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sfstr;
	}

	/**
	 * 转换字符串到日期<br>
	 * 格式YYYMMDD
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse_yyyyMMdd(String dateStr) throws ParseException {
		if (StringUtils.isEmpty(dateStr) || dateStr.length() != 8) {
			throw new RuntimeException("格式错误");
		}
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		return ft.parse(dateStr);
	}
	
	/**
	 * 转换字符串到日期<br>
	 * 格式YYYMMDD 或 yyyy-MM-dd 或 yyyy/MM/dd
	 * @author hyp
	 * @param dateStr
	 * @param spiltType 切分类型 “-”或“/”
	 * @return
	 * @throws ParseException
	 */
	public static Date parse_yyyy_MM_dd(String dateStr,String spiltType) throws ParseException {
		if (StringUtils.isEmpty(dateStr) || dateStr.length() < 8) {
			throw new RuntimeException("格式错误");
		}
		if(StringUtils.isEmpty(spiltType)){
			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
			return ft.parse(dateStr);
		}else{
			SimpleDateFormat ft = new SimpleDateFormat("yyyy"+spiltType+"MM"+spiltType+"dd");
			return ft.parse(dateStr);
		}
	}

	/**
	 * 转换字符串到日期<br>
	 * 格式YYYMM
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse_yyyyMM(String dateStr) throws ParseException {
		if (StringUtils.isEmpty(dateStr) || dateStr.length() != 6) {
			throw new RuntimeException("格式错误");
		}
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMM");
		return ft.parse(dateStr);
	}

	public static boolean isDate(String date) {    
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"; 
		Pattern pat = Pattern.compile(rexp); 
		Matcher mat = pat.matcher(date);  
		boolean dateType = mat.matches(); 
		return dateType; 
	}


	/**
	 * 转换日期到指定格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(date);
	}

	/**
	 * 判断指定日期是否在当前日期一年以内   例如当前时间为 2019-03-06   则有效时间为  2018-03-07至2019-03-05
	 * @param date
	 * @return
	 */
	public static Boolean isInOneYear(String  date){
		Date dateToCheck = DateTimeUtils.parse(date);
		Date dateStart = DateTimeUtils.getDateBeforeOneYear();
		Date dateEnd = DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH);
		return  DateTimeUtils.isBetweenStartAndEnd(dateToCheck,dateStart,dateEnd);
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.formatDate("201401", "yyyyMM", "yyyy-MM-dd").substring(0, 7));
		System.out.println(DateUtil.format.format(new Date()));
		System.out.println(DateUtil.formatDate(DateUtil.parse_yyyyMM("201609"), "yyyy-MM"));
		System.out.println(DateUtil.formatDate(DateUtil.parse_yyyyMMdd("20160931"), "yyyy-MM-dd"));
		System.out.println(DateUtil.getNextMonth(DateUtil.parse_yyyyMM("201611")));
		String dat = DateUtil.getDateStr("-");
		Date d = DateUtil.parse_yyyy_MM_dd("2018-08-24","-");
		String bf = DateUtil.getMonth(dat,-12);
		Date bff = DateUtil.parse_yyyy_MM_dd(bf,"-");
		System.out.println(bff);
		System.out.println(d.before(bff));
		String date = "2018-13-22";
		System.out.println(date +"是否为时间格式：" + isDate(date));
		System.out.println(date + "是否有效：" + isInOneYear(date));
		System.out.println(DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH));

	}

}
