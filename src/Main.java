import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        String keyOrMsg = args[2];
        String msgOrCipher = args[4];
        String outputPath = args[6];
        byte[] keysOrMsg;
        byte[] msgOrCiphr;

        try {
            keysOrMsg = Files.readAllBytes(Paths.get(keyOrMsg));
            msgOrCiphr = Files.readAllBytes(Paths.get(msgOrCipher));
        } catch (Exception e) {
            System.out.println("exception read from files");
            return;
        }
        byte[] k1 = Arrays.copyOfRange(keysOrMsg, 0, keysOrMsg.length / 2);
        byte[] k2 = Arrays.copyOfRange(keysOrMsg, keysOrMsg.length / 2, keysOrMsg.length);
        AES aes = new AES();

        switch (args[0]) {
            case "-e": {
                byte[] res = aes.encryption(msgOrCiphr, k1, k2);
                writeToFile(res, outputPath);
                break;
            }
            case "-d": {
                byte[] res = aes.decryption(msgOrCiphr, k1, k2);
                writeToFile(res, outputPath);
                break;
            }
            case "-b": {
                byte[] res = aes.breakAes(keysOrMsg, msgOrCiphr);
                writeToFile(res, outputPath);
                break;
            }
        }
    }

    private static void writeToFile(byte[] bytes, String path) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(bytes);
        } catch (Exception e) {
            System.out.println("exception write to file");
        }
    }
}