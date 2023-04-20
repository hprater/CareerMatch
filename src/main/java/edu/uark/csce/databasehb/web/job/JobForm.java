package edu.uark.csce.databasehb.web.job;

import lombok.Data;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isAlphaSpace;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
public class JobForm {
    private Integer jobId;
    private String companyName;
    private String jobTitle;
    private Integer salary;
    private Integer major;

    //create method check if inputs are valid.boolean
    public Boolean isValid() {
        if(isBlank(this.jobTitle) || this.jobTitle.contains(";") || this.jobTitle.length() > 55) return false;

        if(isBlank(this.companyName) || this.companyName.contains(";") || this.companyName.length() > 55) return false;

        if(this.salary.toString().length() > 7) return false;

        return true;
    }
}
