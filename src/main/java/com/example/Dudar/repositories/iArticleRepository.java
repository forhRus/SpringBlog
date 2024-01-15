package com.example.Dudar.repositories;

import com.example.Dudar.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface iArticleRepository extends CrudRepository<Article, Long> {

}
