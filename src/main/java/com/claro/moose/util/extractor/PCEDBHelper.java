package com.claro.moose.util.extractor;

import java.util.List;
import java.util.Map;
import com.claro.moose.models.Attribute;
import com.claro.moose.models.AttributeDomain;
import com.claro.moose.models.Component;
import com.claro.moose.models.Domain;
import com.claro.moose.models.Entry;
import com.claro.moose.models.PCEVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PCEDBHelper {
        @Autowired
        @Qualifier("pceJdbcTemplate")
        private JdbcTemplate jdbcTemplate;

        private static final long TEST_PCE_VERSION = 20180703L;

        public int getPCEComponentVersionTotal(long version) {
                return jdbcTemplate.queryForObject(
                                "select count(1) from pce1pcown.tbcatalog_item c where c.pcversion_id = ? and c.item_type in ('OF','CO') ",
                                new Object[] { version }, Integer.class);
        }

        public List<PCEVersion> getPCECatalogVersions() {
                return jdbcTemplate.query("select pcversion_id AS ID from pce1pcown.tbpc_version order by 1 desc",
                                new BeanPropertyRowMapper<PCEVersion>(PCEVersion.class));
        }

        public List<AttributeDomain> getComponentAttributes(Component component) {
                return jdbcTemplate.query("select    distinct(ci.CAPTION) COMP_CODE, "
                                + "   ip.CID,    ip.PCVERSION_ID,     ip.PROPERTY_ID,    pro.PROPERTY_NAME,    ip.DOMAIN_NAME,    tb.name_value as name_text  "
                                + "from     pce1pcown.TBCATALOG_ITEM ci,  pce1pcown.TBITEM_PROPERTY ip,  pce1pcown.TBPROPERTY pro,  pce1pcown.tblocalized_prop tb "
                                + "where  ci.CID=ip.CID and   ci.IS_DELETED=0 and ip.PROPERTY_ID=pro.PROPERTY_ID and   ip.PCVERSION_ID = ? and   ip.cid = ? "
                                + "and   tb.property_id = ip.property_id and tb.language = 'ES' order by ci.CAPTION",
                                new Object[] { TEST_PCE_VERSION, component.getCatalogId() }, (rs, rowNum) -> {
                                        Domain domain = new Domain();
                                        domain.setName(rs.getString("DOMAIN_NAME"));

                                        List<Entry> entries = getDomainEntries(domain);
                                        domain.setEntry(entries);

                                        Attribute attribute = new Attribute();
                                        attribute.setName(rs.getString("NAME_TEXT"));
                                        attribute.setPceVersion(component.getPceVersion());
                                        attribute.setPropertyid(rs.getInt("PROPERTY_ID"));
                                        attribute.setPropertyname(rs.getString("PROPERTY_NAME"));
                                        attribute.setDomain(domain);

                                        AttributeDomain attrDomain = new AttributeDomain();
                                        attrDomain.setAttribute(attribute);
                                        attrDomain.setDomain(domain);
                                        attrDomain.setComponent(component);

                                        return attrDomain;
                                });
        }

        public List<Map<String, Object>> getParents(long childId) {
                return jdbcTemplate.queryForList(
                                "select distinct r.child_id child_id, r.child_item_type, decode(r.parent_item_type,'PR','0', parent_id) parent_id, "
                                                + "max(r.pcversion_id) pcversion_id from pce1pcown.tbrelation r where child_item_type in('PR','CO')  and r.child_id = ? "
                                                + "group by r.child_id,r.parent_id,parent_item_type,r.child_item_type, decode(parent_item_type,'PR','', parent_id)",
                                new Object[] { childId });
        }

        public List<Component> getPCEComponentsByVersion(PCEVersion pceVersion) {
                return jdbcTemplate.query(
                                "select c.CID child_id, nvl((select co.service_type from pce1pcown.tbcomponent co where co.pcversion_id = c.pcversion_id and co.cid = c.cid ),'Sin Service Type') serviceType, nvl((select n.name_text from pce1pcown.tbname n where n.pcversion_id=c.pcversion_id and n.cid = c.cid and n.language = 'ES'),c.caption) name_text, c.item_type, c.caption "
                                                + "from pce1pcown.tbcatalog_item c where  c.pcversion_id = ?  and c.item_type = 'CO' "
                                                + "      UNION ALL "
                                                + "     select c.CID child_id, 'ADSL' service_type,  nvl((select n.name_text from pce1pcown.tbname n where n.pcversion_id=c.pcversion_id and n.cid = c.cid and n.language = 'ES'),c.caption) name_text, c.item_type, c.caption "
                                                + "      FROM  pce1pcown.tbcatalog_item c where c.pcversion_id = ? and c.item_type = 'OF' ",
                                new Object[] { TEST_PCE_VERSION, pceVersion.getId() }, (rs, rowNum) -> {
                                        Component c = new Component();
                                        c.setCatalogId(rs.getLong("CHILD_ID"));
                                        c.setCompCode(rs.getString("CAPTION"));
                                        c.setName(rs.getString("NAME_TEXT"));
                                        c.setPceVersion(pceVersion);
                                        c.setServiceType(rs.getString("SERVICETYPE"));
                                        c.setItemType(rs.getString("ITEM_TYPE"));

                                        return c;
                                });
        }

        public List<Entry> getDomainEntries(Domain domain) {
                return jdbcTemplate.query(
                                "select vv.DOMAIN_NAME, vv.DISCRETE_CODE,de.CAPTION CAPTION_EN from pce1pcown.TBVALID_VALUE vv,"
                                                + "pce1pcown.TBDECODE de where vv.DOMAIN_NAME like ? and vv.DECODE_ID=de.DECODE_ID and de.LANGUAGE_ID='ES'",
                                new Object[] { domain.getName().trim() }, (rs, rowNum) -> {
                                        Entry entry = new Entry();
                                        entry.setDomain(domain);
                                        entry.setDecodedValue(rs.getString("CAPTION_EN"));
                                        entry.setValue(rs.getString("DISCRETE_CODE"));

                                        return entry;
                                });
        }

}