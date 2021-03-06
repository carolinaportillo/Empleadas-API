package ar.com.ada.api.empleadas.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoria_id")
  private Integer categoriaId;

  private String nombre;

  @Column(name= "sueldo_base")
  private BigDecimal sueldoBase;


  @JsonIgnore
  @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Empleada> empleadas = new ArrayList<>();

  public Integer getCategoriaId(){
    return categoriaId;
  }

  public void setCategoriaId(Integer categoriaId){
    this.categoriaId = categoriaId;
  }

  public String getNombre(){
    return nombre;
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public BigDecimal getSueldoBase(){
    return sueldoBase;
  }

  public void setSueldoBase(BigDecimal sueldoBase){
    this.sueldoBase = sueldoBase;
  }

  public List<Empleada> getEmpleadas(){
    return empleadas;
  }

  public void setEmpleadas(List<Empleada> empleadas){
    this.empleadas = empleadas;
  }

  public void agregarEmpleada(Empleada empleada){ //metodo para establecer la RB con empleada.java
    this.empleadas.add(empleada);
  }


   




}
