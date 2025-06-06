import com.fasterxml.jackson.core.JsonProcessingException;
import models.*;


public class DeviseTestApp {
    public static void main(String[] args) throws JsonProcessingException {
        //Forming new Device
        Device device = new Device("000000000c5e32c2", "rpi_lora_tester" );

        //Forming new metadata Map (Roberts simple)
        device.putMetaData("family", "rpi_lora_tester");
        device.putMetaData("timestamp", String.valueOf(System.currentTimeMillis()));
        device.putMetaData("api", "1.0.0");

        //Add Index with data(name, type of data (time), unit(second)) for this Device
        DeviceIndex timeReportMake = new DeviceIndex("timeReportMake", "second", "0");
        timeReportMake.setDataType(DataType.TIME);
        device.addDeviceIndex(timeReportMake);

        DeviceIndex timeSensorRequest = new DeviceIndex("timeSensorRequest", "second", "1");
        timeSensorRequest.setDataType(DataType.TIME);
        device.addDeviceIndex(timeSensorRequest);

        DeviceIndex makeChange = new DeviceIndex("makeChange", "boolean", "0");
        makeChange.setDataType(DataType.BOOLEAN);
        device.addDeviceIndex(makeChange);




        //Forming new Command with name, description and payload: {index: value}
        DeviceCommand command = new DeviceCommand("configure", device);
        command.putPayload(timeReportMake, "20");
        command.putPayload(timeSensorRequest, "20");
        command.putPayload(makeChange, "true");

        //command.setDeviceCommandDescription("Setting the return time of the sensor status report");

        //Add this Command to this Devise
        device.addDeviceCommand(command);

        //Forming new GateWay
        GateWay gateWay = new GateWay("gateWay");

        //Add metadata to GateWay Map
        gateWay.putMetaData("ID", "000000000c5e32c8");
        gateWay.putMetaData("token", "ABCD");

        //Add this Device to this GateWay
        gateWay.addDevice(device);

        //Print JSON with command "configure" to Devise name "000000000c5e32c2"
        System.out.println(gateWay.syncRequest());
    }
}
