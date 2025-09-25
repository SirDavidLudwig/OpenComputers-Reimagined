package tech.dlii.opencomputers.server.component;

import net.fabricmc.loader.impl.util.Arguments;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.Context;
import tech.dlii.opencomputers.api.machine.architecture.Callback;
import tech.dlii.opencomputers.api.network.node.Node;
import tech.dlii.opencomputers.api.network.Visibility;

import java.util.Map;

public class EEPROMComponent extends AbstractComponent {

    public EEPROMComponent() {
        super(Map.of(
                DeviceAttribute.Class, DeviceClass.Processor,
                DeviceAttribute.Description, "CPU",
                DeviceAttribute.Vendor, "",
                DeviceAttribute.Product, "",
                DeviceAttribute.Clock, "Clock speed"
        ));
    }

    @Override
    protected Node initializeNode() {
        return API.network.newNode(this, Visibility.Neighbors).build();
    }

    //    public String checksum() {
//        return Hashing.crc32().hashBytes(data).toString();
//    }

    // ----------------------------------------------------------------------- //

//    @Callback(direct = true, doc = "api.component.eeprom.get")
//    public Object[] get(Context context, Arguments args) {
//
//    }

    @Callback(async = true, doc = "api.component.eeprom.makeReadonly")
    public Object[] makeReadonly(Context context, Arguments args) {
        return new Object[0];
    }

//    @Callback(doc = """function(data:string) -- Overwrite the currently stored byte array.""")
//    def set(context: Context, args: Arguments): Array[AnyRef] = {
//        if (readonly) {
//            return result((), "storage is readonly")
//        }
//        if (!node.tryChangeBuffer(-Settings.get.eepromWriteCost)) {
//            return result((), "not enough energy")
//        }
//        val newData = args.optByteArray(0, Array.empty[Byte])
//        if (newData.length > Settings.get.eepromSize) throw new IllegalArgumentException("not enough space")
//        codeData = newData
//        context.pause(2) // deliberately slow to discourage use as normal storage medium
//        null
//    }
//
//    @Callback(direct = true, doc = """function():string -- Get the label of the EEPROM.""")
//    def getLabel(context: Context, args: Arguments): Array[AnyRef] = result(label)
//
//    @Callback(doc = """function(data:string):string -- Set the label of the EEPROM.""")
//    def setLabel(context: Context, args: Arguments): Array[AnyRef] = {
//        if (readonly) {
//            return result((), "storage is readonly")
//        }
//        label = args.optString(0, "EEPROM").trim.take(24)
//        if (label.isEmpty) label = "EEPROM"
//        result(label)
//    }
//
//    @Callback(direct = true, doc = """function():number -- Get the storage capacity of this EEPROM.""")
//    def getSize(context: Context, args: Arguments): Array[AnyRef] = result(Settings.get.eepromSize)
//
//    @Callback(direct = true, doc = """function():string -- Get the checksum of the data on this EEPROM.""")
//    def getChecksum(context: Context, args: Arguments): Array[AnyRef] = result(checksum)
//
//    @Callback(direct = true, doc = """function(checksum:string):boolean -- Make this EEPROM readonly if it isn't already. This process cannot be reversed!""")
//    def makeReadonly(context: Context, args: Arguments): Array[AnyRef] = {
//        if (args.checkString(0) == checksum) {
//            readonly = true
//            result(true)
//        }
//        else result((), "incorrect checksum")
//    }
//
//    @Callback(direct = true, doc = """function():number -- Get the storage capacity of this EEPROM.""")
//    def getDataSize(context: Context, args: Arguments): Array[AnyRef] = result(Settings.get.eepromDataSize)
//
//    @Callback(direct = true, doc = """function():string -- Get the currently stored byte array.""")
//    def getData(context: Context, args: Arguments): Array[AnyRef] = result(volatileData)
//
//    @Callback(doc = """function(data:string) -- Overwrite the currently stored byte array.""")
//    def setData(context: Context, args: Arguments): Array[AnyRef] = {
//        if (!node.tryChangeBuffer(-Settings.get.eepromWriteCost)) {
//            return result((), "not enough energy")
//        }
//        val newData = args.optByteArray(0, Array.empty[Byte])
//        if (newData.length > Settings.get.eepromDataSize) throw new IllegalArgumentException("not enough space")
//        volatileData = newData
//        context.pause(1) // deliberately slow to discourage use as normal storage medium
//        null
//    }
}
