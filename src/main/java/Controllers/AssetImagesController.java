package Controllers;

import dao.AssetImageDao;
import dao.goodsDao;
import dao.goodsGroupDao;
import entities.AssetImage;
import entities.Goodsgroup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Тёма on 13.04.2017.
 */
@RestController
public class AssetImagesController {

    goodsDao goodsService;
    AssetImageDao assetImageService;

    //-------------------Retrieve Model AssetImage--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/form/image/{idAsset}", method = RequestMethod.GET)
    public ModelAndView getGoodsAdminPageGssetGormImage(@PathVariable("idAsset") int id) {
        ModelAndView model = new ModelAndView();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        goodsService = (goodsDao) ctx.getBean("goodsDao");
        model.addObject("nameAsset", goodsService.findById(id).getAssetNameModel());
        model.addObject("inventNumber", id);

        model.setViewName("assetImage");
        return model;
    }
    //-------------------Retrieve List AssetImage--------------------------------------------------------

    @RequestMapping(value = "/goodsAdminPage/asset/path/to/image/{idAsset}", method = RequestMethod.GET)
    public ResponseEntity<List<AssetImage>> goodsAdminPageAssetPathToImage(@PathVariable("idAsset") int id) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        assetImageService = (AssetImageDao) ctx.getBean("assetImageDao");
        List<AssetImage> images = assetImageService.getAllImagesByAsset(id);
        if (images.isEmpty()) {
            return new ResponseEntity<List<AssetImage>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<AssetImage>>(images, HttpStatus.OK);
    }

    //-------------------Delete Single AssetImage--------------------------------------------------------

    @RequestMapping(value = "/delete/asset/image/{idImage}", method = RequestMethod.GET)
    public ModelAndView deleteAssetImage(@PathVariable("idImage") int idImage) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        assetImageService = (AssetImageDao) ctx.getBean("assetImageDao");
        ModelAndView model = new ModelAndView();
        AssetImage assetImage = assetImageService.findById(idImage);
        if (assetImage == null) {
            model.addObject("message", "Произошли ошибки при удалении Изображения имущества - изображение не найдено!");
            model.setViewName("errorMessage");
            return model;
        }
        if (assetImageService.deleteImage(assetImage)) {
            model.addObject("message", "Изображение удалено успешно");
            model.setViewName("successMessage");
        } else {
            model.addObject("message", "Произошли ошибки при удалении Изображения имущества ");
            model.setViewName("errorMessage");
        }
        return model;


    }
    //-------------------Retrieve Single AssetImage--------------------------------------------------------

    @RequestMapping(value = "/assetImage/{idImage}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetImage> getAssetImage(@PathVariable("idImage") int idImage) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        assetImageService = (AssetImageDao) ctx.getBean("assetImageDao");
        AssetImage assetImage = assetImageService.findById(idImage);
        if (assetImage == null) {
            return new ResponseEntity<AssetImage>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AssetImage>(assetImage, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/image/for/asset", method = RequestMethod.POST)
    public ModelAndView createGroup(HttpServletRequest request,
                                    @RequestParam("groupImageID") int groupImageID,
                                    @RequestParam("image") MultipartFile file) throws IOException {
        ModelAndView model = new ModelAndView();
        model.setViewName("goodsAdminPage");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        assetImageService = (AssetImageDao) ctx.getBean("assetImageDao");
        AssetImage img = new AssetImage();
        img.setAssetIdAsset(groupImageID);
        String str = "";
        if (!file.isEmpty()) {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            str = file.getName()+"_"+assetImageService.count()+1+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.') , file.getOriginalFilename().length());
            File destination = new File(request.getSession().getServletContext().getRealPath("/resources/img/"), str);

            ImageIO.write(src, str.substring(str.indexOf('.') + 1, str.length()), destination);
            img.setNameImage(str);
        }
        assetImageService.save(img);
        return model;
    }
}
