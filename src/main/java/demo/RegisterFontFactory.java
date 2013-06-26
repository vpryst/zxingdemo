package demo;

import java.util.ResourceBundle;

public class RegisterFontFactory {

    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        ResourceBundle bundle = ResourceBundle.getBundle("font");
        System.out.println(bundle.getStringArray("font").toString());
        
        
    }

}
