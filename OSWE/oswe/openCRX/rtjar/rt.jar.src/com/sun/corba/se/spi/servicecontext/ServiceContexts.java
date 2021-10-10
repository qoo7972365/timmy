/*     */ package com.sun.corba.se.spi.servicecontext;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.org.omg.SendingContext.CodeBase;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.OctetSeqHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*     */ public class ServiceContexts
/*     */ {
/*     */   private static final int JAVAIDL_ALIGN_SERVICE_ID = -1106033203;
/*     */   private ORB orb;
/*     */   private Map scMap;
/*     */   private boolean addAlignmentOnWrite;
/*     */   private CodeBase codeBase;
/*     */   private GIOPVersion giopVersion;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   private static boolean isDebugging(OutputStream paramOutputStream) {
/*  67 */     ORB oRB = (ORB)paramOutputStream.orb();
/*  68 */     if (oRB == null)
/*  69 */       return false; 
/*  70 */     return oRB.serviceContextDebugFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDebugging(InputStream paramInputStream) {
/*  75 */     ORB oRB = (ORB)paramInputStream.orb();
/*  76 */     if (oRB == null)
/*  77 */       return false; 
/*  78 */     return oRB.serviceContextDebugFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/*  83 */     ORBUtility.dprint(this, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeNullServiceContext(OutputStream paramOutputStream) {
/*  88 */     if (isDebugging(paramOutputStream))
/*  89 */       ORBUtility.dprint("ServiceContexts", "Writing null service context"); 
/*  90 */     paramOutputStream.write_long(0);
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
/*     */   private void createMapFromInputStream(InputStream paramInputStream) {
/* 105 */     this.orb = (ORB)paramInputStream.orb();
/* 106 */     if (this.orb.serviceContextDebugFlag) {
/* 107 */       dprint("Constructing ServiceContexts from input stream");
/*     */     }
/* 109 */     int i = paramInputStream.read_long();
/*     */     
/* 111 */     if (this.orb.serviceContextDebugFlag) {
/* 112 */       dprint("Number of service contexts = " + i);
/*     */     }
/* 114 */     for (byte b = 0; b < i; b++) {
/* 115 */       int j = paramInputStream.read_long();
/*     */       
/* 117 */       if (this.orb.serviceContextDebugFlag) {
/* 118 */         dprint("Reading service context id " + j);
/*     */       }
/* 120 */       byte[] arrayOfByte = OctetSeqHelper.read((InputStream)paramInputStream);
/*     */       
/* 122 */       if (this.orb.serviceContextDebugFlag) {
/* 123 */         dprint("Service context" + j + " length: " + arrayOfByte.length);
/*     */       }
/* 125 */       this.scMap.put(new Integer(j), arrayOfByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceContexts(ORB paramORB) {
/* 131 */     this.orb = paramORB;
/* 132 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */ 
/*     */     
/* 135 */     this.addAlignmentOnWrite = false;
/*     */     
/* 137 */     this.scMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     this.giopVersion = paramORB.getORBData().getGIOPVersion();
/* 143 */     this.codeBase = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceContexts(InputStream paramInputStream) {
/* 151 */     this((ORB)paramInputStream.orb());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     this.codeBase = ((CDRInputStream)paramInputStream).getCodeBase();
/*     */     
/* 159 */     createMapFromInputStream(paramInputStream);
/*     */ 
/*     */     
/* 162 */     this.giopVersion = ((CDRInputStream)paramInputStream).getGIOPVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceContext unmarshal(Integer paramInteger, byte[] paramArrayOfbyte) {
/* 171 */     ServiceContextRegistry serviceContextRegistry = this.orb.getServiceContextRegistry();
/*     */     
/* 173 */     ServiceContextData serviceContextData = serviceContextRegistry.findServiceContextData(paramInteger.intValue());
/* 174 */     ServiceContext serviceContext = null;
/*     */     
/* 176 */     if (serviceContextData == null) {
/* 177 */       if (this.orb.serviceContextDebugFlag) {
/* 178 */         dprint("Could not find ServiceContextData for " + paramInteger + " using UnknownServiceContext");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 183 */       serviceContext = new UnknownServiceContext(paramInteger.intValue(), paramArrayOfbyte);
/*     */     }
/*     */     else {
/*     */       
/* 187 */       if (this.orb.serviceContextDebugFlag) {
/* 188 */         dprint("Found " + serviceContextData);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream((ORB)this.orb, paramArrayOfbyte, paramArrayOfbyte.length, this.giopVersion, this.codeBase);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       encapsInputStream.consumeEndian();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       serviceContext = serviceContextData.makeServiceContext((InputStream)encapsInputStream, this.giopVersion);
/* 217 */       if (serviceContext == null) {
/* 218 */         throw this.wrapper.svcctxUnmarshalError(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */     } 
/*     */     
/* 222 */     return serviceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAlignmentPadding() {
/* 232 */     this.addAlignmentOnWrite = true;
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
/*     */   public void write(OutputStream paramOutputStream, GIOPVersion paramGIOPVersion) {
/* 249 */     if (isDebugging(paramOutputStream)) {
/* 250 */       dprint("Writing service contexts to output stream");
/* 251 */       Utility.printStackTrace();
/*     */     } 
/*     */     
/* 254 */     int i = this.scMap.size();
/*     */     
/* 256 */     if (this.addAlignmentOnWrite) {
/* 257 */       if (isDebugging(paramOutputStream)) {
/* 258 */         dprint("Adding alignment padding");
/*     */       }
/* 260 */       i++;
/*     */     } 
/*     */     
/* 263 */     if (isDebugging(paramOutputStream)) {
/* 264 */       dprint("Service context has " + i + " components");
/*     */     }
/* 266 */     paramOutputStream.write_long(i);
/*     */     
/* 268 */     writeServiceContextsInOrder(paramOutputStream, paramGIOPVersion);
/*     */     
/* 270 */     if (this.addAlignmentOnWrite) {
/* 271 */       if (isDebugging(paramOutputStream)) {
/* 272 */         dprint("Writing alignment padding");
/*     */       }
/* 274 */       paramOutputStream.write_long(-1106033203);
/* 275 */       paramOutputStream.write_long(4);
/* 276 */       paramOutputStream.write_octet((byte)0);
/* 277 */       paramOutputStream.write_octet((byte)0);
/* 278 */       paramOutputStream.write_octet((byte)0);
/* 279 */       paramOutputStream.write_octet((byte)0);
/*     */     } 
/*     */     
/* 282 */     if (isDebugging(paramOutputStream)) {
/* 283 */       dprint("Service context writing complete");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeServiceContextsInOrder(OutputStream paramOutputStream, GIOPVersion paramGIOPVersion) {
/* 294 */     Integer integer = new Integer(9);
/*     */ 
/*     */     
/* 297 */     Object object = this.scMap.remove(integer);
/*     */     
/* 299 */     Iterator<Integer> iterator = this.scMap.keySet().iterator();
/*     */     
/* 301 */     while (iterator.hasNext()) {
/* 302 */       Integer integer1 = iterator.next();
/*     */       
/* 304 */       writeMapEntry(paramOutputStream, integer1, this.scMap.get(integer1), paramGIOPVersion);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     if (object != null) {
/* 311 */       writeMapEntry(paramOutputStream, integer, object, paramGIOPVersion);
/*     */       
/* 313 */       this.scMap.put(integer, object);
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
/*     */   private void writeMapEntry(OutputStream paramOutputStream, Integer paramInteger, Object paramObject, GIOPVersion paramGIOPVersion) {
/* 328 */     if (paramObject instanceof byte[]) {
/* 329 */       if (isDebugging(paramOutputStream)) {
/* 330 */         dprint("Writing service context bytes for id " + paramInteger);
/*     */       }
/* 332 */       OctetSeqHelper.write((OutputStream)paramOutputStream, (byte[])paramObject);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 338 */       ServiceContext serviceContext = (ServiceContext)paramObject;
/*     */       
/* 340 */       if (isDebugging(paramOutputStream)) {
/* 341 */         dprint("Writing service context " + serviceContext);
/*     */       }
/* 343 */       serviceContext.write(paramOutputStream, paramGIOPVersion);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(ServiceContext paramServiceContext) {
/* 352 */     Integer integer = new Integer(paramServiceContext.getId());
/* 353 */     this.scMap.put(integer, paramServiceContext);
/*     */   }
/*     */   
/*     */   public void delete(int paramInt) {
/* 357 */     delete(new Integer(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(Integer paramInteger) {
/* 362 */     this.scMap.remove(paramInteger);
/*     */   }
/*     */   
/*     */   public ServiceContext get(int paramInt) {
/* 366 */     return get(new Integer(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceContext get(Integer paramInteger) {
/* 371 */     Object object = this.scMap.get(paramInteger);
/* 372 */     if (object == null) {
/* 373 */       return null;
/*     */     }
/*     */     
/* 376 */     if (object instanceof byte[]) {
/*     */       
/* 378 */       ServiceContext serviceContext = unmarshal(paramInteger, (byte[])object);
/*     */       
/* 380 */       this.scMap.put(paramInteger, serviceContext);
/*     */       
/* 382 */       return serviceContext;
/*     */     } 
/* 384 */     return (ServiceContext)object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/ServiceContexts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */