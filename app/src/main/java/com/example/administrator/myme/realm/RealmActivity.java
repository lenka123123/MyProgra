package com.example.administrator.myme.realm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myme.R;
import com.example.administrator.myme.listdialog.ChangeISNOPicker;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.util.ArrayList;
import java.util.List;

public class RealmActivity extends AppCompatActivity {

    Bitmap bitmap = null;
    private ImageView img;
    String stringimg = "http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg";

    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/good/savePic";//保存的确切位置
    private String path;
    private List list = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_activity);
        final ChangeISNOPicker changeISNOPicker = new ChangeISNOPicker(RealmActivity.this);
        for (int i = 0; i < 12; i++) {
            list.add("22" + i);
        }

        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeISNOPicker.showAlertDialog(list);
            }
        });
        changeISNOPicker.setAlertOnClickListener(new ChangeISNOPicker.AlertOnClickListener() {
            @Override
            public void alertClick(String age) {
                Toast.makeText(RealmActivity.this, age, Toast.LENGTH_LONG).show();
            }
        });

        //图片选择
//        Intent intent = new Intent(this, PhotoSelectorActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        intent.putExtra("limit", 1);//number是选择图片的数量
//        startActivityForResult(intent, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    //处理代码
                    for (int i = 0; i < paths.size(); i++) {
                        System.out.println("===paths===" + paths.get(i));
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}