/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.NClob;
/*     */ import java.sql.Ref;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLOutput;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Struct;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
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
/*     */ public class SQLOutputImpl
/*     */   implements SQLOutput
/*     */ {
/*     */   private Vector attribs;
/*     */   private Map map;
/*     */   
/*     */   public SQLOutputImpl(Vector<?> paramVector, Map<String, ?> paramMap) throws SQLException {
/* 100 */     if (paramVector == null || paramMap == null) {
/* 101 */       throw new SQLException("Cannot instantiate a SQLOutputImpl instance with null parameters");
/*     */     }
/*     */     
/* 104 */     this.attribs = paramVector;
/* 105 */     this.map = paramMap;
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
/*     */   public void writeString(String paramString) throws SQLException {
/* 128 */     this.attribs.add(paramString);
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
/*     */   public void writeBoolean(boolean paramBoolean) throws SQLException {
/* 143 */     this.attribs.add(Boolean.valueOf(paramBoolean));
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
/*     */   public void writeByte(byte paramByte) throws SQLException {
/* 158 */     this.attribs.add(Byte.valueOf(paramByte));
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
/*     */   public void writeShort(short paramShort) throws SQLException {
/* 173 */     this.attribs.add(Short.valueOf(paramShort));
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
/*     */   public void writeInt(int paramInt) throws SQLException {
/* 188 */     this.attribs.add(Integer.valueOf(paramInt));
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
/*     */   public void writeLong(long paramLong) throws SQLException {
/* 203 */     this.attribs.add(Long.valueOf(paramLong));
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
/*     */   public void writeFloat(float paramFloat) throws SQLException {
/* 218 */     this.attribs.add(Float.valueOf(paramFloat));
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
/*     */   public void writeDouble(double paramDouble) throws SQLException {
/* 233 */     this.attribs.add(Double.valueOf(paramDouble));
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
/*     */   public void writeBigDecimal(BigDecimal paramBigDecimal) throws SQLException {
/* 248 */     this.attribs.add(paramBigDecimal);
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
/*     */   public void writeBytes(byte[] paramArrayOfbyte) throws SQLException {
/* 264 */     this.attribs.add(paramArrayOfbyte);
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
/*     */   public void writeDate(Date paramDate) throws SQLException {
/* 279 */     this.attribs.add(paramDate);
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
/*     */   public void writeTime(Time paramTime) throws SQLException {
/* 294 */     this.attribs.add(paramTime);
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
/*     */   public void writeTimestamp(Timestamp paramTimestamp) throws SQLException {
/* 309 */     this.attribs.add(paramTimestamp);
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
/*     */   public void writeCharacterStream(Reader paramReader) throws SQLException {
/* 324 */     BufferedReader bufferedReader = new BufferedReader(paramReader);
/*     */     try {
/*     */       int i;
/* 327 */       while ((i = bufferedReader.read()) != -1) {
/* 328 */         char c = (char)i;
/* 329 */         StringBuffer stringBuffer = new StringBuffer();
/* 330 */         stringBuffer.append(c);
/*     */         
/* 332 */         String str1 = new String(stringBuffer);
/* 333 */         String str2 = bufferedReader.readLine();
/*     */         
/* 335 */         writeString(str1.concat(str2));
/*     */       } 
/* 337 */     } catch (IOException iOException) {}
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
/*     */   public void writeAsciiStream(InputStream paramInputStream) throws SQLException {
/* 354 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/*     */     try {
/*     */       int i;
/* 357 */       while ((i = bufferedReader.read()) != -1) {
/* 358 */         char c = (char)i;
/*     */         
/* 360 */         StringBuffer stringBuffer = new StringBuffer();
/* 361 */         stringBuffer.append(c);
/*     */         
/* 363 */         String str1 = new String(stringBuffer);
/* 364 */         String str2 = bufferedReader.readLine();
/*     */         
/* 366 */         writeString(str1.concat(str2));
/*     */       } 
/* 368 */     } catch (IOException iOException) {
/* 369 */       throw new SQLException(iOException.getMessage());
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
/*     */   public void writeBinaryStream(InputStream paramInputStream) throws SQLException {
/* 384 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/*     */     try {
/*     */       int i;
/* 387 */       while ((i = bufferedReader.read()) != -1) {
/* 388 */         char c = (char)i;
/*     */         
/* 390 */         StringBuffer stringBuffer = new StringBuffer();
/* 391 */         stringBuffer.append(c);
/*     */         
/* 393 */         String str1 = new String(stringBuffer);
/* 394 */         String str2 = bufferedReader.readLine();
/*     */         
/* 396 */         writeString(str1.concat(str2));
/*     */       } 
/* 398 */     } catch (IOException iOException) {
/* 399 */       throw new SQLException(iOException.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObject(SQLData paramSQLData) throws SQLException {
/* 445 */     if (paramSQLData == null) {
/* 446 */       this.attribs.add(null);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 453 */       this.attribs.add(new SerialStruct(paramSQLData, this.map));
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
/*     */   public void writeRef(Ref paramRef) throws SQLException {
/* 470 */     if (paramRef == null) {
/* 471 */       this.attribs.add(null);
/*     */     } else {
/* 473 */       this.attribs.add(new SerialRef(paramRef));
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
/*     */   public void writeBlob(Blob paramBlob) throws SQLException {
/* 490 */     if (paramBlob == null) {
/* 491 */       this.attribs.add(null);
/*     */     } else {
/* 493 */       this.attribs.add(new SerialBlob(paramBlob));
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
/*     */   public void writeClob(Clob paramClob) throws SQLException {
/* 510 */     if (paramClob == null) {
/* 511 */       this.attribs.add(null);
/*     */     } else {
/* 513 */       this.attribs.add(new SerialClob(paramClob));
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
/*     */   public void writeStruct(Struct paramStruct) throws SQLException {
/* 536 */     SerialStruct serialStruct = new SerialStruct(paramStruct, this.map);
/* 537 */     this.attribs.add(serialStruct);
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
/*     */   public void writeArray(Array paramArray) throws SQLException {
/* 554 */     if (paramArray == null) {
/* 555 */       this.attribs.add(null);
/*     */     } else {
/* 557 */       this.attribs.add(new SerialArray(paramArray, this.map));
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
/*     */   public void writeURL(URL paramURL) throws SQLException {
/* 574 */     if (paramURL == null) {
/* 575 */       this.attribs.add(null);
/*     */     } else {
/* 577 */       this.attribs.add(new SerialDatalink(paramURL));
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
/*     */   public void writeNString(String paramString) throws SQLException {
/* 597 */     this.attribs.add(paramString);
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
/*     */   public void writeNClob(NClob paramNClob) throws SQLException {
/* 611 */     this.attribs.add(paramNClob);
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
/*     */   public void writeRowId(RowId paramRowId) throws SQLException {
/* 626 */     this.attribs.add(paramRowId);
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
/*     */   public void writeSQLXML(SQLXML paramSQLXML) throws SQLException {
/* 641 */     this.attribs.add(paramSQLXML);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SQLOutputImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */