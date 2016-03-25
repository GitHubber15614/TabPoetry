package com.hawk.poetry.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawk.poetry.R;
import com.hawk.poetry.util.AppFragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PoetryDetailFragment extends BaseFragment{
	private static final String TAG="PoetryDetailFragment";
	private JSONObject poetry;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Bundle arguments=getArguments();
		if(null!=arguments){
			try {
				poetry=new JSONObject(arguments.getString("poetry"));
				Log.i(TAG,"poetry="+poetry.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mListener.setCurrentFragmentTag(AppFragmentManager.POETRYDETAIL);
		View layout=inflater.inflate(R.layout.fragment_poetrydetail,null);
		
		TextView text_title=(TextView) layout.findViewById(R.id.text_title);
		TextView text_author=(TextView) layout.findViewById(R.id.text_author);
		TextView text_content=(TextView) layout.findViewById(R.id.text_content);
		
		 mListener.setHeadTitle(poetry.optJSONObject("poem").optString("title"));
		 mListener.setHomeBackEnable(true);
		mListener.showHeadRight("诗人生平");

		 text_title.setText(poetry.optJSONObject("poem").optString("title"));
		 text_author.setText(poetry.optJSONObject("poet").optString("name"));
		 text_content.setText(poetry.optJSONObject("poem").optString("content"));
		 
		return layout;
	}

	@Override
	public void clickRightBtn() {
		Bundle arguments=new Bundle();
		arguments.putString("poet",poetry.optJSONObject("poet").toString());
		mListener.switchToFragment(AppFragmentManager.POETFRAGMENT,false,arguments);
	}
}