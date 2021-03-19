package com.example.bionimetesting.RoomDataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyTable")
public class MyData {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    public String SiteName;
    public String AQI;
    public String Status;
    public String O3_8hr;
    public String PM;
    public String WindSpeed;
    public String PublishTime;
    public MyData(String SiteName, String AQI, String Status, String O3_8hr, String PM, String WindSpeed, String PublishTime) {
        this.SiteName = SiteName;
        this.AQI = AQI;
        this.Status = Status;
        this.O3_8hr = O3_8hr;
        this.PM = PM;
        this.WindSpeed = WindSpeed;
        this.PublishTime = PublishTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return SiteName;
    }

    public String getAQI() {
        return AQI;
    }

    public String getStatus() {
        return Status;
    }

    public String getO3_8hr() {
        return O3_8hr;
    }

    public String getPM() {
        return PM;
    }

    public String getWindSpeed() {
        return WindSpeed;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setSiteName(String siteName) {
        this.SiteName = siteName;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public void setO3_8hr(String o3_8hr) {
        this.O3_8hr = o3_8hr;
    }

    public void setPM(String PM) {
        this.PM = PM;
    }

    public void setWindSpeed(String windSpeed) {
        this.WindSpeed = windSpeed;
    }

    public void setPublishTime(String publishTime) {
        this.PublishTime = publishTime;
    }
}
