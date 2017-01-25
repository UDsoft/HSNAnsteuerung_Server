/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Data;

import java.util.logging.Level;
import java.util.logging.Logger;
import sun.rmi.runtime.Log;

/**
 *
 * @author sdarwin
 */
public class RecieveDataModel {
    String pinName;
    String pinNumber;
    String value;

    private final static Logger LOGGER = Logger.getLogger(RecieveDataModel.class.getName());

    
    public RecieveDataModel() {
    }

    public RecieveDataModel(String pinName, String pinNumber, String value) {
        this.pinName = pinName;
        this.pinNumber = pinNumber;
        this.value = value;
    }

    
    

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
       LOGGER.log(Level.INFO, "Setting pinName :" + pinName);
        this.pinName = pinName;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        LOGGER.log(Level.INFO, "Setting pinNummer : "+ pinNumber);
        this.pinNumber = pinNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        LOGGER.log(Level.INFO, "Setting value : "+ value);
        this.value = value;
    }
    
}
