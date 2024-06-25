package com.java_ne;

import com.java_ne.dtos.user.CreateUserDTO;
import com.java_ne.enumerations.user.EUserRole;
import com.java_ne.models.Role;
import com.java_ne.models.User;
import com.java_ne.repositories.IUserRepository;
import com.java_ne.services.interfaces.RoleService;
import com.java_ne.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class SpringBootMain {
    private final RoleService roleService;
    private final UserService userService;
    private final IUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    @Bean
    public void seedData() {
        Set<EUserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(EUserRole.ADMIN);
        userRoleSet.add(EUserRole.USER);
        for (EUserRole role : userRoleSet) {
            if (!this.roleService.isRolePresent(role)) {
                this.roleService.createRole(role);
            }
        }

        Optional<User> isUserPresent = userRepository.findUserByEmail(adminEmail);
        if (isUserPresent.isEmpty()) {
            CreateUserDTO createUserDTO = new CreateUserDTO(
                    adminEmail,
                    adminUsername,
                    adminPassword,
                    adminFirstName,
                    adminLastName,
                    LocalDate.of(2006,5,23),
                    "+250788401749",
                    "1234567890"
            );
            User userEntity = this.userService.createUserEntity(createUserDTO);
            Role role = this.roleService.getRoleByName(EUserRole.ADMIN);
            userEntity.getRoles().add(role);
            userRepository.save(userEntity);
        }
    }
}
