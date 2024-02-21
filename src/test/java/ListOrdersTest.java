import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Test;
import ru.yandex.praktikum.scooter.OrderObj;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
public class ListOrdersTest extends BaseTest{

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что список заказов возвращается не пустым")
    public void getListOrders() {

        OrderObj orderObj = new OrderObj();
        ArrayList<String> orders = orderObj.getListOrders().then().extract().path("orders");
        assertFalse(orders.isEmpty());

    }

}
