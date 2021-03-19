package com.example.bionimetesting.RoomDataBase;

import com.example.bionimetesting.Post;

import java.util.List;
import java.util.Map;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DataUao {

    String tableName = "MyTable";
    /**=======================================================================================*/
    /**簡易新增所有資料的方法*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)//預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    void insertData(MyData myData);

    /**複雜(?)新增所有資料的方法*/
    @Query("INSERT INTO "+tableName+"(SiteName, AQI, Status, O3_8hr, PM, WindSpeed, PublishTime) VALUES(:SiteName, :AQI, :Status, :O3_8hr, :PM, :WindSpeed, :PublishTime)")
    void insertData(String SiteName, int AQI, String Status, int O3_8hr, int PM, int WindSpeed, String PublishTime);

    /**=======================================================================================*/
    /**撈取全部資料
     * @return*/
    @Query("SELECT * FROM " + tableName)
    List<MyData> displayAll();


    /**=======================================================================================*/
    /**簡易更新資料的方法*/
    @Update
    void updateData(MyData myData);

    /**複雜(?)更新資料的方法*/
    @Query("UPDATE "+tableName+" SET SiteName = :SiteName,AQI=:AQI,Status=:Status,O3_8hr = :O3_8hr,PM = :PM,WindSpeed = :WindSpeed,PublishTime = :PublishTime WHERE id = :id" )
    void updateData(int id,String SiteName, int AQI, String Status, int O3_8hr, int PM, int WindSpeed, String PublishTime);

    /**=======================================================================================*/
    /**簡單刪除資料的方法*/
    @Delete
    void deleteData(MyData myData);

    /**複雜(?)刪除資料的方法*/
    @Query("DELETE  FROM " + tableName + " WHERE id = :id")
    void deleteData(int id);

    /**複雜(?)刪除全部資料的方法*/
    @Query("DELETE  FROM " + tableName)
    void deleteDataAll();
}
