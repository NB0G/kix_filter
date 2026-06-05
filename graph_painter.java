import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class graph_painter {
    private static final int GRAPH_WIDTH = 800;
    private static final int GRAPH_HEIGHT = 500;
    private static final int GRAPH_PADDING = 50;
    private static final String GRAPH_FILE_PATH = "graph.png";

    public static void saveGraph(float[] values, String title) throws IOException {
        validateGraphValues(values);

        BufferedImage image = new BufferedImage(GRAPH_WIDTH, GRAPH_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, GRAPH_WIDTH, GRAPH_HEIGHT);

        drawTitle(graphics, title);
        drawAxes(graphics);
        drawGraphLine(graphics, values);
        graphics.dispose();

        ImageIO.write(image, "png", new File(GRAPH_FILE_PATH));
    }

    private static void validateGraphValues(float[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array must not be null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Values array must not be empty");
        }
    }

    private static void drawAxes(Graphics2D graphics) {
        int left = GRAPH_PADDING;
        int top = getGraphTop();
        int right = GRAPH_WIDTH - GRAPH_PADDING;
        int bottom = GRAPH_HEIGHT - GRAPH_PADDING;

        graphics.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= 10; i++) {
            int x = left + (right - left) * i / 10;
            int y = top + (bottom - top) * i / 10;
            graphics.drawLine(x, top, x, bottom);
            graphics.drawLine(left, y, right, y);
        }

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawLine(left, bottom, right, bottom);
        graphics.drawLine(left, top, left, bottom);
    }

    private static void drawTitle(Graphics2D graphics, String title) {
        if (title == null || title.isBlank()) {
            return;
        }

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 24));

        FontMetrics metrics = graphics.getFontMetrics();
        int x = (GRAPH_WIDTH - metrics.stringWidth(title)) / 2;
        int y = 35;
        graphics.drawString(title, Math.max(x, GRAPH_PADDING), y);
    }

    private static void drawGraphLine(Graphics2D graphics, float[] values) {
        float min = values[0];
        float max = values[0];
        for (float value : values) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        int left = GRAPH_PADDING;
        int top = getGraphTop();
        int right = GRAPH_WIDTH - GRAPH_PADDING;
        int bottom = GRAPH_HEIGHT - GRAPH_PADDING;
        float range = max - min;

        graphics.setColor(new Color(30, 90, 200));
        graphics.setStroke(new BasicStroke(3));
        if (values.length == 1) {
            int x = (left + right) / 2;
            int y = getGraphY(values[0], min, range, top, bottom);
            graphics.fillOval(x - 4, y - 4, 8, 8);
            return;
        }

        for (int i = 1; i < values.length; i++) {
            int x1 = left + (right - left) * (i - 1) / (values.length - 1);
            int x2 = left + (right - left) * i / (values.length - 1);
            int y1 = getGraphY(values[i - 1], min, range, top, bottom);
            int y2 = getGraphY(values[i], min, range, top, bottom);
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    private static int getGraphY(float value, float min, float range, int top, int bottom) {
        if (range == 0) {
            return (top + bottom) / 2;
        }

        return bottom - Math.round((value - min) * (bottom - top) / range);
    }

    private static int getGraphTop() {
        return GRAPH_PADDING + 20;
    }
}
