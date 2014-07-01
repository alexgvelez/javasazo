/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jafra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.jafra.entities.Email;
import org.jafra.interfases.IConfigurable;
import org.jafra.interfases.IEmailDao;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Administrador
 */
public class EmailDao implements IEmailDao, IConfigurable, InitializingBean {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * data Source de Conexión
     *
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate.setDataSource(dataSource);
    }

    @Override
    public List<Email> getAdressesByUserType(String userType) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT QJNAME, QJMAIL ");
        sb.append(" FROM ").append(_DESARROLLO).append(".QUJ020F");
        sb.append(" WHERE QJTIPU = :type");
        sb.append("   AND QJSTS = :status");
        sb.append(" ORDER BY QJNAME");

        Map<String, Object> namedParameters = new HashMap<String, Object>();

        /* asignamos el valor del sistema */
        namedParameters.put("type", userType);
        namedParameters.put("status", _ENABLED);

        
        //
        return namedParameterJdbcTemplate.query(sb.toString(), namedParameters, new EmailMapper());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException(
                    "Debe establecer el dataSource Email Dao");
        }
    }

    @Override
    public Email getAddressByUser(String userId) {
        Email email = new Email();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT QJNAME, QJMAIL ");
        sb.append(" FROM ").append(_DESARROLLO).append(".QUJ020F");
        sb.append(" WHERE QJUSR = :userId");
        sb.append("   AND QJSTS = :status");
        sb.append(" ORDER BY QJNAME");

        Map<String, Object> namedParameters = new HashMap<String, Object>();

        /* asignamos el valor del sistema */
        namedParameters.put("userId", userId.trim());
        namedParameters.put("status", _ENABLED);

        List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(sb.toString(), namedParameters);

        for (Map m : results) {

            email.setAddress(m.get("QJMAIL").toString().trim());
            email.setAdressName(m.get("QJNAME").toString().trim());

        }

        return email;
    }

    /**
     * Row mapper para consultas
     */
    private static final class EmailMapper implements RowMapper<Email> {

        @Override
        public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
            Email email = new Email();

            try {

                email.setAddress(rs.getString("QJMAIL").trim());
                email.setAdressName(rs.getString("QJNAME").trim());

            } catch (SQLException ex) {
                Logger.getLogger(EmailDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return email;
        }
    }

}
