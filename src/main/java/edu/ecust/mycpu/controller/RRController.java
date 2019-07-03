package edu.ecust.mycpu.controller;

import com.alibaba.fastjson.JSONObject;
import edu.ecust.mycpu.model.PCB;
//import edu.ecust.mycpu.service.RoundListService;
import edu.ecust.mycpu.service.RoundListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class RRController {

    @ResponseBody
    @GetMapping("/RRData")
    public Map<Integer, Map<String, List<PCB>>> sentRRData(){
        List<PCB> unreachedList = new LinkedList<>();
        List<PCB> runList = new LinkedList<>();
        List<PCB> readyList = new LinkedList<>();
        List<PCB> finishList = new LinkedList<>();
        Map<Integer,Map<String,List<PCB>>> allData = new HashMap<>();
        RoundListService rs = new RoundListService(unreachedList,readyList,runList,finishList,allData,3);
        rs.init();
        rs.sortUnreachedList();
        Map<Integer, Map<String, List<PCB>>> res = null;
        try {
            res = rs.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }


//    public String getModifiedRRData(){
//        Object parse = JSONObject.parse();
//        return "";
//    }
}
