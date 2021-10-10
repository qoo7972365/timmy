/*     */ package com.sun.org.apache.xml.internal.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.BootstrapResolver;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.reflect.misc.ReflectUtil;
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
/*     */ public class CatalogManager
/*     */ {
/* 130 */   private static String pFiles = "xml.catalog.files";
/* 131 */   private static String pVerbosity = "xml.catalog.verbosity";
/* 132 */   private static String pPrefer = "xml.catalog.prefer";
/* 133 */   private static String pStatic = "xml.catalog.staticCatalog";
/* 134 */   private static String pAllowPI = "xml.catalog.allowPI";
/* 135 */   private static String pClassname = "xml.catalog.className";
/* 136 */   private static String pIgnoreMissing = "xml.catalog.ignoreMissing";
/*     */ 
/*     */   
/* 139 */   private static CatalogManager staticManager = new CatalogManager();
/*     */ 
/*     */   
/* 142 */   private BootstrapResolver bResolver = new BootstrapResolver();
/*     */ 
/*     */   
/* 145 */   private boolean ignoreMissingProperties = (
/* 146 */     SecuritySupport.getSystemProperty(pIgnoreMissing) != null || 
/* 147 */     SecuritySupport.getSystemProperty(pFiles) != null);
/*     */ 
/*     */   
/*     */   private ResourceBundle resources;
/*     */ 
/*     */   
/* 153 */   private String propertyFile = "CatalogManager.properties";
/*     */ 
/*     */   
/* 156 */   private URL propertyFileURI = null;
/*     */ 
/*     */   
/* 159 */   private String defaultCatalogFiles = "./xcatalog";
/*     */ 
/*     */   
/* 162 */   private String catalogFiles = null;
/*     */ 
/*     */   
/*     */   private boolean fromPropertiesFile = false;
/*     */ 
/*     */   
/* 168 */   private int defaultVerbosity = 1;
/*     */ 
/*     */   
/* 171 */   private Integer verbosity = null;
/*     */ 
/*     */   
/*     */   private boolean defaultPreferPublic = true;
/*     */ 
/*     */   
/* 177 */   private Boolean preferPublic = null;
/*     */ 
/*     */   
/*     */   private boolean defaultUseStaticCatalog = true;
/*     */ 
/*     */   
/* 183 */   private Boolean useStaticCatalog = null;
/*     */ 
/*     */   
/* 186 */   private static Catalog staticCatalog = null;
/*     */ 
/*     */   
/*     */   private boolean defaultOasisXMLCatalogPI = true;
/*     */ 
/*     */   
/* 192 */   private Boolean oasisXMLCatalogPI = null;
/*     */ 
/*     */   
/*     */   private boolean defaultRelativeCatalogs = true;
/*     */ 
/*     */   
/* 198 */   private Boolean relativeCatalogs = null;
/*     */ 
/*     */   
/* 201 */   private String catalogClassName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean overrideDefaultParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public Debug debug = null;
/*     */ 
/*     */   
/*     */   public CatalogManager() {
/* 218 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public CatalogManager(String propertyFile) {
/* 223 */     this.propertyFile = propertyFile;
/* 224 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 228 */     this.debug = new Debug();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (System.getSecurityManager() == null) {
/* 235 */       this.overrideDefaultParser = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBootstrapResolver(BootstrapResolver resolver) {
/* 240 */     this.bResolver = resolver;
/*     */   }
/*     */ 
/*     */   
/*     */   public BootstrapResolver getBootstrapResolver() {
/* 245 */     return this.bResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void readProperties() {
/*     */     try {
/* 254 */       this.propertyFileURI = CatalogManager.class.getResource("/" + this.propertyFile);
/*     */       
/* 256 */       InputStream in = CatalogManager.class.getResourceAsStream("/" + this.propertyFile);
/* 257 */       if (in == null) {
/* 258 */         if (!this.ignoreMissingProperties) {
/* 259 */           System.err.println("Cannot find " + this.propertyFile);
/*     */           
/* 261 */           this.ignoreMissingProperties = true;
/*     */         } 
/*     */         return;
/*     */       } 
/* 265 */       this.resources = new PropertyResourceBundle(in);
/* 266 */     } catch (MissingResourceException mre) {
/* 267 */       if (!this.ignoreMissingProperties) {
/* 268 */         System.err.println("Cannot read " + this.propertyFile);
/*     */       }
/* 270 */     } catch (IOException e) {
/* 271 */       if (!this.ignoreMissingProperties) {
/* 272 */         System.err.println("Failure trying to read " + this.propertyFile);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     if (this.verbosity == null) {
/*     */       try {
/* 281 */         String verbStr = this.resources.getString("verbosity");
/* 282 */         int verb = Integer.parseInt(verbStr.trim());
/* 283 */         this.debug.setDebug(verb);
/* 284 */         this.verbosity = new Integer(verb);
/* 285 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CatalogManager getStaticManager() {
/* 295 */     return staticManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIgnoreMissingProperties() {
/* 306 */     return this.ignoreMissingProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIgnoreMissingProperties(boolean ignore) {
/* 317 */     this.ignoreMissingProperties = ignore;
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
/*     */   public void ignoreMissingProperties(boolean ignore) {
/* 330 */     setIgnoreMissingProperties(ignore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int queryVerbosity() {
/* 340 */     String defaultVerbStr = Integer.toString(this.defaultVerbosity);
/*     */     
/* 342 */     String verbStr = SecuritySupport.getSystemProperty(pVerbosity);
/*     */     
/* 344 */     if (verbStr == null) {
/* 345 */       if (this.resources == null) readProperties(); 
/* 346 */       if (this.resources != null) {
/*     */         try {
/* 348 */           verbStr = this.resources.getString("verbosity");
/* 349 */         } catch (MissingResourceException e) {
/* 350 */           verbStr = defaultVerbStr;
/*     */         } 
/*     */       } else {
/* 353 */         verbStr = defaultVerbStr;
/*     */       } 
/*     */     } 
/*     */     
/* 357 */     int verb = this.defaultVerbosity;
/*     */     
/*     */     try {
/* 360 */       verb = Integer.parseInt(verbStr.trim());
/* 361 */     } catch (Exception e) {
/* 362 */       System.err.println("Cannot parse verbosity: \"" + verbStr + "\"");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     if (this.verbosity == null) {
/* 369 */       this.debug.setDebug(verb);
/* 370 */       this.verbosity = new Integer(verb);
/*     */     } 
/*     */     
/* 373 */     return verb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerbosity() {
/* 380 */     if (this.verbosity == null) {
/* 381 */       this.verbosity = new Integer(queryVerbosity());
/*     */     }
/*     */     
/* 384 */     return this.verbosity.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerbosity(int verbosity) {
/* 391 */     this.verbosity = new Integer(verbosity);
/* 392 */     this.debug.setDebug(verbosity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int verbosity() {
/* 401 */     return getVerbosity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean queryRelativeCatalogs() {
/* 411 */     if (this.resources == null) readProperties();
/*     */     
/* 413 */     if (this.resources == null) return this.defaultRelativeCatalogs;
/*     */     
/*     */     try {
/* 416 */       String allow = this.resources.getString("relative-catalogs");
/* 417 */       return (allow.equalsIgnoreCase("true") || allow
/* 418 */         .equalsIgnoreCase("yes") || allow
/* 419 */         .equalsIgnoreCase("1"));
/* 420 */     } catch (MissingResourceException e) {
/* 421 */       return this.defaultRelativeCatalogs;
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
/*     */   public boolean getRelativeCatalogs() {
/* 446 */     if (this.relativeCatalogs == null) {
/* 447 */       this.relativeCatalogs = new Boolean(queryRelativeCatalogs());
/*     */     }
/*     */     
/* 450 */     return this.relativeCatalogs.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRelativeCatalogs(boolean relative) {
/* 459 */     this.relativeCatalogs = new Boolean(relative);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean relativeCatalogs() {
/* 468 */     return getRelativeCatalogs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String queryCatalogFiles() {
/* 477 */     String catalogList = SecuritySupport.getSystemProperty(pFiles);
/* 478 */     this.fromPropertiesFile = false;
/*     */     
/* 480 */     if (catalogList == null) {
/* 481 */       if (this.resources == null) readProperties(); 
/* 482 */       if (this.resources != null) {
/*     */         try {
/* 484 */           catalogList = this.resources.getString("catalogs");
/* 485 */           this.fromPropertiesFile = true;
/* 486 */         } catch (MissingResourceException e) {
/* 487 */           System.err.println(this.propertyFile + ": catalogs not found.");
/* 488 */           catalogList = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 493 */     if (catalogList == null) {
/* 494 */       catalogList = this.defaultCatalogFiles;
/*     */     }
/*     */     
/* 497 */     return catalogList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getCatalogFiles() {
/* 507 */     if (this.catalogFiles == null) {
/* 508 */       this.catalogFiles = queryCatalogFiles();
/*     */     }
/*     */     
/* 511 */     StringTokenizer files = new StringTokenizer(this.catalogFiles, ";");
/* 512 */     Vector<String> catalogs = new Vector();
/* 513 */     while (files.hasMoreTokens()) {
/* 514 */       String catalogFile = files.nextToken();
/* 515 */       URL absURI = null;
/*     */       
/* 517 */       if (this.fromPropertiesFile && !relativeCatalogs()) {
/*     */         try {
/* 519 */           absURI = new URL(this.propertyFileURI, catalogFile);
/* 520 */           catalogFile = absURI.toString();
/* 521 */         } catch (MalformedURLException mue) {
/* 522 */           absURI = null;
/*     */         } 
/*     */       }
/*     */       
/* 526 */       catalogs.add(catalogFile);
/*     */     } 
/*     */     
/* 529 */     return catalogs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalogFiles(String fileList) {
/* 536 */     this.catalogFiles = fileList;
/* 537 */     this.fromPropertiesFile = false;
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
/*     */   public Vector catalogFiles() {
/* 549 */     return getCatalogFiles();
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
/*     */   private boolean queryPreferPublic() {
/* 562 */     String prefer = SecuritySupport.getSystemProperty(pPrefer);
/*     */     
/* 564 */     if (prefer == null) {
/* 565 */       if (this.resources == null) readProperties(); 
/* 566 */       if (this.resources == null) return this.defaultPreferPublic; 
/*     */       try {
/* 568 */         prefer = this.resources.getString("prefer");
/* 569 */       } catch (MissingResourceException e) {
/* 570 */         return this.defaultPreferPublic;
/*     */       } 
/*     */     } 
/*     */     
/* 574 */     if (prefer == null) {
/* 575 */       return this.defaultPreferPublic;
/*     */     }
/*     */     
/* 578 */     return prefer.equalsIgnoreCase("public");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPreferPublic() {
/* 587 */     if (this.preferPublic == null) {
/* 588 */       this.preferPublic = new Boolean(queryPreferPublic());
/*     */     }
/* 590 */     return this.preferPublic.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreferPublic(boolean preferPublic) {
/* 597 */     this.preferPublic = new Boolean(preferPublic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean preferPublic() {
/* 608 */     return getPreferPublic();
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
/*     */   private boolean queryUseStaticCatalog() {
/* 621 */     String staticCatalog = SecuritySupport.getSystemProperty(pStatic);
/*     */     
/* 623 */     if (staticCatalog == null) {
/* 624 */       if (this.resources == null) readProperties(); 
/* 625 */       if (this.resources == null) return this.defaultUseStaticCatalog; 
/*     */       try {
/* 627 */         staticCatalog = this.resources.getString("static-catalog");
/* 628 */       } catch (MissingResourceException e) {
/* 629 */         return this.defaultUseStaticCatalog;
/*     */       } 
/*     */     } 
/*     */     
/* 633 */     if (staticCatalog == null) {
/* 634 */       return this.defaultUseStaticCatalog;
/*     */     }
/*     */     
/* 637 */     return (staticCatalog.equalsIgnoreCase("true") || staticCatalog
/* 638 */       .equalsIgnoreCase("yes") || staticCatalog
/* 639 */       .equalsIgnoreCase("1"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUseStaticCatalog() {
/* 646 */     if (this.useStaticCatalog == null) {
/* 647 */       this.useStaticCatalog = new Boolean(queryUseStaticCatalog());
/*     */     }
/*     */     
/* 650 */     return this.useStaticCatalog.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseStaticCatalog(boolean useStatic) {
/* 657 */     this.useStaticCatalog = new Boolean(useStatic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean staticCatalog() {
/* 666 */     return getUseStaticCatalog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Catalog getPrivateCatalog() {
/* 675 */     Catalog catalog = staticCatalog;
/*     */     
/* 677 */     if (this.useStaticCatalog == null) {
/* 678 */       this.useStaticCatalog = new Boolean(getUseStaticCatalog());
/*     */     }
/*     */     
/* 681 */     if (catalog == null || !this.useStaticCatalog.booleanValue()) {
/*     */       
/*     */       try {
/* 684 */         String catalogClassName = getCatalogClassName();
/*     */         
/* 686 */         if (catalogClassName == null) {
/* 687 */           catalog = new Catalog();
/*     */         } else {
/*     */           try {
/* 690 */             catalog = (Catalog)ReflectUtil.forName(catalogClassName).newInstance();
/* 691 */           } catch (ClassNotFoundException cnfe) {
/* 692 */             this.debug.message(1, "Catalog class named '" + catalogClassName + "' could not be found. Using default.");
/*     */ 
/*     */             
/* 695 */             catalog = new Catalog();
/* 696 */           } catch (ClassCastException cnfe) {
/* 697 */             this.debug.message(1, "Class named '" + catalogClassName + "' is not a Catalog. Using default.");
/*     */ 
/*     */             
/* 700 */             catalog = new Catalog();
/*     */           } 
/*     */         } 
/*     */         
/* 704 */         catalog.setCatalogManager(this);
/* 705 */         catalog.setupReaders();
/* 706 */         catalog.loadSystemCatalogs();
/* 707 */       } catch (Exception ex) {
/* 708 */         ex.printStackTrace();
/*     */       } 
/*     */       
/* 711 */       if (this.useStaticCatalog.booleanValue()) {
/* 712 */         staticCatalog = catalog;
/*     */       }
/*     */     } 
/*     */     
/* 716 */     return catalog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Catalog getCatalog() {
/* 726 */     Catalog catalog = staticCatalog;
/*     */     
/* 728 */     if (this.useStaticCatalog == null) {
/* 729 */       this.useStaticCatalog = new Boolean(getUseStaticCatalog());
/*     */     }
/*     */     
/* 732 */     if (catalog == null || !this.useStaticCatalog.booleanValue()) {
/* 733 */       catalog = getPrivateCatalog();
/* 734 */       if (this.useStaticCatalog.booleanValue()) {
/* 735 */         staticCatalog = catalog;
/*     */       }
/*     */     } 
/*     */     
/* 739 */     return catalog;
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
/*     */   public boolean queryAllowOasisXMLCatalogPI() {
/* 752 */     String allow = SecuritySupport.getSystemProperty(pAllowPI);
/*     */     
/* 754 */     if (allow == null) {
/* 755 */       if (this.resources == null) readProperties(); 
/* 756 */       if (this.resources == null) return this.defaultOasisXMLCatalogPI; 
/*     */       try {
/* 758 */         allow = this.resources.getString("allow-oasis-xml-catalog-pi");
/* 759 */       } catch (MissingResourceException e) {
/* 760 */         return this.defaultOasisXMLCatalogPI;
/*     */       } 
/*     */     } 
/*     */     
/* 764 */     if (allow == null) {
/* 765 */       return this.defaultOasisXMLCatalogPI;
/*     */     }
/*     */     
/* 768 */     return (allow.equalsIgnoreCase("true") || allow
/* 769 */       .equalsIgnoreCase("yes") || allow
/* 770 */       .equalsIgnoreCase("1"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAllowOasisXMLCatalogPI() {
/* 777 */     if (this.oasisXMLCatalogPI == null) {
/* 778 */       this.oasisXMLCatalogPI = new Boolean(queryAllowOasisXMLCatalogPI());
/*     */     }
/*     */     
/* 781 */     return this.oasisXMLCatalogPI.booleanValue();
/*     */   }
/*     */   
/*     */   public boolean overrideDefaultParser() {
/* 785 */     return this.overrideDefaultParser;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowOasisXMLCatalogPI(boolean allowPI) {
/* 791 */     this.oasisXMLCatalogPI = new Boolean(allowPI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allowOasisXMLCatalogPI() {
/* 800 */     return getAllowOasisXMLCatalogPI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String queryCatalogClassName() {
/* 808 */     String className = SecuritySupport.getSystemProperty(pClassname);
/*     */     
/* 810 */     if (className == null) {
/* 811 */       if (this.resources == null) readProperties(); 
/* 812 */       if (this.resources == null) return null; 
/*     */       try {
/* 814 */         return this.resources.getString("catalog-class-name");
/* 815 */       } catch (MissingResourceException e) {
/* 816 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 820 */     return className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCatalogClassName() {
/* 827 */     if (this.catalogClassName == null) {
/* 828 */       this.catalogClassName = queryCatalogClassName();
/*     */     }
/*     */     
/* 831 */     return this.catalogClassName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalogClassName(String className) {
/* 838 */     this.catalogClassName = className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String catalogClassName() {
/* 847 */     return getCatalogClassName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/CatalogManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */