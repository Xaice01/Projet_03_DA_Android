package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourViewActivity.KEY_NEIGHBOUR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;      //import android.support.v7.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;        //import android.support.v7.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;       //import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.stream.Collectors;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private List<Neighbour> favoris;

    private static final String KEY_POSITION="position"; //position on tab



    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(int position) {
        NeighbourFragment fragment = new NeighbourFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mNeighbours = mApiService.getNeighbours();


        favoris = mNeighbours.stream().filter(Neighbour::isFavori).collect(Collectors.toList()); //récupére que les favoris
        MyNeighbourRecyclerViewAdapter adapter=null;
        if(getArguments().getInt(KEY_POSITION)==0)
        {
            adapter = new MyNeighbourRecyclerViewAdapter(mNeighbours);
            mRecyclerView.setAdapter(adapter);

        }
        else {
            adapter = new MyNeighbourRecyclerViewAdapter(favoris);
            mRecyclerView.setAdapter(adapter);
        }
        adapter.setOnItemClickListener(new MyNeighbourRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long id) {
                Intent intent =new Intent(getActivity(), NeighbourViewActivity.class);
                intent.putExtra(KEY_NEIGHBOUR,id);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }
}
