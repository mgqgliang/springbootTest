package com.example.springboottest.util;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson2.JSONObject;
import com.example.springboottest.bean.Student;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntityUtils {

    public static <T, S> T useVoToEntity(T target, S data) {
        return useVoToEntity(target, data, null);
    }

    public static <T, S> T useVoToEntity(T target, S data, String[] ignoreFields) {
        try {
            if (data == null) return null;
            // 获取集合中 对象的映射类 ，再获取其字段属性
            Class<?> clazz = data.getClass();
            List<Field> fields = new ArrayList<Field>();
            while (clazz != null) {
                fields.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                clazz = clazz.getSuperclass();
            }
            // 目标是将 TT 转换成 T
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID") || field.getName().equals("id"))
                    continue;
                if (ignoreFields != null && Arrays.asList(ignoreFields).contains(field.getName()))
                    continue;
                Object value = getFieldValueByName(field.getName(), data);
                if (value == null)
                    continue;
                Type targetType = getFieldTypeByName(field.getName(), target);
                Type oriType = getFieldTypeByName(field.getName(), data);
                if (targetType == null || !targetType.getTypeName().equals(oriType.getTypeName())) {
                    continue;
                }
                if (targetType.getTypeName().equals(field.getType().getName())) {
                    BeanUtils.setProperty(target, field.getName(), value);
                } else if (targetType.getTypeName().equals("java.util.List")) {
                    if (StringUtils.startsWith((CharSequence) value, "[")
                            && StringUtils.endsWith((CharSequence) value, "]")) {
                        // 认定是存储为字符串形式的List
                        BeanUtils.setProperty(target, field.getName(), new ArrayList<>(
                                Arrays.asList(StringUtils.substring((String) value, 1, -1).split(","))));
                    }
                } else if (field.getType().getName().equals("java.util.Date")) {
                    // vo 中不可以出现Date 格式字段 如果基础类型是Date 那么进行判定
                    if (value != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
                    }
                    BeanUtils.setProperty(target, field.getName(), value);
                } else
                    BeanUtils.setProperty(target, field.getName(), value);

            }
            return target;
        } catch (InvocationTargetException e) {
            // System.out.println("---Exception:00101:" + e.getMessage());
        } catch (IllegalAccessException e) {
            // System.out.println("---Exception:00103:" + e.getMessage());
        }

        return target;

    }

    /**
     * 将数据集合datas 进行类型转换，需要确认转换前类型与转换后类型
     */
    public static <T, S> List<T> getEntityToVo(Class<T> clazz, List<S> datas) {

        List<T> resultList = new ArrayList<T>();
        if (datas == null || datas.size() < 1)
            return null;
        for (S tt : datas) {
            // 获取集合中 对象的映射类 ，再获取其字段属性
            T vo = getEntityToVo(clazz, tt);
            resultList.add(vo);
        }

        return resultList;
    }

    /**
     * 将数据data 进行类型转换，需要确认转换前类型与转换后类型
     *
     * @param clazz
     * @param data
     * @return
     */
    public static <T, S> T getEntityToVo(Class<T> clazz, S data) {

        T vo = null;
        try {
            vo = clazz.newInstance();
            if (data == null)
                return null;
            // 获取集合中 对象的映射类 ，再获取其字段属性
            Class<?> claxx = data.getClass();
            List<Field> fields = new ArrayList<Field>();
            while (claxx != null) {
                fields.addAll(new ArrayList<>(Arrays.asList(claxx.getDeclaredFields())));
                claxx = claxx.getSuperclass();
            }
            // 目标是将 TT 转换成 T
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID"))
                    continue;
                Object value = getFieldValueByName(field.getName(), data);
                Type t = getFieldTypeByName(field.getName(), vo);
                if (t == null) {
                    continue;
                }
                if (t.getTypeName().equals(field.getType().getName())) {
                    BeanUtils.setProperty(vo, field.getName(), value);
                } else if (t.getTypeName().equals("java.util.List")) {
                    if (StringUtils.startsWith((CharSequence) value, "[")
                            && StringUtils.endsWith((CharSequence) value, "]")) {
                        // 认定是存储为字符串形式的List
                        BeanUtils.setProperty(vo, field.getName(), new ArrayList<>(
                                Arrays.asList(StringUtils.substring((String) value, 1, -1).split(","))));
                    }
                } else if (field.getType().getName().equals("java.util.Date")) {
                    // vo 中不可以出现Date 格式字段 如果基础类型是Date 那么进行判定
                    if (value != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
                    }
                    BeanUtils.setProperty(vo, field.getName(), value);
                } else
                    BeanUtils.setProperty(vo, field.getName(), value);

            }
            BeanUtils.setProperty(vo, "id", getFieldValueByName("objId", data));
            return vo;
        } catch (InvocationTargetException e) {
            // System.out.println("---Exception:00101:" + e.getMessage());
        } catch (InstantiationException e) {
            // System.out.println("---Exception:00102:" + e.getMessage());
        } catch (IllegalAccessException e) {
            // System.out.println("---Exception:00103:" + e.getMessage());
        }

        return vo;
    }

    /**
     * 将无指定类型的集合数据datas 进行类型转换，需要确认转换后类型
     */
    public static <T> List<T> getMapToVo(Class<T> clazz, List<Map<String, Object>> datas) {

        List<T> resultList = new ArrayList<T>();
        if (datas == null || datas.size() < 1)
            return null;

        for (Map<String, Object> map : datas) {
            // 获取集合中 对象的映射类 ，再获取其字段属性
            resultList.add(getMapToVo(clazz, map));
        }

        return resultList;
    }

    /**
     * 将无指定类型的数据data 进行类型转换，需要确认转换后类型
     */
    public static <T> T getMapToVo(Class<T> clazz, Map<String, ?> data) {

        T vo = null;
        try {
            if (data == null)
                return null;
            vo = clazz.newInstance();
            Iterator<String> names = data.keySet().iterator();
            while (names.hasNext()) {
                String name = names.next();
                if (name == null)
                    continue;
                if (PropertyUtils.getPropertyType(clazz, name) == null)
                    continue;
                Object value = data.get(name);
                Type t = getFieldTypeByName(name, vo);
                if (t.getTypeName().equals("java.lang.String")) {
                    BeanUtils.setProperty(vo, name, value);
                } else if (t.getTypeName().equals("java.util.List")) {
                    if (StringUtils.startsWith((CharSequence) value, "[")
                            && StringUtils.endsWith((CharSequence) value, "]")) {
                        // 认定是存储为字符串形式的List
                        BeanUtils.setProperty(vo, name, new ArrayList<>(
                                Arrays.asList(StringUtils.substring((String) value, 1, -1).split(","))));
                    }
                } else
                    BeanUtils.setProperty(vo, name, value);
            }
            BeanUtils.setProperty(vo, "id", data.get("OBJ_ID"));
        } catch (InvocationTargetException e) {
            // System.out.println("---Exception:00201:" + e.getMessage());
        } catch (IllegalAccessException e) {
            // System.out.println("---Exception:00203:" + e.getMessage());
        } catch (NoSuchMethodException e) {
            // System.out.println("---Exception:00204:" + e.getMessage());
        } catch (InstantiationException e) {
            // System.out.println("---Exception:00205:" + e.getMessage());
        }

        return vo;
    }

    public static Map<String, Object> getVoToMap(Object data) {
        if (data == null)
            return new HashMap<>(16);
        ;
        Field[] fields = data.getClass().getDeclaredFields();
        Map<String, Object> m = new HashMap<>(16);
        for (Field f : fields) {
            if (f.getType().getName().equals("java.lang.String")) {
                m.put(f.getName(), getFieldValueByName(f.getName(), data));
            } else if (f.getType().getName().equals("java.lang.Integer")) {
                Integer d = (Integer) getFieldValueByName(f.getName(), data);
                m.put(f.getName(), getFieldValueByName(f.getName(),d.toString()));
            } else if (f.getType().getName().equals("java.lang.BigDecimal")) {
                BigDecimal d = (BigDecimal) getFieldValueByName(f.getName(), data);
                m.put(f.getName(), getFieldValueByName(f.getName(), d.toString()));
            } else if (f.getType().getName().equals("java.util.List")) {
                m.put(f.getName(), getFieldValueByName(f.getName(), data));
            }
        }
        return m;

    }

    /**
     * 私有方法，获取指定对象中，指定属性的值 需要有get 方法
     */
    private static Object getFieldValueByName(String fieldName, Object obj) {

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(obj, new Object[] {});
            return value;
        } catch (Exception e) {
            // System.out.println("---Exception:SystemTools00001:" +
            // e.getMessage());
            return null;
        }
    }

    /**
     * 私有方法，获取指定对象中，指定属性的值 需要有get 方法
     */
    private static Type getFieldTypeByName(String fieldName, Object obj) {

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[] {});
            Type t = method.getReturnType();
            return t;
        } catch (Exception e) {
            // System.out.println("---Exception:SystemTools00002:" +
            // e.getMessage());
            return null;
        }
    }

    /**
     * 将name转为小写，去除下划线，将下划线后的字母转为大写
     */
    public static String getNoLineStr(String name) {
        name = name.toLowerCase();
        while (name.indexOf("_") > -1) {
            String word = name.substring(name.indexOf("_") + 1, name.indexOf("_") + 2);
            name = name.replaceAll("_" + word, word.toUpperCase());
        }
        return name;
    }

    public static <T, S> T clone(Class<T> clazz, S data) {

        T entity = null;
        try {
            entity = clazz.newInstance();
            if (data == null)
                return null;
            // 获取集合中 对象的映射类 ，再获取其字段属性
            Class<?> claxx = data.getClass();
            List<Field> fields = new ArrayList<Field>();
            while (claxx != null) {
                fields.addAll(new ArrayList<>(Arrays.asList(claxx.getDeclaredFields())));
                claxx = claxx.getSuperclass();
            }
            // 目标是将 TT 转换成 T
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID") || field.getName().equals("id")
                        || field.getName().equals("objId"))
                    continue;
                Object value = getFieldValueByName(field.getName(), data);
                Type t = getFieldTypeByName(field.getName(), entity);
                if (t == null) {
                    continue;
                }
                if (t.getTypeName().equals(field.getType().getName())) {
                    BeanUtils.setProperty(entity, field.getName(), value);
                } else if (t.getTypeName().equals("java.util.List")) {
                    if (StringUtils.startsWith((CharSequence) value, "[")
                            && StringUtils.endsWith((CharSequence) value, "]")) {
                        // 认定是存储为字符串形式的List
                        BeanUtils.setProperty(entity, field.getName(), new ArrayList<>(
                                Arrays.asList(StringUtils.substring((String) value, 1, -1).split(","))));
                    }
                } else if (field.getType().getName().equals("java.util.Date")) {
                    // vo 中不可以出现Date 格式字段 如果基础类型是Date 那么进行判定
                    if (value != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
                    }
                    BeanUtils.setProperty(entity, field.getName(), value);
                } else
                    BeanUtils.setProperty(entity, field.getName(), value);

            }
            return entity;
        } catch (InvocationTargetException e) {
            // System.out.println("---Exception:00101:" + e.getMessage());
        } catch (InstantiationException e) {
            // System.out.println("---Exception:00102:" + e.getMessage());
        } catch (IllegalAccessException e) {
            // System.out.println("---Exception:00103:" + e.getMessage());
        }

        return entity;
    }

    public static String getMessageFromVo(Object objVo) {
        return JSONObject.toJSONString(objVo);
    }

    public static <T> T getVoFromMessage(Class<T> clazz, String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        T t = jsonObject.to(clazz);
        return t;
    }

}
