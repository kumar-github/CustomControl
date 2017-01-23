package controller;

import javafx.collections.transformation.FilteredList;

public interface IGenericReferenceDataController extends IGenericController
{
	public abstract FilteredList<?> getInnerTableViewControlDataSource();

	public abstract void filter(String filterText);
}