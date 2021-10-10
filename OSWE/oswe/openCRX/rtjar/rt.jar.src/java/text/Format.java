/*     */ package java.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Format
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -299282585814624189L;
/*     */   
/*     */   public final String format(Object paramObject) {
/* 157 */     return format(paramObject, new StringBuffer(), new FieldPosition(0)).toString();
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
/*     */   public abstract StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject) {
/* 206 */     return createAttributedCharacterIterator(format(paramObject));
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
/*     */   public abstract Object parseObject(String paramString, ParsePosition paramParsePosition);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String paramString) throws ParseException {
/* 242 */     ParsePosition parsePosition = new ParsePosition(0);
/* 243 */     Object object = parseObject(paramString, parsePosition);
/* 244 */     if (parsePosition.index == 0) {
/* 245 */       throw new ParseException("Format.parseObject(String) failed", parsePosition.errorIndex);
/*     */     }
/*     */     
/* 248 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 258 */       return super.clone();
/* 259 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 261 */       throw new InternalError(cloneNotSupportedException);
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
/*     */   AttributedCharacterIterator createAttributedCharacterIterator(String paramString) {
/* 278 */     AttributedString attributedString = new AttributedString(paramString);
/*     */     
/* 280 */     return attributedString.getIterator();
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
/*     */   AttributedCharacterIterator createAttributedCharacterIterator(AttributedCharacterIterator[] paramArrayOfAttributedCharacterIterator) {
/* 295 */     AttributedString attributedString = new AttributedString(paramArrayOfAttributedCharacterIterator);
/*     */     
/* 297 */     return attributedString.getIterator();
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
/*     */   AttributedCharacterIterator createAttributedCharacterIterator(String paramString, AttributedCharacterIterator.Attribute paramAttribute, Object paramObject) {
/* 313 */     AttributedString attributedString = new AttributedString(paramString);
/*     */     
/* 315 */     attributedString.addAttribute(paramAttribute, paramObject);
/* 316 */     return attributedString.getIterator();
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
/*     */   AttributedCharacterIterator createAttributedCharacterIterator(AttributedCharacterIterator paramAttributedCharacterIterator, AttributedCharacterIterator.Attribute paramAttribute, Object paramObject) {
/* 332 */     AttributedString attributedString = new AttributedString(paramAttributedCharacterIterator);
/*     */     
/* 334 */     attributedString.addAttribute(paramAttribute, paramObject);
/* 335 */     return attributedString.getIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static interface FieldDelegate
/*     */   {
/*     */     void formatted(Format.Field param1Field, Object param1Object, int param1Int1, int param1Int2, StringBuffer param1StringBuffer);
/*     */ 
/*     */ 
/*     */     
/*     */     void formatted(int param1Int1, Format.Field param1Field, Object param1Object, int param1Int2, int param1Int3, StringBuffer param1StringBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Field
/*     */     extends AttributedCharacterIterator.Attribute
/*     */   {
/*     */     private static final long serialVersionUID = 276966692217360283L;
/*     */ 
/*     */     
/*     */     protected Field(String param1String) {
/* 358 */       super(param1String);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/Format.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */