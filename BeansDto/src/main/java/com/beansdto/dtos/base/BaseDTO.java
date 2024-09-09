package com.beansdto.dtos.base;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlElement;

public abstract class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer _id;

    @XmlElement
    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

}
