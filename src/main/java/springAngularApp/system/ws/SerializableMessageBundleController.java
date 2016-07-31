package springAngularApp.system.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.configuration.SerializableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * 此方法的引用路径在 webapp/scripts/app.js中
 * @author XieWenqian
 *
 */
@RestController	//相当于@ResponseBody ＋ @Controller合在一起的作用,不需要在每个方法都添加 @ResponseBody
@RequestMapping("/ws/messageBundle/properties")	//是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class SerializableMessageBundleController {

    @Autowired private SerializableResourceBundleMessageSource messageBundle;

    @RequestMapping(method = RequestMethod.GET)
    public Properties properties(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }

}