package com.kandouwo.android.model;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by foxcoder on 14-9-16.
 */
public class KDWDaoGenerator {
    private static final int DB_VERSION = 1;

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(DB_VERSION, "com.kandouwo.model.dao");
        schema.enableKeepSectionsByDefault();
        createSplashImage(schema);
        new DaoGenerator().generateAll(schema, "../model/src/main/java");
    }

    private static void createSplashImage(Schema schema){
        Entity splashImage = schema.addEntity("SplashImage");
        splashImage.setTableName("splash_image");
        splashImage.addLongProperty("id").primaryKey();
        splashImage.addIntProperty("version");
        splashImage.addLongProperty("startTime");
        splashImage.addLongProperty("endTime");
        splashImage.addStringProperty("imageUrl");
        splashImage.addStringProperty("title");
    }
}
