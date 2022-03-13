package com.wrx.codeplatform.utils.data;

import com.wrx.codeplatform.domain.config.DataPool;

/**
 * @author 魏荣轩
 * @date 2022/3/11 14:46
 */
public class DataUtil {
    public static void loadLocalData(){
        loadSchools();
        loadLocations();
    }

    private static void loadSchools(){
        DataPool.schools.add("江苏师范大学");
        DataPool.schools.add("南京师范大学");
    }
    private static void loadLocations(){
        DataPool.locations.add("徐州");
        DataPool.locations.add("南京");
    }
}
