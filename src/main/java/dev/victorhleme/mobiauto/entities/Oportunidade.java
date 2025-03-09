package dev.victorhleme.mobiauto.entities;

import dev.victorhleme.mobiauto.enums.StatusOportunidade;
import jakarta.persistence.*;
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

    @Column(name = "cliente_nome", nullable = false)
    private String clienteNome;

    @Column(name = "cliente_email")
    private String clienteEmail;

    @Column(name = "cliente_telefone", length = 20)
    private String clienteTelefone;

    @Column(name = "veiculo_marca")
    private String veiculoMarca;

    @Column(name = "veiculo_modelo")
    private String veiculoModelo;

    @Column(name = "veiculo_versao")
    private String veiculoVersao;

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
