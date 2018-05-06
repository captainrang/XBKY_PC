package ikaoyaner.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/crond")
public class TaskController {
	
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/run/{id}")
	@ResponseBody
	public void run(@PathVariable String id) {
		log.info(id);
	}

}
