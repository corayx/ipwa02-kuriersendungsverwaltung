package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Shipment
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Enumerated(EnumType.STRING)
	private Status status;
	private int length;
	private int width;
	private int height;
	private int weight;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipient_id")
	private Recipient recipient;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="shipment")
	private List<Stamp> stamps = new ArrayList<>();

	public void addStamp(Stamp stamp) {
		this.stamps.add(stamp);
		stamp.setShipment(this);
	}

	public Shipment()
	{
		this(Status.CREATED);
	}

	public Shipment(Status status)
	{
		this.status = status;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public Recipient getRecipient()
	{
		return recipient;
	}

	public void setRecipient(Recipient recipient)
	{
		this.recipient = recipient;
	}

	public List<Stamp> getStamps()
	{
		return stamps;
	}

	public void setStamps(List<Stamp> stamps)
	{
		this.stamps = stamps;
	}


	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	/*@Override
	public String toString()
	{
		return "Shipment{" +
				"id=" + id +
				", status=" + status +
				", recipient=" + recipient +
				", user=" + user +
				", stamps=" + stamps +
				'}';
	}*/

}
