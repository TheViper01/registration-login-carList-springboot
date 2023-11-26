package registrationlogincarlist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.entity.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private List<Role> roles = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    public String getRolesStr(){
        StringBuilder res = new StringBuilder();
        for (Role role:roles) {
            res.append(role.getName()).append(",");
        }
        if (! res.isEmpty()){
            res.delete(res.length()-1, res.length());
        }
        return res.toString();
    }
}
