package com.zonghe.one;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zonghe.one.JSONNewsEntityClass.Contentlist;
import com.zonghe.one.JSONNewsEntityClass.Pagebean;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class NewsRecyclerListAdapter2 extends RecyclerView.Adapter<NewsRecyclerListAdapter2.itemViewHolder> {
    private List<Contentlist> mContentlists;
    private Context mContext;
    private int image_number=0;
    private static String TAG ="NewsRecyclerListAdapter2";
    private NewsListAdapter.onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(NewsListAdapter.onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public NewsRecyclerListAdapter2(Context context, List<Contentlist> contentlists) {
        mContentlists =contentlists;
        mContext = context;
        Log.d(TAG, "NewsRecyclerListAdapter2: first"+mContentlists);
    }

    @Override
    public int getItemViewType(int position) {
        image_number = mContentlists.get(position).getImageurls().size();
        switch (image_number){
            case 0:return 0;
            case 1:return 1;
            case 2:return 2;
            case 3:return 3;
            default: return 3;
        }
    }

    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i){
            case 0:View itemView0 = View.inflate(viewGroup.getContext(),R.layout.item_none_img,null);return  new itemViewHolder(itemView0);
            case 1:View itemView1 = View.inflate(viewGroup.getContext(),R.layout.item_one_img,null);return  new itemViewHolder(itemView1);
            case 2:View itemView2 = View.inflate(viewGroup.getContext(),R.layout.item_two_img,null);return  new itemViewHolder(itemView2);
            case 3:View itemView3 = View.inflate(viewGroup.getContext(),R.layout.item_three_img,null);return  new itemViewHolder(itemView3);
            default:return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull itemViewHolder itemViewHolder, final int i) {
                itemViewHolder.itemNewstitle.setText(mContentlists.get(i).getTitle());
                itemViewHolder.itemNewsAuthor.setText(mContentlists.get(i).getSource());


        if (image_number!=0){
            switch (image_number){
                case 1:Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(0).getUrl()).into(itemViewHolder.itemOneImage1);break;

                case 2:Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(0).getUrl()).into(itemViewHolder.itemTwoImage1);
                        Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(1).getUrl()).into(itemViewHolder.itemTwoImage2);break;

                case 3:Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(0).getUrl()).into(itemViewHolder.itemThreeImage1);
                        Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(1).getUrl()).into(itemViewHolder.itemThreeImage2);
                        Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(2).getUrl()).into(itemViewHolder.itemThreeImage3);break;

                default:Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(0).getUrl()).into(itemViewHolder.itemThreeImage1);
                Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(1).getUrl()).into(itemViewHolder.itemThreeImage2);
                Glide.with(mContext).load(mContentlists.get(i).getImageurls().get(2).getUrl()).into(itemViewHolder.itemThreeImage3);break;
            }
        }

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

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: "+mContentlists.size());
        return mContentlists.size();

    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        private TextView itemNewstitle;
        private TextView itemNewsAuthor;
        private ImageView itemOneImage1;
        private ImageView itemTwoImage1;
        private ImageView itemTwoImage2;
        private ImageView itemThreeImage1;
        private ImageView itemThreeImage2;
        private ImageView itemThreeImage3;
        View mitemView;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            mitemView = itemView;
            itemNewstitle =itemView.findViewById(R.id.itemTitle);
            itemNewsAuthor =itemView.findViewById(R.id.itemAuthorName);
            itemOneImage1 = itemView.findViewById(R.id.item_one_img1);
            itemTwoImage1 = itemView.findViewById(R.id.item_two_img1);
            itemTwoImage2 = itemView.findViewById(R.id.item_two_img2);
            itemThreeImage1 = itemView.findViewById(R.id.item_three_img1);
            itemThreeImage2 =itemView.findViewById(R.id.item_three_img2);
            itemThreeImage3 =itemView.findViewById(R.id.item_three_img3);
        }
    }
    interface onItemClickListener{
        //回调方法
        void onItemClick(int position);
    }
}
