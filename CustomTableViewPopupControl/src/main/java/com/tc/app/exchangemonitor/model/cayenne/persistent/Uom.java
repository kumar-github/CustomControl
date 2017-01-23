package com.tc.app.exchangemonitor.model.cayenne.persistent;

import com.tc.app.exchangemonitor.model.cayenne.persistent.auto._Uom;

public class Uom extends _Uom
{
	private static final long serialVersionUID = 1L;

	public String getUomCode()
	{
		return (this.getObjectId() != null) && !this.getObjectId().isTemporary() ? (String) this.getObjectId().getIdSnapshot().get(UOM_CODE_PK_COLUMN) : null;
	}

	public String uomShortName()
	{
		return this.getUomShortName();
	}
}