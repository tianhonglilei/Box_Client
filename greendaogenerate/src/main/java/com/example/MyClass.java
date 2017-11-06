package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String args[]){
        Schema schema = new Schema(1,"box.lilei.box_client.db");
        Entity adBean = schema.addEntity("AdBean");
        adBean.addIdProperty();
        adBean.addIntProperty("adType");
        adBean.addStringProperty("adVideoFile");
        adBean.addStringProperty("adImgFile");
        adBean.addStringProperty("adUrl");
        adBean.addStringProperty("adPath");
        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
