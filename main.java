public class main {
    private static float[] inputDataF = new float[100];
    private static float[] outputDataF = new float[100];

    private static short[] inputData = new short[100];
    private static short[] outputData = new short[100];

    private short convertFromFloatToShort(float value) {
        return (short) (value * 32768);
    }

    private float convertFromShortToFloat(short value) {
        return (float) value / 32768;
    }

    private static int accumulator = 0;

    private static float[] koefficients = new float[100];

    private static void main(){

    }
}
