/*     */ package sun.tools.jar;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import sun.net.www.MessageHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Manifest
/*     */ {
/*  49 */   private Vector<MessageHeader> entries = new Vector<>();
/*  50 */   private byte[] tmpbuf = new byte[512];
/*     */   
/*  52 */   private Hashtable<String, MessageHeader> tableEntries = new Hashtable<>();
/*     */   
/*  54 */   static final String[] hashes = new String[] { "SHA" };
/*  55 */   static final byte[] EOL = new byte[] { 13, 10 };
/*     */   
/*     */   static final boolean debug = false;
/*     */   
/*     */   static final String VERSION = "1.0";
/*     */ 
/*     */   
/*     */   static final void debug(String paramString) {}
/*     */   
/*     */   public Manifest() {}
/*     */   
/*     */   public Manifest(byte[] paramArrayOfbyte) throws IOException {
/*  67 */     this(new ByteArrayInputStream(paramArrayOfbyte), false);
/*     */   }
/*     */   
/*     */   public Manifest(InputStream paramInputStream) throws IOException {
/*  71 */     this(paramInputStream, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest(InputStream paramInputStream, boolean paramBoolean) throws IOException {
/*  79 */     if (!paramInputStream.markSupported()) {
/*  80 */       paramInputStream = new BufferedInputStream(paramInputStream);
/*     */     }
/*     */     
/*     */     while (true) {
/*  84 */       paramInputStream.mark(1);
/*  85 */       if (paramInputStream.read() == -1) {
/*     */         break;
/*     */       }
/*  88 */       paramInputStream.reset();
/*  89 */       MessageHeader messageHeader = new MessageHeader(paramInputStream);
/*  90 */       if (paramBoolean) {
/*  91 */         doHashes(messageHeader);
/*     */       }
/*  93 */       addEntry(messageHeader);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest(String[] paramArrayOfString) throws IOException {
/*  99 */     MessageHeader messageHeader = new MessageHeader();
/* 100 */     messageHeader.add("Manifest-Version", "1.0");
/* 101 */     String str = System.getProperty("java.version");
/* 102 */     messageHeader.add("Created-By", "Manifest JDK " + str);
/* 103 */     addEntry(messageHeader);
/* 104 */     addFiles(null, paramArrayOfString);
/*     */   }
/*     */   
/*     */   public void addEntry(MessageHeader paramMessageHeader) {
/* 108 */     this.entries.addElement(paramMessageHeader);
/* 109 */     String str = paramMessageHeader.findValue("Name");
/* 110 */     debug("addEntry for name: " + str);
/* 111 */     if (str != null) {
/* 112 */       this.tableEntries.put(str, paramMessageHeader);
/*     */     }
/*     */   }
/*     */   
/*     */   public MessageHeader getEntry(String paramString) {
/* 117 */     return this.tableEntries.get(paramString);
/*     */   }
/*     */   
/*     */   public MessageHeader entryAt(int paramInt) {
/* 121 */     return this.entries.elementAt(paramInt);
/*     */   }
/*     */   
/*     */   public Enumeration<MessageHeader> entries() {
/* 125 */     return this.entries.elements();
/*     */   }
/*     */   
/*     */   public void addFiles(File paramFile, String[] paramArrayOfString) throws IOException {
/* 129 */     if (paramArrayOfString == null)
/*     */       return; 
/* 131 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*     */       File file;
/* 133 */       if (paramFile == null) {
/* 134 */         file = new File(paramArrayOfString[b]);
/*     */       } else {
/* 136 */         file = new File(paramFile, paramArrayOfString[b]);
/*     */       } 
/* 138 */       if (file.isDirectory()) {
/* 139 */         addFiles(file, file.list());
/*     */       } else {
/* 141 */         addFile(file);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String stdToLocal(String paramString) {
/* 152 */     return paramString.replace('/', File.separatorChar);
/*     */   }
/*     */   
/*     */   private final String localToStd(String paramString) {
/* 156 */     paramString = paramString.replace(File.separatorChar, '/');
/* 157 */     if (paramString.startsWith("./")) {
/* 158 */       paramString = paramString.substring(2);
/* 159 */     } else if (paramString.startsWith("/")) {
/* 160 */       paramString = paramString.substring(1);
/* 161 */     }  return paramString;
/*     */   }
/*     */   
/*     */   public void addFile(File paramFile) throws IOException {
/* 165 */     String str = localToStd(paramFile.getPath());
/* 166 */     if (this.tableEntries.get(str) == null) {
/* 167 */       MessageHeader messageHeader = new MessageHeader();
/* 168 */       messageHeader.add("Name", str);
/* 169 */       addEntry(messageHeader);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doHashes(MessageHeader paramMessageHeader) throws IOException {
/* 175 */     String str = paramMessageHeader.findValue("Name");
/* 176 */     if (str == null || str.endsWith("/")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     for (byte b = 0; b < hashes.length; b++) {
/* 183 */       FileInputStream fileInputStream = new FileInputStream(stdToLocal(str));
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
/*     */   public void stream(OutputStream paramOutputStream) throws IOException {
/*     */     PrintStream printStream;
/* 206 */     if (paramOutputStream instanceof PrintStream) {
/* 207 */       printStream = (PrintStream)paramOutputStream;
/*     */     } else {
/* 209 */       printStream = new PrintStream(paramOutputStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     MessageHeader messageHeader = this.entries.elementAt(0);
/*     */     
/* 217 */     if (messageHeader.findValue("Manifest-Version") == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       String str = System.getProperty("java.version");
/*     */       
/* 226 */       if (messageHeader.findValue("Name") == null) {
/* 227 */         messageHeader.prepend("Manifest-Version", "1.0");
/* 228 */         messageHeader.add("Created-By", "Manifest JDK " + str);
/*     */       } else {
/* 230 */         printStream.print("Manifest-Version: 1.0\r\nCreated-By: " + str + "\r\n\r\n");
/*     */       } 
/*     */       
/* 233 */       printStream.flush();
/*     */     } 
/*     */     
/* 236 */     messageHeader.print(printStream);
/*     */     
/* 238 */     for (byte b = 1; b < this.entries.size(); b++) {
/* 239 */       MessageHeader messageHeader1 = this.entries.elementAt(b);
/* 240 */       messageHeader1.print(printStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isManifestName(String paramString) {
/* 247 */     if (paramString.charAt(0) == '/') {
/* 248 */       paramString = paramString.substring(1, paramString.length());
/*     */     }
/*     */     
/* 251 */     paramString = paramString.toUpperCase();
/*     */     
/* 253 */     if (paramString.equals("META-INF/MANIFEST.MF")) {
/* 254 */       return true;
/*     */     }
/* 256 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tools/jar/Manifest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */