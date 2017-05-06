package com.ivan.app;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Ivan on 07.05.2017.
 */
public class App {
    private static final String connectionString = "{yourhubconnectionstring}";
    private static final String deviceId = "myFirstJavaDevice";

    public static void main(String[] args) throws IOException, URISyntaxException, Exception {
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
    }

}
