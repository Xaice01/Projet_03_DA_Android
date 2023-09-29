package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static android.app.PendingIntent.getActivity;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;       //import android.support.v7.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position,long id);
    }
    private final List<Neighbour> mNeighbours;

    private static final String KEY_NEIGHBOUR="kneighbour";

    private OnItemClickListener mListener;
    //private View.OnClickListener onClickListener;   //for the click on l'item

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    //test
    private ViewGroup parent;


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.onBind(neighbour);

    //    holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {
    //            EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
    //        }
//
    //    });


        //click on item
       // holder.itemView.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         if (onClickListener != null) {
       //             onClickListener.onClick(position, neighbour.getId());
       //         }
       //     }
       // });


        //ajout perso
//        holder.mNeighbourName.setOnClickListener(new View.OnClickListener() {
//
//            //appel de l'activity NeighbourViewActivity
//            @Override
//            public void onClick(View view) {//todo
//                //getActivity();
//                //getActivity(Context)
//                //NeighbourViewActivity.navigate(neighbour);
//
//                // non fonctionnel
//               //view = LayoutInflater.from(parent.getContext())
//               //        .inflate(R.layout.activity_neighbour_view, parent, false);
//               //new ViewHolder(view);
//                Bundle bundleNeighbour = new Bundle();
//                bundleNeighbour.putLong(KEY_NEIGHBOUR, neighbour.getId());
//                ListNeighbourActivity listNeighbourActivity= new ListNeighbourActivity();
//                listNeighbourActivity.viewNeighbour(bundleNeighbour);
//            }
  //      });

    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

   // public void setOnClickListener(View.OnClickListener onClickListener) {
   //     this.onClickListener = onClickListener;
   // }
//
   // public interface OnClickListener{
   //     void onClick(int position,int model);
   // }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        //todo
        public void onBind(Neighbour neighbour) {
            mNeighbourName.setText(neighbour.getName());
            Glide.with(mNeighbourAvatar.getContext())
                    .load(neighbour.getAvatarUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mNeighbourAvatar);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                }

            });

            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {//todo
                    if (mListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) { // to make sure the position is valid
                            mListener.onItemClick(getAdapterPosition(), neighbour.getId());
                        }
                    }

                }
            });


        }
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
