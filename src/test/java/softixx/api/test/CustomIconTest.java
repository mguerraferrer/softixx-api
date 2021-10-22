package softixx.api.test;

import lombok.val;
import softixx.api.bean.IconBean;
import softixx.api.bean.IconBean.CustomIcon;

public class CustomIconTest {
    public static void main(String[] args) {
        val success = "success-icon";
        val error = "error-icon";
        val info = "info-icon";
        val warning = "warning-icon";
        val dark = "dark-icon";

        IconBean.instance(success, error, info, warning, dark);
        val successIcon = CustomIcon.SUCCESS.value();
        System.out.println("successIcon: " + successIcon);

        val errorIcon = CustomIcon.ERROR.value();
        System.out.println("errorIcon: " + errorIcon);

        val infoIcon = CustomIcon.INFO.value();
        System.out.println("infoIcon: " + infoIcon);

        val warningIcon = CustomIcon.WARNING.value();
        System.out.println("warningIcon: " + warningIcon);
        
        val darkIcon = CustomIcon.DARK.value();
        System.out.println("darkIcon: " + darkIcon);
    }
}
