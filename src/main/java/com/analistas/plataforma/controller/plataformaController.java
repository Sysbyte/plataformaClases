/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analistas.plataforma.controller;

import com.analistas.plataforma.model.Clase;
import com.analistas.plataforma.model.Modulo;
import com.analistas.plataforma.service.IUploadFile_Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
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
import com.analistas.plataforma.repository.ClaseRepository;
import com.analistas.plataforma.repository.ModuloRepository;

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
    private ClaseRepository claseRepo;

    @Autowired
    private ModuloRepository moduloRepo;

    private Clase clase;

    @GetMapping("/form")
    public String plataforma(Map m) {

        m.put("titulo", "Subir Archivo");

        List<Clase> clases = claseRepo.findAll();
        List<Modulo> modulos = moduloRepo.findAll();

        Clase clase = new Clase();
        
        m.put("clase", clase);
        m.put("clases", clases);
        m.put("modulos", modulos);

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
    public String guardar(@Valid Clase clase, @RequestParam("file") MultipartFile archivo) {
        if (!archivo.isEmpty()) {
            String uniqueFilename = null;

            try {
                uniqueFilename = upl.copy(archivo);
                
                

                clase.setUrl(uniqueFilename);

                claseRepo.save(clase);

                this.clase = clase;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "redirect:/clases/" + clase.getId();
    }

    @GetMapping("/clases/{id}")
    public String verificacion(Map m, @PathVariable Long id) {

        List<Clase> clases = claseRepo.findAll();
        List<Modulo> modulos = moduloRepo.findAll();

        m.put("clases", clases);
        m.put("modulos", modulos);

        m.put("id", id);
        
        for(Modulo modulo : modulos){
            for(Clase clase : modulo.getClases()){
                
                if(clase.getId().equals(id)){
                    m.put("id_modulo", clase.getModulo().getId());
                    break;
                }
            }
        }

        m.put("clase", claseRepo.findById(id).get());

        return "clases";
    }

}
