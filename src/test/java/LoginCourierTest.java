import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.praktikum.scooter.CourierObj;
import ru.yandex.praktikum.scooter.POJO.Courier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends BaseTest{
    int idCourier;

    @Test
    @DisplayName("Авторизация не существующего пользователя")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginNonExistingCourier() {
        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
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
        CourierObj courierObj = new CourierObj();
        String login = RandomStringUtils.randomAlphanumeric(3,10);
        String password = RandomStringUtils.randomAlphanumeric(3,10);
        String firstName = RandomStringUtils.randomAlphanumeric(3,10);
        Courier courier = new Courier(login, password, firstName);
        courierObj.createCourier(courier);
        Response response = courierObj.loginCourier(courier);
        response.then().assertThat().body("id", notNullValue()).and().statusCode(200);
        idCourier = response.jsonPath().getInt("id");
        System.out.println(idCourier);
    }

}