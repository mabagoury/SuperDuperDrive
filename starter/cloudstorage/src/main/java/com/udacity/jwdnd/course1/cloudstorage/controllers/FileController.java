package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, Model model) {
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        model.addAttribute("files", this.fileService.getAllUserFiles(userId));
        return "home";
    }

    @PostMapping
    public String newFile(Authentication authentication, @ModelAttribute("newFile") FileForm newFile, Model model) throws IOException {
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        ArrayList<File> files = fileService.getAllUserFiles(userId);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (File file: files) {
            if (file.getFilename().equals(fileName)) {
                fileIsDuplicate = true;
                break;
            }
        }
        if (!fileIsDuplicate) {
            fileService.addFile(multipartFile, userId);
            model.addAttribute("result", "success");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate file.");
        }
        model.addAttribute("files", fileService.getAllUserFiles(userId));

        return "result";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFiledata();
    }

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile, Model model) {
        fileService.deleteFile(fileName);
        Integer userId = userService.getLoggedinUser(authentication).getUserId();
        model.addAttribute("files", fileService.getAllUserFiles(userId));
        model.addAttribute("result", "success");

        return "result";
    }

    @ModelAttribute("newNote")
    public NoteForm addNoteForm() {
        return new NoteForm();
    }

    @ModelAttribute("newCredential")
    public CredentialForm addCredentialForm() {
        return new CredentialForm();
    }

}
