
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Get argument from command
        String keysPath = args[2];
        String plainTextPath = args[4];
        String outputPath = args[6];

        //initial Files
        File keysFile = new File(keysPath);
        File plainTextFile = new File(plainTextPath);

        //Split to list of blocks
        List[] plainTextSplitToBlocks = utils.getBlocksByFile(plainTextFile);
        List[] keysSplitToBlocks = utils.getBlocksByFile(keysFile);

        if (args[0].equals("–e")) {
            //initial keys
            AesEnc aesEnc = new AesEnc(keysSplitToBlocks);
            aesEnc.writeMatrixToFile(outputPath, plainTextSplitToBlocks);
            List[] cypher = aesEnc.encryption(plainTextSplitToBlocks);
            aesEnc.writeMatrixToFile(outputPath, cypher);

        } else if (args[0].equals("–d")) {
            AesDec aesDec = new AesDec(keysSplitToBlocks);
            aesDec.writeMatrixToFile(outputPath, plainTextSplitToBlocks);
            List[] plain = aesDec.decryption(plainTextSplitToBlocks);
            aesDec.writeMatrixToFile(outputPath, plain);

        } else if (args[0].equals("–b")) {

            AesBreak aesBreak = new AesBreak();
            List[] keys = aesBreak.breakAes(plainTextSplitToBlocks, keysSplitToBlocks);
            aesBreak.writeMatrixToFile(outputPath, keys);
        }
    }
}
