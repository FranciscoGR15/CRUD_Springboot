package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;

public interface IDireccionJPA {

    Result DeleteDireccionSP(int idDireccion);

    Result DireccionAddSP(Direccion direccionML);

    Result DireccionUpdateSP(Direccion direccionML);

    Result DireccionGetById(int idDireccion);
}
