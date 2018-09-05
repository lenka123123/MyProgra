package com.example.administrator.myme.recycleview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract  class BaseRycAdapter<VH extends BaseRycAdapter.BaseViewHolder, T> extends RecyclerView.Adapter<VH>
{

    private Context baseCxt;
    protected List<T> dataLists;

    //默认itemView 点击事件开关
    private boolean itemViewClickToggle = true;
    // 设置大是为避免与itemType类型多时导致可能出现重复
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> headerViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> footerViews = new SparseArrayCompat<>();
    private ViewGroup mViewGroup;

    public void addHeaderView(View view)
    {
        headerViews.put(headerViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view)
    {
        footerViews.put(footerViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public View getHeaderView(int index)
    {
        return (index >= 0 && headerViews.size() > index) ? headerViews.valueAt(index) : null;
    }

    public View getFooterView(int index)
    {
        return (index >= 0 && footerViews.size() > index) ? footerViews.valueAt(index) : null;
    }

    public void removeHeaderView(int index)
    {
        headerViews.removeAt(index);
    }

    public void removeFooterView(int index)
    {
        footerViews.removeAt(index);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(isHeaderViewPos(position))
            return headerViews.keyAt(position);
        else if(isFooterViewPos(position))
            return footerViews.keyAt(getFootersCount() + position - getItemCount());
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount()
    {
        return headerViews.size() + dataLists.size() + footerViews.size();
    }

    abstract class BaseViewHolder extends RecyclerView.ViewHolder
    {

        BaseViewHolder(View itemView)
        {
            super(itemView);
            if(isNormalItemView(itemView))
                initItemView(itemView);
        }

        abstract void initItemView(View itemView);
    }

    @SuppressWarnings("WeakerAccess")
    private boolean isNormalItemView(View itemView)
    {
        return !(headerViews.indexOfValue(itemView) != -1 || footerViews.indexOfValue(itemView) != -1);
    }

    /**
     * GridLayoutManager布局时“Head”和“Foot”处理
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridManager = ((GridLayoutManager)manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    return getItemViewType(position) >= BASE_ITEM_TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * StaggeredGridLayoutManager布局时“Head”和“Foot”处理
     */
    @Override
    public void onViewAttachedToWindow(VH holder)
    {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)lp;
            p.setFullSpan(getItemViewType(holder.getLayoutPosition()) >= BASE_ITEM_TYPE_HEADER);
        }
    }

    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position)
    {
        return position >= getItemCount() - getFootersCount();
    }

    public int getHeadersCount()
    {
        return headerViews.size();
    }

    public int getFootersCount()
    {
        return footerViews.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mViewGroup = parent;
        baseCxt = parent.getContext();
        return getVHolder(viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position)
    {
        //判断位置是头或者尾部则直接返回
        if(getItemViewType(position) >= BASE_ITEM_TYPE_HEADER)
            return;
        if(onItemClickListener != null && itemViewClickToggle)
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(itemViewClickToggle)
                        onItemClickListener.onItemClick(holder.itemView, (holder.getLayoutPosition() - headerViews.size()));
                }
            });

        onBindViewData(holder, (holder.getLayoutPosition() - headerViews.size()));
    }

    /**
     * 子类通过该方法设置item显示数据
     *
     * @param holder   泛型VH
     * @param position item position
     */
    abstract void onBindViewData(VH holder, int position);

    /**
     * 反射获取ViewHolder
     *
     * @param viewType itemView 类型
     * @return 对应类型ViewHolder
     */
    @SuppressWarnings("unchecked")
    private VH getVHolder(int viewType)
    {
        try
        {
            View itemView;
            if(headerViews.get(viewType) != null)
                itemView = headerViews.get(viewType);
            else if(footerViews.get(viewType) != null)
                itemView = footerViews.get(viewType);
            else
            {
                LayoutInflater inflater = LayoutInflater.from(baseCxt);
                itemView = inflater.inflate(getLayoutResId(), mViewGroup, false);
            }
            Class aClass = getClass();
            Type genType = aClass.getGenericSuperclass();
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            Class vhClass = (Class)params[0];
            Constructor constructor = vhClass.getDeclaredConstructor(aClass, View.class);
            return (VH)constructor.newInstance(this, itemView);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取itemView，子类实现
     */
    public abstract int getLayoutResId();

    public List<T> getDataLists()
    {
        return dataLists;
    }

    public void setDataLists(List<T> dataLists)
    {
        this.dataLists = dataLists;
    }

    //点击监听，可自行添加长按
    interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public boolean isItemViewClickToggle()
    {
        return itemViewClickToggle;
    }

    public void setItemViewClickToggle(boolean itemViewClickToggle)
    {
        this.itemViewClickToggle = itemViewClickToggle;
    }

    public OnItemClickListener getOnItemClickListener()
    {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

}
