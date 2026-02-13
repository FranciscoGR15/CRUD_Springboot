
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;


public interface IMunicipio {
    Result GetMunicipiosByEstado(int IdEstado);
}
