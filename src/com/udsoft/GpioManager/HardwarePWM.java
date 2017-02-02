package com.udsoft.GpioManager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;

/**
 * Created by sdarwin on 17/01/17.
 */
public class HardwarePWM {

    private final GpioPinPwmOutput pwm;

    public HardwarePWM(GpioController gpioController, Pin pin) {
        this.pwm = gpioController.provisionPwmOutputPin(pin);
    }

    public void setPwmValue(int value){
        pwm.setPwm(value);
    }
    
    private void GpioPinRules(){
        
    }
}
