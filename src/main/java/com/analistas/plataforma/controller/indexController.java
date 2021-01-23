/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analistas.plataforma.controller;

import com.analistas.plataforma.model.ClaseEnVivo;
import com.analistas.plataforma.repository.ClaseEnVivoRepository;
import com.analistas.plataforma.service.IUploadFile_Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matia
 */
@Controller
public class indexController {

    @Autowired
    private IUploadFile_Service upl;

    @Autowired
    private ClaseEnVivoRepository claseEnVivoRepo;

    @GetMapping({"/", "/index"})
    public String index(Map m) {

        return "index";
    }

}
