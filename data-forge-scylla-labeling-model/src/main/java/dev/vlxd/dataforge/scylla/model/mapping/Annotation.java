package dev.vlxd.dataforge.scylla.model.mapping;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "annotation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Annotation {
    @XmlAttribute
    private String verified;
    private String folder;
    private String filename;
    private String path;
    private Source source;
    private Size size;
    private Integer segmented;
    @XmlElement(name = "object")
    private List<LayoutObject> objects = new ArrayList<>();
}
