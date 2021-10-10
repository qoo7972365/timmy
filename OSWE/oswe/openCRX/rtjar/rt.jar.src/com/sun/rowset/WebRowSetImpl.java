/*     */ package com.sun.rowset;
/*     */ 
/*     */ import com.sun.rowset.internal.WebRowSetXmlReader;
/*     */ import com.sun.rowset.internal.WebRowSetXmlWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ import javax.sql.rowset.spi.SyncFactory;
/*     */ import javax.sql.rowset.spi.SyncProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebRowSetImpl
/*     */   extends CachedRowSetImpl
/*     */   implements WebRowSet
/*     */ {
/*     */   private WebRowSetXmlReader xmlReader;
/*     */   private WebRowSetXmlWriter xmlWriter;
/*     */   private int curPosBfrWrite;
/*     */   private SyncProvider provider;
/*     */   static final long serialVersionUID = -8771775154092422943L;
/*     */   
/*     */   public WebRowSetImpl() throws SQLException {
/*  91 */     this.xmlReader = new WebRowSetXmlReader();
/*  92 */     this.xmlWriter = new WebRowSetXmlWriter();
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
/*     */   public WebRowSetImpl(Hashtable paramHashtable) throws SQLException {
/*     */     try {
/* 108 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 109 */     } catch (IOException iOException) {
/* 110 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */     
/* 113 */     if (paramHashtable == null) {
/* 114 */       throw new SQLException(this.resBundle.handleGetObject("webrowsetimpl.nullhash").toString());
/*     */     }
/*     */ 
/*     */     
/* 118 */     String str = (String)paramHashtable.get("rowset.provider.classname");
/*     */ 
/*     */     
/* 121 */     this.provider = SyncFactory.getInstance(str);
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
/*     */   public void writeXml(ResultSet paramResultSet, Writer paramWriter) throws SQLException {
/* 139 */     populate(paramResultSet);
/*     */ 
/*     */     
/* 142 */     this.curPosBfrWrite = getRow();
/*     */     
/* 144 */     writeXml(paramWriter);
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
/*     */   public void writeXml(Writer paramWriter) throws SQLException {
/* 160 */     if (this.xmlWriter != null) {
/*     */ 
/*     */       
/* 163 */       this.curPosBfrWrite = getRow();
/*     */       
/* 165 */       this.xmlWriter.writeXML(this, paramWriter);
/*     */     } else {
/* 167 */       throw new SQLException(this.resBundle.handleGetObject("webrowsetimpl.invalidwr").toString());
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
/*     */   public void readXml(Reader paramReader) throws SQLException {
/*     */     try {
/* 182 */       if (paramReader != null) {
/* 183 */         this.xmlReader.readXML(this, paramReader);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         if (this.curPosBfrWrite == 0) {
/* 189 */           beforeFirst();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 194 */           absolute(this.curPosBfrWrite);
/*     */         } 
/*     */       } else {
/*     */         
/* 198 */         throw new SQLException(this.resBundle.handleGetObject("webrowsetimpl.invalidrd").toString());
/*     */       } 
/* 200 */     } catch (Exception exception) {
/* 201 */       throw new SQLException(exception.getMessage());
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
/*     */   public void readXml(InputStream paramInputStream) throws SQLException, IOException {
/* 214 */     if (paramInputStream != null) {
/* 215 */       this.xmlReader.readXML(this, paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       if (this.curPosBfrWrite == 0) {
/* 221 */         beforeFirst();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 226 */         absolute(this.curPosBfrWrite);
/*     */       } 
/*     */     } else {
/*     */       
/* 230 */       throw new SQLException(this.resBundle.handleGetObject("webrowsetimpl.invalidrd").toString());
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
/*     */   public void writeXml(OutputStream paramOutputStream) throws SQLException, IOException {
/* 244 */     if (this.xmlWriter != null) {
/*     */ 
/*     */       
/* 247 */       this.curPosBfrWrite = getRow();
/*     */       
/* 249 */       this.xmlWriter.writeXML(this, paramOutputStream);
/*     */     } else {
/* 251 */       throw new SQLException(this.resBundle.handleGetObject("webrowsetimpl.invalidwr").toString());
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
/*     */   public void writeXml(ResultSet paramResultSet, OutputStream paramOutputStream) throws SQLException, IOException {
/* 266 */     populate(paramResultSet);
/*     */ 
/*     */     
/* 269 */     this.curPosBfrWrite = getRow();
/*     */     
/* 271 */     writeXml(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 281 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 284 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 285 */     } catch (IOException iOException) {
/* 286 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/WebRowSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */