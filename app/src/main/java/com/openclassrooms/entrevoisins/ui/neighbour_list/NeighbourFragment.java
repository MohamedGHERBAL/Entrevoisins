package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DetailNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;

    private static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";
    private boolean isFavoriteList;
    private MyNeighbourRecyclerViewAdapter mNeighboursAdapter;


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(boolean getFavorite) {
        NeighbourFragment fragment = new NeighbourFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_FAVORITE, getFavorite);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        EventBus.getDefault().register(this);

        if (getArguments() != null && getArguments().containsKey(EXTRA_FAVORITE)) {
            isFavoriteList = getArguments().getBoolean(EXTRA_FAVORITE);
        }
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
     * Initialize the List of neighbours
     */
    private void initList() {

        // for list
        if (mNeighbours == null) {
            mNeighbours = new ArrayList<>();
        } else {
            mNeighbours.clear();
        }

        // for Adapter
        if (mNeighboursAdapter == null) {
            mNeighboursAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours);
            mRecyclerView.setAdapter(mNeighboursAdapter);
        } else {
            mNeighboursAdapter.notifyDataSetChanged();
        }

        // for Favorite
        if (isFavoriteList) {
            mNeighbours.addAll(mApiService.getNeighboursFavorite());
        } else {
            mNeighbours.addAll(mApiService.getNeighbours());
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Subscribe
    public void onViewDetailEvent(DetailNeighbourEvent event) {
        // Toast.makeText(getActivity(), "Event !?", Toast.LENGTH_SHORT).show();
        Log.i("TAG", "ID="+event.neighbour.getId());
        Intent intent = new Intent(getActivity(), DetailNeighbourActivity.class);
        intent.putExtra("id", event.neighbour.getId());
        getActivity().startActivity(intent);
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
