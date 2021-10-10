/*     */ package com.sun.rowset.internal;
/*     */ 
/*     */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.Writer;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import javax.sql.RowSet;
/*     */ import javax.sql.RowSetInternal;
/*     */ import javax.sql.rowset.WebRowSet;
/*     */ import javax.sql.rowset.spi.XmlWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebRowSetXmlWriter
/*     */   implements XmlWriter, Serializable
/*     */ {
/*     */   private transient Writer writer;
/*     */   private Stack<String> stack;
/*     */   private JdbcRowSetResourceBundle resBundle;
/*     */   static final long serialVersionUID = 7163134986189677641L;
/*     */   
/*     */   public WebRowSetXmlWriter() {
/*     */     try {
/*  65 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  66 */     } catch (IOException iOException) {
/*  67 */       throw new RuntimeException(iOException);
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
/*     */   public void writeXML(WebRowSet paramWebRowSet, Writer paramWriter) throws SQLException {
/*  97 */     this.stack = new Stack<>();
/*  98 */     this.writer = paramWriter;
/*  99 */     writeRowSet(paramWebRowSet);
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
/*     */   public void writeXML(WebRowSet paramWebRowSet, OutputStream paramOutputStream) throws SQLException {
/* 130 */     this.stack = new Stack<>();
/* 131 */     this.writer = new OutputStreamWriter(paramOutputStream);
/* 132 */     writeRowSet(paramWebRowSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeRowSet(WebRowSet paramWebRowSet) throws SQLException {
/*     */     try {
/* 144 */       startHeader();
/*     */       
/* 146 */       writeProperties(paramWebRowSet);
/* 147 */       writeMetaData(paramWebRowSet);
/* 148 */       writeData(paramWebRowSet);
/*     */       
/* 150 */       endHeader();
/*     */     }
/* 152 */     catch (IOException iOException) {
/* 153 */       throw new SQLException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlwriter.ioex").toString(), new Object[] { iOException.getMessage() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void startHeader() throws IOException {
/* 159 */     setTag("webRowSet");
/* 160 */     this.writer.write("<?xml version=\"1.0\"?>\n");
/* 161 */     this.writer.write("<webRowSet xmlns=\"http://java.sun.com/xml/ns/jdbc\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
/* 162 */     this.writer.write("xsi:schemaLocation=\"http://java.sun.com/xml/ns/jdbc http://java.sun.com/xml/ns/jdbc/webrowset.xsd\">\n");
/*     */   }
/*     */   
/*     */   private void endHeader() throws IOException {
/* 166 */     endTag("webRowSet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeProperties(WebRowSet paramWebRowSet) throws IOException {
/* 176 */     beginSection("properties");
/*     */     
/*     */     try {
/* 179 */       propString("command", processSpecialCharacters(paramWebRowSet.getCommand()));
/* 180 */       propInteger("concurrency", paramWebRowSet.getConcurrency());
/* 181 */       propString("datasource", paramWebRowSet.getDataSourceName());
/* 182 */       propBoolean("escape-processing", paramWebRowSet
/* 183 */           .getEscapeProcessing());
/*     */       
/*     */       try {
/* 186 */         propInteger("fetch-direction", paramWebRowSet.getFetchDirection());
/* 187 */       } catch (SQLException sQLException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       propInteger("fetch-size", paramWebRowSet.getFetchSize());
/* 195 */       propInteger("isolation-level", paramWebRowSet
/* 196 */           .getTransactionIsolation());
/*     */       
/* 198 */       beginSection("key-columns");
/*     */       
/* 200 */       int[] arrayOfInt = paramWebRowSet.getKeyColumns();
/* 201 */       for (byte b = 0; arrayOfInt != null && b < arrayOfInt.length; b++) {
/* 202 */         propInteger("column", arrayOfInt[b]);
/*     */       }
/* 204 */       endSection("key-columns");
/*     */ 
/*     */       
/* 207 */       beginSection("map");
/* 208 */       Map<String, Class<?>> map = paramWebRowSet.getTypeMap();
/* 209 */       if (map != null) {
/* 210 */         for (Map.Entry<String, Class<?>> entry : map.entrySet()) {
/* 211 */           propString("type", (String)entry.getKey());
/* 212 */           propString("class", ((Class)entry.getValue()).getName());
/*     */         } 
/*     */       }
/* 215 */       endSection("map");
/*     */       
/* 217 */       propInteger("max-field-size", paramWebRowSet.getMaxFieldSize());
/* 218 */       propInteger("max-rows", paramWebRowSet.getMaxRows());
/* 219 */       propInteger("query-timeout", paramWebRowSet.getQueryTimeout());
/* 220 */       propBoolean("read-only", paramWebRowSet.isReadOnly());
/*     */       
/* 222 */       int i = paramWebRowSet.getType();
/* 223 */       String str1 = "";
/*     */       
/* 225 */       if (i == 1003) {
/* 226 */         str1 = "ResultSet.TYPE_FORWARD_ONLY";
/* 227 */       } else if (i == 1004) {
/* 228 */         str1 = "ResultSet.TYPE_SCROLL_INSENSITIVE";
/* 229 */       } else if (i == 1005) {
/* 230 */         str1 = "ResultSet.TYPE_SCROLL_SENSITIVE";
/*     */       } 
/*     */       
/* 233 */       propString("rowset-type", str1);
/*     */       
/* 235 */       propBoolean("show-deleted", paramWebRowSet.getShowDeleted());
/* 236 */       propString("table-name", paramWebRowSet.getTableName());
/* 237 */       propString("url", paramWebRowSet.getUrl());
/*     */       
/* 239 */       beginSection("sync-provider");
/*     */ 
/*     */       
/* 242 */       String str2 = paramWebRowSet.getSyncProvider().toString();
/* 243 */       String str3 = str2.substring(0, paramWebRowSet.getSyncProvider().toString().indexOf("@"));
/*     */       
/* 245 */       propString("sync-provider-name", str3);
/* 246 */       propString("sync-provider-vendor", "Oracle Corporation");
/* 247 */       propString("sync-provider-version", "1.0");
/* 248 */       propInteger("sync-provider-grade", paramWebRowSet.getSyncProvider().getProviderGrade());
/* 249 */       propInteger("data-source-lock", paramWebRowSet.getSyncProvider().getDataSourceLock());
/*     */       
/* 251 */       endSection("sync-provider");
/*     */     }
/* 253 */     catch (SQLException sQLException) {
/* 254 */       throw new IOException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlwriter.sqlex").toString(), new Object[] { sQLException.getMessage() }));
/*     */     } 
/*     */     
/* 257 */     endSection("properties");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMetaData(WebRowSet paramWebRowSet) throws IOException {
/* 268 */     beginSection("metadata");
/*     */ 
/*     */     
/*     */     try {
/* 272 */       ResultSetMetaData resultSetMetaData = paramWebRowSet.getMetaData();
/* 273 */       int i = resultSetMetaData.getColumnCount();
/* 274 */       propInteger("column-count", i);
/*     */       
/* 276 */       for (byte b = 1; b <= i; b++) {
/* 277 */         beginSection("column-definition");
/*     */         
/* 279 */         propInteger("column-index", b);
/* 280 */         propBoolean("auto-increment", resultSetMetaData.isAutoIncrement(b));
/* 281 */         propBoolean("case-sensitive", resultSetMetaData.isCaseSensitive(b));
/* 282 */         propBoolean("currency", resultSetMetaData.isCurrency(b));
/* 283 */         propInteger("nullable", resultSetMetaData.isNullable(b));
/* 284 */         propBoolean("signed", resultSetMetaData.isSigned(b));
/* 285 */         propBoolean("searchable", resultSetMetaData.isSearchable(b));
/* 286 */         propInteger("column-display-size", resultSetMetaData.getColumnDisplaySize(b));
/* 287 */         propString("column-label", resultSetMetaData.getColumnLabel(b));
/* 288 */         propString("column-name", resultSetMetaData.getColumnName(b));
/* 289 */         propString("schema-name", resultSetMetaData.getSchemaName(b));
/* 290 */         propInteger("column-precision", resultSetMetaData.getPrecision(b));
/* 291 */         propInteger("column-scale", resultSetMetaData.getScale(b));
/* 292 */         propString("table-name", resultSetMetaData.getTableName(b));
/* 293 */         propString("catalog-name", resultSetMetaData.getCatalogName(b));
/* 294 */         propInteger("column-type", resultSetMetaData.getColumnType(b));
/* 295 */         propString("column-type-name", resultSetMetaData.getColumnTypeName(b));
/*     */         
/* 297 */         endSection("column-definition");
/*     */       } 
/* 299 */     } catch (SQLException sQLException) {
/* 300 */       throw new IOException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlwriter.sqlex").toString(), new Object[] { sQLException.getMessage() }));
/*     */     } 
/*     */     
/* 303 */     endSection("metadata");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeData(WebRowSet paramWebRowSet) throws IOException {
/*     */     try {
/* 315 */       ResultSetMetaData resultSetMetaData = paramWebRowSet.getMetaData();
/* 316 */       int i = resultSetMetaData.getColumnCount();
/*     */ 
/*     */       
/* 319 */       beginSection("data");
/*     */       
/* 321 */       paramWebRowSet.beforeFirst();
/* 322 */       paramWebRowSet.setShowDeleted(true);
/* 323 */       while (paramWebRowSet.next()) {
/* 324 */         if (paramWebRowSet.rowDeleted() && paramWebRowSet.rowInserted()) {
/* 325 */           beginSection("modifyRow");
/* 326 */         } else if (paramWebRowSet.rowDeleted()) {
/* 327 */           beginSection("deleteRow");
/* 328 */         } else if (paramWebRowSet.rowInserted()) {
/* 329 */           beginSection("insertRow");
/*     */         } else {
/* 331 */           beginSection("currentRow");
/*     */         } 
/*     */         
/* 334 */         for (byte b = 1; b <= i; b++) {
/* 335 */           if (paramWebRowSet.columnUpdated(b)) {
/* 336 */             ResultSet resultSet = paramWebRowSet.getOriginalRow();
/* 337 */             resultSet.next();
/* 338 */             beginTag("columnValue");
/* 339 */             writeValue(b, (RowSet)resultSet);
/* 340 */             endTag("columnValue");
/* 341 */             beginTag("updateRow");
/* 342 */             writeValue(b, paramWebRowSet);
/* 343 */             endTag("updateRow");
/*     */           } else {
/* 345 */             beginTag("columnValue");
/* 346 */             writeValue(b, paramWebRowSet);
/* 347 */             endTag("columnValue");
/*     */           } 
/*     */         } 
/*     */         
/* 351 */         endSection();
/*     */       } 
/* 353 */       endSection("data");
/* 354 */     } catch (SQLException sQLException) {
/* 355 */       throw new IOException(MessageFormat.format(this.resBundle.handleGetObject("wrsxmlwriter.sqlex").toString(), new Object[] { sQLException.getMessage() }));
/*     */     }  } private void writeValue(int paramInt, RowSet paramRowSet) throws IOException { try {
/*     */       boolean bool; short s; int j; long l; float f; double d;
/*     */       Date date;
/*     */       Time time;
/*     */       Timestamp timestamp;
/* 361 */       int i = paramRowSet.getMetaData().getColumnType(paramInt);
/*     */       
/* 363 */       switch (i) {
/*     */         case -7:
/*     */         case 16:
/* 366 */           bool = paramRowSet.getBoolean(paramInt);
/* 367 */           if (paramRowSet.wasNull()) {
/* 368 */             writeNull();
/*     */           } else {
/* 370 */             writeBoolean(bool);
/*     */           } 
/*     */         case -6:
/*     */         case 5:
/* 374 */           s = paramRowSet.getShort(paramInt);
/* 375 */           if (paramRowSet.wasNull()) {
/* 376 */             writeNull();
/*     */           } else {
/* 378 */             writeShort(s);
/*     */           } 
/*     */         case 4:
/* 381 */           j = paramRowSet.getInt(paramInt);
/* 382 */           if (paramRowSet.wasNull()) {
/* 383 */             writeNull();
/*     */           } else {
/* 385 */             writeInteger(j);
/*     */           } 
/*     */         case -5:
/* 388 */           l = paramRowSet.getLong(paramInt);
/* 389 */           if (paramRowSet.wasNull()) {
/* 390 */             writeNull();
/*     */           } else {
/* 392 */             writeLong(l);
/*     */           } 
/*     */         case 6:
/*     */         case 7:
/* 396 */           f = paramRowSet.getFloat(paramInt);
/* 397 */           if (paramRowSet.wasNull()) {
/* 398 */             writeNull();
/*     */           } else {
/* 400 */             writeFloat(f);
/*     */           } 
/*     */         case 8:
/* 403 */           d = paramRowSet.getDouble(paramInt);
/* 404 */           if (paramRowSet.wasNull()) {
/* 405 */             writeNull();
/*     */           } else {
/* 407 */             writeDouble(d);
/*     */           } 
/*     */         case 2:
/*     */         case 3:
/* 411 */           writeBigDecimal(paramRowSet.getBigDecimal(paramInt));
/*     */         
/*     */         case -4:
/*     */         case -3:
/*     */         case -2:
/*     */           return;
/*     */         case 91:
/* 418 */           date = paramRowSet.getDate(paramInt);
/* 419 */           if (paramRowSet.wasNull()) {
/* 420 */             writeNull();
/*     */           } else {
/* 422 */             writeLong(date.getTime());
/*     */           } 
/*     */         case 92:
/* 425 */           time = paramRowSet.getTime(paramInt);
/* 426 */           if (paramRowSet.wasNull()) {
/* 427 */             writeNull();
/*     */           } else {
/* 429 */             writeLong(time.getTime());
/*     */           } 
/*     */         case 93:
/* 432 */           timestamp = paramRowSet.getTimestamp(paramInt);
/* 433 */           if (paramRowSet.wasNull()) {
/* 434 */             writeNull();
/*     */           } else {
/* 436 */             writeLong(timestamp.getTime());
/*     */           } 
/*     */         case -1:
/*     */         case 1:
/*     */         case 12:
/* 441 */           writeStringData(paramRowSet.getString(paramInt));
/*     */       } 
/*     */       
/* 444 */       System.out.println(this.resBundle.handleGetObject("wsrxmlwriter.notproper").toString());
/*     */     
/*     */     }
/* 447 */     catch (SQLException sQLException) {
/* 448 */       throw new IOException(this.resBundle.handleGetObject("wrsxmlwriter.failedwrite").toString() + sQLException.getMessage());
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void beginSection(String paramString) throws IOException {
/* 458 */     setTag(paramString);
/*     */     
/* 460 */     writeIndent(this.stack.size());
/*     */ 
/*     */     
/* 463 */     this.writer.write("<" + paramString + ">\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endSection(String paramString) throws IOException {
/* 471 */     writeIndent(this.stack.size());
/*     */     
/* 473 */     String str = getTag();
/*     */     
/* 475 */     if (str.indexOf("webRowSet") != -1) {
/* 476 */       str = "webRowSet";
/*     */     }
/*     */     
/* 479 */     if (paramString.equals(str))
/*     */     {
/* 481 */       this.writer.write("</" + str + ">\n");
/*     */     }
/*     */ 
/*     */     
/* 485 */     this.writer.flush();
/*     */   }
/*     */   
/*     */   private void endSection() throws IOException {
/* 489 */     writeIndent(this.stack.size());
/*     */ 
/*     */     
/* 492 */     String str = getTag();
/* 493 */     this.writer.write("</" + str + ">\n");
/*     */     
/* 495 */     this.writer.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   private void beginTag(String paramString) throws IOException {
/* 500 */     setTag(paramString);
/*     */     
/* 502 */     writeIndent(this.stack.size());
/*     */ 
/*     */     
/* 505 */     this.writer.write("<" + paramString + ">");
/*     */   }
/*     */   
/*     */   private void endTag(String paramString) throws IOException {
/* 509 */     String str = getTag();
/* 510 */     if (paramString.equals(str))
/*     */     {
/* 512 */       this.writer.write("</" + str + ">\n");
/*     */     }
/*     */ 
/*     */     
/* 516 */     this.writer.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   private void emptyTag(String paramString) throws IOException {
/* 521 */     this.writer.write("<" + paramString + "/>");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTag(String paramString) {
/* 526 */     this.stack.push(paramString);
/*     */   }
/*     */   
/*     */   private String getTag() {
/* 530 */     return this.stack.pop();
/*     */   }
/*     */   
/*     */   private void writeNull() throws IOException {
/* 534 */     emptyTag("null");
/*     */   }
/*     */   
/*     */   private void writeStringData(String paramString) throws IOException {
/* 538 */     if (paramString == null) {
/* 539 */       writeNull();
/* 540 */     } else if (paramString.equals("")) {
/* 541 */       writeEmptyString();
/*     */     } else {
/*     */       
/* 544 */       paramString = processSpecialCharacters(paramString);
/*     */       
/* 546 */       this.writer.write(paramString);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeString(String paramString) throws IOException {
/* 551 */     if (paramString != null) {
/* 552 */       this.writer.write(paramString);
/*     */     } else {
/* 554 */       writeNull();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeShort(short paramShort) throws IOException {
/* 560 */     this.writer.write(Short.toString(paramShort));
/*     */   }
/*     */   
/*     */   private void writeLong(long paramLong) throws IOException {
/* 564 */     this.writer.write(Long.toString(paramLong));
/*     */   }
/*     */   
/*     */   private void writeInteger(int paramInt) throws IOException {
/* 568 */     this.writer.write(Integer.toString(paramInt));
/*     */   }
/*     */   
/*     */   private void writeBoolean(boolean paramBoolean) throws IOException {
/* 572 */     this.writer.write(Boolean.valueOf(paramBoolean).toString());
/*     */   }
/*     */   
/*     */   private void writeFloat(float paramFloat) throws IOException {
/* 576 */     this.writer.write(Float.toString(paramFloat));
/*     */   }
/*     */   
/*     */   private void writeDouble(double paramDouble) throws IOException {
/* 580 */     this.writer.write(Double.toString(paramDouble));
/*     */   }
/*     */   
/*     */   private void writeBigDecimal(BigDecimal paramBigDecimal) throws IOException {
/* 584 */     if (paramBigDecimal != null) {
/* 585 */       this.writer.write(paramBigDecimal.toString());
/*     */     } else {
/* 587 */       emptyTag("null");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeIndent(int paramInt) throws IOException {
/* 592 */     for (byte b = 1; b < paramInt; b++) {
/* 593 */       this.writer.write("  ");
/*     */     }
/*     */   }
/*     */   
/*     */   private void propString(String paramString1, String paramString2) throws IOException {
/* 598 */     beginTag(paramString1);
/* 599 */     writeString(paramString2);
/* 600 */     endTag(paramString1);
/*     */   }
/*     */   
/*     */   private void propInteger(String paramString, int paramInt) throws IOException {
/* 604 */     beginTag(paramString);
/* 605 */     writeInteger(paramInt);
/* 606 */     endTag(paramString);
/*     */   }
/*     */   
/*     */   private void propBoolean(String paramString, boolean paramBoolean) throws IOException {
/* 610 */     beginTag(paramString);
/* 611 */     writeBoolean(paramBoolean);
/* 612 */     endTag(paramString);
/*     */   }
/*     */   
/*     */   private void writeEmptyString() throws IOException {
/* 616 */     emptyTag("emptyString");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeData(RowSetInternal paramRowSetInternal) {
/* 622 */     return false;
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
/*     */   private String processSpecialCharacters(String paramString) {
/* 635 */     if (paramString == null) {
/* 636 */       return null;
/*     */     }
/* 638 */     char[] arrayOfChar = paramString.toCharArray();
/* 639 */     String str = "";
/*     */     
/* 641 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 642 */       if (arrayOfChar[b] == '&') {
/* 643 */         str = str.concat("&amp;");
/* 644 */       } else if (arrayOfChar[b] == '<') {
/* 645 */         str = str.concat("&lt;");
/* 646 */       } else if (arrayOfChar[b] == '>') {
/* 647 */         str = str.concat("&gt;");
/* 648 */       } else if (arrayOfChar[b] == '\'') {
/* 649 */         str = str.concat("&apos;");
/* 650 */       } else if (arrayOfChar[b] == '"') {
/* 651 */         str = str.concat("&quot;");
/*     */       } else {
/* 653 */         str = str.concat(String.valueOf(arrayOfChar[b]));
/*     */       } 
/*     */     } 
/*     */     
/* 657 */     paramString = str;
/* 658 */     return paramString;
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
/* 669 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 672 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 673 */     } catch (IOException iOException) {
/* 674 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/WebRowSetXmlWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */