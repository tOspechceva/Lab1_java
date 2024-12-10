package tech.reliab.course.ospechceva.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.ospechceva.bank.entity.User;
import tech.reliab.course.ospechceva.bank.model.UserRequest;
import tech.reliab.course.ospechceva.bank.repository.UserRepository;
import tech.reliab.course.ospechceva.bank.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int MONTHLY_INCOME_BOUND = 10001;
    private static final double DIVIDER = 1000.0;
    private static final int FACTOR = 100;

    private final UserRepository userRepository;

    /**
     * Создание нового пользователя.
     *
     * @param userRequest содержит данные пользователя
     * @return Созданный пользователь.
     */
    public User createUser(UserRequest userRequest) {
        User user = new User(userRequest.getFullName(), userRequest.getBirthDate(), userRequest.getJob());
        user.setMonthlyIncome(generateMonthlyIncome());
        user.setCreditRating(generateCreditRating(user.getMonthlyIncome()));
        return userRepository.save(user);
    }

    /**
     * Генерация случайного месячного дохода пользователя.
     *
     * @return Случайный месячный доход.
     */
    private int generateMonthlyIncome() {
        return new Random().nextInt(MONTHLY_INCOME_BOUND);
    }

    /**
     * Генерация кредитного рейтинга пользователя,
     * основанного на его месячном доходе.
     *
     * @param monthlyIncome Месячный доход пользователя.
     * @return Кредитный рейтинг пользователя.
     */
    private int generateCreditRating(double monthlyIncome) {
        return (int) Math.ceil(monthlyIncome / DIVIDER) * FACTOR;
    }

    /**
     * Чтение пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь, если он найден
     * @throws NoSuchElementException Если пользователь не найден.
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User was not found"));
    }

    public User getUserDtoById(int id) {
        return getUserById(id);
    }

    /**
     * Чтение всех пользователей.
     *
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Обновление информации о пользователе по его идентификатору.
     *
     * @param id   Идентификатор пользователя.
     * @param name Новое имя пользователя.
     */
    public User updateUser(int id, String name) {
        User user = getUserById(id);
        user.setFullName(name);
        return userRepository.save(user);
    }

    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
