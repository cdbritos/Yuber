package my.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocalesHelper {

    private static final String DEFAULT_MASSAGE_FILE = "messages";
    
    private static final Map<String, Locale> languageLocaleMap = new HashMap<String, Locale>();
    static {
        
                languageLocaleMap.put("en-us", Locale.US);
                languageLocaleMap.put("en", Locale.ENGLISH);
                languageLocaleMap.put("zh-cn", Locale.CHINA);
                languageLocaleMap.put("zh", Locale.CHINESE);
            }
    
    public static ResourceBundle getResourceBundleByAcceptLanguage(List<String> languages) {
        if (languages == null || languages.isEmpty()) {
            //System.out.println("Cannot detect locale, use default.");
            return getResourceBundle(DEFAULT_MASSAGE_FILE, null);
        } else {
            List<String> langs = getSeperatedLanguages(languages);
            //System.out.println("First language is: " + langs.get(0));
            //System.out.println("Detected locale is: " + languageLocaleMap.get(langs.get(0).toLowerCase()));
            return getResourceBundle(DEFAULT_MASSAGE_FILE, languageLocaleMap.get(langs.get(0).toLowerCase()));
        }
    }
    
    public static ResourceBundle getResourceBundle(String messageFileName, Locale locale) {
        return locale==null ? ResourceBundle.getBundle(messageFileName, Locale.ENGLISH) : ResourceBundle.getBundle(messageFileName,locale);
    }
    
    /**
     * Convert the given languages like "en-us,zh-cn;q=0.5" to "[en-us,zh-cn]"
     * @param languages
     * @return
     */
    protected static List<String> getSeperatedLanguages(List<String> languages) {
        if(languages.size()==0 || languages.size()>1) {
            return languages;
        }
        String langs = languages.get(0);
        String[] langsArray = langs.split(",");
        List<String> result = new ArrayList<String>();
        for(String lang : langsArray) {
            if(lang.indexOf(";")>0) {
                result.add(lang.substring(0, lang.indexOf(";")));
            } else {
                result.add(lang);
            }
        }
        return result;
    }
}
