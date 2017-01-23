package application;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableWithComboBoxExample extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final TableView<Contact> contactTable = new TableView<>();
        contactTable.setEditable(true);

        final TableColumn<Contact, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactTable.getColumns().add(nameCol);


        final TableColumn<Contact, String> categoryCol = new TableColumn<>("Category");
		categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

		categoryCol.setCellFactory(ComboBoxTableCell.forTableColumn("Friends", "Family", "Work Contacts", "Something"));

        contactTable.getColumns().add(categoryCol);

        contactTable.getItems().addAll(
            new Contact("Bill Gates", "Work Contacts"),
            new Contact("Barack Obama", "Friends"),
            new Contact("Tim Cook", "Work Contacts")
        );

        final Scene scene = new Scene(new BorderPane(contactTable), 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class Contact {
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty category = new SimpleStringProperty();

        public Contact(final String name, final String category) {
            this.setName(name);
            this.setCategory(category);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }


        public final String getName() {
            return this.nameProperty().get();
        }


        public final void setName(final String name) {
            this.nameProperty().set(name);
        }


        public final StringProperty categoryProperty() {
            return this.category;
        }


        public final String getCategory() {
            return this.categoryProperty().get();
        }


        public final void setCategory(final String category) {
            this.categoryProperty().set(category);
        }

    }

    public static void main(final String[] args) {
        launch(args);
    }
}