package by.tut.darrko.webapp.model;

public enum SectionType {

    OBJECTIVE("Позиция:"),
    PERSONAL("Личные качества:"),
    ACHIEVEMENT("Достжения:"),
    QUALIFICATION("Квалификация:"),
    EXPERIENCE("Опыт работы:"),
    EDUCATION("Образование:");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
