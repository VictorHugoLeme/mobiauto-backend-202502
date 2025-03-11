package dev.victorhleme.mobiauto.repositories.specifications;

import dev.victorhleme.mobiauto.entities.Oportunidade;
import dev.victorhleme.mobiauto.filters.OportunidadeFilter;
import dev.victorhleme.mobiauto.utils.SpecificationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OportunidadeSpecifications {

    public Specification<Oportunidade> getSpecification(OportunidadeFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(SpecificationUtils.fieldLike("clienteNome", filter.getClienteNome(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("clienteEmail", filter.getClienteEmail(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("clienteTelefone", filter.getClienteTelefone(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("veiculoMarca", filter.getVeiculoMarca(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("veiculoModelo", filter.getVeiculoModelo(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("veiculoVersao", filter.getVeiculoVersao(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("veiculoAno", filter.getVeiculoAno(), cb, root));
            predicates.add(SpecificationUtils.fieldEnumEqual("status", filter.getStatus(), cb, root));

            predicates.add(SpecificationUtils.dateEqualOrBefore("dataConclusao", filter.getConclusaoBefore(), cb, root));
            predicates.add(SpecificationUtils.dateEqualOrAfter("dataConclusao", filter.getConclusaoAfter(), cb, root));

            predicates.add(SpecificationUtils.dateEqualOrBefore("dataAtribuicao", filter.getAtribuicaoBefore(), cb, root));
            predicates.add(SpecificationUtils.dateEqualOrAfter("dataAtribuicao", filter.getAtribuicaoAfter(), cb, root));

            if (filter.getResponsavelId() != null)
                predicates.add(cb.equal(root.get("responsavel").get("id"), filter.getResponsavelId()));

            if (filter.getRevendaId() != null)
                predicates.add(cb.equal(root.get("revenda").get("id"), filter.getRevendaId()));

            assert query != null;
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return null;
        };
    }

}
