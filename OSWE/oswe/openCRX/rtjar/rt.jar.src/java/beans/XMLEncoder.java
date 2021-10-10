/*     */ package java.beans;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEncoder
/*     */   extends Encoder
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final CharsetEncoder encoder;
/*     */   private final String charset;
/*     */   private final boolean declaration;
/*     */   private OutputStreamWriter out;
/*     */   private Object owner;
/* 215 */   private int indentation = 0;
/*     */   private boolean internal = false;
/*     */   private Map<Object, ValueData> valueToExpression;
/*     */   private Map<Object, List<Statement>> targetToStatementList;
/*     */   private boolean preambleWritten = false;
/*     */   private NameGenerator nameGenerator;
/*     */   
/*     */   private class ValueData {
/* 223 */     public int refs = 0;
/*     */     private ValueData() {}
/* 225 */     public boolean marked = false; public String name = null;
/* 226 */     public Expression exp = null;
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
/*     */   public XMLEncoder(OutputStream paramOutputStream) {
/* 242 */     this(paramOutputStream, "UTF-8", true, 0);
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
/*     */   public XMLEncoder(OutputStream paramOutputStream, String paramString, boolean paramBoolean, int paramInt) {
/* 278 */     if (paramOutputStream == null) {
/* 279 */       throw new IllegalArgumentException("the output stream cannot be null");
/*     */     }
/* 281 */     if (paramInt < 0) {
/* 282 */       throw new IllegalArgumentException("the indentation must be >= 0");
/*     */     }
/* 284 */     Charset charset = Charset.forName(paramString);
/* 285 */     this.encoder = charset.newEncoder();
/* 286 */     this.charset = paramString;
/* 287 */     this.declaration = paramBoolean;
/* 288 */     this.indentation = paramInt;
/* 289 */     this.out = new OutputStreamWriter(paramOutputStream, charset.newEncoder());
/* 290 */     this.valueToExpression = new IdentityHashMap<>();
/* 291 */     this.targetToStatementList = new IdentityHashMap<>();
/* 292 */     this.nameGenerator = new NameGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(Object paramObject) {
/* 303 */     this.owner = paramObject;
/* 304 */     writeExpression(new Expression(this, "getOwner", new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOwner() {
/* 315 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeObject(Object paramObject) {
/* 326 */     if (this.internal) {
/* 327 */       super.writeObject(paramObject);
/*     */     } else {
/*     */       
/* 330 */       writeStatement(new Statement(this, "writeObject", new Object[] { paramObject }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<Statement> statementList(Object paramObject) {
/* 335 */     List<Statement> list = this.targetToStatementList.get(paramObject);
/* 336 */     if (list == null) {
/* 337 */       list = new ArrayList();
/* 338 */       this.targetToStatementList.put(paramObject, list);
/*     */     } 
/* 340 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void mark(Object paramObject, boolean paramBoolean) {
/* 345 */     if (paramObject == null || paramObject == this) {
/*     */       return;
/*     */     }
/* 348 */     ValueData valueData = getValueData(paramObject);
/* 349 */     Expression expression = valueData.exp;
/*     */ 
/*     */     
/* 352 */     if (paramObject.getClass() == String.class && expression == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 357 */     if (paramBoolean) {
/* 358 */       valueData.refs++;
/*     */     }
/* 360 */     if (valueData.marked) {
/*     */       return;
/*     */     }
/* 363 */     valueData.marked = true;
/* 364 */     Object object = expression.getTarget();
/* 365 */     mark(expression);
/* 366 */     if (!(object instanceof Class)) {
/* 367 */       statementList(object).add(expression);
/*     */ 
/*     */       
/* 370 */       valueData.refs++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void mark(Statement paramStatement) {
/* 375 */     Object[] arrayOfObject = paramStatement.getArguments();
/* 376 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 377 */       Object object = arrayOfObject[b];
/* 378 */       mark(object, true);
/*     */     } 
/* 380 */     mark(paramStatement.getTarget(), paramStatement instanceof Expression);
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
/*     */   public void writeStatement(Statement paramStatement) {
/* 397 */     boolean bool = this.internal;
/* 398 */     this.internal = true;
/*     */     try {
/* 400 */       super.writeStatement(paramStatement);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 409 */       mark(paramStatement);
/* 410 */       Object object = paramStatement.getTarget();
/* 411 */       if (object instanceof Field) {
/* 412 */         String str = paramStatement.getMethodName();
/* 413 */         Object[] arrayOfObject = paramStatement.getArguments();
/* 414 */         if (str != null && arrayOfObject != null)
/*     */         {
/* 416 */           if (str.equals("get") && arrayOfObject.length == 1) {
/* 417 */             object = arrayOfObject[0];
/*     */           }
/* 419 */           else if (str.equals("set") && arrayOfObject.length == 2) {
/* 420 */             object = arrayOfObject[0];
/*     */           }  } 
/*     */       } 
/* 423 */       statementList(object).add(paramStatement);
/*     */     }
/* 425 */     catch (Exception exception) {
/* 426 */       getExceptionListener().exceptionThrown(new Exception("XMLEncoder: discarding statement " + paramStatement, exception));
/*     */     } 
/* 428 */     this.internal = bool;
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
/*     */   public void writeExpression(Expression paramExpression) {
/* 449 */     boolean bool = this.internal;
/* 450 */     this.internal = true;
/* 451 */     Object object = getValue(paramExpression);
/* 452 */     if (get(object) == null || (object instanceof String && !bool)) {
/* 453 */       (getValueData(object)).exp = paramExpression;
/* 454 */       super.writeExpression(paramExpression);
/*     */     } 
/* 456 */     this.internal = bool;
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
/*     */   public void flush() {
/* 468 */     if (!this.preambleWritten) {
/* 469 */       if (this.declaration) {
/* 470 */         writeln("<?xml version=" + quote("1.0") + " encoding=" + 
/* 471 */             quote(this.charset) + "?>");
/*     */       }
/* 473 */       writeln("<java version=" + quote(System.getProperty("java.version")) + " class=" + 
/* 474 */           quote(XMLDecoder.class.getName()) + ">");
/* 475 */       this.preambleWritten = true;
/*     */     } 
/* 477 */     this.indentation++;
/* 478 */     List<Statement> list = statementList(this);
/* 479 */     while (!list.isEmpty()) {
/* 480 */       Statement statement1 = list.remove(0);
/* 481 */       if ("writeObject".equals(statement1.getMethodName())) {
/* 482 */         outputValue(statement1.getArguments()[0], this, true);
/*     */         continue;
/*     */       } 
/* 485 */       outputStatement(statement1, this, false);
/*     */     } 
/*     */     
/* 488 */     this.indentation--;
/*     */     
/* 490 */     Statement statement = getMissedStatement();
/* 491 */     while (statement != null) {
/* 492 */       outputStatement(statement, this, false);
/* 493 */       statement = getMissedStatement();
/*     */     } 
/*     */     
/*     */     try {
/* 497 */       this.out.flush();
/*     */     }
/* 499 */     catch (IOException iOException) {
/* 500 */       getExceptionListener().exceptionThrown(iOException);
/*     */     } 
/* 502 */     clear();
/*     */   }
/*     */   
/*     */   void clear() {
/* 506 */     super.clear();
/* 507 */     this.nameGenerator.clear();
/* 508 */     this.valueToExpression.clear();
/* 509 */     this.targetToStatementList.clear();
/*     */   }
/*     */   
/*     */   Statement getMissedStatement() {
/* 513 */     for (List<Statement> list : this.targetToStatementList.values()) {
/* 514 */       for (byte b = 0; b < list.size(); b++) {
/* 515 */         if (Statement.class == ((Statement)list.get(b)).getClass()) {
/* 516 */           return list.remove(b);
/*     */         }
/*     */       } 
/*     */     } 
/* 520 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 530 */     flush();
/* 531 */     writeln("</java>");
/*     */     try {
/* 533 */       this.out.close();
/*     */     }
/* 535 */     catch (IOException iOException) {
/* 536 */       getExceptionListener().exceptionThrown(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String quote(String paramString) {
/* 541 */     return "\"" + paramString + "\"";
/*     */   }
/*     */   
/*     */   private ValueData getValueData(Object paramObject) {
/* 545 */     ValueData valueData = this.valueToExpression.get(paramObject);
/* 546 */     if (valueData == null) {
/* 547 */       valueData = new ValueData();
/* 548 */       this.valueToExpression.put(paramObject, valueData);
/*     */     } 
/* 550 */     return valueData;
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
/*     */   private static boolean isValidCharCode(int paramInt) {
/* 573 */     return ((32 <= paramInt && paramInt <= 55295) || 10 == paramInt || 9 == paramInt || 13 == paramInt || (57344 <= paramInt && paramInt <= 65533) || (65536 <= paramInt && paramInt <= 1114111));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeln(String paramString) {
/*     */     try {
/* 583 */       StringBuilder stringBuilder = new StringBuilder();
/* 584 */       for (byte b = 0; b < this.indentation; b++) {
/* 585 */         stringBuilder.append(' ');
/*     */       }
/* 587 */       stringBuilder.append(paramString);
/* 588 */       stringBuilder.append('\n');
/* 589 */       this.out.write(stringBuilder.toString());
/*     */     }
/* 591 */     catch (IOException iOException) {
/* 592 */       getExceptionListener().exceptionThrown(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void outputValue(Object paramObject1, Object paramObject2, boolean paramBoolean) {
/* 597 */     if (paramObject1 == null) {
/* 598 */       writeln("<null/>");
/*     */       
/*     */       return;
/*     */     } 
/* 602 */     if (paramObject1 instanceof Class) {
/* 603 */       writeln("<class>" + ((Class)paramObject1).getName() + "</class>");
/*     */       
/*     */       return;
/*     */     } 
/* 607 */     ValueData valueData = getValueData(paramObject1);
/* 608 */     if (valueData.exp != null) {
/* 609 */       Object object = valueData.exp.getTarget();
/* 610 */       String str = valueData.exp.getMethodName();
/*     */       
/* 612 */       if (object == null || str == null) {
/* 613 */         throw new NullPointerException(((object == null) ? "target" : "methodName") + " should not be null");
/*     */       }
/*     */ 
/*     */       
/* 617 */       if (paramBoolean && object instanceof Field && str.equals("get")) {
/* 618 */         Field field = (Field)object;
/* 619 */         writeln("<object class=" + quote(field.getDeclaringClass().getName()) + " field=" + 
/* 620 */             quote(field.getName()) + "/>");
/*     */         
/*     */         return;
/*     */       } 
/* 624 */       Class<char> clazz = primitiveTypeFor(paramObject1.getClass());
/* 625 */       if (clazz != null && object == paramObject1.getClass() && str
/* 626 */         .equals("new")) {
/* 627 */         String str1 = clazz.getName();
/*     */         
/* 629 */         if (clazz == char.class) {
/* 630 */           char c = ((Character)paramObject1).charValue();
/* 631 */           if (!isValidCharCode(c)) {
/* 632 */             writeln(createString(c));
/*     */             return;
/*     */           } 
/* 635 */           paramObject1 = quoteCharCode(c);
/* 636 */           if (paramObject1 == null) {
/* 637 */             paramObject1 = Character.valueOf(c);
/*     */           }
/*     */         } 
/* 640 */         writeln("<" + str1 + ">" + paramObject1 + "</" + str1 + ">");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 645 */     } else if (paramObject1 instanceof String) {
/* 646 */       writeln(createString((String)paramObject1));
/*     */       
/*     */       return;
/*     */     } 
/* 650 */     if (valueData.name != null) {
/* 651 */       if (paramBoolean) {
/* 652 */         writeln("<object idref=" + quote(valueData.name) + "/>");
/*     */       } else {
/*     */         
/* 655 */         outputXML("void", " idref=" + quote(valueData.name), paramObject1, new Object[0]);
/*     */       }
/*     */     
/* 658 */     } else if (valueData.exp != null) {
/* 659 */       outputStatement(valueData.exp, paramObject2, paramBoolean);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String quoteCharCode(int paramInt) {
/* 664 */     switch (paramInt) { case 38:
/* 665 */         return "&amp;";
/* 666 */       case 60: return "&lt;";
/* 667 */       case 62: return "&gt;";
/* 668 */       case 34: return "&quot;";
/* 669 */       case 39: return "&apos;";
/* 670 */       case 13: return "&#13;"; }
/* 671 */      return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String createString(int paramInt) {
/* 676 */     return "<char code=\"#" + Integer.toString(paramInt, 16) + "\"/>";
/*     */   }
/*     */   
/*     */   private String createString(String paramString) {
/* 680 */     StringBuilder stringBuilder = new StringBuilder();
/* 681 */     stringBuilder.append("<string>");
/* 682 */     int i = 0;
/* 683 */     while (i < paramString.length()) {
/* 684 */       int j = paramString.codePointAt(i);
/* 685 */       int k = Character.charCount(j);
/*     */       
/* 687 */       if (isValidCharCode(j) && this.encoder.canEncode(paramString.substring(i, i + k))) {
/* 688 */         String str = quoteCharCode(j);
/* 689 */         if (str != null) {
/* 690 */           stringBuilder.append(str);
/*     */         } else {
/* 692 */           stringBuilder.appendCodePoint(j);
/*     */         } 
/* 694 */         i += k; continue;
/*     */       } 
/* 696 */       stringBuilder.append(createString(paramString.charAt(i)));
/* 697 */       i++;
/*     */     } 
/*     */     
/* 700 */     stringBuilder.append("</string>");
/* 701 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void outputStatement(Statement paramStatement, Object paramObject, boolean paramBoolean) {
/* 705 */     Object object1 = paramStatement.getTarget();
/* 706 */     String str1 = paramStatement.getMethodName();
/*     */     
/* 708 */     if (object1 == null || str1 == null) {
/* 709 */       throw new NullPointerException(((object1 == null) ? "target" : "methodName") + " should not be null");
/*     */     }
/*     */ 
/*     */     
/* 713 */     Object[] arrayOfObject = paramStatement.getArguments();
/* 714 */     boolean bool = (paramStatement.getClass() == Expression.class) ? true : false;
/* 715 */     Object object2 = bool ? getValue((Expression)paramStatement) : null;
/*     */     
/* 717 */     String str2 = (bool && paramBoolean) ? "object" : "void";
/* 718 */     String str3 = "";
/* 719 */     ValueData valueData = getValueData(object2);
/*     */ 
/*     */     
/* 722 */     if (object1 != paramObject)
/*     */     {
/* 724 */       if (object1 == Array.class && str1.equals("newInstance")) {
/* 725 */         str2 = "array";
/* 726 */         str3 = str3 + " class=" + quote(((Class)arrayOfObject[0]).getName());
/* 727 */         str3 = str3 + " length=" + quote(arrayOfObject[1].toString());
/* 728 */         arrayOfObject = new Object[0];
/*     */       }
/* 730 */       else if (object1.getClass() == Class.class) {
/* 731 */         str3 = str3 + " class=" + quote(((Class)object1).getName());
/*     */       } else {
/*     */         
/* 734 */         valueData.refs = 2;
/* 735 */         if (valueData.name == null) {
/* 736 */           (getValueData(object1)).refs++;
/* 737 */           List<Statement> list = statementList(object1);
/* 738 */           if (!list.contains(paramStatement)) {
/* 739 */             list.add(paramStatement);
/*     */           }
/* 741 */           outputValue(object1, paramObject, false);
/*     */         } 
/* 743 */         if (bool)
/* 744 */           outputValue(object2, paramObject, paramBoolean); 
/*     */         return;
/*     */       } 
/*     */     }
/* 748 */     if (bool && valueData.refs > 1) {
/* 749 */       String str = this.nameGenerator.instanceName(object2);
/* 750 */       valueData.name = str;
/* 751 */       str3 = str3 + " id=" + quote(str);
/*     */     } 
/*     */ 
/*     */     
/* 755 */     if ((!bool && str1.equals("set") && arrayOfObject.length == 2 && arrayOfObject[0] instanceof Integer) || (bool && str1
/*     */       
/* 757 */       .equals("get") && arrayOfObject.length == 1 && arrayOfObject[0] instanceof Integer)) {
/*     */       
/* 759 */       str3 = str3 + " index=" + quote(arrayOfObject[0].toString());
/* 760 */       (new Object[1])[0] = arrayOfObject[1]; arrayOfObject = (arrayOfObject.length == 1) ? new Object[0] : new Object[1];
/*     */     }
/* 762 */     else if ((!bool && str1.startsWith("set") && arrayOfObject.length == 1) || (bool && str1
/* 763 */       .startsWith("get") && arrayOfObject.length == 0)) {
/* 764 */       if (3 < str1.length())
/*     */       {
/* 766 */         str3 = str3 + " property=" + quote(Introspector.decapitalize(str1.substring(3)));
/*     */       }
/*     */     }
/* 769 */     else if (!str1.equals("new") && !str1.equals("newInstance")) {
/* 770 */       str3 = str3 + " method=" + quote(str1);
/*     */     } 
/* 772 */     outputXML(str2, str3, object2, arrayOfObject);
/*     */   }
/*     */   
/*     */   private void outputXML(String paramString1, String paramString2, Object paramObject, Object... paramVarArgs) {
/* 776 */     List<Statement> list = statementList(paramObject);
/*     */     
/* 778 */     if (paramVarArgs.length == 0 && list.size() == 0) {
/* 779 */       writeln("<" + paramString1 + paramString2 + "/>");
/*     */       
/*     */       return;
/*     */     } 
/* 783 */     writeln("<" + paramString1 + paramString2 + ">");
/* 784 */     this.indentation++;
/*     */     
/* 786 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 787 */       outputValue(paramVarArgs[b], (Object)null, true);
/*     */     }
/*     */     
/* 790 */     while (!list.isEmpty()) {
/* 791 */       Statement statement = list.remove(0);
/* 792 */       outputStatement(statement, paramObject, false);
/*     */     } 
/*     */     
/* 795 */     this.indentation--;
/* 796 */     writeln("</" + paramString1 + ">");
/*     */   }
/*     */ 
/*     */   
/*     */   static Class primitiveTypeFor(Class<Boolean> paramClass) {
/* 801 */     if (paramClass == Boolean.class) return boolean.class; 
/* 802 */     if (paramClass == Byte.class) return byte.class; 
/* 803 */     if (paramClass == Character.class) return char.class; 
/* 804 */     if (paramClass == Short.class) return short.class; 
/* 805 */     if (paramClass == Integer.class) return int.class; 
/* 806 */     if (paramClass == Long.class) return long.class; 
/* 807 */     if (paramClass == Float.class) return float.class; 
/* 808 */     if (paramClass == Double.class) return double.class; 
/* 809 */     if (paramClass == Void.class) return void.class; 
/* 810 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/XMLEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */