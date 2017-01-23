package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.tc.app.exchangemonitor.model.cayenne.persistent.Commodity;
import com.tc.app.exchangemonitor.model.cayenne.persistent.IctsUser;
import com.tc.app.exchangemonitor.model.cayenne.persistent.Uom;

import control.SearchableTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class TestController implements Initializable
{
	@FXML
	private TextField txt;
	@FXML
	private SearchableTextField<IctsUser> traderSearchableTextField;
	@FXML
	private SearchableTextField<Uom> uomSearchableTextField;
	@FXML
	private SearchableTextField<Commodity> commoditySearchableTextField;

	@Override
	public void initialize(final URL location, final ResourceBundle resources)
	{
		System.out.println("traderSearchableTextField : " + this.traderSearchableTextField);
		System.out.println("uomSearchableTextField : " + this.uomSearchableTextField);
		System.out.println("commoditySearchableTextField : " + this.commoditySearchableTextField);

		this.traderSearchableTextField.setOnAutoCompleted(event -> System.out.println("just to test...."));
	}
}