package com.zhang.box.util;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilei on 2017/11/1.
 */

public class JsonListUtil {

    /**
     * 根据JSONArray String获取到List
     * @param <T>
     * @param <T>
     * @param jArrayStr
     * @return
     */
    public static <T> List<T> getListByArray(Class<T> class1, String jArrayStr) {
        List<T> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(jArrayStr);
        if (jsonArray==null || jsonArray.isEmpty()) {
            return list;//nerver return null
        }
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            T t = JSONObject.toJavaObject(jsonObject, class1);
            list.add(t);
        }
        return list;
    }

    /**
     * 根据List获取到对应的JSONArray
     * @param list
     * @return
     */
    public static JSONArray getJSONArrayByList(List<?> list){
        JSONArray jsonArray = new JSONArray();
        if (list==null ||list.isEmpty()) {
            return jsonArray;//nerver return null
        }

        for (Object object : list) {
            jsonArray.add(object);
        }
        return jsonArray;
    }

}
