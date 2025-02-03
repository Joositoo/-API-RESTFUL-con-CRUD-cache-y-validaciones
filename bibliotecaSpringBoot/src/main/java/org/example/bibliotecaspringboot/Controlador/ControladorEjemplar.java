package org.example.bibliotecaspringboot.Controlador;

import jakarta.validation.Valid;
import org.example.bibliotecaspringboot.Modelo.Ejemplar;
import org.example.bibliotecaspringboot.Modelo.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Ejemplares")
@CacheConfig(cacheNames = "ejemplar")
public class ControladorEjemplar {
    private EjemplarRepository ejemplarRepository;

    @Autowired
    public ControladorEjemplar(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public ControladorEjemplar(){}

    @GetMapping("/listarEjemplares")
    public ResponseEntity<Iterable<Ejemplar>> listarEjemplares() {
        return ResponseEntity.ok(ejemplarRepository.findAll());
    }

    @GetMapping("/ejemplar/{id}")
    @Cacheable
    public ResponseEntity<Ejemplar> mostrarEjemplar(@PathVariable int id) {
        try{
            Thread.sleep(5000);
            Ejemplar ejemplar = ejemplarRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(ejemplar);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/agregarEjemplar")
    public ResponseEntity<Ejemplar> agregarEjemplar(@RequestBody @Valid Ejemplar ejemplar){
        ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok(ejemplar);
    }

    @PutMapping("/modificarEjemplar")
    public ResponseEntity<Ejemplar> modificarEjemplar(@RequestBody @Valid Ejemplar ejemplar){
        Ejemplar ejemplarModificado = ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok(ejemplarModificado);
    }

    @DeleteMapping("eliminarEjemplar/{id}")
    public ResponseEntity<String> eliminarEjemplar(@PathVariable int id){
        if (ejemplarRepository.existsById(id)){
            ejemplarRepository.deleteById(id);
            return ResponseEntity.ok("Ejemplar con id " +id+ " eliminado.");
        }
        else{
            return ResponseEntity.ok("Ejemplar con id " +id+ " no encontrado.");
        }
    }


}
