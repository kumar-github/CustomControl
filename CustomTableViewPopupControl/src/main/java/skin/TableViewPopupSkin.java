package skin;

import java.net.URL;

import control.TableViewPopup;
import controller.IGenericReferenceDataController;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

public class TableViewPopupSkin<T> implements Skin<TableViewPopup<T>>
{
	private static final String FXML_FILE_EXTENSION = ".fxml";

	private final TableViewPopup<T> tableViewPopupControl;
	private final TableView<T> innerTableViewControl;
	private FilteredList<?> innerTableViewControlDataSource;
	private FXMLLoader fxmlLoader = null;

	private static final int TABLE_ROW_HEIGHT = 26;
	private static final int TABLE_TITLE_ROW_HEIGHT = 26;

	private IGenericReferenceDataController referenceDataController;

	public TableViewPopupSkin(TableViewPopup<T> tableViewPopupControl)
	{
		this.tableViewPopupControl = tableViewPopupControl;

		/* have to check is there a better and efficient way to do this. */
		this.innerTableViewControl = loadFXML();

		//innerTableViewControl.setItems(tableViewPopupControl.getTableViewDataSource());
		//Bindings.bindContent(innerTableViewControl.getItems(), tableViewPopupControl.getData());
		//innerTableViewControl.itemProperty().bind(tableViewPopupControl.list); //Here bind the listview items to your control list

		//suggestionList = new ListView<>(control.getSuggestions());
		//suggestionList.getStylesheets().add(AutoCompletionBinding.class.getResource("autocompletion.css").toExternalForm());

		innerTableViewControl.getStylesheets().add(this.getClass().getResource("/css/tableview-popup.css").toExternalForm());
		innerTableViewControl.getStyleClass().add(TableViewPopup.DEFAULT_STYLE_CLASS);

		/**
		 * Here we bind the prefHeightProperty to the minimum height between the max visible rows and the current items list. We also add an arbitrary
		 * 18 number because when we have only one item we have the vertical scrollBar showing for no reason.
		 */
		innerTableViewControl.prefHeightProperty().bind(Bindings.min(tableViewPopupControl.visibleRowCountProperty(), Bindings.size(innerTableViewControl.getItems())).multiply(TABLE_ROW_HEIGHT).add(TABLE_TITLE_ROW_HEIGHT));

		//suggestionList.setCellFactory(TextFieldListCell.forListView(control.getConverter()));

		//Allowing the user to control TableView width.
		innerTableViewControl.prefWidthProperty().bind(tableViewPopupControl.prefWidthProperty());
		innerTableViewControl.maxWidthProperty().bind(tableViewPopupControl.maxWidthProperty());
		innerTableViewControl.minWidthProperty().bind(tableViewPopupControl.minWidthProperty());

		registerEventListener();
	}

	private void registerEventListener()
	{
		innerTableViewControl.setOnMouseClicked(mouseEvent -> {
			if(mouseEvent.getButton() == MouseButton.PRIMARY)
			{
				onRowSelected(innerTableViewControl.getSelectionModel().getSelectedItem());
			}
		});

		innerTableViewControl.setOnKeyPressed(keyEvent -> {
			switch(keyEvent.getCode())
			{
				case ENTER:
					onRowSelected(innerTableViewControl.getSelectionModel().getSelectedItem());
					break;

				case ESCAPE:
					if(tableViewPopupControl.isHideOnEscape())
						tableViewPopupControl.hide();
					break;

				default:
					break;
			}
		});
	}

	private void onRowSelected(T theSelectedRow)
	{
		if(theSelectedRow != null)
		{
			Event.fireEvent(tableViewPopupControl, new TableViewPopup.SuggestionEvent<>(theSelectedRow));
		}
	}

	@Override
	public TableViewPopup<T> getSkinnable()
	{
		return tableViewPopupControl;
	}

	@Override
	public Node getNode()
	{
		return innerTableViewControl;
	}

	@Override
	public void dispose()
	{
		//tableViewPopupControl = null;
		//innerTableViewControl = null;
	}

	private TableView<T> loadFXML()
	{
		TableView<T> innerTableViewControl = null;

		if(fxmlLoader == null)
		{
			try
			{
				//innerTableViewControl = FXMLLoader.load(this.getClass().getResource("/view/PersonView.fxml"));
				fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(this.getResourceURL());
				innerTableViewControl = fxmlLoader.load();
				//IGenericReferenceDataController referenceDataController = fxmlLoader.getController();
				referenceDataController = fxmlLoader.getController();
				innerTableViewControlDataSource = referenceDataController.getInnerTableViewControlDataSource();
			}
			catch(Exception exception)
			{
				System.err.println(exception);
			}
		}
		return innerTableViewControl;
	}

	private URL getResourceURL()
	{
		return this.getClass().getResource(getFXMLResourcePath());
	}

	private String getFXMLResourcePath()
	{
		return tableViewPopupControl.getFXMLToLoad() + FXML_FILE_EXTENSION;
	}

	public FilteredList<?> getInnerTableViewControlDataSource()
	{
		return innerTableViewControlDataSource;
	}

	public IGenericReferenceDataController getController()
	{
		return referenceDataController;
	}
}