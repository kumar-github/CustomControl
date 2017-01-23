package application;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.cayenne.query.ObjectSelect;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Account;
import com.tc.app.exchangemonitor.model.cayenne.persistent.Commodity;
import com.tc.app.exchangemonitor.model.cayenne.persistent.IctsUser;
import com.tc.app.exchangemonitor.model.cayenne.persistent.Uom;

public class CayenneReferenceDataCache
{
	public static void fetchAllReferenceData()
	{
		fetchAccountsReferenceData();
		fetchIctsUsersReferenceData();
		fetchCommoditiesReferenceData();
		fetchUomsReferenceData();
	}

	private static Map<Integer, Account> accountsReferenceDataHashMap = null;
	private static void fetchAccountsReferenceData()
	{
		try
		{
			if(accountsReferenceDataHashMap == null)
			{
				accountsReferenceDataHashMap = new HashMap<>();

				final List<Account> accountList = ObjectSelect.query(Account.class).where(Account.ACCT_STATUS.eq("A")).select(CayenneHelper.getCayenneServerRuntime().newContext());

				accountsReferenceDataHashMap = accountList.stream().collect(Collectors.toMap(Account::getAccountNum, theAccount -> theAccount, (theExistingAccount, theNewAccount) -> theNewAccount, LinkedHashMap::new));
			}
		}
		catch(final Exception exception)
		{
			throw exception;
		}
	}

	private static Map<String, IctsUser> ictsUsersReferenceDataHashMap = null;
	private static void fetchIctsUsersReferenceData()
	{
		try
		{
			if(ictsUsersReferenceDataHashMap == null)
			{
				ictsUsersReferenceDataHashMap = new HashMap<>();

				final List<IctsUser> ictsUsersList = ObjectSelect.query(IctsUser.class).where(IctsUser.USER_STATUS.eq("A")).select(CayenneHelper.getCayenneServerRuntime().newContext());

				ictsUsersReferenceDataHashMap = ictsUsersList.stream().collect(Collectors.toMap(IctsUser::getUserInit, theIctsUser -> theIctsUser, (theExistingIctsUser, theNewIctsUser) -> theNewIctsUser, LinkedHashMap::new));
			}
		}
		catch(final Exception exception)
		{
			throw exception;
		}
	}

	private static Map<String, Commodity> commoditiesReferenceDataHashMap = null;
	private static void fetchCommoditiesReferenceData()
	{
		try
		{
			if(commoditiesReferenceDataHashMap == null)
			{
				commoditiesReferenceDataHashMap = new HashMap<>();

				//final List<Commodity> commoditiesList = ObjectSelect.query(Commodity.class).where(Commodity.CMDTY_STATUS.eq("A")).select(CayenneHelper.getCayenneServerRuntime().newContext());
				final List<Commodity> commoditiesList = ObjectSelect.query(Commodity.class).where(Commodity.CMDTY_STATUS.eq("A")).select(CayenneHelper.getCayenneServerRuntime().newContext());

				commoditiesReferenceDataHashMap = commoditiesList.stream().collect(Collectors.toMap(Commodity::getCmdtyCode, theCommodity -> theCommodity, (theExistingCommodity, theNewCommodity) -> theNewCommodity, LinkedHashMap::new));
			}
		}
		catch(final Exception exception)
		{
			throw exception;
		}
	}

	private static Map<String, Uom> uomsReferenceDataHashMap = null;
	private static void fetchUomsReferenceData()
	{
		try
		{
			if(uomsReferenceDataHashMap == null)
			{
				uomsReferenceDataHashMap = new HashMap<>();

				final List<Uom> uomsList = ObjectSelect.query(Uom.class).where(Uom.UOM_STATUS.eq("A")).select(CayenneHelper.getCayenneServerRuntime().newContext());

				uomsReferenceDataHashMap = uomsList.stream().collect(Collectors.toMap(Uom::getUomCode, theUom -> theUom, (theExistingUom, theNewUom) -> theNewUom, LinkedHashMap::new));
			}
		}
		catch(final Exception exception)
		{
			throw exception;
		}
	}

	public static Map<Integer, Account> loadAllActiveAccounts()
	{
		if(accountsReferenceDataHashMap == null)
		{
			fetchAccountsReferenceData();
		}
		return accountsReferenceDataHashMap;
	}

	public static Map<String, IctsUser> loadAllActiveIctsUsers()
	{
		if(ictsUsersReferenceDataHashMap == null)
		{
			fetchIctsUsersReferenceData();
		}
		return ictsUsersReferenceDataHashMap;
	}

	public static Map<String, Commodity> loadAllActiveCommodities()
	{
		if(commoditiesReferenceDataHashMap == null)
		{
			fetchCommoditiesReferenceData();
		}
		return commoditiesReferenceDataHashMap;
	}

	public static Map<String, Uom> loadAllActiveUoms()
	{
		if(uomsReferenceDataHashMap == null)
		{
			fetchUomsReferenceData();
		}
		return uomsReferenceDataHashMap;
	}
}