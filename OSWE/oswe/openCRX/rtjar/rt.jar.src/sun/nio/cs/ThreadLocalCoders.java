/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadLocalCoders
/*     */ {
/*     */   private static final int CACHE_SIZE = 3;
/*     */   
/*     */   private static abstract class Cache
/*     */   {
/*  43 */     private ThreadLocal<Object[]> cache = new ThreadLocal();
/*     */     private final int size;
/*     */     
/*     */     Cache(int param1Int) {
/*  47 */       this.size = param1Int;
/*     */     }
/*     */     
/*     */     abstract Object create(Object param1Object);
/*     */     
/*     */     private void moveToFront(Object[] param1ArrayOfObject, int param1Int) {
/*  53 */       Object object = param1ArrayOfObject[param1Int];
/*  54 */       for (int i = param1Int; i > 0; i--)
/*  55 */         param1ArrayOfObject[i] = param1ArrayOfObject[i - 1]; 
/*  56 */       param1ArrayOfObject[0] = object;
/*     */     }
/*     */     
/*     */     abstract boolean hasName(Object param1Object1, Object param1Object2);
/*     */     
/*     */     Object forName(Object param1Object) {
/*  62 */       Object[] arrayOfObject = this.cache.get();
/*  63 */       if (arrayOfObject == null) {
/*  64 */         arrayOfObject = new Object[this.size];
/*  65 */         this.cache.set(arrayOfObject);
/*     */       } else {
/*  67 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/*  68 */           Object object1 = arrayOfObject[b];
/*  69 */           if (object1 != null)
/*     */           {
/*  71 */             if (hasName(object1, param1Object)) {
/*  72 */               if (b > 0)
/*  73 */                 moveToFront(arrayOfObject, b); 
/*  74 */               return object1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       Object object = create(param1Object);
/*  81 */       arrayOfObject[arrayOfObject.length - 1] = object;
/*  82 */       moveToFront(arrayOfObject, arrayOfObject.length - 1);
/*  83 */       return object;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  88 */   private static Cache decoderCache = new Cache(3) {
/*     */       boolean hasName(Object param1Object1, Object param1Object2) {
/*  90 */         if (param1Object2 instanceof String)
/*  91 */           return ((CharsetDecoder)param1Object1).charset().name().equals(param1Object2); 
/*  92 */         if (param1Object2 instanceof Charset)
/*  93 */           return ((CharsetDecoder)param1Object1).charset().equals(param1Object2); 
/*  94 */         return false;
/*     */       }
/*     */       Object create(Object param1Object) {
/*  97 */         if (param1Object instanceof String)
/*  98 */           return Charset.forName((String)param1Object).newDecoder(); 
/*  99 */         if (param1Object instanceof Charset)
/* 100 */           return ((Charset)param1Object).newDecoder(); 
/*     */         assert false;
/* 102 */         return null;
/*     */       }
/*     */     };
/*     */   
/*     */   public static CharsetDecoder decoderFor(Object paramObject) {
/* 107 */     CharsetDecoder charsetDecoder = (CharsetDecoder)decoderCache.forName(paramObject);
/* 108 */     charsetDecoder.reset();
/* 109 */     return charsetDecoder;
/*     */   }
/*     */   
/* 112 */   private static Cache encoderCache = new Cache(3) {
/*     */       boolean hasName(Object param1Object1, Object param1Object2) {
/* 114 */         if (param1Object2 instanceof String)
/* 115 */           return ((CharsetEncoder)param1Object1).charset().name().equals(param1Object2); 
/* 116 */         if (param1Object2 instanceof Charset)
/* 117 */           return ((CharsetEncoder)param1Object1).charset().equals(param1Object2); 
/* 118 */         return false;
/*     */       }
/*     */       Object create(Object param1Object) {
/* 121 */         if (param1Object instanceof String)
/* 122 */           return Charset.forName((String)param1Object).newEncoder(); 
/* 123 */         if (param1Object instanceof Charset)
/* 124 */           return ((Charset)param1Object).newEncoder(); 
/*     */         assert false;
/* 126 */         return null;
/*     */       }
/*     */     };
/*     */   
/*     */   public static CharsetEncoder encoderFor(Object paramObject) {
/* 131 */     CharsetEncoder charsetEncoder = (CharsetEncoder)encoderCache.forName(paramObject);
/* 132 */     charsetEncoder.reset();
/* 133 */     return charsetEncoder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/ThreadLocalCoders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */