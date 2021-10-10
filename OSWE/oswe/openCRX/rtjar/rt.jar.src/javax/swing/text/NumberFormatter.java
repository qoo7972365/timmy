/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.Format;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Map;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberFormatter
/*     */   extends InternationalFormatter
/*     */ {
/*     */   private String specialChars;
/*     */   
/*     */   public NumberFormatter() {
/* 104 */     this(NumberFormat.getNumberInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormatter(NumberFormat paramNumberFormat) {
/* 113 */     super(paramNumberFormat);
/* 114 */     setFormat(paramNumberFormat);
/* 115 */     setAllowsInvalid(true);
/* 116 */     setCommitsOnValidEdit(false);
/* 117 */     setOverwriteMode(false);
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
/*     */   public void setFormat(Format paramFormat) {
/* 131 */     super.setFormat(paramFormat);
/*     */     
/* 133 */     DecimalFormatSymbols decimalFormatSymbols = getDecimalFormatSymbols();
/*     */     
/* 135 */     if (decimalFormatSymbols != null) {
/* 136 */       StringBuilder stringBuilder = new StringBuilder();
/*     */       
/* 138 */       stringBuilder.append(decimalFormatSymbols.getCurrencySymbol());
/* 139 */       stringBuilder.append(decimalFormatSymbols.getDecimalSeparator());
/* 140 */       stringBuilder.append(decimalFormatSymbols.getGroupingSeparator());
/* 141 */       stringBuilder.append(decimalFormatSymbols.getInfinity());
/* 142 */       stringBuilder.append(decimalFormatSymbols.getInternationalCurrencySymbol());
/* 143 */       stringBuilder.append(decimalFormatSymbols.getMinusSign());
/* 144 */       stringBuilder.append(decimalFormatSymbols.getMonetaryDecimalSeparator());
/* 145 */       stringBuilder.append(decimalFormatSymbols.getNaN());
/* 146 */       stringBuilder.append(decimalFormatSymbols.getPercent());
/* 147 */       stringBuilder.append('+');
/* 148 */       this.specialChars = stringBuilder.toString();
/*     */     } else {
/*     */       
/* 151 */       this.specialChars = "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object stringToValue(String paramString, Format paramFormat) throws ParseException {
/* 160 */     if (paramFormat == null) {
/* 161 */       return paramString;
/*     */     }
/* 163 */     Object object = paramFormat.parseObject(paramString);
/*     */     
/* 165 */     return convertValueToValueClass(object, getValueClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object convertValueToValueClass(Object paramObject, Class<Integer> paramClass) {
/* 176 */     if (paramClass != null && paramObject instanceof Number) {
/* 177 */       Number number = (Number)paramObject;
/* 178 */       if (paramClass == Integer.class) {
/* 179 */         return Integer.valueOf(number.intValue());
/*     */       }
/* 181 */       if (paramClass == Long.class) {
/* 182 */         return Long.valueOf(number.longValue());
/*     */       }
/* 184 */       if (paramClass == Float.class) {
/* 185 */         return Float.valueOf(number.floatValue());
/*     */       }
/* 187 */       if (paramClass == Double.class) {
/* 188 */         return Double.valueOf(number.doubleValue());
/*     */       }
/* 190 */       if (paramClass == Byte.class) {
/* 191 */         return Byte.valueOf(number.byteValue());
/*     */       }
/* 193 */       if (paramClass == Short.class) {
/* 194 */         return Short.valueOf(number.shortValue());
/*     */       }
/*     */     } 
/* 197 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char getPositiveSign() {
/* 204 */     return '+';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char getMinusSign() {
/* 211 */     DecimalFormatSymbols decimalFormatSymbols = getDecimalFormatSymbols();
/*     */     
/* 213 */     if (decimalFormatSymbols != null) {
/* 214 */       return decimalFormatSymbols.getMinusSign();
/*     */     }
/* 216 */     return '-';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char getDecimalSeparator() {
/* 223 */     DecimalFormatSymbols decimalFormatSymbols = getDecimalFormatSymbols();
/*     */     
/* 225 */     if (decimalFormatSymbols != null) {
/* 226 */       return decimalFormatSymbols.getDecimalSeparator();
/*     */     }
/* 228 */     return '.';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DecimalFormatSymbols getDecimalFormatSymbols() {
/* 235 */     Format format = getFormat();
/*     */     
/* 237 */     if (format instanceof DecimalFormat) {
/* 238 */       return ((DecimalFormat)format).getDecimalFormatSymbols();
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLegalInsertText(String paramString) {
/* 250 */     if (getAllowsInvalid()) {
/* 251 */       return true;
/*     */     }
/* 253 */     for (int i = paramString.length() - 1; i >= 0; i--) {
/* 254 */       char c = paramString.charAt(i);
/*     */       
/* 256 */       if (!Character.isDigit(c) && this.specialChars
/* 257 */         .indexOf(c) == -1) {
/* 258 */         return false;
/*     */       }
/*     */     } 
/* 261 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLiteral(Map paramMap) {
/* 269 */     if (!super.isLiteral(paramMap)) {
/* 270 */       if (paramMap == null) {
/* 271 */         return false;
/*     */       }
/* 273 */       int i = paramMap.size();
/*     */       
/* 275 */       if (paramMap.get(NumberFormat.Field.GROUPING_SEPARATOR) != null) {
/* 276 */         i--;
/* 277 */         if (paramMap.get(NumberFormat.Field.INTEGER) != null) {
/* 278 */           i--;
/*     */         }
/*     */       } 
/* 281 */       if (paramMap.get(NumberFormat.Field.EXPONENT_SYMBOL) != null) {
/* 282 */         i--;
/*     */       }
/* 284 */       if (paramMap.get(NumberFormat.Field.PERCENT) != null) {
/* 285 */         i--;
/*     */       }
/* 287 */       if (paramMap.get(NumberFormat.Field.PERMILLE) != null) {
/* 288 */         i--;
/*     */       }
/* 290 */       if (paramMap.get(NumberFormat.Field.CURRENCY) != null) {
/* 291 */         i--;
/*     */       }
/* 293 */       if (paramMap.get(NumberFormat.Field.SIGN) != null) {
/* 294 */         i--;
/*     */       }
/* 296 */       return (i == 0);
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isNavigatable(int paramInt) {
/* 307 */     if (!super.isNavigatable(paramInt))
/*     */     {
/* 309 */       return (getBufferedChar(paramInt) == getDecimalSeparator());
/*     */     }
/* 311 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NumberFormat.Field getFieldFrom(int paramInt1, int paramInt2) {
/* 319 */     if (isValidMask()) {
/* 320 */       int i = getFormattedTextField().getDocument().getLength();
/* 321 */       AttributedCharacterIterator attributedCharacterIterator = getIterator();
/*     */       
/* 323 */       if (paramInt1 >= i) {
/* 324 */         paramInt1 += paramInt2;
/*     */       }
/* 326 */       while (paramInt1 >= 0 && paramInt1 < i) {
/* 327 */         attributedCharacterIterator.setIndex(paramInt1);
/*     */         
/* 329 */         Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
/*     */         
/* 331 */         if (map != null && map.size() > 0) {
/* 332 */           for (NumberFormat.Field field : map.keySet()) {
/* 333 */             if (field instanceof NumberFormat.Field) {
/* 334 */               return field;
/*     */             }
/*     */           } 
/*     */         }
/* 338 */         paramInt1 += paramInt2;
/*     */       } 
/*     */     } 
/* 341 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void replace(DocumentFilter.FilterBypass paramFilterBypass, int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) throws BadLocationException {
/* 350 */     if (!getAllowsInvalid() && paramInt2 == 0 && paramString != null && paramString
/* 351 */       .length() == 1 && 
/* 352 */       toggleSignIfNecessary(paramFilterBypass, paramInt1, paramString.charAt(0))) {
/*     */       return;
/*     */     }
/* 355 */     super.replace(paramFilterBypass, paramInt1, paramInt2, paramString, paramAttributeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean toggleSignIfNecessary(DocumentFilter.FilterBypass paramFilterBypass, int paramInt, char paramChar) throws BadLocationException {
/* 366 */     if (paramChar == getMinusSign() || paramChar == getPositiveSign()) {
/* 367 */       NumberFormat.Field field = getFieldFrom(paramInt, -1);
/*     */       
/*     */       try {
/*     */         Object object;
/* 371 */         if (field == null || (field != NumberFormat.Field.EXPONENT && field != NumberFormat.Field.EXPONENT_SYMBOL && field != NumberFormat.Field.EXPONENT_SIGN)) {
/*     */ 
/*     */ 
/*     */           
/* 375 */           object = toggleSign((paramChar == getPositiveSign()));
/*     */         }
/*     */         else {
/*     */           
/* 379 */           object = toggleExponentSign(paramInt, paramChar);
/*     */         } 
/* 381 */         if (object != null && isValidValue(object, false)) {
/* 382 */           int i = getLiteralCountTo(paramInt);
/* 383 */           String str = valueToString(object);
/*     */           
/* 385 */           paramFilterBypass.remove(0, paramFilterBypass.getDocument().getLength());
/* 386 */           paramFilterBypass.insertString(0, str, null);
/* 387 */           updateValue(object);
/* 388 */           repositionCursor(getLiteralCountTo(paramInt) - i + paramInt, 1);
/*     */           
/* 390 */           return true;
/*     */         } 
/* 392 */       } catch (ParseException parseException) {
/* 393 */         invalidEdit();
/*     */       } 
/*     */     } 
/* 396 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object toggleSign(boolean paramBoolean) throws ParseException {
/* 404 */     Object object = stringToValue(getFormattedTextField().getText());
/*     */     
/* 406 */     if (object != null) {
/*     */ 
/*     */       
/* 409 */       String str = object.toString();
/*     */       
/* 411 */       if (str != null && str.length() > 0) {
/* 412 */         if (paramBoolean) {
/* 413 */           if (str.charAt(0) == '-') {
/* 414 */             str = str.substring(1);
/*     */           }
/*     */         } else {
/*     */           
/* 418 */           if (str.charAt(0) == '+') {
/* 419 */             str = str.substring(1);
/*     */           }
/* 421 */           if (str.length() > 0 && str.charAt(0) != '-') {
/* 422 */             str = "-" + str;
/*     */           }
/*     */         } 
/* 425 */         if (str != null) {
/* 426 */           Class<?> clazz = getValueClass();
/*     */           
/* 428 */           if (clazz == null) {
/* 429 */             clazz = object.getClass();
/*     */           }
/*     */           try {
/* 432 */             ReflectUtil.checkPackageAccess(clazz);
/* 433 */             SwingUtilities2.checkAccess(clazz.getModifiers());
/* 434 */             Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class });
/*     */             
/* 436 */             if (constructor != null) {
/* 437 */               SwingUtilities2.checkAccess(constructor.getModifiers());
/* 438 */               return constructor.newInstance(new Object[] { str });
/*     */             } 
/* 440 */           } catch (Throwable throwable) {}
/*     */         } 
/*     */       } 
/*     */     } 
/* 444 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object toggleExponentSign(int paramInt, char paramChar) throws BadLocationException, ParseException {
/* 453 */     String str = getFormattedTextField().getText();
/* 454 */     boolean bool = false;
/* 455 */     int i = getAttributeStart(NumberFormat.Field.EXPONENT_SIGN);
/*     */     
/* 457 */     if (i >= 0) {
/* 458 */       bool = true;
/* 459 */       paramInt = i;
/*     */     } 
/* 461 */     if (paramChar == getPositiveSign()) {
/* 462 */       str = getReplaceString(paramInt, bool, (String)null);
/*     */     } else {
/*     */       
/* 465 */       str = getReplaceString(paramInt, bool, new String(new char[] { paramChar }));
/*     */     } 
/*     */     
/* 468 */     return stringToValue(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/NumberFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */