/*     */ package sun.net.smtp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SmtpPrintStream
/*     */   extends PrintStream
/*     */ {
/*     */   private SmtpClient target;
/* 225 */   private int lastc = 10;
/*     */   
/*     */   SmtpPrintStream(OutputStream paramOutputStream, SmtpClient paramSmtpClient) throws UnsupportedEncodingException {
/* 228 */     super(paramOutputStream, false, paramSmtpClient.getEncoding());
/* 229 */     this.target = paramSmtpClient;
/*     */   }
/*     */   
/*     */   public void close() {
/* 233 */     if (this.target == null)
/*     */       return; 
/* 235 */     if (this.lastc != 10) {
/* 236 */       write(10);
/*     */     }
/*     */     try {
/* 239 */       this.target.issueCommand(".\r\n", 250);
/* 240 */       this.target.message = null;
/* 241 */       this.out = null;
/* 242 */       this.target = null;
/* 243 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) {
/*     */     try {
/* 250 */       if (this.lastc == 10 && paramInt == 46) {
/* 251 */         this.out.write(46);
/*     */       }
/*     */ 
/*     */       
/* 255 */       if (paramInt == 10 && this.lastc != 13) {
/* 256 */         this.out.write(13);
/*     */       }
/* 258 */       this.out.write(paramInt);
/* 259 */       this.lastc = paramInt;
/* 260 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*     */     try {
/* 266 */       int i = this.lastc;
/* 267 */       while (--paramInt2 >= 0) {
/* 268 */         byte b = paramArrayOfbyte[paramInt1++];
/*     */ 
/*     */         
/* 271 */         if (i == 10 && b == 46) {
/* 272 */           this.out.write(46);
/*     */         }
/*     */         
/* 275 */         if (b == 10 && i != 13) {
/* 276 */           this.out.write(13);
/*     */         }
/* 278 */         this.out.write(b);
/* 279 */         i = b;
/*     */       } 
/* 281 */       this.lastc = i;
/* 282 */     } catch (IOException iOException) {}
/*     */   }
/*     */   
/*     */   public void print(String paramString) {
/* 286 */     int i = paramString.length();
/* 287 */     for (byte b = 0; b < i; b++)
/* 288 */       write(paramString.charAt(b)); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/smtp/SmtpPrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */