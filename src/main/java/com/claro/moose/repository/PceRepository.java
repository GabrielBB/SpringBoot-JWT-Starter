package com.claro.moose.repository;

import com.claro.moose.models.CatalogItem;
import com.claro.moose.models.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alenkart Rodriguez on 12/1/2017.
 */
@Repository
public class PceRepository {

    @Autowired
    @Qualifier("mooseJdbcTemplate")
    private JdbcTemplate mooseJdbcTemplate;

    @Autowired
    @Qualifier("omsPceJdbcTemplate")
    private JdbcTemplate omsPceJdbcTemplate;

    public List<Long> getCatalogVersion() {

        String sql = "SELECT pce.id FROM PCEVersion pce";

        return mooseJdbcTemplate.queryForList(sql, Long.class);
    }

    public long getCatalogVersion(long version) {

        String sql = "SELECT pce.id FROM PCEVersion pce where pce.id = ?";

        Object[] params = { version };

        return mooseJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public long getCurrentCatalogVersion() {

        String sql = "SELECT max(pce.id) FROM PCEVersion pce";

        return mooseJdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<CatalogItem> getPceComponents(long version) {

        String sql = "select  \n" +
                "  c.CID child_id, \n" +
                "  nvl(( select co.service_type from pce1pcown.tbcomponent co where co.pcversion_id = c.pcversion_id and co.cid = c.cid ),'Sin Service Type') serviceType, \n" +
                "  nvl((select n.name_text from pce1pcown.tbname n where n.pcversion_id=c.pcversion_id and n.cid = c.cid and n.language = 'ES'), c.caption) name_text, \n" +
                "  c.item_type, \n" +
                "  c.caption \n" +
                "from pce1pcown.tbcatalog_item c where 1=1 \n" +
                "  --and c.pcversion_id = ?  \n" +
                "  and c.item_type = 'CO' \n" +
                "\n" +
                "UNION ALL \n" +
                "\n" +
                "select \n" +
                "  c.CID child_id, \n" +
                "  'ADSL' service_type,  \n" +
                "  nvl((select n.name_text from pce1pcown.tbname n where 1=1 and n.pcversion_id = c.pcversion_id and n.cid = c.cid and n.language = 'ES'), c.caption) name_text, \n" +
                "  c.item_type, \n" +
                "  c.caption \n" +
                "FROM  pce1pcown.tbcatalog_item c \n" +
                "where 1=1 \n" +
                "  --and c.pcversion_id = ? \n" +
                "  and c.item_type = 'OF'\n";


        return omsPceJdbcTemplate.query(sql,(resultSet, i) -> {

            CatalogItem catalogItem = new CatalogItem();

            catalogItem.setChildId(resultSet.getLong("CHILD_ID"));
            catalogItem.setServiceType(resultSet.getString("SERVICETYPE"));
            catalogItem.setServiceType(resultSet.getString("NAME_TEXT"));
            catalogItem.setServiceType(resultSet.getString("ITEM_TYPE"));
            catalogItem.setServiceType(resultSet.getString("CAPTION"));

            System.out.println(catalogItem.toString());

            return catalogItem;
        });
    }
}
