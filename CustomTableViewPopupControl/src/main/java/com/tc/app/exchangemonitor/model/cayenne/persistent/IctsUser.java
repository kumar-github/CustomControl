package com.tc.app.exchangemonitor.model.cayenne.persistent;

import com.tc.app.exchangemonitor.model.cayenne.persistent.auto._IctsUser;

public class IctsUser extends _IctsUser
{
	private static final long serialVersionUID = 1L;

	public String getUserInit()
	{
		return (this.getObjectId() != null) && !this.getObjectId().isTemporary() ? (String) this.getObjectId().getIdSnapshot().get(USER_INIT_PK_COLUMN) : null;
	}

	public String userFirstName()
	{
		return this.getUserFirstName();
	}
}