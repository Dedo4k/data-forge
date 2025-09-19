package dev.vlxd.dataforge.scylla.model.mapping;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Size {
    private Integer width;
    private Integer height;
    private Integer depth;
}
