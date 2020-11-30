package com.just.cache;

import com.just.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
       List<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO progam = new TagDTO();
        progam.setCategoryName("开发语言");
        progam.setTags(Arrays.asList("js","php","css","html","html5","java","node.js","python",
                "c","c++","golang","laravel","spring","express","django","flask","yli","ruby-on-rails","tornado",
                "koa","structs","laravel","spring","express","django","flask","yli","ruby-on-rails","tornado",
                "koa","structs"));
        tagDTOs.add(progam);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django","flask","yli","ruby-on-rails","tornado",
                "koa","structs"));
        tagDTOs.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","nginx","docker","apache","centos"));
        tagDTOs.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","h2","redis","sql","mongodb","oracle","noSql","sqlServer","postgreSql"));
        tagDTOs.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","vscode","vim","xcode","eclipse","maven","ide","svn","idea"));
        tagDTOs.add(tool);

        return tagDTOs;
    }

    public static String filterValid(String tags){
     String[] split = StringUtils.split(tags, ",");
     List<TagDTO> tagDTOS = get();
     List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
     String invalid = Arrays.stream(split).filter(t ->!tagList.contains(t)).collect(Collectors.joining(","));
     return invalid;
    }
}
