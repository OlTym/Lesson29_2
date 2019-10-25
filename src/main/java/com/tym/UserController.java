package com.tym;

import com.tym.model.User;
import com.tym.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping()
    public String main(Map<String, Object> model) {

        return "main";
    }


    @GetMapping("/add")
    public String addUser(Map<String, Object> model) {


        return "add";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String mail, Map<String, Object> model) {
        Iterable<User> users;
        if (!mail.isEmpty() && !name.isEmpty()) {
            users = userRepository.findByMail(mail);
            List<User> result = StreamSupport.stream(users.spliterator(), false)
                    .collect(Collectors.toList());
            if (result.size() == 0) {
                userRepository.save(new User(name, mail));
                users = userRepository.findByMail(mail);
                model.put("users", users);
            } else {
                model.put("message", "This email is registered in application");
            }
        }
        return "add";
    }

    @GetMapping("/find")
    public String getUser(Map<String, Object> model) {

        return "find";
    }

    @PostMapping("/find")
    public String getUser(@RequestParam String mail, Map<String, Object> model) {
        Iterable<User> users;
        if (!mail.isEmpty()) {
            users = userRepository.findByMail(mail);
            List<User> result = StreamSupport.stream(users.spliterator(), false)
                    .collect(Collectors.toList());
            if (result.size() != 0) {
                model.put("users", users);
            } else {
                model.put("message", "This email is not registered in application");
            }
        }
        return "find";
    }

    @GetMapping("/getAllUsers")
    public String getAllUser(Map<String, Object> model) {
        Iterable<User> users;
        users = userRepository.findAll();
        List<User> result = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        if (result.size() != 0) {
            model.put("users", users);
        } else {
            model.put("message", "The data base is empty");
        }
        return "main";
    }
}
