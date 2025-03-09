package dev.victorhleme.mobiauto.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

import static java.text.MessageFormat.format;

public class SpecificationUtils {

    public static Predicate fieldLike(String field, String term, CriteriaBuilder cb, Root<?> root) {
        if (term == null || term.isEmpty())
            return cb.conjunction();
        return cb.like(cb.lower(root.get(field).as(String.class)), likePattern(term));
    }

    public static <E extends Enum<E>> Predicate fieldEnumEqual(String field, E value, CriteriaBuilder cb, Root<?> root) {
        if (value == null)
            return cb.conjunction();
        return cb.equal(root.get(field), value);
    }

    public static <E extends Enum<E>> Predicate fieldEnumIn(String fieldName, List<E> values, CriteriaBuilder cb, Root<?> root) {
        if (values == null || values.isEmpty())
            return cb.conjunction();
        return root.get(fieldName).in(values);
    }

    public static Predicate fieldBooleanEqual(String field, Boolean value, CriteriaBuilder cb, Root<?> root) {
        if (value == null)
            return cb.conjunction();
        return value ? cb.isTrue(root.get(field)) : cb.isFalse(root.get(field));
    }

    public static Predicate dateEqualOrAfter(String field, LocalDateTime dateTime, CriteriaBuilder cb, Root<?> root) {
        if (dateTime == null)
            return cb.conjunction();
        return cb.greaterThanOrEqualTo(root.get(field), dateTime);
    }

    public static Predicate dateEqualOrBefore(String field, LocalDateTime dateTime, CriteriaBuilder cb, Root<?> root) {
        if (dateTime == null)
            return cb.conjunction();
        return cb.lessThanOrEqualTo(root.get(field), dateTime);
    }

    public static String likePattern(String term) {
        return format("%{0}%", term.toLowerCase());
    }

}
