package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;

import java.util.*;

public class DeviceCommand {
    final private String name;
    private String deviceCommandDescription;
    Map<DeviceIndex, String> payload;
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
        // Forming additional JSON to describe devices of type LORA
        JSONObject payloadJson = new JSONObject();
        //Forming SET for IndexDataType
        Set<DataType> tempSet = new HashSet<>();
        for (DeviceIndex index : payload.keySet()) {
            tempSet.add(index.getDataType());
        }
        for (DataType dataType : tempSet) {
            JSONObject payloadJsonDataType = new JSONObject();
            for(Map.Entry<DeviceIndex, String> entry : payload.entrySet()) {
                if(entry.getKey().getDataType().equals(dataType)) {
                    payloadJsonDataType.put(entry.getKey().getDeviceIndexLoraID(), entry.getValue());
                }
            }
            payloadJson.put(dataType.toString(), payloadJsonDataType);
        }


        JSONObject deviceCommandJson = new JSONObject();
                deviceCommandJson.put("command", name);
                //deviceCommandJson.put("deviceCommandDescription", deviceCommandDescription);
                deviceCommandJson.put("payload", payloadJson);

        return deviceCommandJson;
    }


    public boolean putPayload(DeviceIndex index, String value) {
        if (payload.containsKey(index)) {
            System.out.println(index + ": Payload with this key is already described in this device. Choose another name or delete in the device PAYLOAD dictionary\"");
            //We check but there is duplication of Payload commands

            return false;
        }
        for (Device device : devicesHolderList) {
            //We check that this DeviceIndex ist exists and supported by this device
            List<String> temp = new ArrayList<>();
            device.getDeviceIndexes().forEach(i -> {temp.add(i.getDeviceIndexName());});
            //Create a temporary array of DeviceIndex Names for testing
            if (!temp.contains(index.getDeviceIndexName())) {
                System.out.println(index.getDeviceIndexName() + ": Payload with key is not supported by this device");
                return false;
            }
        }
        return payload.put(index, value) != null;
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
        for (DeviceIndex key : payload.keySet()) {
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
