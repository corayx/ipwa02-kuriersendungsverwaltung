package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class User
{
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Stamp> ownedStamps;
    @OneToMany(mappedBy = "user")
    private List<Shipment> comissionedShipments;

    public User()
    {
    }

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

    public User(Role role, String name)
    {
        this.role = role;
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Stamp> getOwnedStamps()
    {
        return ownedStamps;
    }

    public void setOwnedStamps(List<Stamp> ownedStamps)
    {
        this.ownedStamps = ownedStamps;
    }

    public List<Shipment> getComissionedShipments()
    {
        return comissionedShipments;
    }

    public void setComissionedShipments(List<Shipment> comissionedShipments)
    {
        this.comissionedShipments = comissionedShipments;
    }


}
