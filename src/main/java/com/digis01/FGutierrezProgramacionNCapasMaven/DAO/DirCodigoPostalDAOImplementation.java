package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DirCodigoPostalDAOImplementation implements IDireccionCodigoPostal {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetDireccionByCodigoPostal(String CodigoPostal) {

        Result result = new Result();
        result.objects = new ArrayList<>();

        try {

            jdbcTemplate.execute("{CALL GetDireccionByCodigoPostal(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, CodigoPostal);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    Municipio municipio = new Municipio();
                    Estado estado = new Estado();
                    Pais pais = new Pais();

                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombrePais(resultSet.getString("NombrePais"));

                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombreEstado(resultSet.getString("NombreEstado"));
                    estado.pais = pais;

                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                    municipio.estado = estado;

                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    colonia.municipio = municipio;

                    result.objects.add(colonia);
                }
                return true;
            }
            );
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;

    }

}
