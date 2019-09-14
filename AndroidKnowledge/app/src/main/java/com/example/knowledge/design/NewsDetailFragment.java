package com.example.knowledge.design;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.knowledge.R;


public class NewsDetailFragment extends Fragment {
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		TextView tv = new TextView(getContext());
//		Bundle bundle = getArguments();
//		String title = bundle.getString("title");
//		tv.setBackgroundColor(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
//		tv.setText(title);
//		return tv;
		View view = inflater.inflate(R.layout.fragment_layout, container, false);
		return view;
	}

}
