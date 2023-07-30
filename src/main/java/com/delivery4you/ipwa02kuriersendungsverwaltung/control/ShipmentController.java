package com.delivery4you.ipwa02kuriersendungsverwaltung.control;

import com.delivery4you.ipwa02kuriersendungsverwaltung.Courierservice;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.*;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class ShipmentController implements Serializable
{
    private Shipment shipment = null;
    private String type;
    private double price = 0;

    /**  
     * Creates and returns a new instance of {@code Shipment} depending on the given type.
     * @param type the {@code ShipmentType}, either "letter" or "parcel"
     * @return new Shipment
     */
    public Shipment getNewShipment(String type)
    {
        if (type.equals("letter")) {
            this.shipment = new Letter();
        } else if (type.equals("parcel")) {
            this.shipment = new Parcel();
        }
        this.shipment.setRecipient(new Recipient());
        return this.shipment;
    }

    /**  
     * Resets the current {@code Shipment} and directs to the {@code create-shipment.xhtml}
     * @return
     */
    public String add()
    {
        this.reset();
        return "create-shipment";
    }

    public String all() {
        return "shipments";
    }

    /**  
     *
     * @return
     */
    public String collectable() {
        return "collectable-shipments";
    }

    public String edit(Shipment shipment)
    {
        this.reset();
        this.shipment = shipment;
        return "edit-shipment";
    }

    public String saveEdit()
    {
        Courierservice.getInstance().saveShipment(this.shipment);
        FacesContext.getCurrentInstance().addMessage("edit",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolgreich geändert", "Der Status der Sendung #" + this.shipment.getId() + " wurde erfolgreich angepasst."));
        return "collectable-shipments";
    }

    private void reset() {
        this.shipment = null;
        this.type = null;
    }


    /**  
     * Sets a new Shipment when the user selects a new ShipmentType (Letter, Parcel) during the creation of a shipment.
     */
    public void typeChanged()
    {
        this.getNewShipment(this.type);

    }


    /**  
     *
     * @return
     */
    public String createShipment()
    {
        this.setPrice(Courierservice.getInstance().calculatePrice(this.shipment));
        if (this.price == -1) {
            FacesContext.getCurrentInstance().addMessage("size",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Maße", "Die Sendung entspricht nicht den gültigen Maßen bzw. das Gewicht übersteigt den Höchstwert."));
        } else {
            this.shipment.setUser(LoginController.getLoggedinUser());

            Shipment saved = Courierservice.getInstance().saveShipment(this.shipment);

            this.shipment.setId(saved.getId()); // necessary for the following frankShipment save (otherwise a new record will be added)
            this.shipment.getRecipient().setId(saved.getRecipient().getId());

            return "frank-shipment";
        }
        return null;
    }

    /**  
     *
     */
    public void finishShipment() {
        this.shipment.setStatus(Status.READY_FOR_COLLECTION);

        Courierservice.getInstance().saveShipment(this.shipment);

        FacesContext.getCurrentInstance().addMessage("finishMessage",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sendung erfasst", "Die Sendung wurde erfolgreich erfasst und frankiert. Sie wird so schnell wie möglich an einen Kurier übergeben."));

    }


    public Shipment getShipment()
    {
        return shipment;
    }

    public void setShipment(Shipment shipment)
    {
        this.shipment = shipment;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

}
