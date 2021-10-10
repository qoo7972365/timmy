/*      */ package java.beans;
/*      */ 
/*      */ import com.sun.beans.finder.PrimitiveWrapperMap;
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Choice;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.List;
/*      */ import java.awt.Menu;
/*      */ import java.awt.MenuBar;
/*      */ import java.awt.MenuShortcut;
/*      */ import java.awt.Window;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationHandler;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.EnumMap;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JLayeredPane;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.border.MatteBorder;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class MetaData
/*      */ {
/*      */   static final class NullPersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {}
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*   80 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeObject(Object param1Object, Encoder param1Encoder) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class EnumPersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*   94 */       return (param1Object1 == param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*   98 */       Enum enum_ = (Enum)param1Object;
/*   99 */       return new Expression(enum_, Enum.class, "valueOf", new Object[] { enum_.getDeclaringClass(), enum_.name() });
/*      */     }
/*      */   }
/*      */   
/*      */   static final class PrimitivePersistenceDelegate extends PersistenceDelegate {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  105 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  109 */       return new Expression(param1Object, param1Object.getClass(), "new", new Object[] { param1Object
/*  110 */             .toString() });
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ArrayPersistenceDelegate extends PersistenceDelegate {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  116 */       return (param1Object2 != null && param1Object1
/*  117 */         .getClass() == param1Object2.getClass() && 
/*  118 */         Array.getLength(param1Object1) == Array.getLength(param1Object2));
/*      */     }
/*      */ 
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  123 */       Class<?> clazz = param1Object.getClass();
/*  124 */       return new Expression(param1Object, Array.class, "newInstance", new Object[] { clazz
/*  125 */             .getComponentType(), new Integer(
/*  126 */               Array.getLength(param1Object)) });
/*      */     }
/*      */     
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  130 */       int i = Array.getLength(param1Object1);
/*  131 */       for (byte b = 0; b < i; b++) {
/*  132 */         Integer integer = new Integer(b);
/*      */ 
/*      */         
/*  135 */         Expression expression1 = new Expression(param1Object1, "get", new Object[] { integer });
/*  136 */         Expression expression2 = new Expression(param1Object2, "get", new Object[] { integer });
/*      */         try {
/*  138 */           Object object1 = expression1.getValue();
/*  139 */           Object object2 = expression2.getValue();
/*  140 */           param1Encoder.writeExpression(expression1);
/*  141 */           if (!Objects.equals(object2, param1Encoder.get(object1)))
/*      */           {
/*      */             
/*  144 */             DefaultPersistenceDelegate.invokeStatement(param1Object1, "set", new Object[] { integer, object1 }, param1Encoder);
/*      */           }
/*      */         }
/*  147 */         catch (Exception exception) {
/*      */           
/*  149 */           param1Encoder.getExceptionListener().exceptionThrown(exception);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ProxyPersistenceDelegate extends PersistenceDelegate {
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  157 */       Class<?> clazz = param1Object.getClass();
/*  158 */       Proxy proxy = (Proxy)param1Object;
/*      */ 
/*      */       
/*  161 */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
/*  162 */       if (invocationHandler instanceof EventHandler) {
/*  163 */         EventHandler eventHandler = (EventHandler)invocationHandler;
/*  164 */         Vector<Class<?>> vector = new Vector();
/*  165 */         vector.add(clazz.getInterfaces()[0]);
/*  166 */         vector.add(eventHandler.getTarget());
/*  167 */         vector.add(eventHandler.getAction());
/*  168 */         if (eventHandler.getEventPropertyName() != null) {
/*  169 */           vector.add(eventHandler.getEventPropertyName());
/*      */         }
/*  171 */         if (eventHandler.getListenerMethodName() != null) {
/*  172 */           vector.setSize(4);
/*  173 */           vector.add(eventHandler.getListenerMethodName());
/*      */         } 
/*  175 */         return new Expression(param1Object, EventHandler.class, "create", vector
/*      */ 
/*      */             
/*  178 */             .toArray());
/*      */       } 
/*  180 */       return new Expression(param1Object, Proxy.class, "newProxyInstance", new Object[] { clazz
/*      */ 
/*      */             
/*  183 */             .getClassLoader(), clazz
/*  184 */             .getInterfaces(), invocationHandler });
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_lang_String_PersistenceDelegate
/*      */     extends PersistenceDelegate {
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  191 */       return null;
/*      */     }
/*      */     
/*      */     public void writeObject(Object param1Object, Encoder param1Encoder) {}
/*      */   }
/*      */   
/*      */   static final class java_lang_Class_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  201 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  205 */       Class clazz = (Class)param1Object;
/*      */ 
/*      */ 
/*      */       
/*  209 */       if (clazz.isPrimitive()) {
/*  210 */         Field field = null;
/*      */         try {
/*  212 */           field = PrimitiveWrapperMap.getType(clazz.getName()).getDeclaredField("TYPE");
/*  213 */         } catch (NoSuchFieldException noSuchFieldException) {
/*  214 */           System.err.println("Unknown primitive type: " + clazz);
/*      */         } 
/*  216 */         return new Expression(param1Object, field, "get", new Object[] { null });
/*      */       } 
/*  218 */       if (param1Object == String.class) {
/*  219 */         return new Expression(param1Object, "", "getClass", new Object[0]);
/*      */       }
/*  221 */       if (param1Object == Class.class) {
/*  222 */         return new Expression(param1Object, String.class, "getClass", new Object[0]);
/*      */       }
/*      */       
/*  225 */       Expression expression = new Expression(param1Object, Class.class, "forName", new Object[] { clazz.getName() });
/*  226 */       expression.loader = clazz.getClassLoader();
/*  227 */       return expression;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_lang_reflect_Field_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  235 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  239 */       Field field = (Field)param1Object;
/*  240 */       return new Expression(param1Object, field
/*  241 */           .getDeclaringClass(), "getField", new Object[] { field
/*      */             
/*  243 */             .getName() });
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_lang_reflect_Method_PersistenceDelegate
/*      */     extends PersistenceDelegate {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  250 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  254 */       Method method = (Method)param1Object;
/*  255 */       return new Expression(param1Object, method
/*  256 */           .getDeclaringClass(), "getMethod", new Object[] { method
/*      */             
/*  258 */             .getName(), method.getParameterTypes() });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class java_util_Date_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  274 */       if (!super.mutatesTo(param1Object1, param1Object2)) {
/*  275 */         return false;
/*      */       }
/*  277 */       Date date1 = (Date)param1Object1;
/*  278 */       Date date2 = (Date)param1Object2;
/*      */       
/*  280 */       return (date1.getTime() == date2.getTime());
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  284 */       Date date = (Date)param1Object;
/*  285 */       return new Expression(date, date.getClass(), "new", new Object[] { Long.valueOf(date.getTime()) });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_sql_Timestamp_PersistenceDelegate
/*      */     extends java_util_Date_PersistenceDelegate
/*      */   {
/*  296 */     private static final Method getNanosMethod = getNanosMethod();
/*      */     
/*      */     private static Method getNanosMethod() {
/*      */       try {
/*  300 */         Class<?> clazz = Class.forName("java.sql.Timestamp", true, null);
/*  301 */         return clazz.getMethod("getNanos", new Class[0]);
/*  302 */       } catch (ClassNotFoundException classNotFoundException) {
/*  303 */         return null;
/*  304 */       } catch (NoSuchMethodException noSuchMethodException) {
/*  305 */         throw new AssertionError(noSuchMethodException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int getNanos(Object param1Object) {
/*  313 */       if (getNanosMethod == null)
/*  314 */         throw new AssertionError("Should not get here"); 
/*      */       try {
/*  316 */         return ((Integer)getNanosMethod.invoke(param1Object, new Object[0])).intValue();
/*  317 */       } catch (InvocationTargetException invocationTargetException) {
/*  318 */         Throwable throwable = invocationTargetException.getCause();
/*  319 */         if (throwable instanceof RuntimeException)
/*  320 */           throw (RuntimeException)throwable; 
/*  321 */         if (throwable instanceof Error)
/*  322 */           throw (Error)throwable; 
/*  323 */         throw new AssertionError(invocationTargetException);
/*  324 */       } catch (IllegalAccessException illegalAccessException) {
/*  325 */         throw new AssertionError(illegalAccessException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  331 */       int i = getNanos(param1Object1);
/*  332 */       if (i != getNanos(param1Object2)) {
/*  333 */         param1Encoder.writeStatement(new Statement(param1Object1, "setNanos", new Object[] { Integer.valueOf(i) }));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class java_util_Collections
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     private java_util_Collections() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  361 */       if (!super.mutatesTo(param1Object1, param1Object2)) {
/*  362 */         return false;
/*      */       }
/*  364 */       if (param1Object1 instanceof List || param1Object1 instanceof Set || param1Object1 instanceof Map) {
/*  365 */         return param1Object1.equals(param1Object2);
/*      */       }
/*  367 */       Collection collection = (Collection)param1Object1;
/*  368 */       Collection<?> collection1 = (Collection)param1Object2;
/*  369 */       return (collection.size() == collection1.size() && collection.containsAll(collection1));
/*      */     }
/*      */     
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {}
/*      */     
/*      */     static final class EmptyList_PersistenceDelegate
/*      */       extends java_util_Collections
/*      */     {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  378 */         return new Expression(param2Object, Collections.class, "emptyList", null);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class EmptySet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  384 */         return new Expression(param2Object, Collections.class, "emptySet", null);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class EmptyMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  390 */         return new Expression(param2Object, Collections.class, "emptyMap", null);
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SingletonList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  396 */         List list = (List)param2Object;
/*  397 */         return new Expression(param2Object, Collections.class, "singletonList", new Object[] { list.get(0) });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SingletonSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  403 */         Set set = (Set)param2Object;
/*  404 */         return new Expression(param2Object, Collections.class, "singleton", new Object[] { set.iterator().next() });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SingletonMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  410 */         Map map = (Map)param2Object;
/*  411 */         Object object = map.keySet().iterator().next();
/*  412 */         return new Expression(param2Object, Collections.class, "singletonMap", new Object[] { object, map.get(object) });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableCollection_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  418 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  419 */         return new Expression(param2Object, Collections.class, "unmodifiableCollection", new Object[] { arrayList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  425 */         LinkedList linkedList = new LinkedList((Collection)param2Object);
/*  426 */         return new Expression(param2Object, Collections.class, "unmodifiableList", new Object[] { linkedList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableRandomAccessList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  432 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  433 */         return new Expression(param2Object, Collections.class, "unmodifiableList", new Object[] { arrayList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  439 */         HashSet hashSet = new HashSet((Set)param2Object);
/*  440 */         return new Expression(param2Object, Collections.class, "unmodifiableSet", new Object[] { hashSet });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableSortedSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  446 */         TreeSet treeSet = new TreeSet((SortedSet)param2Object);
/*  447 */         return new Expression(param2Object, Collections.class, "unmodifiableSortedSet", new Object[] { treeSet });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  453 */         HashMap<Object, Object> hashMap = new HashMap<>((Map<?, ?>)param2Object);
/*  454 */         return new Expression(param2Object, Collections.class, "unmodifiableMap", new Object[] { hashMap });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class UnmodifiableSortedMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  460 */         TreeMap<Object, Object> treeMap = new TreeMap<>((SortedMap<?, ?>)param2Object);
/*  461 */         return new Expression(param2Object, Collections.class, "unmodifiableSortedMap", new Object[] { treeMap });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedCollection_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  467 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  468 */         return new Expression(param2Object, Collections.class, "synchronizedCollection", new Object[] { arrayList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  474 */         LinkedList linkedList = new LinkedList((Collection)param2Object);
/*  475 */         return new Expression(param2Object, Collections.class, "synchronizedList", new Object[] { linkedList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedRandomAccessList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  481 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  482 */         return new Expression(param2Object, Collections.class, "synchronizedList", new Object[] { arrayList });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  488 */         HashSet hashSet = new HashSet((Set)param2Object);
/*  489 */         return new Expression(param2Object, Collections.class, "synchronizedSet", new Object[] { hashSet });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedSortedSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  495 */         TreeSet treeSet = new TreeSet((SortedSet)param2Object);
/*  496 */         return new Expression(param2Object, Collections.class, "synchronizedSortedSet", new Object[] { treeSet });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  502 */         HashMap<Object, Object> hashMap = new HashMap<>((Map<?, ?>)param2Object);
/*  503 */         return new Expression(param2Object, Collections.class, "synchronizedMap", new Object[] { hashMap });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class SynchronizedSortedMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  509 */         TreeMap<Object, Object> treeMap = new TreeMap<>((SortedMap<?, ?>)param2Object);
/*  510 */         return new Expression(param2Object, Collections.class, "synchronizedSortedMap", new Object[] { treeMap });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedCollection_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  516 */         Object object = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedCollection.type");
/*  517 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  518 */         return new Expression(param2Object, Collections.class, "checkedCollection", new Object[] { arrayList, object });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  524 */         Object object = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedCollection.type");
/*  525 */         LinkedList linkedList = new LinkedList((Collection)param2Object);
/*  526 */         return new Expression(param2Object, Collections.class, "checkedList", new Object[] { linkedList, object });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedRandomAccessList_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  532 */         Object object = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedCollection.type");
/*  533 */         ArrayList arrayList = new ArrayList((Collection)param2Object);
/*  534 */         return new Expression(param2Object, Collections.class, "checkedList", new Object[] { arrayList, object });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  540 */         Object object = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedCollection.type");
/*  541 */         HashSet hashSet = new HashSet((Set)param2Object);
/*  542 */         return new Expression(param2Object, Collections.class, "checkedSet", new Object[] { hashSet, object });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedSortedSet_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  548 */         Object object = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedCollection.type");
/*  549 */         TreeSet treeSet = new TreeSet((SortedSet)param2Object);
/*  550 */         return new Expression(param2Object, Collections.class, "checkedSortedSet", new Object[] { treeSet, object });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  556 */         Object object1 = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedMap.keyType");
/*  557 */         Object object2 = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedMap.valueType");
/*  558 */         HashMap<Object, Object> hashMap = new HashMap<>((Map<?, ?>)param2Object);
/*  559 */         return new Expression(param2Object, Collections.class, "checkedMap", new Object[] { hashMap, object1, object2 });
/*      */       }
/*      */     }
/*      */     
/*      */     static final class CheckedSortedMap_PersistenceDelegate extends java_util_Collections {
/*      */       protected Expression instantiate(Object param2Object, Encoder param2Encoder) {
/*  565 */         Object object1 = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedMap.keyType");
/*  566 */         Object object2 = MetaData.getPrivateFieldValue(param2Object, "java.util.Collections$CheckedMap.valueType");
/*  567 */         TreeMap<Object, Object> treeMap = new TreeMap<>((SortedMap<?, ?>)param2Object);
/*  568 */         return new Expression(param2Object, Collections.class, "checkedSortedMap", new Object[] { treeMap, object1, object2 });
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_util_EnumMap_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  580 */       return (super.mutatesTo(param1Object1, param1Object2) && getType(param1Object1) == getType(param1Object2));
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  584 */       return new Expression(param1Object, EnumMap.class, "new", new Object[] { getType(param1Object) });
/*      */     }
/*      */     
/*      */     private static Object getType(Object param1Object) {
/*  588 */       return MetaData.getPrivateFieldValue(param1Object, "java.util.EnumMap.keyType");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_util_EnumSet_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  599 */       return (super.mutatesTo(param1Object1, param1Object2) && getType(param1Object1) == getType(param1Object2));
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  603 */       return new Expression(param1Object, EnumSet.class, "noneOf", new Object[] { getType(param1Object) });
/*      */     }
/*      */     
/*      */     private static Object getType(Object param1Object) {
/*  607 */       return MetaData.getPrivateFieldValue(param1Object, "java.util.EnumSet.elementType");
/*      */     }
/*      */   }
/*      */   
/*      */   static class java_util_Collection_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  614 */       Collection collection1 = (Collection)param1Object1;
/*  615 */       Collection collection2 = (Collection)param1Object2;
/*      */       
/*  617 */       if (collection2.size() != 0) {
/*  618 */         invokeStatement(param1Object1, "clear", new Object[0], param1Encoder);
/*      */       }
/*  620 */       for (Iterator iterator = collection1.iterator(); iterator.hasNext();) {
/*  621 */         invokeStatement(param1Object1, "add", new Object[] { iterator.next() }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static class java_util_List_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  629 */       List list1 = (List)param1Object1;
/*  630 */       List list2 = (List)param1Object2;
/*  631 */       int i = list1.size();
/*  632 */       byte b1 = (list2 == null) ? 0 : list2.size();
/*  633 */       if (i < b1) {
/*  634 */         invokeStatement(param1Object1, "clear", new Object[0], param1Encoder);
/*  635 */         b1 = 0;
/*      */       }  byte b2;
/*  637 */       for (b2 = 0; b2 < b1; b2++) {
/*  638 */         Integer integer = new Integer(b2);
/*      */         
/*  640 */         Expression expression1 = new Expression(param1Object1, "get", new Object[] { integer });
/*  641 */         Expression expression2 = new Expression(param1Object2, "get", new Object[] { integer });
/*      */         try {
/*  643 */           Object object1 = expression1.getValue();
/*  644 */           Object object2 = expression2.getValue();
/*  645 */           param1Encoder.writeExpression(expression1);
/*  646 */           if (!Objects.equals(object2, param1Encoder.get(object1))) {
/*  647 */             invokeStatement(param1Object1, "set", new Object[] { integer, object1 }, param1Encoder);
/*      */           }
/*      */         }
/*  650 */         catch (Exception exception) {
/*  651 */           param1Encoder.getExceptionListener().exceptionThrown(exception);
/*      */         } 
/*      */       } 
/*  654 */       for (b2 = b1; b2 < i; b2++) {
/*  655 */         invokeStatement(param1Object1, "add", new Object[] { list1.get(b2) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class java_util_Map_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  665 */       Map map1 = (Map)param1Object1;
/*  666 */       Map map2 = (Map)param1Object2;
/*      */ 
/*      */       
/*  669 */       if (map2 != null) {
/*  670 */         for (Object object : map2.keySet().toArray()) {
/*      */           
/*  672 */           if (!map1.containsKey(object)) {
/*  673 */             invokeStatement(param1Object1, "remove", new Object[] { object }, param1Encoder);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  678 */       for (Object object : map1.keySet()) {
/*  679 */         Expression expression1 = new Expression(param1Object1, "get", new Object[] { object });
/*      */         
/*  681 */         Expression expression2 = new Expression(param1Object2, "get", new Object[] { object });
/*      */         try {
/*  683 */           Object object1 = expression1.getValue();
/*  684 */           Object object2 = expression2.getValue();
/*  685 */           param1Encoder.writeExpression(expression1);
/*  686 */           if (!Objects.equals(object2, param1Encoder.get(object1))) {
/*  687 */             invokeStatement(param1Object1, "put", new Object[] { object, object1 }, param1Encoder); continue;
/*  688 */           }  if (object2 == null && !map2.containsKey(object))
/*      */           {
/*  690 */             invokeStatement(param1Object1, "put", new Object[] { object, object1 }, param1Encoder);
/*      */           }
/*      */         }
/*  693 */         catch (Exception exception) {
/*  694 */           param1Encoder.getExceptionListener().exceptionThrown(exception);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class java_util_AbstractCollection_PersistenceDelegate
/*      */     extends java_util_Collection_PersistenceDelegate {}
/*      */   
/*      */   static final class java_util_AbstractList_PersistenceDelegate
/*      */     extends java_util_List_PersistenceDelegate {}
/*      */   
/*      */   static final class java_util_AbstractMap_PersistenceDelegate
/*      */     extends java_util_Map_PersistenceDelegate {}
/*      */   
/*      */   static final class java_util_Hashtable_PersistenceDelegate
/*      */     extends java_util_Map_PersistenceDelegate {}
/*      */   
/*      */   static final class java_beans_beancontext_BeanContextSupport_PersistenceDelegate
/*      */     extends java_util_Collection_PersistenceDelegate {}
/*      */   
/*      */   static final class java_awt_Insets_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  720 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  724 */       Insets insets = (Insets)param1Object;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  729 */       Object[] arrayOfObject = { Integer.valueOf(insets.top), Integer.valueOf(insets.left), Integer.valueOf(insets.bottom), Integer.valueOf(insets.right) };
/*      */       
/*  731 */       return new Expression(insets, insets.getClass(), "new", arrayOfObject);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_awt_Font_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  744 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  748 */       Font font = (Font)param1Object;
/*      */       
/*  750 */       byte b = 0;
/*  751 */       String str = null;
/*  752 */       int i = 0;
/*  753 */       int j = 12;
/*      */       
/*  755 */       Map<TextAttribute, ?> map = font.getAttributes();
/*  756 */       HashMap<Object, Object> hashMap = new HashMap<>(map.size());
/*  757 */       for (TextAttribute textAttribute : map.keySet()) {
/*  758 */         Object object = map.get(textAttribute);
/*  759 */         if (object != null) {
/*  760 */           hashMap.put(textAttribute, object);
/*      */         }
/*  762 */         if (textAttribute == TextAttribute.FAMILY) {
/*  763 */           if (object instanceof String) {
/*  764 */             b++;
/*  765 */             str = (String)object;
/*      */           }  continue;
/*      */         } 
/*  768 */         if (textAttribute == TextAttribute.WEIGHT) {
/*  769 */           if (TextAttribute.WEIGHT_REGULAR.equals(object)) {
/*  770 */             b++; continue;
/*  771 */           }  if (TextAttribute.WEIGHT_BOLD.equals(object)) {
/*  772 */             b++;
/*  773 */             i |= 0x1;
/*      */           }  continue;
/*      */         } 
/*  776 */         if (textAttribute == TextAttribute.POSTURE) {
/*  777 */           if (TextAttribute.POSTURE_REGULAR.equals(object)) {
/*  778 */             b++; continue;
/*  779 */           }  if (TextAttribute.POSTURE_OBLIQUE.equals(object)) {
/*  780 */             b++;
/*  781 */             i |= 0x2;
/*      */           }  continue;
/*  783 */         }  if (textAttribute == TextAttribute.SIZE && 
/*  784 */           object instanceof Number) {
/*  785 */           Number number = (Number)object;
/*  786 */           j = number.intValue();
/*  787 */           if (j == number.floatValue()) {
/*  788 */             b++;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  793 */       Class<?> clazz = font.getClass();
/*  794 */       if (b == hashMap.size()) {
/*  795 */         return new Expression(font, clazz, "new", new Object[] { str, Integer.valueOf(i), Integer.valueOf(j) });
/*      */       }
/*  797 */       if (clazz == Font.class) {
/*  798 */         return new Expression(font, clazz, "getFont", new Object[] { hashMap });
/*      */       }
/*  800 */       return new Expression(font, clazz, "new", new Object[] { Font.getFont((Map)hashMap) });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_awt_AWTKeyStroke_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  813 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  817 */       AWTKeyStroke aWTKeyStroke = (AWTKeyStroke)param1Object;
/*      */       
/*  819 */       char c = aWTKeyStroke.getKeyChar();
/*  820 */       int i = aWTKeyStroke.getKeyCode();
/*  821 */       int j = aWTKeyStroke.getModifiers();
/*  822 */       boolean bool = aWTKeyStroke.isOnKeyRelease();
/*      */       
/*  824 */       Object[] arrayOfObject = null;
/*  825 */       if (c == Character.MAX_VALUE) {
/*  826 */         (new Object[2])[0] = 
/*  827 */           Integer.valueOf(i); (new Object[2])[1] = Integer.valueOf(j); (new Object[3])[0] = 
/*  828 */           Integer.valueOf(i); (new Object[3])[1] = Integer.valueOf(j); (new Object[3])[2] = Boolean.valueOf(bool); arrayOfObject = !bool ? new Object[2] : new Object[3];
/*  829 */       } else if (i == 0) {
/*  830 */         if (!bool) {
/*  831 */           (new Object[1])[0] = 
/*  832 */             Character.valueOf(c); (new Object[2])[0] = 
/*  833 */             Character.valueOf(c); (new Object[2])[1] = Integer.valueOf(j); arrayOfObject = (j == 0) ? new Object[1] : new Object[2];
/*  834 */         } else if (j == 0) {
/*  835 */           arrayOfObject = new Object[] { Character.valueOf(c), Boolean.valueOf(bool) };
/*      */         } 
/*      */       } 
/*  838 */       if (arrayOfObject == null) {
/*  839 */         throw new IllegalStateException("Unsupported KeyStroke: " + aWTKeyStroke);
/*      */       }
/*  841 */       Class<?> clazz = aWTKeyStroke.getClass();
/*  842 */       String str = clazz.getName();
/*      */       
/*  844 */       int k = str.lastIndexOf('.') + 1;
/*  845 */       if (k > 0) {
/*  846 */         str = str.substring(k);
/*      */       }
/*  848 */       return new Expression(aWTKeyStroke, clazz, "get" + str, arrayOfObject);
/*      */     }
/*      */   }
/*      */   
/*      */   static class StaticFieldsPersistenceDelegate extends PersistenceDelegate {
/*      */     protected void installFields(Encoder param1Encoder, Class<?> param1Class) {
/*  854 */       if (Modifier.isPublic(param1Class.getModifiers()) && ReflectUtil.isPackageAccessible(param1Class)) {
/*  855 */         Field[] arrayOfField = param1Class.getFields();
/*  856 */         for (byte b = 0; b < arrayOfField.length; b++) {
/*  857 */           Field field = arrayOfField[b];
/*      */ 
/*      */           
/*  860 */           if (Object.class.isAssignableFrom(field.getType())) {
/*  861 */             param1Encoder.writeExpression(new Expression(field, "get", new Object[] { null }));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  868 */       throw new RuntimeException("Unrecognized instance: " + param1Object);
/*      */     }
/*      */     
/*      */     public void writeObject(Object param1Object, Encoder param1Encoder) {
/*  872 */       if (param1Encoder.getAttribute(this) == null) {
/*  873 */         param1Encoder.setAttribute(this, Boolean.TRUE);
/*  874 */         installFields(param1Encoder, param1Object.getClass());
/*      */       } 
/*  876 */       super.writeObject(param1Object, param1Encoder);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_SystemColor_PersistenceDelegate
/*      */     extends StaticFieldsPersistenceDelegate {}
/*      */   
/*      */   static final class java_awt_font_TextAttribute_PersistenceDelegate
/*      */     extends StaticFieldsPersistenceDelegate {}
/*      */   
/*      */   static final class java_awt_MenuShortcut_PersistenceDelegate
/*      */     extends PersistenceDelegate {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/*  889 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*  893 */       MenuShortcut menuShortcut = (MenuShortcut)param1Object;
/*  894 */       return new Expression(param1Object, menuShortcut.getClass(), "new", new Object[] { new Integer(menuShortcut
/*  895 */               .getKey()), Boolean.valueOf(menuShortcut.usesShiftModifier()) });
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_Component_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  902 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/*  903 */       Component component1 = (Component)param1Object1;
/*  904 */       Component component2 = (Component)param1Object2;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  909 */       if (!(param1Object1 instanceof Window)) {
/*  910 */         Color color1 = component1.isBackgroundSet() ? component1.getBackground() : null;
/*  911 */         Color color2 = component2.isBackgroundSet() ? component2.getBackground() : null;
/*  912 */         if (!Objects.equals(color1, color2)) {
/*  913 */           invokeStatement(param1Object1, "setBackground", new Object[] { color1 }, param1Encoder);
/*      */         }
/*  915 */         Color color3 = component1.isForegroundSet() ? component1.getForeground() : null;
/*  916 */         Color color4 = component2.isForegroundSet() ? component2.getForeground() : null;
/*  917 */         if (!Objects.equals(color3, color4)) {
/*  918 */           invokeStatement(param1Object1, "setForeground", new Object[] { color3 }, param1Encoder);
/*      */         }
/*  920 */         Font font1 = component1.isFontSet() ? component1.getFont() : null;
/*  921 */         Font font2 = component2.isFontSet() ? component2.getFont() : null;
/*  922 */         if (!Objects.equals(font1, font2)) {
/*  923 */           invokeStatement(param1Object1, "setFont", new Object[] { font1 }, param1Encoder);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  928 */       Container container = component1.getParent();
/*  929 */       if (container == null || container.getLayout() == null) {
/*      */         
/*  931 */         boolean bool1 = component1.getLocation().equals(component2.getLocation());
/*  932 */         boolean bool2 = component1.getSize().equals(component2.getSize());
/*  933 */         if (!bool1 && !bool2) {
/*  934 */           invokeStatement(param1Object1, "setBounds", new Object[] { component1.getBounds() }, param1Encoder);
/*      */         }
/*  936 */         else if (!bool1) {
/*  937 */           invokeStatement(param1Object1, "setLocation", new Object[] { component1.getLocation() }, param1Encoder);
/*      */         }
/*  939 */         else if (!bool2) {
/*  940 */           invokeStatement(param1Object1, "setSize", new Object[] { component1.getSize() }, param1Encoder);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_Container_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  949 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/*      */ 
/*      */       
/*  952 */       if (param1Object1 instanceof javax.swing.JScrollPane) {
/*      */         return;
/*      */       }
/*  955 */       Container container1 = (Container)param1Object1;
/*  956 */       Component[] arrayOfComponent1 = container1.getComponents();
/*  957 */       Container container2 = (Container)param1Object2;
/*  958 */       Component[] arrayOfComponent2 = (container2 == null) ? new Component[0] : container2.getComponents();
/*      */ 
/*      */       
/*  961 */       BorderLayout borderLayout = (container1.getLayout() instanceof BorderLayout) ? (BorderLayout)container1.getLayout() : null;
/*      */ 
/*      */       
/*  964 */       JLayeredPane jLayeredPane = (param1Object1 instanceof JLayeredPane) ? (JLayeredPane)param1Object1 : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  969 */       for (int i = arrayOfComponent2.length; i < arrayOfComponent1.length; i++) {
/*  970 */         (new Object[2])[0] = arrayOfComponent1[i]; (new Object[2])[1] = borderLayout
/*  971 */           .getConstraints(arrayOfComponent1[i]); (new Object[3])[0] = arrayOfComponent1[i]; (new Object[3])[1] = 
/*      */           
/*  973 */           Integer.valueOf(jLayeredPane.getLayer(arrayOfComponent1[i])); (new Object[3])[2] = Integer.valueOf(-1); (new Object[1])[0] = arrayOfComponent1[i]; Object[] arrayOfObject = (borderLayout != null) ? new Object[2] : ((jLayeredPane != null) ? new Object[3] : new Object[1]);
/*      */ 
/*      */         
/*  976 */         invokeStatement(param1Object1, "add", arrayOfObject, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_Choice_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  984 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/*  985 */       Choice choice1 = (Choice)param1Object1;
/*  986 */       Choice choice2 = (Choice)param1Object2;
/*  987 */       for (int i = choice2.getItemCount(); i < choice1.getItemCount(); i++) {
/*  988 */         invokeStatement(param1Object1, "add", new Object[] { choice1.getItem(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_Menu_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/*  996 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/*  997 */       Menu menu1 = (Menu)param1Object1;
/*  998 */       Menu menu2 = (Menu)param1Object2;
/*  999 */       for (int i = menu2.getItemCount(); i < menu1.getItemCount(); i++) {
/* 1000 */         invokeStatement(param1Object1, "add", new Object[] { menu1.getItem(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_MenuBar_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1008 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1009 */       MenuBar menuBar1 = (MenuBar)param1Object1;
/* 1010 */       MenuBar menuBar2 = (MenuBar)param1Object2;
/* 1011 */       for (int i = menuBar2.getMenuCount(); i < menuBar1.getMenuCount(); i++) {
/* 1012 */         invokeStatement(param1Object1, "add", new Object[] { menuBar1.getMenu(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_List_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1020 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1021 */       List list1 = (List)param1Object1;
/* 1022 */       List list2 = (List)param1Object2;
/* 1023 */       for (int i = list2.getItemCount(); i < list1.getItemCount(); i++) {
/* 1024 */         invokeStatement(param1Object1, "add", new Object[] { list1.getItem(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class java_awt_BorderLayout_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/* 1034 */     private static final String[] CONSTRAINTS = new String[] { "North", "South", "East", "West", "Center", "First", "Last", "Before", "After" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1048 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1049 */       BorderLayout borderLayout1 = (BorderLayout)param1Object1;
/* 1050 */       BorderLayout borderLayout2 = (BorderLayout)param1Object2;
/* 1051 */       for (String str : CONSTRAINTS) {
/* 1052 */         Component component1 = borderLayout1.getLayoutComponent(str);
/* 1053 */         Component component2 = borderLayout2.getLayoutComponent(str);
/*      */         
/* 1055 */         if (component1 != null && component2 == null) {
/* 1056 */           invokeStatement(param1Object1, "addLayoutComponent", new Object[] { component1, str }, param1Encoder);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class java_awt_CardLayout_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1067 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1068 */       if (getVector(param1Object2).isEmpty())
/* 1069 */         for (Object object : getVector(param1Object1)) {
/*      */           
/* 1071 */           Object[] arrayOfObject = { MetaData.getPrivateFieldValue(object, "java.awt.CardLayout$Card.name"), MetaData.getPrivateFieldValue(object, "java.awt.CardLayout$Card.comp") };
/* 1072 */           invokeStatement(param1Object1, "addLayoutComponent", arrayOfObject, param1Encoder);
/*      */         }  
/*      */     }
/*      */     
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/* 1077 */       return (super.mutatesTo(param1Object1, param1Object2) && getVector(param1Object2).isEmpty());
/*      */     }
/*      */     private static Vector<?> getVector(Object param1Object) {
/* 1080 */       return (Vector)MetaData.getPrivateFieldValue(param1Object, "java.awt.CardLayout.vector");
/*      */     }
/*      */   }
/*      */   
/*      */   static final class java_awt_GridBagLayout_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1088 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1089 */       if (getHashtable(param1Object2).isEmpty())
/* 1090 */         for (Map.Entry<?, ?> entry : getHashtable(param1Object1).entrySet()) {
/* 1091 */           Object[] arrayOfObject = { entry.getKey(), entry.getValue() };
/* 1092 */           invokeStatement(param1Object1, "addLayoutComponent", arrayOfObject, param1Encoder);
/*      */         }  
/*      */     }
/*      */     
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/* 1097 */       return (super.mutatesTo(param1Object1, param1Object2) && getHashtable(param1Object2).isEmpty());
/*      */     }
/*      */     private static Hashtable<?, ?> getHashtable(Object param1Object) {
/* 1100 */       return (Hashtable<?, ?>)MetaData.getPrivateFieldValue(param1Object, "java.awt.GridBagLayout.comptable");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class javax_swing_JFrame_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1111 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1112 */       Window window1 = (Window)param1Object1;
/* 1113 */       Window window2 = (Window)param1Object2;
/* 1114 */       boolean bool1 = window1.isVisible();
/* 1115 */       boolean bool2 = window2.isVisible();
/* 1116 */       if (bool2 != bool1) {
/*      */         
/* 1118 */         boolean bool = param1Encoder.executeStatements;
/* 1119 */         param1Encoder.executeStatements = false;
/* 1120 */         invokeStatement(param1Object1, "setVisible", new Object[] { Boolean.valueOf(bool1) }, param1Encoder);
/* 1121 */         param1Encoder.executeStatements = bool;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class javax_swing_DefaultListModel_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1132 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1133 */       DefaultListModel defaultListModel1 = (DefaultListModel)param1Object1;
/* 1134 */       DefaultListModel defaultListModel2 = (DefaultListModel)param1Object2;
/* 1135 */       for (int i = defaultListModel2.getSize(); i < defaultListModel1.getSize(); i++) {
/* 1136 */         invokeStatement(param1Object1, "add", new Object[] { defaultListModel1
/* 1137 */               .getElementAt(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class javax_swing_DefaultComboBoxModel_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1145 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1146 */       DefaultComboBoxModel defaultComboBoxModel = (DefaultComboBoxModel)param1Object1;
/* 1147 */       for (byte b = 0; b < defaultComboBoxModel.getSize(); b++) {
/* 1148 */         invokeStatement(param1Object1, "addElement", new Object[] { defaultComboBoxModel.getElementAt(b) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class javax_swing_tree_DefaultMutableTreeNode_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1158 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1159 */       DefaultMutableTreeNode defaultMutableTreeNode1 = (DefaultMutableTreeNode)param1Object1;
/*      */       
/* 1161 */       DefaultMutableTreeNode defaultMutableTreeNode2 = (DefaultMutableTreeNode)param1Object2;
/*      */       
/* 1163 */       for (int i = defaultMutableTreeNode2.getChildCount(); i < defaultMutableTreeNode1.getChildCount(); i++) {
/* 1164 */         invokeStatement(param1Object1, "add", new Object[] { defaultMutableTreeNode1
/* 1165 */               .getChildAt(i) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class javax_swing_ToolTipManager_PersistenceDelegate
/*      */     extends PersistenceDelegate {
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/* 1173 */       return new Expression(param1Object, ToolTipManager.class, "sharedInstance", new Object[0]);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class javax_swing_JTabbedPane_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1181 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1182 */       JTabbedPane jTabbedPane = (JTabbedPane)param1Object1;
/* 1183 */       for (byte b = 0; b < jTabbedPane.getTabCount(); b++) {
/* 1184 */         invokeStatement(param1Object1, "addTab", new Object[] { jTabbedPane
/*      */               
/* 1186 */               .getTitleAt(b), jTabbedPane
/* 1187 */               .getIconAt(b), jTabbedPane
/* 1188 */               .getComponentAt(b) }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class javax_swing_Box_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/* 1196 */       return (super.mutatesTo(param1Object1, param1Object2) && getAxis(param1Object1).equals(getAxis(param1Object2)));
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/* 1200 */       return new Expression(param1Object, param1Object.getClass(), "new", new Object[] { getAxis(param1Object) });
/*      */     }
/*      */     
/*      */     private Integer getAxis(Object param1Object) {
/* 1204 */       Box box = (Box)param1Object;
/* 1205 */       return (Integer)MetaData.getPrivateFieldValue(box.getLayout(), "javax.swing.BoxLayout.axis");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class javax_swing_JMenu_PersistenceDelegate
/*      */     extends DefaultPersistenceDelegate
/*      */   {
/*      */     protected void initialize(Class<?> param1Class, Object param1Object1, Object param1Object2, Encoder param1Encoder) {
/* 1217 */       super.initialize(param1Class, param1Object1, param1Object2, param1Encoder);
/* 1218 */       JMenu jMenu = (JMenu)param1Object1;
/* 1219 */       Component[] arrayOfComponent = jMenu.getMenuComponents();
/* 1220 */       for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 1221 */         invokeStatement(param1Object1, "add", new Object[] { arrayOfComponent[b] }, param1Encoder);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class javax_swing_border_MatteBorder_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/*      */       Color color;
/* 1235 */       MatteBorder matteBorder = (MatteBorder)param1Object;
/* 1236 */       Insets insets = matteBorder.getBorderInsets();
/* 1237 */       Icon icon = matteBorder.getTileIcon();
/* 1238 */       if (icon == null) {
/* 1239 */         color = matteBorder.getMatteColor();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1245 */       Object[] arrayOfObject = { Integer.valueOf(insets.top), Integer.valueOf(insets.left), Integer.valueOf(insets.bottom), Integer.valueOf(insets.right), color };
/*      */ 
/*      */       
/* 1248 */       return new Expression(matteBorder, matteBorder.getClass(), "new", arrayOfObject);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class sun_swing_PrintColorUIResource_PersistenceDelegate
/*      */     extends PersistenceDelegate
/*      */   {
/*      */     protected boolean mutatesTo(Object param1Object1, Object param1Object2) {
/* 1277 */       return param1Object1.equals(param1Object2);
/*      */     }
/*      */     
/*      */     protected Expression instantiate(Object param1Object, Encoder param1Encoder) {
/* 1281 */       Color color = (Color)param1Object;
/* 1282 */       Object[] arrayOfObject = { Integer.valueOf(color.getRGB()) };
/* 1283 */       return new Expression(color, ColorUIResource.class, "new", arrayOfObject);
/*      */     }
/*      */   }
/*      */   
/* 1287 */   private static final Map<String, Field> fields = Collections.synchronizedMap(new WeakHashMap<>());
/* 1288 */   private static Hashtable<String, PersistenceDelegate> internalPersistenceDelegates = new Hashtable<>();
/*      */   
/* 1290 */   private static PersistenceDelegate nullPersistenceDelegate = new NullPersistenceDelegate();
/* 1291 */   private static PersistenceDelegate enumPersistenceDelegate = new EnumPersistenceDelegate();
/* 1292 */   private static PersistenceDelegate primitivePersistenceDelegate = new PrimitivePersistenceDelegate();
/* 1293 */   private static PersistenceDelegate defaultPersistenceDelegate = new DefaultPersistenceDelegate();
/*      */   
/*      */   private static PersistenceDelegate arrayPersistenceDelegate;
/*      */   private static PersistenceDelegate proxyPersistenceDelegate;
/*      */   
/*      */   static {
/* 1299 */     internalPersistenceDelegates.put("java.net.URI", new PrimitivePersistenceDelegate());
/*      */ 
/*      */ 
/*      */     
/* 1303 */     internalPersistenceDelegates.put("javax.swing.plaf.BorderUIResource$MatteBorderUIResource", new javax_swing_border_MatteBorder_PersistenceDelegate());
/*      */ 
/*      */ 
/*      */     
/* 1307 */     internalPersistenceDelegates.put("javax.swing.plaf.FontUIResource", new java_awt_Font_PersistenceDelegate());
/*      */ 
/*      */ 
/*      */     
/* 1311 */     internalPersistenceDelegates.put("javax.swing.KeyStroke", new java_awt_AWTKeyStroke_PersistenceDelegate());
/*      */ 
/*      */     
/* 1314 */     internalPersistenceDelegates.put("java.sql.Date", new java_util_Date_PersistenceDelegate());
/* 1315 */     internalPersistenceDelegates.put("java.sql.Time", new java_util_Date_PersistenceDelegate());
/*      */     
/* 1317 */     internalPersistenceDelegates.put("java.util.JumboEnumSet", new java_util_EnumSet_PersistenceDelegate());
/* 1318 */     internalPersistenceDelegates.put("java.util.RegularEnumSet", new java_util_EnumSet_PersistenceDelegate());
/*      */   }
/*      */ 
/*      */   
/*      */   public static synchronized PersistenceDelegate getPersistenceDelegate(Class<?> paramClass) {
/* 1323 */     if (paramClass == null) {
/* 1324 */       return nullPersistenceDelegate;
/*      */     }
/* 1326 */     if (Enum.class.isAssignableFrom(paramClass)) {
/* 1327 */       return enumPersistenceDelegate;
/*      */     }
/* 1329 */     if (null != XMLEncoder.primitiveTypeFor(paramClass)) {
/* 1330 */       return primitivePersistenceDelegate;
/*      */     }
/*      */     
/* 1333 */     if (paramClass.isArray()) {
/* 1334 */       if (arrayPersistenceDelegate == null) {
/* 1335 */         arrayPersistenceDelegate = new ArrayPersistenceDelegate();
/*      */       }
/* 1337 */       return arrayPersistenceDelegate;
/*      */     } 
/*      */     
/*      */     try {
/* 1341 */       if (Proxy.isProxyClass(paramClass)) {
/* 1342 */         if (proxyPersistenceDelegate == null) {
/* 1343 */           proxyPersistenceDelegate = new ProxyPersistenceDelegate();
/*      */         }
/* 1345 */         return proxyPersistenceDelegate;
/*      */       }
/*      */     
/* 1348 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1353 */     String str = paramClass.getName();
/* 1354 */     PersistenceDelegate persistenceDelegate = (PersistenceDelegate)getBeanAttribute(paramClass, "persistenceDelegate");
/* 1355 */     if (persistenceDelegate == null) {
/* 1356 */       persistenceDelegate = internalPersistenceDelegates.get(str);
/* 1357 */       if (persistenceDelegate != null) {
/* 1358 */         return persistenceDelegate;
/*      */       }
/* 1360 */       internalPersistenceDelegates.put(str, defaultPersistenceDelegate);
/*      */       try {
/* 1362 */         String str1 = paramClass.getName();
/* 1363 */         Class<?> clazz = Class.forName("java.beans.MetaData$" + str1.replace('.', '_') + "_PersistenceDelegate");
/*      */         
/* 1365 */         persistenceDelegate = (PersistenceDelegate)clazz.newInstance();
/* 1366 */         internalPersistenceDelegates.put(str, persistenceDelegate);
/*      */       }
/* 1368 */       catch (ClassNotFoundException classNotFoundException) {
/* 1369 */         String[] arrayOfString = getConstructorProperties(paramClass);
/* 1370 */         if (arrayOfString != null) {
/* 1371 */           persistenceDelegate = new DefaultPersistenceDelegate(arrayOfString);
/* 1372 */           internalPersistenceDelegates.put(str, persistenceDelegate);
/*      */         }
/*      */       
/* 1375 */       } catch (Exception exception) {
/* 1376 */         System.err.println("Internal error: " + exception);
/*      */       } 
/*      */     } 
/*      */     
/* 1380 */     return (persistenceDelegate != null) ? persistenceDelegate : defaultPersistenceDelegate;
/*      */   }
/*      */   
/*      */   private static String[] getConstructorProperties(Class<?> paramClass) {
/* 1384 */     String[] arrayOfString = null;
/* 1385 */     int i = 0;
/* 1386 */     for (Constructor<?> constructor : paramClass.getConstructors()) {
/* 1387 */       String[] arrayOfString1 = getAnnotationValue(constructor);
/* 1388 */       if (arrayOfString1 != null && i < arrayOfString1.length && isValid(constructor, arrayOfString1)) {
/* 1389 */         arrayOfString = arrayOfString1;
/* 1390 */         i = arrayOfString1.length;
/*      */       } 
/*      */     } 
/* 1393 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   private static String[] getAnnotationValue(Constructor<?> paramConstructor) {
/* 1397 */     ConstructorProperties constructorProperties = paramConstructor.<ConstructorProperties>getAnnotation(ConstructorProperties.class);
/* 1398 */     return (constructorProperties != null) ? constructorProperties
/* 1399 */       .value() : null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValid(Constructor<?> paramConstructor, String[] paramArrayOfString) {
/* 1404 */     Class[] arrayOfClass = paramConstructor.getParameterTypes();
/* 1405 */     if (paramArrayOfString.length != arrayOfClass.length) {
/* 1406 */       return false;
/*      */     }
/* 1408 */     for (String str : paramArrayOfString) {
/* 1409 */       if (str == null) {
/* 1410 */         return false;
/*      */       }
/*      */     } 
/* 1413 */     return true;
/*      */   }
/*      */   
/*      */   private static Object getBeanAttribute(Class<?> paramClass, String paramString) {
/*      */     try {
/* 1418 */       return Introspector.getBeanInfo(paramClass).getBeanDescriptor().getValue(paramString);
/* 1419 */     } catch (IntrospectionException introspectionException) {
/* 1420 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   static Object getPrivateFieldValue(Object paramObject, String paramString) {
/* 1425 */     Field field = fields.get(paramString);
/* 1426 */     if (field == null) {
/* 1427 */       int i = paramString.lastIndexOf('.');
/* 1428 */       final String className = paramString.substring(0, i);
/* 1429 */       final String fieldName = paramString.substring(1 + i);
/* 1430 */       field = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>() {
/*      */             public Field run() {
/*      */               try {
/* 1433 */                 Field field = Class.forName(className).getDeclaredField(fieldName);
/* 1434 */                 field.setAccessible(true);
/* 1435 */                 return field;
/*      */               }
/* 1437 */               catch (ClassNotFoundException classNotFoundException) {
/* 1438 */                 throw new IllegalStateException("Could not find class", classNotFoundException);
/*      */               }
/* 1440 */               catch (NoSuchFieldException noSuchFieldException) {
/* 1441 */                 throw new IllegalStateException("Could not find field", noSuchFieldException);
/*      */               } 
/*      */             }
/*      */           });
/* 1445 */       fields.put(paramString, field);
/*      */     } 
/*      */     try {
/* 1448 */       return field.get(paramObject);
/*      */     }
/* 1450 */     catch (IllegalAccessException illegalAccessException) {
/* 1451 */       throw new IllegalStateException("Could not get value of the field", illegalAccessException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/MetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */