package org.example.bibliotecaspringboot.Modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @NotBlank(message = "El isbn no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^978[0-9]{10}$", message = "El ISBN debe empezar por '978' y seguirle 10 dígitos.")
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    @NotBlank(message = "El título no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^\\w{2,200}$", message = "El título debe ser de entre 2 y 200 caracteres alfanuméricos.")
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    @NotBlank(message = "El autor no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^\\w{2,100}$", message = "El título debe ser de entre 2 y 100 caracteres alfanuméricos.")
    private String autor;

    @OneToMany(mappedBy = "isbn")
    @JsonManagedReference("ejemplares")
    private Set<Ejemplar> ejemplars = new LinkedHashSet<>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Set<Ejemplar> getEjemplars() {
        return ejemplars;
    }

    public void setEjemplars(Set<Ejemplar> ejemplars) {
        this.ejemplars = ejemplars;
    }

}