package com.example.JournalApp.Cache;

import com.example.JournalApp.Entity.ConfigJournalApp;
import com.example.JournalApp.Repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppCache {

    public enum keys{
        WEATHER_API;

    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        for (ConfigJournalApp configJournalApp : all){
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }

}
