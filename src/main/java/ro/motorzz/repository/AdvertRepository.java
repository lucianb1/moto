package ro.motorzz.repository;

import org.springframework.stereotype.Repository;
import ro.motorzz.core.utils.sql.SQLQuery;
import ro.motorzz.core.utils.sql.SQLQueryBuilder;
import ro.motorzz.model.advert.Advert;

@Repository
public class AdvertRepository extends  BaseRepository{

    public void saveAdvert(Advert advert) {
        SQLQueryBuilder queryBuilder = new SQLQueryBuilder()
                .insertInto("adverts")
                .columns("category", "maker", "first_register", "price", "power_cp", "power_kW", "kilometer",
                        "fuel_type", "driving_mode", "transmission", "cubic_capacity", "colour", "feature", "tva", "vendor")
                .values()
                //.append("?", advert.getId())
                .append("?", advert.getCategory())
                .append("?", advert.getMaker())
                .append("?", advert.getFirstRegister())
                .append("?",advert.getPrice())
                .append("?",advert.getPowerCp())
                .append("?",advert.getPowerKW())
                .append("?", advert.getKilometer())
                .append("?",advert.getFuelType())
                .append("?", advert.getDrivingMode())
                .append("?",advert.getTransmission())
                .append("?",advert.getCubicCapacity())
                .append("?", advert.getColour())
                .append("?", advert.getFeature())
                .append("?",true)
                .append("?",advert.getVendor());
        SQLQuery query = queryBuilder.build();
        jdbcTemplate.update(query.getQuery(),query.getParams());
    }
}
