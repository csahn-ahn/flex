package me.univ.flex.common.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageSortUtil {

    public static Pageable buildPageable(int pageIndex, int pageSize, String sortActive, String sortDirection) {
        Sort sort = Sort.by(sortActive);
        if ( sortDirection != null && sortDirection.equalsIgnoreCase("desc") ) {
            sort = sort.descending();
        }
        return PageRequest.of(pageIndex, pageSize, sort);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static OrderSpecifier[] buildOrderSpecifiers(Sort sort, Path path) {
        return sort.stream().map(order ->
          new OrderSpecifier(
              order.isAscending() ? Order.ASC : Order.DESC,
              Expressions.path(Object.class, path, order.getProperty()))
        ).toArray(OrderSpecifier[]::new);
    }
}