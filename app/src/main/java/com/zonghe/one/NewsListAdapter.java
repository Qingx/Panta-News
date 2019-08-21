package com.zonghe.one;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.itemViewHolder> {
    private List<NewsResult.News> mNewsList;
    private ViewGroup mContext;
    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public NewsListAdapter(ViewGroup mContext, List<NewsResult.News> mNewsList) {
        this.mContext= mContext;
        this.mNewsList = mNewsList;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = View.inflate(mContext.getContext(),R.layout.item_one_img,null);

        return new itemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder itemViewHolder, final int i) {
        final NewsResult.News everyNews = mNewsList.get(i);
        itemViewHolder.TV_title.setText(everyNews.getTitle());
        itemViewHolder.TV_Author_name.setText(everyNews.getAuthor_name());
        //使用Glide 来加载网络图片。。
        Glide.with(mContext).load(everyNews.getThumbnail_pic_s()).into(itemViewHolder.IM_Thumbnail_pic_s);

        itemViewHolder.mitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用接口的回调方法
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(i);
                }
            }
        });

    }


    class itemViewHolder extends RecyclerView.ViewHolder{
        private TextView TV_title;
        private TextView TV_Author_name;
        private ImageView IM_Thumbnail_pic_s;
        View mitemView;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_title = itemView.findViewById(R.id.itemTitle);
            TV_Author_name=itemView.findViewById(R.id.itemAuthorName);
            IM_Thumbnail_pic_s = itemView.findViewById(R.id.item_one_img1);
            mitemView=itemView;
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
    /**
     * 定义点击item事件的回调接口
     */


    interface onItemClickListener{
        //回调方法
        void onItemClick(int position);
    }
}
