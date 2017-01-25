/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsnansteuerung;

import hsnansteuerung.MqttClient.MqttClient;
import java.util.Scanner;

/**
 *
 * @author sdarwin
 */
public class Main {
    
    public static void main(String[] args){
        
        String brokerIpAddres = "192.168.0.101";
        int port = 1883;
        String clientID = "HSNAnsteuerung";
        int qos = 1;
     
        System.out.println();
        System.out.println("Welcome to HSNAnsteuerung Raspberry Master");
        System.out.println();
        System.out.println();

        MqttClient mqttClient = new MqttClient(brokerIpAddres, port, clientID, qos);
        mqttClient.connect();

        String subTopic = "pinName/Value";

        mqttClient.subscribe(subTopic,qos);

        
    }
}
