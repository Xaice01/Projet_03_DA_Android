package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
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

        //recupTrueNeighbour(savedInstanceState.getLong(KEY_NEIGHBOUR));  // initialise l'atttribut neightbour
        recupTrueNeighbour(getIntent().getLongExtra(KEY_NEIGHBOUR,0));


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
                if(neighbour.isFavori()==false){
                    mApiService.addfavori(neighbour);                                       //set favori to true
                    mButtonFavori.setImageResource(R.drawable.ic_star_white_24dp);          //switch image mButtonFavori
                } else {
                    mApiService.deleteFavori(neighbour);                                 //set favori to false
                    mButtonFavori.setImageResource(R.drawable.ic_star_border_white_24dp);   //switch image mButtonFavori
                }
            }
        });
    }

    //recupere le neighbour de la liste de mNeighbours de l'Apiservice
    private void recupTrueNeighbour(long idNeighbour){
        Neighbour Fakeneighbour= new Neighbour(idNeighbour,"a","b","c","d","e");
        for(Neighbour i:mNeighbours){
            if(i.equals(Fakeneighbour)) {
                neighbour= i;
            }
        }
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
        mTextDescription.setText(neighbour.getAboutMe());

    }



   // public static void navigate(Neighbour neighbour) {
   //     ActivityManagerCompat activityManagerCompat = intent;
   //     getActivity();
   //     Intent intent = new Intent((AppCompatActivity)ListNeighbourActivity, NeighbourViewActivity.class);
   //     ActivityManagerCompat activityManagerCompat = intent;
   //     ActivityCompat.startActivity(activity, intent, null);
   // }
 //  public static void navigate(FragmentActivity activity, Bundle neighbour) {//todo
 //      Intent intent = new Intent(activity, NeighbourViewActivity.class);
 //      intent.putExtras(neighbour);
 //      //startActivity(intent);
 //      ActivityCompat.startActivity(activity, intent, null);
 //  }
}