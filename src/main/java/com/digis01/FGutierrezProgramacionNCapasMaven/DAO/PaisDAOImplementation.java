
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PaisDAOImplementation implements IPais {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {
        
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL PaisGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
               
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Pais paises = new Pais();
                    
                    paises.setIdPais(resultSet.getInt("IdPais"));
                    paises.setNombrePais(resultSet.getString("NombrePais"));
                    
                    result.objects.add(paises);
                    
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
