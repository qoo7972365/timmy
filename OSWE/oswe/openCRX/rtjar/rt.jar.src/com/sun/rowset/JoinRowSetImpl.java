/*      */ package com.sun.rowset;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.RowSetListener;
/*      */ import javax.sql.RowSetMetaData;
/*      */ import javax.sql.rowset.CachedRowSet;
/*      */ import javax.sql.rowset.JoinRowSet;
/*      */ import javax.sql.rowset.Joinable;
/*      */ import javax.sql.rowset.RowSetMetaDataImpl;
/*      */ import javax.sql.rowset.WebRowSet;
/*      */ import javax.sql.rowset.spi.SyncProvider;
/*      */ import javax.sql.rowset.spi.SyncProviderException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JoinRowSetImpl
/*      */   extends WebRowSetImpl
/*      */   implements JoinRowSet
/*      */ {
/*  124 */   private Vector<CachedRowSetImpl> vecRowSetsInJOIN = new Vector<>();
/*  125 */   private CachedRowSetImpl crsInternal = new CachedRowSetImpl();
/*  126 */   private Vector<Integer> vecJoinType = new Vector<>();
/*  127 */   private Vector<String> vecTableNames = new Vector<>();
/*  128 */   private int iMatchKey = -1;
/*  129 */   private String strMatchKey = null;
/*  130 */   boolean[] supportedJOINs = new boolean[] { false, true, false, false, false };
/*      */   public JoinRowSetImpl() throws SQLException {
/*      */     try {
/*  133 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  134 */     } catch (IOException iOException) {
/*  135 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WebRowSet wrs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = -5590501621560008453L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRowSet(Joinable paramJoinable) throws SQLException {
/*      */     CachedRowSetImpl cachedRowSetImpl;
/*  161 */     boolean bool1 = false;
/*  162 */     boolean bool2 = false;
/*      */ 
/*      */     
/*  165 */     if (!(paramJoinable instanceof RowSet)) {
/*  166 */       throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.notinstance").toString());
/*      */     }
/*      */     
/*  169 */     if (paramJoinable instanceof JdbcRowSetImpl) {
/*  170 */       cachedRowSetImpl = new CachedRowSetImpl();
/*  171 */       cachedRowSetImpl.populate((RowSet)paramJoinable);
/*  172 */       if (cachedRowSetImpl.size() == 0) {
/*  173 */         throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.emptyrowset").toString());
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  178 */         byte b1 = 0;
/*  179 */         for (byte b2 = 0; b2 < (paramJoinable.getMatchColumnIndexes()).length && 
/*  180 */           paramJoinable.getMatchColumnIndexes()[b2] != -1; b2++) {
/*  181 */           b1++;
/*      */         }
/*      */ 
/*      */         
/*  185 */         int[] arrayOfInt = new int[b1];
/*  186 */         for (byte b3 = 0; b3 < b1; b3++)
/*  187 */           arrayOfInt[b3] = paramJoinable.getMatchColumnIndexes()[b3]; 
/*  188 */         cachedRowSetImpl.setMatchColumn(arrayOfInt);
/*  189 */       } catch (SQLException sQLException) {}
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  194 */       cachedRowSetImpl = (CachedRowSetImpl)paramJoinable;
/*  195 */       if (cachedRowSetImpl.size() == 0) {
/*  196 */         throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.emptyrowset").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  204 */       this.iMatchKey = cachedRowSetImpl.getMatchColumnIndexes()[0];
/*  205 */     } catch (SQLException sQLException) {
/*      */       
/*  207 */       bool1 = true;
/*      */     } 
/*      */     
/*      */     try {
/*  211 */       this.strMatchKey = cachedRowSetImpl.getMatchColumnNames()[0];
/*  212 */     } catch (SQLException sQLException) {
/*      */       
/*  214 */       bool2 = true;
/*      */     } 
/*      */     
/*  217 */     if (bool1 && bool2)
/*      */     {
/*  219 */       throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.matchnotset").toString());
/*      */     }
/*      */ 
/*      */     
/*  223 */     if (bool1) {
/*      */       
/*  225 */       ArrayList<Integer> arrayList = new ArrayList();
/*  226 */       for (byte b1 = 0; b1 < (cachedRowSetImpl.getMatchColumnNames()).length && (
/*  227 */         this.strMatchKey = cachedRowSetImpl.getMatchColumnNames()[b1]) != null; b1++) {
/*  228 */         this.iMatchKey = cachedRowSetImpl.findColumn(this.strMatchKey);
/*  229 */         arrayList.add(Integer.valueOf(this.iMatchKey));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  234 */       int[] arrayOfInt = new int[arrayList.size()];
/*  235 */       for (byte b2 = 0; b2 < arrayList.size(); b2++)
/*  236 */         arrayOfInt[b2] = ((Integer)arrayList.get(b2)).intValue(); 
/*  237 */       cachedRowSetImpl.setMatchColumn(arrayOfInt);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  255 */     initJOIN(cachedRowSetImpl);
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
/*      */   public void addRowSet(RowSet paramRowSet, int paramInt) throws SQLException {
/*  283 */     ((CachedRowSetImpl)paramRowSet).setMatchColumn(paramInt);
/*      */     
/*  285 */     addRowSet((Joinable)paramRowSet);
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
/*      */   public void addRowSet(RowSet paramRowSet, String paramString) throws SQLException {
/*  311 */     ((CachedRowSetImpl)paramRowSet).setMatchColumn(paramString);
/*  312 */     addRowSet((Joinable)paramRowSet);
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
/*      */   public void addRowSet(RowSet[] paramArrayOfRowSet, int[] paramArrayOfint) throws SQLException {
/*  345 */     if (paramArrayOfRowSet.length != paramArrayOfint.length) {
/*  346 */       throw new SQLException(this.resBundle
/*  347 */           .handleGetObject("joinrowsetimpl.numnotequal").toString());
/*      */     }
/*  349 */     for (byte b = 0; b < paramArrayOfRowSet.length; b++) {
/*  350 */       ((CachedRowSetImpl)paramArrayOfRowSet[b]).setMatchColumn(paramArrayOfint[b]);
/*  351 */       addRowSet((Joinable)paramArrayOfRowSet[b]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRowSet(RowSet[] paramArrayOfRowSet, String[] paramArrayOfString) throws SQLException {
/*  392 */     if (paramArrayOfRowSet.length != paramArrayOfString.length) {
/*  393 */       throw new SQLException(this.resBundle
/*  394 */           .handleGetObject("joinrowsetimpl.numnotequal").toString());
/*      */     }
/*  396 */     for (byte b = 0; b < paramArrayOfRowSet.length; b++) {
/*  397 */       ((CachedRowSetImpl)paramArrayOfRowSet[b]).setMatchColumn(paramArrayOfString[b]);
/*  398 */       addRowSet((Joinable)paramArrayOfRowSet[b]);
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
/*      */   public Collection getRowSets() throws SQLException {
/*  418 */     return this.vecRowSetsInJOIN;
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
/*      */   public String[] getRowSetNames() throws SQLException {
/*  430 */     Object[] arrayOfObject = this.vecTableNames.toArray();
/*  431 */     String[] arrayOfString = new String[arrayOfObject.length];
/*      */     
/*  433 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*  434 */       arrayOfString[b] = arrayOfObject[b].toString();
/*      */     }
/*      */     
/*  437 */     return arrayOfString;
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
/*      */   public CachedRowSet toCachedRowSet() throws SQLException {
/*  471 */     return this.crsInternal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsCrossJoin() {
/*  482 */     return this.supportedJOINs[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsInnerJoin() {
/*  492 */     return this.supportedJOINs[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsLeftOuterJoin() {
/*  502 */     return this.supportedJOINs[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsRightOuterJoin() {
/*  512 */     return this.supportedJOINs[3];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsFullJoin() {
/*  522 */     return this.supportedJOINs[4];
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
/*      */   public void setJoinType(int paramInt) throws SQLException {
/*  551 */     if (paramInt >= 0 && paramInt <= 4) {
/*  552 */       if (paramInt != 1)
/*      */       {
/*  554 */         throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.notsupported").toString());
/*      */       }
/*  556 */       Integer integer = Integer.valueOf(1);
/*  557 */       this.vecJoinType.add(integer);
/*      */     } else {
/*      */       
/*  560 */       throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.notdefined").toString());
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
/*      */   private boolean checkforMatchColumn(Joinable paramJoinable) throws SQLException {
/*  573 */     int[] arrayOfInt = paramJoinable.getMatchColumnIndexes();
/*  574 */     if (arrayOfInt.length <= 0) {
/*  575 */       return false;
/*      */     }
/*  577 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initJOIN(CachedRowSet paramCachedRowSet) throws SQLException {
/*      */     try {
/*  586 */       CachedRowSetImpl cachedRowSetImpl1 = (CachedRowSetImpl)paramCachedRowSet;
/*      */       
/*  588 */       CachedRowSetImpl cachedRowSetImpl2 = new CachedRowSetImpl();
/*  589 */       RowSetMetaDataImpl rowSetMetaDataImpl = new RowSetMetaDataImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  600 */       if (this.vecRowSetsInJOIN.isEmpty()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  606 */         this.crsInternal = (CachedRowSetImpl)paramCachedRowSet.createCopy();
/*  607 */         this.crsInternal.setMetaData((RowSetMetaDataImpl)cachedRowSetImpl1.getMetaData());
/*      */ 
/*      */         
/*  610 */         this.vecRowSetsInJOIN.add(cachedRowSetImpl1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  625 */         if (this.vecRowSetsInJOIN.size() - this.vecJoinType.size() == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  630 */           setJoinType(1);
/*  631 */         } else if (this.vecRowSetsInJOIN.size() - this.vecJoinType.size() == 1) {
/*      */         
/*      */         } 
/*      */ 
/*      */         
/*  636 */         this.vecTableNames.add(this.crsInternal.getTableName());
/*  637 */         this.vecTableNames.add(cachedRowSetImpl1.getTableName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  648 */         int i = cachedRowSetImpl1.size();
/*  649 */         int j = this.crsInternal.size();
/*      */ 
/*      */ 
/*      */         
/*  653 */         byte b1 = 0; byte b2;
/*  654 */         for (b2 = 0; b2 < (this.crsInternal.getMatchColumnIndexes()).length && 
/*  655 */           this.crsInternal.getMatchColumnIndexes()[b2] != -1; b2++) {
/*  656 */           b1++;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  661 */         rowSetMetaDataImpl
/*  662 */           .setColumnCount(this.crsInternal.getMetaData().getColumnCount() + cachedRowSetImpl1
/*  663 */             .getMetaData().getColumnCount() - b1);
/*      */         
/*  665 */         cachedRowSetImpl2.setMetaData(rowSetMetaDataImpl);
/*  666 */         this.crsInternal.beforeFirst();
/*  667 */         cachedRowSetImpl1.beforeFirst();
/*  668 */         for (b2 = 1; b2 <= j && 
/*  669 */           !this.crsInternal.isAfterLast(); b2++) {
/*      */ 
/*      */           
/*  672 */           if (this.crsInternal.next()) {
/*  673 */             cachedRowSetImpl1.beforeFirst();
/*  674 */             for (byte b = 1; b <= i && 
/*  675 */               !cachedRowSetImpl1.isAfterLast(); b++) {
/*      */ 
/*      */               
/*  678 */               if (cachedRowSetImpl1.next()) {
/*  679 */                 boolean bool = true; byte b4;
/*  680 */                 for (b4 = 0; b4 < b1; b4++) {
/*      */                   
/*  682 */                   if (!this.crsInternal.getObject(this.crsInternal.getMatchColumnIndexes()[b4]).equals(cachedRowSetImpl1.getObject(cachedRowSetImpl1.getMatchColumnIndexes()[b4]))) {
/*  683 */                     bool = false;
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  687 */                 if (bool) {
/*      */ 
/*      */                   
/*  690 */                   byte b5 = 0;
/*      */                   
/*  692 */                   cachedRowSetImpl2.moveToInsertRow();
/*      */ 
/*      */                   
/*  695 */                   b4 = 1;
/*  696 */                   for (; b4 <= this.crsInternal.getMetaData().getColumnCount(); b4++) {
/*      */                     
/*  698 */                     bool = false;
/*  699 */                     for (byte b7 = 0; b7 < b1; b7++) {
/*  700 */                       if (b4 == this.crsInternal.getMatchColumnIndexes()[b7]) {
/*  701 */                         bool = true;
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*  705 */                     if (!bool) {
/*      */                       
/*  707 */                       cachedRowSetImpl2.updateObject(++b5, this.crsInternal.getObject(b4));
/*      */ 
/*      */                       
/*  710 */                       rowSetMetaDataImpl
/*  711 */                         .setColumnName(b5, this.crsInternal.getMetaData().getColumnName(b4));
/*  712 */                       rowSetMetaDataImpl.setTableName(b5, this.crsInternal.getTableName());
/*      */                       
/*  714 */                       rowSetMetaDataImpl.setColumnType(b4, this.crsInternal.getMetaData().getColumnType(b4));
/*  715 */                       rowSetMetaDataImpl.setAutoIncrement(b4, this.crsInternal.getMetaData().isAutoIncrement(b4));
/*  716 */                       rowSetMetaDataImpl.setCaseSensitive(b4, this.crsInternal.getMetaData().isCaseSensitive(b4));
/*  717 */                       rowSetMetaDataImpl.setCatalogName(b4, this.crsInternal.getMetaData().getCatalogName(b4));
/*  718 */                       rowSetMetaDataImpl.setColumnDisplaySize(b4, this.crsInternal.getMetaData().getColumnDisplaySize(b4));
/*  719 */                       rowSetMetaDataImpl.setColumnLabel(b4, this.crsInternal.getMetaData().getColumnLabel(b4));
/*  720 */                       rowSetMetaDataImpl.setColumnType(b4, this.crsInternal.getMetaData().getColumnType(b4));
/*  721 */                       rowSetMetaDataImpl.setColumnTypeName(b4, this.crsInternal.getMetaData().getColumnTypeName(b4));
/*  722 */                       rowSetMetaDataImpl.setCurrency(b4, this.crsInternal.getMetaData().isCurrency(b4));
/*  723 */                       rowSetMetaDataImpl.setNullable(b4, this.crsInternal.getMetaData().isNullable(b4));
/*  724 */                       rowSetMetaDataImpl.setPrecision(b4, this.crsInternal.getMetaData().getPrecision(b4));
/*  725 */                       rowSetMetaDataImpl.setScale(b4, this.crsInternal.getMetaData().getScale(b4));
/*  726 */                       rowSetMetaDataImpl.setSchemaName(b4, this.crsInternal.getMetaData().getSchemaName(b4));
/*  727 */                       rowSetMetaDataImpl.setSearchable(b4, this.crsInternal.getMetaData().isSearchable(b4));
/*  728 */                       rowSetMetaDataImpl.setSigned(b4, this.crsInternal.getMetaData().isSigned(b4));
/*      */                     
/*      */                     }
/*      */                     else {
/*      */ 
/*      */                       
/*  734 */                       cachedRowSetImpl2.updateObject(++b5, this.crsInternal.getObject(b4));
/*      */                       
/*  736 */                       rowSetMetaDataImpl.setColumnName(b5, this.crsInternal.getMetaData().getColumnName(b4));
/*  737 */                       rowSetMetaDataImpl
/*  738 */                         .setTableName(b5, this.crsInternal.getTableName() + "#" + cachedRowSetImpl1
/*      */                           
/*  740 */                           .getTableName());
/*      */ 
/*      */                       
/*  743 */                       rowSetMetaDataImpl.setColumnType(b4, this.crsInternal.getMetaData().getColumnType(b4));
/*  744 */                       rowSetMetaDataImpl.setAutoIncrement(b4, this.crsInternal.getMetaData().isAutoIncrement(b4));
/*  745 */                       rowSetMetaDataImpl.setCaseSensitive(b4, this.crsInternal.getMetaData().isCaseSensitive(b4));
/*  746 */                       rowSetMetaDataImpl.setCatalogName(b4, this.crsInternal.getMetaData().getCatalogName(b4));
/*  747 */                       rowSetMetaDataImpl.setColumnDisplaySize(b4, this.crsInternal.getMetaData().getColumnDisplaySize(b4));
/*  748 */                       rowSetMetaDataImpl.setColumnLabel(b4, this.crsInternal.getMetaData().getColumnLabel(b4));
/*  749 */                       rowSetMetaDataImpl.setColumnType(b4, this.crsInternal.getMetaData().getColumnType(b4));
/*  750 */                       rowSetMetaDataImpl.setColumnTypeName(b4, this.crsInternal.getMetaData().getColumnTypeName(b4));
/*  751 */                       rowSetMetaDataImpl.setCurrency(b4, this.crsInternal.getMetaData().isCurrency(b4));
/*  752 */                       rowSetMetaDataImpl.setNullable(b4, this.crsInternal.getMetaData().isNullable(b4));
/*  753 */                       rowSetMetaDataImpl.setPrecision(b4, this.crsInternal.getMetaData().getPrecision(b4));
/*  754 */                       rowSetMetaDataImpl.setScale(b4, this.crsInternal.getMetaData().getScale(b4));
/*  755 */                       rowSetMetaDataImpl.setSchemaName(b4, this.crsInternal.getMetaData().getSchemaName(b4));
/*  756 */                       rowSetMetaDataImpl.setSearchable(b4, this.crsInternal.getMetaData().isSearchable(b4));
/*  757 */                       rowSetMetaDataImpl.setSigned(b4, this.crsInternal.getMetaData().isSigned(b4));
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  765 */                   byte b6 = 1;
/*  766 */                   for (; b6 <= cachedRowSetImpl1.getMetaData().getColumnCount(); b6++) {
/*      */                     
/*  768 */                     bool = false;
/*  769 */                     for (byte b7 = 0; b7 < b1; b7++) {
/*  770 */                       if (b6 == cachedRowSetImpl1.getMatchColumnIndexes()[b7]) {
/*  771 */                         bool = true;
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*  775 */                     if (!bool) {
/*      */                       
/*  777 */                       cachedRowSetImpl2.updateObject(++b5, cachedRowSetImpl1.getObject(b6));
/*      */                       
/*  779 */                       rowSetMetaDataImpl
/*  780 */                         .setColumnName(b5, cachedRowSetImpl1.getMetaData().getColumnName(b6));
/*  781 */                       rowSetMetaDataImpl.setTableName(b5, cachedRowSetImpl1.getTableName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  795 */                       rowSetMetaDataImpl.setColumnType(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getColumnType(b6));
/*  796 */                       rowSetMetaDataImpl.setAutoIncrement(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isAutoIncrement(b6));
/*  797 */                       rowSetMetaDataImpl.setCaseSensitive(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isCaseSensitive(b6));
/*  798 */                       rowSetMetaDataImpl.setCatalogName(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getCatalogName(b6));
/*  799 */                       rowSetMetaDataImpl.setColumnDisplaySize(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getColumnDisplaySize(b6));
/*  800 */                       rowSetMetaDataImpl.setColumnLabel(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getColumnLabel(b6));
/*  801 */                       rowSetMetaDataImpl.setColumnType(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getColumnType(b6));
/*  802 */                       rowSetMetaDataImpl.setColumnTypeName(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getColumnTypeName(b6));
/*  803 */                       rowSetMetaDataImpl.setCurrency(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isCurrency(b6));
/*  804 */                       rowSetMetaDataImpl.setNullable(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isNullable(b6));
/*  805 */                       rowSetMetaDataImpl.setPrecision(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getPrecision(b6));
/*  806 */                       rowSetMetaDataImpl.setScale(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getScale(b6));
/*  807 */                       rowSetMetaDataImpl.setSchemaName(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().getSchemaName(b6));
/*  808 */                       rowSetMetaDataImpl.setSearchable(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isSearchable(b6));
/*  809 */                       rowSetMetaDataImpl.setSigned(b4 + b6 - 1, cachedRowSetImpl1.getMetaData().isSigned(b6));
/*      */                     } else {
/*      */                       
/*  812 */                       b4--;
/*      */                     } 
/*      */                   } 
/*  815 */                   cachedRowSetImpl2.insertRow();
/*  816 */                   cachedRowSetImpl2.moveToCurrentRow();
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  830 */         cachedRowSetImpl2.setMetaData(rowSetMetaDataImpl);
/*  831 */         cachedRowSetImpl2.setOriginal();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  836 */         int[] arrayOfInt = new int[b1];
/*  837 */         for (byte b3 = 0; b3 < b1; b3++) {
/*  838 */           arrayOfInt[b3] = this.crsInternal.getMatchColumnIndexes()[b3];
/*      */         }
/*  840 */         this.crsInternal = (CachedRowSetImpl)cachedRowSetImpl2.createCopy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  849 */         this.crsInternal.setMatchColumn(arrayOfInt);
/*      */         
/*  851 */         this.crsInternal.setMetaData(rowSetMetaDataImpl);
/*  852 */         this.vecRowSetsInJOIN.add(cachedRowSetImpl1);
/*      */       } 
/*  854 */     } catch (SQLException sQLException) {
/*      */       
/*  856 */       sQLException.printStackTrace();
/*  857 */       throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.initerror").toString() + sQLException);
/*  858 */     } catch (Exception exception) {
/*  859 */       exception.printStackTrace();
/*  860 */       throw new SQLException(this.resBundle.handleGetObject("joinrowsetimpl.genericerr").toString() + exception);
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
/*      */   public String getWhereClause() throws SQLException {
/*  878 */     String str1 = "Select ";
/*      */     
/*  880 */     String str2 = "";
/*  881 */     String str3 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  895 */     int i = this.vecRowSetsInJOIN.size(); byte b;
/*  896 */     for (b = 0; b < i; b++) {
/*  897 */       CachedRowSetImpl cachedRowSetImpl = this.vecRowSetsInJOIN.get(b);
/*  898 */       int j = cachedRowSetImpl.getMetaData().getColumnCount();
/*  899 */       str2 = str2.concat(cachedRowSetImpl.getTableName());
/*  900 */       str3 = str3.concat(str2 + ", ");
/*  901 */       byte b1 = 1;
/*  902 */       while (b1 < j) {
/*      */ 
/*      */         
/*  905 */         str1 = str1.concat(str2 + "." + cachedRowSetImpl.getMetaData().getColumnName(b1++));
/*  906 */         str1 = str1.concat(", ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  913 */     str1 = str1.substring(0, str1.lastIndexOf(","));
/*      */ 
/*      */     
/*  916 */     str1 = str1.concat(" from ");
/*      */ 
/*      */     
/*  919 */     str1 = str1.concat(str3);
/*      */ 
/*      */ 
/*      */     
/*  923 */     str1 = str1.substring(0, str1.lastIndexOf(","));
/*      */ 
/*      */     
/*  926 */     str1 = str1.concat(" where ");
/*      */ 
/*      */ 
/*      */     
/*  930 */     for (b = 0; b < i; b++) {
/*  931 */       str1 = str1.concat(((CachedRowSetImpl)this.vecRowSetsInJOIN
/*  932 */           .get(b)).getMatchColumnNames()[0]);
/*  933 */       if (b % 2 != 0) {
/*  934 */         str1 = str1.concat("=");
/*      */       } else {
/*  936 */         str1 = str1.concat(" and");
/*      */       } 
/*  938 */       str1 = str1.concat(" ");
/*      */     } 
/*      */     
/*  941 */     return str1;
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
/*      */   public boolean next() throws SQLException {
/*  966 */     return this.crsInternal.next();
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
/*      */   public void close() throws SQLException {
/*  980 */     this.crsInternal.close();
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
/*      */   public boolean wasNull() throws SQLException {
/*  996 */     return this.crsInternal.wasNull();
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
/*      */   public String getString(int paramInt) throws SQLException {
/* 1013 */     return this.crsInternal.getString(paramInt);
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
/*      */   public boolean getBoolean(int paramInt) throws SQLException {
/* 1030 */     return this.crsInternal.getBoolean(paramInt);
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
/*      */   public byte getByte(int paramInt) throws SQLException {
/* 1047 */     return this.crsInternal.getByte(paramInt);
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
/*      */   public short getShort(int paramInt) throws SQLException {
/* 1064 */     return this.crsInternal.getShort(paramInt);
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
/*      */   public int getInt(int paramInt) throws SQLException {
/* 1081 */     return this.crsInternal.getInt(paramInt);
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
/*      */   public long getLong(int paramInt) throws SQLException {
/* 1098 */     return this.crsInternal.getLong(paramInt);
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
/*      */   public float getFloat(int paramInt) throws SQLException {
/* 1115 */     return this.crsInternal.getFloat(paramInt);
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
/*      */   public double getDouble(int paramInt) throws SQLException {
/* 1132 */     return this.crsInternal.getDouble(paramInt);
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
/*      */   @Deprecated
/*      */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
/* 1158 */     return this.crsInternal.getBigDecimal(paramInt1);
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
/*      */   public byte[] getBytes(int paramInt) throws SQLException {
/* 1176 */     return this.crsInternal.getBytes(paramInt);
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
/*      */   public Date getDate(int paramInt) throws SQLException {
/* 1193 */     return this.crsInternal.getDate(paramInt);
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
/*      */   public Time getTime(int paramInt) throws SQLException {
/* 1210 */     return this.crsInternal.getTime(paramInt);
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
/*      */   public Timestamp getTimestamp(int paramInt) throws SQLException {
/* 1227 */     return this.crsInternal.getTimestamp(paramInt);
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
/*      */   public InputStream getAsciiStream(int paramInt) throws SQLException {
/* 1244 */     return this.crsInternal.getAsciiStream(paramInt);
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
/*      */   @Deprecated
/*      */   public InputStream getUnicodeStream(int paramInt) throws SQLException {
/* 1270 */     return this.crsInternal.getUnicodeStream(paramInt);
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
/*      */   public InputStream getBinaryStream(int paramInt) throws SQLException {
/* 1293 */     return this.crsInternal.getBinaryStream(paramInt);
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
/*      */   public String getString(String paramString) throws SQLException {
/* 1311 */     return this.crsInternal.getString(paramString);
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
/*      */   public boolean getBoolean(String paramString) throws SQLException {
/* 1327 */     return this.crsInternal.getBoolean(paramString);
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
/*      */   public byte getByte(String paramString) throws SQLException {
/* 1343 */     return this.crsInternal.getByte(paramString);
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
/*      */   public short getShort(String paramString) throws SQLException {
/* 1359 */     return this.crsInternal.getShort(paramString);
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
/*      */   public int getInt(String paramString) throws SQLException {
/* 1375 */     return this.crsInternal.getInt(paramString);
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
/*      */   public long getLong(String paramString) throws SQLException {
/* 1391 */     return this.crsInternal.getLong(paramString);
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
/*      */   public float getFloat(String paramString) throws SQLException {
/* 1407 */     return this.crsInternal.getFloat(paramString);
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
/*      */   public double getDouble(String paramString) throws SQLException {
/* 1423 */     return this.crsInternal.getDouble(paramString);
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
/*      */   public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
/* 1443 */     return this.crsInternal.getBigDecimal(paramString);
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
/*      */   public byte[] getBytes(String paramString) throws SQLException {
/* 1460 */     return this.crsInternal.getBytes(paramString);
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
/*      */   public Date getDate(String paramString) throws SQLException {
/* 1476 */     return this.crsInternal.getDate(paramString);
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
/*      */   public Time getTime(String paramString) throws SQLException {
/* 1492 */     return this.crsInternal.getTime(paramString);
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
/*      */   public Timestamp getTimestamp(String paramString) throws SQLException {
/* 1508 */     return this.crsInternal.getTimestamp(paramString);
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
/*      */   public InputStream getAsciiStream(String paramString) throws SQLException {
/* 1532 */     return this.crsInternal.getAsciiStream(paramString);
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
/*      */   @Deprecated
/*      */   public InputStream getUnicodeStream(String paramString) throws SQLException {
/* 1560 */     return this.crsInternal.getUnicodeStream(paramString);
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
/*      */   public InputStream getBinaryStream(String paramString) throws SQLException {
/* 1584 */     return this.crsInternal.getBinaryStream(paramString);
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
/*      */   public SQLWarning getWarnings() {
/* 1603 */     return this.crsInternal.getWarnings();
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
/*      */   public void clearWarnings() {
/* 1616 */     this.crsInternal.clearWarnings();
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
/*      */   public String getCursorName() throws SQLException {
/* 1643 */     return this.crsInternal.getCursorName();
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
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/* 1657 */     return this.crsInternal.getMetaData();
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
/*      */   public Object getObject(int paramInt) throws SQLException {
/* 1693 */     return this.crsInternal.getObject(paramInt);
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
/*      */   public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
/* 1735 */     return this.crsInternal.getObject(paramInt, paramMap);
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
/*      */   public Object getObject(String paramString) throws SQLException {
/* 1771 */     return this.crsInternal.getObject(paramString);
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
/*      */   public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
/* 1794 */     return this.crsInternal.getObject(paramString, paramMap);
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
/*      */   public Reader getCharacterStream(int paramInt) throws SQLException {
/* 1815 */     return this.crsInternal.getCharacterStream(paramInt);
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
/*      */   public Reader getCharacterStream(String paramString) throws SQLException {
/* 1835 */     return this.crsInternal.getCharacterStream(paramString);
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
/*      */   public BigDecimal getBigDecimal(int paramInt) throws SQLException {
/* 1852 */     return this.crsInternal.getBigDecimal(paramInt);
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
/*      */   public BigDecimal getBigDecimal(String paramString) throws SQLException {
/* 1868 */     return this.crsInternal.getBigDecimal(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/* 1877 */     return this.crsInternal.size();
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
/*      */   public boolean isBeforeFirst() throws SQLException {
/* 1889 */     return this.crsInternal.isBeforeFirst();
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
/*      */   public boolean isAfterLast() throws SQLException {
/* 1901 */     return this.crsInternal.isAfterLast();
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
/*      */   public boolean isFirst() throws SQLException {
/* 1913 */     return this.crsInternal.isFirst();
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
/*      */   public boolean isLast() throws SQLException {
/* 1929 */     return this.crsInternal.isLast();
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
/*      */   public void beforeFirst() throws SQLException {
/* 1941 */     this.crsInternal.beforeFirst();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void afterLast() throws SQLException {
/* 1952 */     this.crsInternal.afterLast();
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
/*      */   public boolean first() throws SQLException {
/* 1967 */     return this.crsInternal.first();
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
/*      */   public boolean last() throws SQLException {
/* 1983 */     return this.crsInternal.last();
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
/*      */   public int getRow() throws SQLException {
/* 1995 */     return this.crsInternal.getRow();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean absolute(int paramInt) throws SQLException {
/* 2046 */     return this.crsInternal.absolute(paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean relative(int paramInt) throws SQLException {
/* 2105 */     return this.crsInternal.relative(paramInt);
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
/*      */ 
/*      */   
/*      */   public boolean previous() throws SQLException {
/* 2151 */     return this.crsInternal.previous();
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
/*      */   public int findColumn(String paramString) throws SQLException {
/* 2165 */     return this.crsInternal.findColumn(paramString);
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
/*      */   public boolean rowUpdated() throws SQLException {
/* 2183 */     return this.crsInternal.rowUpdated();
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
/*      */   public boolean columnUpdated(int paramInt) throws SQLException {
/* 2199 */     return this.crsInternal.columnUpdated(paramInt);
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
/*      */   public boolean rowInserted() throws SQLException {
/* 2214 */     return this.crsInternal.rowInserted();
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
/*      */   public boolean rowDeleted() throws SQLException {
/* 2231 */     return this.crsInternal.rowDeleted();
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
/*      */   public void updateNull(int paramInt) throws SQLException {
/* 2261 */     this.crsInternal.updateNull(paramInt);
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
/*      */   public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
/* 2288 */     this.crsInternal.updateBoolean(paramInt, paramBoolean);
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
/*      */   public void updateByte(int paramInt, byte paramByte) throws SQLException {
/* 2315 */     this.crsInternal.updateByte(paramInt, paramByte);
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
/*      */   public void updateShort(int paramInt, short paramShort) throws SQLException {
/* 2342 */     this.crsInternal.updateShort(paramInt, paramShort);
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
/*      */   public void updateInt(int paramInt1, int paramInt2) throws SQLException {
/* 2369 */     this.crsInternal.updateInt(paramInt1, paramInt2);
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
/*      */   public void updateLong(int paramInt, long paramLong) throws SQLException {
/* 2396 */     this.crsInternal.updateLong(paramInt, paramLong);
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
/*      */   public void updateFloat(int paramInt, float paramFloat) throws SQLException {
/* 2423 */     this.crsInternal.updateFloat(paramInt, paramFloat);
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
/*      */   public void updateDouble(int paramInt, double paramDouble) throws SQLException {
/* 2450 */     this.crsInternal.updateDouble(paramInt, paramDouble);
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
/*      */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
/* 2477 */     this.crsInternal.updateBigDecimal(paramInt, paramBigDecimal);
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
/*      */   public void updateString(int paramInt, String paramString) throws SQLException {
/* 2507 */     this.crsInternal.updateString(paramInt, paramString);
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
/*      */   public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
/* 2534 */     this.crsInternal.updateBytes(paramInt, paramArrayOfbyte);
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
/*      */   public void updateDate(int paramInt, Date paramDate) throws SQLException {
/* 2562 */     this.crsInternal.updateDate(paramInt, paramDate);
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
/*      */   public void updateTime(int paramInt, Time paramTime) throws SQLException {
/* 2590 */     this.crsInternal.updateTime(paramInt, paramTime);
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
/*      */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
/* 2619 */     this.crsInternal.updateTimestamp(paramInt, paramTimestamp);
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
/*      */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 2644 */     this.crsInternal.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
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
/*      */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 2674 */     this.crsInternal.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
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
/*      */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
/* 2706 */     this.crsInternal.updateCharacterStream(paramInt1, paramReader, paramInt2);
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
/*      */   public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
/* 2738 */     this.crsInternal.updateObject(paramInt1, paramObject, paramInt2);
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
/*      */   public void updateObject(int paramInt, Object paramObject) throws SQLException {
/* 2765 */     this.crsInternal.updateObject(paramInt, paramObject);
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
/*      */   public void updateNull(String paramString) throws SQLException {
/* 2791 */     this.crsInternal.updateNull(paramString);
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
/*      */   public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
/* 2817 */     this.crsInternal.updateBoolean(paramString, paramBoolean);
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
/*      */   public void updateByte(String paramString, byte paramByte) throws SQLException {
/* 2843 */     this.crsInternal.updateByte(paramString, paramByte);
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
/*      */   public void updateShort(String paramString, short paramShort) throws SQLException {
/* 2869 */     this.crsInternal.updateShort(paramString, paramShort);
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
/*      */   public void updateInt(String paramString, int paramInt) throws SQLException {
/* 2895 */     this.crsInternal.updateInt(paramString, paramInt);
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
/*      */   public void updateLong(String paramString, long paramLong) throws SQLException {
/* 2921 */     this.crsInternal.updateLong(paramString, paramLong);
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
/*      */   public void updateFloat(String paramString, float paramFloat) throws SQLException {
/* 2947 */     this.crsInternal.updateFloat(paramString, paramFloat);
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
/*      */   public void updateDouble(String paramString, double paramDouble) throws SQLException {
/* 2973 */     this.crsInternal.updateDouble(paramString, paramDouble);
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
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/* 2999 */     this.crsInternal.updateBigDecimal(paramString, paramBigDecimal);
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
/*      */   public void updateString(String paramString1, String paramString2) throws SQLException {
/* 3025 */     this.crsInternal.updateString(paramString1, paramString2);
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
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/* 3051 */     this.crsInternal.updateBytes(paramString, paramArrayOfbyte);
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
/*      */   public void updateDate(String paramString, Date paramDate) throws SQLException {
/* 3079 */     this.crsInternal.updateDate(paramString, paramDate);
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
/*      */   public void updateTime(String paramString, Time paramTime) throws SQLException {
/* 3107 */     this.crsInternal.updateTime(paramString, paramTime);
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
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/* 3138 */     this.crsInternal.updateTimestamp(paramString, paramTimestamp);
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
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 3165 */     this.crsInternal.updateAsciiStream(paramString, paramInputStream, paramInt);
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
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 3195 */     this.crsInternal.updateBinaryStream(paramString, paramInputStream, paramInt);
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
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/* 3226 */     this.crsInternal.updateCharacterStream(paramString, paramReader, paramInt);
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
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/* 3260 */     this.crsInternal.updateObject(paramString, paramObject, paramInt);
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
/*      */   public void updateObject(String paramString, Object paramObject) throws SQLException {
/* 3286 */     this.crsInternal.updateObject(paramString, paramObject);
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
/*      */   public void insertRow() throws SQLException {
/* 3305 */     this.crsInternal.insertRow();
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
/*      */   public void updateRow() throws SQLException {
/* 3322 */     this.crsInternal.updateRow();
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
/*      */   public void deleteRow() throws SQLException {
/* 3341 */     this.crsInternal.deleteRow();
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
/*      */   public void refreshRow() throws SQLException {
/* 3355 */     this.crsInternal.refreshRow();
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
/*      */   public void cancelRowUpdates() throws SQLException {
/* 3376 */     this.crsInternal.cancelRowUpdates();
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
/*      */   public void moveToInsertRow() throws SQLException {
/* 3406 */     this.crsInternal.moveToInsertRow();
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
/*      */   public void moveToCurrentRow() throws SQLException {
/* 3420 */     this.crsInternal.moveToCurrentRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement getStatement() throws SQLException {
/* 3430 */     return this.crsInternal.getStatement();
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
/*      */   public Ref getRef(int paramInt) throws SQLException {
/* 3448 */     return this.crsInternal.getRef(paramInt);
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
/*      */   public Blob getBlob(int paramInt) throws SQLException {
/* 3466 */     return this.crsInternal.getBlob(paramInt);
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
/*      */   public Clob getClob(int paramInt) throws SQLException {
/* 3484 */     return this.crsInternal.getClob(paramInt);
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
/*      */   public Array getArray(int paramInt) throws SQLException {
/* 3503 */     return this.crsInternal.getArray(paramInt);
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
/*      */   public Ref getRef(String paramString) throws SQLException {
/* 3522 */     return this.crsInternal.getRef(paramString);
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
/*      */   public Blob getBlob(String paramString) throws SQLException {
/* 3540 */     return this.crsInternal.getBlob(paramString);
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
/*      */   public Clob getClob(String paramString) throws SQLException {
/* 3558 */     return this.crsInternal.getClob(paramString);
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
/*      */   public Array getArray(String paramString) throws SQLException {
/* 3576 */     return this.crsInternal.getArray(paramString);
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
/*      */   public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3599 */     return this.crsInternal.getDate(paramInt, paramCalendar);
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
/*      */   public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
/* 3621 */     return this.crsInternal.getDate(paramString, paramCalendar);
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
/*      */   public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3644 */     return this.crsInternal.getTime(paramInt, paramCalendar);
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
/*      */   public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
/* 3666 */     return this.crsInternal.getTime(paramString, paramCalendar);
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
/*      */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3689 */     return this.crsInternal.getTimestamp(paramInt, paramCalendar);
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
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
/* 3712 */     return this.crsInternal.getTimestamp(paramString, paramCalendar);
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
/*      */   public void setMetaData(RowSetMetaData paramRowSetMetaData) throws SQLException {
/* 3725 */     this.crsInternal.setMetaData(paramRowSetMetaData);
/*      */   }
/*      */   
/*      */   public ResultSet getOriginal() throws SQLException {
/* 3729 */     return this.crsInternal.getOriginal();
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
/*      */   public ResultSet getOriginalRow() throws SQLException {
/* 3743 */     return this.crsInternal.getOriginalRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOriginalRow() throws SQLException {
/* 3754 */     this.crsInternal.setOriginalRow();
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
/*      */   public int[] getKeyColumns() throws SQLException {
/* 3769 */     return this.crsInternal.getKeyColumns();
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
/*      */   public void setKeyColumns(int[] paramArrayOfint) throws SQLException {
/* 3789 */     this.crsInternal.setKeyColumns(paramArrayOfint);
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
/*      */   public void updateRef(int paramInt, Ref paramRef) throws SQLException {
/* 3817 */     this.crsInternal.updateRef(paramInt, paramRef);
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
/*      */   public void updateRef(String paramString, Ref paramRef) throws SQLException {
/* 3845 */     this.crsInternal.updateRef(paramString, paramRef);
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
/*      */   public void updateClob(int paramInt, Clob paramClob) throws SQLException {
/* 3873 */     this.crsInternal.updateClob(paramInt, paramClob);
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
/*      */   public void updateClob(String paramString, Clob paramClob) throws SQLException {
/* 3901 */     this.crsInternal.updateClob(paramString, paramClob);
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
/*      */   public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
/* 3929 */     this.crsInternal.updateBlob(paramInt, paramBlob);
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
/*      */   public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
/* 3957 */     this.crsInternal.updateBlob(paramString, paramBlob);
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
/*      */   public void updateArray(int paramInt, Array paramArray) throws SQLException {
/* 3985 */     this.crsInternal.updateArray(paramInt, paramArray);
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
/*      */   public void updateArray(String paramString, Array paramArray) throws SQLException {
/* 4013 */     this.crsInternal.updateArray(paramString, paramArray);
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
/*      */   public void execute() throws SQLException {
/* 4040 */     this.crsInternal.execute();
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
/*      */   public void execute(Connection paramConnection) throws SQLException {
/* 4062 */     this.crsInternal.execute(paramConnection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(int paramInt) throws SQLException {
/* 4069 */     return this.crsInternal.getURL(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(String paramString) throws SQLException {
/* 4076 */     return this.crsInternal.getURL(paramString);
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
/*      */   public void writeXml(ResultSet paramResultSet, Writer paramWriter) throws SQLException {
/* 4089 */     this.wrs = new WebRowSetImpl();
/* 4090 */     this.wrs.populate(paramResultSet);
/* 4091 */     this.wrs.writeXml(paramWriter);
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
/*      */   public void writeXml(Writer paramWriter) throws SQLException {
/* 4104 */     createWebRowSet().writeXml(paramWriter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readXml(Reader paramReader) throws SQLException {
/* 4113 */     this.wrs = new WebRowSetImpl();
/* 4114 */     this.wrs.readXml(paramReader);
/* 4115 */     this.crsInternal = (CachedRowSetImpl)this.wrs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readXml(InputStream paramInputStream) throws SQLException, IOException {
/* 4126 */     this.wrs = new WebRowSetImpl();
/* 4127 */     this.wrs.readXml(paramInputStream);
/* 4128 */     this.crsInternal = (CachedRowSetImpl)this.wrs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeXml(OutputStream paramOutputStream) throws SQLException, IOException {
/* 4139 */     createWebRowSet().writeXml(paramOutputStream);
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
/*      */   public void writeXml(ResultSet paramResultSet, OutputStream paramOutputStream) throws SQLException, IOException {
/* 4151 */     this.wrs = new WebRowSetImpl();
/* 4152 */     this.wrs.populate(paramResultSet);
/* 4153 */     this.wrs.writeXml(paramOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WebRowSet createWebRowSet() throws SQLException {
/* 4160 */     if (this.wrs != null)
/*      */     {
/* 4162 */       return this.wrs;
/*      */     }
/* 4164 */     this.wrs = new WebRowSetImpl();
/* 4165 */     this.crsInternal.beforeFirst();
/* 4166 */     this.wrs.populate(this.crsInternal);
/* 4167 */     return this.wrs;
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
/*      */   public int getJoinType() throws SQLException {
/* 4179 */     if (this.vecJoinType == null)
/*      */     {
/* 4181 */       setJoinType(1);
/*      */     }
/* 4183 */     Integer integer = this.vecJoinType.get(this.vecJoinType.size() - 1);
/* 4184 */     return integer.intValue();
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
/*      */   public void addRowSetListener(RowSetListener paramRowSetListener) {
/* 4210 */     this.crsInternal.addRowSetListener(paramRowSetListener);
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
/*      */   public void removeRowSetListener(RowSetListener paramRowSetListener) {
/* 4227 */     this.crsInternal.removeRowSetListener(paramRowSetListener);
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
/*      */   public Collection<?> toCollection() throws SQLException {
/* 4246 */     return this.crsInternal.toCollection();
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
/*      */   public Collection<?> toCollection(int paramInt) throws SQLException {
/* 4268 */     return this.crsInternal.toCollection(paramInt);
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
/*      */   public Collection<?> toCollection(String paramString) throws SQLException {
/* 4290 */     return this.crsInternal.toCollection(paramString);
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
/*      */   public CachedRowSet createCopySchema() throws SQLException {
/* 4316 */     return this.crsInternal.createCopySchema();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSyncProvider(String paramString) throws SQLException {
/* 4323 */     this.crsInternal.setSyncProvider(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void acceptChanges() throws SyncProviderException {
/* 4330 */     this.crsInternal.acceptChanges();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SyncProvider getSyncProvider() throws SQLException {
/* 4337 */     return this.crsInternal.getSyncProvider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 4347 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     try {
/* 4350 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 4351 */     } catch (IOException iOException) {
/* 4352 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/JoinRowSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */