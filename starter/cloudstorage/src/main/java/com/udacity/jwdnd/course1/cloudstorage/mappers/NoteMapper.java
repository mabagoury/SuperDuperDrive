package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Note selectNote(Integer id);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    ArrayList<Note> getAllUserNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid=#{noteid}")
    void updateNote(int noteid, String notetitle, String notedescription);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    void deleteNote(Integer id);

}
