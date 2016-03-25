package com.hawk.poetry.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hawk.poetry.ui.MainActivity;

public abstract class BaseFragment extends Fragment {
	public OnFragmentInteractionListener mListener;
	private MainActivity mMainActivity;
	
	@Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
		mMainActivity= (MainActivity) context;
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public MainActivity getMainActivity(){
		return mMainActivity;
	}

	public abstract void clickRightBtn();//右上角菜单点击事件

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void switchToFragment(String tag, boolean addToStack, Bundle bundle);
		void setCurrentFragmentTag(String tag);
		void popBackStackInclusive();//清空回退栈
		void setHeadTitle(String title);//设置标题
		void hideHeadRight();//隐藏右上角菜单
		void showHeadRight(String title);//显示并设置右上角菜单标题
		void setHomeBackEnable(boolean ifshow);//是否显示左上角返回按钮
	}
}
