/*     */ package com.sun.rowset.internal;
/*     */ 
/*     */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Calendar;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import javax.sql.DataSource;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.RowSetReader;
/*     */ import javax.sql.rowset.CachedRowSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedRowSetReader
/*     */   implements RowSetReader, Serializable
/*     */ {
/*  79 */   private int writerCalls = 0;
/*     */   
/*     */   private boolean userCon = false;
/*     */   
/*     */   private int startPosition;
/*     */   private JdbcRowSetResourceBundle resBundle;
/*     */   static final long serialVersionUID = 5049738185801363801L;
/*     */   
/*     */   public CachedRowSetReader() {
/*     */     try {
/*  89 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  90 */     } catch (IOException iOException) {
/*  91 */       throw new RuntimeException(iOException);
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
/*     */   public void readData(RowSetInternal paramRowSetInternal) throws SQLException {
/* 132 */     Connection connection = null;
/*     */     try {
/* 134 */       CachedRowSet cachedRowSet = (CachedRowSet)paramRowSetInternal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       if (cachedRowSet.getPageSize() == 0 && cachedRowSet.size() > 0)
/*     */       {
/*     */         
/* 147 */         cachedRowSet.close();
/*     */       }
/*     */       
/* 150 */       this.writerCalls = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       this.userCon = false;
/*     */       
/* 157 */       connection = connect(paramRowSetInternal);
/*     */ 
/*     */       
/* 160 */       if (connection == null || cachedRowSet.getCommand() == null) {
/* 161 */         throw new SQLException(this.resBundle.handleGetObject("crsreader.connecterr").toString());
/*     */       }
/*     */       try {
/* 164 */         connection.setTransactionIsolation(cachedRowSet.getTransactionIsolation());
/* 165 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 169 */       PreparedStatement preparedStatement = connection.prepareStatement(cachedRowSet.getCommand());
/*     */ 
/*     */       
/* 172 */       decodeParams(paramRowSetInternal.getParams(), preparedStatement);
/*     */       try {
/* 174 */         preparedStatement.setMaxRows(cachedRowSet.getMaxRows());
/* 175 */         preparedStatement.setMaxFieldSize(cachedRowSet.getMaxFieldSize());
/* 176 */         preparedStatement.setEscapeProcessing(cachedRowSet.getEscapeProcessing());
/* 177 */         preparedStatement.setQueryTimeout(cachedRowSet.getQueryTimeout());
/* 178 */       } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 183 */         throw new SQLException(exception.getMessage());
/*     */       } 
/*     */       
/* 186 */       if (cachedRowSet.getCommand().toLowerCase().indexOf("select") != -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 195 */         ResultSet resultSet = preparedStatement.executeQuery();
/* 196 */         if (cachedRowSet.getPageSize() == 0) {
/* 197 */           cachedRowSet.populate(resultSet);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 205 */           preparedStatement = connection.prepareStatement(cachedRowSet.getCommand(), 1004, 1008);
/* 206 */           decodeParams(paramRowSetInternal.getParams(), preparedStatement);
/*     */           try {
/* 208 */             preparedStatement.setMaxRows(cachedRowSet.getMaxRows());
/* 209 */             preparedStatement.setMaxFieldSize(cachedRowSet.getMaxFieldSize());
/* 210 */             preparedStatement.setEscapeProcessing(cachedRowSet.getEscapeProcessing());
/* 211 */             preparedStatement.setQueryTimeout(cachedRowSet.getQueryTimeout());
/* 212 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 217 */             throw new SQLException(exception.getMessage());
/*     */           } 
/* 219 */           resultSet = preparedStatement.executeQuery();
/* 220 */           cachedRowSet.populate(resultSet, this.startPosition);
/*     */         } 
/* 222 */         resultSet.close();
/*     */       } else {
/* 224 */         preparedStatement.executeUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 228 */       preparedStatement.close();
/*     */       try {
/* 230 */         connection.commit();
/* 231 */       } catch (SQLException sQLException) {}
/*     */ 
/*     */ 
/*     */       
/* 235 */       if (getCloseConnection() == true) {
/* 236 */         connection.close();
/*     */       }
/* 238 */     } catch (SQLException sQLException) {
/*     */       
/* 240 */       throw sQLException;
/*     */     } finally {
/*     */       
/*     */       try {
/* 244 */         if (connection != null && getCloseConnection() == true) {
/*     */           try {
/* 246 */             if (!connection.getAutoCommit()) {
/* 247 */               connection.rollback();
/*     */             }
/* 249 */           } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 257 */           connection.close();
/* 258 */           connection = null;
/*     */         } 
/* 260 */       } catch (SQLException sQLException) {}
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
/*     */   public boolean reset() throws SQLException {
/* 279 */     this.writerCalls++;
/* 280 */     return (this.writerCalls == 1);
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
/*     */   public Connection connect(RowSetInternal paramRowSetInternal) throws SQLException {
/* 307 */     if (paramRowSetInternal.getConnection() != null) {
/*     */ 
/*     */ 
/*     */       
/* 311 */       this.userCon = true;
/* 312 */       return paramRowSetInternal.getConnection();
/*     */     } 
/* 314 */     if (((RowSet)paramRowSetInternal).getDataSourceName() != null)
/*     */       
/*     */       try {
/* 317 */         InitialContext initialContext = new InitialContext();
/*     */         
/* 319 */         DataSource dataSource = (DataSource)initialContext.lookup(((RowSet)paramRowSetInternal).getDataSourceName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 326 */         if (((RowSet)paramRowSetInternal).getUsername() != null) {
/* 327 */           return dataSource.getConnection(((RowSet)paramRowSetInternal).getUsername(), ((RowSet)paramRowSetInternal)
/* 328 */               .getPassword());
/*     */         }
/* 330 */         return dataSource.getConnection();
/*     */       
/*     */       }
/* 333 */       catch (NamingException namingException) {
/* 334 */         SQLException sQLException = new SQLException(this.resBundle.handleGetObject("crsreader.connect").toString());
/* 335 */         sQLException.initCause(namingException);
/* 336 */         throw sQLException;
/*     */       }  
/* 338 */     if (((RowSet)paramRowSetInternal).getUrl() != null)
/*     */     {
/* 340 */       return DriverManager.getConnection(((RowSet)paramRowSetInternal).getUrl(), ((RowSet)paramRowSetInternal)
/* 341 */           .getUsername(), ((RowSet)paramRowSetInternal)
/* 342 */           .getPassword());
/*     */     }
/*     */     
/* 345 */     return null;
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
/*     */   private void decodeParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
/* 377 */     Object[] arrayOfObject = null;
/*     */     
/* 379 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 380 */       if (paramArrayOfObject[b] instanceof Object[]) {
/* 381 */         arrayOfObject = (Object[])paramArrayOfObject[b];
/*     */         
/* 383 */         if (arrayOfObject.length == 2) {
/* 384 */           if (arrayOfObject[0] == null) {
/* 385 */             paramPreparedStatement.setNull(b + 1, ((Integer)arrayOfObject[1]).intValue());
/*     */ 
/*     */           
/*     */           }
/* 389 */           else if (arrayOfObject[0] instanceof Date || arrayOfObject[0] instanceof java.sql.Time || arrayOfObject[0] instanceof java.sql.Timestamp) {
/*     */ 
/*     */             
/* 392 */             System.err.println(this.resBundle.handleGetObject("crsreader.datedetected").toString());
/* 393 */             if (arrayOfObject[1] instanceof Calendar) {
/* 394 */               System.err.println(this.resBundle.handleGetObject("crsreader.caldetected").toString());
/* 395 */               paramPreparedStatement.setDate(b + 1, (Date)arrayOfObject[0], (Calendar)arrayOfObject[1]);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 400 */               throw new SQLException(this.resBundle.handleGetObject("crsreader.paramtype").toString());
/*     */             }
/*     */           
/*     */           }
/* 404 */           else if (arrayOfObject[0] instanceof Reader) {
/* 405 */             paramPreparedStatement.setCharacterStream(b + 1, (Reader)arrayOfObject[0], ((Integer)arrayOfObject[1])
/* 406 */                 .intValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 413 */           else if (arrayOfObject[1] instanceof Integer) {
/* 414 */             paramPreparedStatement.setObject(b + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue());
/*     */           }
/*     */         
/*     */         }
/* 418 */         else if (arrayOfObject.length == 3) {
/*     */           
/* 420 */           if (arrayOfObject[0] == null) {
/* 421 */             paramPreparedStatement.setNull(b + 1, ((Integer)arrayOfObject[1]).intValue(), (String)arrayOfObject[2]);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 426 */             if (arrayOfObject[0] instanceof InputStream) {
/* 427 */               switch (((Integer)arrayOfObject[2]).intValue()) {
/*     */                 case 0:
/* 429 */                   paramPreparedStatement.setUnicodeStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*     */                       
/* 431 */                       .intValue());
/*     */                   break;
/*     */                 case 1:
/* 434 */                   paramPreparedStatement.setBinaryStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*     */                       
/* 436 */                       .intValue());
/*     */                   break;
/*     */                 case 2:
/* 439 */                   paramPreparedStatement.setAsciiStream(b + 1, (InputStream)arrayOfObject[0], ((Integer)arrayOfObject[1])
/*     */                       
/* 441 */                       .intValue());
/*     */                   break;
/*     */                 default:
/* 444 */                   throw new SQLException(this.resBundle.handleGetObject("crsreader.paramtype").toString());
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 452 */             if (arrayOfObject[1] instanceof Integer && arrayOfObject[2] instanceof Integer) {
/* 453 */               paramPreparedStatement.setObject(b + 1, arrayOfObject[0], ((Integer)arrayOfObject[1]).intValue(), ((Integer)arrayOfObject[2])
/* 454 */                   .intValue());
/*     */             }
/*     */             else {
/*     */               
/* 458 */               throw new SQLException(this.resBundle.handleGetObject("crsreader.paramtype").toString());
/*     */             } 
/*     */           } 
/*     */         } else {
/* 462 */           paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 467 */         paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
/*     */       } 
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
/*     */   protected boolean getCloseConnection() {
/* 480 */     if (this.userCon == true) {
/* 481 */       return false;
/*     */     }
/* 483 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartPosition(int paramInt) {
/* 494 */     this.startPosition = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 499 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 502 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 503 */     } catch (IOException iOException) {
/* 504 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/CachedRowSetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */