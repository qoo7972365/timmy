/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Struct;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
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
/*     */ public class SerialStruct
/*     */   implements Struct, Serializable, Cloneable
/*     */ {
/*     */   private String SQLTypeName;
/*     */   private Object[] attribs;
/*     */   static final long serialVersionUID = -8322445504027483372L;
/*     */   
/*     */   public SerialStruct(Struct paramStruct, Map<String, Class<?>> paramMap) throws SerialException {
/*     */     try {
/* 106 */       this.SQLTypeName = paramStruct.getSQLTypeName();
/* 107 */       System.out.println("SQLTypeName: " + this.SQLTypeName);
/*     */ 
/*     */       
/* 110 */       this.attribs = paramStruct.getAttributes(paramMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       mapToSerial(paramMap);
/*     */     }
/* 119 */     catch (SQLException sQLException) {
/* 120 */       throw new SerialException(sQLException.getMessage());
/*     */     } 
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
/*     */   public SerialStruct(SQLData paramSQLData, Map<String, Class<?>> paramMap) throws SerialException {
/*     */     try {
/* 149 */       this.SQLTypeName = paramSQLData.getSQLTypeName();
/*     */       
/* 151 */       Vector<?> vector = new Vector();
/* 152 */       paramSQLData.writeSQL(new SQLOutputImpl(vector, paramMap));
/* 153 */       this.attribs = vector.toArray();
/*     */     }
/* 155 */     catch (SQLException sQLException) {
/* 156 */       throw new SerialException(sQLException.getMessage());
/*     */     } 
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
/*     */   public String getSQLTypeName() throws SerialException {
/* 172 */     return this.SQLTypeName;
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
/*     */   public Object[] getAttributes() throws SerialException {
/* 186 */     Object[] arrayOfObject = this.attribs;
/* 187 */     return (arrayOfObject == null) ? null : Arrays.<Object>copyOf(arrayOfObject, arrayOfObject.length);
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
/*     */   public Object[] getAttributes(Map<String, Class<?>> paramMap) throws SerialException {
/* 210 */     Object[] arrayOfObject = this.attribs;
/* 211 */     return (arrayOfObject == null) ? null : Arrays.<Object>copyOf(arrayOfObject, arrayOfObject.length);
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
/*     */   private void mapToSerial(Map<String, Class<?>> paramMap) throws SerialException {
/*     */     try {
/* 238 */       for (byte b = 0; b < this.attribs.length; b++) {
/* 239 */         if (this.attribs[b] instanceof Struct) {
/* 240 */           this.attribs[b] = new SerialStruct((Struct)this.attribs[b], paramMap);
/* 241 */         } else if (this.attribs[b] instanceof SQLData) {
/* 242 */           this.attribs[b] = new SerialStruct((SQLData)this.attribs[b], paramMap);
/* 243 */         } else if (this.attribs[b] instanceof Blob) {
/* 244 */           this.attribs[b] = new SerialBlob((Blob)this.attribs[b]);
/* 245 */         } else if (this.attribs[b] instanceof Clob) {
/* 246 */           this.attribs[b] = new SerialClob((Clob)this.attribs[b]);
/* 247 */         } else if (this.attribs[b] instanceof Ref) {
/* 248 */           this.attribs[b] = new SerialRef((Ref)this.attribs[b]);
/* 249 */         } else if (this.attribs[b] instanceof Array) {
/* 250 */           this.attribs[b] = new SerialArray((Array)this.attribs[b], paramMap);
/*     */         }
/*     */       
/*     */       } 
/* 254 */     } catch (SQLException sQLException) {
/* 255 */       throw new SerialException(sQLException.getMessage());
/*     */     } 
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
/*     */   public boolean equals(Object paramObject) {
/* 273 */     if (this == paramObject) {
/* 274 */       return true;
/*     */     }
/* 276 */     if (paramObject instanceof SerialStruct) {
/* 277 */       SerialStruct serialStruct = (SerialStruct)paramObject;
/* 278 */       return (this.SQLTypeName.equals(serialStruct.SQLTypeName) && 
/* 279 */         Arrays.equals(this.attribs, serialStruct.attribs));
/*     */     } 
/* 281 */     return false;
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
/*     */   public int hashCode() {
/* 293 */     return (31 + Arrays.hashCode(this.attribs)) * 31 * 31 + this.SQLTypeName
/* 294 */       .hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 306 */       SerialStruct serialStruct = (SerialStruct)super.clone();
/* 307 */       serialStruct.attribs = Arrays.copyOf(this.attribs, this.attribs.length);
/* 308 */       return serialStruct;
/* 309 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 311 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 323 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 324 */     Object[] arrayOfObject = (Object[])getField.get("attribs", (Object)null);
/* 325 */     this.attribs = (arrayOfObject == null) ? null : (Object[])arrayOfObject.clone();
/* 326 */     this.SQLTypeName = (String)getField.get("SQLTypeName", (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 336 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 337 */     putField.put("attribs", this.attribs);
/* 338 */     putField.put("SQLTypeName", this.SQLTypeName);
/* 339 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */