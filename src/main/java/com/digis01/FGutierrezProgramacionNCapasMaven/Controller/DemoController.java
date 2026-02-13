package com.digis01.FGutierrezProgramacionNCapasMaven.Controller;

import com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario;
import jakarta.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("saludo")
    public String Test(@RequestParam String nombre, Model model) {
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }

    @GetMapping("calculadora")
    public String calcular(@RequestParam int numeroUno,
            @RequestParam int numeroDos, Model model) {
        model.addAttribute("n1", numeroUno);
        model.addAttribute("n2", numeroDos);
        model.addAttribute("suma", numeroUno + numeroDos);
        model.addAttribute("resta", numeroUno - numeroDos);
        model.addAttribute("mutiplicacion", numeroUno * numeroDos);

        if (numeroDos != 0) {
            model.addAttribute("division", (double) numeroUno / numeroDos);
        } else {
            model.addAttribute("division", "Error: no se puede dividir entre 0");
        }

        return "resultado";
    }

    @GetMapping("factorial")
    public String calcularFactorial(@RequestParam int numero, Model model) {
        long resultado = 1;

        for (int i = 1; i <= numero; i++) {
            resultado *= i;
        }

        model.addAttribute("numeroIngresado", numero);
        model.addAttribute("resultadoFinal", resultado);

        return "facto";
    }

    @GetMapping("tabla")
    public String datos(Model model) {
        return "GetAll";
    }

}
