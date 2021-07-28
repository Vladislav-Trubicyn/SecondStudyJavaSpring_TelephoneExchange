package ru.telephoneexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.telephoneexchange.model.Role;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.UserRepository;
import ru.telephoneexchange.service.UserService;

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

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showUserList(Model model)
    {
        Iterable<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userlist";
    }

    @GetMapping("/{id}")
    public String userEditForm(@PathVariable("id") User user, Model model)
    {
        User userFromDb = userService.findByUsername(user.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("servicesAttach", userFromDb.getServices());
        model.addAttribute("userMoney", userFromDb.getMoney());
        return "useredit";
    }

    @PostMapping()
    public String userSaveEdit(@RequestParam Map<String, String> form,
                               @RequestParam("id") User user,
                               @RequestParam("username") String username,
                               @RequestParam("money") int money,
                               @RequestParam(name = "active", required = true, defaultValue = "false") boolean active,
                               Model model)
    {
        user.setUsername(username);
        user.setMoney(money);
        user.setActive(active);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet())
        {
            if(roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

}
