package registrationlogincarlist.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import registrationlogincarlist.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    @NotEmpty
    private String licensePlate;

    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotEmpty
    private String color;
    @NotEmpty
    private String manufactureYear;
    @NotEmpty
    private float engineCapacity;
    @NotEmpty
    private String fuel;
    @NotEmpty
    private float horsepower;
    @NotEmpty
    private float torque;
    @NotEmpty
    private float trunkVolume;
    @NotEmpty
    private float price;
    @NotEmpty
    private User user;
}
