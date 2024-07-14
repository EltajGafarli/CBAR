package org.example.cbarcurrencyproject.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)

@Getter
@Setter
public class ValCurs {

    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlElement(name = "ValType")
    private List<ValType> valTypes;

}