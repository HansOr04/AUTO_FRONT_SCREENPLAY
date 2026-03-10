package com.automation.ui;

import net.serenitybdd.screenplay.targets.Target;

public class BudgetManagementUi {

    public static final Target DISPLAY_NAME_INPUT =
        Target.the("campo nombre").locatedBy("//*[@id='displayName']");

    public static final Target EMAIL_INPUT =
        Target.the("campo email").locatedBy("//*[@id='email']");

    public static final Target PASSWORD_INPUT =
        Target.the("campo password").locatedBy("//*[@id='password']");

    public static final Target CONFIRM_PASSWORD_INPUT =
        Target.the("campo confirmar contraseña").locatedBy("//*[@id='confirmPassword']");

    public static final Target REGISTER_BUTTON =
        Target.the("botón crear cuenta")
              .locatedBy("//button[@type='submit' and contains(.,'Crear')]");

    public static final Target LOGIN_BUTTON =
        Target.the("botón iniciar sesión")
              .locatedBy("//button[@type='submit' and contains(.,'Iniciar')]");

    public static final Target TRANSACTIONS_MENU =
        Target.the("menú transacciones")
              .locatedBy("//a[@href='/transactions']");

    public static final Target REPORTS_MENU =
        Target.the("menú reportes")
              .locatedBy("//a[@href='/dashboard']");

    public static final Target NEW_TRANSACTION_BUTTON =
        Target.the("botón nueva transacción")
              .locatedBy("//button[.//span[contains(text(),'Nueva Transacción')]]");

    public static final Target DESCRIPTION_INPUT =
        Target.the("campo descripción").locatedBy("//*[@name='description']");

    public static final Target AMOUNT_INPUT =
        Target.the("campo monto").locatedBy("//*[@name='amount']");

    public static final Target CATEGORY_DROPDOWN =
        Target.the("desplegable categoría")
              .locatedBy("//button[@role='combobox' and @data-placeholder='']");

    public static final Target FIRST_CATEGORY_OPTION =
        Target.the("primera opción de categoría")
              .locatedBy("(//div[@role='option'])[1]");

    public static final Target SAVE_BUTTON =
        Target.the("botón crear transacción")
              .locatedBy("//button[@type='submit' and contains(.,'Crear Transacción')]");

    public static final Target USER_AVATAR =
        Target.the("avatar usuario")
              .locatedBy("//span[@data-slot='avatar-fallback']");

    public static final Target LOGOUT_MENU_ITEM =
        Target.the("item cerrar sesión")
              .locatedBy("//div[@role='menuitem'][.//span[contains(text(),'Cerrar sesión')]]");
}
