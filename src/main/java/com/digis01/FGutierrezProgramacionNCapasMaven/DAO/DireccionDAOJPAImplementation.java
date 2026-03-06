package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DireccionDAOJPAImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Result DeleteDireccionSP(int idDireccion) {
        Result result = new Result();

        try {

            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            if (direccion == null) {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
                return result;
            }

            entityManager.remove(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DireccionAddSP(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML) {
        Result result = new Result();

        try {

            com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Direccion direccionJPA
                    = new com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Direccion();

            direccionJPA.setCalle(direccionML.getCalle());
            direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());

            direccionJPA.setUsuario(
                    entityManager.getReference(
                            com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Usuario.class,
                            direccionML.getUsuario().getIdUsuario()
                    )
            );

            direccionJPA.setColonia(
                    entityManager.getReference(
                            com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Colonia.class,
                            direccionML.getColonia().getIdColonia()
                    )
            );

            entityManager.persist(direccionJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    
    @Override
    @Transactional
    public Result DireccionUpdateSP(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML) {
        Result result = new Result();

        try {

            Direccion direccionJPA = entityManager.find(Direccion.class,direccionML.getIdDireccion());

            if (direccionJPA == null) {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
                return result;
            }

            direccionJPA.setCalle(direccionML.getCalle());
            direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());

            direccionJPA.setColonia(
                    entityManager.getReference(Colonia.class,direccionML.getColonia().getIdColonia()));

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DireccionGetById(int idDireccion) {
        Result result = new Result();
        
        try {
            Direccion direccion = entityManager.find(Direccion.class, idDireccion);
            
            if (direccion != null) {
                
                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion();
                
                direccionML.setIdDireccion(direccion.getIdDireccion());
                direccionML.setCalle(direccion.getCalle());
                direccionML.setNumeroInterior(direccion.getNumeroInterior());
                direccionML.setNumeroExterior(direccion.getNumeroExterior());
                
                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia coloniaML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia();
                
                
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
