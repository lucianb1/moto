package ro.motorzz.model.advert.details;

public enum Vendor {
    PRIVATE_SELLER(0),DEALER(1),COMPANY_VEHICLES(2);

    private final int id;

    Vendor(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
