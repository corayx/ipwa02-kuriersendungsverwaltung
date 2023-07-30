package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

import jakarta.persistence.*;

@Entity
public class Stamp
{
	@Id
	@GeneratedValue
	private int id;
	private StampType type;
	@ManyToOne
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;

	public Stamp()
	{
	}

	public Stamp(StampType type)
	{
		this.type = type;
	}

	public Stamp(StampType type, User user)
	{
		this.type = type;
		this.owner = user;
	}

	public int getId()
	{
		return id;
	}

	public StampType getValue()
	{
		return type;
	}

	public void setValue(StampType value)
	{
		this.type = type;
	}

	public Shipment getShipment()
	{
		return shipment;
	}

	public void setShipment(Shipment shipment)
	{
		this.shipment = shipment;
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}
}
