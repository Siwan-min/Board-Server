package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    public int register(CategoryDTO categoryDTO);
    public int updateCategory(CategoryDTO categoryDTO);
    public int deleteCategory(int categoryId);
}
