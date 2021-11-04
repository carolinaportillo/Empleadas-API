package ar.com.ada.api.empleadas.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.empleadas.entities.Categoria;
import ar.com.ada.api.empleadas.entities.Empleada;
import ar.com.ada.api.empleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.empleadas.models.request.InfoEmpleadaNueva;
import ar.com.ada.api.empleadas.models.request.SueldoNuevoEmpleada;
import ar.com.ada.api.empleadas.models.response.GenericResponse;
import ar.com.ada.api.empleadas.services.CategoriaService;
import ar.com.ada.api.empleadas.services.EmpleadaService;

@RestController
public class EmpleadaController {

    @Autowired
    private EmpleadaService service;

    @Autowired
    CategoriaService categoriaService;


    /*POST-> web method que permite crear una empleada nueva, con los datos correspondientes
    se le asigna una categoria/puesto a desempeñar, se la da de alta y se le setea el estado a empleado activo*/
    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleada(@RequestBody  InfoEmpleadaNueva empleadaInfo) {
        GenericResponse respuesta = new GenericResponse();

        Empleada empleada = new Empleada();
        empleada.setNombre(empleadaInfo.nombre);
        empleada.setEdad(empleadaInfo.edad);
        empleada.setSueldo(empleadaInfo.sueldo);
        empleada.setFechaAlta(new Date());
        
        Categoria categoria = categoriaService.buscarCategoria(empleadaInfo.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstado(EstadoEmpleadaEnum.ACTIVO);

        service.crearEmpleada(empleada);
        respuesta.isOk = true;
        respuesta.id = empleada.getEmpleadaId();
        respuesta.message = "La empleada fue creada con exito";
        return ResponseEntity.ok(respuesta);

    }


    //GET-> devuelve la lista de empleadxs existentes
    @GetMapping("/empleados")
    public ResponseEntity<List<Empleada>> traerEmpleadas() {
        final List<Empleada> lista = service.traerEmpleadas();

        return ResponseEntity.ok(lista);
    }


    // GET-> busca y obtiene una empleada por numero de id
    @GetMapping("/empleados/{id}")
    public ResponseEntity<?> getEmpleadaPorId(@PathVariable Integer id){
        Empleada empleada = service.buscarEmpleada(id);
        if (empleada == null){
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(empleada);
    }



    /*Detele/empleados/{id} --> Da de baja un empleado poniendo el campo estado en "baja"
     y la fecha de baja que sea el dia actual.*/
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<?> bajaEmpleada(@PathVariable Integer id){

        service.bajaEmpleadaPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "La empleada fue dada de baja con exito";

        return ResponseEntity.ok(respuesta);

    }


    //Get /empleados/categorias/{catId} --> Obtiene la lista de empleados de una categoria.
    @GetMapping("/empleados/categorias/{catId}")
    public ResponseEntity<List<Empleada>> obtenerEmpleadasPorCategoria(@PathVariable Integer catId){
        
        List<Empleada> empleadas = service.traerEmpleadaPorCategoria(catId);
        return ResponseEntity.ok(empleadas);
    }


    //PUT-> busca a un empleado por id, modifica/actualiza su sueldo y lo guarda en la base de datos.
    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> modificarSueldo(@PathVariable Integer id, @RequestBody SueldoNuevoEmpleada sueldoNuevoInfo){

    
        Empleada empleada = service.buscarEmpleada(id);

        empleada.setSueldo(sueldoNuevoInfo.sueldoNuevo);
        
        service.guardar(empleada);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "Sueldo actualizado";

        return ResponseEntity.ok(respuesta);
    }

    


























    
}
