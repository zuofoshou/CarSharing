package com.example.admin.carsharing.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.example.admin.carsharing.R;
import com.example.admin.carsharing.ui.firstpage.FirstPageFgt;
import com.example.admin.carsharing.ui.mine.MineFgt;
import com.example.admin.carsharing.ui.order.OrderFgt;
import com.facebook.drawee.backends.pipeline.Fresco;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseAty {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.rg_main)
    RadioGroup mRgMain;

    private List<BaseFgt> mBaseFgts;

    @Override
    protected void requestData() {

    }


    @Override
    protected void initData() {
        mBaseFgts = new ArrayList<>();
        mBaseFgts.add(new FirstPageFgt());
        mBaseFgts.add(new OrderFgt());
        mBaseFgts.add(new MineFgt());
        MainFgtAdapter mainFgtAdapter = new MainFgtAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFgtAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_homepage:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_order:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_mine:
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    class MainFgtAdapter extends FragmentPagerAdapter{

        public MainFgtAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mBaseFgts.get(position);
        }

        @Override
        public int getCount() {
            return mBaseFgts.size();
        }
    }
}
