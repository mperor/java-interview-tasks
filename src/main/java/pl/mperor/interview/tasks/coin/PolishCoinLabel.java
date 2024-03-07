package pl.mperor.interview.tasks.coin;

public enum PolishCoinLabel {
    GROSZ("gr"),
    ZLOTY("zł");

    private final String label;

    PolishCoinLabel(String label) {
        this.label = label;
    }
}