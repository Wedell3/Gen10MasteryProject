package mastery_project.ui;

public enum ManageAccountsOption {
    EXIT(0, "Exit"),
    VIEW(1, "View Accounts"),
    CREATE(2, "Add Account"),
    UPDATE(3, "Update Account"),
    DELETE(4, "Delete Account");

    private int value;
    private String message;

    ManageAccountsOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static ManageAccountsOption getOptionFromValue (int value) {
        for(ManageAccountsOption o : ManageAccountsOption.values()) {
            if (o.getValue() == value) {
                return o;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
