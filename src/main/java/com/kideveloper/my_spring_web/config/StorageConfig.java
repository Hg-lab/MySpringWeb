package com.kideveloper.my_spring_web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
public class StorageConfig {
    /**
     * Folder location for storing files
     */
    private String location = "/Users/hanhyunkyu/Downloads/개발/my_spring_web/src/main/resources/upload";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
