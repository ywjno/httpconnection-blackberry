
	public class Wap1Params {
		String WapGatewayIP ;
		String WapGatewayAPN ;
		String WapGatewayPort ;
		String WapSourceIP ;
		String WapSourcePort ;
		String TunnelAuthUsername ;
		String TunnelAuthPassword ;
		String WapEnableWTLS;
		public Wap1Params(
			String wapGatewayIP ,
			String wapGatewayAPN ,
			String wapGatewayPort ,
			String wapSourceIP ,
			String wapSourcePort ,
			String wunnelAuthUsername ,
			String wunnelAuthPassword ,
			String wapEnableWTLS
		) {
		WapGatewayIP  = wapGatewayIP;
		WapGatewayAPN = wapGatewayAPN;
		WapGatewayPort = wapGatewayPort;
		WapSourceIP = wapSourceIP;
		WapSourcePort = wapSourcePort;
		TunnelAuthUsername = wunnelAuthUsername;
		TunnelAuthPassword = wunnelAuthPassword;
		WapEnableWTLS = wapEnableWTLS;
		}
	};
