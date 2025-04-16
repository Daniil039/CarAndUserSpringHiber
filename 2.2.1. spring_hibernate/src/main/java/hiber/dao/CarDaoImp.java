package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarDaoImp implements CarDao {

    private SessionFactory sessionFactory;

    @Autowired
    public CarDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        String hql = "FROM User u WHERE u.car.model = :model and u.car.series = :series";
        jakarta.persistence.TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.setMaxResults(1).getSingleResult();
    }
}