package com.analistas.plataforma;

import com.analistas.plataforma.service.IUploadFile_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlataformaApplication implements CommandLineRunner{
    
    @Autowired
    private IUploadFile_Service uploadFileService;

    public static void main(String[] args) {
        SpringApplication.run(PlataformaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        // uploadFileService.deleteAll();
        // uploadFileService.init();
    }

}
