import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import ru.yandex.praktikum.scooter.POJO.Order;
@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest{
    Order order;
    int idOrder;

    public CreateOrderTest(Order order){
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new Order("Владимир", "Ворошилов", "Самара", "Китай Город", "+79453783594", 1, "2024-02-11", "позвоните заранее", List.of("BLACK", "GREY"))},
                {new Order("Владимир", "Ворошилов", "Самара", "Китай Город", "+79453783594", 2, "2024-02-12", "позвоните заранее", List.of("BLACK"))},
                {new Order("Владимир", "Ворошилов", "Самара", "Китай Город", "+79453783594", 3, "2024-02-13", "позвоните заранее", List.of("GREY"))},
                {new Order("Владимир", "Ворошилов", "Самара", "Китай Город", "+79453783594", 4, "2024-02-14", "позвоните заранее", List.of())}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа со всевозможным набором значений параметра color")
    public void createOrder() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .post("/api/v1/orders");
        response.then().assertThat().statusCode(201)
                .and()
                .body("track",notNullValue());
        idOrder = response.then().extract().path("track");


    }
    @After
    public void cancelOrder() {
        System.out.println(idOrder);
        given()
                .header("Content-type", "application/json")
                .body("{\"track\":" + idOrder + "}")
                .when()
                .put("/api/v1/orders/cancel");


    }

}