import net.rim.device.api.ui.Manager;
import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.device.api.ui.Graphics;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.system.Bitmap;


import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.component.CheckboxField;
import javax.microedition.rms.RecordEnumeration; 
import javax.microedition.rms.InvalidRecordIDException; 
import javax.microedition.rms.RecordStoreNotOpenException; 
import javax.microedition.rms.RecordStoreException; 
import net.rim.device.api.util.IntVector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import net.rim.device.api.util.IntIntHashtable;
import net.rim.device.api.util.IntEnumeration ;

import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.UiApplication;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.Vector;
import java.util.Enumeration;
import java.lang.InterruptedException;
import net.rim.device.api.util.IntHashtable; 
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import net.rim.device.api.xml.parsers.*;
import net.rim.device.api.util.IntVector;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.AutoTextEditField;




import org.w3c.dom.*; 
import org.xml.sax.*;
import net.rim.device.api.ui.component.GaugeField;


public class HttpProjScreen extends MainScreen implements HttpUrlTextReceiver {
	private ButtonField _replayButton;
	private FieldChangeListener _replayListener = new FieldChangeListener() {
		public void fieldChanged(Field field, int context) {
			ButtonField buttonField = (ButtonField) field;
			replayAction();
		}
	};
	private FieldChangeListener _checkboxListener = new FieldChangeListener() {
		public void fieldChanged(Field field, int context) {
			CheckboxField cb = (CheckboxField) field;
			if(cb == _wap1) {
				if(cb.getChecked()) {
					wap1SelectedAction(true);
				}
				else {
					wap1SelectedAction(false);
				}
			}
			else if(cb == _directTcp) {
				if(cb.getChecked()) {
					directTcpSelectedAction(true);
				}
				else {
					directTcpSelectedAction(false);
				}
			}
		}
	};
	private void directTcpSelectedAction(boolean selected) {
		int directTcpIndex = _directTcp.getIndex();
		if(selected) {
			insert(_apnPassword, directTcpIndex + 1);
			insert(_apnUser, directTcpIndex + 1);
			insert(_apn, directTcpIndex + 1);
			insert(_infoLabelField, directTcpIndex + 1);
		}
		else {
			deleteRange(directTcpIndex+1, 4);
		}
	}
	private void wap1SelectedAction(boolean selected) {
		int wap1Index = _wap1.getIndex();
		if(selected) {
			insert(_wap1EnableWTLS, wap1Index + 1);
			insert(_wap1SourcePort, wap1Index + 1);
			insert(_wap1SourceIP, wap1Index + 1);
			insert(_wap1Password, wap1Index + 1);
			insert(_wap1UserName, wap1Index + 1);
			insert(_wap1GatewayPort, wap1Index + 1);
			insert(_wap1GatewayIP, wap1Index + 1);
			insert(_wap1GatewayApn, wap1Index + 1);
		}
		else {
			deleteRange(wap1Index+1, 8);
		}
	}
	/*
	private FieldChangeListener _directTcpChoicesListener = new FieldChangeListener() {
		public void fieldChanged(Field field, int context) {
			int selectedIndex = _directTcpChoices.getSelectedIndex();
			if(selectedIndex == 1) {
				customTcpTypeSelectedAction(true);
			}
			else {
				customTcpTypeSelectedAction(false);
			}
			
		}
	};
	private void customTcpTypeSelectedAction() {
		int tcpMangerIndex = _directTcpChoices.getIndex();
		if(tcpMangerIndex != -1) {
			if(add) {
				insert(_apnPassword, tcpMangerIndex + 1);
				insert(_apnUser, tcpMangerIndex + 1);
				insert(_apn, tcpMangerIndex + 1);
			}
			else {
				deleteRange(tcpMangerIndex + 1, 3);
			}

		}
	}
	private void customTcpTypeSelectedAction() {
		int tcpMangerIndex = _directTcpChoicesManager.getIndex();
		if(tcpMangerIndex != -1) {
			insert(_apnPassword, tcpMangerIndex + 1);
			insert(_apnUser, tcpMangerIndex + 1);
			insert(_apn, tcpMangerIndex + 1);

		}
	}
	*/

	private HttpCreateUrl _createUrl;
	private CheckboxField _default;
	private CheckboxField _bes;
	private CheckboxField _directTcp;
	private CheckboxField _wap2;
	private CheckboxField _wifi;
	private CheckboxField _wap1;
	//private ObjectChoiceField _directTcpChoices;
	//private HorizontalFieldManager _directTcpChoicesManager;
	private AutoTextEditField _apn;
	private AutoTextEditField _apnUser;
	private AutoTextEditField _apnPassword;
	private String _testUrl;
	private String _resultStr;
	private AutoTextEditField _urlField;
	private LabelField _infoLabelField;

	private AutoTextEditField _wap1GatewayApn;
	private AutoTextEditField _wap1GatewayIP;
	private AutoTextEditField _wap1GatewayPort;
	private AutoTextEditField _wap1UserName;
	private AutoTextEditField _wap1Password;
	private AutoTextEditField _wap1SourceIP;
	private AutoTextEditField _wap1SourcePort;
	private AutoTextEditField _wap1EnableWTLS;

	private HorizontalFieldManager _feedbackMgr;
	private LabelField _progressField;
	private BitmapField _hourGlass;
	public HttpProjScreen() {
		setTitle("Select a transport type.");
		/*
		GaugeField gf1 = new GaugeField("Trying..", 0, 100, 25, GaugeField.LABEL_AS_PROGRESS);
		add(gf1);
		GaugeField gf2 = new GaugeField("Progress..", 0, 100, 25, GaugeField.NO_TEXT);
		add(gf2);
		GaugeField gf3 = new GaugeField("Progress..", 0, 100, 25, GaugeField.PERCENT);
		BitmapField bmf = new BitmapField(Bitmap.getPredefinedBitmap(Bitmap.HOURGLASS));
		add(bmf);
		add(gf3);
		*/
		_urlField = new AutoTextEditField("URL:","http://www.google.com");
		add(_urlField);
		 _default = new CheckboxField("Default", false, CheckboxField.FIELD_LEFT);
		 add(_default);
		 _bes = new CheckboxField("BES", false, CheckboxField.FIELD_LEFT);
		 add(_bes);
		 _directTcp = new CheckboxField("DIRECT-TCP", false, CheckboxField.FIELD_LEFT);
		 add(_directTcp);
		 _directTcp.setChangeListener(_checkboxListener);
		 _infoLabelField = new LabelField("APN info(Optional)", LabelField.USE_ALL_WIDTH);
		_apn = new AutoTextEditField("APN:", "");
		//add(_apn);
		_apnUser = new AutoTextEditField("Username for APN:", "");
		//add(_apnUser);
		_apnPassword = new AutoTextEditField("Password for APN:", "");
		//add(_apnPassword);
		_wap1GatewayApn = new AutoTextEditField("GatewayAPN:", "");
		_wap1GatewayIP = new AutoTextEditField("GatewayIP:", "");
		_wap1GatewayPort = new AutoTextEditField("GatewayPort:","");
		_wap1UserName  = new AutoTextEditField("Username:","");;
		_wap1Password  = new AutoTextEditField("Password:","");;
		_wap1SourceIP  = new AutoTextEditField("SourceIP:","");;
		_wap1SourcePort  = new AutoTextEditField("SourcePort:","");;
		_wap1EnableWTLS = new AutoTextEditField("EnableWTLD:","");;
		 /*
		_directTcpChoicesManager= new HorizontalFieldManager();
		add(_directTcpChoicesManager);
		 
		 _directTcp = new CheckboxField("DIRECT-TCP", false, CheckboxField.FIELD_LEFT);
		 _directTcpChoicesManager.add(_directTcp);
		Object[] choices = new String[2];
		choices[0] = "Default";
		choices[1] = "Custom";
		_directTcpChoices = new ObjectChoiceField("Type:", choices, 0, ObjectChoiceField.FIELD_RIGHT);
		_directTcpChoices.setChangeListener(_directTcpChoicesListener);
     		 
		 _directTcpChoicesManager.add(_directTcpChoices);
		 */
		 _wap2 = new CheckboxField("WAP2", false, CheckboxField.FIELD_LEFT);
		 add(_wap2);
		 _wifi = new CheckboxField("WIFI", false, CheckboxField.FIELD_LEFT);
		 add(_wifi);
		 _wap1 = new CheckboxField("WAP1", false, CheckboxField.FIELD_LEFT);
		 _wap1.setChangeListener(_checkboxListener);
		 add(_wap1);
		_replayButton = new ButtonField("Start", ButtonField.CONSUME_CLICK|ButtonField.FIELD_HCENTER);
    		_replayButton.setChangeListener(_replayListener);
		add(_replayButton);
		_createUrl = new HttpCreateUrl();
		_testUrl = "http://www.google.com";
		_resultStr = "";
		
		_progressField = new LabelField("Trying..", LabelField.FOCUSABLE);
		_hourGlass = new BitmapField(Bitmap.getPredefinedBitmap(Bitmap.HOURGLASS), BitmapField.FIELD_RIGHT);
		_feedbackMgr = new HorizontalFieldManager(HorizontalFieldManager.USE_ALL_WIDTH);
		_feedbackMgr.add(_progressField);
		_feedbackMgr.add(_hourGlass);
	}

	private void addProgressIndicator() {
		int buttonIndex = _replayButton.getIndex();
		insert(_feedbackMgr, buttonIndex);
		_feedbackMgr.setFocus();
	}

	private void removeProgressIndicator() {
		int buttonIndex = _replayButton.getIndex();
		deleteRange(buttonIndex - 1, 1);

	}

	private String getUrl(String url, short type) {
		_createUrl.reinit();
		_createUrl.setTransportType(type);
		if(type == HttpCreateUrl.DIRECT_TCP) {
			String apn = _apn.getText();
			String userName = _apnUser.getText();
			String userPassword = _apnPassword.getText();
			DirectTcpParams dtp = new DirectTcpParams(apn, userName, userPassword);
		       _createUrl.setDirectTcpParams(dtp);	
			
		}
		else if(type == HttpCreateUrl.WAP1) {
			String wapGatewayIP = _wap1GatewayIP.getText();
			String wapGatewayAPN = _wap1GatewayApn.getText();
			String wapGatewayPort =_wap1GatewayPort.getText();
			String wapSourceIP = _wap1SourceIP.getText();
			String wapSourcePort  = _wap1SourcePort.getText();
			String wunnelAuthUsername = _wap1UserName.getText();
			String wunnelAuthPassword = _wap1Password.getText();
			String wapEnableWTLS = _wap1EnableWTLS.getText();
			Wap1Params wap1Params = new Wap1Params (
				wapGatewayIP ,
				wapGatewayAPN ,
				wapGatewayPort ,
				wapSourceIP ,
				wapSourcePort ,
				wunnelAuthUsername ,
				wunnelAuthPassword ,
				wapEnableWTLS
			);
			_createUrl.setWap1Params(wap1Params);
		}
		return _createUrl.getUrl(url);
	}

	private void scheduleDialogMessage(final String msg) {
		UiApplication.getUiApplication().invokeLater(
			new Runnable() {
				public void run() {
					removeProgressIndicator(); 
					Dialog.alert(msg);
				}
			}
		);
	}
	public void setUrlText(Object userData, String url, String text, boolean errFlag, String errMsg) {
		Integer ing = (Integer)userData;
		int type = ing.intValue();
		if(!errFlag) {
			_resultStr += "Type:" + HttpCreateUrl.getTransportTypeName(type)+ "\n"  + "Url:" + url + "\n" +  "Passed\n" + "len=" + text.length() + "\n";
			//scheduleDialogMessage(url + " " + text); 
		}
		else {
			_resultStr += "Type:" + HttpCreateUrl.getTransportTypeName(type)+ "\n"  +  "Url:" + url + "\n" + "Failed\n" + "err=" + errMsg + "\n";
			//scheduleDialogMessage(url + " " + errMsg);
		}
	}
	public void allUrlsFetched() {
		scheduleDialogMessage(_resultStr);
	}
	public void unforeSeenException(String err) {
		scheduleDialogMessage(err);
	}

	private void fetchUrl(String url, short type) {
		String fixedUrl = getUrl(url, type);
		if(fixedUrl != null) {
			AsynchronousHttpUrlGetter ahug = new AsynchronousHttpUrlGetter(this);
		       Thread thread = new Thread(ahug);	
		       thread.start();
		}
		else {
			Dialog.alert("Null url");
		}
	}

	private void fetchSelectedUrls() {
		CheckboxField[] checkBoxes = {_default, _bes, _directTcp, _wap2, _wifi, _wap1};
		AsynchronousHttpUrlGetter ahug = new AsynchronousHttpUrlGetter(this);
		boolean somethingSelected = false;
		_testUrl = _urlField.getText();
		if(_default.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.DEFAULT);
			ahug.addUrl(new Integer(HttpCreateUrl.DEFAULT), fixedUrl);
			somethingSelected = true;
		}
		if(_bes.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.BES_MDS);
			ahug.addUrl(new Integer(HttpCreateUrl.BES_MDS), fixedUrl);
			somethingSelected = true;
		}
		if(_directTcp.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.DIRECT_TCP);
			ahug.addUrl(new Integer(HttpCreateUrl.DIRECT_TCP), fixedUrl);
			somethingSelected = true;
		}
		if(_wap2.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.WAP2);
			ahug.addUrl(new Integer(HttpCreateUrl.WAP2), fixedUrl);
			somethingSelected = true;
		}
		if(_wifi.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.WIFI);
			ahug.addUrl(new Integer(HttpCreateUrl.WIFI), fixedUrl);
			somethingSelected = true;
		}
		if(_wap1.getChecked()) {
			String fixedUrl = getUrl(_testUrl, HttpCreateUrl.WAP1);
			if(fixedUrl != null) {
				ahug.addUrl(new Integer(HttpCreateUrl.WAP1), fixedUrl);
				somethingSelected = true;
			}
			else {
				Dialog.alert("You must specify parameters for WAP1.0");
			}
		}
		if(somethingSelected) {
			_resultStr = "";
			addProgressIndicator(); 
			Thread thr = new Thread(ahug);
			thr.start();
		}
		else {
			Dialog.alert("Please select transport layer to diagnose.");
		}
	}

	private void replayAction() {
		fetchSelectedUrls();
		/*
		String url = "http://www.google.com";
		//fetchUrl(url, HttpCreateUrl.BES_MDS);
		fetchUrl(url, HttpCreateUrl.DIRECT_TCP);
		short[]types = {
			HttpCreateUrl.BES_MDS ,
			HttpCreateUrl.WAP2,
			HttpCreateUrl.WAP1,
			HttpCreateUrl.DIRECT_TCP,
			HttpCreateUrl.WIFI
		};
		for(int ii=0 ; ii<types.length; ii++) {
			System.out.println(getUrl(url, types[ii])); 
		}
		*/

	}
};
