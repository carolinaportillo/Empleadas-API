package ar.com.ada.api.empleadas.services;

import ar.com.ada.api.empleadas.entities.Categoria;
import ar.com.ada.api.empleadas.repos.CategoriaRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public void crearCategoria(Categoria categoria){
        repo.save(categoria);
    }

    public List<Categoria> traerCategorias(){
        return repo.findAll();
    }
    

    
    public Categoria buscarCategoria(Integer categoriaId) {

        Optional<Categoria> resultado = repo.findById(categoriaId);
        Categoria categoria = null;

        if (resultado.isPresent())
            categoria = resultado.get();

        return categoria;

    }


    public Categoria findCategoria(String categoriaNombre) {
        return repo.findByNombre(categoriaNombre);
    }


    public void eliminarCategoria(Integer categoriaId) {
        repo.deleteById(categoriaId);
    }

    
}
