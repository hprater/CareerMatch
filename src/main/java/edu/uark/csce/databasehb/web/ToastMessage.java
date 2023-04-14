package edu.uark.csce.databasehb.web;

import lombok.Data;

import java.util.Objects;

@Data
public class ToastMessage {
    private static final String baseClass = " d-flex align-items-center alert-dismissible fade show";
    private String cssClass = "alert alert-success d-flex align-items-center alert-dismissible fade show";
    private String message = "Everything worked as expected";
    private String symbol = "#check-circle-fill";

    public void setCssClass(String baseCssAlert) {
        cssClass = baseCssAlert + baseClass;
        symbol = "#exclamation-triangle-fill";
    }
}
