/*     */ package com.sun.corba.se.spi.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OperationFactory
/*     */ {
/*     */   private static String getString(Object paramObject) {
/*  76 */     if (paramObject instanceof String) {
/*  77 */       return (String)paramObject;
/*     */     }
/*  79 */     throw new Error("String expected");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object[] getObjectArray(Object paramObject) {
/*  84 */     if (paramObject instanceof Object[]) {
/*  85 */       return (Object[])paramObject;
/*     */     }
/*  87 */     throw new Error("Object[] expected");
/*     */   }
/*     */ 
/*     */   
/*     */   private static StringPair getStringPair(Object paramObject) {
/*  92 */     if (paramObject instanceof StringPair) {
/*  93 */       return (StringPair)paramObject;
/*     */     }
/*  95 */     throw new Error("StringPair expected");
/*     */   }
/*     */   
/*     */   private static abstract class OperationBase
/*     */     implements Operation {
/*     */     public boolean equals(Object param1Object) {
/* 101 */       if (this == param1Object) {
/* 102 */         return true;
/*     */       }
/* 104 */       if (!(param1Object instanceof OperationBase)) {
/* 105 */         return false;
/*     */       }
/* 107 */       OperationBase operationBase = (OperationBase)param1Object;
/*     */       
/* 109 */       return toString().equals(operationBase.toString());
/*     */     }
/*     */     private OperationBase() {}
/*     */     
/*     */     public int hashCode() {
/* 114 */       return toString().hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MaskErrorAction
/*     */     extends OperationBase
/*     */   {
/*     */     private Operation op;
/*     */     
/*     */     public MaskErrorAction(Operation param1Operation) {
/* 124 */       this.op = param1Operation;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/*     */       try {
/* 130 */         return this.op.operate(param1Object);
/* 131 */       } catch (Exception exception) {
/* 132 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 138 */       return "maskErrorAction(" + this.op + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation maskErrorAction(Operation paramOperation) {
/* 144 */     return new MaskErrorAction(paramOperation);
/*     */   }
/*     */   
/*     */   private static class IndexAction
/*     */     extends OperationBase
/*     */   {
/*     */     private int index;
/*     */     
/*     */     public IndexAction(int param1Int) {
/* 153 */       this.index = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 158 */       return OperationFactory.getObjectArray(param1Object)[this.index];
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 163 */       return "indexAction(" + this.index + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation indexAction(int paramInt) {
/* 169 */     return new IndexAction(paramInt);
/*     */   }
/*     */   
/*     */   private static class SuffixAction extends OperationBase {
/*     */     private SuffixAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 176 */       return OperationFactory.getStringPair(param1Object).getFirst();
/*     */     }
/*     */     public String toString() {
/* 179 */       return "suffixAction";
/*     */     } }
/*     */   
/* 182 */   private static Operation suffixActionImpl = new SuffixAction();
/*     */   
/*     */   private static class ValueAction extends OperationBase {
/*     */     private ValueAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 188 */       return OperationFactory.getStringPair(param1Object).getSecond();
/*     */     }
/*     */     public String toString() {
/* 191 */       return "valueAction";
/*     */     } }
/*     */   
/* 194 */   private static Operation valueActionImpl = new ValueAction();
/*     */   
/*     */   private static class IdentityAction extends OperationBase {
/*     */     private IdentityAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 200 */       return param1Object;
/*     */     }
/*     */     public String toString() {
/* 203 */       return "identityAction";
/*     */     } }
/*     */   
/* 206 */   private static Operation identityActionImpl = new IdentityAction();
/*     */   
/*     */   private static class BooleanAction extends OperationBase {
/*     */     private BooleanAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 212 */       return new Boolean(OperationFactory.getString(param1Object));
/*     */     }
/*     */     public String toString() {
/* 215 */       return "booleanAction";
/*     */     } }
/*     */   
/* 218 */   private static Operation booleanActionImpl = new BooleanAction();
/*     */   
/*     */   private static class IntegerAction extends OperationBase {
/*     */     private IntegerAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 224 */       return new Integer(OperationFactory.getString(param1Object));
/*     */     }
/*     */     public String toString() {
/* 227 */       return "integerAction";
/*     */     } }
/*     */   
/* 230 */   private static Operation integerActionImpl = new IntegerAction();
/*     */   
/*     */   private static class StringAction extends OperationBase {
/*     */     private StringAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 236 */       return param1Object;
/*     */     }
/*     */     public String toString() {
/* 239 */       return "stringAction";
/*     */     } }
/*     */   
/* 242 */   private static Operation stringActionImpl = new StringAction();
/*     */   
/*     */   private static class ClassAction extends OperationBase {
/*     */     private ClassAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 248 */       String str = OperationFactory.getString(param1Object);
/*     */ 
/*     */       
/*     */       try {
/* 252 */         return SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*     */       }
/* 254 */       catch (Exception exception) {
/* 255 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get("orb.lifecycle");
/*     */         
/* 257 */         throw oRBUtilSystemException.couldNotLoadClass(exception, str);
/*     */       } 
/*     */     }
/*     */     public String toString() {
/* 261 */       return "classAction";
/*     */     } }
/*     */   
/* 264 */   private static Operation classActionImpl = new ClassAction();
/*     */   
/*     */   private static class SetFlagAction extends OperationBase {
/*     */     private SetFlagAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 270 */       return Boolean.TRUE;
/*     */     }
/*     */     public String toString() {
/* 273 */       return "setFlagAction";
/*     */     } }
/*     */   
/* 276 */   private static Operation setFlagActionImpl = new SetFlagAction();
/*     */   
/*     */   private static class URLAction extends OperationBase {
/*     */     private URLAction() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 282 */       String str = (String)param1Object;
/*     */       try {
/* 284 */         return new URL(str);
/* 285 */       } catch (MalformedURLException malformedURLException) {
/* 286 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get("orb.lifecycle");
/*     */         
/* 288 */         throw oRBUtilSystemException.badUrl(malformedURLException, str);
/*     */       } 
/*     */     }
/*     */     public String toString() {
/* 292 */       return "URLAction";
/*     */     } }
/*     */   
/* 295 */   private static Operation URLActionImpl = new URLAction();
/*     */ 
/*     */   
/*     */   public static Operation identityAction() {
/* 299 */     return identityActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation suffixAction() {
/* 304 */     return suffixActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation valueAction() {
/* 309 */     return valueActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation booleanAction() {
/* 314 */     return booleanActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation integerAction() {
/* 319 */     return integerActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation stringAction() {
/* 324 */     return stringActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation classAction() {
/* 329 */     return classActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation setFlagAction() {
/* 334 */     return setFlagActionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation URLAction() {
/* 339 */     return URLActionImpl;
/*     */   }
/*     */   
/*     */   private static class IntegerRangeAction
/*     */     extends OperationBase
/*     */   {
/*     */     private int min;
/*     */     private int max;
/*     */     
/*     */     IntegerRangeAction(int param1Int1, int param1Int2) {
/* 349 */       this.min = param1Int1;
/* 350 */       this.max = param1Int2;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 355 */       int i = Integer.parseInt(OperationFactory.getString(param1Object));
/* 356 */       if (i >= this.min && i <= this.max) {
/* 357 */         return new Integer(i);
/*     */       }
/* 359 */       throw new IllegalArgumentException("Property value " + i + " is not in the range " + this.min + " to " + this.max);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 365 */       return "integerRangeAction(" + this.min + "," + this.max + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation integerRangeAction(int paramInt1, int paramInt2) {
/* 371 */     return new IntegerRangeAction(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   private static class ListAction
/*     */     extends OperationBase {
/*     */     private String sep;
/*     */     private Operation act;
/*     */     
/*     */     ListAction(String param1String, Operation param1Operation) {
/* 380 */       this.sep = param1String;
/* 381 */       this.act = param1Operation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 390 */       StringTokenizer stringTokenizer = new StringTokenizer(OperationFactory.getString(param1Object), this.sep);
/*     */       
/* 392 */       int i = stringTokenizer.countTokens();
/* 393 */       Object object = null;
/* 394 */       byte b = 0;
/* 395 */       while (stringTokenizer.hasMoreTokens()) {
/* 396 */         String str = stringTokenizer.nextToken();
/* 397 */         Object object1 = this.act.operate(str);
/* 398 */         if (object == null)
/* 399 */           object = Array.newInstance(object1.getClass(), i); 
/* 400 */         Array.set(object, b++, object1);
/*     */       } 
/*     */       
/* 403 */       return object;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 407 */       return "listAction(separator=\"" + this.sep + "\",action=" + this.act + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Operation listAction(String paramString, Operation paramOperation) {
/* 414 */     return new ListAction(paramString, paramOperation);
/*     */   }
/*     */   
/*     */   private static class SequenceAction
/*     */     extends OperationBase
/*     */   {
/*     */     private String sep;
/*     */     private Operation[] actions;
/*     */     
/*     */     SequenceAction(String param1String, Operation[] param1ArrayOfOperation) {
/* 424 */       this.sep = param1String;
/* 425 */       this.actions = param1ArrayOfOperation;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 430 */       StringTokenizer stringTokenizer = new StringTokenizer(OperationFactory.getString(param1Object), this.sep);
/*     */ 
/*     */       
/* 433 */       int i = stringTokenizer.countTokens();
/* 434 */       if (i != this.actions.length) {
/* 435 */         throw new Error("Number of tokens and number of actions do not match");
/*     */       }
/*     */       
/* 438 */       byte b = 0;
/* 439 */       Object[] arrayOfObject = new Object[i];
/* 440 */       while (stringTokenizer.hasMoreTokens()) {
/* 441 */         Operation operation = this.actions[b];
/* 442 */         String str = stringTokenizer.nextToken();
/* 443 */         arrayOfObject[b++] = operation.operate(str);
/*     */       } 
/*     */       
/* 446 */       return arrayOfObject;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 450 */       return "sequenceAction(separator=\"" + this.sep + "\",actions=" + 
/*     */         
/* 452 */         Arrays.toString((Object[])this.actions) + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Operation sequenceAction(String paramString, Operation[] paramArrayOfOperation) {
/* 459 */     return new SequenceAction(paramString, paramArrayOfOperation);
/*     */   }
/*     */   
/*     */   private static class ComposeAction
/*     */     extends OperationBase
/*     */   {
/*     */     private Operation op1;
/*     */     private Operation op2;
/*     */     
/*     */     ComposeAction(Operation param1Operation1, Operation param1Operation2) {
/* 469 */       this.op1 = param1Operation1;
/* 470 */       this.op2 = param1Operation2;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 475 */       return this.op2.operate(this.op1.operate(param1Object));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 479 */       return "composition(" + this.op1 + "," + this.op2 + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation compose(Operation paramOperation1, Operation paramOperation2) {
/* 485 */     return new ComposeAction(paramOperation1, paramOperation2);
/*     */   }
/*     */   
/*     */   private static class MapAction
/*     */     extends OperationBase
/*     */   {
/*     */     Operation op;
/*     */     
/*     */     MapAction(Operation param1Operation) {
/* 494 */       this.op = param1Operation;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 499 */       Object[] arrayOfObject1 = (Object[])param1Object;
/* 500 */       Object[] arrayOfObject2 = new Object[arrayOfObject1.length];
/* 501 */       for (byte b = 0; b < arrayOfObject1.length; b++)
/* 502 */         arrayOfObject2[b] = this.op.operate(arrayOfObject1[b]); 
/* 503 */       return arrayOfObject2;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 507 */       return "mapAction(" + this.op + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation mapAction(Operation paramOperation) {
/* 513 */     return new MapAction(paramOperation);
/*     */   }
/*     */   
/*     */   private static class MapSequenceAction
/*     */     extends OperationBase
/*     */   {
/*     */     private Operation[] op;
/*     */     
/*     */     public MapSequenceAction(Operation[] param1ArrayOfOperation) {
/* 522 */       this.op = param1ArrayOfOperation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 530 */       Object[] arrayOfObject1 = (Object[])param1Object;
/* 531 */       Object[] arrayOfObject2 = new Object[arrayOfObject1.length];
/* 532 */       for (byte b = 0; b < arrayOfObject1.length; b++)
/* 533 */         arrayOfObject2[b] = this.op[b].operate(arrayOfObject1[b]); 
/* 534 */       return arrayOfObject2;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 538 */       return "mapSequenceAction(" + 
/* 539 */         Arrays.toString((Object[])this.op) + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Operation mapSequenceAction(Operation[] paramArrayOfOperation) {
/* 545 */     return new MapSequenceAction(paramArrayOfOperation);
/*     */   }
/*     */   
/*     */   private static class ConvertIntegerToShort extends OperationBase {
/*     */     private ConvertIntegerToShort() {}
/*     */     
/*     */     public Object operate(Object param1Object) {
/* 552 */       Integer integer = (Integer)param1Object;
/* 553 */       return new Short(integer.shortValue());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 557 */       return "ConvertIntegerToShort";
/*     */     }
/*     */   }
/*     */   
/* 561 */   private static Operation convertIntegerToShortImpl = new ConvertIntegerToShort();
/*     */ 
/*     */   
/*     */   public static Operation convertIntegerToShort() {
/* 565 */     return convertIntegerToShortImpl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/OperationFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */