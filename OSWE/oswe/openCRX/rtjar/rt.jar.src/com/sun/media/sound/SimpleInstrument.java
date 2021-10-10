/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.sound.midi.Patch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleInstrument
/*     */   extends ModelInstrument
/*     */ {
/*     */   private static class SimpleInstrumentPart
/*     */   {
/*     */     ModelPerformer[] performers;
/*     */     int keyFrom;
/*     */     int keyTo;
/*     */     int velFrom;
/*     */     int velTo;
/*     */     int exclusiveClass;
/*     */     
/*     */     private SimpleInstrumentPart() {}
/*     */   }
/*  47 */   protected int preset = 0;
/*  48 */   protected int bank = 0;
/*     */   protected boolean percussion = false;
/*  50 */   protected String name = "";
/*  51 */   protected List<SimpleInstrumentPart> parts = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public SimpleInstrument() {
/*  55 */     super(null, null, null, null);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  59 */     this.parts.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelPerformer[] paramArrayOfModelPerformer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  64 */     SimpleInstrumentPart simpleInstrumentPart = new SimpleInstrumentPart();
/*  65 */     simpleInstrumentPart.performers = paramArrayOfModelPerformer;
/*  66 */     simpleInstrumentPart.keyFrom = paramInt1;
/*  67 */     simpleInstrumentPart.keyTo = paramInt2;
/*  68 */     simpleInstrumentPart.velFrom = paramInt3;
/*  69 */     simpleInstrumentPart.velTo = paramInt4;
/*  70 */     simpleInstrumentPart.exclusiveClass = paramInt5;
/*  71 */     this.parts.add(simpleInstrumentPart);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelPerformer[] paramArrayOfModelPerformer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  76 */     add(paramArrayOfModelPerformer, paramInt1, paramInt2, paramInt3, paramInt4, -1);
/*     */   }
/*     */   
/*     */   public void add(ModelPerformer[] paramArrayOfModelPerformer, int paramInt1, int paramInt2) {
/*  80 */     add(paramArrayOfModelPerformer, paramInt1, paramInt2, 0, 127, -1);
/*     */   }
/*     */   
/*     */   public void add(ModelPerformer[] paramArrayOfModelPerformer) {
/*  84 */     add(paramArrayOfModelPerformer, 0, 127, 0, 127, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelPerformer paramModelPerformer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  89 */     add(new ModelPerformer[] { paramModelPerformer }, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ModelPerformer paramModelPerformer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  95 */     add(new ModelPerformer[] { paramModelPerformer }, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public void add(ModelPerformer paramModelPerformer, int paramInt1, int paramInt2) {
/*  99 */     add(new ModelPerformer[] { paramModelPerformer }, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void add(ModelPerformer paramModelPerformer) {
/* 103 */     add(new ModelPerformer[] { paramModelPerformer });
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelInstrument paramModelInstrument, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 108 */     add(paramModelInstrument.getPerformers(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelInstrument paramModelInstrument, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 113 */     add(paramModelInstrument.getPerformers(), paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public void add(ModelInstrument paramModelInstrument, int paramInt1, int paramInt2) {
/* 117 */     add(paramModelInstrument.getPerformers(), paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void add(ModelInstrument paramModelInstrument) {
/* 121 */     add(paramModelInstrument.getPerformers());
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPerformer[] getPerformers() {
/* 126 */     int i = 0;
/* 127 */     for (SimpleInstrumentPart simpleInstrumentPart : this.parts) {
/* 128 */       if (simpleInstrumentPart.performers != null)
/* 129 */         i += simpleInstrumentPart.performers.length; 
/*     */     } 
/* 131 */     ModelPerformer[] arrayOfModelPerformer = new ModelPerformer[i];
/* 132 */     byte b = 0;
/* 133 */     for (SimpleInstrumentPart simpleInstrumentPart : this.parts) {
/* 134 */       if (simpleInstrumentPart.performers != null) {
/* 135 */         for (ModelPerformer modelPerformer1 : simpleInstrumentPart.performers) {
/* 136 */           ModelPerformer modelPerformer2 = new ModelPerformer();
/* 137 */           modelPerformer2.setName(getName());
/* 138 */           arrayOfModelPerformer[b++] = modelPerformer2;
/*     */           
/* 140 */           modelPerformer2.setDefaultConnectionsEnabled(modelPerformer1
/* 141 */               .isDefaultConnectionsEnabled());
/* 142 */           modelPerformer2.setKeyFrom(modelPerformer1.getKeyFrom());
/* 143 */           modelPerformer2.setKeyTo(modelPerformer1.getKeyTo());
/* 144 */           modelPerformer2.setVelFrom(modelPerformer1.getVelFrom());
/* 145 */           modelPerformer2.setVelTo(modelPerformer1.getVelTo());
/* 146 */           modelPerformer2.setExclusiveClass(modelPerformer1.getExclusiveClass());
/* 147 */           modelPerformer2.setSelfNonExclusive(modelPerformer1.isSelfNonExclusive());
/* 148 */           modelPerformer2.setReleaseTriggered(modelPerformer1.isReleaseTriggered());
/* 149 */           if (simpleInstrumentPart.exclusiveClass != -1)
/* 150 */             modelPerformer2.setExclusiveClass(simpleInstrumentPart.exclusiveClass); 
/* 151 */           if (simpleInstrumentPart.keyFrom > modelPerformer2.getKeyFrom())
/* 152 */             modelPerformer2.setKeyFrom(simpleInstrumentPart.keyFrom); 
/* 153 */           if (simpleInstrumentPart.keyTo < modelPerformer2.getKeyTo())
/* 154 */             modelPerformer2.setKeyTo(simpleInstrumentPart.keyTo); 
/* 155 */           if (simpleInstrumentPart.velFrom > modelPerformer2.getVelFrom())
/* 156 */             modelPerformer2.setVelFrom(simpleInstrumentPart.velFrom); 
/* 157 */           if (simpleInstrumentPart.velTo < modelPerformer2.getVelTo())
/* 158 */             modelPerformer2.setVelTo(simpleInstrumentPart.velTo); 
/* 159 */           modelPerformer2.getOscillators().addAll(modelPerformer1.getOscillators());
/* 160 */           modelPerformer2.getConnectionBlocks().addAll(modelPerformer1
/* 161 */               .getConnectionBlocks());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return arrayOfModelPerformer;
/*     */   }
/*     */   
/*     */   public Object getData() {
/* 170 */     return null;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 174 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/* 178 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public ModelPatch getPatch() {
/* 182 */     return new ModelPatch(this.bank, this.preset, this.percussion);
/*     */   }
/*     */   
/*     */   public void setPatch(Patch paramPatch) {
/* 186 */     if (paramPatch instanceof ModelPatch && ((ModelPatch)paramPatch).isPercussion()) {
/* 187 */       this.percussion = true;
/* 188 */       this.bank = paramPatch.getBank();
/* 189 */       this.preset = paramPatch.getProgram();
/*     */     } else {
/* 191 */       this.percussion = false;
/* 192 */       this.bank = paramPatch.getBank();
/* 193 */       this.preset = paramPatch.getProgram();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SimpleInstrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */