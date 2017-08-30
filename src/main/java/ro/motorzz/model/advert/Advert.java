package ro.motorzz.model.advert;

public class Advert {

    private int id;
    private int category;
    private int maker;
    private int firstRegister;
    private int price;
    private int powerCp;
    private int powerKW;
    private int kilometer;
    private int fuelType;
    private int drivingMode;
    private int transmission;
    private int cubicCapacity;
    private int colour;
    private int feature;
    private boolean tva;
    private int vendor;

    public Advert() {
    }

    public Advert(int id, int category, int maker, int firstRegister, int price, int powerCp, int powerKW, int kilometer, int fuelType, int drivingMode, int transmission, int cubicCapacity, int colour, int feature, boolean tva, int vendor) {
        this.id = id;
        this.category = category;
        this.maker = maker;
        this.firstRegister = firstRegister;
        this.price = price;
        this.powerCp = powerCp;
        this.powerKW = powerKW;
        this.kilometer = kilometer;
        this.fuelType = fuelType;
        this.drivingMode = drivingMode;
        this.transmission = transmission;
        this.cubicCapacity = cubicCapacity;
        this.colour = colour;
        this.feature = feature;
        this.tva = tva;
        this.vendor = vendor;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getMaker() {
        return maker;
    }

    public int getFirstRegister() {
        return firstRegister;
    }

    public int getPrice() {
        return price;
    }

    public int getPowerCp() {
        return powerCp;
    }

    public int getPowerKW() {
        return powerKW;
    }

    public int getKilometer() {
        return kilometer;
    }

    public int getFuelType() {
        return fuelType;
    }

    public int getDrivingMode() {
        return drivingMode;
    }

    public int getTransmission() {
        return transmission;
    }

    public int getCubicCapacity() {
        return cubicCapacity;
    }

    public int getColour() {
        return colour;
    }

    public int getFeature() {
        return feature;
    }

    public boolean isTva() {
        return tva;
    }

    public int getVendor() {
        return vendor;
    }

    public Advert setId(int id) {
        this.id = id;
        return this;
    }

    public Advert setCategory(int category) {
        this.category = category;
        return this;
    }

    public Advert setMaker(int maker) {
        this.maker = maker;
        return this;
    }

    public Advert setFirstRegister(int firstRegister) {
        this.firstRegister = firstRegister;
        return this;
    }

    public Advert setPrice(int price) {
        this.price = price;
        return this;
    }

    public Advert setPowerCp(int powerCp) {
        this.powerCp = powerCp;
        return this;
    }

    public Advert setPowerKW(int powerKW) {
        this.powerKW = powerKW;
        return this;
    }

    public Advert setKilometer(int kilometer) {
        this.kilometer = kilometer;
        return this;
    }

    public Advert setFuelType(int fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public Advert setDrivingMode(int drivingMode) {
        this.drivingMode = drivingMode;
        return this;
    }

    public Advert setTransmission(int transmission) {
        this.transmission = transmission;
        return this;
    }

    public Advert setCubicCapacity(int cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
        return this;
    }

    public Advert setColour(int colour) {
        this.colour = colour;
        return this;
    }

    public Advert setFeature(int feature) {
        this.feature = feature;
        return this;
    }

    public Advert setTva(boolean tva) {
        this.tva = tva;
        return this;
    }

    public Advert setVendor(int vendor) {
        this.vendor = vendor;
        return this;
    }
}
