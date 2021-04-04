
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        //Get argument from command
        String keyOrMsg = args[2];
        String msgOrCipher = args[4];
        String outputPath = args[6];

        byte[] keys = Files.readAllBytes(Paths.get(keyOrMsg));

        byte[] k1 = Arrays.copyOfRange(keys, 0, keys.length / 2);
        byte[] k2 = Arrays.copyOfRange(keys, keys.length / 2, keys.length);

        byte[] m = Files.readAllBytes(Paths.get(msgOrCipher));


        if (args[0].equals("-e")) {
            AesEnc enc = new AesEnc(k1,k2);
            byte[] res = enc.encryption(m);
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(res);
            } catch (Exception e) {
                System.out.println("exception write to file");
            }
        } else if (args[0].equals("-d")) {
            AesDec dec = new AesDec(k1,k2);
            byte[] res = dec.decryption(m);
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(res);
            } catch (Exception e) {
                System.out.println("exception write to file");
            }
        } else if (args[0].equals("-b")) {
            AesBreak aesBreak = new AesBreak();
            byte[] res = aesBreak.breakAes(keys,m);
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(res);
            } catch (Exception e) {
                System.out.println("exception write to file");
            }
        }
    }
}