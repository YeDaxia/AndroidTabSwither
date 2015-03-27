package com.darcye.tabswitcher;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;

public class ViewUtil {

	/**
	 * Calculate the font height of a specific TextPaint
	 * @param textPaint
	 * @return
	 */
	public static float calculateFontHeight(TextPaint textPaint){
		FontMetrics fontMetrics = textPaint.getFontMetrics();
		return fontMetrics.bottom - fontMetrics.top;
	}
	
	/**
	 * calculate the width of a string with a specific TextPaint
	 * @param paint
	 * @param s
	 * @return
	 */
	public static int calculateStringWidth(Paint paint, String s) {
		int iRet = 0;
		if (s != null && s.length() > 0) {
			int len = s.length();
			float[] widths = new float[len];
			paint.getTextWidths(s, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}
}
