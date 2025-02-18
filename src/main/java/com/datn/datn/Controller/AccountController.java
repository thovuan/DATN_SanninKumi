package com.datn.datn.Controller;

import com.datn.datn.Model.Users;
import com.datn.datn.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class AccountController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("sub", token.getPrincipal().getAttribute("sub"));
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));

        String email = token.getPrincipal().getAttribute("email");

        LocalDateTime dt = LocalDateTime.now();
        Users user = new Users();
        user.setUsername("User1");
        user.setEmail(token.getPrincipal().getAttribute("email"));
        user.setName(token.getPrincipal().getAttribute("name"));
        user.setCreate_at(dt);
        user.setUpdate_at(dt);
        //user.setPassword("");
        user.setUsername(email.split("@")[0]);
        user.setProvider(Users.Provider.GOOGLE);
        user.setProvider_id(token.getPrincipal().getAttribute("sub"));
        user.setAvatar_url(token.getPrincipal().getAttribute("picture"));

        try {
            usersService.RegisterUser(user);
            return "/ZenAccountPage/ProfilePage";

        } catch (Exception ex) {
            return "/ErrorPage";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "/ZenAccountPage/LoginPage";
    }
}
