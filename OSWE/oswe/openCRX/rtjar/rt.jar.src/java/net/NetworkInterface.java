/*     */ package java.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetworkInterface
/*     */ {
/*     */   private String name;
/*     */   private String displayName;
/*     */   private int index;
/*     */   private InetAddress[] addrs;
/*     */   private InterfaceAddress[] bindings;
/*     */   private NetworkInterface[] childs;
/*  50 */   private NetworkInterface parent = null;
/*     */   
/*     */   private boolean virtual = false;
/*     */ 
/*     */   
/*     */   static {
/*  56 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  59 */             System.loadLibrary("net");
/*  60 */             return null;
/*     */           }
/*     */         });
/*     */     
/*  64 */     init();
/*  65 */   } private static final NetworkInterface defaultInterface = DefaultInterface.getDefault(); static {
/*  66 */     if (defaultInterface != null) {
/*  67 */       defaultIndex = defaultInterface.getIndex();
/*     */     } else {
/*  69 */       defaultIndex = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int defaultIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NetworkInterface(String paramString, int paramInt, InetAddress[] paramArrayOfInetAddress) {
/*  83 */     this.name = paramString;
/*  84 */     this.index = paramInt;
/*  85 */     this.addrs = paramArrayOfInetAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  94 */     return this.name;
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
/*     */   public Enumeration<InetAddress> getInetAddresses() {
/*     */     class checkedAddresses
/*     */       implements Enumeration<InetAddress>
/*     */     {
/* 114 */       private int i = 0; private int count = 0;
/*     */       private InetAddress[] local_addrs;
/*     */       
/*     */       checkedAddresses() {
/* 118 */         this.local_addrs = new InetAddress[NetworkInterface.this.addrs.length];
/* 119 */         boolean bool = true;
/*     */         
/* 121 */         SecurityManager securityManager = System.getSecurityManager();
/* 122 */         if (securityManager != null) {
/*     */           try {
/* 124 */             securityManager.checkPermission(new NetPermission("getNetworkInformation"));
/* 125 */           } catch (SecurityException securityException) {
/* 126 */             bool = false;
/*     */           } 
/*     */         }
/* 129 */         for (byte b = 0; b < NetworkInterface.this.addrs.length; b++) {
/*     */           try {
/* 131 */             if (securityManager != null && !bool) {
/* 132 */               securityManager.checkConnect(NetworkInterface.this.addrs[b].getHostAddress(), -1);
/*     */             }
/* 134 */             this.local_addrs[this.count++] = NetworkInterface.this.addrs[b];
/* 135 */           } catch (SecurityException securityException) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public InetAddress nextElement() {
/* 141 */         if (this.i < this.count) {
/* 142 */           return this.local_addrs[this.i++];
/*     */         }
/* 144 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasMoreElements() {
/* 149 */         return (this.i < this.count);
/*     */       }
/*     */     };
/* 152 */     return new checkedAddresses();
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
/*     */   public List<InterfaceAddress> getInterfaceAddresses() {
/* 170 */     ArrayList<InterfaceAddress> arrayList = new ArrayList(1);
/* 171 */     SecurityManager securityManager = System.getSecurityManager();
/* 172 */     for (byte b = 0; b < this.bindings.length; b++) {
/*     */       try {
/* 174 */         if (securityManager != null) {
/* 175 */           securityManager.checkConnect(this.bindings[b].getAddress().getHostAddress(), -1);
/*     */         }
/* 177 */         arrayList.add(this.bindings[b]);
/* 178 */       } catch (SecurityException securityException) {}
/*     */     } 
/* 180 */     return arrayList;
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
/*     */   public Enumeration<NetworkInterface> getSubInterfaces() {
/*     */     class subIFs
/*     */       implements Enumeration<NetworkInterface>
/*     */     {
/* 196 */       private int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public NetworkInterface nextElement() {
/* 202 */         if (this.i < NetworkInterface.this.childs.length) {
/* 203 */           return NetworkInterface.this.childs[this.i++];
/*     */         }
/* 205 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasMoreElements() {
/* 210 */         return (this.i < NetworkInterface.this.childs.length);
/*     */       }
/*     */     };
/* 213 */     return new subIFs();
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
/*     */   public NetworkInterface getParent() {
/* 226 */     return this.parent;
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
/*     */   public int getIndex() {
/* 241 */     return this.index;
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
/*     */   public String getDisplayName() {
/* 254 */     return "".equals(this.displayName) ? null : this.displayName;
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
/*     */   public static NetworkInterface getByName(String paramString) throws SocketException {
/* 274 */     if (paramString == null)
/* 275 */       throw new NullPointerException(); 
/* 276 */     return getByName0(paramString);
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
/*     */   public static NetworkInterface getByIndex(int paramInt) throws SocketException {
/* 291 */     if (paramInt < 0)
/* 292 */       throw new IllegalArgumentException("Interface index can't be negative"); 
/* 293 */     return getByIndex0(paramInt);
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
/*     */   public static NetworkInterface getByInetAddress(InetAddress paramInetAddress) throws SocketException {
/* 319 */     if (paramInetAddress == null) {
/* 320 */       throw new NullPointerException();
/*     */     }
/* 322 */     if (paramInetAddress instanceof Inet4Address) {
/* 323 */       Inet4Address inet4Address = (Inet4Address)paramInetAddress;
/* 324 */       if (inet4Address.holder.family != 1) {
/* 325 */         throw new IllegalArgumentException("invalid family type: " + inet4Address.holder.family);
/*     */       }
/*     */     }
/* 328 */     else if (paramInetAddress instanceof Inet6Address) {
/* 329 */       Inet6Address inet6Address = (Inet6Address)paramInetAddress;
/* 330 */       if (inet6Address.holder.family != 2) {
/* 331 */         throw new IllegalArgumentException("invalid family type: " + inet6Address.holder.family);
/*     */       }
/*     */     } else {
/*     */       
/* 335 */       throw new IllegalArgumentException("invalid address type: " + paramInetAddress);
/*     */     } 
/* 337 */     return getByInetAddress0(paramInetAddress);
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
/*     */   public static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
/* 355 */     final NetworkInterface[] netifs = getAll();
/*     */ 
/*     */     
/* 358 */     if (arrayOfNetworkInterface == null) {
/* 359 */       return null;
/*     */     }
/* 361 */     return new Enumeration<NetworkInterface>() {
/* 362 */         private int i = 0;
/*     */         public NetworkInterface nextElement() {
/* 364 */           if (netifs != null && this.i < netifs.length) {
/* 365 */             return netifs[this.i++];
/*     */           }
/*     */           
/* 368 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasMoreElements() {
/* 373 */           return (netifs != null && this.i < netifs.length);
/*     */         }
/*     */       };
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
/*     */   public boolean isUp() throws SocketException {
/* 399 */     return isUp0(this.name, this.index);
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
/*     */   public boolean isLoopback() throws SocketException {
/* 411 */     return isLoopback0(this.name, this.index);
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
/*     */   public boolean isPointToPoint() throws SocketException {
/* 426 */     return isP2P0(this.name, this.index);
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
/*     */   public boolean supportsMulticast() throws SocketException {
/* 438 */     return supportsMulticast0(this.name, this.index);
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
/*     */   public byte[] getHardwareAddress() throws SocketException {
/* 456 */     SecurityManager securityManager = System.getSecurityManager();
/* 457 */     if (securityManager != null) {
/*     */       try {
/* 459 */         securityManager.checkPermission(new NetPermission("getNetworkInformation"));
/* 460 */       } catch (SecurityException securityException) {
/* 461 */         if (!getInetAddresses().hasMoreElements())
/*     */         {
/* 463 */           return null;
/*     */         }
/*     */       } 
/*     */     }
/* 467 */     for (InetAddress inetAddress : this.addrs) {
/* 468 */       if (inetAddress instanceof Inet4Address) {
/* 469 */         return getMacAddr0(((Inet4Address)inetAddress).getAddress(), this.name, this.index);
/*     */       }
/*     */     } 
/* 472 */     return getMacAddr0(null, this.name, this.index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMTU() throws SocketException {
/* 483 */     return getMTU0(this.name, this.index);
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
/*     */   public boolean isVirtual() {
/* 500 */     return this.virtual;
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
/*     */   public boolean equals(Object paramObject) {
/* 525 */     if (!(paramObject instanceof NetworkInterface)) {
/* 526 */       return false;
/*     */     }
/* 528 */     NetworkInterface networkInterface = (NetworkInterface)paramObject;
/* 529 */     if (this.name != null) {
/* 530 */       if (!this.name.equals(networkInterface.name)) {
/* 531 */         return false;
/*     */       }
/*     */     }
/* 534 */     else if (networkInterface.name != null) {
/* 535 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 539 */     if (this.addrs == null)
/* 540 */       return (networkInterface.addrs == null); 
/* 541 */     if (networkInterface.addrs == null) {
/* 542 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 547 */     if (this.addrs.length != networkInterface.addrs.length) {
/* 548 */       return false;
/*     */     }
/*     */     
/* 551 */     InetAddress[] arrayOfInetAddress = networkInterface.addrs;
/* 552 */     int i = arrayOfInetAddress.length;
/*     */     
/* 554 */     for (byte b = 0; b < i; b++) {
/* 555 */       boolean bool = false;
/* 556 */       for (byte b1 = 0; b1 < i; b1++) {
/* 557 */         if (this.addrs[b].equals(arrayOfInetAddress[b1])) {
/* 558 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 562 */       if (!bool) {
/* 563 */         return false;
/*     */       }
/*     */     } 
/* 566 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 570 */     return (this.name == null) ? 0 : this.name.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 574 */     String str = "name:";
/* 575 */     str = str + ((this.name == null) ? "null" : this.name);
/* 576 */     if (this.displayName != null) {
/* 577 */       str = str + " (" + this.displayName + ")";
/*     */     }
/* 579 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NetworkInterface getDefault() {
/* 590 */     return defaultInterface;
/*     */   }
/*     */   
/*     */   NetworkInterface() {}
/*     */   
/*     */   private static native NetworkInterface[] getAll() throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByName0(String paramString) throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByIndex0(int paramInt) throws SocketException;
/*     */   
/*     */   private static native NetworkInterface getByInetAddress0(InetAddress paramInetAddress) throws SocketException;
/*     */   
/*     */   private static native boolean isUp0(String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native boolean isLoopback0(String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native boolean supportsMulticast0(String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native boolean isP2P0(String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native byte[] getMacAddr0(byte[] paramArrayOfbyte, String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native int getMTU0(String paramString, int paramInt) throws SocketException;
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/NetworkInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */