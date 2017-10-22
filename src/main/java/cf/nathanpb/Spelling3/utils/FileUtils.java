package cf.nathanpb.Spelling3.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Scanner;

/**
 * Created by nathanpb on 10/21/17.
 */
public class FileUtils {
    public static String read(File f){
        StringBuilder result = new StringBuilder("");
        try(Scanner scanner = new Scanner(f)){
            while(scanner.hasNextLine()) result.append(scanner.nextLine()).append("\n");
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public static String read(InputStream is){
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer);
        }catch (Exception e){e.printStackTrace();}
        return writer.toString();
    }
}
