package com.lsf.imf.util;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Tools {
	private static Logger log = LoggerFactory.getLogger(Tools.class);


	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";


	public static String getCurrentDateTime() {
		return timeFormat(TIME_FORMAT, new Date());
	}

	public static String timeFormat(String format, Date day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String date = sdf.format(day);
			return date;
		} catch (Exception e) {

		}
		return null;
	}

	public static Date timeParse(String format, String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			return date;
		} catch (Exception e) {
		}
		return null;
	}
	public static String getDay(int num) {
		Date date = new Date();
		if (num != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, num);
			date = calendar.getTime();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		String day = formatter.format(date);

		return day;
	}
	
	public static String getTime(String time,String interval) {
		Date date=timeParse(TIME_FORMAT,time);
		return getTime(date,interval);
	}
	public static String getTime(String interval) {
		Date date = new Date();
		return getTime(date,interval);
	}
	public static String getTime(Date date,String interval) {
		
		String timeInterval = interval.toUpperCase();
		int intervalType;
		if (timeInterval.endsWith("D")) {
			interval = timeInterval.replace("D", "");
			intervalType = Calendar.DAY_OF_MONTH;
		} else if (timeInterval.endsWith("H")) {
			interval = timeInterval.replace("H", "");
			intervalType = Calendar.HOUR;
		} else if (timeInterval.endsWith("M")) {
			interval = timeInterval.replace("M", "");
			intervalType = Calendar.MINUTE;
		} else {
			interval = timeInterval.replace("S", "");
			intervalType = Calendar.SECOND;
		}
		if (!interval.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(intervalType, Integer.parseInt(interval));
			if(intervalType==Calendar.DAY_OF_MONTH) {
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);	
			}
			
			date = calendar.getTime();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);

		return formatter.format(date);
	}

	public static Map<String,Object> getChildElementDataMap(Element dataElement) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		List<Element> list=dataElement.elements();
		if(list.size()==0) {
			
				dataMap.put(StringUtils.lowerCase(dataElement.getName()), dataElement.getTextTrim());
			
			
		}else {	
			for(Element e:list) {
				Map<String,Object> childDataMap =getChildElementDataMap(e);

				Set<String> keySet = childDataMap.keySet();
		        for (Iterator it = keySet.iterator(); it.hasNext();) {
		            String key = (String) it.next();
		            if(dataMap.containsKey(key)) {
		            	dataMap.put(key, dataMap.get(key)+","+childDataMap.get(key));
		            }else {
		            	dataMap.put(key, childDataMap.get(key));
		            }
		        }
			}
		}
		
		return dataMap;
	}
	
	public static void main(String[] args) {
//		
//		System.out.println(getCurrentDateTime());
//		
//		System.out.println(getDay(-1));
//		
//		System.out.println(getTime("-1D"));
//		
//		System.out.println(getTime("-21H"));
//		
//		System.out.println(getTime("-20M"));
//		
//		System.out.println(getTime("2017-03-01 00:15:34","-20M"));
		
		
		String day=Tools.getDay(-7);
        Date date=Tools.timeParse(Tools.TIME_FORMAT, day+" 00:00:00");
        
        System.out.println(new Timestamp(date.getTime()));
	}
}
