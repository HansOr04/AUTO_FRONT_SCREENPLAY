package com.automation.ui;

import net.serenitybdd.screenplay.targets.Target;

public class TransactionRow {

    public static Target containing(String description) {
        return Target.the("transacción con descripción '" + description + "'")
                     .locatedBy("//*[contains(normalize-space(.), '{0}') "
                              + "and not(self::html) and not(self::body)]")
                     .of(description);
    }
}
