package com.example.administrator.myme.recycleview;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.myme.R;

public class MainRycAdapter extends BaseRycAdapter<MainRycAdapter.MainViewHolder, BaseData> {


    //	public class BaseData implements Serializable
    //	{
    //		private String baseDataStr = "";
    //
    //		public String getBaseDataStr()
    //		{
    //			return baseDataStr;
    //		}
    //
    //		public void setBaseDataStr(String baseDataStr)
    //		{
    //			this.baseDataStr = baseDataStr;
    //		}
    //	}

    /**
     * 设置正常数据
     *
     * @param holder   holder
     * @param position position
     */
    @Override
    void onBindViewData(MainViewHolder holder, int position) {
        BaseData dataStr = dataLists.get(position);
        holder.textView.setText(dataStr.getBaseDataStr());
    }

    /**
     * itemView layout
     */
    @Override
    public int getLayoutResId() {
        return R.layout.item_gride;
    }

    /**
     * ViewHolder
     */
    class MainViewHolder extends BaseRycAdapter.BaseViewHolder {
        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void initItemView(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.tv_lunarCalendar);
        }

    }
}
