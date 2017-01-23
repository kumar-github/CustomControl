package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ListBinding;
import javafx.beans.binding.LongBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

public class Controller
{
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField zipCodeTextField;
	@FXML
	private Tab nameTab;
	@FXML
	private Tab contactTab;
	@FXML
	private Tab confirmationTab;
	@FXML
	private TabPane tabPane;
	@FXML
	private TitledPane nameTabErrorList;
	@FXML
	private Pane nameTabErrorMessages;
	@FXML
	private TitledPane contactTabErrorList;
	@FXML
	private Pane contactTabErrorMessages;
	@FXML
	private WebView browser;
	@FXML
	private Label nameTabErrorInstructions;
	@FXML
	private Label contactTabErrorInstructions;

	public void initialize()
	{
		// Bit of a hack. Probably need a ValidationBinding extends BooleanBinding with a message property:
		Map<BooleanBinding, String> messages = new HashMap<>();

		tabPane.getSelectionModel().select(nameTab);
		BooleanBinding firstNameInvalid = emptyTextFieldBinding(firstNameTextField, "First Name is required", messages);
		BooleanBinding lastNameInvalid = emptyTextFieldBinding(lastNameTextField, "Last Name is required", messages);
		final Pattern emailPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*@[a-zA-Z][a-zA-Z0-9_]+(\\.[a-zA-Z][a-zA-Z0-9_]*)+");
		BooleanBinding emailInvalid = patternTextFieldBinding(emailTextField, emailPattern, "You must enter a valid email", messages);
		final Pattern zipPattern = Pattern.compile("[0-9]{5}");
		BooleanBinding zipInvalid = patternTextFieldBinding(zipCodeTextField, zipPattern, "You must enter a 5-digit zip code", messages);

		BooleanBinding[] nameTabBindings = {firstNameInvalid, lastNameInvalid};
		BooleanBinding[] contactTabBindings = {emailInvalid, zipInvalid};

		BooleanBinding nameTabInvalid = any(nameTabBindings);
		BooleanBinding contactTabInvalid = any(contactTabBindings);

		contactTab.disableProperty().bind(nameTabInvalid);
		confirmationTab.disableProperty().bind(nameTabInvalid.or(contactTabInvalid));

		nameTabErrorInstructions.visibleProperty().bind(nameTabInvalid);
		contactTabErrorInstructions.visibleProperty().bind(contactTabInvalid);

		bindMessageLabels(nameTabBindings, nameTabErrorMessages.getChildren(), messages);

		final LongBinding nameTabErrorCount = count(nameTabBindings);
		nameTabErrorList.textProperty().bind(Bindings.format("%d %s on this page", nameTabErrorCount, Bindings.when(nameTabErrorCount.isEqualTo(1)).then("error").otherwise("errors")));
		//nameTabErrorList.textProperty().bind(Bindings.format("%s", Bindings.when(true)));

		bindMessageLabels(contactTabBindings, contactTabErrorMessages.getChildren(), messages);

		final LongBinding contactTabErrorCount = count(contactTabBindings);
		contactTabErrorList.textProperty().bind(Bindings.format("%d %s on this page", contactTabErrorCount, Bindings.when(contactTabErrorCount.isEqualTo(1)).then("error").otherwise("errors")));

		browser.getEngine().load("http://stackoverflow.com/questions/22772364/javafx-prevent-selection-of-a-different-tab-if-the-data-validation-of-the-selec/");
	}

	private void bindMessageLabels(BooleanBinding[] validationBindings, List<Node> labelList, Map<BooleanBinding, String> messages)
	{
		ListBinding<Node> nodeListBinding = new ListBinding<Node>(){

			{
				// calling bind(...) here won't work, neither will using WeakInvalidationListeners. Not sure why....
				InvalidationListener invalidationListener = obs -> invalidate();
				Arrays.stream(validationBindings).forEach(binding -> binding.addListener(invalidationListener));
			}

			@Override
			protected ObservableList<Node> computeValue()
			{
				return FXCollections.observableList(Arrays.stream(validationBindings).filter(BooleanBinding::get).map(messages::get).map(Label::new).collect(Collectors.toList()));
			}
		};

		Bindings.bindContent(labelList, nodeListBinding);
	}

	private BooleanBinding emptyTextFieldBinding(TextField textField, String message, Map<BooleanBinding, String> messages)
	{
		BooleanBinding binding = Bindings.createBooleanBinding(() -> textField.getText().trim().isEmpty(), textField.textProperty());
		configureTextFieldBinding(binding, textField, message, messages);
		return binding;
	}

	private BooleanBinding patternTextFieldBinding(TextField textField, Pattern pattern, String message, Map<BooleanBinding, String> messages)
	{
		BooleanBinding binding = Bindings.createBooleanBinding(() -> !pattern.matcher(textField.getText()).matches(), textField.textProperty());
		configureTextFieldBinding(binding, textField, message, messages);
		return binding;
	}

	private void configureTextFieldBinding(BooleanBinding binding, TextField textField, String message, Map<BooleanBinding, String> messages)
	{
		messages.put(binding, message);
		if(textField.getTooltip() == null)
		{
			textField.setTooltip(new Tooltip());
		}
		String tooltipText = textField.getTooltip().getText();
		binding.addListener((obs, oldValue, newValue) -> {
			updateTextFieldValidationStatus(textField, tooltipText, newValue, message);
		});
		updateTextFieldValidationStatus(textField, tooltipText, binding.get(), message);
	}

	private BooleanBinding any(BooleanBinding[] bindings)
	{
		return Bindings.createBooleanBinding(() -> Arrays.stream(bindings).anyMatch(BooleanBinding::get), bindings);
	}

	private LongBinding count(BooleanBinding[] bindings)
	{
		return Bindings.createLongBinding(() -> Arrays.stream(bindings).filter(BooleanBinding::get).collect(Collectors.counting()), bindings);
	}

	private void updateTextFieldValidationStatus(TextField textField, String defaultTooltipText, boolean invalid, String message)
	{
		textField.pseudoClassStateChanged(PseudoClass.getPseudoClass("validation-error"), invalid);
		String tooltipText;
		if(invalid)
		{
			tooltipText = message;
		}
		else
		{
			tooltipText = defaultTooltipText;
		}
		if(tooltipText == null || tooltipText.isEmpty())
		{
			textField.setTooltip(null);
		}
		else
		{
			Tooltip tooltip = textField.getTooltip();
			if(tooltip == null)
			{
				textField.setTooltip(new Tooltip(tooltipText));
			}
			else
			{
				tooltip.setText(tooltipText);
			}
		}
	}

}
