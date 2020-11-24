package com.just.mapper;

import com.just.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create (Question question);
   
    @Select("select * from question order by id desc limit #{offSize},#{size}")
    List<Question> list(@Param("offSize") Integer offSize,@Param("size") Integer size);
   
    @Select("select * from question  where creator =  #{userId} order by id desc limit #{offSize},#{size} ")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param("offSize") Integer offSize,@Param("size") Integer size);
   
    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator =  #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);
    @Select("select * from question where id =  #{id}")
    Question getById(Integer id);
}
