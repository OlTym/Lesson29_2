package com.tym;

import com.tym.model.User;
import com.tym.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public String main(Map<String, Object> model) {

      /*  Iterable<User> users = userRepository.findAll();

        model.put("users", users);
*/
        return "main";
    }


    @PostMapping()
    public String addUser(@RequestParam String name, @RequestParam String mail, Map<String, Object> model) {

        Iterable<User> users ;

        if (!mail.isEmpty() && !name.isEmpty()) {

            users = userRepository.findByMail(mail);

            if (users == null) {
                userRepository.save(new User(name, mail));
                model.put("users", users);
            } else {
                model.put("message", "This user is registered in application");
            }


        }


        return "main";
    }


}
