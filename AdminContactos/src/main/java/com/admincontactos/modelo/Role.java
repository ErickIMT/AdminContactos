package com.admincontactos.modelo;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String name;

    public Role(){

    }

    public Role(Long id){
        this.id=id;

    }
    public Role(Long id,String name){
        this.id=id;
        this.name=name;

    }
    public Role(String name){
        this.name=name;

    }

    @Override
    public String toString() {
        return this.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}