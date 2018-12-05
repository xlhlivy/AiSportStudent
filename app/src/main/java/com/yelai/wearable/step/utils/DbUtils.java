package com.yelai.wearable.step.utils;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dylan on 2016/1/31.
 */
public class DbUtils {

    public static HashMap<String,LiteOrm> orms = new HashMap<>();

//    public static String DB_NAME;
//    public static LiteOrm liteOrm;


    public static void createDb(Context _activity, Class cla) {
        String DB_NAME = cla.getSimpleName() + ".db";

        if(orms.containsKey(DB_NAME)){
            return;
        }

        LiteOrm liteOrm = LiteOrm.newCascadeInstance(_activity, DB_NAME);
        liteOrm.setDebugged(true);
        orms.put(DB_NAME,liteOrm);

    }






    public static LiteOrm getLiteOrm(Class clazz) {
        return ormDbInstance(clazz);
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        ormDbInstance(t.getClass()).save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        if(list.size() > 0){
            ormDbInstance(list.get(0).getClass()).save(list);
        }
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        return ormDbInstance(cla).query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value) {
        return ormDbInstance(cla).<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    private static <T> LiteOrm ormDbInstance(Class<T> cla) {
        return orms.get(cla.getSimpleName() + ".db");
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
        return ormDbInstance(cla).<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            liteOrm.delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }

    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        ormDbInstance(cla).deleteAll(cla);
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     *
     * @param cla
     * @param field
     * @param value
     */
    public static <T> int deleteWhere(Class<T> cla, String field, String[] value) {
        return ormDbInstance(cla).delete(cla, new WhereBuilder(cla).where(field + "!=?", value));
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        ormDbInstance(t.getClass()).update(t, ConflictAlgorithm.Replace);
    }


    public static <T> void updateALL(List<T> list) {
        if(list.size() > 0){
            ormDbInstance(list.get(0).getClass()).update(list);
        }
    }

    public static void closeDb(Class clazz) {
        ormDbInstance(clazz).close();
    }




}
