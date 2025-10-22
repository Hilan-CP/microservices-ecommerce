package com.hdev.product_service.service;

import com.hdev.product_service.dto.CategoryDTO;
import com.hdev.product_service.entity.Category;
import com.hdev.product_service.mapper.CategoryMapper;
import com.hdev.product_service.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryMapper.toDto(category)).toList();
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto){
        Category category = CategoryMapper.toEntity(dto, new Category());
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        category = CategoryMapper.toEntity(dto, category);
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }
}
