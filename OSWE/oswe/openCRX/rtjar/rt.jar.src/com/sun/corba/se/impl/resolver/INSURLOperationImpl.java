/*     */ package com.sun.corba.se.impl.resolver;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.naming.namingutil.CorbalocURL;
/*     */ import com.sun.corba.se.impl.naming.namingutil.CorbanameURL;
/*     */ import com.sun.corba.se.impl.naming.namingutil.IIOPEndpointInfo;
/*     */ import com.sun.corba.se.impl.naming.namingutil.INSURL;
/*     */ import com.sun.corba.se.impl.naming.namingutil.INSURLHandler;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.Operation;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CosNaming.NamingContextExt;
/*     */ import org.omg.CosNaming.NamingContextExtHelper;
/*     */ import sun.corba.EncapsInputStreamFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class INSURLOperationImpl
/*     */   implements Operation
/*     */ {
/*     */   ORB orb;
/*     */   ORBUtilSystemException wrapper;
/*     */   OMGSystemException omgWrapper;
/*     */   Resolver bootstrapResolver;
/*     */   private NamingContextExt rootNamingContextExt;
/*  87 */   private Object rootContextCacheLock = new Object();
/*     */ 
/*     */   
/*  90 */   private INSURLHandler insURLHandler = INSURLHandler.getINSURLHandler(); private static final int NIBBLES_PER_BYTE = 2;
/*     */   private static final int UN_SHIFT = 4;
/*     */   
/*     */   public INSURLOperationImpl(ORB paramORB, Resolver paramResolver) {
/*  94 */     this.orb = paramORB;
/*  95 */     this.wrapper = ORBUtilSystemException.get(paramORB, "orb.resolver");
/*     */     
/*  97 */     this.omgWrapper = OMGSystemException.get(paramORB, "orb.resolver");
/*     */     
/*  99 */     this.bootstrapResolver = paramResolver;
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
/*     */   private Object getIORFromString(String paramString) {
/* 111 */     if ((paramString.length() & 0x1) == 1) {
/* 112 */       throw this.wrapper.badStringifiedIorLen();
/*     */     }
/* 114 */     byte[] arrayOfByte = new byte[(paramString.length() - "IOR:".length()) / 2]; int i; byte b;
/* 115 */     for (i = "IOR:".length(), b = 0; i < paramString.length(); i += 2, b++) {
/* 116 */       arrayOfByte[b] = (byte)(ORBUtility.hexOf(paramString.charAt(i)) << 4 & 0xF0);
/* 117 */       arrayOfByte[b] = (byte)(arrayOfByte[b] | (byte)(ORBUtility.hexOf(paramString.charAt(i + 1)) & 0xF));
/*     */     } 
/* 119 */     EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream((ORB)this.orb, arrayOfByte, arrayOfByte.length, this.orb
/* 120 */         .getORBData().getGIOPVersion());
/* 121 */     encapsInputStream.consumeEndian();
/* 122 */     return encapsInputStream.read_Object();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object operate(Object paramObject) {
/* 127 */     if (paramObject instanceof String) {
/* 128 */       String str = (String)paramObject;
/*     */       
/* 130 */       if (str.startsWith("IOR:"))
/*     */       {
/* 132 */         return getIORFromString(str);
/*     */       }
/* 134 */       INSURL iNSURL = this.insURLHandler.parseURL(str);
/* 135 */       if (iNSURL == null)
/* 136 */         throw this.omgWrapper.soBadSchemeName(); 
/* 137 */       return resolveINSURL(iNSURL);
/*     */     } 
/*     */ 
/*     */     
/* 141 */     throw this.wrapper.stringExpected();
/*     */   }
/*     */ 
/*     */   
/*     */   private Object resolveINSURL(INSURL paramINSURL) {
/* 146 */     if (paramINSURL.isCorbanameURL()) {
/* 147 */       return resolveCorbaname((CorbanameURL)paramINSURL);
/*     */     }
/* 149 */     return resolveCorbaloc((CorbalocURL)paramINSURL);
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
/*     */   private Object resolveCorbaloc(CorbalocURL paramCorbalocURL) {
/* 161 */     Object object = null;
/*     */     
/* 163 */     if (paramCorbalocURL.getRIRFlag()) {
/* 164 */       object = this.bootstrapResolver.resolve(paramCorbalocURL.getKeyString());
/*     */     } else {
/* 166 */       object = getIORUsingCorbaloc((INSURL)paramCorbalocURL);
/*     */     } 
/*     */     
/* 169 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object resolveCorbaname(CorbanameURL paramCorbanameURL) {
/* 178 */     Object object = null;
/*     */     
/*     */     try {
/* 181 */       NamingContextExt namingContextExt = null;
/*     */       
/* 183 */       if (paramCorbanameURL.getRIRFlag()) {
/*     */         
/* 185 */         namingContextExt = getDefaultRootNamingContext();
/*     */       }
/*     */       else {
/*     */         
/* 189 */         Object object1 = getIORUsingCorbaloc((INSURL)paramCorbanameURL);
/* 190 */         if (object1 == null) {
/* 191 */           return null;
/*     */         }
/*     */ 
/*     */         
/* 195 */         namingContextExt = NamingContextExtHelper.narrow(object1);
/*     */       } 
/*     */       
/* 198 */       String str = paramCorbanameURL.getStringifiedName();
/*     */       
/* 200 */       if (str == null)
/*     */       {
/* 202 */         return (Object)namingContextExt;
/*     */       }
/* 204 */       return namingContextExt.resolve_str(str);
/*     */     }
/* 206 */     catch (Exception exception) {
/* 207 */       clearRootNamingContextCache();
/* 208 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getIORUsingCorbaloc(INSURL paramINSURL) {
/* 219 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 220 */     ArrayList<IIOPProfileTemplate> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */     
/* 224 */     List list = paramINSURL.getEndpointInfo();
/* 225 */     String str = paramINSURL.getKeyString();
/*     */     
/* 227 */     if (str == null) {
/* 228 */       return null;
/*     */     }
/*     */     
/* 231 */     ObjectKey objectKey = this.orb.getObjectKeyFactory().create(str
/* 232 */         .getBytes());
/* 233 */     IORTemplate iORTemplate = IORFactories.makeIORTemplate(objectKey.getTemplate());
/* 234 */     Iterator<IIOPEndpointInfo> iterator = list.iterator();
/* 235 */     while (iterator.hasNext()) {
/*     */       
/* 237 */       IIOPEndpointInfo iIOPEndpointInfo = iterator.next();
/* 238 */       IIOPAddress iIOPAddress = IIOPFactories.makeIIOPAddress(this.orb, iIOPEndpointInfo.getHost(), iIOPEndpointInfo
/* 239 */           .getPort());
/* 240 */       GIOPVersion gIOPVersion1 = GIOPVersion.getInstance((byte)iIOPEndpointInfo.getMajor(), 
/* 241 */           (byte)iIOPEndpointInfo.getMinor());
/* 242 */       IIOPProfileTemplate iIOPProfileTemplate1 = null;
/* 243 */       if (gIOPVersion1.equals(GIOPVersion.V1_0)) {
/* 244 */         iIOPProfileTemplate1 = IIOPFactories.makeIIOPProfileTemplate(this.orb, gIOPVersion1, iIOPAddress);
/*     */         
/* 246 */         arrayList.add(iIOPProfileTemplate1); continue;
/*     */       } 
/* 248 */       if (hashMap.get(gIOPVersion1) == null) {
/* 249 */         iIOPProfileTemplate1 = IIOPFactories.makeIIOPProfileTemplate(this.orb, gIOPVersion1, iIOPAddress);
/*     */         
/* 251 */         hashMap.put(gIOPVersion1, iIOPProfileTemplate1); continue;
/*     */       } 
/* 253 */       iIOPProfileTemplate1 = (IIOPProfileTemplate)hashMap.get(gIOPVersion1);
/*     */       
/* 255 */       AlternateIIOPAddressComponent alternateIIOPAddressComponent = IIOPFactories.makeAlternateIIOPAddressComponent(iIOPAddress);
/* 256 */       iIOPProfileTemplate1.add(alternateIIOPAddressComponent);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 261 */     GIOPVersion gIOPVersion = this.orb.getORBData().getGIOPVersion();
/* 262 */     IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)hashMap.get(gIOPVersion);
/* 263 */     if (iIOPProfileTemplate != null) {
/* 264 */       iORTemplate.add(iIOPProfileTemplate);
/* 265 */       hashMap.remove(gIOPVersion);
/*     */     } 
/*     */ 
/*     */     
/* 269 */     Comparator<?> comparator = new Comparator() {
/*     */         public int compare(Object param1Object1, Object param1Object2) {
/* 271 */           GIOPVersion gIOPVersion1 = (GIOPVersion)param1Object1;
/* 272 */           GIOPVersion gIOPVersion2 = (GIOPVersion)param1Object2;
/* 273 */           return gIOPVersion1.lessThan(gIOPVersion2) ? 1 : (gIOPVersion1.equals(gIOPVersion2) ? 0 : -1);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 278 */     ArrayList<?> arrayList1 = new ArrayList(hashMap.keySet());
/* 279 */     Collections.sort(arrayList1, comparator);
/*     */ 
/*     */     
/* 282 */     Iterator<?> iterator1 = arrayList1.iterator();
/* 283 */     while (iterator1.hasNext()) {
/* 284 */       IIOPProfileTemplate iIOPProfileTemplate1 = (IIOPProfileTemplate)hashMap.get(iterator1.next());
/* 285 */       iORTemplate.add(iIOPProfileTemplate1);
/*     */     } 
/*     */ 
/*     */     
/* 289 */     iORTemplate.addAll(arrayList);
/*     */     
/* 291 */     IOR iOR = iORTemplate.makeIOR(this.orb, "", objectKey.getId());
/* 292 */     return ORBUtility.makeObjectReference(iOR);
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
/*     */   private NamingContextExt getDefaultRootNamingContext() {
/* 306 */     synchronized (this.rootContextCacheLock) {
/* 307 */       if (this.rootNamingContextExt == null) {
/*     */         try {
/* 309 */           this
/* 310 */             .rootNamingContextExt = NamingContextExtHelper.narrow(this.orb
/* 311 */               .getLocalResolver().resolve("NameService"));
/* 312 */         } catch (Exception exception) {
/* 313 */           this.rootNamingContextExt = null;
/*     */         } 
/*     */       }
/*     */     } 
/* 317 */     return this.rootNamingContextExt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearRootNamingContextCache() {
/* 325 */     synchronized (this.rootContextCacheLock) {
/* 326 */       this.rootNamingContextExt = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/INSURLOperationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */