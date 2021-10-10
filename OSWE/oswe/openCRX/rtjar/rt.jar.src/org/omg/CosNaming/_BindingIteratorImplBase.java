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
/*     */ import org.omg.CORBA.ServerRequest;
/*     */ import org.omg.CORBA.TCKind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class _BindingIteratorImplBase
/*     */   extends DynamicImplementation
/*     */   implements BindingIterator
/*     */ {
/*  40 */   private static final String[] _type_ids = new String[] { "IDL:omg.org/CosNaming/BindingIterator:1.0" };
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  44 */     return (String[])_type_ids.clone();
/*     */   }
/*  46 */   private static Dictionary _methods = new Hashtable<>();
/*     */   
/*  48 */   static { _methods.put("next_one", new Integer(0));
/*  49 */     _methods.put("next_n", new Integer(1));
/*  50 */     _methods.put("destroy", new Integer(2)); } public void invoke(ServerRequest paramServerRequest) { NVList nVList; Any any1; BindingHolder bindingHolder; Any any2; boolean bool1; int i; Any any3;
/*     */     BindingListHolder bindingListHolder;
/*     */     boolean bool2;
/*     */     Any any4;
/*  54 */     switch (((Integer)_methods.get(paramServerRequest.op_name())).intValue()) {
/*     */       
/*     */       case 0:
/*  57 */         nVList = _orb().create_list(0);
/*  58 */         any1 = _orb().create_any();
/*  59 */         any1.type(BindingHelper.type());
/*  60 */         nVList.add_value("b", any1, 2);
/*  61 */         paramServerRequest.params(nVList);
/*     */         
/*  63 */         bindingHolder = new BindingHolder();
/*     */         
/*  65 */         bool1 = next_one(bindingHolder);
/*  66 */         BindingHelper.insert(any1, bindingHolder.value);
/*  67 */         any3 = _orb().create_any();
/*  68 */         any3.insert_boolean(bool1);
/*  69 */         paramServerRequest.result(any3);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 1:
/*  74 */         nVList = _orb().create_list(0);
/*  75 */         any1 = _orb().create_any();
/*  76 */         any1.type(ORB.init().get_primitive_tc(TCKind.tk_ulong));
/*  77 */         nVList.add_value("how_many", any1, 1);
/*  78 */         any2 = _orb().create_any();
/*  79 */         any2.type(BindingListHelper.type());
/*  80 */         nVList.add_value("bl", any2, 2);
/*  81 */         paramServerRequest.params(nVList);
/*     */         
/*  83 */         i = any1.extract_ulong();
/*     */         
/*  85 */         bindingListHolder = new BindingListHolder();
/*     */         
/*  87 */         bool2 = next_n(i, bindingListHolder);
/*  88 */         BindingListHelper.insert(any2, bindingListHolder.value);
/*  89 */         any4 = _orb().create_any();
/*  90 */         any4.insert_boolean(bool2);
/*  91 */         paramServerRequest.result(any4);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/*  96 */         nVList = _orb().create_list(0);
/*  97 */         paramServerRequest.params(nVList);
/*  98 */         destroy();
/*  99 */         any1 = _orb().create_any();
/* 100 */         any1.type(_orb().get_primitive_tc(TCKind.tk_void));
/* 101 */         paramServerRequest.result(any1);
/*     */         return;
/*     */     } 
/*     */     
/* 105 */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/_BindingIteratorImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */