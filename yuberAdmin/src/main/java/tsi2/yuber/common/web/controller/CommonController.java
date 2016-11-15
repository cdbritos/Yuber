package tsi2.yuber.common.web.controller;

import javax.faces.event.ActionEvent;

public abstract class CommonController {

	protected void listen(){
		
	};
	
	protected void listen(ActionEvent event) {
		System.out.println(event.getPhaseId());
		System.out.println(event.getSource());
		System.out.println(event.getComponent());
		System.out.println(event.getClass());
	};
}
