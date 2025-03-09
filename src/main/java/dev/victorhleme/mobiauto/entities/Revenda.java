package dev.victorhleme.mobiauto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Data
@Table(name = "revenda")
public class Revenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="CNPJ must not be blank")
    @CNPJ(message="Invalid CNPJ")
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;


    @NotBlank(message="Nome Social must not be blank")
    @Column(name = "nome_social", nullable = false)
    private String nomeSocial;

    @OneToMany(mappedBy = "revenda")
    @JsonIgnore
    private List<Oportunidade> oportunidades;

}
