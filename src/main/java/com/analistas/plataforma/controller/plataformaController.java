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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

/**
 *
 * @author matia
 */
@Controller
@SessionAttributes("claseenvivo")
public class plataformaController {

    @Autowired
    private IUploadFile_Service upl;

    @Autowired
    private ClaseEnVivoRepository claseEnVivoRepo;
    
    private ClaseEnVivo claseenvivo;

    @GetMapping("/form")
    public String plataforma(Map m) {

        m.put("titulo", "Subir Archivo");
        
        ClaseEnVivo claseenvivo = new ClaseEnVivo();
        
        m.put("claseenvivo", claseenvivo);

        return "form";
    }

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verVideo(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = upl.load(filename);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @PostMapping("/form")
    public String guardar(@Valid ClaseEnVivo claseenvivo, @RequestParam("file") MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            String uniqueFilename = null;

            try {
                uniqueFilename = upl.copy(archivo);

                claseenvivo.setUrl(uniqueFilename);

                claseEnVivoRepo.save(claseenvivo);
                
                this.claseenvivo = claseenvivo;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "redirect:/verificacion";
    }
    
    @GetMapping("/verificacion")
    public String verificacion(Map m){
        
        m.put("claseenvivo", claseenvivo);
        
        return "verificacion";
    }

}
