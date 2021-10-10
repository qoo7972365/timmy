/*     */ package org.omg.CosNaming;
/*     */ 
/*     */ import java.util.Dictionary;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.DynamicImplementation;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.ServerRequest;
/*     */ import org.omg.CORBA.TCKind;
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
/*     */ public abstract class _NamingContextImplBase
/*     */   extends DynamicImplementation
/*     */   implements NamingContext
/*     */ {
/*  40 */   private static final String[] _type_ids = new String[] { "IDL:omg.org/CosNaming/NamingContext:1.0" };
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  44 */     return (String[])_type_ids.clone();
/*     */   }
/*  46 */   private static Dictionary _methods = new Hashtable<>();
/*     */   
/*  48 */   static { _methods.put("bind", new Integer(0));
/*  49 */     _methods.put("bind_context", new Integer(1));
/*  50 */     _methods.put("rebind", new Integer(2));
/*  51 */     _methods.put("rebind_context", new Integer(3));
/*  52 */     _methods.put("resolve", new Integer(4));
/*  53 */     _methods.put("unbind", new Integer(5));
/*  54 */     _methods.put("list", new Integer(6));
/*  55 */     _methods.put("new_context", new Integer(7));
/*  56 */     _methods.put("bind_new_context", new Integer(8));
/*  57 */     _methods.put("destroy", new Integer(9)); } public void invoke(ServerRequest paramServerRequest) { NVList nVList; Any any2; NamingContext namingContext1; Any any1; Any any4; NameComponent[] arrayOfNameComponent2; Any any3; NameComponent[] arrayOfNameComponent1; NameComponent[] arrayOfNameComponent3; Object object1; Any any5; NamingContext namingContext2; Object object2; Any any7; int i; Any any6; Any any8;
/*     */     BindingListHolder bindingListHolder;
/*     */     BindingIteratorHolder bindingIteratorHolder;
/*     */     Any any9;
/*  61 */     switch (((Integer)_methods.get(paramServerRequest.op_name())).intValue()) {
/*     */       
/*     */       case 0:
/*  64 */         nVList = _orb().create_list(0);
/*  65 */         any2 = _orb().create_any();
/*  66 */         any2.type(NameHelper.type());
/*  67 */         nVList.add_value("n", any2, 1);
/*  68 */         any4 = _orb().create_any();
/*  69 */         any4.type(ORB.init().get_primitive_tc(TCKind.tk_objref));
/*  70 */         nVList.add_value("obj", any4, 1);
/*  71 */         paramServerRequest.params(nVList);
/*     */         
/*  73 */         arrayOfNameComponent3 = NameHelper.extract(any2);
/*     */         
/*  75 */         object2 = any4.extract_Object();
/*     */         try {
/*  77 */           bind(arrayOfNameComponent3, object2);
/*     */         }
/*  79 */         catch (NotFound notFound) {
/*  80 */           Any any = _orb().create_any();
/*  81 */           NotFoundHelper.insert(any, notFound);
/*  82 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/*  85 */         } catch (CannotProceed cannotProceed) {
/*  86 */           Any any = _orb().create_any();
/*  87 */           CannotProceedHelper.insert(any, cannotProceed);
/*  88 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/*  91 */         } catch (InvalidName invalidName) {
/*  92 */           Any any = _orb().create_any();
/*  93 */           InvalidNameHelper.insert(any, invalidName);
/*  94 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/*  97 */         } catch (AlreadyBound alreadyBound) {
/*  98 */           Any any = _orb().create_any();
/*  99 */           AlreadyBoundHelper.insert(any, alreadyBound);
/* 100 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 103 */         any8 = _orb().create_any();
/* 104 */         any8.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 105 */         paramServerRequest.result(any8);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 1:
/* 110 */         nVList = _orb().create_list(0);
/* 111 */         any2 = _orb().create_any();
/* 112 */         any2.type(NameHelper.type());
/* 113 */         nVList.add_value("n", any2, 1);
/* 114 */         any4 = _orb().create_any();
/* 115 */         any4.type(NamingContextHelper.type());
/* 116 */         nVList.add_value("nc", any4, 1);
/* 117 */         paramServerRequest.params(nVList);
/*     */         
/* 119 */         arrayOfNameComponent3 = NameHelper.extract(any2);
/*     */         
/* 121 */         object2 = NamingContextHelper.extract(any4);
/*     */         try {
/* 123 */           bind_context(arrayOfNameComponent3, (NamingContext)object2);
/*     */         }
/* 125 */         catch (NotFound notFound) {
/* 126 */           Any any = _orb().create_any();
/* 127 */           NotFoundHelper.insert(any, notFound);
/* 128 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 131 */         } catch (CannotProceed cannotProceed) {
/* 132 */           Any any = _orb().create_any();
/* 133 */           CannotProceedHelper.insert(any, cannotProceed);
/* 134 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 137 */         } catch (InvalidName invalidName) {
/* 138 */           Any any = _orb().create_any();
/* 139 */           InvalidNameHelper.insert(any, invalidName);
/* 140 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 143 */         } catch (AlreadyBound alreadyBound) {
/* 144 */           Any any = _orb().create_any();
/* 145 */           AlreadyBoundHelper.insert(any, alreadyBound);
/* 146 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 149 */         any8 = _orb().create_any();
/* 150 */         any8.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 151 */         paramServerRequest.result(any8);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/* 156 */         nVList = _orb().create_list(0);
/* 157 */         any2 = _orb().create_any();
/* 158 */         any2.type(NameHelper.type());
/* 159 */         nVList.add_value("n", any2, 1);
/* 160 */         any4 = _orb().create_any();
/* 161 */         any4.type(ORB.init().get_primitive_tc(TCKind.tk_objref));
/* 162 */         nVList.add_value("obj", any4, 1);
/* 163 */         paramServerRequest.params(nVList);
/*     */         
/* 165 */         arrayOfNameComponent3 = NameHelper.extract(any2);
/*     */         
/* 167 */         object2 = any4.extract_Object();
/*     */         try {
/* 169 */           rebind(arrayOfNameComponent3, object2);
/*     */         }
/* 171 */         catch (NotFound notFound) {
/* 172 */           Any any = _orb().create_any();
/* 173 */           NotFoundHelper.insert(any, notFound);
/* 174 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 177 */         } catch (CannotProceed cannotProceed) {
/* 178 */           Any any = _orb().create_any();
/* 179 */           CannotProceedHelper.insert(any, cannotProceed);
/* 180 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 183 */         } catch (InvalidName invalidName) {
/* 184 */           Any any = _orb().create_any();
/* 185 */           InvalidNameHelper.insert(any, invalidName);
/* 186 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 189 */         any8 = _orb().create_any();
/* 190 */         any8.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 191 */         paramServerRequest.result(any8);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 3:
/* 196 */         nVList = _orb().create_list(0);
/* 197 */         any2 = _orb().create_any();
/* 198 */         any2.type(NameHelper.type());
/* 199 */         nVList.add_value("n", any2, 1);
/* 200 */         any4 = _orb().create_any();
/* 201 */         any4.type(NamingContextHelper.type());
/* 202 */         nVList.add_value("nc", any4, 1);
/* 203 */         paramServerRequest.params(nVList);
/*     */         
/* 205 */         arrayOfNameComponent3 = NameHelper.extract(any2);
/*     */         
/* 207 */         object2 = NamingContextHelper.extract(any4);
/*     */         try {
/* 209 */           rebind_context(arrayOfNameComponent3, (NamingContext)object2);
/*     */         }
/* 211 */         catch (NotFound notFound) {
/* 212 */           Any any = _orb().create_any();
/* 213 */           NotFoundHelper.insert(any, notFound);
/* 214 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 217 */         } catch (CannotProceed cannotProceed) {
/* 218 */           Any any = _orb().create_any();
/* 219 */           CannotProceedHelper.insert(any, cannotProceed);
/* 220 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 223 */         } catch (InvalidName invalidName) {
/* 224 */           Any any = _orb().create_any();
/* 225 */           InvalidNameHelper.insert(any, invalidName);
/* 226 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 229 */         any8 = _orb().create_any();
/* 230 */         any8.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 231 */         paramServerRequest.result(any8);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/* 236 */         nVList = _orb().create_list(0);
/* 237 */         any2 = _orb().create_any();
/* 238 */         any2.type(NameHelper.type());
/* 239 */         nVList.add_value("n", any2, 1);
/* 240 */         paramServerRequest.params(nVList);
/*     */         
/* 242 */         arrayOfNameComponent2 = NameHelper.extract(any2);
/*     */         
/*     */         try {
/* 245 */           object1 = resolve(arrayOfNameComponent2);
/*     */         }
/* 247 */         catch (NotFound notFound) {
/* 248 */           any8 = _orb().create_any();
/* 249 */           NotFoundHelper.insert(any8, notFound);
/* 250 */           paramServerRequest.except(any8);
/*     */           
/*     */           return;
/* 253 */         } catch (CannotProceed cannotProceed) {
/* 254 */           any8 = _orb().create_any();
/* 255 */           CannotProceedHelper.insert(any8, cannotProceed);
/* 256 */           paramServerRequest.except(any8);
/*     */           
/*     */           return;
/* 259 */         } catch (InvalidName invalidName) {
/* 260 */           any8 = _orb().create_any();
/* 261 */           InvalidNameHelper.insert(any8, invalidName);
/* 262 */           paramServerRequest.except(any8);
/*     */           return;
/*     */         } 
/* 265 */         any7 = _orb().create_any();
/* 266 */         any7.insert_Object(object1);
/* 267 */         paramServerRequest.result(any7);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 5:
/* 272 */         nVList = _orb().create_list(0);
/* 273 */         any2 = _orb().create_any();
/* 274 */         any2.type(NameHelper.type());
/* 275 */         nVList.add_value("n", any2, 1);
/* 276 */         paramServerRequest.params(nVList);
/*     */         
/* 278 */         arrayOfNameComponent2 = NameHelper.extract(any2);
/*     */         try {
/* 280 */           unbind(arrayOfNameComponent2);
/*     */         }
/* 282 */         catch (NotFound notFound) {
/* 283 */           any7 = _orb().create_any();
/* 284 */           NotFoundHelper.insert(any7, notFound);
/* 285 */           paramServerRequest.except(any7);
/*     */           
/*     */           return;
/* 288 */         } catch (CannotProceed cannotProceed) {
/* 289 */           any7 = _orb().create_any();
/* 290 */           CannotProceedHelper.insert(any7, cannotProceed);
/* 291 */           paramServerRequest.except(any7);
/*     */           
/*     */           return;
/* 294 */         } catch (InvalidName invalidName) {
/* 295 */           any7 = _orb().create_any();
/* 296 */           InvalidNameHelper.insert(any7, invalidName);
/* 297 */           paramServerRequest.except(any7);
/*     */           return;
/*     */         } 
/* 300 */         any5 = _orb().create_any();
/* 301 */         any5.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 302 */         paramServerRequest.result(any5);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 6:
/* 307 */         nVList = _orb().create_list(0);
/* 308 */         any2 = _orb().create_any();
/* 309 */         any2.type(ORB.init().get_primitive_tc(TCKind.tk_ulong));
/* 310 */         nVList.add_value("how_many", any2, 1);
/* 311 */         any3 = _orb().create_any();
/* 312 */         any3.type(BindingListHelper.type());
/* 313 */         nVList.add_value("bl", any3, 2);
/* 314 */         any5 = _orb().create_any();
/* 315 */         any5.type(BindingIteratorHelper.type());
/* 316 */         nVList.add_value("bi", any5, 2);
/* 317 */         paramServerRequest.params(nVList);
/*     */         
/* 319 */         i = any2.extract_ulong();
/*     */         
/* 321 */         bindingListHolder = new BindingListHolder();
/*     */         
/* 323 */         bindingIteratorHolder = new BindingIteratorHolder();
/* 324 */         list(i, bindingListHolder, bindingIteratorHolder);
/* 325 */         BindingListHelper.insert(any3, bindingListHolder.value);
/* 326 */         BindingIteratorHelper.insert(any5, bindingIteratorHolder.value);
/* 327 */         any9 = _orb().create_any();
/* 328 */         any9.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 329 */         paramServerRequest.result(any9);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 7:
/* 334 */         nVList = _orb().create_list(0);
/* 335 */         paramServerRequest.params(nVList);
/*     */         
/* 337 */         namingContext1 = new_context();
/* 338 */         any3 = _orb().create_any();
/* 339 */         NamingContextHelper.insert(any3, namingContext1);
/* 340 */         paramServerRequest.result(any3);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 8:
/* 345 */         nVList = _orb().create_list(0);
/* 346 */         any1 = _orb().create_any();
/* 347 */         any1.type(NameHelper.type());
/* 348 */         nVList.add_value("n", any1, 1);
/* 349 */         paramServerRequest.params(nVList);
/*     */         
/* 351 */         arrayOfNameComponent1 = NameHelper.extract(any1);
/*     */         
/*     */         try {
/* 354 */           namingContext2 = bind_new_context(arrayOfNameComponent1);
/*     */         }
/* 356 */         catch (NotFound notFound) {
/* 357 */           Any any = _orb().create_any();
/* 358 */           NotFoundHelper.insert(any, notFound);
/* 359 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 362 */         } catch (AlreadyBound alreadyBound) {
/* 363 */           Any any = _orb().create_any();
/* 364 */           AlreadyBoundHelper.insert(any, alreadyBound);
/* 365 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 368 */         } catch (CannotProceed cannotProceed) {
/* 369 */           Any any = _orb().create_any();
/* 370 */           CannotProceedHelper.insert(any, cannotProceed);
/* 371 */           paramServerRequest.except(any);
/*     */           
/*     */           return;
/* 374 */         } catch (InvalidName invalidName) {
/* 375 */           Any any = _orb().create_any();
/* 376 */           InvalidNameHelper.insert(any, invalidName);
/* 377 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 380 */         any6 = _orb().create_any();
/* 381 */         NamingContextHelper.insert(any6, namingContext2);
/* 382 */         paramServerRequest.result(any6);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 9:
/* 387 */         nVList = _orb().create_list(0);
/* 388 */         paramServerRequest.params(nVList);
/*     */         try {
/* 390 */           destroy();
/*     */         }
/* 392 */         catch (NotEmpty notEmpty) {
/* 393 */           Any any = _orb().create_any();
/* 394 */           NotEmptyHelper.insert(any, notEmpty);
/* 395 */           paramServerRequest.except(any);
/*     */           return;
/*     */         } 
/* 398 */         any1 = _orb().create_any();
/* 399 */         any1.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 400 */         paramServerRequest.result(any1);
/*     */         return;
/*     */     } 
/*     */     
/* 404 */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/_NamingContextImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */