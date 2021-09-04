/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlashDialChart
/*     */   extends BodyTagSupport
/*     */ {
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/*  16 */     return 0;
/*     */   }
/*     */   
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  24 */       JspWriter out = this.pageContext.getOut();
/*  25 */       out.println("<div id='chartdiv" + this.chartid + "' class='text' align='center'><embed type='application/x-shockwave-flash' src='flash/AngularGauge.swf' id='myChartId" + this.chartid + "' name='myChartId" + this.chartid + "' quality='high' allowscriptaccess='always' wmode='transparent'  ></div>");
/*  26 */       out.println("<script type=\"text/javascript\">");
/*  27 */       out.println(" var myChart" + this.chartid + " = new FusionCharts(\"/flash/AngularGauge.swf\",\"myChartId" + this.chartid + "\",\"" + this.width + "\", \"" + this.height + "\", \"0\", \"0\");");
/*  28 */       out.println("var str=\"/getflashxml.do?\"+encodeURIComponent(\"method=getXML&chartWidth=" + this.width + "&chartHeight=" + this.height + "&value=" + this.value + "&upperLimit=" + this.maxvalue + "&units=" + this.units + "&metricname=" + this.metricname + "&isSemiDial=" + this.isSemiDial + "\");");
/*  29 */       out.println("myChart" + this.chartid + ".setDataURL(str);");
/*  30 */       out.println("myChart" + this.chartid + ".setTransparent(true);");
/*  31 */       out.println(" myChart" + this.chartid + ".render(\"chartdiv" + this.chartid + "\");");
/*  32 */       out.println("</script>");
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  37 */       ex.printStackTrace();
/*     */     }
/*  39 */     return 6; }
/*     */   
/*  41 */   private String width = "220";
/*  42 */   private String height = "220";
/*  43 */   private String maxvalue = "100";
/*  44 */   private String value = "0";
/*  45 */   private String chartid = "";
/*  46 */   private String metricname = "";
/*  47 */   private String units = "";
/*  48 */   private boolean isSemiDial = false;
/*     */   
/*     */   public void setValue(String value) {
/*  51 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  55 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setMaxvalue(String maxvalue) {
/*  59 */     this.maxvalue = maxvalue;
/*     */   }
/*     */   
/*     */   public String getMaxvalue() {
/*  63 */     return this.maxvalue;
/*     */   }
/*     */   
/*     */   public void setChartid(String chartid)
/*     */   {
/*  68 */     this.chartid = chartid;
/*     */   }
/*     */   
/*     */   public String getChartid() {
/*  72 */     return this.chartid;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setWidth(String width)
/*     */   {
/*  78 */     this.width = width;
/*     */   }
/*     */   
/*     */   public String getWidth() {
/*  82 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setHeight(String height) {
/*  86 */     this.height = height;
/*     */   }
/*     */   
/*     */   public String getHeight() {
/*  90 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setUnits(String units) {
/*  94 */     this.units = units;
/*     */   }
/*     */   
/*     */   public String getUnits() {
/*  98 */     return this.units;
/*     */   }
/*     */   
/*     */   public void setMetricname(String metricname) {
/* 102 */     this.metricname = metricname;
/*     */   }
/*     */   
/*     */   public String getMetricname() {
/* 106 */     return this.metricname;
/*     */   }
/*     */   
/*     */   public void setIsSemiDial(boolean isSemiDial) {
/* 110 */     this.isSemiDial = isSemiDial;
/*     */   }
/*     */   
/*     */   public boolean getIsSemiDial() {
/* 114 */     return this.isSemiDial;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\FlashDialChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */