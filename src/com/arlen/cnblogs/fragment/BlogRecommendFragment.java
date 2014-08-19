package com.arlen.cnblogs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlen.cnblogs.R;

public class BlogRecommendFragment extends Fragment {

	public BlogRecommendFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog_recommend, container,
				false);
		return rootView;
	}
}
