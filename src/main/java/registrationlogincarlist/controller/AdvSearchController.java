package registrationlogincarlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import registrationlogincarlist.advSearch.EmpSpecificationBuilder;
import registrationlogincarlist.advSearch.EmployeeSearchDto;
import registrationlogincarlist.advSearch.SearchCriteria;
import registrationlogincarlist.dto.CarDto;
import registrationlogincarlist.service.CarService;
import registrationlogincarlist.service.UserService;
import registrationlogincarlist.utils.APIResponse;

import java.util.List;
import java.util.stream.Collectors;
/*

    {
            "dataOption":"all",
                "searchCriteriaList":[
            {
                "filterKey":"licensePlate",
                "operation":"eq",
                "value":"GJ68CMVGGG"
            }]
    }
*/
@CrossOrigin(maxAge = 10)
@RestController
@RequestMapping("/api/v1")
public class AdvSearchController {

    @Autowired
    private CarService carService;
    private UserService userService;

    public AdvSearchController(UserService userService, CarService carService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping("/cars")
    public ResponseEntity<APIResponse> getAllEmployees(){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(carService.findAllCars().stream().map((car) -> CarService.convertToDto(car))
                .collect(Collectors.toList()));
        apiResponse.setMessage("Car record retrieved successfully");
        apiResponse.setResponseCode(HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }

    @PostMapping("/cars/search")
    public ResponseEntity<APIResponse> searchEmployees(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                       @RequestParam(name = "pageSize", defaultValue = "10000") int pageSize,
                                                       @RequestBody EmployeeSearchDto employeeSearchDto){
        System.out.println("employeeSearchDto:" + employeeSearchDto);
        APIResponse apiResponse = new APIResponse();
        EmpSpecificationBuilder builder = new EmpSpecificationBuilder();
        List<SearchCriteria> criteriaList = employeeSearchDto.getSearchCriteriaList();
        if(criteriaList != null){
            criteriaList.forEach(x-> {x.setDataOption(employeeSearchDto.getDataOption());
                builder.with(x);
            });
        }

        Pageable page = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        Page<CarDto> employeePage = carService.findBySearchCriteria(builder.build(), page).map(CarService::convertToDto);

        apiResponse.setData(employeePage.toList());
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully retrieved employee record");

        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }

}
