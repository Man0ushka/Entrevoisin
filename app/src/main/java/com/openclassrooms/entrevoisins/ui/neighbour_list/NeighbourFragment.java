package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.ClickedNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;

    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION="position";


    /**
     * Create and return a new instance
     *
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
        Log.i("FRAG", "onCreate: you created a new fragment");
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, -1);
        if(position==0)
        mApiService = DI.getNeighbourApiService();
        else
            mApiService = DI.getFavoriteNeighbourApiService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, -1);
        View view;
        if(position==0)
        {
            view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        }
        else
            view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() throws IOException {
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, -1);
        mNeighbours = mApiService.getNeighbours();
            // CHECK IF FILE FAVORITES EXISTS
//            Gson gson = new Gson();
//            Type listType = new TypeToken<ArrayList<Neighbour>>(){}.getType();
//            String jsonStringFromFile = FileFunctions.readFromFile("favorites.txt",getContext());
//            if(jsonStringFromFile!=null)
//            mNeighbours = gson.fromJson(jsonStringFromFile,listType);
//            else
//                mNeighbours = new ArrayList<>();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours,position));
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            initList();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Fired if the user clicks on a neighbour
     *
     * @param event
     */
    @Subscribe
    public void onClickedNeighbour(ClickedNeighbourEvent event)
    {
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, -1);
        if(event.tabPos==position)
        {
            Log.i("TAG", "onClicked you start a new activity");
            Intent intent = new Intent(this.getActivity(),ViewNeighbourActivity.class);
            intent.putExtra("id",event.neighbour.getId())
                    .putExtra("name",event.neighbour.getName())
                    .putExtra("address",event.neighbour.getAddress())
                    .putExtra("phone",event.neighbour.getPhoneNumber())
                    .putExtra("aboutMe",event.neighbour.getAboutMe())
                    .putExtra("avatarUrl",event.neighbour.getAvatarUrl())
                    .putExtra("tabPosition", position);
            startActivity(intent);
        }
    }
    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) throws IOException {
        assert getArguments() != null;
        int position = getArguments().getInt(KEY_POSITION, -1);
        if(event.tabPos==position || event.tabPos==0)
        {
            mApiService.deleteNeighbour(event.neighbour);
            initList();
        }

    }

}
