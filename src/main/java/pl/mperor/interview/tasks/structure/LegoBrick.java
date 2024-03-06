package pl.mperor.interview.tasks.structure;

public class LegoBrick implements Block {

    private final String color;

    private LegoBrick(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getMaterial() {
        return "plastic";
    }

    public static LegoBrick ofColor(String color) {
        return new LegoBrick(color);
    }

}
