package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Rol;
import com.digis01.FGutierrezProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();

            result.objects = new ArrayList<>();
            result.objects.addAll(usuarios);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result GetById(int idUsuario) {
        Result result = new Result();

        try {

            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            if (usuario == null) {
                result.correct = false;
                return result;
            }

            usuario.getDirecciones().size();

            com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML
                    = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario();

            usuarioML.setIdUsuario(usuario.getIdUsuario());
            usuarioML.setNombre(usuario.getNombre());
            usuarioML.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioML.setApellidoMaterno(usuario.getApellidoMaterno());
            if (usuario.getFechaNacimiento() != null) {

                LocalDate localDate = usuario.getFechaNacimiento();

                Date fecha = Date.from(
                        localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                );

                usuarioML.setFechaNacimiento(fecha);
            }
            usuarioML.setEmail(usuario.getEmail());
            usuarioML.setUserName(usuario.getUserName());
            usuarioML.setTelefono(usuario.getTelefono());
            usuarioML.setCelular(usuario.getCelular());
            usuarioML.setSexo(usuario.getSexo());
            usuarioML.setCURP(usuario.getCURP());
            usuarioML.setImagen(usuario.getImagen());
            usuarioML.setStatus(usuario.getStatus());
            if (usuario.getRol() != null) {

                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol rolML
                        = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol();

                rolML.setIdRol(usuario.getRol().getIdRol());
                rolML.setNombreRol(usuario.getRol().getNombreRol());

                usuarioML.setRol(rolML);
            }

            List<com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion> direccionesML
                    = new ArrayList<>();

            for (Direccion dir : usuario.getDirecciones()) {

                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion dirML
                        = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion();

                dirML.setIdDireccion(dir.getIdDireccion());
                dirML.setCalle(dir.getCalle());
                dirML.setNumeroInterior(dir.getNumeroInterior());
                dirML.setNumeroExterior(dir.getNumeroExterior());

                if (dir.getColonia() != null) {

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia colML
                            = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia();

                    colML.setIdColonia(dir.getColonia().getIdColonia());
                    colML.setNombreColonia(dir.getColonia().getNombreColonia());
                    colML.setCodigoPostal(dir.getColonia().getCodigoPostal());

                    // ===== MUNICIPIO =====
                    if (dir.getColonia().getMunicipio() != null) {

                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio munML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio();

                        munML.setIdMunicipio(
                                dir.getColonia().getMunicipio().getIdMunicipio());

                        munML.setNombreMunicipio(
                                dir.getColonia().getMunicipio().getNombreMunicipio());

                        // ===== ESTADO =====
                        if (dir.getColonia().getMunicipio().getEstado() != null) {

                            com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado estML
                                    = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado();

                            estML.setIdEstado(
                                    dir.getColonia().getMunicipio().getEstado().getIdEstado());

                            estML.setNombreEstado(
                                    dir.getColonia().getMunicipio().getEstado().getNombreEstado());

                            // ===== PAIS =====
                            if (dir.getColonia().getMunicipio().getEstado().getPais() != null) {

                                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais paisML
                                        = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais();

                                paisML.setIdPais(
                                        dir.getColonia().getMunicipio().getEstado().getPais().getIdPais());

                                paisML.setNombrePais(
                                        dir.getColonia().getMunicipio().getEstado().getPais().getNombrePais());

                                estML.setPais(paisML);
                            }

                            munML.setEstado(estML);
                        }

                        colML.setMunicipio(munML);
                    }

                    dirML.setColonia(colML);
                }

                direccionesML.add(dirML);
            }

            usuarioML.setDirecciones(direccionesML);

            result.object = usuarioML;
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
    public Result UsuarioDireccionAdd(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();

        try {

            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombre(usuarioML.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioML.getApellidoMaterno());
            usuarioJPA.setUserName(usuarioML.getUserName());
            usuarioJPA.setEmail(usuarioML.getEmail());
            usuarioJPA.setPassword(usuarioML.getPassword());
            usuarioJPA.setSexo(usuarioML.getSexo());
            usuarioJPA.setTelefono(usuarioML.getTelefono());
            usuarioJPA.setCelular(usuarioML.getCelular());
            usuarioJPA.setCURP(usuarioML.getCURP());
            usuarioJPA.setImagen(usuarioML.getImagen());
            usuarioJPA.setStatus(usuarioML.getStatus());

            if (usuarioML.getFechaNacimiento() != null) {
                usuarioJPA.setFechaNacimiento(
                        usuarioML.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }

            Rol rolJPA = entityManager.getReference(Rol.class, usuarioML.getRol().getIdRol());
            usuarioJPA.setRol(rolJPA);

            if (usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()) {

                usuarioJPA.setDirecciones(new ArrayList<>());

                for (com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML : usuarioML.getDirecciones()) {
                    Direccion direccionJPA = new Direccion();
                    direccionJPA.setCalle(direccionML.getCalle());
                    direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
                    direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
                    Colonia coloniaJPA = entityManager.getReference(Colonia.class, direccionML.getColonia().getIdColonia());
                    direccionJPA.setColonia(coloniaJPA);
                    direccionJPA.setUsuario(usuarioJPA);
                    usuarioJPA.getDirecciones().add(direccionJPA);
                }
            }

            entityManager.persist(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UsuarioImageUpdate(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA == null) {
                result.correct = false;
                return result;
            }

            usuarioJPA.setImagen(usuarioML.getImagen());

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result UsuarioDireccionDelete(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA == null) {
                result.correct = false;
                return result;
            }

            entityManager.remove(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result UsuarioUpdate(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();

        try {

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA != null) {
                usuarioJPA.setNombre(usuarioML.getNombre());
                usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
                usuarioJPA.setApellidoMaterno(usuarioML.getApellidoMaterno());
                usuarioJPA.setUserName(usuarioML.getUserName());
                usuarioJPA.setEmail(usuarioML.getEmail());
                usuarioJPA.setSexo(usuarioML.getSexo());
                usuarioJPA.setTelefono(usuarioML.getTelefono());
                usuarioJPA.setCelular(usuarioML.getCelular());
                usuarioJPA.setCURP(usuarioML.getCURP());

                if (usuarioML.getFechaNacimiento() != null) {
                    usuarioJPA.setFechaNacimiento(usuarioML.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }

                Rol rolJPA = entityManager.getReference(Rol.class, usuarioML.getRol().getIdRol());
                usuarioJPA.setRol(rolJPA);

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

    @Override
    public Result UsuarioStatusUpdate(com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA == null) {
                result.correct = false;
                return result;
            }

            usuarioJPA.setStatus(usuarioML.getStatus());

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetAllFiltrado(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {

            StringBuilder jpql = new StringBuilder(
                    "SELECT u FROM Usuario u "
                    + "LEFT JOIN FETCH u.rol r "
                    + "LEFT JOIN FETCH u.direcciones d "
                    + "LEFT JOIN FETCH d.colonia c "
                    + "LEFT JOIN FETCH c.municipio m "
                    + "LEFT JOIN FETCH m.estado e "
                    + "LEFT JOIN FETCH e.pais p "
                    + "WHERE 1=1 "
            );

            if (nombre != null && !nombre.isEmpty()) {
                jpql.append("AND LOWER(u.nombre) LIKE LOWER(:nombre) ");
            }
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
                jpql.append("AND LOWER(u.apellidoPaterno) LIKE LOWER(:apellidoPaterno) ");
            }
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
                jpql.append("AND LOWER(u.apellidoMaterno) LIKE LOWER(:apellidoMaterno) ");
            }
            if (idRol != null && idRol != 0) {
                jpql.append("AND u.rol.idRol = :idRol ");
            }

            TypedQuery<Usuario> query = entityManager.createQuery(jpql.toString(), Usuario.class);

            if (nombre != null && !nombre.isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
                query.setParameter("apellidoPaterno", "%" + apellidoPaterno + "%");
            }
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
                query.setParameter("apellidoMaterno", "%" + apellidoMaterno + "%");
            }
            if (idRol != null && idRol != 0) {
                query.setParameter("idRol", idRol);
            }

            List<Usuario> usuarios = query.getResultList();

            Map<Integer, com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario> mapUsuarios = new LinkedHashMap<>();

            for (Usuario usuario : usuarios) {
                com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML = mapUsuarios.get(usuario.getIdUsuario());
                if (usuarioML == null) {
                    usuarioML = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario();
                    usuarioML.setIdUsuario(usuario.getIdUsuario());
                    usuarioML.setNombre(usuario.getNombre());
                    usuarioML.setApellidoPaterno(usuario.getApellidoPaterno());
                    usuarioML.setApellidoMaterno(usuario.getApellidoMaterno());
                    usuarioML.setUserName(usuario.getUserName());
                    usuarioML.setEmail(usuario.getEmail());
                    usuarioML.setSexo(usuario.getSexo());
                    usuarioML.setTelefono(usuario.getTelefono());
                    usuarioML.setCelular(usuario.getCelular());
                    usuarioML.setCURP(usuario.getCURP());
                    if (usuario.getFechaNacimiento() != null) {
                        usuarioML.setFechaNacimiento(
                                java.util.Date.from(
                                        usuario.getFechaNacimiento()
                                                .atStartOfDay(java.time.ZoneId.systemDefault())
                                                .toInstant()
                                )
                        );
                    }
                    usuarioML.setImagen(usuario.getImagen());
                    usuarioML.setStatus(usuario.getStatus());

                    usuarioML.rol = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol();
                    usuarioML.rol.setNombreRol(usuario.getRol().getNombreRol());

                    usuarioML.direcciones = new ArrayList<>();
                    mapUsuarios.put(usuario.getIdUsuario(), usuarioML);
                }

                // Mapear direcciones
                if (usuario.getDirecciones() != null) {
                    for (Direccion direccion : usuario.getDirecciones()) {
                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion();
                        direccionML.setIdDireccion(direccion.getIdDireccion());
                        direccionML.setCalle(direccion.getCalle());
                        direccionML.setNumeroExterior(direccion.getNumeroExterior());
                        direccionML.setNumeroInterior(direccion.getNumeroInterior());

                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia coloniaML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia();
                        coloniaML.setIdColonia(direccion.getColonia().getIdColonia());
                        coloniaML.setNombreColonia(direccion.getColonia().getNombreColonia());
                        coloniaML.setCodigoPostal(direccion.getColonia().getCodigoPostal());

                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio municipioML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio();
                        municipioML.setIdMunicipio(direccion.getColonia().getMunicipio().getIdMunicipio());
                        municipioML.setNombreMunicipio(direccion.getColonia().getMunicipio().getNombreMunicipio());

                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado estadoML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado();
                        estadoML.setIdEstado(direccion.getColonia().getMunicipio().getEstado().getIdEstado());
                        estadoML.setNombreEstado(direccion.getColonia().getMunicipio().getEstado().getNombreEstado());

                        com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais paisML
                                = new com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais();
                        paisML.setIdPais(direccion.getColonia().getMunicipio().getEstado().getPais().getIdPais());
                        paisML.setNombrePais(direccion.getColonia().getMunicipio().getEstado().getPais().getNombrePais());

                        estadoML.setPais(paisML);
                        municipioML.setEstado(estadoML);
                        coloniaML.setMunicipio(municipioML);
                        direccionML.setColonia(coloniaML);

                        usuarioML.direcciones.add(direccionML);
                    }
                }
            }

            result.objects.addAll(mapUsuarios.values());
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result UsuarioDireccionAddAll(List<com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario> usuarios) {
        Result result = new Result();
        try {
            int batchSize = 50;
            int i = 0;

            for (com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuarioML : usuarios) {

                // ===== Mapear Usuario ML → JPA =====
                Usuario usuarioJPA = new Usuario();

                usuarioJPA.setNombre(usuarioML.getNombre());
                usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
                usuarioJPA.setApellidoMaterno(usuarioML.getApellidoMaterno());
                usuarioJPA.setFechaNacimiento(
                        usuarioML.getFechaNacimiento()
                                .toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate());
                usuarioJPA.setUserName(usuarioML.getUserName());
                usuarioJPA.setEmail(usuarioML.getEmail());
                usuarioJPA.setPassword(usuarioML.getPassword());
                usuarioJPA.setSexo(usuarioML.getSexo());
                usuarioJPA.setTelefono(usuarioML.getTelefono());
                usuarioJPA.setCelular(usuarioML.getCelular());
                usuarioJPA.setCURP(usuarioML.getCURP());

                // Rol (solo referencia)
                Rol rol = entityManager.getReference(Rol.class, usuarioML.getRol().getIdRol());
                usuarioJPA.setRol(rol);

                entityManager.persist(usuarioJPA);

                // ===== Dirección =====
                if (usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()) {

                    com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion direccionML = usuarioML.getDirecciones().get(0);

                    Direccion direccionJPA = new Direccion();
                    direccionJPA.setCalle(direccionML.getCalle());
                    direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
                    direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());

                    // Relación con usuario
                    direccionJPA.setUsuario(usuarioJPA);

                    // Colonia (solo referencia)
                    Colonia colonia = entityManager.getReference(
                            Colonia.class,
                            direccionML.getColonia().getIdColonia()
                    );

                    direccionJPA.setColonia(colonia);

                    entityManager.persist(direccionJPA);
                }

                // ===== Control Batch =====
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }

                i++;
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
