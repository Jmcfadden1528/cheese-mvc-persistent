package org.launchcode.controllers;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller

@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;


    @RequestMapping(value = "")
        public String index(Model model) {
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "add")
    public String displayAddForm(Model model) {
        model.addAttribute("title", "Add a Menu");
        model.addAttribute("menu", new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {
        if (errors.hasErrors()) {
            return "menu/add";
        }
        menuDao.save(menu);
        return "redirect:view/" + menu.getId();

    }
// having trouble passing from one above to one below
    @RequestMapping(value = "view/{menuId}")
    public String viewMenu(Model model, @PathVariable int menuId) {
        Menu aMenu = menuDao.findOne(menuId);
        model.addAttribute("menu", aMenu);
        model.addAttribute("title", aMenu.getName());
        model.addAttribute("menuId", menuId);

        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}")
    public String addItem(Model model, @PathVariable int menuId){
        Menu aMenu = menuDao.findOne(menuId);
        AddMenuItemForm form = new AddMenuItemForm(aMenu, cheeseDao.findAll());

        model.addAttribute("title", "Add item to menu: " + aMenu.getName());
        model.addAttribute("form", form);

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm form, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "menu/add-item";
        }
        Menu theMenu = menuDao.findOne(form.getMenuId());
        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());

        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        int menuId = theMenu.getId();



        return "redirect:/menu/view/" + menuId;
    }
}