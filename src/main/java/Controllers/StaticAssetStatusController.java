package Controllers;

import dao.StaticAssetStatusDao;
import entities.StaticAssetStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
@RestController
public class StaticAssetStatusController {

    StaticAssetStatusDao staticAssetStatusService;

    @RequestMapping(value = "/staticAssetStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaticAssetStatus>> listAllStaticAssetStatuses(HttpServletRequest request) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        staticAssetStatusService = (StaticAssetStatusDao) ctx.getBean("staticAssetStatusDao");
        List<StaticAssetStatus> goodsGroupList = staticAssetStatusService.getAll();
        if (goodsGroupList.isEmpty()) {
            return new ResponseEntity<List<StaticAssetStatus>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<StaticAssetStatus>>(goodsGroupList, HttpStatus.OK);
    }
}
