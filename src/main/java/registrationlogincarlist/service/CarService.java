package registrationlogincarlist.service;

import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Car;

import java.util.List;

public interface CarService {
    void saveCar(CarDto carDto);

    Car findByLicensePlate(String licensePlate);

    List<CarDto> findAllCars();
}