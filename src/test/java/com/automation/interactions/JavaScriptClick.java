package com.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class JavaScriptClick implements Interaction {

    private final Target target;

    private JavaScriptClick(Target target) {
        this.target = target;
    }

    public static JavaScriptClick on(Target target) {
        return new JavaScriptClick(target);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement element = target.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }
}
