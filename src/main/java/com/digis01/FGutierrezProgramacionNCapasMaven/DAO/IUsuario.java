
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;

public interface IUsuario {
    Result GetAll();
    Result GetById(int IdUsuario);
    Result UsuarioDireccionAddSP(Usuario usuario);
    Result UsuarioImageUpdateSP(Usuario usuario);
    Result UsuarioUpdateSP(Usuario usuario);
    Result UsuarioDeleteSP(Usuario usuario);
}
