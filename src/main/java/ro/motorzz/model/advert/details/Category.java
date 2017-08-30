package ro.motorzz.model.advert.details;

public enum Category {
    ADVENTURE(0), CLASSIC(1), CRUISER(2), DUAL_SPORT(3), OFF_ROAD(4), SCOOTER(5), SPORT(6), SPORT_TOURING(7),
    STANDARD(8), SUPERMOTO(9), TOURING(10);

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
