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

@Controller
@RequestMapping("/services")
public class ServiceController
{
    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping()
    public String showServiceList(@AuthenticationPrincipal User user, Model model)
    {
        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        model.addAttribute("serviceSwitch", "servicelist");
        model.addAttribute("roles", user.getRoles());
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
        serviceRepository.save(service);
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
        serviceRepository.save(service);
        return "redirect:/services";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteServiceAdmin(@PathVariable("id") Service service)
    {
        serviceRepository.delete(service);
        return "redirect:/services";
    }

}
