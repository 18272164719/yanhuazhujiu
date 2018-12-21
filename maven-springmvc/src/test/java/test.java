import com.test.annotation.MyAutowired;

import java.lang.reflect.Field;

public class test {

    public static void main(String[] args) {
        String cn = "com.test.servlet.DispatcherServlet";
        try {
            Class<?> clazz = Class.forName(cn);
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if(field.isAnnotationPresent(MyAutowired.class)){
                    System.out.println(field.getName());

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
