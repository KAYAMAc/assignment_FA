package com.FA.assignment.entity.requests;

import java.io.File;

public class FAReceiptApiRequest {
    public File uploadedImage;

    public FAReceiptApiRequest(File file){
        this.uploadedImage = file;
    }
}
