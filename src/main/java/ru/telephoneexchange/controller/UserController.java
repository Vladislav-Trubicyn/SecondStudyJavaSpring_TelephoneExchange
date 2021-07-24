package ru.telephoneexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.telephoneexchange.model.Role;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.UserRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String showUserList(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userlist";
    }

    @GetMapping("/{id}")
    public String userEditForm(@PathVariable("id") User user, Model model)
    {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PostMapping()
    public String userSaveEdit(@RequestParam Map<String, String> form,
                               @RequestParam("id") User user,
                               @RequestParam("username") String username,
                               @RequestParam("money") int money)
    {
        user.setUsername(username);
        user.setMoney(money);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet())
        {
            if(roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
        return "redirect:/users";
    }
}