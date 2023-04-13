package edu.uark.csce.databasehb.web;

import lombok.Data;

@Data
public class ToastMessage {
    private static final String baseClass = "alert alert-dismissible fade show";
    private String cssClass = "alert alert-success alert-dismissible fade show";
    private String message = "Everything worked as expected";

    public void setCssClass(String baseCssAlert) {
        cssClass = baseCssAlert + baseClass;
    }
}
