import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Signature {
    public final static int MAX_SIGNATURE_SIZE = 20;

    public String type;
    public String signature;

    private Signature () {

    }

    private Signature (String type, String signature) {
        this.type = type;
        this.signature = signature;
    }

    public void display(OutputStream outputStream) {
        try {
                outputStream.write(type.getBytes(), 0, type.length());
                outputStream.write('\n');
            }
        catch (java.io.IOException ioException) {
            throw new RuntimeException();
        }
    }
    public static Signature createFromSignatureFile (InputStream inputStream) throws SignatureScannerFormatException, SignatureScannerEOFException {
        try {
            char[] buffer = new char[MAX_SIGNATURE_SIZE];
            int [] inHex = new int[2];
            String formatName;
            int currentChar = inputStream.read();
            if (currentChar == '\n') {
                currentChar = inputStream.read();
            }
            if (currentChar == -1) {
                throw new SignatureScannerEOFException();
            }
            int charCounter = 0;
            while (currentChar >= 'A' && currentChar <= 'Z'
                    || (currentChar >= '0' && currentChar <= '9')) {
                buffer[charCounter] = (char) currentChar;
                currentChar = inputStream.read();
                ++charCounter;
            }
            if (charCounter < 1 || currentChar != ',') {
                throw new SignatureScannerFormatException();
            }
            formatName = new String(buffer, 0, charCounter);
            int charPairCounter = 0;
            currentChar = inputStream.read();

            while (currentChar != -1 && currentChar != '\n' && currentChar != '\r' && charPairCounter < Signature.MAX_SIGNATURE_SIZE) {
                if (currentChar != ' ') {
                    throw new SignatureScannerFormatException();
                }
                currentChar = inputStream.read();
                charCounter = 0;
                while ((currentChar >= 'A' && currentChar <= 'F')
                        || (currentChar >= '0' && currentChar <= '9')) {
                    if (currentChar >= 'A') {
                        currentChar = currentChar -'A' + 10;
                    } else {
                        currentChar -= '0';
                    }
                    inHex[charCounter] = currentChar;
                    currentChar = inputStream.read();
                    ++charCounter;
                }
                if (charCounter != 2) {
                    throw new SignatureScannerFormatException();
                }
                buffer[charPairCounter] = (char)(inHex[0] * 16 + inHex[1]);
                ++charPairCounter;
            }
            if (charPairCounter < 2) {
                throw new SignatureScannerFormatException();
            }
            return new Signature(formatName, new String(buffer, 0, charPairCounter));
        } catch (java.io.IOException ioException) {
            throw new RuntimeException();
        }
    }
}
