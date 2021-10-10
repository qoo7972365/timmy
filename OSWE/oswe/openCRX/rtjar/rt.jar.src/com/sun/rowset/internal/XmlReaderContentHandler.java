/*      */ package com.sun.rowset.internal;
/*      */ 
/*      */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*      */ import com.sun.rowset.WebRowSetImpl;
/*      */ import java.io.IOException;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.sql.RowSet;
/*      */ import javax.sql.RowSetMetaData;
/*      */ import javax.sql.rowset.RowSetMetaDataImpl;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.helpers.DefaultHandler;
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
/*      */ public class XmlReaderContentHandler
/*      */   extends DefaultHandler
/*      */ {
/*      */   private HashMap<String, Integer> propMap;
/*      */   private HashMap<String, Integer> colDefMap;
/*      */   private HashMap<String, Integer> dataMap;
/*      */   private HashMap<String, Class<?>> typeMap;
/*      */   private Vector<Object[]> updates;
/*      */   private Vector<String> keyCols;
/*      */   private String columnValue;
/*      */   private String propertyValue;
/*      */   private String metaDataValue;
/*      */   private int tag;
/*      */   private int state;
/*      */   private WebRowSetImpl rs;
/*      */   private boolean nullVal;
/*      */   private boolean emptyStringVal;
/*      */   private RowSetMetaData md;
/*      */   private int idx;
/*      */   private String lastval;
/*      */   private String Key_map;
/*      */   private String Value_map;
/*      */   private String tempStr;
/*      */   private String tempUpdate;
/*      */   private String tempCommand;
/*      */   private Object[] upd;
/*  109 */   private String[] properties = new String[] { "command", "concurrency", "datasource", "escape-processing", "fetch-direction", "fetch-size", "isolation-level", "key-columns", "map", "max-field-size", "max-rows", "query-timeout", "read-only", "rowset-type", "show-deleted", "table-name", "url", "null", "column", "type", "class", "sync-provider", "sync-provider-name", "sync-provider-vendor", "sync-provider-version", "sync-provider-grade", "data-source-lock" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CommandTag = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ConcurrencyTag = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DatasourceTag = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int EscapeProcessingTag = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int FetchDirectionTag = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int FetchSizeTag = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int IsolationLevelTag = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int KeycolsTag = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MapTag = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MaxFieldSizeTag = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MaxRowsTag = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int QueryTimeoutTag = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ReadOnlyTag = 12;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int RowsetTypeTag = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ShowDeletedTag = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TableNameTag = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int UrlTag = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PropNullTag = 17;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PropColumnTag = 18;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PropTypeTag = 19;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PropClassTag = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SyncProviderTag = 21;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SyncProviderNameTag = 22;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SyncProviderVendorTag = 23;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SyncProviderVersionTag = 24;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SyncProviderGradeTag = 25;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DataSourceLock = 26;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   private String[] colDef = new String[] { "column-count", "column-definition", "column-index", "auto-increment", "case-sensitive", "currency", "nullable", "signed", "searchable", "column-display-size", "column-label", "column-name", "schema-name", "column-precision", "column-scale", "table-name", "catalog-name", "column-type", "column-type-name", "null" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnCountTag = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnDefinitionTag = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnIndexTag = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int AutoIncrementTag = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CaseSensitiveTag = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CurrencyTag = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NullableTag = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SignedTag = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SearchableTag = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnDisplaySizeTag = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnLabelTag = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnNameTag = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SchemaNameTag = 12;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnPrecisionTag = 13;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnScaleTag = 14;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MetaTableNameTag = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CatalogNameTag = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnTypeTag = 17;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColumnTypeNameTag = 18;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MetaNullTag = 19;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  373 */   private String[] data = new String[] { "currentRow", "columnValue", "insertRow", "deleteRow", "insdel", "updateRow", "null", "emptyString" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int RowTag = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ColTag = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int InsTag = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DelTag = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int InsDelTag = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int UpdTag = 5;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NullTag = 6;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int EmptyStringTag = 7;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int INITIAL = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PROPERTIES = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int METADATA = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DATA = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   private JdbcRowSetResourceBundle resBundle;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XmlReaderContentHandler(RowSet paramRowSet) {
/*  435 */     this.rs = (WebRowSetImpl)paramRowSet;
/*      */ 
/*      */     
/*  438 */     initMaps();
/*      */ 
/*      */     
/*  441 */     this.updates = new Vector();
/*      */ 
/*      */     
/*  444 */     this.columnValue = "";
/*  445 */     this.propertyValue = "";
/*  446 */     this.metaDataValue = "";
/*      */     
/*  448 */     this.nullVal = false;
/*  449 */     this.idx = 0;
/*  450 */     this.tempStr = "";
/*  451 */     this.tempUpdate = "";
/*  452 */     this.tempCommand = "";
/*      */     
/*      */     try {
/*  455 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  456 */     } catch (IOException iOException) {
/*  457 */       throw new RuntimeException(iOException);
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
/*      */   private void initMaps() {
/*  480 */     this.propMap = new HashMap<>();
/*  481 */     int i = this.properties.length;
/*      */     byte b;
/*  483 */     for (b = 0; b < i; b++) {
/*  484 */       this.propMap.put(this.properties[b], Integer.valueOf(b));
/*      */     }
/*      */     
/*  487 */     this.colDefMap = new HashMap<>();
/*  488 */     i = this.colDef.length;
/*      */     
/*  490 */     for (b = 0; b < i; b++) {
/*  491 */       this.colDefMap.put(this.colDef[b], Integer.valueOf(b));
/*      */     }
/*      */     
/*  494 */     this.dataMap = new HashMap<>();
/*  495 */     i = this.data.length;
/*      */     
/*  497 */     for (b = 0; b < i; b++) {
/*  498 */       this.dataMap.put(this.data[b], Integer.valueOf(b));
/*      */     }
/*      */ 
/*      */     
/*  502 */     this.typeMap = new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument() throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDocument() throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException {
/*      */     int i;
/*  544 */     String str = "";
/*      */     
/*  546 */     str = paramString2;
/*      */     
/*  548 */     switch (getState()) {
/*      */       
/*      */       case 1:
/*  551 */         this.tempCommand = "";
/*  552 */         i = ((Integer)this.propMap.get(str)).intValue();
/*  553 */         if (i == 17) {
/*  554 */           setNullValue(true);
/*      */         } else {
/*  556 */           setTag(i);
/*      */         }  return;
/*      */       case 2:
/*  559 */         i = ((Integer)this.colDefMap.get(str)).intValue();
/*      */         
/*  561 */         if (i == 19) {
/*  562 */           setNullValue(true);
/*      */         } else {
/*  564 */           setTag(i);
/*      */         } 
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  572 */         this.tempStr = "";
/*  573 */         this.tempUpdate = "";
/*  574 */         if (this.dataMap.get(str) == null) {
/*  575 */           i = 6;
/*  576 */         } else if (((Integer)this.dataMap.get(str)).intValue() == 7) {
/*  577 */           i = 7;
/*      */         } else {
/*  579 */           i = ((Integer)this.dataMap.get(str)).intValue();
/*      */         } 
/*      */         
/*  582 */         if (i == 6) {
/*  583 */           setNullValue(true);
/*  584 */         } else if (i == 7) {
/*  585 */           setEmptyStringValue(true);
/*      */         } else {
/*  587 */           setTag(i);
/*      */           
/*  589 */           if (i == 0 || i == 3 || i == 2) {
/*  590 */             this.idx = 0;
/*      */             try {
/*  592 */               this.rs.moveToInsertRow();
/*  593 */             } catch (SQLException sQLException) {}
/*      */           } 
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  601 */     setState(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String paramString1, String paramString2, String paramString3) throws SAXException {
/*      */     int i;
/*  637 */     String str = "";
/*  638 */     str = paramString2;
/*      */     
/*  640 */     switch (getState()) {
/*      */       case 1:
/*  642 */         if (str.equals("properties")) {
/*  643 */           this.state = 0;
/*      */           
/*      */           break;
/*      */         } 
/*      */         try {
/*  648 */           i = ((Integer)this.propMap.get(str)).intValue();
/*  649 */           switch (i) {
/*      */             case 7:
/*  651 */               if (this.keyCols != null) {
/*  652 */                 int[] arrayOfInt = new int[this.keyCols.size()];
/*  653 */                 for (byte b = 0; b < arrayOfInt.length; b++)
/*  654 */                   arrayOfInt[b] = Integer.parseInt((String)this.keyCols.elementAt(b)); 
/*  655 */                 this.rs.setKeyColumns(arrayOfInt);
/*      */               } 
/*      */               break;
/*      */ 
/*      */ 
/*      */             
/*      */             case 20:
/*      */               try {
/*  663 */                 this.typeMap.put(this.Key_map, ReflectUtil.forName(this.Value_map));
/*      */               }
/*  665 */               catch (ClassNotFoundException classNotFoundException) {
/*  666 */                 throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errmap").toString(), new Object[] { classNotFoundException.getMessage() }));
/*      */               } 
/*      */               break;
/*      */ 
/*      */             
/*      */             case 8:
/*  672 */               this.rs.setTypeMap(this.typeMap);
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  679 */           if (getNullValue()) {
/*  680 */             setPropertyValue(null);
/*  681 */             setNullValue(false);
/*      */           } else {
/*  683 */             setPropertyValue(this.propertyValue);
/*      */           } 
/*  685 */         } catch (SQLException sQLException) {
/*  686 */           throw new SAXException(sQLException.getMessage());
/*      */         } 
/*      */ 
/*      */         
/*  690 */         this.propertyValue = "";
/*  691 */         setTag(-1);
/*      */         break;
/*      */       case 2:
/*  694 */         if (str.equals("metadata")) {
/*      */           try {
/*  696 */             this.rs.setMetaData(this.md);
/*  697 */             this.state = 0;
/*  698 */           } catch (SQLException sQLException) {
/*  699 */             throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errmetadata").toString(), new Object[] { sQLException.getMessage() }));
/*      */           } 
/*      */         } else {
/*      */           try {
/*  703 */             if (getNullValue()) {
/*  704 */               setMetaDataValue(null);
/*  705 */               setNullValue(false);
/*      */             } else {
/*  707 */               setMetaDataValue(this.metaDataValue);
/*      */             } 
/*  709 */           } catch (SQLException sQLException) {
/*  710 */             throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errmetadata").toString(), new Object[] { sQLException.getMessage() }));
/*      */           } 
/*      */ 
/*      */           
/*  714 */           this.metaDataValue = "";
/*      */         } 
/*  716 */         setTag(-1);
/*      */         break;
/*      */       case 3:
/*  719 */         if (str.equals("data")) {
/*  720 */           this.state = 0;
/*      */           
/*      */           return;
/*      */         } 
/*  724 */         if (this.dataMap.get(str) == null) {
/*  725 */           i = 6;
/*      */         } else {
/*  727 */           i = ((Integer)this.dataMap.get(str)).intValue();
/*      */         } 
/*  729 */         switch (i) {
/*      */           case 1:
/*      */             try {
/*  732 */               this.idx++;
/*  733 */               if (getNullValue()) {
/*  734 */                 insertValue(null);
/*  735 */                 setNullValue(false);
/*      */               } else {
/*  737 */                 insertValue(this.tempStr);
/*      */               } 
/*      */               
/*  740 */               this.columnValue = "";
/*  741 */             } catch (SQLException sQLException) {
/*  742 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errinsertval").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */           case 0:
/*      */             try {
/*  747 */               this.rs.insertRow();
/*  748 */               this.rs.moveToCurrentRow();
/*  749 */               this.rs.next();
/*      */ 
/*      */ 
/*      */               
/*  753 */               this.rs.setOriginalRow();
/*      */               
/*  755 */               applyUpdates();
/*  756 */             } catch (SQLException sQLException) {
/*  757 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errconstr").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */           case 3:
/*      */             try {
/*  762 */               this.rs.insertRow();
/*  763 */               this.rs.moveToCurrentRow();
/*  764 */               this.rs.next();
/*  765 */               this.rs.setOriginalRow();
/*  766 */               applyUpdates();
/*  767 */               this.rs.deleteRow();
/*  768 */             } catch (SQLException sQLException) {
/*  769 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errdel").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */           case 2:
/*      */             try {
/*  774 */               this.rs.insertRow();
/*  775 */               this.rs.moveToCurrentRow();
/*  776 */               this.rs.next();
/*  777 */               applyUpdates();
/*  778 */             } catch (SQLException sQLException) {
/*  779 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errinsert").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 4:
/*      */             try {
/*  785 */               this.rs.insertRow();
/*  786 */               this.rs.moveToCurrentRow();
/*  787 */               this.rs.next();
/*  788 */               this.rs.setOriginalRow();
/*  789 */               applyUpdates();
/*  790 */             } catch (SQLException sQLException) {
/*  791 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errinsdel").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 5:
/*      */             try {
/*  797 */               if (getNullValue()) {
/*      */                 
/*  799 */                 insertValue(null);
/*  800 */                 setNullValue(false); break;
/*  801 */               }  if (getEmptyStringValue()) {
/*  802 */                 insertValue("");
/*  803 */                 setEmptyStringValue(false); break;
/*      */               } 
/*  805 */               this.updates.add(this.upd);
/*      */             }
/*  807 */             catch (SQLException sQLException) {
/*  808 */               throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errupdate").toString(), new Object[] { sQLException.getMessage() }));
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyUpdates() throws SAXException {
/*  822 */     if (this.updates.size() > 0) {
/*      */       
/*      */       try {
/*  825 */         Iterator<Object[]> iterator = this.updates.iterator();
/*  826 */         while (iterator.hasNext()) {
/*  827 */           Object[] arrayOfObject = iterator.next();
/*  828 */           this.idx = ((Integer)arrayOfObject[0]).intValue();
/*      */           
/*  830 */           if (!this.lastval.equals(arrayOfObject[1])) {
/*  831 */             insertValue((String)arrayOfObject[1]);
/*      */           }
/*      */         } 
/*      */         
/*  835 */         this.rs.updateRow();
/*  836 */       } catch (SQLException sQLException) {
/*  837 */         throw new SAXException(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.errupdrow").toString(), new Object[] { sQLException.getMessage() }));
/*      */       } 
/*  839 */       this.updates.removeAllElements();
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
/*      */   public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/*      */     try {
/*  863 */       switch (getState()) {
/*      */         case 1:
/*  865 */           this.propertyValue = new String(paramArrayOfchar, paramInt1, paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  874 */           this.tempCommand = this.tempCommand.concat(this.propertyValue);
/*  875 */           this.propertyValue = this.tempCommand;
/*      */ 
/*      */           
/*  878 */           if (this.tag == 19) {
/*      */             
/*  880 */             this.Key_map = this.propertyValue;
/*      */             
/*      */             break;
/*      */           } 
/*  884 */           if (this.tag == 20)
/*      */           {
/*  886 */             this.Value_map = this.propertyValue;
/*      */           }
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/*  897 */           if (this.tag == -1) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  902 */           this.metaDataValue = new String(paramArrayOfchar, paramInt1, paramInt2);
/*      */           break;
/*      */         case 3:
/*  905 */           setDataValue(paramArrayOfchar, paramInt1, paramInt2);
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*  910 */     } catch (SQLException sQLException) {
/*  911 */       throw new SAXException(this.resBundle.handleGetObject("xmlrch.chars").toString() + sQLException.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setState(String paramString) throws SAXException {
/*  916 */     if (paramString.equals("webRowSet")) {
/*  917 */       this.state = 0;
/*  918 */     } else if (paramString.equals("properties")) {
/*  919 */       if (this.state != 1)
/*  920 */       { this.state = 1; }
/*      */       else
/*  922 */       { this.state = 0; } 
/*  923 */     } else if (paramString.equals("metadata")) {
/*  924 */       if (this.state != 2)
/*  925 */       { this.state = 2; }
/*      */       else
/*  927 */       { this.state = 0; } 
/*  928 */     } else if (paramString.equals("data")) {
/*  929 */       if (this.state != 3) {
/*  930 */         this.state = 3;
/*      */       } else {
/*  932 */         this.state = 0;
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
/*      */   private int getState() {
/*  949 */     return this.state;
/*      */   }
/*      */   
/*      */   private void setTag(int paramInt) {
/*  953 */     this.tag = paramInt;
/*      */   }
/*      */   
/*      */   private int getTag() {
/*  957 */     return this.tag;
/*      */   }
/*      */   
/*      */   private void setNullValue(boolean paramBoolean) {
/*  961 */     this.nullVal = paramBoolean;
/*      */   }
/*      */   
/*      */   private boolean getNullValue() {
/*  965 */     return this.nullVal;
/*      */   }
/*      */   
/*      */   private void setEmptyStringValue(boolean paramBoolean) {
/*  969 */     this.emptyStringVal = paramBoolean;
/*      */   }
/*      */   
/*      */   private boolean getEmptyStringValue() {
/*  973 */     return this.emptyStringVal;
/*      */   }
/*      */   
/*      */   private String getStringValue(String paramString) {
/*  977 */     return paramString;
/*      */   }
/*      */   
/*      */   private int getIntegerValue(String paramString) {
/*  981 */     return Integer.parseInt(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean getBooleanValue(String paramString) {
/*  986 */     return Boolean.valueOf(paramString).booleanValue();
/*      */   }
/*      */   
/*      */   private BigDecimal getBigDecimalValue(String paramString) {
/*  990 */     return new BigDecimal(paramString);
/*      */   }
/*      */   
/*      */   private byte getByteValue(String paramString) {
/*  994 */     return Byte.parseByte(paramString);
/*      */   }
/*      */   
/*      */   private short getShortValue(String paramString) {
/*  998 */     return Short.parseShort(paramString);
/*      */   }
/*      */   
/*      */   private long getLongValue(String paramString) {
/* 1002 */     return Long.parseLong(paramString);
/*      */   }
/*      */   
/*      */   private float getFloatValue(String paramString) {
/* 1006 */     return Float.parseFloat(paramString);
/*      */   }
/*      */   
/*      */   private double getDoubleValue(String paramString) {
/* 1010 */     return Double.parseDouble(paramString);
/*      */   }
/*      */   
/*      */   private byte[] getBinaryValue(String paramString) {
/* 1014 */     return paramString.getBytes();
/*      */   }
/*      */   
/*      */   private Date getDateValue(String paramString) {
/* 1018 */     return new Date(getLongValue(paramString));
/*      */   }
/*      */   
/*      */   private Time getTimeValue(String paramString) {
/* 1022 */     return new Time(getLongValue(paramString));
/*      */   }
/*      */   
/*      */   private Timestamp getTimestampValue(String paramString) {
/* 1026 */     return new Timestamp(getLongValue(paramString));
/*      */   }
/*      */   private void setPropertyValue(String paramString) throws SQLException {
/*      */     String str;
/*      */     char c;
/* 1031 */     boolean bool = getNullValue();
/*      */     
/* 1033 */     switch (getTag()) {
/*      */       case 0:
/* 1035 */         if (bool) {
/*      */           break;
/*      */         }
/* 1038 */         this.rs.setCommand(paramString);
/*      */         break;
/*      */       case 1:
/* 1041 */         if (bool) {
/* 1042 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1044 */         this.rs.setConcurrency(getIntegerValue(paramString));
/*      */         break;
/*      */       case 2:
/* 1047 */         if (bool) {
/* 1048 */           this.rs.setDataSourceName(null); break;
/*      */         } 
/* 1050 */         this.rs.setDataSourceName(paramString);
/*      */         break;
/*      */       case 3:
/* 1053 */         if (bool) {
/* 1054 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1056 */         this.rs.setEscapeProcessing(getBooleanValue(paramString));
/*      */         break;
/*      */       case 4:
/* 1059 */         if (bool) {
/* 1060 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1062 */         this.rs.setFetchDirection(getIntegerValue(paramString));
/*      */         break;
/*      */       case 5:
/* 1065 */         if (bool) {
/* 1066 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1068 */         this.rs.setFetchSize(getIntegerValue(paramString));
/*      */         break;
/*      */       case 6:
/* 1071 */         if (bool) {
/* 1072 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1074 */         this.rs.setTransactionIsolation(getIntegerValue(paramString));
/*      */         break;
/*      */ 
/*      */       
/*      */       case 18:
/* 1079 */         if (this.keyCols == null)
/* 1080 */           this.keyCols = new Vector<>(); 
/* 1081 */         this.keyCols.add(paramString);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 9:
/* 1086 */         if (bool) {
/* 1087 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1089 */         this.rs.setMaxFieldSize(getIntegerValue(paramString));
/*      */         break;
/*      */       case 10:
/* 1092 */         if (bool) {
/* 1093 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1095 */         this.rs.setMaxRows(getIntegerValue(paramString));
/*      */         break;
/*      */       case 11:
/* 1098 */         if (bool) {
/* 1099 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1101 */         this.rs.setQueryTimeout(getIntegerValue(paramString));
/*      */         break;
/*      */       case 12:
/* 1104 */         if (bool) {
/* 1105 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1107 */         this.rs.setReadOnly(getBooleanValue(paramString));
/*      */         break;
/*      */       case 13:
/* 1110 */         if (bool) {
/* 1111 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/*      */         
/* 1114 */         str = getStringValue(paramString);
/* 1115 */         c = Character.MIN_VALUE;
/*      */         
/* 1117 */         if (str.trim().equals("ResultSet.TYPE_SCROLL_INSENSITIVE")) {
/* 1118 */           c = 'Ϭ';
/* 1119 */         } else if (str.trim().equals("ResultSet.TYPE_SCROLL_SENSITIVE")) {
/* 1120 */           c = 'ϭ';
/* 1121 */         } else if (str.trim().equals("ResultSet.TYPE_FORWARD_ONLY")) {
/* 1122 */           c = 'ϫ';
/*      */         } 
/* 1124 */         this.rs.setType(c);
/*      */         break;
/*      */       
/*      */       case 14:
/* 1128 */         if (bool) {
/* 1129 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue").toString());
/*      */         }
/* 1131 */         this.rs.setShowDeleted(getBooleanValue(paramString));
/*      */         break;
/*      */       case 15:
/* 1134 */         if (bool) {
/*      */           break;
/*      */         }
/*      */         
/* 1138 */         this.rs.setTableName(paramString);
/*      */         break;
/*      */       case 16:
/* 1141 */         if (bool) {
/* 1142 */           this.rs.setUrl(null); break;
/*      */         } 
/* 1144 */         this.rs.setUrl(paramString);
/*      */         break;
/*      */       case 22:
/* 1147 */         if (bool) {
/* 1148 */           this.rs.setSyncProvider((String)null); break;
/*      */         } 
/* 1150 */         str = paramString.substring(0, paramString.indexOf("@") + 1);
/* 1151 */         this.rs.setSyncProvider(str);
/*      */         break;
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
/*      */   private void setMetaDataValue(String paramString) throws SQLException {
/* 1174 */     boolean bool = getNullValue();
/*      */     
/* 1176 */     switch (getTag()) {
/*      */       case 0:
/* 1178 */         this.md = new RowSetMetaDataImpl();
/* 1179 */         this.idx = 0;
/*      */         
/* 1181 */         if (bool) {
/* 1182 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1184 */         this.md.setColumnCount(getIntegerValue(paramString));
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1190 */         this.idx++;
/*      */         break;
/*      */       case 3:
/* 1193 */         if (bool) {
/* 1194 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1196 */         this.md.setAutoIncrement(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 4:
/* 1199 */         if (bool) {
/* 1200 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1202 */         this.md.setCaseSensitive(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 5:
/* 1205 */         if (bool) {
/* 1206 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1208 */         this.md.setCurrency(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 6:
/* 1211 */         if (bool) {
/* 1212 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1214 */         this.md.setNullable(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case 7:
/* 1217 */         if (bool) {
/* 1218 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1220 */         this.md.setSigned(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 8:
/* 1223 */         if (bool) {
/* 1224 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1226 */         this.md.setSearchable(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 9:
/* 1229 */         if (bool) {
/* 1230 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1232 */         this.md.setColumnDisplaySize(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case 10:
/* 1235 */         if (bool) {
/* 1236 */           this.md.setColumnLabel(this.idx, null); break;
/*      */         } 
/* 1238 */         this.md.setColumnLabel(this.idx, paramString);
/*      */         break;
/*      */       case 11:
/* 1241 */         if (bool) {
/* 1242 */           this.md.setColumnName(this.idx, null); break;
/*      */         } 
/* 1244 */         this.md.setColumnName(this.idx, paramString);
/*      */         break;
/*      */       
/*      */       case 12:
/* 1248 */         if (bool) {
/* 1249 */           this.md.setSchemaName(this.idx, null); break;
/*      */         } 
/* 1251 */         this.md.setSchemaName(this.idx, paramString);
/*      */         break;
/*      */       case 13:
/* 1254 */         if (bool) {
/* 1255 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1257 */         this.md.setPrecision(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case 14:
/* 1260 */         if (bool) {
/* 1261 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1263 */         this.md.setScale(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case 15:
/* 1266 */         if (bool) {
/* 1267 */           this.md.setTableName(this.idx, null); break;
/*      */         } 
/* 1269 */         this.md.setTableName(this.idx, paramString);
/*      */         break;
/*      */       case 16:
/* 1272 */         if (bool) {
/* 1273 */           this.md.setCatalogName(this.idx, null); break;
/*      */         } 
/* 1275 */         this.md.setCatalogName(this.idx, paramString);
/*      */         break;
/*      */       case 17:
/* 1278 */         if (bool) {
/* 1279 */           throw new SQLException(this.resBundle.handleGetObject("xmlrch.badvalue1").toString());
/*      */         }
/* 1281 */         this.md.setColumnType(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case 18:
/* 1284 */         if (bool) {
/* 1285 */           this.md.setColumnTypeName(this.idx, null); break;
/*      */         } 
/* 1287 */         this.md.setColumnTypeName(this.idx, paramString);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDataValue(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SQLException {
/* 1297 */     switch (getTag()) {
/*      */       case 1:
/* 1299 */         this.columnValue = new String(paramArrayOfchar, paramInt1, paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1307 */         this.tempStr = this.tempStr.concat(this.columnValue);
/*      */         break;
/*      */       case 5:
/* 1310 */         this.upd = new Object[2];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1320 */         this.tempUpdate = this.tempUpdate.concat(new String(paramArrayOfchar, paramInt1, paramInt2));
/* 1321 */         this.upd[0] = Integer.valueOf(this.idx);
/* 1322 */         this.upd[1] = this.tempUpdate;
/*      */ 
/*      */         
/* 1325 */         this.lastval = (String)this.upd[1];
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertValue(String paramString) throws SQLException {
/* 1335 */     if (getNullValue()) {
/* 1336 */       this.rs.updateNull(this.idx);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1341 */     int i = this.rs.getMetaData().getColumnType(this.idx);
/* 1342 */     switch (i) {
/*      */       case -7:
/* 1344 */         this.rs.updateBoolean(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case 16:
/* 1347 */         this.rs.updateBoolean(this.idx, getBooleanValue(paramString));
/*      */         break;
/*      */       case -6:
/*      */       case 5:
/* 1351 */         this.rs.updateShort(this.idx, getShortValue(paramString));
/*      */         break;
/*      */       case 4:
/* 1354 */         this.rs.updateInt(this.idx, getIntegerValue(paramString));
/*      */         break;
/*      */       case -5:
/* 1357 */         this.rs.updateLong(this.idx, getLongValue(paramString));
/*      */         break;
/*      */       case 6:
/*      */       case 7:
/* 1361 */         this.rs.updateFloat(this.idx, getFloatValue(paramString));
/*      */         break;
/*      */       case 8:
/* 1364 */         this.rs.updateDouble(this.idx, getDoubleValue(paramString));
/*      */         break;
/*      */       case 2:
/*      */       case 3:
/* 1368 */         this.rs.updateObject(this.idx, getBigDecimalValue(paramString));
/*      */         break;
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 1373 */         this.rs.updateBytes(this.idx, getBinaryValue(paramString));
/*      */         break;
/*      */       case 91:
/* 1376 */         this.rs.updateDate(this.idx, getDateValue(paramString));
/*      */         break;
/*      */       case 92:
/* 1379 */         this.rs.updateTime(this.idx, getTimeValue(paramString));
/*      */         break;
/*      */       case 93:
/* 1382 */         this.rs.updateTimestamp(this.idx, getTimestampValue(paramString));
/*      */         break;
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/* 1387 */         this.rs.updateString(this.idx, getStringValue(paramString));
/*      */         break;
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
/*      */   public void error(SAXParseException paramSAXParseException) throws SAXParseException {
/* 1403 */     throw paramSAXParseException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warning(SAXParseException paramSAXParseException) throws SAXParseException {
/* 1415 */     System.out.println(MessageFormat.format(this.resBundle.handleGetObject("xmlrch.warning").toString(), new Object[] { paramSAXParseException.getMessage(), Integer.valueOf(paramSAXParseException.getLineNumber()), paramSAXParseException.getSystemId() }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notationDecl(String paramString1, String paramString2, String paramString3) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unparsedEntityDecl(String paramString1, String paramString2, String paramString3, String paramString4) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Row getPresentRow(WebRowSetImpl paramWebRowSetImpl) throws SQLException {
/* 1448 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/XmlReaderContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */