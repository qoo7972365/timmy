/*     */ package javax.sql.rowset.serial;
/*     */ 
/*     */ import java.io.InputStream;
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
/*     */ import java.sql.SQLInput;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Struct;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SQLInputImpl
/*     */   implements SQLInput
/*     */ {
/*     */   private boolean lastValueWasNull;
/*     */   private int idx;
/*     */   private Object[] attrib;
/*     */   private Map<String, Class<?>> map;
/*     */   
/*     */   public SQLInputImpl(Object[] paramArrayOfObject, Map<String, Class<?>> paramMap) throws SQLException {
/* 120 */     if (paramArrayOfObject == null || paramMap == null) {
/* 121 */       throw new SQLException("Cannot instantiate a SQLInputImpl object with null parameters");
/*     */     }
/*     */ 
/*     */     
/* 125 */     this.attrib = Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length);
/*     */     
/* 127 */     this.idx = -1;
/*     */     
/* 129 */     this.map = paramMap;
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
/*     */   private Object getNextAttribute() throws SQLException {
/* 143 */     if (++this.idx >= this.attrib.length) {
/* 144 */       throw new SQLException("SQLInputImpl exception: Invalid read position");
/*     */     }
/*     */     
/* 147 */     this.lastValueWasNull = (this.attrib[this.idx] == null);
/* 148 */     return this.attrib[this.idx];
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
/*     */   public String readString() throws SQLException {
/* 174 */     return (String)getNextAttribute();
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
/*     */   public boolean readBoolean() throws SQLException {
/* 192 */     Boolean bool = (Boolean)getNextAttribute();
/* 193 */     return (bool == null) ? false : bool.booleanValue();
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
/*     */   public byte readByte() throws SQLException {
/* 211 */     Byte byte_ = (Byte)getNextAttribute();
/* 212 */     return (byte_ == null) ? 0 : byte_.byteValue();
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
/*     */   public short readShort() throws SQLException {
/* 229 */     Short short_ = (Short)getNextAttribute();
/* 230 */     return (short_ == null) ? 0 : short_.shortValue();
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
/*     */   public int readInt() throws SQLException {
/* 247 */     Integer integer = (Integer)getNextAttribute();
/* 248 */     return (integer == null) ? 0 : integer.intValue();
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
/*     */   public long readLong() throws SQLException {
/* 265 */     Long long_ = (Long)getNextAttribute();
/* 266 */     return (long_ == null) ? 0L : long_.longValue();
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
/*     */   public float readFloat() throws SQLException {
/* 283 */     Float float_ = (Float)getNextAttribute();
/* 284 */     return (float_ == null) ? 0.0F : float_.floatValue();
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
/*     */   public double readDouble() throws SQLException {
/* 301 */     Double double_ = (Double)getNextAttribute();
/* 302 */     return (double_ == null) ? 0.0D : double_.doubleValue();
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
/*     */   public BigDecimal readBigDecimal() throws SQLException {
/* 319 */     return (BigDecimal)getNextAttribute();
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
/*     */   public byte[] readBytes() throws SQLException {
/* 336 */     return (byte[])getNextAttribute();
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
/*     */   public Date readDate() throws SQLException {
/* 353 */     return (Date)getNextAttribute();
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
/*     */   public Time readTime() throws SQLException {
/* 371 */     return (Time)getNextAttribute();
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
/*     */   public Timestamp readTimestamp() throws SQLException {
/* 384 */     return (Timestamp)getNextAttribute();
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
/*     */   public Reader readCharacterStream() throws SQLException {
/* 401 */     return (Reader)getNextAttribute();
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
/*     */   public InputStream readAsciiStream() throws SQLException {
/* 419 */     return (InputStream)getNextAttribute();
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
/*     */   public InputStream readBinaryStream() throws SQLException {
/* 437 */     return (InputStream)getNextAttribute();
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
/*     */   public Object readObject() throws SQLException {
/* 471 */     Object object = getNextAttribute();
/* 472 */     if (object instanceof Struct) {
/* 473 */       Struct struct = (Struct)object;
/*     */       
/* 475 */       Class clazz = this.map.get(struct.getSQLTypeName());
/* 476 */       if (clazz != null) {
/*     */         
/* 478 */         SQLData sQLData = null;
/*     */         try {
/* 480 */           sQLData = (SQLData)ReflectUtil.newInstance(clazz);
/* 481 */         } catch (Exception exception) {
/* 482 */           throw new SQLException("Unable to Instantiate: ", exception);
/*     */         } 
/*     */         
/* 485 */         Object[] arrayOfObject = struct.getAttributes(this.map);
/*     */         
/* 487 */         SQLInputImpl sQLInputImpl = new SQLInputImpl(arrayOfObject, this.map);
/*     */         
/* 489 */         sQLData.readSQL(sQLInputImpl, struct.getSQLTypeName());
/* 490 */         return sQLData;
/*     */       } 
/*     */     } 
/* 493 */     return object;
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
/*     */   public Ref readRef() throws SQLException {
/* 507 */     return (Ref)getNextAttribute();
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
/*     */   public Blob readBlob() throws SQLException {
/* 528 */     return (Blob)getNextAttribute();
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
/*     */   public Clob readClob() throws SQLException {
/* 549 */     return (Clob)getNextAttribute();
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
/*     */   public Array readArray() throws SQLException {
/* 571 */     return (Array)getNextAttribute();
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
/*     */   public boolean wasNull() throws SQLException {
/* 585 */     return this.lastValueWasNull;
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
/*     */   public URL readURL() throws SQLException {
/* 606 */     return (URL)getNextAttribute();
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
/*     */   public NClob readNClob() throws SQLException {
/* 622 */     return (NClob)getNextAttribute();
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
/*     */   public String readNString() throws SQLException {
/* 636 */     return (String)getNextAttribute();
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
/*     */   public SQLXML readSQLXML() throws SQLException {
/* 650 */     return (SQLXML)getNextAttribute();
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
/*     */   public RowId readRowId() throws SQLException {
/* 664 */     return (RowId)getNextAttribute();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/serial/SQLInputImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */