package demo.ngo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import demo.ngo.fragment.F1;
import demo.ngo.fragment.F2;
import demo.ngo.fragment.F3;
import demo.ngo.fragment.F4;

public class TabAdapter extends FragmentStatePagerAdapter
{
    String[] s={"NEWS","EVENTS","DONATE","HELP"};
   // int [] draw={R.drawable.ic_fa_book,R.drawable.ic_news,R.drawable.ic_action_download};
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        //ImageSpan span=new ImageSpan(getBaseContext(),draw[position]);
//            SpannableString spannable=new SpannableString(" ");
//            spannable.setSpan(span,0,spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s[position];
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new F1();

            case 1:
                return new F2();

            case 2:
                return new F3();

            case 3:
                return new F4();

        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
