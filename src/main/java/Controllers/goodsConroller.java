package Controllers;

import dao.applicationDao;
import dao.goodsDao;
import dao.goodsGroupDao;

import dao.userDao;
import entities.Application;
import entities.Asset;
import entities.Goodsgroup;
import entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Тёма on 04.12.2016.
 */

@RestController
public class goodsConroller {

    goodsGroupDao goodsGroupService;
    goodsDao goodsService;
    userDao userService;
    applicationDao applicationService;

    @RequestMapping(value = "/goodsAdminPage/data/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Goodsgroup> getGroup(@PathVariable("name") String name) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        System.out.println("Fetching Group with id " + name);
        Goodsgroup group = goodsGroupService.findGroupByName(name);
        if (group == null) {
            System.out.println("Goods with id " + name + " not found");
            return new ResponseEntity<Goodsgroup>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Goodsgroup>(group, HttpStatus.OK);
    }

    @RequestMapping(value = "/goodsAdminPage/data/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGroup(@PathVariable("name") String name) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Fetching & Deleting Goodsgroup with id " + name);

        Goodsgroup group = goodsGroupService.findGroupByName(name);
        if (group == null) {
            System.out.println("Unable to delete. Group with id " + name + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if(goodsService.findAllAssets(name).isEmpty()) {
            if (goodsGroupService.deleteGroup(group))
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            else return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/goodsAdminPage/data", method = RequestMethod.GET)
    public ResponseEntity<List<Goodsgroup>> listAllGoodsgroups(HttpServletRequest request) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        List<Goodsgroup> goodsgroup = goodsGroupService.findAllGoodsgroups();
        if (goodsgroup.isEmpty()) {
            return new ResponseEntity<List<Goodsgroup>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Goodsgroup>>(goodsgroup, HttpStatus.OK);
    }

    @RequestMapping(value = "/goodsAdminPage/data", method = RequestMethod.POST)
    public ModelAndView createGroup(HttpServletRequest request, @RequestParam("image") MultipartFile file,
                                                   @RequestParam("groupname")  String  groupname) throws IOException {
        ModelAndView model=new ModelAndView();
        model.setViewName("goodsAdminPage");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        Goodsgroup group=new Goodsgroup();
        group.setName(groupname);
        String str="";
        if (!file.isEmpty()) {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            File destination =  new File(request.getSession().getServletContext().getRealPath("/resources/img/"),file.getOriginalFilename());
            str = file.getOriginalFilename();
            ImageIO.write(src, str.substring(str.indexOf('.') + 1, str.length()), destination);
            group.setImage(str);
        }
        System.out.println("Creating Group " + group.getName());
        if (goodsGroupService.isUserExist(group)) {
            System.out.println("Группа с таким названием уже существует" + group.getName() + " уже существует");
            return model;
        }
        goodsGroupService.saveGroup(group);
        return model;
    }
//-------------------Create a goods in group--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset", method = RequestMethod.POST)
    public ResponseEntity<Void> createGoodsInGroup(Principal admin, @RequestBody Asset asset, UriComponentsBuilder ucBuilder) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Creating Goods " + asset.getAssetNameModel()+" in group "+asset.getGoodsGroupName()+ " by "+admin.getName());

        asset.setUsername(admin.getName());
        if (goodsService.saveGoods(asset)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/goodsAdminPage/asset").buildAndExpand(asset.getUsername()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
    //-------------------Retrieve All Goods--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/listGoodsGroups/goodsPage/{groupname}", method = RequestMethod.GET)
    public ResponseEntity<List<Asset>> listAllGoods(@PathVariable("groupname") String groupname) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        userService = (userDao) ctx.getBean("userDao");
        List<Asset> goods = goodsService.findAllAssets(groupname);
        if (goods.isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.NO_CONTENT);
        }

         for(Asset good:goods)
         {
             Users user = userService.findByUserName(good.getUsername());
             good.setUsername(user.getSurname()+" "+user.getName()+" "+user.getFathername());
             good.setGoodsGroupName(user.getEmail()+"\n"+user.getPhone());
         }

        return new ResponseEntity<List<Asset>>(goods, HttpStatus.OK);
    }
    //-------------------Retrieve Single Goods--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/{idAsset}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Asset> getAsset(@PathVariable("idAsset") int idAsset) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Fetching Asset with id " + idAsset);
        Asset asset = goodsService.findById(idAsset);
        if (asset == null) {
            System.out.println("Asset with id " + idAsset + " not found");
            return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Asset>(asset, HttpStatus.OK);
    }
    //------------------- Delete a Asset --------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/{idAsset}", method = RequestMethod.DELETE)
    public ResponseEntity<Asset> deleteAsset(@PathVariable("idAsset") int idAsset){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Fetching & Deleting Asset with id " + idAsset);

        Asset asset = goodsService.findById(idAsset);
        if (asset == null) {
            System.out.println("Asset with id " + idAsset + " not found");
            return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
        }
        if (goodsService.deleteGoods(asset))
            return new ResponseEntity<Asset>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
    }

    //------------------- Update a Asset --------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/{idAsset}", method = RequestMethod.PUT)
    public ResponseEntity<Asset> updateUser(Principal admin,@PathVariable("idAsset") int idAsset, @RequestBody Asset asset) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Updating Asset " + idAsset);
        if (!goodsService.isAssetExist(asset)) {
            System.out.println("Asset with id " + idAsset + " not found");
            return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
        }
        asset.setUsername(admin.getName());
        System.out.println("Updating Asset " + asset.getAssetNameModel()+" "+asset.getGoodsGroupName());
        if (goodsService.updateAsset(asset)) {
            return new ResponseEntity<Asset>(asset, HttpStatus.OK);
        } else return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
    }
//------------------Create Application--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/application", method = RequestMethod.POST)
    public ResponseEntity<Void> createApplication(@RequestBody Application appl, UriComponentsBuilder ucBuilder) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        System.out.println("Creating Application " + appl.getDate()+" id= "+appl.getIdapplication());
        if (applicationService.saveApplication(appl)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/goodsAdminPage/application").buildAndExpand(appl.getIdapplication()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
    //------------------Get List Application--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/application", method = RequestMethod.GET)
    public ResponseEntity<List<Application>> getListApplication() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        System.out.println("List Applications");
        List<Application> list= applicationService.findAllApplications();
        if (list.isEmpty()) {
            return new ResponseEntity<List<Application>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Application>>(list, HttpStatus.OK);
    }
}
