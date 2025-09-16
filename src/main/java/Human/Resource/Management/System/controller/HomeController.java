package Human.Resource.Management.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/dashboard/index"})
    public String dashboardHome() {
        return "dashboard/index"; // Matches src/main/resources/templates/employee/index.html
    }
}
