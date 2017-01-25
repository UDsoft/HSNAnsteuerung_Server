/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsnansteuerung.MqttClient;

import Model.Data.RecieveDataModel;
import Model.Logic.HSNAnsteuerung;
import com.google.gson.Gson;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.udsoft.GpioManager.HardwarePWM;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
/**
 *
 * @author sdarwin
 */
public class MqttClient implements MqttCallback{

    private final static Logger LOGGER = Logger.getLogger(MqttClient.class.getName());

    HSNAnsteuerung hsnAnsteuerung = new HSNAnsteuerung();

    MqttAsyncClient mqttClient;
    int qos;
    String ipAddress;
    int port;
    String clientID;
    MemoryPersistence persistence = new MemoryPersistence();
    
    public final GpioController gpioCnController  = GpioFactory.getInstance();

    public MqttClient(String ipAddress,int port,String clientID,int qos){
        this.ipAddress =  "tcp://"+ipAddress+":";
        this.qos = qos;
        this.port = port;
        this.clientID = clientID;
    }

    public boolean isConnected(){
        return mqttClient.isConnected();
    }

    public void reconnect(){
        if(!mqttClient.isConnected()){
            LOGGER.log(Level.SEVERE,"Client is not Connected ");
            try {
                mqttClient.reconnect();

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else{
            LOGGER.log(Level.INFO,"Mqtt Client is connected");
        }
    }

    public boolean disconnected(){
        try {
            mqttClient.disconnect();
            LOGGER.log(Level.INFO,"Raspberry Pi Master Controller Disconnected with the Broker");
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,e.getMessage().toString());
            LOGGER.log(Level.WARNING,e.getCause().toString());
            LOGGER.log(Level.WARNING,e.getLocalizedMessage().toString());

        }

        return false;
    }

    public MqttAsyncClient mqttClient(){
        return mqttClient;
    }

    public void connect(){

        try {
            String broker = ipAddress + port;

            mqttClient = new MqttAsyncClient(broker,clientID,persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            mqttClient.setCallback(this);
            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connectOptions);
            Thread.sleep(1000);
            if(mqttClient.isConnected()){
                System.out.println("Connected to broker: " + broker);

            }else{
                reconnect();
                LOGGER.log(Level.SEVERE,"Error to connect to BROKER "+
                        MqttClient.class.getName()+"--- method connect");
            }
        } catch (MqttException e) {
            e.printStackTrace();
            if(e instanceof MqttException){
                System.out.println("reason" + ((MqttException)e).getReasonCode());
            }
            LOGGER.log(Level.WARNING,"Msg " + e.getMessage());
            LOGGER.log(Level.WARNING,"Loc " + e.getLocalizedMessage());
            LOGGER.log(Level.WARNING,"Cause " + e.getCause());
            LOGGER.log(Level.WARNING,"Exception " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void subscribe(String topic,int qos){
        if(mqttClient.isConnected()){
            try {
                mqttClient.subscribe(topic,qos);
                LOGGER.log(Level.INFO,"The Topic " + topic + " is Subscribed.");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else{
            reconnect();
            System.out.println("Reconnecting to Broker....");
        }
    }

    public void publish(String topic,String message,int qos){
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        if(mqttClient.isConnected()){
            try {
                mqttClient.publish(topic,mqttMessage);
                LOGGER.log(Level.INFO,message + "is published to Broker");
            } catch (MqttException e) {
                e.printStackTrace();
                LOGGER.log(Level.SEVERE,e.toString(),e);
            }
        }

    }



    @Override
    public void connectionLost(Throwable cause) {

        System.out.println("Connection Lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        
        LOGGER.log(Level.INFO,message.toString()+"- Message Recieved from topic "+ topic);
        Gson gson = new Gson();
        RecieveDataModel data = new RecieveDataModel();
        data = gson.fromJson(message.toString(), RecieveDataModel.class);
        int pinNumber = Integer.parseInt(data.getPinNumber());
        
        Pin raspPin = RaspiPin.getPinByAddress(pinNumber);
        
        int pwmValue = Integer.parseInt(data.getValue());
        
        
        
        HardwarePWM outputHardwarePWM = 
                new HardwarePWM(gpioCnController,raspPin,pwmValue);
        
        

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}