package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

import jakarta.persistence.Entity;

@Entity
public class Parcel extends Shipment
{
	private boolean bulkygoods;

	public Parcel()
	{
		super();
	}

	public Parcel(boolean bulkygoods)
	{
		super();
		this.bulkygoods = bulkygoods;
	}

	public boolean isBulkygoods()
	{
		return bulkygoods;
	}

	public void setBulkygoods(boolean bulkygoods)
	{
		this.bulkygoods = bulkygoods;
	}

	@Override
	public String toString()
	{
		return "Parcel{" + "bulkygoods=" + bulkygoods + "} " + super.toString();
	}
}
