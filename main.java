import java.io.IOException;

public class main {
    private static float[] inputDataF = new float[100];
    private static float[] outputDataF = new float[100];
    private static short[] inputData = new short[100];
    private static short[] outputData = new short[100];

    private static float[] coefficientsF = new float[16];
    private static short[] coefficients = new short[16];

    private static int accumulator = 0;

    private static short[] workData = new short[16];

    private static short convertFromFloatToShort(float value) {
        return (short) (value * 32768);
    }
    private static float convertFromShortToFloat(short value) {
        return (float) value / 32768;
    }

    private static void getCoefs(){
        try {
            coefficientsF = file_worker.readFloatArray("coefs.txt", 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 16; i++) {
            coefficients[i] = (short)coefficientsF[i];
        }
    }

    private static void getInputData(String fileName){
        try {
            inputDataF = file_worker.readFloatArray(fileName, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            inputData[i] = convertFromFloatToShort(inputDataF[i]);
        }
    }

    private static void fillWorkData(int startIndex) {
        for (int i = 0; i < 16; i++) {
            if (startIndex - i < 0 || startIndex - i >= 16) {
                workData[i] = 0;
            } else {
                workData[i] = inputData[startIndex - i];
            }
        }
    }

    private static short processData() {
        accumulator = 0;
        for (int i = 0; i < 16; i++) {
            accumulator += workData[i] * coefficients[i];
        }
        return (short) (accumulator >> 15);
    }

    private static void writeCoefs(String fileName) {
        try {
            file_worker.writeFloatArray(fileName, outputDataF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawGraphs() {
        try {
            graph_painter.saveGraph(outputDataF, "Output Data Graph", "output_graph.png");
            graph_painter.saveGraph(inputDataF, "Input Data Graph", "input_graph.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getCoefs();
        getInputData("input.txt");

        for (int i = 0; i < 100; i++) {
            fillWorkData(i);
            outputData[i] = processData();
            outputDataF[i] = convertFromShortToFloat(outputData[i]);
        }

        System.out.println("coefs:");
        for (int i = 0; i < 16; i++) {
            System.out.print(coefficientsF[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 16; i++) {
            System.out.print(coefficients[i] + " ");
        }
        System.out.println();

        System.out.println("input data:");
        for (int i = 0; i < 100; i++) {
            System.out.print(inputDataF[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.print(inputData[i] + " ");
        }
        System.out.println();

        System.out.println("output data:");
        for (int i = 0; i < 100; i++) {
            System.out.print(outputDataF[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.print(outputData[i] + " ");
        }
        System.out.println();

        writeCoefs("output.txt");
        drawGraphs();
    }
}
