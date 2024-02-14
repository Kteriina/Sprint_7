import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.scooter.CourierObj;
import ru.yandex.praktikum.scooter.POJO.Courier;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseTest {
    String idCourier;

    @Test
    @DisplayName("Создание учетной записи курьера со всеми полями")
    @Description("Успешный запрос должен возвращать статус код - 201 и ok: true")
    public void createCourierWithAllData() {
        CourierObj courierObj = new CourierObj();
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);

        idCourier = courierObj.getCourierId(courier);

        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(201);

    }

    @Test
    @DisplayName("Создание 2х одинаковых учетных записей курьеров")
    @Description("Нельзя создать одинаковых курьеров")
    public void tryCreateEqualsCouriers() {

        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
        String login = "";
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        Response response = courierObj.createCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @Test
    @DisplayName("Сохдание учетной записи курьера с уже существующим логином")
    @Description("Если создать курьера с уже существующим логином возвращается ошибка")
    public void tryCreateCouriersWithEqualLogins() {
        CourierObj courierObj = new CourierObj();
        String login = RandomStringUtils.randomAlphanumeric(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(3, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        courierObj.createCourier(courier);

        idCourier = courierObj.getCourierId(courier);

        Courier courier_1 = new Courier();
        courier_1.setLogin(login);
        courier_1.setPassword(RandomStringUtils.randomAlphanumeric(3, 10));
        courier_1.setFirstName(RandomStringUtils.randomAlphanumeric(3, 10));

        Response response = courierObj.createCourier(courier_1);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);


    }

    @After
    public void deleteCourierData() {
        if (idCourier != null) {
            Response deletion = RestAssured
                    .given()
                    .when()
                    .delete("/api/v1/courier/{id}", idCourier);

            deletion.then().assertThat().statusCode(200).and().body("ok", Matchers.is(true));
        }


    }
}
