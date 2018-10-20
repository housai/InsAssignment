package com.klein.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import com.aliyuncs.exceptions.ClientException;


public class MyUtil {

	/**
	 * 判断是否是double类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsDouble(String str) {
		try {
			Double.valueOf(str);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	public static SimpleDateFormat sdf_date=new SimpleDateFormat("yyyy-MM-dd");
	public static Date StringToDate(String date){
		Date result = Calendar.getInstance().getTime();
		try {
			result=sdf_date.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 金额验证
	 * 
	 * @param str
	 * @return true:是金额;false:不是金额
	 */
	public static boolean IsMoney(String str) {
		// 判断小数点后2位的数字的正则表达式
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
		java.util.regex.Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 两数相减
	 * @return
	 */
	public static double SubDouble(double db1, double db2) {
		BigDecimal value1 = new BigDecimal(String.valueOf(db1));
		BigDecimal value2 = new BigDecimal(String.valueOf(db2));
		double value = value1.subtract(value2).doubleValue();
		if(value <0.0){
			return 0.0;
		}else{
			return value;
		}
	}

	/**
	 * 两数相加
	 *
	 * @return
	 */
	public static double AddDouble(double db1, double db2) {
		BigDecimal value1 = new BigDecimal(String.valueOf(db1));
		BigDecimal value2 = new BigDecimal(String.valueOf(db2));
		return value1.add(value2).doubleValue();
	}

	/**
	 * 两数相加(四舍五入保留2位)
	 *
	 * @return
	 */
	public static double AddDouble2(double db1, double db2) {
		BigDecimal value1 = new BigDecimal(String.valueOf(db1));
		BigDecimal value2 = new BigDecimal(String.valueOf(db2));
		BigDecimal total = value1.add(value2);
		return total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 取得double值
	 * 
	 * @param value
	 * @return
	 */
	public static double GetDouble(Double value) {
		if (value == null) {
			return 0;
		} else {
			return value;
		}
	}

	/**
	 * 结束时间
	 * 
	 * @param date
	 * @return yyyy-MM-dd 23:59:59日期
	 */
	public static Date GetEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);    
        int minute = cal.get(Calendar.MINUTE);    
        int second = cal.get(Calendar.SECOND);  
        
        // 时分秒（毫秒数）    
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;   
        
        // 凌晨00:00:00    
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);    

		// 凌晨23:59:59
		cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59
				* 60 * 1000 + 59 * 1000);

		return cal.getTime();
	}

	/**
	 * 文件名称
	 * 
	 * @return
	 */
	public static String GetFileName() {
		int temp = (int) (Math.random() * 9000 + 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
		return sdf.format(new Date()) + "_" + new Integer(temp).toString()
				+ ".png";
	}
	
	/**
	 * 文件名称
	 * 
	 * @return
	 */
	public static String GetFileName(String end) {
		int temp = (int) (Math.random() * 9000 + 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
		return sdf.format(new Date()) + "_" + new Integer(temp).toString()
				+ end;
	}

	/**
	 * 文件名称(时间+随机数)
	 * 
	 * @return
	 */
	public static String GetFileNameRandom() {
		return GetCurrentTime() + GetRandom4();
	}

	/**
	 * 得到当前时间
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String GetCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String FormatCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 时间转年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String DateTimeToString(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}

	/**
	 * 时间转年月日
	 * 
	 * @return yyyy年MM月dd日
	 */
	public static String DateTimeToDateStr(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(time);
	}

	/**
	 * 取得4位随机数
	 * 
	 * @return 4位随机数
	 */
	public static String GetRandom4() {
		Random random = new Random();
		int x = random.nextInt(8999);
		int y = x + 1000;
		return String.valueOf(y);
	}


	
	/**
	 * 日期格式化
	 * 
	 * @return
	 */
	public static String formatDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	public static boolean isEmpty(String value) {
		if (value == null || value.equals("")) {
			return true;
		} else
			return false;
	}
	
	//根据日期取得星期几
		public static String getWeek(Date date){
			String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if(week_index<0){
				week_index = 0;
			} 
			return weeks[week_index];
		}
	
	/**
	 * 判断文件类型（是否是excel）
	 * 
	 * @param myFileName
	 * @return
	 */
	public static boolean checkFileType(String myFileName) {
		boolean flag = false;
		String postfix = myFileName.substring(myFileName.lastIndexOf("."), myFileName.length());
		if (postfix.equals(".xls") || postfix.equals(".xlsx")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否是图片
	 * 
	 * @param myFileName
	 * @return
	 */
	public static boolean checkPhotos(String myFileName) {
		boolean flag = false;
		String postfix = myFileName.substring(myFileName.lastIndexOf("."), myFileName.length());
		if (postfix.equals(".jpg") || postfix.equals(".png")|| postfix.equals(".jpeg")) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	public static double getMapDoubleValue(String key,Map<String, ?> map) {
		if (map == null) {
			return 0;
		} else
			if(map.get(key) != null){
				return Double.parseDouble(map.get(key).toString());
			}else{
				return 0;
			}
	}
	
	
	/** 
	 * @param addr 
	 * 查询的地址 
	 * @return 
	 * @throws IOException 
	 */ 
	 public static Object[] getCoordinate(String addr) throws IOException { 
	 String lng = null;//经度
	 String lat = null;//纬度
	 String address = null; 
	 try { 
	 address = java.net.URLEncoder.encode(addr, "UTF-8"); 
	 }catch (UnsupportedEncodingException e1) { 
	 e1.printStackTrace(); 
	 } 
	 String key = "f247cdb592eb43ebac6ccd27f796e2d2"; 
	 String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
	 URL myURL = null; 
	 URLConnection httpsConn = null; 
	 try { 
	 myURL = new URL(url); 
	 } catch (MalformedURLException e) { 
	 e.printStackTrace(); 
	 } 
	 InputStreamReader insr = null;
	 BufferedReader br = null;
	 try { 
	 httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
	 if (httpsConn != null) { 
	 insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
	 br = new BufferedReader(insr); 
	 String data = null; 
	 int count = 1;
	 while((data= br.readLine())!=null){ 
	 if(count==5){
	 lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
	 count++;
	 }else if(count==6){
	 lat = data.substring(data.indexOf(":")+1);//纬度
	 count++;
	 }else{
	 count++;
	 }
	 } 
	 } 
	 } catch (IOException e) { 
	 e.printStackTrace(); 
	 } finally {
	 if(insr!=null){
	 insr.close();
	 }
	 if(br!=null){
	 br.close();
	 }
	 }
	 return new Object[]{lng,lat}; 
	 } 
	
	public static String getMapValue(String key,Map<String, ?> map) {
		if (map == null) {
			return "";
		} else
			if(map.get(key) != null){
				return map.get(key).toString();
			}else{
				return "";
			}
	}
	
	
/*    public static void main(String[] args) throws ClientException, InterruptedException {
     	 Object[] o;
		try {
			o = getCoordinate("成都市天府四街");
			 System.out.println(o[0]);//经度
	    	 System.out.println(o[1]);//纬度
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    	
    }*/
	
	/**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    
//    public static String[] transferAddress(String address) {
//    	String[] str = {"-37.833236","144.971316"};
//		try {
//			 str = AddressToLatLng.getLatLongPositions(address);
//			 return str;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return str;
//		}
//    	
//	}
    
    /**
     * @author huangt
     * @return false:小于24小时，true：大于等于24小时
     */
    public static boolean timeDifference(Date startTime,Date endTime){
    	long difference = endTime.getTime() - startTime.getTime(); 

        double result = difference * 1.0 / (1000 * 60 * 60);
        
        if(result>=24){ 
             return true; 
        }else{ 
             return false; 
        } 
    }
    
    
	/*
	    _ooOoo_
	   o8888888o
	   88" . "88
	   (| -_- |)
	   O\  =  /O
	____/`---'\____
	.'  \\|     |//  `.
	/  \\|||  :  |||//  \
	/  _||||| -:- |||||-  \
	|   | \\\  -  /// |   |
	| \_|  ''\---/''  |   |
	\  .-\__  `-`  ___/-. /
	___`. .'  /--.--\  `. . __
	."" '<  `.___\_<|>_/___.'  >'"".
	| | :  `- \`.;`\ _ /`;.`/ - ` : | |
	\  \ `-.   \_ __\ /__ _/   .-` /  /
	======`-.____`-.___\_____/___.-`____.-'======
	    `=---='
	^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	佛祖保佑       永无BUG
	*/
    public static String uploadString(String pathRoot){
    	
    	//-------------windows服务器
//    	String newPath1 = pathRoot.substring(0, pathRoot.lastIndexOf("\\"));
//		String newPath = newPath1.substring(0, newPath1.lastIndexOf("\\"));
//		newPath = newPath + "\\userPost\\";
    	//--------------Linux服务器--------------
    		String newPath1= pathRoot.substring(0, pathRoot.lastIndexOf("/"));
        String newPath=newPath1.substring(0, newPath1.lastIndexOf("/"));
	    newPath=newPath+"/userPost/";
    	return newPath;
    }
    
    public static void main(String[] args) {
    		String prefix = "F"+System.currentTimeMillis() + "";
    		System.out.println(prefix);
	}
}
