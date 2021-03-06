package com.group2.pet_feeder.controller;

import com.group2.pet_feeder.entity.Message;
import com.group2.pet_feeder.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
public class Controller {

    @Autowired
    private Service service;

    @RequestMapping(value = "/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto() {
        return service.getPhoto();
    }

    @RequestMapping("/checkEmpty")
    public Message checkEmpty() {
        return service.checkEmpty();
    }

    @RequestMapping("/enablePump")
    public Message enablePump() {
        return service.enablePump();
    }

    @RequestMapping("/serveFood")
    public Message serveFood(HttpSession session) {
        return service.serveFood((String) session.getAttribute("userId"));
    }

    @RequestMapping("/disablePump")
    public Message disablePump() {
        return service.disablePump();
    }

    @RequestMapping("/getWaterStatus")
    public HashMap<String, Object> getWaterStatus(){
        return service.getWaterStatus();
    }

    @RequestMapping("/getWeightStatus")
    public HashMap<String, Object> getWeightStatus(){
        return service.getWeightStatus();
    }

    @RequestMapping("/getTasks")
    public HashMap<String, Object> getTasks(HttpSession session) {
        return service.getTasks((String) session.getAttribute("userId"));
    }

    @RequestMapping("/addTask")
    public HashMap<String, Object> addTask(String time, HttpSession session) {
        return service.addTask((String) session.getAttribute("userId"), time);
    }

    @RequestMapping("/deleteTask")
    public HashMap<String, Object> deleteTask(String time, HttpSession session) {
        return service.deleteTask((String) session.getAttribute("userId"), time);
    }

    @RequestMapping("/updateTask")
    public HashMap<String, Object> updateTask(String oldTime,String newTime, HttpSession session) {
        HashMap<String, Object> rtn = service.deleteTask((String) session.getAttribute("userId"), oldTime);
        if("success".equals(rtn.get("message"))){
            return service.addTask((String) session.getAttribute("userId"), newTime);
        }else {
            return rtn;
        }
    }


}
