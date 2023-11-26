package registrationlogincarlist.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
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
    private long id;
    @NotEmpty
    private String licensePlate;

    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotEmpty
    private String color;

    private int manufactureYear;

    private float engineCapacity;

    private String fuel;

    private float horsepower;

    private float torque;

    private float trunkVolume;

    private float price;

    private User user;
}
