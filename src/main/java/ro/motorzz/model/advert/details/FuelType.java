package ro.motorzz.model.advert.details;

public enum FuelType {

    PETROL(0), DIESEL(1), ELECTRIC(2), OTHER(3);

    private final int id;

    FuelType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
