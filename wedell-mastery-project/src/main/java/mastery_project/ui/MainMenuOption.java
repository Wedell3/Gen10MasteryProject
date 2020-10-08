package mastery_project.ui;

public enum MainMenuOption {
    EXIT(0, "EXIT"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations By Host"),
    MAKE_RESERVATION(2, "Make Reservation"),
    EDIT_RESERVATION(3,"Edit Reservation"),
    DELETE_RESERVATION(4,"Delete Reservation");


    private int value;
    private String message;
    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption getMainMenuOptionFromValue (int value) {
        for(MainMenuOption option : MainMenuOption.values()) {
            if(option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }
}
