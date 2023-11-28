package registrationlogincarlist.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.dto.UserDto;
import registrationlogincarlist.entity.Car;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CarService {
    void saveCar(CarDto carDto) throws ParseException;
    void saveCar(Car car);
    public void deleteCar(CarDto carDto) throws ParseException;
    public void deleteCar(Car car);
    void deleteCarById(long id);
    Optional<Car> findByLicensePlate(String licensePlate);
    List<Car> findAllCars();
    Optional<Car> findById(long id);
    //CarDto entityToDto(Car car);
    //Car dtoToEntity(CarDto carDto);
    static CarDto convertToDto(Car car) {
        ModelMapper modelMapper = new ModelMapper();
        CarDto carDto = modelMapper.map(car, CarDto.class);;
        carDto.setUserId(car.getUser().getId());
        return carDto;
    }
     Car convertToEntity(CarDto carDto) throws ParseException;

    Page<Car> findBySearchCriteria(Specification<Car> spec, Pageable page);

}