package com.zaozao.hu.myapplication.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zaozao.hu.myapplication.R;
import com.zaozao.hu.myapplication.utils.LogUtils;
import com.zaozao.hu.myapplication.utils.StatusBarUtil;
import com.zaozao.hu.myapplication.widget.HealthStandardView;
import com.zaozao.hu.myapplication.widget.banner.BannerAdapter;
import com.zaozao.hu.myapplication.widget.banner.BannerView;
import com.zaozao.hu.myapplication.widget.banner.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerTestAty extends AppCompatActivity {


    private BannerView mBannerView;
    private String TAG = "BannerTestAty";
    public static List<String> imgUrls = new ArrayList<>();
    private HealthStandardView mHealthView0, mHealthView1, mHealthView2;

    static {
        imgUrls.add("http://img0.imgtn.bdimg.com/it/u=2666559461,3483798527&fm=26&gp=0.jpg");
        imgUrls.add("http://img1.imgtn.bdimg.com/it/u=186708541,3500736570&fm=26&gp=0.jpg");
        imgUrls.add("http://img5.imgtn.bdimg.com/it/u=4262181683,3577565818&fm=15&gp=0.jpg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_test_aty);
        mBannerView = findViewById(R.id.banner_vp);
        mHealthView0 = findViewById(R.id.healthView0);
        mHealthView1 = findViewById(R.id.healthView);
        mHealthView2 = findViewById(R.id.healthView2);
        //StatusBarUtil.setActivityTranslucent(this);
        StatusBarUtil.setStatusBarColor(this, Color.RED);
        mBannerView.post(new Runnable() {
            @Override
            public void run() {
                mBannerView.setAdapter(new BannerAdapter() {

                    @Override
                    protected View getView(int position, View convertView) {
                        ImageView bannerView;
                        if (convertView == null) {
                            bannerView = new ImageView(BannerTestAty.this);
                        } else {
                            bannerView = (ImageView) convertView;
                        }
                        bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(BannerTestAty.this).load(imgUrls.get(position))
                                .apply(RequestOptions.fitCenterTransform())
                                .into(bannerView);
                        return bannerView;
                    }

                    @Override
                    protected int getCount() {
                        return imgUrls.size();
                    }
                });
                mBannerView.startRoll();
            }
        });
        mBannerView.setBannerItemClickListener(new BannerViewPager.BannerItemClickListener() {
            @Override
            public void onClick(int position) {
                LogUtils.showToast(BannerTestAty.this, "position-->" + position);
            }
        });

        mHealthView0.setBottomTexts(new String[]{"一万", "十万", "百万"});
        mHealthView0.setScaleTexts(new String[]{"穷逼", "有钱"});
        mHealthView0.setProgressbarColors(new int[]{Color.GREEN, Color.YELLOW, Color.RED});
        mHealthView0.setCurProgress(20);
        mHealthView0.show();

        mHealthView1.setBottomTexts(new String[]{"偏低", "理想", "超重", "严重超标"});
        mHealthView1.setScaleTexts(new String[]{"105.0|b", "118.0|b", "144.0|b"});
        mHealthView1.setCurProgress(60);
        mHealthView1.setProgressbarColors(new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE});
        mHealthView1.show();

        mHealthView2.setBottomTexts(new String[]{"苹果", "葡萄", "李子", "荔枝", "香蕉"});
        mHealthView2.setScaleTexts(new String[]{"吃", "吃", "吃", "吃"});
        mHealthView2.setProgressbarColors(new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.GRAY, Color.BLUE});
        mHealthView2.setCurProgress(70);
        mHealthView2.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
