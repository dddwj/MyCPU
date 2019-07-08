package edu.ecust.mycpu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.ecust.mycpu.model.PCB;
import edu.ecust.mycpu.model.State;
import edu.ecust.mycpu.service.PriorityService;
import edu.ecust.mycpu.util.RandomProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller

public class PriorityController {
    @Autowired
    PriorityService priorityService;

    @ResponseBody
    @PostMapping("/PriorityData")
    public Map<Integer, Map<String, List<PCB>>> priorityData(@RequestBody String data){
        try {
            data = java.net.URLDecoder.decode(data,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] split = data.split("=");
        String currentDataMap = split[0];
        //将第一层Map转化出来
        Map allData = JSON.parseObject(currentDataMap,Map.class);
        //获取当前时间
        Integer currentTime = Integer.parseInt(String.valueOf(allData.get("current_time")));
        //获取当前队列JSON字符串
        String currentDataStr = String.valueOf(allData.get(String.valueOf(currentTime)));
        Map<String, List<PCB>> result = JSONObject.parseObject(currentDataStr)
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> JSONObject.parseArray(String.valueOf(entry.getValue()), PCB.class)));
        try{
            ArrayList<PCB> unreachedList = new ArrayList<>();
            unreachedList.addAll(result.get("blockup"));
            ArrayList<PCB> readyList = new ArrayList<>();
            unreachedList.addAll(result.get("ready"));
            ArrayList<PCB> finishList = new ArrayList<>();
            unreachedList.addAll(result.get("finish"));
            ArrayList<PCB> runningList = new ArrayList<>();
            unreachedList.addAll(result.get("run"));
            ArrayList<PCB> jamList = new ArrayList<>();
            unreachedList.addAll(result.get("jam"));
            if(unreachedList.isEmpty()){
                unreachedList = RandomProcess.getRandomUnreachedProcess(10);
            }
            return priorityService.Priority(unreachedList,readyList,finishList,runningList,jamList,currentTime);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
