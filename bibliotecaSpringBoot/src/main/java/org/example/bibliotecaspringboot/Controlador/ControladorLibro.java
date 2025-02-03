package org.example.bibliotecaspringboot.Controlador;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.example.bibliotecaspringboot.Modelo.Libro;
import org.example.bibliotecaspringboot.Modelo.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> mostrarLibro(@PathVariable String isbn) {
        if (!isbn.matches("^978[0-9]{10}$")) {
            return ResponseEntity.badRequest().body("El ISBN debe empezar por '978' y seguirle 10 dígitos.");
        }
        try {
            Thread.sleep(5000);
            Libro libro = libroRepository.findById(isbn).orElseThrow();
            return ResponseEntity.ok(libro);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/agregarLibro")
    public ResponseEntity<?> agregarLibro(@Valid @RequestBody Libro libro) {
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
        if (!libroRepository.existsById(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro con ISBN: " + isbn + " no encontrado.");
        }

        libroRepository.deleteById(isbn);
        return ResponseEntity.ok("Libro con ISBN: " + isbn + " eliminado exitosamente.");
    }
}