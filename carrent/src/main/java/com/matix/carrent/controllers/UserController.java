package com.matix.carrent.controllers;

import com.matix.carrent.model.User;
import com.matix.carrent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;


@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users_attr", userRepository.findAll());
        return "users";
    }

    @RequestMapping(value = "/add_user_form", method= RequestMethod.GET)
    public String addUserForm(Model model) {
        User newUser = new User();
        model.addAttribute("user_attr", newUser);
        return "user_new";
    }

    @RequestMapping(value = "/save_user", method=RequestMethod.POST)
    public String saveCar(@ModelAttribute(value="user") User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/{user_id}/delete_form", method=RequestMethod.GET)
    public String deleteCarForm(Model model, @PathVariable(value="user_id") long userId) {
        Optional<User> maybeUser = userRepository.findById(userId);
        if(maybeUser.isPresent()) {
            model.addAttribute("user_attr", maybeUser.get());
        }
        return "user_delete";
    }

    @RequestMapping(value = "/delete_user", method=RequestMethod.POST)
    public String deleteCar(Model model, @ModelAttribute(value="user") User user) {
        if (!user.getRentedCars().isEmpty()) {
            model.addAttribute("cannot_delete_warning_attr", true);
            return "user_delete";
        }
        userRepository.delete(user);
        return "redirect:/users";
    }

}
