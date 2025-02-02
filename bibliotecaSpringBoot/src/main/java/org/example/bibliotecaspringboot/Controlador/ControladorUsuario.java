package org.example.bibliotecaspringboot.Controlador;

import org.example.bibliotecaspringboot.Modelo.Usuario;
import org.example.bibliotecaspringboot.Modelo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Usuarios")
@CacheConfig(cacheNames = "usuario")
public class ControladorUsuario {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public ControladorUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ControladorUsuario() {}

    @GetMapping("/listarUsuarios")
    public ResponseEntity<Iterable<Usuario>> listarusuarios(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/usuario/{id}")
    @Cacheable
    public ResponseEntity<Usuario> mostrarUsuario(@PathVariable int id){
        try{
            Thread.sleep(5000);
            Usuario usuario = usuarioRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(usuario);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/agregarUsuario")
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/modificarUsuario")
    public ResponseEntity<Usuario> modificarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioModificado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioModificado);
    }

    @DeleteMapping("eliminarUsuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id){
        usuarioRepository.findById(id);
        return ResponseEntity.ok("Usuario con id " +id+ " eliminado.");
    }
}
