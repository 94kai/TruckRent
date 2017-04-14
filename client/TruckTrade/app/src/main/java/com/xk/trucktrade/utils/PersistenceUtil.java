package com.xk.trucktrade.utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by xk on 2016/8/2 9:41.
 */
public class PersistenceUtil {


    /**
     * 把t这个对象保存成文件
     *
     * @param name
     * @param t
     * @param <T>
     */
    public static <T> void saveObjectToFile(String name, T t) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + name;
        FileWriter fw = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(t);
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, false);
            fw.write(json);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } /**
     * 把t这个对象保存成文件
     *
     * @param name
     * @param content
     */
    public static  void saveStringToFile(String name, String content) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + name;
        FileWriter fw = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, false);
            fw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String getStringFromFile(String name) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + name;
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
