package entitypredicates;

import java.util.function.Predicate;

import com.tc.app.exchangemonitor.model.cayenne.persistent.IctsUser;

public interface ITraderPredicates
{
	public static Predicate<IctsUser> applyTraderPredicate(final String filterText)
	{
		return (ictsUser) -> {
			/*
			if(filterText == null || filterText.isEmpty())
				return true;
			else if(ictsUser.getUserInit().trim().toLowerCase().contains(filterText))
				return true;
			else if(ictsUser.getUserFirstName().trim().toLowerCase().contains(filterText))
				return true;
			else if(ictsUser.getUserLastName().trim().toLowerCase().contains(filterText))
				return true;
			return false;
			 */
			return ((filterText == null) || filterText.isEmpty() || ictsUser.getUserInit().trim().toLowerCase().contains(filterText) || ictsUser.getUserFirstName().trim().toLowerCase().contains(filterText) || ictsUser.getUserLastName().trim().toLowerCase().contains(filterText));
		};
	}
}