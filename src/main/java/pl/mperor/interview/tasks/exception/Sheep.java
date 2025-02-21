package pl.mperor.interview.tasks.exception;

class Sheep {

    private boolean alive = true;

    boolean isAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * No matter what the sheep says, the wolf will eat it anyway.
     * Unless the sheep offers something exceptionally good :)
     */
    boolean doesWantToBeEaten() {
        return false;
    }
}