package by.tut.darrko.webapp.model;

public enum ContactType {
    ADDRESS("Адрес:"),
    PHONE("Телефон:"),
    EMAIL("E-mail:"),
    SKYPE("Skype"),
    LINKEDIN("Квалификация"),
    GITHUB("Опыт работы"),
    EDUCATION("Обраование");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
