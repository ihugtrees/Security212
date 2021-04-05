import java.util.Arrays;

public class AES {
    private byte[][] makeMatrix(byte[] arr, int i) {
        byte[][] matrix = new byte[4][4];
        for (int r = 0; r < 4; r++) {
            matrix[r] = Arrays.copyOfRange(arr, (r * 4) + (i * 16), ((r + 1) * 4) + (i * 16));
        }
        return matrix;
    }

    private void matXor(byte[][] block, byte[][] key){
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                block[k][j] = (byte) (key[k][j] ^ block[k][j]);
            }
        }
    }

    private byte [][] matTranspose(byte[][] block){
        byte[][] transposed = new byte[4][4];
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                transposed[k][j] = block[j][k];
            }
        }
        return transposed;
    }

    public byte[] encryption(byte[] msg, byte[] key11, byte[] key22) {

        byte[][] key1 = makeMatrix(key11,0);
        byte[][] key2 = makeMatrix(key22,0);
        byte[] encrypted = new byte[msg.length];

//        for (int r = 0; r < 4; r++) {
//            key1[r] = Arrays.copyOfRange(key11, r * 4, (r + 1) * 4);
//            key2[r] = Arrays.copyOfRange(key22, r * 4, (r + 1) * 4);
//        }

        for (int i = 0; i < msg.length / 16; i++) {
            byte[][] block = makeMatrix(msg, i);

//            for (int r = 0; r < 4; r++)
//                block[r] = Arrays.copyOfRange(msg, (r * 4) + (i * 16), ((r + 1) * 4) + (i * 16));

//            byte[][] transposed = new byte[4][4];

//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed[k][j] = block[j][k];
//                }
//            }
//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed[k][j] = (byte) (key1[k][j] ^ transposed[k][j]);
//                }
//            }
//            byte[][] transposed2 = new byte[4][4];
//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed2[k][j] = transposed[j][k];
//                }
//            }

            byte[][] transposed = matTranspose(block);
            matXor(transposed, key1);
            transposed = matTranspose(transposed);
            matXor(transposed, key2);

            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    encrypted[k * 4 + j + i * 16] = transposed[k][j];
                }
            }
        }
        return encrypted;
    }

    public byte[] decryption(byte[] cipher, byte[] key11, byte[] key22) {

        byte[][] key1 = makeMatrix(key11,0);
        byte[][] key2 = makeMatrix(key22,0);
        byte[] message = new byte[cipher.length];

//        for (int r = 0; r < 4; r++) {
//            key1[r] = Arrays.copyOfRange(key11, r * 4, (r + 1) * 4);
//            key2[r] = Arrays.copyOfRange(key22, r * 4, (r + 1) * 4);
//        }

        for (int i = 0; i < cipher.length / 16; i++) {
            byte[][] block = makeMatrix(cipher, i);

//            for (int r = 0; r < 4; r++) {
//                block[r] = Arrays.copyOfRange(cipher, (r * 4) + (i * 16), ((r + 1) * 4) + (i * 16));
//            }

//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    block[k][j] = (byte) (key2[k][j] ^ block[k][j]);
//                }
//            }
//            byte[][] transposed = new byte[4][4];
//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed[k][j] = block[j][k];
//                }
//            }
//
//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed[k][j] = (byte) (key1[k][j] ^ transposed[k][j]);
//                }
//            }
//
//            byte[][] transposed2 = new byte[4][4];
//            for (int k = 0; k < 4; k++) {
//                for (int j = 0; j < 4; j++) {
//                    transposed2[k][j] = transposed[j][k];
//                }
//            }

            matXor(block, key2);
            byte[][] transposed = matTranspose(block);
            matXor(transposed, key1);
            transposed = matTranspose(transposed);

            for (int k = 0; k < 4; k++) {
                for (int j = 0; j < 4; j++) {
                    message[k * 4 + j + i * 16] = transposed[k][j];
                }
            }
        }
        return message;
    }

    public byte[] breakAes(byte[] msg, byte[] cipher) {

        byte[] key1 = "FFFFFFFFFFFFFFFF".getBytes();
        byte[] key = new byte[32];

        System.arraycopy(key1, 0, key, 0, 16);

//        byte[][] matrix_msg = makeMatrix(msg,0);
//        byte[][] matrix_cipher = makeMatrix(cipher,0);
//        byte[][] matrix_key = makeMatrix(key1,0);
//
////        for (int r = 0; r < 4; r++) {
////            matrix_text[r] = Arrays.copyOfRange(message, r * 4, (r + 1) * 4);
////            matrix_cipher[r] = Arrays.copyOfRange(cypher, r * 4, (r + 1) * 4);
////            matrix_key[r] = Arrays.copyOfRange(key1, r * 4, (r + 1) * 4);
////        }
//
//        for (int k = 0; k < 4; k++) {
//            for (int j = 0; j < 4; j++) {
//                key[k * 4 + j + 16] = (byte) (matrix_cipher[k][j] ^ matrix_msg[k][j] ^ matrix_key[j][k]);
//            }
//        }

        for (int i=0; i<16; i++){
            key[i+16] = (byte) (cipher[i] ^ msg[i] ^ key1[i]);
        }
        return key;
    }
}
