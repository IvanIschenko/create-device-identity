package com.ivan.app;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ivan on 07.05.2017.
 */
public class App {
    private static final String deviceId = "myJavaDevice";
    private static String connectionString;

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        InputStream inputStream = null;

        try{
            inputStream = new FileInputStream("src/main/resources/config.properties");

            properties.load(inputStream);
            connectionString = properties.getProperty("ConnectionStringPrimaryKey");

            RegistryManager registryManager = RegistryManager.createFromConnectionString(connectionString);

            Device device = Device.createFromId(deviceId, null, null);
            try {
                device = registryManager.addDevice(device);
            } catch (IotHubException iote) {
                try {
                    device = registryManager.getDevice(deviceId);
                } catch (IotHubException iotf) {
                    iotf.printStackTrace();
                }
            }
            System.out.println("Device ID: " + device.getDeviceId());
            System.out.println("Device key: " + device.getPrimaryKey());
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
