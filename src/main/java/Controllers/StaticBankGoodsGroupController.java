package Controllers;

import dao.ClientDesireGroupsDao;
import dao.StaticGoodsGroupDao;
import dao.userDao;
import entities.StaticGoodsGroup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Тёма on 11.04.2017.
 */
@RestController
public class StaticBankGoodsGroupController {

    StaticGoodsGroupDao staticGoodsGroupService;
    ClientDesireGroupsDao clientDesireGroupsService;
    userDao userService;


    @RequestMapping(value = "/staticGoodsGroup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaticGoodsGroup>> listAllGoodsgroups(HttpServletRequest request) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        staticGoodsGroupService = (StaticGoodsGroupDao) ctx.getBean("staticGoodsGroupDao");
        List<StaticGoodsGroup> goodsGroupList = staticGoodsGroupService.getAll();
        if (goodsGroupList.isEmpty()) {
            return new ResponseEntity<List<StaticGoodsGroup>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<StaticGoodsGroup>>(goodsGroupList, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/desire/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addClientDesireGroupsToDB
            (HttpServletRequest request, @RequestParam(value = "myArray[]") String[] listGroupsChooseByClient) {
        String userName = listGroupsChooseByClient[listGroupsChooseByClient.length - 1];
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        clientDesireGroupsService = (ClientDesireGroupsDao) ctx.getBean("clientDesireGroupsDao");
        userService = (userDao) ctx.getBean("userDao");
        String[] ar = new String[listGroupsChooseByClient.length - 1];
        for (int i = 0; i < listGroupsChooseByClient.length - 1; i++)
            ar[i] = listGroupsChooseByClient[i];
        HttpHeaders headers = new HttpHeaders();
        if (userService.findByUserName(userName) != null) {
            clientDesireGroupsService.saveAll(userName, Arrays.asList(ar));
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        }
        headers.add("x-filename", "Ошибка создания пользователя: возможно с такими данными уже существует");
        return new ResponseEntity<Void>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
