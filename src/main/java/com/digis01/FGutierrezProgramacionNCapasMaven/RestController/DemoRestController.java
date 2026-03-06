package com.digis01.FGutierrezProgramacionNCapasMaven.RestController;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Libros;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pelicula;
import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {

    @GetMapping
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("saludo/{nombre}")
    public String Saludo(@PathVariable("nombre") String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("saludo")
    public String Saludo2(@RequestParam("nombre") String nombre) {
        return "Hola " + nombre;
    }

    @GetMapping("suma")
    public int suma(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        return numeroUno + numeroDos;
    }

    @PostMapping("sumas")
    public int sumas(@RequestBody List<Integer> numeros) {

        int suma = 0;

        for (Integer numero : numeros) {
            suma += numero;
        }

        return suma;
    }

    @GetMapping("usuarios")
    public List<Usuario> obtenerUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        Usuario u1 = new Usuario();
        u1.setIdUsuario(1);
        u1.setNombre("Juan");
        u1.setApellidoPaterno("Perez");
        u1.setEmail("juan@mail.com");

        Usuario u2 = new Usuario();
        u2.setIdUsuario(2);
        u2.setNombre("Ana");
        u2.setApellidoPaterno("Lopez");
        u2.setEmail("ana@mail.com");

        usuarios.add(u1);
        usuarios.add(u2);

        return usuarios;
    }
    
    @PostMapping("libro")
    public Libros recibirLibros(@RequestBody Libros libros){
        return libros;
    }
    
    @PostMapping("catalogoPeliculas")
    public String catalogoPeliculas(@RequestBody List<Pelicula> peliculas){
        for(Pelicula pelicula : peliculas){
            System.out.println(pelicula.getTitulo());
        }
        return "Catalogo recibido";
    }
}
