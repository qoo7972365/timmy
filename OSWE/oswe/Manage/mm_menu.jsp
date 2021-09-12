<!--$Id$-->
<%@ page import="com.adventnet.appmanager.util.*" %>
<%@ page import="com.adventnet.utilities.stringutils.StrUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page  import=" com.adventnet.appmanager.client.resourcemanagement.ManagedApplication"%>
<%@page  import="java.util.Properties,java.util.ArrayList"%>
<%ManagedApplication mo1=new ManagedApplication();%>
function mmLoadMenus() {

  if (window.mm_menu_0713101921_0_1) return;

			<% if(!Constants.sqlManager) { %>
			<% String queryerp="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='ERP' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";

                   ArrayList rowserp =mo1.getRows(queryerp);
                    if(rowserp.size()>0)
                        {

                %>
            window.mm_menu_0713101930_0_1 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
              <%
                            for(int i=0;i<rowserp.size();i++)
                                {
                             ArrayList row1 = (ArrayList)rowserp.get(i);
                             String res1=(String)row1.get(0);

                             String dname=(String)row1.get(1);
                             String values=(String)row1.get(2);
                             %>

                             mm_menu_0713101930_0_1.addMenuItem("<%=FormatUtil.getString(dname)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values%>&method=getMonitorForm'");

                              <%
                                }
                              %>

    		 mm_menu_0713101930_0_1.hideOnMouseOut=true;
    	     mm_menu_0713101930_0_1.menuBorder=1;
    		 mm_menu_0713101930_0_1.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
    		 mm_menu_0713101930_0_1.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
    	     mm_menu_0713101930_0_1.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";
                  <%}%>

 		 <% String query1="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='APP' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";

               ArrayList rows1 =mo1.getRows(query1);
                if(rows1.size()>0)
                    {

            %>
        window.mm_menu_0713101921_0_1 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
          <%
                        for(int i=0;i<rows1.size();i++)
                            {
                         ArrayList row1 = (ArrayList)rows1.get(i);
                         String res1=(String)row1.get(0);

                         String dname=(String)row1.get(1);
                         String values=(String)row1.get(2);


                         if(!"WebLogic Clusters".equalsIgnoreCase(dname)){
                         %>
                         mm_menu_0713101921_0_1.addMenuItem("<%=FormatUtil.getString(dname)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values%>&method=getMonitorForm'");

                          <%
                                }
                            }%>

		 mm_menu_0713101921_0_1.hideOnMouseOut=true;
	     mm_menu_0713101921_0_1.menuBorder=1;
		 mm_menu_0713101921_0_1.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
		 mm_menu_0713101921_0_1.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
	     mm_menu_0713101921_0_1.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";
                <%}%>


	      <% String query2="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='TM' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows2 =mo1.getRows(query2);
                if(rows2.size()>0)
                    {

            %>
	 window.mm_menu_0713101921_0_8 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction")%>",150,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
	  <%
                        for(int i=0;i<rows2.size();i++)
                            {
                         ArrayList row2 = (ArrayList)rows2.get(i);
                         String res2=(String)row2.get(0);

                         String dname2=(String)row2.get(1);
                         String values2=(String)row2.get(2);
                         %>
           mm_menu_0713101921_0_8.addMenuItem("<%=FormatUtil.getString(dname2)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values2%>&method=getMonitorForm'");
       <%}%>





	     mm_menu_0713101921_0_8.hideOnMouseOut=true;
         mm_menu_0713101921_0_8.menuBorder=1;
         mm_menu_0713101921_0_8.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
         mm_menu_0713101921_0_8.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_8.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";

<%}
}
%>
 <% String query6="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='DBS' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows6 =mo1.getRows(query6);
                if(rows6.size()>0)
                    {

            %>
      window.mm_menu_0713101921_0_2 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver")%>",120,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
       <%
                        for(int i=0;i<rows6.size();i++)
                            {
                         ArrayList row6 = (ArrayList)rows6.get(i);
                         String res6=(String)row6.get(0);

                         String dname6=(String)row6.get(1);
                         String values6=(String)row6.get(2);
                         %>
                          mm_menu_0713101921_0_2.addMenuItem("<%= FormatUtil.getString(dname6)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values6%>&method=getMonitorForm'");


       <%}%>
            mm_menu_0713101921_0_2.hideOnMouseOut=true;
         mm_menu_0713101921_0_2.menuBorder=1;
         mm_menu_0713101921_0_2.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
         mm_menu_0713101921_0_2.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_2.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";
	<%}%>
	<% if(!Constants.sqlManager) { %>
 <%
                String query4="SELECT  Min(RESOURCETYPE) RESOURCETYPE, Min(DISPLAYNAME) DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='SYS' and RESOURCETYPE in "+Constants.resourceTypes+" and  RESOURCETYPE NOT IN('Node','WindowsNT','WindowsNT_Server','Windows95','SUN PC','Windows 2000','Windows 2003','Windows XP','snmp-node') GROUP BY SUBGROUP";
               ArrayList rows4 =mo1.getRows(query4);
                if(rows4.size()>0)
                    {
           %>

 window.mm_menu_0713101921_0_3 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers")%>",120,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
  <%
                         for(int l=0;l<rows4.size();l++)
                            {
                         ArrayList row4 = (ArrayList)rows4.get(l);

                         String svalue=(String)row4.get(2);
                         String sername=(String)row4.get(1);
                         %>
  mm_menu_0713101921_0_3.addMenuItem("<%= FormatUtil.getString(sername)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=svalue%>&method=getMonitorForm'");
  <%}%>
  mm_menu_0713101921_0_3.addMenuItem("<%= FormatUtil.getString("Windows")%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=Windows&method=getMonitorForm'");
  mm_menu_0713101921_0_3.addMenuItem("<%= FormatUtil.getString("Unknown")%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=Unknown&method=getMonitorForm'");
  mm_menu_0713101921_0_3.hideOnMouseOut=true;
   mm_menu_0713101921_0_3.menuBorder=1;
   mm_menu_0713101921_0_3.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
   mm_menu_0713101921_0_3.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
   mm_menu_0713101921_0_3.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";

  <%}%>


 <% String query3="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='SER' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows3 =mo1.getRows(query3);
                if(rows3.size()>0)
                    {

            %>
            window.mm_menu_0713101921_0_4 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.services")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);

      <%
                        for(int i=0;i<rows3.size();i++)
                            {
                         ArrayList row3 = (ArrayList)rows3.get(i);
                         String res3=(String)row3.get(0);

                         String dname3=(String)row3.get(1);
                         String values3=(String)row3.get(2);
                         %>
                mm_menu_0713101921_0_4.addMenuItem("<%= FormatUtil.getString(dname3)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values3%>&method=getMonitorForm'");
         <%}%>

   mm_menu_0713101921_0_4.hideOnMouseOut=true;
         mm_menu_0713101921_0_4.menuBorder=1;
         mm_menu_0713101921_0_4.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
         mm_menu_0713101921_0_4.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_4.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";

<%}%>
  <% String query5="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='URL' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows5 =mo1.getRows(query5);
                if(rows5.size()>0)
                    {

            %>
       window.mm_menu_0713101921_0_6 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
        <%
                        for(int i=0;i<rows5.size();i++)
                            {
                         ArrayList row5 = (ArrayList)rows5.get(i);
                         String res5=(String)row5.get(0);

                         String dname5=(String)row5.get(1);
                         String values5=(String)row5.get(2);
                         %>
                          mm_menu_0713101921_0_6.addMenuItem("<%= FormatUtil.getString(dname5)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values5%>&method=getMonitorForm'");
                          <%}%>
                           mm_menu_0713101921_0_6.hideOnMouseOut=true;
		 mm_menu_0713101921_0_6.menuBorder=1;
		 mm_menu_0713101921_0_6.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
		 mm_menu_0713101921_0_6.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_6.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";


<%}%>


 <% String query7="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='MS' and RESOURCETYPE in "+Constants.resourceTypes+" ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows7 =mo1.getRows(query7);
                if(rows7.size()>0)
                    {

            %>
       window.mm_menu_0713101921_0_7 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
       <%
                        for(int i=0;i<rows7.size();i++)
                            {
                         ArrayList row7 = (ArrayList)rows7.get(i);
                         String res7=(String)row7.get(0);

                         String dname7=(String)row7.get(1);
                         String values7=(String)row7.get(2);
                         %>
                          mm_menu_0713101921_0_7.addMenuItem("<%= FormatUtil.getString(dname7)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values7%>&method=getMonitorForm'");

       <%}%>
 mm_menu_0713101921_0_7.hideOnMouseOut=true;
         mm_menu_0713101921_0_7.menuBorder=1;
         mm_menu_0713101921_0_7.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
         mm_menu_0713101921_0_7.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_7.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";

<%}%>




<% String query8="SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='CAM' and RESOURCETYPE in "+Constants.resourceTypes+" and RESOURCETYPE !='directory' ORDER BY DISPLAYNAME";
                // out.println("the q"+query1);
               ArrayList rows8 =mo1.getRows(query8);
                if(rows8.size()>0)
                    {

            %>
       window.mm_menu_0713101921_0_5 = new Menu("<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom")%>",200,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
      <% for(int i=0;i<rows8.size();i++)
                            {
                         ArrayList row8 = (ArrayList)rows8.get(i);
                         String res8=(String)row8.get(0);

                         String dname8=(String)row8.get(1);
                         String values8=(String)row8.get(2);

                         %>

                     mm_menu_0713101921_0_5.addMenuItem("<%= FormatUtil.getString(dname8)%>","location='/showresource.do?haid=<c:out value="${param.haid}"/>&type=<%=values8%>&method=getMonitorForm'");
                          <%}%>
mm_menu_0713101921_0_5.hideOnMouseOut=true;
         mm_menu_0713101921_0_5.menuBorder=1;
         mm_menu_0713101921_0_5.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
         mm_menu_0713101921_0_5.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
		 mm_menu_0713101921_0_5.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";


<%}
}
%>




		window.mm_menu_0713101921_0 = new Menu("<%= FormatUtil.getString("am.webclient.mouseover.allservices.text")%>",140,16,"Verdana",11,"#000000","#000000","<c:out value="${applicationScope[selectedskin].Background}"/>","<c:out value="${applicationScope[selectedskin].MouseOver}"/>","left","middle",2,0,1000,0,0,false,true,true,0,true,true);
 		 <% if(!Constants.sqlManager) { %>
		 mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_1,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver")%>");
 		  mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_8,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction")%>");
		 <% } %>
		 mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_2,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver")%>");
		 <% if(!Constants.sqlManager) { %>
		 mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_3,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers")%>");
		 mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_7,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver")%>");
		 mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_6,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices")%>");
                   mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_4,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.services")%>");
		  mm_menu_0713101921_0.addMenuItem(mm_menu_0713101921_0_5,"<%= FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom")%>");
		 <% } %>


         mm_menu_0713101921_0.childMenuIcon="/images/icon_left_arrow.gif";
	   	 mm_menu_0713101921_0.hideOnMouseOut=true;
	     mm_menu_0713101921_0.menuBorder=1;
		 mm_menu_0713101921_0.menuLiteBgColor="<c:out value="${applicationScope[selectedskin].MenuLiteBgColor}"/>";
		 mm_menu_0713101921_0.menuBorderBgColor="<c:out value="${applicationScope[selectedskin].MenuBorderBgColor}"/>";
	     mm_menu_0713101921_0.bgColor="<c:out value="${applicationScope[selectedskin].BgColor}"/>";

		 mm_menu_0713101921_0.writeMenus();



} // mmLoadMenus()
