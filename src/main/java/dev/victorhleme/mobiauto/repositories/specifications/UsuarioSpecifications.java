package dev.victorhleme.mobiauto.repositories.specifications;

import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.filters.UsuarioFilter;
import dev.victorhleme.mobiauto.utils.SpecificationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioSpecifications {

    public Specification<Usuario> getSpecification(UsuarioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(SpecificationUtils.fieldLike("cnpj", filter.getNome(), cb, root));
            predicates.add(SpecificationUtils.fieldLike("nomeSocial", filter.getEmail(), cb, root));
            predicates.add(SpecificationUtils.fieldEnumEqual("cargo", filter.getCargo(), cb, root));

            if (filter.getRevendaId() != null) {
                predicates.add(cb.equal(root.get("revenda").get("id"), filter.getRevendaId()));
            }

            assert query != null;
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return null;
        };
    }

}
