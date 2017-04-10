package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Тёма on 02.12.2016.
 */
@Controller
public class messageConroller {
    @RequestMapping(value = "/user/addErrorMessage", method = RequestMethod.GET)
    public ModelAndView getAddErrorMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Пользователь с таким логином уже существует");
        model.setViewName("errorMessage");
        return  model;

    }
    @RequestMapping(value = "/user/addSuccessMessage", method = RequestMethod.GET)
    public ModelAndView getAddSuccessMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Пользователь успешно добавлен в ссистему!");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/user/updateErrorMessage", method = RequestMethod.GET)
    public ModelAndView getUpdateErrorMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Пользователь c таким логином не найден");
        model.setViewName("warningMessage");
        return  model;

    }
    @RequestMapping(value = "/user/updateSuccessMessage", method = RequestMethod.GET)
    public ModelAndView getUpdateSuccessMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Данные о пользователе успешно обновлены!");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/user/deleteErrorMessage", method = RequestMethod.GET)
    public ModelAndView getDeleteErrorMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Пользователь с таким логином не найден не найден");
        model.setViewName("warningMessage");
        return  model;

    }
    @RequestMapping(value = "/user/deleteSuccessMessage", method = RequestMethod.GET)
    public ModelAndView getDeleteSuccessMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Данные о пользователе и его работе успешно удалены!");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/getWarningMessage", method = RequestMethod.GET)
    public ModelAndView getErrorMessageGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","На данный момент не создано ни одной группы товаров");
        model.setViewName("warningMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/deleteSuccessMessage", method = RequestMethod.GET)
    public ModelAndView addSuccessMessageGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Группа товаров успешно удалена");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/addGoodsSuccessMessage", method = RequestMethod.GET)
    public ModelAndView successMessageaddGoodsToGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Товар добавлен в  грппу");
        model.setViewName("successMessage");
        return  model;

    }    @RequestMapping(value = "/goodsAdminPage/addGoodsErrorMessage", method = RequestMethod.GET)
    public ModelAndView errorMessageaddGoodsToGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Ошибки при добавлении товара в группу");
        model.setViewName("errorMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/deleteGoodsErrorMessage", method = RequestMethod.GET)
    public ModelAndView errorMessageDeleteGoodsToGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Ошибки при удалении товара");
        model.setViewName("errorMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/deleteGoodsSuccessMessage", method = RequestMethod.GET)
    public ModelAndView successMessageDeleteGoodsToGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Товар удален из списка продаж");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/deleteErrorMessage", method = RequestMethod.GET)
    public ModelAndView addErrorMessageGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","При удалении группы произошли ошибки(группа с таким названием нет)");
        model.setViewName("errorMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/updateGoodsErrorMessage", method = RequestMethod.GET)
    public ModelAndView updateErrorMessageGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","При обновлении инормации об товаре произошли ошибки");
        model.setViewName("errorMessage");
        return  model;
    }


    @RequestMapping(value = "/goodsAdminPage/deleteErrorMessageGroupIsNotEmpty", method = RequestMethod.GET)
    public ModelAndView deleteErrorMessageGroupIsNotEmpty() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Удаление группы невозможно, так как не реализованы (не удалены) товары группы");
        model.setViewName("warningMessage");
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage/applicationList/getWarningMessage", method = RequestMethod.GET)
    public ModelAndView listApplicationErrorMessageListIsEmpty() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Список заявок пуст");
        model.setViewName("warningMessage");
        return  model;
    }
    @RequestMapping(value = "/goodsAdminPage/updateGoodsSuccessMessage", method = RequestMethod.GET)
    public ModelAndView updateSuccessMessageGroupGoods() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Информация об товаре обновлена успешно");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/addApplicationSuccessMessage", method = RequestMethod.GET)
    public ModelAndView addApplicationSuccessMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Заявка на товар успешно создана");
        model.setViewName("successMessage");
        return  model;

    }
    @RequestMapping(value = "/goodsAdminPage/addApplicationErrorMessage", method = RequestMethod.GET)
    public ModelAndView addApplicationУккщкMessage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message","Заявка на товар не создана из-за ошибок");
        model.setViewName("warningMessage");
        return  model;

    }
}
