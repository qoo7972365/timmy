/*     */ package org.omg.CosNaming;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.ObjectHelper;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
/*     */ import org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.CannotProceed;
/*     */ import org.omg.CosNaming.NamingContextPackage.CannotProceedHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.InvalidName;
/*     */ import org.omg.CosNaming.NamingContextPackage.InvalidNameHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotEmpty;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFound;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _NamingContextStub
/*     */   extends ObjectImpl
/*     */   implements NamingContext
/*     */ {
/*     */   public void bind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  50 */     InputStream inputStream = null;
/*     */     try {
/*  52 */       OutputStream outputStream = _request("bind", true);
/*  53 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/*  54 */       ObjectHelper.write(outputStream, paramObject);
/*  55 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  57 */     } catch (ApplicationException applicationException) {
/*  58 */       inputStream = applicationException.getInputStream();
/*  59 */       String str = applicationException.getId();
/*  60 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/*  61 */         throw NotFoundHelper.read(inputStream); 
/*  62 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/*  63 */         throw CannotProceedHelper.read(inputStream); 
/*  64 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0"))
/*  65 */         throw InvalidNameHelper.read(inputStream); 
/*  66 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
/*  67 */         throw AlreadyBoundHelper.read(inputStream);
/*     */       }
/*  69 */       throw new MARSHAL(str);
/*  70 */     } catch (RemarshalException remarshalException) {
/*  71 */       bind(paramArrayOfNameComponent, paramObject);
/*     */     } finally {
/*  73 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/* 100 */     InputStream inputStream = null;
/*     */     try {
/* 102 */       OutputStream outputStream = _request("bind_context", true);
/* 103 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 104 */       NamingContextHelper.write(outputStream, paramNamingContext);
/* 105 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 107 */     } catch (ApplicationException applicationException) {
/* 108 */       inputStream = applicationException.getInputStream();
/* 109 */       String str = applicationException.getId();
/* 110 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 111 */         throw NotFoundHelper.read(inputStream); 
/* 112 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 113 */         throw CannotProceedHelper.read(inputStream); 
/* 114 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0"))
/* 115 */         throw InvalidNameHelper.read(inputStream); 
/* 116 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
/* 117 */         throw AlreadyBoundHelper.read(inputStream);
/*     */       }
/* 119 */       throw new MARSHAL(str);
/* 120 */     } catch (RemarshalException remarshalException) {
/* 121 */       bind_context(paramArrayOfNameComponent, paramNamingContext);
/*     */     } finally {
/* 123 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName {
/* 148 */     InputStream inputStream = null;
/*     */     try {
/* 150 */       OutputStream outputStream = _request("rebind", true);
/* 151 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 152 */       ObjectHelper.write(outputStream, paramObject);
/* 153 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 155 */     } catch (ApplicationException applicationException) {
/* 156 */       inputStream = applicationException.getInputStream();
/* 157 */       String str = applicationException.getId();
/* 158 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 159 */         throw NotFoundHelper.read(inputStream); 
/* 160 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 161 */         throw CannotProceedHelper.read(inputStream); 
/* 162 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 163 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 165 */       throw new MARSHAL(str);
/* 166 */     } catch (RemarshalException remarshalException) {
/* 167 */       rebind(paramArrayOfNameComponent, paramObject);
/*     */     } finally {
/* 169 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName {
/* 194 */     InputStream inputStream = null;
/*     */     try {
/* 196 */       OutputStream outputStream = _request("rebind_context", true);
/* 197 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 198 */       NamingContextHelper.write(outputStream, paramNamingContext);
/* 199 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 201 */     } catch (ApplicationException applicationException) {
/* 202 */       inputStream = applicationException.getInputStream();
/* 203 */       String str = applicationException.getId();
/* 204 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 205 */         throw NotFoundHelper.read(inputStream); 
/* 206 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 207 */         throw CannotProceedHelper.read(inputStream); 
/* 208 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 209 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 211 */       throw new MARSHAL(str);
/* 212 */     } catch (RemarshalException remarshalException) {
/* 213 */       rebind_context(paramArrayOfNameComponent, paramNamingContext);
/*     */     } finally {
/* 215 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object resolve(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/* 240 */     InputStream inputStream = null;
/*     */     try {
/* 242 */       OutputStream outputStream = _request("resolve", true);
/* 243 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 244 */       inputStream = _invoke(outputStream);
/* 245 */       Object object = ObjectHelper.read(inputStream);
/* 246 */       return object;
/* 247 */     } catch (ApplicationException applicationException) {
/* 248 */       inputStream = applicationException.getInputStream();
/* 249 */       String str = applicationException.getId();
/* 250 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 251 */         throw NotFoundHelper.read(inputStream); 
/* 252 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 253 */         throw CannotProceedHelper.read(inputStream); 
/* 254 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 255 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 257 */       throw new MARSHAL(str);
/* 258 */     } catch (RemarshalException remarshalException) {
/* 259 */       return resolve(paramArrayOfNameComponent);
/*     */     } finally {
/* 261 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbind(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/* 281 */     InputStream inputStream = null;
/*     */     try {
/* 283 */       OutputStream outputStream = _request("unbind", true);
/* 284 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 285 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 287 */     } catch (ApplicationException applicationException) {
/* 288 */       inputStream = applicationException.getInputStream();
/* 289 */       String str = applicationException.getId();
/* 290 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 291 */         throw NotFoundHelper.read(inputStream); 
/* 292 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 293 */         throw CannotProceedHelper.read(inputStream); 
/* 294 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 295 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 297 */       throw new MARSHAL(str);
/* 298 */     } catch (RemarshalException remarshalException) {
/* 299 */       unbind(paramArrayOfNameComponent);
/*     */     } finally {
/* 301 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void list(int paramInt, BindingListHolder paramBindingListHolder, BindingIteratorHolder paramBindingIteratorHolder) {
/* 328 */     InputStream inputStream = null;
/*     */     try {
/* 330 */       OutputStream outputStream = _request("list", true);
/* 331 */       outputStream.write_ulong(paramInt);
/* 332 */       inputStream = _invoke(outputStream);
/* 333 */       paramBindingListHolder.value = BindingListHelper.read(inputStream);
/* 334 */       paramBindingIteratorHolder.value = BindingIteratorHelper.read(inputStream);
/*     */       return;
/* 336 */     } catch (ApplicationException applicationException) {
/* 337 */       inputStream = applicationException.getInputStream();
/* 338 */       String str = applicationException.getId();
/* 339 */       throw new MARSHAL(str);
/* 340 */     } catch (RemarshalException remarshalException) {
/* 341 */       list(paramInt, paramBindingListHolder, paramBindingIteratorHolder);
/*     */     } finally {
/* 343 */       _releaseReply(inputStream);
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
/*     */   public NamingContext new_context() {
/* 355 */     InputStream inputStream = null;
/*     */     try {
/* 357 */       OutputStream outputStream = _request("new_context", true);
/* 358 */       inputStream = _invoke(outputStream);
/* 359 */       NamingContext namingContext = NamingContextHelper.read(inputStream);
/* 360 */       return namingContext;
/* 361 */     } catch (ApplicationException applicationException) {
/* 362 */       inputStream = applicationException.getInputStream();
/* 363 */       String str = applicationException.getId();
/* 364 */       throw new MARSHAL(str);
/* 365 */     } catch (RemarshalException remarshalException) {
/* 366 */       return new_context();
/*     */     } finally {
/* 368 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingContext bind_new_context(NameComponent[] paramArrayOfNameComponent) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
/* 395 */     InputStream inputStream = null;
/*     */     try {
/* 397 */       OutputStream outputStream = _request("bind_new_context", true);
/* 398 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 399 */       inputStream = _invoke(outputStream);
/* 400 */       NamingContext namingContext = NamingContextHelper.read(inputStream);
/* 401 */       return namingContext;
/* 402 */     } catch (ApplicationException applicationException) {
/* 403 */       inputStream = applicationException.getInputStream();
/* 404 */       String str = applicationException.getId();
/* 405 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 406 */         throw NotFoundHelper.read(inputStream); 
/* 407 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0"))
/* 408 */         throw AlreadyBoundHelper.read(inputStream); 
/* 409 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 410 */         throw CannotProceedHelper.read(inputStream); 
/* 411 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 412 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 414 */       throw new MARSHAL(str);
/* 415 */     } catch (RemarshalException remarshalException) {
/* 416 */       return bind_new_context(paramArrayOfNameComponent);
/*     */     } finally {
/* 418 */       _releaseReply(inputStream);
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
/*     */   public void destroy() throws NotEmpty {
/* 431 */     InputStream inputStream = null;
/*     */     try {
/* 433 */       OutputStream outputStream = _request("destroy", true);
/* 434 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 436 */     } catch (ApplicationException applicationException) {
/* 437 */       inputStream = applicationException.getInputStream();
/* 438 */       String str = applicationException.getId();
/* 439 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0")) {
/* 440 */         throw NotEmptyHelper.read(inputStream);
/*     */       }
/* 442 */       throw new MARSHAL(str);
/* 443 */     } catch (RemarshalException remarshalException) {
/* 444 */       destroy();
/*     */     } finally {
/* 446 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 451 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/NamingContext:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 456 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 461 */     String str = paramObjectInputStream.readUTF();
/* 462 */     String[] arrayOfString = null;
/* 463 */     Properties properties = null;
/* 464 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 466 */       Object object = oRB.string_to_object(str);
/* 467 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 468 */       _set_delegate(delegate);
/*     */     } finally {
/* 470 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 476 */     String[] arrayOfString = null;
/* 477 */     Properties properties = null;
/* 478 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 480 */       String str = oRB.object_to_string(this);
/* 481 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 483 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/_NamingContextStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */