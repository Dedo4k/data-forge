package dev.vlxd.dataforge.scylla.model.mapping;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Object")
public class LayoutObject {
    private String name;
    private String pose;
    private Integer truncated;
    private Integer difficult;
    private BndBox bndbox;
}