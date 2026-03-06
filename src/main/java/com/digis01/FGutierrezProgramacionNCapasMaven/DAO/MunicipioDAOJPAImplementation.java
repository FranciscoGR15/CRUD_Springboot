
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementation implements IMunicipioJPA{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetMunicipiosByEstado(int IdEstado) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            String jpql = "FROM Municipio m WHERE m.estado.idEstado = :idEstado";
            List<Municipio> municipiosJPA = entityManager.createQuery(jpql, Municipio.class)
                    .setParameter("idEstado", IdEstado).getResultList();
            
            if (municipiosJPA != null) {
                for(Municipio municipio : municipiosJPA){
                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio municipioML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio();
                    
                    municipioML.setIdMunicipio(municipio.getIdMunicipio());
                    municipioML.setNombreMunicipio(municipio.getNombreMunicipio());
                    
                    result.objects.add(municipioML);
                    
                }
                result.correct = true;
            } else {
                result.correct = false;
                return result;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
}
