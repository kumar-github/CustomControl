package application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Hasan Kara <hasan.kara@fhnw.ch>
 */
public class EditableTable extends Application
{

	private final TableView<Person> table = new TableView<>();
	private final ObservableList<Typ> typData = FXCollections.observableArrayList(new Typ("Hund"), new Typ("Fuchs"), new Typ("Esel"));
	private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Jacob", this.typData.get(0), new Date()), new Person("Abcd", null, null), new Person("Urs", this.typData.get(1), new Date()), new Person("Hans", this.typData.get(2), new Date()), new Person("Ueli", this.typData.get(2), new Date()));

	final HBox hb = new HBox();

	@Override
	public void start(final Stage stage)
	{
		final Scene scene = new Scene(new Group());
		stage.setWidth(550);
		stage.setHeight(550);

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));

		this.table.setEditable(true);
		final Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = (final TableColumn<Person, String> param) -> new EditingCell();
		final Callback<TableColumn<Person, Typ>, TableCell<Person, Typ>> comboBoxCellFactory = (final TableColumn<Person, Typ> param) -> new ComboBoxEditingCell();
		final Callback<TableColumn<Person, Date>, TableCell<Person, Date>> dateCellFactory = (final TableColumn<Person, Date> param) -> new DateEditingCell();

		final TableColumn<Person, String> firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		firstNameCol.setCellFactory(cellFactory);
		firstNameCol.setOnEditCommit((final TableColumn.CellEditEvent<Person, String> t) -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(t.getNewValue());

		});

		final TableColumn<Person, Typ> lastNameCol = new TableColumn("Type");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(cellData -> cellData.getValue().typObjProperty());
		lastNameCol.setCellFactory(comboBoxCellFactory);
		lastNameCol.setOnEditCommit((final TableColumn.CellEditEvent<Person, Typ> t) -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setTypObj(t.getNewValue());

		});

		final TableColumn<Person, Date> emailCol = new TableColumn("Birthday");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
		emailCol.setCellFactory(dateCellFactory);
		emailCol.setOnEditCommit((final TableColumn.CellEditEvent<Person, Date> t) -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBirthday(t.getNewValue());

		});

		this.table.setItems(this.data);
		this.table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		final TextField addFirstName = new TextField();
		addFirstName.setPromptText("First Name");
		addFirstName.setMaxWidth(firstNameCol.getPrefWidth());

		final TextField addLastName = new TextField();
		addLastName.setPromptText("Last Name");
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());

		final TextField addEmail = new TextField();
		addEmail.setPromptText("email");
		addEmail.setMaxWidth(emailCol.getPrefWidth());

		final Button addButton = new Button("Add");
		addButton.setOnAction((final ActionEvent e) -> {
			this.data.add(new Person(addFirstName.getText(), new Typ("Hund"), new Date()));
			addFirstName.clear();
			addLastName.clear();
			addEmail.clear();
		});

		final Button showButton = new Button("Show");
		showButton.setOnAction((final ActionEvent e) -> {
			this.table.getItems().forEach(System.out::println);
		});

		this.hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton, showButton);
		this.hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, this.table, this.hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args)
	{
		launch(args);
	}

	class EditingCell extends TableCell<Person, String>
	{

		private TextField textField;

		private EditingCell()
		{
		}

		@Override
		public void startEdit()
		{
			if(!this.isEmpty())
			{
				super.startEdit();
				this.createTextField();
				this.setText(null);
				this.setGraphic(this.textField);
				this.textField.selectAll();
			}
		}

		@Override
		public void cancelEdit()
		{
			super.cancelEdit();

			this.setText(this.getItem());
			this.setGraphic(null);
		}

		@Override
		public void updateItem(final String item, final boolean empty)
		{
			super.updateItem(item, empty);

			if(empty)
			{
				this.setText(item);
				this.setGraphic(null);
			}
			else
			{
				if(this.isEditing())
				{
					if(this.textField != null)
					{
						this.textField.setText(this.getString());
						//                        setGraphic(null);
					}
					this.setText(null);
					this.setGraphic(this.textField);
				}
				else
				{
					this.setText(this.getString());
					this.setGraphic(null);
				}
			}
		}

		private void createTextField()
		{
			this.textField = new TextField(this.getString());
			this.textField.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));
			this.textField.setOnAction((e) -> this.commitEdit(this.textField.getText()));
			this.textField.focusedProperty().addListener((final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) -> {
				if(!newValue)
				{
					System.out.println("Commiting " + this.textField.getText());
					this.commitEdit(this.textField.getText());
				}
			});
		}

		private String getString()
		{
			return this.getItem() == null ? "" : this.getItem();
		}
	}

	class DateEditingCell extends TableCell<Person, Date>
	{

		private DatePicker datePicker;

		private DateEditingCell()
		{
		}

		@Override
		public void startEdit()
		{
			if(!this.isEmpty())
			{
				super.startEdit();
				this.createDatePicker();
				this.setText(null);
				this.setGraphic(this.datePicker);
			}
		}

		@Override
		public void cancelEdit()
		{
			super.cancelEdit();

			this.setText(this.getDate().toString());
			this.setGraphic(null);
		}

		@Override
		public void updateItem(final Date item, final boolean empty)
		{
			super.updateItem(item, empty);

			if(empty)
			{
				this.setText(null);
				this.setGraphic(null);
			}
			else
			{
				if(this.isEditing())
				{
					if(this.datePicker != null)
					{
						this.datePicker.setValue(this.getDate());
					}
					this.setText(null);
					this.setGraphic(this.datePicker);
				}
				else
				{
					if(this.getDate() != null)
					{
						if(this.getDate() != null)
						{
							this.setText(this.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
						}
						else
						{
							this.setText(null);
						}
						this.setGraphic(null);
					}
					else
					{
						this.setText(null);
						this.setGraphic(null);
					}
				}
			}
		}

		private void createDatePicker()
		{
			this.datePicker = new DatePicker(this.getDate());

			this.datePicker.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));
			this.datePicker.setOnAction((e) -> {
				System.out.println("Committed: " + this.datePicker.getValue().toString());
				this.commitEdit(Date.from(this.datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			});
			//            datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//                if (!newValue) {
			//                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			//                }
			//            });
		}

		private LocalDate getDate()
		{
			//return this.getItem() == null ? LocalDate.now() : this.getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			return this.getItem() == null ? null : this.getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}

	class ComboBoxEditingCell extends TableCell<Person, Typ>
	{

		private ComboBox<Typ> comboBox;

		private ComboBoxEditingCell()
		{
		}

		@Override
		public void startEdit()
		{
			if(!this.isEmpty())
			{
				super.startEdit();
				this.createComboBox();
				this.setText(null);
				this.setGraphic(this.comboBox);
			}
		}

		@Override
		public void cancelEdit()
		{
			super.cancelEdit();

			this.setText(this.getTyp().getTyp());
			this.setGraphic(null);
		}

		@Override
		public void updateItem(final Typ item, final boolean empty)
		{
			super.updateItem(item, empty);

			if(empty)
			{
				this.setText(null);
				this.setGraphic(null);
			}
			else
			{
				if(this.isEditing())
				{
					if(this.comboBox != null)
					{
						this.comboBox.setValue(this.getTyp());
					}
					this.setText(this.getTyp().getTyp());
					this.setGraphic(this.comboBox);
				}
				else
				{
					this.setText(this.getTyp().getTyp());
					this.setGraphic(null);
				}
			}
		}

		private void createComboBox()
		{
			this.comboBox = new ComboBox<>(EditableTable.this.typData);
			this.comboBoxConverter(this.comboBox);
			this.comboBox.valueProperty().set(this.getTyp());
			this.comboBox.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));
			this.comboBox.setOnAction((e) -> {
				System.out.println("Committed: " + this.comboBox.getSelectionModel().getSelectedItem());
				this.commitEdit(this.comboBox.getSelectionModel().getSelectedItem());
			});
			//            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			//                if (!newValue) {
			//                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
			//                }
			//            });
		}

		private void comboBoxConverter(final ComboBox<Typ> comboBox)
		{
			// Define rendering of the list of values in ComboBox drop down.
			comboBox.setCellFactory((c) -> {
				return new ListCell<Typ>(){
					@Override
					protected void updateItem(final Typ item, final boolean empty)
					{
						super.updateItem(item, empty);

						if((item == null) || empty)
						{
							this.setText(null);
						}
						else
						{
							this.setText(item.getTyp());
						}
					}
				};
			});
		}

		private Typ getTyp()
		{
			return this.getItem() == null ? new Typ("") : this.getItem();
		}
	}

	public static class Typ
	{

		private final SimpleStringProperty typ;

		public Typ(final String typ)
		{
			this.typ = new SimpleStringProperty(typ);
		}

		public String getTyp()
		{
			return this.typ.get();
		}

		public StringProperty typProperty()
		{
			return this.typ;
		}

		public void setTyp(final String typ)
		{
			this.typ.set(typ);
		}

		@Override
		public String toString()
		{
			return this.typ.get();
		}

	}

	public static class Project
	{
		private final SimpleStringProperty name;
		private final SimpleListProperty<Person> persons;

		public Project(final String name, final List<Person> persons)
		{
			this.name = new SimpleStringProperty(name);

			this.persons = new SimpleListProperty<>();
			this.persons.setAll(persons);
		}

		public String getName()
		{
			return this.name.get();
		}

		public StringProperty nameProperty()
		{
			return this.name;
		}

		public void setName(final String name)
		{
			this.name.set(name);
		}

		public List<Person> getPersons()
		{
			return this.persons.get();
		}

		public SimpleListProperty<Person> personsProperty()
		{
			return this.persons;
		}

		public void setPersons(final List<Person> persons)
		{
			this.persons.setAll(persons);
		}

	}

	public static class Person
	{

		private final SimpleStringProperty firstName;
		private final SimpleObjectProperty<Typ> typ;
		private final SimpleObjectProperty<Date> birthday;

		public Person(final String firstName, final Typ typ, final Date bithday)
		{
			this.firstName = new SimpleStringProperty(firstName);
			this.typ = new SimpleObjectProperty(typ);
			this.birthday = new SimpleObjectProperty(bithday);
		}

		public String getFirstName()
		{
			return this.firstName.get();
		}

		public StringProperty firstNameProperty()
		{
			return this.firstName;
		}

		public void setFirstName(final String firstName)
		{
			this.firstName.set(firstName);
		}

		public Typ getTypObj()
		{
			return this.typ.get();
		}

		public ObjectProperty<Typ> typObjProperty()
		{
			return this.typ;
		}

		public void setTypObj(final Typ typ)
		{
			this.typ.set(typ);
		}

		public Date getBirthday()
		{
			return this.birthday.get();
		}

		public ObjectProperty<Date> birthdayProperty()
		{
			return this.birthday;
		}

		public void setBirthday(final Date birthday)
		{
			this.birthday.set(birthday);
		}

		@Override
		public String toString()
		{
			return this.getFirstName() + this.getBirthday() + this.getTypObj().getTyp();
		}
	}
}