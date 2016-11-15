package tsi2.yuber.common.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import tsi2.yuber.common.config.ConstantsConfig;
import tsi2.yuber.common.data.Theme;
import tsi2.yuber.common.services.ThemeService;

 
@ManagedBean
public class ThemeSwitcher {
 
    private List<Theme> themes;
     
    @ManagedProperty(value=ConstantsConfig.THEME_SERVICE_BEAN)
    private ThemeService service;
 
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
     
    public List<Theme> getThemes() {
        return themes;
    } 
 
    public void setService(ThemeService service) {
        this.service = service;
    }

}