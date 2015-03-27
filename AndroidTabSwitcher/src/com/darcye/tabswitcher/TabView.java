package com.darcye.tabswitcher;

import com.darcye.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * 
 * <p>Tab with drawable and text elements, you can use both or one of them. Also , there has simple tip for the Tap , you can decide the Style all by your self..
 *  <p> hope you like it :-)
 * @author Darcy
 * @site www.darcye.com
 */
public class TabView  extends RadioButton{

	private Drawable buttonDrawable;

	private int mDrawableWidth;
	private int mDrawableHeight;
	
	private int mTipBackgroundColor;
	private int mTipTextColor;
	private int mTipTextSize;
	
	private int mTipDotRadius;
	private int mTextDotRadius;
	
	private TextPaint mTipTextPaint;
	private Paint mTipBgPaint;
	
	private boolean mHasTip;
	private String mTipText;
	
	private int mDrawableLableGap;
	
	private RectF mOvalRectF = new RectF();
	
	private int mTipTextDotTopMargin;
	private int mTipTextDotRightMargin;
	private int mTipDotTopMargin;
	private int mTipDotRightMargin;
	private int mDotOvalPadding;
	
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		Resources res = context.getResources();
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CompoundButton, 0, 0);
		buttonDrawable = a.getDrawable(R.styleable.CompoundButton_android_button);
		mDrawableWidth = a.getDimensionPixelSize(R.styleable.CompoundButton_drawableWidth, 0);
		mDrawableHeight = a.getDimensionPixelSize(R.styleable.CompoundButton_drawableHeight, 0);
		a.recycle();
		
		TypedArray iconTypedArray = context.obtainStyledAttributes(attrs,R.styleable.TabView, 0, 0);
		mTipBackgroundColor = iconTypedArray.getColor(R.styleable.TabView_TipBackgroundColor,res.getColor(R.color.default_tab_tip_dot_bg));
		mTipTextColor = iconTypedArray.getColor(R.styleable.TabView_TipTextColor,res.getColor(R.color.default_tab_tip_text_color));
		mTipTextSize = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipTextSize, res.getDimensionPixelSize(R.dimen.tab_lable_text_size));
		mTipDotRadius = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipDotRadius, res.getDimensionPixelSize(R.dimen.tab_tip_red_dot_radius));
		mTextDotRadius = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TextDotRadius, res.getDimensionPixelSize(R.dimen.tab_tip_text_red_dot_radius));
		mDrawableLableGap = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_DrawableLableGap, res.getDimensionPixelSize(R.dimen.tab_drawable_lable_gap));
		mTipTextDotRightMargin = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipTextDotRightMargin, res.getDimensionPixelSize(R.dimen.tab_tip_text_dot_right_margit));
		mTipTextDotTopMargin = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipTextDotTopMargin, res.getDimensionPixelSize(R.dimen.tab_tip_text_dot_right_margit));
		mTipDotRightMargin = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipDotRightMargin, res.getDimensionPixelSize(R.dimen.tab_tip_dot_right_margit));
		mTipDotTopMargin = iconTypedArray.getDimensionPixelSize(R.styleable.TabView_TipDotTopMargin, res.getDimensionPixelSize(R.dimen.tab_tip_dot_right_margit));
		iconTypedArray.recycle();
		
		setButtonDrawable(R.drawable.empty_drawable);
		
		mDotOvalPadding = res.getDimensionPixelSize(R.dimen.tab_tip_dot_oval_padding);
		
		mTipBgPaint = new Paint();
		mTipBgPaint.setColor(mTipBackgroundColor);
		mTipBgPaint.setAntiAlias(true);
		
		mTipTextPaint = new TextPaint();
		mTipTextPaint.setColor(mTipTextColor);
		mTipTextPaint.setTextSize(mTipTextSize);
		mTipTextPaint.setAntiAlias(true);
	}

	/**
	 * when there exists tip text and dot tip at the same time, text will show to front
	 * @see {@link #setTipText(String tipText)}
	 * @param hasTip
	 */
	public void setHasTip(boolean hasTip) {
		if(mHasTip == hasTip)
			return;
		
		mHasTip = hasTip;
		invalidate();
	}

	/**
	 * when there exists tip text and dot tip at the same time, text will show to front
	 * @see {@link #setHasTip(boolean hasTip)} 
	 * @param tipText
	 */
	public void setTipText(String tipText) {
		if(mTipText == tipText || (mTipText != null && mTipText.equals(tipText)))
			return;
		
		mTipText = tipText;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		final int width = getWidth();
		final int height = getHeight();
		final int[] drawableState = getDrawableState();
		
		//draw backgroud
		Drawable backgroundDrawable = getBackground();
		if(backgroundDrawable != null){
			backgroundDrawable.setState(drawableState);
			backgroundDrawable.setBounds(0, 0, width, height);
			backgroundDrawable.draw(canvas);
		}
		
		final CharSequence lableText = getText();
		
		boolean noLable = false;
		if(lableText == null || lableText.length() == 0){
			noLable = true;
		}
		
		final TextPaint lablePaint = getPaint();
		final float lableTextHeight = ViewUtil.calculateFontHeight(lablePaint);
		int buttonDrawableBottom = 0;
		
		//draw drawable
		if (buttonDrawable != null) {
			buttonDrawable.setState(drawableState);
			final int drawableHeight = mDrawableWidth == 0? buttonDrawable.getIntrinsicHeight() : mDrawableWidth;
			int drawableY = 0;

			if(noLable){
				drawableY = (height - drawableHeight) / 2;
			}else{
				drawableY = (int) ((height - drawableHeight - lableTextHeight - mDrawableLableGap) / 2);
			}

			int drawableWidth = mDrawableHeight == 0 ? buttonDrawable.getIntrinsicWidth() : mDrawableHeight;
			int drawableLeft = (width - drawableWidth) / 2;
			buttonDrawableBottom = drawableY+ drawableHeight;
			buttonDrawable.setBounds(drawableLeft, drawableY, drawableLeft + drawableWidth, buttonDrawableBottom);
			buttonDrawable.draw(canvas);
		}
		
		//draw lable text
		if(!noLable){
			ColorStateList lableCSList = getTextColors();
			int curColor = lableCSList.getColorForState(drawableState, lableCSList.getDefaultColor());
			lablePaint.setColor(curColor);
			String drawLableText = lableText.toString();
			int lableTextWidth = ViewUtil.calculateStringWidth(lablePaint, drawLableText);
			int lableX =  ( width - lableTextWidth ) / 2;
			int lableY = (int) (buttonDrawable != null ?  buttonDrawableBottom + mDrawableLableGap - lablePaint.getFontMetrics().top : ( height - lableTextHeight ) / 2 - lablePaint.getFontMetrics().top) ;;
			canvas.drawText(drawLableText, lableX, lableY, lablePaint);
		}
		
		//draw tip
		if(!TextUtils.isEmpty(mTipText)){
			final float tipTextWidth = mTipTextPaint.measureText(mTipText);
			final int rightMargin  = mTipTextDotRightMargin;
			int cx,cy ; 
			int textX, textY;
			
			final int dotDiameter = mTextDotRadius * 2;
			final FontMetricsInt fontMetrics = mTipTextPaint.getFontMetricsInt();
			final int fontHeight = fontMetrics.bottom - fontMetrics.top;
			
			textY = (int) (rightMargin + (dotDiameter - fontHeight) * 0.5 - fontMetrics.top); 
			
			if(tipTextWidth < dotDiameter){
				
				cx = width - mTextDotRadius -  rightMargin;
				cy = mTipTextDotTopMargin + mTextDotRadius;
				canvas.drawCircle(cx, cy, mTextDotRadius, mTipBgPaint);
				
				textX = (int) (width - (dotDiameter - tipTextWidth ) * 0.5 - rightMargin - tipTextWidth); 
				canvas.drawText(mTipText, textX, textY, mTipTextPaint);
			}else{
				
				final int ovalPadding = mDotOvalPadding;
				
				int ovalWidth = (int) (tipTextWidth + 2 * ovalPadding);
				
				final RectF ovalRect  = mOvalRectF;
				
				ovalRect.right = width - rightMargin;
				ovalRect.left = ovalRect.right -  ovalWidth;
				ovalRect.top = mTipTextDotTopMargin;
				ovalRect.bottom = ovalRect.top + dotDiameter;
				
				canvas.drawOval(ovalRect, mTipBgPaint);
				
				textX = (int) (width - (ovalRect.right - ovalRect.left - tipTextWidth ) * 0.5 - rightMargin - tipTextWidth); 
				canvas.drawText(mTipText, textX, textY, mTipTextPaint);
			}
		}else if(mHasTip){
			int cx,cy ; 
			final int rightMargin  = mTipDotRightMargin;
			cy = mTipDotTopMargin + mTipDotRadius;
			cx = width - rightMargin - mTipDotRadius;
			canvas.drawCircle(cx, cy, mTipDotRadius, mTipBgPaint);
		}
	}
}
