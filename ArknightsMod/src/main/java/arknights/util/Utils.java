package arknights.util;
/**
 * @author hundun
 * Created on 2021/03/09
 */
public class Utils {
    
    public static String getCallerInfo() {
        StackTraceElement[] stes = new Throwable().getStackTrace();
        if (stes.length >= 2) {
            StackTraceElement ste = stes[2];
            String[] classPath = ste.getClassName().split(".");
            String classSimpleName = classPath.length > 0 ? classPath[classPath.length - 1] : "unknown";
            return "[" + classSimpleName + "."+ ste.getMethodName() + "-line: " + ste.getLineNumber() + "]";
        } else {
            return "[self]";
        }
        
    }

}
