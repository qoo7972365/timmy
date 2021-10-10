/*     */ package javax.naming.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LdapName
/*     */   implements Name
/*     */ {
/*     */   private transient List<Rdn> rdns;
/*     */   private transient String unparsed;
/*     */   private static final long serialVersionUID = -1595520034788997356L;
/*     */   
/*     */   public LdapName(String paramString) throws InvalidNameException {
/* 122 */     this.unparsed = paramString;
/* 123 */     parse();
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
/*     */   public LdapName(List<Rdn> paramList) {
/* 145 */     this.rdns = new ArrayList<>(paramList.size());
/* 146 */     for (byte b = 0; b < paramList.size(); b++) {
/* 147 */       Rdn rdn = (Rdn)paramList.get(b);
/* 148 */       if (!(rdn instanceof Rdn)) {
/* 149 */         throw new IllegalArgumentException("Entry:" + rdn + "  not a valid type;list entries must be of type Rdn");
/*     */       }
/*     */       
/* 152 */       this.rdns.add(rdn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LdapName(String paramString, List<Rdn> paramList, int paramInt1, int paramInt2) {
/* 163 */     this.unparsed = paramString;
/*     */ 
/*     */     
/* 166 */     List<Rdn> list = paramList.subList(paramInt1, paramInt2);
/* 167 */     this.rdns = new ArrayList<>(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 175 */     return this.rdns.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 184 */     return this.rdns.isEmpty();
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
/*     */   public Enumeration<String> getAll() {
/* 200 */     final Iterator<Rdn> iter = this.rdns.iterator();
/*     */     
/* 202 */     return new Enumeration<String>() {
/*     */         public boolean hasMoreElements() {
/* 204 */           return iter.hasNext();
/*     */         }
/*     */         public String nextElement() {
/* 207 */           return ((Rdn)iter.next()).toString();
/*     */         }
/*     */       };
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
/*     */   public String get(int paramInt) {
/* 221 */     return ((Rdn)this.rdns.get(paramInt)).toString();
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
/*     */   public Rdn getRdn(int paramInt) {
/* 233 */     return this.rdns.get(paramInt);
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
/*     */   public Name getPrefix(int paramInt) {
/*     */     try {
/* 251 */       return new LdapName(null, this.rdns, 0, paramInt);
/* 252 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 253 */       throw new IndexOutOfBoundsException("Posn: " + paramInt + ", Size: " + this.rdns
/* 254 */           .size());
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
/*     */   public Name getSuffix(int paramInt) {
/*     */     try {
/* 275 */       return new LdapName(null, this.rdns, paramInt, this.rdns.size());
/* 276 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 277 */       throw new IndexOutOfBoundsException("Posn: " + paramInt + ", Size: " + this.rdns
/* 278 */           .size());
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
/*     */   public boolean startsWith(Name paramName) {
/* 296 */     if (paramName == null) {
/* 297 */       return false;
/*     */     }
/* 299 */     int i = this.rdns.size();
/* 300 */     int j = paramName.size();
/* 301 */     return (i >= j && 
/* 302 */       matches(0, j, paramName));
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
/*     */   public boolean startsWith(List<Rdn> paramList) {
/* 317 */     if (paramList == null) {
/* 318 */       return false;
/*     */     }
/* 320 */     int i = this.rdns.size();
/* 321 */     int j = paramList.size();
/* 322 */     return (i >= j && 
/* 323 */       doesListMatch(0, j, paramList));
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
/*     */   public boolean endsWith(Name paramName) {
/* 339 */     if (paramName == null) {
/* 340 */       return false;
/*     */     }
/* 342 */     int i = this.rdns.size();
/* 343 */     int j = paramName.size();
/* 344 */     return (i >= j && 
/* 345 */       matches(i - j, i, paramName));
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
/*     */   public boolean endsWith(List<Rdn> paramList) {
/* 360 */     if (paramList == null) {
/* 361 */       return false;
/*     */     }
/* 363 */     int i = this.rdns.size();
/* 364 */     int j = paramList.size();
/* 365 */     return (i >= j && 
/* 366 */       doesListMatch(i - j, i, paramList));
/*     */   }
/*     */   
/*     */   private boolean doesListMatch(int paramInt1, int paramInt2, List<Rdn> paramList) {
/* 370 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 371 */       if (!((Rdn)this.rdns.get(i)).equals(paramList.get(i - paramInt1))) {
/* 372 */         return false;
/*     */       }
/*     */     } 
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matches(int paramInt1, int paramInt2, Name paramName) {
/* 386 */     if (paramName instanceof LdapName) {
/* 387 */       LdapName ldapName = (LdapName)paramName;
/* 388 */       return doesListMatch(paramInt1, paramInt2, ldapName.rdns);
/*     */     } 
/* 390 */     for (int i = paramInt1; i < paramInt2; i++) {
/*     */       Rdn rdn;
/* 392 */       String str = paramName.get(i - paramInt1);
/*     */       try {
/* 394 */         rdn = (new Rfc2253Parser(str)).parseRdn();
/* 395 */       } catch (InvalidNameException invalidNameException) {
/* 396 */         return false;
/*     */       } 
/* 398 */       if (!rdn.equals(this.rdns.get(i))) {
/* 399 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 403 */     return true;
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
/*     */   public Name addAll(Name paramName) throws InvalidNameException {
/* 417 */     return addAll(size(), paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name addAll(List<Rdn> paramList) {
/* 428 */     return addAll(size(), paramList);
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
/*     */   public Name addAll(int paramInt, Name paramName) throws InvalidNameException {
/* 451 */     this.unparsed = null;
/* 452 */     if (paramName instanceof LdapName) {
/* 453 */       LdapName ldapName = (LdapName)paramName;
/* 454 */       this.rdns.addAll(paramInt, ldapName.rdns);
/*     */     } else {
/* 456 */       Enumeration<String> enumeration = paramName.getAll();
/* 457 */       while (enumeration.hasMoreElements()) {
/* 458 */         this.rdns.add(paramInt++, (new Rfc2253Parser(enumeration
/* 459 */               .nextElement()))
/* 460 */             .parseRdn());
/*     */       }
/*     */     } 
/* 463 */     return this;
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
/*     */   public Name addAll(int paramInt, List<Rdn> paramList) {
/* 481 */     this.unparsed = null;
/* 482 */     for (byte b = 0; b < paramList.size(); b++) {
/* 483 */       Rdn rdn = (Rdn)paramList.get(b);
/* 484 */       if (!(rdn instanceof Rdn)) {
/* 485 */         throw new IllegalArgumentException("Entry:" + rdn + "  not a valid type;suffix list entries must be of type Rdn");
/*     */       }
/*     */       
/* 488 */       this.rdns.add(b + paramInt, rdn);
/*     */     } 
/* 490 */     return this;
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
/*     */   public Name add(String paramString) throws InvalidNameException {
/* 503 */     return add(size(), paramString);
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
/*     */   public Name add(Rdn paramRdn) {
/* 515 */     return add(size(), paramRdn);
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
/*     */   public Name add(int paramInt, String paramString) throws InvalidNameException {
/* 536 */     Rdn rdn = (new Rfc2253Parser(paramString)).parseRdn();
/* 537 */     this.rdns.add(paramInt, rdn);
/* 538 */     this.unparsed = null;
/* 539 */     return this;
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
/*     */   public Name add(int paramInt, Rdn paramRdn) {
/* 558 */     if (paramRdn == null) {
/* 559 */       throw new NullPointerException("Cannot set comp to null");
/*     */     }
/* 561 */     this.rdns.add(paramInt, paramRdn);
/* 562 */     this.unparsed = null;
/* 563 */     return this;
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
/*     */   public Object remove(int paramInt) throws InvalidNameException {
/* 582 */     this.unparsed = null;
/* 583 */     return ((Rdn)this.rdns.remove(paramInt)).toString();
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
/*     */   public List<Rdn> getRdns() {
/* 597 */     return Collections.unmodifiableList(this.rdns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 608 */     return new LdapName(this.unparsed, this.rdns, 0, this.rdns.size());
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
/*     */   public String toString() {
/* 620 */     if (this.unparsed != null) {
/* 621 */       return this.unparsed;
/*     */     }
/* 623 */     StringBuilder stringBuilder = new StringBuilder();
/* 624 */     int i = this.rdns.size();
/* 625 */     if (i - 1 >= 0) {
/* 626 */       stringBuilder.append(this.rdns.get(i - 1));
/*     */     }
/* 628 */     for (int j = i - 2; j >= 0; j--) {
/* 629 */       stringBuilder.append(',');
/* 630 */       stringBuilder.append(this.rdns.get(j));
/*     */     } 
/* 632 */     this.unparsed = stringBuilder.toString();
/* 633 */     return this.unparsed;
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
/*     */   public boolean equals(Object paramObject) {
/* 654 */     if (paramObject == this) {
/* 655 */       return true;
/*     */     }
/* 657 */     if (!(paramObject instanceof LdapName)) {
/* 658 */       return false;
/*     */     }
/* 660 */     LdapName ldapName = (LdapName)paramObject;
/* 661 */     if (this.rdns.size() != ldapName.rdns.size()) {
/* 662 */       return false;
/*     */     }
/* 664 */     if (this.unparsed != null && this.unparsed.equalsIgnoreCase(ldapName.unparsed))
/*     */     {
/* 666 */       return true;
/*     */     }
/*     */     
/* 669 */     for (byte b = 0; b < this.rdns.size(); b++) {
/*     */       
/* 671 */       Rdn rdn1 = this.rdns.get(b);
/* 672 */       Rdn rdn2 = ldapName.rdns.get(b);
/* 673 */       if (!rdn1.equals(rdn2)) {
/* 674 */         return false;
/*     */       }
/*     */     } 
/* 677 */     return true;
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
/*     */   public int compareTo(Object paramObject) {
/* 707 */     if (!(paramObject instanceof LdapName)) {
/* 708 */       throw new ClassCastException("The obj is not a LdapName");
/*     */     }
/*     */ 
/*     */     
/* 712 */     if (paramObject == this) {
/* 713 */       return 0;
/*     */     }
/* 715 */     LdapName ldapName = (LdapName)paramObject;
/*     */     
/* 717 */     if (this.unparsed != null && this.unparsed.equalsIgnoreCase(ldapName.unparsed))
/*     */     {
/* 719 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 723 */     int i = Math.min(this.rdns.size(), ldapName.rdns.size());
/* 724 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 726 */       Rdn rdn1 = this.rdns.get(b);
/* 727 */       Rdn rdn2 = ldapName.rdns.get(b);
/*     */       
/* 729 */       int j = rdn1.compareTo(rdn2);
/* 730 */       if (j != 0) {
/* 731 */         return j;
/*     */       }
/*     */     } 
/* 734 */     return this.rdns.size() - ldapName.rdns.size();
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
/*     */   public int hashCode() {
/* 747 */     int i = 0;
/*     */ 
/*     */     
/* 750 */     for (byte b = 0; b < this.rdns.size(); b++) {
/* 751 */       Rdn rdn = this.rdns.get(b);
/* 752 */       i += rdn.hashCode();
/*     */     } 
/* 754 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 765 */     paramObjectOutputStream.defaultWriteObject();
/* 766 */     paramObjectOutputStream.writeObject(toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 771 */     paramObjectInputStream.defaultReadObject();
/* 772 */     this.unparsed = (String)paramObjectInputStream.readObject();
/*     */     try {
/* 774 */       parse();
/* 775 */     } catch (InvalidNameException invalidNameException) {
/*     */       
/* 777 */       throw new StreamCorruptedException("Invalid name: " + this.unparsed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse() throws InvalidNameException {
/* 785 */     this.rdns = (new Rfc2253Parser(this.unparsed)).parseDn();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/LdapName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */