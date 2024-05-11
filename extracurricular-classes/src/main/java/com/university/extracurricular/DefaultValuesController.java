package com.university.extracurricular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/default-values")
public class DefaultValuesController {

    @Autowired
    private DeporteRepository deporteRepository;

    // GET: Recuperar valores predeterminados de deportes desde la base de datos
    @GetMapping("/deportes")
    public List<Deporte> getDeportes() {
        return deporteRepository.findAll();
    }
}
