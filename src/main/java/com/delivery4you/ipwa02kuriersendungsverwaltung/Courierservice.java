package com.delivery4you.ipwa02kuriersendungsverwaltung;

import com.delivery4you.ipwa02kuriersendungsverwaltung.dao.ShipmentDAO;
import com.delivery4you.ipwa02kuriersendungsverwaltung.dao.StampDAO;
import com.delivery4you.ipwa02kuriersendungsverwaltung.dao.UserDAO;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class Courierservice
{

    private static final Courierservice instance = new Courierservice();
    private final double bulkySurcharge = 6.0;
    private boolean initialized = false;

    private final ShipmentDAO shipmentDAO = new ShipmentDAO();
    private final UserDAO userDao = new UserDAO();
    private final StampDAO stampDAO = new StampDAO();

    public Courierservice()
    {
        this.init();
    }

    /**  
     * Returns the static instance of {@code Courierservice}.
     *
     * @return
     */
    public static Courierservice getInstance()
    {
        return instance;
    }

	/**  
	 * Checks if the applications has already been initialized. If not, it will do so.
	 */
	public void init()
	{
		if (!this.initialized) {
			System.out.println("======> init()");
			this.createExampleData(); // comment if no example data is to be created
			this.initialized = true;
		}

	}


    /**  
     * Retrieves all Shipments.
     *
     * @return a list of Shipments
     */
    public List<Shipment> getShipments()
    {
        return shipmentDAO.findAll();
    }

    /** 
     * Retrieves the Shipments with the given Status.
     *
     * @param status
     * @return a list of Shipments
     */
    public List<Shipment> getShipments(Status status)
    {
        return shipmentDAO.findByStatus(status);
    }

    /** 
     * Retrieves the Shipments that have the Status "READY_FOR_COLLECTION".
     *
     * @return a list of Shipments
     */
    public List<Shipment> getCollectableShipments()
    {
        return this.getShipments(Status.READY_FOR_COLLECTION);
    }


    /**
     * Returns all available Statuses.
     *
     * @return array of all Status
     */
    public Status[] getStatuses()
    {
        return Status.values();
    }

    /**  
     * Saves the
     *
     * @param shipment
     * @return the persisted Shipmentg
     */
    public Shipment saveShipment(Shipment shipment)
    {
        return shipmentDAO.save(shipment);
    }

    /**  
     * Saves the given Stamp by calling its DAO.
     *
     * @param stamp
     * @return the persisted Stamp
     */
    public Stamp saveStamp(Stamp stamp)
    {
        return stampDAO.save(stamp);
    }


    /**  
     * Determines if the price of a Letter, Basic Parcel or bulky Parcel should be calculated.
     * Sets the this.price accordingly.
     */
    public double calculatePrice(Shipment shipment)
    {
        if (shipment.getClass().getSimpleName().equals("Letter"))
            return this.calculateLetterPrice(shipment);
        else {
            if (((Parcel) shipment).isBulkygoods())
                return this.calculateParcelBulkyPrice(this.calculateParcelBasicPrice(shipment));
            return this.calculateParcelBasicPrice(shipment);

        }

    }


    /**  
     * Takes the current price of the shipment and adds the surcharge for bulky goods.
     *
     * @return the final price of the shipment
     */
    private double calculateParcelBulkyPrice(double basicPrice)
    {
        return basicPrice + this.bulkySurcharge;
    }

    /**  
     * Determines - based on the Letter's measurements - which ShipmentType applies and therefor how much the Letter costs.
     *
     * @return price of the Letter
     */
    private double calculateLetterPrice(Shipment shipment)
    {
        double length = shipment.getLength();
        double width = shipment.getWidth();
        double height = shipment.getHeight();
        double weight = shipment.getWeight();

        boolean isStandard = (length <= 235 && width <= 125 && height <= 5 && weight <= 20);
        boolean isKompakt = (length <= 235 && width <= 125 && height <= 10 && weight <= 50);
        boolean isGross = (length <= 353 && width <= 250 && height <= 20 && weight <= 500);
        boolean isMaxi = (length <= 353 && width <= 250 && height <= 50 && weight <= 1000);

        if (isStandard) {
            return ShipmentType.STANDARD.price;
        } else if (isKompakt) {
            return ShipmentType.KOMPAKT.price;
        } else if (isGross) {
            return ShipmentType.GROSS.price;
        } else if (isMaxi) {
            return ShipmentType.MAXI.price;
        }
        return this.calculateParcelBasicPrice(shipment);

    }

    /**  
     * Determines - based on the Parcel's measurements - which ShipmentType applies and therefor how much the Parcel costs.
     *
     * @return price of the Parcel or -1 if the Parcel is too large to be shipped.
     */
    private double calculateParcelBasicPrice(Shipment parcel)
    {
        double length = parcel.getLength();
        double width = parcel.getWidth();
        double height = parcel.getHeight();
        double weight = parcel.getWeight();

        boolean isPaketS = (length <= 350 && width <= 250 && height <= 100 && weight <= 1000);
        boolean isPaketM = (length <= 1200 && width <= 600 && height <= 600 && weight <= 5000);
        boolean isPaketL = (length <= 1200 && width <= 600 && height <= 600 && weight <= 10000);
        boolean isPaketXL = (length <= 1200 && width <= 600 && height <= 600 && weight <= 25000);

        if (isPaketS) {
            return ShipmentType.PAKET_S.price;
        } else if (isPaketM) {
            return ShipmentType.PAKET_M.price;
        } else if (isPaketL) {
            return ShipmentType.PAKET_L.price;
        } else if (isPaketXL) {
            return ShipmentType.PAKET_XL.price;
        }
        return -1; // too large
    }


    /**  
     * Calls methods for creating example data fpr the models User, Letter, Parcel.
     */
    private void createExampleData()
    {
        this.createUsers();

        for (int i = 0; i < 1; i++) {

            this.createExampleLetter(Status.READY_FOR_COLLECTION);
            this.createExampleLetter(Status.OUT_FOR_DELIVERY);
            this.createExampleLetter(Status.DELIVERED);
            this.createExampleLetter(Status.FAILED_ATTEMPT);

            this.createExampleParcel(Status.READY_FOR_COLLECTION);
            this.createExampleParcel(Status.DELIVERED);
            this.createExampleParcel(Status.FAILED_ATTEMPT);
            this.createExampleParcel(Status.READY_FOR_COLLECTION, true);
            this.createExampleParcel(Status.OUT_FOR_DELIVERY, true);
        }
    }

    /**  
     * Creates a test user for each role. Currently "CLIENTuser", "COURIERuser" and "MANAGERuser".
     */
    private void createUsers()
    {
        for (Role role : Role.values()) {
            User newUser = new User(role, role + "user");
            newUser.setPassword("password");
            userDao.merge(newUser);
        }
    }

    /**  
     * Creates an example Letter with valid measurements including a stamp and the test user "CLIENTuser" as owner.
     */
    private void createExampleLetter(Status status)
    {
        Letter letter = new Letter();
        letter.setLength(235);
        letter.setWidth(125);
        letter.setHeight(4);
        letter.setWeight(15);
        letter.setRecipient(this.getExampleRecipient());
        letter.addStamp(new Stamp(StampType.XL, this.userDao.findByName("CLIENTuser")));
        letter.setStatus(status);
        letter.setUser(this.userDao.findByName("CLIENTuser"));

        shipmentDAO.save(letter);
    }

	/**  
	 * Creates an example Parcel (bulkygoods=false) with valid measurements including a stamp and the test user "CLIENTuser" as owner.
	 * @param status
	 */
    private void createExampleParcel(Status status)
    {
        createExampleParcel(status, false);
    }

    /**  
     * Creates an example Parcel with valid measurements including a stamp and the test user "CLIENTuser" as owner.
     *
     * @param bulkygoods
     */
    private void createExampleParcel(Status status, boolean bulkygoods)
    {
        Parcel regular = new Parcel();
        regular.setLength(350);
        regular.setWidth(250);
        regular.setHeight(200);
        regular.setWeight(850);
        regular.setRecipient(this.getExampleRecipient());
        regular.addStamp(new Stamp(StampType.L, this.userDao.findByName("CLIENTuser")));
        regular.setStatus(Status.READY_FOR_COLLECTION);
        regular.setUser(this.userDao.findByName("CLIENTuser"));

        if (bulkygoods) regular.setBulkygoods(true);

        shipmentDAO.save(regular);
    }

    /**  
     * Create an example Recipient which can be used for example Shipments.
     *
     * @return
     */
    private Recipient getExampleRecipient()
    {
        Recipient recipient = new Recipient();

        recipient.setName("Kuchen");
        recipient.setFirstname("Käse");
        recipient.setZip("12345");
        recipient.setCity("Kuchenhausen");
        recipient.setStreetname("Gebäckstraße");
        recipient.setStreetnr("12a");

        return recipient;
    }

    /**
     * Returns the values of all StampStypes
     *
     * @return Array of all StampTypes
     */
    public StampType[] getStampTypes()
    {
        return StampType.values();
    }


}
