package com.derek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public Page<Product> listAll(int pageNumber, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
        return repo.findAll(pageable);
    }

    public void save(Product product){
        repo.save(product);
    }

    public Product get(Long id){
        return repo.findById(id).get();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

    public List<Product> search(String keyword){
        return repo.search(keyword);
    }
}
