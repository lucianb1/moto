package ro.motorzz.model.advert.details;

public enum Feature {
    ABS(0), BOX(1), CATALYTIC_CONVERTER(2), ELECTRIC_STARTER(3), KICKSTARTER(4), ROLL_OVER_BAR(5), WINDSHIELD(6);

    private final int id;

    Feature(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
