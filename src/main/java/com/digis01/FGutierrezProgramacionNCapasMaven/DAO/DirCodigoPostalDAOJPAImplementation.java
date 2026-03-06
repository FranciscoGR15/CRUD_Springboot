package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DirCodigoPostalDAOJPAImplementation implements IDireccionCodigoPostalJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetDireccionByCodigoPostal(String CodigoPostal) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            String jpql = "SELECT c FROM Colonia c "
                    + "JOIN FETCH c.municipio m "
                    + "JOIN FETCH m.estado e "
                    + "JOIN FETCH e.pais p "
                    + "WHERE c.codigoPostal = :codigoPostal";
            List<Colonia> coloniaJPA = entityManager.createQuery(jpql, Colonia.class)
                    .setParameter("codigoPostal", CodigoPostal).getResultList();

            if (coloniaJPA != null) {
                for (Colonia colonia : coloniaJPA) {

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia coloniaML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia();
                    coloniaML.setIdColonia(colonia.getIdColonia());
                    coloniaML.setNombreColonia(colonia.getNombreColonia());
                    coloniaML.setCodigoPostal(colonia.getCodigoPostal());

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio municipioML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio();
                    municipioML.setIdMunicipio(colonia.getMunicipio().getIdMunicipio());
                    municipioML.setNombreMunicipio(colonia.getMunicipio().getNombreMunicipio());

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado estadoML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado();
                    estadoML.setIdEstado(colonia.getMunicipio().getEstado().getIdEstado());
                    estadoML.setNombreEstado(colonia.getMunicipio().getEstado().getNombreEstado());

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais paisML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais();
                    paisML.setIdPais(colonia.getMunicipio().getEstado().getPais().getIdPais());
                    paisML.setNombrePais(colonia.getMunicipio().getEstado().getPais().getNombrePais());

                    estadoML.setPais(paisML);
                    municipioML.setEstado(estadoML);
                    coloniaML.setMunicipio(municipioML);

                    result.objects.add(coloniaML);

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
