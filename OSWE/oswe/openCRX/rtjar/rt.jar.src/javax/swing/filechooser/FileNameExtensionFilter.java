/*     */ package javax.swing.filechooser;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileNameExtensionFilter
/*     */   extends FileFilter
/*     */ {
/*     */   private final String description;
/*     */   private final String[] extensions;
/*     */   private final String[] lowerCaseExtensions;
/*     */   
/*     */   public FileNameExtensionFilter(String paramString, String... paramVarArgs) {
/*  75 */     if (paramVarArgs == null || paramVarArgs.length == 0) {
/*  76 */       throw new IllegalArgumentException("Extensions must be non-null and not empty");
/*     */     }
/*     */     
/*  79 */     this.description = paramString;
/*  80 */     this.extensions = new String[paramVarArgs.length];
/*  81 */     this.lowerCaseExtensions = new String[paramVarArgs.length];
/*  82 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/*  83 */       if (paramVarArgs[b] == null || paramVarArgs[b].length() == 0) {
/*  84 */         throw new IllegalArgumentException("Each extension must be non-null and not empty");
/*     */       }
/*     */       
/*  87 */       this.extensions[b] = paramVarArgs[b];
/*  88 */       this.lowerCaseExtensions[b] = paramVarArgs[b].toLowerCase(Locale.ENGLISH);
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
/*     */   public boolean accept(File paramFile) {
/* 102 */     if (paramFile != null) {
/* 103 */       if (paramFile.isDirectory()) {
/* 104 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       String str = paramFile.getName();
/* 112 */       int i = str.lastIndexOf('.');
/* 113 */       if (i > 0 && i < str.length() - 1) {
/*     */         
/* 115 */         String str1 = str.substring(i + 1).toLowerCase(Locale.ENGLISH);
/* 116 */         for (String str2 : this.lowerCaseExtensions) {
/* 117 */           if (str1.equals(str2)) {
/* 118 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 132 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getExtensions() {
/* 141 */     String[] arrayOfString = new String[this.extensions.length];
/* 142 */     System.arraycopy(this.extensions, 0, arrayOfString, 0, this.extensions.length);
/* 143 */     return arrayOfString;
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
/*     */   public String toString() {
/* 155 */     return super.toString() + "[description=" + getDescription() + " extensions=" + 
/* 156 */       Arrays.<String>asList(getExtensions()) + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/filechooser/FileNameExtensionFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */