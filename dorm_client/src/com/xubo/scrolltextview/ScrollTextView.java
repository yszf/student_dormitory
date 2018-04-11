package com.xubo.scrolltextview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import cn.edu.xsyu.dorm.R;


public class ScrollTextView extends View implements OnClickListener
{
    /** 鏂囧瓧榛樿棰滆壊 */
    private static final int TEXT_COLOR = Color.BLACK;
    
    /** 鏂囧瓧榛樿澶у皬(鍗曚綅sp) */
    private static final int TEXT_SIZE = 16;
    
    /** 鍗曡妯″紡 */
    private static final boolean SINGLE_LINE = true;
    
    /** 鍗曡鏄剧ず涓嶄笅榛樿甯︾渷鐣ュ彿(鍗曡妯″紡涓嬫墠鏈夋晥) */
    private static final boolean ELLIPSIS = true;
    
    /** 鏂囧瓧婊氬姩鏃堕棿 */
    private static final long SCROLL_TIME = 500;
    
    /** 鏂囧瓧鍒囨崲鏃堕棿 */
    private static final long CUT_TIME = 3000;
    
    /** 鏄惁鍗曡妯″紡 */
    private boolean isSingleLine;
    
    /** 鍗曡鏄剧ず涓嶄笅鏄惁鑷甫鐪佺暐鍙� */
    private boolean isEllipsis;
    
    /** 鏂囧瓧缁樺埗鐢荤瑪 */
    private Paint mPaint;
    
    /** 鏂囧瓧楂樺害 */
    private float mTextHeight;
    
    /** 鏂囧瓧鍋忕Щ璺濈 */
    private float mTextOffsetY;
    
    /** 婊氬姩鏂囧瓧闆嗗悎 */
    private List<String> mContents;
    
    /** 婊氬姩鏂囧瓧鐐瑰嚮鐩戝惉闆嗗悎 */
    private List<OnScrollClickListener> mListeners;
    
    /** 鏂囧瓧淇℃伅闆嗗悎 */
    private Queue<List<TextInfo>> mTextInfos;
    
    /** 鐪佺暐鍙蜂俊鎭泦鍚� */
    private List<TextInfo> mEllipsisTextInfos;
    
    /** 鐪佺暐鍙峰搴� */
    private float mEllipsisTextWidth;
    
    /** 婊氬姩鐨勯珮搴� */
    private int mTop;
    
    /** 褰撳墠鏄剧ず鐨勬枃瀛椾俊鎭� */
    private List<TextInfo> mCurrentTextInfos;
    
    /** 绱㈠紩淇℃伅闆嗗悎 */
    private Map<List<TextInfo>, OnScrollClickListener> mIndexMap;
    
    public ScrollTextView(Context context)
    {
        // TODO Auto-generated constructor stub
        this(context, null);
    }
    
    public ScrollTextView(Context context, AttributeSet attrs)
    {
        // TODO Auto-generated constructor stub
        this(context, attrs, 0);
    }
    
    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollTextLayout, defStyleAttr, 0);
        int textColor = TEXT_COLOR;
        float textSize = sp2px(context, TEXT_SIZE);
        if (typedArray != null)
        {
            textColor = typedArray.getColor(R.styleable.ScrollTextLayout_textColor, textColor);
            textSize = typedArray.getDimension(R.styleable.ScrollTextLayout_textSize, textSize);
            isSingleLine = typedArray.getBoolean(R.styleable.ScrollTextLayout_singleLine, SINGLE_LINE);
            isEllipsis = typedArray.getBoolean(R.styleable.ScrollTextLayout_ellipsis, ELLIPSIS);
        }
        typedArray.recycle();
        
        mPaint = new Paint();
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
        
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
        mTextOffsetY = -fontMetrics.top;
        
        mIndexMap = new HashMap<List<TextInfo>, OnScrollClickListener>();
        mTextInfos = new LinkedList<List<TextInfo>>();
        mEllipsisTextInfos = new ArrayList<TextInfo>();
        
        setOnClickListener(this);
    }
    
    ValueAnimator mValueAnimator;
    
    Handler mHandler = new Handler();
    
    Runnable mRunnable = new Runnable()
    {
        
        @SuppressLint("NewApi")
		@Override
        public void run()
        {
            // TODO Auto-generated method stub
            if (mTextInfos.size() > 1)
            {
                mValueAnimator = ValueAnimator.ofFloat(0.0f, -1.0f);
                mValueAnimator.setDuration(SCROLL_TIME);
                mValueAnimator.addListener(new AnimatorListener()
                {
                    
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void onAnimationRepeat(Animator animation)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        // TODO Auto-generated method stub
                        mTop = 0;
                        mCurrentTextInfos = mTextInfos.poll();
                        mTextInfos.offer(mCurrentTextInfos);
                        startTextScroll();
                    }
                    
                    @Override
                    public void onAnimationCancel(Animator animation)
                    {
                        // TODO Auto-generated method stub
                        mTop = 0;
                    }
                });
                mValueAnimator.addUpdateListener(new AnimatorUpdateListener()
                {
                    
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        // TODO Auto-generated method stub
                        float value = (Float)animation.getAnimatedValue();
                        mTop = (int)(value * (mTextHeight + getPaddingTop() + getPaddingBottom()));
                        invalidate();
                    }
                    
                });
                mValueAnimator.start();
            }
        }
    };
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // TODO Auto-generated method stub
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        
        int textMaxWidth = 0;
        if (mContents != null && mContents.size() > 0)
        {
            textMaxWidth = textTypesetting(measuredWidth - getPaddingLeft() - getPaddingRight(), mContents);
            mCurrentTextInfos = mTextInfos.poll();
            mTextInfos.offer(mCurrentTextInfos);
        }
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)
        {
            measuredWidth = textMaxWidth + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)
        {
            measuredHeight = (int)(mTextHeight + getPaddingBottom() + getPaddingTop());
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
        startTextScroll();
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);
        if (mCurrentTextInfos != null && mCurrentTextInfos.size() > 0)
        {
            for (TextInfo textInfo : mCurrentTextInfos)
            {
                canvas.drawText(textInfo.text,
                    textInfo.x + getPaddingLeft(),
                    textInfo.y + getPaddingTop() + mTop,
                    mPaint);
            }
        }
        if (mTextInfos.size() > 1)
        {
            List<TextInfo> nextTextInfos = mTextInfos.peek();
            if (nextTextInfos != null && nextTextInfos.size() > 0)
            {
                for (TextInfo textInfo : nextTextInfos)
                {
                    canvas.drawText(textInfo.text, textInfo.x + getPaddingLeft(), textInfo.y + getPaddingTop() + mTop
                        + mTextHeight + getPaddingTop() + getPaddingBottom(), mPaint);
                }
            }
        }
    }
    
    /**
     * 鎻忚堪: 璁剧疆婊氬姩鍐呭</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param list 婊氬姩鍐呭闆嗗悎
     */
    public void setTextContent(List<String> list)
    {
        this.mContents = list;
        this.mListeners = null;
        requestLayout();
        invalidate();
    }
    
    /**
     * 鎻忚堪: 璁剧疆婊氬姩鍐呭</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param list 婊氬姩鍐呭闆嗗悎
     * @param listener 婊氬姩鍐呭鐨勭偣鍑荤洃鍚泦鍚�
     */
    public void setTextContent(List<String> list, List<OnScrollClickListener> listener)
    {
        this.mContents = list;
        this.mListeners = listener;
        requestLayout();
        invalidate();
    }
    
    /**
     * 鎻忚堪: 璁剧疆鏂囧瓧棰滆壊</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param color 鏂囧瓧棰滆壊
     */
    public void setTextColor(int color)
    {
        mPaint.setColor(color);
        invalidate();
    }
    
    /**
     * 鎻忚堪: 璁剧疆鏂囧瓧澶у皬</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param size 鏂囧瓧澶у皬
     */
    public void setTextSize(float size)
    {
        mPaint.setTextSize(size);
        FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
        mTextOffsetY = -fontMetrics.top;
        requestLayout();
        invalidate();
    }
    
    /**
     * 鎻忚堪: 鏂囧瓧寮�濮嬭嚜鍔ㄦ粴鍔�,鍒濆鍖栦細鑷姩璋冪敤璇ユ柟娉�(閫傚綋鐨勬椂鏈鸿皟鐢�)</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     */
    @SuppressLint("NewApi")
	public synchronized void startTextScroll()
    {
        if (mValueAnimator != null && mValueAnimator.isRunning())
        {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, CUT_TIME);
    }
    
    /**
     * 鎻忚堪: 鏂囧瓧鏆傚仠婊氬姩(閫傚綋鐨勬椂鏈鸿皟鐢�)</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     */
    @SuppressLint("NewApi")
	public synchronized void stopTextScroll()
    {
        if (mValueAnimator != null && mValueAnimator.isRunning())
        {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        mHandler.removeCallbacks(mRunnable);
    }
    
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        if (mCurrentTextInfos != null && mListeners != null)
        {
            OnScrollClickListener onScrollClickListener = mIndexMap.get(mCurrentTextInfos);
            if (onScrollClickListener != null)
            {
                onScrollClickListener.onClick();
            }
        }
    }
    
    /**
     * 鎻忚堪: 鏂囧瓧鎺掔増</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param maxParentWidth
     * @param list 鎺掔増鐨勬粴鍔ㄦ枃瀛�
     * @return 鎺掔増鏂囧瓧鐨勫搴�
     */
    private int textTypesetting(float maxParentWidth, List<String> list)
    {
        // 娓呯┖鏁版嵁鍙婂垵濮嬪寲鏁版嵁
        mTextInfos.clear();
        mIndexMap.clear();
        mEllipsisTextInfos.clear();
        mEllipsisTextWidth = 0f;
        // 鍒濆鍖栫渷鐣ュ彿
        if (isSingleLine && isEllipsis)
        {
            String ellipsisText = "...";
            for (int i = 0; i < ellipsisText.length(); i++)
            {
                char ch = ellipsisText.charAt(i);
                float[] widths = new float[1];
                String srt = String.valueOf(ch);
                mPaint.getTextWidths(srt, widths);
                TextInfo textInfo = new TextInfo();
                textInfo.text = srt;
                textInfo.x = mEllipsisTextWidth;
                textInfo.y = mTextOffsetY;
                mEllipsisTextInfos.add(textInfo);
                mEllipsisTextWidth += widths[0];
            }
        }
        // 鏂囧瓧鎺掔増
        float maxWidth = 0;
        // 鏂囧瓧鎺掔増鏈�澶у搴�
        float tempMaxWidth = 0f;
        int index = 0;
        for (String text : list)
        {
            if (isNullOrEmpty(text))
            {
                continue;
            }
            // 鎺掔増鏂囧瓧褰撳墠鐨勫搴�
            float textWidth = 0;
            // 鏂囧瓧淇℃伅闆嗗悎
            List<TextInfo> textInfos = new ArrayList<TextInfo>();
            if (isSingleLine)
            {
                // 涓存椂鏂囧瓧淇℃伅闆嗗悎
                List<TextInfo> tempTextInfos = new ArrayList<TextInfo>();
                // 鍗曡鎺掍笉涓�
                boolean isLess = false;
                // 鐪佺暐鍙风殑璧峰浣嶇疆
                float ellipsisStartX = 0;
                for (int j = 0; j < text.length(); j++)
                {
                    char ch = text.charAt(j);
                    float[] widths = new float[1];
                    String srt = String.valueOf(ch);
                    mPaint.getTextWidths(srt, widths);
                    TextInfo textInfo = new TextInfo();
                    textInfo.text = srt;
                    textInfo.x = textWidth;
                    textInfo.y = mTextOffsetY;
                    textWidth += widths[0];
                    if (textWidth <= maxParentWidth - mEllipsisTextWidth) // 褰撴帓鐗堢殑瀹藉害灏忎簬绛変簬鏈�澶у搴﹀幓闄ょ渷鐣ュ彿闀垮害鏃�
                    {
                        textInfos.add(textInfo);
                        ellipsisStartX = textWidth;
                    }
                    else if (textWidth <= maxParentWidth) // 褰撴帓鐗堝搴﹀皬浜庢渶澶у搴︽椂
                    {
                        tempTextInfos.add(textInfo);
                    }
                    else
                    // 鏈�澶у搴︽帓鐗堜笉涓�
                    {
                        isLess = true;
                        break;
                    }
                }
                if (isLess)
                {
                    tempMaxWidth = maxParentWidth;
                    for (TextInfo ellipsisTextInfo : mEllipsisTextInfos)
                    {
                        TextInfo textInfo = new TextInfo();
                        textInfo.text = ellipsisTextInfo.text;
                        textInfo.x = (ellipsisTextInfo.x + ellipsisStartX);
                        textInfo.y = ellipsisTextInfo.y;
                        textInfos.add(textInfo);
                    }
                }
                else
                {
                    tempMaxWidth = textWidth;
                    textInfos.addAll(tempTextInfos);
                }
                if (tempMaxWidth > maxWidth)
                {
                    maxWidth = tempMaxWidth;
                }
                mTextInfos.offer(textInfos);
                if (mListeners != null && mListeners.size() > index)
                {
                    mIndexMap.put(textInfos, mListeners.get(index));
                }
                index++;
            }
            else
            {
                for (int j = 0; j < text.length(); j++)
                {
                    char ch = text.charAt(j);
                    float[] widths = new float[1];
                    String srt = String.valueOf(ch);
                    mPaint.getTextWidths(srt, widths);
                    TextInfo textInfo = new TextInfo();
                    textInfo.text = srt;
                    textInfo.x = textWidth;
                    textInfo.y = mTextOffsetY;
                    textWidth += widths[0];
                    if (textWidth > maxParentWidth) // 褰撴帓鐗堝搴﹀皬浜庢渶澶у搴︽椂
                    {
                        tempMaxWidth = maxParentWidth;
                        mTextInfos.offer(textInfos);
                        if (mListeners != null && mListeners.size() > index)
                        {
                            mIndexMap.put(textInfos, mListeners.get(index));
                        }
                        
                        textInfos = new ArrayList<TextInfo>();
                        textInfo.x = 0;
                        textInfo.y = mTextOffsetY;
                        textWidth = widths[0];
                    }
                    textInfos.add(textInfo);
                }
                if (textWidth > tempMaxWidth)
                {
                    
                    tempMaxWidth = textWidth;
                }
                mTextInfos.offer(textInfos);
                if (tempMaxWidth > maxWidth)
                {
                    maxWidth = tempMaxWidth;
                }
                if (mListeners != null && mListeners.size() > index)
                {
                    mIndexMap.put(textInfos, mListeners.get(index));
                }
                index++;
            }
        }
        return (int)maxWidth;
    }
    
    @SuppressLint("NewApi")
	@Override
    protected void onDetachedFromWindow()
    {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        if (mValueAnimator != null && mValueAnimator.isRunning())
        {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
    }
    
    /**
     * 鎻忚堪: sp杞琾x</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param context 涓婁笅鏂�
     * @param spValue sp鍊�
     * @return 杞崲鍚庡搴旂殑px鍊�
     */
    private float sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale;
    }
    
    /**
     * 鎻忚堪: 鍒ゆ柇瀛楃涓叉槸鍚︿负绌�</br>
     * 寮�鍙戜汉鍛橈細寰愭尝</br>
     * 鍒涘缓鏃堕棿锛�2016骞�6鏈�15鏃�</br>
     * @param text
     * @return true涓虹┖,fale涓嶄负绌�
     */
    private boolean isNullOrEmpty(String text)
    {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0 || "null".equals(text.trim())
            || "empty".equals(text))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 绫诲悕: TextInfo</br>
     * 鍖呭悕锛歝om.xubo.scrolltext.view </br>
     * 鎻忚堪: 缁樺埗鏂囧瓧淇℃伅</br>
     * 鍙戝竷鐗堟湰鍙凤細</br>
     * 寮�鍙戜汉鍛橈細 寰愭尝</br>
     * 鍒涘缓鏃堕棿锛� 2016骞�6鏈�15鏃�
     */
    public class TextInfo
    {
        /** x鍧愭爣 */
        public float x;
        
        /** y鍧愭爣 */
        public float y;
        
        /** 鍐呭 */
        public String text;
    }
    
    /**
     * 绫诲悕: OnScrollClickListener</br>
     * 鍖呭悕锛歝om.xubo.scrolltext.view </br>
     * 鎻忚堪: 婊氬姩鍐呭鐐瑰嚮鐩戝惉浜嬩欢</br>
     * 鍙戝竷鐗堟湰鍙凤細</br>
     * 寮�鍙戜汉鍛橈細 寰愭尝</br>
     * 鍒涘缓鏃堕棿锛� 2016骞�6鏈�15鏃�
     */
    public interface OnScrollClickListener
    {
        public void onClick();
    }
}
