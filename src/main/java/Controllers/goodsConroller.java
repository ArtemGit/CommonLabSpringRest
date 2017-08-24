package Controllers;

import classes.ApplicationStatus;
import dao.*;
import entities.*;
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
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Тёма on 04.12.2016.
 */

@RestController
public class goodsConroller {
    daoGroupMembers daoGroupMembersService;
    goodsGroupDao goodsGroupService;
    goodsDao goodsService;
    userDao userService;
    applicationDao applicationService;
    ClientDesireGroupsDao сlientDesireGroupsService;
    AssetImageDao assetImageService;

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

        if (goodsService.findAllAssets(name).isEmpty()) {
            if (goodsGroupService.deleteGroup(group))
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            else return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/goodsAdminPage/data", method = RequestMethod.GET)
    public ResponseEntity<List<Goodsgroup>> listAllGoodsgroups(HttpServletRequest request, Principal user) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        daoGroupMembersService = (daoGroupMembers) ctx.getBean("daoGroupMembers");
        List<Goodsgroup> goodsgroup = null;
        if (user != null) {
            if (daoGroupMembersService.findRoleUserByLogin(user.getName()) == 3) {
                //получить список   названий желаемых групп
                сlientDesireGroupsService = (ClientDesireGroupsDao) ctx.getBean("clientDesireGroupsDao");
                List<ClientDesireGroups> desireGroups = сlientDesireGroupsService.getAllForClient(user.getName());
                goodsgroup = goodsGroupService.findAllDesire(desireGroups);
            } else
                goodsgroup = goodsGroupService.findAllGoodsgroups();
        } else goodsgroup = goodsGroupService.findAllGoodsgroups();
        if (goodsgroup.isEmpty()) {
            return new ResponseEntity<List<Goodsgroup>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Goodsgroup>>(goodsgroup, HttpStatus.OK);
    }

    @RequestMapping(value = "/goodsAdminPage/data", method = RequestMethod.POST)
    public ModelAndView createGroup(HttpServletRequest request, @RequestParam("image") MultipartFile file,
                                    @RequestParam("groupname") String groupname) throws IOException {
        ModelAndView model = new ModelAndView();
        model.setViewName("goodsAdminPage");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsGroupService = (goodsGroupDao) ctx.getBean("goodsGroupDao");
        Goodsgroup group = new Goodsgroup();
        group.setName(groupname);
        String str = "";
        if (!file.isEmpty()) {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            str = file.getName() + "_" + (goodsGroupService.count() + 1) + file.getOriginalFilename()
                    .substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());

            File destination = new File(request.getSession().getServletContext().getRealPath("/resources/img/"), str);
            //  str = file.getName()+"_"+goodsGroupService.count()+file.getOriginalFilename().substring(str.indexOf('.') , file.getOriginalFilename().length());
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
        System.out.println("Creating Goods " + asset.getAssetNameModel() + " in group " + asset.getGoodsGroupName() + " by " + admin.getName());

        asset.setUsername(admin.getName());
        asset.setEnable((byte) 1);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        asset.setDateRegistration(format.format(new Date()));
        if (goodsService.saveGoods(asset)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/goodsAdminPage/asset").buildAndExpand(asset.getUsername()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
    //-------------------Retrieve All Goods--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/listGoodsGroups/goodsPage/{groupname}", method = RequestMethod.GET)
    public ResponseEntity<List<Asset>> listAllGoods(@PathVariable("groupname") String groupname, Principal admin) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        userService = (userDao) ctx.getBean("userDao");
        List<Asset> goods = goodsService.findAllAssets(groupname);
        goods = goods.stream()
                .filter(g -> g.getEnable() == 1)
                .collect(Collectors.toList());
        if (goods.isEmpty()) {
            return new ResponseEntity<List<Asset>>(HttpStatus.NO_CONTENT);
        }
        goods.removeIf(g -> (!g.getUsername().equals(admin.getName())
                && userService.findByUserName(admin.getName()).getPosition() != null));
        for (Asset good : goods) {
            Users user = userService.findByUserName(good.getUsername());
            good.setUsername(user.getSurname() + " " + user.getName() + " " + user.getFathername());
            good.setGoodsGroupName(user.getEmail() + " \n" + user.getPhone());
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
    public ResponseEntity<Asset> deleteAsset(@PathVariable("idAsset") int idAsset) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        assetImageService = (AssetImageDao) ctx.getBean("assetImageDao");
        System.out.println("Fetching & Deleting Asset with id " + idAsset);

        Asset asset = goodsService.findById(idAsset);
        if (asset == null || applicationService.existApplicationForAsset(idAsset)) {
            System.out.println("Asset with id " + idAsset + " not found");
            return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
        }
        if (assetImageService.deleteImage(idAsset))
            if (goodsService.deleteGoods(asset))
                return new ResponseEntity<Asset>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
    }
    //------------------- Update a Asset --------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/{idAsset}", method = RequestMethod.PUT)
    public ResponseEntity<Asset> updateUser(Principal admin, @PathVariable("idAsset") int idAsset, @RequestBody Asset asset) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        if (!goodsService.isAssetExist(asset)) {
            System.out.println("Asset with id " + idAsset + " not found");
            return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
        }
        asset.setUsername(admin.getName());
        System.out.println("Updating Asset " + asset.getAssetNameModel() + " " + asset.getGoodsGroupName());
        if (goodsService.updateAsset(asset)) {
            return new ResponseEntity<Asset>(asset, HttpStatus.OK);
        } else return new ResponseEntity<Asset>(HttpStatus.NOT_FOUND);
    }
//------------------Create Application--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/application", method = RequestMethod.POST)
    public ResponseEntity<Void> createApplication(@RequestBody Application appl, UriComponentsBuilder ucBuilder) throws UnsupportedEncodingException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        System.out.println("Creating Application " + appl.getDate() + " id= " + appl.getIdapplication());
        appl.setStatus(ApplicationStatus.ACTIVE.getStatus());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/goodsAdminPage/application").buildAndExpand(appl.getIdapplication()).toUri());
        Application assetApplication = applicationService.findApplicationByAssetIdAndPhone(appl.getAssetIdAsset(), appl.getPhone());

        if (assetApplication == null) {
            Asset asset = goodsService.findById(appl.getAssetIdAsset());
            if ((asset) != null)
                if (asset.getEnable() == 0)
                    return new ResponseEntity(HttpStatus.CONFLICT);
            if (applicationService.saveApplication(appl))
                return new ResponseEntity(HttpStatus.CREATED);
            else
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.FOUND);

    }
    //------------------Get List Application--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/application", method = RequestMethod.GET)
    public ResponseEntity<List<Application>> getListApplication(Principal user) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        System.out.println("List Applications");
        List<Application> list;

        if (daoGroupMembersService.findRoleUserByLogin(user.getName()) == 3) {
            //получить список   заявок для пользователя  ---т к  логи клиента его телефон,то ищем по телефону -лошину
            list = applicationService.getAllForClient(user.getName());
        } else
            list = applicationService.findAllApplications();
        if (list.isEmpty()) {
            return new ResponseEntity<List<Application>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Application>>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/currentClient", method = RequestMethod.GET)
    public ResponseEntity<Users> getCurrentClient(Principal user) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (userDao) ctx.getBean("userDao");
        Users currentUser = userService.findByUserName(user.getName());
        if (currentUser == null) {
            return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Users>(currentUser, HttpStatus.OK);
    }
    //------------------- Update an Application --------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/application", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateApplication(@RequestBody Application appl) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationService = (applicationDao) ctx.getBean("applicationDao");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        if (applicationService.findApplicationByAssetIdAndPhone(appl.getAssetIdAsset(), appl.getPhone()) == null
                || ApplicationStatus.valueOf(appl.getStatus()) == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        appl.setDate(date);
        if (applicationService.updateApplication(appl)) {
            if (appl.getStatus() == 6) {
                Asset asset = goodsService.findById(appl.getAssetIdAsset());
                asset.setEnable((byte) 0);
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                asset.setDateRegistration(format.format(date));
                asset.setAssetCost(appl.getPrice().doubleValue());
                if (goodsService.updateAsset(asset))
                    return new ResponseEntity<Void>(HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    //-------------------Retrieve All Goods--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/listGoodsGroups/{filter}/{groupname}", method = RequestMethod.GET)
    public ResponseEntity<List<Asset>> listAllGoodsBy(@PathVariable("filter") String filter,
                                                      @PathVariable("groupname") String groupname,
                                                      Principal admin) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        userService = (userDao) ctx.getBean("userDao");
        List<Asset> goods = goodsService.findAllAssets(groupname);
        goods.removeIf(g -> (!g.getUsername().equals(admin.getName())
                && userService.findByUserName(admin.getName()).getPosition() != null));
        if (filter.equals("release")) {
            goods = goods.stream()
                    .filter(g -> g.getEnable() == 0)
                    .collect(Collectors.toList());
        } else {
            if (filter.equals("old")) {
                goods = goods.stream()
                        .filter(g -> g.getEnable() == 1)
                        .collect(Collectors.toList());
                goods.removeIf(asset -> {
                    try {
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = format.parse(asset.getDateRegistration());
                        Instant instant = Instant.ofEpochMilli(date.getTime());
                        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        if (!LocalDateTime.now().minusDays(60).isAfter(ldt))
                            return true;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                });
            } else goods = null;
        }
        if (goods.isEmpty() || goods == null) {
            return new ResponseEntity<List<Asset>>(HttpStatus.NO_CONTENT);
        }

        for (Asset good : goods) {
            Users user = userService.findByUserName(good.getUsername());
            good.setUsername(user.getSurname() + " " + user.getName() + " " + user.getFathername());
            good.setGoodsGroupName(user.getEmail() + " \n" + user.getPhone());
        }

        return new ResponseEntity<List<Asset>>(goods, HttpStatus.OK);
    }


}
