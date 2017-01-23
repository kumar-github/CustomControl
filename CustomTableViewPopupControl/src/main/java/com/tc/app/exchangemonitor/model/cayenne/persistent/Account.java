package com.tc.app.exchangemonitor.model.cayenne.persistent;

import com.tc.app.exchangemonitor.model.cayenne.persistent.auto._Account;

public class Account extends _Account
{
	private static final long serialVersionUID = 1L;

	public Integer getAccountNum()
	{
		return (this.getObjectId() != null) && !this.getObjectId().isTemporary() ? (Integer) this.getObjectId().getIdSnapshot().get(ACCT_NUM_PK_COLUMN) : null;
	}

	/*
	@Override
	public String toString()
	{
		return this.getAccountNum() + " for testing";
	}
	 */
}