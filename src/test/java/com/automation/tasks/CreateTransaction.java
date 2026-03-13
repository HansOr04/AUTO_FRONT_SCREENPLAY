package com.automation.tasks;

import com.automation.interactions.ReactEnter;
import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.annotations.Step;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CreateTransaction implements Task {

    private final String descripcion;
    private final String monto;

    public CreateTransaction(String descripcion, String monto) {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public static CreateTransaction with(String descripcion, String monto) {
        return instrumented(CreateTransaction.class, descripcion, monto);
    }

    @Override
    @Step("{0} creates a transaction with description '#descripcion' and amount '#monto'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.NEW_TRANSACTION_BUTTON, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.NEW_TRANSACTION_BUTTON),
            WaitUntil.the(BudgetManagementUi.DESCRIPTION_INPUT, isVisible())
                     .forNoMoreThan(30).seconds(),
            Enter.theValue(descripcion).into(BudgetManagementUi.DESCRIPTION_INPUT),
            ReactEnter.theValue(monto).into(BudgetManagementUi.AMOUNT_INPUT),
            WaitUntil.the(BudgetManagementUi.CATEGORY_DROPDOWN, isVisible())
                     .forNoMoreThan(10).seconds(),
            Click.on(BudgetManagementUi.CATEGORY_DROPDOWN),
            WaitUntil.the(BudgetManagementUi.FIRST_CATEGORY_OPTION, isVisible())
                     .forNoMoreThan(10).seconds(),
            Click.on(BudgetManagementUi.FIRST_CATEGORY_OPTION),
            WaitUntil.the(BudgetManagementUi.SAVE_BUTTON, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.SAVE_BUTTON)
        );
    }
}
