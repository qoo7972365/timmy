/*     */ package com.sun.org.apache.xml.internal.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xml.internal.resolver.readers.CatalogReader;
/*     */ import com.sun.org.apache.xml.internal.resolver.readers.SAXCatalogReader;
/*     */ import com.sun.org.apache.xml.internal.resolver.readers.TR9401CatalogReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Resolver
/*     */   extends Catalog
/*     */ {
/*  61 */   public static final int URISUFFIX = CatalogEntry.addEntryType("URISUFFIX", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final int SYSTEMSUFFIX = CatalogEntry.addEntryType("SYSTEMSUFFIX", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final int RESOLVER = CatalogEntry.addEntryType("RESOLVER", 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final int SYSTEMREVERSE = CatalogEntry.addEntryType("SYSTEMREVERSE", 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupReaders() {
/*  93 */     SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/*  94 */     spf.setValidating(false);
/*     */     
/*  96 */     SAXCatalogReader saxReader = new SAXCatalogReader(spf);
/*     */     
/*  98 */     saxReader.setCatalogParser(null, "XMLCatalog", "com.sun.org.apache.xml.internal.resolver.readers.XCatalogReader");
/*     */ 
/*     */     
/* 101 */     saxReader.setCatalogParser("urn:oasis:names:tc:entity:xmlns:xml:catalog", "catalog", "com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader");
/*     */ 
/*     */ 
/*     */     
/* 105 */     addReader("application/xml", (CatalogReader)saxReader);
/*     */     
/* 107 */     TR9401CatalogReader textReader = new TR9401CatalogReader();
/* 108 */     addReader("text/plain", (CatalogReader)textReader);
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
/*     */   public void addEntry(CatalogEntry entry) {
/* 122 */     int type = entry.getEntryType();
/*     */     
/* 124 */     if (type == URISUFFIX) {
/* 125 */       String suffix = normalizeURI(entry.getEntryArg(0));
/* 126 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*     */       
/* 128 */       entry.setEntryArg(1, fsi);
/*     */       
/* 130 */       this.catalogManager.debug.message(4, "URISUFFIX", suffix, fsi);
/* 131 */     } else if (type == SYSTEMSUFFIX) {
/* 132 */       String suffix = normalizeURI(entry.getEntryArg(0));
/* 133 */       String fsi = makeAbsolute(normalizeURI(entry.getEntryArg(1)));
/*     */       
/* 135 */       entry.setEntryArg(1, fsi);
/*     */       
/* 137 */       this.catalogManager.debug.message(4, "SYSTEMSUFFIX", suffix, fsi);
/*     */     } 
/*     */     
/* 140 */     super.addEntry(entry);
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
/*     */   public String resolveURI(String uri) throws MalformedURLException, IOException {
/* 166 */     String resolved = super.resolveURI(uri);
/* 167 */     if (resolved != null) {
/* 168 */       return resolved;
/*     */     }
/*     */     
/* 171 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 172 */     while (en.hasMoreElements()) {
/* 173 */       CatalogEntry e = en.nextElement();
/* 174 */       if (e.getEntryType() == RESOLVER) {
/* 175 */         resolved = resolveExternalSystem(uri, e.getEntryArg(0));
/* 176 */         if (resolved != null)
/* 177 */           return resolved;  continue;
/*     */       } 
/* 179 */       if (e.getEntryType() == URISUFFIX) {
/* 180 */         String suffix = e.getEntryArg(0);
/* 181 */         String result = e.getEntryArg(1);
/*     */         
/* 183 */         if (suffix.length() <= uri.length() && uri
/* 184 */           .substring(uri.length() - suffix.length()).equals(suffix)) {
/* 185 */           return result;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return resolveSubordinateCatalogs(Catalog.URI, null, null, uri);
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
/*     */   public String resolveSystem(String systemId) throws MalformedURLException, IOException {
/* 223 */     String resolved = super.resolveSystem(systemId);
/* 224 */     if (resolved != null) {
/* 225 */       return resolved;
/*     */     }
/*     */     
/* 228 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 229 */     while (en.hasMoreElements()) {
/* 230 */       CatalogEntry e = en.nextElement();
/* 231 */       if (e.getEntryType() == RESOLVER) {
/* 232 */         resolved = resolveExternalSystem(systemId, e.getEntryArg(0));
/* 233 */         if (resolved != null)
/* 234 */           return resolved;  continue;
/*     */       } 
/* 236 */       if (e.getEntryType() == SYSTEMSUFFIX) {
/* 237 */         String suffix = e.getEntryArg(0);
/* 238 */         String result = e.getEntryArg(1);
/*     */         
/* 240 */         if (suffix.length() <= systemId.length() && systemId
/* 241 */           .substring(systemId.length() - suffix.length()).equals(suffix)) {
/* 242 */           return result;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return resolveSubordinateCatalogs(Catalog.SYSTEM, null, null, systemId);
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
/*     */   public String resolvePublic(String publicId, String systemId) throws MalformedURLException, IOException {
/* 285 */     String resolved = super.resolvePublic(publicId, systemId);
/* 286 */     if (resolved != null) {
/* 287 */       return resolved;
/*     */     }
/*     */     
/* 290 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 291 */     while (en.hasMoreElements()) {
/* 292 */       CatalogEntry e = en.nextElement();
/* 293 */       if (e.getEntryType() == RESOLVER) {
/* 294 */         if (systemId != null) {
/* 295 */           resolved = resolveExternalSystem(systemId, e
/* 296 */               .getEntryArg(0));
/* 297 */           if (resolved != null) {
/* 298 */             return resolved;
/*     */           }
/*     */         } 
/* 301 */         resolved = resolveExternalPublic(publicId, e.getEntryArg(0));
/* 302 */         if (resolved != null) {
/* 303 */           return resolved;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     return resolveSubordinateCatalogs(Catalog.PUBLIC, null, publicId, systemId);
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
/*     */   protected String resolveExternalSystem(String systemId, String resolver) throws MalformedURLException, IOException {
/* 324 */     Resolver r = queryResolver(resolver, "i2l", systemId, (String)null);
/* 325 */     if (r != null) {
/* 326 */       return r.resolveSystem(systemId);
/*     */     }
/* 328 */     return null;
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
/*     */   protected String resolveExternalPublic(String publicId, String resolver) throws MalformedURLException, IOException {
/* 342 */     Resolver r = queryResolver(resolver, "fpi2l", publicId, (String)null);
/* 343 */     if (r != null) {
/* 344 */       return r.resolvePublic(publicId, (String)null);
/*     */     }
/* 346 */     return null;
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
/*     */   protected Resolver queryResolver(String resolver, String command, String arg1, String arg2) {
/* 364 */     InputStream iStream = null;
/* 365 */     String RFC2483 = resolver + "?command=" + command + "&format=tr9401&uri=" + arg1 + "&uri2=" + arg2;
/*     */ 
/*     */     
/* 368 */     String line = null;
/*     */     
/*     */     try {
/* 371 */       URL url = new URL(RFC2483);
/*     */       
/* 373 */       URLConnection urlCon = url.openConnection();
/*     */       
/* 375 */       urlCon.setUseCaches(false);
/*     */       
/* 377 */       Resolver r = (Resolver)newCatalog();
/*     */       
/* 379 */       String cType = urlCon.getContentType();
/*     */ 
/*     */       
/* 382 */       if (cType.indexOf(";") > 0) {
/* 383 */         cType = cType.substring(0, cType.indexOf(";"));
/*     */       }
/*     */       
/* 386 */       r.parseCatalog(cType, urlCon.getInputStream());
/*     */       
/* 388 */       return r;
/* 389 */     } catch (CatalogException cex) {
/* 390 */       if (cex.getExceptionType() == 6) {
/* 391 */         this.catalogManager.debug.message(1, "Unparseable catalog: " + RFC2483);
/* 392 */       } else if (cex.getExceptionType() == 5) {
/*     */         
/* 394 */         this.catalogManager.debug.message(1, "Unknown catalog format: " + RFC2483);
/*     */       } 
/* 396 */       return null;
/* 397 */     } catch (MalformedURLException mue) {
/* 398 */       this.catalogManager.debug.message(1, "Malformed resolver URL: " + RFC2483);
/* 399 */       return null;
/* 400 */     } catch (IOException ie) {
/* 401 */       this.catalogManager.debug.message(1, "I/O Exception opening resolver: " + RFC2483);
/* 402 */       return null;
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
/*     */   private Vector appendVector(Vector vec, Vector appvec) {
/* 414 */     if (appvec != null) {
/* 415 */       for (int count = 0; count < appvec.size(); count++) {
/* 416 */         vec.addElement(appvec.elementAt(count));
/*     */       }
/*     */     }
/* 419 */     return vec;
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
/*     */   public Vector resolveAllSystemReverse(String systemId) throws MalformedURLException, IOException {
/* 431 */     Vector resolved = new Vector();
/*     */ 
/*     */     
/* 434 */     if (systemId != null) {
/* 435 */       Vector localResolved = resolveLocalSystemReverse(systemId);
/* 436 */       resolved = appendVector(resolved, localResolved);
/*     */     } 
/*     */ 
/*     */     
/* 440 */     Vector subResolved = resolveAllSubordinateCatalogs(SYSTEMREVERSE, (String)null, (String)null, systemId);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     return appendVector(resolved, subResolved);
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
/*     */   public String resolveSystemReverse(String systemId) throws MalformedURLException, IOException {
/* 457 */     Vector<String> resolved = resolveAllSystemReverse(systemId);
/* 458 */     if (resolved != null && resolved.size() > 0) {
/* 459 */       return resolved.elementAt(0);
/*     */     }
/* 461 */     return null;
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
/*     */   public Vector resolveAllSystem(String systemId) throws MalformedURLException, IOException {
/* 494 */     Vector resolutions = new Vector();
/*     */ 
/*     */     
/* 497 */     if (systemId != null) {
/* 498 */       Vector localResolutions = resolveAllLocalSystem(systemId);
/* 499 */       resolutions = appendVector(resolutions, localResolutions);
/*     */     } 
/*     */ 
/*     */     
/* 503 */     Vector subResolutions = resolveAllSubordinateCatalogs(SYSTEM, (String)null, (String)null, systemId);
/*     */ 
/*     */ 
/*     */     
/* 507 */     resolutions = appendVector(resolutions, subResolutions);
/*     */     
/* 509 */     if (resolutions.size() > 0) {
/* 510 */       return resolutions;
/*     */     }
/* 512 */     return null;
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
/*     */   private Vector resolveAllLocalSystem(String systemId) {
/* 528 */     Vector<String> map = new Vector();
/* 529 */     String osname = SecuritySupport.getSystemProperty("os.name");
/* 530 */     boolean windows = (osname.indexOf("Windows") >= 0);
/* 531 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 532 */     while (en.hasMoreElements()) {
/* 533 */       CatalogEntry e = en.nextElement();
/* 534 */       if (e.getEntryType() == SYSTEM && (e
/* 535 */         .getEntryArg(0).equals(systemId) || (windows && e
/*     */         
/* 537 */         .getEntryArg(0).equalsIgnoreCase(systemId)))) {
/* 538 */         map.addElement(e.getEntryArg(1));
/*     */       }
/*     */     } 
/* 541 */     if (map.size() == 0) {
/* 542 */       return null;
/*     */     }
/* 544 */     return map;
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
/*     */   private Vector resolveLocalSystemReverse(String systemId) {
/* 556 */     Vector<String> map = new Vector();
/* 557 */     String osname = SecuritySupport.getSystemProperty("os.name");
/* 558 */     boolean windows = (osname.indexOf("Windows") >= 0);
/* 559 */     Enumeration<CatalogEntry> en = this.catalogEntries.elements();
/* 560 */     while (en.hasMoreElements()) {
/* 561 */       CatalogEntry e = en.nextElement();
/* 562 */       if (e.getEntryType() == SYSTEM && (e
/* 563 */         .getEntryArg(1).equals(systemId) || (windows && e
/*     */         
/* 565 */         .getEntryArg(1).equalsIgnoreCase(systemId)))) {
/* 566 */         map.addElement(e.getEntryArg(0));
/*     */       }
/*     */     } 
/* 569 */     if (map.size() == 0) {
/* 570 */       return null;
/*     */     }
/* 572 */     return map;
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
/*     */   private synchronized Vector resolveAllSubordinateCatalogs(int entityType, String entityName, String publicId, String systemId) throws MalformedURLException, IOException {
/* 610 */     Vector<String> resolutions = new Vector();
/*     */     
/* 612 */     for (int catPos = 0; catPos < this.catalogs.size(); catPos++) {
/* 613 */       Resolver c = null;
/*     */       
/*     */       try {
/* 616 */         c = this.catalogs.elementAt(catPos);
/* 617 */       } catch (ClassCastException e) {
/* 618 */         String catfile = this.catalogs.elementAt(catPos);
/* 619 */         c = (Resolver)newCatalog();
/*     */         
/*     */         try {
/* 622 */           c.parseCatalog(catfile);
/* 623 */         } catch (MalformedURLException mue) {
/* 624 */           this.catalogManager.debug.message(1, "Malformed Catalog URL", catfile);
/* 625 */         } catch (FileNotFoundException fnfe) {
/* 626 */           this.catalogManager.debug.message(1, "Failed to load catalog, file not found", catfile);
/*     */         }
/* 628 */         catch (IOException ioe) {
/* 629 */           this.catalogManager.debug.message(1, "Failed to load catalog, I/O error", catfile);
/*     */         } 
/*     */         
/* 632 */         this.catalogs.setElementAt(c, catPos);
/*     */       } 
/*     */       
/* 635 */       String resolved = null;
/*     */ 
/*     */       
/* 638 */       if (entityType == DOCTYPE)
/* 639 */       { resolved = c.resolveDoctype(entityName, publicId, systemId);
/*     */ 
/*     */         
/* 642 */         if (resolved != null) {
/*     */           
/* 644 */           resolutions.addElement(resolved);
/* 645 */           return resolutions;
/*     */         }  }
/* 647 */       else if (entityType == DOCUMENT)
/* 648 */       { resolved = c.resolveDocument();
/* 649 */         if (resolved != null) {
/*     */           
/* 651 */           resolutions.addElement(resolved);
/* 652 */           return resolutions;
/*     */         }  }
/* 654 */       else if (entityType == ENTITY)
/* 655 */       { resolved = c.resolveEntity(entityName, publicId, systemId);
/*     */ 
/*     */         
/* 658 */         if (resolved != null) {
/*     */           
/* 660 */           resolutions.addElement(resolved);
/* 661 */           return resolutions;
/*     */         }  }
/* 663 */       else if (entityType == NOTATION)
/* 664 */       { resolved = c.resolveNotation(entityName, publicId, systemId);
/*     */ 
/*     */         
/* 667 */         if (resolved != null) {
/*     */           
/* 669 */           resolutions.addElement(resolved);
/* 670 */           return resolutions;
/*     */         }  }
/* 672 */       else if (entityType == PUBLIC)
/* 673 */       { resolved = c.resolvePublic(publicId, systemId);
/* 674 */         if (resolved != null) {
/*     */           
/* 676 */           resolutions.addElement(resolved);
/* 677 */           return resolutions;
/*     */         }  }
/* 679 */       else { if (entityType == SYSTEM) {
/* 680 */           Vector localResolutions = c.resolveAllSystem(systemId);
/* 681 */           resolutions = appendVector(resolutions, localResolutions); break;
/*     */         } 
/* 683 */         if (entityType == SYSTEMREVERSE) {
/* 684 */           Vector localResolutions = c.resolveAllSystemReverse(systemId);
/* 685 */           resolutions = appendVector(resolutions, localResolutions);
/*     */         }  }
/*     */     
/*     */     } 
/* 689 */     if (resolutions != null) {
/* 690 */       return resolutions;
/*     */     }
/* 692 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/Resolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */