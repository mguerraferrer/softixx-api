package softixx.api.wrapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import softixx.api.json.JSelect;
import softixx.api.payload.OptionDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WAddress {
	private JSelect stateSelect;
	private List<OptionDto> states;
	
	private JSelect townSelect;
	private List<OptionDto> towns;
	
	private JSelect colonySelect;
	private List<OptionDto> colonies;
	
	private JSelect select;
	private List<OptionDto> options;
	
	private String state;
	private String town;
	private String colony;
	private String zipCode;
	
	public static WAddress populate(final JSelect select, final List<OptionDto> options) {
		val wa = new WAddress();
		wa.setSelect(select);
		wa.setOptions(options);
		return wa;
	}
	
	public static WAddress stateSelect(final JSelect stateSelect) {
		val wa = new WAddress();
		wa.setStateSelect(stateSelect);
		return wa;
	}
	
	public static WAddress stateSelect(final JSelect stateSelect, final List<OptionDto> states) {
		val wa = new WAddress();
		wa.setStateSelect(stateSelect);
		wa.setStates(states);
		return wa;
	}
	
	public static WAddress states(final List<OptionDto> states) {
		val wa = new WAddress();
		wa.setStates(states);
		return wa;
	}
	
	public static WAddress townSelect(final JSelect townSelect) {
		val wa = new WAddress();
		wa.setTownSelect(townSelect);
		return wa;
	}
	
	public static WAddress townSelect(final JSelect townSelect, final List<OptionDto> towns) {
		val wa = new WAddress();
		wa.setTownSelect(townSelect);
		wa.setTowns(towns);
		return wa;
	}
	
	public static WAddress towns(final List<OptionDto> towns) {
		val wa = new WAddress();
		wa.setTowns(towns);
		return wa;
	}
	
	public static WAddress colonySelect(final JSelect colonySelect) {
		val wa = new WAddress();
		wa.setColonySelect(colonySelect);
		return wa;
	}
	
	public static WAddress colonySelect(final JSelect colonySelect, final List<OptionDto> colonies) {
		val wa = new WAddress();
		wa.setColonySelect(colonySelect);
		wa.setColonies(colonies);
		return wa;
	}
	
	public static WAddress colonies(final List<OptionDto> colonies) {
		val wa = new WAddress();
		wa.setColonies(colonies);
		return wa;
	}
}