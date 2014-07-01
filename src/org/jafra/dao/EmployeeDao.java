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
import org.jafra.entities.Employee;
import org.jafra.interfases.IConfigurable;
import org.jafra.interfases.IEmailDao;
import org.jafra.interfases.IEmployee;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Administrador
 */
public class EmployeeDao implements IEmployee, IConfigurable, InitializingBean {

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
    public Employee getInfo(Integer employeeId) {
        Employee employee = new Employee();
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT TrabajadorId, ISNULL( Email, '").append(_COPY_EMAIL).append("') As Email, NombreCompleto, Estatus ");
        sb.append(" FROM ").append("Personal");
        sb.append(" WHERE TrabajadorId = :empleadoId");

        Map<String, Object> namedParameters = new HashMap<String, Object>();

        /* asignamos el valor del sistema */
        namedParameters.put("empleadoId", employeeId);
        
        for(Employee emp : namedParameterJdbcTemplate.query(sb.toString(), namedParameters, new EmployeeMapper())){
            employee = emp;
        }
        
        return employee;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException(
                    "Debe establecer el dataSource Email Dao");
        }
    }

    /**
     * Row mapper para consultas
     */
    private static final class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();

            try {

                employee.setId(rs.getInt("TrabajadorId"));
                employee.setName(rs.getString("NombreCompleto").trim());
                employee.setEmail(rs.getString("Email").trim());
                employee.setStatus(rs.getString("Estatus").trim());

            } catch (SQLException ex) {
                Logger.getLogger(EmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return employee;
        }
    }

}
