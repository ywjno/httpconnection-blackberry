import java.util.Vector;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.io.MIMETypeAssociations;
import java.util.Enumeration;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.crypto.DigestOutputStream;
import net.rim.device.api.crypto.CryptoException;
import net.rim.blackberry.api.browser.URLEncodedPostData;

import net.rim.device.api.crypto.MD5Digest;
import net.rim.device.api.util.Arrays;
import net.rim.device.api.util.SimpleSortingVector;

import net.rim.device.api.util.Comparator; 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;

public class HttpUtil {
	//deletes file if it exists and downloads it.
	HttpConnection _c;//HttpConnection to the external site
	InputStream _is;//Http Input stream for reading.
	FileConnection _connection;//File connection where data read will be written.
	DataOutputStream _dout;//Data out put stream 
	public HttpUtil() {
		_c = null;
		_is = null;
		_connection = null;
		_dout = null;
	}

	private void closeFileDownloadResources() {
			//close inputstream of httpconnection
             if (_is != null) {
				 try {
                 	_is.close();
				 }
				 catch (Exception ign) {
				 }
			 }
			//close httpconnection
             if (_c != null) {
				 try {
                 	_c.close();
				 }
				 catch (Exception ign) {
				 }
			}
			 //close outputstream of file
			if (_dout != null) {
				try {
					_dout.close();
				} 
				catch (final IOException e) {
				}
			}
			//close fileconnection.
			if (_connection != null) {
				try {
					_connection.close();
				} 
				catch (final IOException e) {
				}
			}
	}



	public String fetchUrlReturningTextContent(String url ) throws CryptoException, IOException {
         int rc;
		if(CoverageInfo.isOutOfCoverage()) {
			throw new IOException("Data services are not available at this point.");
		}
		StringBuffer raw = new StringBuffer();

         try {
             _c = (HttpConnection)Connector.open(url);

             // Getting the response code will open the connection,
             // send the request, and read the HTTP response headers.
             // The headers are stored until requested.
             rc = _c.getResponseCode();
			 //Dialog.alert("response code = " + rc);
			 /*
			 if(rc == HttpConnection.HTTP_MOVED_TEMP) {
				Dialog.alert(_c.getHeaderField("Location"));
			 }
			 */
             if (rc != HttpConnection.HTTP_OK) {
                 throw new IOException("HTTP response code: " + rc);
             }

             _is = _c.openInputStream();

             // Get the ContentType
             //String type = _c.getType();

             // Get the length and process the data
             //int len = (int)_c.getLength();
             {
                 int ch;
                 byte[] data = new byte[256];
                 int len = 0;
                 int size = 0;
				while ( -1 != (len = _is.read(data)) ) {
					raw.append(new String(data, 0, len));
					size += len;
				}
			 	String retVal = raw.toString();
			 	return retVal;
				 
             }
         } catch (ClassCastException e) {
             throw new IllegalArgumentException("Not an HTTP URL");
         } finally {
				 // Do not put return in finally block. Otherwise any exception thrown in the try block, not caught in the catch block will be ignored.
             if (_is != null)
                 _is.close();
             if (_c != null)
                 _c.close();
         }
	}

	public void cancel() {
		closeFileDownloadResources();
	}
};
