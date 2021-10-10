/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MessageFormat
/*      */   extends Format
/*      */ {
/*      */   private static final long serialVersionUID = 6479157306784022952L;
/*      */   private Locale locale;
/*      */   private String pattern;
/*      */   private static final int INITIAL_FORMATS = 10;
/*      */   private Format[] formats;
/*      */   private int[] offsets;
/*      */   private int[] argumentNumbers;
/*      */   private int maxOffset;
/*      */   private static final int SEG_RAW = 0;
/*      */   private static final int SEG_INDEX = 1;
/*      */   private static final int SEG_TYPE = 2;
/*      */   private static final int SEG_MODIFIER = 3;
/*      */   private static final int TYPE_NULL = 0;
/*      */   private static final int TYPE_NUMBER = 1;
/*      */   private static final int TYPE_DATE = 2;
/*      */   private static final int TYPE_TIME = 3;
/*      */   private static final int TYPE_CHOICE = 4;
/*      */   
/*      */   public MessageFormat(String paramString) {
/* 1188 */     this.pattern = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1197 */     this.formats = new Format[10];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1204 */     this.offsets = new int[10];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1212 */     this.argumentNumbers = new int[10];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1221 */     this.maxOffset = -1; this.locale = Locale.getDefault(Locale.Category.FORMAT); applyPattern(paramString); } public void setLocale(Locale paramLocale) { this.locale = paramLocale; } public Locale getLocale() { return this.locale; } public void applyPattern(String paramString) { StringBuilder[] arrayOfStringBuilder = new StringBuilder[4]; arrayOfStringBuilder[0] = new StringBuilder(); byte b1 = 0; byte b2 = 0; boolean bool = false; byte b3 = 0; this.maxOffset = -1; for (byte b4 = 0; b4 < paramString.length(); b4++) { char c = paramString.charAt(b4); if (!b1) { if (c == '\'') { if (b4 + 1 < paramString.length() && paramString.charAt(b4 + 1) == '\'') { arrayOfStringBuilder[b1].append(c); b4++; } else { bool = !bool ? true : false; }  } else if (c == '{' && !bool) { b1 = 1; if (arrayOfStringBuilder[1] == null) arrayOfStringBuilder[1] = new StringBuilder();  } else { arrayOfStringBuilder[b1].append(c); }  } else if (bool) { arrayOfStringBuilder[b1].append(c); if (c == '\'') bool = false;  } else { switch (c) { case ',': if (b1 < 3) { if (arrayOfStringBuilder[++b1] == null) arrayOfStringBuilder[b1] = new StringBuilder();  break; }  arrayOfStringBuilder[b1].append(c); break;case '{': b3++; arrayOfStringBuilder[b1].append(c); break;case '}': if (b3 == 0) { b1 = 0; makeFormat(b4, b2, arrayOfStringBuilder); b2++; arrayOfStringBuilder[1] = null; arrayOfStringBuilder[2] = null; arrayOfStringBuilder[3] = null; break; }  b3--; arrayOfStringBuilder[b1].append(c); break;case ' ': if (b1 != 2 || arrayOfStringBuilder[2].length() > 0) arrayOfStringBuilder[b1].append(c);  break;case '\'': bool = true;default: arrayOfStringBuilder[b1].append(c); break; }  }  }  if (b3 == 0 && b1 != 0) { this.maxOffset = -1; throw new IllegalArgumentException("Unmatched braces in the pattern."); }  this.pattern = arrayOfStringBuilder[0].toString(); } public String toPattern() { int i = 0; StringBuilder stringBuilder = new StringBuilder(); for (byte b = 0; b <= this.maxOffset; b++) { copyAndFixQuotes(this.pattern, i, this.offsets[b], stringBuilder); i = this.offsets[b]; stringBuilder.append('{').append(this.argumentNumbers[b]); Format format = this.formats[b]; if (format != null) if (format instanceof NumberFormat) { if (format.equals(NumberFormat.getInstance(this.locale))) { stringBuilder.append(",number"); } else if (format.equals(NumberFormat.getCurrencyInstance(this.locale))) { stringBuilder.append(",number,currency"); } else if (format.equals(NumberFormat.getPercentInstance(this.locale))) { stringBuilder.append(",number,percent"); } else if (format.equals(NumberFormat.getIntegerInstance(this.locale))) { stringBuilder.append(",number,integer"); } else if (format instanceof DecimalFormat) { stringBuilder.append(",number,").append(((DecimalFormat)format).toPattern()); } else if (format instanceof ChoiceFormat) { stringBuilder.append(",choice,").append(((ChoiceFormat)format).toPattern()); }  } else if (format instanceof DateFormat) { byte b1; for (b1 = 0; b1 < DATE_TIME_MODIFIERS.length; b1++) { DateFormat dateFormat = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[b1], this.locale); if (format.equals(dateFormat)) { stringBuilder.append(",date"); break; }  dateFormat = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[b1], this.locale); if (format.equals(dateFormat)) { stringBuilder.append(",time"); break; }  }  if (b1 >= DATE_TIME_MODIFIERS.length) { if (format instanceof SimpleDateFormat) stringBuilder.append(",date,").append(((SimpleDateFormat)format).toPattern());  } else if (b1 != 0) { stringBuilder.append(',').append(DATE_TIME_MODIFIER_KEYWORDS[b1]); }  }   stringBuilder.append('}'); }  copyAndFixQuotes(this.pattern, i, this.pattern.length(), stringBuilder); return stringBuilder.toString(); } public void setFormatsByArgumentIndex(Format[] paramArrayOfFormat) { for (byte b = 0; b <= this.maxOffset; b++) { int i = this.argumentNumbers[b]; if (i < paramArrayOfFormat.length) this.formats[b] = paramArrayOfFormat[i];  }  } public MessageFormat(String paramString, Locale paramLocale) { this.pattern = ""; this.formats = new Format[10]; this.offsets = new int[10]; this.argumentNumbers = new int[10]; this.maxOffset = -1; this.locale = paramLocale; applyPattern(paramString); }
/*      */   public void setFormats(Format[] paramArrayOfFormat) { int i = paramArrayOfFormat.length; if (i > this.maxOffset + 1)
/*      */       i = this.maxOffset + 1;  for (byte b = 0; b < i; b++)
/*      */       this.formats[b] = paramArrayOfFormat[b];  }
/*      */   public void setFormatByArgumentIndex(int paramInt, Format paramFormat) { for (byte b = 0; b <= this.maxOffset; b++) {
/*      */       if (this.argumentNumbers[b] == paramInt)
/*      */         this.formats[b] = paramFormat; 
/*      */     }  }
/*      */   public void setFormat(int paramInt, Format paramFormat) { this.formats[paramInt] = paramFormat; }
/*      */   public Format[] getFormatsByArgumentIndex() { int i = -1; for (byte b1 = 0; b1 <= this.maxOffset; b1++) {
/*      */       if (this.argumentNumbers[b1] > i)
/*      */         i = this.argumentNumbers[b1]; 
/*      */     }  Format[] arrayOfFormat = new Format[i + 1];
/*      */     for (byte b2 = 0; b2 <= this.maxOffset; b2++)
/*      */       arrayOfFormat[this.argumentNumbers[b2]] = this.formats[b2]; 
/*      */     return arrayOfFormat; }
/*      */   public Format[] getFormats() { Format[] arrayOfFormat = new Format[this.maxOffset + 1];
/*      */     System.arraycopy(this.formats, 0, arrayOfFormat, 0, this.maxOffset + 1);
/* 1239 */     return arrayOfFormat; } private StringBuffer subformat(Object[] paramArrayOfObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition, List<AttributedCharacterIterator> paramList) { int i = 0;
/* 1240 */     int j = paramStringBuffer.length();
/* 1241 */     for (byte b = 0; b <= this.maxOffset; b++) {
/* 1242 */       paramStringBuffer.append(this.pattern.substring(i, this.offsets[b]));
/* 1243 */       i = this.offsets[b];
/* 1244 */       int k = this.argumentNumbers[b];
/* 1245 */       if (paramArrayOfObject == null || k >= paramArrayOfObject.length) {
/* 1246 */         paramStringBuffer.append('{').append(k).append('}');
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1254 */         Object object = paramArrayOfObject[k];
/* 1255 */         String str = null;
/* 1256 */         Format format = null;
/* 1257 */         if (object == null) {
/* 1258 */           str = "null";
/* 1259 */         } else if (this.formats[b] != null) {
/* 1260 */           format = this.formats[b];
/* 1261 */           if (format instanceof ChoiceFormat) {
/* 1262 */             str = this.formats[b].format(object);
/* 1263 */             if (str.indexOf('{') >= 0) {
/* 1264 */               format = new MessageFormat(str, this.locale);
/* 1265 */               object = paramArrayOfObject;
/* 1266 */               str = null;
/*      */             } 
/*      */           } 
/* 1269 */         } else if (object instanceof Number) {
/*      */           
/* 1271 */           format = NumberFormat.getInstance(this.locale);
/* 1272 */         } else if (object instanceof java.util.Date) {
/*      */           
/* 1274 */           format = DateFormat.getDateTimeInstance(3, 3, this.locale);
/*      */         }
/* 1276 */         else if (object instanceof String) {
/* 1277 */           str = (String)object;
/*      */         } else {
/*      */           
/* 1280 */           str = object.toString();
/* 1281 */           if (str == null) str = "null";
/*      */         
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1288 */         if (paramList != null) {
/*      */ 
/*      */           
/* 1291 */           if (j != paramStringBuffer.length()) {
/* 1292 */             paramList.add(
/* 1293 */                 createAttributedCharacterIterator(paramStringBuffer
/* 1294 */                   .substring(j)));
/* 1295 */             j = paramStringBuffer.length();
/*      */           } 
/* 1297 */           if (format != null) {
/*      */             
/* 1299 */             AttributedCharacterIterator attributedCharacterIterator = format.formatToCharacterIterator(object);
/*      */             
/* 1301 */             append(paramStringBuffer, attributedCharacterIterator);
/* 1302 */             if (j != paramStringBuffer.length()) {
/* 1303 */               paramList.add(
/* 1304 */                   createAttributedCharacterIterator(attributedCharacterIterator, Field.ARGUMENT, 
/*      */                     
/* 1306 */                     Integer.valueOf(k)));
/* 1307 */               j = paramStringBuffer.length();
/*      */             } 
/* 1309 */             str = null;
/*      */           } 
/* 1311 */           if (str != null && str.length() > 0) {
/* 1312 */             paramStringBuffer.append(str);
/* 1313 */             paramList.add(
/* 1314 */                 createAttributedCharacterIterator(str, Field.ARGUMENT, 
/*      */                   
/* 1316 */                   Integer.valueOf(k)));
/* 1317 */             j = paramStringBuffer.length();
/*      */           } 
/*      */         } else {
/*      */           
/* 1321 */           if (format != null) {
/* 1322 */             str = format.format(object);
/*      */           }
/* 1324 */           j = paramStringBuffer.length();
/* 1325 */           paramStringBuffer.append(str);
/* 1326 */           if (b == 0 && paramFieldPosition != null && Field.ARGUMENT.equals(paramFieldPosition
/* 1327 */               .getFieldAttribute())) {
/* 1328 */             paramFieldPosition.setBeginIndex(j);
/* 1329 */             paramFieldPosition.setEndIndex(paramStringBuffer.length());
/*      */           } 
/* 1331 */           j = paramStringBuffer.length();
/*      */         } 
/*      */       } 
/*      */     } 
/* 1335 */     paramStringBuffer.append(this.pattern.substring(i, this.pattern.length()));
/* 1336 */     if (paramList != null && j != paramStringBuffer.length()) {
/* 1337 */       paramList.add(createAttributedCharacterIterator(paramStringBuffer
/* 1338 */             .substring(j)));
/*      */     }
/* 1340 */     return paramStringBuffer; } public final StringBuffer format(Object[] paramArrayOfObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) { return subformat(paramArrayOfObject, paramStringBuffer, paramFieldPosition, (List<AttributedCharacterIterator>)null); } public static String format(String paramString, Object... paramVarArgs) { MessageFormat messageFormat = new MessageFormat(paramString); return messageFormat.format(paramVarArgs); } public final StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) { return subformat((Object[])paramObject, paramStringBuffer, paramFieldPosition, (List<AttributedCharacterIterator>)null); } public AttributedCharacterIterator formatToCharacterIterator(Object paramObject) { StringBuffer stringBuffer = new StringBuffer(); ArrayList<AttributedCharacterIterator> arrayList = new ArrayList(); if (paramObject == null) throw new NullPointerException("formatToCharacterIterator must be passed non-null object");  subformat((Object[])paramObject, stringBuffer, (FieldPosition)null, arrayList); if (arrayList.size() == 0) return createAttributedCharacterIterator("");  return createAttributedCharacterIterator(arrayList.<AttributedCharacterIterator>toArray(new AttributedCharacterIterator[arrayList.size()])); } public Object[] parse(String paramString, ParsePosition paramParsePosition) { if (paramString == null) return new Object[0];  int i = -1; for (byte b = 0; b <= this.maxOffset; b++) { if (this.argumentNumbers[b] > i) i = this.argumentNumbers[b];  }  Object[] arrayOfObject = new Object[i + 1]; int j = 0; int k = paramParsePosition.index; ParsePosition parsePosition = new ParsePosition(0); int m; for (m = 0; m <= this.maxOffset; m++) { int n = this.offsets[m] - j; if (n == 0 || this.pattern.regionMatches(j, paramString, k, n)) { k += n; j += n; } else { paramParsePosition.errorIndex = k; return null; }  if (this.formats[m] == null) { int i2, i1 = (m != this.maxOffset) ? this.offsets[m + 1] : this.pattern.length(); if (j >= i1) { i2 = paramString.length(); } else { i2 = paramString.indexOf(this.pattern.substring(j, i1), k); }  if (i2 < 0) { paramParsePosition.errorIndex = k; return null; }  String str = paramString.substring(k, i2); if (!str.equals("{" + this.argumentNumbers[m] + "}")) arrayOfObject[this.argumentNumbers[m]] = paramString.substring(k, i2);  k = i2; } else { parsePosition.index = k; arrayOfObject[this.argumentNumbers[m]] = this.formats[m].parseObject(paramString, parsePosition); if (parsePosition.index == k) { paramParsePosition.errorIndex = k; return null; }  k = parsePosition.index; }  }  m = this.pattern.length() - j; if (m == 0 || this.pattern.regionMatches(j, paramString, k, m)) { paramParsePosition.index = k + m; } else { paramParsePosition.errorIndex = k; return null; }  return arrayOfObject; } public Object[] parse(String paramString) throws ParseException { ParsePosition parsePosition = new ParsePosition(0); Object[] arrayOfObject = parse(paramString, parsePosition); if (parsePosition.index == 0) throw new ParseException("MessageFormat parse error!", parsePosition.errorIndex);  return arrayOfObject; } public Object parseObject(String paramString, ParsePosition paramParsePosition) { return parse(paramString, paramParsePosition); } public Object clone() { MessageFormat messageFormat = (MessageFormat)super.clone(); messageFormat.formats = (Format[])this.formats.clone(); for (byte b = 0; b < this.formats.length; b++) { if (this.formats[b] != null) messageFormat.formats[b] = (Format)this.formats[b].clone();  }  messageFormat.offsets = (int[])this.offsets.clone(); messageFormat.argumentNumbers = (int[])this.argumentNumbers.clone(); return messageFormat; }
/*      */   public boolean equals(Object paramObject) { if (this == paramObject) return true;  if (paramObject == null || getClass() != paramObject.getClass()) return false;  MessageFormat messageFormat = (MessageFormat)paramObject; return (this.maxOffset == messageFormat.maxOffset && this.pattern.equals(messageFormat.pattern) && ((this.locale != null && this.locale.equals(messageFormat.locale)) || (this.locale == null && messageFormat.locale == null)) && Arrays.equals(this.offsets, messageFormat.offsets) && Arrays.equals(this.argumentNumbers, messageFormat.argumentNumbers) && Arrays.equals((Object[])this.formats, (Object[])messageFormat.formats)); }
/*      */   public int hashCode() { return this.pattern.hashCode(); }
/*      */   public static class Field extends Format.Field {
/*      */     private static final long serialVersionUID = 7899943957617360810L;
/*      */     protected Field(String param1String) { super(param1String); }
/*      */     protected Object readResolve() throws InvalidObjectException { if (getClass() != Field.class) throw new InvalidObjectException("subclass didn't correctly implement readResolve");  return ARGUMENT; }
/*      */     public static final Field ARGUMENT = new Field("message argument field"); }
/* 1348 */   private void append(StringBuffer paramStringBuffer, CharacterIterator paramCharacterIterator) { if (paramCharacterIterator.first() != Character.MAX_VALUE) {
/*      */ 
/*      */       
/* 1351 */       paramStringBuffer.append(paramCharacterIterator.first()); char c;
/* 1352 */       while ((c = paramCharacterIterator.next()) != Character.MAX_VALUE) {
/* 1353 */         paramStringBuffer.append(c);
/*      */       }
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1371 */   private static final String[] TYPE_KEYWORDS = new String[] { "", "number", "date", "time", "choice" };
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_DEFAULT = 0;
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_CURRENCY = 1;
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_PERCENT = 2;
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_INTEGER = 3;
/*      */   
/* 1385 */   private static final String[] NUMBER_MODIFIER_KEYWORDS = new String[] { "", "currency", "percent", "integer" };
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_SHORT = 1;
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_MEDIUM = 2;
/*      */ 
/*      */   
/*      */   private static final int MODIFIER_LONG = 3;
/*      */   
/*      */   private static final int MODIFIER_FULL = 4;
/*      */   
/* 1398 */   private static final String[] DATE_TIME_MODIFIER_KEYWORDS = new String[] { "", "short", "medium", "long", "full" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1407 */   private static final int[] DATE_TIME_MODIFIERS = new int[] { 2, 3, 2, 1, 0 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void makeFormat(int paramInt1, int paramInt2, StringBuilder[] paramArrayOfStringBuilder) {
/* 1418 */     String[] arrayOfString = new String[paramArrayOfStringBuilder.length]; int i;
/* 1419 */     for (i = 0; i < paramArrayOfStringBuilder.length; i++) {
/* 1420 */       StringBuilder stringBuilder = paramArrayOfStringBuilder[i];
/* 1421 */       arrayOfString[i] = (stringBuilder != null) ? stringBuilder.toString() : "";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1427 */       i = Integer.parseInt(arrayOfString[1]);
/* 1428 */     } catch (NumberFormatException numberFormatException) {
/* 1429 */       throw new IllegalArgumentException("can't parse argument number: " + arrayOfString[1], numberFormatException);
/*      */     } 
/*      */     
/* 1432 */     if (i < 0) {
/* 1433 */       throw new IllegalArgumentException("negative argument number: " + i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1438 */     if (paramInt2 >= this.formats.length) {
/* 1439 */       int k = this.formats.length * 2;
/* 1440 */       Format[] arrayOfFormat = new Format[k];
/* 1441 */       int[] arrayOfInt1 = new int[k];
/* 1442 */       int[] arrayOfInt2 = new int[k];
/* 1443 */       System.arraycopy(this.formats, 0, arrayOfFormat, 0, this.maxOffset + 1);
/* 1444 */       System.arraycopy(this.offsets, 0, arrayOfInt1, 0, this.maxOffset + 1);
/* 1445 */       System.arraycopy(this.argumentNumbers, 0, arrayOfInt2, 0, this.maxOffset + 1);
/* 1446 */       this.formats = arrayOfFormat;
/* 1447 */       this.offsets = arrayOfInt1;
/* 1448 */       this.argumentNumbers = arrayOfInt2;
/*      */     } 
/* 1450 */     int j = this.maxOffset;
/* 1451 */     this.maxOffset = paramInt2;
/* 1452 */     this.offsets[paramInt2] = arrayOfString[0].length();
/* 1453 */     this.argumentNumbers[paramInt2] = i;
/*      */ 
/*      */     
/* 1456 */     NumberFormat numberFormat = null;
/* 1457 */     if (arrayOfString[2].length() != 0) {
/* 1458 */       int m, k = findKeyword(arrayOfString[2], TYPE_KEYWORDS);
/* 1459 */       switch (k) {
/*      */         case 0:
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 1466 */           switch (findKeyword(arrayOfString[3], NUMBER_MODIFIER_KEYWORDS)) {
/*      */             case 0:
/* 1468 */               numberFormat = NumberFormat.getInstance(this.locale);
/*      */               break;
/*      */             case 1:
/* 1471 */               numberFormat = NumberFormat.getCurrencyInstance(this.locale);
/*      */               break;
/*      */             case 2:
/* 1474 */               numberFormat = NumberFormat.getPercentInstance(this.locale);
/*      */               break;
/*      */             case 3:
/* 1477 */               numberFormat = NumberFormat.getIntegerInstance(this.locale);
/*      */               break;
/*      */           } 
/*      */           
/*      */           try {
/* 1482 */             numberFormat = new DecimalFormat(arrayOfString[3], DecimalFormatSymbols.getInstance(this.locale));
/* 1483 */           } catch (IllegalArgumentException illegalArgumentException) {
/* 1484 */             this.maxOffset = j;
/* 1485 */             throw illegalArgumentException;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/*      */         case 3:
/* 1493 */           m = findKeyword(arrayOfString[3], DATE_TIME_MODIFIER_KEYWORDS);
/* 1494 */           if (m >= 0 && m < DATE_TIME_MODIFIER_KEYWORDS.length) {
/* 1495 */             if (k == 2) {
/* 1496 */               DateFormat dateFormat1 = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[m], this.locale);
/*      */               break;
/*      */             } 
/* 1499 */             DateFormat dateFormat = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[m], this.locale);
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*      */           try {
/* 1505 */             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(arrayOfString[3], this.locale);
/* 1506 */           } catch (IllegalArgumentException illegalArgumentException) {
/* 1507 */             this.maxOffset = j;
/* 1508 */             throw illegalArgumentException;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/*      */           try {
/* 1516 */             numberFormat = new ChoiceFormat(arrayOfString[3]);
/* 1517 */           } catch (Exception exception) {
/* 1518 */             this.maxOffset = j;
/* 1519 */             throw new IllegalArgumentException("Choice Pattern incorrect: " + arrayOfString[3], exception);
/*      */           } 
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 1525 */           this.maxOffset = j;
/* 1526 */           throw new IllegalArgumentException("unknown format type: " + arrayOfString[2]);
/*      */       } 
/*      */     
/*      */     } 
/* 1530 */     this.formats[paramInt2] = numberFormat;
/*      */   }
/*      */   
/*      */   private static final int findKeyword(String paramString, String[] paramArrayOfString) {
/* 1534 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 1535 */       if (paramString.equals(paramArrayOfString[b])) {
/* 1536 */         return b;
/*      */       }
/*      */     } 
/*      */     
/* 1540 */     String str = paramString.trim().toLowerCase(Locale.ROOT);
/* 1541 */     if (str != paramString)
/* 1542 */       for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/* 1543 */         if (str.equals(paramArrayOfString[b1])) {
/* 1544 */           return b1;
/*      */         }
/*      */       }  
/* 1547 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static final void copyAndFixQuotes(String paramString, int paramInt1, int paramInt2, StringBuilder paramStringBuilder) {
/* 1552 */     boolean bool = false;
/*      */     
/* 1554 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1555 */       char c = paramString.charAt(i);
/* 1556 */       if (c == '{') {
/* 1557 */         if (!bool) {
/* 1558 */           paramStringBuilder.append('\'');
/* 1559 */           bool = true;
/*      */         } 
/* 1561 */         paramStringBuilder.append(c);
/* 1562 */       } else if (c == '\'') {
/* 1563 */         paramStringBuilder.append("''");
/*      */       } else {
/* 1565 */         if (bool) {
/* 1566 */           paramStringBuilder.append('\'');
/* 1567 */           bool = false;
/*      */         } 
/* 1569 */         paramStringBuilder.append(c);
/*      */       } 
/*      */     } 
/* 1572 */     if (bool) {
/* 1573 */       paramStringBuilder.append('\'');
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1583 */     paramObjectInputStream.defaultReadObject();
/* 1584 */     boolean bool = (this.maxOffset >= -1 && this.formats.length > this.maxOffset && this.offsets.length > this.maxOffset && this.argumentNumbers.length > this.maxOffset) ? true : false;
/*      */ 
/*      */ 
/*      */     
/* 1588 */     if (bool) {
/* 1589 */       int i = this.pattern.length() + 1;
/* 1590 */       for (int j = this.maxOffset; j >= 0; j--) {
/* 1591 */         if (this.offsets[j] < 0 || this.offsets[j] > i) {
/* 1592 */           bool = false;
/*      */           break;
/*      */         } 
/* 1595 */         i = this.offsets[j];
/*      */       } 
/*      */     } 
/*      */     
/* 1599 */     if (!bool)
/* 1600 */       throw new InvalidObjectException("Could not reconstruct MessageFormat from corrupt stream."); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/MessageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */