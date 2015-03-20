package springapp.web;

import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.validation.BindingResult;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import org.springframework.ui.ModelMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import springapp.service.ProductManager;
import springapp.service.PriceIncrease;
import springapp.service.PriceIncreaseValidator;

@Controller
@RequestMapping("/priceincrease.htm")
public class PriceIncreaseFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    PriceIncreaseValidator priceIncreaseValidator; 

    @Autowired 
    private ProductManager productManager;

    @RequestMapping(method=RequestMethod.POST)
    public String onSubmit(@ModelAttribute("priceIncrease")PriceIncrease priceIncrease, BindingResult result)
        throws ServletException {

        priceIncreaseValidator.validate(priceIncrease, result);

        if (result.hasErrors()) {
            return "priceincrease";
        }

        int increase = priceIncrease.getPercentage();
        logger.info("Increasing prices by " + increase + "%.");
        productManager.increasePrice(increase);

        logger.info("returning from PriceIncreaseForm view to home.htm");

        return "redirect:hello.htm";
    }

    @RequestMapping(method=RequestMethod.GET)
    public String initializeForm(ModelMap model) {
        Map<Integer, String> priority = new LinkedHashMap<Integer, String>();
        priority.put(1, "low");
        priority.put(2, "medium");
        priority.put(3, "high");

        PriceIncrease priceIncrease = new PriceIncrease();  //Not sure of constructor
        model.addAttribute("priceIncrease", priceIncrease);

        model.addAttribute("priorityList", priority);
        return "priceincrease";
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        PriceIncrease priceIncrease = new PriceIncrease();
        priceIncrease.setPercentage(20);
        return priceIncrease;
    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }
}
