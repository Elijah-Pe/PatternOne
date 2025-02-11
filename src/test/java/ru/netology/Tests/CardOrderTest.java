package ru.netology.Tests ;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.dataClass.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    @BeforeEach
    void setup() {open("http://localhost:9999"); }

    @Test
    @DisplayName("Should success")
    void shouldSuccess() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var daysForFirstMeeting = 5;
        var firstMeetingDay = DataGenerator.generateDate(daysForFirstMeeting);
        var daysForSecondMeeting = 10;
        var secondMeeting = DataGenerator.generateDate(daysForSecondMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDay);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDay))
                .shouldBe(Condition.visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeeting);
        $(Selectors.byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"))
                .shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] button").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeeting))
                .shouldBe(Condition.visible);
    }
}
