package com.treehole.evaluation.MyUtils;

import java.util.*;

/**
 * @auther: Yan Hao
 */
public final class MyMapUtils {
    /**
     * 例子： 先创建一个list存储map，然后使用该方法
     * List<Map> list = new ArrayList<>();
     *         Map map0 = new HashMap();
     *         map0.put(1, 1);
     *         list.add(map0);
     *         Map map1 = new HashMap();
     *         map1.put(3, 4);
     *         list.add(map1);
     *         Map map2 = new HashMap();
     *         map2.put(1, 2);
     *         list.add(map2);
     * 然后
     * Map m = mapCombine(list);
     * <p>
     * map集合的key相同，value不同时,组合value到相同的key
     * <p>
     * ###########################################
     * 返回的是一个新map，迭代传入list中的map的key，将key传入新map里作为新key，
     * 新建list当做value，获取旧map里的value传入list(新value)，当新key存在时获取旧map里的value传入list(新value)
     */
    public static final Map mapValueCombine(List<Map> list) {
        Map<Object, List> map = new HashMap<>();
        for (Map m : list) {
            Iterator<Object> it = m.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (!map.containsKey(key)) {
                    List newList = new ArrayList<>();
                    newList.add(m.get(key));
                    map.put(key, newList);
                } else {
                    map.get(key).add(m.get(key));
                }
            }
        }
        return map;
    }
}
