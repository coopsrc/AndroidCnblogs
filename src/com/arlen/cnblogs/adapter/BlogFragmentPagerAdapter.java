package com.arlen.cnblogs.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BlogFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentList;

	public BlogFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public BlogFragmentPagerAdapter(FragmentManager fm,
			List<Fragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

}
