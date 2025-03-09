package dev.victorhleme.mobiauto.entities;

import dev.victorhleme.mobiauto.enums.StatusOportunidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "oportunidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oportunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cliente_nome", nullable = false)
    private String clienteNome;

    @NotBlank
    @Column(name = "cliente_email")
    private String clienteEmail;

    @NotBlank
    @Column(name = "cliente_telefone", length = 20)
    private String clienteTelefone;

    @NotBlank
    @Column(name = "veiculo_marca")
    private String veiculoMarca;

    @NotBlank
    @Column(name = "veiculo_modelo")
    private String veiculoModelo;

    @NotBlank
    @Column(name = "veiculo_versao")
    private String veiculoVersao;

    @NotBlank
    @Column(name = "veiculo_ano")
    private String veiculoAno;

    @Enumerated(EnumType.STRING)
    private StatusOportunidade status = StatusOportunidade.NOVO;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario responsavel;

    @Column(name = "data_atribuicao")
    private LocalDateTime dataAtribuicao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(name = "motivo_conclusao")
    private String motivoConclusao;

    @ManyToOne
    @JoinColumn(name = "revenda_id", nullable = false)
    private Revenda revenda;
}
