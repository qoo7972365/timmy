/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Struct;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialArray
/*     */   implements Array, Serializable, Cloneable
/*     */ {
/*     */   private Object[] elements;
/*     */   private int baseType;
/*     */   private String baseTypeName;
/*     */   private int len;
/*     */   static final long serialVersionUID = -8466174297270688520L;
/*     */   
/*     */   public SerialArray(Array paramArray, Map<String, Class<?>> paramMap) throws SerialException, SQLException {
/*     */     byte b;
/* 150 */     if (paramArray == null || paramMap == null) {
/* 151 */       throw new SQLException("Cannot instantiate a SerialArray object with null parameters");
/*     */     }
/*     */ 
/*     */     
/* 155 */     if ((this.elements = (Object[])paramArray.getArray()) == null) {
/* 156 */       throw new SQLException("Invalid Array object. Calls to Array.getArray() return null value which cannot be serialized");
/*     */     }
/*     */ 
/*     */     
/* 160 */     this.elements = (Object[])paramArray.getArray(paramMap);
/* 161 */     this.baseType = paramArray.getBaseType();
/* 162 */     this.baseTypeName = paramArray.getBaseTypeName();
/* 163 */     this.len = this.elements.length;
/*     */     
/* 165 */     switch (this.baseType) {
/*     */       case 2002:
/* 167 */         for (b = 0; b < this.len; b++) {
/* 168 */           this.elements[b] = new SerialStruct((Struct)this.elements[b], paramMap);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2003:
/* 173 */         for (b = 0; b < this.len; b++) {
/* 174 */           this.elements[b] = new SerialArray((Array)this.elements[b], paramMap);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2004:
/* 179 */         for (b = 0; b < this.len; b++) {
/* 180 */           this.elements[b] = new SerialBlob((Blob)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2005:
/* 185 */         for (b = 0; b < this.len; b++) {
/* 186 */           this.elements[b] = new SerialClob((Clob)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 70:
/* 191 */         for (b = 0; b < this.len; b++) {
/* 192 */           this.elements[b] = new SerialDatalink((URL)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2000:
/* 197 */         for (b = 0; b < this.len; b++) {
/* 198 */           this.elements[b] = new SerialJavaObject(this.elements[b]);
/*     */         }
/*     */         break;
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
/*     */   public void free() throws SQLException {
/* 213 */     if (this.elements != null) {
/* 214 */       this.elements = null;
/* 215 */       this.baseTypeName = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerialArray(Array paramArray) throws SerialException, SQLException {
/*     */     byte b;
/* 253 */     if (paramArray == null) {
/* 254 */       throw new SQLException("Cannot instantiate a SerialArray object with a null Array object");
/*     */     }
/*     */ 
/*     */     
/* 258 */     if ((this.elements = (Object[])paramArray.getArray()) == null) {
/* 259 */       throw new SQLException("Invalid Array object. Calls to Array.getArray() return null value which cannot be serialized");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 264 */     this.baseType = paramArray.getBaseType();
/* 265 */     this.baseTypeName = paramArray.getBaseTypeName();
/* 266 */     this.len = this.elements.length;
/*     */     
/* 268 */     switch (this.baseType) {
/*     */       
/*     */       case 2004:
/* 271 */         for (b = 0; b < this.len; b++) {
/* 272 */           this.elements[b] = new SerialBlob((Blob)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2005:
/* 277 */         for (b = 0; b < this.len; b++) {
/* 278 */           this.elements[b] = new SerialClob((Clob)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 70:
/* 283 */         for (b = 0; b < this.len; b++) {
/* 284 */           this.elements[b] = new SerialDatalink((URL)this.elements[b]);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2000:
/* 289 */         for (b = 0; b < this.len; b++) {
/* 290 */           this.elements[b] = new SerialJavaObject(this.elements[b]);
/*     */         }
/*     */         break;
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
/*     */   public Object getArray() throws SerialException {
/* 309 */     isValid();
/* 310 */     Object[] arrayOfObject = new Object[this.len];
/* 311 */     System.arraycopy(this.elements, 0, arrayOfObject, 0, this.len);
/* 312 */     return arrayOfObject;
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
/*     */   public Object getArray(Map<String, Class<?>> paramMap) throws SerialException {
/* 340 */     isValid();
/* 341 */     Object[] arrayOfObject = new Object[this.len];
/* 342 */     System.arraycopy(this.elements, 0, arrayOfObject, 0, this.len);
/* 343 */     return arrayOfObject;
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
/*     */   public Object getArray(long paramLong, int paramInt) throws SerialException {
/* 363 */     isValid();
/* 364 */     Object[] arrayOfObject = new Object[paramInt];
/* 365 */     System.arraycopy(this.elements, (int)paramLong, arrayOfObject, 0, paramInt);
/* 366 */     return arrayOfObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArray(long paramLong, int paramInt, Map<String, Class<?>> paramMap) throws SerialException {
/* 401 */     isValid();
/* 402 */     Object[] arrayOfObject = new Object[paramInt];
/* 403 */     System.arraycopy(this.elements, (int)paramLong, arrayOfObject, 0, paramInt);
/* 404 */     return arrayOfObject;
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
/*     */   public int getBaseType() throws SerialException {
/* 418 */     isValid();
/* 419 */     return this.baseType;
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
/*     */   public String getBaseTypeName() throws SerialException {
/* 432 */     isValid();
/* 433 */     return this.baseTypeName;
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
/*     */   public ResultSet getResultSet(long paramLong, int paramInt) throws SerialException {
/* 456 */     SerialException serialException = new SerialException();
/* 457 */     serialException.initCause(new UnsupportedOperationException());
/* 458 */     throw serialException;
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
/*     */   public ResultSet getResultSet(Map<String, Class<?>> paramMap) throws SerialException {
/* 487 */     SerialException serialException = new SerialException();
/* 488 */     serialException.initCause(new UnsupportedOperationException());
/* 489 */     throw serialException;
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
/*     */   public ResultSet getResultSet() throws SerialException {
/* 506 */     SerialException serialException = new SerialException();
/* 507 */     serialException.initCause(new UnsupportedOperationException());
/* 508 */     throw serialException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet(long paramLong, int paramInt, Map<String, Class<?>> paramMap) throws SerialException {
/* 545 */     SerialException serialException = new SerialException();
/* 546 */     serialException.initCause(new UnsupportedOperationException());
/* 547 */     throw serialException;
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
/* 563 */     if (this == paramObject) {
/* 564 */       return true;
/*     */     }
/*     */     
/* 567 */     if (paramObject instanceof SerialArray) {
/* 568 */       SerialArray serialArray = (SerialArray)paramObject;
/* 569 */       return (this.baseType == serialArray.baseType && this.baseTypeName
/* 570 */         .equals(serialArray.baseTypeName) && 
/* 571 */         Arrays.equals(this.elements, serialArray.elements));
/*     */     } 
/* 573 */     return false;
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
/* 584 */     return (((31 + Arrays.hashCode(this.elements)) * 31 + this.len) * 31 + this.baseType) * 31 + this.baseTypeName
/* 585 */       .hashCode();
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
/* 597 */       SerialArray serialArray = (SerialArray)super.clone();
/* 598 */       serialArray.elements = (this.elements != null) ? Arrays.<Object>copyOf(this.elements, this.len) : null;
/* 599 */       return serialArray;
/* 600 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 602 */       throw new InternalError();
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
/* 614 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 615 */     Object[] arrayOfObject = (Object[])getField.get("elements", (Object)null);
/* 616 */     if (arrayOfObject == null)
/* 617 */       throw new InvalidObjectException("elements is null and should not be!"); 
/* 618 */     this.elements = (Object[])arrayOfObject.clone();
/* 619 */     this.len = getField.get("len", 0);
/* 620 */     if (this.elements.length != this.len) {
/* 621 */       throw new InvalidObjectException("elements is not the expected size");
/*     */     }
/* 623 */     this.baseType = getField.get("baseType", 0);
/* 624 */     this.baseTypeName = (String)getField.get("baseTypeName", (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException, ClassNotFoundException {
/* 634 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 635 */     putField.put("elements", this.elements);
/* 636 */     putField.put("len", this.len);
/* 637 */     putField.put("baseType", this.baseType);
/* 638 */     putField.put("baseTypeName", this.baseTypeName);
/* 639 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void isValid() throws SerialException {
/* 649 */     if (this.elements == null)
/* 650 */       throw new SerialException("Error: You cannot call a method on a SerialArray instance once free() has been called."); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SerialArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */