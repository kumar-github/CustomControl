package behavior;

import com.sun.javafx.scene.control.behavior.TextFieldBehavior;

import control.SearchableTextField;

public class SearchableTextFieldBehavior<T> extends TextFieldBehavior
{
	public SearchableTextFieldBehavior(SearchableTextField<T> searchableTextField)
	{
		super(searchableTextField);
	}
}