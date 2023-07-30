package com.delivery4you.ipwa02kuriersendungsverwaltung.control;

import com.delivery4you.ipwa02kuriersendungsverwaltung.Courierservice;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.Shipment;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.Stamp;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.StampType;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Named
@ViewScoped
public class StampController implements Serializable
{
    private Stamp stamp = null;
    private double stampSum = 0;
    private Map<StampType, Integer> stampSelection = new HashMap<>();

    public Stamp getNewStamp() {
        this.stamp = new Stamp();
        return this.stamp;
    }

    /**  
     *
     */
    public void stampChanged()
    {
        this.stampSum = 0;
        this.calculateStampSum();
    }

    /**  
     *
     * @param shipment {@code Shipment} to which the {@code Stamps} should be added
     * @param price price of the {@code Shipment}
     * @return
     */
    public String addStampsToShipment(Shipment shipment, double price)
    {
        // check if enough stamps were selected
        if (this.stampSum < price) {
            FacesContext.getCurrentInstance().addMessage("frankMessage",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Briefmarken reichen nicht aus", ""));
        } else {
            // add stamps to db and shipment
            for (Map.Entry<StampType, Integer> stampTypeIntegerEntry : stampSelection.entrySet()) {
                StampType stampTypeName = stampTypeIntegerEntry.getKey();
                Integer amount = stampTypeIntegerEntry.getValue();

                for (int i = 0; i < amount; i++) {
                    this.stamp = new Stamp(stampTypeName);
                    this.stamp.setOwner(LoginController.getLoggedinUser());
                    this.stamp.setShipment(shipment);

                    Courierservice.getInstance().saveStamp(this.stamp);
                }
            }
            return "finish-shipment";
        }
        return null;
    }

    /**  
     *
     */
    private void calculateStampSum()
    {
        for (Map.Entry<StampType, Integer> entry : stampSelection.entrySet()) {
            double singleStampPrice = entry.getKey().getValue();

            stampSum += (singleStampPrice * entry.getValue());
        }
    }

    public void prepareStampSelection() {
        for (StampType stamptype : StampType.values()) {
            this.stampSelection.put(stamptype, 0);
        }
    }

    public Map<StampType, Integer> getStampSelection()
    {
        return stampSelection;
    }

    public void setStampSelection(Map<StampType, Integer> stampSelection)
    {
        this.stampSelection = stampSelection;
    }



    public double getStampSum()
    {
        return stampSum;
    }

    public void setStampSum(double stampSum)
    {
        this.stampSum = stampSum;
    }
}
