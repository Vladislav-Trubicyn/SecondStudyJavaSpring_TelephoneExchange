package ru.telephoneexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.telephoneexchange.model.Role;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.UserRepository;
import ru.telephoneexchange.service.UserService;

import java.util.Collections;

@Controller
public class RegistrationController
{
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model)
    {
        User userFromDb = userService.findByUsername(user.getUsername());
        if(userFromDb == null)
        {
            user.setActive(false);
            user.setMoney(123);
            user.setRoles(Collections.singleton(Role.USER));
            userService.saveUser(user);
            return "login";
        }
        else
        {
            model.addAttribute("errRegMessage", "Такой никнейм занят");
            model.addAttribute("user", new User());
            return "registration";
        }

    }
}
