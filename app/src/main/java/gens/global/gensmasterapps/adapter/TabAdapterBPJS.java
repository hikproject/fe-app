package gens.global.gensmasterapps.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import gens.global.gensmasterapps.fragment.BpjsKesehatanFragment;
import gens.global.gensmasterapps.fragment.BpjsTenagakerjaFragment;
import gens.global.gensmasterapps.transfer.TransferBankFragment;
import gens.global.gensmasterapps.transfer.TransferMitraFragment;

public class TabAdapterBPJS extends FragmentPagerAdapter {

    public TabAdapterBPJS(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new BpjsKesehatanFragment();
        }
        else if (position == 1)
        {
            fragment = new BpjsTenagakerjaFragment();
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
            title = "Kesehatan";
        }
        else if (position == 1)
        {
            title = "Ketenagakerjaan";
        }
        return title;
    }
}
