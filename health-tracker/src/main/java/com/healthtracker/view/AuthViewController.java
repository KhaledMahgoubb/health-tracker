package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.ActivityLogService;
import com.healthtracker.auth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class AuthViewController {

	private final UserService userService;
	private final ActivityLogService activityLogService;

	public AuthViewController(UserService userService, ActivityLogService activityLogService) {
	    this.userService = userService;
	    this.activityLogService = activityLogService;
	}

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute User user,
	            BindingResult result,
	            Model model) {
	if (result.hasErrors()) {
	return "register";
	}
	try {
	User saved = userService.registerUser(user);
	activityLogService.log(saved, "User registered");
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
                              HttpSession session,
                              Model model) {
        try {
            User user = userService.getUserByEmail(email);
            if (user.getPassword().equals(password)) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                activityLogService.log(user, "User logged in");
                if (user.getRole().equals("ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/user/dashboard";
                }
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
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}