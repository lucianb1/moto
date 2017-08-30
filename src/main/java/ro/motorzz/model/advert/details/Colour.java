package ro.motorzz.model.advert.details;

public enum Colour {

    BEIGE(0), BLACK(1), BLUE(2), BROWN(3), GOLD(4), GREEN(5), GREY(6), ORANGE(7), PURPLE(8), RED(9), SILVER(10), WHITE(11), YELLOW(12), METALLIC(13);

    private final int id;

    Colour(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
