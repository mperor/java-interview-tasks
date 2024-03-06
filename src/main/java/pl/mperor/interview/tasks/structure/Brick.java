package pl.mperor.interview.tasks.structure;

public class Brick implements Block {
    @Override
    public String getColor() {
        return "red";
    }

    @Override
    public String getMaterial() {
        return "clay";
    }
}
