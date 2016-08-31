package com.example.mayank.travelagentproject;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    TabLayout tablayout;
    ViewPagerAdapter viewpageradapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login/SignUp");
        setSupportActionBar(toolbar);

        tablayout=(TabLayout)findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.container);
        viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addfragment(new Login(),"LOGIN");
        viewpageradapter.addfragment(new Signup(),"SIGNUP");
        mViewPager.setAdapter(viewpageradapter);
        tablayout.setupWithViewPager(mViewPager);
    }

}
