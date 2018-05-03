package by.tut.darrko.webapp.model;

class SectionFactory {
    static Section createSection(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE:
                return new SimpleSection(sectionType);
            case CONTACTS:
            case PERSONAL:
            case ACHIEVEMENT:
            case QUALIFICATION:
                return new ListedSection(sectionType);
            case EXPERIENCE:
            case EDUCATION:
                return new ListedDatedSection(sectionType);
        }
        return null;
    }
}
