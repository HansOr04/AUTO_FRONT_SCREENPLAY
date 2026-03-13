package com.automation.tasks;

import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Logout implements Task {

    public static Logout fromUserMenu() {
        return instrumented(Logout.class);
    }

    @Override
    @Step("{0} logs out from the user menu")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.USER_AVATAR, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.USER_AVATAR),
            WaitUntil.the(BudgetManagementUi.LOGOUT_MENU_ITEM, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.LOGOUT_MENU_ITEM)
        );
    }
}
