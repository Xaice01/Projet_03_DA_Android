package com.openclassrooms.entrevoisins.ui.neighbour_list;


import androidx.fragment.app.Fragment;   //import android.support.v4.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;   //import android.support.v4.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;      //import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return NeighbourFragment.newInstance(position);     //la position détermine le tab utilisé
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {return 2;} // page voisin et page favoris
}