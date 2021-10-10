/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.RequestImpl;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.util.JDKBridge;
/*     */ import com.sun.corba.se.pept.broker.Broker;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.protocol.ClientInvocationInfo;
/*     */ import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoListIterator;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.ContextList;
/*     */ import org.omg.CORBA.ExceptionList;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Request;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ import org.omg.CORBA.portable.ServantObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorbaClientDelegateImpl
/*     */   extends CorbaClientDelegate
/*     */ {
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private CorbaContactInfoList contactInfoList;
/*     */   
/*     */   public CorbaClientDelegateImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList) {
/*  89 */     this.orb = paramORB;
/*  90 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/*  92 */     this.contactInfoList = paramCorbaContactInfoList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Broker getBroker() {
/* 101 */     return (Broker)this.orb;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContactInfoList getContactInfoList() {
/* 106 */     return (ContactInfoList)this.contactInfoList;
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
/*     */   public OutputStream request(Object paramObject, String paramString, boolean paramBoolean) {
/* 118 */     ClientInvocationInfo clientInvocationInfo = this.orb.createOrIncrementInvocationInfo();
/*     */     
/* 120 */     Iterator<CorbaContactInfo> iterator = clientInvocationInfo.getContactInfoListIterator();
/* 121 */     if (iterator == null) {
/* 122 */       iterator = this.contactInfoList.iterator();
/* 123 */       clientInvocationInfo.setContactInfoListIterator(iterator);
/*     */     } 
/* 125 */     if (!iterator.hasNext()) {
/* 126 */       throw ((CorbaContactInfoListIterator)iterator)
/* 127 */         .getFailureException();
/*     */     }
/* 129 */     CorbaContactInfo corbaContactInfo = iterator.next();
/* 130 */     ClientRequestDispatcher clientRequestDispatcher = corbaContactInfo.getClientRequestDispatcher();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     clientInvocationInfo.setClientRequestDispatcher(clientRequestDispatcher);
/* 136 */     return (OutputStream)clientRequestDispatcher
/* 137 */       .beginRequest(paramObject, paramString, !paramBoolean, (ContactInfo)corbaContactInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream invoke(Object paramObject, OutputStream paramOutputStream) throws ApplicationException, RemarshalException {
/* 146 */     ClientRequestDispatcher clientRequestDispatcher = getClientRequestDispatcher();
/* 147 */     return (InputStream)clientRequestDispatcher
/* 148 */       .marshalingComplete(paramObject, (OutputObject)paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseReply(Object paramObject, InputStream paramInputStream) {
/* 154 */     ClientRequestDispatcher clientRequestDispatcher = getClientRequestDispatcher();
/* 155 */     clientRequestDispatcher.endRequest((Broker)this.orb, paramObject, (InputObject)paramInputStream);
/* 156 */     this.orb.releaseOrDecrementInvocationInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientRequestDispatcher getClientRequestDispatcher() {
/* 161 */     return ((CorbaInvocationInfo)this.orb
/* 162 */       .getInvocationInfo())
/* 163 */       .getClientRequestDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get_interface_def(Object paramObject) {
/* 168 */     InputStream inputStream = null;
/*     */     
/* 170 */     Object object = null;
/*     */     
/*     */     try {
/* 173 */       OutputStream outputStream = request(null, "_interface", true);
/* 174 */       inputStream = invoke((Object)null, outputStream);
/*     */ 
/*     */       
/* 177 */       Object object1 = inputStream.read_Object();
/*     */ 
/*     */       
/* 180 */       if (!object1._is_a("IDL:omg.org/CORBA/InterfaceDef:1.0")) {
/* 181 */         throw this.wrapper.wrongInterfaceDef(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 186 */         object = JDKBridge.loadClass("org.omg.CORBA._InterfaceDefStub").newInstance();
/* 187 */       } catch (Exception exception) {
/* 188 */         throw this.wrapper.noInterfaceDefStub(exception);
/*     */       } 
/*     */ 
/*     */       
/* 192 */       Delegate delegate = StubAdapter.getDelegate(object1);
/* 193 */       StubAdapter.setDelegate(object, delegate);
/* 194 */     } catch (ApplicationException applicationException) {
/*     */       
/* 196 */       throw this.wrapper.applicationExceptionInSpecialMethod(applicationException);
/* 197 */     } catch (RemarshalException remarshalException) {
/* 198 */       return get_interface_def(paramObject);
/*     */     } finally {
/* 200 */       releaseReply((Object)null, inputStream);
/*     */     } 
/*     */     
/* 203 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is_a(Object paramObject, String paramString) {
/* 213 */     String[] arrayOfString = StubAdapter.getTypeIds(paramObject);
/* 214 */     String str = this.contactInfoList.getTargetIOR().getTypeId();
/* 215 */     if (paramString.equals(str)) {
/* 216 */       return true;
/*     */     }
/* 218 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 219 */       if (paramString.equals(arrayOfString[b])) {
/* 220 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     InputStream inputStream = null;
/*     */     try {
/* 229 */       OutputStream outputStream = request(null, "_is_a", true);
/* 230 */       outputStream.write_string(paramString);
/* 231 */       inputStream = invoke((Object)null, outputStream);
/*     */       
/* 233 */       return inputStream.read_boolean();
/*     */     }
/* 235 */     catch (ApplicationException applicationException) {
/*     */       
/* 237 */       throw this.wrapper.applicationExceptionInSpecialMethod(applicationException);
/* 238 */     } catch (RemarshalException remarshalException) {
/* 239 */       return is_a(paramObject, paramString);
/*     */     } finally {
/* 241 */       releaseReply((Object)null, inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean non_existent(Object paramObject) {
/* 247 */     InputStream inputStream = null;
/*     */     try {
/* 249 */       OutputStream outputStream = request(null, "_non_existent", true);
/* 250 */       inputStream = invoke((Object)null, outputStream);
/*     */       
/* 252 */       return inputStream.read_boolean();
/*     */     }
/* 254 */     catch (ApplicationException applicationException) {
/*     */       
/* 256 */       throw this.wrapper.applicationExceptionInSpecialMethod(applicationException);
/* 257 */     } catch (RemarshalException remarshalException) {
/* 258 */       return non_existent(paramObject);
/*     */     } finally {
/* 260 */       releaseReply((Object)null, inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object duplicate(Object paramObject) {
/* 266 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release(Object paramObject) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is_equivalent(Object paramObject1, Object paramObject2) {
/* 280 */     if (paramObject2 == null) {
/* 281 */       return false;
/*     */     }
/*     */     
/* 284 */     if (!StubAdapter.isStub(paramObject2)) {
/* 285 */       return false;
/*     */     }
/* 287 */     Delegate delegate = StubAdapter.getDelegate(paramObject2);
/* 288 */     if (delegate == null) {
/* 289 */       return false;
/*     */     }
/*     */     
/* 292 */     if (delegate == this) {
/* 293 */       return true;
/*     */     }
/*     */     
/* 296 */     if (!(delegate instanceof CorbaClientDelegateImpl)) {
/* 297 */       return false;
/*     */     }
/* 299 */     CorbaClientDelegateImpl corbaClientDelegateImpl = (CorbaClientDelegateImpl)delegate;
/*     */     
/* 301 */     CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)corbaClientDelegateImpl.getContactInfoList();
/* 302 */     return this.contactInfoList.getTargetIOR().isEquivalent(corbaContactInfoList
/* 303 */         .getTargetIOR());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject, Object paramObject1) {
/* 312 */     if (paramObject1 == null) {
/* 313 */       return false;
/*     */     }
/* 315 */     if (!StubAdapter.isStub(paramObject1)) {
/* 316 */       return false;
/*     */     }
/*     */     
/* 319 */     Delegate delegate = StubAdapter.getDelegate(paramObject1);
/* 320 */     if (delegate == null) {
/* 321 */       return false;
/*     */     }
/* 323 */     if (delegate instanceof CorbaClientDelegateImpl) {
/* 324 */       CorbaClientDelegateImpl corbaClientDelegateImpl = (CorbaClientDelegateImpl)delegate;
/*     */       
/* 326 */       IOR iOR = corbaClientDelegateImpl.contactInfoList.getTargetIOR();
/* 327 */       return this.contactInfoList.getTargetIOR().equals(iOR);
/*     */     } 
/*     */ 
/*     */     
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode(Object paramObject) {
/* 336 */     return hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hash(Object paramObject, int paramInt) {
/* 341 */     int i = hashCode();
/* 342 */     if (i > paramInt)
/* 343 */       return 0; 
/* 344 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public Request request(Object paramObject, String paramString) {
/* 349 */     return (Request)new RequestImpl(this.orb, paramObject, null, paramString, null, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request create_request(Object paramObject, Context paramContext, String paramString, NVList paramNVList, NamedValue paramNamedValue) {
/* 359 */     return (Request)new RequestImpl(this.orb, paramObject, paramContext, paramString, paramNVList, paramNamedValue, null, null);
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
/*     */   public Request create_request(Object paramObject, Context paramContext, String paramString, NVList paramNVList, NamedValue paramNamedValue, ExceptionList paramExceptionList, ContextList paramContextList) {
/* 371 */     return (Request)new RequestImpl(this.orb, paramObject, paramContext, paramString, paramNVList, paramNamedValue, paramExceptionList, paramContextList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ORB orb(Object paramObject) {
/* 377 */     return (ORB)this.orb;
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
/*     */   public boolean is_local(Object paramObject) {
/* 393 */     return this.contactInfoList.getEffectiveTargetIOR().getProfile()
/* 394 */       .isLocal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServantObject servant_preinvoke(Object paramObject, String paramString, Class paramClass) {
/* 401 */     return this.contactInfoList
/* 402 */       .getLocalClientRequestDispatcher()
/* 403 */       .servant_preinvoke(paramObject, paramString, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void servant_postinvoke(Object paramObject, ServantObject paramServantObject) {
/* 409 */     this.contactInfoList.getLocalClientRequestDispatcher()
/* 410 */       .servant_postinvoke(paramObject, paramServantObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get_codebase(Object paramObject) {
/* 421 */     if (this.contactInfoList.getTargetIOR() != null) {
/* 422 */       return this.contactInfoList.getTargetIOR().getProfile().getCodebase();
/*     */     }
/* 424 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(Object paramObject) {
/* 429 */     return this.contactInfoList.getTargetIOR().stringify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 439 */     return this.contactInfoList.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/CorbaClientDelegateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */