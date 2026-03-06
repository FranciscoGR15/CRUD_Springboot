package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementation implements IColoniaJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetColoniaByMunicipio(int IdMunicipio) {

        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            String jpql = "FROM Colonia c WHERE c.municipio.idMunicipio = :idMunicipio";
            List<Colonia> coloniasJPA = entityManager
                    .createQuery(jpql, Colonia.class)
                    .setParameter("idMunicipio", IdMunicipio)
                    .getResultList();

            if (coloniasJPA != null && !coloniasJPA.isEmpty()) {
                for (Colonia colonia : coloniasJPA) {
                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia coloniaML
                            = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia();

                    coloniaML.setIdColonia(colonia.getIdColonia());
                    coloniaML.setNombreColonia(colonia.getNombreColonia());
                    coloniaML.setCodigoPostal(colonia.getCodigoPostal());

                    result.objects.add(coloniaML);
                }
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontraron colonias para el municipio con id " + IdMunicipio;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
