package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{id}")
    File selectFile(Integer id);

    @Select("SELECT * FROM FILES WHERE filename = #{name}")
    File selectFileByName(String name);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    ArrayList<File> getAllUserFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{name}")
    void deleteFile(String name);
    
}
