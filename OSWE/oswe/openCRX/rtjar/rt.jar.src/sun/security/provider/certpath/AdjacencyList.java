/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdjacencyList
/*     */ {
/*     */   private ArrayList<BuildStep> mStepList;
/*     */   private List<List<Vertex>> mOrigList;
/*     */   
/*     */   public AdjacencyList(List<List<Vertex>> paramList) {
/* 101 */     this.mStepList = new ArrayList<>();
/* 102 */     this.mOrigList = paramList;
/* 103 */     buildList(paramList, 0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<BuildStep> iterator() {
/* 114 */     return Collections.<BuildStep>unmodifiableList(this.mStepList).iterator();
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
/*     */   private boolean buildList(List<List<Vertex>> paramList, int paramInt, BuildStep paramBuildStep) {
/* 129 */     List list = paramList.get(paramInt);
/*     */ 
/*     */     
/* 132 */     boolean bool1 = true;
/*     */     
/* 134 */     boolean bool2 = true;
/*     */     
/* 136 */     for (Vertex vertex : list) {
/* 137 */       if (vertex.getIndex() != -1) {
/*     */ 
/*     */         
/* 140 */         if (((List)paramList.get(vertex.getIndex())).size() != 0) {
/* 141 */           bool1 = false;
/*     */         }
/* 143 */       } else if (vertex.getThrowable() == null) {
/* 144 */         bool2 = false;
/*     */       } 
/*     */ 
/*     */       
/* 148 */       this.mStepList.add(new BuildStep(vertex, 1));
/*     */     } 
/*     */     
/* 151 */     if (bool1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       if (bool2) {
/*     */         
/* 158 */         if (paramBuildStep == null) {
/* 159 */           this.mStepList.add(new BuildStep(null, 4));
/*     */         } else {
/* 161 */           this.mStepList.add(new BuildStep(paramBuildStep.getVertex(), 2));
/*     */         } 
/*     */         
/* 164 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       ArrayList<Vertex> arrayList = new ArrayList();
/* 171 */       for (Vertex vertex : list) {
/* 172 */         if (vertex.getThrowable() == null) {
/* 173 */           arrayList.add(vertex);
/*     */         }
/*     */       } 
/* 176 */       if (arrayList.size() == 1) {
/*     */         
/* 178 */         this.mStepList.add(new BuildStep(arrayList.get(0), 5));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         this.mStepList.add(new BuildStep(arrayList.get(0), 5));
/*     */       } 
/*     */ 
/*     */       
/* 192 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     boolean bool = false;
/*     */     
/* 202 */     for (Vertex vertex : list) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 208 */       if (vertex.getIndex() != -1 && (
/* 209 */         (List)paramList.get(vertex.getIndex())).size() != 0) {
/*     */ 
/*     */ 
/*     */         
/* 213 */         BuildStep buildStep = new BuildStep(vertex, 3);
/* 214 */         this.mStepList.add(buildStep);
/* 215 */         bool = buildList(paramList, vertex.getIndex(), buildStep);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 220 */     if (bool)
/*     */     {
/* 222 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (paramBuildStep == null) {
/* 227 */       this.mStepList.add(new BuildStep(null, 4));
/*     */     } else {
/* 229 */       this.mStepList.add(new BuildStep(paramBuildStep.getVertex(), 2));
/*     */     } 
/*     */     
/* 232 */     return false;
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
/* 244 */     StringBuilder stringBuilder = new StringBuilder("[\n");
/*     */     
/* 246 */     byte b = 0;
/* 247 */     for (List<Vertex> list : this.mOrigList) {
/* 248 */       stringBuilder.append("LinkedList[").append(b++).append("]:\n");
/*     */       
/* 250 */       for (Vertex vertex : list) {
/* 251 */         stringBuilder.append(vertex.toString()).append("\n");
/*     */       }
/*     */     } 
/* 254 */     stringBuilder.append("]\n");
/*     */     
/* 256 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/AdjacencyList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */