package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.BlogFragmentPagerAdapter;

public class BlogFragment extends Fragment implements OnClickListener {

	private TextView homeTextView;
	private TextView recommendTextView;
	private TextView readdingTextView;

	private LinearLayout linearLayoutBlogTitleHome;
	private LinearLayout linearLayoutBlogTitleRecommend;
	private LinearLayout linearLayoutBlogTitleReading;

	private ImageView guildLineView;

	private ViewPager viewPager;

	private int currentPageIndex;
	private int screenWidth;

	private BlogFragmentPagerAdapter adapter;
	private OnPageChangeListener blogPageChangeListener;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	public BlogFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog, container,
				false);

		initComponent(rootView);
		initGuildLine(rootView);

		addListener();

		adapter = new BlogFragmentPagerAdapter(getChildFragmentManager(),
				fragmentList);
		viewPager.setAdapter(adapter);

		return rootView;
	}

	private void initComponent(View view) {

		linearLayoutBlogTitleHome = (LinearLayout) view
				.findViewById(R.id.linearLayoutBlogTitleHome);
		linearLayoutBlogTitleRecommend = (LinearLayout) view
				.findViewById(R.id.linearLayoutBlogTitleRecommend);
		linearLayoutBlogTitleReading = (LinearLayout) view
				.findViewById(R.id.linearLayoutBlogTitleReading);

		homeTextView = (TextView) view.findViewById(R.id.textViewBlogHome);
		recommendTextView = (TextView) view
				.findViewById(R.id.textViewBlogRecommend);
		readdingTextView = (TextView) view
				.findViewById(R.id.textViewBlogReading);

		guildLineView = (ImageView) view.findViewById(R.id.imageViewGuildLine);

		viewPager = (ViewPager) view.findViewById(R.id.viewPagerBlog);

		BlogHomeFragment homeFragment = new BlogHomeFragment();
		BlogRecommendFragment recommendFragment = new BlogRecommendFragment();
		BlogReadingFragment readdingFragment = new BlogReadingFragment();

		fragmentList.add(homeFragment);
		fragmentList.add(recommendFragment);
		fragmentList.add(readdingFragment);
	}

	private void initGuildLine(View view) {
		DisplayMetrics outMetrics = new DisplayMetrics();

		this.getActivity().getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) guildLineView
				.getLayoutParams();
		layoutParams.width = screenWidth / 3;
		guildLineView.setLayoutParams(layoutParams);
	}

	private void addListener() {
		linearLayoutBlogTitleHome.setOnClickListener(this);
		linearLayoutBlogTitleRecommend.setOnClickListener(this);
		linearLayoutBlogTitleReading.setOnClickListener(this);

		blogPageChangeListener = new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				Log.i("onPageSelected", "position" + position);

				resetTextView();
				switch (position) {
				case 0:
					homeTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				case 1:
					recommendTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				case 2:
					readdingTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				}

				currentPageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				if (currentPageIndex == 0 && position == 0) {// 0->1
					LayoutParams layoutParams = (LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (positionOffset
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);
				} else if (currentPageIndex == 1 && position == 0) { // 1->0
					LayoutParams layoutParams = (LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (-(1 - positionOffset)
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);

				} else if (currentPageIndex == 1 && position == 1) { // 1->2
					LayoutParams layoutParams = (LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (positionOffset
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);
				} else if (currentPageIndex == 2 && position == 1) {// 2->1
					LinearLayout.LayoutParams layoutParams = (LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (-(1 - positionOffset)
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);

				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		};
		viewPager.setOnPageChangeListener(blogPageChangeListener);
	}

	private void resetTextView() {
		homeTextView.setTextColor(Color.BLACK);
		recommendTextView.setTextColor(Color.BLACK);
		readdingTextView.setTextColor(Color.BLACK);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayoutBlogTitleHome:
			viewPager.setCurrentItem(0);
			break;
		case R.id.linearLayoutBlogTitleRecommend:
			viewPager.setCurrentItem(1);
			break;
		case R.id.linearLayoutBlogTitleReading:
			viewPager.setCurrentItem(2);
			break;
		}
	}
}
