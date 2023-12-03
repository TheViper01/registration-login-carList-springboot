package registrationlogincarlist.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Role;
import registrationlogincarlist.entity.User;
import registrationlogincarlist.repository.RoleRepository;
import registrationlogincarlist.repository.UserRepository;
import registrationlogincarlist.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        String defaultRoleName = "ROLE_USER";
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(defaultRoleName);
        if(role == null){
            role = checkRoleExist(defaultRoleName);
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void addRole(long userId, Role role){
        User user = userRepository.getById(userId);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.addAll(user.getRoles());
        //List<Role> roles = user.getRoles();
        //roles.add(role);
        roles = roles.stream().distinct().collect(Collectors.toList());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public Role checkRoleExist(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}
