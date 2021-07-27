package ru.telephoneexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.telephoneexchange.model.Service;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.ServiceRepository;
import ru.telephoneexchange.repository.UserRepository;
import ru.telephoneexchange.service.ServiceService;
import ru.telephoneexchange.service.UserService;

import java.util.Collections;

@Controller
@RequestMapping("/services")
public class ServiceController
{
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showServiceList(@AuthenticationPrincipal User user, Model model)
    {
        Iterable<Service> services = serviceService.findAllServices();
        model.addAttribute("services", services);
        model.addAttribute("serviceSwitch", "servicelist");
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("servicesAttach", user.getServices());
        return "service";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddServiceFormAdmin(@ModelAttribute Service service, Model model)
    {
        model.addAttribute("serviceSwitch", "addservice");
        return "service";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addServiceAdmin(@ModelAttribute Service service, Model model)
    {
        serviceService.addServiceAdmin(service);
        model.addAttribute("serviceSwitch", "servicelist");
        return"redirect:/services";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showEditServiceFormAdmin(@PathVariable("id") Service service, Model model)
    {
        model.addAttribute("service", service);
        model.addAttribute("serviceSwitch", "editservice");
        return "service";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveEditServiceAdmin(@RequestParam("id") Service service,
                                  @RequestParam("title") String title,
                                  @RequestParam("price") int price)
    {
        service.setTitle(title);
        service.setPrice(price);

        serviceService.saveEditServiceAdmin(service);
        return "redirect:/services";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteServiceAdmin(@PathVariable("id") Service service)
    {
        serviceService.deleteServiceAdmin(service);
        return "redirect:/services";
    }

    @GetMapping("/{id}/add")
    public String userAddService(@AuthenticationPrincipal User user, @PathVariable("id") Service service)
    {
        userService.userAddService(user, service);
        return "redirect:/services";
    }

}
