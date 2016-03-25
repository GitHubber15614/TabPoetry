package com.hawk.poetry.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawk.poetry.R;
import com.hawk.poetry.util.AppFragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PoetFragment extends BaseFragment {
    private static final String TAG = "PoetFragment";
    private JSONObject poet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        try {
            if (null != arguments) {
                poet = new JSONObject(arguments.getString("poet"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mListener.setCurrentFragmentTag(AppFragmentManager.POETFRAGMENT);

        mListener.setHomeBackEnable(true);
        mListener.setHeadTitle("诗人生平");
        mListener.showHeadRight("回到首页");

        View layout = inflater.inflate(R.layout.fragment_poet, null);
        ImageView poet_avater_iv = (ImageView) layout.findViewById(R.id.poet_avater_iv);
        TextView poet_name_tv = (TextView) layout.findViewById(R.id.poet_name_tv);
        TextView poet_dynasty_tv = (TextView) layout.findViewById(R.id.poet_dynasty_tv);

        Glide.with(this).load(poet.optString("avatar")).placeholder(R.mipmap.ic_launcher).into(poet_avater_iv);
        poet_name_tv.setText(poet.optString("name"));
        poet_dynasty_tv.setText(poet.optString("dynasty"));

        return layout;
    }

    @Override
    public void clickRightBtn() {
//        mListener.popBackStackInclusive();
		getMainActivity().popBackStackInclusive();
    }
}
