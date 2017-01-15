package io.instadakwah.instadakwah.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.instadakwah.instadakwah.R;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private MenuItem mMenuItem;

    @BindView(R.id.dl_main)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    @BindView(R.id.nv_main)
    NavigationView navigationView;

    ImageView drawAvatar;
    TextView nameAvatar;
    LinearLayout navHeader;
    private Context context = HomeActivity.this;
    private static final String URL = "https://cdn-images-1.medium.com/fit/c/100/100/1*_Lm5zgfjlNN3RBCCe6rSwA.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setNavListener(navigationView);
        addNavListener(toolbar, navigationView);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        View hView = navigationView.getHeaderView(0);
        navHeader = (LinearLayout) hView.findViewById(R.id.nav_header);
        drawAvatar = (ImageView) hView.findViewById(R.id.avatar_image);
        nameAvatar = (TextView) hView.findViewById(R.id.avatar_name);
        nameAvatar.setText("Wisnu Kurniawan");

        navHeader.setOnClickListener(this);

        setUpAvatar();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            finish();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    private void setNavListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mMenuItem = item;
                mMenuItem.setCheckable(false);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public void setUpAvatar() {
        Glide.with(context).load(URL).asBitmap().centerCrop().into(new BitmapImageViewTarget(drawAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable rounded =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                rounded.setCircular(true);
                drawAvatar.setImageDrawable(rounded);
            }
        });
    }

    private void addNavListener(final Toolbar toolbar, final NavigationView navigationView) {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != mMenuItem) {
                    switchNavigationMenu(mMenuItem.getItemId());
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (drawerView != null && drawerView == navigationView) {
                    super.onDrawerSlide(drawerView, 0);
                } else {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }
        };
    }

    private void switchNavigationMenu(int itemId) {
        switch (itemId) {
            case R.id.menu_setting: {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                mMenuItem = null;
                // TODO: 15/01/2017 Go to setting..
                break;
            }
            case R.id.menu_about: {
                showAbout();
                break;
            }
            case R.id.menu_logout: {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                mMenuItem = null;
                // TODO: 15/01/2017 Logout here...
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_header: {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
                // TODO: 15/01/2017 Go to profile..
                break;
            }
        }
    }

    private void showAbout() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.view_bottom_sheet_about, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        mMenuItem = null;
    }
}
