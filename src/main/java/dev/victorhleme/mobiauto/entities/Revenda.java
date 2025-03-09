package dev.victorhleme.mobiauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "revenda")
public class Revenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @Column(name = "nome_social", nullable = false)
    private String nomeSocial;

    @OneToMany(mappedBy = "revenda")
    @JsonIgnore
    private List<Oportunidade> oportunidades;

}
