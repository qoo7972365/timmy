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
/*     */ import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;
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
/*     */ public class _NamingContextExtStub
/*     */   extends ObjectImpl
/*     */   implements NamingContextExt
/*     */ {
/*     */   public String to_string(NameComponent[] paramArrayOfNameComponent) throws InvalidName {
/*  41 */     InputStream inputStream = null;
/*     */     try {
/*  43 */       OutputStream outputStream = _request("to_string", true);
/*  44 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/*  45 */       inputStream = _invoke(outputStream);
/*  46 */       String str = StringNameHelper.read(inputStream);
/*  47 */       return str;
/*  48 */     } catch (ApplicationException applicationException) {
/*  49 */       inputStream = applicationException.getInputStream();
/*  50 */       String str = applicationException.getId();
/*  51 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/*  52 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/*  54 */       throw new MARSHAL(str);
/*  55 */     } catch (RemarshalException remarshalException) {
/*  56 */       return to_string(paramArrayOfNameComponent);
/*     */     } finally {
/*  58 */       _releaseReply(inputStream);
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
/*     */   public NameComponent[] to_name(String paramString) throws InvalidName {
/*  75 */     InputStream inputStream = null;
/*     */     try {
/*  77 */       OutputStream outputStream = _request("to_name", true);
/*  78 */       StringNameHelper.write(outputStream, paramString);
/*  79 */       inputStream = _invoke(outputStream);
/*  80 */       NameComponent[] arrayOfNameComponent = NameHelper.read(inputStream);
/*  81 */       return arrayOfNameComponent;
/*  82 */     } catch (ApplicationException applicationException) {
/*  83 */       inputStream = applicationException.getInputStream();
/*  84 */       String str = applicationException.getId();
/*  85 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/*  86 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/*  88 */       throw new MARSHAL(str);
/*  89 */     } catch (RemarshalException remarshalException) {
/*  90 */       return to_name(paramString);
/*     */     } finally {
/*  92 */       _releaseReply(inputStream);
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
/*     */   public String to_url(String paramString1, String paramString2) throws InvalidAddress, InvalidName {
/* 113 */     InputStream inputStream = null;
/*     */     try {
/* 115 */       OutputStream outputStream = _request("to_url", true);
/* 116 */       AddressHelper.write(outputStream, paramString1);
/* 117 */       StringNameHelper.write(outputStream, paramString2);
/* 118 */       inputStream = _invoke(outputStream);
/* 119 */       String str = URLStringHelper.read(inputStream);
/* 120 */       return str;
/* 121 */     } catch (ApplicationException applicationException) {
/* 122 */       inputStream = applicationException.getInputStream();
/* 123 */       String str = applicationException.getId();
/* 124 */       if (str.equals("IDL:omg.org/CosNaming/NamingContextExt/InvalidAddress:1.0"))
/* 125 */         throw InvalidAddressHelper.read(inputStream); 
/* 126 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 127 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 129 */       throw new MARSHAL(str);
/* 130 */     } catch (RemarshalException remarshalException) {
/* 131 */       return to_url(paramString1, paramString2);
/*     */     } finally {
/* 133 */       _releaseReply(inputStream);
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
/*     */   public Object resolve_str(String paramString) throws NotFound, CannotProceed, InvalidName {
/* 154 */     InputStream inputStream = null;
/*     */     try {
/* 156 */       OutputStream outputStream = _request("resolve_str", true);
/* 157 */       StringNameHelper.write(outputStream, paramString);
/* 158 */       inputStream = _invoke(outputStream);
/* 159 */       Object object = ObjectHelper.read(inputStream);
/* 160 */       return object;
/* 161 */     } catch (ApplicationException applicationException) {
/* 162 */       inputStream = applicationException.getInputStream();
/* 163 */       String str = applicationException.getId();
/* 164 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 165 */         throw NotFoundHelper.read(inputStream); 
/* 166 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 167 */         throw CannotProceedHelper.read(inputStream); 
/* 168 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 169 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 171 */       throw new MARSHAL(str);
/* 172 */     } catch (RemarshalException remarshalException) {
/* 173 */       return resolve_str(paramString);
/*     */     } finally {
/* 175 */       _releaseReply(inputStream);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/* 205 */     InputStream inputStream = null;
/*     */     try {
/* 207 */       OutputStream outputStream = _request("bind", true);
/* 208 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 209 */       ObjectHelper.write(outputStream, paramObject);
/* 210 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 212 */     } catch (ApplicationException applicationException) {
/* 213 */       inputStream = applicationException.getInputStream();
/* 214 */       String str = applicationException.getId();
/* 215 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 216 */         throw NotFoundHelper.read(inputStream); 
/* 217 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 218 */         throw CannotProceedHelper.read(inputStream); 
/* 219 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0"))
/* 220 */         throw InvalidNameHelper.read(inputStream); 
/* 221 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
/* 222 */         throw AlreadyBoundHelper.read(inputStream);
/*     */       }
/* 224 */       throw new MARSHAL(str);
/* 225 */     } catch (RemarshalException remarshalException) {
/* 226 */       bind(paramArrayOfNameComponent, paramObject);
/*     */     } finally {
/* 228 */       _releaseReply(inputStream);
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
/* 255 */     InputStream inputStream = null;
/*     */     try {
/* 257 */       OutputStream outputStream = _request("bind_context", true);
/* 258 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 259 */       NamingContextHelper.write(outputStream, paramNamingContext);
/* 260 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 262 */     } catch (ApplicationException applicationException) {
/* 263 */       inputStream = applicationException.getInputStream();
/* 264 */       String str = applicationException.getId();
/* 265 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 266 */         throw NotFoundHelper.read(inputStream); 
/* 267 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 268 */         throw CannotProceedHelper.read(inputStream); 
/* 269 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0"))
/* 270 */         throw InvalidNameHelper.read(inputStream); 
/* 271 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0")) {
/* 272 */         throw AlreadyBoundHelper.read(inputStream);
/*     */       }
/* 274 */       throw new MARSHAL(str);
/* 275 */     } catch (RemarshalException remarshalException) {
/* 276 */       bind_context(paramArrayOfNameComponent, paramNamingContext);
/*     */     } finally {
/* 278 */       _releaseReply(inputStream);
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
/* 303 */     InputStream inputStream = null;
/*     */     try {
/* 305 */       OutputStream outputStream = _request("rebind", true);
/* 306 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 307 */       ObjectHelper.write(outputStream, paramObject);
/* 308 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 310 */     } catch (ApplicationException applicationException) {
/* 311 */       inputStream = applicationException.getInputStream();
/* 312 */       String str = applicationException.getId();
/* 313 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 314 */         throw NotFoundHelper.read(inputStream); 
/* 315 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 316 */         throw CannotProceedHelper.read(inputStream); 
/* 317 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 318 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 320 */       throw new MARSHAL(str);
/* 321 */     } catch (RemarshalException remarshalException) {
/* 322 */       rebind(paramArrayOfNameComponent, paramObject);
/*     */     } finally {
/* 324 */       _releaseReply(inputStream);
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
/* 349 */     InputStream inputStream = null;
/*     */     try {
/* 351 */       OutputStream outputStream = _request("rebind_context", true);
/* 352 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 353 */       NamingContextHelper.write(outputStream, paramNamingContext);
/* 354 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 356 */     } catch (ApplicationException applicationException) {
/* 357 */       inputStream = applicationException.getInputStream();
/* 358 */       String str = applicationException.getId();
/* 359 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 360 */         throw NotFoundHelper.read(inputStream); 
/* 361 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 362 */         throw CannotProceedHelper.read(inputStream); 
/* 363 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 364 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 366 */       throw new MARSHAL(str);
/* 367 */     } catch (RemarshalException remarshalException) {
/* 368 */       rebind_context(paramArrayOfNameComponent, paramNamingContext);
/*     */     } finally {
/* 370 */       _releaseReply(inputStream);
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
/* 395 */     InputStream inputStream = null;
/*     */     try {
/* 397 */       OutputStream outputStream = _request("resolve", true);
/* 398 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 399 */       inputStream = _invoke(outputStream);
/* 400 */       Object object = ObjectHelper.read(inputStream);
/* 401 */       return object;
/* 402 */     } catch (ApplicationException applicationException) {
/* 403 */       inputStream = applicationException.getInputStream();
/* 404 */       String str = applicationException.getId();
/* 405 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 406 */         throw NotFoundHelper.read(inputStream); 
/* 407 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 408 */         throw CannotProceedHelper.read(inputStream); 
/* 409 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 410 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 412 */       throw new MARSHAL(str);
/* 413 */     } catch (RemarshalException remarshalException) {
/* 414 */       return resolve(paramArrayOfNameComponent);
/*     */     } finally {
/* 416 */       _releaseReply(inputStream);
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
/* 436 */     InputStream inputStream = null;
/*     */     try {
/* 438 */       OutputStream outputStream = _request("unbind", true);
/* 439 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 440 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 442 */     } catch (ApplicationException applicationException) {
/* 443 */       inputStream = applicationException.getInputStream();
/* 444 */       String str = applicationException.getId();
/* 445 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 446 */         throw NotFoundHelper.read(inputStream); 
/* 447 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 448 */         throw CannotProceedHelper.read(inputStream); 
/* 449 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 450 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 452 */       throw new MARSHAL(str);
/* 453 */     } catch (RemarshalException remarshalException) {
/* 454 */       unbind(paramArrayOfNameComponent);
/*     */     } finally {
/* 456 */       _releaseReply(inputStream);
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
/* 483 */     InputStream inputStream = null;
/*     */     try {
/* 485 */       OutputStream outputStream = _request("list", true);
/* 486 */       outputStream.write_ulong(paramInt);
/* 487 */       inputStream = _invoke(outputStream);
/* 488 */       paramBindingListHolder.value = BindingListHelper.read(inputStream);
/* 489 */       paramBindingIteratorHolder.value = BindingIteratorHelper.read(inputStream);
/*     */       return;
/* 491 */     } catch (ApplicationException applicationException) {
/* 492 */       inputStream = applicationException.getInputStream();
/* 493 */       String str = applicationException.getId();
/* 494 */       throw new MARSHAL(str);
/* 495 */     } catch (RemarshalException remarshalException) {
/* 496 */       list(paramInt, paramBindingListHolder, paramBindingIteratorHolder);
/*     */     } finally {
/* 498 */       _releaseReply(inputStream);
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
/* 510 */     InputStream inputStream = null;
/*     */     try {
/* 512 */       OutputStream outputStream = _request("new_context", true);
/* 513 */       inputStream = _invoke(outputStream);
/* 514 */       NamingContext namingContext = NamingContextHelper.read(inputStream);
/* 515 */       return namingContext;
/* 516 */     } catch (ApplicationException applicationException) {
/* 517 */       inputStream = applicationException.getInputStream();
/* 518 */       String str = applicationException.getId();
/* 519 */       throw new MARSHAL(str);
/* 520 */     } catch (RemarshalException remarshalException) {
/* 521 */       return new_context();
/*     */     } finally {
/* 523 */       _releaseReply(inputStream);
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
/* 550 */     InputStream inputStream = null;
/*     */     try {
/* 552 */       OutputStream outputStream = _request("bind_new_context", true);
/* 553 */       NameHelper.write(outputStream, paramArrayOfNameComponent);
/* 554 */       inputStream = _invoke(outputStream);
/* 555 */       NamingContext namingContext = NamingContextHelper.read(inputStream);
/* 556 */       return namingContext;
/* 557 */     } catch (ApplicationException applicationException) {
/* 558 */       inputStream = applicationException.getInputStream();
/* 559 */       String str = applicationException.getId();
/* 560 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotFound:1.0"))
/* 561 */         throw NotFoundHelper.read(inputStream); 
/* 562 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0"))
/* 563 */         throw AlreadyBoundHelper.read(inputStream); 
/* 564 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0"))
/* 565 */         throw CannotProceedHelper.read(inputStream); 
/* 566 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0")) {
/* 567 */         throw InvalidNameHelper.read(inputStream);
/*     */       }
/* 569 */       throw new MARSHAL(str);
/* 570 */     } catch (RemarshalException remarshalException) {
/* 571 */       return bind_new_context(paramArrayOfNameComponent);
/*     */     } finally {
/* 573 */       _releaseReply(inputStream);
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
/* 586 */     InputStream inputStream = null;
/*     */     try {
/* 588 */       OutputStream outputStream = _request("destroy", true);
/* 589 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 591 */     } catch (ApplicationException applicationException) {
/* 592 */       inputStream = applicationException.getInputStream();
/* 593 */       String str = applicationException.getId();
/* 594 */       if (str.equals("IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0")) {
/* 595 */         throw NotEmptyHelper.read(inputStream);
/*     */       }
/* 597 */       throw new MARSHAL(str);
/* 598 */     } catch (RemarshalException remarshalException) {
/* 599 */       destroy();
/*     */     } finally {
/* 601 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 606 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/NamingContextExt:1.0", "IDL:omg.org/CosNaming/NamingContext:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 612 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 617 */     String str = paramObjectInputStream.readUTF();
/* 618 */     String[] arrayOfString = null;
/* 619 */     Properties properties = null;
/* 620 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 622 */       Object object = oRB.string_to_object(str);
/* 623 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 624 */       _set_delegate(delegate);
/*     */     } finally {
/* 626 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 632 */     String[] arrayOfString = null;
/* 633 */     Properties properties = null;
/* 634 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 636 */       String str = oRB.object_to_string(this);
/* 637 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 639 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/_NamingContextExtStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */