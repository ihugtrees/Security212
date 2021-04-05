import java.util.Arrays;

public class AesEnc {
    private byte[][] key1 = new byte[4][4];
    private byte[][] key2 = new byte[4][4];

    public AesEnc(byte[] key1, byte[] key2) {
        for (int r = 0; r < 4; r++) {
            this.key1[r] = Arrays.copyOfRange(key1, r * 4, (r + 1) * 4);
            this.key2[r] = Arrays.copyOfRange(key2, r * 4, (r + 1) * 4);
        }
    }

    public byte[] encryption(byte[] message) {
        if (message == null)
            return null;

        byte[] encrypted = new byte[message.length];

        for (int i = 0; i < message.length / 16; i++) {
            byte[][] blocked = new byte[4][4];

            for (int r = 0; r < 4; r++)
                blocked[r] = Arrays.copyOfRange(message, (r * 4) + (i * 16), ((r + 1) * 4) + (i * 16));

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
                    encrypted[k * 4 + j + i * 16] = (byte) ((this.key2[k][j]) ^ (transposed2[k][j]));
                }
            }
        }
        return encrypted;
    }
}
