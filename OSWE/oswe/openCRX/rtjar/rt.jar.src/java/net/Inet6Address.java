/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Inet6Address
/*     */   extends InetAddress
/*     */ {
/*     */   static final int INADDRSZ = 16;
/*     */   private transient int cached_scope_id;
/*     */   private final transient Inet6AddressHolder holder6;
/*     */   private static final long serialVersionUID = 6880410070516793377L;
/*     */   
/*     */   private class Inet6AddressHolder
/*     */   {
/*     */     byte[] ipaddress;
/*     */     int scope_id;
/*     */     boolean scope_id_set;
/*     */     NetworkInterface scope_ifname;
/*     */     boolean scope_ifname_set;
/*     */     
/*     */     private Inet6AddressHolder() {
/* 186 */       this.ipaddress = new byte[16];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Inet6AddressHolder(byte[] param1ArrayOfbyte, int param1Int, boolean param1Boolean1, NetworkInterface param1NetworkInterface, boolean param1Boolean2) {
/* 193 */       this.ipaddress = param1ArrayOfbyte;
/* 194 */       this.scope_id = param1Int;
/* 195 */       this.scope_id_set = param1Boolean1;
/* 196 */       this.scope_ifname_set = param1Boolean2;
/* 197 */       this.scope_ifname = param1NetworkInterface;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setAddr(byte[] param1ArrayOfbyte) {
/* 231 */       if (param1ArrayOfbyte.length == 16) {
/* 232 */         System.arraycopy(param1ArrayOfbyte, 0, this.ipaddress, 0, 16);
/*     */       }
/*     */     }
/*     */     
/*     */     void init(byte[] param1ArrayOfbyte, int param1Int) {
/* 237 */       setAddr(param1ArrayOfbyte);
/*     */       
/* 239 */       if (param1Int >= 0) {
/* 240 */         this.scope_id = param1Int;
/* 241 */         this.scope_id_set = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void init(byte[] param1ArrayOfbyte, NetworkInterface param1NetworkInterface) throws UnknownHostException {
/* 248 */       setAddr(param1ArrayOfbyte);
/*     */       
/* 250 */       if (param1NetworkInterface != null) {
/* 251 */         this.scope_id = Inet6Address.deriveNumericScope(this.ipaddress, param1NetworkInterface);
/* 252 */         this.scope_id_set = true;
/* 253 */         this.scope_ifname = param1NetworkInterface;
/* 254 */         this.scope_ifname_set = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     String getHostAddress() {
/* 259 */       String str = Inet6Address.numericToTextFormat(this.ipaddress);
/* 260 */       if (this.scope_ifname != null) {
/* 261 */         str = str + "%" + this.scope_ifname.getName();
/* 262 */       } else if (this.scope_id_set) {
/* 263 */         str = str + "%" + this.scope_id;
/*     */       } 
/* 265 */       return str;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 269 */       if (!(param1Object instanceof Inet6AddressHolder)) {
/* 270 */         return false;
/*     */       }
/* 272 */       Inet6AddressHolder inet6AddressHolder = (Inet6AddressHolder)param1Object;
/*     */       
/* 274 */       return Arrays.equals(this.ipaddress, inet6AddressHolder.ipaddress);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 278 */       if (this.ipaddress != null) {
/*     */         
/* 280 */         int i = 0;
/* 281 */         byte b = 0;
/* 282 */         while (b < 16) {
/* 283 */           byte b1 = 0;
/* 284 */           int j = 0;
/* 285 */           while (b1 < 4 && b < 16) {
/* 286 */             j = (j << 8) + this.ipaddress[b];
/* 287 */             b1++;
/* 288 */             b++;
/*     */           } 
/* 290 */           i += j;
/*     */         } 
/* 292 */         return i;
/*     */       } 
/*     */       
/* 295 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isIPv4CompatibleAddress() {
/* 300 */       if (this.ipaddress[0] == 0 && this.ipaddress[1] == 0 && this.ipaddress[2] == 0 && this.ipaddress[3] == 0 && this.ipaddress[4] == 0 && this.ipaddress[5] == 0 && this.ipaddress[6] == 0 && this.ipaddress[7] == 0 && this.ipaddress[8] == 0 && this.ipaddress[9] == 0 && this.ipaddress[10] == 0 && this.ipaddress[11] == 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 306 */         return true;
/*     */       }
/* 308 */       return false;
/*     */     }
/*     */     
/*     */     boolean isMulticastAddress() {
/* 312 */       return ((this.ipaddress[0] & 0xFF) == 255);
/*     */     }
/*     */     
/*     */     boolean isAnyLocalAddress() {
/* 316 */       byte b = 0;
/* 317 */       for (byte b1 = 0; b1 < 16; b1++) {
/* 318 */         b = (byte)(b | this.ipaddress[b1]);
/*     */       }
/* 320 */       return (b == 0);
/*     */     }
/*     */     
/*     */     boolean isLoopbackAddress() {
/* 324 */       byte b = 0;
/* 325 */       for (byte b1 = 0; b1 < 15; b1++) {
/* 326 */         b = (byte)(b | this.ipaddress[b1]);
/*     */       }
/* 328 */       return (b == 0 && this.ipaddress[15] == 1);
/*     */     }
/*     */     
/*     */     boolean isLinkLocalAddress() {
/* 332 */       return ((this.ipaddress[0] & 0xFF) == 254 && (this.ipaddress[1] & 0xC0) == 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isSiteLocalAddress() {
/* 338 */       return ((this.ipaddress[0] & 0xFF) == 254 && (this.ipaddress[1] & 0xC0) == 192);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isMCGlobal() {
/* 343 */       return ((this.ipaddress[0] & 0xFF) == 255 && (this.ipaddress[1] & 0xF) == 14);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isMCNodeLocal() {
/* 348 */       return ((this.ipaddress[0] & 0xFF) == 255 && (this.ipaddress[1] & 0xF) == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isMCLinkLocal() {
/* 353 */       return ((this.ipaddress[0] & 0xFF) == 255 && (this.ipaddress[1] & 0xF) == 2);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isMCSiteLocal() {
/* 358 */       return ((this.ipaddress[0] & 0xFF) == 255 && (this.ipaddress[1] & 0xF) == 5);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isMCOrgLocal() {
/* 363 */       return ((this.ipaddress[0] & 0xFF) == 255 && (this.ipaddress[1] & 0xF) == 8);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 373 */     init();
/*     */   }
/*     */   
/*     */   Inet6Address() {
/* 377 */     this.holder.init(null, 2);
/* 378 */     this.holder6 = new Inet6AddressHolder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Inet6Address(String paramString, byte[] paramArrayOfbyte, int paramInt) {
/* 385 */     this.holder.init(paramString, 2);
/* 386 */     this.holder6 = new Inet6AddressHolder();
/* 387 */     this.holder6.init(paramArrayOfbyte, paramInt);
/*     */   }
/*     */   
/*     */   Inet6Address(String paramString, byte[] paramArrayOfbyte) {
/* 391 */     this.holder6 = new Inet6AddressHolder();
/*     */     try {
/* 393 */       initif(paramString, paramArrayOfbyte, null);
/* 394 */     } catch (UnknownHostException unknownHostException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Inet6Address(String paramString, byte[] paramArrayOfbyte, NetworkInterface paramNetworkInterface) throws UnknownHostException {
/* 400 */     this.holder6 = new Inet6AddressHolder();
/* 401 */     initif(paramString, paramArrayOfbyte, paramNetworkInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Inet6Address(String paramString1, byte[] paramArrayOfbyte, String paramString2) throws UnknownHostException {
/* 407 */     this.holder6 = new Inet6AddressHolder();
/* 408 */     initstr(paramString1, paramArrayOfbyte, paramString2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Inet6Address getByAddress(String paramString, byte[] paramArrayOfbyte, NetworkInterface paramNetworkInterface) throws UnknownHostException {
/* 435 */     if (paramString != null && paramString.length() > 0 && paramString.charAt(0) == '[' && 
/* 436 */       paramString.charAt(paramString.length() - 1) == ']') {
/* 437 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */     
/* 440 */     if (paramArrayOfbyte != null && 
/* 441 */       paramArrayOfbyte.length == 16) {
/* 442 */       return new Inet6Address(paramString, paramArrayOfbyte, paramNetworkInterface);
/*     */     }
/*     */     
/* 445 */     throw new UnknownHostException("addr is of illegal length");
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
/*     */ 
/*     */   
/*     */   public static Inet6Address getByAddress(String paramString, byte[] paramArrayOfbyte, int paramInt) throws UnknownHostException {
/* 468 */     if (paramString != null && paramString.length() > 0 && paramString.charAt(0) == '[' && 
/* 469 */       paramString.charAt(paramString.length() - 1) == ']') {
/* 470 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */     
/* 473 */     if (paramArrayOfbyte != null && 
/* 474 */       paramArrayOfbyte.length == 16) {
/* 475 */       return new Inet6Address(paramString, paramArrayOfbyte, paramInt);
/*     */     }
/*     */     
/* 478 */     throw new UnknownHostException("addr is of illegal length");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initstr(String paramString1, byte[] paramArrayOfbyte, String paramString2) throws UnknownHostException {
/*     */     try {
/* 485 */       NetworkInterface networkInterface = NetworkInterface.getByName(paramString2);
/* 486 */       if (networkInterface == null) {
/* 487 */         throw new UnknownHostException("no such interface " + paramString2);
/*     */       }
/* 489 */       initif(paramString1, paramArrayOfbyte, networkInterface);
/* 490 */     } catch (SocketException socketException) {
/* 491 */       throw new UnknownHostException("SocketException thrown" + paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initif(String paramString, byte[] paramArrayOfbyte, NetworkInterface paramNetworkInterface) throws UnknownHostException {
/* 498 */     byte b = -1;
/* 499 */     this.holder6.init(paramArrayOfbyte, paramNetworkInterface);
/*     */     
/* 501 */     if (paramArrayOfbyte.length == 16) {
/* 502 */       b = 2;
/*     */     }
/* 504 */     this.holder.init(paramString, b);
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
/*     */   private static boolean isDifferentLocalAddressType(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 516 */     if (isLinkLocalAddress(paramArrayOfbyte1) && 
/* 517 */       !isLinkLocalAddress(paramArrayOfbyte2)) {
/* 518 */       return false;
/*     */     }
/* 520 */     if (isSiteLocalAddress(paramArrayOfbyte1) && 
/* 521 */       !isSiteLocalAddress(paramArrayOfbyte2)) {
/* 522 */       return false;
/*     */     }
/* 524 */     return true;
/*     */   }
/*     */   
/*     */   private static int deriveNumericScope(byte[] paramArrayOfbyte, NetworkInterface paramNetworkInterface) throws UnknownHostException {
/* 528 */     Enumeration<InetAddress> enumeration = paramNetworkInterface.getInetAddresses();
/* 529 */     while (enumeration.hasMoreElements()) {
/* 530 */       InetAddress inetAddress = enumeration.nextElement();
/* 531 */       if (!(inetAddress instanceof Inet6Address)) {
/*     */         continue;
/*     */       }
/* 534 */       Inet6Address inet6Address = (Inet6Address)inetAddress;
/*     */       
/* 536 */       if (!isDifferentLocalAddressType(paramArrayOfbyte, inet6Address.getAddress())) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 541 */       return inet6Address.getScopeId();
/*     */     } 
/* 543 */     throw new UnknownHostException("no scope_id found");
/*     */   }
/*     */   
/*     */   private int deriveNumericScope(String paramString) throws UnknownHostException {
/*     */     Enumeration<NetworkInterface> enumeration;
/*     */     try {
/* 549 */       enumeration = NetworkInterface.getNetworkInterfaces();
/* 550 */     } catch (SocketException socketException) {
/* 551 */       throw new UnknownHostException("could not enumerate local network interfaces");
/*     */     } 
/* 553 */     while (enumeration.hasMoreElements()) {
/* 554 */       NetworkInterface networkInterface = enumeration.nextElement();
/* 555 */       if (networkInterface.getName().equals(paramString)) {
/* 556 */         return deriveNumericScope(this.holder6.ipaddress, networkInterface);
/*     */       }
/*     */     } 
/* 559 */     throw new UnknownHostException("No matching address found for interface : " + paramString);
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
/* 570 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("ipaddress", byte[].class), new ObjectStreamField("scope_id", int.class), new ObjectStreamField("scope_id_set", boolean.class), new ObjectStreamField("scope_ifname_set", boolean.class), new ObjectStreamField("ifname", String.class) };
/*     */ 
/*     */   
/*     */   private static final long FIELDS_OFFSET;
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */   
/*     */   private static final int INT16SZ = 2;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 583 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 584 */       FIELDS_OFFSET = unsafe.objectFieldOffset(Inet6Address.class
/* 585 */           .getDeclaredField("holder6"));
/* 586 */       UNSAFE = unsafe;
/* 587 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 588 */       throw new Error(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 599 */     NetworkInterface networkInterface = null;
/*     */     
/* 601 */     if (getClass().getClassLoader() != null) {
/* 602 */       throw new SecurityException("invalid address type");
/*     */     }
/*     */     
/* 605 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 606 */     byte[] arrayOfByte = (byte[])getField.get("ipaddress", (Object)null);
/* 607 */     int i = getField.get("scope_id", -1);
/* 608 */     boolean bool1 = getField.get("scope_id_set", false);
/* 609 */     boolean bool2 = getField.get("scope_ifname_set", false);
/* 610 */     String str = (String)getField.get("ifname", (Object)null);
/*     */     
/* 612 */     if (str != null && !"".equals(str)) {
/*     */       try {
/* 614 */         networkInterface = NetworkInterface.getByName(str);
/* 615 */         if (networkInterface == null) {
/*     */ 
/*     */           
/* 618 */           bool1 = false;
/* 619 */           bool2 = false;
/* 620 */           i = 0;
/*     */         } else {
/* 622 */           bool2 = true;
/*     */           try {
/* 624 */             i = deriveNumericScope(arrayOfByte, networkInterface);
/* 625 */           } catch (UnknownHostException unknownHostException) {}
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 631 */       catch (SocketException socketException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 636 */     arrayOfByte = (byte[])arrayOfByte.clone();
/*     */ 
/*     */     
/* 639 */     if (arrayOfByte.length != 16) {
/* 640 */       throw new InvalidObjectException("invalid address length: " + arrayOfByte.length);
/*     */     }
/*     */ 
/*     */     
/* 644 */     if (this.holder.getFamily() != 2) {
/* 645 */       throw new InvalidObjectException("invalid address family type");
/*     */     }
/*     */     
/* 648 */     Inet6AddressHolder inet6AddressHolder = new Inet6AddressHolder(arrayOfByte, i, bool1, networkInterface, bool2);
/*     */ 
/*     */ 
/*     */     
/* 652 */     UNSAFE.putObject(this, FIELDS_OFFSET, inet6AddressHolder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 663 */     String str = null;
/*     */     
/* 665 */     if (this.holder6.scope_ifname != null) {
/* 666 */       str = this.holder6.scope_ifname.getName();
/* 667 */       this.holder6.scope_ifname_set = true;
/*     */     } 
/* 669 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 670 */     putField.put("ipaddress", this.holder6.ipaddress);
/* 671 */     putField.put("scope_id", this.holder6.scope_id);
/* 672 */     putField.put("scope_id_set", this.holder6.scope_id_set);
/* 673 */     putField.put("scope_ifname_set", this.holder6.scope_ifname_set);
/* 674 */     putField.put("ifname", str);
/* 675 */     paramObjectOutputStream.writeFields();
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
/*     */   public boolean isMulticastAddress() {
/* 690 */     return this.holder6.isMulticastAddress();
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
/*     */   public boolean isAnyLocalAddress() {
/* 703 */     return this.holder6.isAnyLocalAddress();
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
/*     */   public boolean isLoopbackAddress() {
/* 716 */     return this.holder6.isLoopbackAddress();
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
/*     */   public boolean isLinkLocalAddress() {
/* 729 */     return this.holder6.isLinkLocalAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isLinkLocalAddress(byte[] paramArrayOfbyte) {
/* 734 */     return ((paramArrayOfbyte[0] & 0xFF) == 254 && (paramArrayOfbyte[1] & 0xC0) == 128);
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
/*     */   public boolean isSiteLocalAddress() {
/* 748 */     return this.holder6.isSiteLocalAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isSiteLocalAddress(byte[] paramArrayOfbyte) {
/* 753 */     return ((paramArrayOfbyte[0] & 0xFF) == 254 && (paramArrayOfbyte[1] & 0xC0) == 192);
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
/*     */   public boolean isMCGlobal() {
/* 768 */     return this.holder6.isMCGlobal();
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
/*     */   public boolean isMCNodeLocal() {
/* 782 */     return this.holder6.isMCNodeLocal();
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
/*     */   public boolean isMCLinkLocal() {
/* 796 */     return this.holder6.isMCLinkLocal();
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
/*     */   public boolean isMCSiteLocal() {
/* 810 */     return this.holder6.isMCSiteLocal();
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
/*     */   public boolean isMCOrgLocal() {
/* 824 */     return this.holder6.isMCOrgLocal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getAddress() {
/* 835 */     return (byte[])this.holder6.ipaddress.clone();
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
/*     */   public int getScopeId() {
/* 847 */     return this.holder6.scope_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetworkInterface getScopedInterface() {
/* 858 */     return this.holder6.scope_ifname;
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
/*     */   public String getHostAddress() {
/* 872 */     return this.holder6.getHostAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 882 */     return this.holder6.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 903 */     if (paramObject == null || !(paramObject instanceof Inet6Address)) {
/* 904 */       return false;
/*     */     }
/* 906 */     Inet6Address inet6Address = (Inet6Address)paramObject;
/*     */     
/* 908 */     return this.holder6.equals(inet6Address.holder6);
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
/*     */   public boolean isIPv4CompatibleAddress() {
/* 921 */     return this.holder6.isIPv4CompatibleAddress();
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
/*     */   static String numericToTextFormat(byte[] paramArrayOfbyte) {
/* 936 */     StringBuilder stringBuilder = new StringBuilder(39);
/* 937 */     for (byte b = 0; b < 8; b++) {
/* 938 */       stringBuilder.append(Integer.toHexString(paramArrayOfbyte[b << 1] << 8 & 0xFF00 | paramArrayOfbyte[(b << 1) + 1] & 0xFF));
/*     */       
/* 940 */       if (b < 7) {
/* 941 */         stringBuilder.append(":");
/*     */       }
/*     */     } 
/* 944 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Inet6Address.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */