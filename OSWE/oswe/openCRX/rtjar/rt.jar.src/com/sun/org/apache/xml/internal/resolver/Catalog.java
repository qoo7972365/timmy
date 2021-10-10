/*      */ package com.sun.org.apache.xml.internal.resolver;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;
/*      */ import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
/*      */ import com.sun.org.apache.xml.internal.resolver.readers.CatalogReader;
/*      */ import com.sun.org.apache.xml.internal.resolver.readers.SAXCatalogReader;
/*      */ import com.sun.org.apache.xml.internal.resolver.readers.TR9401CatalogReader;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.SAXParserFactory;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Catalog
/*      */ {
/*  194 */   public static final int BASE = CatalogEntry.addEntryType("BASE", 1);
/*      */ 
/*      */   
/*  197 */   public static final int CATALOG = CatalogEntry.addEntryType("CATALOG", 1);
/*      */ 
/*      */   
/*  200 */   public static final int DOCUMENT = CatalogEntry.addEntryType("DOCUMENT", 1);
/*      */ 
/*      */   
/*  203 */   public static final int OVERRIDE = CatalogEntry.addEntryType("OVERRIDE", 1);
/*      */ 
/*      */   
/*  206 */   public static final int SGMLDECL = CatalogEntry.addEntryType("SGMLDECL", 1);
/*      */ 
/*      */   
/*  209 */   public static final int DELEGATE_PUBLIC = CatalogEntry.addEntryType("DELEGATE_PUBLIC", 2);
/*      */ 
/*      */   
/*  212 */   public static final int DELEGATE_SYSTEM = CatalogEntry.addEntryType("DELEGATE_SYSTEM", 2);
/*      */ 
/*      */   
/*  215 */   public static final int DELEGATE_URI = CatalogEntry.addEntryType("DELEGATE_URI", 2);
/*      */ 
/*      */   
/*  218 */   public static final int DOCTYPE = CatalogEntry.addEntryType("DOCTYPE", 2);
/*      */ 
/*      */   
/*  221 */   public static final int DTDDECL = CatalogEntry.addEntryType("DTDDECL", 2);
/*      */ 
/*      */   
/*  224 */   public static final int ENTITY = CatalogEntry.addEntryType("ENTITY", 2);
/*      */ 
/*      */   
/*  227 */   public static final int LINKTYPE = CatalogEntry.addEntryType("LINKTYPE", 2);
/*      */ 
/*      */   
/*  230 */   public static final int NOTATION = CatalogEntry.addEntryType("NOTATION", 2);
/*      */ 
/*      */   
/*  233 */   public static final int PUBLIC = CatalogEntry.addEntryType("PUBLIC", 2);
/*      */ 
/*      */   
/*  236 */   public static final int SYSTEM = CatalogEntry.addEntryType("SYSTEM", 2);
/*      */ 
/*      */   
/*  239 */   public static final int URI = CatalogEntry.addEntryType("URI", 2);
/*      */ 
/*      */   
/*  242 */   public static final int REWRITE_SYSTEM = CatalogEntry.addEntryType("REWRITE_SYSTEM", 2);
/*      */ 
/*      */   
/*  245 */   public static final int REWRITE_URI = CatalogEntry.addEntryType("REWRITE_URI", 2);
/*      */   
/*  247 */   public static final int SYSTEM_SUFFIX = CatalogEntry.addEntryType("SYSTEM_SUFFIX", 2);
/*      */   
/*  249 */   public static final int URI_SUFFIX = CatalogEntry.addEntryType("URI_SUFFIX", 2);
/*      */ 
/*      */ 
/*      */   
/*      */   protected URL base;
/*      */ 
/*      */ 
/*      */   
/*      */   protected URL catalogCwd;
/*      */ 
/*      */ 
/*      */   
/*  261 */   protected Vector catalogEntries = new Vector();
/*      */ 
/*      */   
/*      */   protected boolean default_override = true;
/*      */ 
/*      */   
/*  267 */   protected CatalogManager catalogManager = CatalogManager.getStaticManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  280 */   protected Vector catalogFiles = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  299 */   protected Vector localCatalogFiles = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  318 */   protected Vector catalogs = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  335 */   protected Vector localDelegate = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  344 */   protected Map<String, Integer> readerMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  354 */   protected Vector readerArr = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Catalog() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Catalog(CatalogManager manager) {
/*  375 */     this.catalogManager = manager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CatalogManager getCatalogManager() {
/*  383 */     return this.catalogManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCatalogManager(CatalogManager manager) {
/*  391 */     this.catalogManager = manager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupReaders() {
/*  398 */     SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/*  399 */     spf.setValidating(false);
/*      */     
/*  401 */     SAXCatalogReader saxReader = new SAXCatalogReader(spf);
/*      */     
/*  403 */     saxReader.setCatalogParser(null, "XMLCatalog", "com.sun.org.apache.xml.internal.resolver.readers.XCatalogReader");
/*      */ 
/*      */     
/*  406 */     saxReader.setCatalogParser("urn:oasis:names:tc:entity:xmlns:xml:catalog", "catalog", "com.sun.org.apache.xml.internal.resolver.readers.OASISXMLCatalogReader");
/*      */ 
/*      */ 
/*      */     
/*  410 */     addReader("application/xml", (CatalogReader)saxReader);
/*      */     
/*  412 */     TR9401CatalogReader textReader = new TR9401CatalogReader();
/*  413 */     addReader("text/plain", (CatalogReader)textReader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReader(String mimeType, CatalogReader reader) {
/*  437 */     if (this.readerMap.containsKey(mimeType)) {
/*  438 */       Integer pos = this.readerMap.get(mimeType);
/*  439 */       this.readerArr.set(pos.intValue(), reader);
/*      */     } else {
/*  441 */       this.readerArr.add(reader);
/*  442 */       Integer pos = Integer.valueOf(this.readerArr.size() - 1);
/*  443 */       this.readerMap.put(mimeType, pos);
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
/*      */   protected void copyReaders(Catalog newCatalog) {
/*  458 */     Vector<String> mapArr = new Vector(this.readerMap.size());
/*      */     
/*      */     int count;
/*  461 */     for (count = 0; count < this.readerMap.size(); count++) {
/*  462 */       mapArr.add(null);
/*      */     }
/*      */     
/*  465 */     for (Map.Entry<String, Integer> entry : this.readerMap.entrySet()) {
/*  466 */       mapArr.set(((Integer)entry.getValue()).intValue(), entry.getKey());
/*      */     }
/*      */     
/*  469 */     for (count = 0; count < mapArr.size(); count++) {
/*  470 */       String mimeType = mapArr.get(count);
/*  471 */       Integer pos = this.readerMap.get(mimeType);
/*  472 */       newCatalog.addReader(mimeType, this.readerArr
/*      */           
/*  474 */           .get(pos.intValue()));
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
/*      */   protected Catalog newCatalog() {
/*  491 */     String catalogClass = getClass().getName();
/*      */     
/*      */     try {
/*  494 */       Catalog catalog = (Catalog)Class.forName(catalogClass).newInstance();
/*  495 */       catalog.setCatalogManager(this.catalogManager);
/*  496 */       copyReaders(catalog);
/*  497 */       return catalog;
/*  498 */     } catch (ClassNotFoundException cnfe) {
/*  499 */       this.catalogManager.debug.message(1, "Class Not Found Exception: " + catalogClass);
/*  500 */     } catch (IllegalAccessException iae) {
/*  501 */       this.catalogManager.debug.message(1, "Illegal Access Exception: " + catalogClass);
/*  502 */     } catch (InstantiationException ie) {
/*  503 */       this.catalogManager.debug.message(1, "Instantiation Exception: " + catalogClass);
/*  504 */     } catch (ClassCastException cce) {
/*  505 */       this.catalogManager.debug.message(1, "Class Cast Exception: " + catalogClass);
/*  506 */     } catch (Exception e) {
/*  507 */       this.catalogManager.debug.message(1, "Other Exception: " + catalogClass);
/*      */     } 
/*      */     
/*  510 */     Catalog c = new Catalog();
/*  511 */     c.setCatalogManager(this.catalogManager);
/*  512 */     copyReaders(c);
/*  513 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCurrentBase() {
/*  520 */     return this.base.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultOverride() {
/*  531 */     if (this.default_override) {
/*  532 */       return "yes";
/*      */     }
/*  534 */     return "no";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadSystemCatalogs() throws MalformedURLException, IOException {
/*  552 */     Vector catalogs = this.catalogManager.getCatalogFiles();
/*  553 */     if (catalogs != null) {
/*  554 */       for (int count = 0; count < catalogs.size(); count++) {
/*  555 */         this.catalogFiles.addElement(catalogs.elementAt(count));
/*      */       }
/*      */     }
/*      */     
/*  559 */     if (this.catalogFiles.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  572 */       String catfile = this.catalogFiles.lastElement();
/*  573 */       this.catalogFiles.removeElement(catfile);
/*  574 */       parseCatalog(catfile);
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
/*      */   public synchronized void parseCatalog(String fileName) throws MalformedURLException, IOException {
/*  590 */     this.default_override = this.catalogManager.getPreferPublic();
/*  591 */     this.catalogManager.debug.message(4, "Parse catalog: " + fileName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  596 */     this.catalogFiles.addElement(fileName);
/*      */ 
/*      */     
/*  599 */     parsePendingCatalogs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void parseCatalog(String mimeType, InputStream is) throws IOException, CatalogException {
/*  618 */     this.default_override = this.catalogManager.getPreferPublic();
/*  619 */     this.catalogManager.debug.message(4, "Parse " + mimeType + " catalog on input stream");
/*      */     
/*  621 */     CatalogReader reader = null;
/*      */     
/*  623 */     if (this.readerMap.containsKey(mimeType)) {
/*  624 */       int arrayPos = ((Integer)this.readerMap.get(mimeType)).intValue();
/*  625 */       reader = this.readerArr.get(arrayPos);
/*      */     } 
/*      */     
/*  628 */     if (reader == null) {
/*  629 */       String msg = "No CatalogReader for MIME type: " + mimeType;
/*  630 */       this.catalogManager.debug.message(2, msg);
/*  631 */       throw new CatalogException(6, msg);
/*      */     } 
/*      */     
/*  634 */     reader.readCatalog(this, is);
/*      */ 
/*      */     
/*  637 */     parsePendingCatalogs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void parseCatalog(URL aUrl) throws IOException {
/*  656 */     this.catalogCwd = aUrl;
/*  657 */     this.base = aUrl;
/*      */     
/*  659 */     this.default_override = this.catalogManager.getPreferPublic();
/*  660 */     this.catalogManager.debug.message(4, "Parse catalog: " + aUrl.toString());
/*      */     
/*  662 */     DataInputStream inStream = null;
/*  663 */     boolean parsed = false;
/*      */     
/*  665 */     for (int count = 0; !parsed && count < this.readerArr.size(); count++) {
/*  666 */       CatalogReader reader = this.readerArr.get(count);
/*      */       
/*      */       try {
/*  669 */         inStream = new DataInputStream(aUrl.openStream());
/*  670 */       } catch (FileNotFoundException fnfe) {
/*      */         break;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  676 */         reader.readCatalog(this, inStream);
/*  677 */         parsed = true;
/*  678 */       } catch (CatalogException ce) {
/*  679 */         if (ce.getExceptionType() == 7) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  688 */         inStream.close();
/*  689 */       } catch (IOException iOException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  694 */     if (parsed) parsePendingCatalogs();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void parsePendingCatalogs() throws MalformedURLException, IOException {
/*  706 */     if (!this.localCatalogFiles.isEmpty()) {
/*      */ 
/*      */       
/*  709 */       Vector<String> newQueue = new Vector();
/*  710 */       Enumeration q = this.localCatalogFiles.elements();
/*  711 */       while (q.hasMoreElements()) {
/*  712 */         newQueue.addElement(q.nextElement());
/*      */       }
/*      */ 
/*      */       
/*  716 */       for (int curCat = 0; curCat < this.catalogFiles.size(); curCat++) {
/*  717 */         String catfile = this.catalogFiles.elementAt(curCat);
/*  718 */         newQueue.addElement(catfile);
/*      */       } 
/*      */       
/*  721 */       this.catalogFiles = newQueue;
/*  722 */       this.localCatalogFiles.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  728 */     if (this.catalogFiles.isEmpty() && !this.localDelegate.isEmpty()) {
/*  729 */       Enumeration e = this.localDelegate.elements();
/*  730 */       while (e.hasMoreElements()) {
/*  731 */         this.catalogEntries.addElement(e.nextElement());
/*      */       }
/*  733 */       this.localDelegate.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  739 */     while (!this.catalogFiles.isEmpty()) {
/*  740 */       String catfile = this.catalogFiles.elementAt(0);
/*      */       try {
/*  742 */         this.catalogFiles.remove(0);
/*  743 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */ 
/*      */ 
/*      */       
/*  747 */       if (this.catalogEntries.size() == 0 && this.catalogs.size() == 0) {
/*      */ 
/*      */         
/*      */         try {
/*  751 */           parseCatalogFile(catfile);
/*  752 */         } catch (CatalogException ce) {
/*  753 */           System.out.println("FIXME: " + ce.toString());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  758 */         this.catalogs.addElement(catfile);
/*      */       } 
/*      */       
/*  761 */       if (!this.localCatalogFiles.isEmpty()) {
/*      */ 
/*      */         
/*  764 */         Vector<String> newQueue = new Vector();
/*  765 */         Enumeration q = this.localCatalogFiles.elements();
/*  766 */         while (q.hasMoreElements()) {
/*  767 */           newQueue.addElement(q.nextElement());
/*      */         }
/*      */ 
/*      */         
/*  771 */         for (int curCat = 0; curCat < this.catalogFiles.size(); curCat++) {
/*  772 */           catfile = this.catalogFiles.elementAt(curCat);
/*  773 */           newQueue.addElement(catfile);
/*      */         } 
/*      */         
/*  776 */         this.catalogFiles = newQueue;
/*  777 */         this.localCatalogFiles.clear();
/*      */       } 
/*      */       
/*  780 */       if (!this.localDelegate.isEmpty()) {
/*  781 */         Enumeration e = this.localDelegate.elements();
/*  782 */         while (e.hasMoreElements()) {
/*  783 */           this.catalogEntries.addElement(e.nextElement());
/*      */         }
/*  785 */         this.localDelegate.clear();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  790 */     this.catalogFiles.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void parseCatalogFile(String fileName) throws MalformedURLException, IOException, CatalogException {
/*      */     try {
/*  812 */       this.catalogCwd = FileURL.makeURL("basename");
/*  813 */     } catch (MalformedURLException e) {
/*  814 */       this.catalogManager.debug.message(1, "Malformed URL on cwd", "user.dir");
/*  815 */       this.catalogCwd = null;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  820 */       this.base = new URL(this.catalogCwd, fixSlashes(fileName));
/*  821 */     } catch (MalformedURLException e) {
/*      */       try {
/*  823 */         this.base = new URL("file:" + fixSlashes(fileName));
/*  824 */       } catch (MalformedURLException e2) {
/*  825 */         this.catalogManager.debug.message(1, "Malformed URL on catalog filename", 
/*  826 */             fixSlashes(fileName));
/*  827 */         this.base = null;
/*      */       } 
/*      */     } 
/*      */     
/*  831 */     this.catalogManager.debug.message(2, "Loading catalog", fileName);
/*  832 */     this.catalogManager.debug.message(4, "Default BASE", this.base.toString());
/*      */     
/*  834 */     fileName = this.base.toString();
/*      */     
/*  836 */     DataInputStream inStream = null;
/*  837 */     boolean parsed = false;
/*  838 */     boolean notFound = false;
/*      */     
/*  840 */     for (int count = 0; !parsed && count < this.readerArr.size(); count++) {
/*  841 */       CatalogReader reader = this.readerArr.get(count);
/*      */       
/*      */       try {
/*  844 */         notFound = false;
/*  845 */         inStream = new DataInputStream(this.base.openStream());
/*  846 */       } catch (FileNotFoundException fnfe) {
/*      */         
/*  848 */         notFound = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */       try {
/*  853 */         reader.readCatalog(this, inStream);
/*  854 */         parsed = true;
/*  855 */       } catch (CatalogException ce) {
/*  856 */         if (ce.getExceptionType() == 7) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  865 */         inStream.close();
/*  866 */       } catch (IOException iOException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  871 */     if (!parsed) {
/*  872 */       if (notFound) {
/*  873 */         this.catalogManager.debug.message(3, "Catalog does not exist", fileName);
/*      */       } else {
/*  875 */         this.catalogManager.debug.message(1, "Failed to parse catalog", fileName);
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
/*      */   public void addEntry(CatalogEntry entry) {
/*  891 */     int type = entry.getEntryType();
/*      */     
/*  893 */     if (type == BASE) {
/*  894 */       String value = entry.getEntryArg(0);
/*  895 */       URL newbase = null;
/*      */       
/*  897 */       if (this.base == null) {
/*  898 */         this.catalogManager.debug.message(5, "BASE CUR", "null");
/*      */       } else {
/*  900 */         this.catalogManager.debug.message(5, "BASE CUR", this.base.toString());
/*      */       } 
/*  902 */       this.catalogManager.debug.message(4, "BASE STR", value);
/*      */       
/*      */       try {
/*  905 */         value = fixSlashes(value);
/*  906 */         newbase = new URL(this.base, value);
/*  907 */       } catch (MalformedURLException e) {
/*      */         try {
/*  909 */           newbase = new URL("file:" + value);
/*  910 */         } catch (MalformedURLException e2) {
/*  911 */           this.catalogManager.debug.message(1, "Malformed URL on base", value);
/*  912 */           newbase = null;
/*      */         } 
/*      */       } 
/*      */       
/*  916 */       if (newbase != null) {
/*  917 */         this.base = newbase;
/*      */       }
/*      */       
/*  920 */       this.catalogManager.debug.message(5, "BASE NEW", this.base.toString());
/*  921 */     } else if (type == CATALOG) {
/*  922 */       String fsi = makeAbsolute(entry.getEntryArg(0));
/*      */       
/*  924 */       this.catalogManager.debug.message(4, "CATALOG", fsi);
/*      */       
/*  926 */       this.localCatalogFiles.addElement(fsi);
/*  927 */     } else if (type == PUBLIC) {
/*  928 */       String publicid = PublicId.normalize(entry.getEntryArg(0));
/*  929 */       String systemid = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  931 */       entry.setEntryArg(0, publicid);
/*  932 */       entry.setEntryArg(1, systemid);
/*      */       
/*  934 */       this.catalogManager.debug.message(4, "PUBLIC", publicid, systemid);
/*      */       
/*  936 */       this.catalogEntries.addElement(entry);
/*  937 */     } else if (type == SYSTEM) {
/*  938 */       String systemid = normalizeURI(entry.getEntryArg(0));
/*  939 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  941 */       entry.setEntryArg(1, fsi);
/*      */       
/*  943 */       this.catalogManager.debug.message(4, "SYSTEM", systemid, fsi);
/*      */       
/*  945 */       this.catalogEntries.addElement(entry);
/*  946 */     } else if (type == URI) {
/*  947 */       String uri = normalizeURI(entry.getEntryArg(0));
/*  948 */       String altURI = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  950 */       entry.setEntryArg(1, altURI);
/*      */       
/*  952 */       this.catalogManager.debug.message(4, "URI", uri, altURI);
/*      */       
/*  954 */       this.catalogEntries.addElement(entry);
/*  955 */     } else if (type == DOCUMENT) {
/*  956 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(0)));
/*  957 */       entry.setEntryArg(0, fsi);
/*      */       
/*  959 */       this.catalogManager.debug.message(4, "DOCUMENT", fsi);
/*      */       
/*  961 */       this.catalogEntries.addElement(entry);
/*  962 */     } else if (type == OVERRIDE) {
/*  963 */       this.catalogManager.debug.message(4, "OVERRIDE", entry.getEntryArg(0));
/*      */       
/*  965 */       this.catalogEntries.addElement(entry);
/*  966 */     } else if (type == SGMLDECL) {
/*      */       
/*  968 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(0)));
/*  969 */       entry.setEntryArg(0, fsi);
/*      */       
/*  971 */       this.catalogManager.debug.message(4, "SGMLDECL", fsi);
/*      */       
/*  973 */       this.catalogEntries.addElement(entry);
/*  974 */     } else if (type == DELEGATE_PUBLIC) {
/*  975 */       String ppi = PublicId.normalize(entry.getEntryArg(0));
/*  976 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  978 */       entry.setEntryArg(0, ppi);
/*  979 */       entry.setEntryArg(1, fsi);
/*      */       
/*  981 */       this.catalogManager.debug.message(4, "DELEGATE_PUBLIC", ppi, fsi);
/*      */       
/*  983 */       addDelegate(entry);
/*  984 */     } else if (type == DELEGATE_SYSTEM) {
/*  985 */       String psi = normalizeURI(entry.getEntryArg(0));
/*  986 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  988 */       entry.setEntryArg(0, psi);
/*  989 */       entry.setEntryArg(1, fsi);
/*      */       
/*  991 */       this.catalogManager.debug.message(4, "DELEGATE_SYSTEM", psi, fsi);
/*      */       
/*  993 */       addDelegate(entry);
/*  994 */     } else if (type == DELEGATE_URI) {
/*  995 */       String pui = normalizeURI(entry.getEntryArg(0));
/*  996 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/*  998 */       entry.setEntryArg(0, pui);
/*  999 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1001 */       this.catalogManager.debug.message(4, "DELEGATE_URI", pui, fsi);
/*      */       
/* 1003 */       addDelegate(entry);
/* 1004 */     } else if (type == REWRITE_SYSTEM) {
/* 1005 */       String psi = normalizeURI(entry.getEntryArg(0));
/* 1006 */       String rpx = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/* 1008 */       entry.setEntryArg(0, psi);
/* 1009 */       entry.setEntryArg(1, rpx);
/*      */       
/* 1011 */       this.catalogManager.debug.message(4, "REWRITE_SYSTEM", psi, rpx);
/*      */       
/* 1013 */       this.catalogEntries.addElement(entry);
/* 1014 */     } else if (type == REWRITE_URI) {
/* 1015 */       String pui = normalizeURI(entry.getEntryArg(0));
/* 1016 */       String upx = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/* 1018 */       entry.setEntryArg(0, pui);
/* 1019 */       entry.setEntryArg(1, upx);
/*      */       
/* 1021 */       this.catalogManager.debug.message(4, "REWRITE_URI", pui, upx);
/*      */       
/* 1023 */       this.catalogEntries.addElement(entry);
/* 1024 */     } else if (type == SYSTEM_SUFFIX) {
/* 1025 */       String pui = normalizeURI(entry.getEntryArg(0));
/* 1026 */       String upx = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/* 1028 */       entry.setEntryArg(0, pui);
/* 1029 */       entry.setEntryArg(1, upx);
/*      */       
/* 1031 */       this.catalogManager.debug.message(4, "SYSTEM_SUFFIX", pui, upx);
/*      */       
/* 1033 */       this.catalogEntries.addElement(entry);
/* 1034 */     } else if (type == URI_SUFFIX) {
/* 1035 */       String pui = normalizeURI(entry.getEntryArg(0));
/* 1036 */       String upx = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*      */       
/* 1038 */       entry.setEntryArg(0, pui);
/* 1039 */       entry.setEntryArg(1, upx);
/*      */       
/* 1041 */       this.catalogManager.debug.message(4, "URI_SUFFIX", pui, upx);
/*      */       
/* 1043 */       this.catalogEntries.addElement(entry);
/* 1044 */     } else if (type == DOCTYPE) {
/* 1045 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/* 1046 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1048 */       this.catalogManager.debug.message(4, "DOCTYPE", entry.getEntryArg(0), fsi);
/*      */       
/* 1050 */       this.catalogEntries.addElement(entry);
/* 1051 */     } else if (type == DTDDECL) {
/*      */       
/* 1053 */       String fpi = PublicId.normalize(entry.getEntryArg(0));
/* 1054 */       entry.setEntryArg(0, fpi);
/* 1055 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/* 1056 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1058 */       this.catalogManager.debug.message(4, "DTDDECL", fpi, fsi);
/*      */       
/* 1060 */       this.catalogEntries.addElement(entry);
/* 1061 */     } else if (type == ENTITY) {
/* 1062 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/* 1063 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1065 */       this.catalogManager.debug.message(4, "ENTITY", entry.getEntryArg(0), fsi);
/*      */       
/* 1067 */       this.catalogEntries.addElement(entry);
/* 1068 */     } else if (type == LINKTYPE) {
/*      */       
/* 1070 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/* 1071 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1073 */       this.catalogManager.debug.message(4, "LINKTYPE", entry.getEntryArg(0), fsi);
/*      */       
/* 1075 */       this.catalogEntries.addElement(entry);
/* 1076 */     } else if (type == NOTATION) {
/* 1077 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/* 1078 */       entry.setEntryArg(1, fsi);
/*      */       
/* 1080 */       this.catalogManager.debug.message(4, "NOTATION", entry.getEntryArg(0), fsi);
/*      */       
/* 1082 */       this.catalogEntries.addElement(entry);
/*      */     } else {
/* 1084 */       this.catalogEntries.addElement(entry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unknownEntry(Vector<String> strings) {
/* 1095 */     if (strings != null && strings.size() > 0) {
/* 1096 */       String keyword = strings.elementAt(0);
/* 1097 */       this.catalogManager.debug.message(2, "Unrecognized token parsing catalog", keyword);
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
/*      */   public void parseAllCatalogs() throws MalformedURLException, IOException {
/* 1134 */     for (int catPos = 0; catPos < this.catalogs.size(); catPos++) {
/* 1135 */       Catalog c = null;
/*      */       
/*      */       try {
/* 1138 */         c = this.catalogs.elementAt(catPos);
/* 1139 */       } catch (ClassCastException e) {
/* 1140 */         String catfile = this.catalogs.elementAt(catPos);
/* 1141 */         c = newCatalog();
/*      */         
/* 1143 */         c.parseCatalog(catfile);
/* 1144 */         this.catalogs.setElementAt(c, catPos);
/* 1145 */         c.parseAllCatalogs();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1150 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1151 */     while (en.hasMoreElements()) {
/* 1152 */       CatalogEntry e = en.nextElement();
/* 1153 */       if (e.getEntryType() == DELEGATE_PUBLIC || e
/* 1154 */         .getEntryType() == DELEGATE_SYSTEM || e
/* 1155 */         .getEntryType() == DELEGATE_URI) {
/* 1156 */         Catalog dcat = newCatalog();
/* 1157 */         dcat.parseCatalog(e.getEntryArg(1));
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
/*      */   public String resolveDoctype(String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 1183 */     String resolved = null;
/*      */     
/* 1185 */     this.catalogManager.debug.message(3, "resolveDoctype(" + entityName + "," + publicId + "," + systemId + ")");
/*      */ 
/*      */     
/* 1188 */     systemId = normalizeURI(systemId);
/*      */     
/* 1190 */     if (publicId != null && publicId.startsWith("urn:publicid:")) {
/* 1191 */       publicId = PublicId.decodeURN(publicId);
/*      */     }
/*      */     
/* 1194 */     if (systemId != null && systemId.startsWith("urn:publicid:")) {
/* 1195 */       systemId = PublicId.decodeURN(systemId);
/* 1196 */       if (publicId != null && !publicId.equals(systemId)) {
/* 1197 */         this.catalogManager.debug.message(1, "urn:publicid: system identifier differs from public identifier; using public identifier");
/* 1198 */         systemId = null;
/*      */       } else {
/* 1200 */         publicId = systemId;
/* 1201 */         systemId = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1205 */     if (systemId != null) {
/*      */       
/* 1207 */       resolved = resolveLocalSystem(systemId);
/* 1208 */       if (resolved != null) {
/* 1209 */         return resolved;
/*      */       }
/*      */     } 
/*      */     
/* 1213 */     if (publicId != null) {
/*      */       
/* 1215 */       resolved = resolveLocalPublic(DOCTYPE, entityName, publicId, systemId);
/*      */ 
/*      */ 
/*      */       
/* 1219 */       if (resolved != null) {
/* 1220 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1225 */     boolean over = this.default_override;
/* 1226 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1227 */     while (en.hasMoreElements()) {
/* 1228 */       CatalogEntry e = en.nextElement();
/* 1229 */       if (e.getEntryType() == OVERRIDE) {
/* 1230 */         over = e.getEntryArg(0).equalsIgnoreCase("YES");
/*      */         
/*      */         continue;
/*      */       } 
/* 1234 */       if (e.getEntryType() == DOCTYPE && e
/* 1235 */         .getEntryArg(0).equals(entityName) && (
/* 1236 */         over || systemId == null)) {
/* 1237 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1243 */     return resolveSubordinateCatalogs(DOCTYPE, entityName, publicId, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolveDocument() throws MalformedURLException, IOException {
/* 1262 */     this.catalogManager.debug.message(3, "resolveDocument");
/*      */     
/* 1264 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1265 */     while (en.hasMoreElements()) {
/* 1266 */       CatalogEntry e = en.nextElement();
/* 1267 */       if (e.getEntryType() == DOCUMENT) {
/* 1268 */         return e.getEntryArg(0);
/*      */       }
/*      */     } 
/*      */     
/* 1272 */     return resolveSubordinateCatalogs(DOCUMENT, null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolveEntity(String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 1296 */     String resolved = null;
/*      */     
/* 1298 */     this.catalogManager.debug.message(3, "resolveEntity(" + entityName + "," + publicId + "," + systemId + ")");
/*      */ 
/*      */     
/* 1301 */     systemId = normalizeURI(systemId);
/*      */     
/* 1303 */     if (publicId != null && publicId.startsWith("urn:publicid:")) {
/* 1304 */       publicId = PublicId.decodeURN(publicId);
/*      */     }
/*      */     
/* 1307 */     if (systemId != null && systemId.startsWith("urn:publicid:")) {
/* 1308 */       systemId = PublicId.decodeURN(systemId);
/* 1309 */       if (publicId != null && !publicId.equals(systemId)) {
/* 1310 */         this.catalogManager.debug.message(1, "urn:publicid: system identifier differs from public identifier; using public identifier");
/* 1311 */         systemId = null;
/*      */       } else {
/* 1313 */         publicId = systemId;
/* 1314 */         systemId = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1318 */     if (systemId != null) {
/*      */       
/* 1320 */       resolved = resolveLocalSystem(systemId);
/* 1321 */       if (resolved != null) {
/* 1322 */         return resolved;
/*      */       }
/*      */     } 
/*      */     
/* 1326 */     if (publicId != null) {
/*      */       
/* 1328 */       resolved = resolveLocalPublic(ENTITY, entityName, publicId, systemId);
/*      */ 
/*      */ 
/*      */       
/* 1332 */       if (resolved != null) {
/* 1333 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1338 */     boolean over = this.default_override;
/* 1339 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1340 */     while (en.hasMoreElements()) {
/* 1341 */       CatalogEntry e = en.nextElement();
/* 1342 */       if (e.getEntryType() == OVERRIDE) {
/* 1343 */         over = e.getEntryArg(0).equalsIgnoreCase("YES");
/*      */         
/*      */         continue;
/*      */       } 
/* 1347 */       if (e.getEntryType() == ENTITY && e
/* 1348 */         .getEntryArg(0).equals(entityName) && (
/* 1349 */         over || systemId == null)) {
/* 1350 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1356 */     return resolveSubordinateCatalogs(ENTITY, entityName, publicId, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolveNotation(String notationName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 1382 */     String resolved = null;
/*      */     
/* 1384 */     this.catalogManager.debug.message(3, "resolveNotation(" + notationName + "," + publicId + "," + systemId + ")");
/*      */ 
/*      */     
/* 1387 */     systemId = normalizeURI(systemId);
/*      */     
/* 1389 */     if (publicId != null && publicId.startsWith("urn:publicid:")) {
/* 1390 */       publicId = PublicId.decodeURN(publicId);
/*      */     }
/*      */     
/* 1393 */     if (systemId != null && systemId.startsWith("urn:publicid:")) {
/* 1394 */       systemId = PublicId.decodeURN(systemId);
/* 1395 */       if (publicId != null && !publicId.equals(systemId)) {
/* 1396 */         this.catalogManager.debug.message(1, "urn:publicid: system identifier differs from public identifier; using public identifier");
/* 1397 */         systemId = null;
/*      */       } else {
/* 1399 */         publicId = systemId;
/* 1400 */         systemId = null;
/*      */       } 
/*      */     } 
/*      */     
/* 1404 */     if (systemId != null) {
/*      */       
/* 1406 */       resolved = resolveLocalSystem(systemId);
/* 1407 */       if (resolved != null) {
/* 1408 */         return resolved;
/*      */       }
/*      */     } 
/*      */     
/* 1412 */     if (publicId != null) {
/*      */       
/* 1414 */       resolved = resolveLocalPublic(NOTATION, notationName, publicId, systemId);
/*      */ 
/*      */ 
/*      */       
/* 1418 */       if (resolved != null) {
/* 1419 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1424 */     boolean over = this.default_override;
/* 1425 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1426 */     while (en.hasMoreElements()) {
/* 1427 */       CatalogEntry e = en.nextElement();
/* 1428 */       if (e.getEntryType() == OVERRIDE) {
/* 1429 */         over = e.getEntryArg(0).equalsIgnoreCase("YES");
/*      */         
/*      */         continue;
/*      */       } 
/* 1433 */       if (e.getEntryType() == NOTATION && e
/* 1434 */         .getEntryArg(0).equals(notationName) && (
/* 1435 */         over || systemId == null)) {
/* 1436 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1442 */     return resolveSubordinateCatalogs(NOTATION, notationName, publicId, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolvePublic(String publicId, String systemId) throws MalformedURLException, IOException {
/* 1474 */     this.catalogManager.debug.message(3, "resolvePublic(" + publicId + "," + systemId + ")");
/*      */     
/* 1476 */     systemId = normalizeURI(systemId);
/*      */     
/* 1478 */     if (publicId != null && publicId.startsWith("urn:publicid:")) {
/* 1479 */       publicId = PublicId.decodeURN(publicId);
/*      */     }
/*      */     
/* 1482 */     if (systemId != null && systemId.startsWith("urn:publicid:")) {
/* 1483 */       systemId = PublicId.decodeURN(systemId);
/* 1484 */       if (publicId != null && !publicId.equals(systemId)) {
/* 1485 */         this.catalogManager.debug.message(1, "urn:publicid: system identifier differs from public identifier; using public identifier");
/* 1486 */         systemId = null;
/*      */       } else {
/* 1488 */         publicId = systemId;
/* 1489 */         systemId = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1494 */     if (systemId != null) {
/* 1495 */       String str = resolveLocalSystem(systemId);
/* 1496 */       if (str != null) {
/* 1497 */         return str;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1502 */     String resolved = resolveLocalPublic(PUBLIC, null, publicId, systemId);
/*      */ 
/*      */ 
/*      */     
/* 1506 */     if (resolved != null) {
/* 1507 */       return resolved;
/*      */     }
/*      */ 
/*      */     
/* 1511 */     return resolveSubordinateCatalogs(PUBLIC, null, publicId, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized String resolveLocalPublic(int entityType, String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 1572 */     publicId = PublicId.normalize(publicId);
/*      */ 
/*      */     
/* 1575 */     if (systemId != null) {
/* 1576 */       String resolved = resolveLocalSystem(systemId);
/* 1577 */       if (resolved != null) {
/* 1578 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1583 */     boolean over = this.default_override;
/* 1584 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1585 */     while (en.hasMoreElements()) {
/* 1586 */       CatalogEntry e = en.nextElement();
/* 1587 */       if (e.getEntryType() == OVERRIDE) {
/* 1588 */         over = e.getEntryArg(0).equalsIgnoreCase("YES");
/*      */         
/*      */         continue;
/*      */       } 
/* 1592 */       if (e.getEntryType() == PUBLIC && e
/* 1593 */         .getEntryArg(0).equals(publicId) && (
/* 1594 */         over || systemId == null)) {
/* 1595 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1601 */     over = this.default_override;
/* 1602 */     en = this.catalogEntries.elements();
/* 1603 */     Vector<String> delCats = new Vector();
/* 1604 */     while (en.hasMoreElements()) {
/* 1605 */       CatalogEntry e = en.nextElement();
/* 1606 */       if (e.getEntryType() == OVERRIDE) {
/* 1607 */         over = e.getEntryArg(0).equalsIgnoreCase("YES");
/*      */         
/*      */         continue;
/*      */       } 
/* 1611 */       if (e.getEntryType() == DELEGATE_PUBLIC && (over || systemId == null)) {
/*      */         
/* 1613 */         String p = e.getEntryArg(0);
/* 1614 */         if (p.length() <= publicId.length() && p
/* 1615 */           .equals(publicId.substring(0, p.length())))
/*      */         {
/*      */           
/* 1618 */           delCats.addElement(e.getEntryArg(1));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1623 */     if (delCats.size() > 0) {
/* 1624 */       Enumeration<String> enCats = delCats.elements();
/*      */       
/* 1626 */       if (this.catalogManager.debug.getDebug() > 1) {
/* 1627 */         this.catalogManager.debug.message(2, "Switching to delegated catalog(s):");
/* 1628 */         while (enCats.hasMoreElements()) {
/* 1629 */           String delegatedCatalog = enCats.nextElement();
/* 1630 */           this.catalogManager.debug.message(2, "\t" + delegatedCatalog);
/*      */         } 
/*      */       } 
/*      */       
/* 1634 */       Catalog dcat = newCatalog();
/*      */       
/* 1636 */       enCats = delCats.elements();
/* 1637 */       while (enCats.hasMoreElements()) {
/* 1638 */         String delegatedCatalog = enCats.nextElement();
/* 1639 */         dcat.parseCatalog(delegatedCatalog);
/*      */       } 
/*      */       
/* 1642 */       return dcat.resolvePublic(publicId, null);
/*      */     } 
/*      */ 
/*      */     
/* 1646 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolveSystem(String systemId) throws MalformedURLException, IOException {
/* 1670 */     this.catalogManager.debug.message(3, "resolveSystem(" + systemId + ")");
/*      */     
/* 1672 */     systemId = normalizeURI(systemId);
/*      */     
/* 1674 */     if (systemId != null && systemId.startsWith("urn:publicid:")) {
/* 1675 */       systemId = PublicId.decodeURN(systemId);
/* 1676 */       return resolvePublic(systemId, null);
/*      */     } 
/*      */ 
/*      */     
/* 1680 */     if (systemId != null) {
/* 1681 */       String resolved = resolveLocalSystem(systemId);
/* 1682 */       if (resolved != null) {
/* 1683 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1688 */     return resolveSubordinateCatalogs(SYSTEM, null, null, systemId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String resolveLocalSystem(String systemId) throws MalformedURLException, IOException {
/* 1708 */     String osname = SecuritySupport.getSystemProperty("os.name");
/* 1709 */     boolean windows = (osname.indexOf("Windows") >= 0);
/* 1710 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1711 */     while (en.hasMoreElements()) {
/* 1712 */       CatalogEntry e = en.nextElement();
/* 1713 */       if (e.getEntryType() == SYSTEM && (e
/* 1714 */         .getEntryArg(0).equals(systemId) || (windows && e
/*      */         
/* 1716 */         .getEntryArg(0).equalsIgnoreCase(systemId)))) {
/* 1717 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1722 */     en = this.catalogEntries.elements();
/* 1723 */     String startString = null;
/* 1724 */     String prefix = null;
/* 1725 */     while (en.hasMoreElements()) {
/* 1726 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1728 */       if (e.getEntryType() == REWRITE_SYSTEM) {
/* 1729 */         String p = e.getEntryArg(0);
/* 1730 */         if (p.length() <= systemId.length() && p
/* 1731 */           .equals(systemId.substring(0, p.length())))
/*      */         {
/* 1733 */           if (startString == null || p
/* 1734 */             .length() > startString.length()) {
/* 1735 */             startString = p;
/* 1736 */             prefix = e.getEntryArg(1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1742 */     if (prefix != null)
/*      */     {
/* 1744 */       return prefix + systemId.substring(startString.length());
/*      */     }
/*      */ 
/*      */     
/* 1748 */     en = this.catalogEntries.elements();
/* 1749 */     String suffixString = null;
/* 1750 */     String suffixURI = null;
/* 1751 */     while (en.hasMoreElements()) {
/* 1752 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1754 */       if (e.getEntryType() == SYSTEM_SUFFIX) {
/* 1755 */         String p = e.getEntryArg(0);
/* 1756 */         if (p.length() <= systemId.length() && systemId
/* 1757 */           .endsWith(p))
/*      */         {
/* 1759 */           if (suffixString == null || p
/* 1760 */             .length() > suffixString.length()) {
/* 1761 */             suffixString = p;
/* 1762 */             suffixURI = e.getEntryArg(1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1768 */     if (suffixURI != null)
/*      */     {
/* 1770 */       return suffixURI;
/*      */     }
/*      */ 
/*      */     
/* 1774 */     en = this.catalogEntries.elements();
/* 1775 */     Vector<String> delCats = new Vector();
/* 1776 */     while (en.hasMoreElements()) {
/* 1777 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1779 */       if (e.getEntryType() == DELEGATE_SYSTEM) {
/* 1780 */         String p = e.getEntryArg(0);
/* 1781 */         if (p.length() <= systemId.length() && p
/* 1782 */           .equals(systemId.substring(0, p.length())))
/*      */         {
/*      */           
/* 1785 */           delCats.addElement(e.getEntryArg(1));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1790 */     if (delCats.size() > 0) {
/* 1791 */       Enumeration<String> enCats = delCats.elements();
/*      */       
/* 1793 */       if (this.catalogManager.debug.getDebug() > 1) {
/* 1794 */         this.catalogManager.debug.message(2, "Switching to delegated catalog(s):");
/* 1795 */         while (enCats.hasMoreElements()) {
/* 1796 */           String delegatedCatalog = enCats.nextElement();
/* 1797 */           this.catalogManager.debug.message(2, "\t" + delegatedCatalog);
/*      */         } 
/*      */       } 
/*      */       
/* 1801 */       Catalog dcat = newCatalog();
/*      */       
/* 1803 */       enCats = delCats.elements();
/* 1804 */       while (enCats.hasMoreElements()) {
/* 1805 */         String delegatedCatalog = enCats.nextElement();
/* 1806 */         dcat.parseCatalog(delegatedCatalog);
/*      */       } 
/*      */       
/* 1809 */       return dcat.resolveSystem(systemId);
/*      */     } 
/*      */     
/* 1812 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String resolveURI(String uri) throws MalformedURLException, IOException {
/* 1834 */     this.catalogManager.debug.message(3, "resolveURI(" + uri + ")");
/*      */     
/* 1836 */     uri = normalizeURI(uri);
/*      */     
/* 1838 */     if (uri != null && uri.startsWith("urn:publicid:")) {
/* 1839 */       uri = PublicId.decodeURN(uri);
/* 1840 */       return resolvePublic(uri, null);
/*      */     } 
/*      */ 
/*      */     
/* 1844 */     if (uri != null) {
/* 1845 */       String resolved = resolveLocalURI(uri);
/* 1846 */       if (resolved != null) {
/* 1847 */         return resolved;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1852 */     return resolveSubordinateCatalogs(URI, null, null, uri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String resolveLocalURI(String uri) throws MalformedURLException, IOException {
/* 1870 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 1871 */     while (en.hasMoreElements()) {
/* 1872 */       CatalogEntry e = en.nextElement();
/* 1873 */       if (e.getEntryType() == URI && e
/* 1874 */         .getEntryArg(0).equals(uri)) {
/* 1875 */         return e.getEntryArg(1);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1880 */     en = this.catalogEntries.elements();
/* 1881 */     String startString = null;
/* 1882 */     String prefix = null;
/* 1883 */     while (en.hasMoreElements()) {
/* 1884 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1886 */       if (e.getEntryType() == REWRITE_URI) {
/* 1887 */         String p = e.getEntryArg(0);
/* 1888 */         if (p.length() <= uri.length() && p
/* 1889 */           .equals(uri.substring(0, p.length())))
/*      */         {
/* 1891 */           if (startString == null || p
/* 1892 */             .length() > startString.length()) {
/* 1893 */             startString = p;
/* 1894 */             prefix = e.getEntryArg(1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1900 */     if (prefix != null)
/*      */     {
/* 1902 */       return prefix + uri.substring(startString.length());
/*      */     }
/*      */ 
/*      */     
/* 1906 */     en = this.catalogEntries.elements();
/* 1907 */     String suffixString = null;
/* 1908 */     String suffixURI = null;
/* 1909 */     while (en.hasMoreElements()) {
/* 1910 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1912 */       if (e.getEntryType() == URI_SUFFIX) {
/* 1913 */         String p = e.getEntryArg(0);
/* 1914 */         if (p.length() <= uri.length() && uri
/* 1915 */           .endsWith(p))
/*      */         {
/* 1917 */           if (suffixString == null || p
/* 1918 */             .length() > suffixString.length()) {
/* 1919 */             suffixString = p;
/* 1920 */             suffixURI = e.getEntryArg(1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1926 */     if (suffixURI != null)
/*      */     {
/* 1928 */       return suffixURI;
/*      */     }
/*      */ 
/*      */     
/* 1932 */     en = this.catalogEntries.elements();
/* 1933 */     Vector<String> delCats = new Vector();
/* 1934 */     while (en.hasMoreElements()) {
/* 1935 */       CatalogEntry e = en.nextElement();
/*      */       
/* 1937 */       if (e.getEntryType() == DELEGATE_URI) {
/* 1938 */         String p = e.getEntryArg(0);
/* 1939 */         if (p.length() <= uri.length() && p
/* 1940 */           .equals(uri.substring(0, p.length())))
/*      */         {
/*      */           
/* 1943 */           delCats.addElement(e.getEntryArg(1));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1948 */     if (delCats.size() > 0) {
/* 1949 */       Enumeration<String> enCats = delCats.elements();
/*      */       
/* 1951 */       if (this.catalogManager.debug.getDebug() > 1) {
/* 1952 */         this.catalogManager.debug.message(2, "Switching to delegated catalog(s):");
/* 1953 */         while (enCats.hasMoreElements()) {
/* 1954 */           String delegatedCatalog = enCats.nextElement();
/* 1955 */           this.catalogManager.debug.message(2, "\t" + delegatedCatalog);
/*      */         } 
/*      */       } 
/*      */       
/* 1959 */       Catalog dcat = newCatalog();
/*      */       
/* 1961 */       enCats = delCats.elements();
/* 1962 */       while (enCats.hasMoreElements()) {
/* 1963 */         String delegatedCatalog = enCats.nextElement();
/* 1964 */         dcat.parseCatalog(delegatedCatalog);
/*      */       } 
/*      */       
/* 1967 */       return dcat.resolveURI(uri);
/*      */     } 
/*      */     
/* 1970 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized String resolveSubordinateCatalogs(int entityType, String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 2007 */     for (int catPos = 0; catPos < this.catalogs.size(); catPos++) {
/* 2008 */       Catalog c = null;
/*      */       
/*      */       try {
/* 2011 */         c = this.catalogs.elementAt(catPos);
/* 2012 */       } catch (ClassCastException e) {
/* 2013 */         String catfile = this.catalogs.elementAt(catPos);
/* 2014 */         c = newCatalog();
/*      */         
/*      */         try {
/* 2017 */           c.parseCatalog(catfile);
/* 2018 */         } catch (MalformedURLException mue) {
/* 2019 */           this.catalogManager.debug.message(1, "Malformed Catalog URL", catfile);
/* 2020 */         } catch (FileNotFoundException fnfe) {
/* 2021 */           this.catalogManager.debug.message(1, "Failed to load catalog, file not found", catfile);
/*      */         }
/* 2023 */         catch (IOException ioe) {
/* 2024 */           this.catalogManager.debug.message(1, "Failed to load catalog, I/O error", catfile);
/*      */         } 
/*      */         
/* 2027 */         this.catalogs.setElementAt(c, catPos);
/*      */       } 
/*      */       
/* 2030 */       String resolved = null;
/*      */ 
/*      */       
/* 2033 */       if (entityType == DOCTYPE) {
/* 2034 */         resolved = c.resolveDoctype(entityName, publicId, systemId);
/*      */       
/*      */       }
/* 2037 */       else if (entityType == DOCUMENT) {
/* 2038 */         resolved = c.resolveDocument();
/* 2039 */       } else if (entityType == ENTITY) {
/* 2040 */         resolved = c.resolveEntity(entityName, publicId, systemId);
/*      */       
/*      */       }
/* 2043 */       else if (entityType == NOTATION) {
/* 2044 */         resolved = c.resolveNotation(entityName, publicId, systemId);
/*      */       
/*      */       }
/* 2047 */       else if (entityType == PUBLIC) {
/* 2048 */         resolved = c.resolvePublic(publicId, systemId);
/* 2049 */       } else if (entityType == SYSTEM) {
/* 2050 */         resolved = c.resolveSystem(systemId);
/* 2051 */       } else if (entityType == URI) {
/* 2052 */         resolved = c.resolveURI(systemId);
/*      */       } 
/*      */       
/* 2055 */       if (resolved != null) {
/* 2056 */         return resolved;
/*      */       }
/*      */     } 
/*      */     
/* 2060 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String fixSlashes(String sysid) {
/* 2074 */     return sysid.replace('\\', '/');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String makeAbsolute(String sysid) {
/* 2086 */     URL local = null;
/*      */     
/* 2088 */     sysid = fixSlashes(sysid);
/*      */     
/*      */     try {
/* 2091 */       local = new URL(this.base, sysid);
/* 2092 */     } catch (MalformedURLException e) {
/* 2093 */       this.catalogManager.debug.message(1, "Malformed URL on system identifier", sysid);
/*      */     } 
/*      */     
/* 2096 */     if (local != null) {
/* 2097 */       return local.toString();
/*      */     }
/* 2099 */     return sysid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String normalizeURI(String uriref) {
/*      */     byte[] bytes;
/* 2110 */     if (uriref == null) {
/* 2111 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2116 */       bytes = uriref.getBytes("UTF-8");
/* 2117 */     } catch (UnsupportedEncodingException uee) {
/*      */       
/* 2119 */       this.catalogManager.debug.message(1, "UTF-8 is an unsupported encoding!?");
/* 2120 */       return uriref;
/*      */     } 
/*      */     
/* 2123 */     StringBuilder newRef = new StringBuilder(bytes.length);
/* 2124 */     for (int count = 0; count < bytes.length; count++) {
/* 2125 */       int ch = bytes[count] & 0xFF;
/*      */       
/* 2127 */       if (ch <= 32 || ch > 127 || ch == 34 || ch == 60 || ch == 62 || ch == 92 || ch == 94 || ch == 96 || ch == 123 || ch == 124 || ch == 125 || ch == 127) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2139 */         newRef.append(encodedByte(ch));
/*      */       } else {
/* 2141 */         newRef.append((char)bytes[count]);
/*      */       } 
/*      */     } 
/*      */     
/* 2145 */     return newRef.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String encodedByte(int b) {
/* 2156 */     String hex = Integer.toHexString(b).toUpperCase();
/* 2157 */     if (hex.length() < 2) {
/* 2158 */       return "%0" + hex;
/*      */     }
/* 2160 */     return "%" + hex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDelegate(CatalogEntry entry) {
/* 2176 */     int pos = 0;
/* 2177 */     String partial = entry.getEntryArg(0);
/*      */     
/* 2179 */     Enumeration<CatalogEntry> local = this.localDelegate.elements();
/* 2180 */     while (local.hasMoreElements()) {
/* 2181 */       CatalogEntry dpe = local.nextElement();
/* 2182 */       String dp = dpe.getEntryArg(0);
/* 2183 */       if (dp.equals(partial)) {
/*      */         return;
/*      */       }
/*      */       
/* 2187 */       if (dp.length() > partial.length()) {
/* 2188 */         pos++;
/*      */       }
/* 2190 */       if (dp.length() < partial.length()) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2196 */     if (this.localDelegate.size() == 0) {
/* 2197 */       this.localDelegate.addElement(entry);
/*      */     } else {
/* 2199 */       this.localDelegate.insertElementAt(entry, pos);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/Catalog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */