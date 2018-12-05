package GenerateGIF;

import java.io.File;

/**
 * @author guangxush
 */
public class GetFiles {
    public static File[] getFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        return files;
    }
}
