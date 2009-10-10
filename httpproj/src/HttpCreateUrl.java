import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.servicebook.ServiceBook;

public class HttpCreateUrl {
	public final static short DEFAULT = 0;
	public final static short BES_MDS = 1;
	public final static short WAP2 = 2;
	public final static short WAP1 = 3;
	public final static short DIRECT_TCP = 4;
	public final static short WIFI = 5;

	private short _transportType;
	private DirectTcpParams _directParams;
	private Wap1Params _wap1Params;
	public HttpCreateUrl() {
		reinit();
	}
	public void reinit() {
		_transportType = DEFAULT;
		_directParams = null;
		_wap1Params = null;
	}
	public void setTransportType(short type) {
		_transportType = type;
	}
	public void setDirectTcpParams(DirectTcpParams directParams) {
		_directParams = directParams;
	}
	public void setWap1Params(Wap1Params wap1Params) {
		_wap1Params = wap1Params;
	}

	private String getDefaultUrl(String url) {
		return url; 
	}

	private String getBesMdsUrl(String url) {
		return url +";deviceside=false" ;
	}

	private static String getWap2ConnectionUID() {
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.findRecordsByCid("WPTCP");
		String uid = null;
		for(int i=0; i < records.length; i++)
		{
    		//Search through all service records to find the
    		//valid non-Wi-Fi and non-MMS
    		//WAP 2.0 Gateway Service Record.
    			if (records[i].isValid() && !records[i].isDisabled()) {
			
        			if (records[i].getUid() != null && records[i].getUid().length() != 0)
        			{
            				if ((records[i].getUid().toLowerCase().indexOf("wifi") == -1) &&
                				(records[i].getUid().toLowerCase().indexOf("mms") == -1))
            				{
                    				uid = records[i].getUid();
                    				break;
            				}
        			}
			}
		}
		return uid;
	}

	private String getWap2Url(String url) {
		String uid = getWap2ConnectionUID();
		if(uid != null) {
			String retVal = url + ";deviceside=true" + ";ConnectionUID=" + uid;
			return retVal;
		}
		return null;
	}


	private String getWap1Url(String url) {
		if(_wap1Params != null) {
			String retVal =  url + ";deviceside=true";
			boolean someParamGiven = false;
			if(_wap1Params.WapGatewayIP != null && (!_wap1Params.WapGatewayIP.equals(""))) {
				retVal = retVal + ";WapGatewayIP="+_wap1Params.WapGatewayIP;
				someParamGiven = true;
			}
			if(_wap1Params.WapGatewayAPN != null && (!_wap1Params.WapGatewayAPN.equals(""))) {
				retVal = retVal + ";WapGatewayAPN="+_wap1Params.WapGatewayAPN;
				someParamGiven = true;
			}
			if(_wap1Params.WapGatewayPort != null && (!_wap1Params.WapGatewayPort.equals(""))) {
				retVal = retVal + ";WapGatewayPort="+_wap1Params.WapGatewayPort;
				someParamGiven = true;
			}
			if(_wap1Params.WapSourceIP != null && (!_wap1Params.WapSourceIP.equals(""))) {
				retVal = retVal + ";WapSourceIP="+_wap1Params.WapSourceIP;
				someParamGiven = true;
			}
			if(_wap1Params.WapSourcePort != null && (!_wap1Params.WapSourcePort.equals(""))) {
				retVal = retVal + ";WapSourcePort="+_wap1Params.WapSourcePort;
				someParamGiven = true;
			}
			if(_wap1Params.TunnelAuthUsername != null && (!_wap1Params.TunnelAuthUsername.equals(""))) {
				retVal = retVal + ";TunnelAuthUsername="+_wap1Params.TunnelAuthUsername;
				someParamGiven = true;
			}
			if(_wap1Params.TunnelAuthPassword != null && (!_wap1Params.TunnelAuthPassword.equals(""))) {
				retVal = retVal + ";TunnelAuthPassword="+_wap1Params.TunnelAuthPassword;
				someParamGiven = true;
			}
			if(_wap1Params.WapEnableWTLS != null && (!_wap1Params.WapEnableWTLS.equals(""))) {
				retVal = retVal + ";WapEnableWTLS="+_wap1Params.WapEnableWTLS;
				someParamGiven = true;
			}
			if(someParamGiven) {
				return retVal;
			}
		}
		return null ;
	}


	private String getDirectTcpUrl(String url) {
		String retVal =  url + ";deviceside=true";
		if(_directParams != null) {
			if(_directParams._apn != null && (!_directParams._apn.equals(""))) {
				retVal = retVal + ";apn="+_directParams._apn;
			}
			if(_directParams._userName != null && (!_directParams._userName.equals(""))) {
				retVal = retVal + ";TunnelAuthUsername=" + _directParams._userName;
			}
			if(_directParams._passWord != null && (!_directParams._passWord.equals(""))) {
				retVal = retVal + ";TunnelAuthPassword=" + _directParams._passWord;
			}
		}
		return retVal;
	}

	private String getWifiUrl(String url) {
		String retVal = url + ";interface=wifi";
		return retVal;
	}

	public String getUrl(String url) {
		switch(_transportType) {
			case DEFAULT: {
				return getDefaultUrl(url);
			}
			case BES_MDS: {
				return getBesMdsUrl(url);
			}
			case WAP2: {
				return getWap2Url(url);
			}
			case WAP1: {
				return getWap1Url(url);
			}
			case DIRECT_TCP: {
				return getDirectTcpUrl(url);
			}
			case WIFI: {
				return getWifiUrl(url);
			}
		}
		return null;
	}

	public static String getTransportTypeName(int type) {
		switch(type) {
			case DEFAULT: {
				return "DEFAULT";
			}
			case BES_MDS: {
				return "BES_MDS";
			}
			case WAP2: {
				return "WAP-2";
			}
			case WAP1: {
				return "WAP-1";
			}
			case DIRECT_TCP: {
				return "DIRECT_TCP";
			}
			case WIFI: {
				return "WIFI";
			}
		}
		return "UNKNOWN";
	}
};
