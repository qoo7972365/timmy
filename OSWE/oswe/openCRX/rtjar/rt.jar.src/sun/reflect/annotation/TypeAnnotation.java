/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeAnnotation
/*     */ {
/*     */   private final TypeAnnotationTargetInfo targetInfo;
/*     */   private final LocationInfo loc;
/*     */   private final Annotation annotation;
/*     */   private final AnnotatedElement baseDeclaration;
/*     */   
/*     */   public TypeAnnotation(TypeAnnotationTargetInfo paramTypeAnnotationTargetInfo, LocationInfo paramLocationInfo, Annotation paramAnnotation, AnnotatedElement paramAnnotatedElement) {
/*  53 */     this.targetInfo = paramTypeAnnotationTargetInfo;
/*  54 */     this.loc = paramLocationInfo;
/*  55 */     this.annotation = paramAnnotation;
/*  56 */     this.baseDeclaration = paramAnnotatedElement;
/*     */   }
/*     */   
/*     */   public TypeAnnotationTargetInfo getTargetInfo() {
/*  60 */     return this.targetInfo;
/*     */   }
/*     */   public Annotation getAnnotation() {
/*  63 */     return this.annotation;
/*     */   }
/*     */   public AnnotatedElement getBaseDeclaration() {
/*  66 */     return this.baseDeclaration;
/*     */   }
/*     */   public LocationInfo getLocationInfo() {
/*  69 */     return this.loc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<TypeAnnotation> filter(TypeAnnotation[] paramArrayOfTypeAnnotation, TypeAnnotationTarget paramTypeAnnotationTarget) {
/*  74 */     ArrayList<TypeAnnotation> arrayList = new ArrayList(paramArrayOfTypeAnnotation.length);
/*  75 */     for (TypeAnnotation typeAnnotation : paramArrayOfTypeAnnotation) {
/*  76 */       if (typeAnnotation.getTargetInfo().getTarget() == paramTypeAnnotationTarget)
/*  77 */         arrayList.add(typeAnnotation); 
/*  78 */     }  arrayList.trimToSize();
/*  79 */     return arrayList;
/*     */   }
/*     */   
/*     */   public enum TypeAnnotationTarget {
/*  83 */     CLASS_TYPE_PARAMETER,
/*  84 */     METHOD_TYPE_PARAMETER,
/*  85 */     CLASS_EXTENDS,
/*  86 */     CLASS_IMPLEMENTS,
/*  87 */     CLASS_TYPE_PARAMETER_BOUND,
/*  88 */     METHOD_TYPE_PARAMETER_BOUND,
/*  89 */     FIELD,
/*  90 */     METHOD_RETURN,
/*  91 */     METHOD_RECEIVER,
/*  92 */     METHOD_FORMAL_PARAMETER,
/*  93 */     THROWS;
/*     */   }
/*     */   
/*     */   public static final class TypeAnnotationTargetInfo {
/*     */     private final TypeAnnotation.TypeAnnotationTarget target;
/*     */     private final int count;
/*     */     private final int secondaryIndex;
/*     */     private static final int UNUSED_INDEX = -2;
/*     */     
/*     */     public TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget param1TypeAnnotationTarget) {
/* 103 */       this(param1TypeAnnotationTarget, -2, -2);
/*     */     }
/*     */ 
/*     */     
/*     */     public TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget param1TypeAnnotationTarget, int param1Int) {
/* 108 */       this(param1TypeAnnotationTarget, param1Int, -2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget param1TypeAnnotationTarget, int param1Int1, int param1Int2) {
/* 114 */       this.target = param1TypeAnnotationTarget;
/* 115 */       this.count = param1Int1;
/* 116 */       this.secondaryIndex = param1Int2;
/*     */     }
/*     */     
/*     */     public TypeAnnotation.TypeAnnotationTarget getTarget() {
/* 120 */       return this.target;
/*     */     }
/*     */     public int getCount() {
/* 123 */       return this.count;
/*     */     }
/*     */     public int getSecondaryIndex() {
/* 126 */       return this.secondaryIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 131 */       return "" + this.target + ": " + this.count + ", " + this.secondaryIndex;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class LocationInfo {
/*     */     private final int depth;
/*     */     private final Location[] locations;
/*     */     
/*     */     private LocationInfo() {
/* 140 */       this(0, new Location[0]);
/*     */     }
/*     */     private LocationInfo(int param1Int, Location[] param1ArrayOfLocation) {
/* 143 */       this.depth = param1Int;
/* 144 */       this.locations = param1ArrayOfLocation;
/*     */     }
/*     */     
/* 147 */     public static final LocationInfo BASE_LOCATION = new LocationInfo();
/*     */     
/*     */     public static LocationInfo parseLocationInfo(ByteBuffer param1ByteBuffer) {
/* 150 */       int i = param1ByteBuffer.get() & 0xFF;
/* 151 */       if (i == 0)
/* 152 */         return BASE_LOCATION; 
/* 153 */       Location[] arrayOfLocation = new Location[i];
/* 154 */       for (byte b = 0; b < i; b++) {
/* 155 */         byte b1 = param1ByteBuffer.get();
/* 156 */         short s = (short)(param1ByteBuffer.get() & 0xFF);
/* 157 */         if (b1 != 0) if ((((b1 == 1) ? 1 : 0) | ((b1 == 2) ? 1 : 0)) == 0 && b1 != 3)
/* 158 */             throw new AnnotationFormatError("Bad Location encoding in Type Annotation");  
/* 159 */         if (b1 != 3 && s != 0)
/* 160 */           throw new AnnotationFormatError("Bad Location encoding in Type Annotation"); 
/* 161 */         arrayOfLocation[b] = new Location(b1, s);
/*     */       } 
/* 163 */       return new LocationInfo(i, arrayOfLocation);
/*     */     }
/*     */     
/*     */     public LocationInfo pushArray() {
/* 167 */       return pushLocation((byte)0, (short)0);
/*     */     }
/*     */     
/*     */     public LocationInfo pushInner() {
/* 171 */       return pushLocation((byte)1, (short)0);
/*     */     }
/*     */     
/*     */     public LocationInfo pushWildcard() {
/* 175 */       return pushLocation((byte)2, (short)0);
/*     */     }
/*     */     
/*     */     public LocationInfo pushTypeArg(short param1Short) {
/* 179 */       return pushLocation((byte)3, param1Short);
/*     */     }
/*     */     
/*     */     public LocationInfo pushLocation(byte param1Byte, short param1Short) {
/* 183 */       int i = this.depth + 1;
/* 184 */       Location[] arrayOfLocation = new Location[i];
/* 185 */       System.arraycopy(this.locations, 0, arrayOfLocation, 0, this.depth);
/* 186 */       arrayOfLocation[i - 1] = new Location(param1Byte, (short)(param1Short & 0xFF));
/* 187 */       return new LocationInfo(i, arrayOfLocation);
/*     */     }
/*     */     
/*     */     public TypeAnnotation[] filter(TypeAnnotation[] param1ArrayOfTypeAnnotation) {
/* 191 */       ArrayList<TypeAnnotation> arrayList = new ArrayList(param1ArrayOfTypeAnnotation.length);
/* 192 */       for (TypeAnnotation typeAnnotation : param1ArrayOfTypeAnnotation) {
/* 193 */         if (isSameLocationInfo(typeAnnotation.getLocationInfo()))
/* 194 */           arrayList.add(typeAnnotation); 
/*     */       } 
/* 196 */       return arrayList.<TypeAnnotation>toArray(new TypeAnnotation[0]);
/*     */     }
/*     */     
/*     */     boolean isSameLocationInfo(LocationInfo param1LocationInfo) {
/* 200 */       if (this.depth != param1LocationInfo.depth)
/* 201 */         return false; 
/* 202 */       for (byte b = 0; b < this.depth; b++) {
/* 203 */         if (!this.locations[b].isSameLocation(param1LocationInfo.locations[b]))
/* 204 */           return false; 
/* 205 */       }  return true;
/*     */     }
/*     */     
/*     */     public static final class Location {
/*     */       public final byte tag;
/*     */       public final short index;
/*     */       
/*     */       boolean isSameLocation(Location param2Location) {
/* 213 */         return (this.tag == param2Location.tag && this.index == param2Location.index);
/*     */       }
/*     */       
/*     */       public Location(byte param2Byte, short param2Short) {
/* 217 */         this.tag = param2Byte;
/* 218 */         this.index = param2Short;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return this.annotation.toString() + " with Targetnfo: " + this.targetInfo
/* 226 */       .toString() + " on base declaration: " + this.baseDeclaration
/* 227 */       .toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/TypeAnnotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */