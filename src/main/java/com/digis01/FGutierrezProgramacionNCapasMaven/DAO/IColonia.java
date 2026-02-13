
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;

public interface IColonia {
    Result GetColoniaByMunicipio(int IdMunicipio);
}
