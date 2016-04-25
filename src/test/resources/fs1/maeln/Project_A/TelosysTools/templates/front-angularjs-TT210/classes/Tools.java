import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import org.telosys.tools.generator.context.EntityInContext;
import org.telosys.tools.generator.context.AttributeInContext;

public class Tools {
	
	public List<Object> randomKeyAttributesValues(EntityInContext entity) {
		List<AttributeInContext> keys = entity.getKeyAttributes();
		if(keys == null) {
			return new ArrayList<Object>();
		}
		List<Object> values = new ArrayList<Object>();
		int i=1;
		for(AttributeInContext key : keys) {
			values.add(String.valueOf(i));
			i++;
		}
		return values;
	}
	
}
