package com.example.Dudar.controllers;


import com.example.Dudar.model.Article;
import com.example.Dudar.repositories.iArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.postprocessor.IPostProcessor;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class BlogController {
  @Autowired
  private iArticleRepository articleRepository;



  @GetMapping("/blog")
  public String blogMain(Model model){
    Iterable<Article> articles= articleRepository.findAll();
    model.addAttribute("articles", articles);
    return "blog-main";
  }

  @GetMapping("/blog/add")
  public String blogAdd(Model model){
    Iterable<Article> articles= articleRepository.findAll();
    model.addAttribute("articles", articles);
    return "blog-add";
  }

  @PostMapping("/blog/add")
  public String blogArticleAdd(@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,Model model){
    Article article = new Article(title, anons, fullText);
    articleRepository.save(article);
    return "redirect:/blog";
  }

  @GetMapping("/blog/{id}")
  public String blogDetails(@PathVariable(value = "id") Long articleId, Model model){
    if(!articleRepository.existsById(articleId)){
      return "redirect:/";
    }
    Optional<Article> article = articleRepository.findById(articleId);
    ArrayList<Article> res = new ArrayList<>();
    article.ifPresent(res::add);
    model.addAttribute("article", res);
    return "blog-details";
  }

  @GetMapping("/blog/{id}/edit")
  public String blogArticleEdit(@PathVariable(value = "id") Long articleId, Model model){
    if(!articleRepository.existsById(articleId)){
      return "redirect:/";
    }
    Optional<Article> article = articleRepository.findById(articleId);
    ArrayList<Article> res = new ArrayList<>();
    article.ifPresent(res::add);
    model.addAttribute("article", res);
    return "blog-edit";
  }

  @PostMapping("/blog/{id}/edit")
  public String blogArticleUpdate(@PathVariable(value = "id") Long articleId,
                                  @RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                                  Model model){
    Article article = articleRepository.findById(articleId).orElseThrow();
    article.setTitle(title);
    article.setAnons(anons);
    article.setFullText(fullText);
    articleRepository.save(article);
    return "redirect:/blog";
  }
  @PostMapping("/blog/{id}/remove")
  public String blogArticleDelete(@PathVariable(value = "id") Long articleId, Model model){
    Article article = articleRepository.findById(articleId).orElseThrow();
    articleRepository.delete(article);
    return "redirect:/blog";
  }
}
