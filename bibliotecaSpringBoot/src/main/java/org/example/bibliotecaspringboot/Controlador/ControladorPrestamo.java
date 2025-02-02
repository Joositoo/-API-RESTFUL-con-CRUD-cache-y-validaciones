package org.example.bibliotecaspringboot.Controlador;

import org.example.bibliotecaspringboot.Modelo.Prestamo;
import org.example.bibliotecaspringboot.Modelo.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Prestamos")
@CacheConfig(cacheNames = "prestamo")
public class ControladorPrestamo {
    private PrestamoRepository prestamoRepository;

    @Autowired
    public ControladorPrestamo(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public ControladorPrestamo(){}

    @GetMapping("/listarPrestamos")
    public ResponseEntity<Iterable<Prestamo>> listarPrestamos() {
        return ResponseEntity.ok(prestamoRepository.findAll());
    }

    @GetMapping("/prestamo/{id}")
    @Cacheable
    public ResponseEntity<Prestamo> mostrarPrestamo(@PathVariable int id) {
        try{
            Thread.sleep(5000);
            Prestamo prestamo = prestamoRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(prestamo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/agregarPrestamo")
    public ResponseEntity<Prestamo> agregarPrestamo(@RequestBody Prestamo prestamo) {
        prestamoRepository.save(prestamo);
        return ResponseEntity.ok(prestamo);
    }

    @PutMapping("/modificarPrestamo")
    public ResponseEntity<Prestamo> modificarPrestamo(@RequestBody Prestamo prestamo) {
        Prestamo prestamoModificado = prestamoRepository.save(prestamo);
        return ResponseEntity.ok(prestamoModificado);
    }

    @DeleteMapping("/eliminarPrestamo/{id}")
    public ResponseEntity<String> eliminarPrestamo(@PathVariable int id) {
        prestamoRepository.deleteById(id);
        return ResponseEntity.ok("El prestamo con id " +id+ " ha sido eliminado.");
    }
}
