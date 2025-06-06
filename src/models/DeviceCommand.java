package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceCommand {
    final private String name;
    private String deviceCommandDescription;
    Map<String, String> payload;
    /*
    * String Key = DeviceIndex.getDeviceIndexName for soft binding of object DeviceIndex
    * String Value = We cast the value to the string type since we don't know the type of data that will be used
    * */


    List<Device> devicesHolderList;

    public DeviceCommand(String name, Device deviceHolder) {
        this.name = name;
        this.deviceCommandDescription = "";
        this.payload = new HashMap<>();
        this.devicesHolderList = new ArrayList<>();
    }

    public JSONObject toJson() {
        JSONObject payloadJson = new JSONObject();
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            payloadJson.put(entry.getKey(), entry.getValue());
        }

        JSONObject deviceCommandJson = new JSONObject();
                deviceCommandJson.put("command", name);
                deviceCommandJson.put("deviceCommandDescription", deviceCommandDescription);
                deviceCommandJson.put("payload", payloadJson);

        return deviceCommandJson;
    }


    public boolean putPayload(String key, String value) {
        if (payload.containsKey(key)) {
            System.out.println(key + ": Payload with this key is already described in this device. Choose another name or delete in the device PAYLOAD dictionary\"");
            //We check but there is duplication of Payload commands

            return false;
        }
        for (Device device : devicesHolderList) {
            //We check that this DeviceIndex ist exists and supported by this device
            List<String> temp = new ArrayList<>();
            device.getDeviceIndexes().forEach(index -> {temp.add(index.getDeviceIndexName());});
            //Create a temporary array of DeviceIndex Names for testing
            if (!temp.contains(key)) {
                System.out.println(key + ": Payload with key is not supported by this device");
                return false;
            }
        }
        return payload.put(key, value) != null;
    }
    public boolean removePayload(String key) {
        return payload.remove(key) != null;
    }
    public String getPayload(String key) {
        return payload.get(key);
    }

    public boolean addDevice(Device device) {
        //Check but the list of supported devices is duplicated
        if (devicesHolderList.contains(device)) {
            System.out.println(device.getName() + " is already in the device list");
            return false;
        }
        //we check that the added Device supports the DeviseIndexes used in this Payload
        for (String key : payload.keySet()) {
            List<String> temp = new ArrayList<>();
            device.getDeviceIndexes().forEach(index -> {temp.add(index.getDeviceIndexName());});
            if (!temp.contains(key)) {
                System.out.println(device.getName() + ": Devise is not supported by this DeviseIndex: " + key);
                return false;
            }
        }
        return devicesHolderList.add(device);
    }
    public boolean removeDevice(Device device) {
        return devicesHolderList.remove(device);
    }
    public List<Device> getDevices() {
        return devicesHolderList;
    }

    public String getName() {
        return name;
    }

    public String getDeviceCommandDescription() {
        return deviceCommandDescription;
    }

    public void setDeviceCommandDescription(String deviceCommandDescription) {
        this.deviceCommandDescription = deviceCommandDescription;
    }

}
