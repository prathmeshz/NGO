package demo.ngo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import demo.ngo.adapter.TabAdapter;
import demo.ngo.fragment.DrawerFragment;

public class TabActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    DrawerFragment drawerFragment;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment= (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navfragment);
        drawerFragment.setUp(drawerLayout,toolbar);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
    }
}