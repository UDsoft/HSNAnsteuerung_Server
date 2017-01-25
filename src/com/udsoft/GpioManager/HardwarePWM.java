package com.udsoft.GpioManager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;

/**
 * Created by sdarwin on 17/01/17.
 */
public class HardwarePWM {

    private final GpioPinPwmOutput pwm;
    private final int value;

    public HardwarePWM(GpioController gpioController, Pin pin,int value) {
        this.pwm = gpioController.provisionPwmOutputPin(pin);
        this.value = value;
        
        setPwmValue(value);
    }

    private void setPwmValue(int value){
        pwm.setPwm(value);
    }
    
    private void GpioPinRules(){
        
    }
}
