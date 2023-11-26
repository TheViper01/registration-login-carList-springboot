package registrationlogincarlist.repository;

import registrationlogincarlist.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    Car findByLicensePlate(String licensePlate);
    Car findById(long id);
}
