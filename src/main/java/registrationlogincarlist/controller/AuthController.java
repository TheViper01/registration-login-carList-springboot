package registrationlogincarlist.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.entity.Role;
import registrationlogincarlist.entity.User;
import registrationlogincarlist.service.CarService;
import registrationlogincarlist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value={"/index", "/", ""})
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("user", userDetails);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(10);
        System.out.println("Added admin and user account!");
        UserDto userDto = new UserDto();
        userDto.setFirstName("admin");
        userDto.setLastName("admin");
        userDto.setEmail("admin");
        userDto.setPassword("admin");
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing == null) {
            userService.saveUser(userDto);
        }

        User user = userService.findByEmail("admin");
        userService.addRole(user.getId(), userService.checkRoleExist("ROLE_USER"));
        userService.addRole(user.getId(), userService.checkRoleExist("ROLE_ADMIN"));


        userDto = new UserDto();
        userDto.setFirstName("user");
        userDto.setLastName("user");
        userDto.setEmail("user");
        userDto.setPassword("user");
        existing = userService.findByEmail(userDto.getEmail());
        if (existing == null) {
            userService.saveUser(userDto);
        }

        user = userService.findByEmail("user");
        userService.addRole(user.getId(), userService.checkRoleExist("ROLE_USER"));
    }
}
