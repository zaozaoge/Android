package com.zaozao.hu.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {
    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //详解为什么要这么设置
        //ScrollView的测量模式为UnSpecified
        //而在ListView的onMeasure()方法中，判断了当测量模式为UnSpecified时候，返回的高度只有一个Item的高度
        //所以我们要将测量模式改为AtMost

        //那么为什么要设置为Integer.MAX_VALUE >> 2

        //首先在super.onMeasure(widthMeasureSpec, heightMeasureSpec)方法中
        //当测量模式为AtMost时会调用measureHeightOfChildren（）方法
        //measureHeightOfChildren方法中有个判断
        // if (returnedHeight >= maxHeight) {
        //                // We went over, figure out which height to return.  If returnedHeight > maxHeight,
        //                // then the i'th position did not fit completely.
        //                return (disallowPartialChildPosition >= 0) // Disallowing is enabled (> -1)
        //                            && (i > disallowPartialChildPosition) // We've past the min pos
        //                            && (prevHeightWithoutPartialChild > 0) // We have a prev height
        //                            && (returnedHeight != maxHeight) // i'th child did not fit completely
        //                        ? prevHeightWithoutPartialChild
        //                        : maxHeight;
        //            }
        //如果高度大于maxHeight，也就是我们传入的size的时候，返回的是另外一个值
        //因此我们不能让程序进入到这个判断中去，所以设置为Integer.MAX_VALUE


        //那么为什么要右移两位？
        //heightMeasureSpec 是一个32位的值，包含两个信息，前两位用来表示模式，后面30位才是代表的宽高值
        //Integer.MAX_VALUE

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
