package com.test.mongodb;


import com.mongodb.util.JSON;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

public class MongoDao<T> implements IMongoDao<T> {

    private MongoTemplate mongoTemplate;

    @Override
    public void save(T var,String tableName) {
        JSONObject jsonObject = JSONObject.fromObject(var);
        mongoTemplate.insert(jsonObject,tableName);
    }

    @Override
    public void update(Long id,T var,String tableName) {
        Query query = new Query(Criteria.where("id").is(id));
        Update up = Update.update("要更新的字段", "更新的值");
        mongoTemplate.updateFirst(query,up,tableName);

    }

    @Override
    public void delete(Long id,String tableName) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,tableName);
    }

    @Override
    public T findById(Long id,Class clz,String tableName) {
        Query query = new Query(Criteria.where("id").is(id));
        Object one = mongoTemplate.findOne(query, clz, tableName);
        JSONObject js = JSONObject.fromObject(one);
        return (T)js;
    }

    @Override
    public List<T> findAll(Class clz,String tableName) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<String> list = mongoTemplate.findAll(String.class,tableName);
        for(String s : list){
            Object parse = JSON.parse(s);
            JSONObject jsonObject = JSONObject.fromObject(parse);
            jsonObjects.add(jsonObject);
        }
        return (List<T>) jsonObjects;
    }


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
