package com.shadowphase.magitech.lib.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shadowphase.magitech.init.ModItems;
import com.shadowphase.magitech.lib.Constants;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class NameConverter {
    private static final String RESOURCE_FOLDER = "./1.11/src/main/resources/";

    private static final Map<String, Integer> fieldMap = new HashMap<String, Integer>();
    private static final Map<String, Integer> methodMap = new HashMap<String, Integer>();

    private NameConverter() {
    }

    static {
        InputStream readIn = null;
        Reader reader = null;
        try {
            if (Constants.DEV_ENV) {
                List<Class<?>> classList = new ArrayList<Class<?>>();
                classList.add(Items.class);
                classList.add(Blocks.class);
                classList.add(SoundType.class);
                classList.add(Material.class);
                createMapFile(classList, Constants.FIELD_MAP, SOURCE.FIELD);
                createMapFile(classList, Constants.METHOD_MAP, SOURCE.METHOD);
            } else {
                final ClassLoader loader = ModItems.class.getClassLoader();
                readIn = loader.getResourceAsStream(Constants.FIELD_MAP);
                reader = new InputStreamReader(readIn, "UTF-8");
                final NameConverterObject[] names = new Gson().fromJson(reader, NameConverterObject[].class);
                for (int i = 0; i < names.length; ++i) {
                    fieldMap.put(names[i].getName(), names[i].getId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (readIn != null) {
                    readIn.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getField(Class<?> c, String fieldName) {
        Object obj = null;
        try {
            final Field field = c.getField(fieldName);
            obj = field.get(null);
        } catch (NoSuchFieldException e) {
            try {
                final Integer fieldId = convertString(fieldName, SOURCE.FIELD);
                if (fieldId == null) {
                    throw new NoSuchFieldException(fieldName);
                }
                final String convertedFieldName = c.getFields()[fieldId].getName();
                final Field field = c.getField(convertedFieldName);
                obj = field.get(null);
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Method getMethod(Class<?> c, String methodName, Class<?>... params) {
        Method method = null;
        try {
            method = c.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            try {
                final Integer methodId = convertString(methodName, SOURCE.FIELD);
                if (methodId == null) {
                    throw new NoSuchMethodException(methodName);
                }
                final String convertedMethodName = c.getMethods()[methodId].getName();
                method = c.getMethod(convertedMethodName, params);
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return method;
    }

    private static Integer convertString(String name, SOURCE source) {
        switch (source) {
        case FIELD:
            return fieldMap.get(name);
        case METHOD:
            return methodMap.get(name);
        default:
            return -1;
        }
    }

    private static void createMapFile(List<Class<?>> classes, String fileName, SOURCE source) {
        System.out.println("CREATING NAME MAP FILE AT: " + RESOURCE_FOLDER + fileName);
        List<NameConverterObject> objectList = new ArrayList<NameConverterObject>();
        Iterator<Class<?>> it = classes.iterator();
        while (it.hasNext()) {
            switch (source) {
            case FIELD:
                addFields(it.next(), objectList);
                break;
            case METHOD:
                addMethods(it.next(), objectList);
                break;
            }
        }

        FileWriter writer = null;
        try {
            File file = new File(RESOURCE_FOLDER + fileName);
            file.getParentFile().mkdirs();
            writer = new FileWriter(file);
            Gson gson = new GsonBuilder().create();
            gson.toJson(objectList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void addFields(Class<?> c, List<NameConverterObject> fieldList) {
        Field[] fields = c.getFields();
        for (int i = 0; i < fields.length; ++i) {
            NameConverterObject obj = new NameConverterObject();
            obj.setName(fields[i].getName());
            obj.setId(i);
            fieldList.add(obj);
        }
    }

    private static void addMethods(Class<?> c, List<NameConverterObject> fieldList) {
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            NameConverterObject obj = new NameConverterObject();
            obj.setName(methods[i].getName());
            obj.setId(i);
            fieldList.add(obj);
        }
    }
}

enum SOURCE {
    FIELD, METHOD
}
