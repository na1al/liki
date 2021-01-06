package repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicinePriceRepository extends AbstractRepository {

    public void cache() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into medicine_price (medicine_id, city_id, quantity, price) " +
                "    (select m.id                                                                  as medicine_id, " +
                "            p.city_id                                                             as city_id, " +
                "            MAX((CASE WHEN pp.quantity > 0 THEN 1 ELSE 0 END) * i.is_active::int) AS quantity, " +
                "            GREATEST(0, MIN(NULLIF((CASE WHEN pp.quantity > 0 THEN 1 ELSE 0 END) * i.is_active::int * pp.price, 0))) AS price" +
                "     from medicine m " +
                "              inner join partner_medicine pm on m.id = pm.medicine_id " +
                "              inner join integration i on pm.integration_id = i.id " +
                "              inner join partner_price pp on pm.external_medicine_id = pp.external_medicine_id and pm.integration_id = pp.integration_id " +
                "              inner join pharmacy p on p.external_pharmacy_id = pp.external_pharmacy_id and " +
                "                            p.integration_id = pp.integration_id and p.city_id is not null " +
                "     group by m.id, city_id)" +
                "   on conflict on constraint medicine_price_pkey do update " +
                "set price = excluded.price, quantity = excluded.quantity");
        ps.execute();
    }

}
