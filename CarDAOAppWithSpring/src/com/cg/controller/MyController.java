package com.cg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cg.dao.CarDAO;
import com.cg.dto.CarDTO;

@Controller
@RequestMapping("/controller")
public class MyController {
	private static final String ACTION_KEY = "action";
	private static final String VIEW_CAR_LIST_ACTION = "viewCarList";
	private static final String ADD_CAR_ACTION = "addCar";
	private static final String SAVE_CAR_ACTION = "saveCar";
	private static final String EDIT_CAR_ACTION = "editCar";
	private static final String DELETE_CAR_ACTION = "deleteCar";
	private static final String ERROR_KEY = "errorMessage";
	@Autowired
	private CarDAO carDAO;

//	@RequestMapping(method = RequestMethod.POST, params = "id")
	
	
	@RequestMapping(method = RequestMethod.GET)
	    protected String processViewAddRequest(ModelMap map,@RequestParam("action") String actionName){
	        if((VIEW_CAR_LIST_ACTION.equals(actionName))) {
	        	  
	                 
	           	//TODO 4 
				//Use carDao to get the list of the cars
	        	List<CarDTO> cars = carDAO.findAll();
				//Set the list in request with attribute name as 'carList'
	        	map.addAttribute("carList", cars);
				//Set the destination page to carList.jsp
	        	return "carList";
				
	        }
	        else if(ADD_CAR_ACTION.equals(actionName))
	        {
				//TODO 5 
				//Create a new CarDTO and set in request with attribute name as 'car'
	        	CarDTO car = new CarDTO();
	        	map.addAttribute("car", car);
	        	//Set the destination page to carForm.jsp
	        	return "carForm";
	            
	        }  
	           else
	        {
	            String errorMessage = "[" + actionName + "] is not a valid action.";
	            map.addAttribute(ERROR_KEY, errorMessage);
	            return null;
	        }
}
	@RequestMapping(method = RequestMethod.POST)
    protected String processSaveRequest(@RequestParam("action") String action, @ModelAttribute("car") CarDTO car){
		System.out.println("IN save car");
		if(SAVE_CAR_ACTION.equals(action)){
			System.out.println(car.getMake());
		}
		return "carList";
		}
	
/*	@RequestMapping(method = RequestMethod.POST)
    protected String processRequest(ModelMap map,
    		@RequestParam("action") String action, @RequestParam(value="id", required=false) Integer id) {
		System.out.println("IN processRequest");
		if(action.equals(VIEW_CAR_LIST_ACTION)){
			List<CarDTO> cars = carDAO.findAll();
			map.addAttribute("carList", cars);
			return "carList";
			else if(action.equals(ADD_CAR_ACTION)) {
				return "carForm";
			} else if(action.eq)
		}*/
	
//	@RequestMapping(method = RequestMethod.POST, params= {"make","model","modelYear","id"})
//    protected String processSaveRequest(ModelMap map,@RequestParam("action") String actionName, @RequestParam("make") String make, @RequestParam("model") String model, @RequestParam("modelYear") String modelYear, @RequestParam("id") int id) {
//		System.out.println("IN save car");
//		if(SAVE_CAR_ACTION.equals(actionName))
//    {
//		//TODO 7 
//		//Create a new CarDTO
//    	CarDTO car = new CarDTO();
//		//set the properties of the DTO from request parameters
//    	car.setMake(make);
//    	car.setModel(model);
//    	car.setModelYear(modelYear);
//    	car.setId(id);
//		//If it is a new car then invoke create api of DAO else update api
//    	if (id == -1)
//    	carDAO.create(car);
//    	else
//    	carDAO.update(car);
//    	//Get all the Cars using DAO
//    	List<CarDTO> cars = carDAO.findAll();
//		//Set the found cars in request with name as 'carList'
//    	map.addAttribute("carList", cars);
//		//Set the destination page to carList.jsp
//    	return "carList";
//		        
//    }   else
//    {
//        String errorMessage = "[" + actionName + "] is not a valid action.";
//        map.addAttribute(ERROR_KEY, errorMessage);
//        return null;
//    }
//}

	@RequestMapping(method = RequestMethod.GET, params="id")
    protected String processSearchRequest(ModelMap map,@RequestParam("action") String actionName, @RequestParam("id") int id) {
		if(EDIT_CAR_ACTION.equals(actionName))
        {
			System.out.println("AA raha hai");
        	//TODO 6 
			//Get the car id from request, with parameter name as 'id'
        	CarDTO existingCar = carDAO.findById(id);
			//Set the found car in request with name as 'car'
        	map.addAttribute("car", existingCar);
			//Set the destination page to carForm.jsp
        	return "carForm";
            
        }                       
        else
        {
            String errorMessage = "[" + actionName + "] is not a valid action.";
            map.addAttribute(ERROR_KEY, errorMessage);
            return null;
        }
	}

	@RequestMapping(method = RequestMethod.POST )
    protected String processDeleteRequest(ModelMap map,@RequestParam("action") String actionName, @RequestParam("id") String []ids) {
		if(DELETE_CAR_ACTION.equals(actionName))
        {
            //TODO 8 
			//Get the ids of all cars to be deleted from request
        	//Use appropriate api of DAO to delete all cars
        	carDAO.delete(ids);
        	//Get all the Cars using DAO
        	List<CarDTO> cars = carDAO.findAll();
			//Set the found cars in request with name as 'carList'
        	map.addAttribute("carList", cars);
			//Set the destination page to carList.jsp
        	return "carList";
			
            
        }                  
        else
        {
            String errorMessage = "[" + actionName + "] is not a valid action.";
            map.addAttribute(ERROR_KEY, errorMessage);
            return null;
        }
	}
}