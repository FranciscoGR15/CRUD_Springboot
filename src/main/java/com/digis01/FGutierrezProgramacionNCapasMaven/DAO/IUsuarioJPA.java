
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import java.util.List;

public interface IUsuarioJPA {
    
    Result GetAll();
    
    Result GetById(int idUsuario);
    
    Result UsuarioDireccionAdd(Usuario usuarioML);
    
    Result UsuarioImageUpdate(Usuario usuarioML);
    
    Result UsuarioDireccionDelete(Usuario usuarioML);
    
    Result UsuarioUpdate(Usuario usuarioML);
    
    Result UsuarioStatusUpdate(Usuario usuarioML);
    
    Result GetAllFiltrado(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol);
    
    Result UsuarioDireccionAddAll(List<Usuario> usuarios);
}
