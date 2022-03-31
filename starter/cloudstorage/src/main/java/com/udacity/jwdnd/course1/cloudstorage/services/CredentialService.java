package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public ArrayList<Credential> getAllUserCredentials(int userid){
        return credentialMapper.getAllUserCredentials(userid);
    }

    public void addCredential(String url, String userName, String key, String password, int userId) {
        credentialMapper.insertCredential(new Credential(0, url, userName, key, password, userId));
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.selectCredential(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public void updateCredential(Integer credentialId, String url, String userName, String key, String password, int userId) {
        credentialMapper.updateCredential(new Credential(credentialId, url, userName, key, password, userId));
    }
}
