package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tc.app.exchangemonitor.model.cayenne.persistent.IctsUser;

import application.CayenneReferenceDataCache;
import entitypredicates.ITraderPredicates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TraderReferenceDataTableViewController implements IGenericReferenceDataController
{
	@FXML
	private TableView<IctsUser> traderReferenceDataTableView;
	@FXML
	private TableColumn<IctsUser, String> userInitTableColumn;
	@FXML
	private TableColumn<IctsUser, String> userFirstNameTableColumn;
	@FXML
	private TableColumn<IctsUser, String> userLastNameTableColumn;
	@FXML
	private TableColumn<IctsUser, String> userLogonIdTableColumn;

	private final ObservableList<IctsUser> tradersObservableList = FXCollections.observableArrayList();
	private final FilteredList<IctsUser> tradersFilteredList = new FilteredList<>(this.tradersObservableList, p -> true);
	private final SortedList<IctsUser> tradersSortedList = new SortedList<>(this.tradersFilteredList);

	@Override
	public void initialize(final URL location, final ResourceBundle resources)
	{
		this.tradersSortedList.comparatorProperty().bind(this.traderReferenceDataTableView.comparatorProperty());

		this.tradersObservableList.addAll(CayenneReferenceDataCache.loadAllActiveIctsUsers().values());
		this.traderReferenceDataTableView.setItems(this.tradersSortedList);
	}

	@Override
	public FilteredList<IctsUser> getInnerTableViewControlDataSource()
	{
		return this.tradersFilteredList;
	}

	@Override
	public void filter(final String filterText)
	{
		this.tradersFilteredList.setPredicate(ITraderPredicates.applyTraderPredicate(filterText));
	}
}