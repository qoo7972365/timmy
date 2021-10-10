/*     */ package com.sun.corba.se.impl.resolver;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileResolverImpl
/*     */   implements Resolver
/*     */ {
/*     */   private ORB orb;
/*     */   private File file;
/*     */   private Properties savedProps;
/*  49 */   private long fileModified = 0L;
/*     */ 
/*     */   
/*     */   public FileResolverImpl(ORB paramORB, File paramFile) {
/*  53 */     this.orb = paramORB;
/*  54 */     this.file = paramFile;
/*  55 */     this.savedProps = new Properties();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object resolve(String paramString) {
/*  60 */     check();
/*  61 */     String str = this.savedProps.getProperty(paramString);
/*  62 */     if (str == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     return this.orb.string_to_object(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set list() {
/*  70 */     check();
/*     */     
/*  72 */     HashSet hashSet = new HashSet();
/*     */ 
/*     */     
/*  75 */     Enumeration<?> enumeration = this.savedProps.propertyNames();
/*  76 */     while (enumeration.hasMoreElements()) {
/*  77 */       hashSet.add(enumeration.nextElement());
/*     */     }
/*     */     
/*  80 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void check() {
/*  89 */     if (this.file == null) {
/*     */       return;
/*     */     }
/*  92 */     long l = this.file.lastModified();
/*  93 */     if (l > this.fileModified)
/*     */       try {
/*  95 */         FileInputStream fileInputStream = new FileInputStream(this.file);
/*  96 */         this.savedProps.clear();
/*  97 */         this.savedProps.load(fileInputStream);
/*  98 */         fileInputStream.close();
/*  99 */         this.fileModified = l;
/* 100 */       } catch (FileNotFoundException fileNotFoundException) {
/* 101 */         System.err.println(CorbaResourceUtil.getText("bootstrap.filenotfound", this.file
/* 102 */               .getAbsolutePath()));
/* 103 */       } catch (IOException iOException) {
/* 104 */         System.err.println(CorbaResourceUtil.getText("bootstrap.exception", this.file
/*     */               
/* 106 */               .getAbsolutePath(), iOException.toString()));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/FileResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */