package ro.motorzz.model.advert.details;

public enum Transmission {
    MANUAL_GEARBOX(0), SEMI_AUTOMATIC(1), AUTOMATIC_TRANSMISSION(2);

    private final int id;

    Transmission(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
