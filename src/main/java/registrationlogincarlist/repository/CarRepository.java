package registrationlogincarlist.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import registrationlogincarlist.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car> {
    Car findByLicensePlate(String licensePlate);
    Car findById(long id);
}
