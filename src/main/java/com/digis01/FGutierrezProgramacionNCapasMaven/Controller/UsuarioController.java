package com.digis01.FGutierrezProgramacionNCapasMaven.Controller;

import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.DirCodigoPostalDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Autowired
    private DirCodigoPostalDAOImplementation dirCodigoPostalDAOImplementation;

    @GetMapping
    public String Index(Model model) {

        Result result = usuarioDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        return "GetAll";

    }

    @GetMapping("form")
    public String Accion(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        return "Formulario";

    }

    @GetMapping("/form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {

        Result result = usuarioDAOImplementation.GetById(IdUsuario);

        model.addAttribute("usuario", result.object);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "Formulario";

    }

    @GetMapping("/getEstadosByPais/{IdPais}")
    @ResponseBody
    public Result getEstadosByPais(@PathVariable("IdPais") int IdPais) {

        Result result = estadoDAOImplementation.GetEstadosByPais(IdPais);

        return result;
    }

    @GetMapping("/getMunicipiosByEstado/{IdEstado}")
    @ResponseBody
    public Result getMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado) {

        Result result = municipioDAOImplementation.GetMunicipiosByEstado(IdEstado);
        return result;
    }

    @GetMapping("/getColoniasByMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result getColoniasByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {

        Result result = coloniaDAOImplementation.GetColoniaByMunicipio(IdMunicipio);
        return result;
    }

    @GetMapping("/getDireccionByCodigoPostal/{codigoPostal}")
    @ResponseBody
    public Result getDireccionByCP(@PathVariable String codigoPostal) {

        return dirCodigoPostalDAOImplementation.GetDireccionByCodigoPostal(codigoPostal);
    }

}
