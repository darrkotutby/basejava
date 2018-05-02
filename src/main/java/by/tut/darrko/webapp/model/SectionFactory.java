package by.tut.darrko.webapp.model;

class SectionFactory {
    static Section createSection(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE:
                return new SimpleSection(sectionType);
            case PERSONAL:
                return new ListedSection(sectionType);
            case ACHIEVEMENT:
                return new ListedSection(sectionType);
            case QUALIFICATION:
                return new ListedSection(sectionType);
            case EXPERIENCE:
                return new ListedDatedSection(sectionType);
            case EDUCATION:
                return new ListedDatedSection(sectionType);
        }
        return null;
    }
}
