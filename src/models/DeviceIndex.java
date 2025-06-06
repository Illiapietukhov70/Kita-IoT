package models;
/*
*This class describes the structure and characteristics of data intended for export to devices,
* unique data name unit of measurement and data type.
*
* */
public class DeviceIndex {
    final private String deviceIndexName;
    final private String deviceIndexLoraID;
    private String deviceIndexDescription;
    private DataType dataType;
    private String dataUnitName;

    public DeviceIndex(String deviceIndexName, String dataUnitName, String deviceIndexLoraID) {
        this.deviceIndexName = deviceIndexName;
        this.deviceIndexLoraID = deviceIndexLoraID;
        this.dataUnitName = dataUnitName;
        this.deviceIndexDescription = "";
        this.dataType = DataType.STRING;
    }

    public String getDeviceIndexLoraID() {
        return deviceIndexLoraID;
    }

    public String getDataUnitName() {
        return dataUnitName;
    }

    public void setDataUnitName(String dataUnitName) {
        this.dataUnitName = dataUnitName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDeviceIndexName() {
        return deviceIndexName;
    }

    public String getDeviceIndexDescription() {
        return deviceIndexDescription;
    }

    public void setDeviceIndexDescription(String deviceIndexDescription) {
        this.deviceIndexDescription = deviceIndexDescription;
    }
}

