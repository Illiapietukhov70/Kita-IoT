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
        DeviceIndex timeReportMake = new DeviceIndex("timeReportMake", "second");
        timeReportMake.setDataType(DataType.TIME);
        device.addDeviceIndex(timeReportMake);

        //Forming new Command with name, description and payload: {index: value}
        DeviceCommand command = new DeviceCommand("configure", device);
        command.putPayload(timeReportMake.getDeviceIndexName(), "20");
        command.setDeviceCommandDescription("Setting the return time of the sensor status report");

        //Add this Command to this Devise
        device.addDeviceCommand(command);

        //Forming new GateWay
        GateWay gateWay = new GateWay("gateWay");

        //Add metadata to GateWay Map
        gateWay.putMetaData("token", "ABCD");

        //Add this Device to this GateWay
        gateWay.addDevice(device);

        //Print JSON with command "configure" to Devise name "000000000c5e32c2"
        System.out.println(gateWay.syncRequest());
    }
}
