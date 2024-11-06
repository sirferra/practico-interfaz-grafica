package util;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

public class Tools {
    static public Map<String, String> mapFromObject(Object object) {
        Map<String, String> map = new HashMap<>();
        
        // Iterar sobre los campos de la clase Persona y sus subclases
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);  // Me aseguro que el campo es accesible
                try {
                    Object value = field.get(object);  // Obtener el valor del campo
                    map.put(field.getName(), value != null ? value.toString() : "");  // Asignar el valor al map
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();  // Moverse a la clase padre en caso de herencia
        }
        
        return map;
    }
}
