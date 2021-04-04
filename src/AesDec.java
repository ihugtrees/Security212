import java.util.Arrays;
import java.util.ArrayList;

public class AesDec {
    private byte[][] key1 = new byte[4][4];
    private byte[][] key2 = new byte[4][4];

    public AesDec(byte[] key1, byte[] key2) {
        for (int r = 0; r < 4; r++) {
            this.key1[r] = Arrays.copyOfRange(key1, r * 4, (r + 1) * 4);
            this.key2[r] = Arrays.copyOfRange(key2, r * 4, (r + 1) * 4);
        }
    }

    public byte[] decryption(byte[] encrypted) {

        if (encrypted == null) {
            return null;
        }

        //initial list to encrypted message
        byte[] message = new byte[encrypted.length];

        //declare runner
        ArrayList<ArrayList<String>> currentMatToEnc;

        for (int i = 0; i < encrypted.length / 16; i++) {
            byte[][] blocked = new byte[4][4];

            for (int r = 0; r < 4; r++) {
                blocked[r] = Arrays.copyOfRange(encrypted, (r * 4) + (i * 16), ((r + 1) * 4) + (i * 16));

            }
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    blocked[k][j] = (byte) ((this.key2[k][j]) ^ (blocked[k][j]));
                }
            }
            byte[][] transposed = new byte[4][4];
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    transposed[k][j] = blocked[j][k];
                }
            }
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    transposed[k][j] = (byte) ((this.key1[k][j]) ^ (transposed[k][j]));
                }
            }
            byte[][] transposed2 = new byte[4][4];
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    transposed2[k][j] = transposed[j][k];
                }
            }
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    message[k * 4 + j + i * 16] = transposed2[k][j];
                }
            }

        }
        return message;
    }
}
