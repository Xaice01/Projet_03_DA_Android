package com.openclassrooms.entrevoisins.ui.neighbour_list;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return NeighbourFragment.newInstance(position);     //position détermine le tab utilisé
    }

    /**
     * get the number of pages
     *
     * @return 2
     */
    @Override
    public int getCount() {
        return 2;
    } // page voisin et page favoris
}