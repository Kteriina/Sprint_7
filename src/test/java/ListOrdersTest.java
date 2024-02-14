import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrdersTest extends BaseTest{

    @Test
    @DisplayName("Получение списка заказа")
    @Description("Проверкаб что список заказов возвращается не пустым")
    public void getListOrders() {

        given()
                .get("/api/v1/orders")
                .then().assertThat().extract().path("orders", String.valueOf(notNullValue()));

    }
}
