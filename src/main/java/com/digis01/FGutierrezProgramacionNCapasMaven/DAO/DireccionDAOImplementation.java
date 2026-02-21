
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result DeleteDireccionSP(int idDireccion) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL DeleteDireccionSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
               
                callableStatement.setInt(1, idDireccion);
                callableStatement.execute();
                
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

    @Override
    public Result DireccionAddSP(Direccion direccion) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL DireccionAddSP(?, ?, ?, ?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, direccion.getUsuario().getIdUsuario());
                callableStatement.execute();
                
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

    @Override
    public Result DireccionUpdateSP(Direccion direccion) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL DireccionUpdateSP(?, ?, ?, ?, ?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.getColonia().getIdColonia());
                callableStatement.setInt(5, direccion.getIdDireccion());
                callableStatement.execute();
                
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
