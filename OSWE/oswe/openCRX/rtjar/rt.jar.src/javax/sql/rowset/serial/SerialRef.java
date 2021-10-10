/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
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
/*     */ public class SerialRef
/*     */   implements Ref, Serializable, Cloneable
/*     */ {
/*     */   private String baseTypeName;
/*     */   private Object object;
/*     */   private Ref reference;
/*     */   static final long serialVersionUID = -4727123500609662274L;
/*     */   
/*     */   public SerialRef(Ref paramRef) throws SerialException, SQLException {
/*  77 */     if (paramRef == null) {
/*  78 */       throw new SQLException("Cannot instantiate a SerialRef object with a null Ref object");
/*     */     }
/*     */     
/*  81 */     this.reference = paramRef;
/*  82 */     this.object = paramRef;
/*  83 */     if (paramRef.getBaseTypeName() == null) {
/*  84 */       throw new SQLException("Cannot instantiate a SerialRef object that returns a null base type name");
/*     */     }
/*     */     
/*  87 */     this.baseTypeName = paramRef.getBaseTypeName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseTypeName() throws SerialException {
/*  98 */     return this.baseTypeName;
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
/*     */   public Object getObject(Map<String, Class<?>> paramMap) throws SerialException {
/* 119 */     paramMap = new Hashtable<>(paramMap);
/* 120 */     if (this.object != null) {
/* 121 */       return paramMap.get(this.object);
/*     */     }
/* 123 */     throw new SerialException("The object is not set");
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
/*     */   public Object getObject() throws SerialException {
/* 137 */     if (this.reference != null) {
/*     */       try {
/* 139 */         return this.reference.getObject();
/* 140 */       } catch (SQLException sQLException) {
/* 141 */         throw new SerialException("SQLException: " + sQLException.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 145 */     if (this.object != null) {
/* 146 */       return this.object;
/*     */     }
/*     */ 
/*     */     
/* 150 */     throw new SerialException("The object is not set");
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
/*     */   public void setObject(Object paramObject) throws SerialException {
/*     */     try {
/* 165 */       this.reference.setObject(paramObject);
/* 166 */     } catch (SQLException sQLException) {
/* 167 */       throw new SerialException("SQLException: " + sQLException.getMessage());
/*     */     } 
/* 169 */     this.object = paramObject;
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
/*     */   public boolean equals(Object paramObject) {
/* 185 */     if (this == paramObject) {
/* 186 */       return true;
/*     */     }
/* 188 */     if (paramObject instanceof SerialRef) {
/* 189 */       SerialRef serialRef = (SerialRef)paramObject;
/* 190 */       return (this.baseTypeName.equals(serialRef.baseTypeName) && this.object
/* 191 */         .equals(serialRef.object));
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 201 */     return (31 + this.object.hashCode()) * 31 + this.baseTypeName.hashCode();
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
/* 212 */       SerialRef serialRef = (SerialRef)super.clone();
/* 213 */       serialRef.reference = null;
/* 214 */       return serialRef;
/* 215 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 217 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 228 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 229 */     this.object = getField.get("object", (Object)null);
/* 230 */     this.baseTypeName = (String)getField.get("baseTypeName", (Object)null);
/* 231 */     this.reference = (Ref)getField.get("reference", (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 241 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 242 */     putField.put("baseTypeName", this.baseTypeName);
/* 243 */     putField.put("object", this.object);
/*     */ 
/*     */     
/* 246 */     putField.put("reference", (this.reference instanceof Serializable) ? this.reference : null);
/* 247 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */