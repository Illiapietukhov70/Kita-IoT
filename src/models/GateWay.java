package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GateWay {
    private String name;
    private String gateWayDescription;
    Map<String, String> metaData;
    List<Device> devices;

    public GateWay(String name) {
        this.name = name;
        this.gateWayDescription = "";
        this.metaData = new HashMap<>();
        this.devices = new ArrayList<>();
    }

    public List<Device> getDevices() {
        return devices;
    }
    public Device getDevice(String deviceName) {
        for (Device device : devices) {
            if (device.getName().equals(deviceName)) {
                return device;
            }
        }
        return null;
    }

    public boolean addDevice(Device device) {
        if (device != null && !devices.contains(device)) {
            return devices.add(device);
        }
        return false;
    }

    public boolean removeDevice(Device device) {
        return devices.remove(device);
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public boolean putMetaData(String key, String value) {
        if(!metaData.containsKey(key)) {
            metaData.put(key, value);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGateWayDescription() {
        return gateWayDescription;
    }

    public void setGateWayDescription(String gateWayDescription) {
        this.gateWayDescription = gateWayDescription;
    }
    public JSONObject syncRequest() throws JsonProcessingException {
        //Make metadata JSON

        JSONObject metaJson = new JSONObject();
        for(Map.Entry<String, String> entry : metaData.entrySet()) {
            metaJson.put(entry.getKey(), entry.getValue());
        }
        JSONObject gateWayJson = new JSONObject();
        gateWayJson.put("metadata", metaJson);

        // Make devicesArray JSON with all devices
        List<JSONObject> deviceList = new ArrayList<>();
        for(Device device : devices) {
            deviceList.add(device.syncRequest());
        }
        JSONArray deviseJsonArray = new JSONArray();
        deviseJsonArray.putAll(deviceList);

        JSONObject response = new JSONObject();
        response.put("gateway", gateWayJson);
        response.put("devices", deviseJsonArray);


        return response;
    }

}
