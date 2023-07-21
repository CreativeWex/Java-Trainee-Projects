import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private static final String outputFile = "result.txt";

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        Scanner console = new Scanner(System.in);
        System.out.println("Enter signature.txt path: ");
        String filename = console.nextLine();
        try {
            fileInputStream = new FileInputStream(filename);
        } catch (java.io.FileNotFoundException fileNotFoundException) {
            System.out.println("Error: Signature file not found");
            System.exit(-1);
        }

        ArrayList<Signature> signatures = new ArrayList<>();
        try {
            while (true) {
                signatures.add(Signature.createFromSignatureFile(fileInputStream));
            }
        } catch (SignatureScannerFormatException signatureScannerFormatException) {
            System.out.println("Error: Wrong signature file format");
            System.exit(-1);
        } catch (SignatureScannerEOFException signatureScannerEOFException) {
        }

        try {
            fileInputStream.close();
        } catch (java.io.IOException ioException) {
            throw new RuntimeException(ioException);
        }
        if (signatures.size() == 0) {
            System.out.println("Error: Empty signature file");
            System.exit(-1);
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(outputFile);
        } catch (java.io.FileNotFoundException fileNotFoundException) {
            System.out.println("Error: Can't create file");
            System.exit(-1);
        }

        String currentInput = new String("");
        while (!currentInput.equals("42")) {
            System.out.println("Enter file path: ");
            currentInput = console.nextLine();
            try {
                fileInputStream = new FileInputStream(currentInput);
            } catch (java.io.FileNotFoundException fileNotFoundException) {
                System.out.println("Error: File not found");
                continue;
            }
            boolean isSignatureFound = false;
            SignatureChecker signatureChecker = new SignatureChecker(fileInputStream);
            for (int i = 0; i < signatures.size(); ++i) {
                if (signatureChecker.checkSignature(signatures.get(i))) {
                    signatures.get(i).display(fileOutputStream);
                    System.out.println("PROCESSED");
                    isSignatureFound = true;
                    break;
                }
            }
            if (!isSignatureFound) {
                System.out.println("UNDEFINED");
            }
            try {
                fileInputStream.close();
            } catch (java.io.IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }
}