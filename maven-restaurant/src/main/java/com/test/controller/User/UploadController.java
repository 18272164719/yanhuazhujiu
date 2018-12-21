package com.test.controller.User;

import com.test.service.IBaseService;
import com.test.util.CommonMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.test.util.Message;

import javax.annotation.Resource;

@Controller
@RequestMapping("/upload")
public class UploadController {


    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/impExcel", method = RequestMethod.POST)
    @ResponseBody
    public Message impExcel(@RequestParam(value="file",required=false)CommonsMultipartFile excel){
        Message message = new Message();
        String filename = excel.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            message.setMsg("文件不能为空");
            return message;
        }
        try{
            if(filename.endsWith(".xls") ||filename.endsWith(".xlsx")) {
                //读取Excel
                String[][] upExcel = CommonMethod.tranExcelToList(excel);


            }else {
                message.setMsg("请用正确模版格式导入!");
                message.setSuccess(false);
            }
        }catch(Exception e){
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return  message;
    }

}
