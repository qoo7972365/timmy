/*     */ package sun.net.sdp;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
/*     */ import java.util.Scanner;
/*     */ import sun.net.NetHooks;
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
/*     */ 
/*     */ 
/*     */ public class SdpProvider
/*     */   extends NetHooks.Provider
/*     */ {
/*     */   private static final int MAX_PORT = 65535;
/*     */   private final boolean enabled;
/*     */   private final List<Rule> rules;
/*     */   private PrintStream log;
/*     */   
/*     */   public SdpProvider() {
/*  60 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("com.sun.sdp.conf"));
/*     */     
/*  62 */     if (str1 == null) {
/*  63 */       this.enabled = false;
/*  64 */       this.rules = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  69 */     List<Rule> list = null;
/*  70 */     if (str1 != null) {
/*     */       try {
/*  72 */         list = loadRulesFromFile(str1);
/*  73 */       } catch (IOException iOException) {
/*  74 */         fail("Error reading %s: %s", new Object[] { str1, iOException.getMessage() });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  79 */     PrintStream printStream = null;
/*  80 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("com.sun.sdp.debug"));
/*     */     
/*  82 */     if (str2 != null) {
/*  83 */       printStream = System.out;
/*  84 */       if (str2.length() > 0) {
/*     */         try {
/*  86 */           printStream = new PrintStream(str2);
/*  87 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */     
/*  91 */     this.enabled = !list.isEmpty();
/*  92 */     this.rules = list;
/*  93 */     this.log = printStream;
/*     */   }
/*     */   
/*     */   private enum Action
/*     */   {
/*  98 */     BIND,
/*  99 */     CONNECT;
/*     */   }
/*     */   
/*     */   private static interface Rule {
/*     */     boolean match(SdpProvider.Action param1Action, InetAddress param1InetAddress, int param1Int);
/*     */   }
/*     */   
/*     */   private static class PortRangeRule
/*     */     implements Rule {
/*     */     private final SdpProvider.Action action;
/*     */     private final int portStart;
/*     */     private final int portEnd;
/*     */     
/*     */     PortRangeRule(SdpProvider.Action param1Action, int param1Int1, int param1Int2) {
/* 113 */       this.action = param1Action;
/* 114 */       this.portStart = param1Int1;
/* 115 */       this.portEnd = param1Int2;
/*     */     }
/*     */     SdpProvider.Action action() {
/* 118 */       return this.action;
/*     */     }
/*     */     
/*     */     public boolean match(SdpProvider.Action param1Action, InetAddress param1InetAddress, int param1Int) {
/* 122 */       return (param1Action == this.action && param1Int >= this.portStart && param1Int <= this.portEnd);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AddressPortRangeRule
/*     */     extends PortRangeRule
/*     */   {
/*     */     private final byte[] addressAsBytes;
/*     */     
/*     */     private final int prefixByteCount;
/*     */     private final byte mask;
/*     */     
/*     */     AddressPortRangeRule(SdpProvider.Action param1Action, InetAddress param1InetAddress, int param1Int1, int param1Int2, int param1Int3) {
/* 136 */       super(param1Action, param1Int2, param1Int3);
/* 137 */       this.addressAsBytes = param1InetAddress.getAddress();
/* 138 */       this.prefixByteCount = param1Int1 >> 3;
/* 139 */       this.mask = (byte)(255 << 8 - param1Int1 % 8);
/*     */     }
/*     */     
/*     */     public boolean match(SdpProvider.Action param1Action, InetAddress param1InetAddress, int param1Int) {
/* 143 */       if (param1Action != action())
/* 144 */         return false; 
/* 145 */       byte[] arrayOfByte = param1InetAddress.getAddress();
/*     */       
/* 147 */       if (arrayOfByte.length != this.addressAsBytes.length) {
/* 148 */         return false;
/*     */       }
/* 150 */       for (byte b = 0; b < this.prefixByteCount; b++) {
/* 151 */         if (arrayOfByte[b] != this.addressAsBytes[b]) {
/* 152 */           return false;
/*     */         }
/*     */       } 
/* 155 */       if (this.prefixByteCount < this.addressAsBytes.length && (arrayOfByte[this.prefixByteCount] & this.mask) != (this.addressAsBytes[this.prefixByteCount] & this.mask))
/*     */       {
/*     */         
/* 158 */         return false; } 
/* 159 */       return super.match(param1Action, param1InetAddress, param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] parsePortRange(String paramString) {
/* 165 */     int i = paramString.indexOf('-');
/*     */     try {
/* 167 */       int[] arrayOfInt = new int[2];
/* 168 */       if (i < 0) {
/* 169 */         boolean bool = paramString.equals("*");
/* 170 */         arrayOfInt[0] = bool ? 0 : Integer.parseInt(paramString);
/* 171 */         arrayOfInt[1] = bool ? 65535 : arrayOfInt[0];
/*     */       } else {
/* 173 */         String str1 = paramString.substring(0, i);
/* 174 */         if (str1.length() == 0) str1 = "*"; 
/* 175 */         String str2 = paramString.substring(i + 1);
/* 176 */         if (str2.length() == 0) str2 = "*"; 
/* 177 */         arrayOfInt[0] = str1.equals("*") ? 0 : Integer.parseInt(str1);
/* 178 */         arrayOfInt[1] = str2.equals("*") ? 65535 : Integer.parseInt(str2);
/*     */       } 
/* 180 */       return arrayOfInt;
/* 181 */     } catch (NumberFormatException numberFormatException) {
/* 182 */       return new int[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fail(String paramString, Object... paramVarArgs) {
/* 187 */     Formatter formatter = new Formatter();
/* 188 */     formatter.format(paramString, paramVarArgs);
/* 189 */     throw new RuntimeException(formatter.out().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Rule> loadRulesFromFile(String paramString) throws IOException {
/* 199 */     Scanner scanner = new Scanner(new File(paramString));
/*     */     try {
/* 201 */       ArrayList<PortRangeRule> arrayList = new ArrayList();
/* 202 */       while (scanner.hasNextLine()) {
/* 203 */         String str = scanner.nextLine().trim();
/*     */ 
/*     */         
/* 206 */         if (str.length() == 0 || str.charAt(0) == '#') {
/*     */           continue;
/*     */         }
/*     */         
/* 210 */         String[] arrayOfString = str.split("\\s+");
/* 211 */         if (arrayOfString.length != 3) {
/* 212 */           fail("Malformed line '%s'", new Object[] { str });
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 217 */         Action action = null;
/* 218 */         for (Action action1 : Action.values()) {
/* 219 */           if (arrayOfString[0].equalsIgnoreCase(action1.name())) {
/* 220 */             action = action1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 224 */         if (action == null) {
/* 225 */           fail("Action '%s' not recognized", new Object[] { arrayOfString[0] });
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 230 */         int[] arrayOfInt = parsePortRange(arrayOfString[2]);
/* 231 */         if (arrayOfInt.length == 0) {
/* 232 */           fail("Malformed port range '%s'", new Object[] { arrayOfString[2] });
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 237 */         if (arrayOfString[1].equals("*")) {
/* 238 */           arrayList.add(new PortRangeRule(action, arrayOfInt[0], arrayOfInt[1]));
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 243 */         int i = arrayOfString[1].indexOf('/');
/*     */         try {
/* 245 */           if (i < 0) {
/*     */             
/* 247 */             InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(arrayOfString[1]);
/* 248 */             for (InetAddress inetAddress1 : arrayOfInetAddress) {
/* 249 */               boolean bool = (inetAddress1 instanceof java.net.Inet4Address) ? true : true;
/*     */               
/* 251 */               arrayList.add(new AddressPortRangeRule(action, inetAddress1, bool, arrayOfInt[0], arrayOfInt[1]));
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 257 */           InetAddress inetAddress = InetAddress.getByName(arrayOfString[1].substring(0, i));
/* 258 */           int j = -1;
/*     */           try {
/* 260 */             j = Integer.parseInt(arrayOfString[1].substring(i + 1));
/* 261 */             if (inetAddress instanceof java.net.Inet4Address)
/*     */             
/* 263 */             { if (j < 0 || j > 32) j = -1;
/*     */                }
/*     */             
/* 266 */             else if (j < 0 || j > 128) { j = -1; }
/*     */           
/* 268 */           } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */           
/* 271 */           if (j > 0) {
/* 272 */             arrayList.add(new AddressPortRangeRule(action, inetAddress, j, arrayOfInt[0], arrayOfInt[1]));
/*     */             continue;
/*     */           } 
/* 275 */           fail("Malformed prefix '%s'", new Object[] { arrayOfString[1] });
/*     */ 
/*     */         
/*     */         }
/* 279 */         catch (UnknownHostException unknownHostException) {
/* 280 */           fail("Unknown host or malformed IP address '%s'", new Object[] { arrayOfString[1] });
/*     */         } 
/*     */       } 
/*     */       
/* 284 */       return (List)arrayList;
/*     */     } finally {
/* 286 */       scanner.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertTcpToSdpIfMatch(FileDescriptor paramFileDescriptor, Action paramAction, InetAddress paramInetAddress, int paramInt) throws IOException {
/* 297 */     boolean bool = false;
/* 298 */     for (Rule rule : this.rules) {
/* 299 */       if (rule.match(paramAction, paramInetAddress, paramInt)) {
/* 300 */         SdpSupport.convertSocket(paramFileDescriptor);
/* 301 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 305 */     if (this.log != null) {
/*     */       
/* 307 */       String str = (paramInetAddress instanceof java.net.Inet4Address) ? paramInetAddress.getHostAddress() : ("[" + paramInetAddress.getHostAddress() + "]");
/* 308 */       if (bool) {
/* 309 */         this.log.format("%s to %s:%d (socket converted to SDP protocol)\n", new Object[] { paramAction, str, Integer.valueOf(paramInt) });
/*     */       } else {
/* 311 */         this.log.format("%s to %s:%d (no match)\n", new Object[] { paramAction, str, Integer.valueOf(paramInt) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void implBeforeTcpBind(FileDescriptor paramFileDescriptor, InetAddress paramInetAddress, int paramInt) throws IOException {
/* 322 */     if (this.enabled) {
/* 323 */       convertTcpToSdpIfMatch(paramFileDescriptor, Action.BIND, paramInetAddress, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void implBeforeTcpConnect(FileDescriptor paramFileDescriptor, InetAddress paramInetAddress, int paramInt) throws IOException {
/* 332 */     if (this.enabled)
/* 333 */       convertTcpToSdpIfMatch(paramFileDescriptor, Action.CONNECT, paramInetAddress, paramInt); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/sdp/SdpProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */