
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;

public interface IEstadoJPA {
    Result GetEstadosByPais(int IdPais);
}
