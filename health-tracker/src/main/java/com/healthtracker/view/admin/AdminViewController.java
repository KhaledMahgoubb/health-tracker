package com.healthtracker.view.admin;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.ActivityLogService;
import com.healthtracker.auth.model.ActivityLog;
import com.healthtracker.auth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

	private final UserService userService;
	private final ActivityLogService activityLogService;

	public AdminViewController(UserService userService, ActivityLogService activityLogService) {
	    this.userService = userService;
	    this.activityLogService = activityLogService;
	}

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return "ADMIN".equals(role);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("logs", activityLogService.getAllHistory());
        return "admin/dashboard";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }
}