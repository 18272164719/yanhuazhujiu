/*
package com.test.controller;


*/
/*需求说明
 * 1、登陆功能，输入账号密码，点击登录进入管理系统
 * 2、系统页面有菜品管理和餐桌管理两个模块
 * 3、菜品名称和价格两个属性，可以添加新的菜品；餐桌有编号和是否使用的状态，可以添加新的餐桌
 * 4、可以以餐桌为单位统计当前餐费
 * 5、利用mysql数据库存储数据
 * 6、餐桌类数据表，创建sql语句 tableId：餐桌编号，tableStatue：餐桌可用状态，totalExpense：总消费
 *CREATE TABLE Table(
 * tableId int NOT NULL AUTO_INCREMENT,
 * tableStatue VARCHAR(4) NOT NULL,
 * totalExpense NUMBER NOT NULL
 * PRIMARY KEY (tableId)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf-8;
 * 菜品数据表，创建sql语句 foodId：菜品编号，foodName：菜品名称，foodPrice：菜品单价
 *CREATE TABLE Food(
 * foodId int NOT NULL AUTO_INCREMENT,
 * foodName VARCHAR(20) NOT NULL,
 * foodPrice VARCHAR(20) NOT NULL
 * PRIMARY KEY (foodId)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf-8;*//*


import com.shyl.common.entity.DataGrid;
import com.shyl.common.entity.PageRequest;
import com.shyl.common.framework.annotation.CurrentUser;
import com.shyl.common.framework.annotation.Fastjson;
import com.shyl.common.web.controller.BaseController;
import com.test.entity.TableVa;
import com.test.entity.Variety;
import com.test.service.ITableService;
import com.test.service.IVarietyService;
import com.test.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/Variety")
public class VarietyController extends BaseController{

    @Resource
    private IVarietyService varietyService;
    @Resource
    private ITableService tableService;
    */
/**
     * 主页
     * @return
     *//*

    @RequestMapping("")
    public String home(){
        return "table/list";
    }

    */
/**
     * 分页查询
     * @param pageable
     * @return
     *//*

    @RequestMapping("/pageByTable")
    @ResponseBody
    public DataGrid<TableVa> pageByTable(PageRequest pageable){
        DataGrid<TableVa> page = tableService.query(pageable);
        return page;
    }

    */
/**
     * 分页查询
     * @param pageable
     * @return
     *//*

    @RequestMapping("/pageByList")
    @ResponseBody
    public DataGrid<Variety> pageByList(PageRequest pageable){
        DataGrid<Variety> varietyDataGrid = new DataGrid<>();
        List<Variety> varietyList = new ArrayList<>();
        TableVa tableVa = tableService.getByKey(pageable);
        if(tableVa != null){
            varietyList.addAll(tableVa.getVarietys());
        }
        varietyDataGrid.setPageSize(pageable.getPageSize());
        varietyDataGrid.setRows(varietyList);
        return varietyDataGrid;
    }



    */
/**
     * 分页查询
     * @param pageable
     * @return
     *//*

    @RequestMapping("/pageBy")
    @ResponseBody
    public DataGrid<Variety> page(PageRequest pageable){
        DataGrid<Variety> page = varietyService.query(pageable);
        return page;
    }

    */
/**
     * 新增画面
     * @return
     *//*

    @RequestMapping(value = "/addVa", method = RequestMethod.GET)
    public String addVa(){
        return "table/addVa";
    }

    */
/**
     * 新增画面
     * @return
     *//*

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "table/add";
    }

    */
/**
     * 点菜
     * @return
     *//*

    @RequestMapping(value = "/addVarity", method = RequestMethod.GET)
    public String addVarity(){
        return "table/addVarity";
    }


    */
/**
     * 挑选
     * @return
     *//*

    @RequestMapping(value = "/choose", method = RequestMethod.GET)
    public String choose(){
        return "table/choose";
    }

    */
/**
     * 点菜
     * @param tableVa
     * @return
     *//*

    @RequestMapping(value = "/addByDetail", method = RequestMethod.POST)
    @ResponseBody
    public Message addByDetail(@Fastjson List<JSONObject> data,TableVa tableVa){
        Message message = new Message();
        try{
            List<Long> ids = new ArrayList<>();
            for(JSONObject obj : data){
                ids.add(obj.getLong("id"));
            }
            tableService.addByDetail(ids,tableVa);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 结算
     * @param id
     * @return
     *//*

    @RequestMapping(value = "/settle", method = RequestMethod.POST)
    @ResponseBody
    public Message settle(Long id){
        Message message = new Message();
        try{
            TableVa tableVa = tableService.getById(id);
            tableVa.setStatus(TableVa.Status.notUse);
            tableVa.setUseNum(tableVa.getNum()+1);
            tableService.update(tableVa);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 加菜
     * @param tableVa
     * @return
     *//*

    @RequestMapping(value = "/editBydetail", method = RequestMethod.POST)
    @ResponseBody
    public Message editBydetail(@Fastjson List<JSONObject> data, TableVa tableVa){
        Message message = new Message();
        try{
            //varietyService.save(variety);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 加菜
     * @return
     *//*

    @RequestMapping(value = "/editVarity", method = RequestMethod.GET)
    public String editVarity(){
        return "table/editVarity";
    }

    */
/**
     * 新增
     * @param variety
     * @return
     *//*

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message add(Variety variety){
        Message message = new Message();
        try{
            varietyService.save(variety);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 新增
     * @param tableVa
     * @return
     *//*

    @RequestMapping(value = "/addTable", method = RequestMethod.POST)
    @ResponseBody
    public Message addTable(TableVa tableVa){
        Message message = new Message();
        try{
            tableService.save(tableVa);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 修改
     * @param tableVa
     * @return
     *//*

    @RequestMapping(value = "/editTable", method = RequestMethod.POST)
    @ResponseBody
    public Message editTable(TableVa tableVa){
        Message message = new Message();
        try{
            tableService.update(tableVa);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 修改
     * @param variety
     * @return
     *//*

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Message edit(Variety variety){
        Message message = new Message();
        try{
            varietyService.update(variety);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg(e.getMessage());
        }
        return message;
    }

    */
/**
     * 修改画面
     * @return
     *//*

    @RequestMapping(value = "/editVa", method = RequestMethod.GET)
    public String editVa(){
        return "table/editVa";
    }

    */
/**
     * 修改画面
     * @return
     *//*

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(){
        return "table/edit";
    }

    @Override
    protected void init(WebDataBinder webDataBinder) {

    }
}
*/
