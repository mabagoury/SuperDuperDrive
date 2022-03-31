package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        model.addAttribute("credentials", credentialService.getAllUserCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @PostMapping("/add-credential")
    public String newCredential(Authentication authentication, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        String newUrl = newCredential.getUrl();
        String credentialIdStr = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (credentialIdStr.isEmpty()) {
            credentialService.addCredential(newUrl, newCredential.getUserName(), encodedKey, encryptedPassword, userId);
        } else {
            Credential existingCredential = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialid(), newUrl, newCredential.getUserName(), encodedKey, encryptedPassword, userId);
        }
        model.addAttribute("credentials", credentialService.getAllUserCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/get-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @GetMapping(value = "/delete-credential/{credentialId}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialId,
            @ModelAttribute("newCredential") CredentialForm newCredential, Model model) {
        credentialService.deleteCredential(credentialId);
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        model.addAttribute("credentials", credentialService.getAllUserCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @ModelAttribute("newFile")
    public FileForm addFileForm() {
        return new FileForm();
    }

    @ModelAttribute("newNote")
    public NoteForm addNoteForm() {
        return new NoteForm();
    }
}