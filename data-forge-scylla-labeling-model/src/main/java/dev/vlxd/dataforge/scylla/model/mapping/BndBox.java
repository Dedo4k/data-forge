package dev.vlxd.dataforge.scylla.model.mapping;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class BndBox {
    @XmlElement(name = "xmin")
    private Integer xMin;
    @XmlElement(name = "ymin")
    private Integer yMin;
    @XmlElement(name = "xmax")
    private Integer xMax;
    @XmlElement(name = "ymax")
    private Integer yMax;
}