/*     */ package sun.net.smtp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessController;
/*     */ import sun.net.TransferProtocolClient;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SmtpClient
/*     */   extends TransferProtocolClient
/*     */ {
/*  46 */   private static int DEFAULT_SMTP_PORT = 25;
/*     */   
/*     */   String mailhost;
/*     */   
/*     */   SmtpPrintStream message;
/*     */ 
/*     */   
/*     */   public void closeServer() throws IOException {
/*  54 */     if (serverIsOpen()) {
/*  55 */       closeMessage();
/*  56 */       issueCommand("QUIT\r\n", 221);
/*  57 */       super.closeServer();
/*     */     } 
/*     */   }
/*     */   
/*     */   void issueCommand(String paramString, int paramInt) throws IOException {
/*  62 */     sendServer(paramString);
/*     */     int i;
/*  64 */     while ((i = readServerResponse()) != paramInt) {
/*  65 */       if (i != 220)
/*  66 */         throw new SmtpProtocolException(getResponseString()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toCanonical(String paramString) throws IOException {
/*  71 */     if (paramString.startsWith("<")) {
/*  72 */       issueCommand("rcpt to: " + paramString + "\r\n", 250);
/*     */     } else {
/*  74 */       issueCommand("rcpt to: <" + paramString + ">\r\n", 250);
/*     */     } 
/*     */   }
/*     */   public void to(String paramString) throws IOException {
/*  78 */     if (paramString.indexOf('\n') != -1) {
/*  79 */       throw new IOException("Illegal SMTP command", new IllegalArgumentException("Illegal carriage return"));
/*     */     }
/*     */     
/*  82 */     int i = 0;
/*  83 */     int j = paramString.length();
/*  84 */     byte b1 = 0;
/*  85 */     int k = 0;
/*  86 */     byte b2 = 0;
/*  87 */     boolean bool = false;
/*  88 */     while (b1 < j) {
/*  89 */       char c = paramString.charAt(b1);
/*  90 */       if (b2) {
/*  91 */         if (c == '(') {
/*  92 */           b2++;
/*  93 */         } else if (c == ')') {
/*  94 */           b2--;
/*  95 */         }  if (b2 == 0)
/*  96 */           if (k > i)
/*  97 */           { bool = true; }
/*     */           else
/*  99 */           { i = b1 + 1; }  
/* 100 */       } else if (c == '(') {
/* 101 */         b2++;
/* 102 */       } else if (c == '<') {
/* 103 */         i = k = b1 + 1;
/* 104 */       } else if (c == '>') {
/* 105 */         bool = true;
/* 106 */       } else if (c == ',') {
/* 107 */         if (k > i)
/* 108 */           toCanonical(paramString.substring(i, k)); 
/* 109 */         i = b1 + 1;
/* 110 */         bool = false;
/*     */       }
/* 112 */       else if (c > ' ' && !bool) {
/* 113 */         k = b1 + 1;
/* 114 */       } else if (i == b1) {
/* 115 */         i++;
/*     */       } 
/* 117 */       b1++;
/*     */     } 
/* 119 */     if (k > i)
/* 120 */       toCanonical(paramString.substring(i, k)); 
/*     */   }
/*     */   
/*     */   public void from(String paramString) throws IOException {
/* 124 */     if (paramString.indexOf('\n') != -1) {
/* 125 */       throw new IOException("Illegal SMTP command", new IllegalArgumentException("Illegal carriage return"));
/*     */     }
/*     */     
/* 128 */     if (paramString.startsWith("<")) {
/* 129 */       issueCommand("mail from: " + paramString + "\r\n", 250);
/*     */     } else {
/* 131 */       issueCommand("mail from: <" + paramString + ">\r\n", 250);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void openServer(String paramString) throws IOException {
/* 137 */     this.mailhost = paramString;
/* 138 */     openServer(this.mailhost, DEFAULT_SMTP_PORT);
/* 139 */     issueCommand("helo " + InetAddress.getLocalHost().getHostName() + "\r\n", 250);
/*     */   }
/*     */   
/*     */   public PrintStream startMessage() throws IOException {
/* 143 */     issueCommand("data\r\n", 354);
/*     */     try {
/* 145 */       this.message = new SmtpPrintStream(this.serverOutput, this);
/* 146 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 147 */       throw new InternalError(encoding + " encoding not found", unsupportedEncodingException);
/*     */     } 
/* 149 */     return this.message;
/*     */   }
/*     */   
/*     */   void closeMessage() throws IOException {
/* 153 */     if (this.message != null) {
/* 154 */       this.message.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SmtpClient(String paramString) throws IOException {
/* 160 */     if (paramString != null) {
/*     */       try {
/* 162 */         openServer(paramString);
/* 163 */         this.mailhost = paramString;
/*     */         return;
/* 165 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 170 */       this.mailhost = AccessController.<String>doPrivileged(new GetPropertyAction("mail.host"));
/*     */       
/* 172 */       if (this.mailhost != null) {
/* 173 */         openServer(this.mailhost);
/*     */         return;
/*     */       } 
/* 176 */     } catch (Exception exception) {}
/*     */     
/*     */     try {
/* 179 */       this.mailhost = "localhost";
/* 180 */       openServer(this.mailhost);
/* 181 */     } catch (Exception exception) {
/* 182 */       this.mailhost = "mailhost";
/* 183 */       openServer(this.mailhost);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SmtpClient() throws IOException {
/* 189 */     this((String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public SmtpClient(int paramInt) throws IOException {
/* 194 */     setConnectTimeout(paramInt);
/*     */     
/*     */     try {
/* 197 */       this.mailhost = AccessController.<String>doPrivileged(new GetPropertyAction("mail.host"));
/*     */       
/* 199 */       if (this.mailhost != null) {
/* 200 */         openServer(this.mailhost);
/*     */         return;
/*     */       } 
/* 203 */     } catch (Exception exception) {}
/*     */     
/*     */     try {
/* 206 */       this.mailhost = "localhost";
/* 207 */       openServer(this.mailhost);
/* 208 */     } catch (Exception exception) {
/* 209 */       this.mailhost = "mailhost";
/* 210 */       openServer(this.mailhost);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getMailHost() {
/* 215 */     return this.mailhost;
/*     */   }
/*     */   
/*     */   String getEncoding() {
/* 219 */     return encoding;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/smtp/SmtpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */