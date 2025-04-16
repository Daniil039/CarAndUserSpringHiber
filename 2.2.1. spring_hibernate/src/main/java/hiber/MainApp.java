package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);


        User user1 = new User("John", "Doe", "john.doe@example.com");
        User user2 = new User("Jane", "Smith", "jane.smith@example.com");
        User user3 = new User("Peter", "Jones", "peter.jones@example.com");
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);

        Car car1 = new Car("Toyota", 2020);
        Car car2 = new Car("Honda", 2022);
        Car car3 = new Car("BMW", 2023);

        car1.setUser(user3);
        user3.setCar(car1);
        carService.add(car1);

        car2.setUser(user2);
        user2.setCar(car2);
        carService.add(car2);

        car3.setUser(user1);
        user1.setCar(car3);
        carService.add(car3);


        List<User> users = userService.getListUsers();
        for (User user : users) {
            System.out.println("User Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car Model = " + user.getCar().getModel());
                System.out.println("Car Series = " + user.getCar().getSeries());
            } else {
                System.out.println("User has no car.");
            }
            System.out.println();

        }

        context.close();
    }
}