package models;

import java.util.ArrayList;
import java.util.List;

/*
*
*
*
*
*
*/
public class DeviceDataImport {
    String deviceDataName; // Unique Data Name
    String deviceDataDescription; // Full Data Description by text
    String dataType; // Type of Data {Boolean, Integer, Double and ... }
    String dataUnits; // Units of Data {%, Number, Sec, Min, degrees Celsius  and ...}
    String dataValue; // Value od Data
    List<Device> arraySupportedByDevices; // ArrayList devices supported this Class of Data

    public DeviceDataImport(String deviceDataName, String deviceDataDescription, String dataType, String dataUnits, String dataValue) {
        this.deviceDataName = deviceDataName;
        this.deviceDataDescription = deviceDataDescription;
        this.dataType = dataType;
        this.dataUnits = dataUnits;
        this.dataValue = dataValue;
        this.arraySupportedByDevices = new ArrayList<>();
        initDeviceDate();
    }

    private void initDeviceDate() {
        /*We perform the syroilization of all parameters
         *of the instance of this class for further processing,
         *calculations or analysis
         *TODO
         *After understanding the further structure
         */
    }
    public boolean addSupportedDevice (Device device) {
        arraySupportedByDevices.add(device);
        return true;
    }
    public boolean deleteSupportedDevice (Device device) {
        arraySupportedByDevices.remove(device);
        return true;
    }
}



