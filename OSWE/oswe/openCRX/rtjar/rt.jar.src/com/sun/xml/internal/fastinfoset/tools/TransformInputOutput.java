/*     */ package com.sun.xml.internal.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TransformInputOutput
/*     */ {
/*     */   public void parse(String[] args) throws Exception {
/*  52 */     InputStream in = null;
/*  53 */     OutputStream out = null;
/*  54 */     if (args.length == 0) {
/*  55 */       in = new BufferedInputStream(System.in);
/*  56 */       out = new BufferedOutputStream(System.out);
/*  57 */     } else if (args.length == 1) {
/*  58 */       in = new BufferedInputStream(new FileInputStream(args[0]));
/*  59 */       out = new BufferedOutputStream(System.out);
/*  60 */     } else if (args.length == 2) {
/*  61 */       in = new BufferedInputStream(new FileInputStream(args[0]));
/*  62 */       out = new BufferedOutputStream(new FileOutputStream(args[1]));
/*     */     } else {
/*  64 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.optinalFileNotSpecified"));
/*     */     } 
/*     */     
/*  67 */     parse(in, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputStream in, OutputStream out, String workingDirectory) throws Exception {
/*  75 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static URI currentJavaWorkingDirectory = (new File(System.getProperty("user.dir"))).toURI();
/*     */ 
/*     */   
/*     */   protected static EntityResolver createRelativePathResolver(final String workingDirectory) {
/*  84 */     return new EntityResolver() {
/*     */         public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/*  86 */           if (systemId != null && systemId.startsWith("file:/")) {
/*  87 */             URI workingDirectoryURI = (new File(workingDirectory)).toURI();
/*     */ 
/*     */             
/*     */             try {
/*  91 */               URI workingFile = TransformInputOutput.convertToNewWorkingDirectory(TransformInputOutput.currentJavaWorkingDirectory, workingDirectoryURI, (new File(new URI(systemId))).toURI());
/*  92 */               return new InputSource(workingFile.toString());
/*  93 */             } catch (URISyntaxException uRISyntaxException) {}
/*     */           } 
/*     */ 
/*     */           
/*  97 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static URI convertToNewWorkingDirectory(URI oldwd, URI newwd, URI file) throws IOException, URISyntaxException {
/* 103 */     String oldwdStr = oldwd.toString();
/* 104 */     String newwdStr = newwd.toString();
/* 105 */     String fileStr = file.toString();
/*     */     
/* 107 */     String cmpStr = null;
/*     */     
/* 109 */     if (fileStr.startsWith(oldwdStr) && (cmpStr = fileStr.substring(oldwdStr.length())).indexOf('/') == -1) {
/* 110 */       return new URI(newwdStr + '/' + cmpStr);
/*     */     }
/*     */     
/* 113 */     String[] oldwdSplit = oldwdStr.split("/");
/* 114 */     String[] newwdSplit = newwdStr.split("/");
/* 115 */     String[] fileSplit = fileStr.split("/");
/*     */     
/*     */     int diff;
/* 118 */     for (diff = 0; diff < oldwdSplit.length && diff < fileSplit.length && 
/* 119 */       oldwdSplit[diff].equals(fileSplit[diff]); diff++);
/*     */ 
/*     */     
/*     */     int diffNew;
/*     */ 
/*     */     
/* 125 */     for (diffNew = 0; diffNew < newwdSplit.length && diffNew < fileSplit.length && 
/* 126 */       newwdSplit[diffNew].equals(fileSplit[diffNew]); diffNew++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (diffNew > diff) {
/* 134 */       return file;
/*     */     }
/*     */     
/* 137 */     int elemsToSub = oldwdSplit.length - diff;
/* 138 */     StringBuffer resultStr = new StringBuffer(100); int i;
/* 139 */     for (i = 0; i < newwdSplit.length - elemsToSub; i++) {
/* 140 */       resultStr.append(newwdSplit[i]);
/* 141 */       resultStr.append('/');
/*     */     } 
/*     */     
/* 144 */     for (i = diff; i < fileSplit.length; i++) {
/* 145 */       resultStr.append(fileSplit[i]);
/* 146 */       if (i < fileSplit.length - 1) resultStr.append('/');
/*     */     
/*     */     } 
/* 149 */     return new URI(resultStr.toString());
/*     */   }
/*     */   
/*     */   public abstract void parse(InputStream paramInputStream, OutputStream paramOutputStream) throws Exception;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/tools/TransformInputOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */