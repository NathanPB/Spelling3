package cf.nathanpb.Spelling3;

import cf.nathanpb.Spelling3.utils.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nathanpb on 10/21/17.
 */
public class LanguageManager {
    public static String getText(String registry, String locale ){
        locale = locale.split("_")[0]+"_"+locale.split("_")[1].toUpperCase();
        try {
            String f = FileUtils.read(LanguageManager.class.getResourceAsStream("/assets/lang/" + locale + ".lang"));
            Map<String, String> map = Arrays.stream(f.split("\n")).map(s -> s.split("=")).collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));
            return map.get(registry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
