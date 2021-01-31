/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analistas.plataforma.repository;

import com.analistas.plataforma.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matia
 */
public interface ModuloRepository extends JpaRepository<Modulo, Long>{
    
}
