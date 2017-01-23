package control;

import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.stage.Window;
import javafx.util.StringConverter;
import skin.TableViewPopupSkin;

/**
 * The TableViewPopup provides an list of available suggestions in order to complete current user input.
 */
@SuppressWarnings("restriction")
public class TableViewPopup<T> extends PopupControl
{
	public static final String DEFAULT_STYLE_CLASS = "tableview-popup";
	private static final int SOURCE_NODE_HEIGHT = 28; //!HARD CODED!. ASSUMING THAT THE SOURCE NODE WILL BE A TEXTFIELD WHOSE DEFAULT HEIGHT IS 25

	/* need to check whether this is the proper way to do it */
	//private TableViewPopupSkin<T> tableViewPopupSkin;

	private final ObservableList<T> suggestions = FXCollections.observableArrayList();
	private final ObservableList<T> data = FXCollections.observableArrayList();

	/**
	 * Creates a new TableViewPopup
	 */
	public TableViewPopup()
	{
		getStyleClass().add(DEFAULT_STYLE_CLASS);

		this.setAutoFix(true);
		this.setAutoHide(true);
		this.setHideOnEscape(true);
	}

	/**
	 * The maximum number of rows to be visible in the popup when it is showing. By default this value is 5, but this can be changed to increase
	 * or decrease the height of the popup.
	 */
	private IntegerProperty visibleRowCount = new SimpleIntegerProperty(this, "visibleRowCount", 10);

	public final void setVisibleRowCount(int value)
	{
		visibleRowCount.set(value);
	}

	public final int getVisibleRowCount()
	{
		return visibleRowCount.get();
	}

	public final IntegerProperty visibleRowCountProperty()
	{
		return visibleRowCount;
	}

	/**
	 * Get the suggestions presented by this TableViewPopup
	 */
	public ObservableList<T> getSuggestions()
	{
		return suggestions;
	}

	/**
	 * Get the data set on the TableView
	 */
	public ObservableList<T> getData()
	{
		return data;
	}

	/**
	 * Show this popup right below the given Node
	 */
	public void show(Node node)
	{
		if(node.getScene() == null || node.getScene().getWindow() == null)
			throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");

		if(isShowing())
			return;

		Window parent = node.getScene().getWindow();
		this.show(parent, parent.getX() + node.localToScene(0, 0).getX() + node.getScene().getX(), parent.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + SOURCE_NODE_HEIGHT);
	}

	/**
	 * Set the string converter used to turn a generic suggestion into a string
	 */
	private StringConverter<T> converter;

	public void setConverter(StringConverter<T> converter)
	{
		this.converter = converter;
	}

	/**
	 * Get the string converter used to turn a generic suggestion into a string
	 */
	public StringConverter<T> getConverter()
	{
		return converter;
	}

	private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);

	private ObjectProperty<EventHandler<SuggestionEvent<T>>> onSuggestion = new ObjectPropertyBase<EventHandler<SuggestionEvent<T>>>(){
		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
		protected void invalidated()
		{
			eventHandlerManager.setEventHandler(SuggestionEvent.SUGGESTION, (EventHandler<SuggestionEvent>) (Object) get());
		}

		@Override
		public Object getBean()
		{
			return TableViewPopup.this;
		}

		@Override
		public String getName()
		{
			return "onSuggestion";
		}
	};

	public final ObjectProperty<EventHandler<SuggestionEvent<T>>> onSuggestionProperty()
	{
		return onSuggestion;
	}

	public final void setOnSuggestion(EventHandler<SuggestionEvent<T>> value)
	{
		onSuggestionProperty().set(value);
	}

	public final EventHandler<SuggestionEvent<T>> getOnSuggestion()
	{
		return onSuggestionProperty().get();
	}

	@Override
	public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail)
	{
		return super.buildEventDispatchChain(tail).append(eventHandlerManager);
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new TableViewPopupSkin<T>(this);
		//tableViewPopupSkin = new TableViewPopupSkin<T>(this);
		//return tableViewPopupSkin;
	}

	private String fxmlToLoad;

	public void setFXMLToLoad(String fxmlToLoad)
	{
		this.fxmlToLoad = fxmlToLoad;
	}

	public String getFXMLToLoad()
	{
		return this.fxmlToLoad;
	}

	/***************************************************************************
	 * *
	 * Inner classes *
	 * *
	 **************************************************************************/

	/**
	 * Represents an Event which is fired when the user has selected a suggestion/row from the auto-complete popup
	 */
	@SuppressWarnings("serial")
	public static class SuggestionEvent<TE> extends Event
	{
		@SuppressWarnings("rawtypes")
		public static final EventType<SuggestionEvent> SUGGESTION = new EventType<>("SUGGESTION");
		private final TE suggestion;

		public SuggestionEvent(TE suggestion)
		{
			super(SUGGESTION);
			this.suggestion = suggestion;
		}

		/**
		 * Returns the suggestion which was chosen by the user
		 */
		public TE getSuggestion()
		{
			return suggestion;
		}
	}

	/* need to double check this */
	public TableViewPopupSkin<T> getTableViewPopupSkin()
	{
		//return tableViewPopupSkin;
		return (TableViewPopupSkin<T>) getSkin();
	}
}