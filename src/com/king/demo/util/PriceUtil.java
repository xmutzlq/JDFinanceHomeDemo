package com.king.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.king.demo.R;

import android.content.Context;
import android.text.TextUtils;

/** 价格工具类
 * @author zlq
 * @date 2016-2-13
 */
public final class PriceUtil {
	
	public static String formatNewPrice(String price) {
		try {
			Integer.valueOf(price);
			return price + ".00";
		} catch (Exception e) {
			return price;
		}
	}
	
	/** 格式或价格；price(单位分)->0.00元
	 * @param sb
	 * @return
	 */
	public static String formatPrice(StringBuilder sb) {
		return formatPrice(sb.toString());
	}
	
	/** 格式或价格；price(单位分)->0.00元
	 * @param price
	 * @return
	 */
	public static String formatPrice(String price) {
		try {
			int temp = Integer.valueOf(price);
			return formatPrice(temp);
		} catch (Exception e) {
			return price;
		}
	}

	/** 格式或价格；price(单位分)->0.00元
	 * @param price
	 * @return
	 */
	public static String formatPrice(int price) {
		int temp = price;
		int tc = temp / 100;
		int tb = temp % 100;
		StringBuilder s = new StringBuilder();
		if (tc > 0) {
			s.append(tc);
		} else {
			s.append("0");
		}
		if (tb > 0) {
			s.append(".");
			if (tb < 10) {
				s.append("0");
			}
			s.append(tb);
		} else {
			s.append(".00");
		}
		return s.toString();
	}
	
	/** 判断价格是否免费
	 * @param price
	 * @return
	 */
	public static boolean isFree(String price){
		if("0".equals(price)){
			return true;
		}
		return false;
	}

	public static double getDecimal(double num) {
		if (Double.isNaN(num)) {
			return 0;
		}
		BigDecimal bd = new BigDecimal(num);
		num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	
	/**
	 * 获取分隔符的价格
	 * @param context
	 * @param amount
	 * @return
	 */
	public static String getFormatPrice(Context context, double amount) {
		return getFormatPrice(context, R.string.default_format, amount);
	}
	
	public static String getFormatPrice_(Context context, double amount) {
		return getFormatPrice(context, R.string.str_default_format, amount);
	}
	
	public static String getFormatPrice(Context context, int formator, double amount) {
		if(context == null) return "0";
		String format = context.getString(formator);
		DecimalFormat decimalFormat = new DecimalFormat(format);
		DecimalFormatSymbols decimalFormatSymbol = DecimalFormatSymbols.getInstance();
		char decimalSeparator = context.getString(R.string.str_decimal_separator).charAt(0);
		decimalFormatSymbol.setDecimalSeparator(decimalSeparator);
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
		return decimalFormat.format(amount);
	}
	
	/**
	 * 万元或元
	 * @param context
	 * @param price
	 * @return
	 */
	public static String getFormatTenThousand(Context context, String price) {
		if(TextUtils.isEmpty(price)) return "0万元";
		double tmpPrice = Double.valueOf(price);
		if (tmpPrice>0){
			String removeZeroStr = removeZero(String.valueOf(tmpPrice / 10000));
			return context.getString(R.string.str_money_ten_thousand_value, removeZeroStr);
		}else{
			return context.getString(R.string.str_money_ten_thousand_value, "0");
		}

	}
	public static String removeZero(String price){
		String[] split = price.split("\\.");
		if (split.length>=2){
			String s1 = split[1];
			if (s1.length()==1&&s1.equals("0")){
				return split[0];
			}
		}
		return price;
	}

//	public static String formatMoney(double money){
//		BigDecimal b = new BigDecimal(money);
//		return  b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//	}
	public static String formatMoney(double money){
		if (money>0){
			return removeZero(String.valueOf(money / 10000));
		}else{
			return "0";
		}
	}
}
