# FoldView#
Expandable TextView With Smooth Transition Animation

## Preview ##
<img src="http://oce5ahwfo.bkt.clouddn.com/foldview.gif" />
## How do Imp ？ ##
* In Java
```java
        //create tmp textview to calculate TextView Height
        StringBuilder sb = new StringBuilder();
        //...
        int width = getScreenWidth() - getMarginPlusPadding();
        TextView tmpTextView = new TextView(getContext());
        tmpTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNormalSize);
        SpannableString spannableString = hightLightAction(sb.toString());
        tmpTextView.setText(spannableString);
        tmpTextView.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        tmpTextView.setLineSpacing(mRowSpace, 1);
        TextPaint textPaint = tmpTextView.getPaint();
        StaticLayout staticLayout = new StaticLayout(spannableString,textPaint,width,Layout.Alignment.ALIGN_NORMAL, 1, mRowSpace, false);
        return staticLayout.getHeight();
        // so StaticLayout can getHeight or getLineCount ....
```

## How Use ?
   I Just Provide idea , Clone or download my project








