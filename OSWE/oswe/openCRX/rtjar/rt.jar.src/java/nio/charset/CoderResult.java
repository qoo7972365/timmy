/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoderResult
/*     */ {
/*     */   private static final int CR_UNDERFLOW = 0;
/*     */   private static final int CR_OVERFLOW = 1;
/*     */   private static final int CR_ERROR_MIN = 2;
/*     */   private static final int CR_MALFORMED = 2;
/*     */   private static final int CR_UNMAPPABLE = 3;
/*  93 */   private static final String[] names = new String[] { "UNDERFLOW", "OVERFLOW", "MALFORMED", "UNMAPPABLE" };
/*     */   
/*     */   private final int type;
/*     */   
/*     */   private final int length;
/*     */   
/*     */   private CoderResult(int paramInt1, int paramInt2) {
/* 100 */     this.type = paramInt1;
/* 101 */     this.length = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     String str = names[this.type];
/* 111 */     return isError() ? (str + "[" + this.length + "]") : str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderflow() {
/* 120 */     return (this.type == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOverflow() {
/* 129 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isError() {
/* 139 */     return (this.type >= 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMalformed() {
/* 149 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnmappable() {
/* 160 */     return (this.type == 3);
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
/*     */   public int length() {
/* 174 */     if (!isError())
/* 175 */       throw new UnsupportedOperationException(); 
/* 176 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static final CoderResult UNDERFLOW = new CoderResult(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static final CoderResult OVERFLOW = new CoderResult(1, 0);
/*     */ 
/*     */   
/*     */   private static abstract class Cache
/*     */   {
/* 196 */     private Map<Integer, WeakReference<CoderResult>> cache = null;
/*     */     
/*     */     protected abstract CoderResult create(int param1Int);
/*     */     
/*     */     private synchronized CoderResult get(int param1Int) {
/* 201 */       if (param1Int <= 0)
/* 202 */         throw new IllegalArgumentException("Non-positive length"); 
/* 203 */       Integer integer = new Integer(param1Int);
/*     */       
/* 205 */       CoderResult coderResult = null;
/* 206 */       if (this.cache == null)
/* 207 */       { this.cache = new HashMap<>(); }
/* 208 */       else { WeakReference<CoderResult> weakReference; if ((weakReference = this.cache.get(integer)) != null)
/* 209 */           coderResult = weakReference.get();  }
/*     */       
/* 211 */       if (coderResult == null) {
/* 212 */         coderResult = create(param1Int);
/* 213 */         this.cache.put(integer, new WeakReference<>(coderResult));
/*     */       } 
/* 215 */       return coderResult;
/*     */     }
/*     */     
/*     */     private Cache() {} }
/*     */   
/* 220 */   private static Cache malformedCache = new Cache()
/*     */     {
/*     */       public CoderResult create(int param1Int) {
/* 223 */         return new CoderResult(2, param1Int);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CoderResult malformedForLength(int paramInt) {
/* 236 */     return malformedCache.get(paramInt);
/*     */   }
/*     */   
/* 239 */   private static Cache unmappableCache = new Cache()
/*     */     {
/*     */       public CoderResult create(int param1Int) {
/* 242 */         return new CoderResult(3, param1Int);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CoderResult unmappableForLength(int paramInt) {
/* 255 */     return unmappableCache.get(paramInt);
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
/*     */   public void throwException() throws CharacterCodingException {
/* 278 */     switch (this.type) { case 0:
/* 279 */         throw new BufferUnderflowException();
/* 280 */       case 1: throw new BufferOverflowException();
/* 281 */       case 2: throw new MalformedInputException(this.length);
/* 282 */       case 3: throw new UnmappableCharacterException(this.length); }
/*     */     
/*     */     assert false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/charset/CoderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */