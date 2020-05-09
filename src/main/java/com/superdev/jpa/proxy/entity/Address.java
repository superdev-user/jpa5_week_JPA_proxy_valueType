package com.superdev.jpa.proxy.entity;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Address implements Cloneable{

    private String city;
    private String street;
    private String zipcode;

    @Builder
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }


    @Override
    public Address clone() throws CloneNotSupportedException{
        Address clone =  (Address) super.clone();
        return clone;
    }
}
