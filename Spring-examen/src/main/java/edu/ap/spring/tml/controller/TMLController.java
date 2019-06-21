package edu.ap.spring.tml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.redis.RedisService;

@Controller
public class TMLController {

   @Autowired
   private RedisService service;

   @GetMapping("/new")
   public String getCreateForm() {

	   return "newRecipe";
   }

   @GetMapping("/search")
   public String getSearchForm() {

	   return "searchRecipe";
   }
}
