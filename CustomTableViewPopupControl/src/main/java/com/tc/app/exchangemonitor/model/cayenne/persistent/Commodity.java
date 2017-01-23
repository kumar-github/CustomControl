package com.tc.app.exchangemonitor.model.cayenne.persistent;

import com.tc.app.exchangemonitor.model.cayenne.persistent.auto._Commodity;

public class Commodity extends _Commodity
{
	private static final long serialVersionUID = 1L;

	public String getCmdtyCode()
	{
		return (this.getObjectId() != null) && !this.getObjectId().isTemporary() ? (String) this.getObjectId().getIdSnapshot().get(CMDTY_CODE_PK_COLUMN) : null;
	}

	public String cmdtyShortName()
	{
		return this.getCmdtyShortName();
	}
}