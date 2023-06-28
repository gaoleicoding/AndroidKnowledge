package com.fifedu.lib_common_utils.json;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    /**
     * 把一个map变成json字符串
     *
     * @param map
     * @return
     */
    public static String parseMapToJson(Map<?, ?> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将一个bean或者list转为json
     *
     * @param obj
     * @return
     */
    public static String parseBeanOrListToJson(Object obj) {
        Gson gson = new Gson();
        String t = null;
        t = gson.toJson(obj);
        return t;
    }

    /**
     * 将一个bean或者list转为json
     * 带富文本标签的，防止出现’ \u003d '的情况
     *
     * @param obj
     * @return
     */
    public static String parseBeanOrListOrRichTextToJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()            //格式化输出json字符串
                .disableHtmlEscaping()            //消除字符 ‘=’输出为 \u003d
                .create();
        String t = null;
        t = gson.toJson(obj);
        return t;
    }

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * 将一个bean或者list转为json
     *
     * @param obj
     * @return
     */
    public static String parseBeanJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 把json字符串变成map
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> parseJsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 把json字符串变成集合 params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getIntValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return 0;
        if (!json.contains(key))
            return 0;
        JSONObject jsonObject;
        int value = 0;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static long getLongValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return 0;
        if (!json.contains(key))
            return 0;
        JSONObject jsonObject;
        long value = 0;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 将json字符串写到本地
     *
     * @param json
     */
    public static void saveJsonToLocal(String json) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String nowTime = sdf.format(new Date(System.currentTimeMillis()));
        String path = "/sdcard/" + nowTime + "_json.txt";
        File file = new File(path);
        // 把日志写到文件中
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            pw.print(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

}
