package registrationlogincarlist.service.impl;

import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.repository.CarRepository;
import registrationlogincarlist.service.CarService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void saveCar(CarDto carDto) {
        Car car = new Car();
        car.setLicensePlate(carDto.getLicensePlate());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setManufactureYear(carDto.getManufactureYear());
        car.setEngineCapacity(carDto.getEngineCapacity());
        car.setFuel(carDto.getFuel());
        car.setHorsepower(carDto.getHorsepower());
        car.setTorque(carDto.getTorque());
        car.setTrunkVolume(carDto.getTrunkVolume());
        car.setPrice(carDto.getPrice());
        car.setUser(carDto.getUser());

        carRepository.save(car);
    }

    @Override
    public Car findByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public List<CarDto> findAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map((car) -> convertEntityToDto(car))
                .collect(Collectors.toList());
    }

    private CarDto convertEntityToDto(Car car){
        CarDto carDto = new CarDto();
        carDto.setLicensePlate(car.getLicensePlate());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setColor(car.getColor());
        carDto.setManufactureYear(car.getManufactureYear());
        carDto.setEngineCapacity(car.getEngineCapacity());
        carDto.setFuel(car.getFuel());
        carDto.setHorsepower(car.getHorsepower());
        carDto.setTorque(car.getTorque());
        carDto.setTrunkVolume(car.getTrunkVolume());
        carDto.setPrice(car.getPrice());
        carDto.setUser(car.getUser());
        return carDto;
    }
}
