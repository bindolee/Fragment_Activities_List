package sbin.com.listfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Product> products = DataProvider.productList;
    private final int numPages = products.size();
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //This gets called each time I navigate to page.
        @Override
        public Fragment getItem(int position) {
            return ItemFragment.newInstance(products.get(position));
        }

        @Override
        public int getCount() {
            return numPages;
        }
    }

    @Override
    public void onBackPressed() {
        //Am I in the 1st page, then exit..
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            //if I am not 1st page, then go back to the previous page. when back button is pressed
            mPager.setCurrentItem(mPager.getCurrentItem()-1);
        }
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
        switch(id){
            case R.id.action_settings:
                onSettingsClick();
                break;
            case R.id.actiontest_pref:
                onTestPrefClick();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSettingsClick() {
        Intent intent = new Intent(this, MyPreferencesActivity.class);
        startActivity(intent);
    }

    public void onTestPrefClick() {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String usrName = preferences.getString("username", "not defined");
        Toast.makeText(MainActivity.this,"User name: " + usrName,
                Toast.LENGTH_LONG).show();
    }
}
