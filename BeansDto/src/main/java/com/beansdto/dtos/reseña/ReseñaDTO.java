package com.beansdto.dtos.reseña;


import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;

@Data
public class ReseñaDTO extends BaseDTO {
    private Integer Id_Reseña;
    private Integer Id_Usuario;
    private Integer Id_Producto;
    private int calificacion;
    private String comentario;
    private String fecha;
}
