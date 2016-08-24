package cn.andaction.foldviewlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Geek_Soledad(wuhaiyang@danlu.com)
 * Date: 2016-08-23
 * Time: 14:24
 * Description: .....
 */

public class RichTextView extends TextView {
    /**
     * 超过三条 就折叠处理
     */
    private static final int THRESHOLD_PRO = 3;

    // 折叠/展开
    /**
     * 折叠时的高度
     */
    private int mShortHeight = 0;
    /**
     * 展开时的高度
     */
    private int mLongHeight = 0;

    // spanable
    private int mHightLightColor;
    private int mHightLightSize;
    private int mNormalSize;
    private int mNormalColor;
    private int mRowSpace;

    /**
     * 当前是否处于折叠状态
     */
    private boolean isFold = true;

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.aa_rich_textview_attrs);
        mHightLightColor = typedArray.getColor(R.styleable.aa_rich_textview_attrs_text_hightlight_color, Color.RED);
        mHightLightSize = typedArray.getDimensionPixelSize(R.styleable.aa_rich_textview_attrs_text_hightlight_size, 13);
        mNormalSize = typedArray.getDimensionPixelSize(R.styleable.aa_rich_textview_attrs_text_normal_size, 12);
        mNormalColor = typedArray.getColor(R.styleable.aa_rich_textview_attrs_text_normal_color,Color.BLACK);
        mRowSpace = typedArray.getDimensionPixelOffset(R.styleable.aa_rich_textview_attrs_text_row_space, 5);
        setAttrs();
    }
    private void setAttrs() {
        setTextColor(mNormalColor);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mNormalSize);
        setLineSpacing(mRowSpace, 1);
    }
    public void bindData(List<String> datas,boolean isBeforeFold){
        if (null == datas || datas.isEmpty()) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        //渲染不同的spanable
        showText(datas);
        this.isFold = isBeforeFold;
        mLongHeight = calculateHeightByStaticLayout(datas);
        if (datas.size() > THRESHOLD_PRO) {
            //计算短的高度
            List tmpDatas = new ArrayList();
            for (int i = 0 ; i < THRESHOLD_PRO;i ++) {
                tmpDatas.add(datas.get(i));
            }
            mShortHeight = calculateHeightByStaticLayout(tmpDatas);
            if (isFold) {
                setHeight(mShortHeight);
            } else {
                setHeight(mLongHeight);
            }
        } else {
            setHeight(mLongHeight);
        }
    }
    public void foldAction() {
        int startHeight = 0;
        int targetHeight = 0;
        if (isFold) {
            startHeight = mShortHeight;
            targetHeight = mLongHeight;
        } else {
            startHeight = mLongHeight;
            targetHeight = mShortHeight;
        }
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                setHeight(animatedValue);
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent viewParent = getParent();
                //降低耦合度
                if (null != viewParent && viewParent instanceof ViewGroup) {
                    ViewGroup parent = (ViewGroup) viewParent;
                    ImageView iv_fold = (ImageView) parent.findViewById(R.id.iv_fold);
                    if (null != iv_fold) {
                        iv_fold.setImageResource(isFold ? R.drawable.icon_fold_down : R.drawable.dl_icon_fold_up);
                    }
                }
                isFold = !isFold;
                if (null != mOnFoldListener) {
                    mOnFoldListener.onFold(isFold);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(300);
        //执行动画之前先清除所有相关动画
        clearAnimation();
        animator.start();
    }

    /**
     * 巧妙利用StaticLayout计算文本当前高度
     * @param datas
     * @return
     */
    private int calculateHeightByStaticLayout(List<String> datas) {
        //创建一个临时变量（clone一个和当前一致的文本框） 准确计算高度
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            String promotionInfo = (String) datas.get(i);
            sb.append("●  " + datas.get(i));
            if (i != datas.size() - 1) {
                sb.append("\n");
            }
        }
        int width = getScreenWidth() - getMarginPlusPadding();
        TextView tmpTextView = new TextView(getContext());
        tmpTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNormalSize);
        SpannableString spannableString = hightLightAction(sb.toString());
        tmpTextView.setText(spannableString);
        tmpTextView.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        tmpTextView.setLineSpacing(mRowSpace, 1);
        TextPaint textPaint = tmpTextView.getPaint();
        StaticLayout staticLayout = new StaticLayout(spannableString, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, mRowSpace, false);
        return staticLayout.getHeight();
    }
    private int getMarginPlusPadding() {
        int total = getPaddingLeft() + getPaddingRight();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (null != layoutParams && layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            total += params.leftMargin + params.rightMargin;
        }
        return total;
    }
    /**
     * 渲染文本
     * @param datas
     */
    private void showText(List<String> datas) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            sb.append("●  " + datas.get(i));
            if (i != datas.size() - 1) {
                sb.append("\n");
            }
        }
        setText(hightLightAction(sb.toString()));
    }
    /**
     * 利用正则过滤
     * @param string
     * @return
     */
    public SpannableString hightLightAction(String string) {
        String res = string.replace("[", "").replace("]", "");
        SpannableString s = new SpannableString(string);
        SpannableString s2 = new SpannableString(res);

        String rex = "\\[([^\\[\\]]*)\\]";

        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(s);

        int i = 0;
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            int dx = end - start - 2;
            if (i != 0) {
                start = start - i * 2;
            }
            s2.setSpan(new ForegroundColorSpan(mHightLightColor), start, start + dx, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            s2.setSpan(new AbsoluteSizeSpan(mHightLightSize), start, start + dx, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            i++;
        }
        return s2;
    }
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    private OnFoldListener mOnFoldListener;
    public void setOnFoldListener(OnFoldListener onFoldListener) {
        mOnFoldListener = onFoldListener;
    }
    public interface OnFoldListener {
        void onFold(boolean isFold);
    }
}
