package com.xk.trucktrade.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.ui.custom.OptionItem2View;
import com.xk.trucktrade.utils.Base64Util;
import com.xk.trucktrade.utils.PersistenceUtil;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.ViewUtils;
import com.xk.trucktrade.utils.XmlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * Created by xk on 2016/7/30 12:05.
 */
public class ChangeMyInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_SELECT_EXACT_POSITION_OK = 0;
    private static final int RESULT_SELECT_EXACT_POSITION_CANCEL = 1;
    private static final int REQUEST_INTRODUCE = 0;
    private static final int REQUEST_NAME = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_GALLERY = 3;
    private static final int REQUEST_CROP= 4;
    private static final int RESULT_INTRODUCE_SAVE = 0;
    private static final int RESULT_INTRODUCE_BACK = 1;
    private static final int RESULT_NAME_SAVE = 2;
    private static final int RESULT_NAME_BACK = 3;

    private static final int RESULT_SELECT_TRUCK_BACK = 0;
    private static final int RESULT_SELECT_TRUCK_SAVE = 1;

    private static final int REQUEST_SELECT_TRUCK = 1;
    private static final int REQUEST_SELECT_EXACT_POSITION = 2;
    private static final int NOHTTP_REQUEST_UPLOADHEAD = 1;
    private static final int NOHTTP_REQUEST_UPDATEUSERINFO = 2;
    private LinearLayout ll_get_head;
    private Button btn_camera;
    private Button btn_gallery;
    private Button btn_cancel;
    private MToolbar toolbar;
    private OptionItem2View oi2v_introduce, oi2v_head, oi2v_name, oi2v_area;
    private FrameLayout fl_other;
    private String introduce="";
    private String name="无名小卒";
    private String area="";
    private String headimg="";
    private Bitmap headPicbitmap=null;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_change_myinfo);
    }

    @Override
    protected void findViews() {
        oi2v_introduce = ViewUtils.findViewById(this, R.id.oi2v_introduce);
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        oi2v_head = ViewUtils.findViewById(this, R.id.oi2v_head);
        oi2v_name = ViewUtils.findViewById(this, R.id.oi2v_name);
        oi2v_area = ViewUtils.findViewById(this, R.id.oi2v_area);
        ll_get_head = ViewUtils.findViewById(this, R.id.ll_get_head);
        btn_camera = ViewUtils.findViewById(this, R.id.btn_camera);
        btn_gallery = ViewUtils.findViewById(this, R.id.btn_gallery);
        fl_other = ViewUtils.findViewById(this, R.id.fl_other);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("编辑个人信息");
        toolbar.setRightTextView("保存");
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {

            }
        });

        toolbar.setOnTextViewClickListener(new MToolbar.OnTextViewClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                toast("保存");
                //保存数据
                //1.上传图片 返回一个uri （正在上传头像）
                //先要判断bitmap是否为空 再决定是否要上传头像 以后如果没有url 就根据name来制作头像
                if (headPicbitmap != null) {
                    sendImage(headPicbitmap);
                }else{
                    //上传userInfo
                    requestUpdateUserInfo();
                }
            }
        });
        oi2v_introduce.setOnClickListener(this);
        oi2v_head.setOnClickListener(this);
        oi2v_name.setOnClickListener(this);
        oi2v_area.setOnClickListener(this);
        ll_get_head.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        fl_other.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oi2v_introduce:
                toActivityForResult(IntroduceActivity.class, REQUEST_INTRODUCE);
                break;
            case R.id.oi2v_head:
                showHeadTool();
                break;
            case R.id.oi2v_name:
                toActivityForResult(NameActivity.class, REQUEST_NAME);
                break;
            case R.id.oi2v_area:
                selectArea();
                break;
            case R.id.ll_get_head:
                break;
            case R.id.btn_camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 摄像头
                startActivityForResult(intent, REQUEST_CAMERA);
                break;
            case R.id.btn_gallery:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);// 内容选择的Intent
                intent2.setType("image/*");// 内容选择类型为图像类型
                startActivityForResult(intent2, REQUEST_GALLERY);
                break;

            case R.id.fl_other:
                ll_get_head.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 显示选择头像的工具栏
     */
    private void showHeadTool() {
        if (ll_get_head.getVisibility() == View.VISIBLE) {
            ll_get_head.setVisibility(View.GONE);
        } else {
            ll_get_head.setVisibility(View.VISIBLE);
        }
    }


    private void selectArea() {
        AddressPicker picker = new AddressPicker(this, XmlUtils.parseArea(this, "china_area.xml"));
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                oi2v_area.setRightText((city + county).trim());
            }
        });
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ll_get_head.setVisibility(View.GONE);

        if (requestCode == REQUEST_INTRODUCE) {
            if (resultCode == RESULT_INTRODUCE_SAVE) {
                //添加备注信息
                introduce = data.getStringExtra("introduce");
                if (introduce.length() > 0) {
                    oi2v_introduce.setRightText(introduce);
                }
            }
        } else if (requestCode == REQUEST_NAME) {
            if (resultCode == RESULT_NAME_SAVE) {
                //修改姓名
                name = data.getStringExtra("name");
                if (name.length() > 0) {
                    oi2v_name.setRightText(name);
                }
            }
        } else if (requestCode == REQUEST_CAMERA) {
            // 通过data是否为空来判断用户是否拍照
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bitmap = extras.getParcelable("data");
                Uri bmUri = saveBitmap(bitmap);
                startImageZoom(bmUri);
            }
        } else if (requestCode == REQUEST_GALLERY) {
            if (data == null) {
                return;
            }
            // 定义一个URI来保存返回过来的URI
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        } else if (requestCode == REQUEST_CROP) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            headPicbitmap = bundle.getParcelable("data");
            showOnOptionView(headPicbitmap);
        }
    }
    //发送图像数据
    private void sendImage(final Bitmap bitmap){
        //发送之前首先显示出来
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[]bytes = baos.toByteArray();
        //Base64的编码
        String imgCode = Base64Util.encode(bytes);
//        String encode = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        Request<String> urlRequest = NoHttp.createStringRequest(Constant.url_upload_head, RequestMethod.POST);
        urlRequest.add("userphonenumber", SharedPreferencesUtil.getString(this,Constant.SPKEY_CURRENTUSERPHONENUMBER));
        urlRequest.add("imgcode",imgCode);
        CallServer.getRequestInstance().add(this, NOHTTP_REQUEST_UPLOADHEAD, urlRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String s = response.get();
                if (s != null) {
                    String url=Constant.url_base_headimg+s;
                    headimg=url;
                    //开始上传数据了
                    requestUpdateUserInfo();
                }else{
                    toast("头像上传失败");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                toast("头像上传失败");
            }
        },true,true,"正在上传头像");
    }

    private void requestUpdateUserInfo() {
        //上传userinfo  成功后 finish
        Request<JSONObject> updateInfoRequest = NoHttp.createJsonObjectRequest(Constant.url_update_userinfo, RequestMethod.POST);
        updateInfoRequest.add("phoneNumber",SharedPreferencesUtil.getString(this,Constant.SPKEY_CURRENTUSERPHONENUMBER));
        updateInfoRequest.add("username",name);
        updateInfoRequest.add("headimg",headimg);
        updateInfoRequest.add("area",area);
        updateInfoRequest.add("introduce",introduce);
        CallServer.getRequestInstance().add(this, NOHTTP_REQUEST_UPDATEUSERINFO, updateInfoRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                JSONObject json = response.get();
                Log.e("ChangeMyInfoActivity",json.toString());
                try {
                    boolean state = json.getBoolean("state");
                    if(state){
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("username",name);
                        jsonObject.put("city",area);
                        jsonObject.put("avatarurl",headimg);
                        jsonObject.put("introduce",introduce);
                        PersistenceUtil.saveStringToFile(SharedPreferencesUtil.getString(ChangeMyInfoActivity.this,Constant.SPKEY_CURRENTUSERPHONENUMBER),jsonObject.toString());
                        finish();
                    }else{
                        toast("保存失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                toast("保存失败");
            }
        },true,true,"正在保存...");

    }

    // 图像裁剪
    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// 设置数据和类型
        intent.putExtra("crop", true);// 在开启的intent中设置的View是可裁剪的
        intent.putExtra("aspectX", 1);// 裁剪的宽的比例
        intent.putExtra("aspectY", 1);// 裁剪的高的比例
        intent.putExtra("outputX", 50);// 裁剪的宽
        intent.putExtra("outputY", 50);// 裁剪的高
        intent.putExtra("return-data", true);// 裁剪后的数据是通过intent返回的
        startActivityForResult(intent, REQUEST_CROP);
    }

    private Uri saveBitmap(Bitmap bitmap) {
        // 获取到SD卡中的一个路径
        File tmpDir = new File(Environment.getExternalStorageDirectory()
                + "/com.xk");
        // 判断该路径是否存在
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        // 创建要保存的文件对象
        File img = new File(tmpDir.getAbsolutePath() + "temp_head.png");
        // 获取该文件的输出流
        try {
            FileOutputStream fos = new FileOutputStream(img);
            /**
             * 将图像的数据写入该输出流中 format:要压缩的格式 quality:图片的质量 stream:要写入的文件输出流
             */
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void showOnOptionView(Bitmap bitmap){
        oi2v_head.setRightImg(bitmap);
    }
}
