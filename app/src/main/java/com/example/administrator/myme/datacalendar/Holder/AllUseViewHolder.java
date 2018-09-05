package com.example.administrator.myme.datacalendar.Holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhq_zhao on 2017/12/14.
 */
public class AllUseViewHolder {
    private final SparseArray<View> viewSparseArray;
    private View mConvertView;//创建复用的布局对象
    private int mPosition;
    public AllUseViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position) {
        //用SparseArray存储view
        this.viewSparseArray = new SparseArray<>();
        this.mPosition=position;
        //创建具体的view布局
        mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        //利用复用布局对象和viewholder里面的控件进行绑定
        mConvertView.setTag(this);//this指的就是viewholder对象里面控件id的绑定
    }
    /**
     * 拿到viewholder对象可以单选多线颜色状态等等变化的方法封装
     *
     * @param context
     * @param convertView
     * @param viewGroup
     * @param layoutId
     * @param postion
     * @return
     */
    public static AllUseViewHolder get(Context context, View convertView, ViewGroup viewGroup, int layoutId, int postion) {
        if (convertView == null) {
            return new AllUseViewHolder(context, viewGroup, layoutId, postion);
        } else {//说明布局对象已存在，直接取出来viewholder控件绑定信息的对象
            return (AllUseViewHolder) convertView.getTag();
        }
    }
    /**
     * 封装一个方法为外面类调用拿到view控件id去做任何操作,通过控件的Id获取对于的控件，如果没有则加入viewSparseArray
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        //首先判断当前控件是否已经存入
        View view = viewSparseArray.get(viewId);
        if (view == null) {//说明没有存入进去
            //先找到控件id
            view = mConvertView.findViewById(viewId);
            viewSparseArray.put(viewId, view);//viewid是控件的id，item里面控件id都是不一样的所以可以当做key来使用
        }
        //说明已经存在，直接拿出来，这个也是viewholder的好处，省去好多重复去查询控件id次数
        return (T) view;//返回控件id的引用，拿到引用可以设置颜色状态文字等等
    }
    public View getConvertView() {
        return mConvertView;
    }
    //一般操作都是textview，imageview，button，checkbox等
    /**
     * 为TextView设置字符串
     *
     * @param viewid
     * @param text
     * @return
     */
    public AllUseViewHolder setText(int viewid, Object text) {
        TextView textView = getView(viewid);
        textView.setText(""+text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public AllUseViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public AllUseViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }


    /**隐藏文字
     *
     * @param viewId
     */
    public void setHideText(int viewId){
        TextView textView=getView(viewId);
        textView.setVisibility(View.GONE);
    }

    /**显示文字
     *
     * @param viewId
     */
    public void setShowText(int viewId){
        TextView textView=getView(viewId);
        textView.setVisibility(View.VISIBLE);
    }
    /**隐藏图片
     *
     * @param viewId
     */
    public void setHidePicture(int viewId){
        ImageView view = getView(viewId);
        view.setVisibility(View.GONE);
    }

    /**显示图片
     *
     * @param viewId
     */
    public void showPicture(int viewId){
        ImageView view = getView(viewId);
        view.setVisibility(View.VISIBLE);
    }
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public AllUseViewHolder setImageBitmapTag(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
    /**
     * 设置圆形图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public AllUseViewHolder setImageByUrl(int viewId, String url) {
        //   Glide.with(MyApplication.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.user_default).into((ImageView) getView(viewId));
      //  Glide.with(MyApplication.context).load(url).transform(new GlideCircleTransform(MyApplication.context)).into((ImageView) getView(viewId));
        return this;
    }

    /**设置正常的图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public AllUseViewHolder setImageByUrlOfNormol(int viewId, String url) {
        // Glide.with(MyApplication.context).load(detail.get(position).getPictureUrl()).transform(new CenterCrop(MyApplication.context),new GlideRoundTransform(MyApplication.context,5)).into(holder.iv_imageview_talls);
        //Glide.with(MyApplication.context).load(url).into((ImageView) getView(viewId));
        return this;
    }
    public AllUseViewHolder setBackground(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
    public int getPosition() {
        return mPosition ;

    }

}