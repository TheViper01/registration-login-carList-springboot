package registrationlogincarlist.service;

import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Role;
import registrationlogincarlist.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
    Role checkRoleExist(String roleName);
    void addRole(long userId, Role role);
}
