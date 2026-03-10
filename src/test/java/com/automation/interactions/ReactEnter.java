package com.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class ReactEnter implements Interaction {

    private final Target target;
    private final String value;

    private ReactEnter(String value, Target target) {
        this.value = value;
        this.target = target;
    }

    public static ReactEnter theValue(String value) {
        return new ReactEnter(value, null);
    }

    public ReactEnter into(Target target) {
        return new ReactEnter(this.value, target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement element = target.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
            "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            element, value
        );
    }
}
