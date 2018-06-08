package by.tut.darrko.webapp.model;

public enum SectionType {
    PERSONAL("Personal:"),
    OBJECTIVE("Objective:"),
    ACHIEVEMENT("Achievement:"),
    QUALIFICATIONS("Qualifications:"),
    EXPERIENCE("Experience:"),
    EDUCATION("Education:");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

