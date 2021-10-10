/*      */ package org.omg.CORBA;
/*      */ 
/*      */ import com.sun.corba.se.impl.orb.ORBImpl;
/*      */ import com.sun.corba.se.impl.orb.ORBSingleton;
/*      */ import java.applet.Applet;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Properties;
/*      */ import org.omg.CORBA.ORBPackage.InconsistentTypeCode;
/*      */ import org.omg.CORBA.ORBPackage.InvalidName;
/*      */ import org.omg.CORBA.portable.OutputStream;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ORB
/*      */ {
/*      */   private static final String ORBClassKey = "org.omg.CORBA.ORBClass";
/*      */   private static final String ORBSingletonClassKey = "org.omg.CORBA.ORBSingletonClass";
/*      */   private static ORB singleton;
/*      */   
/*      */   private static String getSystemProperty(final String name) {
/*  193 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           public java.lang.Object run() {
/*  196 */             return System.getProperty(name);
/*      */           }
/*      */         });
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
/*      */   private static String getPropertyFromFile(final String name) {
/*  210 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           private Properties getFileProperties(String param1String) {
/*      */             try {
/*  214 */               File file = new File(param1String);
/*  215 */               if (!file.exists()) {
/*  216 */                 return null;
/*      */               }
/*  218 */               Properties properties = new Properties();
/*  219 */               FileInputStream fileInputStream = new FileInputStream(file);
/*      */               try {
/*  221 */                 properties.load(fileInputStream);
/*      */               } finally {
/*  223 */                 fileInputStream.close();
/*      */               } 
/*      */               
/*  226 */               return properties;
/*  227 */             } catch (Exception exception) {
/*  228 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           public java.lang.Object run() {
/*  233 */             String str1 = System.getProperty("user.home");
/*  234 */             String str2 = str1 + File.separator + "orb.properties";
/*      */             
/*  236 */             Properties properties = getFileProperties(str2);
/*      */             
/*  238 */             if (properties != null) {
/*  239 */               String str = properties.getProperty(name);
/*  240 */               if (str != null) {
/*  241 */                 return str;
/*      */               }
/*      */             } 
/*  244 */             String str3 = System.getProperty("java.home");
/*  245 */             str2 = str3 + File.separator + "lib" + File.separator + "orb.properties";
/*      */             
/*  247 */             properties = getFileProperties(str2);
/*      */             
/*  249 */             if (properties == null) {
/*  250 */               return null;
/*      */             }
/*  252 */             return properties.getProperty(name);
/*      */           }
/*      */         });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized ORB init() {
/*  286 */     if (singleton == null) {
/*  287 */       String str = getSystemProperty("org.omg.CORBA.ORBSingletonClass");
/*  288 */       if (str == null)
/*  289 */         str = getPropertyFromFile("org.omg.CORBA.ORBSingletonClass"); 
/*  290 */       if (str == null || str
/*  291 */         .equals("com.sun.corba.se.impl.orb.ORBSingleton")) {
/*  292 */         singleton = (ORB)new ORBSingleton();
/*      */       } else {
/*  294 */         singleton = create_impl(str);
/*      */       } 
/*      */     } 
/*  297 */     return singleton;
/*      */   }
/*      */   
/*      */   private static ORB create_impl(String paramString) {
/*  301 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  302 */     if (classLoader == null) {
/*  303 */       classLoader = ClassLoader.getSystemClassLoader();
/*      */     }
/*      */     try {
/*  306 */       ReflectUtil.checkPackageAccess(paramString);
/*  307 */       Class<ORB> clazz = ORB.class;
/*  308 */       Class<? extends ORB> clazz1 = Class.forName(paramString, true, classLoader).asSubclass(clazz);
/*  309 */       return clazz1.newInstance();
/*  310 */     } catch (Throwable throwable) {
/*  311 */       INITIALIZE iNITIALIZE = new INITIALIZE("can't instantiate default ORB implementation " + paramString);
/*      */       
/*  313 */       iNITIALIZE.initCause(throwable);
/*  314 */       throw iNITIALIZE;
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
/*      */   public static ORB init(String[] paramArrayOfString, Properties paramProperties) {
/*      */     ORB oRB;
/*  338 */     String str = null;
/*      */ 
/*      */     
/*  341 */     if (paramProperties != null)
/*  342 */       str = paramProperties.getProperty("org.omg.CORBA.ORBClass"); 
/*  343 */     if (str == null)
/*  344 */       str = getSystemProperty("org.omg.CORBA.ORBClass"); 
/*  345 */     if (str == null)
/*  346 */       str = getPropertyFromFile("org.omg.CORBA.ORBClass"); 
/*  347 */     if (str == null || str
/*  348 */       .equals("com.sun.corba.se.impl.orb.ORBImpl")) {
/*  349 */       ORBImpl oRBImpl = new ORBImpl();
/*      */     } else {
/*  351 */       oRB = create_impl(str);
/*      */     } 
/*  353 */     oRB.set_parameters(paramArrayOfString, paramProperties);
/*  354 */     return oRB;
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
/*      */   public static ORB init(Applet paramApplet, Properties paramProperties) {
/*      */     ORB oRB;
/*  370 */     String str = paramApplet.getParameter("org.omg.CORBA.ORBClass");
/*  371 */     if (str == null && paramProperties != null)
/*  372 */       str = paramProperties.getProperty("org.omg.CORBA.ORBClass"); 
/*  373 */     if (str == null)
/*  374 */       str = getSystemProperty("org.omg.CORBA.ORBClass"); 
/*  375 */     if (str == null)
/*  376 */       str = getPropertyFromFile("org.omg.CORBA.ORBClass"); 
/*  377 */     if (str == null || str
/*  378 */       .equals("com.sun.corba.se.impl.orb.ORBImpl")) {
/*  379 */       ORBImpl oRBImpl = new ORBImpl();
/*      */     } else {
/*  381 */       oRB = create_impl(str);
/*      */     } 
/*  383 */     oRB.set_parameters(paramApplet, paramProperties);
/*  384 */     return oRB;
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
/*      */   protected abstract void set_parameters(String[] paramArrayOfString, Properties paramProperties);
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
/*      */   protected abstract void set_parameters(Applet paramApplet, Properties paramProperties);
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
/*      */   public void connect(Object paramObject) {
/*  432 */     throw new NO_IMPLEMENT();
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
/*      */   public void destroy() {
/*  454 */     throw new NO_IMPLEMENT();
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
/*      */   public void disconnect(Object paramObject) {
/*  476 */     throw new NO_IMPLEMENT();
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
/*      */   public abstract String[] list_initial_services();
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
/*      */   public abstract Object resolve_initial_references(String paramString) throws InvalidName;
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
/*      */   public abstract String object_to_string(Object paramObject);
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
/*      */   public abstract Object string_to_object(String paramString);
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
/*      */   public abstract NVList create_list(int paramInt);
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
/*      */   public NVList create_operation_list(Object paramObject) {
/*      */     try {
/*  577 */       String str = "org.omg.CORBA.OperationDef";
/*  578 */       Class<?> clazz = null;
/*      */       
/*  580 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  581 */       if (classLoader == null) {
/*  582 */         classLoader = ClassLoader.getSystemClassLoader();
/*      */       }
/*  584 */       clazz = Class.forName(str, true, classLoader);
/*      */ 
/*      */ 
/*      */       
/*  588 */       Class[] arrayOfClass = { clazz };
/*      */       
/*  590 */       Method method = getClass().getMethod("create_operation_list", arrayOfClass);
/*      */ 
/*      */       
/*  593 */       java.lang.Object[] arrayOfObject = { paramObject };
/*  594 */       return (NVList)method.invoke(this, arrayOfObject);
/*      */     }
/*  596 */     catch (InvocationTargetException invocationTargetException) {
/*  597 */       Throwable throwable = invocationTargetException.getTargetException();
/*  598 */       if (throwable instanceof Error) {
/*  599 */         throw (Error)throwable;
/*      */       }
/*  601 */       if (throwable instanceof RuntimeException) {
/*  602 */         throw (RuntimeException)throwable;
/*      */       }
/*      */       
/*  605 */       throw new NO_IMPLEMENT();
/*      */     
/*      */     }
/*  608 */     catch (RuntimeException runtimeException) {
/*  609 */       throw runtimeException;
/*      */     }
/*  611 */     catch (Exception exception) {
/*  612 */       throw new NO_IMPLEMENT();
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
/*      */   public abstract NamedValue create_named_value(String paramString, Any paramAny, int paramInt);
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
/*      */   public abstract ExceptionList create_exception_list();
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
/*      */   public abstract ContextList create_context_list();
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
/*      */   public abstract Context get_default_context();
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
/*      */   public abstract Environment create_environment();
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
/*      */   public abstract OutputStream create_output_stream();
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
/*      */   public abstract void send_multiple_requests_oneway(Request[] paramArrayOfRequest);
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
/*      */   public abstract void send_multiple_requests_deferred(Request[] paramArrayOfRequest);
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
/*      */   public abstract boolean poll_next_response();
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
/*      */   public abstract Request get_next_response() throws WrongTransaction;
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
/*      */   public abstract TypeCode get_primitive_tc(TCKind paramTCKind);
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
/*      */   public abstract TypeCode create_struct_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember);
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
/*      */   public abstract TypeCode create_union_tc(String paramString1, String paramString2, TypeCode paramTypeCode, UnionMember[] paramArrayOfUnionMember);
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
/*      */   public abstract TypeCode create_enum_tc(String paramString1, String paramString2, String[] paramArrayOfString);
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
/*      */   public abstract TypeCode create_alias_tc(String paramString1, String paramString2, TypeCode paramTypeCode);
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
/*      */   public abstract TypeCode create_exception_tc(String paramString1, String paramString2, StructMember[] paramArrayOfStructMember);
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
/*      */   public abstract TypeCode create_interface_tc(String paramString1, String paramString2);
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
/*      */   public abstract TypeCode create_string_tc(int paramInt);
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
/*      */   public abstract TypeCode create_wstring_tc(int paramInt);
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
/*      */   public abstract TypeCode create_sequence_tc(int paramInt, TypeCode paramTypeCode);
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
/*      */   @Deprecated
/*      */   public abstract TypeCode create_recursive_sequence_tc(int paramInt1, int paramInt2);
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
/*      */   public abstract TypeCode create_array_tc(int paramInt, TypeCode paramTypeCode);
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
/*      */   public TypeCode create_native_tc(String paramString1, String paramString2) {
/*  902 */     throw new NO_IMPLEMENT();
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
/*      */   public TypeCode create_abstract_interface_tc(String paramString1, String paramString2) {
/*  916 */     throw new NO_IMPLEMENT();
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
/*      */   public TypeCode create_fixed_tc(short paramShort1, short paramShort2) {
/*  930 */     throw new NO_IMPLEMENT();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeCode create_value_tc(String paramString1, String paramString2, short paramShort, TypeCode paramTypeCode, ValueMember[] paramArrayOfValueMember) {
/*  959 */     throw new NO_IMPLEMENT();
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
/*      */   public TypeCode create_recursive_tc(String paramString) {
/* 1003 */     throw new NO_IMPLEMENT();
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
/*      */   public TypeCode create_value_box_tc(String paramString1, String paramString2, TypeCode paramTypeCode) {
/* 1019 */     throw new NO_IMPLEMENT();
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
/*      */   public abstract Any create_any();
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
/*      */   @Deprecated
/*      */   public Current get_current() {
/* 1050 */     throw new NO_IMPLEMENT();
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
/*      */   public void run() {
/* 1062 */     throw new NO_IMPLEMENT();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown(boolean paramBoolean) {
/* 1096 */     throw new NO_IMPLEMENT();
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
/*      */   public boolean work_pending() {
/* 1112 */     throw new NO_IMPLEMENT();
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
/*      */   public void perform_work() {
/* 1126 */     throw new NO_IMPLEMENT();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean get_service_information(short paramShort, ServiceInformationHolder paramServiceInformationHolder) {
/* 1156 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynAny create_dyn_any(Any paramAny) {
/* 1176 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynAny create_basic_dyn_any(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1196 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynStruct create_dyn_struct(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1216 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynSequence create_dyn_sequence(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1236 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynArray create_dyn_array(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1257 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynUnion create_dyn_union(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1277 */     throw new NO_IMPLEMENT();
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
/*      */   @Deprecated
/*      */   public DynEnum create_dyn_enum(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 1297 */     throw new NO_IMPLEMENT();
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
/*      */   
/*      */   public Policy create_policy(int paramInt, Any paramAny) throws PolicyError {
/* 1323 */     throw new NO_IMPLEMENT();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */