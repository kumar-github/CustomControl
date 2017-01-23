package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Account;

import application.CayenneReferenceDataCache;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AccountReferenceDataTableViewController implements IGenericReferenceDataController
{
	@FXML
	private TableView<Account> accountReferenceDataTableView;
	@FXML
	private TableColumn<Account, Integer> accountNumTableColumn;
	@FXML
	private TableColumn<Account, String> accountShortNameTableColumn;
	@FXML
	private TableColumn<Account, String> accountFullNameTableColumn;
	@FXML
	private TableColumn<Account, String> accountTypeCodeTableColumn;

	private final ObservableList<Account> accountsObservableList = FXCollections.observableArrayList();
	private final FilteredList<Account> accountsFilteredList = new FilteredList<>(this.accountsObservableList, p -> true);
	private final SortedList<Account> accountsSortedList = new SortedList<>(this.accountsFilteredList);

	@Override
	public void initialize(final URL location, final ResourceBundle resources)
	{
		this.accountsSortedList.comparatorProperty().bind(this.accountReferenceDataTableView.comparatorProperty());

		this.accountsObservableList.addAll(CayenneReferenceDataCache.loadAllActiveAccounts().values());
		this.accountReferenceDataTableView.setItems(this.accountsSortedList);
	}

	@Override
	public FilteredList<Account> getInnerTableViewControlDataSource()
	{
		return this.accountsFilteredList;
	}

	@Override
	public void filter(final String filterText)
	{
	}
}