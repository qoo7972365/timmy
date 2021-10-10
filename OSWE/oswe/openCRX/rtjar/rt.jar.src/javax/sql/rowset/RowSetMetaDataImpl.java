/*      */ package javax.sql.rowset;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Field;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.sql.Types;
/*      */ import javax.sql.RowSetMetaData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RowSetMetaDataImpl
/*      */   implements RowSetMetaData, Serializable
/*      */ {
/*      */   private int colCount;
/*      */   private ColInfo[] colInfo;
/*      */   static final long serialVersionUID = 6893806403181801867L;
/*      */   
/*      */   private void checkColRange(int paramInt) throws SQLException {
/*   84 */     if (paramInt <= 0 || paramInt > this.colCount) {
/*   85 */       throw new SQLException("Invalid column index :" + paramInt);
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
/*      */   private void checkColType(int paramInt) throws SQLException {
/*      */     try {
/*  102 */       Class<Types> clazz = Types.class;
/*  103 */       Field[] arrayOfField = clazz.getFields();
/*  104 */       int i = 0;
/*  105 */       for (byte b = 0; b < arrayOfField.length; b++) {
/*  106 */         i = arrayOfField[b].getInt(clazz);
/*  107 */         if (i == paramInt) {
/*      */           return;
/*      */         }
/*      */       } 
/*  111 */     } catch (Exception exception) {
/*  112 */       throw new SQLException(exception.getMessage());
/*      */     } 
/*  114 */     throw new SQLException("Invalid SQL type for column");
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
/*      */   public void setColumnCount(int paramInt) throws SQLException {
/*  127 */     if (paramInt <= 0) {
/*  128 */       throw new SQLException("Invalid column count. Cannot be less or equal to zero");
/*      */     }
/*      */ 
/*      */     
/*  132 */     this.colCount = paramInt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  142 */     if (this.colCount != Integer.MAX_VALUE) {
/*  143 */       this.colInfo = new ColInfo[this.colCount + 1];
/*      */       
/*  145 */       for (byte b = 1; b <= this.colCount; b++) {
/*  146 */         this.colInfo[b] = new ColInfo();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoIncrement(int paramInt, boolean paramBoolean) throws SQLException {
/*  168 */     checkColRange(paramInt);
/*  169 */     (this.colInfo[paramInt]).autoIncrement = paramBoolean;
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
/*      */   public void setCaseSensitive(int paramInt, boolean paramBoolean) throws SQLException {
/*  185 */     checkColRange(paramInt);
/*  186 */     (this.colInfo[paramInt]).caseSensitive = paramBoolean;
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
/*      */   public void setSearchable(int paramInt, boolean paramBoolean) throws SQLException {
/*  205 */     checkColRange(paramInt);
/*  206 */     (this.colInfo[paramInt]).searchable = paramBoolean;
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
/*      */   public void setCurrency(int paramInt, boolean paramBoolean) throws SQLException {
/*  222 */     checkColRange(paramInt);
/*  223 */     (this.colInfo[paramInt]).currency = paramBoolean;
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
/*      */   public void setNullable(int paramInt1, int paramInt2) throws SQLException {
/*  247 */     if (paramInt2 < 0 || paramInt2 > 2)
/*      */     {
/*  249 */       throw new SQLException("Invalid nullable constant set. Must be either columnNoNulls, columnNullable or columnNullableUnknown");
/*      */     }
/*      */     
/*  252 */     checkColRange(paramInt1);
/*  253 */     (this.colInfo[paramInt1]).nullable = paramInt2;
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
/*      */   public void setSigned(int paramInt, boolean paramBoolean) throws SQLException {
/*  269 */     checkColRange(paramInt);
/*  270 */     (this.colInfo[paramInt]).signed = paramBoolean;
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
/*      */   public void setColumnDisplaySize(int paramInt1, int paramInt2) throws SQLException {
/*  286 */     if (paramInt2 < 0) {
/*  287 */       throw new SQLException("Invalid column display size. Cannot be less than zero");
/*      */     }
/*      */     
/*  290 */     checkColRange(paramInt1);
/*  291 */     (this.colInfo[paramInt1]).columnDisplaySize = paramInt2;
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
/*      */   public void setColumnLabel(int paramInt, String paramString) throws SQLException {
/*  309 */     checkColRange(paramInt);
/*  310 */     if (paramString != null) {
/*  311 */       (this.colInfo[paramInt]).columnLabel = paramString;
/*      */     } else {
/*  313 */       (this.colInfo[paramInt]).columnLabel = "";
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
/*      */   public void setColumnName(int paramInt, String paramString) throws SQLException {
/*  329 */     checkColRange(paramInt);
/*  330 */     if (paramString != null) {
/*  331 */       (this.colInfo[paramInt]).columnName = paramString;
/*      */     } else {
/*  333 */       (this.colInfo[paramInt]).columnName = "";
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
/*      */   public void setSchemaName(int paramInt, String paramString) throws SQLException {
/*  351 */     checkColRange(paramInt);
/*  352 */     if (paramString != null) {
/*  353 */       (this.colInfo[paramInt]).schemaName = paramString;
/*      */     } else {
/*  355 */       (this.colInfo[paramInt]).schemaName = "";
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
/*      */   public void setPrecision(int paramInt1, int paramInt2) throws SQLException {
/*  373 */     if (paramInt2 < 0) {
/*  374 */       throw new SQLException("Invalid precision value. Cannot be less than zero");
/*      */     }
/*      */     
/*  377 */     checkColRange(paramInt1);
/*  378 */     (this.colInfo[paramInt1]).colPrecision = paramInt2;
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
/*      */   public void setScale(int paramInt1, int paramInt2) throws SQLException {
/*  394 */     if (paramInt2 < 0) {
/*  395 */       throw new SQLException("Invalid scale size. Cannot be less than zero");
/*      */     }
/*      */     
/*  398 */     checkColRange(paramInt1);
/*  399 */     (this.colInfo[paramInt1]).colScale = paramInt2;
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
/*      */   public void setTableName(int paramInt, String paramString) throws SQLException {
/*  414 */     checkColRange(paramInt);
/*  415 */     if (paramString != null) {
/*  416 */       (this.colInfo[paramInt]).tableName = paramString;
/*      */     } else {
/*  418 */       (this.colInfo[paramInt]).tableName = "";
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
/*      */   public void setCatalogName(int paramInt, String paramString) throws SQLException {
/*  435 */     checkColRange(paramInt);
/*  436 */     if (paramString != null) {
/*  437 */       (this.colInfo[paramInt]).catName = paramString;
/*      */     } else {
/*  439 */       (this.colInfo[paramInt]).catName = "";
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
/*      */   public void setColumnType(int paramInt1, int paramInt2) throws SQLException {
/*  459 */     checkColType(paramInt2);
/*  460 */     checkColRange(paramInt1);
/*  461 */     (this.colInfo[paramInt1]).colType = paramInt2;
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
/*      */   public void setColumnTypeName(int paramInt, String paramString) throws SQLException {
/*  477 */     checkColRange(paramInt);
/*  478 */     if (paramString != null) {
/*  479 */       (this.colInfo[paramInt]).colTypeName = paramString;
/*      */     } else {
/*  481 */       (this.colInfo[paramInt]).colTypeName = "";
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
/*      */   public int getColumnCount() throws SQLException {
/*  493 */     return this.colCount;
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
/*      */   public boolean isAutoIncrement(int paramInt) throws SQLException {
/*  508 */     checkColRange(paramInt);
/*  509 */     return (this.colInfo[paramInt]).autoIncrement;
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
/*      */   public boolean isCaseSensitive(int paramInt) throws SQLException {
/*  524 */     checkColRange(paramInt);
/*  525 */     return (this.colInfo[paramInt]).caseSensitive;
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
/*      */   public boolean isSearchable(int paramInt) throws SQLException {
/*  540 */     checkColRange(paramInt);
/*  541 */     return (this.colInfo[paramInt]).searchable;
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
/*      */   public boolean isCurrency(int paramInt) throws SQLException {
/*  556 */     checkColRange(paramInt);
/*  557 */     return (this.colInfo[paramInt]).currency;
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
/*      */   public int isNullable(int paramInt) throws SQLException {
/*  574 */     checkColRange(paramInt);
/*  575 */     return (this.colInfo[paramInt]).nullable;
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
/*      */   public boolean isSigned(int paramInt) throws SQLException {
/*  590 */     checkColRange(paramInt);
/*  591 */     return (this.colInfo[paramInt]).signed;
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
/*      */   public int getColumnDisplaySize(int paramInt) throws SQLException {
/*  605 */     checkColRange(paramInt);
/*  606 */     return (this.colInfo[paramInt]).columnDisplaySize;
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
/*      */   public String getColumnLabel(int paramInt) throws SQLException {
/*  620 */     checkColRange(paramInt);
/*  621 */     return (this.colInfo[paramInt]).columnLabel;
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
/*      */   public String getColumnName(int paramInt) throws SQLException {
/*  634 */     checkColRange(paramInt);
/*  635 */     return (this.colInfo[paramInt]).columnName;
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
/*      */   public String getSchemaName(int paramInt) throws SQLException {
/*  651 */     checkColRange(paramInt);
/*  652 */     String str = "";
/*  653 */     if ((this.colInfo[paramInt]).schemaName != null)
/*      */     {
/*  655 */       str = (this.colInfo[paramInt]).schemaName;
/*      */     }
/*  657 */     return str;
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
/*      */   public int getPrecision(int paramInt) throws SQLException {
/*  671 */     checkColRange(paramInt);
/*  672 */     return (this.colInfo[paramInt]).colPrecision;
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
/*      */   public int getScale(int paramInt) throws SQLException {
/*  686 */     checkColRange(paramInt);
/*  687 */     return (this.colInfo[paramInt]).colScale;
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
/*      */   public String getTableName(int paramInt) throws SQLException {
/*  702 */     checkColRange(paramInt);
/*  703 */     return (this.colInfo[paramInt]).tableName;
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
/*      */   public String getCatalogName(int paramInt) throws SQLException {
/*  718 */     checkColRange(paramInt);
/*  719 */     String str = "";
/*  720 */     if ((this.colInfo[paramInt]).catName != null)
/*      */     {
/*  722 */       str = (this.colInfo[paramInt]).catName;
/*      */     }
/*  724 */     return str;
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
/*      */   public int getColumnType(int paramInt) throws SQLException {
/*  741 */     checkColRange(paramInt);
/*  742 */     return (this.colInfo[paramInt]).colType;
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
/*      */   public String getColumnTypeName(int paramInt) throws SQLException {
/*  756 */     checkColRange(paramInt);
/*  757 */     return (this.colInfo[paramInt]).colTypeName;
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
/*      */   public boolean isReadOnly(int paramInt) throws SQLException {
/*  773 */     checkColRange(paramInt);
/*  774 */     return (this.colInfo[paramInt]).readOnly;
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
/*      */   public boolean isWritable(int paramInt) throws SQLException {
/*  791 */     checkColRange(paramInt);
/*  792 */     return (this.colInfo[paramInt]).writable;
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
/*      */   public boolean isDefinitelyWritable(int paramInt) throws SQLException {
/*  807 */     checkColRange(paramInt);
/*  808 */     return true;
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
/*      */   public String getColumnClassName(int paramInt) throws SQLException {
/*  834 */     String str = String.class.getName();
/*      */     
/*  836 */     int i = getColumnType(paramInt);
/*      */     
/*  838 */     switch (i) {
/*      */       
/*      */       case 2:
/*      */       case 3:
/*  842 */         str = BigDecimal.class.getName();
/*      */         break;
/*      */       
/*      */       case -7:
/*  846 */         str = Boolean.class.getName();
/*      */         break;
/*      */       
/*      */       case -6:
/*  850 */         str = Byte.class.getName();
/*      */         break;
/*      */       
/*      */       case 5:
/*  854 */         str = Short.class.getName();
/*      */         break;
/*      */       
/*      */       case 4:
/*  858 */         str = Integer.class.getName();
/*      */         break;
/*      */       
/*      */       case -5:
/*  862 */         str = Long.class.getName();
/*      */         break;
/*      */       
/*      */       case 7:
/*  866 */         str = Float.class.getName();
/*      */         break;
/*      */       
/*      */       case 6:
/*      */       case 8:
/*  871 */         str = Double.class.getName();
/*      */         break;
/*      */       
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/*  877 */         str = "byte[]";
/*      */         break;
/*      */       
/*      */       case 91:
/*  881 */         str = Date.class.getName();
/*      */         break;
/*      */       
/*      */       case 92:
/*  885 */         str = Time.class.getName();
/*      */         break;
/*      */       
/*      */       case 93:
/*  889 */         str = Timestamp.class.getName();
/*      */         break;
/*      */       
/*      */       case 2004:
/*  893 */         str = Blob.class.getName();
/*      */         break;
/*      */       
/*      */       case 2005:
/*  897 */         str = Clob.class.getName();
/*      */         break;
/*      */     } 
/*      */     
/*  901 */     return str;
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
/*      */   public <T> T unwrap(Class<T> paramClass) throws SQLException {
/*  920 */     if (isWrapperFor(paramClass)) {
/*  921 */       return paramClass.cast(this);
/*      */     }
/*  923 */     throw new SQLException("unwrap failed for:" + paramClass);
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
/*      */   public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
/*  943 */     return paramClass.isInstance(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class ColInfo
/*      */     implements Serializable
/*      */   {
/*      */     public boolean autoIncrement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean caseSensitive;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean currency;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean signed;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean searchable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int columnDisplaySize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String columnLabel;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String columnName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String schemaName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int colPrecision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int colScale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ColInfo() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1058 */     public String tableName = "";
/*      */     public String catName;
/*      */     public int colType;
/*      */     public String colTypeName;
/*      */     public boolean readOnly = false;
/*      */     public boolean writable = true;
/*      */     static final long serialVersionUID = 5490834817919311283L;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/RowSetMetaDataImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */