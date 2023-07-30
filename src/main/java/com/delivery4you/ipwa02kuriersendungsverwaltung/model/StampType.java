package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

public enum StampType
{
	S(0.1), M(0.3), L(1.0), XL(5.0);

	public final double value;

	StampType(double value)
	{
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
