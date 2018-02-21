/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.moose.pce.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class PCEDBHelper {

    @Autowired
    @Qualifier("pceJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public int getPCEComponentVersionTotal(long version) {
        return jdbcTemplate.queryForObject(
                "select count(1) from pce1pcown.tbcatalog_item c where c.pcversion_id = ? and c.item_type in ('OF','CO') ", new Object[] { version }, Integer.class);
    }

    public List<Long> getPCECatalogVersions() {
        return jdbcTemplate.query(  "select PCVERSION_ID from pce1pcown.tbpc_version order by 1 desc", (rs, rowNum) -> rs.getLong("PCVERSION_ID"));
    }

    public List<Map<String, Object>> getListAttributes(long childId, long pceVersion) {
         return jdbcTemplate.queryForList("select    distinct(ci.CAPTION) COMP_CODE, "
                + "   ip.CID,    ip.PCVERSION_ID,     ip.PROPERTY_ID,    pro.PROPERTY_NAME,    ip.DOMAIN_NAME,    tb.name_value as name_text  " +
                "from     pce1pcown.TBCATALOG_ITEM ci,  pce1pcown.TBITEM_PROPERTY ip,  pce1pcown.TBPROPERTY pro,  pce1pcown.tblocalized_prop tb " +
                "where  ci.CID=ip.CID and   ci.IS_DELETED=0 and ip.PROPERTY_ID=pro.PROPERTY_ID and   ip.PCVERSION_ID = ? and   ip.cid = ? " +
                "and   tb.property_id = ip.property_id and tb.language = 'ES' order by ci.CAPTION", new Object[] {pceVersion, childId});
    }

    public List<Map<String, Object>> getParents(long childId) {
        return jdbcTemplate.queryForList("select distinct r.child_id child_id, r.child_item_type, decode(r.parent_item_type,'PR','0', parent_id) parent_id, "
            + "max(r.pcversion_id) pcversion_id from pce1pcown.tbrelation r where child_item_type in('PR','CO')  and r.child_id = ? "
            + "group by r.child_id,r.parent_id,parent_item_type,r.child_item_type, decode(parent_item_type,'PR','', parent_id)", new Object[] { childId});
}

    public List<Map<String, Object>> getPCEComponentsVersion(long version) {
        return jdbcTemplate.queryForList("select c.CID child_id, nvl((select co.service_type from pce1pcown.tbcomponent co where co.pcversion_id = c.pcversion_id and co.cid = c.cid ),'Sin Service Type') serviceType, nvl((select n.name_text from pce1pcown.tbname n where n.pcversion_id=c.pcversion_id and n.cid = c.cid and n.language = 'ES'),c.caption) name_text, c.item_type, c.caption "
                + "from pce1pcown.tbcatalog_item c where  c.pcversion_id = ?  and c.item_type = 'CO' "
                + "      UNION ALL "
                + "     select c.CID child_id, 'ADSL' service_type,  nvl((select n.name_text from pce1pcown.tbname n where n.pcversion_id=c.pcversion_id and n.cid = c.cid and n.language = 'ES'),c.caption) name_text, c.item_type, c.caption "
                + "      FROM  pce1pcown.tbcatalog_item c where c.pcversion_id = ? and c.item_type = 'OF' ", new Object[] {version, version});
    }

    public List<Map<String, Object>> getAllDomains() {
        return jdbcTemplate.queryForList("select dom.DOMAIN_NAME DOMAIN_NAME, dom.DOMAIN_TYPE DOMAIN_TYPE " +
                "from pce1pcown.TBDOMAIN dom where (dom.DOMAIN_NAME like 'Abs%' or dom.DOMAIN_NAME like 'Dyn%')");
    }

    public List<Map<String, Object>> getDomainEntries(String domainName) {
        return jdbcTemplate.queryForList("select vv.DOMAIN_NAME, vv.DISCRETE_CODE,de.CAPTION CAPTION_EN from pce1pcown.TBVALID_VALUE vv,"
                + "pce1pcown.TBDECODE de where vv.DOMAIN_NAME like ? and vv.DECODE_ID=de.DECODE_ID and de.LANGUAGE_ID='ES'", new Object[]{domainName.trim()});
    }

}