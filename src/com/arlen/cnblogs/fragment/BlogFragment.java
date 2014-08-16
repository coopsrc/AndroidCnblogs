package com.arlen.cnblogs.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlen.cnblogs.R;

public class BlogFragment extends Fragment {

	public BlogFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blog, container,
				false);
		return rootView;
	}
}
