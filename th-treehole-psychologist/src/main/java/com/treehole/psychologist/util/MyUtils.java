package com.treehole.psychologist.util;

import java.util.UUID;

/**
 * @author Helay
 * @date 2019/12/4 9:05
 */
public class MyUtils {

    /**
     * 随机生成32位不重复id
     *
     * @return
     */
    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
