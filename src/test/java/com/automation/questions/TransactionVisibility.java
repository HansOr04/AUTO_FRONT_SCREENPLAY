package com.automation.questions;

import com.automation.ui.TransactionRow;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

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
        return TransactionRow.containing(descripcion)
                             .resolveFor(actor)
                             .isCurrentlyVisible();
    }
}
