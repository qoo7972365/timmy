/*      */ package javax.management;
/*      */ 
/*      */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.security.AccessController;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectName
/*      */   implements Comparable<ObjectName>, QueryExp
/*      */ {
/*      */   private static final long oldSerialVersionUID = -5467795090068647408L;
/*      */   private static final long newSerialVersionUID = 1081892073854801359L;
/*      */   
/*      */   private static class Property
/*      */   {
/*      */     int _key_index;
/*      */     int _key_length;
/*      */     int _value_length;
/*      */     
/*      */     Property(int param1Int1, int param1Int2, int param1Int3) {
/*  239 */       this._key_index = param1Int1;
/*  240 */       this._key_length = param1Int2;
/*  241 */       this._value_length = param1Int3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setKeyIndex(int param1Int) {
/*  248 */       this._key_index = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getKeyString(String param1String) {
/*  255 */       return param1String.substring(this._key_index, this._key_index + this._key_length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getValueString(String param1String) {
/*  262 */       int i = this._key_index + this._key_length + 1;
/*  263 */       int j = i + this._value_length;
/*  264 */       return param1String.substring(i, j);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PatternProperty
/*      */     extends Property
/*      */   {
/*      */     PatternProperty(int param1Int1, int param1Int2, int param1Int3) {
/*  276 */       super(param1Int1, param1Int2, param1Int3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("domain", String.class), new ObjectStreamField("propertyList", Hashtable.class), new ObjectStreamField("propertyListString", String.class), new ObjectStreamField("canonicalName", String.class), new ObjectStreamField("pattern", boolean.class), new ObjectStreamField("propertyPattern", boolean.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  312 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[0];
/*      */   
/*      */   private static final long serialVersionUID;
/*      */   private static final ObjectStreamField[] serialPersistentFields;
/*      */   private static boolean compat = false;
/*      */   
/*      */   static {
/*      */     try {
/*  320 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  321 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  322 */       compat = (str != null && str.equals("1.0"));
/*  323 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  326 */     if (compat) {
/*  327 */       serialPersistentFields = oldSerialPersistentFields;
/*  328 */       serialVersionUID = -5467795090068647408L;
/*      */     } else {
/*  330 */       serialPersistentFields = newSerialPersistentFields;
/*  331 */       serialVersionUID = 1081892073854801359L;
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
/*  343 */   private static final Property[] _Empty_property_array = new Property[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String _canonicalName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Property[] _kp_array;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Property[] _ca_array;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  370 */   private transient int _domain_length = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Map<String, String> _propertyList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean _domain_pattern = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean _property_list_pattern = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean _property_value_pattern = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void construct(String paramString) throws MalformedObjectNameException {
/*  420 */     if (paramString == null) {
/*  421 */       throw new NullPointerException("name cannot be null");
/*      */     }
/*      */     
/*  424 */     if (paramString.length() == 0) {
/*      */       
/*  426 */       this._canonicalName = "*:*";
/*  427 */       this._kp_array = _Empty_property_array;
/*  428 */       this._ca_array = _Empty_property_array;
/*  429 */       this._domain_length = 1;
/*  430 */       this._propertyList = null;
/*  431 */       this._domain_pattern = true;
/*  432 */       this._property_list_pattern = true;
/*  433 */       this._property_value_pattern = false;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  438 */     char[] arrayOfChar1 = paramString.toCharArray();
/*  439 */     int i = arrayOfChar1.length;
/*  440 */     char[] arrayOfChar2 = new char[i];
/*      */     
/*  442 */     int j = 0;
/*  443 */     byte b1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  448 */     while (b1 < i) {
/*  449 */       byte b; switch (arrayOfChar1[b1]) {
/*      */         case ':':
/*  451 */           this._domain_length = b1++;
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case '=':
/*  461 */           b = ++b1;
/*  462 */           while (b < i && arrayOfChar1[b++] != ':') {
/*  463 */             if (b == i)
/*  464 */               throw new MalformedObjectNameException("Domain part must be specified"); 
/*      */           } 
/*      */           continue;
/*      */         case '\n':
/*  468 */           throw new MalformedObjectNameException("Invalid character '\\n' in domain name");
/*      */         
/*      */         case '*':
/*      */         case '?':
/*  472 */           this._domain_pattern = true;
/*  473 */           b1++;
/*      */           continue;
/*      */       } 
/*  476 */       b1++;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  482 */     if (b1 == i) {
/*  483 */       throw new MalformedObjectNameException("Key properties cannot be empty");
/*      */     }
/*      */ 
/*      */     
/*  487 */     System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, this._domain_length);
/*  488 */     arrayOfChar2[this._domain_length] = ':';
/*  489 */     j = this._domain_length + 1;
/*      */ 
/*      */ 
/*      */     
/*  493 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  497 */     byte b2 = 0;
/*      */ 
/*      */ 
/*      */     
/*  501 */     String[] arrayOfString = new String[10];
/*  502 */     this._kp_array = new Property[10];
/*  503 */     this._property_list_pattern = false;
/*  504 */     this._property_value_pattern = false;
/*      */     
/*  506 */     while (b1 < i) {
/*  507 */       Property property; boolean bool1; int m; char c1 = arrayOfChar1[b1];
/*      */ 
/*      */       
/*  510 */       if (c1 == '*') {
/*  511 */         if (this._property_list_pattern) {
/*  512 */           throw new MalformedObjectNameException("Cannot have several '*' characters in pattern property list");
/*      */         }
/*      */ 
/*      */         
/*  516 */         this._property_list_pattern = true;
/*  517 */         if (++b1 < i && arrayOfChar1[b1] != ',') {
/*  518 */           throw new MalformedObjectNameException("Invalid character found after '*': end of name or ',' expected");
/*      */         }
/*      */         
/*  521 */         if (b1 == i) {
/*  522 */           if (!b2) {
/*      */             
/*  524 */             this._kp_array = _Empty_property_array;
/*  525 */             this._ca_array = _Empty_property_array;
/*  526 */             this._propertyList = Collections.emptyMap();
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  531 */         b1++;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  538 */       byte b3 = b1;
/*  539 */       byte b4 = b3;
/*  540 */       if (arrayOfChar1[b3] == '=')
/*  541 */         throw new MalformedObjectNameException("Invalid key (empty)");  char c2;
/*  542 */       while (b3 < i && (c2 = arrayOfChar1[b3++]) != '=') {
/*  543 */         String str1; switch (c2) {
/*      */           
/*      */           case '\n':
/*      */           case '*':
/*      */           case ',':
/*      */           case ':':
/*      */           case '?':
/*  550 */             str1 = (c2 == '\n') ? "\\n" : ("" + c2);
/*  551 */             throw new MalformedObjectNameException("Invalid character '" + str1 + "' in key part of property");
/*      */         } 
/*      */       
/*      */       } 
/*  555 */       if (arrayOfChar1[b3 - 1] != '=') {
/*  556 */         throw new MalformedObjectNameException("Unterminated key property part");
/*      */       }
/*  558 */       byte b5 = b3;
/*  559 */       int k = b5 - b4 - 1;
/*      */ 
/*      */       
/*  562 */       boolean bool2 = false;
/*  563 */       if (b3 < i && arrayOfChar1[b3] == '"') {
/*  564 */         bool1 = true;
/*      */ 
/*      */         
/*  567 */         while (++b3 < i && (c2 = arrayOfChar1[b3]) != '"') {
/*      */ 
/*      */           
/*  570 */           if (c2 == '\\') {
/*  571 */             if (++b3 == i) {
/*  572 */               throw new MalformedObjectNameException("Unterminated quoted value");
/*      */             }
/*  574 */             switch (c2 = arrayOfChar1[b3]) {
/*      */               case '"':
/*      */               case '*':
/*      */               case '?':
/*      */               case '\\':
/*      */               case 'n':
/*      */                 continue;
/*      */             } 
/*  582 */             throw new MalformedObjectNameException("Invalid escape sequence '\\" + c2 + "' in quoted value");
/*      */           } 
/*      */ 
/*      */           
/*  586 */           if (c2 == '\n') {
/*  587 */             throw new MalformedObjectNameException("Newline in quoted value");
/*      */           }
/*      */           
/*  590 */           switch (c2) {
/*      */             case '*':
/*      */             case '?':
/*  593 */               bool2 = true;
/*      */           } 
/*      */ 
/*      */         
/*      */         } 
/*  598 */         if (b3 == i) {
/*  599 */           throw new MalformedObjectNameException("Unterminated quoted value");
/*      */         }
/*  601 */         m = ++b3 - b5;
/*      */       } else {
/*      */         
/*  604 */         bool1 = false;
/*  605 */         while (b3 < i && (c2 = arrayOfChar1[b3]) != ',') {
/*  606 */           String str1; switch (c2) {
/*      */             
/*      */             case '*':
/*      */             case '?':
/*  610 */               bool2 = true;
/*  611 */               b3++;
/*      */               continue;
/*      */             case '\n':
/*      */             case '"':
/*      */             case ':':
/*      */             case '=':
/*  617 */               str1 = (c2 == '\n') ? "\\n" : ("" + c2);
/*  618 */               throw new MalformedObjectNameException("Invalid character '" + str1 + "' in value part of property");
/*      */           } 
/*      */ 
/*      */           
/*  622 */           b3++;
/*      */         } 
/*      */         
/*  625 */         m = b3 - b5;
/*      */       } 
/*      */ 
/*      */       
/*  629 */       if (b3 == i - 1) {
/*  630 */         if (bool1) {
/*  631 */           throw new MalformedObjectNameException("Invalid ending character `" + arrayOfChar1[b3] + "'");
/*      */         }
/*      */         
/*  634 */         throw new MalformedObjectNameException("Invalid ending comma");
/*      */       } 
/*  636 */       b3++;
/*      */ 
/*      */       
/*  639 */       if (!bool2) {
/*  640 */         property = new Property(b4, k, m);
/*      */       } else {
/*  642 */         this._property_value_pattern = true;
/*  643 */         property = new PatternProperty(b4, k, m);
/*      */       } 
/*  645 */       String str = paramString.substring(b4, b4 + k);
/*      */       
/*  647 */       if (b2 == arrayOfString.length) {
/*  648 */         String[] arrayOfString1 = new String[b2 + 10];
/*  649 */         System.arraycopy(arrayOfString, 0, arrayOfString1, 0, b2);
/*  650 */         arrayOfString = arrayOfString1;
/*      */       } 
/*  652 */       arrayOfString[b2] = str;
/*      */       
/*  654 */       addProperty(property, b2, (Map)hashMap, str);
/*  655 */       b2++;
/*  656 */       b1 = b3;
/*      */     } 
/*      */ 
/*      */     
/*  660 */     setCanonicalName(arrayOfChar1, arrayOfChar2, arrayOfString, (Map)hashMap, j, b2);
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
/*      */   private void construct(String paramString, Map<String, String> paramMap) throws MalformedObjectNameException {
/*  680 */     if (paramString == null) {
/*  681 */       throw new NullPointerException("domain cannot be null");
/*      */     }
/*      */     
/*  684 */     if (paramMap == null) {
/*  685 */       throw new NullPointerException("key property list cannot be null");
/*      */     }
/*      */     
/*  688 */     if (paramMap.isEmpty()) {
/*  689 */       throw new MalformedObjectNameException("key property list cannot be empty");
/*      */     }
/*      */ 
/*      */     
/*  693 */     if (!isDomain(paramString)) {
/*  694 */       throw new MalformedObjectNameException("Invalid domain: " + paramString);
/*      */     }
/*      */     
/*  697 */     StringBuilder stringBuilder = new StringBuilder();
/*  698 */     stringBuilder.append(paramString).append(':');
/*  699 */     this._domain_length = paramString.length();
/*      */ 
/*      */     
/*  702 */     int i = paramMap.size();
/*  703 */     this._kp_array = new Property[i];
/*      */     
/*  705 */     String[] arrayOfString = new String[i];
/*  706 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/*  709 */     byte b = 0;
/*  710 */     for (Map.Entry<String, String> entry : paramMap.entrySet()) {
/*  711 */       Property property; String str2; if (stringBuilder.length() > 0)
/*  712 */         stringBuilder.append(","); 
/*  713 */       String str1 = (String)entry.getKey();
/*      */       
/*      */       try {
/*  716 */         str2 = (String)entry.getValue();
/*  717 */       } catch (ClassCastException classCastException) {
/*  718 */         throw new MalformedObjectNameException(classCastException.getMessage());
/*      */       } 
/*  720 */       int k = stringBuilder.length();
/*  721 */       checkKey(str1);
/*  722 */       stringBuilder.append(str1);
/*  723 */       arrayOfString[b] = str1;
/*  724 */       stringBuilder.append("=");
/*  725 */       boolean bool = checkValue(str2);
/*  726 */       stringBuilder.append(str2);
/*  727 */       if (!bool) {
/*      */ 
/*      */         
/*  730 */         property = new Property(k, str1.length(), str2.length());
/*      */       } else {
/*  732 */         this._property_value_pattern = true;
/*      */ 
/*      */         
/*  735 */         property = new PatternProperty(k, str1.length(), str2.length());
/*      */       } 
/*  737 */       addProperty(property, b, (Map)hashMap, str1);
/*  738 */       b++;
/*      */     } 
/*      */ 
/*      */     
/*  742 */     int j = stringBuilder.length();
/*  743 */     char[] arrayOfChar1 = new char[j];
/*  744 */     stringBuilder.getChars(0, j, arrayOfChar1, 0);
/*  745 */     char[] arrayOfChar2 = new char[j];
/*  746 */     System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, this._domain_length + 1);
/*      */     
/*  748 */     setCanonicalName(arrayOfChar1, arrayOfChar2, arrayOfString, (Map)hashMap, this._domain_length + 1, this._kp_array.length);
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
/*      */   private void addProperty(Property paramProperty, int paramInt, Map<String, Property> paramMap, String paramString) throws MalformedObjectNameException {
/*  763 */     if (paramMap.containsKey(paramString)) throw new MalformedObjectNameException("key `" + paramString + "' already defined");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     if (paramInt == this._kp_array.length) {
/*  769 */       Property[] arrayOfProperty = new Property[paramInt + 10];
/*  770 */       System.arraycopy(this._kp_array, 0, arrayOfProperty, 0, paramInt);
/*  771 */       this._kp_array = arrayOfProperty;
/*      */     } 
/*  773 */     this._kp_array[paramInt] = paramProperty;
/*  774 */     paramMap.put(paramString, paramProperty);
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
/*      */   private void setCanonicalName(char[] paramArrayOfchar1, char[] paramArrayOfchar2, String[] paramArrayOfString, Map<String, Property> paramMap, int paramInt1, int paramInt2) {
/*  788 */     if (this._kp_array != _Empty_property_array) {
/*  789 */       String[] arrayOfString = new String[paramInt2];
/*  790 */       Property[] arrayOfProperty = new Property[paramInt2];
/*      */       
/*  792 */       System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt2);
/*  793 */       Arrays.sort((Object[])arrayOfString);
/*  794 */       paramArrayOfString = arrayOfString;
/*  795 */       System.arraycopy(this._kp_array, 0, arrayOfProperty, 0, paramInt2);
/*  796 */       this._kp_array = arrayOfProperty;
/*  797 */       this._ca_array = new Property[paramInt2];
/*      */       
/*      */       int i;
/*      */       
/*  801 */       for (i = 0; i < paramInt2; i++) {
/*  802 */         this._ca_array[i] = paramMap.get(paramArrayOfString[i]);
/*      */       }
/*      */ 
/*      */       
/*  806 */       i = paramInt2 - 1;
/*      */ 
/*      */       
/*  809 */       for (byte b = 0; b <= i; b++) {
/*  810 */         Property property = this._ca_array[b];
/*      */         
/*  812 */         int j = property._key_length + property._value_length + 1;
/*  813 */         System.arraycopy(paramArrayOfchar1, property._key_index, paramArrayOfchar2, paramInt1, j);
/*      */         
/*  815 */         property.setKeyIndex(paramInt1);
/*  816 */         paramInt1 += j;
/*  817 */         if (b != i) {
/*  818 */           paramArrayOfchar2[paramInt1] = ',';
/*  819 */           paramInt1++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  825 */     if (this._property_list_pattern) {
/*  826 */       if (this._kp_array != _Empty_property_array)
/*  827 */         paramArrayOfchar2[paramInt1++] = ','; 
/*  828 */       paramArrayOfchar2[paramInt1++] = '*';
/*      */     } 
/*      */ 
/*      */     
/*  832 */     this._canonicalName = (new String(paramArrayOfchar2, 0, paramInt1)).intern();
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
/*      */   private static int parseKey(char[] paramArrayOfchar, int paramInt) throws MalformedObjectNameException {
/*  847 */     int i = paramInt;
/*  848 */     int j = paramInt;
/*  849 */     int k = paramArrayOfchar.length;
/*  850 */     while (i < k) {
/*  851 */       String str; char c = paramArrayOfchar[i++];
/*  852 */       switch (c) {
/*      */         case '\n':
/*      */         case '*':
/*      */         case ',':
/*      */         case ':':
/*      */         case '?':
/*  858 */           str = (c == '\n') ? "\\n" : ("" + c);
/*  859 */           throw new MalformedObjectNameException("Invalid character in key: `" + str + "'");
/*      */ 
/*      */ 
/*      */         
/*      */         case '=':
/*  864 */           j = i - 1;
/*      */           break;
/*      */       } 
/*  867 */       if (i < k)
/*  868 */         continue;  j = i;
/*      */     } 
/*      */ 
/*      */     
/*  872 */     return j;
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
/*      */   private static int[] parseValue(char[] paramArrayOfchar, int paramInt) throws MalformedObjectNameException {
/*  891 */     boolean bool = false;
/*      */     
/*  893 */     int i = paramInt;
/*  894 */     int j = paramInt;
/*      */     
/*  896 */     int k = paramArrayOfchar.length;
/*  897 */     char c = paramArrayOfchar[paramInt];
/*      */     
/*  899 */     if (c == '"') {
/*      */       
/*  901 */       if (++i == k) throw new MalformedObjectNameException("Invalid quote");
/*      */       
/*  903 */       while (i < k) {
/*  904 */         char c1 = paramArrayOfchar[i];
/*  905 */         if (c1 == '\\') {
/*  906 */           if (++i == k) throw new MalformedObjectNameException("Invalid unterminated quoted character sequence");
/*      */ 
/*      */           
/*  909 */           c1 = paramArrayOfchar[i];
/*  910 */           switch (c1) {
/*      */             case '*':
/*      */             case '?':
/*      */             case '\\':
/*      */             case 'n':
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case '"':
/*  921 */               if (i + 1 == k) throw new MalformedObjectNameException("Missing termination quote");
/*      */               
/*      */               break;
/*      */             
/*      */             default:
/*  926 */               throw new MalformedObjectNameException("Invalid quoted character sequence '\\" + c1 + "'");
/*      */           } 
/*      */ 
/*      */         
/*      */         } else {
/*  931 */           if (c1 == '\n') {
/*  932 */             throw new MalformedObjectNameException("Newline in quoted value");
/*      */           }
/*  934 */           if (c1 == '"') {
/*  935 */             i++;
/*      */             break;
/*      */           } 
/*  938 */           switch (c1) {
/*      */             case '*':
/*      */             case '?':
/*  941 */               bool = true;
/*      */               break;
/*      */           } 
/*      */         } 
/*  945 */         i++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  951 */         if (i >= k && c1 != '"') throw new MalformedObjectNameException("Missing termination quote");
/*      */       
/*      */       } 
/*  954 */       j = i;
/*  955 */       if (i < k && 
/*  956 */         paramArrayOfchar[i++] != ',') throw new MalformedObjectNameException("Invalid quote");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  961 */       while (i < k) {
/*  962 */         String str; char c1 = paramArrayOfchar[i++];
/*  963 */         switch (c1) {
/*      */           case '*':
/*      */           case '?':
/*  966 */             bool = true;
/*  967 */             if (i < k)
/*  968 */               continue;  j = i;
/*      */             break;
/*      */           case '\n':
/*      */           case ':':
/*      */           case '=':
/*  973 */             str = (c1 == '\n') ? "\\n" : ("" + c1);
/*  974 */             throw new MalformedObjectNameException("Invalid character `" + str + "' in value");
/*      */ 
/*      */           
/*      */           case ',':
/*  978 */             j = i - 1;
/*      */             break;
/*      */         } 
/*  981 */         if (i < k)
/*  982 */           continue;  j = i;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  987 */     return new int[] { j, bool ? 1 : 0 };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean checkValue(String paramString) throws MalformedObjectNameException {
/*  998 */     if (paramString == null) throw new NullPointerException("Invalid value (null)");
/*      */ 
/*      */     
/* 1001 */     int i = paramString.length();
/* 1002 */     if (i == 0) {
/* 1003 */       return false;
/*      */     }
/* 1005 */     char[] arrayOfChar = paramString.toCharArray();
/* 1006 */     int[] arrayOfInt = parseValue(arrayOfChar, 0);
/* 1007 */     int j = arrayOfInt[0];
/* 1008 */     boolean bool = (arrayOfInt[1] == 1) ? true : false;
/* 1009 */     if (j < i) throw new MalformedObjectNameException("Invalid character in value: `" + arrayOfChar[j] + "'");
/*      */ 
/*      */     
/* 1012 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkKey(String paramString) throws MalformedObjectNameException {
/* 1021 */     if (paramString == null) throw new NullPointerException("Invalid key (null)");
/*      */ 
/*      */     
/* 1024 */     int i = paramString.length();
/* 1025 */     if (i == 0) throw new MalformedObjectNameException("Invalid key (empty)");
/*      */     
/* 1027 */     char[] arrayOfChar = paramString.toCharArray();
/* 1028 */     int j = parseKey(arrayOfChar, 0);
/* 1029 */     if (j < i) throw new MalformedObjectNameException("Invalid character in value: `" + arrayOfChar[j] + "'");
/*      */   
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
/*      */   private boolean isDomain(String paramString) {
/* 1043 */     if (paramString == null) return true; 
/* 1044 */     int i = paramString.length();
/* 1045 */     byte b = 0;
/* 1046 */     while (b < i) {
/* 1047 */       char c = paramString.charAt(b++);
/* 1048 */       switch (c) {
/*      */         case '\n':
/*      */         case ':':
/* 1051 */           return false;
/*      */         case '*':
/*      */         case '?':
/* 1054 */           this._domain_pattern = true;
/*      */       } 
/*      */     
/*      */     } 
/* 1058 */     return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*      */     String str;
/* 1127 */     if (compat) {
/*      */ 
/*      */ 
/*      */       
/* 1131 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */       
/* 1133 */       String str1 = (String)getField.get("propertyListString", "");
/*      */ 
/*      */ 
/*      */       
/* 1137 */       boolean bool = getField.get("propertyPattern", false);
/* 1138 */       if (bool)
/*      */       {
/* 1140 */         str1 = (str1.length() == 0) ? "*" : (str1 + ",*");
/*      */       }
/*      */       
/* 1143 */       str = (String)getField.get("domain", "default") + ":" + str1;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1148 */       paramObjectInputStream.defaultReadObject();
/* 1149 */       str = (String)paramObjectInputStream.readObject();
/*      */     } 
/*      */     
/*      */     try {
/* 1153 */       construct(str);
/* 1154 */     } catch (NullPointerException nullPointerException) {
/* 1155 */       throw new InvalidObjectException(nullPointerException.toString());
/* 1156 */     } catch (MalformedObjectNameException malformedObjectNameException) {
/* 1157 */       throw new InvalidObjectException(malformedObjectNameException.toString());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1223 */     if (compat) {
/*      */ 
/*      */ 
/*      */       
/* 1227 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 1228 */       putField.put("domain", this._canonicalName.substring(0, this._domain_length));
/* 1229 */       putField.put("propertyList", getKeyPropertyList());
/* 1230 */       putField.put("propertyListString", getKeyPropertyListString());
/* 1231 */       putField.put("canonicalName", this._canonicalName);
/* 1232 */       putField.put("pattern", (this._domain_pattern || this._property_list_pattern));
/* 1233 */       putField.put("propertyPattern", this._property_list_pattern);
/* 1234 */       paramObjectOutputStream.writeFields();
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1240 */       paramObjectOutputStream.defaultWriteObject();
/* 1241 */       paramObjectOutputStream.writeObject(getSerializedNameString());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ObjectName getInstance(String paramString) throws MalformedObjectNameException, NullPointerException {
/* 1273 */     return new ObjectName(paramString);
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
/*      */   public static ObjectName getInstance(String paramString1, String paramString2, String paramString3) throws MalformedObjectNameException {
/* 1301 */     return new ObjectName(paramString1, paramString2, paramString3);
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
/*      */   public static ObjectName getInstance(String paramString, Hashtable<String, String> paramHashtable) throws MalformedObjectNameException {
/* 1332 */     return new ObjectName(paramString, paramHashtable);
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
/*      */   public static ObjectName getInstance(ObjectName paramObjectName) {
/* 1365 */     if (paramObjectName.getClass().equals(ObjectName.class))
/* 1366 */       return paramObjectName; 
/* 1367 */     return Util.newObjectName(paramObjectName.getSerializedNameString());
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
/*      */   public ObjectName(String paramString) throws MalformedObjectNameException {
/* 1382 */     construct(paramString);
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
/*      */   public ObjectName(String paramString1, String paramString2, String paramString3) throws MalformedObjectNameException {
/* 1403 */     Map<String, String> map = Collections.singletonMap(paramString2, paramString3);
/* 1404 */     construct(paramString1, map);
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
/*      */   public ObjectName(String paramString, Hashtable<String, String> paramHashtable) throws MalformedObjectNameException {
/* 1425 */     construct(paramString, paramHashtable);
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
/*      */   public boolean isPattern() {
/* 1446 */     return (this._domain_pattern || this._property_list_pattern || this._property_value_pattern);
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
/*      */   public boolean isDomainPattern() {
/* 1458 */     return this._domain_pattern;
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
/*      */   public boolean isPropertyPattern() {
/* 1471 */     return (this._property_list_pattern || this._property_value_pattern);
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
/*      */   public boolean isPropertyListPattern() {
/* 1485 */     return this._property_list_pattern;
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
/*      */   public boolean isPropertyValuePattern() {
/* 1500 */     return this._property_value_pattern;
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
/*      */   public boolean isPropertyValuePattern(String paramString) {
/* 1519 */     if (paramString == null)
/* 1520 */       throw new NullPointerException("key property can't be null"); 
/* 1521 */     for (byte b = 0; b < this._ca_array.length; b++) {
/* 1522 */       Property property = this._ca_array[b];
/* 1523 */       String str = property.getKeyString(this._canonicalName);
/* 1524 */       if (str.equals(paramString))
/* 1525 */         return property instanceof PatternProperty; 
/*      */     } 
/* 1527 */     throw new IllegalArgumentException("key property not found");
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
/*      */   public String getCanonicalName() {
/* 1557 */     return this._canonicalName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDomain() {
/* 1566 */     return this._canonicalName.substring(0, this._domain_length);
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
/*      */   public String getKeyProperty(String paramString) {
/* 1580 */     return _getKeyPropertyList().get(paramString);
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
/*      */   private Map<String, String> _getKeyPropertyList() {
/* 1594 */     synchronized (this) {
/* 1595 */       if (this._propertyList == null) {
/*      */ 
/*      */         
/* 1598 */         this._propertyList = new HashMap<>();
/* 1599 */         int i = this._ca_array.length;
/*      */         
/* 1601 */         for (int j = i - 1; j >= 0; j--) {
/* 1602 */           Property property = this._ca_array[j];
/* 1603 */           this._propertyList.put(property.getKeyString(this._canonicalName), property
/* 1604 */               .getValueString(this._canonicalName));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1608 */     return this._propertyList;
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
/*      */   public Hashtable<String, String> getKeyPropertyList() {
/* 1624 */     return new Hashtable<>(_getKeyPropertyList());
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
/*      */   public String getKeyPropertyListString() {
/* 1639 */     if (this._kp_array.length == 0) return "";
/*      */ 
/*      */ 
/*      */     
/* 1643 */     int i = this._canonicalName.length() - this._domain_length - 1 - (this._property_list_pattern ? 2 : 0);
/*      */ 
/*      */     
/* 1646 */     char[] arrayOfChar1 = new char[i];
/* 1647 */     char[] arrayOfChar2 = this._canonicalName.toCharArray();
/* 1648 */     writeKeyPropertyListString(arrayOfChar2, arrayOfChar1, 0);
/* 1649 */     return new String(arrayOfChar1);
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
/*      */   private String getSerializedNameString() {
/* 1665 */     int i = this._canonicalName.length();
/* 1666 */     char[] arrayOfChar1 = new char[i];
/* 1667 */     char[] arrayOfChar2 = this._canonicalName.toCharArray();
/* 1668 */     int j = this._domain_length + 1;
/*      */ 
/*      */ 
/*      */     
/* 1672 */     System.arraycopy(arrayOfChar2, 0, arrayOfChar1, 0, j);
/*      */ 
/*      */     
/* 1675 */     int k = writeKeyPropertyListString(arrayOfChar2, arrayOfChar1, j);
/*      */ 
/*      */     
/* 1678 */     if (this._property_list_pattern) {
/* 1679 */       if (k == j) {
/*      */         
/* 1681 */         arrayOfChar1[k] = '*';
/*      */       } else {
/*      */         
/* 1684 */         arrayOfChar1[k] = ',';
/* 1685 */         arrayOfChar1[k + 1] = '*';
/*      */       } 
/*      */     }
/*      */     
/* 1689 */     return new String(arrayOfChar1);
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
/*      */   private int writeKeyPropertyListString(char[] paramArrayOfchar1, char[] paramArrayOfchar2, int paramInt) {
/* 1704 */     if (this._kp_array.length == 0) return paramInt;
/*      */     
/* 1706 */     char[] arrayOfChar1 = paramArrayOfchar2;
/* 1707 */     char[] arrayOfChar2 = paramArrayOfchar1;
/*      */     
/* 1709 */     int i = paramInt;
/* 1710 */     int j = this._kp_array.length;
/* 1711 */     int k = j - 1;
/* 1712 */     for (byte b = 0; b < j; b++) {
/* 1713 */       Property property = this._kp_array[b];
/* 1714 */       int m = property._key_length + property._value_length + 1;
/* 1715 */       System.arraycopy(arrayOfChar2, property._key_index, arrayOfChar1, i, m);
/*      */       
/* 1717 */       i += m;
/* 1718 */       if (b < k) arrayOfChar1[i++] = ','; 
/*      */     } 
/* 1720 */     return i;
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
/*      */   public String getCanonicalKeyPropertyListString() {
/* 1737 */     if (this._ca_array.length == 0) return "";
/*      */     
/* 1739 */     int i = this._canonicalName.length();
/* 1740 */     if (this._property_list_pattern) i -= 2; 
/* 1741 */     return this._canonicalName.substring(this._domain_length + 1, i);
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
/*      */   public String toString() {
/* 1757 */     return getSerializedNameString();
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
/*      */   public boolean equals(Object paramObject) {
/* 1776 */     if (this == paramObject) return true;
/*      */ 
/*      */     
/* 1779 */     if (!(paramObject instanceof ObjectName)) return false;
/*      */ 
/*      */ 
/*      */     
/* 1783 */     ObjectName objectName = (ObjectName)paramObject;
/* 1784 */     String str = objectName._canonicalName;
/* 1785 */     if (this._canonicalName == str) return true;
/*      */ 
/*      */ 
/*      */     
/* 1789 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1798 */     return this._canonicalName.hashCode();
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
/*      */   public static String quote(String paramString) {
/* 1832 */     StringBuilder stringBuilder = new StringBuilder("\"");
/* 1833 */     int i = paramString.length();
/* 1834 */     for (byte b = 0; b < i; b++) {
/* 1835 */       char c = paramString.charAt(b);
/* 1836 */       switch (c) {
/*      */         case '\n':
/* 1838 */           c = 'n';
/* 1839 */           stringBuilder.append('\\');
/*      */           break;
/*      */         case '"':
/*      */         case '*':
/*      */         case '?':
/*      */         case '\\':
/* 1845 */           stringBuilder.append('\\');
/*      */           break;
/*      */       } 
/* 1848 */       stringBuilder.append(c);
/*      */     } 
/* 1850 */     stringBuilder.append('"');
/* 1851 */     return stringBuilder.toString();
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
/*      */   public static String unquote(String paramString) {
/* 1876 */     StringBuilder stringBuilder = new StringBuilder();
/* 1877 */     int i = paramString.length();
/* 1878 */     if (i < 2 || paramString.charAt(0) != '"' || paramString.charAt(i - 1) != '"')
/* 1879 */       throw new IllegalArgumentException("Argument not quoted"); 
/* 1880 */     for (byte b = 1; b < i - 1; b++) {
/* 1881 */       char c = paramString.charAt(b);
/* 1882 */       if (c == '\\') {
/* 1883 */         if (b == i - 2)
/* 1884 */           throw new IllegalArgumentException("Trailing backslash"); 
/* 1885 */         c = paramString.charAt(++b);
/* 1886 */         switch (c) {
/*      */           case 'n':
/* 1888 */             c = '\n';
/*      */             break;
/*      */           case '"':
/*      */           case '*':
/*      */           case '?':
/*      */           case '\\':
/*      */             break;
/*      */           default:
/* 1896 */             throw new IllegalArgumentException("Bad character '" + c + "' after backslash");
/*      */         } 
/*      */       
/*      */       } else {
/* 1900 */         switch (c) {
/*      */           case '\n':
/*      */           case '"':
/*      */           case '*':
/*      */           case '?':
/* 1905 */             throw new IllegalArgumentException("Invalid unescaped character '" + c + "' in the string to unquote");
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 1910 */       stringBuilder.append(c);
/*      */     } 
/* 1912 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1920 */   public static final ObjectName WILDCARD = Util.newObjectName("*:*");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean apply(ObjectName paramObjectName) {
/* 1945 */     if (paramObjectName == null) throw new NullPointerException();
/*      */     
/* 1947 */     if (paramObjectName._domain_pattern || paramObjectName._property_list_pattern || paramObjectName._property_value_pattern)
/*      */     {
/*      */       
/* 1950 */       return false;
/*      */     }
/*      */     
/* 1953 */     if (!this._domain_pattern && !this._property_list_pattern && !this._property_value_pattern)
/*      */     {
/*      */       
/* 1956 */       return this._canonicalName.equals(paramObjectName._canonicalName);
/*      */     }
/* 1958 */     return (matchDomains(paramObjectName) && matchKeys(paramObjectName));
/*      */   }
/*      */   
/*      */   private final boolean matchDomains(ObjectName paramObjectName) {
/* 1962 */     if (this._domain_pattern)
/*      */     {
/*      */ 
/*      */       
/* 1966 */       return Util.wildmatch(paramObjectName.getDomain(), getDomain());
/*      */     }
/* 1968 */     return getDomain().equals(paramObjectName.getDomain());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean matchKeys(ObjectName paramObjectName) {
/* 1975 */     if (this._property_value_pattern && !this._property_list_pattern && paramObjectName._ca_array.length != this._ca_array.length)
/*      */     {
/*      */       
/* 1978 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1983 */     if (this._property_value_pattern || this._property_list_pattern) {
/* 1984 */       Map<String, String> map = paramObjectName._getKeyPropertyList();
/* 1985 */       Property[] arrayOfProperty = this._ca_array;
/* 1986 */       String str = this._canonicalName;
/* 1987 */       for (int i = arrayOfProperty.length - 1; i >= 0; i--) {
/*      */ 
/*      */ 
/*      */         
/* 1991 */         Property property = arrayOfProperty[i];
/* 1992 */         String str3 = property.getKeyString(str);
/* 1993 */         String str4 = map.get(str3);
/*      */ 
/*      */         
/* 1996 */         if (str4 == null) return false;
/*      */ 
/*      */         
/* 1999 */         if (this._property_value_pattern && property instanceof PatternProperty) {
/*      */ 
/*      */           
/* 2002 */           if (!Util.wildmatch(str4, property.getValueString(str)))
/*      */           {
/*      */             
/* 2005 */             return false;
/*      */           }
/* 2007 */         } else if (!str4.equals(property.getValueString(str))) {
/* 2008 */           return false;
/*      */         } 
/* 2010 */       }  return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2015 */     String str1 = paramObjectName.getCanonicalKeyPropertyListString();
/* 2016 */     String str2 = getCanonicalKeyPropertyListString();
/* 2017 */     return str1.equals(str2);
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
/*      */   public void setMBeanServer(MBeanServer paramMBeanServer) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(ObjectName paramObjectName) {
/* 2076 */     if (paramObjectName == this) return 0;
/*      */ 
/*      */ 
/*      */     
/* 2080 */     int i = getDomain().compareTo(paramObjectName.getDomain());
/* 2081 */     if (i != 0) {
/* 2082 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2092 */     String str1 = getKeyProperty("type");
/* 2093 */     String str2 = paramObjectName.getKeyProperty("type");
/* 2094 */     if (str1 == null)
/* 2095 */       str1 = ""; 
/* 2096 */     if (str2 == null)
/* 2097 */       str2 = ""; 
/* 2098 */     int j = str1.compareTo(str2);
/* 2099 */     if (j != 0) {
/* 2100 */       return j;
/*      */     }
/*      */ 
/*      */     
/* 2104 */     return getCanonicalName().compareTo(paramObjectName.getCanonicalName());
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/ObjectName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */