package control;

import java.net.URL;

import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import skin.SearchableTextFieldSkin;

public class SearchableTextField<T> extends TextField
{
	public SearchableTextField()
	{
		this("");
	}

	public SearchableTextField(final String text)
	{
		super(text);
		//getStylesheets().add(this.getClass().getResource("/css/searchable-textfield.css").toExternalForm()); /*either this or getUserAgentStylesheet()*/
		//setSkin(new SearchableTextFieldSkin(this)); /*either this or createDefaultSkin()*/
	}

	@Override
	protected Skin<?> createDefaultSkin()
	{
		return new SearchableTextFieldSkin<>(this);
	}

	@Override
	public String getUserAgentStylesheet()
	{
		final URL pathToCSS = SearchableTextField.class.getResource("/css/searchable-textfield.css");
		if(pathToCSS != null)
			return pathToCSS.toExternalForm();
		else
		{
			System.err.println("CSS file \"searchable-textfield.css\" for SearchableTextField could not be found.");
			return null;
		}
	}

	/*
	@Override
	public void replaceText(int start, int end, String text)
	{
		super.replaceText(start, end, text);
		String oldValue = getText();
		if ((validate(text)))
		{
			super.replaceText(start, end, text);
			String newText = super.getText();
			if (!validate(newText))
			{
				super.setText(oldValue);
			}
		}
	}
	
	@Override
	public void replaceSelection(String text)
	{
		super.replaceSelection(text);
		String oldValue = getText();
		if (validate(text))
		{
			super.replaceSelection(text);
			String newText = super.getText();
			if (!validate(newText))
			{
				super.setText(oldValue);
			}
		}
	}
	
	private static final String numberRegEx = "\\b([0-9]{1,2}|[1-6][0-9]{2}|7[0-3][0-9]|74[0-4])\\b";
	private boolean validate(String text)
	{
		return ("".equals(text) || text.matches(numberRegEx));
	}
	 */

	/**
	 * Here we get the fxml file name (without extension) that should be loaded and displayed as reference data tableview
	 */
	private String referenceDataFxmlPath = null;

	public String getReferenceDataFxmlPath()
	{
		return this.referenceDataFxmlPath;
	}

	public void setReferenceDataFxmlPath(final String referenceDataFxmlPath)
	{
		this.referenceDataFxmlPath = referenceDataFxmlPath;
	}

	/**
	 * Here we get the display field name configured by the user in the fxml. We need the get the actual method from this string.
	 */
	private String displayField = null;

	public String getDisplayField()
	{
		return this.displayField;
	}

	public void setDisplayField(final String displayField)
	{
		this.displayField = displayField;
	}

	/***************************************************************************
	 * *
	 * Events *
	 * *
	 **************************************************************************/

	// --- AutoCompletionEvent

	/**
	 * Represents an Event which is fired after an auto completion.
	 */
	@SuppressWarnings("serial")
	public static class AutoCompletionEvent<TE> extends Event
	{
		/**
		 * The event type that should be listened to by people interested in knowing when an auto completion has been performed.
		 */
		@SuppressWarnings("rawtypes")
		public static final EventType<AutoCompletionEvent> AUTO_COMPLETED = new EventType<>("AUTO_COMPLETED");

		private final TE completion;

		/**
		 * Creates a new event that can subsequently be fired.
		 */
		public AutoCompletionEvent(final TE completion)
		{
			super(AUTO_COMPLETED);
			this.completion = completion;
		}

		/**
		 * Returns the chosen completion.
		 */
		public TE getCompletion()
		{
			return this.completion;
		}
	}

	//private ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompleted;
	private final ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompleted = new ObjectPropertyBase<EventHandler<AutoCompletionEvent<T>>>(){

		@Override
		protected void invalidated()
		{
			SearchableTextField.this.eventHandlerManager.setEventHandler(AutoCompletionEvent.AUTO_COMPLETED, (EventHandler<AutoCompletionEvent>) (Object) this.get());
		};

		@Override
		public Object getBean()
		{
			return SearchableTextField.this;
		}

		@Override
		public String getName()
		{
			return "onAutoCompleted";
		}
	};

	public final ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompletedProperty()
	{
		return this.onAutoCompleted;
	}

	/**
	 * Set a event handler which is invoked after an auto completion.
	 * 
	 * @param value
	 */
	public final void setOnAutoCompleted(final EventHandler<AutoCompletionEvent<T>> value)
	{
		this.onAutoCompletedProperty().set(value);
	}

	public final EventHandler<AutoCompletionEvent<T>> getOnAutoCompleted()
	{
		return this.onAutoCompleted == null ? null : this.onAutoCompleted.get();
	}

	/*
	public final ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompletedProperty() {
		if (onAutoCompleted == null) {
			onAutoCompleted = new ObjectPropertyBase<EventHandler<AutoCompletionEvent<T>>>() {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				protected void invalidated()
				{
					eventHandlerManager.setEventHandler(AutoCompletionEvent.AUTO_COMPLETED, (EventHandler<AutoCompletionEvent>)(Object)get());
				}
	
				@Override
				public Object getBean()
				{
					return SearchableTextField.this;
				}
	
				@Override
				public String getName() {
					return "onAutoCompleted"; //$NON-NLS-1$
				}
			};
		}
		return onAutoCompleted;
	}*/

	/***************************************************************************
	 * *
	 * EventTarget Implementation *
	 * *
	 **************************************************************************/

	private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);

	/**
	 * Registers an event handler to this EventTarget. The handler is called when the
	 * menu item receives an {@code Event} of the specified type during the bubbling
	 * phase of event delivery.
	 *
	 * @param <E>
	 *            the specific event class of the handler
	 * @param eventType
	 *            the type of the events to receive by the handler
	 * @param eventHandler
	 *            the handler to register
	 * @throws NullPointerException
	 *             if the event type or handler is null
	 */
	/*
	public <E extends Event> void addEventHandler(EventType<E> eventType, EventHandler<E> eventHandler)
	{
		eventHandlerManager.addEventHandler(eventType, eventHandler);
	}
	 */

	/**
	 * Unregisters a previously registered event handler from this EventTarget. One
	 * handler might have been registered for different event types, so the
	 * caller needs to specify the particular event type from which to
	 * unregister the handler.
	 *
	 * @param <E>
	 *            the specific event class of the handler
	 * @param eventType
	 *            the event type from which to unregister
	 * @param eventHandler
	 *            the handler to unregister
	 * @throws NullPointerException
	 *             if the event type or handler is null
	 */

	/*public <E extends Event> void removeEventHandler(EventType<E> eventType, EventHandler<E> eventHandler)
	{
		eventHandlerManager.removeEventHandler(eventType, eventHandler);
	}*/

	/** {@inheritDoc} */
	@Override
	public EventDispatchChain buildEventDispatchChain(final EventDispatchChain tail)
	{
		//return tail.prepend(eventHandlerManager);
		return super.buildEventDispatchChain(tail).append(this.eventHandlerManager);
	}
}