package com.zq.android.module.smarttab.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zq.android.R;
import com.zq.android.base.BaseActivity;
import com.zq.android.injector.component.AppComponent;
import com.zq.android.module.smarttab.fragment.MeFragment;
import com.zq.android.module.smarttab.fragment.RecommendedFragment;
import com.zq.android.module.smarttab.fragment.TopicsFragment;
import com.zq.android.module.smarttab.fragment.WikiFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;

public class SmartTabActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagerTab;

    @Override
    public int bindLayout() {
        return R.layout.activity_smart_tab;
    }

    @Override
    public void getBundleExtras(Bundle parms) {

    }

    @Override
    public void initViewsAndEvents(View view) {
        setupTabView();
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void injectModule(AppComponent appComponent) {

    }

    protected void setupTabView() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        final int[] tabIcons = {R.drawable.ic_recommended, R.drawable.ic_topics, R.drawable.ic_wiki, R.drawable.ic_me};
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add(R.string.recommended, RecommendedFragment.class)
                .add(R.string.topics, TopicsFragment.class)
                .add(R.string.wiki, WikiFragment.class)
                .add(R.string.me, MeFragment.class)
                .create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages);

        viewPager.setOffscreenPageLimit(pages.size());
        viewPager.setAdapter(adapter);
        viewpagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter pagerAdapter) {
                View view = inflater.inflate(R.layout.custom_tab_icon, container, false);
                ImageView iconView = (ImageView) view.findViewById(R.id.iv_icon);
                iconView.setBackgroundResource(tabIcons[position % tabIcons.length]);
                return view;
            }
        });
        viewpagerTab.setViewPager(viewPager);
    }
}
