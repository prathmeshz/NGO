package demo.ngo.profiletab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by prathmesh zade on 02/25/2017.
 */

public class ProfileTabAdapter extends FragmentStatePagerAdapter {

    String title[]={"User","Ngo"};
    public ProfileTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new user();

            case 1:
               return new ngo();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
