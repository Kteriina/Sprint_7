import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.scooter.CourierObj;
import ru.yandex.praktikum.scooter.pojo.Courier;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {
    String idCourier;
    CourierObj courierObj = new CourierObj();

    @Test
    @DisplayName("Создание учетной записи курьера со всеми полями")
    @Description("Успешный запрос должен возвращать статус код - 201 и ok: true")
    public void createCourierWithAllData() {
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);

        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(201);

        idCourier = courierObj.getCourierId(courier);

    }

    @Test
    @DisplayName("Создание 2х одинаковых учетных записей курьеров")
    @Description("Нельзя создать одинаковых курьеров")
    public void tryCreateEqualsCouriers() {
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        courierObj.createCourier(courier);

        idCourier = courierObj.getCourierId(courier);

        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);

    }

    @Test
    @DisplayName("Создание учетной записи курьера без пароля")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void createCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = "";
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @Test
    @DisplayName("Создание учетной записи курьера без логина")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void createCourierWithoutLogin() {
        String login = "";
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @Test
    @DisplayName("Создание учетной записи курьера с уже существующим логином")
    @Description("Если создать курьера с уже существующим логином возвращается ошибка")
    public void tryCreateCouriersWithEqualLogins() {
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        courierObj.createCourier(courier);

        idCourier = courierObj.getCourierId(courier);
        System.out.println(idCourier);

        courier.setLogin(login);
        courier.setPassword(RandomStringUtils.randomAlphanumeric(3, 10));
        courier.setFirstName(RandomStringUtils.randomAlphanumeric(3, 10));

        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);


    }

    @After
    public void deleteCourier() {
        if (idCourier != null) {
            Response deletion = courierObj.deleteCourier(idCourier);
            deletion.then().log().all().assertThat().statusCode(200).and().body("ok", Matchers.is(true));
        }


    }
}
