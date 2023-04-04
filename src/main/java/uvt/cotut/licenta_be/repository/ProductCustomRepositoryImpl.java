package uvt.cotut.licenta_be.repository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import uvt.cotut.licenta_be.model.Product;
import uvt.cotut.licenta_be.service.api.dto.FilterCriteriaDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Product> findProductsFiltered(FilterCriteriaDTO dto) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        final Root<Product> root = criteriaQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isTrue(root.get("available")));

        if (StringUtils.isNotBlank(dto.getName())) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + dto.getName().toUpperCase() + "%")));
        }

        if (dto.getSubCategory() != null && !dto.getSubCategory().isEmpty()) {
            predicates.add(criteriaBuilder.and(root.get("subCategory").get("name").in(dto.getSubCategory())));
        }

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            predicates.add(criteriaBuilder.and(root.get("subCategory").get("category").get("name").in(dto.getCategory())));
        }

        if (dto.getLowerPrice() != null && dto.getLowerPrice() > 0) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("price"), dto.getLowerPrice())));
        }

        if (dto.getUpperPrice() != null && dto.getUpperPrice() > 0) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.lessThan(root.get("price"), dto.getUpperPrice())));
        }

        if (Boolean.TRUE.equals(dto.getInStock())) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("amount"), 0)));
        }

        Order order = criteriaBuilder.asc(root.get("name"));
        if (StringUtils.isNotBlank(dto.getSort())) {
            String sort = dto.getSort().substring(1);
            order = "+".equals(dto.getSort().substring(0, 1)) ? criteriaBuilder.asc(root.get(sort)) : criteriaBuilder.desc(root.get(sort));
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).orderBy(order);
        int limit = 20;
        int offset = (dto.getPage() - 1) * limit;
        return entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
