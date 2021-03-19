package com.example.bionimetesting;

public class Post {
    public String SiteName;
    public String AQI;
    public String Status;
    public String O3_8hr;
    public String PM;
    public String WindSpeed;
    public String PublishTime;
    public Post(String SiteName, String AQI, String Status, String O3_8hr, String PM, String WindSpeed, String PublishTime) {
        this.SiteName = SiteName;
        this.AQI = AQI;
        this.Status = Status;
        this.O3_8hr = O3_8hr;
        this.PM = PM;
        this.WindSpeed = WindSpeed;
        this.PublishTime = PublishTime;
    }
}
