/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneralSubtrees
/*     */   implements Cloneable
/*     */ {
/*     */   private final List<GeneralSubtree> trees;
/*     */   private static final int NAME_DIFF_TYPE = -1;
/*     */   private static final int NAME_MATCH = 0;
/*     */   private static final int NAME_NARROWS = 1;
/*     */   private static final int NAME_WIDENS = 2;
/*     */   private static final int NAME_SAME_TYPE = 3;
/*     */   
/*     */   public GeneralSubtrees() {
/*  62 */     this.trees = new ArrayList<>();
/*     */   }
/*     */   
/*     */   private GeneralSubtrees(GeneralSubtrees paramGeneralSubtrees) {
/*  66 */     this.trees = new ArrayList<>(paramGeneralSubtrees.trees);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralSubtrees(DerValue paramDerValue) throws IOException {
/*  75 */     this();
/*  76 */     if (paramDerValue.tag != 48) {
/*  77 */       throw new IOException("Invalid encoding of GeneralSubtrees.");
/*     */     }
/*  79 */     while (paramDerValue.data.available() != 0) {
/*  80 */       DerValue derValue = paramDerValue.data.getDerValue();
/*  81 */       GeneralSubtree generalSubtree = new GeneralSubtree(derValue);
/*  82 */       add(generalSubtree);
/*     */     } 
/*     */   }
/*     */   
/*     */   public GeneralSubtree get(int paramInt) {
/*  87 */     return this.trees.get(paramInt);
/*     */   }
/*     */   
/*     */   public void remove(int paramInt) {
/*  91 */     this.trees.remove(paramInt);
/*     */   }
/*     */   
/*     */   public void add(GeneralSubtree paramGeneralSubtree) {
/*  95 */     if (paramGeneralSubtree == null) {
/*  96 */       throw new NullPointerException();
/*     */     }
/*  98 */     this.trees.add(paramGeneralSubtree);
/*     */   }
/*     */   
/*     */   public boolean contains(GeneralSubtree paramGeneralSubtree) {
/* 102 */     if (paramGeneralSubtree == null) {
/* 103 */       throw new NullPointerException();
/*     */     }
/* 105 */     return this.trees.contains(paramGeneralSubtree);
/*     */   }
/*     */   
/*     */   public int size() {
/* 109 */     return this.trees.size();
/*     */   }
/*     */   
/*     */   public Iterator<GeneralSubtree> iterator() {
/* 113 */     return this.trees.iterator();
/*     */   }
/*     */   
/*     */   public List<GeneralSubtree> trees() {
/* 117 */     return this.trees;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 121 */     return new GeneralSubtrees(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return "   GeneralSubtrees:\n" + this.trees.toString() + "\n";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 138 */     DerOutputStream derOutputStream = new DerOutputStream(); byte b;
/*     */     int i;
/* 140 */     for (b = 0, i = size(); b < i; b++) {
/* 141 */       get(b).encode(derOutputStream);
/*     */     }
/* 143 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 154 */     if (this == paramObject) {
/* 155 */       return true;
/*     */     }
/* 157 */     if (!(paramObject instanceof GeneralSubtrees)) {
/* 158 */       return false;
/*     */     }
/* 160 */     GeneralSubtrees generalSubtrees = (GeneralSubtrees)paramObject;
/* 161 */     return this.trees.equals(generalSubtrees.trees);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 165 */     return this.trees.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GeneralNameInterface getGeneralNameInterface(int paramInt) {
/* 175 */     return getGeneralNameInterface(get(paramInt));
/*     */   }
/*     */   
/*     */   private static GeneralNameInterface getGeneralNameInterface(GeneralSubtree paramGeneralSubtree) {
/* 179 */     GeneralName generalName = paramGeneralSubtree.getName();
/* 180 */     return generalName.getName();
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
/*     */   private void minimize() {
/* 194 */     for (byte b = 0; b < size() - 1; b++) {
/* 195 */       GeneralNameInterface generalNameInterface = getGeneralNameInterface(b);
/* 196 */       boolean bool = false;
/*     */ 
/*     */       
/* 199 */       for (int i = b + 1; i < size(); i++) {
/* 200 */         GeneralNameInterface generalNameInterface1 = getGeneralNameInterface(i);
/* 201 */         switch (generalNameInterface.constrains(generalNameInterface1)) {
/*     */           case -1:
/*     */             break;
/*     */ 
/*     */           
/*     */           case 0:
/* 207 */             bool = true;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 1:
/* 212 */             remove(i);
/* 213 */             i--;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 218 */             bool = true;
/*     */             break;
/*     */           
/*     */           case 3:
/*     */             break;
/*     */           default:
/*     */             break;
/*     */         } 
/*     */       } 
/* 227 */       if (bool) {
/* 228 */         remove(b);
/* 229 */         b--;
/*     */       } 
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
/*     */   private GeneralSubtree createWidestSubtree(GeneralNameInterface paramGeneralNameInterface) {
/*     */     try {
/*     */       GeneralName generalName;
/*     */       ObjectIdentifier objectIdentifier;
/* 246 */       switch (paramGeneralNameInterface.getType())
/*     */       
/*     */       { 
/*     */         case 0:
/* 250 */           objectIdentifier = ((OtherName)paramGeneralNameInterface).getOID();
/* 251 */           generalName = new GeneralName(new OtherName(objectIdentifier, null));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 282 */           return new GeneralSubtree(generalName, 0, -1);case 1: generalName = new GeneralName(new RFC822Name("")); return new GeneralSubtree(generalName, 0, -1);case 2: generalName = new GeneralName(new DNSName("")); return new GeneralSubtree(generalName, 0, -1);case 3: generalName = new GeneralName(new X400Address((byte[])null)); return new GeneralSubtree(generalName, 0, -1);case 4: generalName = new GeneralName(new X500Name("")); return new GeneralSubtree(generalName, 0, -1);case 5: generalName = new GeneralName(new EDIPartyName("")); return new GeneralSubtree(generalName, 0, -1);case 6: generalName = new GeneralName(new URIName("")); return new GeneralSubtree(generalName, 0, -1);case 7: generalName = new GeneralName(new IPAddressName((byte[])null)); return new GeneralSubtree(generalName, 0, -1);case 8: generalName = new GeneralName(new OIDName(new ObjectIdentifier((int[])null))); return new GeneralSubtree(generalName, 0, -1); }  throw new IOException("Unsupported GeneralNameInterface type: " + paramGeneralNameInterface.getType());
/* 283 */     } catch (IOException iOException) {
/* 284 */       throw new RuntimeException("Unexpected error: " + iOException, iOException);
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
/*     */   public GeneralSubtrees intersect(GeneralSubtrees paramGeneralSubtrees) {
/* 322 */     if (paramGeneralSubtrees == null) {
/* 323 */       throw new NullPointerException("other GeneralSubtrees must not be null");
/*     */     }
/*     */     
/* 326 */     GeneralSubtrees generalSubtrees1 = new GeneralSubtrees();
/* 327 */     GeneralSubtrees generalSubtrees2 = null;
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (size() == 0) {
/* 332 */       union(paramGeneralSubtrees);
/* 333 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     minimize();
/* 340 */     paramGeneralSubtrees.minimize();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     for (b = 0; b < size(); b++) {
/* 351 */       GeneralNameInterface generalNameInterface = getGeneralNameInterface(b);
/* 352 */       boolean bool1 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       boolean bool2 = false; byte b1;
/* 360 */       for (b1 = 0; b1 < paramGeneralSubtrees.size(); b1++) {
/* 361 */         GeneralSubtree generalSubtree = paramGeneralSubtrees.get(b1);
/*     */         
/* 363 */         GeneralNameInterface generalNameInterface1 = getGeneralNameInterface(generalSubtree);
/* 364 */         switch (generalNameInterface.constrains(generalNameInterface1)) {
/*     */           case 1:
/* 366 */             remove(b);
/* 367 */             b--;
/* 368 */             generalSubtrees1.add(generalSubtree);
/* 369 */             bool2 = false;
/*     */             break;
/*     */           case 3:
/* 372 */             bool2 = true;
/*     */             break;
/*     */           case 0:
/*     */           case 2:
/* 376 */             bool2 = false;
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 389 */       if (bool2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 395 */         b1 = 0;
/* 396 */         for (byte b2 = 0; b2 < size(); b2++) {
/* 397 */           GeneralNameInterface generalNameInterface1 = getGeneralNameInterface(b2);
/*     */           
/* 399 */           if (generalNameInterface1.getType() == generalNameInterface.getType()) {
/* 400 */             for (byte b3 = 0; b3 < paramGeneralSubtrees.size(); b3++) {
/*     */               
/* 402 */               GeneralNameInterface generalNameInterface2 = paramGeneralSubtrees.getGeneralNameInterface(b3);
/*     */ 
/*     */               
/* 405 */               int i = generalNameInterface1.constrains(generalNameInterface2);
/* 406 */               if (i == 0 || i == 2 || i == 1) {
/*     */ 
/*     */                 
/* 409 */                 b1 = 1;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 415 */         if (b1 == 0) {
/* 416 */           if (generalSubtrees2 == null) {
/* 417 */             generalSubtrees2 = new GeneralSubtrees();
/*     */           }
/*     */           
/* 420 */           GeneralSubtree generalSubtree = createWidestSubtree(generalNameInterface);
/* 421 */           if (!generalSubtrees2.contains(generalSubtree)) {
/* 422 */             generalSubtrees2.add(generalSubtree);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 427 */         remove(b);
/* 428 */         b--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 433 */     if (generalSubtrees1.size() > 0) {
/* 434 */       union(generalSubtrees1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 439 */     for (b = 0; b < paramGeneralSubtrees.size(); b++) {
/* 440 */       GeneralSubtree generalSubtree = paramGeneralSubtrees.get(b);
/* 441 */       GeneralNameInterface generalNameInterface = getGeneralNameInterface(generalSubtree);
/* 442 */       boolean bool = false;
/* 443 */       for (byte b1 = 0; b1 < size(); b1++) {
/* 444 */         GeneralNameInterface generalNameInterface1 = getGeneralNameInterface(b1);
/* 445 */         switch (generalNameInterface1.constrains(generalNameInterface)) {
/*     */           case -1:
/* 447 */             bool = true;
/*     */             break;
/*     */ 
/*     */           
/*     */           case 0:
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/* 455 */             bool = false;
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 464 */       if (bool) {
/* 465 */         add(generalSubtree);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 470 */     return generalSubtrees2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void union(GeneralSubtrees paramGeneralSubtrees) {
/* 479 */     if (paramGeneralSubtrees != null) {
/* 480 */       byte b; int i; for (b = 0, i = paramGeneralSubtrees.size(); b < i; b++) {
/* 481 */         add(paramGeneralSubtrees.get(b));
/*     */       }
/*     */       
/* 484 */       minimize();
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
/*     */   public void reduce(GeneralSubtrees paramGeneralSubtrees) {
/* 497 */     if (paramGeneralSubtrees == null)
/*     */       return;  byte b;
/*     */     int i;
/* 500 */     for (b = 0, i = paramGeneralSubtrees.size(); b < i; b++) {
/* 501 */       GeneralNameInterface generalNameInterface = paramGeneralSubtrees.getGeneralNameInterface(b);
/* 502 */       for (byte b1 = 0; b1 < size(); b1++) {
/* 503 */         GeneralNameInterface generalNameInterface1 = getGeneralNameInterface(b1);
/* 504 */         switch (generalNameInterface.constrains(generalNameInterface1)) {
/*     */ 
/*     */           
/*     */           case 0:
/* 508 */             remove(b1);
/* 509 */             b1--;
/*     */             break;
/*     */           
/*     */           case 1:
/* 513 */             remove(b1);
/* 514 */             b1--;
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/GeneralSubtrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */