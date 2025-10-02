package com.hdev.ecommerce.service;

import com.hdev.ecommerce.dto.CategoryDTO;
import com.hdev.ecommerce.entity.Category;
import com.hdev.ecommerce.mapper.CategoryMapper;
import com.hdev.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryMapper.toDto(category)).toList();
    }

    public CategoryDTO createCategory(CategoryDTO dto){
        Category category = CategoryMapper.toEntity(dto, new Category());
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        category = CategoryMapper.toEntity(dto, category);
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }
}
