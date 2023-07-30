package com.delivery4you.ipwa02kuriersendungsverwaltung.model;

public enum ShipmentType
{

    STANDARD("Standardbrief", 1.0), KOMPAKT("Kompaktbrief", 1.3), GROSS("Großbrief", 1.6),
    MAXI("Maxibrief", 2.0), PAKET_S("Paket S", 4.9), PAKET_M("Paket M", 6.9),
    PAKET_L("Paket L", 10.9), PAKET_XL("Paket XL", 15.9);

    public final String name;
    public final double price;

    private ShipmentType(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }
}
