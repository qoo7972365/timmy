/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import javax.swing.JFormattedTextField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MaskFormatter
/*      */   extends DefaultFormatter
/*      */ {
/*      */   private static final char DIGIT_KEY = '#';
/*      */   private static final char LITERAL_KEY = '\'';
/*      */   private static final char UPPERCASE_KEY = 'U';
/*      */   private static final char LOWERCASE_KEY = 'L';
/*      */   private static final char ALPHA_NUMERIC_KEY = 'A';
/*      */   private static final char CHARACTER_KEY = '?';
/*      */   private static final char ANYTHING_KEY = '*';
/*      */   private static final char HEX_KEY = 'H';
/*  163 */   private static final MaskCharacter[] EmptyMaskChars = new MaskCharacter[0];
/*      */ 
/*      */ 
/*      */   
/*      */   private String mask;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient MaskCharacter[] maskChars;
/*      */ 
/*      */   
/*      */   private String validCharacters;
/*      */ 
/*      */   
/*      */   private String invalidCharacters;
/*      */ 
/*      */   
/*      */   private String placeholderString;
/*      */ 
/*      */   
/*      */   private char placeholder;
/*      */ 
/*      */   
/*      */   private boolean containsLiteralChars;
/*      */ 
/*      */ 
/*      */   
/*      */   public MaskFormatter() {
/*  191 */     setAllowsInvalid(false);
/*  192 */     this.containsLiteralChars = true;
/*  193 */     this.maskChars = EmptyMaskChars;
/*  194 */     this.placeholder = ' ';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MaskFormatter(String paramString) throws ParseException {
/*  205 */     this();
/*  206 */     setMask(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMask(String paramString) throws ParseException {
/*  217 */     this.mask = paramString;
/*  218 */     updateInternalMask();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMask() {
/*  227 */     return this.mask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidCharacters(String paramString) {
/*  241 */     this.validCharacters = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValidCharacters() {
/*  250 */     return this.validCharacters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInvalidCharacters(String paramString) {
/*  264 */     this.invalidCharacters = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getInvalidCharacters() {
/*  273 */     return this.invalidCharacters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaceholder(String paramString) {
/*  284 */     this.placeholderString = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlaceholder() {
/*  295 */     return this.placeholderString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaceholderCharacter(char paramChar) {
/*  310 */     this.placeholder = paramChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getPlaceholderCharacter() {
/*  321 */     return this.placeholder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValueContainsLiteralCharacters(boolean paramBoolean) {
/*  341 */     this.containsLiteralChars = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getValueContainsLiteralCharacters() {
/*  352 */     return this.containsLiteralChars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object stringToValue(String paramString) throws ParseException {
/*  371 */     return stringToValue(paramString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String valueToString(Object paramObject) throws ParseException {
/*  386 */     String str1 = (paramObject == null) ? "" : paramObject.toString();
/*  387 */     StringBuilder stringBuilder = new StringBuilder();
/*  388 */     String str2 = getPlaceholder();
/*  389 */     int[] arrayOfInt = { 0 };
/*      */     
/*  391 */     append(stringBuilder, str1, arrayOfInt, str2, this.maskChars);
/*  392 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void install(JFormattedTextField paramJFormattedTextField) {
/*  426 */     super.install(paramJFormattedTextField);
/*      */ 
/*      */     
/*  429 */     if (paramJFormattedTextField != null) {
/*  430 */       Object object = paramJFormattedTextField.getValue();
/*      */       
/*      */       try {
/*  433 */         stringToValue(valueToString(object));
/*  434 */       } catch (ParseException parseException) {
/*  435 */         setEditValid(false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object stringToValue(String paramString, boolean paramBoolean) throws ParseException {
/*      */     int i;
/*  450 */     if ((i = getInvalidOffset(paramString, paramBoolean)) == -1) {
/*  451 */       if (!getValueContainsLiteralCharacters()) {
/*  452 */         paramString = stripLiteralChars(paramString);
/*      */       }
/*  454 */       return super.stringToValue(paramString);
/*      */     } 
/*  456 */     throw new ParseException("stringToValue passed invalid value", i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getInvalidOffset(String paramString, boolean paramBoolean) {
/*  465 */     int i = paramString.length();
/*      */     
/*  467 */     if (i != getMaxLength())
/*      */     {
/*  469 */       return i; }  byte b;
/*      */     int j;
/*  471 */     for (b = 0, j = paramString.length(); b < j; b++) {
/*  472 */       char c = paramString.charAt(b);
/*      */       
/*  474 */       if (!isValidCharacter(b, c) && (paramBoolean || 
/*  475 */         !isPlaceholder(b, c))) {
/*  476 */         return b;
/*      */       }
/*      */     } 
/*  479 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void append(StringBuilder paramStringBuilder, String paramString1, int[] paramArrayOfint, String paramString2, MaskCharacter[] paramArrayOfMaskCharacter) throws ParseException {
/*  489 */     byte b = 0; int i = paramArrayOfMaskCharacter.length;
/*  490 */     for (; b < i; b++) {
/*  491 */       paramArrayOfMaskCharacter[b].append(paramStringBuilder, paramString1, paramArrayOfint, paramString2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateInternalMask() throws ParseException {
/*  499 */     String str = getMask();
/*  500 */     ArrayList<DigitMaskCharacter> arrayList1 = new ArrayList();
/*  501 */     ArrayList<DigitMaskCharacter> arrayList2 = arrayList1;
/*      */     
/*  503 */     if (str != null) {
/*  504 */       byte b = 0; int i = str.length();
/*  505 */       for (; b < i; b++) {
/*  506 */         char c = str.charAt(b);
/*      */         
/*  508 */         switch (c) {
/*      */           case '#':
/*  510 */             arrayList2.add(new DigitMaskCharacter());
/*      */             break;
/*      */           case '\'':
/*  513 */             if (++b < i) {
/*  514 */               c = str.charAt(b);
/*  515 */               arrayList2.add(new LiteralCharacter(c));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 'U':
/*  520 */             arrayList2.add(new UpperCaseCharacter());
/*      */             break;
/*      */           case 'L':
/*  523 */             arrayList2.add(new LowerCaseCharacter());
/*      */             break;
/*      */           case 'A':
/*  526 */             arrayList2.add(new AlphaNumericCharacter());
/*      */             break;
/*      */           case '?':
/*  529 */             arrayList2.add(new CharCharacter());
/*      */             break;
/*      */           case '*':
/*  532 */             arrayList2.add(new MaskCharacter());
/*      */             break;
/*      */           case 'H':
/*  535 */             arrayList2.add(new HexCharacter());
/*      */             break;
/*      */           default:
/*  538 */             arrayList2.add(new LiteralCharacter(c));
/*      */             break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  543 */     if (arrayList1.size() == 0) {
/*  544 */       this.maskChars = EmptyMaskChars;
/*      */     } else {
/*      */       
/*  547 */       this.maskChars = new MaskCharacter[arrayList1.size()];
/*  548 */       arrayList1.toArray(this.maskChars);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MaskCharacter getMaskCharacter(int paramInt) {
/*  556 */     if (paramInt >= this.maskChars.length) {
/*  557 */       return null;
/*      */     }
/*  559 */     return this.maskChars[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isPlaceholder(int paramInt, char paramChar) {
/*  566 */     return (getPlaceholderCharacter() == paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValidCharacter(int paramInt, char paramChar) {
/*  574 */     return getMaskCharacter(paramInt).isValidCharacter(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiteral(int paramInt) {
/*  582 */     return getMaskCharacter(paramInt).isLiteral();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMaxLength() {
/*  589 */     return this.maskChars.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char getLiteral(int paramInt) {
/*  596 */     return getMaskCharacter(paramInt).getChar(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char getCharacter(int paramInt, char paramChar) {
/*  606 */     return getMaskCharacter(paramInt).getChar(paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String stripLiteralChars(String paramString) {
/*  613 */     StringBuilder stringBuilder = null;
/*  614 */     int i = 0; byte b;
/*      */     int j;
/*  616 */     for (b = 0, j = paramString.length(); b < j; b++) {
/*  617 */       if (isLiteral(b)) {
/*  618 */         if (stringBuilder == null) {
/*  619 */           stringBuilder = new StringBuilder();
/*  620 */           if (b > 0) {
/*  621 */             stringBuilder.append(paramString.substring(0, b));
/*      */           }
/*  623 */           i = b + 1;
/*      */         }
/*  625 */         else if (i != b) {
/*  626 */           stringBuilder.append(paramString.substring(i, b));
/*      */         } 
/*  628 */         i = b + 1;
/*      */       } 
/*      */     } 
/*  631 */     if (stringBuilder == null)
/*      */     {
/*  633 */       return paramString;
/*      */     }
/*  635 */     if (i != paramString.length()) {
/*  636 */       if (stringBuilder == null) {
/*  637 */         return paramString.substring(i);
/*      */       }
/*  639 */       stringBuilder.append(paramString.substring(i));
/*      */     } 
/*  641 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  651 */     paramObjectInputStream.defaultReadObject();
/*      */     try {
/*  653 */       updateInternalMask();
/*  654 */     } catch (ParseException parseException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isNavigatable(int paramInt) {
/*  665 */     if (!getAllowsInvalid()) {
/*  666 */       return (paramInt < getMaxLength() && !isLiteral(paramInt));
/*      */     }
/*  668 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isValidEdit(DefaultFormatter.ReplaceHolder paramReplaceHolder) {
/*  679 */     if (!getAllowsInvalid()) {
/*  680 */       String str = getReplaceString(paramReplaceHolder.offset, paramReplaceHolder.length, paramReplaceHolder.text);
/*      */       
/*      */       try {
/*  683 */         paramReplaceHolder.value = stringToValue(str, false);
/*      */         
/*  685 */         return true;
/*  686 */       } catch (ParseException parseException) {
/*  687 */         return false;
/*      */       } 
/*      */     } 
/*  690 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean canReplace(DefaultFormatter.ReplaceHolder paramReplaceHolder) {
/*  719 */     if (!getAllowsInvalid()) {
/*  720 */       StringBuilder stringBuilder = null;
/*  721 */       String str = paramReplaceHolder.text;
/*  722 */       byte b1 = (str != null) ? str.length() : 0;
/*      */       
/*  724 */       if (!b1 && paramReplaceHolder.length == 1 && getFormattedTextField()
/*  725 */         .getSelectionStart() != paramReplaceHolder.offset)
/*      */       {
/*  727 */         while (paramReplaceHolder.offset > 0 && isLiteral(paramReplaceHolder.offset)) {
/*  728 */           paramReplaceHolder.offset--;
/*      */         }
/*      */       }
/*  731 */       int i = Math.min(getMaxLength() - paramReplaceHolder.offset, 
/*  732 */           Math.max(b1, paramReplaceHolder.length));
/*  733 */       for (byte b2 = 0, b3 = 0; b2 < i; b2++) {
/*  734 */         if (b3 < b1 && isValidCharacter(paramReplaceHolder.offset + b2, str
/*  735 */             .charAt(b3))) {
/*  736 */           char c = str.charAt(b3);
/*  737 */           if (c != getCharacter(paramReplaceHolder.offset + b2, c) && 
/*  738 */             stringBuilder == null) {
/*  739 */             stringBuilder = new StringBuilder();
/*  740 */             if (b3 > 0) {
/*  741 */               stringBuilder.append(str.substring(0, b3));
/*      */             }
/*      */           } 
/*      */           
/*  745 */           if (stringBuilder != null) {
/*  746 */             stringBuilder.append(getCharacter(paramReplaceHolder.offset + b2, c));
/*      */           }
/*      */           
/*  749 */           b3++;
/*      */         }
/*  751 */         else if (isLiteral(paramReplaceHolder.offset + b2)) {
/*  752 */           if (stringBuilder != null) {
/*  753 */             stringBuilder.append(getLiteral(paramReplaceHolder.offset + b2));
/*  754 */             if (b3 < b1) {
/*  755 */               i = Math.min(i + 1, getMaxLength() - paramReplaceHolder.offset);
/*      */             
/*      */             }
/*      */           }
/*  759 */           else if (b3 > 0) {
/*  760 */             stringBuilder = new StringBuilder(i);
/*  761 */             stringBuilder.append(str.substring(0, b3));
/*  762 */             stringBuilder.append(getLiteral(paramReplaceHolder.offset + b2));
/*  763 */             if (b3 < b1) {
/*      */               
/*  765 */               i = Math.min(i + 1, getMaxLength() - paramReplaceHolder.offset);
/*      */             
/*      */             }
/*  768 */             else if (paramReplaceHolder.cursorPosition == -1) {
/*  769 */               paramReplaceHolder.cursorPosition = paramReplaceHolder.offset + b2;
/*      */             } 
/*      */           } else {
/*      */             
/*  773 */             paramReplaceHolder.offset++;
/*  774 */             paramReplaceHolder.length--;
/*  775 */             b2--;
/*  776 */             i--;
/*      */           }
/*      */         
/*  779 */         } else if (b3 >= b1) {
/*      */           
/*  781 */           if (stringBuilder == null) {
/*  782 */             stringBuilder = new StringBuilder();
/*  783 */             if (str != null) {
/*  784 */               stringBuilder.append(str);
/*      */             }
/*      */           } 
/*  787 */           stringBuilder.append(getPlaceholderCharacter());
/*  788 */           if (b1 > 0 && paramReplaceHolder.cursorPosition == -1) {
/*  789 */             paramReplaceHolder.cursorPosition = paramReplaceHolder.offset + b2;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  794 */           return false;
/*      */         } 
/*      */       } 
/*  797 */       if (stringBuilder != null) {
/*  798 */         paramReplaceHolder.text = stringBuilder.toString();
/*      */       }
/*  800 */       else if (str != null && paramReplaceHolder.offset + b1 > getMaxLength()) {
/*  801 */         paramReplaceHolder.text = str.substring(0, getMaxLength() - paramReplaceHolder.offset);
/*      */       } 
/*  803 */       if (getOverwriteMode() && paramReplaceHolder.text != null) {
/*  804 */         paramReplaceHolder.length = paramReplaceHolder.text.length();
/*      */       }
/*      */     } 
/*  807 */     return super.canReplace(paramReplaceHolder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MaskCharacter
/*      */   {
/*      */     private MaskCharacter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLiteral() {
/*  821 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  833 */       if (isLiteral()) {
/*  834 */         return (getChar(param1Char) == param1Char);
/*      */       }
/*      */       
/*  837 */       param1Char = getChar(param1Char);
/*      */       
/*  839 */       String str = MaskFormatter.this.getValidCharacters();
/*      */       
/*  841 */       if (str != null && str.indexOf(param1Char) == -1) {
/*  842 */         return false;
/*      */       }
/*  844 */       str = MaskFormatter.this.getInvalidCharacters();
/*  845 */       if (str != null && str.indexOf(param1Char) != -1) {
/*  846 */         return false;
/*      */       }
/*  848 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char getChar(char param1Char) {
/*  858 */       return param1Char;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void append(StringBuilder param1StringBuilder, String param1String1, int[] param1ArrayOfint, String param1String2) throws ParseException {
/*  868 */       boolean bool1 = (param1ArrayOfint[0] < param1String1.length()) ? true : false;
/*  869 */       boolean bool2 = bool1 ? param1String1.charAt(param1ArrayOfint[0]) : false;
/*      */       
/*  871 */       if (isLiteral()) {
/*  872 */         param1StringBuilder.append(getChar(bool2));
/*  873 */         if (MaskFormatter.this.getValueContainsLiteralCharacters()) {
/*  874 */           if (bool1 && bool2 != getChar(bool2)) {
/*  875 */             throw new ParseException("Invalid character: " + bool2, param1ArrayOfint[0]);
/*      */           }
/*      */           
/*  878 */           param1ArrayOfint[0] = param1ArrayOfint[0] + 1;
/*      */         }
/*      */       
/*  881 */       } else if (param1ArrayOfint[0] >= param1String1.length()) {
/*  882 */         if (param1String2 != null && param1ArrayOfint[0] < param1String2.length()) {
/*  883 */           param1StringBuilder.append(param1String2.charAt(param1ArrayOfint[0]));
/*      */         } else {
/*      */           
/*  886 */           param1StringBuilder.append(MaskFormatter.this.getPlaceholderCharacter());
/*      */         } 
/*  888 */         param1ArrayOfint[0] = param1ArrayOfint[0] + 1;
/*      */       }
/*  890 */       else if (isValidCharacter(bool2)) {
/*  891 */         param1StringBuilder.append(getChar(bool2));
/*  892 */         param1ArrayOfint[0] = param1ArrayOfint[0] + 1;
/*      */       } else {
/*      */         
/*  895 */         throw new ParseException("Invalid character: " + bool2, param1ArrayOfint[0]);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class LiteralCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private char fixedChar;
/*      */ 
/*      */     
/*      */     public LiteralCharacter(char param1Char) {
/*  909 */       this.fixedChar = param1Char;
/*      */     }
/*      */     
/*      */     public boolean isLiteral() {
/*  913 */       return true;
/*      */     }
/*      */     
/*      */     public char getChar(char param1Char) {
/*  917 */       return this.fixedChar;
/*      */     }
/*      */   }
/*      */   
/*      */   private class DigitMaskCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private DigitMaskCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  927 */       return (Character.isDigit(param1Char) && super
/*  928 */         .isValidCharacter(param1Char));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class UpperCaseCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private UpperCaseCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  939 */       return (Character.isLetter(param1Char) && super
/*  940 */         .isValidCharacter(param1Char));
/*      */     }
/*      */     
/*      */     public char getChar(char param1Char) {
/*  944 */       return Character.toUpperCase(param1Char);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class LowerCaseCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private LowerCaseCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  955 */       return (Character.isLetter(param1Char) && super
/*  956 */         .isValidCharacter(param1Char));
/*      */     }
/*      */     
/*      */     public char getChar(char param1Char) {
/*  960 */       return Character.toLowerCase(param1Char);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class AlphaNumericCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private AlphaNumericCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  971 */       return (Character.isLetterOrDigit(param1Char) && super
/*  972 */         .isValidCharacter(param1Char));
/*      */     }
/*      */   }
/*      */   
/*      */   private class CharCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private CharCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  982 */       return (Character.isLetter(param1Char) && super
/*  983 */         .isValidCharacter(param1Char));
/*      */     }
/*      */   }
/*      */   
/*      */   private class HexCharacter
/*      */     extends MaskCharacter
/*      */   {
/*      */     private HexCharacter() {}
/*      */     
/*      */     public boolean isValidCharacter(char param1Char) {
/*  993 */       return ((param1Char == '0' || param1Char == '1' || param1Char == '2' || param1Char == '3' || param1Char == '4' || param1Char == '5' || param1Char == '6' || param1Char == '7' || param1Char == '8' || param1Char == '9' || param1Char == 'a' || param1Char == 'A' || param1Char == 'b' || param1Char == 'B' || param1Char == 'c' || param1Char == 'C' || param1Char == 'd' || param1Char == 'D' || param1Char == 'e' || param1Char == 'E' || param1Char == 'f' || param1Char == 'F') && super
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1004 */         .isValidCharacter(param1Char));
/*      */     }
/*      */     
/*      */     public char getChar(char param1Char) {
/* 1008 */       if (Character.isDigit(param1Char)) {
/* 1009 */         return param1Char;
/*      */       }
/* 1011 */       return Character.toUpperCase(param1Char);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/MaskFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */