package com.sumer.whatso.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sumer.whatso.Fragments.CallsFragment;
import com.sumer.whatso.Fragments.ChatsFragment;
import com.sumer.whatso.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {

    private final int NO_OF_TABS = 3;
    private final String CHATS = "Chats";
    private final String STATUS = "Status";
    private final String CALLS = "Calls";

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: return new ChatsFragment();
            case 1: return new StatusFragment();
            case 2: return new CallsFragment();
            default : return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return NO_OF_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position)
        {
            case 0: title = CHATS;break;
            case 1: title = STATUS;break;
            case 2: title = CALLS;break;
            default: title = CHATS;
        }
        return title;
    }
}
