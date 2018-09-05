package com.example.administrator.myme.datacalendar.Adapter;

/**
 * Created by zhq_zhao on 2017/12/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.myme.datacalendar.Holder.AllUseViewHolder;

import java.util.List;


/**
 * Created by zhq_zhao on 2017/8/15.
 */
public abstract class AllUseAdapter<T> extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<T> list;//数据源
    private Context context;
    private int itemlayoutId;

    //在构造方法中传入数据源
    public AllUseAdapter(Context context, List<T> list, int itemlayoutId) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemlayoutId = itemlayoutId;
    }

    //在构造方法中传入数据源
    public AllUseAdapter(Context context, List<T> list, int itemlayoutId,int year, int month, int day) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemlayoutId = itemlayoutId;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AllUseViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, (T) getItem(position));
        convert(viewHolder, (T) getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected abstract void convert(AllUseViewHolder viewHolder, T item, int position);

    protected  void convert(AllUseViewHolder viewHolder, T item){}

    private AllUseViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return AllUseViewHolder.get(context, convertView, parent, itemlayoutId, position);
    }
}