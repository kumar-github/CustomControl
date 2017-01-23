package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Uom;

import application.CayenneReferenceDataCache;
import entitypredicates.IUomPredicates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UomReferenceDataTableViewController implements IGenericReferenceDataController
{
	@FXML
	private TableView<Uom> uomReferenceDataTableView;
	@FXML
	private TableColumn<Uom, String> uomCodeTableColumn;
	@FXML
	private TableColumn<Uom, String> uomShortNameTableColumn;
	@FXML
	private TableColumn<Uom, String> uomFullNameTableColumn;

	private final ObservableList<Uom> uomObservableList = FXCollections.observableArrayList();
	private final FilteredList<Uom> uomFilteredList = new FilteredList<>(this.uomObservableList, p -> true);
	private final SortedList<Uom> uomSortedList = new SortedList<>(this.uomFilteredList);

	@Override
	public void initialize(final URL location, final ResourceBundle resources)
	{
		this.uomSortedList.comparatorProperty().bind(this.uomReferenceDataTableView.comparatorProperty());

		this.uomObservableList.addAll(CayenneReferenceDataCache.loadAllActiveUoms().values());
		this.uomReferenceDataTableView.setItems(this.uomSortedList);
	}

	@Override
	public FilteredList<Uom> getInnerTableViewControlDataSource()
	{
		return this.uomFilteredList;
	}

	@Override
	public void filter(final String filterText)
	{
		this.uomFilteredList.setPredicate(IUomPredicates.applyUomPredicate(filterText));
	}
}