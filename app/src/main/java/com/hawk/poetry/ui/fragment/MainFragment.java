package com.hawk.poetry.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hawk.poetry.R;
import com.hawk.poetry.util.AppFragmentManager;
import com.hawk.poetry.util.TabFragmentManager;
import com.hawk.poetry.util.TabFragmentManager.OnRgsExtraCheckedChangedListener;
import com.hawk.poetry.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";
    private View layout;
    private RadioGroup rg_tabs;
    private List<Fragment> fragmentList;
    private String[]tabsName={"唐诗","宋词","元曲"};
    private int tabFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mListener.setCurrentFragmentTag(AppFragmentManager.MAINFRAGMENT);

        if (null == layout) {
            layout = inflater.inflate(R.layout.fragment_main, null);
            rg_tabs = (RadioGroup) layout.findViewById(R.id.rg_tabs);
        }
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // 这里加一个判断，因为fragment切换时候会重新onCreateView、onActivityCreated...，
        // 但是数据还存在，不需要初始化数据
        if (null == fragmentList) {
            initFragmentList();
            TabFragmentManager tabFragManager = new TabFragmentManager();
            tabFragManager.init(this, fragmentList, R.id.fragment_container,rg_tabs);
            //设置页面切换处理
            tabFragManager.setOnRgsExtraCheckedChangedListener(new OnRgsExtraCheckedChangedListener() {
                public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
                                                     int checkedId, int index) {
                    tabFlag=index;
                    mListener.setHeadTitle(tabsName[tabFlag]);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        ToastUtil.show("mainfragment onresume");
        mListener.setHeadTitle(tabsName[tabFlag]);
        mListener.showHeadRight("我的博客");
        mListener.setHomeBackEnable(false);
    }

    /**
     * 初始化FragmentList
     */
    private void initFragmentList() {
        fragmentList = new ArrayList<>();

        CommonFragment tempFragment;
        Bundle tempBundle;

        // 添加tab-唐诗到fragmentList
        tempFragment = new CommonFragment();
        tempBundle = new Bundle();
        tempBundle.putString("tabflag", "TangShi");
        tempFragment.setArguments(tempBundle);
        fragmentList.add(tempFragment);

        // 添加tab-宋词到fragmentList
        tempFragment = new CommonFragment();
        tempBundle = new Bundle();
        tempBundle.putString("tabflag", "SongCi");
        tempFragment.setArguments(tempBundle);
        fragmentList.add(tempFragment);

        // 添加tab-元曲到fragmentList
        tempFragment = new CommonFragment();
        tempBundle = new Bundle();
        tempBundle.putString("tabflag", "YuanQu");
        tempFragment.setArguments(tempBundle);
        fragmentList.add(tempFragment);
    }

    @Override
    public void clickRightBtn() {
        ToastUtil.show("click");
        mListener.switchToFragment(AppFragmentManager.WEBVIEWFRAGMENT,false,null);
    }
}
