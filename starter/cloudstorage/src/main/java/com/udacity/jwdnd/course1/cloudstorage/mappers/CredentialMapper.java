package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
    Credential selectCredential(Integer id);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    ArrayList<Credential> getAllUserCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (credentialtitle, credentialdescription, userid) VALUES(#{credentialtitle}, #{credentialdescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username=#{username}, password=#{password} WHERE credentialid =#{credentialid} AND userid=#{userid}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    void deleteCredential(Integer id);

}
