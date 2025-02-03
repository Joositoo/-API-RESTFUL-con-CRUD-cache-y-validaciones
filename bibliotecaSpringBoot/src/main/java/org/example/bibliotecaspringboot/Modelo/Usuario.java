package org.example.bibliotecaspringboot.Modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@EntityListeners(Usuario.usuarioValidador.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", nullable = false, length = 15)
    @Pattern(regexp = "^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$")
    @NotBlank(message = "El dni no puede ser nulo ni contener una cadena vacía.")
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^\\w{2,100}$", message = "El nombre debe ser de entre 2 y 100 caracteres alfanuméricos.")
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    @NotBlank(message = "El email no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^[A-Za-z0-9]{1,50}@gmail.com$", message = "EL email debe ser @gmail.com")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "La contraseña no puede ser nula ni contener una cadena vacía.")
    @Pattern(regexp = "^\\w{4,12}$", message = "La contraseña debe contener entre 4 y 12 caracteres.")
    private String password;

    @Lob
    @Column(name = "tipo", nullable = false)
    @NotBlank(message = "El tipo no puede ser nulo ni contener una cadena vacía.")
    @Pattern(regexp = "^(normal|administrador)$", message = "El tipo debe ser 'normal' o 'administrador'.")
    private String tipo;

    @Column(name = "penalizacion_hasta")
    private LocalDate penalizacionHasta;

    @OneToMany(mappedBy = "usuario")
    @JsonBackReference("usuario")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getPenalizacionHasta() {
        return penalizacionHasta;
    }

    public void setPenalizacionHasta(LocalDate penalizacionHasta) {
        this.penalizacionHasta = penalizacionHasta;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public static class usuarioValidador{
        @PrePersist
        @PreUpdate
        public void validar(Usuario usuario) throws Exception {
            validadorDNI(usuario.getDni());
        }
    }

    public static boolean validadorDNI(String dni) throws Exception {
        String dniLetters = "TRWAGMYFPDXBNJZSQVHLCKE";
        String dniParamLetter = dni.substring(dni.length()-1);
        dni = dni.substring(0, dni.length()-1);
        int module = Integer.parseInt(dni) % 23;
        if(Character.toString(dniLetters.charAt(module)).equalsIgnoreCase(dniParamLetter))
            return true;
        else throw new Exception("El dni no es correcto");
    }

}