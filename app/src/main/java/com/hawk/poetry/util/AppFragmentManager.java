package com.hawk.poetry.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.hawk.poetry.R;
import com.hawk.poetry.ui.MainActivity;
import com.hawk.poetry.ui.fragment.BaseFragment;
import com.hawk.poetry.ui.fragment.MainFragment;
import com.hawk.poetry.ui.fragment.PoetFragment;
import com.hawk.poetry.ui.fragment.PoetryDetailFragment;
import com.hawk.poetry.ui.fragment.WebViewFragment;

public class AppFragmentManager {
    private static final String TAG = "AppFragmentManager";

    public static final String MAINFRAGMENT = "MainFragment";
    public static final String POETRYDETAIL = "PoetryDetailFragment";
    public static final String POETFRAGMENT = "PoetFragment";
    public static final String WEBVIEWFRAGMENT="WebViewFragment";

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private String currentFragmentTag = "";

    public AppFragmentManager(MainActivity mainActivity) {
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void setCurrentFragmentTag(String tag) {
        currentFragmentTag = tag;
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    /**
     * 弹出堆栈中的一个并且显示，也就是代码模拟按下返回键的操作。
     */
    public void popBackStack() {
        mFragmentManager.popBackStack();
    }

    /**
     * void popBackStack(String name, int flags);
     * 针对第一个参数，如果name为null，那么只有顶部的状态被弹出；
     * 如果name不为null，并且找到了这个name所指向的Fragment对象；
     * 根据flags的值，如果是flag=0，那么将会弹出该状态以上的所有状态；
     * 如果flag=POP_BACK_STACK_INCLUSIVE，那么将会弹出该状态（包括该状态）以上的所有状态。
     */
    public void popBackStackInclusive() {
        /*
         * Fragment中使用POP_BACK_STACK_INCLUSIVE
		 * 达到一次跳转到栈底。类似Activity的
		 * 采用FLAG_ACTIVITY_CLEAR_TOP
		 */
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void switchFragment(String tag, boolean addToStack, Bundle bundle) {
        Log.i(TAG, "tag=" + tag);

        if (tag.equals(currentFragmentTag)) {
            return;
        }

        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        currentFragmentTag = tag;
        boolean isExist = true;
        if (null == fragment) {
            isExist = false;
            if (tag.equals(MAINFRAGMENT)) {
                fragment = new MainFragment();
            } else if (tag.equals(POETRYDETAIL)) {
                fragment = new PoetryDetailFragment();
            } else if (tag.equals(POETFRAGMENT)) {
                fragment = new PoetFragment();
            }else if(tag.equals(WEBVIEWFRAGMENT)){
                fragment = new WebViewFragment();
            }
            fragment.setArguments(bundle);
        }
        ensureTransaction().replace(R.id.main_container, fragment, tag);

//        在事务提交之前调用FragmentTransaction的addToBackStack()方法，它可以接受一个名字用于描述返回栈的状态，一般传入null即可。
        if (addToStack) {
            mFragmentTransaction.addToBackStack(tag);
        } else{
            mFragmentTransaction.addToBackStack(null);
        }
        commitTransactions();
//        ToastUtil.show("stack count=" + mFragmentManager.getBackStackEntryCount() + ";fragment count=" + mFragmentManager.getFragments().size());
    }

    protected FragmentTransaction ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();

//            加上以下两句代码会出错
//            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
//            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        }
        return mFragmentTransaction;
    }

    protected void commitTransactions() {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.commitAllowingStateLoss();
            mFragmentTransaction = null;
        }
    }

    public void clickRightBtn() {
        BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(currentFragmentTag);
        if (null == fragment) return;
        fragment.clickRightBtn();
    }
}