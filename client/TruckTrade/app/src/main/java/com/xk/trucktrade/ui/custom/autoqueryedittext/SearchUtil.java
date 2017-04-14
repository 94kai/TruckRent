package com.xk.trucktrade.ui.custom.autoqueryedittext;

import android.content.Context;
import android.widget.EditText;

import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xk on 2016/8/8 2:02.
 */
public class SearchUtil {
    private String currentSearchContent;
    private Map<String, String> searchMap;
    private EditText editText;
    private Context context;

    public SearchUtil(EditText editText, Context context) {
        searchMap = new HashMap<>();
        this.editText = editText;
        this.context = context;
    }

    public String getCurrentSearchContent() {
        return currentSearchContent;
    }

    public void setCurrentSearchContent(String currentSearchContent) {
        this.currentSearchContent = currentSearchContent;
    }

    /**
     * 在线程中进行
     *
     * @param searchContent
     */
    public void search(final String searchContent) {
        currentSearchContent = searchContent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (searchMap.get(searchContent) != null && !searchMap.get(searchContent).equals("")) {
                    //已经查询除了这个值，直接回调方法中把这个值传回去
                    if (currentSearchContent.equals(searchContent)) {
                        //回调
                        String result = searchMap.get(searchContent);
                        if (onSearchListener != null) {
                            onSearchListener.onSearchSuccess(result);
                        }
                    }
                } else {
                    //曾经没有查询过这个词，但是不会立即去查 等待200毫秒之后再去查
                    try {
                        Thread.sleep(200);
                        requestSearchContent(searchContent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void requestSearchContent(final String searchContent) {
        if (searchContent.equals("")) {
            if (onSearchListener != null) {
                onSearchListener.onSearchSuccess(null);
            }
            return;
        }
        if (searchMap.containsKey(searchContent)) {
            //已经发送过这个内容的请求 所以直接return
            return;
        }
        //开始查询，此时要给map添加一条空的数据，这样的目的是防止当正在查询一个数据的时候 第二次再去查询他
        searchMap.put(searchContent, "");


        //查询
        Request<JSONArray> queryUserByPhone = NoHttp.createJsonArrayRequest(Constant.url_my_friend, RequestMethod.POST);
        queryUserByPhone.add("action","queryFriend");
        queryUserByPhone.add("queryPhone",searchContent);
        CallServer.getRequestInstance().add(context, 0, queryUserByPhone, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                //查询成功后 保存数据
                JSONArray jsonArray= response.get();
                searchMap.put(searchContent, jsonArray.toString());
                //判断当前输入框是不是这个值，如果是 就回调显示 如果不是不做操作
                if (currentSearchContent.equals(searchContent)) {
                    //回调
                    if (onSearchListener != null) {

                        onSearchListener.onSearchSuccess(jsonArray.toString());
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                //查询失败后 移除这条数据
                searchMap.remove(searchContent);
            }
        }, false, false, "");




    }

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onSearchSuccess(String result);
    }
}
