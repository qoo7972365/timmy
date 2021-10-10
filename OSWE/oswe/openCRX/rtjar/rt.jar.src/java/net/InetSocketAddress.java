/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.ObjectStreamField;
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
/*     */ public class InetSocketAddress
/*     */   extends SocketAddress
/*     */ {
/*     */   private final transient InetSocketAddressHolder holder;
/*     */   private static final long serialVersionUID = 5076001401234631237L;
/*     */   
/*     */   private static class InetSocketAddressHolder
/*     */   {
/*     */     private String hostname;
/*     */     private InetAddress addr;
/*     */     private int port;
/*     */     
/*     */     private InetSocketAddressHolder(String param1String, InetAddress param1InetAddress, int param1Int) {
/*  65 */       this.hostname = param1String;
/*  66 */       this.addr = param1InetAddress;
/*  67 */       this.port = param1Int;
/*     */     }
/*     */     
/*     */     private int getPort() {
/*  71 */       return this.port;
/*     */     }
/*     */     
/*     */     private InetAddress getAddress() {
/*  75 */       return this.addr;
/*     */     }
/*     */     
/*     */     private String getHostName() {
/*  79 */       if (this.hostname != null)
/*  80 */         return this.hostname; 
/*  81 */       if (this.addr != null)
/*  82 */         return this.addr.getHostName(); 
/*  83 */       return null;
/*     */     }
/*     */     
/*     */     private String getHostString() {
/*  87 */       if (this.hostname != null)
/*  88 */         return this.hostname; 
/*  89 */       if (this.addr != null) {
/*  90 */         if (this.addr.holder().getHostName() != null) {
/*  91 */           return this.addr.holder().getHostName();
/*     */         }
/*  93 */         return this.addr.getHostAddress();
/*     */       } 
/*  95 */       return null;
/*     */     }
/*     */     
/*     */     private boolean isUnresolved() {
/*  99 */       return (this.addr == null);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 104 */       if (isUnresolved()) {
/* 105 */         return this.hostname + ":" + this.port;
/*     */       }
/* 107 */       return this.addr.toString() + ":" + this.port;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/*     */       boolean bool;
/* 113 */       if (param1Object == null || !(param1Object instanceof InetSocketAddressHolder))
/* 114 */         return false; 
/* 115 */       InetSocketAddressHolder inetSocketAddressHolder = (InetSocketAddressHolder)param1Object;
/*     */       
/* 117 */       if (this.addr != null) {
/* 118 */         bool = this.addr.equals(inetSocketAddressHolder.addr);
/* 119 */       } else if (this.hostname != null) {
/*     */         
/* 121 */         bool = (inetSocketAddressHolder.addr == null && this.hostname.equalsIgnoreCase(inetSocketAddressHolder.hostname)) ? true : false;
/*     */       } else {
/* 123 */         bool = (inetSocketAddressHolder.addr == null && inetSocketAddressHolder.hostname == null) ? true : false;
/* 124 */       }  return (bool && this.port == inetSocketAddressHolder.port);
/*     */     }
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 129 */       if (this.addr != null)
/* 130 */         return this.addr.hashCode() + this.port; 
/* 131 */       if (this.hostname != null)
/* 132 */         return this.hostname.toLowerCase().hashCode() + this.port; 
/* 133 */       return this.port;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int checkPort(int paramInt) {
/* 142 */     if (paramInt < 0 || paramInt > 65535)
/* 143 */       throw new IllegalArgumentException("port out of range:" + paramInt); 
/* 144 */     return paramInt;
/*     */   }
/*     */   
/*     */   private static String checkHost(String paramString) {
/* 148 */     if (paramString == null)
/* 149 */       throw new IllegalArgumentException("hostname can't be null"); 
/* 150 */     return paramString;
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
/*     */   public InetSocketAddress(int paramInt) {
/* 166 */     this(InetAddress.anyLocalAddress(), paramInt);
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
/*     */   public InetSocketAddress(InetAddress paramInetAddress, int paramInt) {
/* 185 */     this
/*     */ 
/*     */       
/* 188 */       .holder = new InetSocketAddressHolder(null, (paramInetAddress == null) ? InetAddress.anyLocalAddress() : paramInetAddress, checkPort(paramInt));
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
/*     */   
/*     */   public InetSocketAddress(String paramString, int paramInt) {
/* 216 */     checkHost(paramString);
/* 217 */     InetAddress inetAddress = null;
/* 218 */     String str = null;
/*     */     try {
/* 220 */       inetAddress = InetAddress.getByName(paramString);
/* 221 */     } catch (UnknownHostException unknownHostException) {
/* 222 */       str = paramString;
/*     */     } 
/* 224 */     this.holder = new InetSocketAddressHolder(str, inetAddress, checkPort(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   private InetSocketAddress(int paramInt, String paramString) {
/* 229 */     this.holder = new InetSocketAddressHolder(paramString, null, paramInt);
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
/*     */   public static InetSocketAddress createUnresolved(String paramString, int paramInt) {
/* 254 */     return new InetSocketAddress(checkPort(paramInt), checkHost(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("hostname", String.class), new ObjectStreamField("addr", InetAddress.class), new ObjectStreamField("port", int.class) };
/*     */ 
/*     */   
/*     */   private static final long FIELDS_OFFSET;
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 271 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 272 */     putField.put("hostname", this.holder.hostname);
/* 273 */     putField.put("addr", this.holder.addr);
/* 274 */     putField.put("port", this.holder.port);
/* 275 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 282 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 283 */     String str = (String)getField.get("hostname", (Object)null);
/* 284 */     InetAddress inetAddress = (InetAddress)getField.get("addr", (Object)null);
/* 285 */     int i = getField.get("port", -1);
/*     */ 
/*     */     
/* 288 */     checkPort(i);
/* 289 */     if (str == null && inetAddress == null) {
/* 290 */       throw new InvalidObjectException("hostname and addr can't both be null");
/*     */     }
/*     */     
/* 293 */     InetSocketAddressHolder inetSocketAddressHolder = new InetSocketAddressHolder(str, inetAddress, i);
/*     */ 
/*     */     
/* 296 */     UNSAFE.putObject(this, FIELDS_OFFSET, inetSocketAddressHolder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObjectNoData() throws ObjectStreamException {
/* 302 */     throw new InvalidObjectException("Stream data required");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 309 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 310 */       FIELDS_OFFSET = unsafe.objectFieldOffset(InetSocketAddress.class
/* 311 */           .getDeclaredField("holder"));
/* 312 */       UNSAFE = unsafe;
/* 313 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 314 */       throw new Error(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getPort() {
/* 324 */     return this.holder.getPort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final InetAddress getAddress() {
/* 334 */     return this.holder.getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getHostName() {
/* 345 */     return this.holder.getHostName();
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
/*     */   public final String getHostString() {
/* 357 */     return this.holder.getHostString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isUnresolved() {
/* 367 */     return this.holder.isUnresolved();
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
/*     */   public String toString() {
/* 380 */     return this.holder.toString();
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
/*     */   public final boolean equals(Object paramObject) {
/* 405 */     if (paramObject == null || !(paramObject instanceof InetSocketAddress))
/* 406 */       return false; 
/* 407 */     return this.holder.equals(((InetSocketAddress)paramObject).holder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 417 */     return this.holder.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/InetSocketAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */