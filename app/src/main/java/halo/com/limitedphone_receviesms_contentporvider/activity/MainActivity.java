package halo.com.limitedphone_receviesms_contentporvider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import halo.com.limitedphone_receviesms_contentporvider.R;
import halo.com.limitedphone_receviesms_contentporvider.fragment.PhoneFragment;
import halo.com.limitedphone_receviesms_contentporvider.fragment.SmsFragment;
import halo.com.limitedphone_receviesms_contentporvider.viewpaper.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    SmsFragment smsFragment;
    PhoneFragment phoneFragment;
    ViewPagerAdapter viewPagerAdapter;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Main Activity", "onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPaper);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        smsFragment = new SmsFragment();
        phoneFragment = new PhoneFragment();
        fragmentManager = this.getSupportFragmentManager();
        setViewMain();
    }

    private void setViewMain() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(smsFragment, "SMS");
        viewPagerAdapter.addFragment(phoneFragment, "Limited Phone");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_phone) {
            startActivity(new Intent(this, AddLimitedPhone.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
