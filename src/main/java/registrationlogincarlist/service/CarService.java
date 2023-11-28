package registrationlogincarlist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    void saveCar(CarDto carDto);
    void deleteCar(CarDto carDto);
    Optional<Car> findByLicensePlate(String licensePlate);
    List<CarDto> findAllCars();
    Optional<Car> findById(long id);
    CarDto entityToDto(Car car);
    Car dtoToEntity(CarDto carDto);

    Page<CarDto> findBySearchCriteria(Specification<Car> spec, Pageable page);

}