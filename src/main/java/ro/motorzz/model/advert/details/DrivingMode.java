package ro.motorzz.model.advert.details;

public enum DrivingMode {
    SHAFT_DRIVE(0), CHAIN_DRIVE(1), BELT_DRIVE(2);

    private final int id;

    DrivingMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
