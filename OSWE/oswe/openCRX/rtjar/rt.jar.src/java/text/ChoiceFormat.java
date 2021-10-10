/*     */ package java.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChoiceFormat
/*     */   extends NumberFormat
/*     */ {
/*     */   private static final long serialVersionUID = 1795184449645032964L;
/*     */   private double[] choiceLimits;
/*     */   private String[] choiceFormats;
/*     */   static final long SIGN = -9223372036854775808L;
/*     */   static final long EXPONENT = 9218868437227405312L;
/*     */   static final long POSITIVEINFINITY = 9218868437227405312L;
/*     */   
/*     */   public void applyPattern(String paramString) {
/* 177 */     StringBuffer[] arrayOfStringBuffer = new StringBuffer[2];
/* 178 */     for (byte b1 = 0; b1 < arrayOfStringBuffer.length; b1++) {
/* 179 */       arrayOfStringBuffer[b1] = new StringBuffer();
/*     */     }
/* 181 */     double[] arrayOfDouble = new double[30];
/* 182 */     String[] arrayOfString = new String[30];
/* 183 */     byte b2 = 0;
/* 184 */     boolean bool1 = false;
/* 185 */     double d1 = 0.0D;
/* 186 */     double d2 = Double.NaN;
/* 187 */     boolean bool2 = false;
/* 188 */     for (byte b3 = 0; b3 < paramString.length(); b3++) {
/* 189 */       char c = paramString.charAt(b3);
/* 190 */       if (c == '\'') {
/*     */         
/* 192 */         if (b3 + 1 < paramString.length() && paramString.charAt(b3 + 1) == c) {
/* 193 */           arrayOfStringBuffer[bool1].append(c);
/* 194 */           b3++;
/*     */         } else {
/* 196 */           bool2 = !bool2 ? true : false;
/*     */         } 
/* 198 */       } else if (bool2) {
/* 199 */         arrayOfStringBuffer[bool1].append(c);
/* 200 */       } else if (c == '<' || c == '#' || c == '≤') {
/* 201 */         if (arrayOfStringBuffer[0].length() == 0) {
/* 202 */           throw new IllegalArgumentException();
/*     */         }
/*     */         try {
/* 205 */           String str = arrayOfStringBuffer[0].toString();
/* 206 */           if (str.equals("∞")) {
/* 207 */             d1 = Double.POSITIVE_INFINITY;
/* 208 */           } else if (str.equals("-∞")) {
/* 209 */             d1 = Double.NEGATIVE_INFINITY;
/*     */           } else {
/* 211 */             d1 = Double.valueOf(arrayOfStringBuffer[0].toString()).doubleValue();
/*     */           } 
/* 213 */         } catch (Exception exception) {
/* 214 */           throw new IllegalArgumentException();
/*     */         } 
/* 216 */         if (c == '<' && d1 != Double.POSITIVE_INFINITY && d1 != Double.NEGATIVE_INFINITY)
/*     */         {
/* 218 */           d1 = nextDouble(d1);
/*     */         }
/* 220 */         if (d1 <= d2) {
/* 221 */           throw new IllegalArgumentException();
/*     */         }
/* 223 */         arrayOfStringBuffer[0].setLength(0);
/* 224 */         bool1 = true;
/* 225 */       } else if (c == '|') {
/* 226 */         if (b2 == arrayOfDouble.length) {
/* 227 */           arrayOfDouble = doubleArraySize(arrayOfDouble);
/* 228 */           arrayOfString = doubleArraySize(arrayOfString);
/*     */         } 
/* 230 */         arrayOfDouble[b2] = d1;
/* 231 */         arrayOfString[b2] = arrayOfStringBuffer[1].toString();
/* 232 */         b2++;
/* 233 */         d2 = d1;
/* 234 */         arrayOfStringBuffer[1].setLength(0);
/* 235 */         bool1 = false;
/*     */       } else {
/* 237 */         arrayOfStringBuffer[bool1].append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     if (bool1 == true) {
/* 242 */       if (b2 == arrayOfDouble.length) {
/* 243 */         arrayOfDouble = doubleArraySize(arrayOfDouble);
/* 244 */         arrayOfString = doubleArraySize(arrayOfString);
/*     */       } 
/* 246 */       arrayOfDouble[b2] = d1;
/* 247 */       arrayOfString[b2] = arrayOfStringBuffer[1].toString();
/* 248 */       b2++;
/*     */     } 
/* 250 */     this.choiceLimits = new double[b2];
/* 251 */     System.arraycopy(arrayOfDouble, 0, this.choiceLimits, 0, b2);
/* 252 */     this.choiceFormats = new String[b2];
/* 253 */     System.arraycopy(arrayOfString, 0, this.choiceFormats, 0, b2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toPattern() {
/* 262 */     StringBuffer stringBuffer = new StringBuffer();
/* 263 */     for (byte b = 0; b < this.choiceLimits.length; b++) {
/* 264 */       if (b != 0) {
/* 265 */         stringBuffer.append('|');
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 270 */       double d1 = previousDouble(this.choiceLimits[b]);
/* 271 */       double d2 = Math.abs(Math.IEEEremainder(this.choiceLimits[b], 1.0D));
/* 272 */       double d3 = Math.abs(Math.IEEEremainder(d1, 1.0D));
/*     */       
/* 274 */       if (d2 < d3) {
/* 275 */         stringBuffer.append("" + this.choiceLimits[b]);
/* 276 */         stringBuffer.append('#');
/*     */       } else {
/* 278 */         if (this.choiceLimits[b] == Double.POSITIVE_INFINITY) {
/* 279 */           stringBuffer.append("∞");
/* 280 */         } else if (this.choiceLimits[b] == Double.NEGATIVE_INFINITY) {
/* 281 */           stringBuffer.append("-∞");
/*     */         } else {
/* 283 */           stringBuffer.append("" + d1);
/*     */         } 
/* 285 */         stringBuffer.append('<');
/*     */       } 
/*     */ 
/*     */       
/* 289 */       String str = this.choiceFormats[b];
/*     */ 
/*     */ 
/*     */       
/* 293 */       boolean bool = (str.indexOf('<') >= 0 || str.indexOf('#') >= 0 || str.indexOf('≤') >= 0 || str.indexOf('|') >= 0) ? true : false;
/* 294 */       if (bool) stringBuffer.append('\''); 
/* 295 */       if (str.indexOf('\'') < 0) { stringBuffer.append(str); }
/*     */       else
/* 297 */       { for (byte b1 = 0; b1 < str.length(); b1++) {
/* 298 */           char c = str.charAt(b1);
/* 299 */           stringBuffer.append(c);
/* 300 */           if (c == '\'') stringBuffer.append(c); 
/*     */         }  }
/*     */       
/* 303 */       if (bool) stringBuffer.append('\''); 
/*     */     } 
/* 305 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChoiceFormat(String paramString) {
/* 315 */     applyPattern(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChoiceFormat(double[] paramArrayOfdouble, String[] paramArrayOfString) {
/* 326 */     setChoices(paramArrayOfdouble, paramArrayOfString);
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
/*     */   public void setChoices(double[] paramArrayOfdouble, String[] paramArrayOfString) {
/* 344 */     if (paramArrayOfdouble.length != paramArrayOfString.length) {
/* 345 */       throw new IllegalArgumentException("Array and limit arrays must be of the same length.");
/*     */     }
/*     */     
/* 348 */     this.choiceLimits = Arrays.copyOf(paramArrayOfdouble, paramArrayOfdouble.length);
/* 349 */     this.choiceFormats = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getLimits() {
/* 357 */     return Arrays.copyOf(this.choiceLimits, this.choiceLimits.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getFormats() {
/* 366 */     return Arrays.copyOf((Object[])this.choiceFormats, this.choiceFormats.length);
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
/*     */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/* 381 */     return format(paramLong, paramStringBuffer, paramFieldPosition);
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
/*     */   public StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*     */     byte b;
/* 394 */     for (b = 0; b < this.choiceLimits.length && 
/* 395 */       paramDouble >= this.choiceLimits[b]; b++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     b--;
/* 401 */     if (b < 0) b = 0;
/*     */     
/* 403 */     return paramStringBuffer.append(this.choiceFormats[b]);
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
/*     */   public Number parse(String paramString, ParsePosition paramParsePosition) {
/* 420 */     int i = paramParsePosition.index;
/* 421 */     int j = i;
/* 422 */     double d1 = Double.NaN;
/* 423 */     double d2 = 0.0D;
/* 424 */     for (byte b = 0; b < this.choiceFormats.length; b++) {
/* 425 */       String str = this.choiceFormats[b];
/* 426 */       if (paramString.regionMatches(i, str, 0, str.length())) {
/* 427 */         paramParsePosition.index = i + str.length();
/* 428 */         d2 = this.choiceLimits[b];
/* 429 */         if (paramParsePosition.index > j) {
/* 430 */           j = paramParsePosition.index;
/* 431 */           d1 = d2;
/* 432 */           if (j == paramString.length())
/*     */             break; 
/*     */         } 
/*     */       } 
/* 436 */     }  paramParsePosition.index = j;
/* 437 */     if (paramParsePosition.index == i) {
/* 438 */       paramParsePosition.errorIndex = j;
/*     */     }
/* 440 */     return new Double(d1);
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
/*     */   public static final double nextDouble(double paramDouble) {
/* 453 */     return nextDouble(paramDouble, true);
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
/*     */   public static final double previousDouble(double paramDouble) {
/* 465 */     return nextDouble(paramDouble, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 473 */     ChoiceFormat choiceFormat = (ChoiceFormat)super.clone();
/*     */     
/* 475 */     choiceFormat.choiceLimits = (double[])this.choiceLimits.clone();
/* 476 */     choiceFormat.choiceFormats = (String[])this.choiceFormats.clone();
/* 477 */     return choiceFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 484 */     int i = this.choiceLimits.length;
/* 485 */     if (this.choiceFormats.length > 0)
/*     */     {
/* 487 */       i ^= this.choiceFormats[this.choiceFormats.length - 1].hashCode();
/*     */     }
/* 489 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 496 */     if (paramObject == null) return false; 
/* 497 */     if (this == paramObject)
/* 498 */       return true; 
/* 499 */     if (getClass() != paramObject.getClass())
/* 500 */       return false; 
/* 501 */     ChoiceFormat choiceFormat = (ChoiceFormat)paramObject;
/* 502 */     return (Arrays.equals(this.choiceLimits, choiceFormat.choiceLimits) && 
/* 503 */       Arrays.equals((Object[])this.choiceFormats, (Object[])choiceFormat.choiceFormats));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 512 */     paramObjectInputStream.defaultReadObject();
/* 513 */     if (this.choiceLimits.length != this.choiceFormats.length) {
/* 514 */       throw new InvalidObjectException("limits and format arrays of different length.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double nextDouble(double paramDouble, boolean paramBoolean) {
/* 588 */     if (Double.isNaN(paramDouble)) {
/* 589 */       return paramDouble;
/*     */     }
/*     */ 
/*     */     
/* 593 */     if (paramDouble == 0.0D) {
/* 594 */       double d = Double.longBitsToDouble(1L);
/* 595 */       if (paramBoolean) {
/* 596 */         return d;
/*     */       }
/* 598 */       return -d;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 605 */     long l1 = Double.doubleToLongBits(paramDouble);
/*     */ 
/*     */     
/* 608 */     long l2 = l1 & Long.MAX_VALUE;
/*     */ 
/*     */     
/* 611 */     if (((l1 > 0L)) == paramBoolean) {
/* 612 */       if (l2 != 9218868437227405312L) {
/* 613 */         l2++;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 618 */       l2--;
/*     */     } 
/*     */ 
/*     */     
/* 622 */     long l3 = l1 & Long.MIN_VALUE;
/* 623 */     return Double.longBitsToDouble(l2 | l3);
/*     */   }
/*     */   
/*     */   private static double[] doubleArraySize(double[] paramArrayOfdouble) {
/* 627 */     int i = paramArrayOfdouble.length;
/* 628 */     double[] arrayOfDouble = new double[i * 2];
/* 629 */     System.arraycopy(paramArrayOfdouble, 0, arrayOfDouble, 0, i);
/* 630 */     return arrayOfDouble;
/*     */   }
/*     */   
/*     */   private String[] doubleArraySize(String[] paramArrayOfString) {
/* 634 */     int i = paramArrayOfString.length;
/* 635 */     String[] arrayOfString = new String[i * 2];
/* 636 */     System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, i);
/* 637 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/ChoiceFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */