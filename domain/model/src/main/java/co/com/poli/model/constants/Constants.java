package co.com.poli.model.constants;

public enum Constants {
    CREATED("CREATED"),
    FINISHED("FINISHED");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
