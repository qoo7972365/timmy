/*     */ package sun.awt;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.AccessController;
/*     */ import sun.nio.cs.HistoricallyNamedCharset;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontDescriptor
/*     */   implements Cloneable
/*     */ {
/*     */   String nativeName;
/*     */   public CharsetEncoder encoder;
/*     */   String charsetName;
/*     */   private int[] exclusionRanges;
/*     */   public CharsetEncoder unicodeEncoder;
/*     */   boolean useUnicode;
/*     */   static boolean isLE;
/*     */   
/*     */   static {
/*  35 */     NativeLibLoader.loadLibraries();
/*  36 */     initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.io.unicode.encoding", "UnicodeBig"));
/*     */ 
/*     */     
/* 119 */     isLE = !"UnicodeBig".equals(str);
/*     */   }
/*     */   
/*     */   public FontDescriptor(String paramString, CharsetEncoder paramCharsetEncoder, int[] paramArrayOfint) {
/*     */     this.nativeName = paramString;
/*     */     this.encoder = paramCharsetEncoder;
/*     */     this.exclusionRanges = paramArrayOfint;
/*     */     this.useUnicode = false;
/*     */     Charset charset = paramCharsetEncoder.charset();
/*     */     if (charset instanceof HistoricallyNamedCharset) {
/*     */       this.charsetName = ((HistoricallyNamedCharset)charset).historicalName();
/*     */     } else {
/*     */       this.charsetName = charset.name();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getNativeName() {
/*     */     return this.nativeName;
/*     */   }
/*     */   
/*     */   public CharsetEncoder getFontCharsetEncoder() {
/*     */     return this.encoder;
/*     */   }
/*     */   
/*     */   public String getFontCharsetName() {
/*     */     return this.charsetName;
/*     */   }
/*     */   
/*     */   public int[] getExclusionRanges() {
/*     */     return this.exclusionRanges;
/*     */   }
/*     */   
/*     */   public boolean isExcluded(char paramChar) {
/*     */     for (byte b = 0; b < this.exclusionRanges.length; ) {
/*     */       int i = this.exclusionRanges[b++];
/*     */       int j = this.exclusionRanges[b++];
/*     */       if (paramChar >= i && paramChar <= j)
/*     */         return true; 
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return super.toString() + " [" + this.nativeName + "|" + this.encoder + "]";
/*     */   }
/*     */   
/*     */   public boolean useUnicode() {
/*     */     if (this.useUnicode && this.unicodeEncoder == null)
/*     */       try {
/*     */         this.unicodeEncoder = isLE ? StandardCharsets.UTF_16LE.newEncoder() : StandardCharsets.UTF_16BE.newEncoder();
/*     */       } catch (IllegalArgumentException illegalArgumentException) {} 
/*     */     return this.useUnicode;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/FontDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */