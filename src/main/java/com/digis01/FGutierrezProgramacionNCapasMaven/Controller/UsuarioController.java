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
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Result;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import com.digis01.FGutierrezProgramacionNCapasMaven.Service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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

    @Autowired
    private ValidationService validationService;

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

    //------------------ TABLA DE DATOS CON FILTRO ------------------------
    @GetMapping("/getAll")
    public String GetAllFiltrado(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellidoPaterno", required = false) String apellidoPaterno,
            @RequestParam(name = "apellidoMaterno", required = false) String apellidoMaterno,
            @RequestParam(name = "idRol", required = false, defaultValue = "0") Integer idRol,
            Model model) {

        Result result = usuarioDAOImplementation.GetAllFiltrado(
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                (idRol != null && idRol != 0) ? idRol : null
        );

        // Cargamos la lista de usuarios filtrada
        model.addAttribute("usuarios", result.objects);

        // Lista de roles para el dropdown del formulario
        Result rolesResult = rolDAOImplementation.GetAll();
        model.addAttribute("roles", rolesResult.objects);

        // Mantener los valores del formulario
        model.addAttribute("param", new ParamFilter(nombre, apellidoPaterno, apellidoMaterno, idRol));

        return "GetAll";
    }

    // Clase auxiliar para mantener los valores del formulario
    public static class ParamFilter {

        public String nombre;
        public String apellidoPaterno;
        public String apellidoMaterno;
        public Integer idRol;

        public ParamFilter(String nombre, String apellidoPaterno, String apellidoMaterno, Integer idRol) {
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
            this.idRol = idRol;
        }
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

        if (direccion.getColonia() == null) {
            direccion.setColonia(new Colonia());
        }
        if (direccion.getColonia().getMunicipio() == null) {
            direccion.getColonia().setMunicipio(new Municipio());
        }
        if (direccion.getColonia().getMunicipio().getEstado() == null) {
            direccion.getColonia().getMunicipio().setEstado(new Estado());
        }
        if (direccion.getColonia().getMunicipio().getEstado().getPais() == null) {
            direccion.getColonia().getMunicipio().getEstado().setPais(new Pais());
        }

        Result result = direccionDAOImplementation.DireccionUpdateSP(direccion);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Actualizado");
            redirectAttributes.addFlashAttribute("text", "Se actualizó la dirección correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error");
            redirectAttributes.addFlashAttribute("text", "No fue posible actualizar la información: " + result.errorMessage);
        }

        int idUsuario = direccion.getUsuario() != null ? direccion.getUsuario().getIdUsuario() : 0;
        if (idUsuario == 0) {
            idUsuario = 1;
        }
        return "redirect:/usuario/perfil/" + idUsuario;
    }

    /* ------------ CARGA MASIVA -------------- */
    @GetMapping("/cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    /* ------------- Validacion ------------ */
    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {

            if (archivo != null) {
                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivoCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                List<Usuario> usuarios = null;

                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(new File(rutaArchivo));
                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoExcel(new File(rutaArchivo));
                } else {
                    redirectAttributes.addFlashAttribute("icon", "error");
                    redirectAttributes.addFlashAttribute("title", "Error");
                    redirectAttributes.addFlashAttribute("text", "Error: extension erronea");
                }

                List<ErroresArchivo> errores = ValidarDatos(usuarios);

                if (errores.isEmpty()) {

                    UUID uuidCarga = UUID.randomUUID();
                    request.getSession().setAttribute("ruta_" + uuidCarga, rutaArchivo);
                    request.getSession().setAttribute("tipo_" + uuidCarga, extension);

                    redirectAttributes.addFlashAttribute("uuidCarga", uuidCarga.toString());
                    redirectAttributes.addFlashAttribute("icon", "success");
                    redirectAttributes.addFlashAttribute("title", "Archivo válido");
                    redirectAttributes.addFlashAttribute("text", "Los datos son correctos, ¿Desea procesarlos?");
                    redirectAttributes.addFlashAttribute("mostrarProcesar", true);

                    return "redirect:/usuario/cargamasiva";
                } else {
                    redirectAttributes.addFlashAttribute("icon", "error");
                    redirectAttributes.addFlashAttribute("title", "Errores en el archivo");
                    redirectAttributes.addFlashAttribute("errores", errores);

                    return "redirect:/usuario/cargamasiva";
                }
                /*
                    - insertarlos
                    - renderizar la lista de errores
                 */
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        List<Usuario> usuarios;

        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            usuarios = new ArrayList<>();
            String cadena = "";

            while ((cadena = bufferedReader.readLine()) != null) {
                String[] datosUsuario = cadena.split("\\|");//Delimito con pipe |

                Usuario usuario = new Usuario();
                usuario.setNombre(datosUsuario[0]);
                usuario.setApellidoPaterno(datosUsuario[1]);
                usuario.setApellidoMaterno(datosUsuario[2]);
                SimpleDateFormat nFechaNacimiento = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
                nFechaNacimiento.setLenient(false);
                usuario.setFechaNacimiento(nFechaNacimiento.parse(datosUsuario[3]));
                usuario.setUserName(datosUsuario[4]);
                usuario.setEmail(datosUsuario[5]);
                usuario.setPassword(datosUsuario[6]);
                usuario.setSexo(datosUsuario[7]);
                usuario.setTelefono(datosUsuario[8]);
                usuario.setCelular(datosUsuario[9]);
                usuario.setCURP(datosUsuario[10]);

                Rol rol = new Rol();
                rol.setIdRol(Integer.parseInt(datosUsuario[11]));
                usuario.setRol(rol);

                Direccion direccion = new Direccion();
                direccion.setCalle(datosUsuario[12]);
                direccion.setNumeroInterior(datosUsuario[13]);
                direccion.setNumeroExterior(datosUsuario[14]);
                Colonia colonia = new Colonia();
                colonia.setIdColonia(Integer.parseInt(datosUsuario[15]));
                direccion.setColonia(colonia);

                direccion.setUsuario(usuario);
                usuario.getDirecciones().add(direccion);

                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            return null;
        }
        return usuarios;
    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = null;

        try (InputStream inputStream = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            usuarios = new ArrayList<>();

            DataFormatter dataFormatter = new DataFormatter();

            for (Row row : sheet) {
                Usuario usuario = new Usuario();

                usuario.setNombre(dataFormatter.formatCellValue(row.getCell(0)));
                usuario.setApellidoPaterno(dataFormatter.formatCellValue(row.getCell(1)));
                usuario.setApellidoMaterno(dataFormatter.formatCellValue(row.getCell(2)));

                Cell fechaCell = row.getCell(3);
                if (fechaCell != null) {

                    if (fechaCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(fechaCell)) {

                        usuario.setFechaNacimiento(fechaCell.getDateCellValue());

                    } else {
                        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
                        formato.setLenient(false);
                        usuario.setFechaNacimiento(formato.parse(fechaCell.toString()));
                    }
                }

                usuario.setUserName(row.getCell(4).toString());
                usuario.setEmail(row.getCell(5).toString());
                usuario.setPassword(row.getCell(6).toString());
                usuario.setSexo(row.getCell(7).toString());
                usuario.setTelefono(dataFormatter.formatCellValue(row.getCell(8)));
                usuario.setCelular(dataFormatter.formatCellValue(row.getCell(9)));
                usuario.setCURP(row.getCell(10).toString());

                Cell rolCell = row.getCell(11);
                if (rolCell != null) {
                    Rol rol = new Rol();
                    rol.setIdRol(Integer.parseInt(dataFormatter.formatCellValue(rolCell)));
                    usuario.setRol(rol);
                }

                Direccion direccion = new Direccion();
                direccion.setCalle(row.getCell(12).toString());
                direccion.setNumeroInterior(dataFormatter.formatCellValue(row.getCell(13)));
                direccion.setNumeroExterior(dataFormatter.formatCellValue(row.getCell(14)));

                Cell coloniaCell = row.getCell(15);
                if (coloniaCell != null) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(Integer.parseInt(dataFormatter.formatCellValue(coloniaCell)));
                    direccion.setColonia(colonia);
                }

                direccion.setUsuario(usuario);
                usuario.getDirecciones().add(direccion);

                usuarios.add(usuario);
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        List<ErroresArchivo> errores = new ArrayList<>();
        int fila = 1;

        for (Usuario usuario : usuarios) {
            BindingResult bindingResult = validationService.ValidateObject(usuario);

            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    ErroresArchivo erroresArchivo = new ErroresArchivo();
                    erroresArchivo.setFila(fila);
                    erroresArchivo.setDato(fieldError.getField()); //es el nombre del campo
                    erroresArchivo.setDescripcion(fieldError.getDefaultMessage());

                    errores.add(erroresArchivo);
                }
            }
            fila++;
        }

        return errores;
    }

    /*--------------- Ingresar datos del archivo ------------------*/
    @GetMapping("/cargamasiva/procesar/{uuidCarga}")
    public String ProcesarCargaMasiva(
            @PathVariable String uuidCarga,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        String rutaArchivo = (String) session.getAttribute("ruta_" + uuidCarga);
        String tipoArchivo = (String) session.getAttribute("tipo_" + uuidCarga);

        if (rutaArchivo == null || tipoArchivo == null) {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Sesión expirada");
            redirectAttributes.addFlashAttribute("text", "La carga ya no está disponible.");
            return "redirect:/usuario/cargamasiva";
        }

        List<Usuario> usuarios;

        if (tipoArchivo.equals("txt")) {
            usuarios = LecturaArchivoTxt(new File(rutaArchivo));
        } else {
            usuarios = LecturaArchivoExcel(new File(rutaArchivo));
        }

        Result result = usuarioDAOImplementation.UsuarioDireccionAddAll(usuarios);

        session.removeAttribute("ruta_" + uuidCarga);
        session.removeAttribute("tipo_" + uuidCarga);

        if (result.correct) {
            redirectAttributes.addFlashAttribute("icon", "success");
            redirectAttributes.addFlashAttribute("title", "Carga completada");
            redirectAttributes.addFlashAttribute("text", "Los registros fueron insertados correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("icon", "error");
            redirectAttributes.addFlashAttribute("title", "Error al insertar");
            redirectAttributes.addFlashAttribute("text", result.errorMessage);
        }

        return "redirect:/usuario";
    }

    
    
    /* --------------- MODIFICAR BOTON STATUS ---------------*/
    @PostMapping("/statusUpate")
    public String UpdateStatus(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes){
        
        Result result = usuarioDAOImplementation.UsuarioStatusUpdateSP(usuario);

        return "usuario";
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
