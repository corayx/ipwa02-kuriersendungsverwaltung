package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Recipient
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String firstname;
	private String streetname;
	private String streetnr;
	private String zip;
	private String city;
	@OneToMany(mappedBy = "recipient")
	private List<Shipment> shipments;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getStreetname()
	{
		return streetname;
	}

	public void setStreetname(String streetname)
	{
		this.streetname = streetname;
	}

	public String getStreetnr()
	{
		return streetnr;
	}

	public void setStreetnr(String streetnr)
	{
		this.streetnr = streetnr;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public List<Shipment> getShipments()
	{
		return shipments;
	}

	public void setShipments(List<Shipment> shipments)
	{
		this.shipments = shipments;
	}

	@Override
	public String toString()
	{
		return "Recipient{" + "id=" + id + ", name='" + name + '\'' + ", firstname='" + firstname + '\'' + ", streetname='" + streetname + '\'' + ", streetnr='" + streetnr + '\'' + ", zip='" + zip + '\'' + ", city='" + city + '\'' + ", shipments=" + shipments + '}';
	}
}
