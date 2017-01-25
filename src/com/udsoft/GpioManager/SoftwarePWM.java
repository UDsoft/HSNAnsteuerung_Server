package com.udsoft.GpioManager;


import com.pi4j.wiringpi.SoftPwm;

/**
 * Created by sdarwin on 17/01/17.
 */
public class SoftwarePWM {

    private final int pin;
    private final int min;
    private final int max;

    public SoftwarePWM(int pin, int min, int max) {
        this.pin = pin;
        this.min = min;
        this.max = max;
        SoftPwm.softPwmCreate(pin,min,max);
    }

    public void setValue(int value) {
        SoftPwm.softPwmWrite(pin,value);
    }
}

