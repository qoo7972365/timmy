/*      */ package sun.net.ftp.impl;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.Closeable;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.Proxy;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketAddress;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLSession;
/*      */ import javax.net.ssl.SSLSocket;
/*      */ import javax.net.ssl.SSLSocketFactory;
/*      */ import sun.misc.BASE64Decoder;
/*      */ import sun.misc.BASE64Encoder;
/*      */ import sun.net.TelnetInputStream;
/*      */ import sun.net.TelnetOutputStream;
/*      */ import sun.net.ftp.FtpClient;
/*      */ import sun.net.ftp.FtpDirEntry;
/*      */ import sun.net.ftp.FtpDirParser;
/*      */ import sun.net.ftp.FtpProtocolException;
/*      */ import sun.net.ftp.FtpReplyCode;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ public class FtpClient
/*      */   extends FtpClient
/*      */ {
/*      */   private static int defaultSoTimeout;
/*      */   private static int defaultConnectTimeout;
/*   56 */   private static final PlatformLogger logger = PlatformLogger.getLogger("sun.net.ftp.FtpClient");
/*      */   private Proxy proxy;
/*      */   private Socket server;
/*      */   private PrintStream out;
/*      */   private InputStream in;
/*   61 */   private int readTimeout = -1;
/*   62 */   private int connectTimeout = -1;
/*      */ 
/*      */   
/*   65 */   private static String encoding = "ISO8859_1";
/*      */   
/*      */   private InetSocketAddress serverAddr;
/*      */   
/*      */   private boolean replyPending = false;
/*      */   private boolean loggedIn = false;
/*      */   private boolean useCrypto = false;
/*      */   private SSLSocketFactory sslFact;
/*      */   private Socket oldSocket;
/*   74 */   private Vector<String> serverResponse = new Vector<>(1);
/*      */   
/*   76 */   private FtpReplyCode lastReplyCode = null;
/*      */ 
/*      */   
/*      */   private String welcomeMsg;
/*      */   
/*      */   private final boolean passiveMode = true;
/*      */   
/*   83 */   private FtpClient.TransferType type = FtpClient.TransferType.BINARY;
/*   84 */   private long restartOffset = 0L;
/*   85 */   private long lastTransSize = -1L;
/*      */ 
/*      */   
/*      */   private String lastFileName;
/*      */   
/*   90 */   private static String[] patStrings = new String[] { "([\\-ld](?:[r\\-][w\\-][x\\-]){3})\\s*\\d+ (\\w+)\\s*(\\w+)\\s*(\\d+)\\s*([A-Z][a-z][a-z]\\s*\\d+)\\s*(\\d\\d:\\d\\d)\\s*(\\p{Print}*)", "([\\-ld](?:[r\\-][w\\-][x\\-]){3})\\s*\\d+ (\\w+)\\s*(\\w+)\\s*(\\d+)\\s*([A-Z][a-z][a-z]\\s*\\d+)\\s*(\\d{4})\\s*(\\p{Print}*)", "(\\d{2}/\\d{2}/\\d{4})\\s*(\\d{2}:\\d{2}[ap])\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)", "(\\d{2}-\\d{2}-\\d{2})\\s*(\\d{2}:\\d{2}[AP]M)\\s*((?:[0-9,]+)|(?:<DIR>))\\s*(\\p{Graph}*)" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   private static int[][] patternGroups = new int[][] { { 7, 4, 5, 6, 0, 1, 2, 3 }, { 7, 4, 5, 0, 6, 1, 2, 3 }, { 4, 3, 1, 2, 0, 0, 0, 0 }, { 4, 3, 1, 2, 0, 0, 0, 0 } };
/*      */ 
/*      */ 
/*      */   
/*      */   private static Pattern[] patterns;
/*      */ 
/*      */ 
/*      */   
/*  108 */   private static Pattern linkp = Pattern.compile("(\\p{Print}+) \\-\\> (\\p{Print}+)$");
/*  109 */   private DateFormat df = DateFormat.getDateInstance(2, Locale.US); private static boolean isASCIISuperset(String paramString) throws Exception { String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,"; byte[] arrayOfByte1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 45, 95, 46, 33, 126, 42, 39, 40, 41, 59, 47, 63, 58, 64, 38, 61, 43, 36, 44 }; byte[] arrayOfByte2 = str.getBytes(paramString); return Arrays.equals(arrayOfByte2, arrayOfByte1); } private class DefaultParser implements FtpDirParser {
/*      */     private DefaultParser() {} public FtpDirEntry parseLine(String param1String) { String str1 = null; String str2 = null; String str3 = null; String str4 = null; String str5 = null; String str6 = null; String str7 = null; boolean bool = false; Calendar calendar = Calendar.getInstance(); int i = calendar.get(1); Matcher matcher = null; for (byte b = 0; b < FtpClient.patterns.length; b++) { matcher = FtpClient.patterns[b].matcher(param1String); if (matcher.find()) { str4 = matcher.group(FtpClient.patternGroups[b][0]); str2 = matcher.group(FtpClient.patternGroups[b][1]); str1 = matcher.group(FtpClient.patternGroups[b][2]); if (FtpClient.patternGroups[b][4] > 0) { str1 = str1 + ", " + matcher.group(FtpClient.patternGroups[b][4]); } else if (FtpClient.patternGroups[b][3] > 0) { str1 = str1 + ", " + String.valueOf(i); }  if (FtpClient.patternGroups[b][3] > 0) str3 = matcher.group(FtpClient.patternGroups[b][3]);  if (FtpClient.patternGroups[b][5] > 0) { str5 = matcher.group(FtpClient.patternGroups[b][5]); bool = str5.startsWith("d"); }  if (FtpClient.patternGroups[b][6] > 0) str6 = matcher.group(FtpClient.patternGroups[b][6]);  if (FtpClient.patternGroups[b][7] > 0) str7 = matcher.group(FtpClient.patternGroups[b][7]);  if ("<DIR>".equals(str2)) { bool = true; str2 = null; }  }  }  if (str4 != null) { Date date; try { date = FtpClient.this.df.parse(str1); } catch (Exception exception) { date = null; }  if (date != null && str3 != null) { int j = str3.indexOf(":"); calendar.setTime(date); calendar.set(10, Integer.parseInt(str3.substring(0, j))); calendar.set(12, Integer.parseInt(str3.substring(j + 1))); date = calendar.getTime(); }  Matcher matcher1 = FtpClient.linkp.matcher(str4); if (matcher1.find()) str4 = matcher1.group(1);  boolean[][] arrayOfBoolean = new boolean[3][3]; for (byte b1 = 0; b1 < 3; b1++) { for (byte b2 = 0; b2 < 3; b2++) arrayOfBoolean[b1][b2] = (str5.charAt(b1 * 3 + b2) != '-');  }  FtpDirEntry ftpDirEntry = new FtpDirEntry(str4); ftpDirEntry.setUser(str6).setGroup(str7); ftpDirEntry.setSize(Long.parseLong(str2)).setLastModified(date); ftpDirEntry.setPermissions(arrayOfBoolean); ftpDirEntry.setType(bool ? FtpDirEntry.Type.DIR : ((param1String.charAt(0) == 'l') ? FtpDirEntry.Type.LINK : FtpDirEntry.Type.FILE)); return ftpDirEntry; }  return null; } } private class MLSxParser implements FtpDirParser {
/*      */     private SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); public FtpDirEntry parseLine(String param1String) { String str1 = null; int i = param1String.lastIndexOf(";"); if (i > 0) { str1 = param1String.substring(i + 1).trim(); param1String = param1String.substring(0, i); } else { str1 = param1String.trim(); param1String = ""; }  FtpDirEntry ftpDirEntry = new FtpDirEntry(str1); while (!param1String.isEmpty()) { String str; i = param1String.indexOf(";"); if (i > 0) { str = param1String.substring(0, i); param1String = param1String.substring(i + 1); } else { str = param1String; param1String = ""; }  i = str.indexOf("="); if (i > 0) { String str3 = str.substring(0, i); String str4 = str.substring(i + 1); ftpDirEntry.addFact(str3, str4); }  }  String str2 = ftpDirEntry.getFact("Size"); if (str2 != null) ftpDirEntry.setSize(Long.parseLong(str2));  str2 = ftpDirEntry.getFact("Modify"); if (str2 != null) { Date date = null; try { date = this.df.parse(str2); } catch (ParseException parseException) {} if (date != null) ftpDirEntry.setLastModified(date);  }  str2 = ftpDirEntry.getFact("Create"); if (str2 != null) { Date date = null; try { date = this.df.parse(str2); } catch (ParseException parseException) {} if (date != null) ftpDirEntry.setCreated(date);  }  str2 = ftpDirEntry.getFact("Type"); if (str2 != null) { if (str2.equalsIgnoreCase("file")) ftpDirEntry.setType(FtpDirEntry.Type.FILE);  if (str2.equalsIgnoreCase("dir")) ftpDirEntry.setType(FtpDirEntry.Type.DIR);  if (str2.equalsIgnoreCase("cdir")) ftpDirEntry.setType(FtpDirEntry.Type.CDIR);  if (str2.equalsIgnoreCase("pdir")) ftpDirEntry.setType(FtpDirEntry.Type.PDIR);  }  return ftpDirEntry; } private MLSxParser() {} } private FtpDirParser parser = new DefaultParser(); private FtpDirParser mlsxParser = new MLSxParser(); private static Pattern transPat; private static Pattern epsvPat; private static Pattern pasvPat; private static String[] MDTMformats; private static SimpleDateFormat[] dateFormats; private void getTransferSize() { this.lastTransSize = -1L; String str = getLastResponseString(); if (transPat == null) transPat = Pattern.compile("150 Opening .*\\((\\d+) bytes\\).");  Matcher matcher = transPat.matcher(str); if (matcher.find()) { String str1 = matcher.group(1); this.lastTransSize = Long.parseLong(str1); }  } private void getTransferName() { this.lastFileName = null; String str = getLastResponseString(); int i = str.indexOf("unique file name:"); int j = str.lastIndexOf(')'); if (i >= 0) { i += 17; this.lastFileName = str.substring(i, j); }  } private int readServerResponse() throws IOException { // Byte code:
/*      */     //   0: new java/lang/StringBuffer
/*      */     //   3: dup
/*      */     //   4: bipush #32
/*      */     //   6: invokespecial <init> : (I)V
/*      */     //   9: astore_1
/*      */     //   10: iconst_m1
/*      */     //   11: istore_3
/*      */     //   12: aload_0
/*      */     //   13: getfield serverResponse : Ljava/util/Vector;
/*      */     //   16: iconst_0
/*      */     //   17: invokevirtual setSize : (I)V
/*      */     //   20: aload_0
/*      */     //   21: getfield in : Ljava/io/InputStream;
/*      */     //   24: invokevirtual read : ()I
/*      */     //   27: dup
/*      */     //   28: istore_2
/*      */     //   29: iconst_m1
/*      */     //   30: if_icmpeq -> 76
/*      */     //   33: iload_2
/*      */     //   34: bipush #13
/*      */     //   36: if_icmpne -> 60
/*      */     //   39: aload_0
/*      */     //   40: getfield in : Ljava/io/InputStream;
/*      */     //   43: invokevirtual read : ()I
/*      */     //   46: dup
/*      */     //   47: istore_2
/*      */     //   48: bipush #10
/*      */     //   50: if_icmpeq -> 60
/*      */     //   53: aload_1
/*      */     //   54: bipush #13
/*      */     //   56: invokevirtual append : (C)Ljava/lang/StringBuffer;
/*      */     //   59: pop
/*      */     //   60: aload_1
/*      */     //   61: iload_2
/*      */     //   62: i2c
/*      */     //   63: invokevirtual append : (C)Ljava/lang/StringBuffer;
/*      */     //   66: pop
/*      */     //   67: iload_2
/*      */     //   68: bipush #10
/*      */     //   70: if_icmpne -> 20
/*      */     //   73: goto -> 76
/*      */     //   76: aload_1
/*      */     //   77: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   80: astore #5
/*      */     //   82: aload_1
/*      */     //   83: iconst_0
/*      */     //   84: invokevirtual setLength : (I)V
/*      */     //   87: getstatic sun/net/ftp/impl/FtpClient.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   90: getstatic sun/util/logging/PlatformLogger$Level.FINEST : Lsun/util/logging/PlatformLogger$Level;
/*      */     //   93: invokevirtual isLoggable : (Lsun/util/logging/PlatformLogger$Level;)Z
/*      */     //   96: ifeq -> 137
/*      */     //   99: getstatic sun/net/ftp/impl/FtpClient.logger : Lsun/util/logging/PlatformLogger;
/*      */     //   102: new java/lang/StringBuilder
/*      */     //   105: dup
/*      */     //   106: invokespecial <init> : ()V
/*      */     //   109: ldc 'Server ['
/*      */     //   111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   114: aload_0
/*      */     //   115: getfield serverAddr : Ljava/net/InetSocketAddress;
/*      */     //   118: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   121: ldc '] --> '
/*      */     //   123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   126: aload #5
/*      */     //   128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   131: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   134: invokevirtual finest : (Ljava/lang/String;)V
/*      */     //   137: aload #5
/*      */     //   139: invokevirtual length : ()I
/*      */     //   142: ifne -> 151
/*      */     //   145: iconst_m1
/*      */     //   146: istore #4
/*      */     //   148: goto -> 179
/*      */     //   151: aload #5
/*      */     //   153: iconst_0
/*      */     //   154: iconst_3
/*      */     //   155: invokevirtual substring : (II)Ljava/lang/String;
/*      */     //   158: invokestatic parseInt : (Ljava/lang/String;)I
/*      */     //   161: istore #4
/*      */     //   163: goto -> 179
/*      */     //   166: astore #6
/*      */     //   168: iconst_m1
/*      */     //   169: istore #4
/*      */     //   171: goto -> 179
/*      */     //   174: astore #6
/*      */     //   176: goto -> 20
/*      */     //   179: aload_0
/*      */     //   180: getfield serverResponse : Ljava/util/Vector;
/*      */     //   183: aload #5
/*      */     //   185: invokevirtual addElement : (Ljava/lang/Object;)V
/*      */     //   188: iload_3
/*      */     //   189: iconst_m1
/*      */     //   190: if_icmpeq -> 227
/*      */     //   193: iload #4
/*      */     //   195: iload_3
/*      */     //   196: if_icmpne -> 20
/*      */     //   199: aload #5
/*      */     //   201: invokevirtual length : ()I
/*      */     //   204: iconst_4
/*      */     //   205: if_icmplt -> 222
/*      */     //   208: aload #5
/*      */     //   210: iconst_3
/*      */     //   211: invokevirtual charAt : (I)C
/*      */     //   214: bipush #45
/*      */     //   216: if_icmpne -> 222
/*      */     //   219: goto -> 20
/*      */     //   222: iconst_m1
/*      */     //   223: istore_3
/*      */     //   224: goto -> 253
/*      */     //   227: aload #5
/*      */     //   229: invokevirtual length : ()I
/*      */     //   232: iconst_4
/*      */     //   233: if_icmplt -> 253
/*      */     //   236: aload #5
/*      */     //   238: iconst_3
/*      */     //   239: invokevirtual charAt : (I)C
/*      */     //   242: bipush #45
/*      */     //   244: if_icmpne -> 253
/*      */     //   247: iload #4
/*      */     //   249: istore_3
/*      */     //   250: goto -> 20
/*      */     //   253: iload #4
/*      */     //   255: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #412	-> 0
/*      */     //   #414	-> 10
/*      */     //   #418	-> 12
/*      */     //   #420	-> 20
/*      */     //   #421	-> 33
/*      */     //   #422	-> 39
/*      */     //   #423	-> 53
/*      */     //   #426	-> 60
/*      */     //   #427	-> 67
/*      */     //   #428	-> 73
/*      */     //   #431	-> 76
/*      */     //   #432	-> 82
/*      */     //   #433	-> 87
/*      */     //   #434	-> 99
/*      */     //   #437	-> 137
/*      */     //   #438	-> 145
/*      */     //   #441	-> 151
/*      */     //   #448	-> 163
/*      */     //   #442	-> 166
/*      */     //   #443	-> 168
/*      */     //   #448	-> 171
/*      */     //   #444	-> 174
/*      */     //   #447	-> 176
/*      */     //   #450	-> 179
/*      */     //   #451	-> 188
/*      */     //   #453	-> 193
/*      */     //   #454	-> 201
/*      */     //   #455	-> 219
/*      */     //   #458	-> 222
/*      */     //   #459	-> 224
/*      */     //   #461	-> 227
/*      */     //   #462	-> 247
/*      */     //   #463	-> 250
/*      */     //   #469	-> 253
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   151	163	166	java/lang/NumberFormatException
/*      */     //   151	163	174	java/lang/StringIndexOutOfBoundsException } private void sendServer(String paramString) { this.out.print(paramString); if (logger.isLoggable(PlatformLogger.Level.FINEST)) logger.finest("Server [" + this.serverAddr + "] <-- " + paramString);  } private String getResponseString() { return this.serverResponse.elementAt(0); } private Vector<String> getResponseStrings() { return this.serverResponse; } private boolean readReply() throws IOException { this.lastReplyCode = FtpReplyCode.find(readServerResponse()); if (this.lastReplyCode.isPositivePreliminary()) { this.replyPending = true; return true; }  if (this.lastReplyCode.isPositiveCompletion() || this.lastReplyCode.isPositiveIntermediate()) { if (this.lastReplyCode == FtpReplyCode.CLOSING_DATA_CONNECTION) getTransferName();  return true; }  return false; }
/*  112 */   static { final int[] vals = { 0, 0 };
/*  113 */     final String[] encs = { null };
/*      */     
/*  115 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run()
/*      */           {
/*  119 */             vals[0] = Integer.getInteger("sun.net.client.defaultReadTimeout", 300000).intValue();
/*  120 */             vals[1] = Integer.getInteger("sun.net.client.defaultConnectTimeout", 300000).intValue();
/*  121 */             encs[0] = System.getProperty("file.encoding", "ISO8859_1");
/*  122 */             return null;
/*      */           }
/*      */         });
/*  125 */     if (arrayOfInt[0] == 0) {
/*  126 */       defaultSoTimeout = -1;
/*      */     } else {
/*  128 */       defaultSoTimeout = arrayOfInt[0];
/*      */     } 
/*      */     
/*  131 */     if (arrayOfInt[1] == 0) {
/*  132 */       defaultConnectTimeout = -1;
/*      */     } else {
/*  134 */       defaultConnectTimeout = arrayOfInt[1];
/*      */     } 
/*      */     
/*  137 */     encoding = arrayOfString[0];
/*      */     try {
/*  139 */       if (!isASCIISuperset(encoding)) {
/*  140 */         encoding = "ISO8859_1";
/*      */       }
/*  142 */     } catch (Exception exception) {
/*  143 */       encoding = "ISO8859_1";
/*      */     } 
/*      */     
/*  146 */     patterns = new Pattern[patStrings.length];
/*  147 */     for (byte b2 = 0; b2 < patStrings.length; b2++) {
/*  148 */       patterns[b2] = Pattern.compile(patStrings[b2]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  370 */     transPat = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  554 */     epsvPat = null;
/*  555 */     pasvPat = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1753 */     MDTMformats = new String[] { "yyyyMMddHHmmss.SSS", "yyyyMMddHHmmss" };
/*      */ 
/*      */ 
/*      */     
/* 1757 */     dateFormats = new SimpleDateFormat[MDTMformats.length];
/*      */ 
/*      */     
/* 1760 */     for (byte b1 = 0; b1 < MDTMformats.length; b1++)
/* 1761 */     { dateFormats[b1] = new SimpleDateFormat(MDTMformats[b1]);
/* 1762 */       dateFormats[b1].setTimeZone(TimeZone.getTimeZone("GMT")); }  } private boolean issueCommand(String paramString) throws IOException, FtpProtocolException { if (!isConnected()) throw new IllegalStateException("Not connected");  if (this.replyPending) try { completePending(); } catch (FtpProtocolException ftpProtocolException) {}  if (paramString.indexOf('\n') != -1) { FtpProtocolException ftpProtocolException = new FtpProtocolException("Illegal FTP command"); ftpProtocolException.initCause(new IllegalArgumentException("Illegal carriage return")); throw ftpProtocolException; }  sendServer(paramString + "\r\n"); return readReply(); } private void issueCommandCheck(String paramString) throws FtpProtocolException, IOException { if (!issueCommand(paramString)) throw new FtpProtocolException(paramString + ":" + getResponseString(), getLastReplyCode());  }
/*      */   private Socket openPassiveDataConnection(String paramString) throws FtpProtocolException, IOException { Socket socket; InetSocketAddress inetSocketAddress = null; if (issueCommand("EPSV ALL")) { issueCommandCheck("EPSV"); String str1 = getResponseString(); if (epsvPat == null) epsvPat = Pattern.compile("^229 .* \\(\\|\\|\\|(\\d+)\\|\\)");  Matcher matcher = epsvPat.matcher(str1); if (!matcher.find()) throw new FtpProtocolException("EPSV failed : " + str1);  String str2 = matcher.group(1); int i = Integer.parseInt(str2); InetAddress inetAddress1 = this.server.getInetAddress(); if (inetAddress1 != null) { inetSocketAddress = new InetSocketAddress(inetAddress1, i); } else { inetSocketAddress = InetSocketAddress.createUnresolved(this.serverAddr.getHostName(), i); }  } else { issueCommandCheck("PASV"); String str1 = getResponseString(); if (pasvPat == null) pasvPat = Pattern.compile("227 .* \\(?(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)?");  Matcher matcher = pasvPat.matcher(str1); if (!matcher.find()) throw new FtpProtocolException("PASV failed : " + str1);  int i = Integer.parseInt(matcher.group(3)) + (Integer.parseInt(matcher.group(2)) << 8); String str2 = matcher.group(1).replace(',', '.'); inetSocketAddress = new InetSocketAddress(str2, i); }  if (this.proxy != null) { if (this.proxy.type() == Proxy.Type.SOCKS) { socket = AccessController.<Socket>doPrivileged(new PrivilegedAction<Socket>() { public Socket run() { return new Socket(FtpClient.this.proxy); } }
/*      */           ); } else { socket = new Socket(Proxy.NO_PROXY); }  } else { socket = new Socket(); }  InetAddress inetAddress = AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>() { public InetAddress run() { return FtpClient.this.server.getLocalAddress(); } }
/*      */       ); socket.bind(new InetSocketAddress(inetAddress, 0)); if (this.connectTimeout >= 0) { socket.connect(inetSocketAddress, this.connectTimeout); } else if (defaultConnectTimeout > 0) { socket.connect(inetSocketAddress, defaultConnectTimeout); } else { socket.connect(inetSocketAddress); }  if (this.readTimeout >= 0) { socket.setSoTimeout(this.readTimeout); } else if (defaultSoTimeout > 0) { socket.setSoTimeout(defaultSoTimeout); }  if (this.useCrypto) try { socket = this.sslFact.createSocket(socket, inetSocketAddress.getHostName(), inetSocketAddress.getPort(), true); } catch (Exception exception) { throw new FtpProtocolException("Can't open secure data channel: " + exception); }   if (!issueCommand(paramString)) { socket.close(); if (getLastReplyCode() == FtpReplyCode.FILE_UNAVAILABLE) throw new FileNotFoundException(paramString);  throw new FtpProtocolException(paramString + ":" + getResponseString(), getLastReplyCode()); }  return socket; }
/*      */   private Socket openDataConnection(String paramString) throws FtpProtocolException, IOException { try { return openPassiveDataConnection(paramString); } catch (FtpProtocolException ftpProtocolException) { Socket socket; String str = ftpProtocolException.getMessage(); if (!str.startsWith("PASV") && !str.startsWith("EPSV")) throw ftpProtocolException;  if (this.proxy != null && this.proxy.type() == Proxy.Type.SOCKS) throw new FtpProtocolException("Passive mode failed");  ServerSocket serverSocket = new ServerSocket(0, 1, this.server.getLocalAddress()); try { InetAddress inetAddress = serverSocket.getInetAddress(); if (inetAddress.isAnyLocalAddress()) inetAddress = this.server.getLocalAddress();  String str1 = "EPRT |" + ((inetAddress instanceof java.net.Inet6Address) ? "2" : "1") + "|" + inetAddress.getHostAddress() + "|" + serverSocket.getLocalPort() + "|"; if (!issueCommand(str1) || !issueCommand(paramString)) { str1 = "PORT "; byte[] arrayOfByte = inetAddress.getAddress(); for (byte b = 0; b < arrayOfByte.length; b++) str1 = str1 + (arrayOfByte[b] & 0xFF) + ",";  str1 = str1 + (serverSocket.getLocalPort() >>> 8 & 0xFF) + "," + (serverSocket.getLocalPort() & 0xFF); issueCommandCheck(str1); issueCommandCheck(paramString); }  if (this.connectTimeout >= 0) { serverSocket.setSoTimeout(this.connectTimeout); } else if (defaultConnectTimeout > 0) { serverSocket.setSoTimeout(defaultConnectTimeout); }  socket = serverSocket.accept(); if (this.readTimeout >= 0) { socket.setSoTimeout(this.readTimeout); } else if (defaultSoTimeout > 0) { socket.setSoTimeout(defaultSoTimeout); }  } finally { serverSocket.close(); }  if (this.useCrypto) try { socket = this.sslFact.createSocket(socket, this.serverAddr.getHostName(), this.serverAddr.getPort(), true); } catch (Exception exception) { throw new IOException(exception.getLocalizedMessage()); }   return socket; }  }
/*      */   private InputStream createInputStream(InputStream paramInputStream) { if (this.type == FtpClient.TransferType.ASCII) return new TelnetInputStream(paramInputStream, false);  return paramInputStream; }
/*      */   private OutputStream createOutputStream(OutputStream paramOutputStream) { if (this.type == FtpClient.TransferType.ASCII) return new TelnetOutputStream(paramOutputStream, false);  return paramOutputStream; }
/*      */   public static FtpClient create() { return new FtpClient(); }
/*      */   public FtpClient enablePassiveMode(boolean paramBoolean) { return this; }
/*      */   public boolean isPassiveModeEnabled() { return true; }
/*      */   public FtpClient setConnectTimeout(int paramInt) { this.connectTimeout = paramInt; return this; }
/*      */   public int getConnectTimeout() { return this.connectTimeout; }
/*      */   public FtpClient setReadTimeout(int paramInt) { this.readTimeout = paramInt; return this; }
/*      */   public int getReadTimeout() { return this.readTimeout; }
/*      */   public FtpClient setProxy(Proxy paramProxy) { this.proxy = paramProxy; return this; }
/*      */   public Proxy getProxy() { return this.proxy; }
/*      */   private void tryConnect(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException { if (isConnected()) disconnect();  this.server = doConnect(paramInetSocketAddress, paramInt); try { this.out = new PrintStream(new BufferedOutputStream(this.server.getOutputStream()), true, encoding); } catch (UnsupportedEncodingException unsupportedEncodingException) { throw new InternalError(encoding + "encoding not found", unsupportedEncodingException); }  this.in = new BufferedInputStream(this.server.getInputStream()); }
/* 1779 */   public Date getLastModified(String paramString) throws FtpProtocolException, IOException { issueCommandCheck("MDTM " + paramString);
/* 1780 */     if (this.lastReplyCode == FtpReplyCode.FILE_STATUS) {
/* 1781 */       String str = getResponseString().substring(4);
/* 1782 */       Date date = null;
/* 1783 */       for (SimpleDateFormat simpleDateFormat : dateFormats) {
/*      */         try {
/* 1785 */           date = simpleDateFormat.parse(str);
/* 1786 */         } catch (ParseException parseException) {}
/*      */         
/* 1788 */         if (date != null) {
/* 1789 */           return date;
/*      */         }
/*      */       } 
/*      */     } 
/* 1793 */     return null; }
/*      */   private Socket doConnect(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException { Socket socket; if (this.proxy != null) { if (this.proxy.type() == Proxy.Type.SOCKS) { socket = AccessController.<Socket>doPrivileged(new PrivilegedAction<Socket>() {
/*      */               public Socket run() { return new Socket(FtpClient.this.proxy); }
/*      */             }); } else { socket = new Socket(Proxy.NO_PROXY); }  } else { socket = new Socket(); }  if (paramInt >= 0) { socket.connect(paramInetSocketAddress, paramInt); } else if (this.connectTimeout >= 0) { socket.connect(paramInetSocketAddress, this.connectTimeout); } else if (defaultConnectTimeout > 0) { socket.connect(paramInetSocketAddress, defaultConnectTimeout); } else { socket.connect(paramInetSocketAddress); }  if (this.readTimeout >= 0) { socket.setSoTimeout(this.readTimeout); } else if (defaultSoTimeout > 0) { socket.setSoTimeout(defaultSoTimeout); }  return socket; }
/*      */   private void disconnect() throws IOException { if (isConnected()) this.server.close();  this.server = null; this.in = null; this.out = null; this.lastTransSize = -1L; this.lastFileName = null; this.restartOffset = 0L; this.welcomeMsg = null; this.lastReplyCode = null; this.serverResponse.setSize(0); }
/*      */   public boolean isConnected() { return (this.server != null); }
/*      */   public SocketAddress getServerAddress() { return (this.server == null) ? null : this.server.getRemoteSocketAddress(); }
/*      */   public FtpClient connect(SocketAddress paramSocketAddress) throws FtpProtocolException, IOException { return connect(paramSocketAddress, -1); }
/*      */   public FtpClient connect(SocketAddress paramSocketAddress, int paramInt) throws FtpProtocolException, IOException { if (!(paramSocketAddress instanceof InetSocketAddress)) throw new IllegalArgumentException("Wrong address type");  this.serverAddr = (InetSocketAddress)paramSocketAddress; tryConnect(this.serverAddr, paramInt); if (!readReply()) throw new FtpProtocolException("Welcome message: " + getResponseString(), this.lastReplyCode);  this.welcomeMsg = getResponseString().substring(4); return this; }
/*      */   private void tryLogin(String paramString, char[] paramArrayOfchar) throws FtpProtocolException, IOException { issueCommandCheck("USER " + paramString); if (this.lastReplyCode == FtpReplyCode.NEED_PASSWORD && paramArrayOfchar != null && paramArrayOfchar.length > 0) issueCommandCheck("PASS " + String.valueOf(paramArrayOfchar));  }
/*      */   public FtpClient login(String paramString, char[] paramArrayOfchar) throws FtpProtocolException, IOException { if (!isConnected()) throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);  if (paramString == null || paramString.length() == 0) throw new IllegalArgumentException("User name can't be null or empty");  tryLogin(paramString, paramArrayOfchar); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < this.serverResponse.size(); b++) { String str = this.serverResponse.elementAt(b); if (str != null) { if (str.length() >= 4 && str.startsWith("230")) str = str.substring(4);  stringBuffer.append(str); }  }  this.welcomeMsg = stringBuffer.toString(); this.loggedIn = true; return this; }
/*      */   public FtpClient login(String paramString1, char[] paramArrayOfchar, String paramString2) throws FtpProtocolException, IOException { if (!isConnected()) throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);  if (paramString1 == null || paramString1.length() == 0) throw new IllegalArgumentException("User name can't be null or empty");  tryLogin(paramString1, paramArrayOfchar); if (this.lastReplyCode == FtpReplyCode.NEED_ACCOUNT) issueCommandCheck("ACCT " + paramString2);  StringBuffer stringBuffer = new StringBuffer(); if (this.serverResponse != null) for (String str : this.serverResponse) { if (str != null) { if (str.length() >= 4 && str.startsWith("230")) str = str.substring(4);  stringBuffer.append(str); }  }   this.welcomeMsg = stringBuffer.toString(); this.loggedIn = true; return this; }
/*      */   public void close() throws IOException { if (isConnected()) { try { issueCommand("QUIT"); } catch (FtpProtocolException ftpProtocolException) {} this.loggedIn = false; }  disconnect(); }
/*      */   public boolean isLoggedIn() { return this.loggedIn; }
/* 1807 */   public FtpClient changeDirectory(String paramString) throws FtpProtocolException, IOException { if (paramString == null || "".equals(paramString)) throw new IllegalArgumentException("directory can't be null or empty");  issueCommandCheck("CWD " + paramString); return this; } public FtpClient changeToParentDirectory() throws FtpProtocolException, IOException { issueCommandCheck("CDUP"); return this; } public String getWorkingDirectory() throws FtpProtocolException, IOException { issueCommandCheck("PWD"); String str = getResponseString(); if (!str.startsWith("257")) return null;  return str.substring(5, str.lastIndexOf('"')); } public FtpClient setRestartOffset(long paramLong) { if (paramLong < 0L) throw new IllegalArgumentException("offset can't be negative");  this.restartOffset = paramLong; return this; } public FtpClient getFile(String paramString, OutputStream paramOutputStream) throws FtpProtocolException, IOException { char c = 'ל'; if (this.restartOffset > 0L) { Socket socket; try { socket = openDataConnection("REST " + this.restartOffset); } finally { this.restartOffset = 0L; }  issueCommandCheck("RETR " + paramString); getTransferSize(); InputStream inputStream = createInputStream(socket.getInputStream()); byte[] arrayOfByte = new byte[c * 10]; int i; while ((i = inputStream.read(arrayOfByte)) >= 0) { if (i > 0) paramOutputStream.write(arrayOfByte, 0, i);  }  inputStream.close(); } else { Socket socket = openDataConnection("RETR " + paramString); getTransferSize(); InputStream inputStream = createInputStream(socket.getInputStream()); byte[] arrayOfByte = new byte[c * 10]; int i; while ((i = inputStream.read(arrayOfByte)) >= 0) { if (i > 0) paramOutputStream.write(arrayOfByte, 0, i);  }  inputStream.close(); }  return completePending(); } public InputStream getFileStream(String paramString) throws FtpProtocolException, IOException { if (this.restartOffset > 0L) { Socket socket1; try { socket1 = openDataConnection("REST " + this.restartOffset); } finally { this.restartOffset = 0L; }  if (socket1 == null) return null;  issueCommandCheck("RETR " + paramString); getTransferSize(); return createInputStream(socket1.getInputStream()); }  Socket socket = openDataConnection("RETR " + paramString); if (socket == null) return null;  getTransferSize(); return createInputStream(socket.getInputStream()); } public FtpClient setDirParser(FtpDirParser paramFtpDirParser) { this.parser = paramFtpDirParser;
/* 1808 */     return this; }
/*      */   public OutputStream putFileStream(String paramString, boolean paramBoolean) throws FtpProtocolException, IOException { String str = paramBoolean ? "STOU " : "STOR "; Socket socket = openDataConnection(str + paramString); if (socket == null) return null;  boolean bool = (this.type == FtpClient.TransferType.BINARY) ? true : false; return new TelnetOutputStream(socket.getOutputStream(), bool); }
/*      */   public FtpClient putFile(String paramString, InputStream paramInputStream, boolean paramBoolean) throws FtpProtocolException, IOException { String str = paramBoolean ? "STOU " : "STOR "; char c = 'ל'; if (this.type == FtpClient.TransferType.BINARY) { Socket socket = openDataConnection(str + paramString); OutputStream outputStream = createOutputStream(socket.getOutputStream()); byte[] arrayOfByte = new byte[c * 10]; int i; while ((i = paramInputStream.read(arrayOfByte)) >= 0) { if (i > 0) outputStream.write(arrayOfByte, 0, i);  }  outputStream.close(); }  return completePending(); }
/*      */   public FtpClient appendFile(String paramString, InputStream paramInputStream) throws FtpProtocolException, IOException { char c = 'ל'; Socket socket = openDataConnection("APPE " + paramString); OutputStream outputStream = createOutputStream(socket.getOutputStream()); byte[] arrayOfByte = new byte[c * 10]; int i; while ((i = paramInputStream.read(arrayOfByte)) >= 0) { if (i > 0) outputStream.write(arrayOfByte, 0, i);  }  outputStream.close(); return completePending(); } public FtpClient rename(String paramString1, String paramString2) throws FtpProtocolException, IOException { issueCommandCheck("RNFR " + paramString1); issueCommandCheck("RNTO " + paramString2); return this; } public FtpClient deleteFile(String paramString) throws FtpProtocolException, IOException { issueCommandCheck("DELE " + paramString); return this; } public FtpClient makeDirectory(String paramString) throws FtpProtocolException, IOException { issueCommandCheck("MKD " + paramString); return this; } public FtpClient removeDirectory(String paramString) throws FtpProtocolException, IOException { issueCommandCheck("RMD " + paramString); return this; } public FtpClient noop() throws FtpProtocolException, IOException { issueCommandCheck("NOOP"); return this; } public String getStatus(String paramString) throws FtpProtocolException, IOException { issueCommandCheck((paramString == null) ? "STAT" : ("STAT " + paramString)); Vector<String> vector = getResponseStrings(); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 1; b < vector.size() - 1; b++) stringBuffer.append(vector.get(b));  return stringBuffer.toString(); } public List<String> getFeatures() throws FtpProtocolException, IOException { ArrayList<String> arrayList = new ArrayList(); issueCommandCheck("FEAT"); Vector<String> vector = getResponseStrings(); for (byte b = 1; b < vector.size() - 1; b++) { String str = vector.get(b); arrayList.add(str.substring(1, str.length() - 1)); }  return arrayList; } public FtpClient abort() throws FtpProtocolException, IOException { issueCommandCheck("ABOR"); return this; } public FtpClient completePending() throws FtpProtocolException, IOException { while (this.replyPending) { this.replyPending = false; if (!readReply()) throw new FtpProtocolException(getLastResponseString(), this.lastReplyCode);  }  return this; } public FtpClient reInit() throws FtpProtocolException, IOException { issueCommandCheck("REIN"); this.loggedIn = false; if (this.useCrypto && this.server instanceof SSLSocket) { SSLSession sSLSession = ((SSLSocket)this.server).getSession(); sSLSession.invalidate(); this.server = this.oldSocket; this.oldSocket = null; try { this.out = new PrintStream(new BufferedOutputStream(this.server.getOutputStream()), true, encoding); } catch (UnsupportedEncodingException unsupportedEncodingException) { throw new InternalError(encoding + "encoding not found", unsupportedEncodingException); }  this.in = new BufferedInputStream(this.server.getInputStream()); }  this.useCrypto = false; return this; } public FtpClient setType(FtpClient.TransferType paramTransferType) throws FtpProtocolException, IOException { String str = "NOOP"; this.type = paramTransferType; if (paramTransferType == FtpClient.TransferType.ASCII) str = "TYPE A";  if (paramTransferType == FtpClient.TransferType.BINARY) str = "TYPE I";  if (paramTransferType == FtpClient.TransferType.EBCDIC) str = "TYPE E";  issueCommandCheck(str); return this; } public InputStream list(String paramString) throws FtpProtocolException, IOException { Socket socket = openDataConnection((paramString == null) ? "LIST" : ("LIST " + paramString)); if (socket != null) return createInputStream(socket.getInputStream());  return null; } public InputStream nameList(String paramString) throws FtpProtocolException, IOException { Socket socket = openDataConnection((paramString == null) ? "NLST" : ("NLST " + paramString)); if (socket != null) return createInputStream(socket.getInputStream());  return null; } public long getSize(String paramString) throws FtpProtocolException, IOException { if (paramString == null || paramString.length() == 0) throw new IllegalArgumentException("path can't be null or empty");  issueCommandCheck("SIZE " + paramString); if (this.lastReplyCode == FtpReplyCode.FILE_STATUS) { String str = getResponseString(); str = str.substring(4, str.length() - 1); return Long.parseLong(str); }  return -1L; } private class FtpFileIterator implements Iterator<FtpDirEntry>, Closeable
/*      */   {
/* 1813 */     private BufferedReader in = null;
/* 1814 */     private FtpDirEntry nextFile = null;
/* 1815 */     private FtpDirParser fparser = null;
/*      */     private boolean eof = false;
/*      */     
/*      */     public FtpFileIterator(FtpDirParser param1FtpDirParser, BufferedReader param1BufferedReader) {
/* 1819 */       this.in = param1BufferedReader;
/* 1820 */       this.fparser = param1FtpDirParser;
/* 1821 */       readNext();
/*      */     }
/*      */     
/*      */     private void readNext() {
/* 1825 */       this.nextFile = null;
/* 1826 */       if (this.eof) {
/*      */         return;
/*      */       }
/* 1829 */       String str = null;
/*      */       
/*      */       try { while (true)
/* 1832 */         { str = this.in.readLine();
/* 1833 */           if (str != null) {
/* 1834 */             this.nextFile = this.fparser.parseLine(str);
/* 1835 */             if (this.nextFile != null) {
/*      */               return;
/*      */             }
/*      */           } 
/* 1839 */           if (str == null) {
/* 1840 */             this.in.close();
/*      */           } else {
/*      */             continue;
/* 1843 */           }  this.eof = true; return; }  } catch (IOException iOException) {} this.eof = true;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1847 */       return (this.nextFile != null);
/*      */     }
/*      */     
/*      */     public FtpDirEntry next() {
/* 1851 */       FtpDirEntry ftpDirEntry = this.nextFile;
/* 1852 */       readNext();
/* 1853 */       return ftpDirEntry;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1857 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 1861 */       if (this.in != null && !this.eof) {
/* 1862 */         this.in.close();
/*      */       }
/* 1864 */       this.eof = true;
/* 1865 */       this.nextFile = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<FtpDirEntry> listFiles(String paramString) throws FtpProtocolException, IOException {
/* 1889 */     Socket socket = null;
/* 1890 */     BufferedReader bufferedReader = null;
/*      */     try {
/* 1892 */       socket = openDataConnection((paramString == null) ? "MLSD" : ("MLSD " + paramString));
/* 1893 */     } catch (FtpProtocolException ftpProtocolException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1898 */     if (socket != null) {
/* 1899 */       bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/* 1900 */       return new FtpFileIterator(this.mlsxParser, bufferedReader);
/*      */     } 
/* 1902 */     socket = openDataConnection((paramString == null) ? "LIST" : ("LIST " + paramString));
/* 1903 */     if (socket != null) {
/* 1904 */       bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/* 1905 */       return new FtpFileIterator(this.parser, bufferedReader);
/*      */     } 
/*      */     
/* 1908 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean sendSecurityData(byte[] paramArrayOfbyte) throws IOException, FtpProtocolException {
/* 1913 */     BASE64Encoder bASE64Encoder = new BASE64Encoder();
/* 1914 */     String str = bASE64Encoder.encode(paramArrayOfbyte);
/* 1915 */     return issueCommand("ADAT " + str);
/*      */   }
/*      */   
/*      */   private byte[] getSecurityData() {
/* 1919 */     String str = getLastResponseString();
/* 1920 */     if (str.substring(4, 9).equalsIgnoreCase("ADAT=")) {
/* 1921 */       BASE64Decoder bASE64Decoder = new BASE64Decoder();
/*      */ 
/*      */       
/*      */       try {
/* 1925 */         return bASE64Decoder.decodeBuffer(str.substring(9, str.length() - 1));
/* 1926 */       } catch (IOException iOException) {}
/*      */     } 
/*      */ 
/*      */     
/* 1930 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient useKerberos() throws FtpProtocolException, IOException {
/* 1977 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getWelcomeMsg() {
/* 1987 */     return this.welcomeMsg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpReplyCode getLastReplyCode() {
/* 1996 */     return this.lastReplyCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLastResponseString() {
/* 2006 */     StringBuffer stringBuffer = new StringBuffer();
/* 2007 */     if (this.serverResponse != null) {
/* 2008 */       for (String str : this.serverResponse) {
/* 2009 */         if (str != null) {
/* 2010 */           stringBuffer.append(str);
/*      */         }
/*      */       } 
/*      */     }
/* 2014 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastTransferSize() {
/* 2026 */     return this.lastTransSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLastFileName() {
/* 2039 */     return this.lastFileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient startSecureSession() throws FtpProtocolException, IOException {
/* 2056 */     if (!isConnected()) {
/* 2057 */       throw new FtpProtocolException("Not connected yet", FtpReplyCode.BAD_SEQUENCE);
/*      */     }
/* 2059 */     if (this.sslFact == null) {
/*      */       try {
/* 2061 */         this.sslFact = (SSLSocketFactory)SSLSocketFactory.getDefault();
/* 2062 */       } catch (Exception exception) {
/* 2063 */         throw new IOException(exception.getLocalizedMessage());
/*      */       } 
/*      */     }
/* 2066 */     issueCommandCheck("AUTH TLS");
/* 2067 */     Socket socket = null;
/*      */     try {
/* 2069 */       socket = this.sslFact.createSocket(this.server, this.serverAddr.getHostName(), this.serverAddr.getPort(), true);
/* 2070 */     } catch (SSLException sSLException) {
/*      */       try {
/* 2072 */         disconnect();
/* 2073 */       } catch (Exception exception) {}
/*      */       
/* 2075 */       throw sSLException;
/*      */     } 
/*      */     
/* 2078 */     this.oldSocket = this.server;
/* 2079 */     this.server = socket;
/*      */     try {
/* 2081 */       this.out = new PrintStream(new BufferedOutputStream(this.server.getOutputStream()), true, encoding);
/*      */     }
/* 2083 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 2084 */       throw new InternalError(encoding + "encoding not found", unsupportedEncodingException);
/*      */     } 
/* 2086 */     this.in = new BufferedInputStream(this.server.getInputStream());
/*      */     
/* 2088 */     issueCommandCheck("PBSZ 0");
/* 2089 */     issueCommandCheck("PROT P");
/* 2090 */     this.useCrypto = true;
/* 2091 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient endSecureSession() throws FtpProtocolException, IOException {
/* 2104 */     if (!this.useCrypto) {
/* 2105 */       return this;
/*      */     }
/*      */     
/* 2108 */     issueCommandCheck("CCC");
/* 2109 */     issueCommandCheck("PROT C");
/* 2110 */     this.useCrypto = false;
/*      */     
/* 2112 */     this.server = this.oldSocket;
/* 2113 */     this.oldSocket = null;
/*      */     try {
/* 2115 */       this.out = new PrintStream(new BufferedOutputStream(this.server.getOutputStream()), true, encoding);
/*      */     }
/* 2117 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 2118 */       throw new InternalError(encoding + "encoding not found", unsupportedEncodingException);
/*      */     } 
/* 2120 */     this.in = new BufferedInputStream(this.server.getInputStream());
/*      */     
/* 2122 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient allocate(long paramLong) throws FtpProtocolException, IOException {
/* 2134 */     issueCommandCheck("ALLO " + paramLong);
/* 2135 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient structureMount(String paramString) throws FtpProtocolException, IOException {
/* 2149 */     issueCommandCheck("SMNT " + paramString);
/* 2150 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSystem() throws FtpProtocolException, IOException {
/* 2163 */     issueCommandCheck("SYST");
/*      */ 
/*      */ 
/*      */     
/* 2167 */     String str = getResponseString();
/*      */     
/* 2169 */     return str.substring(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHelp(String paramString) throws FtpProtocolException, IOException {
/* 2183 */     issueCommandCheck("HELP " + paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2202 */     Vector<String> vector = getResponseStrings();
/* 2203 */     if (vector.size() == 1)
/*      */     {
/* 2205 */       return ((String)vector.get(0)).substring(4);
/*      */     }
/*      */ 
/*      */     
/* 2209 */     StringBuffer stringBuffer = new StringBuffer();
/* 2210 */     for (byte b = 1; b < vector.size() - 1; b++) {
/* 2211 */       stringBuffer.append(((String)vector.get(b)).substring(3));
/*      */     }
/* 2213 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FtpClient siteCmd(String paramString) throws FtpProtocolException, IOException {
/* 2226 */     issueCommandCheck("SITE " + paramString);
/* 2227 */     return this;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ftp/impl/FtpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */