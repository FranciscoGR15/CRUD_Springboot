package com.digis01.FGutierrezProgramacionNCapasMaven.Controller;

import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.DirCodigoPostalDAOImplementation;
import com.digis01.FGutierrezProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
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
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    //------------------ TABLA DE DATOS ------------------------
    @GetMapping
    public String Index(Model model) {

        Result result = usuarioDAOImplementation.GetAll();

        model.addAttribute("usuarios", result.objects);
        return "GetAll";

    }

    //------------ FORMULARIO VACIO PARA NUEVO USUARIO --------------
    @GetMapping("form")
    public String Accion(Model model) {

        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());

        Direccion direccion = new Direccion();
        Colonia colonia = new Colonia();
        direccion.setColonia(colonia);

        usuario.setDirecciones(new ArrayList<>());
        usuario.getDirecciones().add(direccion);

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "Formulario";
    }

    //--------------- METODO POST (AGREGAR) --------------------
    @PostMapping("form")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            return "Formulario";
        }

        String nombreArchivo = imagenFile.getOriginalFilename();
        String[] cadena = nombreArchivo.split("\\.");
        if (cadena[1].equals("jpg") || cadena[1].equals("png")) {
            System.out.println("Imagen");
            try {
                byte[] arregloBytes = imagenFile.getBytes();
                String base64Img = Base64.getEncoder().encodeToString(arregloBytes);
                usuario.setImagen(base64Img);
            } catch (Exception ex) {
                return "Formulario";
            }
        } else if (imagenFile != null) {

        }

        Result result = usuarioDAOImplementation.UsuarioDireccionAddSP(usuario);
        if (!result.correct) {
            model.addAttribute("error", "Error al guardar usuario: " + result.errorMessage);
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            return "Formulario";
        }

        return "redirect:/usuario";
    }

    //---------------- METODO POST (AGREGAR DIRECCION) ----------------
    @PostMapping("/direccion/addDireccion")
    public String addDireccion(@ModelAttribute Direccion direccion,
            @RequestParam int idUsuario, RedirectAttributes redirectAttributes) {

        direccion.setUsuario(new Usuario());
        direccion.getUsuario().setIdUsuario(idUsuario);

        Result result = direccionDAOImplementation.DireccionAddSP(direccion);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Direccion agregada");
            redirectAttributes.addFlashAttribute("text", "Se agrego la dirección.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No se agrego la dirección");
        }

        return "redirect:/usuario/perfil/" + idUsuario;
    }

    //--------------- CARGAR VISTA DE CADA USUARIO -------------------
    @GetMapping("/perfil/{idUsuario}")
    public String perfilUsuario(@PathVariable int idUsuario, Model model) {

        Result result = usuarioDAOImplementation.GetById(idUsuario);
        Usuario usuario = (Usuario) result.object;

        if (usuario.getDirecciones() == null) {
            usuario.setDirecciones(new ArrayList<>());
        }

        if (usuario.getDirecciones().isEmpty()) {

            Direccion direccion = new Direccion();
            Colonia colonia = new Colonia();
            Municipio municipio = new Municipio();
            Estado estado = new Estado();
            Pais pais = new Pais();

            estado.setPais(pais);
            municipio.setEstado(estado);
            colonia.setMunicipio(municipio);
            direccion.setColonia(colonia);

            usuario.getDirecciones().add(direccion);
        }

        Direccion direccion = usuario.getDirecciones().get(0);

        int idPais = direccion.getColonia()
                .getMunicipio()
                .getEstado()
                .getPais()
                .getIdPais();

        int idEstado = direccion.getColonia()
                .getMunicipio()
                .getEstado()
                .getIdEstado();

        int idMunicipio = direccion.getColonia()
                .getMunicipio()
                .getIdMunicipio();

        model.addAttribute("estados",
                estadoDAOImplementation.GetEstadosByPais(idPais).objects);

        model.addAttribute("municipios",
                municipioDAOImplementation.GetMunicipiosByEstado(idEstado).objects);

        model.addAttribute("colonias",
                coloniaDAOImplementation.GetColoniaByMunicipio(idMunicipio).objects);

        //  SOLO crear direccionNueva limpia
        Direccion direccionNueva = new Direccion();
        direccionNueva.setColonia(new Colonia());
        direccionNueva.getColonia().setMunicipio(new Municipio());
        direccionNueva.getColonia().getMunicipio().setEstado(new Estado());
        direccionNueva.getColonia().getMunicipio().getEstado().setPais(new Pais());

        model.addAttribute("direccionNueva", direccionNueva);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "UsuarioPerfil";
    }

    //-------------- ACTUALIZAR USUARIO -------------------
    @PostMapping("/updateUsuario")
    public String updateUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {

        Result result = usuarioDAOImplementation.UsuarioUpdateSP(usuario);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Actualizado");
            redirectAttributes.addFlashAttribute("text", "Usuario actualizado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No se pudo actualizazr el usuario.");
        }

        return "redirect:/usuario/perfil/" + usuario.getIdUsuario();
    }

    //------------- MODIFICAR FOTO -------------------
    @PostMapping("/perfil/updateImagen")
    public String updateImagen(@RequestParam("idUsuario") int idUsuario,
            @RequestParam("imagenFile") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {

            if (!file.isEmpty()) {

                byte[] bytes = file.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                if (base64 != null && !base64.isEmpty()) {

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(idUsuario);
                    usuario.setImagen(base64);

                    Result result = usuarioDAOImplementation.UsuarioImageUpdateSP(usuario);

                    if (result.correct) {
                        redirectAttributes.addFlashAttribute("icon", "success");
                        redirectAttributes.addFlashAttribute("title", "Actualizado");
                        redirectAttributes.addFlashAttribute("text", "Foto actualizada correctamente.");
                    } else {
                        redirectAttributes.addFlashAttribute("icon", "error");
                        redirectAttributes.addFlashAttribute("title", "Error");
                        redirectAttributes.addFlashAttribute("text", "No se pudo actualizar la Foto.");
                    }

                }

            }

        } catch (Exception ex) {
            return "UsuarioPerfil";
        }

        return "redirect:/usuario/perfil/" + idUsuario;
    }

    //----------------- ELIMINAR Usurio y direcciones -------------
    @PostMapping("/delete/{idUsuario}")
    public String deleteUsuario(@PathVariable int idUsuario,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Result result = usuarioDAOImplementation.UsuarioDeleteSP(usuario);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Eliminado");
            redirectAttributes.addFlashAttribute("text", "Usuario eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No se pudo eliminar el usuario.");
        }

        return "redirect:/usuario";
    }

    /*-------------- VISA PARA EDITAR FORMULARIO COMPLETO --------------------*/
    @GetMapping("/form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {

        Result result = usuarioDAOImplementation.GetById(IdUsuario);

        Usuario usuario = (Usuario) result.object;

        if (usuario.getDirecciones() == null) {
            usuario.setDirecciones(new ArrayList<>());
        }

        if (usuario.getDirecciones().isEmpty()) {

            Direccion direccion = new Direccion();
            Colonia colonia = new Colonia();
            Municipio municipio = new Municipio();
            Estado estado = new Estado();
            Pais pais = new Pais();

            estado.setPais(pais);
            municipio.setEstado(estado);
            colonia.setMunicipio(municipio);
            direccion.setColonia(colonia);

            usuario.getDirecciones().add(direccion);
        }

        Direccion direccion = usuario.getDirecciones().get(0);

        int idPais = direccion.getColonia()
                .getMunicipio()
                .getEstado()
                .getPais()
                .getIdPais();

        int idEstado = direccion.getColonia()
                .getMunicipio()
                .getEstado()
                .getIdEstado();

        int idMunicipio = direccion.getColonia()
                .getMunicipio()
                .getIdMunicipio();

        model.addAttribute("estados",
                estadoDAOImplementation.GetEstadosByPais(idPais).objects);

        model.addAttribute("municipios",
                municipioDAOImplementation.GetMunicipiosByEstado(idEstado).objects);

        model.addAttribute("colonias",
                coloniaDAOImplementation.GetColoniaByMunicipio(idMunicipio).objects);

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        model.addAttribute("paises", paisDAOImplementation.GetAll().objects);

        return "Formulario";
    }

    /*------------------ ELIMINAR DIRECCION -----------------------*/
    @PostMapping("/deleteDireccion/{idDireccion}")
    public String deleteDireccion(@PathVariable int idDireccion,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = new Usuario();

        Direccion direccion = new Direccion();
        direccion.setIdDireccion(idDireccion);

        Result result = direccionDAOImplementation.DeleteDireccionSP(idDireccion);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Eliminado");
            redirectAttributes.addFlashAttribute("text", "Direcci+on eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No se pudo eliminar la dirección");
        }

        return "redirect:/usuario";

    }

    /* ---------------- ACTUALIZAR DIRECCION -----------------*/
    @PostMapping("/updateDireccion")
    public String updateDireccion(@ModelAttribute Direccion direccion, RedirectAttributes redirectAttributes) {
        
        Result result = direccionDAOImplementation.DireccionUpdateSP(direccion);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Actualizado");
            redirectAttributes.addFlashAttribute("text", "Se actualizo la direccion.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No fue posible actualizar la informacion." +result.errorMessage);
        }

        return "redirect:/usuario";
    }

    /*------------- CONTROLLER PARA CARGAR LOS SELECT ---------------*/
    //-------------- CONTROLLER PARA SELECT - PAIS ------------------
    @GetMapping("/getEstadosByPais/{IdPais}")
    @ResponseBody
    public Result getEstadosByPais(@PathVariable("IdPais") int IdPais) {

        Result result = estadoDAOImplementation.GetEstadosByPais(IdPais);

        return result;
    }

    //----------- CONTROLLER PARA SELECT - ESTADO ---------------
    @GetMapping("/getMunicipiosByEstado/{IdEstado}")
    @ResponseBody
    public Result getMunicipiosByEstado(@PathVariable("IdEstado") int IdEstado) {

        Result result = municipioDAOImplementation.GetMunicipiosByEstado(IdEstado);
        return result;
    }

    //------------ CONTROLLER PARA SELECT - MUNICIPIO ---------------
    @GetMapping("/getColoniasByMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result getColoniasByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {

        Result result = coloniaDAOImplementation.GetColoniaByMunicipio(IdMunicipio);
        return result;
    }

    //-------------- CONTROLLER PARA INPUT - CODIGO POSTAL ----------------
    @GetMapping("/getDireccionByCodigoPostal/{codigoPostal}")
    @ResponseBody
    public Result getDireccionByCP(@PathVariable String codigoPostal) {

        return dirCodigoPostalDAOImplementation.GetDireccionByCodigoPostal(codigoPostal);
    }

}
