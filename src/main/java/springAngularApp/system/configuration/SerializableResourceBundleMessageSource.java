package springAngularApp.system.configuration;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

/**
 * Retrieves all properties for selected locale from all properties files
 * which are in the message source directory.
 */
@Component //告诉spring要加载此类
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
	/**
	 * ReloadableResourceBundleMessageSource基于Java的ResourceBundle基础类实现，允许仅通过资源名加载国际化资源。
	 * 提供了定时刷新功能，允许在不重启系统的情况下，更新资源的信息。
	 * 
	 * ResourceBundle读取资源属性文件（properties），
	 * 然后根据.properties文件的名称信息（本地化信息），匹配当前系统的国别语言信息（也可以程序指定），
	 * 然后获取相应的properties文件的内容。
	 * 这个properties文件的名字是有规范的：一般的命名规范是： 自定义名_语言代码_国别代码.properties；
	 * 如果是默认的，直接写为：自定义名.properties。
	 */

    private static final String MESSAGES_FILE_SUFFIX = ".properties";

    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        String[] propertyFilesNames = getMessagesFiles().stream() //java8特性，流式操作
                .map(this::getFileSystemResourcesFile)
                .distinct()	//去重
                .toArray(String[]::new);//最终获取的有file:E:\example-WEB\SpringAngularApp\build\resources\main\messages\messages
        								// 和file:E:\example-WEB\SpringAngularApp\build\resources\main\messages\ users\messages
        setBasenames(propertyFilesNames);
        return getMergedProperties(locale).getProperties();
    }

    private List<File> getMessagesFiles() {
        URL messageSource = getClass().getClassLoader().getResource(getMessageSource());
        if(messageSource == null){
            return emptyList();
        }	
        File messageSourceFolder = new File(messageSource.getFile());
        return (List<File>) listFiles(messageSourceFolder, 	//查询到此目录下的全部符合条件的文件
                new SuffixFileFilter(MESSAGES_FILE_SUFFIX),
                TrueFileFilter.INSTANCE);	// Use TrueFileFilter.INSTANCE to match all directories.
    }

    /**
     * Prepares file path to be used as spring resource
     * @param file gives the path to the file to process
     * @return file path without suffix wrapped with "file:" at the start.
     * e.g. messages_en.properties -> messages
     * e.g. messages.properties -> messages
     */
    private String getFileSystemResourcesFile(File file) {
        String targetFileName = file.getName();
        targetFileName = substringBeforeLast(targetFileName, "_");//从最后截取不包含此元素的字符串，如：a_bc_d→a_bc
        targetFileName = substringBeforeLast(targetFileName, ".");
        String filePathWithoutSuffix = file.getPath().replace(file.getName(), targetFileName);
        return "file:".concat(filePathWithoutSuffix);
    }

    protected String getMessageSource() {
        return "messages";
    }

}