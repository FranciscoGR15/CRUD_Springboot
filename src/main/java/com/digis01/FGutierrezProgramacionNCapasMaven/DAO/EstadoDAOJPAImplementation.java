
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOJPAImplementation implements IEstadoJPA{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetEstadosByPais(int IdPais) {
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            String jpql = "FROM Estado e WHERE e.pais.idPais = :idPais";
            List<Estado> estadoJPA = entityManager.createQuery(jpql, Estado.class)
                    .setParameter("idPais", IdPais).getResultList();
            
            if (estadoJPA != null) {
                for(Estado estado : estadoJPA){
                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado estadoML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado();
                    
                    estadoML.setIdEstado(estado.getIdEstado());
                    estadoML.setNombreEstado(estado.getNombreEstado());
                    
                    result.objects.add(estadoML);
                    
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
