import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AesBreak {

    //Fields
    private byte[] key1 = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".getBytes();
    private byte[] key = new byte[32];
    public AesBreak() {
        for(int i=0;i<16;i++)
            this.key[i] = this.key1[i];
    }



    public byte[] breakAes(byte[] message, byte[] cypher) {

        if (message == null || cypher == null) {
            return null;
        }



            byte[][] blocked_text = new byte[4][4];
            byte[][] blocked_cipher = new byte[4][4];
            byte[][] blocked_key = new byte[4][4];

            for (int r = 0; r < 4; r++) {
                blocked_text[r] = Arrays.copyOfRange(message, (r * 4) , ((r + 1) * 4) );
                blocked_cipher[r] = Arrays.copyOfRange(cypher, (r * 4) , ((r + 1) * 4));
                blocked_key[r] = Arrays.copyOfRange(this.key1, (r * 4) , ((r + 1) * 4));
            }
            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    this.key[k * 4 + j + 16] = (byte) ((blocked_cipher[k][j]) ^ (blocked_text[k][j])^blocked_key[j][k]);
                }
            }


        return this.key;
    }

}
