/*      */ package com.sun.rowset;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.Date;
/*      */ import java.sql.DriverManager;
/*      */ import java.sql.NClob;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.RowId;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLFeatureNotSupportedException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.SQLXML;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.naming.NamingException;
/*      */ import javax.sql.DataSource;
/*      */ import javax.sql.RowSetMetaData;
/*      */ import javax.sql.rowset.BaseRowSet;
/*      */ import javax.sql.rowset.JdbcRowSet;
/*      */ import javax.sql.rowset.Joinable;
/*      */ import javax.sql.rowset.RowSetMetaDataImpl;
/*      */ import javax.sql.rowset.RowSetWarning;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JdbcRowSetImpl
/*      */   extends BaseRowSet
/*      */   implements JdbcRowSet, Joinable
/*      */ {
/*      */   private Connection conn;
/*      */   private PreparedStatement ps;
/*      */   private ResultSet rs;
/*      */   private RowSetMetaDataImpl rowsMD;
/*      */   private ResultSetMetaData resMD;
/*      */   private Vector<Integer> iMatchColumns;
/*      */   private Vector<String> strMatchColumns;
/*      */   protected transient JdbcRowSetResourceBundle resBundle;
/*      */   static final long serialVersionUID = -3591946023893483003L;
/*      */   
/*      */   public JdbcRowSetImpl() {
/*  131 */     this.conn = null;
/*  132 */     this.ps = null;
/*  133 */     this.rs = null;
/*      */     
/*      */     try {
/*  136 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  137 */     } catch (IOException iOException) {
/*  138 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */ 
/*      */     
/*  142 */     initParams();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  147 */       setShowDeleted(false);
/*  148 */     } catch (SQLException sQLException) {
/*  149 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setshowdeleted").toString() + sQLException
/*  150 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  154 */       setQueryTimeout(0);
/*  155 */     } catch (SQLException sQLException) {
/*  156 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setquerytimeout").toString() + sQLException
/*  157 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  161 */       setMaxRows(0);
/*  162 */     } catch (SQLException sQLException) {
/*  163 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setmaxrows").toString() + sQLException
/*  164 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  168 */       setMaxFieldSize(0);
/*  169 */     } catch (SQLException sQLException) {
/*  170 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setmaxfieldsize").toString() + sQLException
/*  171 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  175 */       setEscapeProcessing(true);
/*  176 */     } catch (SQLException sQLException) {
/*  177 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setescapeprocessing").toString() + sQLException
/*  178 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  182 */       setConcurrency(1008);
/*  183 */     } catch (SQLException sQLException) {
/*  184 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setconcurrency").toString() + sQLException
/*  185 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*  188 */     setTypeMap(null);
/*      */     
/*      */     try {
/*  191 */       setType(1004);
/*  192 */     } catch (SQLException sQLException) {
/*  193 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.settype").toString() + sQLException
/*  194 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*  197 */     setReadOnly(true);
/*      */     
/*      */     try {
/*  200 */       setTransactionIsolation(2);
/*  201 */     } catch (SQLException sQLException) {
/*  202 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.settransactionisolation").toString() + sQLException
/*  203 */           .getLocalizedMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  208 */     this.iMatchColumns = new Vector<>(10); byte b;
/*  209 */     for (b = 0; b < 10; b++) {
/*  210 */       this.iMatchColumns.add(b, Integer.valueOf(-1));
/*      */     }
/*      */     
/*  213 */     this.strMatchColumns = new Vector<>(10);
/*  214 */     for (b = 0; b < 10; b++) {
/*  215 */       this.strMatchColumns.add(b, null);
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
/*      */   public JdbcRowSetImpl(Connection paramConnection) throws SQLException {
/*  253 */     this.conn = paramConnection;
/*  254 */     this.ps = null;
/*  255 */     this.rs = null;
/*      */     
/*      */     try {
/*  258 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  259 */     } catch (IOException iOException) {
/*  260 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */ 
/*      */     
/*  264 */     initParams();
/*      */     
/*  266 */     setShowDeleted(false);
/*  267 */     setQueryTimeout(0);
/*  268 */     setMaxRows(0);
/*  269 */     setMaxFieldSize(0);
/*      */     
/*  271 */     setParams();
/*      */     
/*  273 */     setReadOnly(true);
/*  274 */     setTransactionIsolation(2);
/*  275 */     setEscapeProcessing(true);
/*  276 */     setTypeMap(null);
/*      */ 
/*      */ 
/*      */     
/*  280 */     this.iMatchColumns = new Vector<>(10); byte b;
/*  281 */     for (b = 0; b < 10; b++) {
/*  282 */       this.iMatchColumns.add(b, Integer.valueOf(-1));
/*      */     }
/*      */     
/*  285 */     this.strMatchColumns = new Vector<>(10);
/*  286 */     for (b = 0; b < 10; b++) {
/*  287 */       this.strMatchColumns.add(b, null);
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
/*      */   public JdbcRowSetImpl(String paramString1, String paramString2, String paramString3) throws SQLException {
/*  327 */     this.conn = null;
/*  328 */     this.ps = null;
/*  329 */     this.rs = null;
/*      */     
/*      */     try {
/*  332 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  333 */     } catch (IOException iOException) {
/*  334 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */ 
/*      */     
/*  338 */     initParams();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  343 */     setUsername(paramString2);
/*  344 */     setPassword(paramString3);
/*  345 */     setUrl(paramString1);
/*      */ 
/*      */     
/*  348 */     setShowDeleted(false);
/*  349 */     setQueryTimeout(0);
/*  350 */     setMaxRows(0);
/*  351 */     setMaxFieldSize(0);
/*      */     
/*  353 */     setParams();
/*      */     
/*  355 */     setReadOnly(true);
/*  356 */     setTransactionIsolation(2);
/*  357 */     setEscapeProcessing(true);
/*  358 */     setTypeMap(null);
/*      */ 
/*      */ 
/*      */     
/*  362 */     this.iMatchColumns = new Vector<>(10); byte b;
/*  363 */     for (b = 0; b < 10; b++) {
/*  364 */       this.iMatchColumns.add(b, Integer.valueOf(-1));
/*      */     }
/*      */     
/*  367 */     this.strMatchColumns = new Vector<>(10);
/*  368 */     for (b = 0; b < 10; b++) {
/*  369 */       this.strMatchColumns.add(b, null);
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
/*      */   public JdbcRowSetImpl(ResultSet paramResultSet) throws SQLException {
/*  412 */     this.conn = null;
/*      */     
/*  414 */     this.ps = null;
/*      */     
/*  416 */     this.rs = paramResultSet;
/*      */     
/*      */     try {
/*  419 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  420 */     } catch (IOException iOException) {
/*  421 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */ 
/*      */     
/*  425 */     initParams();
/*      */ 
/*      */     
/*  428 */     setShowDeleted(false);
/*  429 */     setQueryTimeout(0);
/*  430 */     setMaxRows(0);
/*  431 */     setMaxFieldSize(0);
/*      */     
/*  433 */     setParams();
/*      */     
/*  435 */     setReadOnly(true);
/*  436 */     setTransactionIsolation(2);
/*  437 */     setEscapeProcessing(true);
/*  438 */     setTypeMap(null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     this.resMD = this.rs.getMetaData();
/*      */     
/*  445 */     this.rowsMD = new RowSetMetaDataImpl();
/*      */     
/*  447 */     initMetaData(this.rowsMD, this.resMD);
/*      */ 
/*      */ 
/*      */     
/*  451 */     this.iMatchColumns = new Vector<>(10); byte b;
/*  452 */     for (b = 0; b < 10; b++) {
/*  453 */       this.iMatchColumns.add(b, Integer.valueOf(-1));
/*      */     }
/*      */     
/*  456 */     this.strMatchColumns = new Vector<>(10);
/*  457 */     for (b = 0; b < 10; b++) {
/*  458 */       this.strMatchColumns.add(b, null);
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
/*      */   protected void initMetaData(RowSetMetaData paramRowSetMetaData, ResultSetMetaData paramResultSetMetaData) throws SQLException {
/*  474 */     int i = paramResultSetMetaData.getColumnCount();
/*      */     
/*  476 */     paramRowSetMetaData.setColumnCount(i);
/*  477 */     for (byte b = 1; b <= i; b++) {
/*  478 */       paramRowSetMetaData.setAutoIncrement(b, paramResultSetMetaData.isAutoIncrement(b));
/*  479 */       paramRowSetMetaData.setCaseSensitive(b, paramResultSetMetaData.isCaseSensitive(b));
/*  480 */       paramRowSetMetaData.setCurrency(b, paramResultSetMetaData.isCurrency(b));
/*  481 */       paramRowSetMetaData.setNullable(b, paramResultSetMetaData.isNullable(b));
/*  482 */       paramRowSetMetaData.setSigned(b, paramResultSetMetaData.isSigned(b));
/*  483 */       paramRowSetMetaData.setSearchable(b, paramResultSetMetaData.isSearchable(b));
/*  484 */       paramRowSetMetaData.setColumnDisplaySize(b, paramResultSetMetaData.getColumnDisplaySize(b));
/*  485 */       paramRowSetMetaData.setColumnLabel(b, paramResultSetMetaData.getColumnLabel(b));
/*  486 */       paramRowSetMetaData.setColumnName(b, paramResultSetMetaData.getColumnName(b));
/*  487 */       paramRowSetMetaData.setSchemaName(b, paramResultSetMetaData.getSchemaName(b));
/*  488 */       paramRowSetMetaData.setPrecision(b, paramResultSetMetaData.getPrecision(b));
/*  489 */       paramRowSetMetaData.setScale(b, paramResultSetMetaData.getScale(b));
/*  490 */       paramRowSetMetaData.setTableName(b, paramResultSetMetaData.getTableName(b));
/*  491 */       paramRowSetMetaData.setCatalogName(b, paramResultSetMetaData.getCatalogName(b));
/*  492 */       paramRowSetMetaData.setColumnType(b, paramResultSetMetaData.getColumnType(b));
/*  493 */       paramRowSetMetaData.setColumnTypeName(b, paramResultSetMetaData.getColumnTypeName(b));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkState() throws SQLException {
/*  504 */     if (this.conn == null && this.ps == null && this.rs == null) {
/*  505 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.invalstate").toString());
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
/*      */   public void execute() throws SQLException {
/*  556 */     prepare();
/*      */ 
/*      */     
/*  559 */     setProperties(this.ps);
/*      */ 
/*      */ 
/*      */     
/*  563 */     decodeParams(getParams(), this.ps);
/*      */ 
/*      */ 
/*      */     
/*  567 */     this.rs = this.ps.executeQuery();
/*      */ 
/*      */ 
/*      */     
/*  571 */     notifyRowSetChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setProperties(PreparedStatement paramPreparedStatement) throws SQLException {
/*      */     try {
/*  579 */       paramPreparedStatement.setEscapeProcessing(getEscapeProcessing());
/*  580 */     } catch (SQLException sQLException) {
/*  581 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setescapeprocessing").toString() + sQLException
/*  582 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  586 */       paramPreparedStatement.setMaxFieldSize(getMaxFieldSize());
/*  587 */     } catch (SQLException sQLException) {
/*  588 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setmaxfieldsize").toString() + sQLException
/*  589 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  593 */       paramPreparedStatement.setMaxRows(getMaxRows());
/*  594 */     } catch (SQLException sQLException) {
/*  595 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setmaxrows").toString() + sQLException
/*  596 */           .getLocalizedMessage());
/*      */     } 
/*      */     
/*      */     try {
/*  600 */       paramPreparedStatement.setQueryTimeout(getQueryTimeout());
/*  601 */     } catch (SQLException sQLException) {
/*  602 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.setquerytimeout").toString() + sQLException
/*  603 */           .getLocalizedMessage());
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
/*      */   private Connection connect() throws SQLException {
/*  615 */     if (this.conn != null) {
/*  616 */       return this.conn;
/*      */     }
/*  618 */     if (getDataSourceName() != null) {
/*      */       
/*      */       try {
/*      */         
/*  622 */         InitialContext initialContext = new InitialContext();
/*      */         
/*  624 */         DataSource dataSource = (DataSource)initialContext.lookup(getDataSourceName());
/*      */ 
/*      */         
/*  627 */         if (getUsername() != null && !getUsername().equals("")) {
/*  628 */           return dataSource.getConnection(getUsername(), getPassword());
/*      */         }
/*  630 */         return dataSource.getConnection();
/*      */       
/*      */       }
/*  633 */       catch (NamingException namingException) {
/*  634 */         throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.connect").toString());
/*      */       } 
/*      */     }
/*  637 */     if (getUrl() != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  642 */       return 
/*  643 */         DriverManager.getConnection(getUrl(), getUsername(), getPassword());
/*      */     }
/*      */     
/*  646 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PreparedStatement prepare() throws SQLException {
/*  654 */     this.conn = connect();
/*      */ 
/*      */     
/*      */     try {
/*  658 */       Map<String, Class<?>> map = getTypeMap();
/*  659 */       if (map != null) {
/*  660 */         this.conn.setTypeMap(map);
/*      */       }
/*  662 */       this.ps = this.conn.prepareStatement(getCommand(), 1004, 1008);
/*  663 */     } catch (SQLException sQLException) {
/*  664 */       System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.prepare").toString() + sQLException
/*  665 */           .getLocalizedMessage());
/*      */       
/*  667 */       if (this.ps != null)
/*  668 */         this.ps.close(); 
/*  669 */       if (this.conn != null) {
/*  670 */         this.conn.close();
/*      */       }
/*  672 */       throw new SQLException(sQLException.getMessage());
/*      */     } 
/*      */     
/*  675 */     return this.ps;
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
/*      */   private void decodeParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
/*  690 */     Object[] arrayOfObject = null;
/*      */     
/*  692 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/*  693 */       if (paramArrayOfObject[b] instanceof Object[]) {
/*  694 */         arrayOfObject = (Object[])paramArrayOfObject[b];
/*      */         
/*  696 */         if (arrayOfObject.length == 2) {
/*  697 */           if (arrayOfObject[0] == null) {
/*  698 */             paramPreparedStatement.setNull(b + 1, ((Integer)arrayOfObject[1]).intValue());
/*      */ 
/*      */           
/*      */           }
/*  702 */           else if (arrayOfObject[0] instanceof Date || arrayOfObject[0] instanceof Time || arrayOfObject[0] instanceof Timestamp) {
/*      */ 
/*      */             
/*  705 */             System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.detecteddate"));
/*  706 */             if (arrayOfObject[1] instanceof Calendar) {
/*  707 */               System.err.println(this.resBundle.handleGetObject("jdbcrowsetimpl.detectedcalendar"));
/*  708 */               paramPreparedStatement.setDate(b + 1, (Date)arrayOfObject[0], (Calendar)arrayOfObject[1]);
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  713 */               throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.paramtype").toString());
/*      */             }
/*      */           
/*      */           }
/*  717 */           else if (arrayOfObject[0] instanceof Reader) {
/*  718 */             paramPreparedStatement.setCharacterStream(b + 1, (Reader)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*  719 */                 .intValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  726 */           else if (arrayOfObject[1] instanceof Integer) {
/*  727 */             paramPreparedStatement.setObject(b + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*      */           }
/*      */         
/*      */         }
/*  731 */         else if (arrayOfObject.length == 3) {
/*      */           
/*  733 */           if (arrayOfObject[0] == null) {
/*  734 */             paramPreparedStatement.setNull(b + 1, ((Integer)arrayOfObject[1]).intValue(), (String)arrayOfObject[2]);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  739 */             if (arrayOfObject[0] instanceof InputStream) {
/*  740 */               switch (((Integer)arrayOfObject[2]).intValue()) {
/*      */                 case 0:
/*  742 */                   paramPreparedStatement.setUnicodeStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*      */                       
/*  744 */                       .intValue());
/*      */                   break;
/*      */                 case 1:
/*  747 */                   paramPreparedStatement.setBinaryStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*      */                       
/*  749 */                       .intValue());
/*      */                   break;
/*      */                 case 2:
/*  752 */                   paramPreparedStatement.setAsciiStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*      */                       
/*  754 */                       .intValue());
/*      */                   break;
/*      */                 default:
/*  757 */                   throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.paramtype").toString());
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  765 */             if (arrayOfObject[1] instanceof Integer && arrayOfObject[2] instanceof Integer) {
/*  766 */               paramPreparedStatement.setObject(b + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue(), ((Integer)arrayOfObject[2])
/*  767 */                   .intValue());
/*      */             }
/*      */             else {
/*      */               
/*  771 */               throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.paramtype").toString());
/*      */             } 
/*      */           } 
/*      */         } else {
/*  775 */           paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  780 */         paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
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
/*      */   public boolean next() throws SQLException {
/*  806 */     checkState();
/*      */     
/*  808 */     boolean bool = this.rs.next();
/*  809 */     notifyCursorMoved();
/*  810 */     return bool;
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
/*      */   public void close() throws SQLException {
/*  829 */     if (this.rs != null)
/*  830 */       this.rs.close(); 
/*  831 */     if (this.ps != null)
/*  832 */       this.ps.close(); 
/*  833 */     if (this.conn != null) {
/*  834 */       this.conn.close();
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
/*      */   public boolean wasNull() throws SQLException {
/*  852 */     checkState();
/*      */     
/*  854 */     return this.rs.wasNull();
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
/*      */   public String getString(int paramInt) throws SQLException {
/*  874 */     checkState();
/*      */     
/*  876 */     return this.rs.getString(paramInt);
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
/*      */   public boolean getBoolean(int paramInt) throws SQLException {
/*  892 */     checkState();
/*      */     
/*  894 */     return this.rs.getBoolean(paramInt);
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
/*      */   public byte getByte(int paramInt) throws SQLException {
/*  910 */     checkState();
/*      */     
/*  912 */     return this.rs.getByte(paramInt);
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
/*      */   public short getShort(int paramInt) throws SQLException {
/*  928 */     checkState();
/*      */     
/*  930 */     return this.rs.getShort(paramInt);
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
/*      */   public int getInt(int paramInt) throws SQLException {
/*  946 */     checkState();
/*      */     
/*  948 */     return this.rs.getInt(paramInt);
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
/*      */   public long getLong(int paramInt) throws SQLException {
/*  964 */     checkState();
/*      */     
/*  966 */     return this.rs.getLong(paramInt);
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
/*      */   public float getFloat(int paramInt) throws SQLException {
/*  982 */     checkState();
/*      */     
/*  984 */     return this.rs.getFloat(paramInt);
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
/*      */   public double getDouble(int paramInt) throws SQLException {
/* 1000 */     checkState();
/*      */     
/* 1002 */     return this.rs.getDouble(paramInt);
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
/*      */   @Deprecated
/*      */   public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
/* 1021 */     checkState();
/*      */     
/* 1023 */     return this.rs.getBigDecimal(paramInt1, paramInt2);
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
/*      */   public byte[] getBytes(int paramInt) throws SQLException {
/* 1040 */     checkState();
/*      */     
/* 1042 */     return this.rs.getBytes(paramInt);
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
/*      */   public Date getDate(int paramInt) throws SQLException {
/* 1058 */     checkState();
/*      */     
/* 1060 */     return this.rs.getDate(paramInt);
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
/*      */   public Time getTime(int paramInt) throws SQLException {
/* 1076 */     checkState();
/*      */     
/* 1078 */     return this.rs.getTime(paramInt);
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
/*      */   public Timestamp getTimestamp(int paramInt) throws SQLException {
/* 1094 */     checkState();
/*      */     
/* 1096 */     return this.rs.getTimestamp(paramInt);
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
/*      */   public InputStream getAsciiStream(int paramInt) throws SQLException {
/* 1125 */     checkState();
/*      */     
/* 1127 */     return this.rs.getAsciiStream(paramInt);
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
/*      */   @Deprecated
/*      */   public InputStream getUnicodeStream(int paramInt) throws SQLException {
/* 1160 */     checkState();
/*      */     
/* 1162 */     return this.rs.getUnicodeStream(paramInt);
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
/*      */   public InputStream getBinaryStream(int paramInt) throws SQLException {
/* 1189 */     checkState();
/*      */     
/* 1191 */     return this.rs.getBinaryStream(paramInt);
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
/*      */   public String getString(String paramString) throws SQLException {
/* 1212 */     return getString(findColumn(paramString));
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
/* 1228 */     return getBoolean(findColumn(paramString));
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
/* 1244 */     return getByte(findColumn(paramString));
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
/* 1260 */     return getShort(findColumn(paramString));
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
/* 1276 */     return getInt(findColumn(paramString));
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
/* 1292 */     return getLong(findColumn(paramString));
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
/* 1308 */     return getFloat(findColumn(paramString));
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
/* 1324 */     return getDouble(findColumn(paramString));
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
/*      */   @Deprecated
/*      */   public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
/* 1343 */     return getBigDecimal(findColumn(paramString), paramInt);
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
/* 1360 */     return getBytes(findColumn(paramString));
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
/* 1376 */     return getDate(findColumn(paramString));
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
/*      */   public Time getTime(String paramString) throws SQLException {
/* 1393 */     return getTime(findColumn(paramString));
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
/* 1409 */     return getTimestamp(findColumn(paramString));
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
/*      */   public InputStream getAsciiStream(String paramString) throws SQLException {
/* 1437 */     return getAsciiStream(findColumn(paramString));
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
/*      */   @Deprecated
/*      */   public InputStream getUnicodeStream(String paramString) throws SQLException {
/* 1469 */     return getUnicodeStream(findColumn(paramString));
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
/*      */   public InputStream getBinaryStream(String paramString) throws SQLException {
/* 1496 */     return getBinaryStream(findColumn(paramString));
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
/*      */   public SQLWarning getWarnings() throws SQLException {
/* 1526 */     checkState();
/*      */     
/* 1528 */     return this.rs.getWarnings();
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
/*      */   public void clearWarnings() throws SQLException {
/* 1542 */     checkState();
/*      */     
/* 1544 */     this.rs.clearWarnings();
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
/*      */   public String getCursorName() throws SQLException {
/* 1573 */     checkState();
/*      */     
/* 1575 */     return this.rs.getCursorName();
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
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/* 1590 */     checkState();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1599 */       checkState();
/* 1600 */     } catch (SQLException sQLException) {
/* 1601 */       prepare();
/*      */       
/* 1603 */       return this.ps.getMetaData();
/*      */     } 
/* 1605 */     return this.rs.getMetaData();
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
/*      */   public Object getObject(int paramInt) throws SQLException {
/* 1636 */     checkState();
/*      */     
/* 1638 */     return this.rs.getObject(paramInt);
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
/*      */   public Object getObject(String paramString) throws SQLException {
/* 1669 */     return getObject(findColumn(paramString));
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
/*      */   public int findColumn(String paramString) throws SQLException {
/* 1686 */     checkState();
/*      */     
/* 1688 */     return this.rs.findColumn(paramString);
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
/* 1709 */     checkState();
/*      */     
/* 1711 */     return this.rs.getCharacterStream(paramInt);
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
/*      */   public Reader getCharacterStream(String paramString) throws SQLException {
/* 1727 */     return getCharacterStream(findColumn(paramString));
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
/* 1744 */     checkState();
/*      */     
/* 1746 */     return this.rs.getBigDecimal(paramInt);
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
/*      */   public BigDecimal getBigDecimal(String paramString) throws SQLException {
/* 1763 */     return getBigDecimal(findColumn(paramString));
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
/*      */   public boolean isBeforeFirst() throws SQLException {
/* 1782 */     checkState();
/*      */     
/* 1784 */     return this.rs.isBeforeFirst();
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
/*      */   public boolean isAfterLast() throws SQLException {
/* 1799 */     checkState();
/*      */     
/* 1801 */     return this.rs.isAfterLast();
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
/*      */   public boolean isFirst() throws SQLException {
/* 1815 */     checkState();
/*      */     
/* 1817 */     return this.rs.isFirst();
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
/*      */   public boolean isLast() throws SQLException {
/* 1836 */     checkState();
/*      */     
/* 1838 */     return this.rs.isLast();
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
/*      */   public void beforeFirst() throws SQLException {
/* 1852 */     checkState();
/*      */     
/* 1854 */     this.rs.beforeFirst();
/* 1855 */     notifyCursorMoved();
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
/*      */   public void afterLast() throws SQLException {
/* 1868 */     checkState();
/*      */     
/* 1870 */     this.rs.afterLast();
/* 1871 */     notifyCursorMoved();
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
/* 1886 */     checkState();
/*      */     
/* 1888 */     boolean bool = this.rs.first();
/* 1889 */     notifyCursorMoved();
/* 1890 */     return bool;
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
/* 1906 */     checkState();
/*      */     
/* 1908 */     boolean bool = this.rs.last();
/* 1909 */     notifyCursorMoved();
/* 1910 */     return bool;
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
/*      */   public int getRow() throws SQLException {
/* 1923 */     checkState();
/*      */     
/* 1925 */     return this.rs.getRow();
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
/*      */   public boolean absolute(int paramInt) throws SQLException {
/* 1961 */     checkState();
/*      */     
/* 1963 */     boolean bool = this.rs.absolute(paramInt);
/* 1964 */     notifyCursorMoved();
/* 1965 */     return bool;
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
/*      */   public boolean relative(int paramInt) throws SQLException {
/* 1991 */     checkState();
/*      */     
/* 1993 */     boolean bool = this.rs.relative(paramInt);
/* 1994 */     notifyCursorMoved();
/* 1995 */     return bool;
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
/*      */   public boolean previous() throws SQLException {
/* 2014 */     checkState();
/*      */     
/* 2016 */     boolean bool = this.rs.previous();
/* 2017 */     notifyCursorMoved();
/* 2018 */     return bool;
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
/*      */   public void setFetchDirection(int paramInt) throws SQLException {
/* 2037 */     checkState();
/*      */     
/* 2039 */     this.rs.setFetchDirection(paramInt);
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
/*      */   public int getFetchDirection() throws SQLException {
/*      */     try {
/* 2054 */       checkState();
/* 2055 */     } catch (SQLException sQLException) {
/* 2056 */       super.getFetchDirection();
/*      */     } 
/* 2058 */     return this.rs.getFetchDirection();
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
/*      */   public void setFetchSize(int paramInt) throws SQLException {
/* 2079 */     checkState();
/*      */     
/* 2081 */     this.rs.setFetchSize(paramInt);
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
/*      */   public int getType() throws SQLException {
/*      */     try {
/* 2096 */       checkState();
/* 2097 */     } catch (SQLException sQLException) {
/* 2098 */       return super.getType();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2103 */     if (this.rs == null) {
/* 2104 */       return super.getType();
/*      */     }
/* 2106 */     return this.rs.getType();
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
/*      */   public int getConcurrency() throws SQLException {
/*      */     try {
/* 2126 */       checkState();
/* 2127 */     } catch (SQLException sQLException) {
/* 2128 */       super.getConcurrency();
/*      */     } 
/* 2130 */     return this.rs.getConcurrency();
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
/*      */   public boolean rowUpdated() throws SQLException {
/* 2149 */     checkState();
/*      */     
/* 2151 */     return this.rs.rowUpdated();
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
/*      */   public boolean rowInserted() throws SQLException {
/* 2168 */     checkState();
/*      */     
/* 2170 */     return this.rs.rowInserted();
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
/* 2187 */     checkState();
/*      */     
/* 2189 */     return this.rs.rowDeleted();
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
/*      */   public void updateNull(int paramInt) throws SQLException {
/* 2206 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2210 */     checkTypeConcurrency();
/*      */     
/* 2212 */     this.rs.updateNull(paramInt);
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
/*      */   public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
/* 2230 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2234 */     checkTypeConcurrency();
/*      */     
/* 2236 */     this.rs.updateBoolean(paramInt, paramBoolean);
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
/*      */   public void updateByte(int paramInt, byte paramByte) throws SQLException {
/* 2255 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2259 */     checkTypeConcurrency();
/*      */     
/* 2261 */     this.rs.updateByte(paramInt, paramByte);
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
/*      */   public void updateShort(int paramInt, short paramShort) throws SQLException {
/* 2279 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2283 */     checkTypeConcurrency();
/*      */     
/* 2285 */     this.rs.updateShort(paramInt, paramShort);
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
/*      */   public void updateInt(int paramInt1, int paramInt2) throws SQLException {
/* 2302 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2306 */     checkTypeConcurrency();
/*      */     
/* 2308 */     this.rs.updateInt(paramInt1, paramInt2);
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
/*      */   public void updateLong(int paramInt, long paramLong) throws SQLException {
/* 2326 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2330 */     checkTypeConcurrency();
/*      */     
/* 2332 */     this.rs.updateLong(paramInt, paramLong);
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
/*      */   public void updateFloat(int paramInt, float paramFloat) throws SQLException {
/* 2350 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2354 */     checkTypeConcurrency();
/*      */     
/* 2356 */     this.rs.updateFloat(paramInt, paramFloat);
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
/*      */   public void updateDouble(int paramInt, double paramDouble) throws SQLException {
/* 2374 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2378 */     checkTypeConcurrency();
/*      */     
/* 2380 */     this.rs.updateDouble(paramInt, paramDouble);
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
/*      */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
/* 2399 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2403 */     checkTypeConcurrency();
/*      */     
/* 2405 */     this.rs.updateBigDecimal(paramInt, paramBigDecimal);
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
/*      */   public void updateString(int paramInt, String paramString) throws SQLException {
/* 2423 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2427 */     checkTypeConcurrency();
/*      */     
/* 2429 */     this.rs.updateString(paramInt, paramString);
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
/*      */   public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
/* 2447 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2451 */     checkTypeConcurrency();
/*      */     
/* 2453 */     this.rs.updateBytes(paramInt, paramArrayOfbyte);
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
/*      */   public void updateDate(int paramInt, Date paramDate) throws SQLException {
/* 2471 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2475 */     checkTypeConcurrency();
/*      */     
/* 2477 */     this.rs.updateDate(paramInt, paramDate);
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
/*      */   public void updateTime(int paramInt, Time paramTime) throws SQLException {
/* 2496 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2500 */     checkTypeConcurrency();
/*      */     
/* 2502 */     this.rs.updateTime(paramInt, paramTime);
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
/*      */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
/* 2521 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2525 */     checkTypeConcurrency();
/*      */     
/* 2527 */     this.rs.updateTimestamp(paramInt, paramTimestamp);
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
/*      */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 2546 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2550 */     checkTypeConcurrency();
/*      */     
/* 2552 */     this.rs.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
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
/*      */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 2571 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2575 */     checkTypeConcurrency();
/*      */     
/* 2577 */     this.rs.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
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
/*      */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
/* 2596 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2600 */     checkTypeConcurrency();
/*      */     
/* 2602 */     this.rs.updateCharacterStream(paramInt1, paramReader, paramInt2);
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
/*      */   public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
/* 2624 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2628 */     checkTypeConcurrency();
/*      */     
/* 2630 */     this.rs.updateObject(paramInt1, paramObject, paramInt2);
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
/*      */   public void updateObject(int paramInt, Object paramObject) throws SQLException {
/* 2648 */     checkState();
/*      */ 
/*      */ 
/*      */     
/* 2652 */     checkTypeConcurrency();
/*      */     
/* 2654 */     this.rs.updateObject(paramInt, paramObject);
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
/*      */   public void updateNull(String paramString) throws SQLException {
/* 2671 */     updateNull(findColumn(paramString));
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
/*      */   public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
/* 2687 */     updateBoolean(findColumn(paramString), paramBoolean);
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
/*      */   public void updateByte(String paramString, byte paramByte) throws SQLException {
/* 2703 */     updateByte(findColumn(paramString), paramByte);
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
/*      */   public void updateShort(String paramString, short paramShort) throws SQLException {
/* 2719 */     updateShort(findColumn(paramString), paramShort);
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
/*      */   public void updateInt(String paramString, int paramInt) throws SQLException {
/* 2735 */     updateInt(findColumn(paramString), paramInt);
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
/*      */   public void updateLong(String paramString, long paramLong) throws SQLException {
/* 2751 */     updateLong(findColumn(paramString), paramLong);
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
/*      */   public void updateFloat(String paramString, float paramFloat) throws SQLException {
/* 2767 */     updateFloat(findColumn(paramString), paramFloat);
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
/*      */   public void updateDouble(String paramString, double paramDouble) throws SQLException {
/* 2783 */     updateDouble(findColumn(paramString), paramDouble);
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
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/* 2800 */     updateBigDecimal(findColumn(paramString), paramBigDecimal);
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
/*      */   public void updateString(String paramString1, String paramString2) throws SQLException {
/* 2816 */     updateString(findColumn(paramString1), paramString2);
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
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/* 2841 */     updateBytes(findColumn(paramString), paramArrayOfbyte);
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
/*      */   public void updateDate(String paramString, Date paramDate) throws SQLException {
/* 2857 */     updateDate(findColumn(paramString), paramDate);
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
/*      */   public void updateTime(String paramString, Time paramTime) throws SQLException {
/* 2873 */     updateTime(findColumn(paramString), paramTime);
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
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/* 2890 */     updateTimestamp(findColumn(paramString), paramTimestamp);
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
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 2907 */     updateAsciiStream(findColumn(paramString), paramInputStream, paramInt);
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
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 2924 */     updateBinaryStream(findColumn(paramString), paramInputStream, paramInt);
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
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/* 2942 */     updateCharacterStream(findColumn(paramString), paramReader, paramInt);
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
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/* 2962 */     updateObject(findColumn(paramString), paramObject, paramInt);
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
/*      */   public void updateObject(String paramString, Object paramObject) throws SQLException {
/* 2978 */     updateObject(findColumn(paramString), paramObject);
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
/*      */   public void insertRow() throws SQLException {
/* 2995 */     checkState();
/*      */     
/* 2997 */     this.rs.insertRow();
/* 2998 */     notifyRowChanged();
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
/* 3015 */     checkState();
/*      */     
/* 3017 */     this.rs.updateRow();
/* 3018 */     notifyRowChanged();
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
/*      */   public void deleteRow() throws SQLException {
/* 3038 */     checkState();
/*      */     
/* 3040 */     this.rs.deleteRow();
/* 3041 */     notifyRowChanged();
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
/*      */   public void refreshRow() throws SQLException {
/* 3072 */     checkState();
/*      */     
/* 3074 */     this.rs.refreshRow();
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
/*      */   public void cancelRowUpdates() throws SQLException {
/* 3094 */     checkState();
/*      */     
/* 3096 */     this.rs.cancelRowUpdates();
/*      */     
/* 3098 */     notifyRowChanged();
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
/*      */   public void moveToInsertRow() throws SQLException {
/* 3126 */     checkState();
/*      */     
/* 3128 */     this.rs.moveToInsertRow();
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
/*      */   public void moveToCurrentRow() throws SQLException {
/* 3143 */     checkState();
/*      */     
/* 3145 */     this.rs.moveToCurrentRow();
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
/*      */   public Statement getStatement() throws SQLException {
/* 3162 */     if (this.rs != null)
/*      */     {
/* 3164 */       return this.rs.getStatement();
/*      */     }
/* 3166 */     return null;
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
/*      */   public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
/* 3189 */     checkState();
/*      */     
/* 3191 */     return this.rs.getObject(paramInt, paramMap);
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
/*      */   public Ref getRef(int paramInt) throws SQLException {
/* 3205 */     checkState();
/*      */     
/* 3207 */     return this.rs.getRef(paramInt);
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
/*      */   public Blob getBlob(int paramInt) throws SQLException {
/* 3223 */     checkState();
/*      */     
/* 3225 */     return this.rs.getBlob(paramInt);
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
/*      */   public Clob getClob(int paramInt) throws SQLException {
/* 3240 */     checkState();
/*      */     
/* 3242 */     return this.rs.getClob(paramInt);
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
/*      */   public Array getArray(int paramInt) throws SQLException {
/* 3257 */     checkState();
/*      */     
/* 3259 */     return this.rs.getArray(paramInt);
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
/*      */   public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
/* 3280 */     return getObject(findColumn(paramString), paramMap);
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
/*      */   public Ref getRef(String paramString) throws SQLException {
/* 3295 */     return getRef(findColumn(paramString));
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
/*      */   public Blob getBlob(String paramString) throws SQLException {
/* 3310 */     return getBlob(findColumn(paramString));
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
/*      */   public Clob getClob(String paramString) throws SQLException {
/* 3325 */     return getClob(findColumn(paramString));
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
/*      */   public Array getArray(String paramString) throws SQLException {
/* 3340 */     return getArray(findColumn(paramString));
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
/*      */   public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3361 */     checkState();
/*      */     
/* 3363 */     return this.rs.getDate(paramInt, paramCalendar);
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
/* 3385 */     return getDate(findColumn(paramString), paramCalendar);
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
/*      */   public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3406 */     checkState();
/*      */     
/* 3408 */     return this.rs.getTime(paramInt, paramCalendar);
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
/*      */   public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
/* 3429 */     return getTime(findColumn(paramString), paramCalendar);
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
/*      */   public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
/* 3451 */     checkState();
/*      */     
/* 3453 */     return this.rs.getTimestamp(paramInt, paramCalendar);
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
/*      */   public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
/* 3475 */     return getTimestamp(findColumn(paramString), paramCalendar);
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
/*      */   public void updateRef(int paramInt, Ref paramRef) throws SQLException {
/* 3504 */     checkState();
/* 3505 */     this.rs.updateRef(paramInt, paramRef);
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
/*      */   public void updateRef(String paramString, Ref paramRef) throws SQLException {
/* 3532 */     updateRef(findColumn(paramString), paramRef);
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
/*      */   public void updateClob(int paramInt, Clob paramClob) throws SQLException {
/* 3559 */     checkState();
/* 3560 */     this.rs.updateClob(paramInt, paramClob);
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
/*      */   public void updateClob(String paramString, Clob paramClob) throws SQLException {
/* 3587 */     updateClob(findColumn(paramString), paramClob);
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
/*      */   public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
/* 3614 */     checkState();
/* 3615 */     this.rs.updateBlob(paramInt, paramBlob);
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
/*      */   public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
/* 3641 */     updateBlob(findColumn(paramString), paramBlob);
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
/*      */   public void updateArray(int paramInt, Array paramArray) throws SQLException {
/* 3668 */     checkState();
/* 3669 */     this.rs.updateArray(paramInt, paramArray);
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
/*      */   public void updateArray(String paramString, Array paramArray) throws SQLException {
/* 3695 */     updateArray(findColumn(paramString), paramArray);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(int paramInt) throws SQLException {
/* 3702 */     checkState();
/* 3703 */     return this.rs.getURL(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(String paramString) throws SQLException {
/* 3710 */     return getURL(findColumn(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowSetWarning getRowSetWarnings() throws SQLException {
/* 3718 */     return null;
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
/*      */   public void unsetMatchColumn(int[] paramArrayOfint) throws SQLException {
/*      */     byte b;
/* 3737 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 3738 */       int i = Integer.parseInt(((Integer)this.iMatchColumns.get(b)).toString());
/* 3739 */       if (paramArrayOfint[b] != i) {
/* 3740 */         throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols").toString());
/*      */       }
/*      */     } 
/*      */     
/* 3744 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 3745 */       this.iMatchColumns.set(b, Integer.valueOf(-1));
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
/*      */   public void unsetMatchColumn(String[] paramArrayOfString) throws SQLException {
/*      */     byte b;
/* 3765 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 3766 */       if (!paramArrayOfString[b].equals(this.strMatchColumns.get(b))) {
/* 3767 */         throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols").toString());
/*      */       }
/*      */     } 
/*      */     
/* 3771 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 3772 */       this.strMatchColumns.set(b, null);
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
/*      */   public String[] getMatchColumnNames() throws SQLException {
/* 3788 */     String[] arrayOfString = new String[this.strMatchColumns.size()];
/*      */     
/* 3790 */     if (this.strMatchColumns.get(0) == null) {
/* 3791 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.setmatchcols").toString());
/*      */     }
/*      */     
/* 3794 */     this.strMatchColumns.copyInto((Object[])arrayOfString);
/* 3795 */     return arrayOfString;
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
/*      */   public int[] getMatchColumnIndexes() throws SQLException {
/* 3809 */     Integer[] arrayOfInteger = new Integer[this.iMatchColumns.size()];
/* 3810 */     int[] arrayOfInt = new int[this.iMatchColumns.size()];
/*      */ 
/*      */     
/* 3813 */     int i = ((Integer)this.iMatchColumns.get(0)).intValue();
/*      */     
/* 3815 */     if (i == -1) {
/* 3816 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.setmatchcols").toString());
/*      */     }
/*      */ 
/*      */     
/* 3820 */     this.iMatchColumns.copyInto((Object[])arrayOfInteger);
/*      */     
/* 3822 */     for (byte b = 0; b < arrayOfInteger.length; b++) {
/* 3823 */       arrayOfInt[b] = arrayOfInteger[b].intValue();
/*      */     }
/*      */     
/* 3826 */     return arrayOfInt;
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
/*      */   public void setMatchColumn(int[] paramArrayOfint) throws SQLException {
/*      */     byte b;
/* 3848 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 3849 */       if (paramArrayOfint[b] < 0) {
/* 3850 */         throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols1").toString());
/*      */       }
/*      */     } 
/* 3853 */     for (b = 0; b < paramArrayOfint.length; b++) {
/* 3854 */       this.iMatchColumns.add(b, Integer.valueOf(paramArrayOfint[b]));
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
/*      */   public void setMatchColumn(String[] paramArrayOfString) throws SQLException {
/*      */     byte b;
/* 3875 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 3876 */       if (paramArrayOfString[b] == null || paramArrayOfString[b].equals("")) {
/* 3877 */         throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols2").toString());
/*      */       }
/*      */     } 
/* 3880 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 3881 */       this.strMatchColumns.add(b, paramArrayOfString[b]);
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
/*      */   public void setMatchColumn(int paramInt) throws SQLException {
/* 3905 */     if (paramInt < 0) {
/* 3906 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols1").toString());
/*      */     }
/*      */     
/* 3909 */     this.iMatchColumns.set(0, Integer.valueOf(paramInt));
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
/*      */   public void setMatchColumn(String paramString) throws SQLException {
/* 3931 */     if (paramString == null || (paramString = paramString.trim()).equals("")) {
/* 3932 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.matchcols2").toString());
/*      */     }
/*      */     
/* 3935 */     this.strMatchColumns.set(0, paramString);
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
/*      */   public void unsetMatchColumn(int paramInt) throws SQLException {
/* 3956 */     if (!((Integer)this.iMatchColumns.get(0)).equals(Integer.valueOf(paramInt)))
/* 3957 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.unsetmatch").toString()); 
/* 3958 */     if (this.strMatchColumns.get(0) != null) {
/* 3959 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.usecolname").toString());
/*      */     }
/*      */     
/* 3962 */     this.iMatchColumns.set(0, Integer.valueOf(-1));
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
/*      */   public void unsetMatchColumn(String paramString) throws SQLException {
/* 3983 */     paramString = paramString.trim();
/*      */     
/* 3985 */     if (!((String)this.strMatchColumns.get(0)).equals(paramString))
/* 3986 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.unsetmatch").toString()); 
/* 3987 */     if (((Integer)this.iMatchColumns.get(0)).intValue() > 0) {
/* 3988 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.usecolid").toString());
/*      */     }
/* 3990 */     this.strMatchColumns.set(0, null);
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
/*      */   public DatabaseMetaData getDatabaseMetaData() throws SQLException {
/* 4004 */     Connection connection = connect();
/* 4005 */     return connection.getMetaData();
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
/*      */   public ParameterMetaData getParameterMetaData() throws SQLException {
/* 4018 */     prepare();
/* 4019 */     return this.ps.getParameterMetaData();
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
/*      */   public void commit() throws SQLException {
/* 4037 */     this.conn.commit();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4042 */     if (this.conn.getHoldability() != 1) {
/* 4043 */       this.rs = null;
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
/*      */   public void setAutoCommit(boolean paramBoolean) throws SQLException {
/* 4057 */     if (this.conn != null) {
/* 4058 */       this.conn.setAutoCommit(paramBoolean);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 4067 */       this.conn = connect();
/*      */ 
/*      */ 
/*      */       
/* 4071 */       this.conn.setAutoCommit(paramBoolean);
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
/*      */   public boolean getAutoCommit() throws SQLException {
/* 4083 */     return this.conn.getAutoCommit();
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
/*      */   public void rollback() throws SQLException {
/* 4099 */     this.conn.rollback();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4104 */     this.rs = null;
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
/*      */   public void rollback(Savepoint paramSavepoint) throws SQLException {
/* 4119 */     this.conn.rollback(paramSavepoint);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setParams() throws SQLException {
/* 4124 */     if (this.rs == null) {
/* 4125 */       setType(1004);
/* 4126 */       setConcurrency(1008);
/*      */     } else {
/*      */       
/* 4129 */       setType(this.rs.getType());
/* 4130 */       setConcurrency(this.rs.getConcurrency());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkTypeConcurrency() throws SQLException {
/* 4137 */     if (this.rs.getType() == 1003 || this.rs
/* 4138 */       .getConcurrency() == 1007) {
/* 4139 */       throw new SQLException(this.resBundle.handleGetObject("jdbcrowsetimpl.resnotupd").toString());
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
/*      */   protected Connection getConnection() {
/* 4154 */     return this.conn;
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
/*      */   protected void setConnection(Connection paramConnection) {
/* 4168 */     this.conn = paramConnection;
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
/*      */   protected PreparedStatement getPreparedStatement() {
/* 4182 */     return this.ps;
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
/*      */   protected void setPreparedStatement(PreparedStatement paramPreparedStatement) {
/* 4196 */     this.ps = paramPreparedStatement;
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
/*      */   protected ResultSet getResultSet() throws SQLException {
/* 4211 */     checkState();
/*      */     
/* 4213 */     return this.rs;
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
/*      */   protected void setResultSet(ResultSet paramResultSet) {
/* 4227 */     this.rs = paramResultSet;
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
/*      */   public void setCommand(String paramString) throws SQLException {
/* 4255 */     if (getCommand() != null) {
/* 4256 */       if (!getCommand().equals(paramString)) {
/* 4257 */         super.setCommand(paramString);
/* 4258 */         this.ps = null;
/* 4259 */         this.rs = null;
/*      */       } 
/*      */     } else {
/*      */       
/* 4263 */       super.setCommand(paramString);
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
/*      */   public void setDataSourceName(String paramString) throws SQLException {
/* 4298 */     if (getDataSourceName() != null) {
/* 4299 */       if (!getDataSourceName().equals(paramString)) {
/* 4300 */         super.setDataSourceName(paramString);
/* 4301 */         this.conn = null;
/* 4302 */         this.ps = null;
/* 4303 */         this.rs = null;
/*      */       } 
/*      */     } else {
/*      */       
/* 4307 */       super.setDataSourceName(paramString);
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
/*      */   public void setUrl(String paramString) throws SQLException {
/* 4355 */     if (getUrl() != null) {
/* 4356 */       if (!getUrl().equals(paramString)) {
/* 4357 */         super.setUrl(paramString);
/* 4358 */         this.conn = null;
/* 4359 */         this.ps = null;
/* 4360 */         this.rs = null;
/*      */       } 
/*      */     } else {
/*      */       
/* 4364 */       super.setUrl(paramString);
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
/*      */   public void setUsername(String paramString) {
/* 4390 */     if (getUsername() != null) {
/* 4391 */       if (!getUsername().equals(paramString)) {
/* 4392 */         super.setUsername(paramString);
/* 4393 */         this.conn = null;
/* 4394 */         this.ps = null;
/* 4395 */         this.rs = null;
/*      */       } 
/*      */     } else {
/*      */       
/* 4399 */       super.setUsername(paramString);
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
/*      */   public void setPassword(String paramString) {
/* 4424 */     if (getPassword() != null) {
/* 4425 */       if (!getPassword().equals(paramString)) {
/* 4426 */         super.setPassword(paramString);
/* 4427 */         this.conn = null;
/* 4428 */         this.ps = null;
/* 4429 */         this.rs = null;
/*      */       } 
/*      */     } else {
/*      */       
/* 4433 */       super.setPassword(paramString);
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
/*      */   public void setType(int paramInt) throws SQLException {
/*      */     int i;
/*      */     try {
/* 4459 */       i = getType();
/* 4460 */     } catch (SQLException sQLException) {
/* 4461 */       i = 0;
/*      */     } 
/*      */     
/* 4464 */     if (i != paramInt) {
/* 4465 */       super.setType(paramInt);
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
/*      */   public void setConcurrency(int paramInt) throws SQLException {
/*      */     int i;
/*      */     try {
/* 4491 */       i = getConcurrency();
/* 4492 */     } catch (NullPointerException nullPointerException) {
/* 4493 */       i = 0;
/*      */     } 
/*      */     
/* 4496 */     if (i != paramInt) {
/* 4497 */       super.setConcurrency(paramInt);
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
/*      */   public SQLXML getSQLXML(int paramInt) throws SQLException {
/* 4511 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SQLXML getSQLXML(String paramString) throws SQLException {
/* 4522 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public RowId getRowId(int paramInt) throws SQLException {
/* 4537 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public RowId getRowId(String paramString) throws SQLException {
/* 4552 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
/* 4568 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
/* 4584 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHoldability() throws SQLException {
/* 4594 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/* 4605 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNString(int paramInt, String paramString) throws SQLException {
/* 4617 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNString(String paramString1, String paramString2) throws SQLException {
/* 4629 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
/* 4642 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
/* 4654 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public NClob getNClob(int paramInt) throws SQLException {
/* 4669 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public NClob getNClob(String paramString) throws SQLException {
/* 4685 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
/*      */   }
/*      */   
/*      */   public <T> T unwrap(Class<T> paramClass) throws SQLException {
/* 4689 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
/* 4693 */     return false;
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
/*      */   public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
/* 4705 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
/* 4717 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
/* 4732 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setRowId(String paramString, RowId paramRowId) throws SQLException {
/* 4746 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNString(int paramInt, String paramString) throws SQLException {
/* 4766 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/* 4794 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(String paramString, NClob paramNClob) throws SQLException {
/* 4809 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public Reader getNCharacterStream(int paramInt) throws SQLException {
/* 4829 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public Reader getNCharacterStream(String paramString) throws SQLException {
/* 4849 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
/* 4865 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
/* 4882 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public String getNString(int paramInt) throws SQLException {
/* 4900 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public String getNString(String paramString) throws SQLException {
/* 4918 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 4940 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 4962 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/* 4992 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
/* 5024 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5057 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5090 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
/* 5125 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
/* 5160 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 5192 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 5224 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateClob(int paramInt, Reader paramReader) throws SQLException {
/* 5258 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateClob(String paramString, Reader paramReader) throws SQLException {
/* 5293 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 5327 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 5361 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
/* 5397 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateNClob(String paramString, Reader paramReader) throws SQLException {
/* 5434 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5459 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5483 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 5507 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5531 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
/* 5556 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
/* 5582 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/* 5607 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
/* 5632 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
/* 5659 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 5685 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
/* 5710 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
/* 5737 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setURL(int paramInt, URL paramURL) throws SQLException {
/* 5754 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(int paramInt, Reader paramReader) throws SQLException {
/* 5783 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 5811 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(String paramString, Reader paramReader) throws SQLException {
/* 5838 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 5865 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
/* 5881 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNString(String paramString1, String paramString2) throws SQLException {
/* 5898 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 5915 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 5935 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
/* 5961 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
/* 5987 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
/* 6013 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setClob(String paramString, Clob paramClob) throws SQLException {
/* 6032 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setClob(String paramString, Reader paramReader) throws SQLException {
/* 6057 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setDate(String paramString, Date paramDate) throws SQLException {
/* 6079 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
/* 6105 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setTime(String paramString, Time paramTime) throws SQLException {
/* 6125 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
/* 6151 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setClob(int paramInt, Reader paramReader) throws SQLException {
/* 6177 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
/* 6202 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
/* 6232 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
/* 6264 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
/* 6295 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBlob(String paramString, Blob paramBlob) throws SQLException {
/* 6313 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
/* 6339 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
/* 6385 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/* 6411 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setObject(String paramString, Object paramObject) throws SQLException {
/* 6451 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 6478 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 6505 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/* 6534 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
/* 6561 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
/* 6588 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
/* 6617 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/* 6636 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setString(String paramString1, String paramString2) throws SQLException {
/* 6657 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/* 6679 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/* 6699 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNull(String paramString, int paramInt) throws SQLException {
/* 6716 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
/* 6753 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
/* 6771 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setByte(String paramString, byte paramByte) throws SQLException {
/* 6791 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setShort(String paramString, short paramShort) throws SQLException {
/* 6810 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setInt(String paramString, int paramInt) throws SQLException {
/* 6829 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setLong(String paramString, long paramLong) throws SQLException {
/* 6847 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setFloat(String paramString, float paramFloat) throws SQLException {
/* 6866 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
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
/*      */   public void setDouble(String paramString, double paramDouble) throws SQLException {
/* 6884 */     throw new SQLFeatureNotSupportedException(this.resBundle.handleGetObject("jdbcrowsetimpl.featnotsupp").toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 6894 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     try {
/* 6897 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 6898 */     } catch (IOException iOException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T getObject(int paramInt, Class<T> paramClass) throws SQLException {
/* 6907 */     throw new SQLFeatureNotSupportedException("Not supported yet.");
/*      */   }
/*      */   
/*      */   public <T> T getObject(String paramString, Class<T> paramClass) throws SQLException {
/* 6911 */     throw new SQLFeatureNotSupportedException("Not supported yet.");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/JdbcRowSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */