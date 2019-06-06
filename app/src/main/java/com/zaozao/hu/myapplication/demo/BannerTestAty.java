package com.zaozao.hu.myapplication.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zaozao.hu.myapplication.R;
import com.zaozao.hu.myapplication.utils.LogUtils;
import com.zaozao.hu.myapplication.widget.banner.BannerAdapter;
import com.zaozao.hu.myapplication.widget.banner.BannerView;
import com.zaozao.hu.myapplication.widget.banner.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerTestAty extends AppCompatActivity {


    private BannerView mBannerView;
    private String TAG = "BannerTestAty";
    public static List<String> imgUrls = new ArrayList<>();

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
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
