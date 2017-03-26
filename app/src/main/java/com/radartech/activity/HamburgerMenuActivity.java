package com.radartech.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.radartech.adapter.NavigationMenuAdapter;
import com.radartech.callbacks.AlertDialogCallback;
import com.radartech.callbacks.OnItemClickListener;
import com.radartech.customviews.RadarAlertDialog;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 03 Nov 2016 5:27 PM.
 *
 */

public abstract class HamburgerMenuActivity extends BaseActivity implements OnItemClickListener {

    //Hamburger menu constants
    private static final int INDEX_PROVIDER_INFO = 0;
    private static final int INDEX_ACCOUNT_INFO = 1;
    private static final int INDEX_CURRENT_VERSION = 2;
    private static final int INDEX_ABOUT = 3;
    private static final int INDEX_HELP = 4;
    private static final int INDEX_LOGOUT = 5;
    @BindView(R.id.navigation_menu_RecyclerView)
    RecyclerView mNavigationMenuRecyclerView;
    @BindView(R.id.navigation_drawer_user_account_picture_profile)
    ImageView userAccountProfilePic;
    @BindView(R.id.navigation_drawer_account_information_display_name)
    TextView accountInformationDisplayName;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private Unbinder mUnbinder;
    private RadarAlertDialog dialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setNavigationMenuView());
        mUnbinder = ButterKnife.bind(this);
        setNavigationMenu();
    }

    private void setNavigationMenu() {

        accountInformationDisplayName.setText(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_USER_NAME));
        String[] menuTitleData = getResources().getStringArray(R.array.menu_title);
        TypedArray menuIconData = getResources().obtainTypedArray(R.array.menu_icon);
        mNavigationMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NavigationMenuAdapter adapter = new NavigationMenuAdapter();
        adapter.setMenuData(menuTitleData, menuIconData);
        adapter.setOnItemClickListener(this);
        mNavigationMenuRecyclerView.setAdapter(adapter);
        /*mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                UiUtils.hideSoftKeyboard(HamburgerMenuActivity.this, mDrawerLayout);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    */}

    @Override
    public void onItemClick(View view, int position) {
        if (position >= 0 && position != INDEX_LOGOUT) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        switch (position) {
            case INDEX_PROVIDER_INFO:
                UiUtils.launchActivity(this, ProviderInfoActivity.class, null, false);
                break;
            case INDEX_ACCOUNT_INFO:
                UiUtils.launchActivity(this, AccountInfoActivity.class, null, false);
                break;
            case INDEX_CURRENT_VERSION:
                UiUtils.showToast(this, getString(R.string.user_account_info_current_version));

                break;
           case INDEX_ABOUT:
                UiUtils.showToast(this, getString(R.string.coming_soon));
                break;
            case INDEX_HELP:
                UiUtils.showToast(this, getString(R.string.coming_soon));
                break;
            case INDEX_LOGOUT:
                dialog = new RadarAlertDialog.MagellanAlertDialogBuilder(HamburgerMenuActivity.this, new
                        AlertDialogCallback() {
                            @Override
                            public void alertDialogCallback(byte dialogStatus) {
                                mDrawerLayout.closeDrawer(Gravity.LEFT);
                                switch (dialogStatus) {
                                    case AlertDialogCallback.OK:
                                        dialog.dismiss();
                                        //logout
                                        SharedPreferenceUtils.clear();
                                        finishAffinity();
                                        UiUtils.launchActivity(HamburgerMenuActivity.this, LoginActivity.class, null, true);
                                        break;
                                    case AlertDialogCallback.CANCEL:
                                        dialog.dismiss();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).title(getString(R.string.logout_dialog_title_text))
                        .button1Text(getString(R.string.logout))
                        .button2Text(getString(R.string.cancel)).build();
                dialog.showDialog();
                break;
            default:
                break;
        }
    }

    /**
     * This method is used to change the title of the fragment
     */
    public void setTitle(String title) {
        //TODO set title
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindView(mUnbinder);
    }

    public abstract int setNavigationMenuView();
}
