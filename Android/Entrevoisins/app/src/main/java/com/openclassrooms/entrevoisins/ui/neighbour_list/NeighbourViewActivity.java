package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.utils.RecupNeighbour;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

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


    static final String KEY_NEIGHBOUR="kneighbour";

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private Neighbour neighbour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_view);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        mNeighbours=mApiService.getNeighbours();

        //recupere le neighbour de la liste de mNeighbours de l'Apiservice
        neighbour= RecupNeighbour.recupTrueNeighbour(getIntent().getLongExtra(KEY_NEIGHBOUR,0),mNeighbours);

        init();
        //pour le Back à la list
        mbuttonBack.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to previous Activity.
                finish();
            }
        });

        mButtonFavori.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!neighbour.isFavori()){                                                  //if neighbour.isFavori==false
                    mApiService.addfavori(neighbour);                                       //set favori to true
                    mButtonFavori.setImageResource(R.drawable.ic_star_white_24dp);          //switch image mButtonFavori
                } else {
                    mApiService.deleteFavori(neighbour);                                 //set favori to false
                    mButtonFavori.setImageResource(R.drawable.ic_star_border_white_24dp);   //switch image mButtonFavori
                }
            }
        });
    }

    private void init(){

        Glide.with(this).load(neighbour.getAvatarUrl()).into(mAvatar); //afficher l'image de l'avatar

        mPrenomAvatar.setText(neighbour.getName());
        mPrenomCard.setText(neighbour.getName());
       if(neighbour.isFavori()) {
           mButtonFavori.setImageResource(R.drawable.ic_star_white_24dp);
       }
       else {
           mButtonFavori.setImageResource(R.drawable.ic_star_border_white_24dp);
       }
        mTextPosition.setText(neighbour.getAddress());
        mTextTel.setText(neighbour.getPhoneNumber());
        mTextLienFacebook.setText("");              //lien FaceBook inexistant dans l'objet neighbour
        if(mTextLienFacebook.getText()==""){        // rendre invisible si il n'y a pas de lien facebook
            mTextLienFacebook.setVisibility(View.GONE);
            mImageFB.setVisibility(View.GONE);
        }
        mTextDescription.setText(neighbour.getAboutMe());

    }
}