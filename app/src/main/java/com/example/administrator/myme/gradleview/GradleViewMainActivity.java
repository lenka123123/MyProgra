package com.example.administrator.myme.gradleview;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.administrator.myme.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradleViewMainActivity extends Activity {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradle_activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();

        String[] from = {"img", "text"};

        int[] to = {R.id.img, R.id.text};

        adapter = new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String s) {
                //判断是否为我们要处理的对象
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }else
                    return false;
            }
        });

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GradleViewMainActivity.this);
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
            }
        });
    }

    void initData() {
        //图标
        int icno[] = {R.drawable.ic_done_gray, R.drawable.ic_done_gray, R.drawable.ic_done_gray,
                R.drawable.ic_done_gray, R.drawable.ic_done_gray, R.drawable.ic_done_gray, R.drawable.ic_done_gray,
                R.drawable.ic_done_gray, R.drawable.ic_done_gray, R.drawable.ic_done_gray, R.drawable.ic_done_gray,   };
        //图标下的文字
        String name[] = {"时钟", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }


}
