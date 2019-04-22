package vip.wen.spring.framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

public class GPViewResolver {

    private final static String DEFAULT_TEPLATE_SUFFX = ".html";
    private File templateRootDir;
    //private String viewName;

    public GPViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    //解析文件，成为view
    public GPView resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEPLATE_SUFFX)?viewName:(viewName+".html");
        File templateFile = new File((templateRootDir.getPath()+"/"+viewName).replaceAll("/+","/"));
        return new GPView(templateFile);
    }

    public File getTemplateFile() {
        return templateRootDir;
    }

}
