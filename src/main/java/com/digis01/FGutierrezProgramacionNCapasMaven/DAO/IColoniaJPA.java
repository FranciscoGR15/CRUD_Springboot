
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;

public interface IColoniaJPA {
    Result GetColoniaByMunicipio(int IdMunicipio);
}
