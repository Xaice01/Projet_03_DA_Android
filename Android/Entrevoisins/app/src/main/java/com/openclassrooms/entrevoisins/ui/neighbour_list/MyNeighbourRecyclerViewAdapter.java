package com.openclassrooms.entrevoisins.ui.neighbour_list;


import androidx.recyclerview.widget.RecyclerView;       //import android.support.v7.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;


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
                public void onClick(View view) {
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
