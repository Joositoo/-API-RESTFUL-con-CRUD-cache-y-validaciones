package org.example.bibliotecaspringboot.Controlador;

import jakarta.validation.Valid;
import org.example.bibliotecaspringboot.Modelo.Libro;
import org.example.bibliotecaspringboot.Modelo.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Libros")
@CacheConfig(cacheNames = "libro")
public class ControladorLibro {
    private LibroRepository libroRepository;

    @Autowired
    public ControladorLibro(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public ControladorLibro() {
    }

    @GetMapping("listarLibros")
    public ResponseEntity<Iterable<Libro>> listarLibros() {
        return ResponseEntity.ok(libroRepository.findAll());
    }

    @GetMapping("/libro/{isbn}")
    @Cacheable
    public ResponseEntity<Libro> mostrarLibro(@PathVariable String isbn) {
        try {
            Thread.sleep(5000);
            Libro libro = libroRepository.findById(isbn).orElseThrow();
            return ResponseEntity.ok(libro);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/agregarLibro")
    public ResponseEntity<Libro> agregarLibro(@Valid @RequestBody Libro libro) {
        libroRepository.save(libro);
        return ResponseEntity.ok(libro);
    }

    @PutMapping("/modificarLibro")
    public ResponseEntity<Libro> modificarLibro(@Valid @RequestBody Libro libro) {
        Libro libroModificado = libroRepository.save(libro);
        return ResponseEntity.ok(libroModificado);
    }

    @DeleteMapping("/eliminarLibro/{isbn}")
    public ResponseEntity<String> eliminaLibro(@PathVariable String isbn) {
        libroRepository.deleteById(isbn);
        return ResponseEntity.ok("Libro con isbn: " + isbn + " eliminado.");
    }
}