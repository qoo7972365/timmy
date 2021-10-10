/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneralNames
/*     */ {
/*     */   public GeneralNames(DerValue paramDerValue) throws IOException {
/*  56 */     this();
/*  57 */     if (paramDerValue.tag != 48) {
/*  58 */       throw new IOException("Invalid encoding for GeneralNames.");
/*     */     }
/*  60 */     if (paramDerValue.data.available() == 0) {
/*  61 */       throw new IOException("No data available in passed DER encoded value.");
/*     */     }
/*     */ 
/*     */     
/*  65 */     while (paramDerValue.data.available() != 0) {
/*  66 */       DerValue derValue = paramDerValue.data.getDerValue();
/*     */       
/*  68 */       GeneralName generalName = new GeneralName(derValue);
/*  69 */       add(generalName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final List<GeneralName> names = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public GeneralNames add(GeneralName paramGeneralName) {
/*  81 */     if (paramGeneralName == null) {
/*  82 */       throw new NullPointerException();
/*     */     }
/*  84 */     this.names.add(paramGeneralName);
/*  85 */     return this;
/*     */   }
/*     */   public GeneralNames() {}
/*     */   public GeneralName get(int paramInt) {
/*  89 */     return this.names.get(paramInt);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  93 */     return this.names.isEmpty();
/*     */   }
/*     */   
/*     */   public int size() {
/*  97 */     return this.names.size();
/*     */   }
/*     */   
/*     */   public Iterator<GeneralName> iterator() {
/* 101 */     return this.names.iterator();
/*     */   }
/*     */   
/*     */   public List<GeneralName> names() {
/* 105 */     return this.names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 115 */     if (isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 120 */     for (GeneralName generalName : this.names) {
/* 121 */       generalName.encode(derOutputStream);
/*     */     }
/* 123 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 132 */     if (this == paramObject) {
/* 133 */       return true;
/*     */     }
/* 135 */     if (!(paramObject instanceof GeneralNames)) {
/* 136 */       return false;
/*     */     }
/* 138 */     GeneralNames generalNames = (GeneralNames)paramObject;
/* 139 */     return this.names.equals(generalNames.names);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 143 */     return this.names.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 147 */     return this.names.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/GeneralNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */