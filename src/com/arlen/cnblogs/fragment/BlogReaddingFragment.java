package com.arlen.cnblogs.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlen.cnblogs.R;

public class BlogReaddingFragment extends ListFragment {

	public BlogReaddingFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog_readding, container,
				false);
		return rootView;
	}
}
