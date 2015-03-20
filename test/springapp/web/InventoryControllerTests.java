package springapp.web;

import java.util.Map;
import java.util.ArrayList;

import org.springframework.web.servlet.ModelAndView;
import junit.framework.TestCase;

import springapp.domain.Product;
import springapp.repository.InMemoryProductDao;

import springapp.web.InventoryController;
import springapp.service.SimpleProductManager;

public class InventoryControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{   
        InventoryController controller = new InventoryController();
        SimpleProductManager spm = new SimpleProductManager();
        spm.setProductDao(new InMemoryProductDao(new ArrayList<Product>()));
        controller.setProductManager(spm);

        ModelAndView modelAndView = controller.handleRequest(null, null);   
        assertEquals("hello", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        Map modelMap = (Map) modelAndView.getModel().get("model");
        String nowValue = (String) modelMap.get("now");
        assertNotNull(nowValue);
    }
}
