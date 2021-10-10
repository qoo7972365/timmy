/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import org.omg.CORBA.UserException;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExceptionHandlerImpl
/*     */   implements ExceptionHandler
/*     */ {
/*     */   private ExceptionRW[] rws;
/*     */   
/*     */   public static interface ExceptionRW
/*     */   {
/*     */     Class getExceptionClass();
/*     */     
/*     */     String getId();
/*     */     
/*     */     void write(OutputStream param1OutputStream, Exception param1Exception);
/*     */     
/*     */     Exception read(InputStream param1InputStream);
/*     */   }
/*     */   
/*     */   public abstract class ExceptionRWBase
/*     */     implements ExceptionRW
/*     */   {
/*     */     private Class cls;
/*     */     private String id;
/*     */     
/*     */     public ExceptionRWBase(Class param1Class) {
/*  77 */       this.cls = param1Class;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class getExceptionClass() {
/*  82 */       return this.cls;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getId() {
/*  87 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     void setId(String param1String) {
/*  92 */       this.id = param1String;
/*     */     }
/*     */   }
/*     */   
/*     */   public class ExceptionRWIDLImpl
/*     */     extends ExceptionRWBase
/*     */   {
/*     */     private Method readMethod;
/*     */     private Method writeMethod;
/*     */     
/*     */     public ExceptionRWIDLImpl(Class param1Class) {
/* 103 */       super(param1Class);
/*     */       Class<?> clazz;
/* 105 */       String str = param1Class.getName() + "Helper";
/* 106 */       ClassLoader classLoader = param1Class.getClassLoader();
/*     */ 
/*     */       
/*     */       try {
/* 110 */         clazz = Class.forName(str, true, classLoader);
/* 111 */         Method method = clazz.getDeclaredMethod("id", (Class[])null);
/* 112 */         setId((String)method.invoke(null, (Object[])null));
/* 113 */       } catch (Exception exception) {
/* 114 */         throw ExceptionHandlerImpl.this.wrapper.badHelperIdMethod(exception, str);
/*     */       } 
/*     */       
/*     */       try {
/* 118 */         Class[] arrayOfClass = { OutputStream.class, param1Class };
/*     */         
/* 120 */         this.writeMethod = clazz.getDeclaredMethod("write", arrayOfClass);
/*     */       }
/* 122 */       catch (Exception exception) {
/* 123 */         throw ExceptionHandlerImpl.this.wrapper.badHelperWriteMethod(exception, str);
/*     */       } 
/*     */       
/*     */       try {
/* 127 */         Class[] arrayOfClass = { InputStream.class };
/*     */         
/* 129 */         this.readMethod = clazz.getDeclaredMethod("read", arrayOfClass);
/* 130 */       } catch (Exception exception) {
/* 131 */         throw ExceptionHandlerImpl.this.wrapper.badHelperReadMethod(exception, str);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(OutputStream param1OutputStream, Exception param1Exception) {
/*     */       try {
/* 138 */         Object[] arrayOfObject = { param1OutputStream, param1Exception };
/* 139 */         this.writeMethod.invoke(null, arrayOfObject);
/* 140 */       } catch (Exception exception) {
/* 141 */         throw ExceptionHandlerImpl.this.wrapper.badHelperWriteMethod(exception, this.writeMethod
/* 142 */             .getDeclaringClass().getName());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Exception read(InputStream param1InputStream) {
/*     */       try {
/* 149 */         Object[] arrayOfObject = { param1InputStream };
/* 150 */         return (Exception)this.readMethod.invoke(null, arrayOfObject);
/* 151 */       } catch (Exception exception) {
/* 152 */         throw ExceptionHandlerImpl.this.wrapper.badHelperReadMethod(exception, this.readMethod
/* 153 */             .getDeclaringClass().getName());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class ExceptionRWRMIImpl
/*     */     extends ExceptionRWBase
/*     */   {
/*     */     public ExceptionRWRMIImpl(Class param1Class) {
/* 162 */       super(param1Class);
/* 163 */       setId(IDLNameTranslatorImpl.getExceptionId(param1Class));
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(OutputStream param1OutputStream, Exception param1Exception) {
/* 168 */       param1OutputStream.write_string(getId());
/* 169 */       param1OutputStream.write_value(param1Exception, getExceptionClass());
/*     */     }
/*     */ 
/*     */     
/*     */     public Exception read(InputStream param1InputStream) {
/* 174 */       param1InputStream.read_string();
/* 175 */       return (Exception)param1InputStream.read_value(getExceptionClass());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   private final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");
/*     */   
/*     */   public ExceptionHandlerImpl(Class[] paramArrayOfClass) {
/* 186 */     byte b1 = 0; byte b2;
/* 187 */     for (b2 = 0; b2 < paramArrayOfClass.length; b2++) {
/* 188 */       Class<?> clazz = paramArrayOfClass[b2];
/* 189 */       if (!RemoteException.class.isAssignableFrom(clazz)) {
/* 190 */         b1++;
/*     */       }
/*     */     } 
/* 193 */     this.rws = new ExceptionRW[b1];
/*     */     
/* 195 */     b2 = 0;
/* 196 */     for (byte b3 = 0; b3 < paramArrayOfClass.length; b3++) {
/* 197 */       Class<?> clazz = paramArrayOfClass[b3];
/* 198 */       if (!RemoteException.class.isAssignableFrom(clazz)) {
/* 199 */         ExceptionRWRMIImpl exceptionRWRMIImpl; ExceptionRWIDLImpl exceptionRWIDLImpl = null;
/* 200 */         if (UserException.class.isAssignableFrom(clazz)) {
/* 201 */           exceptionRWIDLImpl = new ExceptionRWIDLImpl(clazz);
/*     */         } else {
/* 203 */           exceptionRWRMIImpl = new ExceptionRWRMIImpl(clazz);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 224 */         this.rws[b2++] = exceptionRWRMIImpl;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int findDeclaredException(Class<?> paramClass) {
/* 231 */     for (byte b = 0; b < this.rws.length; b++) {
/* 232 */       Class clazz = this.rws[b].getExceptionClass();
/* 233 */       if (clazz.isAssignableFrom(paramClass)) {
/* 234 */         return b;
/*     */       }
/*     */     } 
/* 237 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findDeclaredException(String paramString) {
/* 242 */     for (byte b = 0; b < this.rws.length; b++) {
/*     */ 
/*     */       
/* 245 */       if (this.rws[b] == null) {
/* 246 */         return -1;
/*     */       }
/* 248 */       String str = this.rws[b].getId();
/* 249 */       if (paramString.equals(str)) {
/* 250 */         return b;
/*     */       }
/*     */     } 
/* 253 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDeclaredException(Class paramClass) {
/* 258 */     return (findDeclaredException(paramClass) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeException(OutputStream paramOutputStream, Exception paramException) {
/* 263 */     int i = findDeclaredException(paramException.getClass());
/* 264 */     if (i < 0) {
/* 265 */       throw this.wrapper.writeUndeclaredException(paramException, paramException
/* 266 */           .getClass().getName());
/*     */     }
/* 268 */     this.rws[i].write(paramOutputStream, paramException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception readException(ApplicationException paramApplicationException) {
/* 277 */     InputStream inputStream = (InputStream)paramApplicationException.getInputStream();
/* 278 */     String str = paramApplicationException.getId();
/* 279 */     int i = findDeclaredException(str);
/* 280 */     if (i < 0) {
/* 281 */       str = inputStream.read_string();
/* 282 */       UnexpectedException unexpectedException = new UnexpectedException(str);
/* 283 */       unexpectedException.initCause((Throwable)paramApplicationException);
/* 284 */       return unexpectedException;
/*     */     } 
/*     */     
/* 287 */     return this.rws[i].read(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ExceptionRW getRMIExceptionRW(Class paramClass) {
/* 293 */     return new ExceptionRWRMIImpl(paramClass);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/ExceptionHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */