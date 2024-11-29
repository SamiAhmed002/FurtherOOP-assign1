package reflection.uml;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.Set;
import reflection.uml.UMLLayout.ClassLayout;
import reflection.uml.ReflectionData;

public class ProcessClassesRunner extends JPanel {

    private final Map<String, ClassLayout> layout; // Holds the layout of the class boxes
    private final Set<ReflectionData.Link> links; // Holds the links to be displayed
    private final Map<String, List<ReflectionData.FieldData>> classFields; // Stores fields for each class
    private final Map<String, List<ReflectionData.MethodData>> classMethods; // Stores methods for each class

    // Constructor with layout, links, fields, and methods
    public ProcessClassesRunner(Map<String, ClassLayout> layout, Set<ReflectionData.Link> links,
                      Map<String, List<ReflectionData.FieldData>> classFields,
                      Map<String, List<ReflectionData.MethodData>> classMethods) {
        this.layout = layout;
        this.links = links;
        this.classFields = classFields;
        this.classMethods = classMethods;
        setPreferredSize(new Dimension(800, 600)); // Set the preferred size for the component
    }

    // Override the paintComponent method to draw the class boxes and links
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        // Loop through the layout map and draw each class box
        for (Map.Entry<String, ClassLayout> entry : layout.entrySet()) {
            String className = entry.getKey();
            ClassLayout cl = entry.getValue();

            double x = cl.centerX() - cl.width() / 2;
            double y = cl.centerY() - cl.height() / 2;
            double width = cl.width() + 10;
            double height = cl.height();

            // Draw class box (rectangle)
            g2d.drawRect((int) x, (int) y, (int) width, (int) height);

            // Draw the class name centered in the box
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(className);
            int textX = (int) (x + (width - textWidth) / 2);
            int textY = (int) y + fm.getAscent();
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(className, textX, textY);

            // Draw fields inside the box
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            List<ReflectionData.FieldData> fields = classFields.get(className);
            int fieldY = textY + fm.getHeight();
            if (fields != null) {
                for (ReflectionData.FieldData field : fields) {
                    String fieldText = field.name() + ": " + field.type();
                    g2d.drawString(fieldText, (int) x + 10, fieldY);
                    fieldY += fm.getHeight();
                }
            }

            // Draw methods inside the box, below fields
            List<ReflectionData.MethodData> methods = classMethods.get(className);
            int methodY = fieldY + 10;
            if (methods != null) {
                for (ReflectionData.MethodData method : methods) {
                    String methodText = method.name() + "(): " + method.returnType();
                    g2d.drawString(methodText, (int) x + 10, methodY);
                    methodY += fm.getHeight();
                }
            }
        }

        // Draw links between the classes
        for (ReflectionData.Link link : links) {
            ClassLayout fromLayout = layout.get(link.from());
            ClassLayout toLayout = layout.get(link.to());

            if (fromLayout != null && toLayout != null) {
                int startX = (int) fromLayout.centerX();
                int startY = 0;
                int endX = (int) toLayout.centerX();
                int endY = 0;

                if (link.type() == ReflectionData.LinkType.SUPERCLASS) {
                    startY = (int) (fromLayout.centerY() - fromLayout.height() / 2);
                    endY = (int) (toLayout.centerY() + toLayout.height() / 2);
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(2));
                } else if (link.type() == ReflectionData.LinkType.DEPENDENCY) {
                    startY = (int) (fromLayout.centerY() + fromLayout.height() / 2);
                    endY = (int) (toLayout.centerY() - toLayout.height() / 2);
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(2));
                }

                g2d.drawLine(startX, startY, endX, endY);
                drawArrowHead(g2d, startX, startY, endX, endY);
            }
        }
    }

    private void drawArrowHead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 10;  // Length of the arrowhead
        int arrowWidth = 5;    // Width of the arrowhead

        // Calculate the points for the arrowhead
        int xArrow1 = (int) (x2 - arrowLength * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (y2 - arrowLength * Math.sin(angle - Math.PI / 6));
        int xArrow2 = (int) (x2 - arrowLength * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (y2 - arrowLength * Math.sin(angle + Math.PI / 6));

        // Draw the arrowhead
        g2d.drawLine(x2, y2, xArrow1, yArrow1);
        g2d.drawLine(x2, y2, xArrow2, yArrow2);
    }

    // Main method to set up a simple frame and display the panel
    public static void main(String[] args) {
        // Mock-up example layout to display
        Map<String, ClassLayout> exampleLayout = Map.of(
                "MyShape", new ClassLayout(200, 100, 150, 60),
                "MyCircle", new ClassLayout(200, 250, 150, 120),
                "MyEllipse", new ClassLayout(400, 250, 150, 90),
                "Connector", new ClassLayout(300, 400, 150, 60)
        );

        // Example links to be displayed
        Set<ReflectionData.Link> links = Set.of(
                new ReflectionData.Link("MyCircle", "Connector", ReflectionData.LinkType.DEPENDENCY),
                new ReflectionData.Link("MyCircle", "MyShape", ReflectionData.LinkType.SUPERCLASS),
                new ReflectionData.Link("MyEllipse", "MyShape", ReflectionData.LinkType.SUPERCLASS)
        );

        Map<String, List<ReflectionData.FieldData>> fields = Map.of(
                "MyCircle", List.of(new ReflectionData.FieldData("radius", "double"), new ReflectionData.FieldData("pi", "double"), new ReflectionData.FieldData("circles", "Set"), new ReflectionData.FieldData("map2", "Map"), new ReflectionData.FieldData("connector", "Connector")),
                "MyEllipse", List.of(new ReflectionData.FieldData("majorAxis", "double"), new ReflectionData.FieldData("minorAxis", "double")),
                "Connector", List.of(new ReflectionData.FieldData("radius", "double"))
        );

        Map<String, List<ReflectionData.MethodData>> methods = Map.of(
                "MyShape", List.of(new ReflectionData.MethodData("area", "double")),
                "MyCircle", List.of(new ReflectionData.MethodData("newMethod", "double"), new ReflectionData.MethodData("getConnecter", "Connector")),
                "MyEllipse", List.of(new ReflectionData.MethodData("addConnectorSet", "Set"))
        );

        // Create the panel to display the UML diagram
        DisplayUML display = new DisplayUML(exampleLayout, links, fields, methods);

        // Create a frame to hold the panel
        JFrame frame = new JFrame("UML Class Diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
