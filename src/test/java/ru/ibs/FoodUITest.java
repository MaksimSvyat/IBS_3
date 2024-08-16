package ru.ibs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(TestListener.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FoodUITest extends BaseTest {

    @Owner("Maks jivern")
    @Description("Проверка функционала добавления товаров и валидация отображения товаров в списке.")
    @ParameterizedTest
    @MethodSource("getParameters")
    public void checkAddFood(String name, String type, Boolean isExotic) throws InterruptedException {
        FoodPage foodPage = new FoodPage();
        foodPage.clickBtnAdd()
                .fillFieldForm(name, type, isExotic)
                .assertFormFields(name, type, isExotic);
    }

    public Stream<Arguments> getParameters() {
        Config config = ConfigFactory.load("app.conf");
        List<? extends ConfigObject> params = config.getObjectList("testData");
        return params.stream().map(param -> Arguments.of(
                param.get("name").unwrapped(),
                param.get("type").unwrapped(),
                param.get("isExotic").unwrapped()
        ));
    }
}
