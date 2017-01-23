package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Commodity;

import application.CayenneReferenceDataCache;
import entitypredicates.ICommodityPredicates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CommodityReferenceDataTableViewController implements IGenericReferenceDataController
{
	@FXML
	private TableView<Commodity> commodityReferenceDataTableView;
	@FXML
	private TableColumn<Commodity, String> commodityCodeTableColumn;
	@FXML
	private TableColumn<Commodity, String> commodityShortNameTableColumn;
	@FXML
	private TableColumn<Commodity, String> commodityFullNameTableColumn;

	private final ObservableList<Commodity> commoditiesObservableList = FXCollections.observableArrayList();
	private final FilteredList<Commodity> commoditiesFilteredList = new FilteredList<>(this.commoditiesObservableList, p -> true);
	private final SortedList<Commodity> commoditiesSortedList = new SortedList<>(this.commoditiesFilteredList);

	@Override
	public void initialize(final URL location, final ResourceBundle resources)
	{
		this.commoditiesSortedList.comparatorProperty().bind(this.commodityReferenceDataTableView.comparatorProperty());

		final long startTime = System.currentTimeMillis();
		this.commoditiesObservableList.addAll(CayenneReferenceDataCache.loadAllActiveCommodities().values());
		final long endTime = System.currentTimeMillis();
		this.commodityReferenceDataTableView.setItems(this.commoditiesSortedList);
		System.out.println(endTime - startTime);
	}

	@Override
	public FilteredList<Commodity> getInnerTableViewControlDataSource()
	{
		return this.commoditiesFilteredList;
	}

	@Override
	public void filter(final String filterText)
	{
		this.commoditiesFilteredList.setPredicate(ICommodityPredicates.applyCommodityPredicate(filterText));
	}
}