package ru.netology.dataClass ;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String cityGenerator() {
        String[] cities = new String[]{
                "Уфа", "Майкоп", "Черкесск", "Петрозаводск",
                "Ижевск", "Смоленск", "Москва", "Владимир", "Брянск",
                "Ставрополь", "Архангельск", "Омск", "Самара", "Рязань", "Тверь", "Томск", "Тула",
                "Ярославль",
        };
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String name) {
        Faker faker = new Faker(new Locale(name));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String phoneGenerator(String phone) {
        Faker faker = new Faker(new Locale(phone));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static userInfo generateUser(String user) {
            return new userInfo(cityGenerator(), generateName(user), phoneGenerator(user));
        }
    }

    @Value
    public static class userInfo {
        String city;
        String name;
        String phone;
    }
}
