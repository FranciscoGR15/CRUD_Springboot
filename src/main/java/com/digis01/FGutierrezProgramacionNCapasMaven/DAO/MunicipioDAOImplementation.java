
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetMunicipiosByEstado(int IdEstado) {
        
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            
            jdbcTemplate.execute("{CALL GetMunicipioByEstado(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setInt(1, IdEstado);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                while(resultSet.next()) {                    
                    Municipio municipio = new Municipio();
                    
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                    
                    result.objects.add(municipio);
                    
                }
                
                return true;
            });
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    
    
}
