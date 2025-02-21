package pl.mperor.interview.tasks.exception;

class Wolf {

    private boolean hungry = true;

    boolean isHungry() {
        return hungry;
    }

    void eat(Sheep sheep) {
        sheep.setAlive(false);
        hungry = false;
        System.out.println("The wolf 🐺 devoured the sheep 🐑!");
    }

    void eat(SomethingExceptionallyGood something) {
        hungry = false;
        System.out.printf("The wolf 🐺 has eaten %s%n", something.getMessage());
    }
}
