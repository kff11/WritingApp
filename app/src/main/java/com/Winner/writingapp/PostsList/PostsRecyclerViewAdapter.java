package com.Winner.writingapp.PostsList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Winner.writingapp.R;
import com.Winner.writingapp.RecyclerViewItem;
import com.Winner.writingapp.SharedPreference.Post;
import com.Winner.writingapp.SharedPreference.SP_Main;

import java.util.ArrayList;


public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ItemViewHolder> {

    private View view;
    private ArrayList<RecyclerViewItem> itemList = new ArrayList<RecyclerViewItem>();
    private Context context;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        view = LayoutInflater.from(context).inflate(R.layout.posts_recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getTitle().hashCode();
    }

    // 받을 데이터 추가
    public void addItem(RecyclerViewItem item) {

        itemList.add(item);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title, write, date;
        private ViewGroup layout;

        ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.posts_item_title);
            write = itemView.findViewById(R.id.posts_item_write);
            date = itemView.findViewById(R.id.posts_item_date);
            layout = itemView.findViewById(R.id.posts_recyclerview_layout);

        }

        void onBind(final RecyclerViewItem item) {
            title.setText(item.getTitle());
            write.setText(item.getWrite());
            date.setText(item.getDate());
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 넘겨줄 post 객체를 보냄
                    Post post = new Post(item.getTitle(), item.getWrite(), item.isBookmark());
                    Intent intent = new Intent(context.getApplicationContext(), PostActivity.class);
                    intent.putExtra("post", post);
                    context.startActivity(intent);
                    ((PostsActivity) context).overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });

        }
    }
}

