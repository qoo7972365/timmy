/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileSystemException
/*     */   extends IOException
/*     */ {
/*     */   static final long serialVersionUID = -3055425747967319812L;
/*     */   private final String file;
/*     */   private final String other;
/*     */   
/*     */   public FileSystemException(String paramString) {
/*  54 */     super((String)null);
/*  55 */     this.file = paramString;
/*  56 */     this.other = null;
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
/*     */   public FileSystemException(String paramString1, String paramString2, String paramString3) {
/*  73 */     super(paramString3);
/*  74 */     this.file = paramString1;
/*  75 */     this.other = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFile() {
/*  84 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOtherFile() {
/*  93 */     return this.other;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() {
/* 102 */     return super.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 110 */     if (this.file == null && this.other == null)
/* 111 */       return getReason(); 
/* 112 */     StringBuilder stringBuilder = new StringBuilder();
/* 113 */     if (this.file != null)
/* 114 */       stringBuilder.append(this.file); 
/* 115 */     if (this.other != null) {
/* 116 */       stringBuilder.append(" -> ");
/* 117 */       stringBuilder.append(this.other);
/*     */     } 
/* 119 */     if (getReason() != null) {
/* 120 */       stringBuilder.append(": ");
/* 121 */       stringBuilder.append(getReason());
/*     */     } 
/* 123 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/FileSystemException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */