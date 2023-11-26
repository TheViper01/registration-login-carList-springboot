package registrationlogincarlist.service.impl;

import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.repository.CarRepository;
import registrationlogincarlist.service.CarService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void saveCar(CarDto carDto) {
        carRepository.save(dtoToEntity(carDto));
    }
    @Override
    public void deleteCar(CarDto carDto){
        carRepository.delete(dtoToEntity(carDto));
    }

    @Override
    public Optional<Car> findByLicensePlate(String licensePlate) {
        return Optional.ofNullable(carRepository.findByLicensePlate(licensePlate));
    }
    @Override
    public Optional<Car> findById(long id) {
        return Optional.ofNullable(carRepository.findById(id));
    }

    @Override
    public List<CarDto> findAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map((car) -> entityToDto(car))
                .collect(Collectors.toList());
    }

    public CarDto entityToDto(Car car){
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
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

    public Car dtoToEntity(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());
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

        return car;
    }
}
