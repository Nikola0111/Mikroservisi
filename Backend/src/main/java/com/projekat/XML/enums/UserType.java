package com.projekat.XML.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum UserType {
    @XmlEnumValue("0")
    ADMINISTRATOR,
    @XmlEnumValue("1")
    AGENT,
    @XmlEnumValue("2")
    ENDUSER
}
