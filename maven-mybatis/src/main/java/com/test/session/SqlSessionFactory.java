package com.test.session;

import com.test.config.Configuration;
import com.test.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * 手写mybatis 实现思路
 1、创建sqlSessionFactory实例
 2、实例化要做的事情：1、加载配置文件到内存 创建configuration对象
 3、通过sqlSessionFactory创建sqlSession
 4、通过SqlSession获取mapper接口动态代理
 5、动态代理回掉SqlSession中的查询方法
 6、SqlSession将查询方法转发给Executor
 7、Executor基于JDBC访问数据库获取数据
 8、Executor通过反射将数据转为pojo并返回给SqlSession
 9、将数据返回给调用者
 */

//1、把配置文件加载到内存
//2、工厂类生产sqlSession
public class SqlSessionFactory {

    private Configuration conf = new Configuration();

    public SqlSessionFactory(){
        //加载数据库信息
        loadDbInfo();
        //加载mapper文件
        loadMappersInfo();
    }

    private static final String DB_CONFIG_FILE = "db.properties";

    private static final String MAPPER_CONFIG_LOCATION = "mappers";

    private void loadDbInfo() {
        InputStream in = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();
        try {
            p.load(in); //将数据库配置信息写入Properties
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将数据库配置信息写入conf对象
        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
    }

    //加载指定文件的mapper
    private void loadMappersInfo() {
        URL resources = null;
        resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile());
        if(mappers.isDirectory()){
            File[] files = mappers.listFiles();
            for(File file : files){
                loadMapperInfo(file);
            }
        }
    }

    private void loadMapperInfo(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点
        Element root = document.getRootElement();
        //获取命名空间
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点列表
        List<Element> selects = root.elements("select");
        for(Element element : selects){
            MappedStatement mappedStatement = new MappedStatement();
            String id = element.attribute("id").getData().toString();
            String resultType = element.attribute("resultType").getData().toString();
            String sql = element.getData().toString();

            String sourceId = namespace+"."+id;

            mappedStatement.setNamespace(namespace);
            mappedStatement.setSourceId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);

            conf.getMappedStatements().put(sourceId,mappedStatement);
        }
    }

    public SqlSession openSession(){
        return new DefaultSqLSession(conf);
    }

}
