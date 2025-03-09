package dev.victorhleme.mobiauto.entities;

import dev.victorhleme.mobiauto.enums.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "revenda_id", nullable = false)
    private Revenda revenda;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Cargo cargo;

}
