package com.example.xiaoli.amusementpark.ui;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.ui
 *    文件名：    MyDetailActivity
 *    创建者：    XiaoLi
 *    创建时间：  2017/4/30  17:23
 *    描述：       个人信息页面
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoli.amusementpark.R;
import com.example.xiaoli.amusementpark.entity.DataEvent;
import com.example.xiaoli.amusementpark.utils.L;
import com.example.xiaoli.amusementpark.utils.PicassoUtils;
import com.example.xiaoli.amusementpark.utils.ShareUtils;
import com.example.xiaoli.amusementpark.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDetailActivity extends AppCompatActivity implements View.OnClickListener {
    //toolbar
    private TextView title_bar;
    private ImageView iv_back;

    private RelativeLayout mRelative1;
    private CircleImageView user_img;
    private CustomDialog dialog;
    //3个按钮
    private TextView tv_album,tv_photo,tv_cancle;
    //回掉结果
    public static final int CAMERA_REQUEST_CODE=100;
    public static final int PICTURE_REQUEST_CODE=101;
    public static final int RESULT_REQUEST_CODE=102;
    //个人信息textview
    private TextView tv_name,tv_phone;

    //保存拍照上传的图片
    private File temFile;
    private String  userimage_name;
    //图片路径
    private String imagePath;
    //拍照图片存储
    private String fileName;
    private Bundle bundle;
    // 文件Bitmap
    private Bitmap bitmap;
    //图片bitmap
    private Bitmap imageBitmap;
    //保存字节数组输出流转化成的字符串
    private String encoding_string;
    //线程
    private Mythread mythread=new Mythread();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_detail);
        //new RxVolley.Builder().httpMethod(RxVolley.Method.POST).shouldCache(false);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        userimage_name=ShareUtils.getString(this,"user_name","user");
        temFile = new File("/sdcard/Image/");
        if(!temFile.exists()){
            temFile.mkdirs();// 创建文件夹
        }
        fileName = "/sdcard/Image/"+userimage_name+".jpg";
       initView();

    }

    private void initView() {
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phone= (TextView) findViewById(R.id.tv_phone);

        title_bar= (TextView) findViewById(R.id.title_bar);
        title_bar.setText("个人信息");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        //头像那行
        mRelative1= (RelativeLayout) findViewById(R.id.mRelative1);
        mRelative1.setOnClickListener(this);
        user_img= (CircleImageView) findViewById(R.id.user_img);
        initData();
        //PicassoUtils.loadImageView(this,ShareUtils.getString(this,"imageurl",""),user_img);
        //弹出框
        dialog=new CustomDialog(this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                R.layout.dialog_photo,R.style.Theme_dialog,Gravity.BOTTOM,R.style.pop_anim_style );
        //屏幕外点击无效
        dialog.setCancelable(false);
        //相册
        tv_album= (TextView) dialog.findViewById(R.id.tv_album);
        tv_album.setOnClickListener(this);
        //拍照
        tv_photo= (TextView) dialog.findViewById(R.id.tv_photo);
        tv_photo.setOnClickListener(this);
        //取消
        tv_cancle= (TextView) dialog.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(this);
    }

    private void initData() {
        Intent intent=getIntent();
        tv_name.setText(intent.getStringExtra("user_nickname"));
        tv_phone.setText(intent.getStringExtra("md_username"));
        PicassoUtils.loadImageView(this,intent.getStringExtra("imageurl"),user_img);
        setResult(RESULT_CANCELED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回按钮
            case R.id.iv_back:
                finish();
                break;
            //头像那行
            case R.id.mRelative1:
                dialog.show();
                break;
            //相册
            case R.id.tv_album:
                toPicture();
                break;
            //拍照
            case R.id.tv_photo:
                toCamera();
                break;
            case R.id.tv_cancle:
                dialog.dismiss();
                break;
        }
    }
    //裁剪方法
    private void startPhotoZoom(Uri uri){
        if (uri==null){
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("sapectY",1);
        //裁剪质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }
    private void toCamera() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就存储
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),userimage_name)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    private void toPicture() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICTURE_REQUEST_CODE);
        dialog.dismiss();
    }
//    private void setImageToView(Intent data){
//        Bundle bundle= data.getExtras();
//        if (bundle!=null){
//            Bitmap bitmap= bundle.getParcelable("data");
//            user_img.setImageBitmap(bitmap);
//        }
//    }
    //回掉
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode!=this.RESULT_CANCELED){
            switch (requestCode){
                //相机数据
                case CAMERA_REQUEST_CODE:
//                    temFile=new File(Environment.getExternalStorageDirectory(),userimage_name);
//                    imagePath=temFile.getPath();
//                    startPhotoZoom(Uri.fromFile(temFile));
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        return;
                    }
                   if (data!=null){
                       bundle = data.getExtras();
                       imageBitmap= (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                       FileOutputStream b = null;
                       imagePath=fileName;
                       try {
                           b = new FileOutputStream(fileName);
                           imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                       } catch (FileNotFoundException e) {
                           e.printStackTrace();
                       } finally {
                           try {
                               b.flush();
                               b.close();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                       user_img.setImageBitmap(imageBitmap);
                       mythread.run();
                   }
                    break;
                //相册数据
                case PICTURE_REQUEST_CODE:
                    //必须要加判断
                    if(data!=null){
                        startPhotoZoom(data.getData());
                        imagePath=data.getData().toString().substring(7);
                        Log.e("TAG",imagePath);
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍去
                    if (data!=null){
                        //拿到图片设置
                        //setImageToView(data);
                        bundle= data.getExtras();
                        if (bundle!=null){
                            imageBitmap= bundle.getParcelable("data");
                            user_img.setImageBitmap(imageBitmap);
                        }
//                        删除原先的图片
//                        if (temFile!=null){
//                            temFile.delete();
//                        }
                        mythread.run();
                    }
                    break;
            }
        }
    }

    //   线程  将图片转化->字节数组输出流->String
    class Mythread implements Runnable{
        @Override
        public void run() {
            bitmap = BitmapFactory.decodeFile(imagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] array = stream.toByteArray();
                encoding_string= Base64.encodeToString(array, 0);
                bitmap.recycle();  //防止oom
                //上传图片
                uploadImage();
                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                    Log.e("TAG",e.toString());
                }
            }
        }
    }

    private void uploadImage() {
        //post请求简洁版实现
        HttpParams params = new HttpParams();
        params.put("encoding_string",encoding_string);
        params.put("user_name",userimage_name);
        RxVolley.post("http://120.25.249.201/sql/upimage.php", params, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.e(t);
                parseJson(t);

            }
        });
    }
    //解析Json
    private void parseJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            int  result=jsonObject.getInt("resultCode");
            if (result>0){
                Toast.makeText(this,"修改成功!",Toast.LENGTH_SHORT).show();
                //EventBus.getDefault().post(new DataEvent());
                ShareUtils.putBoolean(getApplicationContext(),"isUp",true);
            }
            else {
                Toast.makeText(this,"修改失败!",Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageBitmap!=null){
            imageBitmap.recycle();
        }
    }
}
