package edu.uark.csce.databasehb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToastMessage {
    private static final String baseClass = " alert d-flex align-items-center alert-dismissible fade show";
    private String cssClass = "alert-success" + baseClass;
    private String message = "Everything worked as expected";
    private String symbol = "fa-check-circle";

    public ToastMessage(String cssClass, String message, String symbol) {
        this.cssClass = cssClass + baseClass;
        this.message = message;
        this.symbol = symbol;
    }

    public void setCssClass(String baseCssAlert) {
        cssClass = baseCssAlert + baseClass;
    }

    public void setCssClass(String baseCssAlert, String symbolClass) {
        cssClass = baseCssAlert + baseClass;
        symbol = symbolClass;
    }
}
