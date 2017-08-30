package ro.motorzz.test.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import ro.motorzz.service.api.GeneratorService;
import ro.motorzz.test.config.BaseTestClass;

public class GenerateAdvert extends BaseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateAdvert.class);

    @Autowired
    private GeneratorService generatorService;

    @Test
    public void insertRandomAdvert(){
        long millis = System.currentTimeMillis();
        generatorService.insertRandomDataInAdvertTable(10000);
        LOGGER.info("DURATION:     " + (System.currentTimeMillis() - millis));
    }
}
