package com.darcye.tabswitcher;

import com.darcye.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment{

	private TextView tvContent;
	
	public final static SimpleFragment newFragment(String content){
		SimpleFragment fragment  = new SimpleFragment();
		Bundle bundle = new Bundle();
		bundle.putString("content", content);
		fragment.setArguments(bundle);
		return fragment;
	} 
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView  = inflater.inflate(R.layout.fragment_simple, container, false);
		tvContent = (TextView) rootView.findViewById(R.id.content_tv);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvContent.setText(getArguments().getString("content"));
	}
}
