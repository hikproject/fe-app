package gens.global.gensmasterapps.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import gens.global.gensmasterapps.transfer.TransferBankFragment;
import gens.global.gensmasterapps.transfer.TransferMitraFragment;

public class TransferAdapter extends FragmentPagerAdapter {

    public TransferAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TransferMitraFragment();
        }
        else if (position == 1)
        {
            fragment = new TransferBankFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Mitra Gen's";
        }
        else if (position == 1)
        {
            title = "Rekening Bank";
        }
        return title;
    }
}
