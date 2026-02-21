
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;

public interface IDireccion {
    Result DeleteDireccionSP(int idDireccion);
    Result DireccionAddSP(Direccion direccion);
    Result DireccionUpdateSP (Direccion direccion);
}
