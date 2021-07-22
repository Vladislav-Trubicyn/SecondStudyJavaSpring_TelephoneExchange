package ru.telephoneexchange.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.telephoneexchange.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController
{
    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();

        session = request.getSession(false);
        if(session != null)
        {
            session.invalidate();
        }

        for(Cookie cookie : request.getCookies())
        {
            cookie.setMaxAge(0);
        }

        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String newUser(@ModelAttribute User user)
    {
        return "registration";
    }
}
