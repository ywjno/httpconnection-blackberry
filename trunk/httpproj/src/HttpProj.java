import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.system.Bitmap;
import java.lang.Thread;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.Dialog;
import java.util.Enumeration;
import java.io.IOException;



final class HttpProj extends UiApplication
{

    public static void main(String args[]) {
        new HttpProj().enterEventDispatcher();
    }
    HttpProj() {
	pushScreen(new HttpProjScreen());
    }

};
