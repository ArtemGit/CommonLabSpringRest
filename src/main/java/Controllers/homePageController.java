package Controllers;

import dao.applicationDao;
import dao.goodsDao;
import dao.userDao;
import dao.userDaoImpl;
import entities.Asset;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by Тёма on 23.11.2016.
 */
@Controller
public class homePageController {


   @RequestMapping(value = "/authFormGet", method = RequestMethod.GET)
    public ModelAndView addFormToMainContent() {
        ModelAndView model = new ModelAndView();
        model.setViewName("authform");
        return  model;

    }

    @RequestMapping(value = "/registrationFormGet", method = RequestMethod.GET)
    public ModelAndView addFormREgistrationClientToMainContent() {
        ModelAndView model = new ModelAndView();
        model.setViewName("registrationClient/clientRegistration");
        return  model;

    }

    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public ModelAndView adminUserPage() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userDao userService = (userDao) ctx.getBean("userDao");
        ModelAndView model = new ModelAndView();
        model.addObject("countWorkers",userService.countUsers());
        model.addObject("countVisitors",userService.countClients());
        model.setViewName("adminPage");
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage", method = RequestMethod.GET)
    public ModelAndView adminGoodsPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("goodsAdminPage");
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage/listGoodsGroups", method = RequestMethod.GET)
    public ModelAndView showListGroupsGoods() {
        ModelAndView model = new ModelAndView();
        model.setViewName("listGoodsGroups");
        return  model;
    }


    @RequestMapping(value = "/goodsAdminPage/formRegistrationGroupGoods", method = RequestMethod.GET)
    public ModelAndView showformRegistrationGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.setViewName("formRegistrationGroupGoods");
        return  model;
    }

    @RequestMapping(value = "/goodsAdminPage/listGoodsGroups/goodsPage/html/{groupname}", method = RequestMethod.GET)
    public  ModelAndView getGoodsPage(@PathVariable("groupname") String name) {
        ModelAndView model = new ModelAndView();
        model.addObject("group",name);
        model.setViewName("goodsPage");
        return model;
    }
    @RequestMapping(value = "/adminPage/UserRegistration", method = RequestMethod.GET)
    public ModelAndView getUserRegistrationForm() {
        ModelAndView model = new ModelAndView();
        model.setViewName("formRegistrationUsersForAdmin");
        return  model;
    }
    @RequestMapping(value = "/adminPage/tableUsers", method = RequestMethod.GET)
    public ModelAndView gettableUsers() {
        ModelAndView model = new ModelAndView();
        model.setViewName("userTable");
        return  model;
    }
    @RequestMapping(value = "/adminPage/tableClients", method = RequestMethod.GET)
    public ModelAndView gettableClients() {
        ModelAndView model = new ModelAndView();
        model.setViewName("clientTable");
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage/formApplicationForGoods/{status}", method = RequestMethod.GET)
    public ModelAndView getformApplicationForGoods(@PathVariable String status) {
        ModelAndView model = new ModelAndView();
        model.setViewName("applicationForm");
        if(status.equals("add"))
        model.addObject("status",true);
        if(status.equals("edit"))
            model.addObject("status",false);
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage/applicationList/listApplications", method = RequestMethod.GET)
    public ModelAndView getApplications() {
        ModelAndView model = new ModelAndView();
        model.setViewName("applicationsTable");
        return  model;
    }

    @RequestMapping(value = { "/", "/homePage" }, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Вы ввели некорректные данные для входа!");
        }

        model.setViewName("homePage");

        return model;

    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        // пока русский текст без локализации, хотя так не рекомендуется!
        if (user != null) {
            model.addObject("errorMsg", user.getName() + " у вас нет доступа к этой странице!");
        } else {
            model.addObject("errorMsg", "У вас нет доступа к этой странице!");
        }

        model.setViewName("accessDenied");
        return model;

    }

    @RequestMapping(value = "/application/form/{assetIdAsset}", method = RequestMethod.GET)
    public ModelAndView getformSetPriceRealisation(@PathVariable("assetIdAsset") int assetIdAsset) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsDao goodsService = (goodsDao) ctx.getBean("goodsDao");
        ModelAndView model = new ModelAndView();
        model.setViewName("formSetPriceRealisation");
        Asset asset=goodsService.findById(assetIdAsset);
        model.addObject("startCost",asset.getAssetCost());
        model.addObject("assetName",asset.getAssetNameModel());
        return  model;
    }
}
