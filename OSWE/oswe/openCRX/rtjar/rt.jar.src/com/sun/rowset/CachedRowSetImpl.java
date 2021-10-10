/*       */ package com.sun.rowset;
/*       */ 
/*       */ import com.sun.rowset.internal.BaseRow;
/*       */ import com.sun.rowset.internal.CachedRowSetReader;
/*       */ import com.sun.rowset.internal.CachedRowSetWriter;
/*       */ import com.sun.rowset.internal.InsertRow;
/*       */ import com.sun.rowset.internal.Row;
/*       */ import java.io.ByteArrayInputStream;
/*       */ import java.io.ByteArrayOutputStream;
/*       */ import java.io.IOException;
/*       */ import java.io.InputStream;
/*       */ import java.io.InputStreamReader;
/*       */ import java.io.ObjectInputStream;
/*       */ import java.io.ObjectOutputStream;
/*       */ import java.io.OptionalDataException;
/*       */ import java.io.Reader;
/*       */ import java.io.Serializable;
/*       */ import java.io.StreamCorruptedException;
/*       */ import java.io.StringBufferInputStream;
/*       */ import java.io.StringReader;
/*       */ import java.io.UnsupportedEncodingException;
/*       */ import java.math.BigDecimal;
/*       */ import java.net.URL;
/*       */ import java.sql.Array;
/*       */ import java.sql.Blob;
/*       */ import java.sql.Clob;
/*       */ import java.sql.Connection;
/*       */ import java.sql.Date;
/*       */ import java.sql.NClob;
/*       */ import java.sql.Ref;
/*       */ import java.sql.ResultSet;
/*       */ import java.sql.ResultSetMetaData;
/*       */ import java.sql.RowId;
/*       */ import java.sql.SQLData;
/*       */ import java.sql.SQLException;
/*       */ import java.sql.SQLFeatureNotSupportedException;
/*       */ import java.sql.SQLWarning;
/*       */ import java.sql.SQLXML;
/*       */ import java.sql.Savepoint;
/*       */ import java.sql.Statement;
/*       */ import java.sql.Struct;
/*       */ import java.sql.Time;
/*       */ import java.sql.Timestamp;
/*       */ import java.text.DateFormat;
/*       */ import java.text.MessageFormat;
/*       */ import java.text.ParseException;
/*       */ import java.util.Arrays;
/*       */ import java.util.Calendar;
/*       */ import java.util.Collection;
/*       */ import java.util.Date;
/*       */ import java.util.Hashtable;
/*       */ import java.util.Iterator;
/*       */ import java.util.Map;
/*       */ import java.util.TreeMap;
/*       */ import java.util.Vector;
/*       */ import javax.sql.RowSet;
/*       */ import javax.sql.RowSetEvent;
/*       */ import javax.sql.RowSetInternal;
/*       */ import javax.sql.RowSetMetaData;
/*       */ import javax.sql.RowSetReader;
/*       */ import javax.sql.RowSetWriter;
/*       */ import javax.sql.rowset.BaseRowSet;
/*       */ import javax.sql.rowset.CachedRowSet;
/*       */ import javax.sql.rowset.RowSetMetaDataImpl;
/*       */ import javax.sql.rowset.RowSetWarning;
/*       */ import javax.sql.rowset.serial.SQLInputImpl;
/*       */ import javax.sql.rowset.serial.SerialArray;
/*       */ import javax.sql.rowset.serial.SerialBlob;
/*       */ import javax.sql.rowset.serial.SerialClob;
/*       */ import javax.sql.rowset.serial.SerialRef;
/*       */ import javax.sql.rowset.serial.SerialStruct;
/*       */ import javax.sql.rowset.spi.SyncFactory;
/*       */ import javax.sql.rowset.spi.SyncProvider;
/*       */ import javax.sql.rowset.spi.SyncProviderException;
/*       */ import javax.sql.rowset.spi.TransactionalWriter;
/*       */ import sun.reflect.misc.ReflectUtil;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class CachedRowSetImpl
/*       */   extends BaseRowSet
/*       */   implements RowSet, RowSetInternal, Serializable, Cloneable, CachedRowSet
/*       */ {
/*       */   private SyncProvider provider;
/*       */   private RowSetReader rowSetReader;
/*       */   private RowSetWriter rowSetWriter;
/*       */   private transient Connection conn;
/*       */   private transient ResultSetMetaData RSMD;
/*       */   private RowSetMetaDataImpl RowSetMD;
/*       */   private int[] keyCols;
/*       */   private String tableName;
/*       */   private Vector<Object> rvh;
/*       */   private int cursorPos;
/*       */   private int absolutePos;
/*       */   private int numDeleted;
/*       */   private int numRows;
/*       */   private InsertRow insertRow;
/*       */   private boolean onInsertRow;
/*       */   private int currentRow;
/*       */   private boolean lastValueNull;
/*       */   private SQLWarning sqlwarn;
/*   193 */   private String strMatchColumn = "";
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   198 */   private int iMatchColumn = -1;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private RowSetWarning rowsetWarning;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   208 */   private String DEFAULT_SYNC_PROVIDER = "com.sun.rowset.providers.RIOptimisticProvider";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean dbmslocatorsUpdateCopy;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private transient ResultSet resultSet;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int endPos;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int prevEndPos;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int startPos;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int startPrev;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int pageSize;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int maxRowsreached;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean pagenotend = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean onFirstPage;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean onLastPage;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int populatecallcount;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int totalRows;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean callWithCon;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private CachedRowSetReader crsReader;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Vector<Integer> iMatchColumns;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Vector<String> strMatchColumns;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean tXWriter = false;
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   313 */   private TransactionalWriter tWriter = null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected transient JdbcRowSetResourceBundle resBundle;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean updateOnInsert;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   static final long serialVersionUID = 1884577171200622428L;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public CachedRowSetImpl() throws SQLException {
/*       */     try {
/*   354 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*   355 */     } catch (IOException iOException) {
/*   356 */       throw new RuntimeException(iOException);
/*       */     } 
/*       */ 
/*       */     
/*   360 */     this
/*   361 */       .provider = SyncFactory.getInstance(this.DEFAULT_SYNC_PROVIDER);
/*       */     
/*   363 */     if (!(this.provider instanceof com.sun.rowset.providers.RIOptimisticProvider)) {
/*   364 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidp").toString());
/*       */     }
/*       */     
/*   367 */     this.rowSetReader = this.provider.getRowSetReader();
/*   368 */     this.rowSetWriter = this.provider.getRowSetWriter();
/*       */ 
/*       */     
/*   371 */     initParams();
/*       */     
/*   373 */     initContainer();
/*       */ 
/*       */     
/*   376 */     initProperties();
/*       */ 
/*       */     
/*   379 */     this.onInsertRow = false;
/*   380 */     this.insertRow = null;
/*       */ 
/*       */     
/*   383 */     this.sqlwarn = new SQLWarning();
/*   384 */     this.rowsetWarning = new RowSetWarning();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public CachedRowSetImpl(Hashtable paramHashtable) throws SQLException {
/*       */     try {
/*   456 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*   457 */     } catch (IOException iOException) {
/*   458 */       throw new RuntimeException(iOException);
/*       */     } 
/*       */     
/*   461 */     if (paramHashtable == null) {
/*   462 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.nullhash").toString());
/*       */     }
/*       */     
/*   465 */     String str = (String)paramHashtable.get("rowset.provider.classname");
/*       */ 
/*       */ 
/*       */     
/*   469 */     this
/*   470 */       .provider = SyncFactory.getInstance(str);
/*       */     
/*   472 */     this.rowSetReader = this.provider.getRowSetReader();
/*   473 */     this.rowSetWriter = this.provider.getRowSetWriter();
/*       */     
/*   475 */     initParams();
/*   476 */     initContainer();
/*   477 */     initProperties();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void initContainer() {
/*   487 */     this.rvh = new Vector(100);
/*   488 */     this.cursorPos = 0;
/*   489 */     this.absolutePos = 0;
/*   490 */     this.numRows = 0;
/*   491 */     this.numDeleted = 0;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void initProperties() throws SQLException {
/*   502 */     if (this.resBundle == null) {
/*       */       try {
/*   504 */         this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*   505 */       } catch (IOException iOException) {
/*   506 */         throw new RuntimeException(iOException);
/*       */       } 
/*       */     }
/*   509 */     setShowDeleted(false);
/*   510 */     setQueryTimeout(0);
/*   511 */     setMaxRows(0);
/*   512 */     setMaxFieldSize(0);
/*   513 */     setType(1004);
/*   514 */     setConcurrency(1008);
/*   515 */     if (this.rvh.size() > 0 && !isReadOnly()) {
/*   516 */       setReadOnly(false);
/*       */     } else {
/*   518 */       setReadOnly(true);
/*   519 */     }  setTransactionIsolation(2);
/*   520 */     setEscapeProcessing(true);
/*       */     
/*   522 */     checkTransactionalWriter();
/*       */ 
/*       */ 
/*       */     
/*   526 */     this.iMatchColumns = new Vector<>(10); byte b;
/*   527 */     for (b = 0; b < 10; b++) {
/*   528 */       this.iMatchColumns.add(b, Integer.valueOf(-1));
/*       */     }
/*       */     
/*   531 */     this.strMatchColumns = new Vector<>(10);
/*   532 */     for (b = 0; b < 10; b++) {
/*   533 */       this.strMatchColumns.add(b, null);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void checkTransactionalWriter() {
/*   542 */     if (this.rowSetWriter != null) {
/*   543 */       Class<?> clazz = this.rowSetWriter.getClass();
/*   544 */       if (clazz != null) {
/*   545 */         Class[] arrayOfClass = clazz.getInterfaces();
/*   546 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/*   547 */           if (arrayOfClass[b].getName().indexOf("TransactionalWriter") > 0) {
/*   548 */             this.tXWriter = true;
/*   549 */             establishTransactionalWriter();
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void establishTransactionalWriter() {
/*   560 */     this.tWriter = (TransactionalWriter)this.provider.getRowSetWriter();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setCommand(String paramString) throws SQLException {
/*   583 */     super.setCommand(paramString);
/*       */     
/*   585 */     if (!buildTableName(paramString).equals("")) {
/*   586 */       setTableName(buildTableName(paramString));
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void populate(ResultSet paramResultSet) throws SQLException {
/*   625 */     Map<String, Class<?>> map = getTypeMap();
/*       */ 
/*       */ 
/*       */     
/*   629 */     if (paramResultSet == null) {
/*   630 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.populate").toString());
/*       */     }
/*   632 */     this.resultSet = paramResultSet;
/*       */ 
/*       */     
/*   635 */     this.RSMD = paramResultSet.getMetaData();
/*       */ 
/*       */     
/*   638 */     this.RowSetMD = new RowSetMetaDataImpl();
/*   639 */     initMetaData(this.RowSetMD, this.RSMD);
/*       */ 
/*       */     
/*   642 */     this.RSMD = null;
/*   643 */     int i = this.RowSetMD.getColumnCount();
/*   644 */     int j = getMaxRows();
/*   645 */     byte b = 0;
/*   646 */     Row row = null;
/*       */     
/*   648 */     while (paramResultSet.next()) {
/*       */       
/*   650 */       row = new Row(i);
/*       */       
/*   652 */       if (b > j && j > 0) {
/*   653 */         this.rowsetWarning.setNextWarning(new RowSetWarning("Populating rows setting has exceeded max row setting"));
/*       */       }
/*       */       
/*   656 */       for (byte b1 = 1; b1 <= i; b1++) {
/*       */         Object object;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*   663 */         if (map == null || map.isEmpty()) {
/*   664 */           object = paramResultSet.getObject(b1);
/*       */         } else {
/*   666 */           object = paramResultSet.getObject(b1, map);
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*   673 */         if (object instanceof Struct) {
/*   674 */           object = new SerialStruct((Struct)object, map);
/*   675 */         } else if (object instanceof SQLData) {
/*   676 */           object = new SerialStruct((SQLData)object, map);
/*   677 */         } else if (object instanceof Blob) {
/*   678 */           object = new SerialBlob((Blob)object);
/*   679 */         } else if (object instanceof Clob) {
/*   680 */           object = new SerialClob((Clob)object);
/*   681 */         } else if (object instanceof Array) {
/*   682 */           if (map != null) {
/*   683 */             object = new SerialArray((Array)object, map);
/*       */           } else {
/*   685 */             object = new SerialArray((Array)object);
/*       */           } 
/*       */         } 
/*   688 */         row.initColumnObject(b1, object);
/*       */       } 
/*   690 */       b++;
/*   691 */       this.rvh.add(row);
/*       */     } 
/*       */     
/*   694 */     this.numRows = b;
/*       */ 
/*       */ 
/*       */     
/*   698 */     notifyRowSetChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void initMetaData(RowSetMetaDataImpl paramRowSetMetaDataImpl, ResultSetMetaData paramResultSetMetaData) throws SQLException {
/*   715 */     int i = paramResultSetMetaData.getColumnCount();
/*       */     
/*   717 */     paramRowSetMetaDataImpl.setColumnCount(i);
/*   718 */     for (byte b = 1; b <= i; b++) {
/*   719 */       paramRowSetMetaDataImpl.setAutoIncrement(b, paramResultSetMetaData.isAutoIncrement(b));
/*   720 */       if (paramResultSetMetaData.isAutoIncrement(b))
/*   721 */         this.updateOnInsert = true; 
/*   722 */       paramRowSetMetaDataImpl.setCaseSensitive(b, paramResultSetMetaData.isCaseSensitive(b));
/*   723 */       paramRowSetMetaDataImpl.setCurrency(b, paramResultSetMetaData.isCurrency(b));
/*   724 */       paramRowSetMetaDataImpl.setNullable(b, paramResultSetMetaData.isNullable(b));
/*   725 */       paramRowSetMetaDataImpl.setSigned(b, paramResultSetMetaData.isSigned(b));
/*   726 */       paramRowSetMetaDataImpl.setSearchable(b, paramResultSetMetaData.isSearchable(b));
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   731 */       int j = paramResultSetMetaData.getColumnDisplaySize(b);
/*   732 */       if (j < 0) {
/*   733 */         j = 0;
/*       */       }
/*   735 */       paramRowSetMetaDataImpl.setColumnDisplaySize(b, j);
/*   736 */       paramRowSetMetaDataImpl.setColumnLabel(b, paramResultSetMetaData.getColumnLabel(b));
/*   737 */       paramRowSetMetaDataImpl.setColumnName(b, paramResultSetMetaData.getColumnName(b));
/*   738 */       paramRowSetMetaDataImpl.setSchemaName(b, paramResultSetMetaData.getSchemaName(b));
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   743 */       int k = paramResultSetMetaData.getPrecision(b);
/*   744 */       if (k < 0) {
/*   745 */         k = 0;
/*       */       }
/*   747 */       paramRowSetMetaDataImpl.setPrecision(b, k);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   754 */       int m = paramResultSetMetaData.getScale(b);
/*   755 */       if (m < 0) {
/*   756 */         m = 0;
/*       */       }
/*   758 */       paramRowSetMetaDataImpl.setScale(b, m);
/*   759 */       paramRowSetMetaDataImpl.setTableName(b, paramResultSetMetaData.getTableName(b));
/*   760 */       paramRowSetMetaDataImpl.setCatalogName(b, paramResultSetMetaData.getCatalogName(b));
/*   761 */       paramRowSetMetaDataImpl.setColumnType(b, paramResultSetMetaData.getColumnType(b));
/*   762 */       paramRowSetMetaDataImpl.setColumnTypeName(b, paramResultSetMetaData.getColumnTypeName(b));
/*       */     } 
/*       */     
/*   765 */     if (this.conn != null)
/*       */     {
/*       */       
/*   768 */       this.dbmslocatorsUpdateCopy = this.conn.getMetaData().locatorsUpdateCopy();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void execute(Connection paramConnection) throws SQLException {
/*   792 */     setConnection(paramConnection);
/*       */     
/*   794 */     if (getPageSize() != 0) {
/*   795 */       this.crsReader = (CachedRowSetReader)this.provider.getRowSetReader();
/*   796 */       this.crsReader.setStartPosition(1);
/*   797 */       this.callWithCon = true;
/*   798 */       this.crsReader.readData(this);
/*       */     
/*       */     }
/*       */     else {
/*       */       
/*   803 */       this.rowSetReader.readData(this);
/*       */     } 
/*   805 */     this.RowSetMD = (RowSetMetaDataImpl)getMetaData();
/*       */     
/*   807 */     if (paramConnection != null)
/*       */     {
/*       */       
/*   810 */       this.dbmslocatorsUpdateCopy = paramConnection.getMetaData().locatorsUpdateCopy();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setConnection(Connection paramConnection) {
/*   830 */     this.conn = paramConnection;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void acceptChanges() throws SyncProviderException {
/*   869 */     if (this.onInsertRow == true) {
/*   870 */       throw new SyncProviderException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*       */     
/*   873 */     int i = this.cursorPos;
/*   874 */     boolean bool = false;
/*   875 */     boolean bool1 = false;
/*       */     
/*       */     try {
/*   878 */       if (this.rowSetWriter != null) {
/*   879 */         i = this.cursorPos;
/*   880 */         bool1 = this.rowSetWriter.writeData(this);
/*   881 */         this.cursorPos = i;
/*       */       } 
/*       */       
/*   884 */       if (this.tXWriter)
/*       */       {
/*   886 */         if (!bool1) {
/*   887 */           this.tWriter = (TransactionalWriter)this.rowSetWriter;
/*   888 */           this.tWriter.rollback();
/*   889 */           bool = false;
/*       */         } else {
/*   891 */           this.tWriter = (TransactionalWriter)this.rowSetWriter;
/*   892 */           if (this.tWriter instanceof CachedRowSetWriter) {
/*   893 */             ((CachedRowSetWriter)this.tWriter).commit(this, this.updateOnInsert);
/*       */           } else {
/*   895 */             this.tWriter.commit();
/*       */           } 
/*       */           
/*   898 */           bool = true;
/*       */         } 
/*       */       }
/*       */       
/*   902 */       if (bool == true) {
/*   903 */         setOriginal();
/*   904 */       } else if (!bool) {
/*   905 */         throw new SyncProviderException(this.resBundle.handleGetObject("cachedrowsetimpl.accfailed").toString());
/*       */       }
/*       */     
/*   908 */     } catch (SyncProviderException syncProviderException) {
/*   909 */       throw syncProviderException;
/*   910 */     } catch (SQLException sQLException) {
/*   911 */       sQLException.printStackTrace();
/*   912 */       throw new SyncProviderException(sQLException.getMessage());
/*   913 */     } catch (SecurityException securityException) {
/*   914 */       throw new SyncProviderException(securityException.getMessage());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void acceptChanges(Connection paramConnection) throws SyncProviderException {
/*   941 */     setConnection(paramConnection);
/*   942 */     acceptChanges();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void restoreOriginal() throws SQLException {
/*   958 */     for (Iterator<Row> iterator = this.rvh.iterator(); iterator.hasNext(); ) {
/*   959 */       Row row = iterator.next();
/*   960 */       if (row.getInserted() == true) {
/*   961 */         iterator.remove();
/*   962 */         this.numRows--; continue;
/*       */       } 
/*   964 */       if (row.getDeleted() == true) {
/*   965 */         row.clearDeleted();
/*       */       }
/*   967 */       if (row.getUpdated() == true) {
/*   968 */         row.clearUpdated();
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/*   973 */     this.cursorPos = 0;
/*       */ 
/*       */     
/*   976 */     notifyRowSetChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void release() throws SQLException {
/*   989 */     initContainer();
/*   990 */     notifyRowSetChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void undoDelete() throws SQLException {
/*  1003 */     if (!getShowDeleted()) {
/*       */       return;
/*       */     }
/*       */     
/*  1007 */     checkCursor();
/*       */ 
/*       */     
/*  1010 */     if (this.onInsertRow == true) {
/*  1011 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  1014 */     Row row = (Row)getCurrentRow();
/*  1015 */     if (row.getDeleted() == true) {
/*  1016 */       row.clearDeleted();
/*  1017 */       this.numDeleted--;
/*  1018 */       notifyRowChanged();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void undoInsert() throws SQLException {
/*  1037 */     checkCursor();
/*       */ 
/*       */     
/*  1040 */     if (this.onInsertRow == true) {
/*  1041 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  1044 */     Row row = (Row)getCurrentRow();
/*  1045 */     if (row.getInserted() == true) {
/*  1046 */       this.rvh.remove(this.cursorPos - 1);
/*  1047 */       this.numRows--;
/*  1048 */       notifyRowChanged();
/*       */     } else {
/*  1050 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.illegalop").toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void undoUpdate() throws SQLException {
/*  1076 */     moveToCurrentRow();
/*       */ 
/*       */ 
/*       */     
/*  1080 */     undoDelete();
/*       */     
/*  1082 */     undoInsert();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public RowSet createShared() throws SQLException {
/*       */     RowSet rowSet;
/*       */     try {
/*  1106 */       rowSet = (RowSet)clone();
/*  1107 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  1108 */       throw new SQLException(cloneNotSupportedException.getMessage());
/*       */     } 
/*  1110 */     return rowSet;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected Object clone() throws CloneNotSupportedException {
/*  1130 */     return super.clone();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public CachedRowSet createCopy() throws SQLException {
/*       */     ObjectInputStream objectInputStream;
/*  1156 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*       */     try {
/*  1158 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
/*  1159 */       objectOutputStream.writeObject(this);
/*  1160 */     } catch (IOException iOException) {
/*  1161 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { iOException.getMessage() }));
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*  1167 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
/*  1168 */       objectInputStream = new ObjectInputStream(byteArrayInputStream);
/*  1169 */     } catch (StreamCorruptedException streamCorruptedException) {
/*  1170 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { streamCorruptedException.getMessage() }));
/*  1171 */     } catch (IOException iOException) {
/*  1172 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { iOException.getMessage() }));
/*       */     } 
/*       */ 
/*       */     
/*       */     try {
/*  1177 */       CachedRowSetImpl cachedRowSetImpl = (CachedRowSetImpl)objectInputStream.readObject();
/*  1178 */       cachedRowSetImpl.resBundle = this.resBundle;
/*  1179 */       return cachedRowSetImpl;
/*       */     }
/*  1181 */     catch (ClassNotFoundException classNotFoundException) {
/*  1182 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { classNotFoundException.getMessage() }));
/*  1183 */     } catch (OptionalDataException optionalDataException) {
/*  1184 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { optionalDataException.getMessage() }));
/*  1185 */     } catch (IOException iOException) {
/*  1186 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.clonefail").toString(), new Object[] { iOException.getMessage() }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public CachedRowSet createCopySchema() throws SQLException {
/*  1218 */     int i = this.numRows;
/*  1219 */     this.numRows = 0;
/*       */     
/*  1221 */     CachedRowSet cachedRowSet = createCopy();
/*       */ 
/*       */     
/*  1224 */     this.numRows = i;
/*       */     
/*  1226 */     return cachedRowSet;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public CachedRowSet createCopyNoConstraints() throws SQLException {
/*  1250 */     CachedRowSetImpl cachedRowSetImpl = (CachedRowSetImpl)createCopy();
/*       */     
/*  1252 */     cachedRowSetImpl.initProperties();
/*       */     try {
/*  1254 */       cachedRowSetImpl.unsetMatchColumn(cachedRowSetImpl.getMatchColumnIndexes());
/*  1255 */     } catch (SQLException sQLException) {}
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*  1260 */       cachedRowSetImpl.unsetMatchColumn(cachedRowSetImpl.getMatchColumnNames());
/*  1261 */     } catch (SQLException sQLException) {}
/*       */ 
/*       */ 
/*       */     
/*  1265 */     return cachedRowSetImpl;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Collection<?> toCollection() throws SQLException {
/*  1285 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*       */     
/*  1287 */     for (byte b = 0; b < this.numRows; b++) {
/*  1288 */       treeMap.put(Integer.valueOf(b), this.rvh.get(b));
/*       */     }
/*       */     
/*  1291 */     return treeMap.values();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Collection<?> toCollection(int paramInt) throws SQLException {
/*  1314 */     int i = this.numRows;
/*  1315 */     Vector<Object> vector = new Vector(i);
/*       */ 
/*       */ 
/*       */     
/*  1319 */     CachedRowSetImpl cachedRowSetImpl = (CachedRowSetImpl)createCopy();
/*       */     
/*  1321 */     while (i != 0) {
/*  1322 */       cachedRowSetImpl.next();
/*  1323 */       vector.add(cachedRowSetImpl.getObject(paramInt));
/*  1324 */       i--;
/*       */     } 
/*       */     
/*  1327 */     return vector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Collection<?> toCollection(String paramString) throws SQLException {
/*  1349 */     return toCollection(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public SyncProvider getSyncProvider() throws SQLException {
/*  1368 */     return this.provider;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setSyncProvider(String paramString) throws SQLException {
/*  1379 */     this
/*  1380 */       .provider = SyncFactory.getInstance(paramString);
/*       */     
/*  1382 */     this.rowSetReader = this.provider.getRowSetReader();
/*  1383 */     this.rowSetWriter = this.provider.getRowSetWriter();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void execute() throws SQLException {
/*  1424 */     execute((Connection)null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean next() throws SQLException {
/*  1459 */     if (this.cursorPos < 0 || this.cursorPos >= this.numRows + 1) {
/*  1460 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  1463 */     boolean bool = internalNext();
/*  1464 */     notifyCursorMoved();
/*       */     
/*  1466 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean internalNext() throws SQLException {
/*  1493 */     boolean bool = false;
/*       */     
/*       */     do {
/*  1496 */       if (this.cursorPos < this.numRows) {
/*  1497 */         this.cursorPos++;
/*  1498 */         bool = true;
/*  1499 */       } else if (this.cursorPos == this.numRows) {
/*       */         
/*  1501 */         this.cursorPos++;
/*  1502 */         bool = false;
/*       */         break;
/*       */       } 
/*  1505 */     } while (!getShowDeleted() && rowDeleted() == true);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1510 */     if (bool == true) {
/*  1511 */       this.absolutePos++;
/*       */     } else {
/*  1513 */       this.absolutePos = 0;
/*       */     } 
/*  1515 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void close() throws SQLException {
/*  1530 */     this.cursorPos = 0;
/*  1531 */     this.absolutePos = 0;
/*  1532 */     this.numRows = 0;
/*  1533 */     this.numDeleted = 0;
/*       */ 
/*       */ 
/*       */     
/*  1537 */     initProperties();
/*       */ 
/*       */     
/*  1540 */     this.rvh.clear();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean wasNull() throws SQLException {
/*  1558 */     return this.lastValueNull;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void setLastValueNull(boolean paramBoolean) {
/*  1570 */     this.lastValueNull = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void checkIndex(int paramInt) throws SQLException {
/*  1591 */     if (paramInt < 1 || paramInt > this.RowSetMD.getColumnCount()) {
/*  1592 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcol").toString());
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void checkCursor() throws SQLException {
/*  1609 */     if (isAfterLast() == true || isBeforeFirst() == true) {
/*  1610 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private int getColIdxByName(String paramString) throws SQLException {
/*  1626 */     this.RowSetMD = (RowSetMetaDataImpl)getMetaData();
/*  1627 */     int i = this.RowSetMD.getColumnCount();
/*       */     
/*  1629 */     for (byte b = 1; b <= i; b++) {
/*  1630 */       String str = this.RowSetMD.getColumnName(b);
/*  1631 */       if (str != null && 
/*  1632 */         paramString.equalsIgnoreCase(str)) {
/*  1633 */         return b;
/*       */       }
/*       */     } 
/*       */     
/*  1637 */     throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalcolnm").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected BaseRow getCurrentRow() {
/*  1649 */     if (this.onInsertRow == true) {
/*  1650 */       return this.insertRow;
/*       */     }
/*  1652 */     return (BaseRow)this.rvh.get(this.cursorPos - 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected void removeCurrentRow() {
/*  1666 */     ((Row)getCurrentRow()).setDeleted();
/*  1667 */     this.rvh.remove(this.cursorPos - 1);
/*  1668 */     this.numRows--;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getString(int paramInt) throws SQLException {
/*  1694 */     checkIndex(paramInt);
/*       */     
/*  1696 */     checkCursor();
/*       */     
/*  1698 */     setLastValueNull(false);
/*  1699 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1702 */     if (object == null) {
/*  1703 */       setLastValueNull(true);
/*  1704 */       return null;
/*       */     } 
/*       */     
/*  1707 */     return object.toString();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getBoolean(int paramInt) throws SQLException {
/*  1730 */     checkIndex(paramInt);
/*       */     
/*  1732 */     checkCursor();
/*       */     
/*  1734 */     setLastValueNull(false);
/*  1735 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1738 */     if (object == null) {
/*  1739 */       setLastValueNull(true);
/*  1740 */       return false;
/*       */     } 
/*       */ 
/*       */     
/*  1744 */     if (object instanceof Boolean) {
/*  1745 */       return ((Boolean)object).booleanValue();
/*       */     }
/*       */ 
/*       */     
/*       */     try {
/*  1750 */       return (Double.compare(Double.parseDouble(object.toString()), 0.0D) != 0);
/*  1751 */     } catch (NumberFormatException numberFormatException) {
/*  1752 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.boolfail").toString(), new Object[] { object
/*  1753 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public byte getByte(int paramInt) throws SQLException {
/*  1780 */     checkIndex(paramInt);
/*       */     
/*  1782 */     checkCursor();
/*       */     
/*  1784 */     setLastValueNull(false);
/*  1785 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1788 */     if (object == null) {
/*  1789 */       setLastValueNull(true);
/*  1790 */       return 0;
/*       */     } 
/*       */     try {
/*  1793 */       return Byte.valueOf(object.toString()).byteValue();
/*  1794 */     } catch (NumberFormatException numberFormatException) {
/*  1795 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.bytefail").toString(), new Object[] { object
/*  1796 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public short getShort(int paramInt) throws SQLException {
/*  1823 */     checkIndex(paramInt);
/*       */     
/*  1825 */     checkCursor();
/*       */     
/*  1827 */     setLastValueNull(false);
/*  1828 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1831 */     if (object == null) {
/*  1832 */       setLastValueNull(true);
/*  1833 */       return 0;
/*       */     } 
/*       */     
/*       */     try {
/*  1837 */       return Short.valueOf(object.toString().trim()).shortValue();
/*  1838 */     } catch (NumberFormatException numberFormatException) {
/*  1839 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.shortfail").toString(), new Object[] { object
/*  1840 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getInt(int paramInt) throws SQLException {
/*  1866 */     checkIndex(paramInt);
/*       */     
/*  1868 */     checkCursor();
/*       */     
/*  1870 */     setLastValueNull(false);
/*  1871 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1874 */     if (object == null) {
/*  1875 */       setLastValueNull(true);
/*  1876 */       return 0;
/*       */     } 
/*       */     
/*       */     try {
/*  1880 */       return Integer.valueOf(object.toString().trim()).intValue();
/*  1881 */     } catch (NumberFormatException numberFormatException) {
/*  1882 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.intfail").toString(), new Object[] { object
/*  1883 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public long getLong(int paramInt) throws SQLException {
/*  1910 */     checkIndex(paramInt);
/*       */     
/*  1912 */     checkCursor();
/*       */     
/*  1914 */     setLastValueNull(false);
/*  1915 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1918 */     if (object == null) {
/*  1919 */       setLastValueNull(true);
/*  1920 */       return 0L;
/*       */     } 
/*       */     try {
/*  1923 */       return Long.valueOf(object.toString().trim()).longValue();
/*  1924 */     } catch (NumberFormatException numberFormatException) {
/*  1925 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.longfail").toString(), new Object[] { object
/*  1926 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public float getFloat(int paramInt) throws SQLException {
/*  1953 */     checkIndex(paramInt);
/*       */     
/*  1955 */     checkCursor();
/*       */     
/*  1957 */     setLastValueNull(false);
/*  1958 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  1961 */     if (object == null) {
/*  1962 */       setLastValueNull(true);
/*  1963 */       return 0.0F;
/*       */     } 
/*       */     try {
/*  1966 */       return (new Float(object.toString())).floatValue();
/*  1967 */     } catch (NumberFormatException numberFormatException) {
/*  1968 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.floatfail").toString(), new Object[] { object
/*  1969 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getDouble(int paramInt) throws SQLException {
/*  1997 */     checkIndex(paramInt);
/*       */     
/*  1999 */     checkCursor();
/*       */     
/*  2001 */     setLastValueNull(false);
/*  2002 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  2005 */     if (object == null) {
/*  2006 */       setLastValueNull(true);
/*  2007 */       return 0.0D;
/*       */     } 
/*       */     try {
/*  2010 */       return (new Double(object.toString().trim())).doubleValue();
/*  2011 */     } catch (NumberFormatException numberFormatException) {
/*  2012 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.doublefail").toString(), new Object[] { object
/*  2013 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
/*  2044 */     checkIndex(paramInt1);
/*       */     
/*  2046 */     checkCursor();
/*       */     
/*  2048 */     setLastValueNull(false);
/*  2049 */     Object object = getCurrentRow().getColumnObject(paramInt1);
/*       */ 
/*       */     
/*  2052 */     if (object == null) {
/*  2053 */       setLastValueNull(true);
/*  2054 */       return new BigDecimal(0);
/*       */     } 
/*       */     
/*  2057 */     BigDecimal bigDecimal = getBigDecimal(paramInt1);
/*       */     
/*  2059 */     return bigDecimal.setScale(paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public byte[] getBytes(int paramInt) throws SQLException {
/*  2086 */     checkIndex(paramInt);
/*       */     
/*  2088 */     checkCursor();
/*       */     
/*  2090 */     if (!isBinary(this.RowSetMD.getColumnType(paramInt))) {
/*  2091 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  2094 */     return (byte[])getCurrentRow().getColumnObject(paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Date getDate(int paramInt) throws SQLException {
/*       */     long l;
/*  2115 */     checkIndex(paramInt);
/*       */     
/*  2117 */     checkCursor();
/*       */     
/*  2119 */     setLastValueNull(false);
/*  2120 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  2123 */     if (object == null) {
/*  2124 */       setLastValueNull(true);
/*  2125 */       return null;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2135 */     switch (this.RowSetMD.getColumnType(paramInt)) {
/*       */       case 91:
/*  2137 */         l = ((Date)object).getTime();
/*  2138 */         return new Date(l);
/*       */       
/*       */       case 93:
/*  2141 */         l = ((Timestamp)object).getTime();
/*  2142 */         return new Date(l);
/*       */       
/*       */       case -1:
/*       */       case 1:
/*       */       case 12:
/*       */         try {
/*  2148 */           DateFormat dateFormat = DateFormat.getDateInstance();
/*  2149 */           return (Date)dateFormat.parse(object.toString());
/*  2150 */         } catch (ParseException parseException) {
/*  2151 */           throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.datefail").toString(), new Object[] { object
/*  2152 */                   .toString().trim(), Integer.valueOf(paramInt) }));
/*       */         } 
/*       */     } 
/*       */     
/*  2156 */     throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.datefail").toString(), new Object[] { object
/*  2157 */             .toString().trim(), Integer.valueOf(paramInt) }));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Time getTime(int paramInt) throws SQLException {
/*       */     long l;
/*  2179 */     checkIndex(paramInt);
/*       */     
/*  2181 */     checkCursor();
/*       */     
/*  2183 */     setLastValueNull(false);
/*  2184 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  2187 */     if (object == null) {
/*  2188 */       setLastValueNull(true);
/*  2189 */       return null;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2199 */     switch (this.RowSetMD.getColumnType(paramInt)) {
/*       */       case 92:
/*  2201 */         return (Time)object;
/*       */       
/*       */       case 93:
/*  2204 */         l = ((Timestamp)object).getTime();
/*  2205 */         return new Time(l);
/*       */       
/*       */       case -1:
/*       */       case 1:
/*       */       case 12:
/*       */         try {
/*  2211 */           DateFormat dateFormat = DateFormat.getTimeInstance();
/*  2212 */           return (Time)dateFormat.parse(object.toString());
/*  2213 */         } catch (ParseException parseException) {
/*  2214 */           throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.timefail").toString(), new Object[] { object
/*  2215 */                   .toString().trim(), Integer.valueOf(paramInt) }));
/*       */         } 
/*       */     } 
/*       */     
/*  2219 */     throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.timefail").toString(), new Object[] { object
/*  2220 */             .toString().trim(), Integer.valueOf(paramInt) }));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Timestamp getTimestamp(int paramInt) throws SQLException {
/*       */     long l;
/*  2242 */     checkIndex(paramInt);
/*       */     
/*  2244 */     checkCursor();
/*       */     
/*  2246 */     setLastValueNull(false);
/*  2247 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  2250 */     if (object == null) {
/*  2251 */       setLastValueNull(true);
/*  2252 */       return null;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2262 */     switch (this.RowSetMD.getColumnType(paramInt)) {
/*       */       case 93:
/*  2264 */         return (Timestamp)object;
/*       */       
/*       */       case 92:
/*  2267 */         l = ((Time)object).getTime();
/*  2268 */         return new Timestamp(l);
/*       */       
/*       */       case 91:
/*  2271 */         l = ((Date)object).getTime();
/*  2272 */         return new Timestamp(l);
/*       */       
/*       */       case -1:
/*       */       case 1:
/*       */       case 12:
/*       */         try {
/*  2278 */           DateFormat dateFormat = DateFormat.getTimeInstance();
/*  2279 */           return (Timestamp)dateFormat.parse(object.toString());
/*  2280 */         } catch (ParseException parseException) {
/*  2281 */           throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.timefail").toString(), new Object[] { object
/*  2282 */                   .toString().trim(), Integer.valueOf(paramInt) }));
/*       */         } 
/*       */     } 
/*       */     
/*  2286 */     throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.timefail").toString(), new Object[] { object
/*  2287 */             .toString().trim(), Integer.valueOf(paramInt) }));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputStream getAsciiStream(int paramInt) throws SQLException {
/*  2327 */     this.asciiStream = null;
/*       */ 
/*       */     
/*  2330 */     checkIndex(paramInt);
/*       */     
/*  2332 */     checkCursor();
/*       */     
/*  2334 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*  2335 */     if (object == null) {
/*  2336 */       this.lastValueNull = true;
/*  2337 */       return null;
/*       */     } 
/*       */     
/*       */     try {
/*  2341 */       if (isString(this.RowSetMD.getColumnType(paramInt))) {
/*  2342 */         this.asciiStream = new ByteArrayInputStream(((String)object).getBytes("ASCII"));
/*       */       } else {
/*  2344 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */       } 
/*  2346 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  2347 */       throw new SQLException(unsupportedEncodingException.getMessage());
/*       */     } 
/*       */     
/*  2350 */     return this.asciiStream;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public InputStream getUnicodeStream(int paramInt) throws SQLException {
/*  2377 */     this.unicodeStream = null;
/*       */ 
/*       */     
/*  2380 */     checkIndex(paramInt);
/*       */     
/*  2382 */     checkCursor();
/*       */     
/*  2384 */     if (!isBinary(this.RowSetMD.getColumnType(paramInt)) && 
/*  2385 */       !isString(this.RowSetMD.getColumnType(paramInt))) {
/*  2386 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  2389 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*  2390 */     if (object == null) {
/*  2391 */       this.lastValueNull = true;
/*  2392 */       return null;
/*       */     } 
/*       */     
/*  2395 */     this.unicodeStream = new StringBufferInputStream(object.toString());
/*       */     
/*  2397 */     return this.unicodeStream;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputStream getBinaryStream(int paramInt) throws SQLException {
/*  2433 */     this.binaryStream = null;
/*       */ 
/*       */     
/*  2436 */     checkIndex(paramInt);
/*       */     
/*  2438 */     checkCursor();
/*       */     
/*  2440 */     if (!isBinary(this.RowSetMD.getColumnType(paramInt))) {
/*  2441 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  2444 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*  2445 */     if (object == null) {
/*  2446 */       this.lastValueNull = true;
/*  2447 */       return null;
/*       */     } 
/*       */     
/*  2450 */     this.binaryStream = new ByteArrayInputStream((byte[])object);
/*       */     
/*  2452 */     return this.binaryStream;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getString(String paramString) throws SQLException {
/*  2476 */     return getString(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getBoolean(String paramString) throws SQLException {
/*  2495 */     return getBoolean(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public byte getByte(String paramString) throws SQLException {
/*  2515 */     return getByte(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public short getShort(String paramString) throws SQLException {
/*  2536 */     return getShort(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getInt(String paramString) throws SQLException {
/*  2557 */     return getInt(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public long getLong(String paramString) throws SQLException {
/*  2578 */     return getLong(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public float getFloat(String paramString) throws SQLException {
/*  2599 */     return getFloat(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public double getDouble(String paramString) throws SQLException {
/*  2621 */     return getDouble(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
/*  2646 */     return getBigDecimal(getColIdxByName(paramString), paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public byte[] getBytes(String paramString) throws SQLException {
/*  2667 */     return getBytes(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Date getDate(String paramString) throws SQLException {
/*  2685 */     return getDate(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Time getTime(String paramString) throws SQLException {
/*  2701 */     return getTime(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Timestamp getTimestamp(String paramString) throws SQLException {
/*  2717 */     return getTimestamp(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputStream getAsciiStream(String paramString) throws SQLException {
/*  2751 */     return getAsciiStream(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public InputStream getUnicodeStream(String paramString) throws SQLException {
/*  2778 */     return getUnicodeStream(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputStream getBinaryStream(String paramString) throws SQLException {
/*  2811 */     return getBinaryStream(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public SQLWarning getWarnings() {
/*  2833 */     return this.sqlwarn;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void clearWarnings() {
/*  2843 */     this.sqlwarn = null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getCursorName() throws SQLException {
/*  2871 */     throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.posupdate").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ResultSetMetaData getMetaData() throws SQLException {
/*  2901 */     return this.RowSetMD;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Object getObject(int paramInt) throws SQLException {
/*  2942 */     checkIndex(paramInt);
/*       */     
/*  2944 */     checkCursor();
/*       */     
/*  2946 */     setLastValueNull(false);
/*  2947 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  2950 */     if (object == null) {
/*  2951 */       setLastValueNull(true);
/*  2952 */       return null;
/*       */     } 
/*  2954 */     if (object instanceof Struct) {
/*  2955 */       Struct struct = (Struct)object;
/*  2956 */       Map<String, Class<?>> map = getTypeMap();
/*       */       
/*  2958 */       Class clazz = map.get(struct.getSQLTypeName());
/*  2959 */       if (clazz != null) {
/*       */         
/*  2961 */         SQLData sQLData = null;
/*       */         try {
/*  2963 */           sQLData = (SQLData)ReflectUtil.newInstance(clazz);
/*  2964 */         } catch (Exception exception) {
/*  2965 */           throw new SQLException("Unable to Instantiate: ", exception);
/*       */         } 
/*       */         
/*  2968 */         Object[] arrayOfObject = struct.getAttributes(map);
/*       */         
/*  2970 */         SQLInputImpl sQLInputImpl = new SQLInputImpl(arrayOfObject, map);
/*       */         
/*  2972 */         sQLData.readSQL(sQLInputImpl, struct.getSQLTypeName());
/*  2973 */         return sQLData;
/*       */       } 
/*       */     } 
/*  2976 */     return object;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Object getObject(String paramString) throws SQLException {
/*  3012 */     return getObject(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int findColumn(String paramString) throws SQLException {
/*  3028 */     return getColIdxByName(paramString);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Reader getCharacterStream(int paramInt) throws SQLException {
/*  3064 */     checkIndex(paramInt);
/*       */     
/*  3066 */     checkCursor();
/*       */     
/*  3068 */     if (isBinary(this.RowSetMD.getColumnType(paramInt))) {
/*  3069 */       Object object = getCurrentRow().getColumnObject(paramInt);
/*  3070 */       if (object == null) {
/*  3071 */         this.lastValueNull = true;
/*  3072 */         return null;
/*       */       } 
/*  3074 */       this.charStream = new InputStreamReader(new ByteArrayInputStream((byte[])object));
/*       */     }
/*  3076 */     else if (isString(this.RowSetMD.getColumnType(paramInt))) {
/*  3077 */       Object object = getCurrentRow().getColumnObject(paramInt);
/*  3078 */       if (object == null) {
/*  3079 */         this.lastValueNull = true;
/*  3080 */         return null;
/*       */       } 
/*  3082 */       this.charStream = new StringReader(object.toString());
/*       */     } else {
/*  3084 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     } 
/*       */     
/*  3087 */     return this.charStream;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Reader getCharacterStream(String paramString) throws SQLException {
/*  3111 */     return getCharacterStream(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public BigDecimal getBigDecimal(int paramInt) throws SQLException {
/*  3137 */     checkIndex(paramInt);
/*       */     
/*  3139 */     checkCursor();
/*       */     
/*  3141 */     setLastValueNull(false);
/*  3142 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  3145 */     if (object == null) {
/*  3146 */       setLastValueNull(true);
/*  3147 */       return null;
/*       */     } 
/*       */     try {
/*  3150 */       return new BigDecimal(object.toString().trim());
/*  3151 */     } catch (NumberFormatException numberFormatException) {
/*  3152 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.doublefail").toString(), new Object[] { object
/*  3153 */               .toString().trim(), Integer.valueOf(paramInt) }));
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public BigDecimal getBigDecimal(String paramString) throws SQLException {
/*  3177 */     return getBigDecimal(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int size() {
/*  3190 */     return this.numRows;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isBeforeFirst() throws SQLException {
/*  3202 */     if (this.cursorPos == 0 && this.numRows > 0) {
/*  3203 */       return true;
/*       */     }
/*  3205 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isAfterLast() throws SQLException {
/*  3218 */     if (this.cursorPos == this.numRows + 1 && this.numRows > 0) {
/*  3219 */       return true;
/*       */     }
/*  3221 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isFirst() throws SQLException {
/*  3235 */     int i = this.cursorPos;
/*  3236 */     int j = this.absolutePos;
/*  3237 */     internalFirst();
/*  3238 */     if (this.cursorPos == i) {
/*  3239 */       return true;
/*       */     }
/*  3241 */     this.cursorPos = i;
/*  3242 */     this.absolutePos = j;
/*  3243 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isLast() throws SQLException {
/*  3260 */     int i = this.cursorPos;
/*  3261 */     int j = this.absolutePos;
/*  3262 */     boolean bool = getShowDeleted();
/*  3263 */     setShowDeleted(true);
/*  3264 */     internalLast();
/*  3265 */     if (this.cursorPos == i) {
/*  3266 */       setShowDeleted(bool);
/*  3267 */       return true;
/*       */     } 
/*  3269 */     setShowDeleted(bool);
/*  3270 */     this.cursorPos = i;
/*  3271 */     this.absolutePos = j;
/*  3272 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void beforeFirst() throws SQLException {
/*  3285 */     if (getType() == 1003) {
/*  3286 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.beforefirst").toString());
/*       */     }
/*  3288 */     this.cursorPos = 0;
/*  3289 */     this.absolutePos = 0;
/*  3290 */     notifyCursorMoved();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void afterLast() throws SQLException {
/*  3301 */     if (this.numRows > 0) {
/*  3302 */       this.cursorPos = this.numRows + 1;
/*  3303 */       this.absolutePos = 0;
/*  3304 */       notifyCursorMoved();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean first() throws SQLException {
/*  3320 */     if (getType() == 1003) {
/*  3321 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.first").toString());
/*       */     }
/*       */ 
/*       */     
/*  3325 */     boolean bool = internalFirst();
/*  3326 */     notifyCursorMoved();
/*       */     
/*  3328 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean internalFirst() throws SQLException {
/*  3348 */     boolean bool = false;
/*       */     
/*  3350 */     if (this.numRows > 0) {
/*  3351 */       this.cursorPos = 1;
/*  3352 */       if (!getShowDeleted() && rowDeleted() == true) {
/*  3353 */         bool = internalNext();
/*       */       } else {
/*  3355 */         bool = true;
/*       */       } 
/*       */     } 
/*       */     
/*  3359 */     if (bool == true) {
/*  3360 */       this.absolutePos = 1;
/*       */     } else {
/*  3362 */       this.absolutePos = 0;
/*       */     } 
/*  3364 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean last() throws SQLException {
/*  3379 */     if (getType() == 1003) {
/*  3380 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.last").toString());
/*       */     }
/*       */ 
/*       */     
/*  3384 */     boolean bool = internalLast();
/*  3385 */     notifyCursorMoved();
/*       */     
/*  3387 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean internalLast() throws SQLException {
/*  3408 */     boolean bool = false;
/*       */     
/*  3410 */     if (this.numRows > 0) {
/*  3411 */       this.cursorPos = this.numRows;
/*  3412 */       if (!getShowDeleted() && rowDeleted() == true) {
/*  3413 */         bool = internalPrevious();
/*       */       } else {
/*  3415 */         bool = true;
/*       */       } 
/*       */     } 
/*  3418 */     if (bool == true) {
/*  3419 */       this.absolutePos = this.numRows - this.numDeleted;
/*       */     } else {
/*  3421 */       this.absolutePos = 0;
/*  3422 */     }  return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getRow() throws SQLException {
/*  3436 */     if (this.numRows > 0 && this.cursorPos > 0 && this.cursorPos < this.numRows + 1 && 
/*       */ 
/*       */       
/*  3439 */       !getShowDeleted() && !rowDeleted())
/*  3440 */       return this.absolutePos; 
/*  3441 */     if (getShowDeleted() == true) {
/*  3442 */       return this.cursorPos;
/*       */     }
/*  3444 */     return 0;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean absolute(int paramInt) throws SQLException {
/*  3496 */     if (paramInt == 0 || getType() == 1003) {
/*  3497 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.absolute").toString());
/*       */     }
/*       */     
/*  3500 */     if (paramInt > 0) {
/*  3501 */       if (paramInt > this.numRows) {
/*       */         
/*  3503 */         afterLast();
/*  3504 */         return false;
/*       */       } 
/*  3506 */       if (this.absolutePos <= 0) {
/*  3507 */         internalFirst();
/*       */       }
/*       */     } else {
/*  3510 */       if (this.cursorPos + paramInt < 0) {
/*       */         
/*  3512 */         beforeFirst();
/*  3513 */         return false;
/*       */       } 
/*  3515 */       if (this.absolutePos >= 0) {
/*  3516 */         internalLast();
/*       */       }
/*       */     } 
/*       */     do {
/*       */     
/*  3521 */     } while (this.absolutePos != paramInt && (
/*  3522 */       (this.absolutePos < paramInt) ? 
/*  3523 */       !internalNext() : 
/*       */ 
/*       */ 
/*       */       
/*  3527 */       !internalPrevious()));
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3532 */     notifyCursorMoved();
/*       */     
/*  3534 */     if (isAfterLast() || isBeforeFirst()) {
/*  3535 */       return false;
/*       */     }
/*  3537 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean relative(int paramInt) throws SQLException {
/*  3597 */     if (this.numRows == 0 || isBeforeFirst() || 
/*  3598 */       isAfterLast() || getType() == 1003) {
/*  3599 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.relative").toString());
/*       */     }
/*       */     
/*  3602 */     if (paramInt == 0) {
/*  3603 */       return true;
/*       */     }
/*       */     
/*  3606 */     if (paramInt > 0) {
/*  3607 */       if (this.cursorPos + paramInt > this.numRows) {
/*       */         
/*  3609 */         afterLast();
/*       */       } else {
/*  3611 */         for (byte b = 0; b < paramInt && 
/*  3612 */           internalNext(); b++);
/*       */       
/*       */       }
/*       */     
/*       */     }
/*  3617 */     else if (this.cursorPos + paramInt < 0) {
/*       */       
/*  3619 */       beforeFirst();
/*       */     } else {
/*  3621 */       for (int i = paramInt; i < 0 && 
/*  3622 */         internalPrevious(); i++);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3627 */     notifyCursorMoved();
/*       */     
/*  3629 */     if (isAfterLast() || isBeforeFirst()) {
/*  3630 */       return false;
/*       */     }
/*  3632 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean previous() throws SQLException {
/*  3679 */     if (getType() == 1003) {
/*  3680 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.last").toString());
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3687 */     if (this.cursorPos < 0 || this.cursorPos > this.numRows + 1) {
/*  3688 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  3691 */     boolean bool = internalPrevious();
/*  3692 */     notifyCursorMoved();
/*       */     
/*  3694 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected boolean internalPrevious() throws SQLException {
/*  3714 */     boolean bool = false;
/*       */     
/*       */     do {
/*  3717 */       if (this.cursorPos > 1) {
/*  3718 */         this.cursorPos--;
/*  3719 */         bool = true;
/*  3720 */       } else if (this.cursorPos == 1) {
/*       */         
/*  3722 */         this.cursorPos--;
/*  3723 */         bool = false;
/*       */         break;
/*       */       } 
/*  3726 */     } while (!getShowDeleted() && rowDeleted() == true);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3732 */     if (bool == true) {
/*  3733 */       this.absolutePos--;
/*       */     } else {
/*  3735 */       this.absolutePos = 0;
/*       */     } 
/*  3737 */     return bool;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean rowUpdated() throws SQLException {
/*  3761 */     checkCursor();
/*  3762 */     if (this.onInsertRow == true) {
/*  3763 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*  3765 */     return ((Row)getCurrentRow()).getUpdated();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean columnUpdated(int paramInt) throws SQLException {
/*  3784 */     checkCursor();
/*  3785 */     if (this.onInsertRow == true) {
/*  3786 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*  3788 */     return ((Row)getCurrentRow()).getColUpdated(paramInt - 1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean columnUpdated(String paramString) throws SQLException {
/*  3807 */     return columnUpdated(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean rowInserted() throws SQLException {
/*  3823 */     checkCursor();
/*  3824 */     if (this.onInsertRow == true) {
/*  3825 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*  3827 */     return ((Row)getCurrentRow()).getInserted();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean rowDeleted() throws SQLException {
/*  3846 */     if (isAfterLast() == true || 
/*  3847 */       isBeforeFirst() == true || this.onInsertRow == true)
/*       */     {
/*       */       
/*  3850 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*  3852 */     return ((Row)getCurrentRow()).getDeleted();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isNumeric(int paramInt) {
/*  3866 */     switch (paramInt) {
/*       */       case -7:
/*       */       case -6:
/*       */       case -5:
/*       */       case 2:
/*       */       case 3:
/*       */       case 4:
/*       */       case 5:
/*       */       case 6:
/*       */       case 7:
/*       */       case 8:
/*  3877 */         return true;
/*       */     } 
/*  3879 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isString(int paramInt) {
/*  3892 */     switch (paramInt) {
/*       */       case -1:
/*       */       case 1:
/*       */       case 12:
/*  3896 */         return true;
/*       */     } 
/*  3898 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isBinary(int paramInt) {
/*  3911 */     switch (paramInt) {
/*       */       case -4:
/*       */       case -3:
/*       */       case -2:
/*  3915 */         return true;
/*       */     } 
/*  3917 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isTemporal(int paramInt) {
/*  3932 */     switch (paramInt) {
/*       */       case 91:
/*       */       case 92:
/*       */       case 93:
/*  3936 */         return true;
/*       */     } 
/*  3938 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean isBoolean(int paramInt) {
/*  3953 */     switch (paramInt) {
/*       */       case -7:
/*       */       case 16:
/*  3956 */         return true;
/*       */     } 
/*  3958 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Object convertNumeric(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
/*  3990 */     if (paramInt1 == paramInt2) {
/*  3991 */       return paramObject;
/*       */     }
/*       */     
/*  3994 */     if (!isNumeric(paramInt2) && !isString(paramInt2)) {
/*  3995 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString() + paramInt2);
/*       */     }
/*       */     try {
/*       */       Integer integer;
/*  3999 */       switch (paramInt2) {
/*       */         case -7:
/*  4001 */           integer = Integer.valueOf(paramObject.toString().trim());
/*  4002 */           return integer.equals(Integer.valueOf(0)) ? 
/*  4003 */             Boolean.valueOf(false) : 
/*  4004 */             Boolean.valueOf(true);
/*       */         case -6:
/*  4006 */           return Byte.valueOf(paramObject.toString().trim());
/*       */         case 5:
/*  4008 */           return Short.valueOf(paramObject.toString().trim());
/*       */         case 4:
/*  4010 */           return Integer.valueOf(paramObject.toString().trim());
/*       */         case -5:
/*  4012 */           return Long.valueOf(paramObject.toString().trim());
/*       */         case 2:
/*       */         case 3:
/*  4015 */           return new BigDecimal(paramObject.toString().trim());
/*       */         case 6:
/*       */         case 7:
/*  4018 */           return new Float(paramObject.toString().trim());
/*       */         case 8:
/*  4020 */           return new Double(paramObject.toString().trim());
/*       */         case -1:
/*       */         case 1:
/*       */         case 12:
/*  4024 */           return paramObject.toString();
/*       */       } 
/*  4026 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString() + paramInt2);
/*       */     }
/*  4028 */     catch (NumberFormatException numberFormatException) {
/*  4029 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString() + paramInt2);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Object convertTemporal(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
/*  4089 */     if (paramInt1 == paramInt2) {
/*  4090 */       return paramObject;
/*       */     }
/*       */     
/*  4093 */     if (isNumeric(paramInt2) == true || (
/*  4094 */       !isString(paramInt2) && !isTemporal(paramInt2))) {
/*  4095 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*       */     try {
/*  4099 */       switch (paramInt2) {
/*       */         case 91:
/*  4101 */           if (paramInt1 == 93) {
/*  4102 */             return new Date(((Timestamp)paramObject).getTime());
/*       */           }
/*  4104 */           throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */         
/*       */         case 93:
/*  4107 */           if (paramInt1 == 92) {
/*  4108 */             return new Timestamp(((Time)paramObject).getTime());
/*       */           }
/*  4110 */           return new Timestamp(((Date)paramObject).getTime());
/*       */         
/*       */         case 92:
/*  4113 */           if (paramInt1 == 93) {
/*  4114 */             return new Time(((Timestamp)paramObject).getTime());
/*       */           }
/*  4116 */           throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */         
/*       */         case -1:
/*       */         case 1:
/*       */         case 12:
/*  4121 */           return paramObject.toString();
/*       */       } 
/*  4123 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*  4125 */     catch (NumberFormatException numberFormatException) {
/*  4126 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Object convertBoolean(Object paramObject, int paramInt1, int paramInt2) throws SQLException {
/*  4155 */     if (paramInt1 == paramInt2) {
/*  4156 */       return paramObject;
/*       */     }
/*       */     
/*  4159 */     if (isNumeric(paramInt2) == true || (
/*  4160 */       !isString(paramInt2) && !isBoolean(paramInt2))) {
/*  4161 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*       */     try {
/*       */       Integer integer;
/*  4166 */       switch (paramInt2) {
/*       */         case -7:
/*  4168 */           integer = Integer.valueOf(paramObject.toString().trim());
/*  4169 */           return integer.equals(Integer.valueOf(0)) ? 
/*  4170 */             Boolean.valueOf(false) : 
/*  4171 */             Boolean.valueOf(true);
/*       */         case 16:
/*  4173 */           return Boolean.valueOf(paramObject.toString().trim());
/*       */       } 
/*  4175 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString() + paramInt2);
/*       */     }
/*  4177 */     catch (NumberFormatException numberFormatException) {
/*  4178 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString() + paramInt2);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNull(int paramInt) throws SQLException {
/*  4210 */     checkIndex(paramInt);
/*       */     
/*  4212 */     checkCursor();
/*       */     
/*  4214 */     BaseRow baseRow = getCurrentRow();
/*  4215 */     baseRow.setColumnObject(paramInt, null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
/*  4244 */     checkIndex(paramInt);
/*       */     
/*  4246 */     checkCursor();
/*  4247 */     Object object = convertBoolean(Boolean.valueOf(paramBoolean), -7, this.RowSetMD
/*       */         
/*  4249 */         .getColumnType(paramInt));
/*       */     
/*  4251 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateByte(int paramInt, byte paramByte) throws SQLException {
/*  4279 */     checkIndex(paramInt);
/*       */     
/*  4281 */     checkCursor();
/*       */     
/*  4283 */     Object object = convertNumeric(Byte.valueOf(paramByte), -6, this.RowSetMD
/*       */         
/*  4285 */         .getColumnType(paramInt));
/*       */     
/*  4287 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateShort(int paramInt, short paramShort) throws SQLException {
/*  4315 */     checkIndex(paramInt);
/*       */     
/*  4317 */     checkCursor();
/*       */     
/*  4319 */     Object object = convertNumeric(Short.valueOf(paramShort), 5, this.RowSetMD
/*       */         
/*  4321 */         .getColumnType(paramInt));
/*       */     
/*  4323 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateInt(int paramInt1, int paramInt2) throws SQLException {
/*  4351 */     checkIndex(paramInt1);
/*       */     
/*  4353 */     checkCursor();
/*  4354 */     Object object = convertNumeric(Integer.valueOf(paramInt2), 4, this.RowSetMD
/*       */         
/*  4356 */         .getColumnType(paramInt1));
/*       */     
/*  4358 */     getCurrentRow().setColumnObject(paramInt1, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateLong(int paramInt, long paramLong) throws SQLException {
/*  4386 */     checkIndex(paramInt);
/*       */     
/*  4388 */     checkCursor();
/*       */     
/*  4390 */     Object object = convertNumeric(Long.valueOf(paramLong), -5, this.RowSetMD
/*       */         
/*  4392 */         .getColumnType(paramInt));
/*       */     
/*  4394 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateFloat(int paramInt, float paramFloat) throws SQLException {
/*  4423 */     checkIndex(paramInt);
/*       */     
/*  4425 */     checkCursor();
/*       */     
/*  4427 */     Object object = convertNumeric(Float.valueOf(paramFloat), 7, this.RowSetMD
/*       */         
/*  4429 */         .getColumnType(paramInt));
/*       */     
/*  4431 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateDouble(int paramInt, double paramDouble) throws SQLException {
/*  4459 */     checkIndex(paramInt);
/*       */     
/*  4461 */     checkCursor();
/*  4462 */     Object object = convertNumeric(Double.valueOf(paramDouble), 8, this.RowSetMD
/*       */         
/*  4464 */         .getColumnType(paramInt));
/*       */     
/*  4466 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
/*  4494 */     checkIndex(paramInt);
/*       */     
/*  4496 */     checkCursor();
/*       */     
/*  4498 */     Object object = convertNumeric(paramBigDecimal, 2, this.RowSetMD
/*       */         
/*  4500 */         .getColumnType(paramInt));
/*       */     
/*  4502 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateString(int paramInt, String paramString) throws SQLException {
/*  4533 */     checkIndex(paramInt);
/*       */     
/*  4535 */     checkCursor();
/*       */     
/*  4537 */     getCurrentRow().setColumnObject(paramInt, paramString);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
/*  4565 */     checkIndex(paramInt);
/*       */     
/*  4567 */     checkCursor();
/*       */     
/*  4569 */     if (!isBinary(this.RowSetMD.getColumnType(paramInt))) {
/*  4570 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  4573 */     getCurrentRow().setColumnObject(paramInt, paramArrayOfbyte);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateDate(int paramInt, Date paramDate) throws SQLException {
/*  4602 */     checkIndex(paramInt);
/*       */     
/*  4604 */     checkCursor();
/*       */     
/*  4606 */     Object object = convertTemporal(paramDate, 91, this.RowSetMD
/*       */         
/*  4608 */         .getColumnType(paramInt));
/*       */     
/*  4610 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateTime(int paramInt, Time paramTime) throws SQLException {
/*  4639 */     checkIndex(paramInt);
/*       */     
/*  4641 */     checkCursor();
/*       */     
/*  4643 */     Object object = convertTemporal(paramTime, 92, this.RowSetMD
/*       */         
/*  4645 */         .getColumnType(paramInt));
/*       */     
/*  4647 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
/*  4677 */     checkIndex(paramInt);
/*       */     
/*  4679 */     checkCursor();
/*       */     
/*  4681 */     Object object = convertTemporal(paramTimestamp, 93, this.RowSetMD
/*       */         
/*  4683 */         .getColumnType(paramInt));
/*       */     
/*  4685 */     getCurrentRow().setColumnObject(paramInt, object);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/*  4711 */     checkIndex(paramInt1);
/*       */     
/*  4713 */     checkCursor();
/*       */ 
/*       */     
/*  4716 */     if (!isString(this.RowSetMD.getColumnType(paramInt1)) && 
/*  4717 */       !isBinary(this.RowSetMD.getColumnType(paramInt1))) {
/*  4718 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  4721 */     byte[] arrayOfByte = new byte[paramInt2];
/*       */     try {
/*  4723 */       int i = 0;
/*       */       do {
/*  4725 */         i += paramInputStream.read(arrayOfByte, i, paramInt2 - i);
/*  4726 */       } while (i != paramInt2);
/*       */     }
/*  4728 */     catch (IOException iOException) {
/*  4729 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.asciistream").toString());
/*       */     } 
/*  4731 */     String str = new String(arrayOfByte);
/*       */     
/*  4733 */     getCurrentRow().setColumnObject(paramInt1, str);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/*  4765 */     checkIndex(paramInt1);
/*       */     
/*  4767 */     checkCursor();
/*       */     
/*  4769 */     if (!isBinary(this.RowSetMD.getColumnType(paramInt1))) {
/*  4770 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  4773 */     byte[] arrayOfByte = new byte[paramInt2];
/*       */     try {
/*  4775 */       int i = 0;
/*       */       do {
/*  4777 */         i += paramInputStream.read(arrayOfByte, i, paramInt2 - i);
/*  4778 */       } while (i != -1);
/*  4779 */     } catch (IOException iOException) {
/*  4780 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.binstream").toString());
/*       */     } 
/*       */     
/*  4783 */     getCurrentRow().setColumnObject(paramInt1, arrayOfByte);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
/*  4816 */     checkIndex(paramInt1);
/*       */     
/*  4818 */     checkCursor();
/*       */     
/*  4820 */     if (!isString(this.RowSetMD.getColumnType(paramInt1)) && 
/*  4821 */       !isBinary(this.RowSetMD.getColumnType(paramInt1))) {
/*  4822 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  4825 */     char[] arrayOfChar = new char[paramInt2];
/*       */     try {
/*  4827 */       int i = 0;
/*       */       do {
/*  4829 */         i += paramReader.read(arrayOfChar, i, paramInt2 - i);
/*  4830 */       } while (i != paramInt2);
/*       */     }
/*  4832 */     catch (IOException iOException) {
/*  4833 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.binstream").toString());
/*       */     } 
/*  4835 */     String str = new String(arrayOfChar);
/*       */     
/*  4837 */     getCurrentRow().setColumnObject(paramInt1, str);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
/*  4870 */     checkIndex(paramInt1);
/*       */     
/*  4872 */     checkCursor();
/*       */     
/*  4874 */     int i = this.RowSetMD.getColumnType(paramInt1);
/*  4875 */     if (i == 3 || i == 2) {
/*  4876 */       ((BigDecimal)paramObject).setScale(paramInt2);
/*       */     }
/*  4878 */     getCurrentRow().setColumnObject(paramInt1, paramObject);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateObject(int paramInt, Object paramObject) throws SQLException {
/*  4906 */     checkIndex(paramInt);
/*       */     
/*  4908 */     checkCursor();
/*       */     
/*  4910 */     getCurrentRow().setColumnObject(paramInt, paramObject);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNull(String paramString) throws SQLException {
/*  4934 */     updateNull(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
/*  4960 */     updateBoolean(getColIdxByName(paramString), paramBoolean);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateByte(String paramString, byte paramByte) throws SQLException {
/*  4986 */     updateByte(getColIdxByName(paramString), paramByte);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateShort(String paramString, short paramShort) throws SQLException {
/*  5012 */     updateShort(getColIdxByName(paramString), paramShort);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateInt(String paramString, int paramInt) throws SQLException {
/*  5038 */     updateInt(getColIdxByName(paramString), paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateLong(String paramString, long paramLong) throws SQLException {
/*  5064 */     updateLong(getColIdxByName(paramString), paramLong);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateFloat(String paramString, float paramFloat) throws SQLException {
/*  5090 */     updateFloat(getColIdxByName(paramString), paramFloat);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateDouble(String paramString, double paramDouble) throws SQLException {
/*  5116 */     updateDouble(getColIdxByName(paramString), paramDouble);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/*  5142 */     updateBigDecimal(getColIdxByName(paramString), paramBigDecimal);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateString(String paramString1, String paramString2) throws SQLException {
/*  5168 */     updateString(getColIdxByName(paramString1), paramString2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/*  5194 */     updateBytes(getColIdxByName(paramString), paramArrayOfbyte);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateDate(String paramString, Date paramDate) throws SQLException {
/*  5222 */     updateDate(getColIdxByName(paramString), paramDate);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateTime(String paramString, Time paramTime) throws SQLException {
/*  5250 */     updateTime(getColIdxByName(paramString), paramTime);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/*  5281 */     updateTimestamp(getColIdxByName(paramString), paramTimestamp);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/*  5306 */     updateAsciiStream(getColIdxByName(paramString), paramInputStream, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/*  5336 */     updateBinaryStream(getColIdxByName(paramString), paramInputStream, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/*  5369 */     updateCharacterStream(getColIdxByName(paramString), paramReader, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/*  5400 */     updateObject(getColIdxByName(paramString), paramObject, paramInt);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateObject(String paramString, Object paramObject) throws SQLException {
/*  5426 */     updateObject(getColIdxByName(paramString), paramObject);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void insertRow() throws SQLException {
/*       */     int i;
/*  5447 */     if (!this.onInsertRow || 
/*  5448 */       !this.insertRow.isCompleteRow(this.RowSetMD)) {
/*  5449 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.failedins").toString());
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  5454 */     Object[] arrayOfObject = getParams();
/*       */     
/*  5456 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*  5457 */       this.insertRow.setColumnObject(b + 1, arrayOfObject[b]);
/*       */     }
/*       */ 
/*       */     
/*  5461 */     Row row = new Row(this.RowSetMD.getColumnCount(), this.insertRow.getOrigRow());
/*  5462 */     row.setInserted();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5470 */     if (this.currentRow >= this.numRows || this.currentRow < 0) {
/*  5471 */       i = this.numRows;
/*       */     } else {
/*  5473 */       i = this.currentRow;
/*       */     } 
/*       */     
/*  5476 */     this.rvh.add(i, row);
/*  5477 */     this.numRows++;
/*       */     
/*  5479 */     notifyRowChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateRow() throws SQLException {
/*  5497 */     if (this.onInsertRow == true) {
/*  5498 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.updateins").toString());
/*       */     }
/*       */     
/*  5501 */     ((Row)getCurrentRow()).setUpdated();
/*       */ 
/*       */     
/*  5504 */     notifyRowChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void deleteRow() throws SQLException {
/*  5524 */     checkCursor();
/*       */     
/*  5526 */     ((Row)getCurrentRow()).setDeleted();
/*  5527 */     this.numDeleted++;
/*       */ 
/*       */     
/*  5530 */     notifyRowChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void refreshRow() throws SQLException {
/*  5545 */     checkCursor();
/*       */ 
/*       */     
/*  5548 */     if (this.onInsertRow == true) {
/*  5549 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  5552 */     Row row = (Row)getCurrentRow();
/*       */     
/*  5554 */     row.clearUpdated();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void cancelRowUpdates() throws SQLException {
/*  5572 */     checkCursor();
/*       */ 
/*       */     
/*  5575 */     if (this.onInsertRow == true) {
/*  5576 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcp").toString());
/*       */     }
/*       */     
/*  5579 */     Row row = (Row)getCurrentRow();
/*  5580 */     if (row.getUpdated() == true) {
/*  5581 */       row.clearUpdated();
/*  5582 */       notifyRowChanged();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void moveToInsertRow() throws SQLException {
/*  5613 */     if (getConcurrency() == 1007) {
/*  5614 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.movetoins").toString());
/*       */     }
/*  5616 */     if (this.insertRow == null) {
/*  5617 */       if (this.RowSetMD == null)
/*  5618 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.movetoins1").toString()); 
/*  5619 */       int i = this.RowSetMD.getColumnCount();
/*  5620 */       if (i > 0) {
/*  5621 */         this.insertRow = new InsertRow(i);
/*       */       } else {
/*  5623 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.movetoins2").toString());
/*       */       } 
/*       */     } 
/*  5626 */     this.onInsertRow = true;
/*       */ 
/*       */     
/*  5629 */     this.currentRow = this.cursorPos;
/*  5630 */     this.cursorPos = -1;
/*       */     
/*  5632 */     this.insertRow.initInsertRow();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void moveToCurrentRow() throws SQLException {
/*  5646 */     if (!this.onInsertRow) {
/*       */       return;
/*       */     }
/*  5649 */     this.cursorPos = this.currentRow;
/*  5650 */     this.onInsertRow = false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Statement getStatement() throws SQLException {
/*  5661 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
/*  5689 */     checkIndex(paramInt);
/*       */     
/*  5691 */     checkCursor();
/*       */     
/*  5693 */     setLastValueNull(false);
/*  5694 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  5697 */     if (object == null) {
/*  5698 */       setLastValueNull(true);
/*  5699 */       return null;
/*       */     } 
/*  5701 */     if (object instanceof Struct) {
/*  5702 */       Struct struct = (Struct)object;
/*       */ 
/*       */       
/*  5705 */       Class clazz = paramMap.get(struct.getSQLTypeName());
/*  5706 */       if (clazz != null) {
/*       */         
/*  5708 */         SQLData sQLData = null;
/*       */         try {
/*  5710 */           sQLData = (SQLData)ReflectUtil.newInstance(clazz);
/*  5711 */         } catch (Exception exception) {
/*  5712 */           throw new SQLException("Unable to Instantiate: ", exception);
/*       */         } 
/*       */         
/*  5715 */         Object[] arrayOfObject = struct.getAttributes(paramMap);
/*       */         
/*  5717 */         SQLInputImpl sQLInputImpl = new SQLInputImpl(arrayOfObject, paramMap);
/*       */         
/*  5719 */         sQLData.readSQL(sQLInputImpl, struct.getSQLTypeName());
/*  5720 */         return sQLData;
/*       */       } 
/*       */     } 
/*  5723 */     return object;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Ref getRef(int paramInt) throws SQLException {
/*  5745 */     checkIndex(paramInt);
/*       */     
/*  5747 */     checkCursor();
/*       */     
/*  5749 */     if (this.RowSetMD.getColumnType(paramInt) != 2006) {
/*  5750 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  5753 */     setLastValueNull(false);
/*  5754 */     Ref ref = (Ref)getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  5757 */     if (ref == null) {
/*  5758 */       setLastValueNull(true);
/*  5759 */       return null;
/*       */     } 
/*       */     
/*  5762 */     return ref;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Blob getBlob(int paramInt) throws SQLException {
/*  5784 */     checkIndex(paramInt);
/*       */     
/*  5786 */     checkCursor();
/*       */     
/*  5788 */     if (this.RowSetMD.getColumnType(paramInt) != 2004) {
/*  5789 */       System.out.println(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.type").toString(), new Object[] { Integer.valueOf(this.RowSetMD.getColumnType(paramInt)) }));
/*  5790 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     } 
/*       */     
/*  5793 */     setLastValueNull(false);
/*  5794 */     Blob blob = (Blob)getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  5797 */     if (blob == null) {
/*  5798 */       setLastValueNull(true);
/*  5799 */       return null;
/*       */     } 
/*       */     
/*  5802 */     return blob;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Clob getClob(int paramInt) throws SQLException {
/*  5824 */     checkIndex(paramInt);
/*       */     
/*  5826 */     checkCursor();
/*       */     
/*  5828 */     if (this.RowSetMD.getColumnType(paramInt) != 2005) {
/*  5829 */       System.out.println(MessageFormat.format(this.resBundle.handleGetObject("cachedrowsetimpl.type").toString(), new Object[] { Integer.valueOf(this.RowSetMD.getColumnType(paramInt)) }));
/*  5830 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     } 
/*       */     
/*  5833 */     setLastValueNull(false);
/*  5834 */     Clob clob = (Clob)getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  5837 */     if (clob == null) {
/*  5838 */       setLastValueNull(true);
/*  5839 */       return null;
/*       */     } 
/*       */     
/*  5842 */     return clob;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Array getArray(int paramInt) throws SQLException {
/*  5865 */     checkIndex(paramInt);
/*       */     
/*  5867 */     checkCursor();
/*       */     
/*  5869 */     if (this.RowSetMD.getColumnType(paramInt) != 2003) {
/*  5870 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  5873 */     setLastValueNull(false);
/*  5874 */     Array array = (Array)getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  5877 */     if (array == null) {
/*  5878 */       setLastValueNull(true);
/*  5879 */       return null;
/*       */     } 
/*       */     
/*  5882 */     return array;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
/*  5905 */     return getObject(getColIdxByName(paramString), paramMap);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Ref getRef(String paramString) throws SQLException {
/*  5923 */     return getRef(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Blob getBlob(String paramString) throws SQLException {
/*  5941 */     return getBlob(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Clob getClob(String paramString) throws SQLException {
/*  5960 */     return getClob(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Array getArray(String paramString) throws SQLException {
/*  5979 */     return getArray(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
/*  6005 */     checkIndex(paramInt);
/*       */     
/*  6007 */     checkCursor();
/*       */     
/*  6009 */     setLastValueNull(false);
/*  6010 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  6013 */     if (object == null) {
/*  6014 */       setLastValueNull(true);
/*  6015 */       return null;
/*       */     } 
/*       */     
/*  6018 */     object = convertTemporal(object, this.RowSetMD
/*  6019 */         .getColumnType(paramInt), 91);
/*       */ 
/*       */ 
/*       */     
/*  6023 */     Calendar calendar = Calendar.getInstance();
/*       */     
/*  6025 */     calendar.setTime((Date)object);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6032 */     paramCalendar.set(1, calendar.get(1));
/*  6033 */     paramCalendar.set(2, calendar.get(2));
/*  6034 */     paramCalendar.set(5, calendar.get(5));
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6040 */     return new Date(paramCalendar.getTime().getTime());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
/*  6062 */     return getDate(getColIdxByName(paramString), paramCalendar);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
/*  6088 */     checkIndex(paramInt);
/*       */     
/*  6090 */     checkCursor();
/*       */     
/*  6092 */     setLastValueNull(false);
/*  6093 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  6096 */     if (object == null) {
/*  6097 */       setLastValueNull(true);
/*  6098 */       return null;
/*       */     } 
/*       */     
/*  6101 */     object = convertTemporal(object, this.RowSetMD
/*  6102 */         .getColumnType(paramInt), 92);
/*       */ 
/*       */ 
/*       */     
/*  6106 */     Calendar calendar = Calendar.getInstance();
/*       */     
/*  6108 */     calendar.setTime((Date)object);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6115 */     paramCalendar.set(11, calendar.get(11));
/*  6116 */     paramCalendar.set(12, calendar.get(12));
/*  6117 */     paramCalendar.set(13, calendar.get(13));
/*       */     
/*  6119 */     return new Time(paramCalendar.getTime().getTime());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
/*  6141 */     return getTime(getColIdxByName(paramString), paramCalendar);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
/*  6167 */     checkIndex(paramInt);
/*       */     
/*  6169 */     checkCursor();
/*       */     
/*  6171 */     setLastValueNull(false);
/*  6172 */     Object object = getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  6175 */     if (object == null) {
/*  6176 */       setLastValueNull(true);
/*  6177 */       return null;
/*       */     } 
/*       */     
/*  6180 */     object = convertTemporal(object, this.RowSetMD
/*  6181 */         .getColumnType(paramInt), 93);
/*       */ 
/*       */ 
/*       */     
/*  6185 */     Calendar calendar = Calendar.getInstance();
/*       */     
/*  6187 */     calendar.setTime((Date)object);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6194 */     paramCalendar.set(1, calendar.get(1));
/*  6195 */     paramCalendar.set(2, calendar.get(2));
/*  6196 */     paramCalendar.set(5, calendar.get(5));
/*  6197 */     paramCalendar.set(11, calendar.get(11));
/*  6198 */     paramCalendar.set(12, calendar.get(12));
/*  6199 */     paramCalendar.set(13, calendar.get(13));
/*       */     
/*  6201 */     return new Timestamp(paramCalendar.getTime().getTime());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
/*  6224 */     return getTimestamp(getColIdxByName(paramString), paramCalendar);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Connection getConnection() throws SQLException {
/*  6242 */     return this.conn;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMetaData(RowSetMetaData paramRowSetMetaData) throws SQLException {
/*  6255 */     this.RowSetMD = (RowSetMetaDataImpl)paramRowSetMetaData;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ResultSet getOriginal() throws SQLException {
/*  6273 */     CachedRowSetImpl cachedRowSetImpl = new CachedRowSetImpl();
/*  6274 */     cachedRowSetImpl.RowSetMD = this.RowSetMD;
/*  6275 */     cachedRowSetImpl.numRows = this.numRows;
/*  6276 */     cachedRowSetImpl.cursorPos = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6282 */     int i = this.RowSetMD.getColumnCount();
/*       */ 
/*       */     
/*  6285 */     for (Iterator<Row> iterator = this.rvh.iterator(); iterator.hasNext(); ) {
/*  6286 */       Row row = new Row(i, ((Row)iterator.next()).getOrigRow());
/*  6287 */       cachedRowSetImpl.rvh.add(row);
/*       */     } 
/*  6289 */     return cachedRowSetImpl;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ResultSet getOriginalRow() throws SQLException {
/*  6304 */     CachedRowSetImpl cachedRowSetImpl = new CachedRowSetImpl();
/*  6305 */     cachedRowSetImpl.RowSetMD = this.RowSetMD;
/*  6306 */     cachedRowSetImpl.numRows = 1;
/*  6307 */     cachedRowSetImpl.cursorPos = 0;
/*  6308 */     cachedRowSetImpl.setTypeMap(getTypeMap());
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6316 */     Row row = new Row(this.RowSetMD.getColumnCount(), getCurrentRow().getOrigRow());
/*       */     
/*  6318 */     cachedRowSetImpl.rvh.add(row);
/*       */     
/*  6320 */     return cachedRowSetImpl;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setOriginalRow() throws SQLException {
/*  6331 */     if (this.onInsertRow == true) {
/*  6332 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*       */     
/*  6335 */     Row row = (Row)getCurrentRow();
/*  6336 */     makeRowOriginal(row);
/*       */ 
/*       */     
/*  6339 */     if (row.getDeleted() == true) {
/*  6340 */       removeCurrentRow();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void makeRowOriginal(Row paramRow) {
/*  6354 */     if (paramRow.getInserted() == true) {
/*  6355 */       paramRow.clearInserted();
/*       */     }
/*       */     
/*  6358 */     if (paramRow.getUpdated() == true) {
/*  6359 */       paramRow.moveCurrentToOrig();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setOriginal() throws SQLException {
/*  6371 */     for (Iterator<Row> iterator = this.rvh.iterator(); iterator.hasNext(); ) {
/*  6372 */       Row row = iterator.next();
/*  6373 */       makeRowOriginal(row);
/*       */       
/*  6375 */       if (row.getDeleted() == true) {
/*  6376 */         iterator.remove();
/*  6377 */         this.numRows--;
/*       */       } 
/*       */     } 
/*  6380 */     this.numDeleted = 0;
/*       */ 
/*       */     
/*  6383 */     notifyRowSetChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getTableName() throws SQLException {
/*  6395 */     return this.tableName;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTableName(String paramString) throws SQLException {
/*  6408 */     if (paramString == null) {
/*  6409 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.tablename").toString());
/*       */     }
/*  6411 */     this.tableName = paramString;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int[] getKeyColumns() throws SQLException {
/*  6426 */     int[] arrayOfInt = this.keyCols;
/*  6427 */     return (arrayOfInt == null) ? null : Arrays.copyOf(arrayOfInt, arrayOfInt.length);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setKeyColumns(int[] paramArrayOfint) throws SQLException {
/*  6448 */     int i = 0;
/*  6449 */     if (this.RowSetMD != null) {
/*  6450 */       i = this.RowSetMD.getColumnCount();
/*  6451 */       if (paramArrayOfint.length > i)
/*  6452 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.keycols").toString()); 
/*       */     } 
/*  6454 */     this.keyCols = new int[paramArrayOfint.length];
/*  6455 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*  6456 */       if (this.RowSetMD != null && (paramArrayOfint[b] <= 0 || paramArrayOfint[b] > i))
/*       */       {
/*  6458 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidcol").toString() + paramArrayOfint[b]);
/*       */       }
/*       */       
/*  6461 */       this.keyCols[b] = paramArrayOfint[b];
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateRef(int paramInt, Ref paramRef) throws SQLException {
/*  6490 */     checkIndex(paramInt);
/*       */     
/*  6492 */     checkCursor();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6497 */     getCurrentRow().setColumnObject(paramInt, new SerialRef(paramRef));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateRef(String paramString, Ref paramRef) throws SQLException {
/*  6523 */     updateRef(getColIdxByName(paramString), paramRef);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(int paramInt, Clob paramClob) throws SQLException {
/*  6551 */     checkIndex(paramInt);
/*       */     
/*  6553 */     checkCursor();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6559 */     if (this.dbmslocatorsUpdateCopy) {
/*  6560 */       getCurrentRow().setColumnObject(paramInt, new SerialClob(paramClob));
/*       */     } else {
/*       */       
/*  6563 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotsupp").toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(String paramString, Clob paramClob) throws SQLException {
/*  6590 */     updateClob(getColIdxByName(paramString), paramClob);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
/*  6618 */     checkIndex(paramInt);
/*       */     
/*  6620 */     checkCursor();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6626 */     if (this.dbmslocatorsUpdateCopy) {
/*  6627 */       getCurrentRow().setColumnObject(paramInt, new SerialBlob(paramBlob));
/*       */     } else {
/*       */       
/*  6630 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotsupp").toString());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
/*  6657 */     updateBlob(getColIdxByName(paramString), paramBlob);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateArray(int paramInt, Array paramArray) throws SQLException {
/*  6685 */     checkIndex(paramInt);
/*       */     
/*  6687 */     checkCursor();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6692 */     getCurrentRow().setColumnObject(paramInt, new SerialArray(paramArray));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateArray(String paramString, Array paramArray) throws SQLException {
/*  6718 */     updateArray(getColIdxByName(paramString), paramArray);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public URL getURL(int paramInt) throws SQLException {
/*  6741 */     checkIndex(paramInt);
/*       */     
/*  6743 */     checkCursor();
/*       */     
/*  6745 */     if (this.RowSetMD.getColumnType(paramInt) != 70) {
/*  6746 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.dtypemismt").toString());
/*       */     }
/*       */     
/*  6749 */     setLastValueNull(false);
/*  6750 */     URL uRL = (URL)getCurrentRow().getColumnObject(paramInt);
/*       */ 
/*       */     
/*  6753 */     if (uRL == null) {
/*  6754 */       setLastValueNull(true);
/*  6755 */       return null;
/*       */     } 
/*       */     
/*  6758 */     return uRL;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public URL getURL(String paramString) throws SQLException {
/*  6776 */     return getURL(getColIdxByName(paramString));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public RowSetWarning getRowSetWarnings() {
/*       */     try {
/*  6801 */       notifyCursorMoved();
/*  6802 */     } catch (SQLException sQLException) {}
/*  6803 */     return this.rowsetWarning;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String buildTableName(String paramString) throws SQLException {
/*  6823 */     String str = "";
/*  6824 */     paramString = paramString.trim();
/*       */ 
/*       */ 
/*       */     
/*  6828 */     if (paramString.toLowerCase().startsWith("select")) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  6833 */       int i = paramString.toLowerCase().indexOf("from");
/*  6834 */       int j = paramString.indexOf(",", i);
/*       */       
/*  6836 */       if (j == -1)
/*       */       {
/*  6838 */         str = paramString.substring(i + "from".length(), paramString.length()).trim();
/*       */         
/*  6840 */         String str1 = str;
/*       */         
/*  6842 */         int k = str1.toLowerCase().indexOf("where");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6849 */         if (k != -1)
/*       */         {
/*  6851 */           str1 = str1.substring(0, k).trim();
/*       */         }
/*       */         
/*  6854 */         str = str1;
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*  6860 */     else if (!paramString.toLowerCase().startsWith("insert")) {
/*       */       
/*  6862 */       if (paramString.toLowerCase().startsWith("update"));
/*       */     } 
/*       */     
/*  6865 */     return str;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void commit() throws SQLException {
/*  6875 */     this.conn.commit();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void rollback() throws SQLException {
/*  6885 */     this.conn.rollback();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void rollback(Savepoint paramSavepoint) throws SQLException {
/*  6895 */     this.conn.rollback(paramSavepoint);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void unsetMatchColumn(int[] paramArrayOfint) throws SQLException {
/*       */     byte b;
/*  6915 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  6916 */       int i = Integer.parseInt(((Integer)this.iMatchColumns.get(b)).toString());
/*  6917 */       if (paramArrayOfint[b] != i) {
/*  6918 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols").toString());
/*       */       }
/*       */     } 
/*       */     
/*  6922 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  6923 */       this.iMatchColumns.set(b, Integer.valueOf(-1));
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void unsetMatchColumn(String[] paramArrayOfString) throws SQLException {
/*       */     byte b;
/*  6943 */     for (b = 0; b < paramArrayOfString.length; b++) {
/*  6944 */       if (!paramArrayOfString[b].equals(this.strMatchColumns.get(b))) {
/*  6945 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols").toString());
/*       */       }
/*       */     } 
/*       */     
/*  6949 */     for (b = 0; b < paramArrayOfString.length; b++) {
/*  6950 */       this.strMatchColumns.set(b, null);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String[] getMatchColumnNames() throws SQLException {
/*  6966 */     String[] arrayOfString = new String[this.strMatchColumns.size()];
/*       */     
/*  6968 */     if (this.strMatchColumns.get(0) == null) {
/*  6969 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.setmatchcols").toString());
/*       */     }
/*       */     
/*  6972 */     this.strMatchColumns.copyInto((Object[])arrayOfString);
/*  6973 */     return arrayOfString;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int[] getMatchColumnIndexes() throws SQLException {
/*  6987 */     Integer[] arrayOfInteger = new Integer[this.iMatchColumns.size()];
/*  6988 */     int[] arrayOfInt = new int[this.iMatchColumns.size()];
/*       */ 
/*       */     
/*  6991 */     int i = ((Integer)this.iMatchColumns.get(0)).intValue();
/*       */     
/*  6993 */     if (i == -1) {
/*  6994 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.setmatchcols").toString());
/*       */     }
/*       */ 
/*       */     
/*  6998 */     this.iMatchColumns.copyInto((Object[])arrayOfInteger);
/*       */     
/*  7000 */     for (byte b = 0; b < arrayOfInteger.length; b++) {
/*  7001 */       arrayOfInt[b] = arrayOfInteger[b].intValue();
/*       */     }
/*       */     
/*  7004 */     return arrayOfInt;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMatchColumn(int[] paramArrayOfint) throws SQLException {
/*       */     byte b;
/*  7026 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  7027 */       if (paramArrayOfint[b] < 0) {
/*  7028 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols1").toString());
/*       */       }
/*       */     } 
/*  7031 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  7032 */       this.iMatchColumns.add(b, Integer.valueOf(paramArrayOfint[b]));
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMatchColumn(String[] paramArrayOfString) throws SQLException {
/*       */     byte b;
/*  7053 */     for (b = 0; b < paramArrayOfString.length; b++) {
/*  7054 */       if (paramArrayOfString[b] == null || paramArrayOfString[b].equals("")) {
/*  7055 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols2").toString());
/*       */       }
/*       */     } 
/*  7058 */     for (b = 0; b < paramArrayOfString.length; b++) {
/*  7059 */       this.strMatchColumns.add(b, paramArrayOfString[b]);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMatchColumn(int paramInt) throws SQLException {
/*  7083 */     if (paramInt < 0) {
/*  7084 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols1").toString());
/*       */     }
/*       */     
/*  7087 */     this.iMatchColumns.set(0, Integer.valueOf(paramInt));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMatchColumn(String paramString) throws SQLException {
/*  7109 */     if (paramString == null || (paramString = paramString.trim()).equals("")) {
/*  7110 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.matchcols2").toString());
/*       */     }
/*       */     
/*  7113 */     this.strMatchColumns.set(0, paramString);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void unsetMatchColumn(int paramInt) throws SQLException {
/*  7134 */     if (!((Integer)this.iMatchColumns.get(0)).equals(Integer.valueOf(paramInt)))
/*  7135 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.unsetmatch").toString()); 
/*  7136 */     if (this.strMatchColumns.get(0) != null) {
/*  7137 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.unsetmatch1").toString());
/*       */     }
/*       */     
/*  7140 */     this.iMatchColumns.set(0, Integer.valueOf(-1));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void unsetMatchColumn(String paramString) throws SQLException {
/*  7160 */     paramString = paramString.trim();
/*       */     
/*  7162 */     if (!((String)this.strMatchColumns.get(0)).equals(paramString))
/*  7163 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.unsetmatch").toString()); 
/*  7164 */     if (((Integer)this.iMatchColumns.get(0)).intValue() > 0) {
/*  7165 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.unsetmatch2").toString());
/*       */     }
/*  7167 */     this.strMatchColumns.set(0, null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void rowSetPopulated(RowSetEvent paramRowSetEvent, int paramInt) throws SQLException {
/*  7186 */     if (paramInt < 0 || paramInt < getFetchSize()) {
/*  7187 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.numrows").toString());
/*       */     }
/*       */     
/*  7190 */     if (size() % paramInt == 0) {
/*  7191 */       RowSetEvent rowSetEvent = new RowSetEvent(this);
/*  7192 */       paramRowSetEvent = rowSetEvent;
/*  7193 */       notifyRowSetChanged();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void populate(ResultSet paramResultSet, int paramInt) throws SQLException {
/*  7235 */     Map<String, Class<?>> map = getTypeMap();
/*       */ 
/*       */ 
/*       */     
/*  7239 */     this.cursorPos = 0;
/*  7240 */     if (this.populatecallcount == 0) {
/*  7241 */       if (paramInt < 0) {
/*  7242 */         throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.startpos").toString());
/*       */       }
/*  7244 */       if (getMaxRows() == 0) {
/*  7245 */         paramResultSet.absolute(paramInt);
/*  7246 */         while (paramResultSet.next()) {
/*  7247 */           this.totalRows++;
/*       */         }
/*  7249 */         this.totalRows++;
/*       */       } 
/*  7251 */       this.startPos = paramInt;
/*       */     } 
/*  7253 */     this.populatecallcount++;
/*  7254 */     this.resultSet = paramResultSet;
/*  7255 */     if (this.endPos - this.startPos >= getMaxRows() && getMaxRows() > 0) {
/*  7256 */       this.endPos = this.prevEndPos;
/*  7257 */       this.pagenotend = false;
/*       */       
/*       */       return;
/*       */     } 
/*  7261 */     if ((this.maxRowsreached != getMaxRows() || this.maxRowsreached != this.totalRows) && this.pagenotend) {
/*  7262 */       this.startPrev = paramInt - getPageSize();
/*       */     }
/*       */     
/*  7265 */     if (this.pageSize == 0) {
/*  7266 */       this.prevEndPos = this.endPos;
/*  7267 */       this.endPos = paramInt + getMaxRows();
/*       */     } else {
/*       */       
/*  7270 */       this.prevEndPos = this.endPos;
/*  7271 */       this.endPos = paramInt + getPageSize();
/*       */     } 
/*       */ 
/*       */     
/*  7275 */     if (paramInt == 1) {
/*  7276 */       this.resultSet.beforeFirst();
/*       */     } else {
/*       */       
/*  7279 */       this.resultSet.absolute(paramInt - 1);
/*       */     } 
/*  7281 */     if (this.pageSize == 0) {
/*  7282 */       this.rvh = new Vector(getMaxRows());
/*       */     }
/*       */     else {
/*       */       
/*  7286 */       this.rvh = new Vector(getPageSize());
/*       */     } 
/*       */     
/*  7289 */     if (paramResultSet == null) {
/*  7290 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.populate").toString());
/*       */     }
/*       */ 
/*       */     
/*  7294 */     this.RSMD = paramResultSet.getMetaData();
/*       */ 
/*       */     
/*  7297 */     this.RowSetMD = new RowSetMetaDataImpl();
/*  7298 */     initMetaData(this.RowSetMD, this.RSMD);
/*       */ 
/*       */     
/*  7301 */     this.RSMD = null;
/*  7302 */     int i = this.RowSetMD.getColumnCount();
/*  7303 */     int j = getMaxRows();
/*  7304 */     byte b = 0;
/*  7305 */     Row row = null;
/*       */     
/*  7307 */     if (!paramResultSet.next() && j == 0) {
/*  7308 */       this.endPos = this.prevEndPos;
/*  7309 */       this.pagenotend = false;
/*       */       
/*       */       return;
/*       */     } 
/*  7313 */     paramResultSet.previous();
/*       */     
/*  7315 */     while (paramResultSet.next()) {
/*       */       
/*  7317 */       row = new Row(i);
/*  7318 */       if (this.pageSize == 0) {
/*  7319 */         if (b >= j && j > 0) {
/*  7320 */           this.rowsetWarning.setNextException(new SQLException("Populating rows setting has exceeded max row setting"));
/*       */ 
/*       */ 
/*       */           
/*       */           break;
/*       */         } 
/*  7326 */       } else if (b >= this.pageSize || (this.maxRowsreached >= j && j > 0)) {
/*  7327 */         this.rowsetWarning.setNextException(new SQLException("Populating rows setting has exceeded max row setting"));
/*       */ 
/*       */         
/*       */         break;
/*       */       } 
/*       */       
/*  7333 */       for (byte b1 = 1; b1 <= i; b1++) {
/*       */         Object object;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7340 */         if (map == null) {
/*  7341 */           object = paramResultSet.getObject(b1);
/*       */         } else {
/*  7343 */           object = paramResultSet.getObject(b1, map);
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7350 */         if (object instanceof Struct) {
/*  7351 */           object = new SerialStruct((Struct)object, map);
/*  7352 */         } else if (object instanceof SQLData) {
/*  7353 */           object = new SerialStruct((SQLData)object, map);
/*  7354 */         } else if (object instanceof Blob) {
/*  7355 */           object = new SerialBlob((Blob)object);
/*  7356 */         } else if (object instanceof Clob) {
/*  7357 */           object = new SerialClob((Clob)object);
/*  7358 */         } else if (object instanceof Array) {
/*  7359 */           object = new SerialArray((Array)object, map);
/*       */         } 
/*       */         
/*  7362 */         row.initColumnObject(b1, object);
/*       */       } 
/*  7364 */       b++;
/*  7365 */       this.maxRowsreached++;
/*  7366 */       this.rvh.add(row);
/*       */     } 
/*  7368 */     this.numRows = b;
/*       */ 
/*       */     
/*  7371 */     notifyRowSetChanged();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean nextPage() throws SQLException {
/*  7384 */     if (this.populatecallcount == 0) {
/*  7385 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.nextpage").toString());
/*       */     }
/*       */     
/*  7388 */     this.onFirstPage = false;
/*  7389 */     if (this.callWithCon) {
/*  7390 */       this.crsReader.setStartPosition(this.endPos);
/*  7391 */       this.crsReader.readData(this);
/*  7392 */       this.resultSet = null;
/*       */     } else {
/*       */       
/*  7395 */       populate(this.resultSet, this.endPos);
/*       */     } 
/*  7397 */     return this.pagenotend;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPageSize(int paramInt) throws SQLException {
/*  7408 */     if (paramInt < 0) {
/*  7409 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.pagesize").toString());
/*       */     }
/*  7411 */     if (paramInt > getMaxRows() && getMaxRows() != 0) {
/*  7412 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.pagesize1").toString());
/*       */     }
/*  7414 */     this.pageSize = paramInt;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getPageSize() {
/*  7423 */     return this.pageSize;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean previousPage() throws SQLException {
/*  7441 */     int i = getPageSize();
/*  7442 */     int j = this.maxRowsreached;
/*       */     
/*  7444 */     if (this.populatecallcount == 0) {
/*  7445 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.nextpage").toString());
/*       */     }
/*       */     
/*  7448 */     if (!this.callWithCon && 
/*  7449 */       this.resultSet.getType() == 1003) {
/*  7450 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.fwdonly").toString());
/*       */     }
/*       */ 
/*       */     
/*  7454 */     this.pagenotend = true;
/*       */     
/*  7456 */     if (this.startPrev < this.startPos) {
/*  7457 */       this.onFirstPage = true;
/*  7458 */       return false;
/*       */     } 
/*       */     
/*  7461 */     if (this.onFirstPage) {
/*  7462 */       return false;
/*       */     }
/*       */     
/*  7465 */     int k = j % i;
/*       */     
/*  7467 */     if (k == 0) {
/*  7468 */       this.maxRowsreached -= 2 * i;
/*  7469 */       if (this.callWithCon) {
/*  7470 */         this.crsReader.setStartPosition(this.startPrev);
/*  7471 */         this.crsReader.readData(this);
/*  7472 */         this.resultSet = null;
/*       */       } else {
/*       */         
/*  7475 */         populate(this.resultSet, this.startPrev);
/*       */       } 
/*  7477 */       return true;
/*       */     } 
/*       */ 
/*       */     
/*  7481 */     this.maxRowsreached -= i + k;
/*  7482 */     if (this.callWithCon) {
/*  7483 */       this.crsReader.setStartPosition(this.startPrev);
/*  7484 */       this.crsReader.readData(this);
/*  7485 */       this.resultSet = null;
/*       */     } else {
/*       */       
/*  7488 */       populate(this.resultSet, this.startPrev);
/*       */     } 
/*  7490 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setRowInserted(boolean paramBoolean) throws SQLException {
/*  7682 */     checkCursor();
/*       */     
/*  7684 */     if (this.onInsertRow == true) {
/*  7685 */       throw new SQLException(this.resBundle.handleGetObject("cachedrowsetimpl.invalidop").toString());
/*       */     }
/*  7687 */     if (paramBoolean) {
/*  7688 */       ((Row)getCurrentRow()).setInserted();
/*       */     } else {
/*  7690 */       ((Row)getCurrentRow()).clearInserted();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public SQLXML getSQLXML(int paramInt) throws SQLException {
/*  7703 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public SQLXML getSQLXML(String paramString) throws SQLException {
/*  7714 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public RowId getRowId(int paramInt) throws SQLException {
/*  7729 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public RowId getRowId(String paramString) throws SQLException {
/*  7744 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
/*  7760 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
/*  7776 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getHoldability() throws SQLException {
/*  7786 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isClosed() throws SQLException {
/*  7797 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNString(int paramInt, String paramString) throws SQLException {
/*  7809 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNString(String paramString1, String paramString2) throws SQLException {
/*  7821 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
/*  7834 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
/*  7846 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public NClob getNClob(int paramInt) throws SQLException {
/*  7861 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public NClob getNClob(String paramString) throws SQLException {
/*  7877 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */   
/*       */   public <T> T unwrap(Class<T> paramClass) throws SQLException {
/*  7881 */     return null;
/*       */   }
/*       */   
/*       */   public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
/*  7885 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
/*  7898 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
/*  7910 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
/*  7926 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setRowId(String paramString, RowId paramRowId) throws SQLException {
/*  7941 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/*  7968 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(String paramString, NClob paramNClob) throws SQLException {
/*  7984 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Reader getNCharacterStream(int paramInt) throws SQLException {
/*  8004 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Reader getNCharacterStream(String paramString) throws SQLException {
/*  8024 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
/*  8041 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
/*  8058 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getNString(int paramInt) throws SQLException {
/*  8076 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getNString(String paramString) throws SQLException {
/*  8094 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  8116 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  8138 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.opnotysupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/*  8168 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
/*  8200 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/*  8235 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/*  8268 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
/*  8303 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
/*  8338 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  8370 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  8402 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(int paramInt, Reader paramReader) throws SQLException {
/*  8436 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateClob(String paramString, Reader paramReader) throws SQLException {
/*  8471 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  8505 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  8539 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
/*  8575 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateNClob(String paramString, Reader paramReader) throws SQLException {
/*  8612 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  8683 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  8709 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
/*  8779 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
/*  8806 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/*  8831 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
/*  8858 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
/*  8883 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setURL(int paramInt, URL paramURL) throws SQLException {
/*  8925 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(int paramInt, Reader paramReader) throws SQLException {
/*  8953 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  8981 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(String paramString, Reader paramReader) throws SQLException {
/*  9008 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  9036 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
/*  9052 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNString(int paramInt, String paramString) throws SQLException {
/*  9072 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNString(String paramString1, String paramString2) throws SQLException {
/*  9089 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  9107 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  9126 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
/*  9152 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
/*  9178 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/*  9204 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setClob(String paramString, Clob paramClob) throws SQLException {
/*  9222 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setClob(String paramString, Reader paramReader) throws SQLException {
/*  9248 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setDate(String paramString, Date paramDate) throws SQLException {
/*  9270 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
/*  9297 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTime(String paramString, Time paramTime) throws SQLException {
/*  9317 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
/*  9344 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setClob(int paramInt, Reader paramReader) throws SQLException {
/*  9370 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/*  9394 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/*  9424 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
/*  9454 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/*  9486 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBlob(String paramString, Blob paramBlob) throws SQLException {
/*  9504 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
/*  9531 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
/*  9577 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/*  9605 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setObject(String paramString, Object paramObject) throws SQLException {
/*  9646 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/*  9673 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/*  9700 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/*  9730 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
/*  9758 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
/*  9785 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
/*  9816 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/*  9835 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setString(String paramString1, String paramString2) throws SQLException {
/*  9858 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/*  9880 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/*  9902 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNull(String paramString, int paramInt) throws SQLException {
/*  9919 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
/*  9957 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
/*  9977 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setByte(String paramString, byte paramByte) throws SQLException {
/*  9997 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setShort(String paramString, short paramShort) throws SQLException {
/* 10017 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setInt(String paramString, int paramInt) throws SQLException {
/* 10036 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setLong(String paramString, long paramLong) throws SQLException {
/* 10055 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setFloat(String paramString, float paramFloat) throws SQLException {
/* 10074 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setDouble(String paramString, double paramDouble) throws SQLException {
/* 10093 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("cachedrowsetimpl.featnotsupp").toString());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 10103 */     paramObjectInputStream.defaultReadObject();
/*       */     
/*       */     try {
/* 10106 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 10107 */     } catch (IOException iOException) {
/* 10108 */       throw new RuntimeException(iOException);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public <T> T getObject(int paramInt, Class<T> paramClass) throws SQLException {
/* 10115 */     throw new SQLFeatureNotSupportedException("Not supported yet.");
/*       */   }
/*       */   
/*       */   public <T> T getObject(String paramString, Class<T> paramClass) throws SQLException {
/* 10119 */     throw new SQLFeatureNotSupportedException("Not supported yet.");
/*       */   }
/*       */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/CachedRowSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */