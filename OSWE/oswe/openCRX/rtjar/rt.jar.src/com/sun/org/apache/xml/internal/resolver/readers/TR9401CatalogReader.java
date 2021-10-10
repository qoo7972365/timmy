/*     */ package com.sun.org.apache.xml.internal.resolver.readers;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TR9401CatalogReader
/*     */   extends TextCatalogReader
/*     */ {
/*     */   public void readCatalog(Catalog catalog, InputStream is) throws MalformedURLException, IOException {
/*  74 */     this.catfile = is;
/*     */     
/*  76 */     if (this.catfile == null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     Vector<String> unknownEntry = null;
/*     */     
/*     */     try {
/*     */       while (true) {
/*  84 */         String token = nextToken();
/*     */         
/*  86 */         if (token == null) {
/*  87 */           if (unknownEntry != null) {
/*  88 */             catalog.unknownEntry(unknownEntry);
/*  89 */             unknownEntry = null;
/*     */           } 
/*  91 */           this.catfile.close();
/*  92 */           this.catfile = null;
/*     */           
/*     */           return;
/*     */         } 
/*  96 */         String entryToken = null;
/*  97 */         if (this.caseSensitive) {
/*  98 */           entryToken = token;
/*     */         } else {
/* 100 */           entryToken = token.toUpperCase();
/*     */         } 
/*     */         
/* 103 */         if (entryToken.equals("DELEGATE")) {
/* 104 */           entryToken = "DELEGATE_PUBLIC";
/*     */         }
/*     */         
/*     */         try {
/* 108 */           int type = CatalogEntry.getEntryType(entryToken);
/* 109 */           int numArgs = CatalogEntry.getEntryArgCount(type);
/* 110 */           Vector<String> args = new Vector();
/*     */           
/* 112 */           if (unknownEntry != null) {
/* 113 */             catalog.unknownEntry(unknownEntry);
/* 114 */             unknownEntry = null;
/*     */           } 
/*     */           
/* 117 */           for (int count = 0; count < numArgs; count++) {
/* 118 */             args.addElement(nextToken());
/*     */           }
/*     */           
/* 121 */           catalog.addEntry(new CatalogEntry(entryToken, args));
/* 122 */         } catch (CatalogException cex) {
/* 123 */           if (cex.getExceptionType() == 3) {
/* 124 */             if (unknownEntry == null) {
/* 125 */               unknownEntry = new Vector();
/*     */             }
/* 127 */             unknownEntry.addElement(token); continue;
/* 128 */           }  if (cex.getExceptionType() == 2) {
/* 129 */             (catalog.getCatalogManager()).debug.message(1, "Invalid catalog entry", token);
/* 130 */             unknownEntry = null; continue;
/* 131 */           }  if (cex.getExceptionType() == 8) {
/* 132 */             (catalog.getCatalogManager()).debug.message(1, cex.getMessage());
/*     */           }
/*     */         } 
/*     */       } 
/* 136 */     } catch (CatalogException cex2) {
/* 137 */       if (cex2.getExceptionType() == 8)
/* 138 */         (catalog.getCatalogManager()).debug.message(1, cex2.getMessage()); 
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/readers/TR9401CatalogReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */