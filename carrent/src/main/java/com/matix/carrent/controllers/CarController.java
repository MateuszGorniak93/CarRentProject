package com.matix.carrent.controllers;

import com.matix.carrent.model.Car;
import com.matix.carrent.model.User;
import com.matix.carrent.repositories.CarRepository;
import com.matix.carrent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {
   

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/cars", method= RequestMethod.GET)
    public String getCars(Model model) {
        model.addAttribute("cars_attr", carRepository.findAll());
        return "cars";
    }

    @RequestMapping(value = "/add_car_form", method= RequestMethod.GET)
    public String addCarForm(Model model) {
        Car newCar = new Car();
        model.addAttribute("car_attr", newCar);
        return "car_new";
    }

    @RequestMapping(value = "/save_car", method=RequestMethod.POST)
    public String saveCar(@ModelAttribute(value="car") Car car) {
        
        if (car.getRentingUserId() != null) {
            Optional<User> maybeUser = userRepository.findById(car.getRentingUserId());
            if (maybeUser.isPresent()) {
                car.setUser(maybeUser.get());
            }
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/cars/{car_id}/edit_form", method=RequestMethod.GET)
    public String editCarForm(Model model, @PathVariable(value="car_id") long carId) {       
        Optional<Car> maybeCar = carRepository.findById(carId);
        if(maybeCar.isPresent()) {
            model.addAttribute("car_attr", maybeCar.get());
        }
        return "car_edit";
    }

    @RequestMapping(value = "/cars/{car_id}/delete_form", method=RequestMethod.GET)
    public String deleteCarForm(Model model, @PathVariable(value="car_id") long carId) {
        Optional<Car> maybeCar = carRepository.findById(carId);
        if(maybeCar.isPresent()) {
            model.addAttribute("car_attr", maybeCar.get());
        }
        return "car_delete";
    }

    @RequestMapping(value = "/delete_car", method=RequestMethod.POST)
    public String deleteCar(@ModelAttribute(value="car") Car car) {
        carRepository.delete(car);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/cars/{car_id}/return", method=RequestMethod.GET)
    public String returnCar(@PathVariable(value="car_id") long carId) {
        Optional<Car> maybeCar = carRepository.findById(carId);
        if(maybeCar.isPresent()) {
            Car car = maybeCar.get();
            car.setUser(null);
            carRepository.save(car);
        }
        return "redirect:/cars";
    }

    @RequestMapping(value = "/cars/{car_id}/rent_form", method=RequestMethod.GET)
    public String rentCarForm(Model model, @PathVariable(value="car_id") long carId) {
        Optional<Car> maybeCar = carRepository.findById(carId);
        if(maybeCar.isPresent()) {
            model.addAttribute("car_attr", maybeCar.get());
            model.addAttribute("users_attr", userRepository.findAll());

            List<String> colors = new ArrayList<>();
            colors.add("oRed");
            colors.add("oGreen");
            colors.add("oBlue");
            model.addAttribute("colors_attr", colors);
        }
        return "car_rent";
    }
}
