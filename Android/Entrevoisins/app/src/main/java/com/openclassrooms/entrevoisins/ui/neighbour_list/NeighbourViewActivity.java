package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.utils.RecupNeighbour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourViewActivity extends AppCompatActivity {

    //déclaration du composant du layout
    @BindView(R.id.imageButton_Arrow_Back)
    ImageButton mbuttonBack;

    @BindView(R.id.imageView_Avatar)
    ImageView mAvatar;

    @BindView(R.id.Text_Prenom_Neighbour_Avatar)
    TextView mPrenomAvatar;

    @BindView(R.id.floatingActionButton_favoris)
    FloatingActionButton mButtonFavori;

    @BindView(R.id.Text_Prenom_Neighbour_card)
    TextView mPrenomCard;

    @BindView(R.id.textPosition)
    TextView mTextPosition;

    @BindView(R.id.textNumeroTel)
    TextView mTextTel;

    @BindView(R.id.textLienFaceBook)
    TextView mTextLienFacebook;

    @BindView(R.id.textView_Description)
    TextView mTextDescription;

    @BindView(R.id.image_world)
    ImageView mImageFB;


    static final String KEY_NEIGHBOUR = "kneighbour";

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Neighbour neighbour;
        setContentView(R.layout.activity_neighbour_view);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();

        //recupere le neighbour de la liste de mNeighbours de l'Apiservice
        long NeighbourID = getIntent().getLongExtra(KEY_NEIGHBOUR, 0);
        neighbour = RecupNeighbour.getNeighbourByID(NeighbourID, mNeighbours);

        bind(neighbour);
        //pour le Back à la list
        mbuttonBack.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to previous Activity
                finish();
            }
        });

        mButtonFavori.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!neighbour.isFavori()) {                                                  //if neighbour.isFavori==false
                    mApiService.addfavori(neighbour);                                       //set favori to true
                    mButtonFavori.setImageResource(R.drawable.ic_star_white_24dp);          //switch image mButtonFavori
                } else {
                    mApiService.deleteFavori(neighbour);                                 //set favori to false
                    mButtonFavori.setImageResource(R.drawable.ic_star_border_white_24dp);   //switch image mButtonFavori
                }
            }
        });
    }

    private void bind(Neighbour neighbour) {

        Glide.with(this).load(neighbour.getAvatarUrl()).into(mAvatar); //afficher l'image de l'avatar

        mPrenomAvatar.setText(neighbour.getName());
        mPrenomCard.setText(neighbour.getName());
        if (neighbour.isFavori()) {
            mButtonFavori.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            mButtonFavori.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
        mTextPosition.setText(neighbour.getAddress());
        mTextTel.setText(neighbour.getPhoneNumber());
        mTextDescription.setText(neighbour.getAboutMe());

    }
}