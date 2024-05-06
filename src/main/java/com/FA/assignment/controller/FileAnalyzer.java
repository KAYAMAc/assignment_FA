package com.FA.assignment.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FA.assignment.entity.forms.FAImageForm;
import com.FA.assignment.entity.requests.FAConvertApiRequest;
import com.FA.assignment.entity.requests.FAReceiptApiRequest;
import com.FA.assignment.entity.responses.FAConvertApiResponse;
import com.FA.assignment.entity.responses.FAReceiptApiResponse;
import com.FA.assignment.util.FAApiClient;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;



@Controller
public class FileAnalyzer {

    @RequestMapping(value = "/")
    public String init() {
        return "homepage";
    }

    @RequestMapping(value = "/result")
    public Object upload(@RequestParam("file") MultipartFile file,  Model model) throws IOException{
        FAImageForm form = new FAImageForm();
        form.uploadedPdf = file.getInputStream();
        System.out.println(form.uploadedPdf);
        FAConvertApiRequest convertRequest = new FAConvertApiRequest(this.convert(form.uploadedPdf, "uploadedPdf"));
        FAApiClient httpClient = new FAApiClient();
        try{
            FAConvertApiResponse convertResponse = httpClient.callConvertApi(convertRequest);
            FAReceiptApiRequest receiptRequest = new FAReceiptApiRequest(convertResponse.convertedImage);
            FAReceiptApiResponse receiptResponse = httpClient.callReceiptApi(receiptRequest);
            model.addAttribute("results", receiptResponse);
            return "result";
        }catch(Exception e){
            // logger here
        }
        return "error";
        
    }

    
    @GetMapping("/upload")
    public String result() {
        return "upload";
    }

    /*
    @PostMapping("/error")
    public String error() {
        return "Hello, Spring Boot!";
    }
    */

    public static File convert(InputStream inputStream, String fileName) throws IOException {
        File tempFile = File.createTempFile(fileName, ".pdf");
        tempFile.deleteOnExit(); // Delete the temp file when JVM exits

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }


}
