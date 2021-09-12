/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparkLine
/*     */   extends TagSupport
/*     */ {
/*  59 */   int plots = 20;
/*  60 */   int height = 25;
/*  61 */   String listname = null;
/*  62 */   String lineColor = "0";
/*  63 */   String image = "/images/signinBorder.gif";
/*     */   
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  68 */     return this.height;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setHeight(int height)
/*     */   {
/*  74 */     this.height = height;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getImage()
/*     */   {
/*  80 */     return this.image;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setImage(String image)
/*     */   {
/*  87 */     this.image = image;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getPlots()
/*     */   {
/*  93 */     return this.plots;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setPlots(int plots)
/*     */   {
/*  99 */     this.plots = plots;
/*     */   }
/*     */   
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 108 */       JspWriter out = this.pageContext.getOut();
/* 109 */       ArrayList list1 = (ArrayList)this.pageContext.findAttribute(this.listname);
/* 110 */       boolean isZero = false;
/*     */       
/* 112 */       if ("0".equals(this.lineColor)) {
/* 113 */         this.image = "/images/signinBorder.gif";
/* 114 */       } else if ("1".equals(this.lineColor)) {
/* 115 */         this.image = "/images/magenta_signinBorder.gif";
/*     */       }
/* 117 */       else if ("2".equals(this.lineColor)) {
/* 118 */         this.image = "/images/black_signinBorder.gif";
/*     */       }
/* 120 */       else if ("3".equals(this.lineColor)) {
/* 121 */         this.image = "/images/cyan_signinBorder.gif";
/* 122 */       } else if ("4".equals(this.lineColor)) {
/* 123 */         this.image = "/images/orange_signinBorder.gif";
/*     */       } else {
/* 125 */         this.image = "/images/signinBorder.gif";
/*     */       }
/*     */       
/* 128 */       ArrayList list = new ArrayList();
/* 129 */       for (int i = 0; i < list1.size(); i++) {
/* 130 */         String s1 = list1.get(i).toString();
/*     */         
/* 132 */         if (s1.indexOf("#") != -1) {
/* 133 */           String[] temp = s1.split("#");
/* 134 */           list.add(temp[1]);
/*     */         } else {
/* 136 */           list.add(s1);
/*     */         }
/*     */       }
/* 139 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/* 141 */         String h = list.get(i).toString();
/*     */         
/* 143 */         if (!h.equals("-")) {
/* 144 */           double value = Double.parseDouble(h);
/*     */           
/* 146 */           if (value <= 0.0D) {
/* 147 */             isZero = true;
/*     */           }
/*     */           else {
/* 150 */             isZero = false;
/* 151 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 158 */       if (list != null)
/*     */       {
/*     */ 
/* 161 */         int totalsize = list.size();
/* 162 */         int maxplots = this.plots;
/* 163 */         int plotcount = 0;
/* 164 */         ArrayList list2 = new ArrayList();
/* 165 */         ArrayList list3 = new ArrayList();
/* 166 */         double sum = 0.0D;
/* 167 */         if (totalsize > maxplots)
/*     */         {
/*     */ 
/* 170 */           for (int i = 0; i < maxplots; i++)
/*     */           {
/* 172 */             String l = list.get(i).toString();
/* 173 */             if (!l.equals("-")) {
/* 174 */               list3.add(Double.valueOf(Double.parseDouble(l)));
/*     */             }
/*     */           }
/* 177 */           totalsize = maxplots;
/*     */         }
/* 179 */         if (totalsize > 0)
/*     */         {
/*     */ 
/*     */ 
/* 183 */           double maxvalue = 0.0D;
/* 184 */           for (int i = 0; i < list.size(); i++)
/*     */           {
/* 186 */             String r = list.get(i).toString();
/* 187 */             if (!r.equals("-")) {
/* 188 */               double value = Double.parseDouble(r);
/*     */               
/* 190 */               if (value > maxvalue)
/*     */               {
/* 192 */                 maxvalue = value;
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 197 */           for (int i = 0; i < list.size(); i++)
/*     */           {
/* 199 */             String h = list.get(i).toString();
/*     */             
/* 201 */             if (!h.equals("-")) {
/* 202 */               double value = Double.parseDouble(h);
/* 203 */               long plotheight = Math.round(value * this.height / maxvalue);
/* 204 */               if (isZero) {
/* 205 */                 this.image = "/images/gray_signinBorder.gif";
/* 206 */                 value = 0.0D;
/* 207 */                 plotheight = 3L;
/*     */               }
/* 209 */               out.print("<img src='" + this.image + "' height='" + plotheight + "' width='2' alt='" + plotheight + "' title='" + value + "'>");
/* 210 */               out.print("<img src='/images/spacer.gif' width='3'>");
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 222 */       ee.printStackTrace();
/*     */     }
/* 224 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getListName()
/*     */   {
/* 230 */     return this.listname;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setListName(String list)
/*     */   {
/* 236 */     this.listname = list;
/*     */   }
/*     */   
/*     */   public String getLineColor() {
/* 240 */     return this.lineColor;
/*     */   }
/*     */   
/*     */   public void setLineColor(String lineColor) {
/* 244 */     this.lineColor = lineColor;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\SparkLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */