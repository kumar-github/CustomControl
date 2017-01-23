package entitypredicates;

import java.util.function.Predicate;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Commodity;

// public class ICommodityPredicates
public interface ICommodityPredicates
{
	public static Predicate<Commodity> applyCommodityPredicate(final String filterText)
	{
		return (commodity) -> {
			/*
			if(filterText == null || filterText.isEmpty())
				return true;
			else if(commodity.getCmdtyCode().trim().toLowerCase().contains(filterText))
				return true;
			else if(commodity.getCmdtyFullName().trim().toLowerCase().contains(filterText))
				return true;
			else if(commodity.getCmdtyShortName().trim().toLowerCase().contains(filterText))
				return true;
			return false;
			 */
			return ((filterText == null) || filterText.isEmpty() || commodity.getCmdtyCode().trim().toLowerCase().contains(filterText) || commodity.getCmdtyFullName().trim().toLowerCase().contains(filterText) || commodity.getCmdtyShortName().trim().toLowerCase().contains(filterText));
		};
	}
}