package Controllers;

import dao.StaticGoodsGroupDao;
import entities.StaticGoodsGroup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Тёма on 11.04.2017.
 */
@RestController
@RequestMapping(value = "/staticGoodsGroup")
public class StaticBankGoodsGroupController {

    StaticGoodsGroupDao staticGoodsGroupService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<StaticGoodsGroup>> listAllGoodsgroups(HttpServletRequest request) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        staticGoodsGroupService = (StaticGoodsGroupDao) ctx.getBean("staticGoodsGroupDao");
        List<StaticGoodsGroup> goodsGroupList = staticGoodsGroupService.getAll();
        if (goodsGroupList.isEmpty()) {
            return new ResponseEntity<List<StaticGoodsGroup>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<StaticGoodsGroup>>(goodsGroupList, HttpStatus.OK);
    }
}
