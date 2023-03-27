import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Cloner {
    public static class ClonerException extends Exception {
        public ClonerException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static Object verifyAndCopy(Object data) throws ClonerException {
        if (data instanceof Cloneable)
            return deepCopy(data);

        return data;
    }

    public static Object deepCopy(Object data) throws ClonerException {
        Class<?> classOfData = data.getClass();
        
        Method methodClone=null;
        try
        {
            methodClone = classOfData.getMethod("clone");
        }
        catch (NoSuchMethodException error)
        {
            throw new ClonerException("Classe nao implementa Cloneable!");
        }


        Object cloneOfObject=null;
        try
        {
            cloneOfObject = methodClone.invoke(data);
        }
        catch (InvocationTargetException | IllegalAccessException | NullPointerException error)
        {
            throw new ClonerException("Erro ao clonar objeto!");
        }

        return cloneOfObject;
    }
}