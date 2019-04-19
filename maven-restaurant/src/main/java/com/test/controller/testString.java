package com.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.entity.User;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class testString {

    public static void main(String[] args) throws IOException {
        /*String name = "MainTable.com.shyl.medicshop.entity";
        System.out.println(name.replaceAll("MainTable.",""));*/


        /*String str = "[{\"sheetid_3pl\":\"JH00000072\",\"sheetitemid_3pl\":\"72\",\"sheetid\":\"JH00000003\",\"sheettype\":\"进货\",\"sdate\":\"2018-06-25 00:00:00\",\"supplyid\":\"001\",\"supname\":\"广州和晖公司\",\"note1\":\"\",\"note2\":\"\",\"serialid_3pl\":\"332\",\"serialid\":\"156\",\"itemid\":\"1\",\"goodsid\":\"030000\",\"goodsname\":\"蛇胆川贝液\",\"spec\":\"10ml*6支\",\"unit\":\"盒\",\"origin\":\"广西梧州三箭制药有限公司\",\"stockid\":\"1\",\"qty\":\"10.000000\",\"qty_3pl\":\"10\",\"refuse_qty\":\"\",\"cost\":\"10.100000\",\"amount\":\"101\",\"itemnote\":\"\",\"batchid\":\"001\",\"udate\":\"2020-01-01 00:00:00\",\"mfgdate\":\"2018-01-01 00:00:00\",\"batchmj\":\"\",\"checkout\":\"合格\",\"checkman\":\"操作员001\",\"checkdate\":\"2018-06-25 00:00:00\"},{\"sheetid_3pl\":\"JH00000072\",\"sheetitemid_3pl\":\"72\",\"sheetid\":\"JH00000003\",\"sheettype\":\"进货\",\"sdate\":\"2018-06-25 00:00:00\",\"supplyid\":\"001\",\"supname\":\"广州和晖公司\",\"note1\":\"\",\"note2\":\"\",\"serialid_3pl\":\"331\",\"serialid\":\"157\",\"itemid\":\"6\",\"goodsid\":\"030001\",\"goodsname\":\"羚贝止咳糖浆\",\"spec\":\"100ml\",\"unit\":\"盒\",\"origin\":\"吉林省东丰药业股份有限公司\",\"stockid\":\"1\",\"qty\":\"20.000000\",\"qty_3pl\":\"20\",\"refuse_qty\":\"\",\"cost\":\"10.200000\",\"amount\":\"204\",\"itemnote\":\"\",\"batchid\":\"002\",\"udate\":\"2020-02-01 00:00:00\",\"mfgdate\":\"2018-02-01 00:00:00\",\"batchmj\":\"\",\"checkout\":\"合格\",\"checkman\":\"操作员001\",\"checkdate\":\"2018-06-25 00:00:00\"},{\"sheetid_3pl\":\"JH00000072\",\"sheetitemid_3pl\":\"72\",\"sheetid\":\"JH00000003\",\"sheettype\":\"进货\",\"sdate\":\"2018-06-25 00:00:00\",\"supplyid\":\"001\",\"supname\":\"广州和晖公司\",\"note1\":\"\",\"note2\":\"\",\"serialid_3pl\":\"330\",\"serialid\":\"158\",\"itemid\":\"18\",\"goodsid\":\"030003\",\"goodsname\":\"美芬克午时茶胶囊\",\"spec\":\"0.25g*24粒\",\"unit\":\"盒\",\"origin\":\"江西广丰制药厂             GY\",\"stockid\":\"1\",\"qty\":\"30.000000\",\"qty_3pl\":\"30\",\"refuse_qty\":\"\",\"cost\":\"10.300000\",\"amount\":\"309\",\"itemnote\":\"\",\"batchid\":\"003\",\"udate\":\"2020-01-03 00:00:00\",\"mfgdate\":\"2018-01-03 00:00:00\",\"batchmj\":\"\",\"checkout\":\"合格\",\"checkman\":\"操作员001\",\"checkdate\":\"2018-06-25 00:00:00\"}]";
        JSONArray arr = JSONObject.parseArray(str);

        Iterator<Object> it = arr.iterator();
        while (it.hasNext()){
            JSONObject js = (JSONObject) it.next();
            System.out.println(js);
        }*/
        /*File file = new File("D:\\WORKSOFT\\shyldemo\\newmedicshop/src/main/java/com/shyl/medicshop/entity/base");
        System.out.println(file.isDirectory());*/
        //System.out.println(new User("123","123").getClass().getSuperclass().getSuperclass().getSuperclass() instanceof Object);
        //System.out.println(DateUtil.getLastDayOfMonth("2018-08"));
        //System.out.println("2018-08-22".substring(0,10));
        //System.out.println(CollectionUtils.isEmpty(new ArrayList()));

        //int i = Integer.parseInt("77777777778");
        /*String a = Long.parseLong("77777777778")+"";
        System.out.println(a);*/
        /*File file = new File("a.txt");
        file.createNewFile();

        System.out.println(file.exists());

        List<Long> l = new ArrayList<>();*/

        Map<String,Object> map4 = Collections.synchronizedMap(new HashMap<>());
        System.out.println(map4);
    }
}
