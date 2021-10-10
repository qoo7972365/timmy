/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.Vector;
/*     */ import javax.sql.rowset.RowSetWarning;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
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
/*     */ public class SerialJavaObject
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private Object obj;
/*     */   private transient Field[] fields;
/*     */   static final long serialVersionUID = -1465795139032831023L;
/*     */   Vector<RowSetWarning> chain;
/*     */   
/*     */   public SerialJavaObject(Object paramObject) throws SerialException {
/*  85 */     Class<?> clazz = paramObject.getClass();
/*     */ 
/*     */     
/*  88 */     if (!(paramObject instanceof Serializable)) {
/*  89 */       setWarning(new RowSetWarning("Warning, the object passed to the constructor does not implement Serializable"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.fields = clazz.getFields();
/*     */     
/*  98 */     if (hasStaticFields(this.fields)) {
/*  99 */       throw new SerialException("Located static fields in object instance. Cannot serialize");
/*     */     }
/*     */ 
/*     */     
/* 103 */     this.obj = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject() throws SerialException {
/* 115 */     return this.obj;
/*     */   }
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
/*     */   @CallerSensitive
/*     */   public Field[] getFields() throws SerialException {
/* 136 */     if (this.fields != null) {
/* 137 */       Class<?> clazz = this.obj.getClass();
/* 138 */       SecurityManager securityManager = System.getSecurityManager();
/* 139 */       if (securityManager != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 144 */         Class clazz1 = Reflection.getCallerClass();
/* 145 */         if (ReflectUtil.needsPackageAccessCheck(clazz1.getClassLoader(), clazz
/* 146 */             .getClassLoader())) {
/* 147 */           ReflectUtil.checkPackageAccess(clazz);
/*     */         }
/*     */       } 
/* 150 */       return clazz.getFields();
/*     */     } 
/* 152 */     throw new SerialException("SerialJavaObject does not contain a serialized object instance");
/*     */   }
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
/*     */   public boolean equals(Object paramObject) {
/* 183 */     if (this == paramObject) {
/* 184 */       return true;
/*     */     }
/* 186 */     if (paramObject instanceof SerialJavaObject) {
/* 187 */       SerialJavaObject serialJavaObject = (SerialJavaObject)paramObject;
/* 188 */       return this.obj.equals(serialJavaObject.obj);
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 201 */     return 31 + this.obj.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 212 */       SerialJavaObject serialJavaObject = (SerialJavaObject)super.clone();
/* 213 */       serialJavaObject.fields = Arrays.<Field>copyOf(this.fields, this.fields.length);
/* 214 */       if (this.chain != null)
/* 215 */         serialJavaObject.chain = new Vector<>(this.chain); 
/* 216 */       return serialJavaObject;
/* 217 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 219 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setWarning(RowSetWarning paramRowSetWarning) {
/* 227 */     if (this.chain == null) {
/* 228 */       this.chain = new Vector<>();
/*     */     }
/* 230 */     this.chain.add(paramRowSetWarning);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 240 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/* 242 */     Vector<? extends RowSetWarning> vector = (Vector)getField.get("chain", (Object)null);
/* 243 */     if (vector != null) {
/* 244 */       this.chain = new Vector<>(vector);
/*     */     }
/* 246 */     this.obj = getField.get("obj", (Object)null);
/* 247 */     if (this.obj != null) {
/* 248 */       this.fields = this.obj.getClass().getFields();
/* 249 */       if (hasStaticFields(this.fields)) {
/* 250 */         throw new IOException("Located static fields in object instance. Cannot serialize");
/*     */       }
/*     */     } else {
/* 253 */       throw new IOException("Object cannot be null!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 264 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 265 */     putField.put("obj", this.obj);
/* 266 */     putField.put("chain", this.chain);
/* 267 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasStaticFields(Field[] paramArrayOfField) {
/* 274 */     for (Field field : paramArrayOfField) {
/* 275 */       if (field.getModifiers() == 8) {
/* 276 */         return true;
/*     */       }
/*     */     } 
/* 279 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialJavaObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */