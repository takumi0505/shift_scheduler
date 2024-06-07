package com.example.shift_scheduler.service;

import com.example.shift_scheduler.entity.User;
import com.example.shift_scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String[] COLORS = {
            "#FF5733", "#33FF57", "#3357FF", "#FF33A5", "#A533FF",
            "#FF8F33", "#33FFCE", "#FF3333", "#8FFF33", "#5733FF",
            "#33FFA5", "#FF33CE", "#33A5FF", "#A5FF33", "#FF5733",
            "#57FF33", "#FF3357", "#33FF8F", "#5733FF", "#FF5733"
    };

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        String color = assignColor();
        user.setColor(color);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String assignColor() {
        List<User> users = userRepository.findAll();
        for (String color : COLORS) {
            boolean colorUsed = users.stream().anyMatch(user -> color.equals(user.getColor()));
            if (!colorUsed) {
                return color;
            }
        }
        // もし全ての色が使用されている場合、ランダムに選択する
        Random random = new Random();
        return COLORS[random.nextInt(COLORS.length)];
    }


}
