package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        jdbcTemplate.execute("{CALL UsuarioDireccionesGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

            callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
            result.objects = new ArrayList<>();

            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("IdUsuario");
                if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {

                    Usuario usuarioActual = (Usuario) result.objects.get(result.objects.size() - 1);

                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombrePais(resultSet.getString("NombrePais"));

                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombreEstado(resultSet.getString("NombreEstado"));
                    estado.pais = pais;

                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                    municipio.estado = estado;

                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    colonia.municipio = municipio;

                    direccion.colonia = colonia;

                    usuarioActual.direcciones.add(direccion);

                } else {
                    Usuario usuario = new Usuario();

                    usuario.rol = new Rol();

                    usuario.setIdUsuario(idUsuario);
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setUserName(resultSet.getString("UserName"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.setCURP(resultSet.getString("CURP"));
                    usuario.rol.setNombreRol(resultSet.getString("NombreRol"));
                    usuario.setImagen(resultSet.getString("Imagen"));

                    result.objects.add(usuario);

                    int idDireccion = resultSet.getInt("IdDireccion");
                    if (idDireccion != 0) {
                        usuario.direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(idDireccion);
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Pais pais = new Pais();
                        pais.setIdPais(resultSet.getInt("IdPais"));
                        pais.setNombrePais(resultSet.getString("NombrePais"));

                        Estado estado = new Estado();
                        estado.setIdEstado(resultSet.getInt("IdEstado"));
                        estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        estado.pais = pais;

                        Municipio municipio = new Municipio();
                        municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        municipio.estado = estado;

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        colonia.municipio = municipio;

                        direccion.colonia = colonia;

                        usuario.direcciones.add(direccion);
                    }
                }
            }

            return true;

        }
        );
        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetByIdSP(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                Usuario usuario = null;

                while (resultSet.next()) {

                    if (usuario == null) {
                        usuario = new Usuario();
                        usuario.rol = new Rol();
                        usuario.direcciones = new ArrayList<>();

                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setUserName(resultSet.getString("UserName"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setCURP(resultSet.getString("CURP"));
                        usuario.setImagen(resultSet.getString("Imagen"));

                        usuario.rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.rol.setNombreRol(resultSet.getString("NombreRol"));
                    }

                    int idDireccion = resultSet.getInt("IdDireccion");

                    if (idDireccion != 0) {

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(idDireccion);
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Pais pais = new Pais();
                        pais.setIdPais(resultSet.getInt("IdPais"));
                        pais.setNombrePais(resultSet.getString("NombrePais"));

                        Estado estado = new Estado();
                        estado.setIdEstado(resultSet.getInt("IdEstado"));
                        estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        estado.pais = pais;

                        Municipio municipio = new Municipio();
                        municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        municipio.estado = estado;

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        colonia.municipio = municipio;

                        direccion.colonia = colonia;

                        usuario.direcciones.add(direccion);
                    }
                }

                if (usuario != null) {
                    result.object = usuario;
                    result.correct = true;
                }

                result.object = usuario;
                result.correct = true;
                return true;
            }
            );
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UsuarioDireccionAddSP(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute(
                    "{call UsuarioDireccionAddSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {

                        callableStatement.setString(1, usuario.getNombre());
                        callableStatement.setString(2, usuario.getApellidoPaterno());
                        callableStatement.setString(3, usuario.getApellidoMaterno());
                        callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(5, usuario.getUserName());
                        callableStatement.setString(6, usuario.getEmail());
                        callableStatement.setString(7, usuario.getPassword());
                        callableStatement.setString(8, usuario.getSexo());
                        callableStatement.setString(9, usuario.getTelefono());
                        callableStatement.setString(10, usuario.getCelular());
                        callableStatement.setString(11, usuario.getCURP());
                        callableStatement.setInt(12, usuario.getRol().getIdRol());

                        Direccion direccion = usuario.getDirecciones().get(0);

                        callableStatement.setString(13, direccion.getCalle());
                        callableStatement.setString(14, direccion.getNumeroInterior());
                        callableStatement.setString(15, direccion.getNumeroExterior());
                        callableStatement.setInt(16, direccion.getColonia().getIdColonia());
                        callableStatement.setString(17, usuario.getImagen());

                        callableStatement.execute();
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

    @Override
    public Result UsuarioImageUpdateSP(Usuario usuario) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL UsuarioImageUpdateSP(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getImagen());
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
    public Result UsuarioUpdateSP(Usuario usuario) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL UsuarioUpdateSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(5, usuario.getUserName());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getSexo());
                callableStatement.setString(8, usuario.getTelefono());
                callableStatement.setString(9, usuario.getCelular());
                callableStatement.setString(10, usuario.getCURP());
                callableStatement.setInt(11, usuario.getRol().getIdRol());
                callableStatement.setInt(12, usuario.getIdUsuario());
                
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
    public Result UsuarioDeleteSP(Usuario usuario) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.execute("{CALL UsuarioDeleteSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.execute();
                
                return  true;
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
