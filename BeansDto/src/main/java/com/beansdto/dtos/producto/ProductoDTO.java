package com.beansdto.dtos.producto;

<<<<<<< HEAD
public class ProductoDTO {
}
=======
import com.beansdto.dtos.base.BaseDTO;
import lombok.Data;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "producto")
@Data
public class ProductoDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private Integer Id_Categoria;
    private Integer Id_Usuario;
    private String nombre;
    private String descripcion;
    private double precio;
}

>>>>>>> 34b4adb00779896bc30d3e229191ddfc5bbc06b0
