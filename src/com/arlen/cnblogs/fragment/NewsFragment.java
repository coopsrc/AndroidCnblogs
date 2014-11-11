package com.arlen.cnblogs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlen.cnblogs.R;
import com.arlen.cnblogs.adapter.NewsFragmentPagerAdapter;

public class NewsFragment extends Fragment implements OnClickListener {

	private LinearLayout linearLayoutNewsTitleLatest;
	private LinearLayout linearLayoutNewsTitleHot;
	private LinearLayout linearLayoutNewsTitleRecommend;

	private TextView latestTextView;
	private TextView hotTextView;
	private TextView recommendTextView;

	private ImageView guildLineView;
	private ViewPager viewPager;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private int currentPageIndex;
	private int screenWidth;

	private OnPageChangeListener newsPageChangeListener;

	public NewsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);

		initComponent(rootView);
		initGuildLine(rootView);

		addListener();

		viewPager.setAdapter(new NewsFragmentPagerAdapter(
				getChildFragmentManager(), fragmentList));

		return rootView;
	}

	private void addListener() {
		newsPageChangeListener = new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Log.i("onPageSelected", "position" + position);

				resetTextView();
				switch (position) {
				case 0:
					latestTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				case 1:
					hotTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				case 2:
					recommendTextView.setTextColor(getResources().getColor(
							R.color.title_text_color));
					break;
				}

				currentPageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				if (currentPageIndex == 0 && position == 0) {// 0->1
					LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (positionOffset
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);
				} else if (currentPageIndex == 1 && position == 0) { // 1->0
					LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (-(1 - positionOffset)
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);

				} else if (currentPageIndex == 1 && position == 1) { // 1->2
					LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) guildLineView
							.getLayoutParams();
					layoutParams.leftMargin = (int) (positionOffset
							* (screenWidth * 1.0 / 3) + currentPageIndex
							* (screenWidth / 3));
					guildLineView.setLayoutParams(layoutParams);
				} else if (currentPageIndex == 2 && position == 1) {// 2->1
					LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) guildLineView
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
		viewPager.setOnPageChangeListener(newsPageChangeListener);
	}

	private void resetTextView() {
		Log.i("resetTextView", "resetTextView");
		latestTextView.setTextColor(Color.BLACK);
		hotTextView.setTextColor(Color.BLACK);
		recommendTextView.setTextColor(Color.BLACK);
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

	private void initComponent(View view) {

		linearLayoutNewsTitleLatest = (LinearLayout) view
				.findViewById(R.id.linearLayoutNewsTitleLatest);
		linearLayoutNewsTitleHot = (LinearLayout) view
				.findViewById(R.id.linearLayoutNewsTitleHot);
		linearLayoutNewsTitleRecommend = (LinearLayout) view
				.findViewById(R.id.linearLayoutNewsTitleRecommend);

		latestTextView = (TextView) view.findViewById(R.id.textViewNewsLatest);
		hotTextView = (TextView) view.findViewById(R.id.textViewNewsHot);
		recommendTextView = (TextView) view
				.findViewById(R.id.textViewNewsRecommend);

		guildLineView = (ImageView) view.findViewById(R.id.imageViewGuildLine);
		viewPager = (ViewPager) view.findViewById(R.id.viewPagerNews);

		NewsLatestFragment latestFragment = new NewsLatestFragment();
		NewsHotFragment hotFragment = new NewsHotFragment();
		NewsRecommendFragment recommendFragment = new NewsRecommendFragment();

		fragmentList.add(latestFragment);
		fragmentList.add(hotFragment);
		fragmentList.add(recommendFragment);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayoutNewsTitleLatest:
			viewPager.setCurrentItem(0);
			break;
		case R.id.linearLayoutNewsTitleHot:
			viewPager.setCurrentItem(1);
			break;
		case R.id.linearLayoutNewsTitleRecommend:
			viewPager.setCurrentItem(2);
			break;
		}
	}
}
