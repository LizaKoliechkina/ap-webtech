package edu.ap.spring.tml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.redis.RedisService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TMLController {

   @Autowired
   private RedisService service;

   @GetMapping("/new")
   public String getCreateForm() {

	   return "newRecipe";
   }

   @PostMapping ("/new")
   public String newRecipe(@RequestParam ("name") String name,
                           @RequestParam ("ingredients") String ingredients,
                           Model model) {

       if(this.service.exists("recipe:"+ name + ":*")) {
           return "exists";
       }
       else {

           LocalDate date = LocalDate.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
           String recipeDate = date.format(formatter);

           String [] listIngredients = ingredients.split(",");

           this.service.setKey("recipe:" + name + ":" + listIngredients + recipeDate,  recipeDate + " " + name + ": " + ingredients);
       }

       return "redirect:/listrecipes";

   }

   @GetMapping("/listrecipes")
   public String listRecipes (Model model) {

       ArrayList<String> recipes = new ArrayList<String>();
       for(String a : this.service.keys("recipe:*")) {
           recipes.add(this.service.getKey(a));
       }
       model.addAttribute("recipes", recipes);

       return "listrecipes";
   }

   @GetMapping("/search")
   public String getSearchForm() {

	   return "searchRecipe";
   }
	
  @PostMapping ("/search")
  public String findRecipe (@RequestParam ("name") String name,
                              Model model) {

           ArrayList<String> recipes = new ArrayList<String>();
           for(String a : this.service.keys("recipe:"+ name + ":*")) {
               recipes.add(this.service.getKey(a));
           }

           model.addAttribute("recipes", recipes);
           model.addAttribute("name", name);
           return "recipeByName";


   }
}
