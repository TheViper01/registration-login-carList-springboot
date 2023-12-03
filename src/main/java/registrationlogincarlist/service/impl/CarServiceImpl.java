package registrationlogincarlist.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.entity.User;
import registrationlogincarlist.repository.CarRepository;
import registrationlogincarlist.repository.UserRepository;
import registrationlogincarlist.service.CarService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;

    public CarServiceImpl(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveCar(Car car) {
        carRepository.save(car);
    }
    @Override
    public void saveCar(CarDto carDto) throws ParseException {
        Car car = convertToEntity(carDto);
        carRepository.save(car);
    }
    @Override
    public void deleteCar(CarDto carDto) throws ParseException {
        Car car = convertToEntity(carDto);
        carRepository.delete(car);
    }
    @Override
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public void deleteCarById(long id) {
        carRepository.deleteById(id);
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
    public List<Car> findAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    @Override
    public Page<Car> findBySearchCriteria(Specification<Car> spec, Pageable page){
        //Page<CarDto> searchResult = carRepository.findAll(spec, page).map(this::entityToDto);
        Page<Car> searchResult = carRepository.findAll(spec, page);
        return searchResult;
    }

    public Car convertToEntity(CarDto carDto) throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        Car car = modelMapper.map(carDto, Car.class);
        User user = userRepository.getById(carDto.getUserId());
        if (user != null) {
            car.setUser(user);
        }
        else{
            throw new ParseException(String.format("User id not found! ID=%d", carDto.getUserId()), 13);
        }
        return car;
    }
/*
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
 */
}
