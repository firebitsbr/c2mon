package cern.c2mon.web.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 *
 * This is a super simple html page (we also add the username of the user to
 * show a personalised message once he is logged in).
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

  /**
   * This is the application home page.
   *
   * @return name of a jsp page which will be displayed
   */
  @RequestMapping(method = RequestMethod.GET)
  public String getCreateForm() {
    return "home";
  }

  /**
   * Returns a custom 404 error page.
   *
   * @param model Spring MVC Model instance to be filled in before jsp processes
   *          it
   * @return name of a jsp page which will be displayed
   */
  @RequestMapping("error/404")
  public String error404(final Model model) {
    model.addAttribute("errorMessage", "");
    model.addAttribute("title", "");
    return "error/404";
  }
}