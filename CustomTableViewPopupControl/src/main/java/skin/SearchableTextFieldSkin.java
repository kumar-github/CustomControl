package skin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import behavior.SearchableTextFieldBehavior;
import control.SearchableTextField;
import control.TableViewPopup;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

@SuppressWarnings("restriction")
public class SearchableTextFieldSkin<T> extends TextFieldSkin
{
	private Region loupe;

	/*
	private Region crossButton;
	 */
	private final TableViewPopup<T> tableViewPopup;
	//private final SearchableTextField<T> sourceSearchableTextField;

	public SearchableTextFieldSkin(final SearchableTextField<T> searchableTextField)
	{
		super(searchableTextField);
		//this.sourceSearchableTextField = searchableTextField;

		tableViewPopup = new TableViewPopup<T>();
		tableViewPopup.setFXMLToLoad(searchableTextField.getReferenceDataFxmlPath());

		tableViewPopup.setOnSuggestion(event -> {
			try
			{
				setIgnoreInputChanges(true);
				completeUserInput(event.getSuggestion());
				fireAutoCompletion(event.getSuggestion());
				hidePopup();
			}
			catch(Exception exception)
			{
			}
			finally
			{
				//Ensure that ignore is always set back to false
				setIgnoreInputChanges(false);
			}
		});

		initGraphics();
		registerListeners();
	}

	public SearchableTextFieldSkin(final SearchableTextField<T> searchableTextField, SearchableTextFieldBehavior<T> behavior)
	{
		super(searchableTextField, behavior);
		//this.sourceSearchableTextField = searchableTextField;
		tableViewPopup = new TableViewPopup<T>();
		tableViewPopup.setFXMLToLoad(searchableTextField.getReferenceDataFxmlPath());

		initGraphics();
		registerListeners();
	}

	private void initGraphics()
	{
		loupe = new Region();
		loupe.getStyleClass().add("loupe");
		loupe.setFocusTraversable(false);
		getChildren().addAll(loupe);

		/*
		crossButton = new Region();
		crossButton.getStyleClass().add("cross-button");
		crossButton.setFocusTraversable(false);
		
		getChildren().addAll(loupe, crossButton);
		 */
	}

	private void registerListeners()
	{
		getSkinnable().textProperty().addListener(textChangeListener);
		//sourceSearchableTextField.textProperty().addListener(textChangeListener);

		getSkinnable().focusedProperty().addListener(focusChangedListener);
		//sourceSearchableTextField.focusedProperty().addListener(focusChangedListener);

		//crossButton.setOnMouseClicked(event -> getSkinnable().setText(""));

		getSkinnable().widthProperty().addListener(observable -> {
			double size = loupe.getMaxWidth() < 0 ? 20.8 : loupe.getWidth();
			loupe.setTranslateX(getSkinnable().getWidth() * 0.5 - size * 0.7);
			/*crossButton.setTranslateX(getSkinnable().getWidth() * 0.5 - size * 0.7);*/
		});

		getSkinnable().heightProperty().addListener(observable -> {
			//crossButton.setMaxSize(getSkinnable().getHeight() * 0.8, getSkinnable().getHeight() * 0.8);
			loupe.setMaxSize(getSkinnable().getHeight() * 0.8, getSkinnable().getHeight() * 0.8);
		});

		getSkinnable().sceneProperty().addListener(observable -> {
			//loupe.setTranslateX(getSkinnable().getWidth() * 0.5 + crossButton.getWidth() * 0.7);
			loupe.setTranslateX(getSkinnable().getWidth() * 0.5);
			//crossButton.setTranslateX(getSkinnable().getWidth() * 0.5 - loupe.getWidth() * 0.7);
		});
	}

	/**
	 * Do whatever you want to do when the text in the searchable text field changes.
	 */
	private final ChangeListener<String> textChangeListener = new ChangeListener<String>(){
		@Override
		public void changed(ObservableValue<? extends String> obs, String oldText, String newText)
		{
			//crossButton.setVisible(getSkinnable().getText().isEmpty() ? false : true);
			showPopup();
			if(getSkinnable().isFocused())
			{
				//setUserInput(newText);
			}

			filter(newText.toLowerCase());
		}
	};

	/**
	 * Do whatever you want to do when the focus is changed from/to searchable text field.
	 */

	private final ChangeListener<Boolean> focusChangedListener = new ChangeListener<Boolean>(){
		@Override
		public void changed(ObservableValue<? extends Boolean> obs, Boolean oldFocused, Boolean newFocused)
		{
			if(newFocused == false)
				hidePopup();
			loupe.setVisible(!getSkinnable().isFocused() && getSkinnable().getText().isEmpty());
			//crossButton.setVisible(getSkinnable().isFocused() && !getSkinnable().getText().isEmpty() ? true : false);
		}
	};

	/**
	 * Set the current text the user has entered
	 * 
	 * @param userText
	 */
	public final void setUserInput(String userText)
	{
		if(!isIgnoreInputChanges())
		{
			onUserInputChanged(userText);
		}
	}

	/**
	 * Shall changes to the user input be ignored?
	 * 
	 * @return
	 */
	private boolean ignoreInputChanges = false;

	private boolean isIgnoreInputChanges()
	{
		return ignoreInputChanges;
	}

	/**
	 * If IgnoreInputChanges is set to true, all changes to the user input are
	 * ignored. This is primary used to avoid self triggering while
	 * auto completing.
	 * 
	 * @param state
	 */
	private void setIgnoreInputChanges(boolean state)
	{
		ignoreInputChanges = state;
	}

	/**
	 * Occurs when the user text has changed and the suggestions require an update
	 * 
	 * @param userText
	 */
	private final void onUserInputChanged(final String userText)
	{
		/*
		synchronized (suggestionsTaskLock)
		{
			if(suggestionsTask != null && suggestionsTask.isRunning())
			{
				// cancel the current running task
				suggestionsTask.cancel(); 
			}
			// create a new fetcher task
			suggestionsTask = new FetchSuggestionsTask(userText, delay);
			new Thread(suggestionsTask).start();
		}
		 */
	}

	private void showPopup()
	{
		//Duration duration = new Duration(300);
		//KeyValue kvOpacity = new KeyValue(tableViewPopup.opacityProperty(), 0);
		//KeyFrame atEndFrame = new KeyFrame(duration, (evt) -> ListViewHelperEmployeesPopup.this.hide(), kvOpacity);
		//KeyFrame atEndFrame = new KeyFrame(duration, (evt) -> tableViewPopup.hide(), kvOpacity);
		//KeyFrame atEndFrame = new KeyFrame(duration, (evt) -> tableViewPopup.show(getSkinnable()), kvOpacity);
		//Timeline timeline = new Timeline();
		//timeline.getKeyFrames().add( atEndFrame );
		//timeline.play();
		tableViewPopup.show(getSkinnable());
		//tableViewPopup.show(sourceSearchableTextField);

		Platform.runLater(new Runnable(){
			@Override
			public void run()
			{
				selectFirstMatchingRow(tableViewPopup);
			}
		});
	}

	private void hidePopup()
	{
		tableViewPopup.hide();
	}

	protected void completeUserInput(T completion)
	{
		//String newText = converter.toString(completion);

		//sourceSearchableTextField.setText(completion.toString());
		//sourceSearchableTextField.setText(getDisplayFieldValue(completion, sourceSearchableTextField.getDisplayField()).toString());
		getSkinnable().setText(getDisplayFieldValue(completion, ((SearchableTextField) getSkinnable()).getDisplayField()).toString());

		//sourceSearchableTextField.positionCaret(newText.length());
		//sourceSearchableTextField.selectAll();
		getSkinnable().end();
		//sourceSearchableTextField.selectEnd();
		//sourceSearchableTextField.selectPositionCaret(3);
	}

	protected void fireAutoCompletion(T completion)
	{
		//Event.fireEvent(this, new AutoCompletionEvent<>(completion));
		Event.fireEvent(getSkinnable(), new SearchableTextField.AutoCompletionEvent<>(completion));
	}

	private Object getDisplayFieldValue(T anObject, String methodNameString)
	{
		Object value = anObject;
		if(methodNameString != null)
		{
			try
			{
				Method methodToInvoke = anObject.getClass().getDeclaredMethod(methodNameString, null);
				value = methodToInvoke.invoke(anObject, null);
			}
			catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException exception)
			{
				System.err.println(exception);
			}
		}
		return value;
	}

	private void selectFirstMatchingRow(TableViewPopup<T> tableViewPopup)
	{
		/* The below is a more generic way but we don't need since we know ours is a TableViewPopupSkin always. */
		/*
		Skin<?> tableViewPopupSkin = tableViewPopup.getSkin();
		if(tableViewPopupSkin instanceof TableViewPopupSkin)
		{
			TableViewPopupSkin<?> au = (TableViewPopupSkin<?>)tableViewPopupSkin;
			TableView<?> tv = (TableView<?>)au.getNode();
			if(tv.getItems() != null && !tv.getItems().isEmpty())
			{
				tv.getSelectionModel().selectFirst();
			}
		}
		 */

		/*
		((TableView)((TableViewPopupSkin<T>)tableViewPopup.getSkin()).getNode()).getSelectionModel().clearSelection();
		((TableView)((TableViewPopupSkin<T>)tableViewPopup.getSkin()).getNode()).requestFocus();
		((TableView)((TableViewPopupSkin<T>)tableViewPopup.getSkin()).getNode()).getSelectionModel().selectFirst();
		((TableView)((TableViewPopupSkin<T>)tableViewPopup.getSkin()).getNode()).getFocusModel().focus(0);
		 */

		//we don't want repeated selections
		((TableView<T>) tableViewPopup.getTableViewPopupSkin().getNode()).getSelectionModel().clearSelection();

		//get the focus
		((TableView<T>) tableViewPopup.getTableViewPopupSkin().getNode()).requestFocus();

		//select first item in TableView model
		((TableView<T>) tableViewPopup.getTableViewPopupSkin().getNode()).getSelectionModel().selectFirst();
		//((TableView<T>)tableViewPopup.getTableViewPopupSkin().getNode()).getSelectionModel().select(0); //choose which is correct

		//set the focus on the first element
		((TableView<T>) tableViewPopup.getTableViewPopupSkin().getNode()).getFocusModel().focus(0);

		//render the selected item in the TableView
		//tableClickHandler(null);
	}

	/* the worst way to do*/
	/*
	private final void filter(String filterText)
	{
		String entityName = getSkinnable().getUserData().toString();
		switch(entityName)
		{
		case "Commodity":
			((FilteredList<Commodity>) tableViewPopup.getTableViewPopupSkin().getInnerTableViewControlDataSource()).setPredicate(ICommodityPredicates.applyCommodityPredicate(filterText));
			break;
		case "IctsUser":
			((FilteredList<IctsUser>) tableViewPopup.getTableViewPopupSkin().getInnerTableViewControlDataSource()).setPredicate(ITraderPredicates.applyTraderPredicate(filterText));
			break;
		case "Uom":
			((FilteredList<Uom>) tableViewPopup.getTableViewPopupSkin().getInnerTableViewControlDataSource()).setPredicate(IUomPredicates.applyUomPredicate(filterText));
			break;
		default:
			break;
		}
	 */

	private final void filter(String filterText)
	{
		//tableViewPopup.getTableViewPopupSkin().getController().filter(filterText);
		((TableViewPopupSkin<T>) tableViewPopup.getSkin()).getController().filter(filterText);
	}
}