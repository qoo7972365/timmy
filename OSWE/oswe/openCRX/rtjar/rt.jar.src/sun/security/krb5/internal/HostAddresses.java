/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HostAddresses
/*     */   implements Cloneable
/*     */ {
/*  67 */   private static boolean DEBUG = Krb5.DEBUG;
/*  68 */   private HostAddress[] addresses = null;
/*  69 */   private volatile int hashCode = 0;
/*     */   
/*     */   public HostAddresses(HostAddress[] paramArrayOfHostAddress) throws IOException {
/*  72 */     if (paramArrayOfHostAddress != null) {
/*  73 */       this.addresses = new HostAddress[paramArrayOfHostAddress.length];
/*  74 */       for (byte b = 0; b < paramArrayOfHostAddress.length; b++) {
/*  75 */         if (paramArrayOfHostAddress[b] == null) {
/*  76 */           throw new IOException("Cannot create a HostAddress");
/*     */         }
/*  78 */         this.addresses[b] = (HostAddress)paramArrayOfHostAddress[b].clone();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddresses() throws UnknownHostException {
/*  85 */     this.addresses = new HostAddress[1];
/*  86 */     this.addresses[0] = new HostAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   private HostAddresses(int paramInt) {}
/*     */ 
/*     */   
/*     */   public HostAddresses(PrincipalName paramPrincipalName) throws UnknownHostException, KrbException {
/*  94 */     String[] arrayOfString = paramPrincipalName.getNameStrings();
/*     */     
/*  96 */     if (paramPrincipalName.getNameType() != 3 || arrayOfString.length < 2)
/*     */     {
/*  98 */       throw new KrbException(60, "Bad name");
/*     */     }
/* 100 */     String str = arrayOfString[1];
/* 101 */     InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(str);
/* 102 */     HostAddress[] arrayOfHostAddress = new HostAddress[arrayOfInetAddress.length];
/*     */     
/* 104 */     for (byte b = 0; b < arrayOfInetAddress.length; b++) {
/* 105 */       arrayOfHostAddress[b] = new HostAddress(arrayOfInetAddress[b]);
/*     */     }
/*     */     
/* 108 */     this.addresses = arrayOfHostAddress;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 112 */     HostAddresses hostAddresses = new HostAddresses(0);
/* 113 */     if (this.addresses != null) {
/* 114 */       hostAddresses.addresses = new HostAddress[this.addresses.length];
/* 115 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 116 */         hostAddresses.addresses[b] = (HostAddress)this.addresses[b]
/* 117 */           .clone();
/*     */       }
/*     */     } 
/* 120 */     return hostAddresses;
/*     */   }
/*     */   
/*     */   public boolean inList(HostAddress paramHostAddress) {
/* 124 */     if (this.addresses != null)
/* 125 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 126 */         if (this.addresses[b].equals(paramHostAddress))
/* 127 */           return true; 
/*     */       }  
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 133 */     if (this.hashCode == 0) {
/* 134 */       int i = 17;
/* 135 */       if (this.addresses != null) {
/* 136 */         for (byte b = 0; b < this.addresses.length; b++) {
/* 137 */           i = 37 * i + this.addresses[b].hashCode();
/*     */         }
/*     */       }
/* 140 */       this.hashCode = i;
/*     */     } 
/* 142 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 148 */     if (this == paramObject) {
/* 149 */       return true;
/*     */     }
/*     */     
/* 152 */     if (!(paramObject instanceof HostAddresses)) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     HostAddresses hostAddresses = (HostAddresses)paramObject;
/* 157 */     if ((this.addresses == null && hostAddresses.addresses != null) || (this.addresses != null && hostAddresses.addresses == null))
/*     */     {
/* 159 */       return false; } 
/* 160 */     if (this.addresses != null && hostAddresses.addresses != null) {
/* 161 */       if (this.addresses.length != hostAddresses.addresses.length)
/* 162 */         return false; 
/* 163 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 164 */         if (!this.addresses[b].equals(hostAddresses.addresses[b]))
/* 165 */           return false; 
/*     */       } 
/* 167 */     }  return true;
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
/*     */   public HostAddresses(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 180 */     Vector<HostAddress> vector = new Vector();
/* 181 */     DerValue derValue = null;
/* 182 */     while (paramDerValue.getData().available() > 0) {
/* 183 */       derValue = paramDerValue.getData().getDerValue();
/* 184 */       vector.addElement(new HostAddress(derValue));
/*     */     } 
/* 186 */     if (vector.size() > 0) {
/* 187 */       this.addresses = new HostAddress[vector.size()];
/* 188 */       vector.copyInto((Object[])this.addresses);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 202 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 203 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 205 */     if (this.addresses != null && this.addresses.length > 0)
/* 206 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 207 */         derOutputStream1.write(this.addresses[b].asn1Encode());
/*     */       } 
/* 209 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 210 */     return derOutputStream2.toByteArray();
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
/*     */   public static HostAddresses parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 229 */     if (paramBoolean && (
/* 230 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 231 */       return null; 
/* 232 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 233 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 234 */       throw new Asn1Exception(906);
/*     */     }
/* 236 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 237 */     return new HostAddresses(derValue2);
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
/*     */   public void writeAddrs(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 251 */     if (this.addresses == null || this.addresses.length == 0) {
/* 252 */       paramCCacheOutputStream.write32(0);
/*     */       return;
/*     */     } 
/* 255 */     paramCCacheOutputStream.write32(this.addresses.length);
/* 256 */     for (byte b = 0; b < this.addresses.length; b++) {
/* 257 */       paramCCacheOutputStream.write16((this.addresses[b]).addrType);
/* 258 */       paramCCacheOutputStream.write32((this.addresses[b]).address.length);
/* 259 */       paramCCacheOutputStream.write((this.addresses[b]).address, 0, (this.addresses[b]).address.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress[] getInetAddresses() {
/* 267 */     if (this.addresses == null || this.addresses.length == 0) {
/* 268 */       return null;
/*     */     }
/* 270 */     ArrayList<InetAddress> arrayList = new ArrayList(this.addresses.length);
/*     */     
/* 272 */     for (byte b = 0; b < this.addresses.length; b++) {
/*     */       try {
/* 274 */         if ((this.addresses[b]).addrType == 2 || (this.addresses[b]).addrType == 24)
/*     */         {
/* 276 */           arrayList.add(this.addresses[b].getInetAddress());
/*     */         }
/* 278 */       } catch (UnknownHostException unknownHostException) {
/*     */         
/* 280 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     InetAddress[] arrayOfInetAddress = new InetAddress[arrayList.size()];
/* 285 */     return arrayList.<InetAddress>toArray(arrayOfInetAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostAddresses getLocalAddresses() throws IOException {
/* 294 */     LinkedHashSet<InetAddress> linkedHashSet = new LinkedHashSet();
/*     */     try {
/* 296 */       if (DEBUG) {
/* 297 */         System.out.println(">>> KrbKdcReq local addresses are:");
/*     */       }
/* 299 */       String str = Config.getInstance().getAll(new String[] { "libdefaults", "extra_addresses" });
/*     */       
/* 301 */       if (str != null) {
/* 302 */         for (String str1 : str.split("\\s+")) {
/* 303 */           linkedHashSet.add(InetAddress.getByName(str1));
/* 304 */           if (DEBUG) {
/* 305 */             System.out.println("   extra_addresses: " + 
/* 306 */                 InetAddress.getByName(str1));
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 311 */       for (NetworkInterface networkInterface : Collections.<NetworkInterface>list(NetworkInterface.getNetworkInterfaces())) {
/* 312 */         if (DEBUG) {
/* 313 */           System.out.println("   NetworkInterface " + networkInterface + ":");
/* 314 */           System.out.println("      " + 
/* 315 */               Collections.<InetAddress>list(networkInterface.getInetAddresses()));
/*     */         } 
/* 317 */         linkedHashSet.addAll(Collections.list(networkInterface.getInetAddresses()));
/*     */       } 
/* 319 */       return new HostAddresses(linkedHashSet.<InetAddress>toArray(new InetAddress[linkedHashSet.size()]));
/* 320 */     } catch (Exception exception) {
/* 321 */       throw new IOException(exception.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HostAddresses(InetAddress[] paramArrayOfInetAddress) {
/* 331 */     if (paramArrayOfInetAddress == null) {
/*     */       
/* 333 */       this.addresses = null;
/*     */       
/*     */       return;
/*     */     } 
/* 337 */     this.addresses = new HostAddress[paramArrayOfInetAddress.length];
/* 338 */     for (byte b = 0; b < paramArrayOfInetAddress.length; b++) {
/* 339 */       this.addresses[b] = new HostAddress(paramArrayOfInetAddress[b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 344 */     return Arrays.toString((Object[])this.addresses);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/HostAddresses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */