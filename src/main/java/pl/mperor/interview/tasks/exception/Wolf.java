package pl.mperor.interview.tasks.exception;

class Wolf {

    private boolean hunger = true;

    boolean isHunger() {
        return hunger;
    }

    void eat(Sheep sheep) {
        sheep.setAlive(false);
        hunger = false;
        System.out.println("The wolf devoured the sheep 🐑!");
    }

    void eat(SomethingExceptionallyGood something) {
        hunger = false;
        System.out.printf("The wolf has eaten %s%n", something.getMessage());
    }
}
