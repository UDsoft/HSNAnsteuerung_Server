/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Data;

/**
 *
 * @author sdarwin
 */
public class LogicOutput {
    
    private int gpioPinNummber;
    private String name;
    private String unit;
    private String description;


    public LogicOutput(int gpioPinNummber, String name, String unit, String description) {
        this.gpioPinNummber = gpioPinNummber;
        this.name = name;
        this.unit = unit;
        this.description = description;
    }

    public int getGpioPinNummber() {
        return gpioPinNummber;
    }

    public void setGpioPinNummber(int gpioPinNummber) {
        this.gpioPinNummber = gpioPinNummber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
