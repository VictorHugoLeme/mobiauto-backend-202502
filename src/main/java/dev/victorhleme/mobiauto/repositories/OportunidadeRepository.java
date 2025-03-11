package dev.victorhleme.mobiauto.repositories;

import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long>, JpaSpecificationExecutor<Oportunidade> {
    Long countByResponsavel(Usuario responsavel);
    Optional<Oportunidade> findTopByResponsavelOrderByDataAtribuicaoDesc(Usuario responsavel);
}
