package com.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransactionVisibility implements Question<Boolean> {

    private final String descripcion;

    private TransactionVisibility(String descripcion) {
        this.descripcion = descripcion;
    }

    public static TransactionVisibility of(String descripcion) {
        return new TransactionVisibility(descripcion);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(normalize-space(.), '" + descripcion + "') and not(self::html) and not(self::body)]")
                ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
