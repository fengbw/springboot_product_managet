package com.derek;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.name = :keyword")
    List<Product> search(@Param("keyword") String keyword);
}
