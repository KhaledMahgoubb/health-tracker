package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthViewController {

    private final UserService userService;

    public AuthViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email, 
                              @RequestParam String password, 
                              Model model) {
        try {
            User user = userService.getUserByEmail(email);
            if (user.getPassword().equals(password)) {
                return "redirect:/dashboard/" + user.getId();
            } else {
                model.addAttribute("error", "Invalid password");
                return "login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", "Email not found");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}