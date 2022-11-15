package com.demicon.techtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    
    private String url;
    
    private int userSize;
    
    private long period;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserSize() {
        return this.userSize;
    }

    public void setUserSize(int userSize) {
        this.userSize = userSize;
    }

    public long getPeriod() {
        return this.period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
