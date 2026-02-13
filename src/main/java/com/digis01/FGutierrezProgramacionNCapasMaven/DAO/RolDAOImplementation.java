package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.catalina.valves.JDBCAccessLogValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementation implements IRol {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL RolGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
               
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();
                
                while (resultSet.next()) {
                    
                    Rol roles = new Rol();
                    
                    roles.setIdRol(resultSet.getInt("IdRol"));
                    roles.setNombreRol(resultSet.getString("NombreRol"));
                    
                    result.objects.add(roles);
                    
                }
                return true;
                
            });
            result.correct = true;
            return result;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
