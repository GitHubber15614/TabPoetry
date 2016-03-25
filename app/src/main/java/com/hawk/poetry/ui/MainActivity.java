package com.hawk.poetry.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.hawk.poetry.R;
import com.hawk.poetry.ui.fragment.BaseFragment;
import com.hawk.poetry.ui.fragment.MainFragment;
import com.hawk.poetry.util.AppFragmentManager;
import com.hawk.poetry.util.ToastUtil;
import com.hawk.poetry.widget.SplashDialog;

public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private long firstTime;
    private AppFragmentManager mAppFragmentUtil;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new SplashDialog(this).show();
        mAppFragmentUtil = new AppFragmentManager(this);

//        mAppFragmentUtil.switchFragment(AppFragmentManager.MAINFRAGMENT, true, null);
// 这里不能用以上这句,不然从PoetFragment点击'回到主页'会出现页面一片空白,因为清空回退栈把所有fragment都被移除了,
// 以下的添加并没有记入回退栈，所以mainfragment不会因此被移除
        Fragment mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,mainFragment,AppFragmentManager.MAINFRAGMENT).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mAppFragmentUtil.popBackStack();
        } else if (id == R.id.action_right) {
            mAppFragmentUtil.clickRightBtn();
        }
        return true;
    }

    @Override
    public void switchToFragment(String tag, boolean addToStack, Bundle bundle) {
        mAppFragmentUtil.switchFragment(tag, addToStack, bundle);
    }

    @Override
    public void setCurrentFragmentTag(String tag) {
        mAppFragmentUtil.setCurrentFragmentTag(tag);
    }

    @Override
    public void popBackStackInclusive() {
        mAppFragmentUtil.popBackStackInclusive();
    }

    @Override
    public void setHeadTitle(String title) {
        setTitle(title);
    }

    @Override
    public void hideHeadRight() {
        if(null==mMenu)return;
        mMenu.getItem(0).setVisible(false);
    }

    @Override
    public void showHeadRight(String title) {
        if(mMenu==null)return;
        MenuItem mItem = mMenu.getItem(0);
        mItem.setTitle(title);
        mItem.setVisible(true);
    }

    @Override
    public void setHomeBackEnable(boolean ifshow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(ifshow);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        Log.i(TAG, "mAppFragmentUtil.getCurrentFragmentTag()=" + mAppFragmentUtil.getCurrentFragmentTag());
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAppFragmentUtil.getCurrentFragmentTag().equals(AppFragmentManager.MAINFRAGMENT)) {
                long secondTime = System.currentTimeMillis();

                Log.i(TAG, "firstTime=" + firstTime + ";secondTime=" + secondTime);

                if ((secondTime - firstTime) > 1000) {
                    ToastUtil.show("再按一次退出");
                    firstTime = secondTime;
                    return false;
                } else {
                    finish();
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
