package com.example.administrator.myme.recycleview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myme.R;

import java.util.ArrayList;

public class RecycleViewMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_act);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ArrayList<BaseData> baseDatas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BaseData baseData = new BaseData();
            baseData.setBaseDataStr(("data" + i));
            baseDatas.add(baseData);
        }

        final MainRycAdapter myAdapter = new MainRycAdapter();
        myAdapter.setDataLists(baseDatas);

        TextView head = new TextView(this);
        head.setText("headheadheadhead");
        head.setGravity(Gravity.CENTER);
        head.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130));

        TextView foot = new TextView(this);
        foot.setText("footfootfoot");
        foot.setGravity(Gravity.CENTER);
        foot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        myAdapter.addHeaderView(head);
        myAdapter.addHeaderView(head);
        myAdapter.addHeaderView(head);


        myAdapter.setOnItemClickListener(new BaseRycAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("TAG", "click position = " + position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(myAdapter);

//        new CountDownTimer(2000, 2000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                int headersCount = myAdapter.getHeadersCount();
//                for (int i = 0; i < headersCount; i++) {
//                    View headerView = myAdapter.getHeaderView(i);
//                    if (i != 0) {
//                        headerView.setBackgroundColor(Color.RED);
//                        ViewGroup.LayoutParams layoutParams = headerView.getLayoutParams();
//                        layoutParams.height = 200;
//                        headerView.setLayoutParams(layoutParams);
//                        ((TextView) headerView).setText("延时操作更改文本显示 - - index = " + i);
//                    }
//                }
//                myAdapter.removeHeaderView(0);
//                myAdapter.notifyItemRemoved(0);
//            }
//        }.start();

    }
}
