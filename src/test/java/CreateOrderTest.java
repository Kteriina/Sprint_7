import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

import ru.yandex.praktikum.scooter.OrderObj;
import ru.yandex.praktikum.scooter.pojo.Order;
@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest{
    OrderObj orderObj = new OrderObj();
    Order order;
    int trackOrder;

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
        Response response = orderObj.createOrder(order);
        response.then().assertThat().statusCode(201).and().body("track",notNullValue());
        trackOrder = response.then().extract().path("track");

    }

    @After
    public void cancelOrder() {
        System.out.println(trackOrder);
        orderObj.cancelOrder(trackOrder);
    }

}