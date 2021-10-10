/*      */ package com.sun.rowset.internal;
/*      */ 
/*      */ import com.sun.rowset.CachedRowSetImpl;
/*      */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Struct;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.sql.RowSetInternal;
/*      */ import javax.sql.rowset.CachedRowSet;
/*      */ import javax.sql.rowset.RowSetMetaDataImpl;
/*      */ import javax.sql.rowset.serial.SQLInputImpl;
/*      */ import javax.sql.rowset.serial.SerialArray;
/*      */ import javax.sql.rowset.serial.SerialBlob;
/*      */ import javax.sql.rowset.serial.SerialClob;
/*      */ import javax.sql.rowset.serial.SerialStruct;
/*      */ import javax.sql.rowset.spi.SyncProviderException;
/*      */ import javax.sql.rowset.spi.TransactionalWriter;
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
/*      */ public class CachedRowSetWriter
/*      */   implements TransactionalWriter, Serializable
/*      */ {
/*      */   private transient Connection con;
/*      */   private String selectCmd;
/*      */   private String updateCmd;
/*      */   private String updateWhere;
/*      */   private String deleteCmd;
/*      */   private String deleteWhere;
/*      */   private String insertCmd;
/*      */   private int[] keyCols;
/*      */   private Object[] params;
/*      */   private CachedRowSetReader reader;
/*      */   private ResultSetMetaData callerMd;
/*      */   private int callerColumnCount;
/*      */   private CachedRowSetImpl crsResolve;
/*      */   private ArrayList<Integer> status;
/*      */   private int iChangedValsInDbAndCRS;
/*      */   private int iChangedValsinDbOnly;
/*      */   private JdbcRowSetResourceBundle resBundle;
/*      */   static final long serialVersionUID = -8506030970299413976L;
/*      */   
/*      */   public CachedRowSetWriter() {
/*      */     try {
/*  205 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  206 */     } catch (IOException iOException) {
/*  207 */       throw new RuntimeException(iOException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeData(RowSetInternal paramRowSetInternal) throws SQLException {
/*  268 */     long l = 0L;
/*  269 */     boolean bool = false;
/*  270 */     PreparedStatement preparedStatement = null;
/*  271 */     this.iChangedValsInDbAndCRS = 0;
/*  272 */     this.iChangedValsinDbOnly = 0;
/*      */ 
/*      */     
/*  275 */     CachedRowSetImpl cachedRowSetImpl = (CachedRowSetImpl)paramRowSetInternal;
/*      */     
/*  277 */     this.crsResolve = new CachedRowSetImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     this.con = this.reader.connect(paramRowSetInternal);
/*      */ 
/*      */     
/*  286 */     if (this.con == null) {
/*  287 */       throw new SQLException(this.resBundle.handleGetObject("crswriter.connect").toString());
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
/*  301 */     initSQLStatements(cachedRowSetImpl);
/*      */ 
/*      */     
/*  304 */     RowSetMetaDataImpl rowSetMetaDataImpl1 = (RowSetMetaDataImpl)cachedRowSetImpl.getMetaData();
/*  305 */     RowSetMetaDataImpl rowSetMetaDataImpl2 = new RowSetMetaDataImpl();
/*      */     
/*  307 */     int i = rowSetMetaDataImpl1.getColumnCount();
/*  308 */     int j = cachedRowSetImpl.size() + 1;
/*  309 */     this.status = new ArrayList<>(j);
/*      */     
/*  311 */     this.status.add(0, null);
/*  312 */     rowSetMetaDataImpl2.setColumnCount(i);
/*      */     byte b;
/*  314 */     for (b = 1; b <= i; b++) {
/*  315 */       rowSetMetaDataImpl2.setColumnType(b, rowSetMetaDataImpl1.getColumnType(b));
/*  316 */       rowSetMetaDataImpl2.setColumnName(b, rowSetMetaDataImpl1.getColumnName(b));
/*  317 */       rowSetMetaDataImpl2.setNullable(b, 2);
/*      */     } 
/*  319 */     this.crsResolve.setMetaData(rowSetMetaDataImpl2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     if (this.callerColumnCount < 1) {
/*      */       
/*  326 */       if (this.reader.getCloseConnection() == true)
/*  327 */         this.con.close(); 
/*  328 */       return true;
/*      */     } 
/*      */     
/*  331 */     bool = cachedRowSetImpl.getShowDeleted();
/*  332 */     cachedRowSetImpl.setShowDeleted(true);
/*      */ 
/*      */     
/*  335 */     cachedRowSetImpl.beforeFirst();
/*      */     
/*  337 */     b = 1;
/*  338 */     while (cachedRowSetImpl.next()) {
/*  339 */       if (cachedRowSetImpl.rowDeleted()) {
/*      */         
/*  341 */         if (deleteOriginalRow(cachedRowSetImpl, this.crsResolve)) {
/*  342 */           this.status.add(b, Integer.valueOf(1));
/*  343 */           l++;
/*      */         }
/*      */         else {
/*      */           
/*  347 */           this.status.add(b, Integer.valueOf(3));
/*      */         }
/*      */       
/*  350 */       } else if (cachedRowSetImpl.rowInserted()) {
/*      */ 
/*      */         
/*  353 */         preparedStatement = this.con.prepareStatement(this.insertCmd);
/*  354 */         if (insertNewRow(cachedRowSetImpl, preparedStatement, this.crsResolve)) {
/*  355 */           this.status.add(b, Integer.valueOf(2));
/*  356 */           l++;
/*      */         }
/*      */         else {
/*      */           
/*  360 */           this.status.add(b, Integer.valueOf(3));
/*      */         } 
/*  362 */       } else if (cachedRowSetImpl.rowUpdated()) {
/*      */         
/*  364 */         if (updateOriginalRow(cachedRowSetImpl)) {
/*  365 */           this.status.add(b, Integer.valueOf(0));
/*  366 */           l++;
/*      */         }
/*      */         else {
/*      */           
/*  370 */           this.status.add(b, Integer.valueOf(3));
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  381 */         int k = cachedRowSetImpl.getMetaData().getColumnCount();
/*  382 */         this.status.add(b, Integer.valueOf(3));
/*      */         
/*  384 */         this.crsResolve.moveToInsertRow();
/*  385 */         for (byte b1 = 0; b1 < i; b1++) {
/*  386 */           this.crsResolve.updateNull(b1 + 1);
/*      */         }
/*      */         
/*  389 */         this.crsResolve.insertRow();
/*  390 */         this.crsResolve.moveToCurrentRow();
/*      */       } 
/*      */       
/*  393 */       b++;
/*      */     } 
/*      */ 
/*      */     
/*  397 */     if (preparedStatement != null) {
/*  398 */       preparedStatement.close();
/*      */     }
/*  400 */     cachedRowSetImpl.setShowDeleted(bool);
/*      */     
/*  402 */     cachedRowSetImpl.beforeFirst();
/*  403 */     this.crsResolve.beforeFirst();
/*      */     
/*  405 */     if (l != 0L) {
/*      */       
/*  407 */       SyncProviderException syncProviderException = new SyncProviderException(l + " " + this.resBundle.handleGetObject("crswriter.conflictsno").toString());
/*      */ 
/*      */       
/*  410 */       SyncResolverImpl syncResolverImpl = (SyncResolverImpl)syncProviderException.getSyncResolver();
/*      */       
/*  412 */       syncResolverImpl.setCachedRowSet(cachedRowSetImpl);
/*  413 */       syncResolverImpl.setCachedRowSetResolver(this.crsResolve);
/*      */       
/*  415 */       syncResolverImpl.setStatus(this.status);
/*  416 */       syncResolverImpl.setCachedRowSetWriter(this);
/*      */       
/*  418 */       throw syncProviderException;
/*      */     } 
/*  420 */     return true;
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
/*      */   private boolean updateOriginalRow(CachedRowSet paramCachedRowSet) throws SQLException {
/*  459 */     int i = 0;
/*  460 */     int j = 0;
/*      */ 
/*      */     
/*  463 */     ResultSet resultSet = paramCachedRowSet.getOriginalRow();
/*  464 */     resultSet.next();
/*      */     
/*      */     try {
/*  467 */       this.updateWhere = buildWhereClause(this.updateWhere, resultSet);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  481 */       String str = this.selectCmd.toLowerCase();
/*      */       
/*  483 */       int k = str.indexOf("where");
/*      */       
/*  485 */       if (k != -1) {
/*      */         
/*  487 */         String str1 = this.selectCmd.substring(0, k);
/*  488 */         this.selectCmd = str1;
/*      */       } 
/*      */       
/*  491 */       PreparedStatement preparedStatement = this.con.prepareStatement(this.selectCmd + this.updateWhere, 1005, 1007);
/*      */ 
/*      */       
/*  494 */       for (i = 0; i < this.keyCols.length; i++) {
/*  495 */         if (this.params[i] != null) {
/*  496 */           preparedStatement.setObject(++j, this.params[i]);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  503 */         preparedStatement.setMaxRows(paramCachedRowSet.getMaxRows());
/*  504 */         preparedStatement.setMaxFieldSize(paramCachedRowSet.getMaxFieldSize());
/*  505 */         preparedStatement.setEscapeProcessing(paramCachedRowSet.getEscapeProcessing());
/*  506 */         preparedStatement.setQueryTimeout(paramCachedRowSet.getQueryTimeout());
/*  507 */       } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/*  511 */       ResultSet resultSet1 = null;
/*  512 */       resultSet1 = preparedStatement.executeQuery();
/*  513 */       ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
/*      */       
/*  515 */       if (resultSet1.next()) {
/*  516 */         if (resultSet1.next())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  527 */           return true;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  534 */         resultSet1.first();
/*      */ 
/*      */         
/*  537 */         byte b = 0;
/*  538 */         Vector<Integer> vector = new Vector();
/*  539 */         String str1 = this.updateCmd;
/*      */ 
/*      */ 
/*      */         
/*  543 */         boolean bool1 = true;
/*  544 */         Object object = null;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  549 */         boolean bool2 = true;
/*  550 */         boolean bool3 = true;
/*      */         
/*  552 */         this.crsResolve.moveToInsertRow();
/*      */         
/*  554 */         for (i = 1; i <= this.callerColumnCount; i++) {
/*  555 */           Object object1 = resultSet.getObject(i);
/*  556 */           Object object2 = paramCachedRowSet.getObject(i);
/*  557 */           Object object3 = resultSet1.getObject(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  564 */           Map<String, Class<?>> map = (paramCachedRowSet.getTypeMap() == null) ? this.con.getTypeMap() : paramCachedRowSet.getTypeMap();
/*  565 */           if (object3 instanceof Struct) {
/*      */             
/*  567 */             Struct struct = (Struct)object3;
/*      */ 
/*      */             
/*  570 */             Class clazz = null;
/*  571 */             clazz = map.get(struct.getSQLTypeName());
/*  572 */             if (clazz != null) {
/*      */               
/*  574 */               SQLData sQLData = null;
/*      */               try {
/*  576 */                 sQLData = (SQLData)ReflectUtil.newInstance(clazz);
/*  577 */               } catch (Exception exception) {
/*  578 */                 throw new SQLException("Unable to Instantiate: ", exception);
/*      */               } 
/*      */               
/*  581 */               Object[] arrayOfObject = struct.getAttributes(map);
/*      */               
/*  583 */               SQLInputImpl sQLInputImpl = new SQLInputImpl(arrayOfObject, map);
/*      */               
/*  585 */               sQLData.readSQL(sQLInputImpl, struct.getSQLTypeName());
/*  586 */               object3 = sQLData;
/*      */             } 
/*  588 */           } else if (object3 instanceof SQLData) {
/*  589 */             object3 = new SerialStruct((SQLData)object3, map);
/*  590 */           } else if (object3 instanceof Blob) {
/*  591 */             object3 = new SerialBlob((Blob)object3);
/*  592 */           } else if (object3 instanceof Clob) {
/*  593 */             object3 = new SerialClob((Clob)object3);
/*  594 */           } else if (object3 instanceof Array) {
/*  595 */             object3 = new SerialArray((Array)object3, map);
/*      */           } 
/*      */ 
/*      */           
/*  599 */           bool1 = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  606 */           if (object3 == null && object1 != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  611 */             this.iChangedValsinDbOnly++;
/*      */ 
/*      */             
/*  614 */             bool1 = false;
/*  615 */             object = object3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  623 */           else if (object3 != null && !object3.equals(object1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  629 */             this.iChangedValsinDbOnly++;
/*      */ 
/*      */             
/*  632 */             bool1 = false;
/*  633 */             object = object3;
/*  634 */           } else if (object1 == null || object2 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  642 */             if (!bool2 || !bool3) {
/*  643 */               str1 = str1 + ", ";
/*      */             }
/*  645 */             str1 = str1 + paramCachedRowSet.getMetaData().getColumnName(i);
/*  646 */             vector.add(Integer.valueOf(i));
/*  647 */             str1 = str1 + " = ? ";
/*  648 */             bool2 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  656 */           else if (object1.equals(object2)) {
/*  657 */             b++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  666 */           else if (!object1.equals(object2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  683 */             if (paramCachedRowSet.columnUpdated(i)) {
/*  684 */               if (object3.equals(object1)) {
/*      */ 
/*      */ 
/*      */                 
/*  688 */                 if (!bool3 || !bool2) {
/*  689 */                   str1 = str1 + ", ";
/*      */                 }
/*  691 */                 str1 = str1 + paramCachedRowSet.getMetaData().getColumnName(i);
/*  692 */                 vector.add(Integer.valueOf(i));
/*  693 */                 str1 = str1 + " = ? ";
/*  694 */                 bool3 = false;
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */                 
/*  700 */                 bool1 = false;
/*  701 */                 object = object3;
/*  702 */                 this.iChangedValsInDbAndCRS++;
/*      */               } 
/*      */             }
/*      */           } 
/*      */           
/*  707 */           if (!bool1) {
/*  708 */             this.crsResolve.updateObject(i, object);
/*      */           } else {
/*  710 */             this.crsResolve.updateNull(i);
/*      */           } 
/*      */         } 
/*      */         
/*  714 */         resultSet1.close();
/*  715 */         preparedStatement.close();
/*      */         
/*  717 */         this.crsResolve.insertRow();
/*  718 */         this.crsResolve.moveToCurrentRow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  727 */         if ((!bool2 && vector.size() == 0) || b == this.callerColumnCount)
/*      */         {
/*  729 */           return false;
/*      */         }
/*      */         
/*  732 */         if (this.iChangedValsInDbAndCRS != 0 || this.iChangedValsinDbOnly != 0) {
/*  733 */           return true;
/*      */         }
/*      */ 
/*      */         
/*  737 */         str1 = str1 + this.updateWhere;
/*      */         
/*  739 */         preparedStatement = this.con.prepareStatement(str1);
/*      */ 
/*      */         
/*  742 */         for (i = 0; i < vector.size(); i++) {
/*  743 */           Object object1 = paramCachedRowSet.getObject(((Integer)vector.get(i)).intValue());
/*  744 */           if (object1 != null) {
/*  745 */             preparedStatement.setObject(i + 1, object1);
/*      */           } else {
/*  747 */             preparedStatement.setNull(i + 1, paramCachedRowSet.getMetaData().getColumnType(i + 1));
/*      */           } 
/*  749 */         }  j = i;
/*      */ 
/*      */         
/*  752 */         for (i = 0; i < this.keyCols.length; i++) {
/*  753 */           if (this.params[i] != null) {
/*  754 */             preparedStatement.setObject(++j, this.params[i]);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  760 */         i = preparedStatement.executeUpdate();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  770 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  787 */       return true;
/*      */     }
/*  789 */     catch (SQLException sQLException) {
/*  790 */       sQLException.printStackTrace();
/*      */ 
/*      */       
/*  793 */       this.crsResolve.moveToInsertRow();
/*      */       
/*  795 */       for (i = 1; i <= this.callerColumnCount; i++) {
/*  796 */         this.crsResolve.updateNull(i);
/*      */       }
/*      */       
/*  799 */       this.crsResolve.insertRow();
/*  800 */       this.crsResolve.moveToCurrentRow();
/*      */       
/*  802 */       return true;
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
/*      */   private boolean insertNewRow(CachedRowSet paramCachedRowSet, PreparedStatement paramPreparedStatement, CachedRowSetImpl paramCachedRowSetImpl) throws SQLException {
/*  823 */     boolean bool = false;
/*      */     
/*  825 */     try(PreparedStatement null = this.con.prepareStatement(this.selectCmd, 1005, 1007); 
/*      */ 
/*      */         
/*  828 */         ResultSet null = preparedStatement.executeQuery(); 
/*  829 */         ResultSet null = this.con.getMetaData().getPrimaryKeys(null, null, paramCachedRowSet
/*  830 */           .getTableName())) {
/*      */ 
/*      */       
/*  833 */       ResultSetMetaData resultSetMetaData = paramCachedRowSet.getMetaData();
/*  834 */       int i = resultSetMetaData.getColumnCount();
/*  835 */       String[] arrayOfString = new String[i];
/*  836 */       byte b = 0;
/*  837 */       while (resultSet1.next()) {
/*  838 */         arrayOfString[b] = resultSet1.getString("COLUMN_NAME");
/*  839 */         b++;
/*      */       } 
/*      */       
/*  842 */       if (resultSet.next()) {
/*  843 */         for (String str : arrayOfString) {
/*  844 */           if (isPKNameValid(str, resultSetMetaData)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  855 */             Object object = paramCachedRowSet.getObject(str);
/*  856 */             if (object == null) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  864 */             String str1 = resultSet.getObject(str).toString();
/*  865 */             if (object.toString().equals(str1)) {
/*  866 */               bool = true;
/*  867 */               this.crsResolve.moveToInsertRow();
/*  868 */               for (byte b1 = 1; b1 <= i; b1++) {
/*  869 */                 String str2 = resultSet.getMetaData().getColumnName(b1);
/*  870 */                 if (str2.equals(str)) {
/*  871 */                   this.crsResolve.updateObject(b1, str1);
/*      */                 } else {
/*  873 */                   this.crsResolve.updateNull(b1);
/*      */                 } 
/*  875 */               }  this.crsResolve.insertRow();
/*  876 */               this.crsResolve.moveToCurrentRow();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*  881 */       if (bool) {
/*  882 */         return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteOriginalRow(CachedRowSet paramCachedRowSet, CachedRowSetImpl paramCachedRowSetImpl) throws SQLException {
/*  941 */     byte b2 = 0;
/*      */ 
/*      */     
/*  944 */     ResultSet resultSet1 = paramCachedRowSet.getOriginalRow();
/*  945 */     resultSet1.next();
/*      */     
/*  947 */     this.deleteWhere = buildWhereClause(this.deleteWhere, resultSet1);
/*  948 */     PreparedStatement preparedStatement = this.con.prepareStatement(this.selectCmd + this.deleteWhere, 1005, 1007);
/*      */     
/*      */     byte b1;
/*  951 */     for (b1 = 0; b1 < this.keyCols.length; b1++) {
/*  952 */       if (this.params[b1] != null) {
/*  953 */         preparedStatement.setObject(++b2, this.params[b1]);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  960 */       preparedStatement.setMaxRows(paramCachedRowSet.getMaxRows());
/*  961 */       preparedStatement.setMaxFieldSize(paramCachedRowSet.getMaxFieldSize());
/*  962 */       preparedStatement.setEscapeProcessing(paramCachedRowSet.getEscapeProcessing());
/*  963 */       preparedStatement.setQueryTimeout(paramCachedRowSet.getQueryTimeout());
/*  964 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     ResultSet resultSet2 = preparedStatement.executeQuery();
/*      */     
/*  973 */     if (resultSet2.next() == true) {
/*  974 */       if (resultSet2.next())
/*      */       {
/*  976 */         return true;
/*      */       }
/*  978 */       resultSet2.first();
/*      */ 
/*      */ 
/*      */       
/*  982 */       boolean bool = false;
/*      */       
/*  984 */       paramCachedRowSetImpl.moveToInsertRow();
/*      */       
/*  986 */       for (b1 = 1; b1 <= paramCachedRowSet.getMetaData().getColumnCount(); b1++) {
/*      */         
/*  988 */         Object object1 = resultSet1.getObject(b1);
/*  989 */         Object object2 = resultSet2.getObject(b1);
/*      */         
/*  991 */         if (object1 != null && object2 != null) {
/*  992 */           if (!object1.toString().equals(object2.toString())) {
/*  993 */             bool = true;
/*  994 */             paramCachedRowSetImpl.updateObject(b1, resultSet1.getObject(b1));
/*      */           } 
/*      */         } else {
/*  997 */           paramCachedRowSetImpl.updateNull(b1);
/*      */         } 
/*      */       } 
/*      */       
/* 1001 */       paramCachedRowSetImpl.insertRow();
/* 1002 */       paramCachedRowSetImpl.moveToCurrentRow();
/*      */       
/* 1004 */       if (bool)
/*      */       {
/*      */ 
/*      */         
/* 1008 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1015 */       String str = this.deleteCmd + this.deleteWhere;
/* 1016 */       preparedStatement = this.con.prepareStatement(str);
/*      */       
/* 1018 */       b2 = 0;
/* 1019 */       for (b1 = 0; b1 < this.keyCols.length; b1++) {
/* 1020 */         if (this.params[b1] != null) {
/* 1021 */           preparedStatement.setObject(++b2, this.params[b1]);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1027 */       if (preparedStatement.executeUpdate() != 1) {
/* 1028 */         return true;
/*      */       }
/* 1030 */       preparedStatement.close();
/*      */     } else {
/*      */       
/* 1033 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1037 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReader(CachedRowSetReader paramCachedRowSetReader) throws SQLException {
/* 1046 */     this.reader = paramCachedRowSetReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachedRowSetReader getReader() throws SQLException {
/* 1055 */     return this.reader;
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
/*      */   private void initSQLStatements(CachedRowSet paramCachedRowSet) throws SQLException {
/* 1072 */     this.callerMd = paramCachedRowSet.getMetaData();
/* 1073 */     this.callerColumnCount = this.callerMd.getColumnCount();
/* 1074 */     if (this.callerColumnCount < 1) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     String str1 = paramCachedRowSet.getTableName();
/* 1084 */     if (str1 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1090 */       str1 = this.callerMd.getTableName(1);
/* 1091 */       if (str1 == null || str1.length() == 0) {
/* 1092 */         throw new SQLException(this.resBundle.handleGetObject("crswriter.tname").toString());
/*      */       }
/*      */     } 
/* 1095 */     String str2 = this.callerMd.getCatalogName(1);
/* 1096 */     String str3 = this.callerMd.getSchemaName(1);
/* 1097 */     DatabaseMetaData databaseMetaData = this.con.getMetaData();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1104 */     this.selectCmd = "SELECT "; byte b;
/* 1105 */     for (b = 1; b <= this.callerColumnCount; b++) {
/* 1106 */       this.selectCmd += this.callerMd.getColumnName(b);
/* 1107 */       if (b < this.callerMd.getColumnCount()) {
/* 1108 */         this.selectCmd += ", ";
/*      */       } else {
/* 1110 */         this.selectCmd += " ";
/*      */       } 
/*      */     } 
/*      */     
/* 1114 */     this.selectCmd += "FROM " + buildTableName(databaseMetaData, str2, str3, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1119 */     this.updateCmd = "UPDATE " + buildTableName(databaseMetaData, str2, str3, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     String str4 = this.updateCmd.toLowerCase();
/*      */     
/* 1134 */     int i = str4.indexOf("where");
/*      */     
/* 1136 */     if (i != -1)
/*      */     {
/* 1138 */       this.updateCmd = this.updateCmd.substring(0, i);
/*      */     }
/* 1140 */     this.updateCmd += "SET ";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     this.insertCmd = "INSERT INTO " + buildTableName(databaseMetaData, str2, str3, str1);
/*      */     
/* 1147 */     this.insertCmd += "(";
/* 1148 */     for (b = 1; b <= this.callerColumnCount; b++) {
/* 1149 */       this.insertCmd += this.callerMd.getColumnName(b);
/* 1150 */       if (b < this.callerMd.getColumnCount()) {
/* 1151 */         this.insertCmd += ", ";
/*      */       } else {
/* 1153 */         this.insertCmd += ") VALUES (";
/*      */       } 
/* 1155 */     }  for (b = 1; b <= this.callerColumnCount; b++) {
/* 1156 */       this.insertCmd += "?";
/* 1157 */       if (b < this.callerColumnCount) {
/* 1158 */         this.insertCmd += ", ";
/*      */       } else {
/* 1160 */         this.insertCmd += ")";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1166 */     this.deleteCmd = "DELETE FROM " + buildTableName(databaseMetaData, str2, str3, str1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1172 */     buildKeyDesc(paramCachedRowSet);
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
/*      */   private String buildTableName(DatabaseMetaData paramDatabaseMetaData, String paramString1, String paramString2, String paramString3) throws SQLException {
/* 1196 */     String str = "";
/*      */     
/* 1198 */     paramString1 = paramString1.trim();
/* 1199 */     paramString2 = paramString2.trim();
/* 1200 */     paramString3 = paramString3.trim();
/*      */     
/* 1202 */     if (paramDatabaseMetaData.isCatalogAtStart() == true) {
/* 1203 */       if (paramString1 != null && paramString1.length() > 0) {
/* 1204 */         str = str + paramString1 + paramDatabaseMetaData.getCatalogSeparator();
/*      */       }
/* 1206 */       if (paramString2 != null && paramString2.length() > 0) {
/* 1207 */         str = str + paramString2 + ".";
/*      */       }
/* 1209 */       str = str + paramString3;
/*      */     } else {
/* 1211 */       if (paramString2 != null && paramString2.length() > 0) {
/* 1212 */         str = str + paramString2 + ".";
/*      */       }
/* 1214 */       str = str + paramString3;
/* 1215 */       if (paramString1 != null && paramString1.length() > 0) {
/* 1216 */         str = str + paramDatabaseMetaData.getCatalogSeparator() + paramString1;
/*      */       }
/*      */     } 
/* 1219 */     str = str + " ";
/* 1220 */     return str;
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
/*      */   private void buildKeyDesc(CachedRowSet paramCachedRowSet) throws SQLException {
/* 1244 */     this.keyCols = paramCachedRowSet.getKeyColumns();
/* 1245 */     ResultSetMetaData resultSetMetaData = paramCachedRowSet.getMetaData();
/* 1246 */     if (this.keyCols == null || this.keyCols.length == 0) {
/* 1247 */       ArrayList<Integer> arrayList = new ArrayList();
/*      */       byte b;
/* 1249 */       for (b = 0; b < this.callerColumnCount; b++) {
/* 1250 */         if (resultSetMetaData.getColumnType(b + 1) != 2005 && resultSetMetaData
/* 1251 */           .getColumnType(b + 1) != 2002 && resultSetMetaData
/* 1252 */           .getColumnType(b + 1) != 2009 && resultSetMetaData
/* 1253 */           .getColumnType(b + 1) != 2004 && resultSetMetaData
/* 1254 */           .getColumnType(b + 1) != 2003 && resultSetMetaData
/* 1255 */           .getColumnType(b + 1) != 1111)
/* 1256 */           arrayList.add(Integer.valueOf(b + 1)); 
/*      */       } 
/* 1258 */       this.keyCols = new int[arrayList.size()];
/* 1259 */       for (b = 0; b < arrayList.size(); b++)
/* 1260 */         this.keyCols[b] = ((Integer)arrayList.get(b)).intValue(); 
/*      */     } 
/* 1262 */     this.params = new Object[this.keyCols.length];
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
/*      */   private String buildWhereClause(String paramString, ResultSet paramResultSet) throws SQLException {
/* 1291 */     paramString = "WHERE ";
/*      */     
/* 1293 */     for (byte b = 0; b < this.keyCols.length; b++) {
/* 1294 */       if (b > 0) {
/* 1295 */         paramString = paramString + "AND ";
/*      */       }
/* 1297 */       paramString = paramString + this.callerMd.getColumnName(this.keyCols[b]);
/* 1298 */       this.params[b] = paramResultSet.getObject(this.keyCols[b]);
/* 1299 */       if (paramResultSet.wasNull() == true) {
/* 1300 */         paramString = paramString + " IS NULL ";
/*      */       } else {
/* 1302 */         paramString = paramString + " = ? ";
/*      */       } 
/*      */     } 
/* 1305 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void updateResolvedConflictToDB(CachedRowSet paramCachedRowSet, Connection paramConnection) throws SQLException {
/* 1311 */     String str1 = "WHERE ";
/* 1312 */     String str2 = " ";
/* 1313 */     String str3 = "UPDATE ";
/* 1314 */     int i = paramCachedRowSet.getMetaData().getColumnCount();
/* 1315 */     int[] arrayOfInt = paramCachedRowSet.getKeyColumns();
/*      */     
/* 1317 */     String str4 = "";
/*      */     
/* 1319 */     str1 = buildWhereClause(str1, paramCachedRowSet);
/*      */     
/* 1321 */     if (arrayOfInt == null || arrayOfInt.length == 0) {
/* 1322 */       arrayOfInt = new int[i];
/* 1323 */       for (byte b1 = 0; b1 < arrayOfInt.length;) {
/* 1324 */         arrayOfInt[b1++] = b1;
/*      */       }
/*      */     } 
/* 1327 */     Object[] arrayOfObject = new Object[arrayOfInt.length];
/*      */     
/* 1329 */     str3 = "UPDATE " + buildTableName(paramConnection.getMetaData(), paramCachedRowSet
/* 1330 */         .getMetaData().getCatalogName(1), paramCachedRowSet
/* 1331 */         .getMetaData().getSchemaName(1), paramCachedRowSet
/* 1332 */         .getTableName());
/*      */ 
/*      */ 
/*      */     
/* 1336 */     str3 = str3 + "SET ";
/*      */     
/* 1338 */     boolean bool = true;
/*      */     byte b;
/* 1340 */     for (b = 1; b <= i; b++) {
/* 1341 */       if (paramCachedRowSet.columnUpdated(b)) {
/* 1342 */         if (!bool) {
/* 1343 */           str4 = str4 + ", ";
/*      */         }
/* 1345 */         str4 = str4 + paramCachedRowSet.getMetaData().getColumnName(b);
/* 1346 */         str4 = str4 + " = ? ";
/* 1347 */         bool = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1352 */     str3 = str3 + str4;
/* 1353 */     str1 = "WHERE ";
/*      */     
/* 1355 */     for (b = 0; b < arrayOfInt.length; b++) {
/* 1356 */       if (b > 0) {
/* 1357 */         str1 = str1 + "AND ";
/*      */       }
/* 1359 */       str1 = str1 + paramCachedRowSet.getMetaData().getColumnName(arrayOfInt[b]);
/* 1360 */       arrayOfObject[b] = paramCachedRowSet.getObject(arrayOfInt[b]);
/* 1361 */       if (paramCachedRowSet.wasNull() == true) {
/* 1362 */         str1 = str1 + " IS NULL ";
/*      */       } else {
/* 1364 */         str1 = str1 + " = ? ";
/*      */       } 
/*      */     } 
/* 1367 */     str3 = str3 + str1;
/*      */     
/* 1369 */     PreparedStatement preparedStatement = paramConnection.prepareStatement(str3);
/*      */     
/* 1371 */     b = 0; int j;
/* 1372 */     for (j = 0; j < i; j++) {
/* 1373 */       if (paramCachedRowSet.columnUpdated(j + 1)) {
/* 1374 */         Object object = paramCachedRowSet.getObject(j + 1);
/* 1375 */         if (object != null) {
/* 1376 */           preparedStatement.setObject(++b, object);
/*      */         } else {
/* 1378 */           preparedStatement.setNull(j + 1, paramCachedRowSet.getMetaData().getColumnType(j + 1));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1384 */     for (j = 0; j < arrayOfInt.length; j++) {
/* 1385 */       if (arrayOfObject[j] != null) {
/* 1386 */         preparedStatement.setObject(++b, arrayOfObject[j]);
/*      */       }
/*      */     } 
/*      */     
/* 1390 */     j = preparedStatement.executeUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commit() throws SQLException {
/* 1398 */     this.con.commit();
/* 1399 */     if (this.reader.getCloseConnection() == true) {
/* 1400 */       this.con.close();
/*      */     }
/*      */   }
/*      */   
/*      */   public void commit(CachedRowSetImpl paramCachedRowSetImpl, boolean paramBoolean) throws SQLException {
/* 1405 */     this.con.commit();
/* 1406 */     if (paramBoolean && 
/* 1407 */       paramCachedRowSetImpl.getCommand() != null) {
/* 1408 */       paramCachedRowSetImpl.execute(this.con);
/*      */     }
/*      */     
/* 1411 */     if (this.reader.getCloseConnection() == true) {
/* 1412 */       this.con.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback() throws SQLException {
/* 1420 */     this.con.rollback();
/* 1421 */     if (this.reader.getCloseConnection() == true) {
/* 1422 */       this.con.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback(Savepoint paramSavepoint) throws SQLException {
/* 1430 */     this.con.rollback(paramSavepoint);
/* 1431 */     if (this.reader.getCloseConnection() == true) {
/* 1432 */       this.con.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1438 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     try {
/* 1441 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 1442 */     } catch (IOException iOException) {
/* 1443 */       throw new RuntimeException(iOException);
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
/*      */   private boolean isPKNameValid(String paramString, ResultSetMetaData paramResultSetMetaData) throws SQLException {
/* 1458 */     boolean bool = false;
/* 1459 */     int i = paramResultSetMetaData.getColumnCount();
/* 1460 */     for (byte b = 1; b <= i; b++) {
/* 1461 */       String str = paramResultSetMetaData.getColumnClassName(b);
/* 1462 */       if (str.equalsIgnoreCase(paramString)) {
/* 1463 */         bool = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1468 */     return bool;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/CachedRowSetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */