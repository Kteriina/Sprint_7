import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.scooter.CourierObj;
import ru.yandex.praktikum.scooter.pojo.Courier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends BaseTest{
    String idCourier;
    CourierObj courierObj = new CourierObj();

    @Test
    @DisplayName("Авторизация не существующего пользователя")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginNonExistingCourier() {
        String login = RandomStringUtils.randomAlphanumeric(3,10);
        String password = RandomStringUtils.randomAlphanumeric(3,10);
        Courier courier = new Courier(login, password);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);

    }
    @Test
    @DisplayName("Авторизация пользователя без пароля")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void loginCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphanumeric(3,10);
        String password = "";
        Courier courier = new Courier(login, password);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }
    @Test
    @DisplayName("Авторизация пользователя без логина")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void loginCourierWithoutLogin() {
        String login = "";
        String password = RandomStringUtils.randomAlphanumeric(3,10);
        Courier courier = new Courier(login, password);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }
    @Test
    @DisplayName("Авторизация пользователя без тела запроса")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void loginCourierWithoutData() {
        String login = "";
        String password = "";
        Courier courier = new Courier(login, password);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    @Description("Успешный запрос должен возвращать правильный код ответа - 200 и значение id")
    public void loginCourierWithAllData() {
        String login = RandomStringUtils.randomAlphanumeric(3,10);
        String password = RandomStringUtils.randomAlphanumeric(3,10);
        String firstName = RandomStringUtils.randomAlphanumeric(3,10);
        Courier courier = new Courier(login, password, firstName);
        courierObj.createCourier(courier);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("id", notNullValue()).and().statusCode(200);
        idCourier = response.jsonPath().getString("id");
        System.out.println(idCourier);
    }
    @After
    public void deleteCourier() {
        if (idCourier != null) {
            Response deletion = courierObj.deleteCourier(idCourier);
            deletion.then().log().all().assertThat().statusCode(200).and().body("ok", Matchers.is(true));
        }


    }


}