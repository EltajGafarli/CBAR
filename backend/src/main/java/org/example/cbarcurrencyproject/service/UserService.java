package org.example.cbarcurrencyproject.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.dto.UserDto;
import org.example.cbarcurrencyproject.dto.UserRequestDto;
import org.example.cbarcurrencyproject.entity.User;
import org.example.cbarcurrencyproject.exception.NotFoundException;
import org.example.cbarcurrencyproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto getCurrentUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
        return userToUserDto(user);
    }

    public UserDto updateUser(UserDetails userDetails, UserRequestDto userRequestDto) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        if (userRequestDto.getFirstName() != null) {
            currentUser.setFirstName(
                    userRequestDto.getFirstName()
            );
        }

        if (userRequestDto.getLastName() != null) {
            currentUser.setLastName(userRequestDto.getLastName());
        }

        if (userRequestDto.getEmail() != null) {
            currentUser.setEmail(userRequestDto.getEmail());
        }

        if (userRequestDto.getPassword() != null) {
            currentUser.setPassword(
                    passwordEncoder.encode(userRequestDto.getPassword())
            );
        }

        userRepository.save(currentUser);
        return userToUserDto(currentUser);

    }

    public String deleteUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );



        userRepository.delete(user);
        return "User deleted successfully";
    }



    private UserDto userToUserDto(User user) {
        return UserDto
                .builder()
                .userName(user.getNameOfUser())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleName(user
                        .getRoles()
                        .stream()
                        .map(
                                role -> role.getRole().name()
                        )
                        .toList()
                )
                .build();
    }


}