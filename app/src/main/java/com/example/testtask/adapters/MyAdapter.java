package com.example.testtask.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testtask.R;
import com.example.testtask.RecyclerViewPosition;
import com.example.testtask.activity.YouTubePlayerActivity;
import com.example.testtask.data.FirstPlayListTable;
import com.example.testtask.model.Example;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Example> mList;
    Context context;
    private List<FirstPlayListTable> firstPlayListTableList; // Cached copy of words

    private static RecyclerViewPosition mRecyclerViewPosition;

    public MyAdapter(ArrayList<Example> mList, Context context,RecyclerViewPosition recyclerViewPosition) {
        this.mList = mList;
        this.context = context;
        mRecyclerViewPosition = recyclerViewPosition;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Example example = mList.get(i);
        Picasso.get()
                .load(example.getSnippet().getThumbnails().getMedium().getUrl())
                .into(myViewHolder.image);

        myViewHolder.name.setText(example.getSnippet().getTitle());
        if (example.getSnippet().getDescription() != null && example.getSnippet().getDescription().length() > 120) {
            myViewHolder.description.setText(example.getSnippet().getDescription().substring(0, 120).concat("..."));
        }
    }

    public void setWords(ArrayList<Example> list) {
       this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView name;
        TextView description;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.iv_picture);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.my_recycler_view);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Example example = mList.get(getAdapterPosition());
            mRecyclerViewPosition.recyclerViewPosition(example);
            Intent intent = new Intent(context, YouTubePlayerActivity.class);
            intent.putExtra(YouTubePlayerActivity.VIDEO_ID, example.getSnippet().getResourceId().getVideoId());
            context.startActivity(intent);

        }
    }

}
