package dev.victorhleme.mobiauto.repositories.specifications;

import dev.victorhleme.mobiauto.entities.Revenda;
import dev.victorhleme.mobiauto.filters.RevendaFilter;
import dev.victorhleme.mobiauto.utils.SpecificationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RevendaSpecifications {

    public Specification<Revenda> getSpecification(RevendaFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(SpecificationUtils.fieldLike("cnpj", filter.getCnpj(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("nomeSocial", filter.getNomeSocial(), cb, root));

            assert query != null;
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return null;
        };
    }

}
