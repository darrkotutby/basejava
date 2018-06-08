package by.tut.darrko.webapp.model;

public enum ContactType {
    PHONE("Phone."),
    MOBILE("Mobile phone"),
    HOME_PHONE("Home phone"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    },
    MAIL("E-mail") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("GitHub") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STATCKOVERFLOW("Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Site") {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }
}
