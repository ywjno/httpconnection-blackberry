import java.util.Enumeration;
import java.util.Vector;
	public class AsynchronousHttpUrlGetter implements Runnable {
		private HttpUrlTextReceiver _receiver;
		Vector _urlsToFetchV;
		public AsynchronousHttpUrlGetter(HttpUrlTextReceiver receiver) {
			_receiver = receiver;
			_urlsToFetchV = new Vector();
		}
		public void addUrl(Object userData, String url) {
			if(url != null) {
				System.out.println("Url:" + url);
			}
			else {
				System.out.println("Url:");
			}
			_urlsToFetchV.addElement(new Pair(userData, url));
		}
		private void runBody() {
			Enumeration iter = _urlsToFetchV.elements();
			while(iter.hasMoreElements()) {
				Pair pair = (Pair) iter.nextElement();
				Object ud = pair.first;
				String url = (String)pair.second;
				if(url != null) {
					try {
						HttpUtil httpUtil = new HttpUtil();
						String retVal = httpUtil.fetchUrlReturningTextContent(url);
						_receiver.setUrlText(ud, url, retVal, false, "");
					}
					catch(Exception e) {
						_receiver.setUrlText(ud, url, "", true, e.toString());
					}
				}
				else {
					_receiver.setUrlText(ud, url, "", true, "Null url passed to fetcher.");
				}
			}
			_receiver.allUrlsFetched();
		}
		public void run() {
			try {
				runBody();
			}
			catch(Exception e) {
				_receiver.unforeSeenException(e.toString());
			}
		}

	};
