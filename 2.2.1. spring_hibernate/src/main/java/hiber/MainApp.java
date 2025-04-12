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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);

        // Создаем пользователей с машинами
        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        Car car1 = new Car("BMW", 5);
        user1.setCar(car1);
        userService.add(user1);


        // Проверяем поиск пользователя по модели и серии машины (после добавления пользователей!)
        User foundUser = carService.carList("BMW", 5);

        if (foundUser != null) {
            System.out.println("Найден пользователь: " + foundUser.getFirstName() + " " + foundUser.getLastName());
            System.out.println("Его машина: " + foundUser.getCar().getModel() + " " + foundUser.getCar().getSeries());
        } else {
            System.out.println("Пользователь с машиной BMW 5 не найден.");
        }

        context.close();
    }
}