package models;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Device {
    String name;
    String deviceDescription;
    Map<String, String> metaData;
    List<DeviceCommand> deviceCommands;
    List<DeviceIndex> deviceIndexes;

    public Device(String name, String deviceDescription) {
        this.name = name;
        this.deviceDescription = deviceDescription;
        this.metaData = new HashMap<>();
        this.deviceCommands = new ArrayList<>();
        this.deviceIndexes = new ArrayList<>();
    }
    public JSONObject syncRequest() throws JsonProcessingException {
       //Make JSON metadata
        JSONObject metaJson = new JSONObject();
        for(Map.Entry<String, String> entry : metaData.entrySet()) {
            metaJson.put(entry.getKey(), entry.getValue());
        }
        //Make JSON of deviseCommands

        List<JSONObject> commands = new ArrayList<>();
        for(DeviceCommand command : deviceCommands) {
            commands.add(command.toJson());
        }

        JSONArray deviceCommandsArray = new JSONArray();
        deviceCommandsArray.putAll(commands);


        // Make root JSON with collection metadata and deviceCommandsArray
        JSONObject root = new JSONObject();
        root.put("metadata", metaJson);
        root.put("commands", deviceCommandsArray);

        // Make response JSON
        JSONObject response = new JSONObject();
        response.put(name, root);

        return response;
    }





    public boolean putMetaData(String key, String value) {
        if (metaData.containsKey(key)) {
            System.out.println(key + ":  Data with this key is already described in this device. Choose another name or delete in the device meta dictionary");
            return false;
        }
        return metaData.put(key, value) != null;
    }
    public boolean removeMetaData(String key) {
        return metaData.remove(key) != null;
    }

    public String getMetaData(String key) {
        return metaData.get(key);
    }

    public Map<String, String> getAllMetaData() {
        return metaData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public List<DeviceCommand> getDeviceCommands() {
        return deviceCommands;
    }

    public List<DeviceIndex> getDeviceIndexes() {
        return deviceIndexes;
    }

    public boolean addDeviceCommand(DeviceCommand deviceCommand) {
        if (!deviceCommands.contains(deviceCommand)) {
            return deviceCommands.add(deviceCommand);
        }
        return false;
    }
    public boolean removeDeviceCommand(DeviceCommand deviceCommand) {
        return deviceCommands.remove(deviceCommand);
    }
    public boolean addDeviceIndex(DeviceIndex deviceIndex) {
        if (!deviceIndexes.contains(deviceIndex)) {
            return deviceIndexes.add(deviceIndex);
        }
        return false;
    }
    public boolean removeDeviceIndex(DeviceIndex deviceIndex) {
        return deviceIndexes.remove(deviceIndex);
    }
}
