package com.automation.tasks;

import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class NavigateToTransactions implements Task {

    public static NavigateToTransactions fromSidebar() {
        return new NavigateToTransactions();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.TRANSACTIONS_MENU, isVisible()).forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.TRANSACTIONS_MENU)
        );
    }
}
