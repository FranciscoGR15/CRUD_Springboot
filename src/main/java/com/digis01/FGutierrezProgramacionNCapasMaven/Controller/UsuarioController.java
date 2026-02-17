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
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    //TABLA DE DATOS
    @GetMapping
    public String Index(Model model) {

        Result result = usuarioDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        return "GetAll";

    }

    //FORMULARIO VACIO PARA NUEVO USUARIO
    @GetMapping("form")
    public String Accion(Model model) {
        Usuario usuario = new Usuario();

        // Inicializar Rol
        usuario.setRol(new Rol());

        // Inicializar Direccion con Colonia
        Direccion direccion = new Direccion();
        usuario.getDirecciones().add(direccion);

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        // colonias, estados y municipios los llenas dinámicamente por AJAX
        return "Formulario";
    }

    //METODO POST (AGREGAR)
    @PostMapping("form")
    public String guardarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            return "Formulario";
        }

        // Llamar a tu SP (sin imagen)
        Result result = usuarioDAOImplementation.UsuarioDireccionAddSP(usuario);
        if (!result.correct) {
            model.addAttribute("error", "Error al guardar usuario: " + result.errorMessage);
            model.addAttribute("usuario", usuario);
            return "Formulario";
        }

        return "redirect:/usuario";
    }

    //EDITAR
    @GetMapping("/form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {

        Result result = usuarioDAOImplementation.GetById(IdUsuario);

        model.addAttribute("usuario", result.object);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "Formulario";

    }

    //CONTROLLER PARA SELECT - PAIS
    @GetMapping("/getEstadosByPais/{IdPais}")
    @ResponseBody
    public Result getEstadosByPais(@PathVariable("IdPais") int IdPais) {

        Result result = estadoDAOImplementation.GetEstadosByPais(IdPais);

        return result;
    }

    //CONTROLLER PARA SELECT - ESTADO
    @GetMapping("/getMunicipiosByEstado/{IdEstado}")
    @ResponseBody
    public Result getMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado) {

        Result result = municipioDAOImplementation.GetMunicipiosByEstado(IdEstado);
        return result;
    }

    //CONTROLLER PARA SELECT - MUNICIPIO
    @GetMapping("/getColoniasByMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result getColoniasByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {

        Result result = coloniaDAOImplementation.GetColoniaByMunicipio(IdMunicipio);
        return result;
    }

    //CONTROLLER PARA INPUT - CODIGO POSTAL
    @GetMapping("/getDireccionByCodigoPostal/{codigoPostal}")
    @ResponseBody
    public Result getDireccionByCP(@PathVariable String codigoPostal) {

        return dirCodigoPostalDAOImplementation.GetDireccionByCodigoPostal(codigoPostal);
    }

}
