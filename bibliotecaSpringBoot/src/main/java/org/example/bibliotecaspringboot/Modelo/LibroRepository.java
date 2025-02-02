package org.example.bibliotecaspringboot.Modelo;

import org.springframework.data.repository.CrudRepository;

public interface LibroRepository extends CrudRepository<Libro, String> {
}
