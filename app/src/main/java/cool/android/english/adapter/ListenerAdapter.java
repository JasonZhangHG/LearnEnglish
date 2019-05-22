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
import cool.android.english.bean.Listener;

public class ListenerAdapter extends BaseRVAdapter<Listener, ListenerAdapter.ListenerAdapterHolder> {

    @Override
    protected ListenerAdapterHolder doCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ListenerAdapterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listener_adapter, viewGroup, false));
    }

    @Override
    protected void bindItemData(ListenerAdapterHolder viewHolder, Listener listener, int position) {
        viewHolder.bindView(listener, position);
    }

    public class ListenerAdapterHolder extends RecyclerView.ViewHolder implements IViewHolder<Listener> {

        @BindView(R.id.tv_title_item_listener)
        TextView mTitle;
        @BindView(R.id.tv_introduce_item_listener)
        TextView mIntroduce;
        @BindView(R.id.ll_read_all)
        LinearLayout mReadAll;

        public ListenerAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Listener book, int position) {
            mTitle.setText(book.getTitle());
            mIntroduce.setText(book.getIntroduce());

            Glide.with(CCApplication.getInstance())
                    .load(book.getCover())
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

