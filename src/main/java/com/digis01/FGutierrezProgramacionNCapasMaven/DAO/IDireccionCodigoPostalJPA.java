
package com.digis01.FGutierrezProgramacionNCapasMaven.DAO;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;

public interface IDireccionCodigoPostalJPA {
    Result GetDireccionByCodigoPostal(String CodigoPostal);
}