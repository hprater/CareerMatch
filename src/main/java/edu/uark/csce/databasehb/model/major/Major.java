package edu.uark.csce.databasehb.model.major;

public class Major {
    private int majorId;
    private String major;
    private String majorDescription;

    public Major(int majorId, String major, String majorDescription) {
        this.majorId = majorId;
        this.major = major;
        this.majorDescription = majorDescription;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajorDescription() {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        this.majorDescription = majorDescription;
    }

    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", major='" + major + '\'' +
                ", majorDescription='" + majorDescription + '\'' +
                '}';
    }
}
