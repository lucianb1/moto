package ro.motorzz.model.advert.details;

public enum Maker {
    AJP(0), AJS(1), APRILIA(2), BETA(3), BIMOTA(4), BMW(5), BUELL(6), CAGIVA(7), CCM(8), DUCATI(9), ENFIELD(10), GAS_GAS(11), GILERA(12),
    HARLEY_DAVIDSON(13), HESKETH(14), HONDA(15), HUSABERG(16), HUSQVARNA(17), HYOSUNG(18), KAWASAKI(19), KTM(20), KYMCO(21),
    LAVERDA(22), LEXMOTO(23), MORINI(24), MOTO_GUZZI(25), MOTO_HISPANIA(26), MV_AGUSTA(27), MZ(28), NORTON(29), PEUGEOT(30),
    PIAGGIO(31), QUANTYA(32), RIEJU(33), ROEHR(34), SACHS(35), SHERCO(36), SINNIS(37), SMC(38), SUZUKI(39), SYM(40), TRIUMPH(41),
    VERTEMATI(42), VESPA(43), VICTORY(44), WK_BIKES(45), YAMAHA(46);

    private final int id;

    Maker(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}