package com.openclassrooms.entrevoisins.ui.neighbour_list;



import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoriteNeighboursApiService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewNeighbourActivity extends AppCompatActivity {
    private FavoriteNeighboursApiService mApiService;
    @BindView(R.id.avatarImg)
    ImageView avatar;

    @BindView(R.id.nameTopText)
    TextView nameTop;

    @BindView(R.id.name_card_text)
    TextView name;

    @BindView(R.id.place_text)
    TextView address;

    @BindView(R.id.phone_text)
    TextView phone;

    @BindView(R.id.facebook_text)
    TextView facebook;

    @BindView(R.id.aboutTextCard)
    TextView aboutMeText;

    @BindView(R.id.add_favorite)
    FloatingActionButton addFavorite;

    @OnClick(R.id.add_favorite)
    void addFavouriteToList() {
        // USING FILE SAVE
//        Gson gson = new Gson();
//        Type listType = new TypeToken<ArrayList<Neighbour>>(){}.getType();
//        String jsonStringFromFile = null;
//        try {
//            jsonStringFromFile = FileFunctions.readFromFile("favorites.txt",getApplicationContext());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(jsonStringFromFile!=null)
//            FavouriteNeighbours  = gson.fromJson(jsonStringFromFile,listType);
//        else
//            FavouriteNeighbours = new ArrayList<>();
//
//        FavouriteNeighbours.add(neighbour);
//        String jsonStringToWrite = gson.toJson(FavouriteNeighbours);
//
//        FileFunctions.writeToFile(jsonStringToWrite,"favorites.txt",getApplicationContext());

        // USING API SERVICE
        if(mApiService.neighbourIsAlreadyFavorite(neighbour))
        {

            mApiService.deleteNeighbour(neighbour);
            addFavorite.setImageDrawable(getDrawable(R.drawable.ic_star_border_white_24dp));
        }
        else
        {
            mApiService.createNeighbour(neighbour);
            addFavorite.setImageDrawable(getDrawable(R.drawable.ic_star_white_24dp));
        }





    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Neighbour neighbour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "onCreate: you created a new viewing activity");

        setContentView(R.layout.activity_view_neighbour);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setTint(getResources().getColor(R.color.white));

        mApiService = (FavoriteNeighboursApiService) DI.getFavoriteNeighbourApiService();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // GET THE DATA FROM THE CLICKED NEIGHBOUR
        if (getIntent()!=null)
        {

            long id = getIntent().getLongExtra("id",-1);
            String mName = getIntent().getStringExtra("name");
            String avatarUrl = getIntent().getStringExtra("avatarUrl");
            String addres = getIntent().getStringExtra("address");
            String phoneNmb = getIntent().getStringExtra("phone");
            String aboutMe = getIntent().getStringExtra("aboutMe");
            int position = getIntent().getIntExtra("tabPosition",-1);
            name.setText(mName);
            nameTop.setText(mName);
            address.setText(addres);
            phone.setText(phoneNmb);
            aboutMeText.setText(aboutMe);

            String adjustedAvatarUrl = changeImageResolution(avatarUrl,400);

            Glide.with(avatar).load(adjustedAvatarUrl).apply(RequestOptions.noTransformation()).into(avatar);
            facebook.setText("www.facebook.fr/"+mName.toLowerCase());
            neighbour = new Neighbour(id,mName,avatarUrl,addres,phoneNmb,aboutMe);

            // DISABLE FAVORITE BUTTON IF TAB 1
            if (position==1)
                addFavorite.hide();
            else
            {
                if(mApiService.neighbourIsAlreadyFavorite(neighbour))
                {
                    addFavorite.setImageDrawable(getDrawable(R.drawable.ic_star_white_24dp));
                }
                else
                {
                    addFavorite.setImageDrawable(getDrawable(R.drawable.ic_star_border_white_24dp));
                }
            }
        }

    }
    public static String changeImageResolution(String originalImg, int resolution)
    {
        int indexFirst = originalImg.lastIndexOf("/");
        int indexLast = originalImg.indexOf("?");
        String firstHalf = originalImg.substring(0,indexFirst+1);
        String secondHalf = originalImg.substring(indexLast);
        return firstHalf + resolution + secondHalf;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}