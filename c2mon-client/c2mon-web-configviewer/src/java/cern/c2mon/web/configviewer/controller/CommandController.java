package cern.c2mon.web.configviewer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cern.c2mon.web.configviewer.service.CommandService;
import cern.c2mon.web.configviewer.service.TagIdException;
import cern.c2mon.web.configviewer.service.TagService;
import cern.c2mon.web.configviewer.util.FormUtility;


/**
 * A controller for the command viewer 
 * */
@Controller
public class CommandController {

    /**
     * A REST-style URL to commandviewer, combined with command id displays command configuration
     * */
    public static final String COMMAND_URL = "/commandviewer/";
    
    /**
     * A URL to the commandviewer with input form
     * */
    public static final String COMMAND_FORM_URL = "/commandviewer/form";
    
    /**
     * Title for the command form page
     * */
    public static final String COMMAND_FORM_TITLE = "Command Configuration Viewer";
    
    /**
     * Description for the command form page
     * */
    public static final String COMMAND_FORM_INSTR = "Enter a command id to view the command's configuration.";
    
    /**
     * A command service
     * */
    @Autowired
    private CommandService service;

    /**
     * CommandController logger
     * */
    private static Logger logger = Logger.getLogger(CommandController.class);
    
    /**
     * Displays configuration of an alarm with the given id
     * @param model Spring MVC Model instance to be filled in before jsp processes it
     * @return name of a jsp page which will be displayed
     * */
    @RequestMapping(value = "/commandviewer/", method = { RequestMethod.GET })
    public String viewCommand(final Model model) {
      logger.info("/commandviewer/");
      return ("redirect:" + "/commandviewer/form");
    }    
    
    /**
     * Displays configuration of a process with the given process name
     * @param id command id
     * @param response we write the html result to that HttpServletResponse response
     * @throws IOException 
     * */
    @RequestMapping(value = "/commandviewer/{id}", method = { RequestMethod.GET })
    public String viewCommand(@PathVariable(value = "id") final String id, final HttpServletResponse response) throws IOException  {
      logger.info("/commandviewer/{id} " + id);
      try {
        response.getWriter().println(service.generateHtmlResponse(id));
      } catch (TransformerException e) {
        response.getWriter().println(e.getMessage());
        logger.error(e.getMessage());
      } catch (TagIdException e) {
        return ("redirect:" + "/commandviewer/errorform/" + id);
      }
      return null;
    }
    
    /**
     * Displays an input form for an alarm id, and if a POST was made with an alarm id, also the alarm data.
     * @param id alarm id
     * @param model Spring MVC Model instance to be filled in before jsp processes it
     * @return name of a jsp page which will be displayed
     * */
    @RequestMapping(value = "/commandviewer/errorform/{id}")
    public String viewCommandErrorForm(@PathVariable(value = "id") final String id, final Model model) {
        logger.info("/commandviewer/errorform " + id);
        
        model.addAllAttributes(FormUtility.getFormModel(COMMAND_FORM_TITLE, COMMAND_FORM_INSTR, COMMAND_FORM_URL, id, COMMAND_URL + id));
        model.addAttribute("err", id);
       return "errorFormWithData";
    }
    
    /**
     * Displays configuration of a command with the given id together with a form
     * @param id command id
     * @param model Spring MVC Model instance to be filled in before jsp processes it
     * @return name of a jsp page which will be displayed
     * */
    @RequestMapping(value = "/commandviewer/form/{id}", method = { RequestMethod.GET })
    public String viewCommandWithForm(@PathVariable final String id, final Model model) {
        logger.info("/commandviewer/form/{id} " + id);
        model.addAllAttributes(FormUtility.getFormModel(COMMAND_FORM_TITLE, COMMAND_FORM_INSTR, COMMAND_FORM_URL, id, COMMAND_URL + id));
        return "formWithData";
    }
    
    /**
     * Displays an input form for a command id, and if a POST was made with a command id, also the command data.
     * @param id command id
     * @param model Spring MVC Model instance to be filled in before jsp processes it
     * @return name of a jsp page which will be displayed
     * */
    @RequestMapping(value = "/commandviewer/form", method = { RequestMethod.GET, RequestMethod.POST })
    public String viewCommandFormPost(@RequestParam(value = "id", required = false) final String id, final Model model) {
        logger.info("/commandviewer/form " + id);
        if (id == null)
            model.addAllAttributes(FormUtility.getFormModel(COMMAND_FORM_TITLE, COMMAND_FORM_INSTR, COMMAND_FORM_URL, null, null));
        else
           return ("redirect:" + COMMAND_URL + id);
        return "formWithData";
    }
}
