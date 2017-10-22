package cf.nathanpb.Spelling3.utils;

import java.lang.reflect.Method;

/**
 * Created by nathanpb on 9/14/17.
 */
public class JavaUtils {
    public static Method getMethodByName(String name, Class clasz){
        try{
            for(Method m : clasz.getDeclaredMethods()){
                if(m.getName().equals(name)) return m;
            }
        }catch (Exception e){}
        return null;
    }
}
