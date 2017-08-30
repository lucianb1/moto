package ro.motorzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.motorzz.core.exception.NotFoundException;
import ro.motorzz.model.advert.Advert;
import ro.motorzz.model.advert.details.*;
import ro.motorzz.repository.AdvertRepository;
import ro.motorzz.service.api.GeneratorService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private AdvertRepository advertRepository;

    @Override
    public void insertRandomDataInAdvertTable(int rows) {
        for (int i = 0; i < rows; i++) {
            Advert advert = initRandomAdvert();
            advertRepository.saveAdvert(advert);
        }
    }

    private Advert initRandomAdvert() {
        Advert advert = new Advert();
        advert.setCategory(randomElementFromCollection(Arrays.asList(Category.values())).getId());
        advert.setMaker(randomElementFromCollection(Arrays.asList(Maker.values())).getId());
        advert.setFirstRegister(generateRandomNumber(1950, 2010));
        advert.setPrice(generateRandomNumber(1000, 80000));
        advert.setPowerCp(generateRandomNumber(20, 133));
        advert.setPowerKW(generateRandomNumber(15, 98));
        advert.setKilometer(generateRandomNumber(0, 1_000_000));
        advert.setFuelType(randomElementFromCollection(Arrays.asList(FuelType.values())).getId());
        advert.setDrivingMode(randomElementFromCollection(Arrays.asList(DrivingMode.values())).getId());
        advert.setTransmission(randomElementFromCollection(Arrays.asList(Transmission.values())).getId());
        advert.setCubicCapacity(generateRandomNumber(500, 1800));
        advert.setFeature(randomElementFromCollection(Arrays.asList(Feature.values())).getId());
        advert.setTva(true);
        advert.setVendor(randomElementFromCollection(Arrays.asList(Vendor.values())).getId());
        return advert;

    }

    private int generateRandomNumber(int minValue, int maxValue) {
        Random rand = new Random();
        return rand.nextInt(maxValue - minValue) + minValue;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int randomCategoryID = randomElementFromCollection(Arrays.asList(Category.values())).getId();
            System.out.println(randomCategoryID);
        }
    }

    public static <T> T randomElementFromCollection(List<T> collection) {
        Collections.shuffle(collection);
        return collection.stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Empty collection"));
    }

}
