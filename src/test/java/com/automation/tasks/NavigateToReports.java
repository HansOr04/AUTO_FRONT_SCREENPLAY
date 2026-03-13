package com.automation.tasks;

import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.annotations.Step;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class NavigateToReports implements Task {

    public static NavigateToReports fromSidebar() {
        return instrumented(NavigateToReports.class);
    }

    @Override
    @Step("{0} navigates to the reports section")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.REPORTS_MENU, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.REPORTS_MENU)
        );
    }
}
