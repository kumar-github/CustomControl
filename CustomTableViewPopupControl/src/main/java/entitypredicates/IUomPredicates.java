package entitypredicates;

import java.util.function.Predicate;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Uom;

// public class ICommodityPredicates
public interface IUomPredicates
{
	public static Predicate<Uom> applyUomPredicate(final String filterText)
	{
		return (uom) -> {
			if((filterText == null) || filterText.isEmpty())
				return true;
			else if(uom.getUomCode().trim().toLowerCase().contains(filterText))
				return true;
			else if(uom.getUomFullName().trim().toLowerCase().contains(filterText))
				return true;
			else if(uom.getUomShortName().trim().toLowerCase().contains(filterText))
				return true;
			return false;
		};
	}
}