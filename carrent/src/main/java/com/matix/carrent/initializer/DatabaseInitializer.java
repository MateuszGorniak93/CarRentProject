package com.matix.carrent.initializer;

import com.matix.carrent.model.Car;
import com.matix.carrent.model.User;
import com.matix.carrent.repositories.CarRepository;
import com.matix.carrent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        fillDatabase();
    }

    private void fillDatabase() {
        
        Car car1 = new Car("Toyota Yaris", "2010", "Green");
        Car car2 = new Car("Toyota Corolla", "2020", "Red");
        Car car3 = new Car("Tesla Model S", "2015", "Silver");
        Car car4 = new Car("Fiat 500", "2021", "Yellow");
        Car car5 = new Car("Chevrolet Corvette C9", "2021", "Black");

        
        User user1 = new User("Mariusz", "Koza", "mariusz.koza@gmail.com");
        User user2 = new User("Anna", "Zakrzewska", "anna.zakrzewska@gmail.com");
        User user3 = new User("Zbigniew", "Tyrka", "zbigniew.tyrka@gmail.com");
        User user4 = new User("Zenon", "Madera", "zenon.madera@gmail.com");
        User user5 = new User("Patryk", "Opatka", "patryk.opatka@gmail.com");

        
        Set<Car> user1RentedCars = new HashSet<>();
        user1RentedCars.add(car1);
        user1.setRentedCars(user1RentedCars);
        
        car1.setUser(user1);

        
        Set<Car> user2RentedCars = new HashSet<>();
        user2RentedCars.add(car2);
        user2RentedCars.add(car3);
        user2.setRentedCars(user2RentedCars);
        
        car2.setUser(user2);
        car3.setUser(user2);

        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);
        carRepository.save(car5);

    }
}
