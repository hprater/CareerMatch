package edu.uark.csce.databasehb.web.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewApplicationTable {
    private String studentName;
    private String companyName;
    private Long salary;
    private String majorDescription;
}
