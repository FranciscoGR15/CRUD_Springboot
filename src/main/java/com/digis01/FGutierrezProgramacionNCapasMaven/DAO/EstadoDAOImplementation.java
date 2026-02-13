package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstado {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetEstadosByPais(int IdPais) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {

            jdbcTemplate.execute("{CALL GetEstadoByPais(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                while(resultSet.next()) {                    
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombreEstado(resultSet.getString("NombreEstado"));
                    
                    result.objects.add(estado);
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
