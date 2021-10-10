/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.net.SocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClientId
/*     */ {
/*     */   private final int version;
/*     */   private final String hostname;
/*     */   private final int port;
/*     */   private final String protocol;
/*     */   private final Control[] bindCtls;
/*     */   private final OutputStream trace;
/*     */   private final String socketFactory;
/*     */   private final int myHash;
/*     */   private final int ctlHash;
/*  67 */   private SocketFactory factory = null;
/*  68 */   private Method sockComparator = null;
/*     */   
/*     */   private boolean isDefaultSockFactory = false;
/*     */   public static final boolean debug = false;
/*     */   
/*     */   ClientId(int paramInt1, String paramString1, int paramInt2, String paramString2, Control[] paramArrayOfControl, OutputStream paramOutputStream, String paramString3) {
/*  74 */     this.version = paramInt1;
/*  75 */     this.hostname = paramString1.toLowerCase(Locale.ENGLISH);
/*  76 */     this.port = paramInt2;
/*  77 */     this.protocol = paramString2;
/*  78 */     this.bindCtls = (paramArrayOfControl != null) ? (Control[])paramArrayOfControl.clone() : null;
/*  79 */     this.trace = paramOutputStream;
/*     */ 
/*     */ 
/*     */     
/*  83 */     this.socketFactory = paramString3;
/*  84 */     if (paramString3 != null && 
/*  85 */       !paramString3.equals("javax.net.ssl.SSLSocketFactory")) {
/*     */       
/*     */       try {
/*  88 */         Class<?> clazz1 = Obj.helper.loadClass(paramString3);
/*  89 */         Class<?> clazz2 = Class.forName("java.lang.Object");
/*  90 */         this.sockComparator = clazz1.getMethod("compare", new Class[] { clazz2, clazz2 });
/*     */         
/*  92 */         Method method = clazz1.getMethod("getDefault", new Class[0]);
/*     */         
/*  94 */         this
/*  95 */           .factory = (SocketFactory)method.invoke(null, new Object[0]);
/*  96 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 105 */       this.isDefaultSockFactory = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this
/*     */ 
/*     */ 
/*     */       
/* 116 */       .myHash = paramInt1 + paramInt2 + ((paramOutputStream != null) ? paramOutputStream.hashCode() : 0) + ((this.hostname != null) ? this.hostname.hashCode() : 0) + ((paramString2 != null) ? paramString2.hashCode() : 0) + (this.ctlHash = hashCodeControls(paramArrayOfControl));
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 120 */     if (!(paramObject instanceof ClientId)) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     ClientId clientId = (ClientId)paramObject;
/*     */     
/* 126 */     return (this.myHash == clientId.myHash && this.version == clientId.version && this.port == clientId.port && this.trace == clientId.trace && (this.hostname == clientId.hostname || (this.hostname != null && this.hostname
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       .equals(clientId.hostname))) && (this.protocol == clientId.protocol || (this.protocol != null && this.protocol
/*     */       
/* 133 */       .equals(clientId.protocol))) && this.ctlHash == clientId.ctlHash && 
/*     */       
/* 135 */       equalsControls(this.bindCtls, clientId.bindCtls) && 
/* 136 */       equalsSockFactory(clientId));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 140 */     return this.myHash;
/*     */   }
/*     */   
/*     */   private static int hashCodeControls(Control[] paramArrayOfControl) {
/* 144 */     if (paramArrayOfControl == null) {
/* 145 */       return 0;
/*     */     }
/*     */     
/* 148 */     int i = 0;
/* 149 */     for (byte b = 0; b < paramArrayOfControl.length; b++) {
/* 150 */       i = i * 31 + paramArrayOfControl[b].getID().hashCode();
/*     */     }
/* 152 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean equalsControls(Control[] paramArrayOfControl1, Control[] paramArrayOfControl2) {
/* 156 */     if (paramArrayOfControl1 == paramArrayOfControl2) {
/* 157 */       return true;
/*     */     }
/* 159 */     if (paramArrayOfControl1 == null || paramArrayOfControl2 == null) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (paramArrayOfControl1.length != paramArrayOfControl2.length) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     for (byte b = 0; b < paramArrayOfControl1.length; b++) {
/* 167 */       if (!paramArrayOfControl1[b].getID().equals(paramArrayOfControl2[b].getID()) || paramArrayOfControl1[b]
/* 168 */         .isCritical() != paramArrayOfControl2[b].isCritical() || 
/* 169 */         !Arrays.equals(paramArrayOfControl1[b].getEncodedValue(), paramArrayOfControl2[b]
/* 170 */           .getEncodedValue())) {
/* 171 */         return false;
/*     */       }
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equalsSockFactory(ClientId paramClientId) {
/* 178 */     if (this.isDefaultSockFactory && paramClientId.isDefaultSockFactory) {
/* 179 */       return true;
/*     */     }
/* 181 */     if (!paramClientId.isDefaultSockFactory) {
/* 182 */       return invokeComparator(paramClientId, this);
/*     */     }
/* 184 */     return invokeComparator(this, paramClientId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean invokeComparator(ClientId paramClientId1, ClientId paramClientId2) {
/*     */     Object object;
/*     */     try {
/* 193 */       object = paramClientId1.sockComparator.invoke(paramClientId1.factory, new Object[] { paramClientId1.socketFactory, paramClientId2.socketFactory });
/*     */     }
/* 195 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       return false;
/*     */     } 
/* 203 */     if (((Integer)object).intValue() == 0) {
/* 204 */       return true;
/*     */     }
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   private static String toStringControls(Control[] paramArrayOfControl) {
/* 210 */     if (paramArrayOfControl == null) {
/* 211 */       return "";
/*     */     }
/* 213 */     StringBuffer stringBuffer = new StringBuffer();
/* 214 */     for (byte b = 0; b < paramArrayOfControl.length; b++) {
/* 215 */       stringBuffer.append(paramArrayOfControl[b].getID());
/* 216 */       stringBuffer.append(' ');
/*     */     } 
/* 218 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 222 */     return this.hostname + ":" + this.port + ":" + ((this.protocol != null) ? this.protocol : "") + ":" + 
/*     */       
/* 224 */       toStringControls(this.bindCtls) + ":" + this.socketFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/ClientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */