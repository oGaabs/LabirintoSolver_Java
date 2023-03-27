
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class Keyboard {
    private static BufferedReader console;

    static {
        Keyboard.console = new BufferedReader(new InputStreamReader(System.in));
    }

    public static class KeyboardInputException extends Exception { 
        public KeyboardInputException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void clearScreen() throws KeyboardInputException {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            String command = os.contains("windows") ? "cmd /c cls" : "clear";
            
            new ProcessBuilder(command).inheritIO().start().waitFor();
        } catch (IOException | InterruptedException error) {
            Thread.currentThread().interrupt();
            throw new KeyboardInputException("Erro ao limpar tela!");
        }
    }

    public static String getUmString() {
        String ret = null;
        try {
            ret = console.readLine();
        } catch (IOException error) {
        }

        return ret;
    }

    public static byte getUmByte() throws KeyboardInputException {
        byte ret = 0;

        try {
            ret = Byte.parseByte(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Byte invalido!");
        }

        return ret;
    }

    public static short getUmShort() throws KeyboardInputException {
        short ret = 0;

        try {
            ret = Short.parseShort(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Short invalido!");
        }

        return ret;
    }

    public static int getUmInt() throws KeyboardInputException {
        int ret = 0;

        try {
            ret = Integer.parseInt(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Int invalido!");
        }

        return ret;
    }

    public static long getUmLong() throws KeyboardInputException {
        long ret = 0L;

        try {
            ret = Long.parseLong(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Long invalido!");
        }

        return ret;
    }

    public static float getUmFloat() throws KeyboardInputException {
        float ret = 0.0F;

        try {
            ret = Float.parseFloat(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Float invalido!");
        }

        return ret;
    }

    public static double getUmDouble() throws KeyboardInputException {
        double ret = 0.0;

        try {
            ret = Double.parseDouble(console.readLine());
        } catch (IOException error) {
        } catch (NumberFormatException error) {
            throw new KeyboardInputException("Double invalido!");
        }

        return ret;
    }

    public static char getUmChar() throws KeyboardInputException {
        char ret = ' ';

        try {
            final String str = console.readLine();

            if (str == null)
                throw new KeyboardInputException("Char invalido!");

            if (str.length() != 1)
                throw new KeyboardInputException("Char invalido!");

            ret = str.charAt(0);
        } catch (IOException error) {
        }

        return ret;
    }

    public static boolean getUmBoolean() throws KeyboardInputException {
        boolean ret = false;

        try {
            final String str = console.readLine();

            if (str == null)
                throw new KeyboardInputException("Boolean invalido!");

            if (!str.equals("true") && !str.equals("false"))
                throw new KeyboardInputException("Boolean invalido!");

            ret = Boolean.parseBoolean(str);
        } catch (IOException error) {
        }

        return ret;
    }
}