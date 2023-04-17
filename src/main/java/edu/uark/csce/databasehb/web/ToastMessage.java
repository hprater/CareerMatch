package edu.uark.csce.databasehb.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToastMessage {
    private static final String baseClass = " alert d-flex align-items-center alert-dismissible fade show";
    private String cssClass = "alert-success" + baseClass;
    private String message = "Everything worked as expected";
    private String symbol = "fa-check-circle";

    public void setCssClass(String baseCssAlert, String symbolClass) {
        cssClass = baseCssAlert + baseClass;
        symbol = symbolClass;
    }
}
