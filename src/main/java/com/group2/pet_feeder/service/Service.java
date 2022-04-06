package com.group2.pet_feeder.service;

import com.group2.pet_feeder.other.HttpClient;
import com.group2.pet_feeder.other.ScheduleManager;
import com.group2.pet_feeder.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repository;

    @PostConstruct
    public void initScheduledTask() {
        List<Map<String, Object>> tasks = repository.selectAllTasks();
        if (tasks != null) {
            ScheduleManager.init(tasks);
        }
    }

    public byte[] getPhoto() {
        HttpClient client = new HttpClient(new RestTemplateBuilder());
        return client.getPhoto();
    }


    public HashMap<String, Object> getTasks(String userId) {
        return repository.selectTaskById(userId);
    }

    public HashMap<String, Object> addTask(String userId, String time) {
        HashMap<String, Object> rtn = repository.insertTask(userId, time);
        if ("success".equals(rtn.get("message"))) {
            ScheduleManager.addTask(userId, time);
        }
        return rtn;
    }
}
