package cool.android.english.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import cool.android.english.R;
import cool.android.english.base.BaseRVAdapter;
import cool.android.english.base.CCApplication;
import cool.android.english.base.IViewHolder;
import cool.android.english.bean.VideoBean;

public class VideoAdapter extends BaseRVAdapter<VideoBean, VideoAdapter.VideoAdapterHolder> {

    @Override
    protected VideoAdapterHolder doCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new VideoAdapterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_adapter, viewGroup, false));
    }

    @Override
    protected void bindItemData(VideoAdapterHolder viewHolder, VideoBean listener, int position) {
        viewHolder.bindView(listener, position);
    }

    public class VideoAdapterHolder extends RecyclerView.ViewHolder implements IViewHolder<VideoBean> {

        @BindView(R.id.tv_title_item_video)
        TextView mTitle;
        @BindView(R.id.tv_introduce_item_video)
        TextView mIntroduce;
        @BindView(R.id.ll_read_all)
        LinearLayout mReadAll;

        public VideoAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(VideoBean videoBean, int position) {
            mTitle.setText(videoBean.getTitle());
            mIntroduce.setText(videoBean.getIntroduce());
            Glide.with(CCApplication.getInstance())
                    .load(videoBean.getCover())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mReadAll.setBackground(new BitmapDrawable(resource));
                        }
                    });
        }
    }
}

