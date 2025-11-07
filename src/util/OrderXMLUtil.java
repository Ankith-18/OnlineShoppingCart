package util;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

public class OrderXMLUtil {
    private static final Logger logger = Logger.getLogger(OrderXMLUtil.class.getName());

    public static void saveOrderAsXML(Order order) {
        String fileName = "order_" + order.getId() + ".xml";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("<order id='" + order.getId() + "'>\n");
            writer.write("  <user>" + order.getUser().getUsername() + "</user>\n");
            writer.write("  <total>" + order.getTotalAmount() + "</total>\n");
            writer.write("  <items>\n");
            for (CartItem item : order.getItems()) {
                writer.write("    <item>\n");
                writer.write("      <product>" + item.getProduct().getName() + "</product>\n");
                writer.write("      <quantity>" + item.getQuantity() + "</quantity>\n");
                writer.write("      <price>" + item.getProduct().getPrice() + "</price>\n");
                writer.write("    </item>\n");
            }
            writer.write("  </items>\n");
            writer.write("</order>\n");

            logger.info(" Order saved as XML file: " + fileName);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving order XML", e);
        }
    }
}
