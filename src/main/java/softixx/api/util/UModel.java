package softixx.api.util;

import java.util.Map;

import org.springframework.ui.Model;

import lombok.val;

public class UModel {

	public static Model populateModel(Model model, Map<String, Object> modelMap) {
		if (modelMap != null && !modelMap.isEmpty()) {
			for (val entry : modelMap.entrySet()) {
				val key = entry.getKey();
				val value = entry.getValue();
				model.addAttribute(key, value);
			}
		}
		return model;
	}
	
}