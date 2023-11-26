package registrationlogincarlist.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.entity.Car;
import registrationlogincarlist.service.CarService;
import registrationlogincarlist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CarController {

    private UserService userService;
    private CarService carService;

    public CarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }


    @GetMapping("/car/view")
    public String carTable(Model model){
        List<CarDto> cars = carService.findAllCars();
        model.addAttribute("cars", cars);
        return "car-table";
    }

    @GetMapping("/car/edit/{id}")
    public String showEditCarForm(@PathVariable("id") long id, Model model) {
        Car car = carService.findById(id).orElseThrow(() -> new IllegalArgumentException("car id not found:" + id));
        CarDto carUpdate = carService.entityToDto(car);
        model.addAttribute("car", carUpdate);
        return "update-car";
    }

    @PostMapping("/car/update/{id}")
    public String updateCar(@PathVariable("id") long id, @Valid @ModelAttribute("car") CarDto carUpdate,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("car", carUpdate);
            return "update-car";
        }
        Car car = carService.findById(id).orElseThrow(() -> new IllegalArgumentException("Car ID not found:" + carUpdate.getId()));
        if (! carUpdate.getLicensePlate().equals(car.getLicensePlate())){
            Car tempCar = carService.findByLicensePlate(carUpdate.getLicensePlate()).orElse(null);
            if (tempCar != null){
                result.rejectValue("licensePlate", null, "This License Plate is already registered");
                carUpdate.setLicensePlate(car.getLicensePlate());
                model.addAttribute("car", carUpdate);
                return "update-car";
            }
        }
        carUpdate.setUser(car.getUser());
        carService.saveCar(carUpdate);
        return "redirect:/car/view";
    }

    @GetMapping("/car/delete/{id}")
    public String deleteCar(@PathVariable("id") long id, Model model) {
        Car car = carService.findById(id).orElseThrow(() -> new IllegalArgumentException("car id not found:" + id));
        carService.deleteCar(carService.entityToDto(car));
        return "redirect:/car/view";
    }

    @PostMapping("/car/add")
    public String saveCar(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("car") CarDto carUpdate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("car", carUpdate);
            return "add-car";
        }
        Car car = carService.findByLicensePlate(carUpdate.getLicensePlate()).orElse(null);
        if (car != null){
            result.rejectValue("licensePlate", null, "License Plate already registered!");
            model.addAttribute("car", carUpdate);
            return "add-car";
        }
        carUpdate.setUser(userService.findByEmail(userDetails.getUsername()));
        carService.saveCar(carUpdate);
        return "redirect:/car/view";
    }
    @GetMapping("/car/new")
    public String newCarForm(Model model) {
        CarDto carUpdate = new CarDto();
        model.addAttribute("car", carUpdate);
        return "add-car";
    }
}
