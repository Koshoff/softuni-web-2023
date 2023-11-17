package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.service.CategoryService;
import com.example.CryptoTreasures.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;

    public AdminController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @GetMapping("/admin-panel")
    public ModelAndView adminPanel(){
        ModelAndView model = new ModelAndView("admin-panel");
        model.addObject("users", userService.findAllUsers());
        model.addObject("moderators", userService.findUserByRole(Role.MODERATOR));
        model.addObject("bannedUsers", userService.findUserByRole(Role.BANNED));
        return model;
    }

    @PostMapping("/ban-user")
    public ModelAndView banUser(@RequestParam Long id, RedirectAttributes redirectAttributes){
        userService.banUser(id);
        redirectAttributes.addFlashAttribute("message", "User has been banned!");
        return new ModelAndView("redirect:/admin/admin-panel");
    }

    @PostMapping("/unban-user")
    public ModelAndView unbanUser(@RequestParam Long id, RedirectAttributes redirectAttributes){
        userService.unbanUser(id);
        redirectAttributes.addFlashAttribute("message", "User has been unbanned!");
        return new ModelAndView("redirect:/admin/admin-panel");
    }

    @PostMapping("/make-moderator")
    public ModelAndView makeModerator(@RequestParam Long id, RedirectAttributes redirectAttributes){
        userService.makeModerator(id);
        redirectAttributes.addFlashAttribute("message", "User has been promoted to moderator!");
        return new ModelAndView("redirect:/admin/admin-panel");
    }

    @PostMapping("/remove-moderator")
    public ModelAndView removeModerator(@RequestParam Long id, RedirectAttributes redirectAttributes){
        userService.removeModerator(id);
        redirectAttributes.addFlashAttribute("message", "User has been removed as moderator!");
        return new ModelAndView("redirect:/admin/admin-panel");
    }
}
