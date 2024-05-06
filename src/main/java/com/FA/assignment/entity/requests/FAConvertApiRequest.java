package com.FA.assignment.entity.requests;

import java.io.File;

public class FAConvertApiRequest {
    public File uploadedPdf;
     
    public FAConvertApiRequest(File file){
        this.uploadedPdf = file;
    }
}
